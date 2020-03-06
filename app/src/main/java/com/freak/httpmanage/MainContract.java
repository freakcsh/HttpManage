package com.freak.httpmanage;


import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.bean.BaseBean;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.bean.UpLoadEntity;
import com.freak.httpmanage.net.response.HttpResult;

import java.io.File;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView {
        void onSuccess(LoginBean loginBean);

        void onSuccess(HttpResult loginBean);

        void onSuccess(BaseBean loginBean);

        void onError(String msg);

        void upLoadSuccess(UpLoadEntity upLoadEntity);
        void onProgress(int progress);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin1(String userName, String pwd);

        void doLogin2(String userName, String pwd);

        void loadLoginStatusEntity();

        void doLogin(String phone, String password);

        void uploading(String tip, String tip1, String path);

        void uploading1(String tip, String tip1, String path);

        void uploading2(String path);

        void doLogin3(String userName, String pwd);

        void login11(String account, String pwd, String app_type);

        void upLoadVideo(File file);
    }
}
