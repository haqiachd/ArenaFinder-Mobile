package com.c2.arenafinder.ui.activity;

import static com.c2.arenafinder.data.local.DataShared.KEY.ACC_LEVEL;
import static com.c2.arenafinder.data.local.DataShared.KEY.IS_FIRST_TIME_INSTALL;
import static com.c2.arenafinder.data.local.DataShared.KEY.SAVED_DEVICE_TOKEN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.ArenaFinderModel;
import com.c2.arenafinder.data.repository.ArenaFinderRepository;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.di.ArenaFinderViewModelFactory;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.LanguagesUtil;
import com.c2.arenafinder.util.UsersUtil;
import com.c2.arenafinder.viewmodel.ArenaFinderViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private int currentPermissionIndex = 0;

    private final String[] allPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    private UsersUtil usersUtil;

    private DataShared shared;

    private LottieAnimationView loading;

    private ArenaFinderViewModel viewModel;

    private void initViews() {
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

        // create view model
        viewModel = new ViewModelProvider(
                this,
                new ArenaFinderViewModelFactory(new ArenaFinderRepository())
        ).get(ArenaFinderViewModel.class);

        // initialize firebase
        FirebaseApp.initializeApp(this.getApplicationContext());

        // initialize class
        usersUtil = new UsersUtil(this);
        shared = new DataShared(this);
        LanguagesUtil languagesUtil = new LanguagesUtil(this, shared);

        // handling app status
        try {
            // first time installed
            if (!shared.contains(IS_FIRST_TIME_INSTALL)) {
                LogApp.error(this, LogTag.LIFEFCYLE, "FIRST TIME INSTALL");
                shared.resetDefaultValue();
                languagesUtil.setActivatedLanguage(LanguagesUtil.INDONESIAN);
                languagesUtil.changeLanguage(LanguagesUtil.INDONESIAN);
                callObserver();
            } else {
                LogApp.error(this, LogTag.LIFEFCYLE, "HAS BEEN INSTALLED");
                callObserver();
            }
        } catch (Throwable ex) {
            // when error status
            ex.printStackTrace();
            shared.resetDefaultValue();
            LogApp.error(this, LogTag.LIFEFCYLE, "FIRST TIME INSTALL WITH ERROR");
            languagesUtil.setActivatedLanguage(LanguagesUtil.INDONESIAN);
            languagesUtil.changeLanguage(LanguagesUtil.INDONESIAN);
            callObserver();
        }

        observer();
    }

    private void callObserver() {
        new Handler().postDelayed(() -> viewModel.doCheckKoneksi(), 1000L);
    }

    private void observer() {

        viewModel.cekKoneksi().observe(this, dataState -> {
            if (dataState instanceof RetrofitState.Loading) {
                LogApp.info(this, LogTag.RETROFIT_ON_LOADING, "On SplashScreen Loading");
            } else if (dataState instanceof RetrofitState.Error) {
                LogApp.info(this, LogTag.RETROFIT_ON_LOADING, "On SplashScreen Failure");

                // get error message and handler the problem
                String message = ((RetrofitState.Error) dataState).getMessage();

                // handler server not found
                if (message.contains("server not found")) {
                    // membuka fragment server not found
                    ArenaFinder.VibratorToast(this, R.string.err_server_not_found, Toast.LENGTH_LONG, ArenaFinder.VIBRATOR_LONG);
                    startActivity(new Intent(SplashScreenActivity.this, EmptyActivity.class)
                            .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_NOT_FOUND)
                    );
                    finish();
                }
                // handler timeout
                else if (message.contains("timeout")) {
                    ArenaFinder.playVibrator(this, ArenaFinder.VIBRATOR_SHORT);
                    loading.cancelAnimation();
                    requestNextPermission();
                }
                // unexpected error
                else {
                    LogApp.error(this, LogTag.SPLASH, ((RetrofitState.Error) dataState).getMessage());
                    ArenaFinder.VibratorToast(this, message, Toast.LENGTH_LONG, ArenaFinder.VIBRATOR_LONG);
                }
            } else if (dataState instanceof RetrofitState.Success) {
                LogApp.info(this, LogTag.RETROFIT_ON_LOADING, "On SplashScreen Response");
                ArenaFinderModel model = ((RetrofitState.Success<ArenaFinderResponse>) dataState).getData().getData();
                String userLevel = shared.getData(ACC_LEVEL);

                LogApp.info(this, LogTag.SPLASH, "Server Status : " + model.getServerStatus());
                LogApp.info(this, LogTag.SPLASH, "Have Update : " + model.getHaveUpdate());
                LogApp.info(this, LogTag.SPLASH, "Min Version Code : " + model.getMinVersionCode());
                LogApp.info(this, LogTag.SPLASH, "New Version Name : " + model.getNewVersionName());
                LogApp.info(this, LogTag.SPLASH, "Update Link : " + model.getUpdateLink());
                LogApp.info(this, LogTag.SPLASH, "Desc Update : " + model.getDescUpdate());

                // check status server
                if (model.getServerStatus() || userLevel.equals("SUPER ADMIN")) {

                    // show notif when on develop mode
                    if (!model.getServerStatus() && userLevel.equals("SUPER ADMIN")) {
                        ArenaFinder.VibratorToast(this, "You are logged in in development mode", Toast.LENGTH_LONG, ArenaFinder.VIBRATOR_SHORT);
                    }

                    // handler update app
                    try {
                        LogApp.info(this, LogTag.SPLASH, "Check Update");
                        // get version code
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        int versionCode = packageInfo.versionCode;

                        // check update
                        if (model.getHaveUpdate()) {
                            // check whether the user needs to update
                            if (model.getMinVersionCode() > versionCode) {
                                LogApp.info(this, LogTag.SPLASH, "Update Available");
                                loading.cancelAnimation();

                                // create update dialog
                                var updateDialog = new AlertDialog.Builder(this)
                                        .setTitle(R.string.dia_title_update)
                                        .setMessage(getString(R.string.dia_msg_update, model.getDescUpdate()))
                                        .setPositiveButton(R.string.btn_dia_update, (dialog, which) -> {
                                            LogApp.info(this, LogTag.LIFEFCYLE, "Update App");
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUpdateLink())));
                                            // open app
//                                            requestNextPermission();
                                        });

                                // add skip update button when update can be skipped
                                if (!model.getForceUpdate()) {
                                    updateDialog.setNegativeButton(R.string.btn_dia_skip, (dialog, which) -> {
                                        // open app
                                        LogApp.info(this, LogTag.LIFEFCYLE, "Skip Update");
                                        requestNextPermission();
                                    });
                                }

                                // show update dialog
                                updateDialog.setCancelable(false).create().show();

                            }else {
                                LogApp.info(this, LogTag.SPLASH, "Create Universe");
                                requestNextPermission();
                            }
                        } else {
                            // open app
                            LogApp.info(this, LogTag.SPLASH, "Create Universe");
                            requestNextPermission();
                        }

                    } catch (Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        // skip update
                        LogApp.info(this, LogTag.SPLASH, "Create Universe");
                        loading.cancelAnimation();
                        requestNextPermission();
                    }

                } else {
                    // handler when server not active
                    startActivity(
                            new Intent(this, EmptyActivity.class)
                                    .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.SERVER_OFF)
                    );
                }
            }
        });
    }

    private void createUniverse() {

        // jika pengguna sudah login
        if (usersUtil.isSignIn()) {
            LogApp.info(this, LogTag.LIFEFCYLE, "Membuka MainActivity");
            try {
                // get firebase token
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        // cek token sukses didapatkan atau tidak
                        if (task.isSuccessful() && task.getResult() != null) {
                            String token = task.getResult();
                            LogApp.info(this, LogTag.FIREBASE_MESSAGING_SERVICES, "Token : " + token);

                            // update token jika expired
                            if (!token.equals(shared.getData(SAVED_DEVICE_TOKEN))) {
                                shared.setData(SAVED_DEVICE_TOKEN, token);
                                LogApp.info(this, LogTag.FIREBASE_MESSAGING_SERVICES, "updating device token");
                            }

                            // open main activity
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.anim_bottom_to_top, R.anim.anim_top_to_bottom);
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                LogApp.error(this, LogTag.FIREBASE_MESSAGING_SERVICES, "error : " + ex.getMessage());
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.anim_bottom_to_top, R.anim.anim_top_to_bottom);
            }
        } else {
            // Jika pengguna belum login, Membuka WelcomeActiity
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
            LogApp.info(this, LogTag.SPLASH, "permission");
            String permission = allPermission[currentPermissionIndex];
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                currentPermissionIndex++;
                requestNextPermission();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, getPermissionRequestCode());
            }
        } else {
            LogApp.info(this, LogTag.SPLASH, "create");
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
            case Manifest.permission.READ_PHONE_STATE:
                return ArenaFinder.PERMISSION_PHONE;
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