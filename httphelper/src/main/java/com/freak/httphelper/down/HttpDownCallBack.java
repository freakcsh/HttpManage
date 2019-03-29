package com.freak.httphelper.down;

import android.annotation.SuppressLint;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HttpDownCallBack<T> implements Observer<T>, ProgressListener {
    private HttpDownListener mHttpDownListener;
    private Disposable mDisposable;
    private HttpDownInfo mHttpDownInfo;
    private ResultListener mResultListener;

    public HttpDownCallBack( HttpDownInfo httpDownInfo) {
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
            bytesRead += mHttpDownInfo.getReadLength() - contentLength;
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
    }

    /**
     * 开始下载
     */
    public void downStart() {
        mHttpDownInfo.setState(HttpDownStatus.START);
        if (mHttpDownListener != null) {
            mHttpDownListener.downStart();
        }
    }

    /**
     * 暂停下载
     *
     * @param progress 当前下载进度（完成大小）
     */
    public void downPause(long progress) {
        mHttpDownInfo.setState(HttpDownStatus.PAUSE);
        if (mHttpDownListener != null) {
            mHttpDownListener.downPause(mHttpDownInfo.getReadLength());
        }
    }

    /**
     * 下载停止
     *
     * @param progress 当前下载进度（完成大小）
     */
    public void downStop(long progress) {
        mHttpDownInfo.setState(HttpDownStatus.STOP);
        if (mHttpDownListener != null) {
            mHttpDownListener.downStop(mHttpDownInfo.getReadLength());
        }
    }

}