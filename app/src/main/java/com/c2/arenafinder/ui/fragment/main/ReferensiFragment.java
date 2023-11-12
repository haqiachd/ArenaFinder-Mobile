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
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.response.ReferensiResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.activity.SubMainActivity;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.adapter.VenueSecondAdapter;
import com.c2.arenafinder.ui.adapter.VenueThirdAdapter;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferensiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwipeRefreshLayout refreshLayout;

    private MaterialButton btnFilter;

    private View btnVallRatting, btnVallKosong, btnValLokasi, btnVallGratis, btnVallBerbayar, btnVallDisewakan;

    private LinearLayout topRattingLayout, venueKosongLayout, venueLokasiLayout, venueGratisLayout, venueBebayarLayout, venueDisewakanLayout;

    private RecyclerView jenisLapangan, venueRattingRecycler, venueKosongRecycler, venueTerdekatRecycler,
            venueGratisRecycler, venueBerbayarRecycler, venueDisewakanRecycler;

    private String mParam1;
    private String mParam2;

    public ReferensiFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        refreshLayout = view.findViewById(R.id.mre_refresh_layout);
        topRattingLayout = view.findViewById(R.id.mre_top_ratting_layout);
        venueKosongLayout = view.findViewById(R.id.mre_venue_kosong_layout);
        venueLokasiLayout = view.findViewById(R.id.mre_venue_lokasi_layout);
        venueGratisLayout = view.findViewById(R.id.mre_venue_gratis_layout);
        venueBebayarLayout = view.findViewById(R.id.mre_venue_berbayar_layout);
        venueDisewakanLayout = view.findViewById(R.id.mre_venue_disewakan_layout);
        btnFilter = view.findViewById(R.id.mre_btn_filter);

        jenisLapangan = view.findViewById(R.id.mre_recycler_jenis);
        venueRattingRecycler = view.findViewById(R.id.mre_recycler_ratting);
        venueKosongRecycler = view.findViewById(R.id.mre_recycler_venue_kosong);
        venueTerdekatRecycler = view.findViewById(R.id.mre_recycler_venue_terdekat);
        venueGratisRecycler = view.findViewById(R.id.mre_recycler_venue_gratis);
        venueBerbayarRecycler = view.findViewById(R.id.mre_recycler_venue_berbayar);
        venueDisewakanRecycler = view.findViewById(R.id.mre_recycler_venue_disewakan);

        btnVallRatting = view.findViewById(R.id.mre_vall_ratting);
        btnVallKosong = view.findViewById(R.id.mre_vall_kosong);
        btnValLokasi = view.findViewById(R.id.mre_vall_lokasi);
        btnVallGratis = view.findViewById(R.id.mre_vall_gratis);
        btnVallBerbayar = view.findViewById(R.id.mre_vall_berbayar);
        btnVallDisewakan = view.findViewById(R.id.mre_vall_disewakan);
    }

    public static ReferensiFragment newInstance(String param1, String param2) {
        ReferensiFragment fragment = new ReferensiFragment();
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
        return inflater.inflate(R.layout.fragment_referensi, container, false);
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
            LogApp.info(requireContext(), "PREPARING LOAD DATA TO SERVER");
            fetchData();
            adapterLapangan();
            onClickGroups();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onClickGroups() {

        btnFilter.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "Filter", Toast.LENGTH_SHORT).show();
        });

        btnVallRatting.setOnClickListener(v ->
                startActivity(
                        new Intent(requireActivity(), SubMainActivity.class)
                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                                .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_RATING)
                )
        );

        btnVallKosong.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_KOSONG)
            );
        });

        btnValLokasi.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_LOKASI)
            );
        });

        btnVallGratis.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_GRATIS)
            );
        });

        btnVallBerbayar.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_BERBAYAR)
            );
        });

        btnVallDisewakan.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_DISEWAKAN)
            );
        });

    }

    private void adapterLapangan() {
        ArrayList<JenisLapanganModel> lapanganModels = new ArrayList<>();
//        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_all, "Semua"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_sepak_bola, "Sepak Bola"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_badminton, "Bulu Tangkis"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_voli, "Bola Voli"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_basket, "Bola Basket"));

        JenisLapanganAdapter lapanganAdapter = new JenisLapanganAdapter(requireActivity(), lapanganModels,
                new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), SubMainActivity.class)
                                        .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SPORT_TYPE)
                                        .putExtra(SubMainActivity.SPORT_ACTION, SportTypeFragment.TYPE_VENUE)
                                        .putExtra(SubMainActivity.SPORT_DATA, lapanganModels.get(position).getNamaLapangan())
                        );
                    }
                }
        );
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    private void fetchData() {

        RetrofitClient.getInstance().referensiPage().enqueue(new Callback<ReferensiResponse>() {
            @Override
            public void onResponse(Call<ReferensiResponse> call, Response<ReferensiResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                    LogApp.info(this, LogTag.RETROFIT_ON_RESPONSE, "ON SUCCESS");
                    ReferensiResponse.Data data = response.body().getData();

                    // get data models
                    ArrayList<ReferensiModel> topRating = data.getTopRatting();
                    ArrayList<ReferensiModel> venueKosong = data.getVenueKosong();
                    ArrayList<ReferensiModel> venueLokasi = data.getVenueLokasi();
                    ArrayList<ReferensiModel> venueGratis = data.getVenueGratis();
                    ArrayList<ReferensiModel> venueBerbayar = data.getVenueBerbayar();
                    ArrayList<ReferensiModel> venueDisewakan = data.getVenueDisewakan();

                    if (topRating.size() == 0 && venueKosong.size() == 0 && venueLokasi.size() == 0 && venueGratis.size() == 0 && venueBerbayar.size() == 0 && venueDisewakan.size() == 0) {
//                        Toast.makeText(requireActivity(), "SEMUA DATA NULL", Toast.LENGTH_SHORT).show();
                        handlerNullData();
                    } else {
                        // show referensi recyclerview
                        showVenueTopRatting(topRating);
                        showVenueKosong(venueKosong);
                        showVenueLokasi(venueLokasi);
                        showVenueGratis(venueGratis);
                        showVenueBerbayar(venueBerbayar);
                        showVenueDisewakan(venueDisewakan);
                    }

                } else {
                    Toast.makeText(requireActivity(), "FAILURE " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    handlerNullData();
                }
            }

            @Override
            public void onFailure(Call<ReferensiResponse> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                handlerNullData();
            }
        });

    }

    private void nullAdapter() {
//        venueRattingRecycler.setAdapter(null);
//        venueTerdekatRecycler.setAdapter(null);
//        venueBerbayarRecycler.setAdapter(null);
//        venueGratisRecycler.setAdapter(null);
//        venueDisewakanRecycler.setAdapter(null);
//        venueKosongRecycler.setAdapter(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        nullAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
        nullAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nullAdapter();
    }

    private void handlerNullData() {
        venueLokasiLayout.setVisibility(View.GONE);
        venueKosongLayout.setVisibility(View.GONE);
        venueDisewakanLayout.setVisibility(View.GONE);
        venueGratisLayout.setVisibility(View.GONE);
        venueBebayarLayout.setVisibility(View.GONE);
        topRattingLayout.setVisibility(View.GONE);

    }

    private void showVenueTopRatting(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            topRattingLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue rating -> " + models.size());
                venueRattingRecycler.setAdapter(new VenueFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }, VenueFirstAdapter.RATTING
                ));

                ArenaFinder.setRecyclerWidthByItem(requireActivity(), venueRattingRecycler, models.size(), R.dimen.card_venue_width_java);
            }

        }

    }

    private void showVenueKosong(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueKosongLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue kosong -> " + models.size());
                venueKosongRecycler.setAdapter(new VenueSecondAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }
                ));

                ArenaFinder.setRecyclerWidthByItem(requireContext(), venueKosongRecycler, models.size(), R.dimen.card_venue_second_width_java);
            }
        }

    }

    private void showVenueLokasi(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueLokasiLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue lokasi -> " + models.size());
                venueTerdekatRecycler.setAdapter(new VenueThirdAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }
                ));

                ArenaFinder.setRecyclerWidthByItem(requireContext(), venueTerdekatRecycler, models.size(), R.dimen.card_venue_third_width_java);
            }
        }

    }

    private void showVenueGratis(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueGratisLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue gratis -> " + models.size());
                venueGratisRecycler.setAdapter(new VenueFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }, VenueFirstAdapter.DEFAULT
                ));

                ArenaFinder.setRecyclerWidthByItem(requireContext(), venueGratisRecycler, models.size(), R.dimen.card_venue_width_java);
            }
        }

    }

    private void showVenueBerbayar(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueBebayarLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue berbayar -> " + models.size());
                venueBerbayarRecycler.setAdapter(new VenueFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }, VenueFirstAdapter.DEFAULT
                ));

                ArenaFinder.setRecyclerWidthByItem(requireActivity(), venueBerbayarRecycler, models.size(), R.dimen.card_venue_width_java);
            }
        }

    }

    private void showVenueDisewakan(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueDisewakanLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue disewa -> " + models.size());
                venueDisewakanRecycler.setAdapter(new VenueFirstAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidVenue()))
                        );
                    }
                }, VenueFirstAdapter.SLOT
                ));

                ArenaFinder.setRecyclerWidthByItem(requireContext(), venueDisewakanRecycler, models.size(), R.dimen.card_venue_width_java);
            }

        }

    }

}