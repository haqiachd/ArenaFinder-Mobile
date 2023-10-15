package com.c2.arenafinder.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.account.OtpVerificationFragment;
import com.c2.arenafinder.ui.fragment.account.SignInFragment;
import com.c2.arenafinder.ui.fragment.account.SignUpFirstFragment;
import com.c2.arenafinder.ui.fragment.account.SignUpTypeFragment;
import com.c2.arenafinder.util.FragmentUtil;

public class AccountActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";
    public static final String SIGN_UP = "sign-up", SIGN_IN = "sign-in";

    private ImageView btnBack;

    private void initViews(){
        btnBack = findViewById(R.id.acc_back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initViews();


        Uri data = getIntent().getData();

        // jika activity dibuka melalui link browser
        if (data != null && data.getPathSegments().size() > 0) {
            switch (data.getLastPathSegment()) {
                case "sign-up" : {
                        FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
                case "forgot": {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new OtpVerificationFragment(), false);
                    break;
                }
            }
        }
        // jika activity dibuka melalui activity atau fragment lain
        else {
            switch (getIntent().getStringExtra(FRAGMENT)) {
                case SIGN_UP: {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignUpTypeFragment(), false);
                    break;
                }
                case SIGN_IN: {
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
            }
        }

        onClickGroups();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void onClickGroups(){

        btnBack.setOnClickListener(v ->{
            onBackPressed();
        });

    }

}