package com.freak.httpmanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freak.httphelper.RxBus;
import com.freak.httphelper.download.ProgressListener;
import com.freak.httphelper.rxview.RxView;
import com.freak.httphelper.uploading.FileUploadObserver;
import com.freak.httphelper.uploading.MultipartUtil;
import com.freak.httpmanage.aop.AopOnclick;
import com.freak.httpmanage.app.BaseActivity;
import com.freak.httpmanage.bean.BaseBean;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.bean.UpLoadEntity;
import com.freak.httpmanage.down.DownActivity;
import com.freak.httpmanage.down.DownTaskListActivity;
import com.freak.httpmanage.down.SystemDownloadActivity;
import com.freak.httpmanage.event.RxEvent;
import com.freak.httpmanage.net.log.LogUtil;
import com.freak.httpmanage.net.response.HttpResult;
import com.freak.httpmanage.property.BatteryActivity;
import com.freak.httpmanage.rxbus.RxBusContract;
import com.freak.httpmanage.rxbus.RxBusPresenter;
import com.freak.httpmanage.upload.RetrofitClient;
import com.freak.httpmanage.util.picture.PictureSelectorUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, RxBusContract.View,RxView.OnRxViewClickListener {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Disposable mSubscribe;
    private Button rx_view;
    private final int RESULT_CODE_IMAGE = 1001;
//    @Inject
    RxBusPresenter rxBusPresenter;
    private ProgressDialog dialog;
    private List<LocalMedia> videoSelectList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        username = findViewById(R.id.username);
        rx_view = findViewById(R.id.rx_view);
        pwd = findViewById(R.id.pwd);
        tvResult = findViewById(R.id.result);
        mSubscribe = RxBus.getDefault().tObservable(RxEvent.class).subscribe(new Consumer<RxEvent>() {
            @Override
            public void accept(RxEvent rxEvent) throws Exception {
                if (rxEvent.getCode() == 1000) {
                    username.setText(rxEvent.getUserName());
                    pwd.setText(rxEvent.getPassWord());
                }
            }
        });
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("上传文件中");
        RxView.setIntervalTime(2000);
        RxView.setOnClickListeners(this, rx_view);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void onSuccess(LoginBean loginBean) {
        Logger.e("onSuccess");
        Logger.d(loginBean);
        tvResult.setText(loginBean.toString());
    }

    @Override
    public void onSuccess(HttpResult loginBean) {
        tvResult.setText(loginBean.toString());
    }

    @Override
    public void onSuccess(BaseBean loginBean) {
        Logger.d(loginBean);
    }


    @Override
    public void onError(String msg) {
        Log.e(TAG, msg);
        tvResult.setText(msg);
    }

    @Override
    public void upLoadSuccess(UpLoadEntity upLoadEntity) {
        LogUtil.e("上传成功");
    }


    public void update(View view) {

    }

    @AopOnclick(5000)
    public void aop(View view) {
        LogUtil.e("点击了");
    }

    public void optionalOnclick(View view) {
        LogUtil.e("点击了");
    }

    public void batteryOnclick(View view) {
        BatteryActivity.actionStart(this);
    }

    public void uploadOnclick(View view) {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, RESULT_CODE_IMAGE);
    }
    public void uploadVideoOnclick(View view) {
        //选择视频
        PictureSelectorUtil.getInstance().upAnimationWindowStyle().getWeChatStyle(MainActivity.this).createVideo(MainActivity.this, new OnResultCallbackListener() {
            @Override
            public void onResult(List<LocalMedia> result) {
                // 图片选择结果回调
                videoSelectList = result;
                // 例如 LocalMedia 里面返回五种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                for (LocalMedia media : videoSelectList) {
                    com.freak.httphelper.log.LogUtil.e("是否压缩:" + media.isCompressed());
                    com.freak.httphelper.log.LogUtil.e("压缩:" + media.getCompressPath());
                    com.freak.httphelper.log.LogUtil.e("原图:" + media.getPath());
                    com.freak.httphelper.log.LogUtil.e("是否裁剪:" + media.isCut());
                    com.freak.httphelper.log.LogUtil.e("裁剪:" + media.getCutPath());
                    com.freak.httphelper.log.LogUtil.e("是否开启原图:" + media.isOriginal());
                    com.freak.httphelper.log.LogUtil.e("原图路径:" + media.getOriginalPath());
                    com.freak.httphelper.log.LogUtil.e("Android Q 特有Path:" + media.getAndroidQToPath());
                }
//                                    try {
//                                        Bitmap bitmap = BitmapUtil.fileToBitmap(videoSelectList.get(0).getCutPath());
//                                        if (bitmap == null) {
//                                            return;
//                                        }
//                                        File file1 = BitmapUtil.compressImage(bitmap);
//                                        LuBanCompressUtils.getInstance(mActivity).showResult(file1, false);
//
//                                        //压缩完成，开始上传
////                                        startUpLoading(file1);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
                mPresenter.upLoadVideo(new File(videoSelectList.get(0).getPath()));

//                dialog.show();
//                RetrofitClient
//                        .getInstance()
//                        .upLoadFile("https://test.huang-dou.com/api/staff/v1/qiniu", new File(videoSelectList.get(0).getPath()), new FileUploadObserver<ResponseBody>() {
//                            @Override
//                            public void onUpLoadSuccess(ResponseBody responseBody) {
//                                Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
//                                try {
//                                    Log.d("上传进度",responseBody.string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void onUpLoadFail(Throwable e) {
//                                Toast.makeText(MainActivity.this, "上传失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                            @Override
//                            public void onProgress(int progress) {
//                                dialog.setProgress(progress);
//                            }
//                        });
            }
        }, videoSelectList);
    }


    public void login(View view) {
        mPresenter.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());
