package com.freak.httphelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author freak
 * @date 2019/01/25
 */
public class RxPresenter<T extends RxBaseView> implements BasePresenter<T> {
    protected T mView;

    /**
     * 解除订阅
     */
    protected void unSubscribe() {
        HttpMethods.getCompositeDisposableInstance().clear();
    }

    /**
     * RxJava绑定
     * 添加订阅
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void addSubscription(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
