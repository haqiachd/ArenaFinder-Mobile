package com.c2.arenafinder.ui.fragment.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.google.android.material.button.MaterialButton;


public class SignUpSecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MaterialButton btnPrev, btnSignUp;

    public SignUpSecondFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnPrev = view.findViewById(R.id.signup2_btn_back);
        btnSignUp = view.findViewById(R.id.signup2_btn_signup);
    }

    public static SignUpSecondFragment newInstance(String param1, String param2) {
        SignUpSecondFragment fragment = new SignUpSecondFragment();
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
        return inflater.inflate(R.layout.fragment_sign_up_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClickGroups();
    }

    private void onClickGroups(){

        btnSignUp.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Register", Toast.LENGTH_SHORT).show();
        });

        btnPrev.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

    }
}