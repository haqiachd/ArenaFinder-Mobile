package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.AktivitasResponse;
import com.c2.arenafinder.ui.fragment.submain.EditAccountFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuBookingFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuCommunityFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuTrolleyFragment;
import com.c2.arenafinder.ui.fragment.submain.NotificationsFragment;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.appbar.AppBarLayout;

public class SubMainActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment", SPORT_ACTION = "sport_type", SPORT_DATA = "sport_data";

    public static final String EDIT_ACCOUNT = "edit_acc", MENU_BOOKING = "booking", MENU_COMMUNITY = "community",
            MENU_TROLLEY = "trolley", NOTIFICATIONS = "notif", SEARCH_WORLD = "search",
            SPORT_TYPE = "sport", VIEW_ALL = "view";

    private AppBarLayout appBarLayout;
    private ImageView btnBack;
    private TextView appbarTitle;

    private void initViews() {
        appBarLayout = findViewById(R.id.sub_appbar);
        btnBack = findViewById(R.id.sub_back);
        appbarTitle = findViewById(R.id.sub_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        initViews();

        switch (getIntent().getStringExtra(FRAGMENT)) {
            case EDIT_ACCOUNT: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new EditAccountFragment(), false
                );
                break;
            }
            case MENU_BOOKING: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new MenuBookingFragment(), false
                );
                break;
            }
            case MENU_COMMUNITY: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new MenuCommunityFragment(), false
                );
                break;
            }
            case MENU_TROLLEY: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new MenuTrolleyFragment(), false
                );
                break;
            }
            case NOTIFICATIONS: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new NotificationsFragment(), false
                );
                break;
            }
            case SEARCH_WORLD: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new SearchWorldFragment(), false
                );
                break;
            }
            case SPORT_TYPE: {
                // get action and data
                String action = getIntent().getStringExtra(SPORT_ACTION),
                        data = getIntent().getStringExtra(SPORT_DATA);
                LogApp.info(this, LogTag.LIFEFCYLE, "ACTION --> " + action);
                LogApp.info(this, LogTag.LIFEFCYLE, "DATA   --> " + data);

                // cek action and data
                if (action != null && data != null){
                    // open sport type fragment with action and data
                    FragmentUtil.switchFragmentSubMain(
                            getSupportFragmentManager(),
                            SportTypeFragment.newInstance(Integer.parseInt(action), data),
                            false
                    );
                }else {
                    FragmentUtil.switchFragmentSubMain(
                            getSupportFragmentManager(), new SportTypeFragment(), false
                    );
                }
                break;
            }
            case VIEW_ALL: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new ViewAllFragment(), false
                );
                break;
            }
        }

        onClickGroups();
    }

    private void onClickGroups() {

        btnBack.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }

}