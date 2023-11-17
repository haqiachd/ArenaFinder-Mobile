package com.c2.arenafinder.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.response.AktivitasResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.activity.SubMainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasFirstAdapter;
import com.c2.arenafinder.ui.adapter.AktivitasSecondAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktivitasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout refreshLayout;

    private View btnVallBaru, btnVallKosong;

    private MaterialButton btnFilter;

    private LinearLayout aktivitasBaruLayout, aktivitasKosongLayout, semuaAktivitasLayout;

    private RecyclerView jenisLapangan, aktivitasBaruRecycler, aktivitasKosongRecycler, semuaAktivitasRecycler;

    public AktivitasFragment() {
        // Required empty public constructor
    }

    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.mak_refresh);
        btnVallBaru = view.findViewById(R.id.mak_vall_baru);
        btnVallKosong = view.findViewById(R.id.mak_vall_kosong);
        btnFilter = view.findViewById(R.id.mak_btn_filter);

        aktivitasBaruLayout = view.findViewById(R.id.mak_aktivitas_baru_layout);
        aktivitasKosongLayout = view.findViewById(R.id.mak_aktivitas_kosong_layout);
        semuaAktivitasLayout = view.findViewById(R.id.mak_semua_aktivitas_layout);
        jenisLapangan = view.findViewById(R.id.mak_recycler_jenis);
        semuaAktivitasRecycler = view.findViewById(R.id.mak_recycler_semua);
        aktivitasBaruRecycler = view.findViewById(R.id.mak_recycler_aktivitas_baru);
        aktivitasKosongRecycler = view.findViewById(R.id.mak_recycler_aktivitas_kosong);
    }

    public static AktivitasFragment newInstance(String param1, String param2) {
        AktivitasFragment fragment = new AktivitasFragment();
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
        return inflater.inflate(R.layout.fragment_aktivitas, container, false);
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
                        fetchData();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1500L);
            }
        });

        if (isAdded()) {
            fetchData();
            adapterLapangan();
            onClickGroups();
            getAppbar();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAppbar(){
        if (getActivity() != null){
            MaterialCardView cardSearch = getActivity().findViewById(R.id.main_appbar_search);
            cardSearch.setOnClickListener(v -> {
                startActivity(
                        new Intent(requireActivity(), SubMainActivity.class)
                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SEARCH_WORLD)
                                .putExtra(SubMainActivity.SEARCH_TYPE, SearchWorldFragment.SEARCH_ACTIVITY)
                );
            });
        }
    }

    private void onClickGroups() {

        btnFilter.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "Filter", Toast.LENGTH_SHORT).show();
        });

        btnVallBaru.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.AKTIVITAS_BARU)
            );
        });

        btnVallKosong.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.AKTIVITAS_KOSONG)
            );
        });

    }

    private void adapterLapangan() {
        ArrayList<JenisLapanganModel> lapanganModels = ArenaFinder.getSportType();

        JenisLapanganAdapter lapanganAdapter = new JenisLapanganAdapter(requireActivity(), lapanganModels,
                new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), SubMainActivity.class)
                                        .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SPORT_TYPE)
                                        .putExtra(SubMainActivity.SPORT_ACTION, Integer.toString(SportTypeFragment.TYPE_ACTIVITY))
                                        .putExtra(SubMainActivity.SPORT_DATA, lapanganModels.get(position).getNamaLapangan())
                        );
                    }
                });
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    private void fetchData() {

        RetrofitClient.getInstance().aktivitasPage().enqueue(new Callback<AktivitasResponse>() {
            @Override
            public void onResponse(Call<AktivitasResponse> call, Response<AktivitasResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                    LogApp.info(this, LogTag.RETROFIT_ON_RESPONSE, "ON RESPONSE");
                    AktivitasResponse.Data data = response.body().getData();

                    // get data model
                    ArrayList<AktivitasModel> aktivitasBaru = data.getAktivitasBaru();
                    ArrayList<AktivitasModel> akivitasKosong = data.getAktivitasKosong();
                    ArrayList<AktivitasModel> semuaAktivitas = data.getSemuaAktivitas();

                    if (aktivitasBaru.size() == 0 && akivitasKosong.size() == 0 && semuaAktivitas.size() == 0) {
                        handlerNullData();
                    } else {

                        // show recycler data
                        showAktivitasBaru(aktivitasBaru);
                        showAktivitasKosong(akivitasKosong);
                        showSemuaAktivitasyList(semuaAktivitas);
                    }

                } else {
                    handlerNullData();
                    Toast.makeText(requireActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AktivitasResponse> call, Throwable t) {
                handlerNullData();
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ArenaFinder.VibratorToast(requireActivity(), t.getMessage(), Toast.LENGTH_LONG, ArenaFinder.VIBRATOR_MEDIUM);
            }
        });

    }

    private void handlerNullData() {
        aktivitasKosongLayout.setVisibility(View.GONE);
        aktivitasBaruLayout.setVisibility(View.GONE);
        semuaAktivitasLayout.setVisibility(View.GONE);
    }

    private void showAktivitasBaru(ArrayList<AktivitasModel> models) {

        if (models.size() == 0) {
            aktivitasBaruLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), "aktivitas baru size -> " + models.size());
                aktivitasBaruRecycler.setAdapter(new AktivitasFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.ACTIVITY)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidAktvitias()))
                        );
                    }
                }
                ));

                ArenaFinder.setRecyclerWidthByItem(requireActivity(), aktivitasBaruRecycler, models.size(), R.dimen.card_activity_first_width_java);
            }
        }

    }

    private void showAktivitasKosong(ArrayList<AktivitasModel> models) {

        if (models.size() == 0) {
            aktivitasKosongLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), "aktivitas kosong size -> " + models.size());
                aktivitasKosongRecycler.setAdapter(new AktivitasFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.ACTIVITY)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidAktvitias()))
                        );
                    }
                }
                ));

                ArenaFinder.setRecyclerWidthByItem(requireActivity(), aktivitasBaruRecycler, models.size(), R.dimen.card_activity_first_width_java);
            }
        }

    }

    private void showSemuaAktivitasyList(ArrayList<AktivitasModel> models) {

        if (models.size() == 0) {
            semuaAktivitasLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), "semua aktivitas size -> " + models.size());
                semuaAktivitasRecycler.setAdapter(new AktivitasSecondAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.ACTIVITY)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidAktvitias()))
                        );
                    }
                }
                ));
            }
        }

    }

}