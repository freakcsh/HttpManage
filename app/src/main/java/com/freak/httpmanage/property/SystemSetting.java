package com.freak.httpmanage.property;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.freak.httpmanage.net.log.LogUtil;

/**
 * 系统设置
 *
 * @author Freak
 * @date 2019/6/28.
 */

public class SystemSetting {
    /**
     * 9.0 com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity
     * 8.0 com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity
     * 7.0 6.0 com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity
     * 5.0权限管理 com.huawei.systemmanager/com.huawei.permissionmanager.ui.MainActivity
     * 5.0 开机自启动 com.huawei.systemmanager/.optimize.bootstart.BootStartActivity
     * <p>
     * 小米手机8.0 7.0 6.0 com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivityvivo
     * <p>
     * 8.1.0 7.0 6.0 com.vivo.permissionmanager/.activity.BgStartUpManagerActivity
     * 5.0 4.0com.iqoo.secure/.ui.phoneoptimize.SoftwareManagerActivity
     * <p>
     * 7.0 6.0 com.vivo.permissionmanager/.activity.PurviewTabActivityoppo
     * <p>
     * 权限设置界面8.0 7.0 com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivitycom.color.safecenter/.permission.PermissionTopActivity
     * 自启动管理界面8.1.0 com.coloros.safecenter/.startupapp.StartupAppListActivity自启动管理界面7.0 6.0 com.coloros.safecenter/.startupapp.StartupAppListActivity
     * 5.0 com.oppo.safe/.permission.startup.StartupAppListActivity4.4.4 R系列 com.color.safecenter/.permission.startup.StartupAppListActivity
     * 关联启动管理界面8.0 7.0 无6.0 com.coloros.safecenter/.startupapp.AssociateStartActivitysamsung
     * 8.0  7.1.1  com.samsung.android.sm_cn/com.samsung.android.sm.ui.ram.AutoRunActivity
     */
    public final static int HUAWEI_PHONEMODEL = 1;
    public final static int XIAOMI_PHONEMODEL = 2;
    public final static int VIVO_PHONEMODEL = 3;
    public final static int OPPO_PHONEMODEL = 4;
    public final static int SAMSUNG_PHONEMODEL = 5;
    public final static int DEFAULT_PHONEMODEL = 0;

    /**
     * 获取手机厂商设备名称
     *
     * @return
     */
    public static int getDeviceType() {
        int phoneModel;
        String deviceBrand = SystemUtil.getDeviceBrand();
        System.out.println(" deviceBrand : " + deviceBrand);
        if (!TextUtils.isEmpty(deviceBrand)) {
            if ("honor".equals(SystemUtil.getDeviceBrand().toLowerCase()) || "huawei".equals(SystemUtil.getDeviceBrand().toLowerCase())) {
                phoneModel = 1;
            } else if ("xiaomi".equals(SystemUtil.getDeviceBrand().toLowerCase())) {
                phoneModel = 2;
            } else if ("vivo".equals(SystemUtil.getDeviceBrand().toLowerCase())) {
                phoneModel = 3;
            } else if ("oppo".equals(SystemUtil.getDeviceBrand().toLowerCase())) {
                phoneModel = 4;
            } else if ("samsung".equals(SystemUtil.getDeviceBrand().toLowerCase())) {
                phoneModel = 5;
            } else {
                phoneModel = 0;
            }
        } else {
            phoneModel = 0;
        }
        return phoneModel;
    }

    /**
     * 点击设置自启动设置
     *
     * @param phoneModel 手机商标
     * @param context    context
     */
    public static void onViewClicked(int phoneModel, Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = null;
        switch (phoneModel) {
            case HUAWEI_PHONEMODEL:
                /**
                 * 华为 9.0 ACTIVITYcom.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity
                 * 8.0 com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity
                 * 7.0 6.0 com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity
                 * 5.0权限管理 com.huawei.systemmanager/com.huawei.permissionmanager.ui.MainActivity
                 * 5.0 开机自启动 com.huawei.systemmanager/.optimize.bootstart.BootStartActivity
                 */
                if (Build.VERSION.SDK_INT >= 28) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else if (Build.VERSION.SDK_INT >= 26) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
                } else if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.com.huawei.permissionmanager.ui.MainActivity");
                }
                break;
            case XIAOMI_PHONEMODEL:
                /**
                 * 8.0 7.0 6.0 com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity
                 */
                comp = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
            case VIVO_PHONEMODEL:
                /**
                 * 8.1.0 7.0 6.0 com.vivo.permissionmanager/.activity.BgStartUpManagerActivity
                 * 5.0 4.0com.iqoo.secure/.ui.phoneoptimize.SoftwareManagerActivity
                 * 7.0 6.0 com.vivo.permissionmanager/.activity.PurviewTabActivity
                 */
                if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
                } else {
                    comp = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity");
                }
                break;
            case OPPO_PHONEMODEL:
                /**
                 * 权限设置界面8.0 7.0 com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity
                 * com.color.safecenter/.permission.PermissionTopActivity
                 *
                 * 自启动管理界面8.1.0 com.coloros.safecenter/.startupapp.StartupAppListActivity
                 * 自启动管理界面7.0 6.0 com.coloros.safecenter/.startupapp.StartupAppListActivity
                 * 5.0 com.oppo.safe/.permission.startup.StartupAppListActivity
                 * 4.4.4 R系列 com.color.safecenter/.permission.startup.StartupAppListActivity
                 *
                 * 关联启动管理界面8.0 7.0 无6.0 com.coloros.safecenter/.startupapp.AssociateStartActivity
                 *
                 */
                if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                } else {
                    comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity");
                }
                break;
            case SAMSUNG_PHONEMODEL:
                /**
                 * 8.0 7.1.1 com.samsung.android.sm_cn/com.samsung.android.sm.ui.ram.AutoRunActivity
                 */
                comp = new ComponentName("com.samsung.android.sm_cn",
                        "com.samsung.android.sm_cn.com.samsung.android.sm.ui.ram.AutoRunActivity");
                break;
            case DEFAULT_PHONEMODEL:
                comp = null;
                break;
            default:
                break;
        }
        try {
            if (comp == null) {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } else {
                intent.setComponent(comp);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            //抛出异常就直接打开设置页面
            LogUtil.e("Exception " + e);
            Intent intentSetting = new Intent();
            intentSetting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentSetting.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intentSetting.setData(uri);
            context.startActivity(intentSetting);
        }
    }

}
