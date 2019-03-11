package com.freak.httphelper;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author freak
 * @date 2019/01/25
 */
public class SubscriberCallBack<T> implements Observer<T> {
    private ApiCallback<T> apiCallback;

    public SubscriberCallBack(ApiCallback<T> apiCallback) {
        this.apiCallback = apiCallback;
    }



    /**
     * 错误时调用
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        try {
            String msg;
            if (e instanceof SocketTimeoutException) {
                msg = "连接服务器超时,请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof ConnectException) {
                msg = "网络中断，请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof TimeoutException) {
                msg = "连接超时，请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof JsonSyntaxException) {
                Log.e("SubscriberCallBack", "JSON解析错误，请查看JSON结构", e);
                e.printStackTrace();
                apiCallback.onFailure(e.getMessage());
            } else {
                apiCallback.onFailure(e.getMessage());
            }
        } catch (Exception e1) {
            apiCallback.onFailure(e1.getMessage());
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable disposable) {
        HttpMethods.getCompositeDisposableInstance().add(disposable);
    }

    /**
     * 成功时调用
     *
     * @param o
     */
    @Override
    public void onNext(T o) {
        apiCallback.onSuccess(o);
    }

}
