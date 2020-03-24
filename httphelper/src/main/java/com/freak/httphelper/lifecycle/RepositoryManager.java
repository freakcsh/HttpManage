package com.freak.httphelper.lifecycle;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freak.httphelper.cache.Cache;
import com.freak.httphelper.cache.CacheType;
import com.freak.httphelper.rxview.Preconditions;

import java.lang.reflect.Proxy;

import javax.inject.Inject;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

public class RepositoryManager implements IRepositoryManager{

    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Lazy<RxCache> mRxCache;
    @Inject
    Application mApplication;
    @Inject
    Cache.Factory mCacheFactory;
    @Inject
    @Nullable
    ObtainServiceDelegate mObtainServiceDelegate;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;

    @Inject
    public RepositoryManager() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            if (mObtainServiceDelegate != null) {
                retrofitService = mObtainServiceDelegate.createRetrofitService(
                        mRetrofit.get(), serviceClass);
            }
            if (retrofitService == null) {
                retrofitService = (T) Proxy.newProxyInstance(
                        serviceClass.getClassLoader(),
                        new Class[]{serviceClass},
                        new RetrofitServiceProxyHandler(mRetrofit.get(), serviceClass));
            }
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cacheClass Cache class
     * @param <T>        Cache class
     * @return Cache
     */
    @NonNull
    @Override
    public synchronized <T> T obtainCacheService(@NonNull Class<T> cacheClass) {
        Preconditions.checkNotNull(cacheClass, "cacheClass == null");
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mCacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.get().using(cacheClass);
            mCacheServiceCache.put(cacheClass.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll().subscribe();
    }

    @NonNull
    @Override
    public Context getContext() {
        return mApplication;
    }
}
