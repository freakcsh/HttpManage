package com.freak.httpmanage.test;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxBaseView;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.R;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.net.response.HttpResult;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;

/**
 * Created by Freak on 2019/10/31.
 */
public class TestPresenter<T extends BaseView> extends RxPresenter<TestContract.View> implements TestContract.Presenter{
    private ApiServer mApiServer = HttpMethods.getInstance().create(ApiServer.class);

    @Override
    public void doLogin1(String userName, String pwd) {
        Observable observable = mApiServer.login1(userName, pwd);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                mView.onSuccess(model);
                Logger.d(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }
}
