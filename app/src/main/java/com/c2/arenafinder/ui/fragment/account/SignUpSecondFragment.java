package com.c2.arenafinder.ui.fragment.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.util.ValidatorUtil;
import com.google.android.material.button.MaterialButton;


public class SignUpSecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ValidatorUtil validator;

    private String mParam1;
    private String mParam2;

    private MaterialButton btnPrev, btnSignUp;
    private EditText inpPassword, inpKonf;
    private TextView txtHelper;

    public SignUpSecondFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnPrev = view.findViewById(R.id.signup2_btn_back);
        btnSignUp = view.findViewById(R.id.signup2_btn_signup);
        inpPassword = view.findViewById(R.id.signup2_inp_password);
        inpKonf = view.findViewById(R.id.signup2_inp_konf);
        txtHelper = view.findViewById(R.id.signup2_txt_helper);
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
        validator = new ValidatorUtil(requireContext(), btnSignUp, txtHelper);

        onClickGroups();
        onChangedGroups();
    }

    private void onClickGroups(){

        btnSignUp.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Register", Toast.LENGTH_SHORT).show();
        });

        btnPrev.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

    }

    private void onChangedGroups(){

        EditText[] inputs = {inpPassword, inpKonf};

        for (EditText input : inputs){
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    validator.inputPassword(inpPassword.getText().toString(), inpKonf.getText().toString());
                }
            });
        }
    }
}