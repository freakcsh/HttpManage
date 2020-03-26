/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.freak.httphelper.lifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.freak.httphelper.log.LogUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用于管理所有 {@link Activity}, 和在前台的 {@link Activity}
 */
public final class AppManager {
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 存放activity的列表
     */
    public HashMap<Class<?>, Activity> allActivities;

    private static volatile AppManager sAppManager;

    private Application mApplication;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (sAppManager == null) {
            synchronized (AppManager.class) {
                if (sAppManager == null) {
                    sAppManager = new AppManager();
                }
            }
        }
        return sAppManager;
    }

    /**
     * 添加activity
     *
     * @param activity
     * @param tClass
     */
    public void addActivity(AppCompatActivity activity, Class<?> tClass) {
        if (allActivities == null) {
            allActivities = new LinkedHashMap<>();
        }
        if (!allActivities.containsValue(activity)) {
            allActivities.put(tClass, activity);
        }
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public void removeActivity(AppCompatActivity activity) {
        if (allActivities != null) {
            if (allActivities.containsValue(activity)) {
                allActivities.remove(activity.getClass());
            }
        }
    }

    /**
     * 移除所有activity并结束程序
     */
    public void finishActivity() {
        if (allActivities != null && allActivities.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = allActivities.entrySet();
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            allActivities.clear();
        }
        appExit();
    }


    /**
     * 移除所有的Activity
     */
    public void removeAllActivity() {
        if (allActivities != null && allActivities.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = allActivities.entrySet();
            LogUtil.d("activity堆栈  removeAllActivity  " + allActivities);
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            allActivities.clear();
        }
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public <T extends AppCompatActivity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        AppCompatActivity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public <T extends AppCompatActivity> T getActivity(Class<T> clazz) {
        return (T) allActivities.get(clazz);
    }


    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
