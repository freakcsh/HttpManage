package com.freak.httphelper;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author freak
 * @date 2019/01/25
 */
public class RxPresenter<T extends RxBaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 解除订阅
     */
    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 添加订阅
     */
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * RxJava绑定
     * 添加订阅
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public <T> void addSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 绑定view
     *
     * @param view
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * 分离view
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
