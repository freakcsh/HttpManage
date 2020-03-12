package com.freak.httpmanage.test.supplement.entity;


import com.freak.httpmanage.test.supplement.second.entity.SupplementPhotoDetailCommodityEntity;

import java.util.List;

public class ClassifyDetailEntity {

    /**
     * total : 15
     * per_page : 15
     * current_page : 1
     * last_page : 1
     * data : [{"id":32,"goods_name":"山东冬枣","goods_image":"https://test.huang-dou.com/uploads/20191111/d454339109f78234f11ca18694f55075.png","goods_info":"山东冬枣","selling_points":null},{"id":33,"goods_name":"黄心猕猴桃","goods_image":"https://test.huang-dou.com/uploads/20191111/a9f291d5894c965e2795e1ef84e5ef63.png","goods_info":"黄心猕猴桃","selling_points":null},{"id":35,"goods_name":"冰糖橙","goods_image":"https://test.huang-dou.com/uploads/20191111/a5b80842294928ed13d78ec886d7c14c.png","goods_info":"冰糖橙","selling_points":null},{"id":36,"goods_name":"红心蜜柚","goods_image":"https://test.huang-dou.com/uploads/20191111/eea6c12907d8051105f7ed89053af9c7.jpg","goods_info":"红心蜜柚 1个 含有大量的维生素C、天然叶酸","selling_points":null},{"id":37,"goods_name":"新疆阿克苏冰糖心苹果","goods_image":"https://test.huang-dou.com/uploads/20191111/add5db1b623438b0c5075d4c054bfe6a.jpg","goods_info":"新疆阿克苏冰糖心苹果 500g/份 味甜汁多、含糖量高","selling_points":null},{"id":38,"goods_name":"特级香梨","goods_image":"https://test.huang-dou.com/uploads/20191111/15e0bfe776ae7a81c84a7a726e7949d9.jpg","goods_info":"特级香梨 500g/份 香味浓郁、皮薄肉细、汁多甜酥、清爽可口","selling_points":null},{"id":39,"goods_name":"白心火龙果","goods_image":"https://test.huang-dou.com/uploads/20191111/be2b29476b7c54e3972c225ee14ee544.jpg","goods_info":"白心火龙果 500g/份 果肉糖分以葡萄糖为主","selling_points":null},{"id":40,"goods_name":"巨峰葡萄","goods_image":"https://test.huang-dou.com/uploads/20191111/14ab24d5fc5de6f8b57dc5856a10a8bb.jpg","goods_info":"巨峰葡萄 500g/份 果肉较软，味甜、多汁，有草莓香味","selling_points":null},{"id":41,"goods_name":"国产蓝莓","goods_image":"https://test.huang-dou.com/uploads/20191111/03aad95220753adb2d1c1de21143b37f.jpg","goods_info":"蓝莓 125克/份 含有丰富的营养成分，尤其富含花青素","selling_points":null},{"id":43,"goods_name":"水蜜桃","goods_image":"https://test.huang-dou.com/uploads/20191208/a5e660f2718c666e88a051e8acb99a94.jpg","goods_info":"水蜜桃 500g/ 份 有美肤、清胃、润肺、祛痰等功效","selling_points":null},{"id":603,"goods_name":"山东红富士500g","goods_image":"https://test.huang-dou.com/uploads/20200214/b9f06013cdc96fe9716b02cb8f011869.jpg","goods_info":"山东红富士500g","selling_points":null},{"id":604,"goods_name":"静宁苹果500g","goods_image":"https://test.huang-dou.com/uploads/20200214/a169f123d15558ac9839bb46db70be71.png","goods_info":"静宁苹果500g","selling_points":null},{"id":605,"goods_name":"砂糖橘500g","goods_image":"https://test.huang-dou.com/uploads/20200214/c4d3c92d9611e4d6163d86d961a079e3.jpg","goods_info":"砂糖橘 约500g/份","selling_points":null},{"id":608,"goods_name":"本地香蕉500g","goods_image":"https://test.huang-dou.com/uploads/20200214/e43560c0f6e9598d2c1afdff201083f5.jpg","goods_info":"本地香蕉500g","selling_points":null},{"id":618,"goods_name":"龙眼500g","goods_image":"https://test.huang-dou.com/uploads/20200214/b40c371a473a376f493583e068e05f8b.jpg","goods_info":"龙眼500g","selling_points":null}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<SupplementPhotoDetailCommodityEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }


    public List<SupplementPhotoDetailCommodityEntity> getData() {
        return data;
    }

    public void setData(List<SupplementPhotoDetailCommodityEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassifyDetailEntity{" +
                "total=" + total +
                ", per_page=" + per_page +
                ", current_page=" + current_page +
                ", last_page=" + last_page +
                ", data=" + data +
                '}';
    }
}
