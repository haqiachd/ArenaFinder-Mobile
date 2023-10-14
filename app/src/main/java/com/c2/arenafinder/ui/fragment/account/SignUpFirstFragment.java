package com.c2.arenafinder.ui.fragment.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.FragmentUtil;

public class SignUpFirstFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText inpUsername, inpEmail, inpName;
    private ButtonAccountCustom btnNext;
    private ImageView btnGoogle;
    private TextView btnLogin;

    public SignUpFirstFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnNext = new ButtonAccountCustom(requireContext(), view, R.string.btn_next);
        inpUsername = view.findViewById(R.id.signup1_inp_password);
        inpEmail = view.findViewById(R.id.signup1_inp_konf);
        inpName = view.findViewById(R.id.signup1_inp_name);
        btnGoogle = view.findViewById(R.id.signup1_btn_google);
        btnLogin = view.findViewById(R.id.signup1_btn_login);
    }

    public static SignUpFirstFragment newInstance(String param1, String param2) {
        SignUpFirstFragment fragment = new SignUpFirstFragment();
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
        return inflater.inflate(R.layout.fragment_sign_up_first2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        String btnLoginTxt = getString(R.string.txt_login_here);

        // set underline on login
        SpannableString spanLogin = new SpannableString(btnLoginTxt);
        spanLogin.setSpan(new UnderlineSpan(), 0, btnLoginTxt.length(), 0);
        btnLogin.setText(spanLogin);

        onClickGroups();
    }

    private void onClickGroups(){

        btnNext.setOnClickLoadingListener(() -> {
            FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignUpSecondFragment(), true);
            btnNext.setProgress(ButtonAccountCustom.KILL_PROGRESS);
        });

        btnGoogle.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Google", Toast.LENGTH_SHORT).show();
        });

        btnLogin.setOnClickListener(v -> {
            FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignInFragment(), false);
        });

    }
}