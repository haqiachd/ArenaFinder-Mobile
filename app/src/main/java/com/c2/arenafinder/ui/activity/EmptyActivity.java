package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.empty.ServerNotFoundFragment;
import com.c2.arenafinder.ui.fragment.empty.WelcomeFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;

public class EmptyActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";
    public static final String WELCOME = "wlc",
                                SERVER_NOT_FOUND = "server_404";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        switch (getIntent().getStringExtra(FRAGMENT)){
            case WELCOME: {
                FragmentUtil.switchFragmentEmpty(getSupportFragmentManager(), new WelcomeFragment(), false);
                break;
            }
            case SERVER_NOT_FOUND: {
                FragmentUtil.switchFragmentEmpty(getSupportFragmentManager(), new ServerNotFoundFragment(), false);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(getIntent().getStringExtra(FRAGMENT).equals(WELCOME)){
            ArenaFinder.playVibrator(this, ArenaFinder.VIBRATOR_SHORT);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dia_title_inform)
                    .setMessage(R.string.dia_msg_confirm_close)
                    .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> ArenaFinder.closeApplication(EmptyActivity.this))
                    .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> dialog.dismiss())
                    .create().show();
        }else{
            super.onBackPressed();
        }

    }
}