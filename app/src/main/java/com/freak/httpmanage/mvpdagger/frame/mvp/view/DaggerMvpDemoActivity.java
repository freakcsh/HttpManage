package com.freak.httpmanage.mvpdagger.frame.mvp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.delegate.base.BaseDaggerMvpActivity;
import com.freak.httpmanage.R;
import com.freak.httpmanage.mvpdagger.frame.dagger.component.DaggerDaggerMvpDemoComponent;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.mvpdagger.frame.mvp.presenter.DaggerMvpDemoPresenter;
import com.freak.httpmanage.net.log.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaggerMvpDemoActivity extends BaseDaggerMvpActivity<DaggerMvpDemoPresenter> implements DaggerMvpDemoContract.View {
    @BindView(R.id.btnNet)
    Button btnNet;
    @BindView(R.id.btnCache)
    Button btnCache;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        LogUtil.e("开始注入");
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
    public void loginSuccess(DaggerLoginEntity loginEntity) {
        LogUtil.d("LoginEntity " + loginEntity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        LogUtil.init("HttpManager",true);
    }

    @OnClick({R.id.btnNet, R.id.btnCache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNet:
                Log.e(TAG,"开始请求0");
                if (mPresenter != null) {
                    mPresenter.login("freak","1234561","1",1,true);
                }else {
                    Log.e(TAG,"mPresenter 为 null");
                    LogUtil.e("mPresenter 为 null");
                }
                break;
            case R.id.btnCache:
                if (mPresenter != null) {
                    mPresenter.login("freak","1234561","1",1,false);
                }
                break;
        }
    }
}
