package com.example.ftkj.yunti_test.base;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ftkj.yunti_test.mvp.GenericHelper;
import com.example.ftkj.yunti_test.mvp.presenter.MBasePresenter;
import com.githang.statusbar.StatusBarCompat;

import butterknife.ButterKnife;


/**
 * Created by FTKJ on 2017/5/22.
 */

public abstract class BaseActivity<T extends MBasePresenter> extends AppCompatActivity {
    protected static String TAG_LOG = null;
    public Toolbar mToolbar;
    public TextView mTitle;
    public T presenter;
    private int netMobile;
    private ProgressDialog progressDialog;
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
         * 沉浸式状态栏
         */
        int whiteColor = Color.rgb(24, 119, 236);
        StatusBarCompat.setStatusBarColor(this, whiteColor, true);
        initData();
        onListener();
        /**
         * 初始化presenter
         */
        try {
            presenter = GenericHelper.newPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    /**
     * 这里面写监听事件
     */
    protected void onListener() {
    }

//    /**
//     * 设置Toolbar
//     *
//     * @param title
//     */
//    protected void setToolbar(String title) {
//        try {
//            mTitle = (TextView) findViewById(R.id.tv_title);
//            mToolbar = (Toolbar) findViewById(toolbar);
//            mBackview = (ImageView) findViewById(R.id.back_view);
//            if (mTitle != null) {
//                mTitle.setText(title);
//            }
//            if (mToolbar != null) {
//                mToolbar.setTitle("");
//                setSupportActionBar(mToolbar);
//            }
//            if (mBackview != null) {
//                mBackview.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Observable.create(new Observable.OnSubscribe<Object>() {
//                            @Override
//                            public void call(Subscriber<? super Object> subscriber) {
//                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(mBackview, "rotation", 0.0F, 1080.0F);
//                                animator1.setDuration(1000)
//                                        .start();
//                                subscriber.onNext(1);
//                            }
//                        }).subscribeOn(AndroidSchedulers.mainThread())
//                                .observeOn(Schedulers.newThread())
//                                .doOnNext(new Action1<Object>() {
//                                    @Override
//                                    public void call(Object o) {
//                                        try {
//                                            Thread.sleep(1000);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }).observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Action1<Object>() {
//                                    @Override
//                                    public void call(Object o) {
//                                        finish();
//                                    }
//                                });
//
//
//                    }
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

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


    /**
     * 设置跳转
     *
     * @param clazz
     */

    protected void go(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void Go(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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


//    /**
//     * 初始化时判断有没有网络
//     *
//     * @return
//     */
//    public boolean inspectNet() {
//        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
//
//        return isNetConnect();
//    }

//    /**
//     * 判断有无网络 。
//     *
//     * @return true 有网, false 没有网络.
//     */
//    public boolean isNetConnect() {
//        if (netMobile == NetUtil.NETWORK_WIFI) {
//            return true;
//        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
//            return true;
//        } else if (netMobile == NetUtil.NETWORK_NONE) {
//            return false;
//
//        }
//        return false;
//    }
}
