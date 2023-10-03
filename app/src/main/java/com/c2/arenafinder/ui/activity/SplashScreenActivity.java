package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.util.UsersUtil;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private UsersUtil usersUtil;

    private ImageView appIcon;

    private LottieAnimationView loading;

    private void initViews(){
        appIcon = findViewById(R.id.splash_logo_app);
        loading = findViewById(R.id.splash_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        usersUtil = new UsersUtil(getApplicationContext());

        // remove status bar dan navigation bar
        getWindow().getDecorView().post(() -> new Thread(() -> runOnUiThread(() -> {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.biru_gelap));
        })).start());

        new Handler().postDelayed(
                () -> {
                   if (usersUtil.isSignIn()){
                       startActivity(new Intent(this, MainActivity.class));
                       finish();
                   }else {
                       startActivity(new Intent(this, AccountActivity.class));
                       finish();
                   }
                },
                3000L
        );
    }
}