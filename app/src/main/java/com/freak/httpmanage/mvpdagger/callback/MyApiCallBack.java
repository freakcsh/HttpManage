package com.freak.httpmanage.mvpdagger.callback;

import android.util.Log;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.SubscriberCallBack;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

public class MyApiCallBack<T> extends SubscriberCallBack<T> {
    private ApiCallback<T> apiCallback;
    public MyApiCallBack(ApiCallback<T> apiCallback) {
        super(apiCallback);
        this.apiCallback = apiCallback;
    }

    @Override
    public void onError(Throwable e) {
        try {
            String msg;
            if (e instanceof SocketTimeoutException) {
                msg = "自定义 连接服务器超时,请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof ConnectException) {
                msg = "自定义 网络中断，请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof TimeoutException) {
                msg = "自定义 连接超时，请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof JsonSyntaxException) {
                Log.e("MyApiCallBack", "JSON解析错误，请查看JSON结构", e);
                e.printStackTrace();
                apiCallback.onFailure(e.getMessage());
            } else {
                Log.e("MyApiCallBack", "自定义错误解析", e);
                apiCallback.onFailure(e.getMessage());
            }
        } catch (Exception e1) {
            apiCallback.onFailure(e1.getMessage());
            e1.printStackTrace();
        }

    }
}
