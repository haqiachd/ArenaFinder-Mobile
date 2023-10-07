package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.empty.WelcomeFragment;
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
                break;
            }
        }
    }
}