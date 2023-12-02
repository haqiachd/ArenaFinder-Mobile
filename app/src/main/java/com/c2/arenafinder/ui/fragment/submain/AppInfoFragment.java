package com.c2.arenafinder.ui.fragment.submain;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c2.arenafinder.R;

public class AppInfoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtAppName, txtVersi, txtDeveloper;

    public AppInfoFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        txtAppName = view.findViewById(R.id.fai_app_name);
        txtVersi = view.findViewById(R.id.fai_version);
        txtDeveloper = view.findViewById(R.id.fai_dev);
    }

    public static AppInfoFragment newInstance(String param1, String param2) {
        AppInfoFragment fragment = new AppInfoFragment();
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
        return inflater.inflate(R.layout.fragment_app_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getAppbar();

        try {
            PackageInfo pInfo = requireActivity().getPackageManager().getPackageInfo(requireActivity().getPackageName(), 0);
            txtAppName.setText(getString(R.string.info_nama_aplikasi_val, getString(R.string.app_name)));
            txtVersi.setText(getString(R.string.info_versi_aplikasi_val, pInfo.versionName));
            txtDeveloper.setText(getString(R.string.info_developer_name_val, getString(R.string.dev_name)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAppbar() {

        if (getActivity() != null) {

            LinearLayout linear = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);

            linear.setVisibility(View.VISIBLE);

            txtTitle.setText(getString(R.string.sub_app_info));

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });
        }
    }

}