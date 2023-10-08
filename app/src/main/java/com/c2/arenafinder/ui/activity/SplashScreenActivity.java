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
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private UsersUtil usersUtil;

    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private ImageView appIcon;

    private LottieAnimationView loading;

    private void initViews(){
        appIcon = findViewById(R.id.splash_logo_app);
        loading = findViewById(R.id.splash_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar dan navigation bar
        getWindow().getDecorView().post(() -> new Thread(() -> runOnUiThread(() -> {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.biru_gelap));
        })).start());
        setContentView(R.layout.activity_splash_screen);
        initViews();
        usersUtil = new UsersUtil(getApplicationContext());

        new Handler().postDelayed(this::cekKoneksi, 2000L);

    }

    private void cekKoneksi(){

        RetrofitClient.getInstance().cekKoneksi().enqueue(new Callback<ArenaFinderResponse>() {
            @Override
            public void onResponse(Call<ArenaFinderResponse> call, Response<ArenaFinderResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){
                    if (usersUtil.isSignIn()) {
                        // Jika pengguna sudah masuk, buka MainActivity
                        LogApp.info(this, LogTag.LIFEFCYLE, "Membuka MainActivity");
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    } else {
                        // Jika pengguna belum masuk, buka WelcomeActivity
                        LogApp.info(this, LogTag.LIFEFCYLE, "Membuka WelcomeActivity");
                        startActivity(
                                new Intent(SplashScreenActivity.this, EmptyActivity.class)
                                        .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.WELCOME)
                        );
                    }

                    // Selesai, tutup SplashScreen saat ini
                    finish();
                }else{
                    ArenaFinder.playVibrator(getApplicationContext(), ArenaFinder.VIBRATOR_LONG);
                    startActivity(new Intent(SplashScreenActivity.this, EmptyActivity.class)
                            .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_NOT_FOUND)
                    );
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ArenaFinderResponse> call, Throwable t) {
                t.printStackTrace();
                ArenaFinder.playVibrator(getApplicationContext(), ArenaFinder.VIBRATOR_LONG);
                startActivity(new Intent(SplashScreenActivity.this, EmptyActivity.class)
                        .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_NOT_FOUND)
                );
                finish();
            }
        });

    }

}