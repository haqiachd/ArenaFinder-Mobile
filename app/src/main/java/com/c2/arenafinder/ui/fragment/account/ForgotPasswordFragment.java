package com.c2.arenafinder.ui.fragment.account;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.data.response.VerifyResponse;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText inpEmail;
    private ButtonAccountCustom btnSend;

    private String mParam1;
    private String mParam2;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        inpEmail = view.findViewById(R.id.forgot_inp_email);
        btnSend = new ButtonAccountCustom(requireContext(), view, R.string.btn_send_otp);
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

        onClickGroups();
    }

    public void onClickGroups(){

        btnSend.setOnClickLoadingListener(() -> {

        RetrofitClient.getInstance().sendEmail(inpEmail.getText().toString(), "forgotpass")
            .enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){
                        new AlertDialog.Builder(requireContext())
                                .setTitle(R.string.dia_title_inform)
                                .setMessage(R.string.dia_msg_inform_forgot)
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
                    }else {
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VerifyResponse> call, Throwable t) {
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

//            RetrofitClient.getInstance().cekUser(inpEmail.getText().toString())
//                    .enqueue(new Callback<UsersResponse>() {
//                        @Override
//                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
//                            if (response.body() != null && RetrofitClient.apakahSukses(response)){
//
//
//                            }else {
//                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<UsersResponse> call, Throwable t) {
//                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
//                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
        });

    }
}