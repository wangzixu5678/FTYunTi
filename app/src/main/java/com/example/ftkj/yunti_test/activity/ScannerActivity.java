package com.example.ftkj.yunti_test.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.base.BaseActivity;
import com.google.zxing.Result;

import butterknife.BindView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.scanner_contain)
    FrameLayout mScannerContain;
    private ZXingScannerView mScannerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scanner;
    }


    @Override
    protected void initView() {
        setToolbar("智能屏二维码扫描");
        setHasStatusBar(false);
        mScannerView = new ZXingScannerView(this);
        mScannerContain.addView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void handleResult(Result result) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override

            public void run() {

                mScannerView.resumeCameraPreview(ScannerActivity.this);

            }

        }, 1000);
    }
}
