package com.example.ftkj.yunti_test.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.adapters.AutoCompeleteAdapter;
import com.example.ftkj.yunti_test.adapters.FragmentAdapter;
import com.example.ftkj.yunti_test.base.BaseFragment;

import com.example.ftkj.yunti_test.utils.DensityUtil;
import com.example.ftkj.yunti_test.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkFragment extends BaseFragment {


    @BindView(R.id.fragment_work_tablayout)
    TabLayout mFragmentWorkTablayout;
    @BindView(R.id.fragment_work_pager)
    ViewPager mFragmentWorkPager;
    @BindView(R.id.fragment_autotextview)
    AutoCompleteTextView mFragmentAutotextview;
    private ArrayList<BaseFragment> fragments;
    private FragmentAdapter mAdapter;
    private final String[] TITLE = {"未完成", "完成"};
    private SharedPreferencesUtil mSpUtil;
    private static final String SEARCHCONTENT = "srhHsy";
    private List<String> mSearchList;
    private AutoCompeleteAdapter mAutoapter;
    private StringBuilder mBuilder;
    private final static int MAXCACHEHSY=20;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void onInit() {
        /**
         * 设置Fragment
         */
        setFragment();
        /**
         * 初始化AutoTextView
         */
        mSpUtil = SharedPreferencesUtil.getInstance();
        mSearchList = new ArrayList<String>();
        //从SharePherence获取字符串存储到List中
        getString2List();
        mAutoapter = new AutoCompeleteAdapter(getContext(), mSearchList);
        mFragmentAutotextview.setThreshold(1);
        mFragmentAutotextview.setDropDownWidth(DensityUtil.getScreenSizeWidth(getContext()));
        mFragmentAutotextview.setDropDownHorizontalOffset(0);
        mFragmentAutotextview.setAdapter(mAutoapter);
    }

    /**
     * 从sharePherence获取字符串以“，”为分割搜索历史
     */
    private void getString2List() {
        String searchStr = mSpUtil.getString(SEARCHCONTENT);
        Log.d("AAA", "getString2List: "+searchStr);
        if (hasLenth(searchStr)) {
            mSearchList.clear();
            String[] sarchArray = searchStr.split(",");
            for (int i = sarchArray.length; i>0 ; i--) {
                mSearchList.add(sarchArray[i-1]);
            }
        }
    }

    private void setFragment() {
        fragments = new ArrayList<>();
        mAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, TITLE);
        mFragmentWorkPager.setAdapter(mAdapter);
        FishedFragment fishedFragment = new FishedFragment();
        UnfishedFragment unfishedFragment = new UnfishedFragment();
        fragments.add(unfishedFragment);
        fragments.add(fishedFragment);
        mFragmentWorkTablayout.setupWithViewPager(mFragmentWorkPager);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 软键盘完成键监听
     */
    @Override
    protected void onListener() {
        mFragmentAutotextview.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showToast(mFragmentAutotextview.getText().toString().trim());
                    saveHistory();
                }
                return false;
            }
        });
    }

    @OnClick({R.id.fragment_gosearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_gosearch:
                saveHistory();
                break;
        }
    }

    /**
     * 将搜索历史存储到Sharepherence
     */
    private void saveHistory() {
        String searchStr = mSpUtil.getString(SEARCHCONTENT);
        String editString = mFragmentAutotextview.getText().toString().trim();
        mBuilder = new StringBuilder();
        if (hasLenth(searchStr)) {
            mBuilder.append(searchStr);
            mBuilder.append(",").append(editString);
        } else {
            mBuilder.append(editString);
        }
        /**
         * 去除多余数据在储存
         */
        StringBuilder resultBuild = deleteSome(mBuilder);
        mSpUtil.putString(SEARCHCONTENT, resultBuild.toString());
        getString2List();
        mAutoapter.notifyDataSetChanged();
    }

    public boolean hasLenth(String str) {
        return (str != null && !"".equals(str));
    }

    /**
     * 去除多余数据在储存
     */
    public StringBuilder deleteSome(StringBuilder stringBuilder) {
        String str = stringBuilder.toString();
        LinkedList<String> linkedList = new LinkedList<>();
        String[] split = str.split(",");
        for (int i = 0; i< split.length; i++) {
            if (!linkedList.contains(split[i])) {
                if (i > MAXCACHEHSY-1) {
                    linkedList.poll();
                    linkedList.offer(split[i]);
                } else {
                    linkedList.offer(split[i]);
                }
            }

        }


        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < linkedList.size(); i++) {
            if (i == 0) {
                builder.append(linkedList.get(i));
            } else {
                builder.append("," + linkedList.get(i));
            }
        }

        return builder;
    }

}
