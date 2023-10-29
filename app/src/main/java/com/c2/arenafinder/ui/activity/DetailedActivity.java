package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.detailed.ActivityDetailedFragment;
import com.c2.arenafinder.ui.fragment.detailed.BookingVenueFragment;
import com.c2.arenafinder.ui.fragment.detailed.VenueDetailedFragment;
import com.c2.arenafinder.util.FragmentUtil;
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
        getWindow().getDecorView().post(() -> new Thread(() -> runOnUiThread(() -> {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        })).start());
        setContentView(R.layout.activity_detailed);

        String idSelected = getIntent().getStringExtra(ID);

        switch (getIntent().getStringExtra(FRAGMENT)){
            case VENUE: {
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        VenueDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
            case ACTIVITY: {
                FragmentUtil.switchFragmentDetailed(
                        this.getSupportFragmentManager(),
                        ActivityDetailedFragment.newInstance(idSelected), true
                );
                break;
            }
        }

        MaterialButton button = findViewById(R.id.detailed_booking_btn);
        button.setOnClickListener(v -> {
            FragmentUtil.switchFragmentDetailed(this.getSupportFragmentManager(), BookingVenueFragment.newInstance(idSelected), true);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
    }
}