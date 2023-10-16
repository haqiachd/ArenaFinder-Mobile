package com.c2.arenafinder.ui.fragment.account;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.data.response.VerifyResponse;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.ValidatorUtil;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpSecondFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_NAME = "name";
    private static String savedPassword, savedConfirm;

    private ValidatorUtil validator;

    private String username;
    private String email;
    private String name;

    private AlertDialog loadingVerify;
    private MaterialButton btnPrev, btnSignUp;
    private EditText inpPassword, inpKonf;
    private TextView txtHelper;

    public SignUpSecondFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        btnPrev = view.findViewById(R.id.signup2_btn_back);
        btnSignUp = view.findViewById(R.id.signup2_btn_signup);
        inpPassword = view.findViewById(R.id.signup2_inp_password);
        inpKonf = view.findViewById(R.id.signup2_inp_konf);
        txtHelper = view.findViewById(R.id.signup2_txt_helper);
    }

    public static SignUpSecondFragment newInstance(String username, String email, String name) {
        SignUpSecondFragment fragment = new SignUpSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            email = getArguments().getString(ARG_EMAIL);
            name = getArguments().getString(ARG_NAME);
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

        loadingVerify = new AlertDialog.Builder(requireContext())
                .setView(getLayoutInflater().inflate(R.layout.dialog_loading, null))
                .setTitle(R.string.dia_title_register)
                .setCancelable(false).create();

        onClickGroups();
        onChangedGroups();
    }

    public static void resetSavedPassword(){
        savedPassword = null;
        savedConfirm = null;
    }

    public boolean isSavedPassword(){
        return savedPassword != null && savedConfirm != null;
    }

    @Override
    public void onPause() {
        super.onPause();
        savedPassword = inpPassword.getText().toString();
        savedConfirm = inpKonf.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isSavedPassword()){
            inpPassword.setText(savedPassword);
            inpKonf.setText(savedConfirm);
        }
    }

    private void sendEmailVerify(){

        RetrofitClient.getInstance().sendEmail(email, "signup")
                .enqueue(new Callback<VerifyResponse>() {
                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                            loadingVerify.dismiss();
                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                            new AlertDialog.Builder(requireContext())
                                    .setTitle(R.string.dia_title_inform)
                                    .setMessage(R.string.dia_msg_inform_signup)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.dia_positive_verify, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            FragmentUtil.switchFragmentAccount(
                                                    requireActivity().getSupportFragmentManager(),
                                                    OtpVerificationFragment.newInstance(email, response.body().getData().getOtp(), "signup"),
                                                    false
                                            );

                                        }
                                    })
                                    .create()
                                    .show();

                        } else {
                            assert response.body() != null;
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingVerify.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingVerify.dismiss();
                    }
                });
    }

    private void onClickGroups() {

        btnSignUp.setOnClickListener(v -> {

            loadingVerify.show();

            RetrofitClient.getInstance().register(username, email, name, inpPassword.getText().toString())
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if (RetrofitClient.apakahSukses(response)) {
                                sendEmailVerify();
                            } else {
                                assert response.body() != null;
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                loadingVerify.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            loadingVerify.dismiss();
                        }
                    });
        });

        btnPrev.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

    }

    private void onChangedGroups() {

        EditText[] inputs = {inpPassword, inpKonf};

        for (EditText input : inputs) {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validator.inputPassword(inpPassword.getText().toString(), inpKonf.getText().toString());
                }
            });
        }
    }
}