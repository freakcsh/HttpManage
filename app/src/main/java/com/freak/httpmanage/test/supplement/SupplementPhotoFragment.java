package com.freak.httpmanage.test.supplement;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.freak.httpmanage.R;
import com.freak.httpmanage.app.BaseAbstractMvpFragment;
import com.freak.httpmanage.load.LoadingViewPopupWindow;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;
import com.freak.httpmanage.test.supplement.second.SupplementPhotoDetailFragment;
import com.freak.httpmanage.util.TabLayoutIndicatorLineUtil;
import com.freak.httpmanage.util.ToastUtil;
import com.freak.httpmanage.view.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SupplementPhotoFragment extends BaseAbstractMvpFragment<SupplementPhotoPresenter> implements SupplementPhotoContract.View, ViewPager.OnPageChangeListener {
    @BindView(R.id.tabLayoutSupplementPhoto)
    TabLayout tabLayoutSupplementPhoto;
    @BindView(R.id.linearLayoutSupplementPhoto)
    LinearLayout linearLayoutSupplementPhoto;
    @BindView(R.id.constraintLayoutSupplementPhoto)
    ConstraintLayout constraintLayoutSupplementPhoto;
    @BindView(R.id.viewPagerSupplementPhoto)
    CustomViewPager viewPagerSupplementPhoto;
    @BindView(R.id.textViewSelectClassify)
    TextView textViewSelectClassify;
    private String id;
    private Bundle bundle;
    private List<String> title;
    private List<Fragment> mFragments;
    private int selectPosition;
    private boolean isShowPopup = false;

    @Override
    protected SupplementPhotoPresenter createPresenter() {
        return new SupplementPhotoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_supplement_photo;
    }

    @Override
    protected void initEventAndData(View view) {
        bundle = getArguments();
        if (bundle == null) {
            mActivity.finish();
            return;
        }
        id = bundle.getString("id");
        mFragments = new ArrayList<>();
        title = new ArrayList<>();
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void initLazyData() {

    }

    private void loadData() {
        mPresenter.getTopList(id);
//        LoadingViewPopupWindow.getInstance(mActivity).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void showLoading() {

    }

    @Override
    public void showResult(String toast) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        ToastUtil.shortShow(toast);
    }


    @Override
    public void getTopListSuccess(List<ClassifyEntity> classifyEntityList) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        title.clear();
        mFragments.clear();
        if (classifyEntityList != null && classifyEntityList.size() > 0) {
            title.add("全部");
            Bundle bundleAll = new Bundle();
            bundleAll.putString("id", id);
            SupplementPhotoDetailFragment supplementPhotoDetailFragmentAll = new SupplementPhotoDetailFragment();
            supplementPhotoDetailFragmentAll.setArguments(bundleAll);
            mFragments.add(supplementPhotoDetailFragmentAll);
            for (ClassifyEntity classifyEntity : classifyEntityList) {
                Bundle bundle = new Bundle();
                bundle.putString("id", classifyEntity.getId());
                SupplementPhotoDetailFragment supplementPhotoDetailFragment = new SupplementPhotoDetailFragment();
                supplementPhotoDetailFragment.setArguments(bundle);
                mFragments.add(supplementPhotoDetailFragment);
                title.add(classifyEntity.getCat_name());
            }
            viewPagerSupplementPhoto.setScanScroll(true);
            viewPagerSupplementPhoto.setOffscreenPageLimit(1);
//            viewPagerSupplementPhoto.setAdapter(new TabLayoutAdapter(getChildFragmentManager(), title, mFragments));
            viewPagerSupplementPhoto.setAdapter(new TabLayoutStateAdapter(getChildFragmentManager(), title, mFragments));
            tabLayoutSupplementPhoto.setupWithViewPager(viewPagerSupplementPhoto);
            tabLayoutSupplementPhoto.post(new Runnable() {
                @Override
                public void run() {
                    TabLayoutIndicatorLineUtil.setIndicator(tabLayoutSupplementPhoto, 5, 0);
                }
            });

            tabLayoutSupplementPhoto.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    selectPosition = tab.getPosition();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }


    @OnClick({R.id.linearLayoutSupplementPhoto})
    public void onViewClicked(View view) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
