package com.freak.httpmanage.app;

import android.app.Activity;
import android.app.Application;

import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.log.LogLevel;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptor;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptorHead;
import com.freak.httpmanage.net.interceptor.CookieJarImpl;
import com.freak.httpmanage.util.ImagePickerGlideLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

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
        initImagePicker();
        HttpMethods
                .getInstanceBuilder()
                .setBaseUrl(Constants.BASE_URL)//设置域名
                .setLogLevel(LogLevel.ERROR)//设置日志打印级别，使用默认的日志打印才需要设置这个
                .setLogName("123456")//设置默认日志打印名字
                .setIsOpenLog(true)//设置是否开启框架默认的日志打印
                .setCookieJar(new CookieJarImpl())//设置自定义的cookiejar
//                .setLogger(new HttpLogger())//设置自定义logger，此设置是打印网络请求的数据（如果设置了自定义的，则框架默认的则不需要设置）
//                .setLevel(LoggerLevel.BODY)//设置日志打印级别（自定义logger可设置，框架默认的是BODY级别，如果上架需要关闭日志打印，则设置setIsOpenLog(false)即可）
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
//                .setInterceptor(new CommonParametersInterceptor())//设置拦截器
//                .setNetworkInterceptor(new CommonParametersInterceptor())//设置拦截器
//                .setFactory(CustomConverterFactory.create())//设置自定义解析器
                .setInterceptors(new CommonParametersInterceptor(), new CommonParametersInterceptorHead());//设置多个拦截器
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
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
//        //设置图片加载器
        imagePicker.setImageLoader(new ImagePickerGlideLoader());
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        //允许裁剪（单选才有效）
        imagePicker.setCrop(true);
        //是否按矩形区域保存
        imagePicker.setSaveRectangle(true);
        //选中数量限制
        imagePicker.setSelectLimit(9);
        //裁剪框的形状
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusWidth(800);
        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);
        //保存文件的宽度。单位像素
        imagePicker.setOutPutX(1000);
        //保存文件的高度。单位像素
        imagePicker.setOutPutY(1000);
    }
}
