package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.detailed.ActivityDetailedFragment;
import com.c2.arenafinder.ui.fragment.detailed.BookingVenueFragment;
import com.c2.arenafinder.ui.fragment.detailed.VenueDetailedFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

        button.setOnClickListener(v -> {
            switch (getSupportFragmentManager().getBackStackEntryCount()){
                case 1 : {
                    FragmentUtil.switchFragmentDetailed(
                            this.getSupportFragmentManager(), BookingVenueFragment.newInstance(idSelected), true
                    );
                    break;
                }
                case 2 : {
                    ArenaFinder.playVibrator(this, ArenaFinder.VIBRATOR_SHORT);
                    BottomSheetDialog sheet = new BottomSheetDialog(this, R.style.BottomSheetTheme);
                    View sheetInflater = getLayoutInflater().inflate(R.layout.sheet_choose_payment, null);
                    sheet.setContentView(sheetInflater);

                    sheetInflater.findViewById(R.id.scp_button).setOnClickListener(view -> {
                        Toast.makeText(this, "Cek", Toast.LENGTH_SHORT).show();
                    });

                    sheet.show();
                    sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
                    sheet.getWindow().setGravity(Gravity.BOTTOM);
                    break;
                }
            }
        });
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