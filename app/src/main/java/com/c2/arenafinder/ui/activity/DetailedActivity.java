package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.ui.fragment.detailed.ActivityDetailedFragment;
import com.c2.arenafinder.ui.fragment.detailed.BookingVenueFragment;
import com.c2.arenafinder.ui.fragment.detailed.VenueDetailedFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.button.MaterialButton;

public class DetailedActivity extends AppCompatActivity {

    // codes
    public static final String FRAGMENT = "fragment";
    public static final String ID = "id";

    // extras
    public static final String VENUE = "venue";
    public static final String ACTIVITY = "activity";

    private String idSelected;

    private MaterialButton button;

    private TextView txtTop, txtData, txtRight;

    private void initViews(){
        button = findViewById(R.id.dtld_nav_button);
        txtTop = findViewById(R.id.dtld_nav_txt_top);
        txtRight = findViewById(R.id.dtld_nav_txt_right);
        txtData = findViewById(R.id.dtld_nav_txt_data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initViews();

        idSelected = getIntent().getStringExtra(ID);
        LogApp.info(this, "ID SELECTED -> " + idSelected);

        String selectedFragment = getIntent().getStringExtra(FRAGMENT);

        if (selectedFragment == null){
            selectedFragment  += "";
        }

        switch (selectedFragment) {
            case VENUE: {
                ArenaFinder.setStatusBarColor(this, ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, true);
                // membuka venue detailed
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        VenueDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
            case ACTIVITY: {
                ArenaFinder.setStatusBarColor(this, ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.white, true);
                // membuka venue activity
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        ActivityDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            super.onBackPressed();
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}