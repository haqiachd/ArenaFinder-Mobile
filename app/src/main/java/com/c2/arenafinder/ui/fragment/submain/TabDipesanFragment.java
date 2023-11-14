package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.StatusPesananModel;
import com.c2.arenafinder.data.response.StatusPesananResponse;
import com.c2.arenafinder.ui.adapter.StatusPesananAdapter;
import com.c2.arenafinder.util.UsersUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabDipesanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UsersUtil usersUtil;

    private RecyclerView recyclerView;

    public TabDipesanFragment() {
        // Required empty public constructor
    }

    public void initViews(View root) {
        recyclerView = root.findViewById(R.id.tab_dipesan_recycler);
    }

    public static TabDipesanFragment newInstance(String param1, String param2) {
        TabDipesanFragment fragment = new TabDipesanFragment();
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
        return inflater.inflate(R.layout.fragment_tab_dipesan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        fetchData();
    }

    private void fetchData() {

        RetrofitClient.getInstance().getStatusPesanan(usersUtil.getEmail(), StatusPesananModel.DI_PESAN)
                .enqueue(new Callback<StatusPesananResponse>() {
                    @Override
                    public void onResponse(Call<StatusPesananResponse> call, Response<StatusPesananResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                            showData(response.body().getData());
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusPesananResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showData(ArrayList<StatusPesananModel> models){
        if(models.size() <= 0){
            recyclerView.setVisibility(View.GONE);
        }else {
            if (isAdded()){
                recyclerView.setAdapter(new StatusPesananAdapter(
                        requireContext(), models
                ));
            }
        }
    }

}