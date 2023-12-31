package com.c2.arenafinder.ui.fragment.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.c2.arenafinder.api.maps.MapOSM;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.model.VenueCoordinateModel;
import com.c2.arenafinder.data.repository.ReferensiRepository;
import com.c2.arenafinder.data.response.ReferensiResponse;
import com.c2.arenafinder.di.ReferensiViewModelFactory;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.activity.SubMainActivity;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.adapter.VenueSecondAdapter;
import com.c2.arenafinder.ui.adapter.VenueThirdAdapter;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.viewmodel.ReferensiViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.osmdroid.views.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferensiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ReferensiViewModel referensiViewModel;

    private SwipeRefreshLayout refreshLayout;

    private ShimmerFrameLayout shimmerLayout;

    private MapView mapView;

    private MapOSM mapOSM;

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
        shimmerLayout = view.findViewById(R.id.mre_shimmer);
        topRattingLayout = view.findViewById(R.id.mre_top_ratting_layout);
        venueKosongLayout = view.findViewById(R.id.mre_venue_kosong_layout);
        venueLokasiLayout = view.findViewById(R.id.mre_venue_lokasi_layout);
        venueGratisLayout = view.findViewById(R.id.mre_venue_gratis_layout);
        venueBebayarLayout = view.findViewById(R.id.mre_venue_berbayar_layout);
        venueDisewakanLayout = view.findViewById(R.id.mre_venue_disewakan_layout);
        btnFilter = view.findViewById(R.id.mre_btn_filter);

        mapView = view.findViewById(R.id.mre_map_view);
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

        referensiViewModel = new ViewModelProvider(
                requireActivity(),
                new ReferensiViewModelFactory(new ReferensiRepository())
        ).get(ReferensiViewModel.class);

        refreshLayout.setOnRefreshListener(() ->
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    observer();
                    refreshLayout.setRefreshing(false);
                    requireActivity().runOnUiThread(() -> referensiViewModel.fetchReferensi());
                }, 1500L)
        );

        if (isAdded()) {
            showShimmer(true);
            LogApp.info(requireContext(), "PREPARING LOAD DATA TO SERVER");
            if (getView() != null) {
                new Handler(Looper.getMainLooper()).post(this::observer);
            }
            adapterLapangan();
            onClickGroups();
            getAppbar();
        }

    }

    /**
     * Menampilkan shimmer
     *
     * @param show status tampilkan
     */
    private void showShimmer(boolean show) {
        if (show) {
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            refreshLayout.setVisibility(View.GONE);
        } else {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Mendapatkan data dari database
     */
    private void observer() {

        if (getView() == null) {
            return;
        }

        //java.lang.IllegalStateException: Can't access the Fragment View's LifecycleOwner for ReferensiFragment{acb4d2a} (44bf92bf-85ca-4298-98a3-583095a7d339) when getView() is null i.e., before onCreateView() or after onDestroyView()
        referensiViewModel.getReferensiData().observe(getViewLifecycleOwner(), dataState -> {

            if (dataState instanceof RetrofitState.Loading) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_LOADING, "Referensi loaded");
            } else if (dataState instanceof RetrofitState.Error) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_FAILURE, "Referensi loaded");
                Toast.makeText(requireActivity(), "FAILURE " + ((RetrofitState.Error) dataState).getMessage(), Toast.LENGTH_SHORT).show();
                handlerNullData();
                showShimmer(false);
            } else if (dataState instanceof RetrofitState.Success) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "Referensi loaded");
                ReferensiResponse.Data data = ((RetrofitState.Success<ReferensiResponse>) dataState).getData().getData();

                // get data models
                ArrayList<ReferensiModel> topRating = data.getTopRatting();
                ArrayList<ReferensiModel> venueKosong = data.getVenueKosong();
                ArrayList<ReferensiModel> venueLokasi = data.getVenueLokasi();
                ArrayList<ReferensiModel> venueGratis = data.getVenueGratis();
                ArrayList<ReferensiModel> venueBerbayar = data.getVenueBerbayar();
                ArrayList<ReferensiModel> venueDisewakan = data.getVenueDisewakan();
                ArrayList<VenueCoordinateModel> venueCoordinate = data.getCoordinate();

                for (ReferensiModel model : venueLokasi) {
                    double latitude = ArenaFinder.getLatitude(model.getCoordinate());
                    double longitude = ArenaFinder.getLongitude(model.getCoordinate());
                    double distance = MapOSM.calculateDistance(latitude, longitude);
                    model.setDistance(Math.round(distance * 100.0) / 100.0);
                }

                if (topRating.size() == 0 && venueKosong.size() == 0 && venueLokasi.size() == 0 && venueGratis.size() == 0 && venueBerbayar.size() == 0 && venueDisewakan.size() == 0) {
//                        Toast.makeText(requireActivity(), "SEMUA DATA NULL", Toast.LENGTH_SHORT).show();
                    handlerNullData();
                    showShimmer(false);
                } else {
                    // show referensi recyclerview
                    showVenueTopRatting(topRating);
                    showVenueKosong(venueKosong);
                    showVenueLokasi(venueLokasi);
                    showVenueGratis(venueGratis);
                    showVenueBerbayar(venueBerbayar);
                    showVenueDisewakan(venueDisewakan);
                    showMap(venueCoordinate);
                    showShimmer(false);
                }
            }

        });

    }

    private void getAppbar() {
        if (getActivity() != null) {
            MaterialCardView cardSearch = getActivity().findViewById(R.id.main_appbar_search);
            cardSearch.setVisibility(View.VISIBLE);
            cardSearch.setOnClickListener(v -> {
                startActivity(
                        new Intent(requireActivity(), SubMainActivity.class)
                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SEARCH_WORLD)
                                .putExtra(SubMainActivity.SEARCH_TYPE, SearchWorldFragment.SEARCH_VENUE)
                );
            });
        }
    }

    /**
     * Mendapatkan data dari databaes
     */
    private void onClickGroups() {

        /*
         * Aksi saat button filter di klik
         */
        btnFilter.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "Filter", Toast.LENGTH_SHORT).show();
        });

        /*
         * Aksi saat button venue rating di klik
         */
        btnVallRatting.setOnClickListener(v ->
                startActivity(
                        new Intent(requireActivity(), SubMainActivity.class)
                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                                .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_RATING)
                )
        );

        /*
         * Aksi saat button venue kosong di klik
         */
        btnVallKosong.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_KOSONG)
            );
        });

        /*
         * Aksi saat button venue lokasi di klik
         */
        btnValLokasi.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_LOKASI)
            );
        });

        /*
         * Aksi saat button venue gratis di klik
         */
        btnVallGratis.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_GRATIS)
            );
        });

        /*
         * Aksi saat button venue berbayar di klik
         */
        btnVallBerbayar.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_BERBAYAR)
            );
        });

        /*
         * Aksi saat button venue disewakan di klik
         */
        btnVallDisewakan.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_DISEWAKAN)
            );
        });

    }

    private void adapterLapangan() {
        ArrayList<JenisLapanganModel> lapanganModels = ArenaFinder.getSportType(requireContext());

        JenisLapanganAdapter lapanganAdapter = new JenisLapanganAdapter(requireActivity(), lapanganModels,
                new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), SubMainActivity.class)
                                        .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SPORT_TYPE)
                                        .putExtra(SubMainActivity.SPORT_ACTION, Integer.toString(SportTypeFragment.TYPE_VENUE))
                                        .putExtra(SubMainActivity.SPORT_DATA, lapanganModels.get(position).getNamaLapangan())
                        );
                    }
                }
        );
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    /**
     * Mendapatkan data dari database
     */
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
                    ArrayList<VenueCoordinateModel> venueCoordinate = data.getCoordinate();

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
                        showMap(venueCoordinate);
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

