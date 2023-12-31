package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.StatusPesananModel;
import com.c2.arenafinder.data.response.StatusPesananResponse;
import com.c2.arenafinder.ui.adapter.StatusPesananAdapter;
import com.c2.arenafinder.util.UsersUtil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabDitolakFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UsersUtil usersUtil;

    private RecyclerView recyclerView;

    private ShimmerFrameLayout shimmerLayout;

    private LinearLayout linearLayout;

    public TabDitolakFragment() {
        // Required empty public constructor
    }

    public void initViews(View root) {
        recyclerView = root.findViewById(R.id.tab_ditolak_recycler);
        shimmerLayout = root.findViewById(R.id.tab_ditolak_shimmer);
        linearLayout = root.findViewById(R.id.tab_ditolak_kosong);
    }

    public static TabDitolakFragment newInstance(String param1, String param2) {
        TabDitolakFragment fragment = new TabDitolakFragment();
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
        return inflater.inflate(R.layout.fragment_tab_ditolak, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        showShimmer(true);
        fetchData();
    }

    private void showShimmer(boolean show){
        if (show){
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            recyclerView.setVisibility(View.GONE);
            showContent(true);
        }else {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showContent(boolean show){
        if (show){
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * Menampilkan data tab ditolak
     */
    private void fetchData() {

        RetrofitClient.getInstance().getStatusPesanan(usersUtil.getEmail(), StatusPesananModel.DI_TOLAK)
                .enqueue(new Callback<StatusPesananResponse>() {
                    @Override
                    public void onResponse(Call<StatusPesananResponse> call, Response<StatusPesananResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                            showData(response.body().getData());
                            showShimmer(false);
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            showShimmer(false);
                            showContent(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusPesananResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        showShimmer(false);
                        showContent(false);
                    }
                });
    }

    private void showData(ArrayList<StatusPesananModel> models){
        if(models.size() <= 0){
            showContent(false);
        }else {
            if (isAdded()){
                showContent(true);
                recyclerView.setAdapter(new StatusPesananAdapter(
                        requireContext(), models
                ));
            }
        }
    }

}