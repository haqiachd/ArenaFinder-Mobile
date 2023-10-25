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

public class ReferensiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView jenisLapangan, venueBaruRecycler, buatKamuRecycler, venueTerdekatRecycler;

    private ArrayList<VenueFirstModel> venueBaruModels;
    private ArrayList<VenueFirstModel> buatKamuModels;
    private ArrayList<VenueFirstModel> venueTerdekat;

    private VenueFirstAdapter venueBaruAdapter;
    private VenueFirstAdapter buatKamuAdapter;
    private VenueFirstAdapter venueTerdekatAdapter;


    private String mParam1;
    private String mParam2;

    public ReferensiFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        jenisLapangan = view.findViewById(R.id.home_recycler_jenis);
        venueBaruRecycler = view.findViewById(R.id.mho_recycler_sedang_kosong);
        buatKamuRecycler = view.findViewById(R.id.mho_recycler_venue_baru);
        venueTerdekatRecycler = view.findViewById(R.id.mho_recycler_venue_terdekat);
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
                veneuBaruData();
                buatkamuData();
                venueTerdekatData();
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