package com.freak.httpmanage.rxbus;

import com.freak.httphelper.RxBaseView;
import com.freak.httphelper.RxPresenter;

import javax.inject.Inject;

import dagger.Module;


/**
 * Created by Administrator on 2019/4/19.
 */
//@Module
public class RxBusPresenter extends RxPresenter<RxBusContract.View> implements RxBusContract.Presenter{
    @Inject
    public RxBusPresenter() {
    }

    @Override
    public void doTest() {
        mView.showResult("调用");
//        mView.rxTestSuccess();

    }

}
