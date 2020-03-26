package com.freak.httphelper.mvp;


import android.app.Activity;
import android.app.Service;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.freak.httphelper.RxApiManager;
import com.freak.httphelper.lifecycle.RxLifecycleUtils;
import com.freak.httphelper.log.LogUtil;
import com.freak.httphelper.rxview.Preconditions;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BaseDaggerPresenter<M extends IDaggerModel, V extends IDaggerView> implements IDaggerPresenter, LifecycleObserver {
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected V mRootView;

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    public BaseDaggerPresenter(M model, V rootView) {
        Preconditions.checkNotNull(model, "%s cannot be null", IDaggerModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IDaggerView.class.getName());
        this.mModel = model;
        this.mRootView = rootView;
        LogUtil.e("开始创建onCreate");
        onCreate();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BaseDaggerPresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IDaggerView.class.getName());
        this.mRootView = rootView;
        onCreate();
    }

    public BaseDaggerPresenter() {
        onCreate();
    }

    @Override
    public void onCreate() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mRootView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }

    }


    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IDaggerPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        /**
         * 此方式会存在一个问题（出现场景不常见），在一个activity A有fragment多层嵌套的情况下
         * （特别是tabLayout+viewPager的方式，并且viewPager的fragment需要根据后台返回数据的情况去创建的时候），
         * 从A到B，然后在B返回A的时候，需要刷新所有的fragment，A已经是onResume状态，但是数据还没有返回，此时B执行了onDestroy，但是此时fragment的子fragment还没有完成创建完，
         * 此时执行{@link BaseDaggerPresenter#unDispose()}
         * 会解除所有订阅，如果A的fragment存在订阅关系，则也会取消，导致okHttp请求取消
         * 所以使用{@link RxApiManager}去管理订阅关系
         */
//        unDispose();//解除订阅
        if (mRootView != null) {
            LogUtil.e("取消");
            RxApiManager.getInstance().cancel(mRootView.getClass().getSimpleName());
        }
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 只有当 {@code mRootView} 不为 null, 并且 {@code mRootView} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     * 所以当您想在 {@link Service} 以及一些自定义 {@link View} 或自定义类中使用 {@code Presenter} 时
     * 您也将不能继续使用 {@link OnLifecycleEvent} 绑定生命周期
     *
     * @param owner link {@link Activity} and {@link Fragment}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        /**
         * 注意, 如果在这里调用了 {@link #onDestroy()} 方法, 会出现某些地方引用 {@code mModel} 或 {@code mRootView} 为 null 的情况
         * 比如在 {@link RxLifecycle} 终止 {@link Observable} 时, 在 {@link io.reactivex.Observable#doFinally(Action)} 中却引用了 {@code mRootView} 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 {@code mModel} 或 {@code mRootView} 也可能会出现此情况
         */
        owner.getLifecycle().removeObserver(this);
    }


    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前框架已使用 {@link RxLifecycle} 避免内存泄漏,此方法作为备用方案
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    public <T> void addSubscription(@NonNull Observable<T> observable, @NonNull DisposableObserver<T> observer, @Nullable DoFinallyListener doFinallyListener, @Nullable IDaggerView mRootView) {
        Preconditions.checkNotNull(observable, "%s cannot be null", observable.getClass().getName());
        Preconditions.checkNotNull(observer, "%s cannot be null", observer.getClass().getName());
        Preconditions.checkNotNull(mRootView, "%s cannot be null", mRootView.getClass().getName());
        observable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))////遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔，单位为s
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (doFinallyListener != null) {
                            doFinallyListener.doFinally();
                        }
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(observer);
        RxApiManager.getInstance().add(mRootView.getClass().getSimpleName(), observer);
    }

}
