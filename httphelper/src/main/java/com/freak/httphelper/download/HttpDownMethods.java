package com.freak.httphelper.download;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.freak.httphelper.HttpMethods;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 下载助手
 *
 * @author Administrator
 */
public class HttpDownMethods {
    /**
     * 这是连接网络的时间
     */
    private static int connectTimeOut = 0;
    private static int readTimeOut = 0;
    private static int writeTimeOut = 0;
    private static final int CONNECT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;
    private String baseUrl;
    private Set<HttpDownInfo> mHttpDownInfoSet;
    private Map<String, HttpDownCallBack<HttpDownInfo>> mCallBackMap;
    private static HttpDownMethods mHttpDownMethods;
    private List<HttpDownInfo> mTaskList;
    private static int mTaskCount = 0;
    private static final int TASK_COUNT = 2;
    private HttpDownListener mHttpDownListener;
    /**
     * 取消下载\停止下载时，是否删除未下载完成的文件，默认不删除
     */
    private boolean mIsDeleteFile = false;

    public HttpDownMethods() {
        this.baseUrl = HttpMethods.baseUrl;
        mHttpDownInfoSet = new HashSet<>();
        mCallBackMap = new HashMap<String, HttpDownCallBack<HttpDownInfo>>();
        mTaskList = Collections.synchronizedList(new ArrayList<HttpDownInfo>());
        setTaskCount(TASK_COUNT);
    }

    public static HttpDownMethods getInstance() {
        if (mHttpDownMethods == null) {
            synchronized (HttpDownMethods.class) {
                mHttpDownMethods = new HttpDownMethods();
            }
        }
        return mHttpDownMethods;
    }

    /**
     * 设置下载任务个数（最多同时下载多少个）
     *
     * @param mTaskCount
     */
    public static void setTaskCount(int mTaskCount) {
        HttpDownMethods.mTaskCount = mTaskCount;
    }

    public static int getTaskCount() {
        return mTaskCount;
    }

    public static void setConnectTimeOut(int connectTimeOut) {
        HttpDownMethods.connectTimeOut = connectTimeOut;
    }

    public static void setReadTimeOut(int readTimeOut) {
        HttpDownMethods.readTimeOut = readTimeOut;
    }

    public static void setWriteTimeOut(int writeTimeOut) {
        HttpDownMethods.writeTimeOut = writeTimeOut;
    }

    public void setHttpDownListener(HttpDownListener httpDownListener) {
        mHttpDownListener = httpDownListener;
    }

    public void setDeleteFile(boolean deleteFile) {
        mIsDeleteFile = deleteFile;
    }

    /**
     * 开始下载
     *
     * @param httpDownInfo     下载信息
     * @param httpDownListener 下载监听
     */
    public void downStart(HttpDownInfo httpDownInfo, HttpDownListener httpDownListener) {
        downStart(httpDownInfo, httpDownListener, false);
    }

