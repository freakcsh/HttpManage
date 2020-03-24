package com.freak.httpmanage.mvpdagger.frame.mvp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.delegate.base.BaseDaggerMvpActivity;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.presenter.DaggerMvpDemoPresenter;

@SuppressLint("Registered")
public class DaggerMvpDemoActivity extends BaseDaggerMvpActivity<DaggerMvpDemoPresenter> implements DaggerMvpDemoContract.View {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
