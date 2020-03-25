package com.freak.httpmanage.mvpdagger.frame.mvp.model.api.cache;

import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.net.response.HttpResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface DaggerCommonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<LoginEntity> login(Observable<HttpResult<LoginEntity>> commodityDetail, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
