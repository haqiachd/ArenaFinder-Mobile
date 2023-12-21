package com.c2.arenafinder.ui.fragment.empty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.response.ArenaFinderResponse;
import com.c2.arenafinder.ui.activity.SplashScreenActivity;
import com.c2.arenafinder.util.ArenaFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerOffFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout refreshLayout;

    private void initViews(View root){
        refreshLayout = root.findViewById(R.id.fso_swipe);
    }

    public ServerOffFragment() {
        // Required empty public constructor
    }

    public static ServerOffFragment newInstance(String param1, String param2) {
        ServerOffFragment fragment = new ServerOffFragment();
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
        return inflater.inflate(R.layout.fragment_server_off, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

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

    /**
     * aksi saat refersh
     */
    private void refreshAction(){

        RetrofitClient.getInstance().cekKoneksi().enqueue(new Callback<ArenaFinderResponse>() {
            @Override
            public void onResponse(@Nullable Call<ArenaFinderResponse> call, @Nullable Response<ArenaFinderResponse> response) {
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