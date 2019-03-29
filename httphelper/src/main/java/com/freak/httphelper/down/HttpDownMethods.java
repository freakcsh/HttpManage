package com.freak.httphelper.down;

import com.freak.httphelper.HttpMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
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
    private Map<String, HttpDownCallBack> mCallBackMap;
    private static HttpDownMethods mHttpDownMethods;

    public HttpDownMethods() {
        this.baseUrl = HttpMethods.baseUrl;
        mHttpDownInfoSet = new HashSet<>();
        mCallBackMap = new HashMap<>();
    }

    public static HttpDownMethods getInstance() {
        if (mHttpDownMethods != null) {
            synchronized (HttpDownMethods.class) {
                mHttpDownMethods = new HttpDownMethods();
            }
        }
        return mHttpDownMethods;
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

    /**
     * 开始下载
     *
     * @param httpDownInfo     下载信息
     * @param httpDownListener 下载监听
     */
    public void downStart(HttpDownInfo httpDownInfo, HttpDownListener httpDownListener) {
        start(httpDownInfo, httpDownListener, false);
    }

    /**
     * 开始下载
     *
     * @param httpDownInfo     下载信息
     * @param httpDownListener 监听
     * @param isMoreThread     是否多线程下载
     */
    public void start(final HttpDownInfo httpDownInfo, HttpDownListener httpDownListener, boolean isMoreThread) {

        if (httpDownInfo == null || httpDownInfo.getUrl() == null) {
            return;
        }
        if (mCallBackMap.get(httpDownInfo.getUrl()) != null) {
            mCallBackMap.get(httpDownInfo.getUrl()).setHttpDownInfo(httpDownInfo);
            return;
        }

        if (httpDownInfo.getReadLength() == httpDownInfo.getCountLength()) {
            httpDownInfo.setReadLength(0);
        }

        httpDownInfo.setListener(httpDownListener);
        HttpDownCallBack callBack = new HttpDownCallBack(httpDownInfo);

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
        mHttpDownInfoSet.add(httpDownInfo);
        mCallBackMap.put(httpDownInfo.getUrl(), callBack);
    }

    /**
     * 单线程下载
     * @param retrofit Retrofit
     * @param start 开始位置
     * @param httpDownInfo 下载信息
     * @return Observable
     */
    private Observable downLoad(Retrofit retrofit, long start, final HttpDownInfo httpDownInfo){
        return  retrofit.create(HttpDownloadApiService.class).downFile("bytes=" + start + "-", httpDownInfo.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, HttpDownInfo>() {

                    @Override
                    public HttpDownInfo apply(@NonNull ResponseBody responseBody) throws Exception {
                        writeToCaches(responseBody,httpDownInfo);
                        return httpDownInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
    private void writeToCaches(ResponseBody body,HttpDownInfo httpDownInfo){

        RandomAccessFile randomAccessFile=null;
        FileChannel fileChannel=null;
        InputStream inputStream=null;

        File file=new File(httpDownInfo.getSavePath());
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            long allLength = 0 == httpDownInfo.getCountLength() ? body.contentLength() : httpDownInfo.getReadLength() + body
                    .contentLength();

            randomAccessFile=new RandomAccessFile(file,"rwd");
            fileChannel=randomAccessFile.getChannel();
            inputStream=body.byteStream();
            MappedByteBuffer byteBuffer=fileChannel.map(FileChannel.MapMode.READ_WRITE,httpDownInfo.getReadLength(), allLength - httpDownInfo.getReadLength());

            byte[] buffer=new byte[1024*4];
            int len;
            while((len=inputStream.read(buffer))!=-1){
                byteBuffer.put(buffer,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
                if(fileChannel!=null){
                    fileChannel.close();
                }
                if(randomAccessFile!=null){
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
