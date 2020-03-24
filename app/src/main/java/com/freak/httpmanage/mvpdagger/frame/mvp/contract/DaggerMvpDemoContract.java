package com.freak.httpmanage.mvpdagger.frame.mvp.contract;

import com.freak.httphelper.mvp.IDaggerModel;
import com.freak.httphelper.mvp.IDaggerView;

public interface DaggerMvpDemoContract {
    //这里可以定义经常使用的方法
    interface View extends IDaggerView{

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IDaggerModel{

    }

}
