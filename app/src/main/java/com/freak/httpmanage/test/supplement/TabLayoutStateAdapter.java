package com.freak.httpmanage.test.supplement;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Freak on 2019/9/13.
 */
public class TabLayoutStateAdapter extends FragmentPagerAdapter {
    private List<String> list;
    private List<Fragment> mFragments;

    public TabLayoutStateAdapter(FragmentManager fm, List<String> list, List<Fragment> mFragments) {
        super(fm);
        this.list = list;
        this.mFragments = mFragments;
    }


//    @SuppressLint("WrongConstant")
//    public TabLayoutStateAdapter(@NonNull FragmentManager fm, List<String> list, List<Fragment> fragments) {
//        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        this.list = list;
//        mFragments = fragments;
//    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).toUpperCase();
    }
}
