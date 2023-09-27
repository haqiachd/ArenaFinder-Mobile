package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.ForgotPasswordFragment;
import com.c2.arenafinder.ui.fragment.SignInFragment;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        AccountActivity.this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.acc_frame_layout, new SignInFragment())
                .commit();
    }
}