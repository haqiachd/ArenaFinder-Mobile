package com.c2.arenafinder.ui.fragment.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
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
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.activity.EmptyActivity;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.ui.fragment.empty.AccountMessageFragment;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;
import com.c2.arenafinder.util.ValidatorUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpGoogle extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_NAME = "full_name";

    private UsersUtil usersUtil;
    private DataShared dataShared;
    private ValidatorUtil validator;

    private String email;
    private String fullName;

    private ButtonAccountCustom btnRegister;
    private EditText inpUsername, inpPassword, inpkonf;
    private TextView txtHelper;

    private void initViews(View view) {
        this.btnRegister = new ButtonAccountCustom(requireContext(), view, R.string.btn_sign_up);
        this.inpUsername = view.findViewById(R.id.signupg_inp_username);
        this.inpPassword = view.findViewById(R.id.signupg_inp_pass);
        this.inpkonf = view.findViewById(R.id.signupg_inp_konf);
        txtHelper = view.findViewById(R.id.signupg_txt_helper);
    }

    public SignUpGoogle() {
        // Required empty public constructor
    }

    public static SignUpGoogle newInstance(String email, String fullName) {
        SignUpGoogle fragment = new SignUpGoogle();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_NAME, fullName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
            fullName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_google, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());
        dataShared = new DataShared(requireContext());
        validator = new ValidatorUtil(requireContext(), btnRegister, txtHelper);

        // on back pressed action
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // show dialog confirmation
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.dia_title_confirm)
                        .setMessage(R.string.dia_msg_sgoogle_canceled)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dia_positive_ya, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // buka fragment signup first
                                startActivity(
                                        new Intent(requireContext(), EmptyActivity.class)
                                                .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.WELCOME)
                                );
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {})
                        .create().show();
            }
        });

        onClickGroups();
        onChangedGroups();
    }

    private void onClickGroups() {

        btnRegister.setOnClickLoadingListener(() -> {

            RetrofitClient.getInstance().registerGoogle(
                    inpUsername.getText().toString(), email,
                    fullName, inpPassword.getText().toString()
            ).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body() != null && RetrofitClient.apakahSukses(response)) {

                        // saving data ke preferences
                        new UsersUtil(requireContext(), response.body().getData());

                        startActivity(
                                new Intent(requireActivity(), EmptyActivity.class)
                                        .putExtra(EmptyActivity.FRAGMENT, EmptyActivity.ACCOUNT_MESSAGE)
                                        .putExtra(EmptyActivity.FRAGMENT_MESSAGE, AccountMessageFragment.SIGNUP)
                        );
                        requireActivity().finish();

                    } else {
                        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                        txtHelper.setText(response.body().getMessage());
                        txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
                        Toast.makeText(SignUpGoogle.this.requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    btnRegister.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    btnRegister.setStatus(ButtonAccountCustom.KILL_PROGRESS);
                }
            });

        });

    }

    private void onChangedGroups() {

        EditText[] inputs = {inpUsername, inpPassword, inpkonf};

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
                    validator.signupGoogleValidation(inpUsername.getText().toString(), inpPassword.getText().toString(), inpkonf.getText().toString());
                }
            });
        }

    }

}