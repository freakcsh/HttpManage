package com.freak.httpmanage.mvpdagger.frame.mvp.presenter;

import android.util.Log;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.dagger.scope.ActivityScope;
import com.freak.httphelper.mvp.BaseDaggerPresenter;
import com.freak.httphelper.mvp.DoFinallyListener;
import com.freak.httpmanage.mvpdagger.callback.MyApiCallBack;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.net.log.LogUtil;
import com.freak.httpmanage.net.response.HttpResultFunc;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class DaggerMvpDemoPresenter extends BaseDaggerPresenter<DaggerMvpDemoContract.Model, DaggerMvpDemoContract.View> {
  private final static String TAG =DaggerMvpDemoPresenter.class.getSimpleName();
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
        Log.e(TAG,"开始请求");
        Observable<DaggerLoginEntity> observable = mModel.login(account, pwd, app_type, lastUserId, isEvictCache).map(new HttpResultFunc<>());

//        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<DaggerLoginEntity>() {
//            @Override
//            public void onSuccess(DaggerLoginEntity model) {
//                LogUtil.d("请求数据  " + model);
//                Log.e(TAG,"请求数据  "+model.toString());
//                mRootView.loginSuccess(model);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                LogUtil.e("错误信息  " + msg);
//            }
//        }), new DoFinallyListener() {
//            @Override
//            public void doFinally() {
//                LogUtil.e("最终都会执行的方法，无论请求成功还是失败");
//            }
//        }, mRootView);

        //自定义错误回调提示
        addSubscription(observable, new MyApiCallBack<>(new ApiCallback<DaggerLoginEntity>() {
            @Override
            public void onSuccess(DaggerLoginEntity model) {
                LogUtil.d("请求数据  " + model);
                Log.e(TAG,"请求数据  "+model.toString());
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
