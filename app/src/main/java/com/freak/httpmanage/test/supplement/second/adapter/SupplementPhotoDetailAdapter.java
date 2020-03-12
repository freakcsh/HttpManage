package com.freak.httpmanage.test.supplement.second.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.R;
import com.freak.httpmanage.test.supplement.second.entity.SkuListEntity;
import com.freak.httpmanage.test.supplement.second.entity.SupplementPhotoDetailCommodityEntity;
import com.freak.httpmanage.util.GlideUtil;

import java.util.List;

public class SupplementPhotoDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public final static int ITEM_CONTEXT = 1;
    public final static int ITEM_SKU = 2;

    public SupplementPhotoDetailAdapter(List<MultiItemEntity> data, Context context) {
        super(data);
        this.context = context;
        addItemType(ITEM_CONTEXT, R.layout.view_item_supplement_photo_detail);
        addItemType(ITEM_SKU, R.layout.view_item_supplement_photo_detail_sku);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case ITEM_CONTEXT:
                SupplementPhotoDetailCommodityEntity supplementPhotoDetailCommodityEntity = (SupplementPhotoDetailCommodityEntity) item;
                GlideUtil.getInstance().loadImageCamera1(context, helper.getView(R.id.imageViewSupplementPhotoDetailItemBg), supplementPhotoDetailCommodityEntity.getGoods_image());
                helper.setText(R.id.textViewViewSupplementPhotoDetailItemContext, supplementPhotoDetailCommodityEntity.getGoods_info());
                helper.setText(R.id.textViewViewSupplementPhotoDetailItemContext2, supplementPhotoDetailCommodityEntity.getSelling_points());
                helper.setText(R.id.textViewViewSupplementPhotoDetailItemSku, supplementPhotoDetailCommodityEntity.getSku_list().get(0).getAttr());
                helper.setText(R.id.textViewViewSupplementPhotoDetailItemBarCode, supplementPhotoDetailCommodityEntity.getSku_list().get(0).getSku_sn());
                helper.getView(R.id.constraintLayoutDownOrUp).setVisibility(supplementPhotoDetailCommodityEntity.getSku_list().size() > 1 ? View.VISIBLE : View.GONE);
                helper.setImageResource(R.id.imageViewDownOrUp,supplementPhotoDetailCommodityEntity.isExpanded()?R.drawable.ic_icon_up_1:R.drawable.ic_icon_down_1);
               helper.getView(R.id.textViewViewSupplementPhotoDetailItemSku).setVisibility(supplementPhotoDetailCommodityEntity.isExpanded()?View.GONE:View.VISIBLE);
               helper.getView(R.id.textViewViewSupplementPhotoDetailItemBarCode).setVisibility(supplementPhotoDetailCommodityEntity.isExpanded()?View.GONE:View.VISIBLE);
                helper.getView(R.id.constraintLayoutDownOrUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (supplementPhotoDetailCommodityEntity.isExpanded()) {
                            //收起
                            collapse(pos);
                        }
//                        else if (isOnlyExpandOne) {
//                            IExpandable willExpandItem = (IExpandable) getData().get(pos);
//                            for (int i = getHeaderLayoutCount(); i < getData().size(); i++) {
//                                IExpandable expandable = (IExpandable) getData().get(i);
//                                if (expandable.isExpanded()) {
//                                    collapse(i);
//                                }
//                            }
//                            expand(getData().indexOf(willExpandItem) + getHeaderLayoutCount());
//                        }
                        else {
                            //展开
                            expand(pos);
                        }
                    }
                });
                break;
            case ITEM_SKU:
                SkuListEntity skuListEntity = (SkuListEntity) item;
                helper.setText(R.id.textViewViewSupplementPhotoDetailSkuItemSku, skuListEntity.getAttr());
                helper.setText(R.id.textViewViewSupplementPhotoDetailSkuItemBarCode, skuListEntity.getSku_sn());
                break;
        }

    }
}
