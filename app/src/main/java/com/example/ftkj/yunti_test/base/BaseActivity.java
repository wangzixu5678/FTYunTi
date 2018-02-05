package com.example.ftkj.yunti_test.base;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.mvp.GenericHelper;
import com.example.ftkj.yunti_test.mvp.presenter.MBasePresenter;
import com.githang.statusbar.StatusBarCompat;

import butterknife.ButterKnife;
import io.reactivex.Observable;


/**
 * Created by FTKJ on 2017/5/22.
 */

public abstract class BaseActivity<T extends MBasePresenter> extends AppCompatActivity {
    protected static String TAG_LOG = null;
    public T presenter;
    private ProgressDialog progressDialog;
    protected boolean hasStatusBar = true;
    private TextView mTitle;
    private Toolbar mToolbar;
    private ImageView mBackview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        /**
         * Butterknife绑定布局
         */
        ButterKnife.bind(this);
        /**
         * 强制设置为纵向
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        initView();
        initListener();
        /**
         * 沉浸式状态栏
         */
        int whiteColor = Color.rgb(24, 119, 236);
        if (hasStatusBar) {
            StatusBarCompat.setStatusBarColor(this, whiteColor, true);
        }


//        /**
//         * 初始化presenter
//         */
//        try {
//            presenter = GenericHelper.newPresenter(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected void setHasStatusBar(boolean isHas){
        hasStatusBar = isHas;
    }

    /**
     * 设置Toolbar
     *
     * @param title
     */
    protected void setToolbar(String title) {
        try {
            mTitle = (TextView) findViewById(R.id.tv_title);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mBackview = (ImageView) findViewById(R.id.back_view);
            if (mTitle != null) {
                mTitle.setText(title);
            }
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayShowTitleEnabled(false);
            }
            if (mBackview != null) {
               mBackview.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       finish();
                   }
               });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void go(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 设置吐司
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (null != msg && !msg.equals("")) {
            Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.end();
        }
        super.onDestroy();
    }

    /**
     * 显示菊花
     */
    public void showDialog(String msg) {
        progressDialog = new ProgressDialog(this);
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

    protected <T extends View> T getView(int layoutId) {
        return (T) findViewById(layoutId);
    }


}
