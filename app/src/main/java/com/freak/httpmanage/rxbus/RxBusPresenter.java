package com.freak.httpmanage.rxbus;

import com.freak.httphelper.RxBaseView;
import com.freak.httphelper.RxPresenter;
import com.freak.httpmanage.test.TestPresenter;
import com.freak.httpmanage.test.TestPresenter1;


/**
 * Created by Administrator on 2019/4/19.
 */

public class RxBusPresenter extends TestPresenter<RxBusContract.View> implements RxBusContract.Presenter {
    @Override
    public void doTest() {
        mView.showResult("调用");

    }

}
