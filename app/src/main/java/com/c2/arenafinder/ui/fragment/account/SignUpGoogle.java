package com.c2.arenafinder.ui.fragment.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.UserModel;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpGoogle extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_NAME = "full_name";

    private UsersUtil usersUtil;
    private DataShared dataShared;
    private String email;
    private String fullName;

    private EditText inpUsername, inpPassword;
    private Button btnRegister;

    private void initViews(View view){
        this.inpUsername = view.findViewById(R.id.signupgo_inp_username);
        this.inpPassword = view.findViewById(R.id.signupgo_inp_password);
        this.btnRegister = view.findViewById(R.id.signupgo_register);
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

        onClickGroups();
    }

    private void onClickGroups(){

        btnRegister.setOnClickListener(v -> {

            RetrofitClient.getInstance().registerGoogle(
                    inpUsername.getText().toString(), email,
                    fullName, inpPassword.getText().toString()
            ).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body() != null && RetrofitClient.apakahSukses(response)){
                        UserModel model = response.body().getData();

                        LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "email : " + model.getEmail());

                        dataShared.setData(KEY.ACC_USERNAME, model.getUsername());
                        dataShared.setData(KEY.ACC_EMAIL, model.getEmail());
                        dataShared.setData(KEY.ACC_FULL_NAME, model.getNama());
                        dataShared.setData(KEY.ACC_PASSWORD, model.getPassword());
                        dataShared.setData(KEY.ACC_LEVEL, model.getLevel());
                        dataShared.setData(KEY.ACC_PHOTO, model.getUserPhoto());

                        startActivity(new Intent(requireActivity(), MainActivity.class));

                    }else {
                        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
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

    }

}