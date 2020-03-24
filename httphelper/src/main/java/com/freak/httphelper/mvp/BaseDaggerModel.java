package com.freak.httphelper.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.freak.httphelper.lifecycle.IRepositoryManager;

public class BaseDaggerModel implements IDaggerModel, LifecycleObserver {
    protected IRepositoryManager mRepositoryManager;//用于管理网络请求层, 以及数据缓存层

    public BaseDaggerModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
