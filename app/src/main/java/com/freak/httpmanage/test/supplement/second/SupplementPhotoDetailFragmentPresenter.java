package com.freak.httpmanage.test.supplement.second;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.net.response.HttpResultFunc;
import com.freak.httpmanage.test.supplement.entity.ClassifyDetailEntity;
import com.freak.httpmanage.util.RequestUtils;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class SupplementPhotoDetailFragmentPresenter extends RxPresenter<SupplementPhotoDetailFragmentContract.View> implements SupplementPhotoDetailFragmentContract.Presenter {
    private ApiServer apiService = HttpMethods.getInstance().create(ApiServer.class);

    /**
     * @param id 全部二级分类id传一级分类id，否则传二级分类id
     */
    @Override
    public void getGoods(String id,String page) {
        RequestBody requestBody = RequestUtils.newParams()
                .addParams("id", id)
                .addParams("page", page)
                .createRequestBody();
        Observable<ClassifyDetailEntity> observable = apiService.getGoods(requestBody).map(new HttpResultFunc<>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<ClassifyDetailEntity>() {
            @Override
            public void onSuccess(ClassifyDetailEntity model) {
                mView.getGoodsSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.showResult(msg);
            }
        }));
    }
}
