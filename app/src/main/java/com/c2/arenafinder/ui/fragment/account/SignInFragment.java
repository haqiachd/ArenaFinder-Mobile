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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.data.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.google.GoogleUsers;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.api.retrofit.RetrofitEndPoint;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.util.ArenaFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleUsers google;
    private DataShared dataShared;

    private Button btnSignIn;
    private ImageView btnGoogle;
    private TextView txtLupaSandi;
    private TextInputEditText inpEmail, inpPassword;

    private void initViews(View view){
        btnSignIn = view.findViewById(R.id.signin_btn_signin);
        btnGoogle = view.findViewById(R.id.signin_google);
        txtLupaSandi = view.findViewById(R.id.signin_lupa_sandi);
        inpEmail = view.findViewById(R.id.signin_inp_email);
        inpPassword = view.findViewById(R.id.signin_inp_pass);
    }

    public SignInFragment() {}

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        dataShared = new DataShared(requireContext());

        onClickGroups();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        google.onActivityResult(requestCode, resultCode, data);

        if (google.isAccountSelected()){
            Toast.makeText(requireContext(), google.getUserData().getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickGroups(){

        txtLupaSandi.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.acc_frame_layout, new ForgotPasswordFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnSignIn.setOnClickListener(v -> {
            String email = inpEmail.getText().toString(),
                   password = inpPassword.getText().toString();


            RetrofitEndPoint endPoint = RetrofitClient.getConnection().create(RetrofitEndPoint.class);
            Call<UsersResponse> responseCall = endPoint.login(email, password);
            responseCall.enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body().getStatus().equals("success")){
                        // save data ke preferences
                        UserModel data = response.body().getData();
                        dataShared.setData(KEY.ACC_USERNAME, data.getUsername());
                        dataShared.setData(KEY.ACC_EMAIL, data.getEmail());
                        dataShared.setData(KEY.ACC_FULL_NAME, data.getNama());
                        dataShared.setData(KEY.ACC_LEVEL, data.getLevel());

                        // open main activity
                        Toast.makeText(SignInFragment.this.requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInFragment.this.requireActivity(), MainActivity.class));
                    }else {
                        Toast.makeText(SignInFragment.this.requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    Toast.makeText(SignInFragment.this.requireContext(), "Koneksi Failure", Toast.LENGTH_SHORT).show();
                }
            });

        });

        btnGoogle.setOnClickListener(v -> {
            LogApp.info(requireContext(), v, "Button Google diclick");

            // jika terhubung internet
            if (ArenaFinder.isInternetConnected(requireContext())){
                if (google == null){
                    google = new GoogleUsers(requireActivity());
                }else {
                    google.resetLastSignIn();
                }
                // membuka google sign-in intent
                startActivityForResult(google.getIntent(), GoogleUsers.REQUEST_CODE);
            }else {
                LogApp.warn(requireContext(), LogTag.GOOGLE_SIGN, "no internet connection");
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                Toast.makeText(requireContext(), getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            }
        });

    }
}