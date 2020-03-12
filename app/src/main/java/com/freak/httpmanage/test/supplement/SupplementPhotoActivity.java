package com.freak.httpmanage.test.supplement;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.R;
import com.freak.httpmanage.app.BaseAbstractMvpActivity;
import com.freak.httpmanage.app.IActivityStatusBar;
import com.freak.httpmanage.load.LoadingViewPopupWindow;
import com.freak.httpmanage.test.supplement.adapter.ClassifyMenuTabAdapter;
import com.freak.httpmanage.test.supplement.adapter.VerticalTabLayoutAdapter;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;
import com.freak.httpmanage.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 商品补图工具
 */
public class SupplementPhotoActivity extends BaseAbstractMvpActivity<SupplementPhotoPresenter> implements SupplementPhotoContract.View, IActivityStatusBar {
    @BindView(R.id.text_view_supplement_photo_search)
    TextView textViewSupplementPhotoSearch;
    @BindView(R.id.relative_layout_supplement_photo)
    ConstraintLayout relativeLayoutSupplementPhoto;
    @BindView(R.id.linear_layout_supplement_photo_scan)
    LinearLayout linearLayoutSupplementPhotoScan;
    @BindView(R.id.vertical_tab_layout_supplement_photo_left)
    VerticalTabLayout verticalTabLayoutSupplementPhotoLeft;
    @BindView(R.id.view_pager_supplement_photo_right)
    ViewPager2 viewPagerSupplementPhotoRight;
    private VerticalTabLayoutAdapter mVerticalTabLayoutAdapter;
    private List<Fragment> fragmentList;

    @Override
    protected int getLayout() {
        return R.layout.activity_supplement_photo;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void onCreateLoadData() {

    }

    @Override
    protected void onDestroyRelease() {

    }

    @Override
    protected void onResumeLoadData() {

    }

    @Override
    protected void initView() {
        fragmentList = new ArrayList<>();
        mPresenter.getTopList("");
        LoadingViewPopupWindow.getInstance(mActivity).show();

    }

    /**
     * a.关联TabLayout和ViewPager
     * b.创建TabLayout的数据适配器
     * c.设置TabLayout的数据适配器
     */
    private void bindTabAndPager(List<ClassifyEntity> classify) {
        for (ClassifyEntity classifyEntity : classify) {
            Bundle bundle = new Bundle();
            bundle.putString("id", classifyEntity.getId());
            SupplementPhotoFragment supplementPhotoFragment = new SupplementPhotoFragment();
            supplementPhotoFragment.setArguments(bundle);
            fragmentList.add(supplementPhotoFragment);

        }
        mVerticalTabLayoutAdapter = new VerticalTabLayoutAdapter(this, fragmentList);
        //设置滑动 true:滑动，false：禁止滑动
        viewPagerSupplementPhotoRight.setUserInputEnabled(false);
        viewPagerSupplementPhotoRight.setAdapter(mVerticalTabLayoutAdapter);
        setupWithViewPager(viewPagerSupplementPhotoRight, verticalTabLayoutSupplementPhotoLeft);
        ClassifyMenuTabAdapter classifyMenuTabAdapter =
                new ClassifyMenuTabAdapter(classify);
        LogUtil.e("大小 " + classify.size());
        viewPagerSupplementPhotoRight.setOffscreenPageLimit(classify.size());
        verticalTabLayoutSupplementPhotoLeft.setTabAdapter(classifyMenuTabAdapter);
        LogUtil.e("verticalTabLayoutSupplementPhotoLeft.getWidth() "+verticalTabLayoutSupplementPhotoLeft.getWidth());
    }

    /**
     * VerticalTabLayout只支持对ViewPager的联动
     * 手动关联联动
     *
     * @param viewPager
     * @param tabLayout
     */
    public void setupWithViewPager(@NonNull ViewPager2 viewPager, @NonNull VerticalTabLayout tabLayout) {

        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                if (viewPager.getAdapter().getItemCount() >= position) {
                    LogUtil.e("position " + position);
                    viewPager.setCurrentItem(position, false);
                }
            }

            @Override
            public void onTabReselected(TabView tab, int position) {
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.setTabSelected(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected SupplementPhotoPresenter createPresenter() {
        return new SupplementPhotoPresenter();
    }

    @Override
    public void showResult(String toast) {
        ToastUtil.shortShow(toast);
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
    }


    @Override
    public int getStatusBarColor() {
        return ContextCompat.getColor(this, R.color.color_white);
    }

    @Override
    public int getDrawableStatusBar() {
        return 0;
    }

    @Override
    public void getTopListSuccess(List<ClassifyEntity> classifyEntityList) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        if (classifyEntityList != null && classifyEntityList.size() > 0) {
            LogUtil.d("一级分类数据" + classifyEntityList);
            bindTabAndPager(classifyEntityList);
        }
    }


    @OnClick({R.id.relative_layout_supplement_photo, R.id.linear_layout_supplement_photo_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //搜索
            case R.id.relative_layout_supplement_photo:
                break;
            //商品二维码扫描
            case R.id.linear_layout_supplement_photo_scan:
                break;
        }
    }
}
