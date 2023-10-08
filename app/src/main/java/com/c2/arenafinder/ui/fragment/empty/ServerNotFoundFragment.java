package com.c2.arenafinder.ui.fragment.empty;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.ui.activity.SplashScreenActivity;
import com.c2.arenafinder.util.ArenaFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerNotFoundFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout refreshLayout;
    private TextView txtDesc;

    public ServerNotFoundFragment() {
        // Required empty public constructor
    }

    private void initViews(View root){
        refreshLayout = root.findViewById(R.id.fsnf_swipe);
        txtDesc = root.findViewById(R.id.fsnf_txt_deks);
    }

    public static ServerNotFoundFragment newInstance(String param1, String param2) {
        ServerNotFoundFragment fragment = new ServerNotFoundFragment();
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
        return inflater.inflate(R.layout.fragment_server_not_found, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        // membuat object spannable untuk mewarnai string dalam text
        SpannableString spannableString = new SpannableString(
                getString(R.string.txt_fsnf_server_desk, RetrofitClient.BASE_URL)
        );
        // atur warna, start dan end dari string yang diwarnai
        spannableString.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.butterfly_blue)),
                29, 29+RetrofitClient.BASE_URL.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
        );

        txtDesc.setText(spannableString);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshAction();
                        refreshLayout.setRefreshing(false);
                    }
                }, ArenaFinder.MILLIS_OF_REFRESHING);
            }
        });

    }

    private void refreshAction(){

        RetrofitClient.getInstance().cekKoneksi().enqueue(new Callback<ArenaFinderResponse>() {
            @Override
            public void onResponse(@Nullable  Call<ArenaFinderResponse> call, @Nullable  Response<ArenaFinderResponse> response) {
                assert response != null;
                if(response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){
                    ArenaFinder.restartApplication(requireContext(), SplashScreenActivity.class);
                }else {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    Toast.makeText(requireContext(), R.string.err_server_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@Nullable  Call<ArenaFinderResponse> call, @Nullable  Throwable t) {
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                Toast.makeText(requireContext(), R.string.err_server_not_found, Toast.LENGTH_SHORT).show();
            }
        });

    }
}