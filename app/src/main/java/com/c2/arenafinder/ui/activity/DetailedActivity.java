package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.detailed.ActivityDetailedFragment;
import com.c2.arenafinder.ui.fragment.detailed.BookingVenueFragment;
import com.c2.arenafinder.ui.fragment.detailed.VenueDetailedFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

public class DetailedActivity extends AppCompatActivity {

    // codes
    public static final String FRAGMENT = "fragment";
    public static final String ID = "id";

    // extras
    public static final String VENUE = "venue";
    public static final String ACTIVITY = "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        String idSelected = getIntent().getStringExtra(ID);

        switch (getIntent().getStringExtra(FRAGMENT)) {
            case VENUE: {
                ArenaFinder.setStatusBarColor(this, ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, true);
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        VenueDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
            case ACTIVITY: {
                ArenaFinder.setStatusBarColor(this, ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.white, true);
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        ActivityDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
        }

        MaterialButton button = findViewById(R.id.detailed_booking_btn);
        button.setOnClickListener(v -> {
            ArenaFinder.setStatusBarColor(this, ArenaFinder.WHITE_STATUS_BAR, R.color.white, true);
            FragmentUtil.switchFragmentDetailed(
                    this.getSupportFragmentManager(), BookingVenueFragment.newInstance(idSelected), true
            );
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
    }


}