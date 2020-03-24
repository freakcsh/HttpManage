package com.freak.httpmanage.test.supplement;

import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.net.response.HttpResultFunc;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;
import com.freak.httpmanage.util.RequestUtils;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class SupplementPhotoPresenter extends RxPresenter<SupplementPhotoContract.View> implements SupplementPhotoContract.Presenter {
    private ApiServer apiService = HttpMethods.getInstance().create(ApiServer.class);

    @Override
    public void getTopList(String id) {
        RequestBody requestBody = RequestUtils.newParams()
                .addParams("id", id).createRequestBody();
        Observable<List<ClassifyEntity>> observable = apiService.getTopList(requestBody).map(new HttpResultFunc<>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<List<ClassifyEntity>>() {
            @Override
            public void onSuccess(List<ClassifyEntity> model) {
                mView.getTopListSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.showResult(msg);
            }
        }));

    }


}
