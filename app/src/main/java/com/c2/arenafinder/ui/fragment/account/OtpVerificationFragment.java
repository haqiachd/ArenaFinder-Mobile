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

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.VerifyResponse;
import com.c2.arenafinder.ui.activity.EmptyActivity;
import com.c2.arenafinder.ui.activity.SplashScreenActivity;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.VerifyUtil;
import com.otpview.OTPListener;
import com.otpview.OTPTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationFragment extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_OTP = "otp";
    private static final String ARG_TYPE = "type";

    private VerifyUtil verifyUtil;

    private int totalSeconds;

    private ButtonAccountCustom btnSend;
    private TextView helperText;
    private OTPTextView inpOtp;
    private AlertDialog loadingVerify;

    public OtpVerificationFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
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
        inpOtp.requestFocus();

        loadingVerify = new AlertDialog.Builder(requireContext())
                .setView(getLayoutInflater().inflate(R.layout.dialog_loading, null))
                .setTitle(R.string.dia_title_verificaton)
                .setCancelable(false).create();

        // initialize verify util for getting data from preferences
        LogApp.info(requireContext(), LogTag.LIFEFCYLE, "Initialize verify util");
        verifyUtil = new VerifyUtil(requireContext());

        // cek apakah ada otp yang aktif atau tidak
        if (verifyUtil.haveOtp()) {
            // hitung detik untuk mengetahui berapa lama user dapat menekan tombol resend
            totalSeconds = verifyUtil.getResendSeconds();
            LogApp.info(this, LogTag.METHOD, "total second : " + totalSeconds);
            updateSecond();
        } else {
            // jika tidak ada
            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.dia_title_warning)
                    .setMessage(R.string.dia_msg_otp_expired)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dia_positive_out, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArenaFinder.restartApplication(requireContext(), SplashScreenActivity.class);
                        }
                    })
                    .create().show();
        }

        // on back pressed action
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // show dialog confirmation
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.dia_title_confirm)
                        .setMessage(R.string.dia_msg_otp_canceled)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dia_positive_ya, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // stop loading
                                totalSeconds = 0;
                                startActivity(new Intent(requireContext(), EmptyActivity.class).putExtra(EmptyActivity.FRAGMENT, EmptyActivity.WELCOME));
                            }
                        })
                        .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                        })
                        .create().show();
            }
        });

        onClickGroups();
        onListener();
    }

    /**
     * Mengupdate verifikasi
     */
    private void updateVerify() {
        loadingVerify.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RetrofitClient.getInstance().updateVerify(verifyUtil.getEmail()).enqueue(new Callback<VerifyResponse>() {
                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                            loadingVerify.dismiss();
                            // show dialog information
                            new AlertDialog.Builder(requireContext())
                                    .setTitle(R.string.dia_title_inform)
                                    .setMessage(R.string.dia_msg_otp_signup_suc)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.dia_positive_login, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // buka fragment login
                                            FragmentUtil.switchFragmentAccount(
                                                    requireActivity().getSupportFragmentManager(),
                                                    new SignInFragment(),
                                                    false
                                            );
                                            verifyUtil.removeOtp();
                                        }
                                    })
                                    .create().show();
                            verifyUtil.removeOtp();
                        } else {
                            ArenaFinder.VibratorToast(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                        }
                        btnSend.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        loadingVerify.dismiss();
                        ArenaFinder.VibratorToast(requireContext(), t.getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                        btnSend.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                    }
                });
            }
        }, 1500L);
    }

    private void updateButtonName() {
        int minutes = totalSeconds / 60;
        if (totalSeconds > 59) {
            btnSend.setButtonName(getString(R.string.btn_kirim_ulang_wait, minutes, getString(R.string.txt_minut)));
        } else {
            btnSend.setButtonName(getString(R.string.btn_kirim_ulang_wait, totalSeconds, getString(R.string.txt_second)));
        }
    }

    /**
     * Mengupdate dari detik aplikasi
     */
    private void updateSecond() {
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (OtpVerificationFragment.this.totalSeconds > 0) {
                    updateButtonName();
                    OtpVerificationFragment.this.totalSeconds--;
                    handler.postDelayed(this, 1000);
                    LogApp.error(this, LogTag.METHOD, "second : " + totalSeconds);
                } else {
                    btnSend.setStatus(ButtonAccountCustom.ENABLE);
                    btnSend.setButtonName(getResources().getString(R.string.btn_kirim_ulang));
                }
            }
        };
        handler.post(runnable);
    }

    /**
     * Handler aksi saat button-button yang ada didalam fragment di-klik
     */
    private void onClickGroups() {

        /*
         * Aksi saat button send di klik
         */
        btnSend.setOnClickLoadingListener(new ButtonAccountCustom.OnClickLoadingListener() {
            @Override
            public void onClick() {
                Toast.makeText(requireContext(), R.string.txt_update_otp, Toast.LENGTH_SHORT).show();

            /*
                Digunakan untuk mengirim email
             */
                RetrofitClient.getInstance().sendEmail(verifyUtil.getEmail(), verifyUtil.getType(), VerifyUtil.ACTION_UPDATE)
                        .enqueue(new Callback<VerifyResponse>() {
                            @Override
                            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                                    // save data ke preferences
                                    new VerifyUtil(requireContext(), response.body().getData());

                                    // show dialog
                                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                                    new AlertDialog.Builder(requireContext())
                                            .setTitle(R.string.dia_title_inform)
                                            .setMessage(R.string.dia_msg_otp_updated)
                                            .setCancelable(false)
                                            .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> {
                                            })
                                            .create().show();

                                    btnSend.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                                    btnSend.setStatus(ButtonAccountCustom.DISABLE);

                                    // hitung detik untuk mengetahui berapa lama user dapat menekan tombol resend
                                    totalSeconds = verifyUtil.getResendSeconds();
                                    LogApp.info(this, LogTag.METHOD, "total second : " + totalSeconds);
                                    updateSecond();

                                } else {
                                    ArenaFinder.VibratorToast(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                                    btnSend.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                                }
                            }

                            @Override
                            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                                ArenaFinder.VibratorToast(requireContext(), t.getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                                btnSend.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                            }
                        });
            }
        });

    }

    /**
     * Handler aksi saat button-button yang ada didalam fragment di-klik
     */
    private void onListener() {

        /*
         * Aksi saat button input otp di klik
         */
        inpOtp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                helperText.setTextColor(ContextCompat.getColor(requireContext(), R.color.azure));
                helperText.setText(R.string.msg_otp_input);
            }

            @Override
            public void onOTPComplete(@NonNull String otp) {

                if (!verifyUtil.haveOtp()) {
                    helperText.setText(R.string.err_otp_expired);
                    helperText.setTextColor(ContextCompat.getColor(requireContext(), R.color.vivid_orange));
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inpOtp.showError();
                        }
                    });
                } else if (otp.equals(verifyUtil.getOtp())) {
                    helperText.setText(R.string.suc_otp_valid);
                    // show success info
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inpOtp.showSuccess();
                        }
                    });

                    // stop loading button
                    totalSeconds = 0;

                    switch (verifyUtil.getType()) {
                        case "SignUp": {
                            updateVerify();
                            break;
                        }
                        case "ForgotPass": {
                            new AlertDialog.Builder(requireContext())
                                    .setTitle(R.string.dia_title_inform)
                                    .setMessage(R.string.dia_msg_otp_forgot_suc)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // buka fragment ganti sandi
                                            FragmentUtil.switchFragmentAccount(
                                                    requireActivity().getSupportFragmentManager(),
                                                    ChangePasswordFragment.newInstance(verifyUtil.getEmail()),
                                                    false
                                            );
                                            verifyUtil.removeOtp();
                                        }
                                    })
                                    .create().show();
                            break;
                        }
                    }

                } else {
                    helperText.setText(R.string.err_otp_invalid);
                    helperText.setTextColor(ContextCompat.getColor(requireContext(), R.color.vivid_orange));
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
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