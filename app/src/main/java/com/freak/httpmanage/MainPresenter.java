package com.freak.httpmanage;


import com.freak.httphelper.ApiCallback;
import com.freak.httphelper.HttpMethods;
import com.freak.httphelper.RxPresenter;
import com.freak.httphelper.SubscriberCallBack;
import com.freak.httpmanage.app.ApiServer;
import com.freak.httpmanage.bean.BaseBean;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.bean.LoginStatusEntity;
import com.freak.httpmanage.net.log.LogUtil;
import com.freak.httpmanage.net.response.HttpResult;
import com.freak.httpmanage.net.response.HttpResultFunc;
import com.freak.httpmanage.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);

    @Override
    public void doLogin1(String userName, String pwd) {
        Observable observable = apiServer.login1(userName, pwd);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                mView.onSuccess(model);
                Logger.d(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }

    @Override
    public void doLogin2(String userName, String pwd) {
        Observable<LoginBean> observable = apiServer.login2(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
                mView.onSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }

    @Override
    public void doLogin3(String userName, String pwd) {
        Observable<BaseBean> observable = apiServer.login3(userName, pwd);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean model) {
                Logger.d(model);
                mView.onSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }
    @Override
    public void loadLoginStatusEntity() {
        Observable<LoginStatusEntity> observable = apiServer.loadLoginStatus();
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginStatusEntity>() {
            @Override
            public void onSuccess(LoginStatusEntity model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e(msg);
            }
        }));
    }

    @Override
    public void doLogin(String phone, String password) {
        Observable<LoginEntity> observable = apiServer.login(phone, password);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                LogUtil.e(msg);
            }
        }));
    }

    @Override
    public void uploading(String tip, String tip1, String path) {
//        List<File> files = new ArrayList<>();
//        File file = new File(path);
//        files.add(file);

        //文件上传进度只支持单文件上传的时候使用
//        List<MultipartBody.Part> parts = MultipartUtil.makeMultipart("image", files, new ProgressListener() {
//            @Override
//            public void onProgress(long read, long length, boolean done) {
//                LogUtil.e("进度--》" + read + "\n" + length + "\n" + done);
//            }
//        });
        File file = new File(path);
//        MultipartBody.Part part= MultipartUtil.makeMultipart("image",file);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileRQ);
        LogUtil.d("path"+path);
        Observable<HttpResult> observable = apiServer.uploading(tip, tip1, part);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                LogUtil.e(msg);
            }
        }));
    }

    @Override
    public void uploading1(String tip, String tip1, String path) {
//        List<File> files = new ArrayList<>();
//        File file = new File(Environment.getExternalStorageDirectory() + "test.png");
//        files.add(file);
//
//        //文件上传进度只支持单文件上传的时候使用
//        List<MultipartBody.Part> parts = MultipartUtil.makeMultipart("image", files, new ProgressListener() {
//            @Override
//            public void onProgress(long read, long length, boolean done) {
//                LogUtil.e("进度--》" + read + "\n" + length + "\n" + done);
//            }
//        });
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileRQ);
        Observable<HttpResult> observable = apiServer.uploading(tip, tip1, part);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                LogUtil.e(msg);
            }
        }));
    }

    @Override
    public void uploading2(String path) {
//        List<File> files = new ArrayList<>();
//        File file = new File(Environment.getExternalStorageDirectory() + "test.png");
//        files.add(file);
//
//        //文件上传进度只支持单文件上传的时候使用
//        List<MultipartBody.Part> parts = MultipartUtil.makeMultipart("images", files, new ProgressListener() {
//            @Override
//            public void onProgress(long read, long length, boolean done) {
//                LogUtil.e("进度--》" + read + "\n" + length + "\n" + done);
//            }
//        });
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileRQ);
        Observable<HttpResult> observable = apiServer.uploadingUserPhoto(part);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                LogUtil.e(msg);
            }
        }));
    }
}
