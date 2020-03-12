package com.freak.httpmanage.test.supplement.adapter;


import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;

import java.util.List;

import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by Freak on 2019/9/19.
 */
public class ClassifyMenuTabAdapter extends SimpleTabAdapter {
    List<ClassifyEntity> menus;
    public ClassifyMenuTabAdapter(List<ClassifyEntity> menus) {
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }
    @Override
    public TabView.TabBadge getBadge(int position) {
        return null;
    }
    @Override
    public TabView.TabIcon getIcon(int position) {
        return  null;
    }
    @Override
    public TabView.TabTitle getTitle(int position) {
        ClassifyEntity classify = menus.get(position);
        //自定义Tab选择器的字体大小颜色
        return new QTabView.TabTitle.Builder()
                .setTextColor(0xFF353535,0xFF666666)
                .setTextSize(14)
                .setContent(classify.getCat_name())
                .build();
    }
    @Override
    public int getBackground(int position) {
        return -1;
    }
}
