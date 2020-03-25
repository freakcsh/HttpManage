package com.freak.httpmanage.mvpdagger.frame.mvp.model.api.service;

import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.net.response.HttpResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DaggerApiService {
    /**
     * 商品详情
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/goodsDetail")
    Observable<HttpResult<LoginEntity>> login(@Body RequestBody body);
}
