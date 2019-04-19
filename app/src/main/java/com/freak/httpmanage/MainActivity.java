package com.freak.httpmanage;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.freak.httphelper.RxBus;
import com.freak.httphelper.download.ProgressListener;
import com.freak.httphelper.rxview.RxView;
import com.freak.httphelper.uploading.MultipartUtil;
import com.freak.httpmanage.app.BaseActivity;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.down.DownActivity;
import com.freak.httpmanage.down.DownTaskListActivity;
import com.freak.httpmanage.down.SystemDownloadActivity;
import com.freak.httpmanage.event.RxEvent;
import com.freak.httpmanage.net.log.LogUtil;
import com.freak.httpmanage.net.response.HttpResult;
import com.freak.httpmanage.rxbus.RxBusActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, RxView.OnRxViewClickListener {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Disposable mSubscribe;
    private Button rx_view;

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
    public void onError(String msg) {
        Log.e(TAG, msg);
        tvResult.setText(msg);
    }


    public void update(View view) {

    }


    public void login(View view) {
        mPresenter.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void rxBusOnclick(View view) {
        RxBusActivity.startAction(this);
//        jsonTest();
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
        mPresenter.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    public void cookieLogin(View view) {
        mPresenter.doLogin("13790994100", "caishouhui0524");
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


    public void upload(){

        Map<String, RequestBody> textBody= MultipartUtil.getInstance()
                .addParam("text1","123")
                .addParam("text2","456")
                .Build();

        List<File> files=new ArrayList<>();
        File file=new File(Environment.getExternalStorageDirectory()+"test.png");
        files.add(file);

        //文件上传进度只支持单文件上传的时候使用
        List<MultipartBody.Part> parts= MultipartUtil.makeMultipart("images", files, new ProgressListener() {
            @Override
            public void onProgress(long read, long length, boolean done) {

            }
        });

    }
}
