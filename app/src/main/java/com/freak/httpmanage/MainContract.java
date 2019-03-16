package com.freak.httpmanage;


import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.bean.LoginBean;

import com.freak.httpmanage.net.response.HttpResult;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView {
        void onSuccess(LoginBean loginBean);
        void onSuccess(HttpResult loginBean);
        void onError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin(String userName, String pwd);
        void doLogin2(String userName, String pwd);

    }
}
