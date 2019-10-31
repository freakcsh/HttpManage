package com.freak.httpmanage.test;

import com.freak.httphelper.BasePresenter;
import com.freak.httphelper.RxBaseView;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.net.response.HttpResult;

/**
 * Created by Freak on 2019/10/31.
 */
public interface TestContract {
    interface View extends BaseView {
        void onSuccess(HttpResult loginBean);

        void onError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin1(String userName, String pwd);
    }
}
