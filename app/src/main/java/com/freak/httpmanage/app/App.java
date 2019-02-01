package com.freak.httpmanage.app;

import android.app.Activity;
import android.app.Application;


import com.freak.httphelper.HttpMethods;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2018/2/4.
 * 配置全局变量
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;
//    public static BaseActivity baseActivity;


    public Set<Activity> getAllActivities() {
        return allActivities;
    }

    public void setAllActivities(Set<Activity> allActivities) {
        this.allActivities = allActivities;
    }

    public static App getInstance() {
        return instance;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
//        HttpMethods.setConnectTimeOut(6);
//        HttpMethods.setReadTimeOut(10);
//        HttpMethods.setWriteTimeOut(10);
        FormatStrategy mFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // （可选）是否显示线程信息。默认值true
//                .methodCount(5)         // （可选）要显示的方法行数。默认值2
//                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。默认值5
//                .logStrategy() // （可选）更改要打印的日志策略。默认LogCat
                .tag("HttpHelper")   // （可选）每个日志的全局标记。默认PRETTY_LOGGER .build
                .build();
        //log日志打印框架Logger
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(mFormatStrategy));
        HttpMethods.setBaseUrl(Constants.BASE_URL);
    }

    //##################################### 以下是activity的收litepal.xml集 ####################################
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }
}