//        mPresenter.doLogin3("180");
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void rxBusOnclick(View view) {
//        RxBusActivity.startAction(this);
//        jsonTest();
        rxBusPresenter.doTest();
    }

    @Override
    protected void onResume() {
        LogUtil.e("onResume in MainActivity");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            if (mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            }

        }
    }

    public void login2(View view) {
//        mPresenter.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
        mPresenter.login11("123456", "123456", "1");
    }

    public void cookieLogin(View view) {
        mPresenter.doLogin("", "");
    }

    public void cookieLoginStatus(View view) {
        mPresenter.loadLoginStatusEntity();
    }

    public void downOnclick(View view) {
        DownActivity.startAction(this);
    }

    public void systemDownOnclick(View view) {
        SystemDownloadActivity.startAction(this);
    }

    @Override
    public void onRxViewClick(View view) {
        switch (view.getId()) {
            case R.id.rx_view:
                LogUtil.e("点击了");
                break;
            default:
                break;
        }
    }

    public void downTask(View view) {
        DownTaskListActivity.startAction(this);
    }


    public void upload(String path) {
        LogUtil.e("图片地址--》" + path);
        mPresenter.uploading("上传", "图片上传", path);
//        mPresenter.uploading2(path);
    }

    public void upload() {
        Map<String, RequestBody> textBody = MultipartUtil.getInstance()
                .addParam("text1", "123")
                .addParam("text2", "456")
                .Build();

        List<File> files = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory() + "test.png");
        files.add(file);

        //文件上传进度只支持单文件上传的时候使用
        List<MultipartBody.Part> parts = MultipartUtil.makeMultipart("image", files, new FileUploadObserver<ResponseBody>() {
            @Override
            public void onUpLoadSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onUpLoadFail(Throwable e) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_IMAGE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() > 0) {
                    upload(images.get(0).path);
                }

            }
        }
    }

    @Override
    public void rxTestSuccess() {
        LogUtil.e("调用成功");
    }
}
