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
package com.freak.httpmanage.mvpdagger.config;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.freak.httphelper.dagger.module.GlobalConfigModule;
import com.freak.httphelper.delegate.AppLifecycle;
import com.freak.httphelper.lifecycle.ConfigModule;
import com.freak.httphelper.log.HttpLogger;
import com.freak.httphelper.ssl.HttpsUtils;
import com.freak.httpmanage.app.Constants;
import com.freak.httpmanage.mvpdagger.base.ActivityLifecycleCallbacksImpl;
import com.freak.httpmanage.mvpdagger.base.AppLifecycleImpl;
import com.freak.httpmanage.mvpdagger.base.FragmentLifecycleCallbacksImpl;
import com.freak.httpmanage.net.factory.CustomConverterFactory;
import com.freak.httpmanage.net.interceptor.CookieJarImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 *  <meta-data
 *             android:name="com.freak.httpmanage.mvpdagger.config.GlobalConfiguration"
 *             android:value="ConfigModule"/>
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 *
 *
 */
public final class GlobalConfiguration  implements ConfigModule{


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {


        builder.baseUrl(Constants.BASE_URL)
                //可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性
//                .cacheFactory(new Cache.Factory() {
//                    @NonNull
//                    @Override
//                    public Cache build(CacheType type) {
//                        switch (type.getCacheTypeId()){
//                            case CacheType.EXTRAS_TYPE_ID:
//                                return new IntelligentCache(500);
//                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
//                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
//                            default:
//                                return new LruCache(200);
//                        }
//                    }
//                })

                //可以自定义一个单例的线程池供全局使用
//                .executorService(Executors.newCachedThreadPool())

                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置 Gson 的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化值为 null 的参数
                            .enableComplexMapKeySerialization();//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置 Retrofit 的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
                    retrofitBuilder.addConverterFactory(CustomConverterFactory.create());//自定义解析器
                })
                .okHttpConfiguration((context1, okHttpBuilder) -> {//这里可以自己自定义配置 Okhttp 的参数
                    okHttpBuilder.connectTimeout(30,TimeUnit.SECONDS);
                    okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
                    okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);
                    //支持 Https
                    HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                    okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                    okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
                    //设置日志界级别
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    okHttpBuilder.addNetworkInterceptor(httpLoggingInterceptor);//设置NetworkInterceptor
//                    okHttpBuilder.addInterceptor();//设置拦截器
                    okHttpBuilder.cookieJar(new CookieJarImpl());//同步cookie，框架提供默认的，也可自己实现自定义
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置 RxCache 的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    //想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
                    //否则请 return null;
                    return null;
                });
    }


    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycle> lifecycle) {
        //AppLifecycle 中的所有方法都会在基类 Application 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycle.add(new AppLifecycleImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //ActivityLifecycleCallbacks 中的所有方法都会在 Activity (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }


    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //FragmentLifecycleCallbacks 中的所有方法都会在 Fragment (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
