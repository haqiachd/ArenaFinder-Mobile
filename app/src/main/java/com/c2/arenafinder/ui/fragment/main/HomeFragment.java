package com.c2.arenafinder.ui.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.maps.MapOSM;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.HomeInfoModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.model.VenueCoordinateModel;
import com.c2.arenafinder.data.repository.HomeRepository;
import com.c2.arenafinder.data.response.HomeResponse;
import com.c2.arenafinder.di.HomeViewModelFactory;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.activity.SubMainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasFirstAdapter;
import com.c2.arenafinder.ui.adapter.HomeInfoAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.adapter.VenueSecondAdapter;
import com.c2.arenafinder.ui.adapter.VenueThirdAdapter;
import com.c2.arenafinder.ui.fragment.submain.SearchWorldFragment;
import com.c2.arenafinder.ui.fragment.submain.SportTypeFragment;
import com.c2.arenafinder.ui.fragment.submain.ViewAllFragment;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.viewmodel.HomeViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final String KEY_TEST = "test";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int prevScroll = 0;
    private boolean isShown = false;

    private String sheetSearch = "";
    private int sportType = 0;

    private HomeViewModel homeViewModel;

    private ArrayList<TextView> dots;
    private ArrayList<HomeInfoModel> homeInfoModels;
    private ViewPager2 homeInfoPager;
    private ScrollView scrollView;

    private SwipeRefreshLayout refreshLayout;

    private ShimmerFrameLayout shimmerLayout;

    private MapView mMap;

    private MapOSM mapOSM;

    private View btnVallBaru, btnVallRekomendasi, btnVallAktivitas, btnVallLokasi;

    private MaterialCardView menuAlur, menuKomunitas, menuWebsite, menuBooking;

    private LinearLayout homeDots, venueBaruLayout, venueRekomendasiLayout, aktivitasLayout, venueLokasiLayout;

    private RecyclerView jenisLapangan, venueBaruRecycler, buatKamuRecycler, aktivitasRecycler, venueTerdekatRecycler;

    public void initViews(View view) {

        refreshLayout = view.findViewById(R.id.mho_swipe);
        shimmerLayout = view.findViewById(R.id.mho_shimmer);
        menuAlur = view.findViewById(R.id.mho_menu_alur);
        menuKomunitas = view.findViewById(R.id.mho_menu_komunitas);
        menuWebsite = view.findViewById(R.id.mho_menu_trolley);
        menuBooking = view.findViewById(R.id.mho_menu_booking);
        mMap = view.findViewById(R.id.mho_mapview);

        btnVallBaru = view.findViewById(R.id.mho_vall_baru);
        btnVallRekomendasi = view.findViewById(R.id.mho_vall_rekomendasi);
        btnVallAktivitas = view.findViewById(R.id.mho_vall_aktivitas);
        btnVallLokasi = view.findViewById(R.id.mho_vall_lokasi);

        venueBaruLayout = view.findViewById(R.id.mho_venue_baru_layout);
        venueRekomendasiLayout = view.findViewById(R.id.mho_venue_rekomendasi_layout);
        aktivitasLayout = view.findViewById(R.id.mho_aktivitas_layout);
        venueLokasiLayout = view.findViewById(R.id.mho_venue_lokasi_layout);

        scrollView = view.findViewById(R.id.mho_scroll);
        jenisLapangan = view.findViewById(R.id.home_recycler_jenis);
        venueBaruRecycler = view.findViewById(R.id.mho_recycler_sedang_kosong);
        buatKamuRecycler = view.findViewById(R.id.mho_recycler_venue_baru);
        aktivitasRecycler = view.findViewById(R.id.mho_recycler_aktivitas_seru);
        venueTerdekatRecycler = view.findViewById(R.id.mho_recycler_venue_terdekat);
        homeInfoPager = view.findViewById(R.id.mho_top_education);
        homeDots = view.findViewById(R.id.mho_dots);
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogApp.info(this, LogTag.LIFEFCYLE, "onAttach() Called");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        LogApp.info(this, LogTag.LIFEFCYLE, "onCreated() Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        LogApp.info(this, LogTag.LIFEFCYLE, "onViewCreated() Called");

        homeViewModel = new ViewModelProvider(
                requireActivity(),
                new HomeViewModelFactory(new HomeRepository())
        ).get(HomeViewModel.class);

        // refresh data aplikasi
        refreshLayout.setOnRefreshListener(() ->
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    observer();
                    refreshLayout.setRefreshing(false);
                    requireActivity().runOnUiThread(() -> homeViewModel.fetchHome());
                }, 1500L)
        );

        showShimmer(true);

        // menampilkan map
        try {
            mapOSM = new MapOSM(requireActivity(), mMap);

            if (isAdded()) {
                if (getView() != null) {
                    if (savedInstanceState != null) {
                        Parcelable parcelable = savedInstanceState.getParcelable(KEY_TEST);
                        Objects.requireNonNull(venueBaruRecycler.getLayoutManager()).onRestoreInstanceState(parcelable);
                    } else {
                        new Handler(Looper.getMainLooper()).post(this::observer);
                    }
                }

                onClickGroups();
                adapterLapangan();
                showPager();
                pagerAction();
                getAppbar();
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
            Toast.makeText(requireContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_TEST, Objects.requireNonNull(venueBaruRecycler.getLayoutManager()).onSaveInstanceState());
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

        homeViewModel.getHomeData().observe(getViewLifecycleOwner(), dataState -> {

            if (dataState instanceof RetrofitState.Loading) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_LOADING, "Dashboard loaded");
            } else if (dataState instanceof RetrofitState.Error) {
                Toast.makeText(requireActivity(), "FAILURE " + ((RetrofitState.Error) dataState).getMessage(), Toast.LENGTH_SHORT).show();
                handlerNullData();
                showShimmer(false);
            } else if (dataState instanceof RetrofitState.Success) {
                HomeResponse.Data data = ((RetrofitState.Success<HomeResponse>) dataState).getData().getData();

                // get data models
                ArrayList<ReferensiModel> venueBaru = data.getVenueBaru();
                ArrayList<ReferensiModel> venueRekomendasi = data.getVenueRekomendasi();
                ArrayList<AktivitasModel> aktivitasBaru = data.getAktivitasSeru();
                ArrayList<VenueCoordinateModel> coordinate = data.getCoordinate();
                ArrayList<ReferensiModel> venueLokasi = data.getVenueLokasi();

                for (ReferensiModel model : venueLokasi) {
                    double latitude = ArenaFinder.getLatitude(model.getCoordinate());
                    double longitude = ArenaFinder.getLongitude(model.getCoordinate());
                    double distance = MapOSM.calculateDistance(latitude, longitude);
                    model.setDistance(Math.round(distance * 100.0) / 100.0);
                }

                if (venueBaru.size() == 0 && venueRekomendasi.size() == 0 && aktivitasBaru.size() == 0 && venueLokasi.size() == 0) {
                    handlerNullData();
                    showShimmer(false);
                } else {
                    // show recyclerview
                    requireActivity().runOnUiThread(() -> {
                        showVenueBaru(venueBaru);
                        showVenueRekomendasi(venueRekomendasi);
                        showAktivitasSeru(aktivitasBaru);
                        showVenueLokasi(venueLokasi);
                        showMap(coordinate);
                        showShimmer(false);
                    });

                }
            }

        });

    }

    private void getAppbar() {
        if (getActivity() != null) {
            MaterialCardView cardSearch = getActivity().findViewById(R.id.main_appbar_search);
            cardSearch.setVisibility(View.VISIBLE);
            cardSearch.setOnClickListener(v -> {
                showSheet(R.string.txt_pilih_tipe_pencarian, R.string.btn_cari, R.string.btn_cari_aktivitas, R.string.btn_cari_tempat_olahraga,
                        () -> startActivity(
                                new Intent(requireActivity(), SubMainActivity.class)
                                        .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SEARCH_WORLD)
                                        .putExtra(SubMainActivity.SEARCH_TYPE, sheetSearch)
                        ));
            });
        }
    }

    /**
     * Handler aksi saat button-button yang ada didalam fragment di-klik
     */
    private void onClickGroups() {

        /*
         * Aksi saat button alur di klik
         */
        menuAlur.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://arenafinder.tifnganjuk.com/alur-pesan.php")));
        });

        /*
         * Aksi saat button komunitas di klik
         */
        menuKomunitas.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.MENU_COMMUNITY)
            );
        });

        /*
         * Aksi saat button website di klik
         */
        menuWebsite.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://arenafinder.tifnganjuk.com")));
        });

        /*
         * Aksi saat button booking di klik
         */
        menuBooking.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.MENU_BOOKING)
            );
        });

        /*
         * Aksi saat button venue baru di klik
         */
        btnVallBaru.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_BARU)
            );
        });

        /*
         * Aksi saat button venue rekomendasi di klik
         */
        btnVallRekomendasi.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_REKOMENDASI)
            );
        });

        /*
         * Aksi saat button aktivitas di klik
         */
        btnVallAktivitas.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.AKTIVITAS_SERU)
            );
        });

        /*
         * Aksi saat button venue lokasi di klik
         */
        btnVallLokasi.setOnClickListener(v -> {
            startActivity(
                    new Intent(requireActivity(), SubMainActivity.class)
                            .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.VIEW_ALL)
                            .putExtra(SubMainActivity.SPORT_ACTION, ViewAllFragment.VENUE_LOKASI)
            );
        });

    }

    private void adapterLapangan() {
        ArrayList<JenisLapanganModel> lapanganModels = ArenaFinder.getSportType(requireContext());

        JenisLapanganAdapter lapanganAdapter = new JenisLapanganAdapter(requireActivity(), lapanganModels,
                new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        showSheet(R.string.txt_pilih_tipe_sport, R.string.btn_tampilkan, R.string.btn_sport_activity, R.string.btn_sport_venue,
                                () -> startActivity(
                                        new Intent(requireActivity(), SubMainActivity.class)
                                                .putExtra(SubMainActivity.FRAGMENT, SubMainActivity.SPORT_TYPE)
                                                .putExtra(SubMainActivity.SPORT_ACTION, Integer.toString(sportType))
                                                .putExtra(SubMainActivity.SPORT_DATA, lapanganModels.get(position).getNamaLapangan())
                                ));
                    }
                }
        );
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    /**
     * Menampilkan dialog bottom
     *
     * @param title .
     * @param btnMsg .
     * @param btn1 .
     * @param btn2 .
     * @param runnable .
     */
    private void showSheet(
            @StringRes int title, @StringRes int btnMsg, @StringRes int btn1, @StringRes int btn2, Runnable runnable) {
//        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
        BottomSheetDialog sheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);
        View sheetInflater = requireActivity().getLayoutInflater().inflate(R.layout.sheet_choose_search, null);
        sheet.setContentView(sheetInflater);

        TextView txtTitle = sheetInflater.findViewById(R.id.scs_title);
        TextView txtActivity = sheetInflater.findViewById(R.id.scs_txt_activity);
        TextView txtVenue = sheetInflater.findViewById(R.id.scs_txt_venue);

        // indicator icon
        ImageView activityIndicator = sheetInflater.findViewById(R.id.scs_activity_indicator);
        ImageView venueIndicator = sheetInflater.findViewById(R.id.scs_venue_indicator);

        // button choose type
        MaterialCardView cardActivity = sheetInflater.findViewById(R.id.scs_btn_choose_acvitity);
        MaterialCardView cardVenue = sheetInflater.findViewById(R.id.scs_btn_choose_venue);
        MaterialButton button = sheetInflater.findViewById(R.id.scs_button);

        txtTitle.setText(title);
        button.setText(btnMsg);
        txtActivity.setText(btn1);
        txtVenue.setText(btn2);

        // button cari
        button.setOnClickListener(view -> {
            if (!sheetSearch.isEmpty()) {
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                runnable.run();
                sheet.dismiss();
            } else {
                Toast.makeText(requireContext(), "Pilih Tipe Cari", Toast.LENGTH_SHORT).show();
            }
        });

        // button activity
        cardActivity.setOnClickListener(p -> {
            activityIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicator_selected));
            venueIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicator_unselected));
            cardActivity.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.azure));
            cardVenue.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.dimgray));
            sheetSearch = SearchWorldFragment.SEARCH_ACTIVITY;
            sportType = SportTypeFragment.TYPE_ACTIVITY;
        });

        // button venue
        cardVenue.setOnClickListener(o -> {
            venueIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicator_selected));
            activityIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicator_unselected));
            cardActivity.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.dimgray));
            cardVenue.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.azure));
            sheetSearch = SearchWorldFragment.SEARCH_VENUE;
            sportType = SportTypeFragment.TYPE_VENUE;
        });

        // show dialog
        sheet.show();
        sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
        sheet.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showPager() {
        homeInfoModels = new ArrayList<>();
        homeInfoModels.add(new HomeInfoModel("test/ic_home_viewpager_test.png", ""));
        homeInfoModels.add(new HomeInfoModel("test/venue-1.png", ""));
        homeInfoModels.add(new HomeInfoModel("test/venue-2.png", ""));
        homeInfoModels.add(new HomeInfoModel("test/venue-3.png", ""));
        HomeInfoAdapter infoAdapter = new HomeInfoAdapter(homeInfoModels);

        homeInfoPager.setAdapter(infoAdapter);
        dots = new ArrayList<>();
        addDotsTextView();
    }

    private void addDotsTextView() {
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.roboto_bold);
        // add dots sesuai jumlah gambar
        for (int i = 0; i < homeInfoModels.size(); i++) {
            dots.add(new TextView(requireActivity()));
            dots.get(i).setText("•");
            dots.get(i).setTextSize(getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp));
            dots.get(i).setTypeface(typeface);
            dots.get(i).setPadding(0, 0, 3, 0);
            homeDots.addView(dots.get(i));
        }
    }

    private void setSelectedColor(int posisi) {
        for (int i = 0; i < dots.size(); i++) {
            if (i == posisi) {
                dots.get(i).setTextColor(ContextCompat.getColor(requireActivity(), R.color.whero_purple));
            } else {
                dots.get(i).setTextColor(ContextCompat.getColor(requireActivity(), R.color.gray));
            }
        }
    }

    private void changePosisi(int posisi) {
        homeInfoPager.setCurrentItem(posisi);
        setSelectedColor(posisi);
    }

    private void pagerAction() {

        homeInfoPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                changePosisi(position);
            }
        });

    }

    private void handlerNullData() {
        venueBaruLayout.setVisibility(View.GONE);
        aktivitasLayout.setVisibility(View.GONE);
        venueRekomendasiLayout.setVisibility(View.GONE);
        aktivitasLayout.setVisibility(View.GONE);
        venueLokasiLayout.setVisibility(View.GONE);
    }

    /**
     * Menampilkan venue baru
     *
     * @param models data
     */
    private void showVenueBaru(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueBaruLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                // menampilkan list dari data
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue baru -> " + models.size());
                venueBaruRecycler.setAdapter(new VenueFirstAdapter(
                        this.requireContext(), models, new AdapterActionListener() {
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


                ArenaFinder.setRecyclerWidthByItem(requireContext(), venueBaruRecycler, models.size(), R.dimen.card_venue_width_java);
            }
        }

    }

    /**
     * Menampilkan venue rekomendasi
     *
     * @param models data
     */
    private void showVenueRekomendasi(ArrayList<ReferensiModel> models) {

        if (models.size() <= 0) {
            venueRekomendasiLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue rekomendasi " + models.size());
                buatKamuRecycler.setAdapter(new VenueSecondAdapter(
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

                ArenaFinder.setRecyclerWidthByItem(requireContext(), buatKamuRecycler, models.size(), R.dimen.card_venue_second_width_java);
            }
        }
    }

    /**
     * Menampilkan aktivitas seru
     *
     * @param models data
     */
    private void showAktivitasSeru(ArrayList<AktivitasModel> models) {

        if (models.size() <= 0) {
            aktivitasLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.error(requireContext(), LogTag.LIFEFCYLE, "DATA -> " + models.size());
                aktivitasRecycler.setAdapter(new AktivitasFirstAdapter(
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

                ArenaFinder.setRecyclerWidthByItem(requireContext(), aktivitasRecycler, models.size(), R.dimen.card_activity_first_width_java);
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
            mMap.setVisibility(View.GONE);
            return;
        }

        if (models.size() <= 0) {
            venueLokasiLayout.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), LogTag.LIFEFCYLE, "size of venue lokasi " + models.size());
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
     * Menampilkan map
     *
     */
    private void showMap(ArrayList<VenueCoordinateModel> coordinateModels) {
        mapOSM.initializeMap();

        if (coordinateModels.size() <= 0) {
            mMap.setVisibility(View.GONE);
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