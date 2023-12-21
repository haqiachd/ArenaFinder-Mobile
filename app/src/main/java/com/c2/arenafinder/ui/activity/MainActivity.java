package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.ui.fragment.main.HomeFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;

public class MainActivity extends AppCompatActivity {

    private TextView txtAppName;
    private ImageView imgNotif;

    private void initViews() {
        txtAppName = findViewById(R.id.main_appbar_appname);
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

        /* action saat button nav di klik */
        BottomNavCustom bottomNav = new BottomNavCustom(this);
        bottomNav.playAnimation(BottomNavCustom.ITEM_HOME);
        bottomNav.setActivatedItem(BottomNavCustom.ITEM_HOME);
        bottomNav.setDeactivatedOnFrame(BottomNavCustom.ITEM_HOME);
        bottomNav.setOnItemClickListener(new BottomNavCustom.OnItemListener() {
            @Override
            public void itemHome() {
            }

            @Override
            public void itemAktivitas() {
            }

            @Override
            public void itemReferensi() {
            }

            @Override
            public void itemProfile() {
            }
        });

        FragmentUtil.switchFragmentMain(getSupportFragmentManager(), new HomeFragment(), false);

        onClickGroups();
    }

    @Override
    public void onBackPressed() {
        ArenaFinder.playVibrator(this, ArenaFinder.VIBRATOR_SHORT);
        new AlertDialog.Builder(this)
                .setTitle(R.string.dia_title_warning)
                .setMessage(R.string.dia_msg_confirm_close)
                .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> {
                    MainActivity.super.onBackPressed();
                    ArenaFinder.closeApplication(MainActivity.this);
                })
                .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                })
                .create().show();

    }

    private void onClickGroups() {

        imgNotif.setOnClickListener(v ->
                startActivity(
                        new Intent(MainActivity.this, SubMainActivity.class)
                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.NOTIFICATIONS)
                )
        );

    }
}