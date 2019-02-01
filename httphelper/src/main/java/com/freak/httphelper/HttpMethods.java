package com.freak.httphelper;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * okHttp请求类封装
 *
 * @author freak
 * @date 2019/01/25
 */
public class HttpMethods {
    /**
     * 这是连接网络的时间
     */
    private static int connectTimeOut = 0;
    private static int readTimeOut = 0;
    private static int writeTimeOut = 0;
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 10;

    private static String BaseUrl;
    public static Converter.Factory mFactory;

    private Retrofit retrofit;
    private static HttpMethods mHttpMethods;

    /**
     * 设置服务器域名
     *
     * @param url 服务器域名
     */
    public static void setBaseUrl(String url) {
        BaseUrl = url;
    }

    /**
     * 设置解析库
     *
     * @param factory 解析库
     */
    public static void setFactory(Converter.Factory factory) {
        mFactory = factory;
    }

    /**
     * 设置连接超时时间(秒)
     *
     * @param connectTimeOut 连接时间
     */
    public static void setConnectTimeOut(int connectTimeOut) {
        HttpMethods.connectTimeOut = connectTimeOut;
    }

    /**
     * 设置读取超时时间(秒)
     *
     * @param readTimeOut 读取时间
     */
    public static void setReadTimeOut(int readTimeOut) {
        HttpMethods.readTimeOut = readTimeOut;
    }

    /**
     * 设置写入超时时间(秒)
     *
     * @param writeTimeOut 写入时间
     */
    public static void setWriteTimeOut(int writeTimeOut) {
        HttpMethods.writeTimeOut = writeTimeOut;
    }

    public HttpMethods() {
        /**
         * 创建okHttpClient
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * 设置网络超时时间 时间按秒计算
         */
        builder.connectTimeout(connectTimeOut == 0 ? CONNECT_TIMEOUT : connectTimeOut, TimeUnit.SECONDS);

        builder.readTimeout(readTimeOut == 0 ? READ_TIMEOUT : readTimeOut, TimeUnit.SECONDS);

        builder.writeTimeout(writeTimeOut == 0 ? WRITE_TIMEOUT : writeTimeOut, TimeUnit.SECONDS);
        /**
         * addConverterFactory 添加格式转换器工程  GsonConverterFactory
         * addCallAdapterFactory 添加调用适配器工程 RxJavaCallAdapterFactory
         * baseUrl 基础地址
         */
        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(mFactory == null ? GsonConverterFactory.create() : mFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .build();
    }

    /**
     * 在访问HttpMethods时创建单例
     *
     * @return 返回HttpMethods对象
     */
    public static HttpMethods getInstance() {
        if (mHttpMethods == null) {
            synchronized (HttpMethods.class) {
                mHttpMethods = new HttpMethods();
            }
        }
        return mHttpMethods;
    }

    public <T> T create(Class<T> service) {
        return this.retrofit.create(service);
    }

}
