package com.example.ftkj.yunti_test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class ElevatorSelectActivity extends BaseActivity {
    @BindView(R.id.spin_provice)
    Spinner mSpinProvice;
    @BindView(R.id.spin_city)
    Spinner mSpinCity;
    @BindView(R.id.spin_area)
    Spinner mSpinArea;
    private ArrayAdapter<String> p_adapter, c_adapter, j_adapter;
    private String[] province = {"北京", "上海", "山东", "江苏"};
    private String[][] city = {{"海淀区", "昌平区", "朝阳区"}, {"黄浦区", "浦东新区"}, {"济南", "青岛", "聊城"}, {"苏州", "南京", "常州"}};
    private String[][] jingdian = {{"故宫", "颐和园", "圆明园"}, {"外滩", "豫园", "陆家嘴", "南京步行街"}, {"栈桥", "大明湖", "趵突泉"}, {"苏州园林", "南京长江大桥", "江南小镇"}};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_select;
    }

    @Override
    protected void initView() {
        setToolbar("电梯资料选择");
    }

    @Override
    protected void initData() {
        p_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, province);
        c_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        j_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        c_adapter.addAll(city[0]);
        p_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        j_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        j_adapter.addAll(jingdian[0]);
        //将适配器和Spinner链接
        mSpinProvice.setAdapter(p_adapter);
        mSpinCity.setAdapter(c_adapter);
        mSpinArea.setAdapter(j_adapter);
    }

    @Override
    protected void initListener() {
        mSpinProvice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c_adapter.clear();
                j_adapter.clear();
                //添加数组资源,跟组对应的位置
                c_adapter.addAll(city[position]);
                j_adapter.addAll(jingdian[position]);
                //Spinner默认选择重置为第一个
                mSpinCity.setSelection(0);
                mSpinArea.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.elevator_ac_next_btn)
    public void onViewClicked() {
        go(ScannerActivity.class);

    }
}
