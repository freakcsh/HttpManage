package com.freak.httpmanage.test.supplement.detail.adapter.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.SupplementPhotoDetailImageAdapter;

public class AddIconEntity implements MultiItemEntity {
    @Override
    public int getItemType() {
        return SupplementPhotoDetailImageAdapter.TYPE_CAMERA;
    }
}
