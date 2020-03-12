package com.freak.httpmanage.test.supplement.second.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.test.supplement.second.adapter.SupplementPhotoDetailAdapter;

import java.util.List;

public class SupplementPhotoDetailCommodityEntity extends AbstractExpandableItem<SkuListEntity> implements MultiItemEntity {

    /**
     * id : 3305
     * goods_name : 乐事大礼包
     * goods_image : https://jplife.huang-dou.com/uploads/20200220/0826e91b79ec534298d5b29fd2c033cd.jpg
     * goods_info : 乐事大礼包405g
     * selling_points : null
     * cat_id : 73
     * sku_list : [{"sku_sn":"692474392465","attr":"405g"}]
     */

    private int id;
    private String goods_name;
    private String goods_image;
    private String goods_info;
    private String selling_points;
    private int cat_id;
    private List<CatListEntity> cat_list;

    private List<SkuListEntity> sku_list;

    public List<CatListEntity> getCat_list() {
        return cat_list;
    }

    public void setCat_list(List<CatListEntity> cat_list) {
        this.cat_list = cat_list;
    }

    public List<SkuListEntity> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<SkuListEntity> sku_list) {
        this.sku_list = sku_list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public String getSelling_points() {
        return selling_points;
    }

    public void setSelling_points(String selling_points) {
        this.selling_points = selling_points;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }


    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return SupplementPhotoDetailAdapter.ITEM_CONTEXT;
    }

    @Override
    public String toString() {
        return "SupplementPhotoDetailCommodityEntity{" +
                "id=" + id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_image='" + goods_image + '\'' +
                ", goods_info='" + goods_info + '\'' +
                ", selling_points='" + selling_points + '\'' +
                ", cat_id=" + cat_id +
                ", cat_list=" + cat_list +
                ", sku_list=" + sku_list +
                '}';
    }
}
