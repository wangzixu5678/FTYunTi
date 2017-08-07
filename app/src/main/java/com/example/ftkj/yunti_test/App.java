package com.example.ftkj.yunti_test;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.ftkj.yunti_test.utils.SharedPreferencesUtil;

/**
 * Created by FTKJ on 2017/7/7.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil.init(getApplicationContext(),"searchHsy",MODE_PRIVATE);
    }
}
