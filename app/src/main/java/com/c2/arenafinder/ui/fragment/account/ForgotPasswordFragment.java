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
import com.c2.arenafinder.data.response.VerifyResponse;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.ValidatorUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ValidatorUtil validator;

    private EditText inpEmail;
    private ButtonAccountCustom btnSend;
    private TextView txtHelper;

    private String mParam1;
    private String mParam2;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        btnSend = new ButtonAccountCustom(requireContext(), view, R.string.btn_kirim_ulang);
        inpEmail = view.findViewById(R.id.forgot_inp_email);
        txtHelper = view.findViewById(R.id.forgot_txt_helper);
    }

    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        validator = new ValidatorUtil(requireContext(), btnSend, txtHelper);

        onClickGroups();
        onChangedGroups();
    }

    public void onClickGroups() {

        btnSend.setOnClickLoadingListener(() -> {

            RetrofitClient.getInstance().sendEmail(inpEmail.getText().toString(), "forgotpass")
                    .enqueue(new Callback<VerifyResponse>() {
                        @Override
                        public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                            if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                                new AlertDialog.Builder(requireContext())
                                        .setTitle(R.string.dia_title_inform)
                                        .setMessage(R.string.dia_msg_inform_forgot)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.dia_positive_verify, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                FragmentUtil.switchFragmentAccount(
                                                        requireActivity().getSupportFragmentManager(),
                                                        OtpVerificationFragment.newInstance(inpEmail.getText().toString(), response.body().getData().getOtp(), "forgotpass"),
                                                        false);
                                            }
                                        })
                                        .create().show();
                            } else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            btnSend.setStatus(ButtonAccountCustom.KILL_PROGRESS);
                        }

                        @Override
                        public void onFailure(Call<VerifyResponse> call, Throwable t) {
                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            btnSend.setStatus(ButtonAccountCustom.KILL_PROGRESS);
                        }
                    });

        });

    }

    public void onChangedGroups() {

        inpEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validator.forgotPassValidation(inpEmail.getText().toString());
            }
        });

    }
}