    /**
     * 添加下载任务
     *
     * @param httpDownInfo
     */
    public boolean addDownTask(HttpDownInfo httpDownInfo) {
        if (mTaskList != null && !mTaskList.contains(httpDownInfo)) {
            mTaskList.add(httpDownInfo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 一键添加所有下载任务
     *
     * @param httpDownInfo
     */
    public synchronized void addDownTaskList(List<HttpDownInfo> httpDownInfo) {
        for (HttpDownInfo mHttpDownInfo : httpDownInfo) {
            addDownTask(mHttpDownInfo);
        }
    }

    public void moreTaskDownloadStart() {
        if (mTaskList == null) {
            new IllegalArgumentException("list is null");
            return;
        }
        for (HttpDownInfo httpDownInfo : mTaskList) {
            if (httpDownInfo.getState() == HttpDownStatus.FINISH || httpDownInfo.getState() == HttpDownStatus.STOP){
//                mTaskList.remove()
            }
        }
        downStartAll(mTaskList);
    }

    private synchronized void downStartAll(List<HttpDownInfo> httpDownInfoList) {

        for (ListIterator<HttpDownInfo> iterator = httpDownInfoList.listIterator(); iterator.hasNext(); ) {
            Logger.e("剩余下载任务数：" + getTaskCount());
            HttpDownInfo httpDownInfo = iterator.next();
            if (getTaskCount() > 0) {
                if (httpDownInfo.getState() == HttpDownStatus.WAITING) {
                    Logger.e("下载等待任务");
                    if (mHttpDownListener != null) {
                        downStart(httpDownInfo, mHttpDownListener);
                    } else {
                        downStart(httpDownInfo, null);
                    }
                    setTaskCount(getTaskCount() - 1);
                }
                if (httpDownInfo.getState() == HttpDownStatus.PAUSE) {
                    Logger.e("下载暂停任务");
                    if (mHttpDownListener != null) {
                        downStart(httpDownInfo, mHttpDownListener);
                    } else {
                        downStart(httpDownInfo, null);
                    }
                    setTaskCount(getTaskCount() - 1);
                }
                if (httpDownInfo.getState() == HttpDownStatus.ERROR) {
                    Logger.e("下载错误任务");
                    if (mHttpDownListener != null) {
                        downStart(httpDownInfo, mHttpDownListener);
                    } else {
                        downStart(httpDownInfo, null);
                    }
                    setTaskCount(getTaskCount() - 1);
                }
            }

        }

    }

    public void handleTask(int type) {
        switch (type) {
            case HttpDownStatus.START:
                Log.d("DownTask", "开始下载,下载总数量" + mTaskList.size());
                break;
            case HttpDownStatus.PAUSE:
                setTaskCount(getTaskCount() + 1);
                moreTaskDownloadStart();
                break;
            case HttpDownStatus.STOP:
                setTaskCount(getTaskCount() + 1);
                moreTaskDownloadStart();
                break;
            case HttpDownStatus.FINISH:
                setTaskCount(getTaskCount() + 1);
                Logger.e("下载完成，释放任务之后的任务数量：" + getTaskCount());
                moreTaskDownloadStart();
                break;
            case HttpDownStatus.ERROR:
                setTaskCount(getTaskCount() + 1);
                moreTaskDownloadStart();
                break;
            case 7:
                break;
            default:
                break;
        }
    }

    /**
     * 开始下载
     *
     * @param httpDownInfo     下载信息
     * @param httpDownListener 监听
     * @param isMoreThread     是否多线程下载
     */
    public void downStart(final HttpDownInfo httpDownInfo, HttpDownListener httpDownListener, boolean isMoreThread) {
        //正在下载不处理
        if (httpDownInfo == null || httpDownInfo.getUrl() == null) {
            return;
        }
        //正在下载不处理
        if (mCallBackMap.get(httpDownInfo.getUrl()) != null) {
            mCallBackMap.get(httpDownInfo.getUrl()).setHttpDownInfo(httpDownInfo);
            return;
        }

        if (httpDownInfo.getReadLength() == httpDownInfo.getCountLength()) {
            httpDownInfo.setReadLength(0);
        }
        //添加回调处理类
        if (httpDownListener != null) {
            httpDownInfo.setListener(httpDownListener);
        }
        HttpDownCallBack<HttpDownInfo> callBack = new HttpDownCallBack<>(httpDownInfo);
        callBack.setResultListener(new ResultListener() {
            @Override
            public void downFinish(HttpDownInfo httpDownInfo) {
                mCallBackMap.remove(httpDownInfo.getUrl());
            }

            @Override
            public void downError(HttpDownInfo httpDownInfo) {
                mCallBackMap.remove(httpDownInfo.getUrl());
            }

        });
        //创建自定义下载拦截器
        HttpDownInterceptor interceptor = new HttpDownInterceptor(callBack);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeOut == 0 ? CONNECT_TIMEOUT : connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut == 0 ? READ_TIMEOUT : readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut == 0 ? WRITE_TIMEOUT : writeTimeOut, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();

        if (httpDownListener != null) {
            httpDownListener.downStart();
        }

        downLoad(retrofit, httpDownInfo.getReadLength(), httpDownInfo).subscribe(callBack);


        callBack.downStart();

        httpDownInfo.setState(HttpDownStatus.START);
        //记录回调mCallBackMap
        mHttpDownInfoSet.add(httpDownInfo);
        mCallBackMap.put(httpDownInfo.getUrl(), callBack);
    }

    /**
     * 单线程下载
     *
     * @param retrofit     Retrofit
     * @param start        开始位置
     * @param httpDownInfo 下载信息
     * @return Observable
     */
    private Observable<HttpDownInfo> downLoad(Retrofit retrofit, long start, final HttpDownInfo httpDownInfo) {
        return retrofit.create(HttpDownloadApiService.class).downFile("bytes=" + start + "-", httpDownInfo.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, HttpDownInfo>() {

                    @Override
                    public HttpDownInfo apply(@NonNull ResponseBody responseBody) throws Exception {
                        writeToCaches(responseBody, httpDownInfo);
                        return httpDownInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void writeToCaches(ResponseBody body, HttpDownInfo httpDownInfo) {

        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        InputStream inputStream = null;

        File file = new File(getPathname(httpDownInfo));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            long allLength = 0 == httpDownInfo.getCountLength() ? body.contentLength() : httpDownInfo.getReadLength() + body
                    .contentLength();

            randomAccessFile = new RandomAccessFile(file, "rwd");
            fileChannel = randomAccessFile.getChannel();
            inputStream = body.byteStream();
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, httpDownInfo.getReadLength(), allLength - httpDownInfo.getReadLength());

            byte[] buffer = new byte[1024 * 4];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.put(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPathname(HttpDownInfo httpDownInfo) {
        return TextUtils.isEmpty(httpDownInfo.getSavePath()) ? Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + getFileName(httpDownInfo.getUrl()) : httpDownInfo.getSavePath();
    }

    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 停止下载
     *
     * @param httpDownInfo 下载信息
     */
    public HttpDownInfo downStop(HttpDownInfo httpDownInfo) {
        if (httpDownInfo != null) {
            if (httpDownInfo.getState() == HttpDownStatus.PAUSE) {
                if (mHttpDownListener != null) {
                    mHttpDownListener.downStop();
                }
            }
            if (mCallBackMap.containsKey(httpDownInfo.getUrl())) {
                HttpDownCallBack<HttpDownInfo> callBack = mCallBackMap.get(httpDownInfo.getUrl());
                assert callBack != null;
                callBack.getDisposable().dispose();
                callBack.downStop();
                mCallBackMap.remove(httpDownInfo.getUrl());

                if (mIsDeleteFile) {
                    File file = new File(getPathname(httpDownInfo));
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
        return httpDownInfo;
    }

    /**
     * 下载暂停
     *
     * @param httpDownInfo
     */
    public HttpDownInfo downPause(HttpDownInfo httpDownInfo) {
        if (httpDownInfo != null) {
            if (mCallBackMap.containsKey(httpDownInfo.getUrl())) {
                HttpDownCallBack<HttpDownInfo> callBack = mCallBackMap.get(httpDownInfo.getUrl());
                assert callBack != null;
                callBack.getDisposable().dispose();
                callBack.downPause();
                httpDownInfo.setState(HttpDownStatus.PAUSE);
                mTaskList.remove(httpDownInfo);
                mCallBackMap.remove(httpDownInfo.getUrl());
            }
        }
        return httpDownInfo;
    }

    /**
     * 停止所有的下载
     */
    public void downStopAll() {
        for (HttpDownInfo httpDownInfo : mHttpDownInfoSet) {
            downStop(httpDownInfo);
        }
        mCallBackMap.clear();
        mTaskList.clear();
        mHttpDownInfoSet.clear();
    }

    /**
     * 暂停所有
     */
    public void downPauseAll() {
        for (HttpDownInfo httpDownInfo : mHttpDownInfoSet) {
            downPause(httpDownInfo);
        }
        mCallBackMap.clear();
        mHttpDownInfoSet.clear();
    }

    public void remove(HttpDownInfo httpDownInfo) {
        mCallBackMap.remove(httpDownInfo.getUrl());
        mHttpDownInfoSet.remove(httpDownInfo);
    }

}
