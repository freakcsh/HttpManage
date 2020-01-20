package com.freak.httpmanage.rxbus;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;

/**
 * Created by Administrator on 2019/4/19.
 */

public class RxBusContract {
   public   interface View extends BaseView {
        void rxTestSuccess();
    }

   public   interface Presenter extends BasePresenter<View> {
        void doTest();
    }
}
