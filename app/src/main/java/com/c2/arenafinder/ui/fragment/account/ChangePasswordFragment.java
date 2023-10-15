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
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.ValidatorUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private static final String ARG_EMAIL = "email";

    private String email;
    private ValidatorUtil validator;

    private EditText inpPassword, inpKonf;
    private ButtonAccountCustom btnChange;
    private TextView txtHelper;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        btnChange = new ButtonAccountCustom(requireContext(), view, R.string.btn_ganti_password);
        inpPassword = view.findViewById(R.id.chgpass_inp_pass);
        inpKonf = view.findViewById(R.id.chgpass_inp_konf);
        txtHelper = view.findViewById(R.id.chgpass_txt_helper);
    }

    public static ChangePasswordFragment newInstance(String email) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        validator = new ValidatorUtil(requireContext(), btnChange, txtHelper);

        onClickGroups();
        onChangedGroups();
    }

    private void onClickGroups(){

        btnChange.setOnClickLoadingListener(() -> {;

            RetrofitClient.getInstance().updatePassword(email, inpPassword.getText().toString())
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if (response.body() != null && RetrofitClient.apakahSukses(response)){

                                new AlertDialog.Builder(requireContext())
                                        .setTitle(R.string.dia_title_inform)
                                        .setMessage(R.string.dia_msg_password_changed)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.dia_positive_login, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                FragmentUtil.switchFragmentAccount(
                                                        requireActivity().getSupportFragmentManager(), new SignInFragment(), false
                                                );

                                            }
                                        })
                                        .create()
                                        .show();

                            }else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            btnChange.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            btnChange.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                        }
                    });

        });

    }

    private void onChangedGroups(){

        EditText[] inputs = {inpPassword, inpKonf};

        for (EditText input : inputs){
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    validator.inputPassword(inpPassword.getText().toString(), inpKonf.getText().toString());
                }
            });
        }

    }
}