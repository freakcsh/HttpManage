package com.freak.httpmanage.test.supplement.detail;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httphelper.log.LogUtil;
import com.freak.httphelper.uploading.FileUploadObserver;
import com.freak.httpmanage.app.ApiService;
import com.freak.httpmanage.bean.UpLoadEntity;
import com.freak.httpmanage.net.response.HttpResultFunc;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.CommodityDetailEntity;
import com.freak.httpmanage.util.RequestUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SupplementPhotoDetailPresenter extends RxPresenter<SupplementPhotoDetailContract.View> implements SupplementPhotoDetailContract.Presenter {
    private ApiService apiService = HttpMethods.getInstance().create(ApiService.class);

    @Override
    public void goodsDetail(String sign, String id) {
        RequestBody requestBody = RequestUtils.newParams()
                .addParams("sign", sign)
                .addParams("id", id)
                .createRequestBody();
        Observable<CommodityDetailEntity> observable = apiService.goodsDetail(requestBody).map(new HttpResultFunc<>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<CommodityDetailEntity>() {
            @Override
            public void onSuccess(CommodityDetailEntity model) {
                mView.goodsDetailSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.showResult(msg);
            }
        }));
    }

    @Override
    public void updateGoods(String goods_id, List<String> slider_image,String video_url) {
        RequestBody requestBody = RequestUtils.newParams()
                .addParams("goods_id", goods_id)
                .addParams("slider_image", slider_image)
                .addParams("video_url", video_url)
                .createRequestBody();
        Observable<Object> observable = apiService.updateGoods(requestBody).map(new HttpResultFunc<>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                mView.updateGoodsSuccess();
            }

            @Override
            public void onFailure(String msg) {
                mView.showResult(msg);
            }
        }));
    }

    @Override
    public void upLoad(File file) {
//        RequestBody requestBody = RequestUtils.newParams().createRequestMultipartBodyWithProgress(file, "file", new FileUploadObserver<ResponseBody>() {
//            @Override
//            public void onUpLoadSuccess(ResponseBody responseBody) {
//                mView.onUpLoadSuccess(responseBody);
//            }
//
//            @Override
//            public void onUpLoadFail(Throwable e) {
//                mView.showResult(e.toString());
//            }
//
//            @Override
//            public void onProgress(int progress) {
//                LogUtil.e("当前进度 " + progress);
//                mView.onProgress(progress);
//            }
//        });
//
////        RequestBody requestBody = RequestUtils.newParams().createRequestMultipartBody(file, "file");
//        Observable<UpLoadEntity> observable = apiService.upLoadReceipt(requestBody).map(new HttpResultFunc<>());
//        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<UpLoadEntity>() {
//            @Override
//            public void onSuccess(UpLoadEntity model) {
//                mView.upLoadSuccess(model);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.showResult(msg);
//            }
//        }));
    }


}
