package com.freak.httpmanage.test.supplement.detail.adapter.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.freak.httpmanage.test.supplement.detail.adapter.SupplementPhotoDetailImageAdapter;

public class VideoEntity implements MultiItemEntity {
    private String url;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getItemType() {
        return SupplementPhotoDetailImageAdapter.TYPE_VIDEO;
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
