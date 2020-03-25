package com.freak.httpmanage.mvpdagger.frame.mvp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.delegate.base.BaseDaggerMvpActivity;
import com.freak.httpmanage.R;
import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.mvpdagger.frame.dagger.component.DaggerDaggerMvpDemoComponent;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.presenter.DaggerMvpDemoPresenter;
import com.freak.httpmanage.net.log.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class DaggerMvpDemoActivity extends BaseDaggerMvpActivity<DaggerMvpDemoPresenter> implements DaggerMvpDemoContract.View {
    @BindView(R.id.btnNet)
    Button btnNet;
    @BindView(R.id.btnCache)
    Button btnCache;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDaggerMvpDemoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dagger_mvp_demo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void loginSuccess(LoginEntity loginEntity) {
        LogUtil.d("LoginEntity " + loginEntity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnNet, R.id.btnCache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNet:
                if (mPresenter != null) {
                    mPresenter.login("freak","123456","1",1,false);
                }else {
                    LogUtil.e("mPresenter 为 null");
                }
                break;
            case R.id.btnCache:
                if (mPresenter != null) {
                    mPresenter.login("freak","123456","1",1,true);
                }
                break;
        }
    }
}
