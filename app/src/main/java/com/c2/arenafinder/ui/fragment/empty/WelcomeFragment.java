package com.c2.arenafinder.ui.fragment.empty;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.VerifyModel;
import com.c2.arenafinder.ui.activity.AccountActivity;
import com.c2.arenafinder.ui.activity.EmptyActivity;
import com.c2.arenafinder.util.VerifyUtil;
import com.google.android.material.button.MaterialButton;

public class WelcomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MaterialButton btnSignIn, btnSignUp;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnSignIn = view.findViewById(R.id.wlc_btn_signin);
        btnSignUp = view.findViewById(R.id.wlc_btn_signup);
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        view.findViewById(R.id.wlc_test).setOnClickListener(v -> {
//            startActivity(new Intent(
//                    requireActivity(), EmptyActivity.class
//            ).putExtra(EmptyActivity.FRAGMENT, EmptyActivity.ACCOUNT_MESSAGE));
//            requireActivity().finish();
            startActivity(new Intent(requireActivity(), AccountActivity.class)
                    .putExtra(AccountActivity.FRAGMENT, AccountActivity.OTP_VERIFY)
            );
            requireActivity().finish();
        });

        onClickGroups();

        long currentMillis = System.currentTimeMillis();
        VerifyUtil verifyUtil = new VerifyUtil(requireContext());
        verifyUtil.setResend(1);
        LogApp.error(this, LogTag.METHOD, "CURRENT");

        VerifyModel model = new VerifyModel(
                "123456",
                currentMillis, currentMillis + VerifyUtil._15_MINUTES,
                VerifyUtil.TYPE_CHANGE, "mobile", 1, verifyUtil.countResendMillis()
        );

        verifyUtil = new VerifyUtil(requireContext(), model);
    }

    private void onClickGroups(){

        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(
                    requireActivity(), AccountActivity.class
            ).putExtra(AccountActivity.FRAGMENT, AccountActivity.SIGN_UP_FIRST)
            );
        });

        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(
                    requireActivity(), AccountActivity.class
            ).putExtra(AccountActivity.FRAGMENT, AccountActivity.SIGN_IN)
            );
        });

    }

}