package com.example.ftkj.yunti_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ftkj.yunti_test.base.BaseFragment;


import java.util.List;


public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mList;
    private String[] mString;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> list,String...titles) {
        super(fm);
        mList = list;
        mString = titles;
    }

    @Override
    public Fragment getItem(int position) {
         return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
            return mString[position];
    }
}