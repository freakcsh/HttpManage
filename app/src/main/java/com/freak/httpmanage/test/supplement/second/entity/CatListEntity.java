package com.freak.httpmanage.test.supplement.second.entity;

public class CatListEntity {
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
