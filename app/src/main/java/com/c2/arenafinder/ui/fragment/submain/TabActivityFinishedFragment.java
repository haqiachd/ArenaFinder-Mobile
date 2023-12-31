package com.c2.arenafinder.ui.fragment.submain;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.AktivitasMemberModel;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.response.AktivitasMemberResponse;
import com.c2.arenafinder.data.response.AktivitasStatusResponse;
import com.c2.arenafinder.ui.adapter.StatusAktivitasAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabActivityFinishedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UsersUtil usersUtil;

    private RecyclerView recyclerView;

    private ShimmerFrameLayout shimmerLayout;

    private LinearLayout linearLayout;

    public TabActivityFinishedFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.tab_finished_recycler);
        shimmerLayout = view.findViewById(R.id.tab_finished_shimmer);
        linearLayout = view.findViewById(R.id.tab_finished_kosong);
    }

    public static TabActivityFinishedFragment newInstance(String param1, String param2) {
        TabActivityFinishedFragment fragment = new TabActivityFinishedFragment();
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
        return inflater.inflate(R.layout.fragment_tab_activity_finished, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        showShimmer(true);
        fetchData();
    }

    private void showShimmer(boolean show) {
        if (show) {
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            recyclerView.setVisibility(View.GONE);
            showContent(true);
        } else {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showContent(boolean show) {
        if (show) {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * Menampilkan data tab finished
     */
    private void fetchData() {

        RetrofitClient.getInstance().statusActivity(
                usersUtil.getEmail(), AktivitasModel.STATUS_FINISHED
        ).enqueue(new Callback<AktivitasStatusResponse>() {
            @Override
            public void onResponse(Call<AktivitasStatusResponse> call, Response<AktivitasStatusResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    ArrayList<AktivitasModel> models = response.body().getData();
                    showData(models);
                    showShimmer(false);
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    showShimmer(false);
                    showContent(false);
                }
            }

            @Override
            public void onFailure(Call<AktivitasStatusResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                showShimmer(false);
                showContent(false);
            }
        });
    }

    private void showData(ArrayList<AktivitasModel> models) {
        if (models.size() <= 0) {
            showContent(false);
        } else {
            if (isAdded()) {
                showContent(true);
                recyclerView.setAdapter(
                        new StatusAktivitasAdapter(
                                requireContext(), models, new AdapterActionListener() {
                            @Override
                            public void onClickListener(int position) {
                                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                                new AlertDialog.Builder(requireContext())
                                        .setMessage(R.string.dia_title_warning)
                                        .setMessage("Apakah kamu yakin ingin keluar dari aktivitas " + models.get(position).getNamaAktivitas() + "?")
                                        .setCancelable(true)
                                        .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                action(Integer.toString(models.get(position).getidAktvitias()));
                                            }
                                        }).create().show();
                            }
                        }, true)
                );
            }else {
                showContent(false);
            }
        }
    }

    private void action(String id) {
        RetrofitClient.getInstance().leaveActivity(
                new AktivitasMemberModel(id, usersUtil.getEmail())
        ).enqueue(new Callback<AktivitasMemberResponse>() {
            @Override
            public void onResponse(Call<AktivitasMemberResponse> call, Response<AktivitasMemberResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    new AlertDialog.Builder(requireActivity())
                            .setTitle(R.string.dia_title_inform)
                            .setMessage(response.body().getMessage())
                            .setCancelable(false)
                            .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fetchData();
                                }
                            })
                            .create().show();
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AktivitasMemberResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}