package com.freak.httpmanage.mvpdagger.frame.mvp.contract;

import com.freak.httphelper.mvp.IDaggerModel;
import com.freak.httphelper.mvp.IDaggerView;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.net.response.HttpResult;

import io.reactivex.Observable;

public interface DaggerMvpDemoContract {
    //这里可以定义经常使用的方法
    interface View extends IDaggerView {
        void loginSuccess(DaggerLoginEntity loginEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IDaggerModel {
        Observable<HttpResult<DaggerLoginEntity>> login(String account, String pwd, String app_type, int lastUserId, boolean isEvictCache);
    }

}
