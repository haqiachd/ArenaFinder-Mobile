package com.c2.arenafinder.ui.fragment.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.button.MaterialButton;

public class SignUpTypeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialButton btnUser, btnAdmin;

    public SignUpTypeFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnAdmin = view.findViewById(R.id.signupt_btn_admin);
        btnUser = view.findViewById(R.id.signupt_btn_users);
    }

    public static SignUpTypeFragment newInstance(String param1, String param2) {
        SignUpTypeFragment fragment = new SignUpTypeFragment();
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
        return inflater.inflate(R.layout.fragment_sign_up_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        onClickGroups();
    }

    private void onClickGroups(){

        btnAdmin.setOnClickListener(v -> {
            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.dia_title_inform)
                    .setMessage(R.string.dia_msg_signup_type)
                    .setPositiveButton(R.string.btn_wlc_register, (dialog, which) -> {
                        LogApp.info(requireContext(), LogTag.ON_DIALOG_POSITIVE, "membuka register admin pada web");
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=LgEfTUoiZpE&ab_channel=JKT48")));
                    })
                    .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                    })
                    .create().show();

        });

        btnUser.setOnClickListener(v -> {
            FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignUpFirstFragment(), false);
        });

    }
}