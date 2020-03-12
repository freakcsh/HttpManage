package com.freak.httpmanage.test.supplement;

import com.freak.httphelper.BasePresenter;
import com.freak.httpmanage.app.BaseView;
import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;

import java.util.List;

public class SupplementPhotoContract {
    interface View extends BaseView {
        void getTopListSuccess(List<ClassifyEntity> classifyEntityList);

    }
    interface Presenter extends BasePresenter<View> {
        void getTopList(String id);

    }
}
