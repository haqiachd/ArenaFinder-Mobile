package com.c2.arenafinder.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.submain.EditAccountFragment;
import com.c2.arenafinder.util.FragmentUtil;

public class SubMainActivity extends AppCompatActivity {

    public static final String FRAGMENT = "fragment";
    public static final String EDIT_ACCOUNT = "edit_acc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        switch (getIntent().getStringExtra(FRAGMENT)){
            case EDIT_ACCOUNT: {
                FragmentUtil.switchFragmentSubMain(
                        getSupportFragmentManager(), new EditAccountFragment(), false
                );
                break;
            }
            case "other" : {
                break;
            }
        }
    }
}