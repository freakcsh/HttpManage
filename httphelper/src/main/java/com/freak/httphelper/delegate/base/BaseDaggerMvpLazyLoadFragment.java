package com.freak.httphelper.delegate.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.freak.httphelper.mvp.IDaggerPresenter;

import java.util.List;

import javax.inject.Inject;

public abstract class BaseDaggerMvpLazyLoadFragment<P extends IDaggerPresenter> extends BaseDaggerMvpFragment {
    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求
    public boolean isReLoadData;
    @Inject
    Unused mUnused;
    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    protected abstract void lazyLoadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        loadData();
    }

    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        loadData();
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseDaggerMvpLazyLoadFragment && ((BaseDaggerMvpLazyLoadFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseDaggerMvpLazyLoadFragment && ((BaseDaggerMvpLazyLoadFragment) child).isVisibleToUser) {
                ((BaseDaggerMvpLazyLoadFragment) child).loadData();
            }
        }
    }

    public void loadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
        if (isDataLoaded) {
            if (isReLoadData) {
                isReLoadData = false;
                lazyLoadData();
                //通知子Fragment请求数据
                dispatchParentVisibleState();
            }
        }
    }
    /**
     * 打开一个Activity 默认 不关闭当前activity
     *
     * @param className
     */
    public void gotoActivity(Class<?> className) {
        gotoActivity(className, false);
    }

    /**
     * 打开一个Activity  关闭当前activity
     *
     * @param className
     */
    public void gotoActivityWithFinish(Class<?> className) {
        gotoActivity(className, true);
    }

    /**
     * 打开一个Activity，并控制是否finish
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     */
    public void gotoActivity(Class<?> className, boolean isCloseCurrentActivity) {
        gotoActivity(className, isCloseCurrentActivity, null);
    }

    /**
     * 打开一个activity，并传递数据
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     * @param bundle                 bundle数据
     */
    public void gotoActivity(Class<?> className, boolean isCloseCurrentActivity, Bundle bundle) {
        Intent intent = new Intent(mActivity, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            mActivity.finish();
        }
    }

    /**
     * 打开一个带结果返回的activity，并传递数据
     *
     * @param className   className
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    public void gotoActivityWithResult(Class<?> className, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mActivity, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
