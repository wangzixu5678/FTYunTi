package com.example.ftkj.yunti_test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.activity.ElevatorSelectActivity;
import com.example.ftkj.yunti_test.activity.LoginActivity;
import com.example.ftkj.yunti_test.activity.NetTestActivity;
import com.example.ftkj.yunti_test.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssemFragment extends BaseFragment {

    @BindView(R.id.list_record)
    ListView mListRecord;
    @BindView(R.id.norecord_notify)
    RelativeLayout mNorecordNotify;
    @BindView(R.id.assemfm_assem)
    TextView mAssemfmAssem;
    @BindView(R.id.assemfm_nettest)
    TextView mAssemfmNettest;
    @BindView(R.id.assemfm_more)
    TextView mAssemfmMore;

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


    @OnClick({R.id.assemfm_assem, R.id.assemfm_nettest, R.id.assemfm_more})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.assemfm_assem:
                intent = new Intent(mActivity, ElevatorSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.assemfm_nettest:
                intent = new Intent(mActivity, NetTestActivity.class);
                startActivity(intent);
                break;
            case R.id.assemfm_more:
                showToast("暂未开放");
                break;
        }
    }
}
