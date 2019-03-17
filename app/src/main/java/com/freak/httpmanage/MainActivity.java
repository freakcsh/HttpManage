package com.freak.httpmanage;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freak.httphelper.RxBus;
import com.freak.httpmanage.app.BaseActivity;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.event.RxEvent;

import com.freak.httpmanage.net.response.HttpResult;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

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
            if (mSubscribe.isDisposed()){
                mSubscribe.dispose();
            }

        }
    }

    public void login2(View view) {
        mPresenter.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    public void cookieLogin(View view) {
        mPresenter.doLogin("13790994100","caishouhui0524");
    }

    public void cookieLoginStatus(View view) {
        mPresenter.loadLoginStatusEntity();
    }
}
