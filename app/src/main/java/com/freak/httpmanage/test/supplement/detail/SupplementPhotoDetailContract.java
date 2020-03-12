package com.freak.httpmanage.test.supplement.detail;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.bean.UpLoadEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.entity.CommodityDetailEntity;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;

public class SupplementPhotoDetailContract {
    interface View extends BaseView {
        void goodsDetailSuccess(CommodityDetailEntity commodityDetailEntity);

        void upLoadSuccess(UpLoadEntity upLoadEntity);



        void updateGoodsSuccess();

        void onProgress(int progress);

        void onUpLoadSuccess(ResponseBody responseBody);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * @param sign 扫码的条码号
         * @param id   商品id，条码号和商品id必须要有一个
         */
        void goodsDetail(String sign, String id);

        void updateGoods(String goods_id, List<String> slider_image, String video_url);

        void upLoad(File file);


    }
}
