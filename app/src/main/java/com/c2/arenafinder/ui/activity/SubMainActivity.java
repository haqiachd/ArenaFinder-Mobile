package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.submain.EditAccountFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuBookingFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuCommunityFragment;
import com.c2.arenafinder.ui.fragment.submain.MenuTrolleyFragment;
import com.c2.arenafinder.ui.fragment.submain.NotificationsFragment;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.FragmentUtil;

public class SubMainActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";

    public static final String EDIT_ACCOUNT = "edit_acc", MENU_BOOKING = "booking", MENU_COMMUNITY = "community",
            MENU_TROLLEY = "trolley", NOTIFICATIONS = "notif", SEARCH_WORLD = "search",
            SPORT_TYPE = "sport", VIEW_ALL = "view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

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
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new SportTypeFragment(), false
                );
                break;
            }
            case VIEW_ALL: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new ViewAllFragment(), false
                );
                break;
            }
        }
    }
}