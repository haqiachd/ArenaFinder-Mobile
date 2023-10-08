package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.c2.arenafinder.R;
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

        switch (getIntent().getStringExtra(FRAGMENT)){
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