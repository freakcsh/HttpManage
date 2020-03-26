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
package com.freak.httpmanage.mvpdagger.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.R;
import com.freak.httpmanage.app.IActivityStatusBar;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.WINDOW_SERVICE;


/**
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    public static final int DESIGN_WIDTH = 375;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.i(activity + " - onActivityCreated");
        //限制竖屏
        //8.0.0版本系统，同时设置竖屏和设置全屏透明冲突，
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        resetDensity(activity.getApplicationContext(), DESIGN_WIDTH);
        resetDensity(activity, DESIGN_WIDTH);
        setImmersiveStatusBar(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Logger.i(activity + " - onActivityStarted");
        setToolBar(activity);
        resetDensity(activity.getApplicationContext(), DESIGN_WIDTH);
        resetDensity(activity, DESIGN_WIDTH);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Logger.i(activity + " - onActivityResumed");
        resetDensity(activity.getApplicationContext(), DESIGN_WIDTH);
        resetDensity(activity, DESIGN_WIDTH);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Logger.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Logger.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Logger.i(activity + " - onActivitySaveInstanceState");
        resetDensity(activity.getApplicationContext(), DESIGN_WIDTH);
        resetDensity(activity, DESIGN_WIDTH);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.i(activity + " - onActivityDestroyed");
    }


    /**
     * 设置ToolBar
     *
     * @param activity
     */
    private void setToolBar(final Activity activity) {
        if (activity.findViewById(R.id.tool_bar) != null && ((AppCompatActivity) activity).getSupportActionBar() == null) {
            Toolbar toolbar = activity.findViewById(R.id.tool_bar);
            if (!TextUtils.isEmpty(activity.getTitle())) {
                toolbar.setTitle(activity.getTitle());
            } else {
                toolbar.setTitle("");
            }

            if (((IActivityStatusBar) activity).getStatusBarColor() != 0) {
                toolbar.setBackgroundColor(((IActivityStatusBar) activity).getStatusBarColor());
            } else {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.color_white));
            }

            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
    }


    /**
     * 判断是否是亮色
     *
     * @param color
     * @return
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 设置状态栏
     *
     * @param activity
     */
    private void setImmersiveStatusBar(Activity activity) {
        if (activity instanceof IActivityStatusBar) {
            if (((IActivityStatusBar) activity).getStatusBarColor() != 0) {
                setTranslucentStatus(activity);
                addImmersiveStatusBar(activity, ((IActivityStatusBar) activity).getStatusBarColor());
            } else {
                if (((IActivityStatusBar) activity).getDrawableStatusBar() != 0) {
                    setTranslucentStatus(activity);
                    addImmersiveShadeStatusBar(activity, ((IActivityStatusBar) activity).getDrawableStatusBar());
                }
            }
        }
    }

    /**
     * 添加自定义状态栏
     *
     * @param activity
     */
    private void addImmersiveStatusBar(Activity activity, int color) {
        ViewGroup contentFrameLayout = activity.findViewById(android.R.id.content);
        View contentView = contentFrameLayout.getChildAt(0);
        if (contentView != null) {
            contentView.setFitsSystemWindows(true);
        }

        View statusBar = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasNotchInScreen(activity)) {
            params.height = getNotchSize(activity)[1];
        } else {
            params.height = getStatusBarHeight(activity);
        }
        params.height = getStatusBarHeight(activity);
        statusBar.setLayoutParams(params);
        statusBar.setBackgroundColor(color);
        if (isLightColor(color)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                statusBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                statusBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } else {
            statusBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        contentFrameLayout.addView(statusBar);
    }

    /**
     * 设置状态栏渐变色
     *
     * @param activity activity
     * @param drawable drawable资源文件
     */
    private void addImmersiveShadeStatusBar(Activity activity, @DrawableRes int drawable) {
        ViewGroup contentFrameLayout = activity.findViewById(android.R.id.content);
        View contentView = contentFrameLayout.getChildAt(0);
        if (contentView != null && Build.VERSION.SDK_INT >= 14) {
            contentView.setFitsSystemWindows(true);
        }

        View statusBar = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasNotchInScreen(activity)) {
            params.height = getNotchSize(activity)[1];
        } else {
            params.height = getStatusBarHeight(activity);
        }
//        params.height = getStatusBarHeight();
        statusBar.setLayoutParams(params);
        statusBar.setBackgroundResource(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            statusBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        contentFrameLayout.addView(statusBar);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 设置状态栏为透明
     *
     * @param activity
     */
    private void setTranslucentStatus(Activity activity) {
        //******** 5.0以上系统状态栏透明 ********

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 以pt为单位重新计算大小
     */
    public static void resetDensity(Context context, float designWidth) {
        if (context == null) {
            return;
        }
        Point size = new Point();
        ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        Resources resources = context.getResources();
        resources.getDisplayMetrics().xdpi = size.x / designWidth * 72f;
        DisplayMetrics metrics = getMetricsOnMIUI(context.getResources());
        if (metrics != null) {
            metrics.xdpi = size.x / designWidth * 72f;
        }
    }

    /**
     * 解决MIUI屏幕适配问题
     *
     * @param resources
     * @return
     */
    private static DisplayMetrics getMetricsOnMIUI(Resources resources) {
        if ("MiuiResources".equals(resources.getClass().getSimpleName()) || "XResources".equals(resources.getClass().getSimpleName())) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                return (DisplayMetrics) field.get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    /**
     * 判断是否是刘海屏  华为手机
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInScreen(Context context) {

        boolean ret = false;

        try {

            ClassLoader cl = context.getClassLoader();

            Class hwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");

            Method get = hwNotchSizeUtil.getMethod("hasNotchInScreen");

            ret = (boolean) get.invoke(hwNotchSizeUtil);

        } catch (ClassNotFoundException e) {

            Log.e("test", "hasNotchInScreen ClassNotFoundException");

        } catch (NoSuchMethodException e) {

            Log.e("test", "hasNotchInScreen NoSuchMethodException");

        } catch (Exception e) {

            Log.e("test", "hasNotchInScreen Exception");

        }
//        LogUtil.e("是否刘海屏-->" + ret);
        return ret;

    }

    /**
     * 获取刘海屏尺寸 华为手机
     *
     * @param context
     * @return
     */
    public static int[] getNotchSize(Context context) {

        int[] ret = new int[]{0, 0};

        try {

            ClassLoader cl = context.getClassLoader();

            Class hwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");

            Method get = hwNotchSizeUtil.getMethod("getNotchSize");

            ret = (int[]) get.invoke(hwNotchSizeUtil);

        } catch (ClassNotFoundException e) {

            LogUtil.e("getNotchSize ClassNotFoundException");

        } catch (NoSuchMethodException e) {

            LogUtil.e("getNotchSize NoSuchMethodException");

        } catch (Exception e) {

            LogUtil.e("getNotchSize Exception");

        }
        LogUtil.e("刘海屏尺寸-->" + ret.toString());
        return ret;

    }



}
