package com.freak.httpmanage.mvpdagger.frame.mvp.model.api.cache;

import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.net.response.HttpResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

public interface DaggerCommonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HttpResult<DaggerLoginEntity>>> login(Observable<HttpResult<DaggerLoginEntity>> commodityDetail, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
