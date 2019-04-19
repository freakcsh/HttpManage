package com.freak.httpmanage.app;

import android.app.Activity;
import android.app.Application;

import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.log.LogLevel;
import com.freak.httphelper.log.LoggerLevel;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptor;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptorHead;
import com.freak.httpmanage.net.interceptor.CookieJarImpl;

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
        instance = this;
//        HttpMethods.setConnectTimeOut(6);
//        HttpMethods.setReadTimeOut(10);
//        HttpMethods.setWriteTimeOut(10);
//        LogUtil.init("HttpHelper",true);
        HttpMethods
                .getInstanceBuilder()
                .setBaseUrl(Constants.BASE_URL)
                .setLogLevel(LogLevel.ERROR)
                .setCookieJar(new CookieJarImpl())
//                .setLogger(new HttpLogger())
                .setLogName("123456")
                .setIsOpenLog(true)
                .setLevel(LoggerLevel.BODY)
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
                .setInterceptors(new CommonParametersInterceptor(), new CommonParametersInterceptorHead());
//        HttpMethods.setBaseUrl(Constants.BASE_URL);
////        HttpMethods.setInterceptor(new CommonParametersInterceptor());
//        HttpMethods.getInstance().setInterceptors(new CommonParametersInterceptor(), new CommonParametersInterceptorHead()).setIsOpenLog(true);
////        HttpMethods.getInstance().setLevel(HttpMethods.BODY);
////        HttpMethods.getInstance().setLogger(new HttpLogger());
//        HttpMethods.getInstance().setCookieJar(new CookieJarImpl());

    }

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
