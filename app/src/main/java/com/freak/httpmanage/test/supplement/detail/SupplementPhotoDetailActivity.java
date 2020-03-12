package com.freak.httpmanage.test.supplement.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.R;
import com.freak.httpmanage.aop.AopOnclick;
import com.freak.httpmanage.app.BaseAbstractMvpActivity;
import com.freak.httpmanage.app.IActivityStatusBar;
import com.freak.httpmanage.bean.UpLoadEntity;
import com.freak.httpmanage.load.LoadingViewPopupWindow;
import com.freak.httpmanage.test.supplement.detail.adapter.DragListener;
import com.freak.httpmanage.test.supplement.detail.adapter.SupplementPhotoDetailImageAdapter;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.AddIconEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.CommodityDetailEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.PictureEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.VideoEntity;
import com.freak.httpmanage.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 商品补图详情
 */
public class SupplementPhotoDetailActivity extends BaseAbstractMvpActivity<SupplementPhotoDetailPresenter> implements SupplementPhotoDetailContract.View, IActivityStatusBar {
    @BindView(R.id.constraintLayoutSupplementPhotoDetailBack)
    ConstraintLayout constraintLayoutSupplementPhotoDetailBack;
    @BindView(R.id.textViewSupplementPhotoDetailSave)
    TextView textViewSupplementPhotoDetailSave;
    @BindView(R.id.constraintLayoutSupplementPhotoDetailHead)
    ConstraintLayout constraintLayoutSupplementPhotoDetailHead;
    @BindView(R.id.textViewSupplementPhotoDetailContext)
    TextView textViewSupplementPhotoDetailContext;
    @BindView(R.id.textViewSupplementPhotoDetailContext2)
    TextView textViewSupplementPhotoDetailContext2;
    @BindView(R.id.textViewSupplementPhotoDetailPath)
    TextView textViewSupplementPhotoDetailPath;
    @BindView(R.id.viewSupplementPhotoDetail)
    View viewSupplementPhotoDetail;
    @BindView(R.id.recyclerViewSupplementPhotoDetail)
    RecyclerView recyclerViewSupplementPhotoDetail;
    @BindView(R.id.textViewSupplementPhotoDetail)
    TextView textViewSupplementPhotoDetail;
    @BindView(R.id.view_supplement_camera)
    View viewSupplementCamera;
    @BindView(R.id.text_view_supplement_camera_delete_text)
    TextView textViewSupplementCameraDeleteText;
    @BindView(R.id.image_view_supplement_camera)
    ImageView imageViewSupplementCamera;
    @BindView(R.id.imageViewSupplementPhotoDetailItemDel)
    ImageView imageViewSupplementPhotoDetailItemDel;
    @BindView(R.id.constraintLayoutSupplementPhotoDetailItemDel)
    ConstraintLayout constraintLayoutSupplementPhotoDetailItemDel;
    private String id;
    private String scanResult = "";
    private Bundle bundle;
    private List<String> upList;
    private List<MultiItemEntity> multiItemEntityList;
    private SupplementPhotoDetailImageAdapter supplementPhotoDetailAdapter;
    private boolean isStartUpLoad = false;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> videoSelectList = new ArrayList<>();
    private DragListener mDragListener;
    private boolean isUpward;
    private ItemTouchHelper mItemTouchHelper;
    private ProgressDialog dialog;
    private String videoUrl = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_supplement_photo_detail;
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
        bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        id = bundle.getString("id");
        scanResult = bundle.getString("scanResult");
        upList = new ArrayList<>();
        multiItemEntityList = new ArrayList<>();
        dialog = new ProgressDialog(SupplementPhotoDetailActivity.this);
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        recyclerViewSupplementPhotoDetail.setLayoutManager(new GridLayoutManager(mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        supplementPhotoDetailAdapter = new SupplementPhotoDetailImageAdapter(multiItemEntityList, mActivity);
        supplementPhotoDetailAdapter.bindToRecyclerView(recyclerViewSupplementPhotoDetail);
        recyclerViewSupplementPhotoDetail.setAdapter(supplementPhotoDetailAdapter);
        supplementPhotoDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case SupplementPhotoDetailImageAdapter.TYPE_CAMERA:
                        break;
                    case SupplementPhotoDetailImageAdapter.TYPE_PICTURE:
                        PictureEntity pictureEntity = (PictureEntity) adapter.getData().get(position);
                        new XPopup.Builder(mActivity)
                                .asImageViewer(imageViewSupplementCamera, pictureEntity.getUrl(), new ImageLoader())
                                .show();
                        break;
                    case SupplementPhotoDetailImageAdapter.TYPE_VIDEO:
                        //预览视频
                        VideoEntity videoEntity = (VideoEntity) adapter.getData().get(position);
                        PictureSelector.create(SupplementPhotoDetailActivity.this)
                                .themeStyle(R.style.picture_default_style)
//                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                .externalPictureVideo(videoEntity.getUrl());
                        break;
                }

            }
        });
        supplementPhotoDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.imageViewSupplementPhotoDetailImageItemDel:
                        if (position < supplementPhotoDetailAdapter.getData().size()) {
                            isStartUpLoad = true;
                            textViewSupplementPhotoDetailSave.setEnabled(true);
                            supplementPhotoDetailAdapter.remove(position);
                            supplementPhotoDetailAdapter.notifyItemRemoved(position);
                        }
                        break;
                }
            }
        });
        mDragListener = new DragListener() {
            @Override
            public void deleteState(boolean isDelete) {
                if (isDelete) {
                    textViewSupplementCameraDeleteText.setText("");
                    constraintLayoutSupplementPhotoDetailItemDel.setSelected(true);
                } else {
                    textViewSupplementCameraDeleteText.setText("11");
                    constraintLayoutSupplementPhotoDetailItemDel.setSelected(false);
                }
            }

            @Override
            public void dragState(boolean isStart) {
                int visibility = constraintLayoutSupplementPhotoDetailItemDel.getVisibility();
                if (isStart) {
                    if (visibility == View.GONE) {
                        constraintLayoutSupplementPhotoDetailItemDel.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        constraintLayoutSupplementPhotoDetailItemDel.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (visibility == View.VISIBLE) {
                        constraintLayoutSupplementPhotoDetailItemDel.animate().alpha(0).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        constraintLayoutSupplementPhotoDetailItemDel.setVisibility(View.GONE);
                    }
                }
            }
        };

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            //处理侧滑
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            //同来设置 拖拽移动，或移动删除
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != SupplementPhotoDetailImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(0.7f);
                }
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
            }

            //在拖动中不断的回调这个方法
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到item原来的position
                if (viewHolder.getItemViewType() == SupplementPhotoDetailImageAdapter.TYPE_VIDEO || target.getItemViewType() == SupplementPhotoDetailImageAdapter.TYPE_VIDEO) {
                    return false;
                }
                try {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //得到目标position
                    int toPosition = target.getAdapterPosition();
                    int itemViewType = target.getItemViewType();
                    if (itemViewType != SupplementPhotoDetailImageAdapter.TYPE_CAMERA) {
                        if (fromPosition < toPosition) {
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(supplementPhotoDetailAdapter.getData(), i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(supplementPhotoDetailAdapter.getData(), i, i - 1);
                            }
                        }
                        supplementPhotoDetailAdapter.notifyItemMoved(fromPosition, toPosition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != SupplementPhotoDetailImageAdapter.TYPE_CAMERA) {
                    if (null == mDragListener) {
                        return;
                    }
                    int sh = recyclerView.getHeight() + constraintLayoutSupplementPhotoDetailItemDel.getHeight();
                    int ry = constraintLayoutSupplementPhotoDetailItemDel.getTop() - sh;
                    if (dY >= ry) {
                        //拖到删除处
                        mDragListener.deleteState(true);
                        if (isUpward) {
                            //在删除处放手，则删除item
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            supplementPhotoDetailAdapter.delete(viewHolder.getAdapterPosition());
                            resetState();
                            return;
                        }
                    } else {//没有到删除处
                        if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {
                            //如果viewHolder不可见，则表示用户放手，重置删除区域状态
                            mDragListener.dragState(false);
                        }
                        mDragListener.deleteState(false);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                int itemViewType = viewHolder != null ? viewHolder.getItemViewType() : SupplementPhotoDetailImageAdapter.TYPE_CAMERA;
                if (itemViewType != SupplementPhotoDetailImageAdapter.TYPE_CAMERA) {
                    if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && mDragListener != null) {
                        mDragListener.dragState(true);
                    }
                    super.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                isUpward = true;
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != SupplementPhotoDetailImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(1.0f);
                    super.clearView(recyclerView, viewHolder);
                    supplementPhotoDetailAdapter.notifyDataSetChanged();
                    resetState();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isStartUpLoad = true;
                            textViewSupplementPhotoDetailSave.setEnabled(true);
                        }
                    });

                }
            }
        });
        // 绑定拖拽事件
        mItemTouchHelper.attachToRecyclerView(recyclerViewSupplementPhotoDetail);
        textViewSupplementPhotoDetailSave.setEnabled(false);
        mPresenter.goodsDetail(scanResult, id);
        LoadingViewPopupWindow.getInstance(mActivity).show();
    }



    /**
     * 重置
     */
    private void resetState() {
        if (mDragListener != null) {
            mDragListener.deleteState(false);
            mDragListener.dragState(false);
        }
        isUpward = false;
    }


    private void startUpLoading(File file1) {
        dialog.show();
        mPresenter.upLoad(file1);
        LoadingViewPopupWindow.getInstance(mActivity).show();
    }

    @Override
    protected SupplementPhotoDetailPresenter createPresenter() {
        return new SupplementPhotoDetailPresenter();
    }

    @Override
    public void showResult(String toast) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        ToastUtil.shortShow(toast);
        dismissProgress();
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
    public void goodsDetailSuccess(CommodityDetailEntity commodityDetailEntity) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        if (commodityDetailEntity != null) {
            if (TextUtils.isEmpty(id)) {
                id = String.valueOf(commodityDetailEntity.getId());
            }
            textViewSupplementPhotoDetailContext.setText(commodityDetailEntity.getGoods_name());
            textViewSupplementPhotoDetailContext2.setText(commodityDetailEntity.getGoods_info());
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < commodityDetailEntity.getCat_list().size(); i++) {
                if (i == commodityDetailEntity.getCat_list().size() - 1) {
                    stringBuffer.append(commodityDetailEntity.getCat_list().get(i).getCat_name());
                } else {
                    stringBuffer.append(commodityDetailEntity.getCat_list().get(i).getCat_name()).append("/");
                }
            }
            textViewSupplementPhotoDetailPath.setText(stringBuffer.toString());
            if (!TextUtils.isEmpty(commodityDetailEntity.getVideo_url())) {
                VideoEntity videoEntity = new VideoEntity();
                videoEntity.setUrl(commodityDetailEntity.getVideo_url());
                multiItemEntityList.add(videoEntity);
            }
            if (commodityDetailEntity.getSlider_image().size() >= 5) {
                //图片大于五张
                for (int j = 0; j < 5; j++) {
                    PictureEntity pictureEntity = new PictureEntity();
                    pictureEntity.setUrl(commodityDetailEntity.getSlider_image().get(j));
                    multiItemEntityList.add(pictureEntity);
                }
                if (multiItemEntityList.size() <= 6) {
                    LogUtil.e("添加");
                    AddIconEntity addIconEntity = new AddIconEntity();
                    multiItemEntityList.add(addIconEntity);
                }
            } else {
                //小于五张
                for (int j = 0; j < commodityDetailEntity.getSlider_image().size(); j++) {
                    PictureEntity pictureEntity = new PictureEntity();
                    pictureEntity.setUrl(commodityDetailEntity.getSlider_image().get(j));
                    multiItemEntityList.add(pictureEntity);
                }

                if (multiItemEntityList.size() < 6) {
                    AddIconEntity addIconEntity = new AddIconEntity();
                    multiItemEntityList.add(addIconEntity);
                }
            }
            supplementPhotoDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void upLoadSuccess(UpLoadEntity upLoadEntity) {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        dismissProgress();
        if (upLoadEntity != null) {
            PictureEntity pictureEntity = new PictureEntity();
            pictureEntity.setUrl(upLoadEntity.getUrl());
            if (multiItemEntityList.get(0).getItemType() == SupplementPhotoDetailImageAdapter.TYPE_VIDEO) {
                multiItemEntityList.add(1, pictureEntity);
            } else {
                multiItemEntityList.add(0, pictureEntity);
            }
            supplementPhotoDetailAdapter.notifyDataSetChanged();
        }
        if (!isStartUpLoad) {
            isStartUpLoad = true;
        }
        textViewSupplementPhotoDetailSave.setEnabled(true);
    }

    private void dismissProgress() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }



    @Override
    public void updateGoodsSuccess() {
        LoadingViewPopupWindow.getInstance(mActivity).dismiss();
        finish();
    }

    @Override
    public void onProgress(int progress) {
        dialog.setProgress(progress);
    }

    @Override
    public void onUpLoadSuccess(ResponseBody responseBody) {
        dialog.dismiss();
    }

    @AopOnclick
    @OnClick({R.id.constraintLayoutSupplementPhotoDetailBack, R.id.textViewSupplementPhotoDetailSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.constraintLayoutSupplementPhotoDetailBack:
                finish();
                break;
            case R.id.textViewSupplementPhotoDetailSave:
                if (multiItemEntityList.get(0).getItemType() == SupplementPhotoDetailImageAdapter.TYPE_VIDEO) {
                    if (multiItemEntityList.size() == 2) {
                        ToastUtil.shortShow("请上传图片");
                        return;
                    }
                }
                if (multiItemEntityList.size() == 1) {
                    ToastUtil.shortShow("请上传图片");
                    return;
                }
                upList.clear();
                for (int i = 0; i < multiItemEntityList.size() - 1; i++) {
                    switch (multiItemEntityList.get(i).getItemType()) {
                        case SupplementPhotoDetailImageAdapter.TYPE_PICTURE:
                            PictureEntity pictureEntity = (PictureEntity) multiItemEntityList.get(i);
                            upList.add(pictureEntity.getUrl());
                            break;
                        case SupplementPhotoDetailImageAdapter.TYPE_VIDEO:
                            VideoEntity videoEntity = (VideoEntity) multiItemEntityList.get(i);
                            videoUrl = videoEntity.getUrl();
                            break;

                    }
                }
                LogUtil.d("upList  " + upList);
                mPresenter.updateGoods(id, upList, videoUrl);
                LoadingViewPopupWindow.getInstance(mActivity).show();
                break;
        }
    }


    public static class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
