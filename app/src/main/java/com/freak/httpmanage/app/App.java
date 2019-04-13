package com.freak.httpmanage.app;

import android.app.Activity;
import android.app.Application;

import com.freak.httphelper.HttpMethods;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptor;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptorHead;
import com.freak.httpmanage.net.interceptor.CookieJarImpl;
import com.freak.httpmanage.net.log.HttpLogger;
import com.freak.httpmanage.net.log.LogUtil;

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
        LogUtil.init("HttpHelper",true);
        HttpMethods.setBaseUrl(Constants.BASE_URL);
//        HttpMethods.setInterceptor(new CommonParametersInterceptor());
        HttpMethods.setInterceptors(new CommonParametersInterceptor(),new CommonParametersInterceptorHead());
        HttpMethods.setLevel(HttpMethods.BODY);
        HttpMethods.setLogger(new HttpLogger());
        HttpMethods.setCookieJar(new CookieJarImpl());
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
