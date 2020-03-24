package com.freak.httphelper.delegate.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.freak.httphelper.cache.Cache;
import com.freak.httphelper.cache.CacheType;
import com.freak.httphelper.delegate.IFragment;
import com.freak.httphelper.lifecycle.FragmentLifecycleAble;
import com.freak.httphelper.mvp.IDaggerPresenter;
import com.freak.httphelper.utils.ComponentUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseDaggerMvpFragment<P extends IDaggerPresenter> extends Fragment implements IFragment, FragmentLifecycleAble {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    protected AppCompatActivity mActivity;
    protected Context mContext;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    @NonNull
    @Override
    public Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ComponentUtil.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }
}
