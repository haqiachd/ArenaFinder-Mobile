package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.ui.fragment.empty.VerifyStatusFragment;
import com.c2.arenafinder.ui.fragment.submain.ChangePwOnProfile;
import com.c2.arenafinder.ui.fragment.submain.EditAccountFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuAlurPemesanan;
import com.c2.arenafinder.ui.fragment.submain.MenuBookingFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuCommunityFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuInformasiFragment;
import com.c2.arenafinder.ui.fragment.submain.NotificationsFragment;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Objects;

public class SubMainActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment", SPORT_ACTION = "sport_type", SPORT_DATA = "sport_data", SEARCH_TYPE = "search_type";

    public static final String EDIT_ACCOUNT = "edit_acc", MENU_ALUR = "alur", MENU_BOOKING = "booking", MENU_COMMUNITY = "community",
            MENU_TROLLEY = "trolley", NOTIFICATIONS = "notif", SEARCH_WORLD = "search",
            SPORT_TYPE = "sport", VIEW_ALL = "view", VERIFY_STATUS = "v-status", CHANGE_PASS = "chgpass";

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
            case MENU_ALUR: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new MenuAlurPemesanan(), false
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
                        getSupportFragmentManager(), new MenuInformasiFragment(), false
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
                String type = getIntent().getStringExtra(SEARCH_TYPE);

                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), SearchWorldFragment.newInstance(Objects.requireNonNullElse(type, "")), false
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
                if (action != null && data != null) {
                    // open sport type fragment with action and data
                    FragmentUtil.switchFragmentSubMain(
                            getSupportFragmentManager(),
                            SportTypeFragment.newInstance(Integer.parseInt(action), data),
                            false
                    );
                } else {
                    FragmentUtil.switchFragmentSubMain(
                            getSupportFragmentManager(), new SportTypeFragment(), false
                    );
                }
                break;
            }
            case VIEW_ALL: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(),
                        ViewAllFragment.newInstance(getIntent().getIntExtra(SPORT_ACTION, 0)),
                        false
                );
                break;
            }
            case VERIFY_STATUS: {
                FragmentUtil.switchFragmentSubMain(getSupportFragmentManager(), new VerifyStatusFragment(), false);
                break;
            }
            case CHANGE_PASS: {
                FragmentUtil.switchFragmentSubMain(getSupportFragmentManager(), new ChangePwOnProfile(), false);
                break;
            }
        }

        onClickGroups();
    }

    private void showFragmentByActionAndData(Runnable whenSuccess, Runnable whenError) {
        // get action and data
        String action = getIntent().getStringExtra(SPORT_ACTION),
                data = getIntent().getStringExtra(SPORT_DATA);

        LogApp.info(this, LogTag.LIFEFCYLE, "ACTION --> " + action);
        LogApp.info(this, LogTag.LIFEFCYLE, "DATA   --> " + data);

        // cek action and data
        if (action != null && data != null) {
            // open fragment with action and data
            whenSuccess.run();
        } else {
            whenError.run();
        }
    }

    private void showFragmentByAction(Runnable whenSuccess, Runnable whenError) {
        // get action and data
        int action = getIntent().getIntExtra(SPORT_ACTION, 0);

        LogApp.info(this, LogTag.LIFEFCYLE, "ACTION --> " + action);

        // cek action
        if (action > 0) {
            // open new fragment
            LogApp.info(this, LogTag.LIFEFCYLE, "Replace fragment sub main with instance");
            whenSuccess.run();
        } else {
            LogApp.info(this, LogTag.LIFEFCYLE, "Replace fragment sub main");
            whenError.run();
        }
    }

    private void onClickGroups() {

        btnBack.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }

}