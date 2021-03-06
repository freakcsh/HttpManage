package com.freak.httpmanage.mvpdagger.frame.mvp.model;

import com.freak.httphelper.dagger.scope.ActivityScope;
import com.freak.httphelper.lifecycle.IRepositoryManager;
import com.freak.httphelper.mvp.BaseDaggerModel;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.api.cache.DaggerCommonCache;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.api.service.DaggerApiService;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.net.response.HttpResult;
import com.freak.httpmanage.util.RequestUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import okhttp3.RequestBody;
@ActivityScope
public class DaggerMvpDemoModel extends BaseDaggerModel implements DaggerMvpDemoContract.Model {
    @Inject
    public DaggerMvpDemoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<HttpResult<DaggerLoginEntity>> login(String account, String pwd, String app_type, int lastUserId, boolean isEvictCache) {
        RequestBody requestBody = RequestUtils.newParams()
                .addParams("account", account)
                .addParams("pwd", pwd)
                .addParams("app_type", app_type)
                .createRequestBody();
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(DaggerApiService.class)
                .login(requestBody))
                .flatMap(new Function<Observable<HttpResult<DaggerLoginEntity>>, ObservableSource<HttpResult<DaggerLoginEntity>>>() {
                    @Override
                    public ObservableSource<HttpResult<DaggerLoginEntity>> apply(Observable<HttpResult<DaggerLoginEntity>> httpResultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(DaggerCommonCache.class)
                                .login(httpResultObservable
                                        , new DynamicKey(lastUserId)
                                        , new EvictDynamicKey(isEvictCache))
                                .map(Reply::getData);
                    }
                });
    }
}
