package com.freak.httpmanage.test.supplement.entity;

import java.io.Serializable;

/**
 * Created by Freak on 2019/9/19.
 */
public class ClassifyEntity implements Serializable {
    private String id;
    private String cat_name;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ClassifyEntity{" +
                "id='" + id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
