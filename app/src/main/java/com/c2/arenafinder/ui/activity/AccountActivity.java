package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.account.GantiSandiFragment;
import com.c2.arenafinder.ui.fragment.account.SignInFragment;
import com.c2.arenafinder.ui.fragment.account.SignUpFragmentFirst;
import com.c2.arenafinder.util.FragmentUtil;

public class AccountActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";
    public static final String SIGN_UP = "sign-up", SIGN_IN = "sign-in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Uri data = getIntent().getData();

        // jika activity dibuka melalui link browser
        if (data != null && data.getPathSegments().size() > 0) {
            switch (data.getLastPathSegment()) {
                case "sign-up" : {
                        FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
                case "forgot": {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new GantiSandiFragment(), false);
                    break;
                }
            }
        }
        // jika activity dibuka melalui activity atau fragment lain
        else {
            switch (getIntent().getStringExtra(FRAGMENT)) {
                case SIGN_UP: {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignUpFragmentFirst(), false);
                    break;
                }
                case SIGN_IN: {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
            }
        }
    }

}