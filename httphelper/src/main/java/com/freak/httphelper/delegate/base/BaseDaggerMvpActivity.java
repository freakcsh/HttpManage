package com.freak.httphelper.delegate.base;

import android.os.Bundle;
import android.view.InflateException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.freak.httphelper.cache.Cache;
import com.freak.httphelper.cache.CacheType;
import com.freak.httphelper.delegate.IActivity;
import com.freak.httphelper.lifecycle.ActivityLifecycleAble;
import com.freak.httphelper.mvp.IDaggerPresenter;
import com.freak.httphelper.utils.ComponentUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseDaggerMvpActivity<P extends IDaggerPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleAble {
    protected final String TAG = this.getClass().getSimpleName();
    private Cache<String, Object> mCache;
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }


    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ComponentUtil.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    /**
     * 这个 {@link AppCompatActivity} 是否会使用 {@link Fragment}, 框架会根据这个属性判断是否注册 {@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 {@code false}, 那意味着这个 {@link AppCompatActivity} 不需要绑定 {@link Fragment}, 那你再在这个 {@link AppCompatActivity} 中绑定继承于 {@link BaseDaggerMvpFragment} 的 {@link Fragment} 将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link Fragment}
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

}
