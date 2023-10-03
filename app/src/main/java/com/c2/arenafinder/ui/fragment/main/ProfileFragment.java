package com.c2.arenafinder.ui.fragment.main;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.ui.activity.AccountActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UsersUtil usersUtil;
    private Button btnLogout;
    private TextView txtUsername, txtEmail, txtName, txtLevel;

    private void initViews(View view){
        btnLogout = view.findViewById(R.id.mpr_btn_logout);
        txtUsername = view.findViewById(R.id.mpr_txt_username);
        txtEmail = view.findViewById(R.id.mpr_txt_email);
        txtName = view.findViewById(R.id.mpr_txt_nama);
        txtLevel = view.findViewById(R.id.mpr_txt_level);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        txtUsername.setText(usersUtil.getUsername());
        txtEmail.setText(usersUtil.getEmail());
        txtName.setText(usersUtil.getFullName());
        txtLevel.setText(usersUtil.getLevel());

        onClickGroups();
    }

    private void onClickGroups(){

        btnLogout.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Logout Account");
            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            // konfirmasi logout
            ArenaFinder.showAlertDialog(
                    requireContext(), getString(R.string.dia_title_confirm), getString(R.string.dia_msg_confirm_logout),
                    false,
                    (dialog, which) -> {
                        usersUtil.signOut();
                        if (!usersUtil.isSignIn()) {
                            Toast.makeText(requireContext(), "Logout Sukses", Toast.LENGTH_SHORT).show();
                            ArenaFinder.restartApplication(requireContext(), AccountActivity.class);
                        }
                    },
                    (dialog, which) -> {}
            );
        });

    }
}