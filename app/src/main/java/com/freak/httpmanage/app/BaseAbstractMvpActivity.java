package com.freak.httpmanage.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freak.httphelper.BasePresenter;
import com.freak.httphelper.RxBaseView;
import com.freak.httpmanage.R;
import com.freak.httpmanage.load.LoadingViewPopupWindow;

import butterknife.ButterKnife;

import static com.freak.httpmanage.app.App.DESIGN_WIDTH;


/**
 * @author freak
 * @date 2019/9/11.
 * MVP activity基类
 */

public abstract class BaseAbstractMvpActivity<T extends BasePresenter> extends BaseActivity implements RxBaseView {
    protected T mPresenter;
    protected AppCompatActivity mActivity;

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化事件和数据
     * 网络请求不要放在此方法中，因为断网重连时不会调用此方法，只会调用onCreateLoadData和onResumeLoadData
     */
    protected abstract void initEventAndData();

    /**
     * 在onCreate方法中加载数据
     * 不需要刷新数据则请求数据在此方法中调用
     */
    protected abstract void onCreateLoadData();

    /**
     * 资源释放
     */
    protected abstract void onDestroyRelease();

    /**
     * 在onResume中加载数据
     * 需要返回刷新数据则选择放在这个方法中加载，否则在onCreateLoadData中加载数据
     */
    protected abstract void onResumeLoadData();

    /**
     * 控件初始化
     */
    protected abstract void initView();

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         * 创建presenter对象
         */
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏颜色
//            getWindow().setStatusBarColor(0xff24cf5f);
        }
        App.resetDensity(this, DESIGN_WIDTH);
        setContentView(getLayout());
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        mActivity = this;
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initEventAndData();
        onCreateLoadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * presenter 解除view订阅
         */
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        onDestroyRelease();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        onResumeLoadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        Intent intent = new Intent(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
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
        Intent intent = new Intent(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 自定义返回监听
     */
    public void setBackPress() {
        try {
            View backView = findViewById(R.id.tool_bar);
            backView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 系统返回键监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
