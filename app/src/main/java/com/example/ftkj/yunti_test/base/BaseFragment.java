package com.example.ftkj.yunti_test.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.mvp.GenericHelper;
import com.example.ftkj.yunti_test.mvp.presenter.MBasePresenter;

import butterknife.ButterKnife;

/**
 * Created by FTKJ on 2017/5/22.
 */

public abstract class BaseFragment<B extends MBasePresenter> extends Fragment {
    public B presenter;
    public View rootView;
    private Toast toast;
    private ProgressDialog progressDialog;
    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mTitleSub;
    protected Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        try{
//            presenter = GenericHelper.newPresenter(this);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        mActivity = getActivity();
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,rootView);
        onInit();
        onListener();
        if (presenter != null) {
            presenter.start();
        }
        return rootView;
    }

    /**
     * 添加监听
     */
    protected void onListener(){

        Log.d("test", "this is the base fragment listener ");
    }

    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void onInit(){
        Log.d("test", "this is the base fragment init ");
    }

    public View getRootView() {
        return this.rootView;
    }

    /**
     * 设置Toolbar
     */
    protected void setToolbar(String title){
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mTitle = ((TextView) rootView.findViewById(R.id.tv_title));
        if (mToolbar!=null){
            mToolbar.setTitle("");
        }

        if (mTitle != null) {
                mTitle.setText(title);
            }
            if (mToolbar != null) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            }
    }

    protected void setToolbar(String title,String subTitle){
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mTitle = ((TextView) rootView.findViewById(R.id.tv_title));
        mTitleSub =(TextView) rootView.findViewById(R.id.tv_subtitle);
        if (mToolbar!=null){
            mToolbar.setTitle("");
        }

        if (mTitleSub!=null){
            mTitleSub.setText(subTitle);

        }

        if (mTitle != null) {
            mTitle.setText(title);
        }
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }
    }

    /**
     * 显示吐司
     *
     * @param msg
     */
    public void showTs(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 设置SnackBar
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (null != msg && !msg.equals("")) {
            Snackbar.make(getActivity().getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示菊花
     * 使用默认提示
     */
    public void showDialog() {
        showDialog(getString(R.string.tips_loading));
    }

    /**
     * 显示菊花
     *
     * @param msg
     */
    public void showDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    /**
     * 隐藏掉菊花
     */
    public void dissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

//        presenter.end();
    }
    public <T extends View> T getView(int layoutId){
        return ((T) rootView.findViewById(layoutId));
    }


}
