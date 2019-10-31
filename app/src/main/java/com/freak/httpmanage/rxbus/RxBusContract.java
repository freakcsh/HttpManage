package com.freak.httpmanage.rxbus;

import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.test.TestContract;
import com.freak.httpmanage.test.TestContract1;

/**
 * Created by Administrator on 2019/4/19.
 */

public class RxBusContract {
    interface View extends TestContract.View {
        void rxTestSuccess();
    }

    interface Presenter<T extends BaseView> extends TestContract.Presenter<View> {
        void doTest();
    }
}
