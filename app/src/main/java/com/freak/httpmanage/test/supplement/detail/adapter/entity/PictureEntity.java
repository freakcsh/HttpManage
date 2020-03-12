package com.freak.httpmanage.test.supplement.detail.adapter.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.SupplementPhotoDetailImageAdapter;

public class PictureEntity implements MultiItemEntity {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getItemType() {
        return SupplementPhotoDetailImageAdapter.TYPE_PICTURE;
    }

    @Override
    public String toString() {
        return "PictureEntity{" +
                "url='" + url + '\'' +
                '}';
    }
}
