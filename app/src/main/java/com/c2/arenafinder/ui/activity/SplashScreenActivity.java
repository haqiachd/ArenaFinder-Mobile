package com.c2.arenafinder.ui.activity;

import static com.c2.arenafinder.data.local.DataShared.KEY.IS_FIRST_TIME_INSTALL;
import static com.c2.arenafinder.data.local.DataShared.KEY.APP_LANGUAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.LanguagesUtil;
import com.c2.arenafinder.util.UsersUtil;
import com.google.firebase.FirebaseApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private int currentPermissionIndex = 0;

    private final String[] allPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private UsersUtil usersUtil;

    private DataShared shared;

    private LanguagesUtil languagesUtil;

    private LottieAnimationView loading;

    private void initViews(){
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

        // initialize firebaes
        FirebaseApp.initializeApp(this.getApplicationContext());

        // initialize class
        usersUtil = new UsersUtil(this);
        shared = new DataShared(this);
        languagesUtil = new LanguagesUtil(this, shared);

        if (!shared.contains(IS_FIRST_TIME_INSTALL)){
            shared.setData(IS_FIRST_TIME_INSTALL, "NO");
            languagesUtil.setActivatedLanguage(LanguagesUtil.INDONESIAN);
        }

        languagesUtil.changeLanguage();

        // cek koneksi
        new Handler().postDelayed(this::cekKoneksi, 900L);
    }

    private void cekKoneksi(){

        RetrofitClient.getInstance().cekKoneksi().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ArenaFinderResponse> call, Response<ArenaFinderResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                    // jika aplikasi pertama kali di install maka akan menampilkan request permission
                    if (!shared.contains(IS_FIRST_TIME_INSTALL)){
                        loading.cancelAnimation();
                        requestNextPermission();
                    }else {
                        createUniverse();
                    }
                } else {
                    ArenaFinder.playVibrator(getApplicationContext(), ArenaFinder.VIBRATOR_LONG);
//                    startActivity(new Intent(SplashScreenActivity.this, EmptyActivity.class)
//                            .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_NOT_FOUND)
//                    );
//                    finish();

                    // temp
                    Toast.makeText(SplashScreenActivity.this, "Server tidak ditemukan!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ArenaFinderResponse> call, Throwable t) {
                t.printStackTrace();
                ArenaFinder.playVibrator(getApplicationContext(), ArenaFinder.VIBRATOR_LONG);
//                startActivity(new Intent(SplashScreenActivity.this, EmptyActivity.class)
//                        .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_NOT_FOUND)
//                );
//                finish();

                // temp
                Toast.makeText(SplashScreenActivity.this, "Server tidak ditemukan!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
        });

    }

    private void createUniverse(){
        if (usersUtil.isSignIn()) {
            // Jika pengguna sudah masuk, buka MainActivity
            LogApp.info(this, LogTag.LIFEFCYLE, "Membuka MainActivity");
//            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

        } else {
            // Jika pengguna belum masuk, buka WelcomeActivity
            LogApp.info(this, LogTag.LIFEFCYLE, "Membuka WelcomeActivity");
            startActivity(
                    new Intent(SplashScreenActivity.this, EmptyActivity.class)
                            .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.WELCOME)
            );
        }
        finish();
    }

    private void requestNextPermission() {
        if (currentPermissionIndex < allPermission.length) {
            String permission = allPermission[currentPermissionIndex];
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                currentPermissionIndex++;
                requestNextPermission();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, getPermissionRequestCode());
            }
        } else {
            createUniverse();
        }
    }

    private int getPermissionRequestCode() {
        switch (allPermission[currentPermissionIndex]) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return ArenaFinder.PERMISSION_CURRENT_POSITION;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return ArenaFinder.PERMISSION_STORAGE;
            case Manifest.permission.CAMERA:
                return ArenaFinder.PERMISSION_CAMERA;
            default:
                return -1;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getPermissionRequestCode()) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentPermissionIndex++;
                requestNextPermission();
            } else {
                if (currentPermissionIndex < permissions.length) {
                    currentPermissionIndex++;
                    requestNextPermission();
                } else {
                    createUniverse();
                }
            }
        }
    }

}