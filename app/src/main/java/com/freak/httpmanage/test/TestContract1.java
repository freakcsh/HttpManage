package com.freak.httpmanage.test;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;

/**
 * Created by Freak on 2019/10/31.
 */
public interface TestContract1 {
    interface View extends TestContract.View {
        void testSuccess();
    }

    interface Presenter<T extends BaseView> extends TestContract.Presenter {
        void test();
    }
}
