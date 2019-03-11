package com.freak.httphelper;



import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    private static Converter.Factory mFactory;
    private static Interceptor mInterceptor;
    private static HttpLoggingInterceptor.Logger mLogger;
    /**
     * No logs.
     */
    public final static HttpLoggingInterceptor.Level NONE = HttpLoggingInterceptor.Level.NONE;
    /**
     * Logs request and response lines.
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1 (3-byte body)
     *
     * <-- 200 OK (22ms, 6-byte body)
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level BASIC = HttpLoggingInterceptor.Level.BASIC;
    /**
     * Logs request and response lines and their respective headers.
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level HEADERS = HttpLoggingInterceptor.Level.HEADERS;
    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     *
     * Hi?
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     *
     * Hello!
     * <-- END HTTP
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level BODY = HttpLoggingInterceptor.Level.BODY;

    private static HttpLoggingInterceptor.Level Level;
    private Retrofit retrofit;
    private static HttpMethods mHttpMethods;

    private static CompositeDisposable mCompositeDisposable;


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
     * 可自定义解析逻辑
     *
     * @param factory 解析库
     */
    public static void setFactory(Converter.Factory factory) {
        mFactory = factory;
    }

    /**
     * 设置拦截器
     *
     * @param interceptor
     */
    public static void setInterceptor(Interceptor interceptor) {
        mInterceptor = interceptor;
    }

    /**
     * 设置日志打印logger拦截器
     *
     * @param logger HttpLoggingInterceptor.Logger
     */
    public static void setLogger(HttpLoggingInterceptor.Logger logger) {
        mLogger = logger;
    }

    /**
     * 设置日志打印级别
     *
     * @param level
     */
    public static void setLevel(HttpLoggingInterceptor.Level level) {
        Level = level;
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
        if (mInterceptor != null) {
            builder.addInterceptor(mInterceptor);
        }
        if (mLogger != null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(mLogger);
            //设置日志界级别
            httpLoggingInterceptor.setLevel(Level == null ? HttpLoggingInterceptor.Level.BODY : Level);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }

        /**
         * addConverterFactory 添加格式转换器工程  GsonConverterFactory
         * addCallAdapterFactory 添加调用适配器工程 RxJava2CallAdapterFactory
         * baseUrl 基础地址
         */
        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(mFactory == null ? GsonConverterFactory.create() : mFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    /**
     * 获取单例CompositeDisposable
     *
     * @return
     */
    public static CompositeDisposable getCompositeDisposableInstance() {
        if (mCompositeDisposable == null) {
            synchronized (CompositeDisposable.class) {
                mCompositeDisposable = new CompositeDisposable();
            }
        }
        return mCompositeDisposable;
    }

    public <T> T create(Class<T> service) {
        return this.retrofit.create(service);
    }

}
