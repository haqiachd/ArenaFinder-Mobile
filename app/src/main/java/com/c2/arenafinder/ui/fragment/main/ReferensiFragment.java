package com.c2.arenafinder.ui.fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.data.model.VenueFirstModel;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.adapter.VenueFirstAdapter;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;
import java.util.Collections;

public class ReferensiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView jenisLapangan, venueBaruRecycler, buatKamuRecycler, venueTerdekatRecycler,
    venueGratisRecycler, venueBerbayarRecycler, venueDisewakanRecycler;

    private ArrayList<VenueFirstModel> venueRattingModels;
    private ArrayList<VenueFirstModel> venueKosongModels;
    private ArrayList<VenueFirstModel> venueTerdekatModels;
    private ArrayList<VenueFirstModel> venueGratisModels;
    private ArrayList<VenueFirstModel> venueBerbayarModels;
    private ArrayList<VenueFirstModel> venueDisewakanModels;

    private VenueFirstAdapter venueRattingAdapter;
    private VenueFirstAdapter venueKosongAdapter;
    private VenueFirstAdapter venueTerdekatAdapter;
    private VenueFirstAdapter venueGratisAdapter;
    private VenueFirstAdapter venueBerbayarAdapter;
    private VenueFirstAdapter venueDisewakanAdapter;


    private String mParam1;
    private String mParam2;

    public ReferensiFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        jenisLapangan = view.findViewById(R.id.mre_recycler_jenis);
        venueBaruRecycler = view.findViewById(R.id.mre_recycler_ratting);
        buatKamuRecycler = view.findViewById(R.id.mre_recycler_venue_kosong);
        venueTerdekatRecycler = view.findViewById(R.id.mre_recycler_venue_terdekat);
        venueGratisRecycler = view.findViewById(R.id.mre_recycler_venue_gratis);
        venueBerbayarRecycler = view.findViewById(R.id.mre_recycler_venue_berbayar);
        venueDisewakanRecycler = view.findViewById(R.id.mre_recycler_venue_disewakan);
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

        MainActivity.bottomNav.setOnActionReferensiOnFrame(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requireContext(), "Referensi", Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_REFERENSI);
                adapterLapangan();
                venueRattingData();
                venueKosongData();
                venueTerdekatData();
                venueGratisData();
                venueBerbayarData();
                venueDisewakanData();
            }
        }, 1000L);
    }

    private void adapterLapangan() {
        ArrayList<JenisLapanganModel> lapanganModels = new ArrayList<>();
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_all, "Semua"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_sepak_bola, "Sepak Bola"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_badminton, "Bulu Tangkis"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_voli, "Bola Voli"));
        lapanganModels.add(new JenisLapanganModel(R.drawable.ic_lapangan_basket, "Bola Basket"));

        JenisLapanganAdapter lapanganAdapter = new JenisLapanganAdapter(requireContext(), lapanganModels);
        jenisLapangan.setAdapter(lapanganAdapter);

    }

    private void venueRattingData() {
        venueRattingModels = new ArrayList<>();
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));
        venueRattingModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));

        Collections.shuffle(venueRattingModels);

        venueRattingAdapter = new VenueFirstAdapter(requireContext(), venueRattingModels);
        venueBaruRecycler.setAdapter(venueRattingAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueBaruRecycler, venueRattingModels.size(), R.dimen.card_venue_width_java);
        LogApp.error(requireContext(), LogTag.LIFEFCYLE, "venue baru");
    }

    private void venueKosongData() {
        venueKosongModels = new ArrayList<>();
        venueKosongModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueKosongModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueKosongModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));

        venueKosongModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueKosongModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueKosongModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));

        Collections.shuffle(venueKosongModels);

        venueKosongAdapter = new VenueFirstAdapter(requireContext(), venueKosongModels);
        buatKamuRecycler.setAdapter(venueKosongAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), buatKamuRecycler, venueKosongModels.size(), R.dimen.card_venue_width_java);
    }

    private void venueTerdekatData() {
        venueTerdekatModels = new ArrayList<>();
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Bervariasi", "Harga Bervariasi"
        ));
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueTerdekatModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));

        Collections.shuffle(venueTerdekatModels);

        venueTerdekatAdapter = new VenueFirstAdapter(requireContext(), venueRattingModels);
        venueTerdekatRecycler.setAdapter(venueTerdekatAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueTerdekatRecycler, venueRattingModels.size(), R.dimen.card_venue_width_java);
    }

    private void venueGratisData(){
        venueGratisModels = new ArrayList<>();

        venueGratisModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Gratis", "Mulai dari Rp. 50.000"
        ));
        venueGratisModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Gratis", "Tidak perlu bayar"
        ));
        venueGratisModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Gratis", "Rp. 90.000 / Sesi"
        ));
        venueGratisModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Gratis", "Harga Bervariasi"
        ));
        venueGratisModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Gratis", "Rp. 275.000 / Sesi"
        ));
        venueGratisModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Gratis", "Tidak perlu bayar"
        ));

        Collections.shuffle(venueGratisModels);

        venueGratisAdapter = new VenueFirstAdapter(requireContext(), venueGratisModels);
        venueGratisRecycler.setAdapter(venueGratisAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueGratisRecycler, venueGratisModels.size(), R.dimen.card_venue_width_java);
    }

    private void venueBerbayarData(){
        venueBerbayarModels = new ArrayList<>();

        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Berbayar", "Mulai dari Rp. 50.000"
        ));
        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Berbayar", "Tidak perlu bayar"
        ));
        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Berbayar", "Rp. 90.000 / Sesi"
        ));
        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Berbayar", "Harga Bervariasi"
        ));
        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Berbayar", "Rp. 275.000 / Sesi"
        ));
        venueBerbayarModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Berbayar", "Tidak perlu bayar"
        ));

        Collections.shuffle(venueBerbayarModels);

        venueBerbayarAdapter = new VenueFirstAdapter(requireContext(), venueBerbayarModels);
        venueBerbayarRecycler.setAdapter(venueBerbayarAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueBerbayarRecycler, venueBerbayarModels.size(), R.dimen.card_venue_width_java);
    }

    private void venueDisewakanData(){
        venueDisewakanModels = new ArrayList<>();

        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-3.png", "Lapangan Basket Alun-Alun Nganjuk", "Bola Basket", 4.7F,
                "Disewakan", "Mulai dari Rp. 50.000"
        ));
        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-7.jpg", "Lapangan Baron", "Sepak Bola", 4.6F,
                "Disewakan", "Tidak perlu bayar"
        ));
        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-4.png", "GOR Bhayangkara", "Bulu Tangkis", 4.9F,
                "Disewakan", "Rp. 90.000 / Sesi"
        ));
        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-5.png", "Stadion Anjuk Ladang Nganjuk", "4 Jenis Olahraga", 4.6F,
                "Disewakan", "Harga Bervariasi"
        ));
        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-2.png", "Blessing Futsal", "Futsal", 4.9F,
                "Disewakan", "Rp. 275.000 / Sesi"
        ));
        venueDisewakanModels.add(new VenueFirstModel(
                "test/venue-1.png", "Lapangan Tembarak Kertosono", "Sepak Bola", 4.8F,
                "Disewakan", "Tidak perlu bayar"
        ));

        Collections.shuffle(venueDisewakanModels);

        venueDisewakanAdapter = new VenueFirstAdapter(requireContext(), venueDisewakanModels);
        venueDisewakanRecycler.setAdapter(venueDisewakanAdapter);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), venueDisewakanRecycler, venueDisewakanModels.size(), R.dimen.card_venue_width_java);
    }
}