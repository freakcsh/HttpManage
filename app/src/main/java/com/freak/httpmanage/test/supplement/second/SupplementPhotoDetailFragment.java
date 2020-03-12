package com.freak.httpmanage.test.supplement.second;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.R;
import com.freak.httpmanage.app.BaseAbstractMvpFragment;
import com.freak.httpmanage.load.LoadingViewPopupWindow;
import com.freak.httpmanage.test.supplement.detail.SupplementPhotoDetailActivity;
import com.freak.httpmanage.test.supplement.entity.ClassifyDetailEntity;
import com.freak.httpmanage.test.supplement.second.adapter.SupplementPhotoDetailAdapter;
import com.freak.httpmanage.test.supplement.second.entity.SkuListEntity;
import com.freak.httpmanage.test.supplement.second.entity.SupplementPhotoDetailCommodityEntity;
import com.freak.httpmanage.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SupplementPhotoDetailFragment extends BaseAbstractMvpFragment<SupplementPhotoDetailFragmentPresenter> implements SupplementPhotoDetailFragmentContract.View {
    @BindView(R.id.recyclerViewSupplementPhotoDetail)
    RecyclerView recyclerViewSupplementPhotoDetail;
    private Bundle bundle;
    private String id;
    private boolean isLazyLoadFinish = false;
    private List<MultiItemEntity> list;
    private SupplementPhotoDetailAdapter supplementPhotoDetailAdapter;
    private int page = 1;

    @Override
    protected SupplementPhotoDetailFragmentPresenter createPresenter() {
        return new SupplementPhotoDetailFragmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_supplement_photo_detail;
    }

    @Override
    protected void initEventAndData(View view) {
        bundle = getArguments();
        if (bundle == null) {
            mActivity.finish();
            return;
        }
        id = bundle.getString("id");
        list = new ArrayList<>();
        supplementPhotoDetailAdapter = new SupplementPhotoDetailAdapter(list, mActivity);
        recyclerViewSupplementPhotoDetail.setLayoutManager(new LinearLayoutManager(mActivity));
        supplementPhotoDetailAdapter.bindToRecyclerView(recyclerViewSupplementPhotoDetail);
        //添加自定义分割线：可自定义分割线高度和颜色
//        recyclerViewSupplementPhotoDetail.addItemDecoration(new RecycleViewDivider(
//                mActivity, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_eeeeee)));
        recyclerViewSupplementPhotoDetail.setAdapter(supplementPhotoDetailAdapter);
        supplementPhotoDetailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                loadData();
            }
        }, recyclerViewSupplementPhotoDetail);
        supplementPhotoDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case SupplementPhotoDetailAdapter.ITEM_CONTEXT:
                        SupplementPhotoDetailCommodityEntity dataBean = (SupplementPhotoDetailCommodityEntity) adapter.getData().get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(dataBean.getId()));
                        gotoActivity(SupplementPhotoDetailActivity.class, false, bundle);
                        break;
                }

            }
        });
    }

    @Override
    protected void initLazyData() {
//        loadData();
//        isLazyLoadFinish = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        //TODO 处理返回数据更新问题
        loadData();
    }

    private void loadData() {
        mPresenter.getGoods(id, String.valueOf(page));
        LogUtil.e("开始请求 "+id);
        LoadingViewPopupWindow.getInstance(mActivity).show();
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
//        LogUtil.e("isVisibleToUser "+isVisibleToUser);
//        if (isVisibleToUser) {
//            if (isLazyLoadFinish) {
//                loadData();
//            }
//        } else {
//        }
//    }

    @Override
    protected void showLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showResult(String toast) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        ToastUtil.shortShow(toast);
    }


    @Override
    public void getGoodsSuccess(ClassifyDetailEntity classifyDetailEntity) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        if (page == 1) {
            list.clear();
        }
        if (classifyDetailEntity != null) {
            if (page >= classifyDetailEntity.getLast_page()) {
                supplementPhotoDetailAdapter.loadMoreEnd();
            } else {
                supplementPhotoDetailAdapter.loadMoreComplete();
            }
            if (classifyDetailEntity.getData() != null && classifyDetailEntity.getData().size() > 0) {
                for (SupplementPhotoDetailCommodityEntity supplementPhotoDetailCommodityEntity : classifyDetailEntity.getData()) {
                    if (supplementPhotoDetailCommodityEntity.getSku_list() != null && supplementPhotoDetailCommodityEntity.getSku_list().size() > 0 && supplementPhotoDetailCommodityEntity.getSku_list().size() > 1) {
                        for (SkuListEntity skuListEntity : supplementPhotoDetailCommodityEntity.getSku_list()) {
                            supplementPhotoDetailCommodityEntity.addSubItem(skuListEntity);
                        }
                    }
                    list.add(supplementPhotoDetailCommodityEntity);
                }
            }
        }
        supplementPhotoDetailAdapter.notifyDataSetChanged();
    }

}
