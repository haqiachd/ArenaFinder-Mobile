package com.c2.arenafinder.ui.fragment.empty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.ui.activity.AccountActivity;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.button.MaterialButton;

public class AccountMessageFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    public static final String SIGNUP = "signup", PASSWORD = "pass";

    private String type;

    private MaterialButton btnMsg;
    private LottieAnimationView lottie;
    private TextView txtTitle, txtDesc;

    public AccountMessageFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        btnMsg = view.findViewById(R.id.accmsg_button);
        lottie = view.findViewById(R.id.accmsg_lottie);
        txtTitle = view.findViewById(R.id.accmsg_title);
        txtDesc = view.findViewById(R.id.accmsg_desc);
    }

    public static AccountMessageFragment newInstance(String type) {
        AccountMessageFragment fragment = new AccountMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                buttonAction();
            }
        });

        // set pesan pada fragmment
        switch (type) {
            case SIGNUP: {
                lottie.setAnimation(R.raw.raw_register_success);
                txtTitle.setText(R.string.suc_signup_success);
                txtDesc.setText(R.string.txt_accmsg_signup_desc);
                btnMsg.setText(R.string.btn_buka_app);
                break;
            }
            case PASSWORD: {
                lottie.setAnimation(R.raw.raw_lock_success);
                txtTitle.setText(R.string.suc_chgpass_success);
                txtDesc.setText(R.string.txt_accmsg_chgpass_desc);
                btnMsg.setText(R.string.btn_go_login);
                break;
            }
        }

        onClickGroups();
    }

    private void buttonAction(){
        switch (type) {
            case SIGNUP: {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "Membuka beranda");
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
                break;
            }
            case PASSWORD: {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "Membuka halaman masuk");
                startActivity(
                        new Intent(requireActivity(), AccountActivity.class)
                                .putExtra(AccountActivity.FRAGMENT, AccountActivity.SIGN_IN)
                );
                requireActivity().finish();
                break;
            }
        }
    }

    private void onClickGroups(){

        btnMsg.setOnClickListener(v ->{
            buttonAction();
        });
    }

}