package com.freak.httpmanage.mvpdagger.frame.mvp.presenter;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httphelper.dagger.scope.ActivityScope;
import com.freak.httphelper.mvp.BaseDaggerPresenter;
import com.freak.httphelper.mvp.DoFinallyListener;
import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.net.log.LogUtil;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class DaggerMvpDemoPresenter extends BaseDaggerPresenter<DaggerMvpDemoContract.Model, DaggerMvpDemoContract.View> {
    /**
     * 这里的构造函数不可缺少，可使用多参数构造函数 {@link BaseDaggerPresenter}
     *
     * @param model
     * @param rootView
     */
    @Inject
    public DaggerMvpDemoPresenter(DaggerMvpDemoContract.Model model, DaggerMvpDemoContract.View rootView) {
        super(model, rootView);
    }

    public void login(String account, String pwd, String app_type, int lastUserId, boolean isEvictCache) {
        Observable<LoginEntity> observable = mModel.login(account, pwd, app_type, lastUserId, isEvictCache);

        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity model) {
                LogUtil.d("请求数据  " + model);
                mRootView.loginSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e("错误信息  " + msg);
            }
        }), new DoFinallyListener() {
            @Override
            public void doFinally() {
                LogUtil.e("最终都会执行的方法，无论请求成功还是失败");
            }
        }, mRootView);
    }
}
