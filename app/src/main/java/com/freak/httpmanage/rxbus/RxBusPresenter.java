package com.freak.httpmanage.rxbus;

import com.freak.httphelper.RxPresenter;
import com.freak.httpmanage.net.log.LogUtil;

import javax.inject.Inject;


/**
 * Created by Administrator on 2019/4/19.
 */
public class RxBusPresenter extends RxPresenter<RxBusContract.View> implements RxBusContract.Presenter {

    @Inject
    public RxBusPresenter(RxBusContract.View view) {
        mView = view;
    }

    @Override
    public void doTest() {
//        mView.showResult("调用");
        LogUtil.e("调用Rx");
        mView.rxTestSuccess();
    }

}
