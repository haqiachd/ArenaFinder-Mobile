package com.c2.arenafinder.ui.fragment.main;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasFirstModel;
import com.c2.arenafinder.data.model.HomeInfoModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.VenueFirstModel;
import com.c2.arenafinder.data.model.VenueSecondModel;
import com.c2.arenafinder.data.model.VenueThirdModel;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasFirstAdapter;
import com.c2.arenafinder.ui.adapter.HomeInfoAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.adapter.VenueSecondAdapter;
import com.c2.arenafinder.ui.adapter.VenueThirdAdapter;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;
import java.util.Collections;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int prevScroll = 0;
    private boolean isShown = false;

    private ArrayList<JenisLapanganModel> lapanganModels;
    private ArrayList<VenueFirstModel> venueBaruModels;
    private ArrayList<VenueFirstModel> buatKamuModels;
    private ArrayList<AktivitasFirstModel> aktivitasModels;
    private ArrayList<VenueFirstModel> venueTerdekat;
    private ArrayList<TextView> dots;

    private JenisLapanganAdapter lapanganAdapter;
    private VenueFirstAdapter venueBaruAdapter;
    private VenueFirstAdapter buatKamuAdapter;
    private AktivitasFirstAdapter aktivitasAdapter;
    private VenueFirstAdapter venueTerdekatAdapter;

    private ArrayList<VenueSecondModel> secondModels;
    private VenueSecondAdapter secondAdapter;

    ArrayList<HomeInfoModel> homeInfoModels;

    private ViewPager2 homeInfoPager;
    private LinearLayout homeDots;

    private ScrollView scrollView;
    private RecyclerView jenisLapangan, venueBaruRecycler, buatKamuRecycler, aktivitasRecycler, venueTerdekatRecycler;

    private boolean scrollable = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void initViews(View view) {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_HOME);
