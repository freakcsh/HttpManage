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

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.cache.IntelligentCache;
import com.freak.httphelper.delegate.AppLifecycle;
import com.freak.httphelper.log.LogLevel;
import com.freak.httphelper.utils.ComponentUtil;
import com.freak.httpmanage.BuildConfig;
import com.freak.httpmanage.app.Constants;
import com.freak.httpmanage.net.factory.CustomConverterFactory;
import com.freak.httpmanage.net.interceptor.CommonParametersInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 展示 {@link AppLifecycle} 的用法
 */
public class AppLifecycleImpl implements AppLifecycle {
    @Override
    public void attachBaseContext(@NonNull Context base) {
          MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        HttpMethods
                .getInstanceBuilder()
                .setBaseUrl(Constants.BASE_URL)//设置域名
                .setLogLevel(LogLevel.ERROR)//设置日志打印级别，使用默认的日志打印才需要设置这个
                .setLogName("HttpManager")//设置默认日志打印名字
                .setIsOpenLog(true)//设置是否开启框架默认的日志打印
                .setUseDefaultSSLSocketFactory(true)
//                .setCookieJar(new CookieJarImpl())//设置自定义的cookiejar
//                .setLogger(new HttpLogger())//设置自定义logger，此设置是打印网络请求的数据（如果设置了自定义的，则框架默认的则不需要设置）
//                .setLevel(LoggerLevel.BODY)//设置日志打印级别（自定义logger可设置，框架默认的是BODY级别，如果上架需要关闭日志打印，则设置setIsOpenLog(false)即可）
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
//                .setInterceptor(new CommonParametersInterceptor())//设置拦截器
//                .setNetworkInterceptor(new CommonParametersInterceptor())//设置拦截器
                .setFactory(CustomConverterFactory.create())//设置自定义解析器
                .setInterceptors(new CommonParametersInterceptor());//设置多个拦截器

//        //LeakCanary 内存泄露检查
//        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
//        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        ComponentUtil.obtainAppComponentFromContext(application).extras()
                .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                        , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
