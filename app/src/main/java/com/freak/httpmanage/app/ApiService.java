package com.freak.httpmanage.app;


import com.freak.httpmanage.net.response.HttpResult;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.CommodityDetailEntity;
import com.freak.httpmanage.test.supplement.entity.ClassifyDetailEntity;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author freak
 * @date 2019/9/11.
 */
public interface ApiService {




    /**
     * 获取一级分类
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/getList")
    Observable<HttpResult<List<ClassifyEntity>>> getTopList(@Body RequestBody body);

    /**
     * 获取某个分类下的商品类表
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/getGoods")
    Observable<HttpResult<ClassifyDetailEntity>> getGoods(@Body RequestBody body);

    /**
     * 搜索商品列表
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/search")
    Observable<HttpResult<ClassifyDetailEntity>> searchCommodity(@Body RequestBody body);

    /**
     * 商品详情
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/goodsDetail")
    Observable<HttpResult<CommodityDetailEntity>> goodsDetail(@Body RequestBody body);

    /**
     * 更新商品图
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/goodsCategory/updateGoods")
    Observable<HttpResult<Object>> updateGoods(@Body RequestBody body);

}
