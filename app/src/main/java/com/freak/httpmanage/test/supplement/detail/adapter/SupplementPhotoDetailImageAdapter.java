package com.freak.httpmanage.test.supplement.detail.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.R;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.PictureEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.VideoEntity;
import com.freak.httpmanage.util.GlideUtil;

import java.util.List;

public class SupplementPhotoDetailImageAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_PICTURE = 2;
    //加号按钮
    public static final int TYPE_CAMERA = 3;
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SupplementPhotoDetailImageAdapter(List<MultiItemEntity> data, Context context) {
        super(data);
        addItemType(TYPE_CAMERA, R.layout.view_item_supplement_photo_detail_image_add);
        addItemType(TYPE_PICTURE, R.layout.view_item_supplement_photo_detail_image);
        addItemType(TYPE_VIDEO, R.layout.view_item_supplement_photo_detail_image_video);
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return getData().size() > 6 ? 6 : getData().size();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_VIDEO:
                VideoEntity videoEntity = (VideoEntity) item;
                GlideUtil.getInstance().loadImageSquare(context, helper.getView(R.id.imageViewSupplementPhotoDetailImageItemBg), videoEntity.getUrl());
                helper.addOnClickListener(R.id.imageViewSupplementPhotoDetailImageItemDel);
                break;
            case TYPE_PICTURE:
                PictureEntity pictureEntity = (PictureEntity) item;
                GlideUtil.getInstance().loadImageSquare(context, helper.getView(R.id.imageViewSupplementPhotoDetailImageItemBg), pictureEntity.getUrl());
                helper.addOnClickListener(R.id.imageViewSupplementPhotoDetailImageItemDel);
                break;
            case TYPE_CAMERA:
                break;

        }

    }

    /**
     * 删除
     */
    public void delete(int position) {
        try {

            if (position != RecyclerView.NO_POSITION && getData().size() > position) {
                getData().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getData().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
