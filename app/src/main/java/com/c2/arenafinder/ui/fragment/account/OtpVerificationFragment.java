package com.c2.arenafinder.ui.fragment.account;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.otpview.OTPListener;
import com.otpview.OTPTextView;

public class OtpVerificationFragment extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_OTP = "otp";
    private static final String ARG_TYPE = "type";

    private String email;
    private String otp;
    private String type;

    private ButtonAccountCustom btnSend;
    private TextView helperText;
    private OTPTextView inpOtp;

    public OtpVerificationFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnSend = new ButtonAccountCustom(requireContext(), view, R.string.btn_kirim_ulang);
        inpOtp = view.findViewById(R.id.votp_inp_otp);
        helperText = view.findViewById(R.id.votp_txt_helper);
    }

    public static OtpVerificationFragment newInstance(String email, String otp, String type) {
        OtpVerificationFragment fragment = new OtpVerificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_OTP, otp);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
            otp = getArguments().getString(ARG_OTP);
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        btnSend.setStatus(ButtonAccountCustom.DISABLE);
        inpOtp.requestFocus();
        onClickGroups();
        onListener();
    }

    private void onClickGroups(){

        btnSend.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Button di clik", Toast.LENGTH_SHORT).show();
        });

    }

    private void onListener(){

        inpOtp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                helperText.setTextColor(ContextCompat.getColor(requireContext(), R.color.azure));
                helperText.setText(R.string.msg_otp_input);
            }

            @Override
            public void onOTPComplete(@NonNull String otp) {

                OtpVerificationFragment.this.otp = "123456";
                if (otp.equals(OtpVerificationFragment.this.otp)){
                    helperText.setText(R.string.suc_otp_valid);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inpOtp.showSuccess();
                        }
                    });

//                    switch (type){
//                        case "signup" : {
//                            new AlertDialog.Builder(requireContext())
//                                    .setTitle(R.string.dia_title_inform)
//                                    .setMessage(R.string.dia_msg_otp_signup_suc)
//                                    .setCancelable(false)
//                                    .setPositiveButton(R.string.dia_positive_login, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            FragmentUtil.switchFragmentAccount(
//                                                    requireActivity().getSupportFragmentManager(),
//                                                    new SignInFragment(),
//                                                    false
//                                            );
//                                        }
//                                    })
//                                    .create().show();
//
//                            break;
//                        }
//                        case "forgotpass" : {
//                            new AlertDialog.Builder(requireContext())
//                                    .setTitle(R.string.dia_title_inform)
//                                    .setMessage(R.string.dia_msg_otp_forgot_suc)
//                                    .setCancelable(false)
//                                    .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            FragmentUtil.switchFragmentAccount(
//                                                    requireActivity().getSupportFragmentManager(),
//                                                    GantiSandiFragment.newInstance(email),
//                                                    false
//                                            );
//                                        }
//                                    })
//                                    .create().show();
//                            break;
//                        }
//                    }

                }else{
                    helperText.setText(R.string.err_otp_invalid);
                    helperText.setTextColor(ContextCompat.getColor(requireContext(), R.color.vivid_orange));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inpOtp.showError();
                        }
                    });
                }

            }
        });

    }

}