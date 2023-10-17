package com.c2.arenafinder.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.account.ChangePasswordFragment;
import com.c2.arenafinder.ui.fragment.account.OtpVerificationFragment;
import com.c2.arenafinder.ui.fragment.account.SignInFragment;
import com.c2.arenafinder.ui.fragment.account.SignUpTypeFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.DialogUtil;
import com.c2.arenafinder.util.FragmentUtil;

public class AccountActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";
    public static final String
            SIGN_UP_FIRST = "sign-up-first",
            SIGN_UP_SECOND = "sign-up-second",
            SIGN_UP_GOOGLE = "google",
            SIGN_IN = "sign-in",
            OTP_VERIFY = "otp-code",
            FORGOT_PASS = "forgot-pass",
            CHANGE_PASS = "change-pass";

    private static String IAM_IN = "null";

    private ImageView btnBack;

    private void initViews() {
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
                case "sign-up": {
                    IAM_IN = SIGN_IN;
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
                case "forgot": {
                    IAM_IN = CHANGE_PASS;
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new ChangePasswordFragment(), false);
                    break;
                }
            }
        }
        // jika activity dibuka melalui activity atau fragment lain
        else {
            switch (getIntent().getStringExtra(FRAGMENT)) {
                case SIGN_UP_FIRST: {
                    IAM_IN = SIGN_UP_FIRST;
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignUpTypeFragment(), false);
                    break;
                }
                case SIGN_IN: {
                    IAM_IN = SIGN_IN;
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new SignInFragment(), false);
                    break;
                }
                case OTP_VERIFY: {
                    IAM_IN = OTP_VERIFY;
                    FragmentUtil.switchFragmentAccount(getSupportFragmentManager(), new OtpVerificationFragment(), false);
                    break;
                }
            }
        }

        onClickGroups();
    }

    @Override
    public void onBackPressed() {

        switch (IAM_IN){
            case SIGN_IN: {
                AlertDialog alertDialog = DialogUtil.showInformationDialog(
                        this, "Test", "message",
                        "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("", "");
                                ArenaFinder.playVibrator(AccountActivity.this, ArenaFinder.VIBRATOR_SHORT);
                                Toast.makeText(AccountActivity.this, "oi", Toast.LENGTH_SHORT).show();
                            }
                        },
                        "negative", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }
                );
                alertDialog.show();
                break;
            }
            case OTP_VERIFY: {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.dia_title_warning)
                        .setMessage("This is Message")
                        .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AccountActivity.this, "back pressed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.dia_negative_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show();
                break;
            }
            case CHANGE_PASS: {
                // TODO : on back pressed action
            }
            default:{
                super.onBackPressed();
            }
        }

    }

    private void onClickGroups() {

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }

}