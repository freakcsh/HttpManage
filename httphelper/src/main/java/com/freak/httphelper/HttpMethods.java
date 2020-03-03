package com.freak.httphelper;


import android.text.TextUtils;

import com.freak.httphelper.log.HttpLogger;
import com.freak.httphelper.log.LogUtil;
import com.freak.httphelper.ssl.HttpsUtils;
import com.freak.httphelper.ssl.SSLSocketFactoryUtil;
import com.freak.httphelper.ssl.TrustAllCerts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
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
    private static CompositeDisposable mCompositeDisposable;
    private Retrofit retrofit;
    private static HttpMethods mHttpMethods;
    private static Builder sBuilder;
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 10;

    public HttpMethods() {
        /**
         * 创建okHttpClient
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * 设置网络超时时间 时间按秒计算
         */
        builder.connectTimeout(getInstanceBuilder().getConnectTimeOut() == 0 ? CONNECT_TIMEOUT : getInstanceBuilder().getConnectTimeOut(), TimeUnit.SECONDS);

        builder.readTimeout(getInstanceBuilder().getReadTimeOut() == 0 ? READ_TIMEOUT : getInstanceBuilder().getReadTimeOut(), TimeUnit.SECONDS);

        builder.writeTimeout(getInstanceBuilder().getWriteTimeOut() == 0 ? WRITE_TIMEOUT : getInstanceBuilder().getWriteTimeOut(), TimeUnit.SECONDS);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        if (getInstanceBuilder().isUseDefaultSSLSocketFactory()){
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }else {
            if (getInstanceBuilder().getsSLSocketFactory() != null) {
                if (getInstanceBuilder().getTrustManager() != null) {
                    builder.sslSocketFactory(getInstanceBuilder().getsSLSocketFactory());
                }else {
                    builder.sslSocketFactory(getInstanceBuilder().getsSLSocketFactory(), getInstanceBuilder().getTrustManager());
                }
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }

        }

        if (getInstanceBuilder().getsInterceptorList() != null && getInstanceBuilder().getsInterceptorList().size() > 0) {
            for (Interceptor interceptor : getInstanceBuilder().getsInterceptorList()) {
                builder.addInterceptor(interceptor);
            }
        } else {
            if (getInstanceBuilder().getInterceptor() != null) {
                builder.addInterceptor(getInstanceBuilder().getInterceptor());
            }
        }
        if (getInstanceBuilder().getLogger() != null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(getInstanceBuilder().getLogger());
            //设置日志界级别
            httpLoggingInterceptor.setLevel(getInstanceBuilder().getLevel() == null ? HttpLoggingInterceptor.Level.BODY : getInstanceBuilder().getLevel());
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        } else {
            LogUtil.init(TextUtils.isEmpty(getInstanceBuilder().getLogName()) ? "HttpMethods" : getInstanceBuilder().getLogName(), getInstanceBuilder().isIsOpenLog());
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            //设置日志界级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }
        if (getInstanceBuilder().getCookieJar() != null) {
            builder.cookieJar(getInstanceBuilder().getCookieJar());
        }
        if (getInstanceBuilder().getNetworkInterceptor() != null) {
            builder.addNetworkInterceptor(getInstanceBuilder().getNetworkInterceptor());
        }
        /**
         * addConverterFactory 添加格式转换器工程  GsonConverterFactory
         * addCallAdapterFactory 添加调用适配器工程 RxJava2CallAdapterFactory
         * baseUrl 基础地址
         */
        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(getInstanceBuilder().getFactory() == null ? GsonConverterFactory.create() : getInstanceBuilder().getFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getInstanceBuilder().getBaseUrl())
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
     * 获取构建对象
     *
     * @return
     */
    public static Builder getInstanceBuilder() {
        if (sBuilder == null) {
            synchronized (Builder.class) {
                sBuilder = new Builder();
            }
        }
        return sBuilder;
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

    public static final class Builder {
        /**
         * 这是连接网络的时间
         */
        private int connectTimeOut = 0;
        private int readTimeOut = 0;
        private int writeTimeOut = 0;
        public String baseUrl;
        private Converter.Factory mFactory;
        private Interceptor mInterceptor;
        private Interceptor mNetworkInterceptor;
        private List<Interceptor> sInterceptorList;
        private HttpLoggingInterceptor.Logger mLogger;
        private CookieJar mCookieJar;
        private HttpLoggingInterceptor.Level mLevel;
        private int logLevel;
        private boolean isOpenLog;
        private String logName;

        private SSLSocketFactory sSLSocketFactory;
        private X509TrustManager trustManager;

        private boolean useDefaultSSLSocketFactory;

        public boolean isUseDefaultSSLSocketFactory() {
            return useDefaultSSLSocketFactory;
        }

        public Builder setUseDefaultSSLSocketFactory(boolean useDefaultSSLSocketFactory) {
            this.useDefaultSSLSocketFactory = useDefaultSSLSocketFactory;
            return this;
        }

        public SSLSocketFactory getsSLSocketFactory() {
            return sSLSocketFactory;
        }

        public Builder setsSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sSLSocketFactory = sSLSocketFactory;
            return this;
        }

        public X509TrustManager getTrustManager() {
            return trustManager;
        }

        public Builder setTrustManager(X509TrustManager trustManager) {
            this.trustManager = trustManager;
            return this;
        }

        public boolean isIsOpenLog() {
            return isOpenLog;
        }

        /**
         * 设置是否开启日志打印
         *
         * @return
         */
        public Builder setIsOpenLog(boolean isOpenLog) {
            this.isOpenLog = isOpenLog;
            return this;
        }

        public int getLogLevel() {
            return logLevel;
        }

        /**
         * 设置日志打印机别
         *
         * @param logLevel
         * @return
         */
        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public String getLogName() {
            return logName;
        }

        /**
         * 设置日志打印级别
         *
         * @param logName
         * @return
         */
        public Builder setLogName(String logName) {
            this.logName = logName;
            return this;
        }

        /**
         * 设置服务器域名
         *
         * @param url 服务器域名
         */
        public Builder setBaseUrl(String url) {
            this.baseUrl = url;
            return this;
        }


        /**
         * 设置解析库
         * 可自定义解析逻辑
         *
         * @param factory 解析库
         */
        public Builder setFactory(Converter.Factory factory) {
            mFactory = factory;
            return this;
        }

        /**
         * 设置拦截器
         *
         * @param interceptor
         */
        public Builder setInterceptor(Interceptor interceptor) {
            mInterceptor = interceptor;
            return this;
        }

        /**
         * 设置多个拦截器
         *
         * @param interceptors
         */
        public Builder setInterceptors(Interceptor... interceptors) {
            sInterceptorList = new ArrayList<>();
            for (Interceptor interceptor : interceptors) {
                sInterceptorList.add(interceptor);
            }
            return this;
        }

        /**
         * 设置自定义日志打印logger拦截器
         *
         * @param logger HttpLoggingInterceptor.Logger
         */
        public Builder setLogger(HttpLoggingInterceptor.Logger logger) {
            mLogger = logger;
            return this;
        }

        /**
         * 设置日志打印级别
         *
         * @param level
         */
        public Builder setLevel(HttpLoggingInterceptor.Level level) {
            mLevel = level;
            return this;
        }

        /**
         * 设置连接超时时间(秒)
         *
         * @param connectTimeOut 连接时间
         */
        public Builder setConnectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public int getConnectTimeOut() {
            return connectTimeOut;
        }

        public int getReadTimeOut() {
            return readTimeOut;
        }

        public int getWriteTimeOut() {
            return writeTimeOut;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public Converter.Factory getFactory() {
            return mFactory;
        }

        public Interceptor getInterceptor() {
            return mInterceptor;
        }

        public Interceptor getNetworkInterceptor() {
            return mNetworkInterceptor;
        }

        public List<Interceptor> getsInterceptorList() {
            return sInterceptorList;
        }

        public HttpLoggingInterceptor.Logger getLogger() {
            return mLogger;
        }

        public CookieJar getCookieJar() {
            return mCookieJar;
        }

        public HttpLoggingInterceptor.Level getLevel() {
            return mLevel;
        }


        /**
         * 设置读取超时时间(秒)
         *
         * @param readTimeOut 读取时间
         */
        public Builder setReadTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        /**
         * 设置写入超时时间(秒)
         *
         * @param writeTimeOut 写入时间
         */
        public Builder setWriteTimeOut(int writeTimeOut) {
            this.writeTimeOut = writeTimeOut;
            return this;
        }

        /**
         * 设置cookieJar
         *
         * @param mCookieJar
         */
        public Builder setCookieJar(CookieJar mCookieJar) {
            this.mCookieJar = mCookieJar;
            return this;
        }

        /**
         * 设置网络拦截器
         *
         * @param mNetworkInterceptor
         */
        public Builder setNetworkInterceptor(Interceptor mNetworkInterceptor) {
            this.mNetworkInterceptor = mNetworkInterceptor;
            return this;
        }
    }
}
