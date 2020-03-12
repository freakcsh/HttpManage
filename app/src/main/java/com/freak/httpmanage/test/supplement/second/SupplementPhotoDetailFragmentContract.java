package com.freak.httpmanage.test.supplement.second;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.test.supplement.entity.ClassifyDetailEntity;

public class SupplementPhotoDetailFragmentContract {
    interface View extends BaseView {
        void getGoodsSuccess(ClassifyDetailEntity classifyDetailEntity);
    }
    interface Presenter extends BasePresenter<View> {
        void getGoods(String id, String page);
    }
}
