package com.freak.httpmanage;


import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.net.BaseBean;
import com.freak.httpmanage.net.HttpResult;
import com.freak.httpmanage.net.HttpResultFunc;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;


public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);

    @Override
    public void doLogin(String userName, String pwd) {
        Observable observable = apiServer.login(userName, pwd);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean model) {
                Logger.d(model);
            }

            @Override
            public void onFailure(String msg) {
                Logger.d(msg);
            }
        }));
    }

    @Override
    public void doLogin2(String userName, String pwd) {
        Observable<LoginBean> observable = apiServer.login2(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
            }

            @Override
            public void onFailure(String msg) {
                Logger.d(msg);
            }
        }));
    }


}
