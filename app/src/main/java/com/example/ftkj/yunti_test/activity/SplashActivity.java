package com.example.ftkj.yunti_test.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ftkj.yunti_test.MainActivity;
import com.example.ftkj.yunti_test.R;
import com.githang.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.welcom_image1)
    ImageView mWelcomImage1;
    @BindView(R.id.welcom_image2)
    ImageView mWelcomImage2;
    @BindView(R.id.welcom_image3)
    ImageView mWelcomImage3;
    @BindView(R.id.welcom_image4)
    ImageView mWelcomImage4;
    @BindView(R.id.welcom_anim)
    RelativeLayout mWelcomAnim;
    @BindView(R.id.welcom_add)
    TextView mWelcomAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        ButterKnife.bind(this);
        int whiteColor = Color.rgb(24, 119, 236);

        StatusBarCompat.setStatusBarColor(this, whiteColor, true);
        startWelcomAnim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void startWelcomAnim() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mWelcomImage1, "alpha", 0, 1);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mWelcomImage2, "alpha", 0, 1);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mWelcomImage3, "alpha", 0, 1);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mWelcomImage4, "alpha", 0, 1);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(mWelcomAdd, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim1);
        animatorSet.play(anim2).after(anim1);
        animatorSet.play(anim3).after(anim2);
        animatorSet.play(anim4).after(anim3);
        animatorSet.play(anim5).after(anim4);
        animatorSet.setDuration(800);
        animatorSet.start();
    }
}
