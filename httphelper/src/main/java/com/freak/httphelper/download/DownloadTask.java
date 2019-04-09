package com.freak.httphelper.download;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.File;

import retrofit2.Call;

/**
 * Created by Administrator on 2019/4/9.
 */

public class DownloadTask implements Runnable,ProgressListener {
    //更新任务进度消息
    private static final int UPDATE_PROGRESS_ID = 0x100;
    //下载网络服务
    private HttpDownloadApiService mHttpDownloadApiService;
    //下载任务状态
    private STATE state;
    //下载实体类，使用object基类，方便统一获取
    private Object downloadObject;

    //网络下载请求对象
    private Call<File> downloadCall;
    //下载保存文件对象
    private File downloadFile = null;
    //下载任务进度监听器
    private OnProgressListener onProgressListener;
    private DownloadTask mHttpDownloadTask;
    //是否是下载，区分当前任务是下载还是上传
    private boolean isDownload;

    @Override
    public void run() {
        start();
    }

    @Override
    public void onProgress(long addedBytes, long contentLenght, boolean done) {
        sendUpdateProgressMessage(addedBytes, contentLenght, false);
    }

    public enum STATE {
        IDLE,
        PENDING,
        LOADING,
        FAILED,
        FINISHED,
        UNKNOWN,
    }

    private void sendUpdateProgressMessage(long addedBytes, long contentLenght, boolean done) {
        Message message = handler.obtainMessage(UPDATE_PROGRESS_ID);
        message.obj = done;
        message.arg1 = (int) addedBytes;
        message.arg2 = (int) contentLenght;
        handler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_PROGRESS_ID) {
                if (onProgressListener != null) {
                    onProgressListener.onProgress(msg.arg1, msg.arg2, (Boolean) msg.obj);
                }
            }
        }
    };

//    public DownloadTask(final Object object, List<RequestParameter> list, final boolean download) {
//        downloadObject = object;
//        parameterList = list;
//        isDownload = download;
//        if (isDownload) {
//            mHttpDownloadApiService = HttpApiService.getDownloadApiService(this);
//        } else {
//            uploadApiService = HttpApiService.getUploadApiService(this);
//        }
//        state = STATE.IDLE;
//        mHttpDownloadTask = this;
//    }

    public void start() {
        if (state == STATE.LOADING) {
            return;
        }
        state = STATE.LOADING;
        if (isDownload) {
            download();
        }
    }

    private void download() {
//        if (parameterList != null && parameterList.size() > 1 && mHttpDownloadApiService != null) {
//            //change state pending or idle to loading, notify ui to update.
//            sendUpdateProgressMessage(0, 0, false);
//            String downloadFilename = parameterList.get(0).getValue();
//            String saveFilename = parameterList.get(1).getValue();
//            downloadCall = mHttpDownloadApiService.httpDownloadFile(downloadFilename, saveFilename);
//            downloadCall.enqueue(new Callback<File>() {
//                @Override
//                public void onResponse(Call<File> call, Response<File> response) {
//                    Log.i(response.toString());
//                    if (response.code() == 200) {
//                        mHttpDownloadTask.downloadFile = response.body();
//                        if (mHttpDownloadTask.downloadFile != null && !mHttpDownloadTask.downloadFile.getPath().endsWith(".tmp")) {
//                            sendUpdateProgressMessage(100, 100, true);
//                            mHttpDownloadTask.state = STATE.FINISHED;
//                            TaskDispatcher.getInstance().finished(mHttpDownloadTask);
//                        } else {
//                            mHttpDownloadTask.state = STATE.FAILED;
//                            sendUpdateProgressMessage(0, 0, false);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<File> call, Throwable t) {
//                    mHttpDownloadTask.state = STATE.FAILED;
//                    sendUpdateProgressMessage(0, 0, false);
//                }
//            });
//        }
    }


    public void cancel() {
        if (downloadCall != null) {
            downloadCall.cancel();
        }

        handler.removeMessages(UPDATE_PROGRESS_ID);
    }

    public void setState(final STATE state) {
        this.state = state;
    }

    public STATE getState() {
        return state;
    }

    public Object getDownloadObject() {
        return downloadObject;
    }

    public void setDownloadObject(Object downloadObject) {
        this.downloadObject = downloadObject;
    }

    public File getDownloadFile() {
        return downloadFile;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setOnProgressListener(final OnProgressListener listener) {
        onProgressListener = listener;
    }

    public interface OnProgressListener {
        void onProgress(long addedBytes, long contentLenght, boolean done);
    }

}
