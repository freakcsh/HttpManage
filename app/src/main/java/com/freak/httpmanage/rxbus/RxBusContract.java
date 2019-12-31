package com.freak.httpmanage.rxbus;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;

/**
 * Created by Administrator on 2019/4/19.
 */

public class RxBusContract {
     interface View extends BaseView {
        void rxTestSuccess();
    }

     interface Presenter extends BasePresenter<View> {
        void doTest();
    }
}
