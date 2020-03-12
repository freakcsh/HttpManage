package com.freak.httpmanage.test.supplement.detail.adapter.entity;

import java.util.List;

public class CommodityDetailEntity {

    /**
     * id : 630
     * goods_name : 测试
     * goods_info : 测试测试
     * cat_id : 57
     * slider_image : []
     * desc_image : []
     * cat_list : [{"id":30,"cat_name":"菜市场"},{"id":55,"cat_name":"水产海鲜"},{"id":57,"cat_name":"虾蟹贝壳"}]
     */

    private int id;
    private String goods_name;
    private String goods_info;
    private String video_url;
    private int cat_id;
    private List<String> slider_image;
    private List<String> desc_image;
    private List<CatListBean> cat_list;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
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

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public List<String> getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(List<String> slider_image) {
        this.slider_image = slider_image;
    }

    public List<String> getDesc_image() {
        return desc_image;
    }

    public void setDesc_image(List<String> desc_image) {
        this.desc_image = desc_image;
    }

    public List<CatListBean> getCat_list() {
        return cat_list;
    }

    public void setCat_list(List<CatListBean> cat_list) {
        this.cat_list = cat_list;
    }

    public static class CatListBean {
        /**
         * id : 30
         * cat_name : 菜市场
         */

        private int id;
        private String cat_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        @Override
        public String toString() {
            return "CatListBean{" +
                    "id=" + id +
                    ", cat_name='" + cat_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommodityDetailEntity{" +
                "id=" + id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_info='" + goods_info + '\'' +
                ", video_url='" + video_url + '\'' +
                ", cat_id=" + cat_id +
                ", slider_image=" + slider_image +
                ", desc_image=" + desc_image +
                ", cat_list=" + cat_list +
                '}';
    }
}
