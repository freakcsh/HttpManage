package com.freak.httpmanage.property;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;

import static android.content.Context.POWER_SERVICE;

/**
 * 忽略电池优化，把当前应用加入电池白名单
 *
 * @author Freak
 * @date 2019/6/28.
 */

public class BatteryWhiteList {
    public static final int BATTERY_REQUEST = 1122;

    public static void ignoreBatteryOptimization(Activity activity) {

        PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            assert powerManager != null;
            if (!powerManager.isIgnoringBatteryOptimizations(activity.getPackageName())) {
                Intent intent = new
                        Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                //跳转之前判断intent是否存在，否则有的机型会报找不到activity
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivityForResult(intent, BATTERY_REQUEST);
                } else {
                    //TODO
                }
            } else {
                //TODO
            }
        } else {
            //TODO
        }
    }

    public static void showBatteryOptimization(Activity activity) {

        PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            assert powerManager != null;
//            if (!powerManager.isIgnoringBatteryOptimizations(activity.getPackageName())) {
//                Intent intent = new
//                        Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + activity.getPackageName()));
//                //跳转之前判断intent是否存在，否则有的机型会报找不到activity
//                if (intent.resolveActivity(activity.getPackageManager()) != null) {
//                    activity.startActivityForResult(intent, BATTERY_REQUEST);
//                } else {
//                    //TODO
//                }
//            } else {
//                //TODO
//            }
            Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            activity.startActivity(intent);
        } else {
            //TODO
        }
    }
}
