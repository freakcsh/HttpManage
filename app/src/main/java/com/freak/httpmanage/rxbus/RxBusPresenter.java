package com.freak.httpmanage.rxbus;

import com.freak.httphelper.RxPresenter;

/**
 * Created by Administrator on 2019/4/19.
 */

public class RxBusPresenter extends RxPresenter<RxBusContract.View> implements RxBusContract.Presenter {
    @Override
    public void doTest() {
        mView.showResult("调用");
    }
}
