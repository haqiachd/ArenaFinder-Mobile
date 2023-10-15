package com.c2.arenafinder.ui.fragment.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.google.GoogleUsers;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.ValidatorUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFirstFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ValidatorUtil validator;
    private GoogleUsers google;

    private EditText inpUsername, inpEmail, inpName;
    private ButtonAccountCustom btnNext;
    private ImageView btnGoogle;
    private TextView btnLogin, txtHelper;

    public SignUpFirstFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnNext = new ButtonAccountCustom(requireContext(), view, R.string.btn_next);
        inpUsername = view.findViewById(R.id.signup1_inp_username);
        inpEmail = view.findViewById(R.id.signup1_inp_email);
        inpName = view.findViewById(R.id.signup1_inp_name);
        btnGoogle = view.findViewById(R.id.signup1_btn_google);
        btnLogin = view.findViewById(R.id.signup1_btn_login);
        txtHelper = view.findViewById(R.id.signup1_txt_helper);
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
        validator = new ValidatorUtil(requireContext(), btnNext, txtHelper);
        google = new GoogleUsers(requireActivity());

        String btnLoginTxt = getString(R.string.txt_login_here);

        // set underline on login
        SpannableString spanLogin = new SpannableString(btnLoginTxt);
        spanLogin.setSpan(new UnderlineSpan(), 0, btnLoginTxt.length(), 0);
        btnLogin.setText(spanLogin);

        onClickGroups();
        onChangedGroups();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        google.onActivityResult(requestCode, resultCode, data);

        if (google.isAccountSelected()) {

            GoogleSignInAccount account = google.getUserData();

            RetrofitClient.getInstance().cekUser(account.getEmail()).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body() != null && RetrofitClient.apakahSukses(response)) {
                        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                        new AlertDialog.Builder(requireContext())
                                .setTitle(R.string.dia_title_warning)
                                .setMessage(R.string.dia_msg_account_is_registered)
                                .setCancelable(false)
                                .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .create().show();
                    } else {
                        FragmentUtil.switchFragmentAccount(
                                requireActivity().getSupportFragmentManager(),
                                SignUpGoogle.newInstance(google.getUserData().getEmail(), google.getUserData().getDisplayName()),
                                false
                        );
                    }
                }

                @Override
                public void onFailure(Call<UsersResponse> call, @NonNull Throwable t) {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_MEDIUM);
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void onClickGroups(){

        btnNext.setOnClickLoadingListener(() -> {
            FragmentUtil.switchFragmentAccount(
                    requireActivity().getSupportFragmentManager(),
                    SignUpSecondFragment.newInstance(inpUsername.getText().toString(), inpEmail.getText().toString(), inpName.getText().toString())
                    , true
            );
            btnNext.setProgress(ButtonAccountCustom.KILL_PROGRESS);
        });

        btnGoogle.setOnClickListener(v -> {
            LogApp.info(requireContext(), v, LogTag.ON_CLICK, "Button google di click");

            if (ArenaFinder.isInternetConnected(requireContext())) {
                if (google == null) {
                    google = new GoogleUsers(requireActivity());
                } else {
                    google.resetLastSignIn();
                }
                startActivityForResult(google.getIntent(), GoogleUsers.REQUEST_CODE);
            } else {
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_MEDIUM);
                Toast.makeText(requireContext(), getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(v -> {
            FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignInFragment(), false);
        });

    }

    private void onChangedGroups(){

        EditText[] inputs = {inpUsername, inpEmail, inpName};

        for (EditText input : inputs){
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    validator.signupFirstValidation(inpUsername.getText().toString(), inpEmail.getText().toString(), inpName.getText().toString());
                }
            });
        }
    }
}