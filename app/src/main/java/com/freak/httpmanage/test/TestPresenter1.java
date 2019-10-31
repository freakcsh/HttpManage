package com.freak.httpmanage.test;

import com.freak.httphelper.RxPresenter;
import com.freak.httpmanage.app.BaseView;

/**
 * Created by Freak on 2019/10/31.
 */
public class TestPresenter1<T extends BaseView> extends TestPresenter<TestContract1.View> implements TestContract1.Presenter {
    @Override
    public void test() {

    }
}
