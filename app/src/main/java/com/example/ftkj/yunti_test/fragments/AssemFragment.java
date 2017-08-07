package com.example.ftkj.yunti_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssemFragment extends BaseFragment {

    @BindView(R.id.list_record)
    ListView mListRecord;
    @BindView(R.id.norecord_notify)
    RelativeLayout mNorecordNotify;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_assem;
    }

    @Override
    protected void onInit() {
        setToolbar("云梯-配置工具");
        //TODO 判断listview数据是否为空 如果为空那么显示norecordNotify
        mNorecordNotify.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onListener() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