//    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogApp.info(requireContext(), "first");
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        MainActivity.bottomNav.setDeactivatedOnFrame(BottomNavCustom.ITEM_HOME);

        LogApp.info(requireContext(), "test 1");
        veneuBaruData();
        buatkamuData();
        aktivitasAdapter();
        adapterLapangan();
        showSecond();
        venueTerdekatData();
        venueTerdekatDataNew();
        LogApp.info(requireContext(), "test 2");


        MainActivity.bottomNav.setOnActionHomeOnFrame(new Runnable() {
            @Override
            public void run() {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, 0); // Mengatur posisi ke atas (0, 0)
                    }
                });
            }
        });

        LogApp.info(requireContext(), "test 3");

        scrollView.setOnTouchListener((v, event) -> {
            LogApp.info(requireContext(), LogTag.LIFEFCYLE, "ScrollView Listener");
            return !scrollable;
        });

        LogApp.info(requireContext(), "test 4");

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int currentScroll = scrollView.getScrollY();

                if (currentScroll < 400) {
                    MainActivity.bottomNav.hideSecondIcon(BottomNavCustom.ITEM_HOME);
                } else if (currentScroll < 700) {
//                    MainActivity.bottomNav.showSecondIcon(BottomNavCustom.ITEM_HOME, R.drawable.ic_second_icon_def);
                } else if (currentScroll < 2100) {
//                    MainActivity.bottomNav.showSecondIcon(BottomNavCustom.ITEM_HOME, R.drawable.ic_logo_google);
                } else {
                    MainActivity.bottomNav.hideSecondIcon(BottomNavCustom.ITEM_HOME);
                }

            }
        });

        LogApp.info(requireContext(), "test 5");
        showPager();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_HOME);
            scrollable = true;
        }, 100);

        LogApp.info(requireContext(), "test 6");
        pagerAction();

    }

    private void showSecond(){

        secondModels = new ArrayList<>();
        secondModels.add(new VenueSecondModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "4 Slot Kosong", "Rp. 275.000 / Sesi"
        ));
        secondModels.add(new VenueSecondModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak Perlu Bayar", "Tidak perlu bayar"
        ));
        secondModels.add(new VenueSecondModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan",  "12 Slot Kosong", "Rp. 90.000 / Sesi"
        ));
        secondModels.add(new VenueSecondModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Buka Jam 07:00-23:00", "Mulai dari Rp. 50.000"
        ));
        secondModels.add(new VenueSecondModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi", "Harga Bervariasi"
        ));
        secondModels.add(new VenueSecondModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Hari Ini Buka", "Tidak perlu bayar"
        ));

        Collections.shuffle(secondModels);

        secondAdapter = new VenueSecondAdapter(requireContext(), secondModels, new AdapterActionListener() {
            @Override
            public void onClickListener(int position) {
                AdapterActionListener.super.onClickListener(position);
            }
        });

        ArenaFinder.setRecyclerWidthByItem(requireContext(), buatKamuRecycler, secondModels.size(), R.dimen.card_venue_second_width_java);

        buatKamuRecycler.setAdapter(secondAdapter);

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
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold);
        // add dots sesuai jumlah gambar
        for (int i = 0; i < homeInfoModels.size(); i++){
            dots.add(new TextView(requireActivity()));
            dots.get(i).setText("•");
            dots.get(i).setTextSize(getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp));
            dots.get(i).setTypeface(typeface);
            dots.get(i).setPadding(0, 0, 3, 0);
            homeDots.addView(dots.get(i));
        }
    }

    private void setSelectedColor(int posisi){
        for (int i = 0; i < dots.size(); i++){
            if (i == posisi){
                dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.whero_purple));
            }else {
                dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
            }
        }
    }

    private void changePosisi(int posisi){
        homeInfoPager.setCurrentItem(posisi);
        setSelectedColor(posisi);
    }

    private void pagerAction(){

        homeInfoPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                changePosisi(position);
            }
        });

    }

    private void adapterLapangan() {
        lapanganModels = new ArrayList<>();
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_all, "Semua"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_sepak_bola, "Sepak Bola"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_badminton, "Bulu Tangkis"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_voli, "Bola Voli"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_basket, "Bola Basket"));

        lapanganAdapter = new JenisLapanganAdapter(requireContext(), lapanganModels);
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    private void veneuBaruData() {
        venueBaruModels = new ArrayList<>();
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));

        venueBaruAdapter = new VenueFirstAdapter(requireContext(), venueBaruModels);
        venueBaruRecycler.setAdapter(venueBaruAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueBaruRecycler, venueBaruModels.size(), R.dimen.card_venue_width_java);
        LogApp.error(requireContext(), LogTag.LIFEFCYLE, "venue baru");

    }

    private void buatkamuData() {
        buatKamuModels = new ArrayList<>();
        buatKamuModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        buatKamuModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        buatKamuModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));

        buatKamuModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        buatKamuModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));
        buatKamuModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));

        buatKamuAdapter = new VenueFirstAdapter(requireContext(), buatKamuModels);
        buatKamuRecycler.setAdapter(buatKamuAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), buatKamuRecycler, buatKamuModels.size(), R.dimen.card_venue_width_java);
    }

    private void aktivitasAdapter() {
        aktivitasModels = new ArrayList<>();
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-2.png", "Latihan Bersama Bulutangkis TIF Nganjuk", "GOR Bung Karno", 3, 10, "30 Oktober 2023"));
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-1.png", "Kejuaraan Sepak Bola Tingkat Kabupaten Nganjuk", "Stadion Anjuk Ladang", 4, 12, "29 Oktober 2023"));
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-3.jpg", "Main Bareng Olahraga Futsal", "Blessing Futsal", 12, 20, "28 Oktober 2023"));

        aktivitasAdapter = new AktivitasFirstAdapter(requireContext(), aktivitasModels);
        aktivitasRecycler.setAdapter(aktivitasAdapter);

        Collections.shuffle(aktivitasModels);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), aktivitasRecycler, aktivitasModels.size(), R.dimen.card_activity_first_width_java);

    }

    private void venueTerdekatData() {
        venueBaruModels = new ArrayList<>();
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueBaruModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));


        venueTerdekatAdapter = new VenueFirstAdapter(requireContext(), venueBaruModels);
        venueTerdekatRecycler.setAdapter(venueTerdekatAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueTerdekatRecycler, venueBaruModels.size(), R.dimen.card_venue_width_java);
    }

    private void venueTerdekatDataNew() {
        ArrayList<VenueThirdModel> models = new ArrayList<>();
        models.add(new VenueThirdModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        models.add(new VenueThirdModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        models.add(new VenueThirdModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        models.add(new VenueThirdModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));
        models.add(new VenueThirdModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        models.add(new VenueThirdModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));


        VenueThirdAdapter thirdAdapter = new VenueThirdAdapter(requireContext(), models);
        venueTerdekatRecycler.setAdapter(thirdAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueTerdekatRecycler, models.size(), R.dimen.card_venue_third_width_java);
    }

}