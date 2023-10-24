package com.c2.arenafinder.ui.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasSeruModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.SedangKosongModel;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasSeruAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.SedangKosongAdapter;
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
    private ArrayList<SedangKosongModel> kosongModels;
    private ArrayList<AktivitasSeruModel> aktivitasModels;

    private JenisLapanganAdapter lapanganAdapter;
    private SedangKosongAdapter kosongAdapter;
    private AktivitasSeruAdapter aktivitasAdapter;

    private ScrollView scrollView;
    private RecyclerView jenisLapangan, kosongRecycler, aktivitasRecycler;

    private boolean scrollable = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void initViews(View view) {
        scrollView = view.findViewById(R.id.mho_scroll);
        jenisLapangan = view.findViewById(R.id.home_recycler_jenis);
        kosongRecycler = view.findViewById(R.id.mho_recycler_sedang_kosong);
        aktivitasRecycler = view.findViewById(R.id.mho_recycler_aktivitas);
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        MainActivity.bottomNav.setDeactivatedOnFrame(BottomNavCustom.ITEM_HOME);

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

        scrollView.setOnTouchListener((v, event) -> {
            LogApp.info(requireContext(), LogTag.LIFEFCYLE, "ScrollView Listener");
            return !scrollable;
        });

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

        sedangKosong();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_HOME);
            scrollable = true;
            adapterLapangan();
            aktivitasAdapter();
        }, 1500);

    }

    private void adapterLapangan() {
        lapanganModels = new ArrayList<>();
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_all, "Semua"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_sepak_bola, "Sepak Bola"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_badminton, "Bulu Tangkis"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_voli, "Bola Voli"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_basket, "Bola Basket"));

        lapanganAdapter = new JenisLapanganAdapter(lapanganModels);
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    private void sedangKosong() {
        kosongModels = new ArrayList<>();
        kosongModels.add(new SedangKosongModel());
        kosongModels.add(new SedangKosongModel());
        kosongModels.add(new SedangKosongModel());
        kosongModels.add(new SedangKosongModel());
        kosongModels.add(new SedangKosongModel());
        kosongModels.add(new SedangKosongModel());

        kosongAdapter = new SedangKosongAdapter(kosongModels);
        kosongRecycler.setAdapter(kosongAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), kosongRecycler, kosongModels.size(), R.dimen.card_venue_width_java);

    }

    private void aktivitasAdapter() {
        aktivitasModels = new ArrayList<>();
        aktivitasModels.add(new AktivitasSeruModel());
        aktivitasModels.add(new AktivitasSeruModel());
        aktivitasModels.add(new AktivitasSeruModel());

        aktivitasAdapter = new AktivitasSeruAdapter(aktivitasModels);
        aktivitasRecycler.setAdapter(aktivitasAdapter);

    }

}