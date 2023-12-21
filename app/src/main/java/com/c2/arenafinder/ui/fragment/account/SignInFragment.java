package com.c2.arenafinder.ui.fragment.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.UsersUtil;
import com.c2.arenafinder.util.ValidatorUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.google.GoogleUsers;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.api.retrofit.RetrofitEndPoint;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.UUID;

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
    private ValidatorUtil validator;

    private ImageView btnGoogle;
    private TextView btnForgot, btnSignUp, txtHelper;
    private TextInputEditText inpEmail, inpPassword;
    private ButtonAccountCustom btnLogin;

    private void initViews(View view) {
        btnSignUp = view.findViewById(R.id.signin_btn_register);
        btnGoogle = view.findViewById(R.id.signin_btn_google);
        btnForgot = view.findViewById(R.id.signin_btn_lupa_sandi);
        inpEmail = view.findViewById(R.id.signin_inp_email);
        inpPassword = view.findViewById(R.id.signin_inp_pass);
        txtHelper = view.findViewById(R.id.signin_txt_helper);
        btnLogin = new ButtonAccountCustom(requireContext(), view, R.string.btn_sign_in);
    }

    public SignInFragment() {
    }

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
        validator = new ValidatorUtil(requireContext(), btnLogin, txtHelper);


        String btnRegisterTxt = getString(R.string.txt_register_here);
        String btnLupaPasswordTxt = getString(R.string.txt_forgot_pass);

        // set underline on txt signup
        SpannableString spanSignUp = new SpannableString(btnRegisterTxt);
        spanSignUp.setSpan(new UnderlineSpan(), 0, btnRegisterTxt.length(), 0);
        btnSignUp.setText(spanSignUp);

        // set underline on txt forgot pass
        SpannableString spanForgot = new SpannableString(btnLupaPasswordTxt);
        spanForgot.setSpan(new UnderlineSpan(), 0, btnLupaPasswordTxt.length(), 0);
        btnForgot.setText(spanForgot);

        onClickGroups();
        onChangedListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        google.onActivityResult(requestCode, resultCode, data);

        if (google.isAccountSelected()) {

            if (google.getUserData().getPhotoUrl() != null) {
                LogApp.info(requireContext(), String.valueOf(google.getUserData().getPhotoUrl()));
            }

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    // Get the device token here
                    String token = task.getResult();
                    Log.d("MyFirebaseMessaging", "Device Token: " + token);

                    dataShared.setData(DataShared.KEY.SAVED_DEVICE_TOKEN, token);

                    // Send the token to your server
                    RetrofitEndPoint endPoint = RetrofitClient.getInstance();
                    endPoint.loginGoogle(google.getUserData().getEmail(), token)
                            .enqueue(new Callback<UsersResponse>() {
                                @Override
                                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                                        // saving data ke preferences
                                        new UsersUtil(requireContext(), response.body().getData());

                                        // open main activity
                                        Toast.makeText(SignInFragment.this.requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignInFragment.this.requireActivity(), MainActivity.class));
                                    } else {
                                        Toast.makeText(SignInFragment.this.requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UsersResponse> call, Throwable t) {
                                    ArenaFinder.playVibrator(requireActivity(), ArenaFinder.VIBRATOR_MEDIUM);
                                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

        }
    }

    /**
     * Handler aksi saat button-button yang ada didalam fragment di-klik
     */
    private void onClickGroups() {

        /*
         * Aksi saat button forgot di klik
         */
        btnForgot.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.acc_frame_layout, new ForgotPasswordFragment())
                    .addToBackStack(null)
                    .commit();
        });

        /*
         * Aksi saat button google di klik
         */
        btnGoogle.setOnClickListener(v -> {
            LogApp.info(requireContext(), v, "Button Google diclick");

            // jika terhubung internet
            if (ArenaFinder.isInternetConnected(requireContext())) {
                if (google == null) {
                    google = new GoogleUsers(requireActivity());
                } else {
                    google.resetLastSignIn();
                }
                // membuka google sign-in intent
                startActivityForResult(google.getIntent(), GoogleUsers.REQUEST_CODE);
            } else {
                LogApp.warn(requireContext(), LogTag.GOOGLE_SIGN, "no internet connection");
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                Toast.makeText(requireContext(), getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * Aksi saat button sign-up di klik
         */
        btnSignUp.setOnClickListener(v -> {
            FragmentUtil.switchFragmentAccount(requireActivity().getSupportFragmentManager(), new SignUpTypeFragment(), false);
        });

        /*
         * Aksi saat button login di klik
         */
        btnLogin.setOnClickLoadingListener(() -> {
            String email = inpEmail.getText().toString(),
                    password = inpPassword.getText().toString();

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    // Get the device token here
                    String token = task.getResult();
                    Log.d("MyFirebaseMessaging", "Device Token: " + token);

                    dataShared.setData(DataShared.KEY.SAVED_DEVICE_TOKEN, token);

                    // Send the token to your server
                    RetrofitEndPoint endPoint = RetrofitClient.getConnection().create(RetrofitEndPoint.class);
                    Call<UsersResponse> responseCall = endPoint.login(email, password, token);
                    responseCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if (response.body() != null && response.body().getStatus().equals("success")) {
                                // saving data ke preferences
                                new UsersUtil(requireContext(), response.body().getData());

                                // open main activity
                                Toast.makeText(SignInFragment.this.requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInFragment.this.requireActivity(), MainActivity.class));
                            } else {
                                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                                txtHelper.setText(response.body().getMessage());
                                txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
                                Toast.makeText(SignInFragment.this.requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            btnLogin.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_MEDIUM);
                            Toast.makeText(SignInFragment.this.requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            btnLogin.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }

    private void onChangedListener() {

        EditText[] inputs = {inpEmail, inpPassword};

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
                    validator.signinValidation(inpEmail.getText().toString(), inpPassword.getText().toString());
                }
            });
        }
    }
}