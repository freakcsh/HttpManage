package com.freak.httpmanage.test.supplement.second.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.test.supplement.second.adapter.SupplementPhotoDetailAdapter;

public class SkuListEntity implements MultiItemEntity {

    private String sku_sn;
    private String attr;

    public String getSku_sn() {
        return sku_sn;
    }

    public void setSku_sn(String sku_sn) {
        this.sku_sn = sku_sn;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    @Override
    public int getItemType() {
        return SupplementPhotoDetailAdapter.ITEM_SKU;
    }

    @Override
    public String toString() {
        return "SkuListEntity{" +
                "sku_sn='" + sku_sn + '\'' +
                ", attr='" + attr + '\'' +
                '}';
    }
}
