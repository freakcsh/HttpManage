package com.freak.httpmanage.test.supplement.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.freak.httpmanage.test.supplement.entity.ClassifyEntity;

import java.util.List;

/**
 * Created by Freak on 2019/9/13.
 */
public class VerticalTabLayoutAdapter extends FragmentStateAdapter {
    private List<ClassifyEntity> list;
    private List<Fragment> fragmentList;


    public VerticalTabLayoutAdapter(FragmentManager fm, List<ClassifyEntity> list, Fragment fragment) {
        super(fm, fragment.getLifecycle());
        this.list = list;
    }



    public VerticalTabLayoutAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
