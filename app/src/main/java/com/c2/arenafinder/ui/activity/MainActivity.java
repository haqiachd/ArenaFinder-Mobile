package com.c2.arenafinder.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.main.AktivitasFragment;
import com.c2.arenafinder.ui.fragment.main.HomeFragment;
import com.c2.arenafinder.ui.fragment.main.ProfileFragment;
import com.c2.arenafinder.ui.fragment.main.ReferensiFragment;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 2;

    @SuppressLint("StaticFieldLeak")
    public static BottomNavCustom bottomNav;

    private TextView txtAppName, txtSearchTitle;
    private MaterialCardView cardSearch;
    private ImageView imgNotif;

    private void initViews() {
        txtAppName = findViewById(R.id.main_appbar_appname);
        txtSearchTitle = findViewById(R.id.main_appbar_search_txt);
        cardSearch = findViewById(R.id.main_appbar_search);
        imgNotif = findViewById(R.id.main_appbar_notif);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        String appname = getString(R.string.app_name);
        SpannableString spannableString = new SpannableString(appname);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.mint_green)), 5, appname.length(), 0);
        txtAppName.setText(spannableString);

        bottomNav = new BottomNavCustom(this);
        bottomNav.playAnimation(BottomNavCustom.ITEM_HOME);
        bottomNav.setActivatedItem(BottomNavCustom.ITEM_HOME);
        bottomNav.setDeactivatedOnFrame(BottomNavCustom.ITEM_HOME);

        bottomNav.setOnItemClickListener(new BottomNavCustom.OnItemListener() {
            @Override
            public void itemHome() {
                txtSearchTitle.setText(R.string.app_appbar_home);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new HomeFragment(), false);
            }

            @Override
            public void itemAktivitas() {
                txtSearchTitle.setText(R.string.app_appbar_aktivitas);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new AktivitasFragment(), false);
            }

            @Override
            public void itemReferensi() {
                txtSearchTitle.setText(R.string.app_appbar_referensi);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new ReferensiFragment(), false);
            }

            @Override
            public void itemProfile() {
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new ProfileFragment(), false);
            }
        });

        checkAndRequestStoragePermission();

        FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new HomeFragment(), false);

        onClickGroups();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, you can proceed with your logic (e.g., open the gallery).
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to check and request permission if needed.
    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it.
//            Toast.makeText(this, "Request Permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.dia_title_warning)
                .setMessage(R.string.dia_msg_confirm_close)
                .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                        ArenaFinder.closeApplication(MainActivity.this);
                    }
                })
                .setNegativeButton(R.string.dia_negative_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();

    }

    private void onClickGroups(){

        cardSearch.setOnClickListener(v -> {
            startActivity(
                    new Intent(MainActivity.this, SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SEARCH_WORLD)
            );
        });

        imgNotif.setOnClickListener(v -> {
            startActivity(
                    new Intent(MainActivity.this, SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.NOTIFICATIONS)
            );
        });

    }
}