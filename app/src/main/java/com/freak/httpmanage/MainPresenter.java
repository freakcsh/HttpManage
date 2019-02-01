package com.freak.httpmanage;



import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.HttpResultFunc;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.bean.LoginBean;
import com.orhanobut.logger.Logger;

import rx.Observable;


public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);



    @Override
    public void doLogin(String userName,String pwd) {
        Observable observable = apiServer.login(userName,pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
                mView.onSuccess(model);
                mView.showResult(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                mView.showResult("错误信息或code--》"+msg);
            }
        }));

    }



}