//    @Override
//    public void onPause() {
//        super.onPause();
//        nullAdapter();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        nullAdapter();
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        nullAdapter();
//    }

    private void handlerNullData() {
        venueLokasiLayout.setVisibility(View.GONE);
        venueKosongLayout.setVisibility(View.GONE);
        venueDisewakanLayout.setVisibility(View.GONE);
        venueGratisLayout.setVisibility(View.GONE);
        venueBebayarLayout.setVisibility(View.GONE);
        topRattingLayout.setVisibility(View.GONE);

    }

    /**
     * Menampilkan venue rating
     *
     * @param models data
     */
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

    /**
     * Menampilkan venue kosong
     *
     * @param models data
     */
    private void showVenueKosong(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueKosongLayout.setVisibility(View.GONE);
//            LogApp.error(requireContext(), "VENUE KOSONG GONE");
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

    /**
     * Menampilkan venue lokasi
     *
     * @param models data
     */
    private void showVenueLokasi(ArrayList<ReferensiModel> models) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            venueLokasiLayout.setVisibility(View.GONE);
            mapView.setVisibility(View.GONE);
            return;
        }

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

    /**
     * Menampilkan venue gratis
     *
     * @param models data
     */
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

    /**
     * Menampilkan venue berbayar
     *
     * @param models data
     */
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

    /**
     * Menampilkan venue disewakan
     *
     * @param models data
     */
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


    /**
     * Menampilkan map
     *
     */
    private void showMap(ArrayList<VenueCoordinateModel> coordinateModels) {
        mapOSM = new MapOSM(requireActivity(), mapView);
        mapOSM.initializeMap();

        if (coordinateModels.size() <= 0) {
            mapView.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                for (VenueCoordinateModel coordinate : coordinateModels) {
                    mapOSM.addMarker(
                            ArenaFinder.getLatitude(coordinate.getCoordinate()),
                            ArenaFinder.getLongitude(coordinate.getCoordinate()),
                            coordinate.getVenueName(), R.drawable.ic_map_maker, new Runnable() {
                                @Override
                                public void run() {

                                }
                            }
                    );
                }
            }
        }


    }


}