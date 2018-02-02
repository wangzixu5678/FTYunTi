package com.example.ftkj.yunti_test;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftkj.yunti_test.adapters.FragmentAdapter;
import com.example.ftkj.yunti_test.base.BaseActivity;
import com.example.ftkj.yunti_test.base.BaseFragment;
import com.example.ftkj.yunti_test.fragments.AssemFragment;
import com.example.ftkj.yunti_test.fragments.MineFragment;
import com.example.ftkj.yunti_test.fragments.WorkFragment;


import java.util.ArrayList;

import butterknife.BindView;



public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.indicator)
    TabLayout mIndicator;
    private FragmentAdapter mAdapter;
    private  final int[] ICONS = new int[] {
            R.drawable.perm_group_assem,
            R.drawable.perm_group_work,
            R.drawable.perm_group_mine,
    };
    long temple=0;
    private final String[] TITLE = new String[]{
            "装配","工单","我的"
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        /**
         * 添加Fragment
         */
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        MineFragment mineFragment = new MineFragment();
        AssemFragment assemFragment = new AssemFragment();
        WorkFragment mWorkFragment = new WorkFragment();
        fragments.add(assemFragment);
        fragments.add(mWorkFragment);
        fragments.add(mineFragment);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments);
        mPager.setAdapter(mAdapter);
        TabLayout.Tab newTab;
        for (int i = 0; i < fragments.size(); i++) {
            newTab = mIndicator.newTab();
//            newTab.setIcon(ICONS[i]);
//            newTab.setText(TITLE[i]);
            initNewTab(newTab, i);
            mIndicator.addTab(newTab);
        }
    }

    private void initNewTab(TabLayout.Tab newTab, int i) {
        View view = View.inflate(this, R.layout.tab_customview, null);
        ImageView tab_icon = (ImageView)view.findViewById(R.id.tab_icon);
        TextView tab_text = (TextView)view.findViewById(R.id.tab_text);
        tab_icon.setImageResource(ICONS[i]);
        tab_text.setText(TITLE[i]);
        newTab.setCustomView(view);
    }

    @Override
    protected void onListener() {
        mIndicator.setOnTabSelectedListener(this);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mIndicator));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        long millis = System.currentTimeMillis();
        if (millis-temple<2000){
            finish();
        }else {
            temple = millis;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }




}
