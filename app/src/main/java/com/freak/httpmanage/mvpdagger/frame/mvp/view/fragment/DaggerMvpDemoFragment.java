package com.freak.httpmanage.mvpdagger.frame.mvp.view.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.delegate.base.BaseDaggerMvpLazyLoadFragment;
import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.R;
import com.freak.httpmanage.load.LoadingViewPopupWindow;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.model.entity.DaggerLoginEntity;
import com.freak.httpmanage.mvpdagger.frame.mvp.presenter.DaggerMvpDemoPresenter;
import com.freak.httpmanage.test.supplement.adapter.ClassifyMenuTabAdapter;
import com.freak.httpmanage.test.supplement.adapter.VerticalTabLayoutAdapter;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

public class DaggerMvpDemoFragment extends BaseDaggerMvpLazyLoadFragment<DaggerMvpDemoPresenter> implements DaggerMvpDemoContract.View {
    @BindView(R.id.text_view_dagger_mvp_fragment_search)
    TextView textViewDaggerMvpFragmentSearch;
    @BindView(R.id.relative_layout_dagger_mvp_fragment)
    ConstraintLayout relativeLayoutDaggerMvpFragment;
    @BindView(R.id.linear_layout_dagger_mvp_fragment_scan)
    LinearLayout linearLayoutDaggerMvpFragmentScan;
    @BindView(R.id.vertical_tab_layout_dagger_mvp_fragment_left)
    VerticalTabLayout verticalTabLayoutDaggerMvpFragmentLeft;
    @BindView(R.id.view_pager_dagger_mvp_fragment_right)
    ViewPager2 viewPagerDaggerMvpFragmentRight;
    private VerticalTabLayoutAdapter mVerticalTabLayoutAdapter;
    private List<Fragment> fragmentList;
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dagger_mvp_demo;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void loginSuccess(DaggerLoginEntity loginEntity) {

    }

    @Override
    protected void lazyLoadData() {
        fragmentList = new ArrayList<>();
//        mPresenter.getTopList("");
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
            DaggerMvpDemoDetailFragment supplementPhotoFragment = new DaggerMvpDemoDetailFragment();
            supplementPhotoFragment.setArguments(bundle);
            fragmentList.add(supplementPhotoFragment);
        }
        mVerticalTabLayoutAdapter = new VerticalTabLayoutAdapter(mActivity, fragmentList);
        //设置滑动 true:滑动，false：禁止滑动
        viewPagerDaggerMvpFragmentRight.setUserInputEnabled(false);
        viewPagerDaggerMvpFragmentRight.setAdapter(mVerticalTabLayoutAdapter);
        setupWithViewPager(viewPagerDaggerMvpFragmentRight, verticalTabLayoutDaggerMvpFragmentLeft);
        ClassifyMenuTabAdapter classifyMenuTabAdapter =
                new ClassifyMenuTabAdapter(classify);
        LogUtil.e("大小 " + classify.size());
        viewPagerDaggerMvpFragmentRight.setOffscreenPageLimit(classify.size());
        verticalTabLayoutDaggerMvpFragmentLeft.setTabAdapter(classifyMenuTabAdapter);
        LogUtil.e("verticalTabLayoutDaggerMvpFragmentLeft.getWidth() "+verticalTabLayoutDaggerMvpFragmentLeft.getWidth());
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
//    @Override
    public void getTopListSuccess(List<ClassifyEntity> classifyEntityList) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        if (classifyEntityList != null && classifyEntityList.size() > 0) {
            LogUtil.d("一级分类数据" + classifyEntityList);
            bindTabAndPager(classifyEntityList);
        }
    }

}
