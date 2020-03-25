package com.freak.httpmanage.mvpdagger.frame.dagger.module;

import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.DaggerMvpDemoModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DaggerMvpDemoModule {
    @Binds
    abstract DaggerMvpDemoContract.Model bindDaggerMvpDemoModel(DaggerMvpDemoModel model);
}
