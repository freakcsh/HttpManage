package com.freak.httpmanage;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freak.httphelper.RxBus;
import com.freak.httpmanage.app.BaseActivity;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.event.RxEvent;
import com.freak.httpmanage.net.BaseBean;
import com.freak.httpmanage.net.HttpResult;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Disposable mSubscribe;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        username = findViewById(R.id.username);
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
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void onSuccess(LoginBean loginBean) {
        Logger.e("onSuccess");
        Logger.d(loginBean);
    }

    @Override
    public void onError(String msg) {
        Log.e(TAG, msg);
    }


    public void update(View view) {

    }


    public void login(View view) {
        Logger.e("开始请求");
        mPresenter.doLogin(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void rxBusOnclick(View view) {
//        RxBusActivity.startAction(this);
//        finish();
        jsonTest();
    }
    public void jsonTest(){
        String json="{\n" +
                "\"msg\": \"666666\",\n" +
                "\"code\": \"10000\",\n" +
                "\"data\": \"\"\n" +
                "}";
        BaseBean httpResult=new Gson().fromJson(json,BaseBean.class);
        Logger.d(httpResult);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            if (mSubscribe.isDisposed()){
                mSubscribe.dispose();
            }

        }
    }
}
