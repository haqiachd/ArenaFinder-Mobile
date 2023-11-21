package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.repository.UsersRepository;
import com.c2.arenafinder.di.UsersViewModelFactory;
import com.c2.arenafinder.ui.custom.ButtonAccountCustom;
import com.c2.arenafinder.util.UsersUtil;
import com.c2.arenafinder.util.ValidatorUtil;
import com.c2.arenafinder.viewmodel.UsersViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ChangePwOnProfile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UsersViewModel usersViewModel;
    private UsersUtil usersUtil;
    private ValidatorUtil validatorUtil;

    private ButtonAccountCustom btnGantiPw;
    private TextInputEditText inpPass, inpNewPass, inpKonf;
    private TextView txtHelper;

    public ChangePwOnProfile() {
        // Required empty public constructor
    }

    private void initViews(View view){
        inpPass = view.findViewById(R.id.chgpwpro_old_inp_pass_now);
        inpNewPass = view.findViewById(R.id.chgpwpro_inp_pass_new);
        inpKonf = view.findViewById(R.id.chgpwpro_inp_konf);
        txtHelper = view.findViewById(R.id.chgpwpro_txt_helper);
        btnGantiPw = new ButtonAccountCustom(requireActivity(), view, R.string.btn_ganti_password);
    }

    public static ChangePwOnProfile newInstance(String param1, String param2) {
        ChangePwOnProfile fragment = new ChangePwOnProfile();
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
        return inflater.inflate(R.layout.fragment_change_pw_on_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        usersViewModel = new ViewModelProvider(
                requireActivity(),
                new UsersViewModelFactory(new UsersRepository())
        ).get(UsersViewModel.class);

        usersUtil = new UsersUtil(requireActivity());
        validatorUtil = new ValidatorUtil(requireActivity(), btnGantiPw, txtHelper);

        getAppbar();
        onCLickGroups();
        onChangedListener();

        if(getView() != null){
            observer();
        }
    }

    private void getAppbar() {

        if (getActivity() != null) {

            LinearLayout linear = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);

            linear.setVisibility(View.VISIBLE);

            txtTitle.setText(getString(R.string.sub_change_pass));

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });
        }
    }

    private void observer(){
        usersViewModel.changePassLogin().observe(getViewLifecycleOwner(), dataState -> {
            if (dataState instanceof RetrofitState.Loading){
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_LOADING,"CHANGE ON LOADING");
            }
            else if(dataState instanceof RetrofitState.Error){
                LogApp.error(requireContext(), LogTag.RETROFIT_ON_FAILURE, "CHANGE ON FAILURE");
                Toast.makeText(requireActivity(), ((RetrofitState.Error) dataState).getMessage(), Toast.LENGTH_SHORT).show();
                txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
                txtHelper.setText(((RetrofitState.Error) dataState).getMessage());
                btnGantiPw.setProgress(ButtonAccountCustom.KILL_PROGRESS);
            }
            else if(dataState instanceof RetrofitState.Success){
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE,"CHANGE ON RESPONSE");
                Toast.makeText(requireActivity(), "Password Berhasil Diganti", Toast.LENGTH_SHORT).show();
                btnGantiPw.setProgress(ButtonAccountCustom.KILL_PROGRESS);
                requireActivity().onBackPressed();
            }
        });
    }

    private void onCLickGroups(){

        btnGantiPw.setOnClickLoadingListener(() -> {
            usersViewModel.doChangePassLogin(
                    usersUtil.getEmail(), Objects.requireNonNull(inpPass.getText()).toString(),
                    Objects.requireNonNull(inpNewPass.getText()).toString()
            );
        });

    }

    private void onChangedListener(){

        TextInputEditText[] editTexts = {inpNewPass, inpKonf};

       inpPass.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {}

           @Override
           public void afterTextChanged(Editable s) {
               if (!validatorUtil.isValidPassword(Objects.requireNonNull(inpPass.getText()).toString())){
                   txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
                   txtHelper.setText(validatorUtil.getMessage());
               }
           }
       });

       for (TextInputEditText editText : editTexts){
           editText.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {}

               @Override
               public void afterTextChanged(Editable s) {
                   validatorUtil.inputPassword(
                           Objects.requireNonNull(inpNewPass.getText()).toString(),
                           Objects.requireNonNull(inpKonf.getText()).toString()
                   );
               }
           });
       }

    }
}