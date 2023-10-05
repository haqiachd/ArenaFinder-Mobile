package com.c2.arenafinder.ui.fragment.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.google.GoogleUsers;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragmentFirst extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleUsers google;

    private EditText inpUsername, inpEmail, inpName, inpPassword;
    private MaterialButton btnSignUp;
    private ImageView btnGoogle;

    private void initViews(View view){
        inpUsername = view.findViewById(R.id.signup1_inp_username);
        inpEmail = view.findViewById(R.id.signup1_inp_email);
        inpName = view.findViewById(R.id.signup1_inp_name);
        inpPassword = view.findViewById(R.id.signup1_inp_password);
        btnSignUp = view.findViewById(R.id.signup1_next);
        btnGoogle = view.findViewById(R.id.signup_google);
    }

    public SignUpFragmentFirst() {
        // Required empty public constructor
    }

    public static SignUpFragmentFirst newInstance(String param1, String param2) {
        SignUpFragmentFirst fragment = new SignUpFragmentFirst();
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
        return inflater.inflate(R.layout.fragment_sign_up_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClickGroups();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        google.onActivityResult(requestCode, resultCode, data);

        if(google.isAccountSelected()){

            GoogleSignInAccount account = google.getUserData();

            RetrofitClient.getInstance().cekUser(account.getEmail()).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body() != null && RetrofitClient.apakahSukses(response)){
                        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                        new AlertDialog.Builder(requireContext())
                                .setTitle(R.string.dia_title_warning)
                                .setMessage(R.string.dia_msg_account_is_registered)
                                .setCancelable(false)
                                .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .create().show();
                    }else {
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

        btnSignUp.setOnClickListener(v -> {
            String username = inpUsername.getText().toString(),
                   email = inpEmail.getText().toString(),
                   fullName = inpName.getText().toString(),
                   password = inpPassword.getText().toString();

            RetrofitClient.getInstance().register(username, email, fullName, password)
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if(RetrofitClient.apakahSukses(response)){
                                ArenaFinder.showAlertDialog(
                                        requireContext(), getString(R.string.dia_title_inform), getString(R.string.dia_msg_inform_signup),
                                        false,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignInFragment(), false);
                                            }
                                        },
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignInFragment(), false);
                                            }
                                        }
                                );
                            }else {
                                assert response.body() != null;
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnGoogle.setOnClickListener(v -> {
            LogApp.info(requireContext(), v, LogTag.ON_CLICK, "Button google di click");

            if(ArenaFinder.isInternetConnected(requireContext())){
                if(google == null){
                    google = new GoogleUsers(requireActivity());
                }else {
                    google.resetLastSignIn();
                }
                startActivityForResult(google.getIntent(), GoogleUsers.REQUEST_CODE);
            }else{
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_MEDIUM);
                Toast.makeText(requireContext(), getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            }
        });

    }
}