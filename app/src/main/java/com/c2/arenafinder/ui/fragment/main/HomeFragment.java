package com.c2.arenafinder.ui.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasFirstModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.VenueFirstModel;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasFirstAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;


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

    private JenisLapanganAdapter lapanganAdapter;
    private VenueFirstAdapter venueBaruAdapter;
    private VenueFirstAdapter buatKamuAdapter;
    private AktivitasFirstAdapter aktivitasAdapter;
    private VenueFirstAdapter venueTerdekatAdapter;

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
        venueTerdekatData();
        adapterLapangan();
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
                    MainActivity.bottomNav.showSecondIcon(BottomNavCustom.ITEM_HOME, R.drawable.ic_second_icon_def);
                } else if (currentScroll < 2100) {
                    MainActivity.bottomNav.showSecondIcon(BottomNavCustom.ITEM_HOME, R.drawable.ic_logo_google);
                } else {
                    MainActivity.bottomNav.hideSecondIcon(BottomNavCustom.ITEM_HOME);
                }

            }
        });

        LogApp.info(requireContext(), "test 5");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_HOME);
            scrollable = true;
        }, 100);

        LogApp.info(requireContext(), "test 6");

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
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-2.png", "Latihan Bersama Bulutangkis TIF Nganjuk", "GOR Bung Karno"));
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-1.png", "Kejuaraan Sepak Bola Tingkat Kabupaten Nganjuk", "Stadion Anjuk Ladang"));
        aktivitasModels.add(new AktivitasFirstModel("test/aktivitas-3.jpg", "Main Bareng Olahraga Futsal", "Blessing Futsal"));

        aktivitasAdapter = new AktivitasFirstAdapter(aktivitasModels);
        aktivitasRecycler.setAdapter(aktivitasAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), aktivitasRecycler, aktivitasModels.size(), com.intuit.sdp.R.dimen._208sdp);

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

}