package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.detailed.ActivityDetailedFragment;
import com.c2.arenafinder.ui.fragment.detailed.VenueDetailedFragment;
import com.c2.arenafinder.util.FragmentUtil;

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
    }
}