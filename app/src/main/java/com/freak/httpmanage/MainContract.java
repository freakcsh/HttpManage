package com.freak.httpmanage;


import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.bean.LoginBean;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView {
        void onSuccess(LoginBean loginBean);
        void onError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin(String userName, String pwd);

    }
}
