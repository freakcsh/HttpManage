package com.freak.httphelper.download;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author Administrator
 */
public class HttpDownCallBack<T> implements Observer<T>, ProgressListener {
    private HttpDownListener mHttpDownListener;
    private Disposable mDisposable;
    private HttpDownInfo mHttpDownInfo;
    private ResultListener mResultListener;

    public HttpDownCallBack(HttpDownInfo httpDownInfo) {
        mHttpDownListener = httpDownInfo.getListener();
        mHttpDownInfo = httpDownInfo;
    }

    public HttpDownInfo getHttpDownInfo() {
        return mHttpDownInfo;
    }

    public void setHttpDownInfo(HttpDownInfo httpDownInfo) {
        mHttpDownInfo = httpDownInfo;
    }

    public void setResultListener(ResultListener resultListener) {
        mResultListener = resultListener;
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onProgress(long bytesRead, long contentLength, boolean done) {
        if (mHttpDownInfo.getCountLength() > contentLength) {
            bytesRead = mHttpDownInfo.getCountLength() - contentLength + bytesRead;
        } else {
            mHttpDownInfo.setCountLength(contentLength);
        }

        mHttpDownInfo.setReadLength(bytesRead);
        if (mHttpDownListener != null) {
            if (mHttpDownInfo.getState() == HttpDownStatus.PAUSE || mHttpDownInfo.getState() == HttpDownStatus.STOP) {
                return;
            }

            Observable.just(bytesRead).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    Log.d("DownloadProgress", "Progress = " + aLong);
                    mHttpDownListener.downProgress(aLong, mHttpDownInfo.getCountLength());
                }
            });
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        mHttpDownInfo.setState(HttpDownStatus.ERROR);
        if (mHttpDownListener != null) {
            mHttpDownListener.downError(mHttpDownInfo, e.getMessage());
        }
        if (mResultListener != null) {
            mResultListener.downError(mHttpDownInfo);
        }
        mHandler.sendEmptyMessage(HttpDownStatus.ERROR);
    }

    @Override
    public void onComplete() {
        mHttpDownInfo.setState(HttpDownStatus.FINISH);
        if (mHttpDownListener != null) {
            mHttpDownListener.downFinish(mHttpDownInfo);
        }
        if (mResultListener != null) {
            mResultListener.downFinish(mHttpDownInfo);
        }
        mHandler.sendEmptyMessage(HttpDownStatus.FINISH);
        HttpDownMethods.getInstance().remove(mHttpDownInfo);
    }

    /**
     * 开始下载
     */
    public void downStart() {
        mHttpDownInfo.setState(HttpDownStatus.START);
        if (mHttpDownListener != null) {
            mHttpDownListener.downStart();
        }
        mHandler.sendEmptyMessage(HttpDownStatus.START);
    }

    /**
     * 暂停下载
     */
    public void downPause() {
        mHttpDownInfo.setState(HttpDownStatus.PAUSE);
        if (mHttpDownListener != null) {
            mHttpDownListener.downPause(mHttpDownInfo.getReadLength());
        }
        mHandler.sendEmptyMessage(HttpDownStatus.PAUSE);
    }

    /**
     * 停止下载、取消下载
     *
     * @param
     */
    public void downStop() {
        mHttpDownInfo.setState(HttpDownStatus.STOP);
        if (mHttpDownListener != null) {
            mHttpDownListener.downStop();
        }
        mHandler.sendEmptyMessage(HttpDownStatus.STOP);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpDownStatus.START:
                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.START);
                    break;
                case HttpDownStatus.PAUSE:
                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.PAUSE);
                    break;
                case HttpDownStatus.STOP:
                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.STOP);
                    break;
                case HttpDownStatus.FINISH:
                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.FINISH);
                    break;
                case HttpDownStatus.ERROR:
                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.ERROR);
                    break;
                default:
                    break;
            }
        }
    };

}
