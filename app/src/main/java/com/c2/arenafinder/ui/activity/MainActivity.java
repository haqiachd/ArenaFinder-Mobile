package com.c2.arenafinder.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.main.AktivitasFragment;
import com.c2.arenafinder.ui.fragment.main.HomeFragment;
import com.c2.arenafinder.ui.fragment.main.ProfileFragment;
import com.c2.arenafinder.ui.fragment.main.ReferensiFragment;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.FragmentUtil;


public class MainActivity extends AppCompatActivity {

    public static int MENU_HOME = 1, MENU_AKTIVITAS = 2, MENU_REFERENSI = 3, MENU_PROFILE = 4;

    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private TextView txtAppbar;

    @SuppressLint("StaticFieldLeak")
    public static BottomNavCustom bottomNav;

    private void initViews() {
        txtAppbar = findViewById(R.id.main_appbar_title_old);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        bottomNav = new BottomNavCustom(this);
        bottomNav.setActivatedItem(BottomNavCustom.ITEM_HOME);
        bottomNav.setDisabledClickable(BottomNavCustom.ITEM_HOME);
        bottomNav.setOnItemClickListener(new BottomNavCustom.OnItemListener() {
            @Override
            public void itemHome() {
                bottomNav.setDisabledClickable(BottomNavCustom.ITEM_HOME);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new HomeFragment(), false);
            }

            @Override
            public void itemAktivitas() {
                bottomNav.setDisabledClickable(BottomNavCustom.ITEM_AKTIVITAS);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new AktivitasFragment(), false);
            }

            @Override
            public void itemReferensi() {
                bottomNav.setDisabledClickable(BottomNavCustom.ITEM_REFERENSI);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new ReferensiFragment(), false);
            }

            @Override
            public void itemProfile() {
                bottomNav.setDisabledClickable(BottomNavCustom.ITEM_PROFILE);
                FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new ProfileFragment(), false);
            }
        });

        checkAndRequestStoragePermission();

        FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new HomeFragment(), false);
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

}