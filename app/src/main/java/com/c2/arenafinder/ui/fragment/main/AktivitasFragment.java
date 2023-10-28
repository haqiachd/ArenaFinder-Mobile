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
import com.c2.arenafinder.data.model.AktivitasFirstModel;
import com.c2.arenafinder.data.model.AktivitasThirdModel;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.AktivitasFirstAdapter;
import com.c2.arenafinder.ui.adapter.AktivitasThirdAdapter;
import com.c2.arenafinder.ui.adapter.JenisLapanganAdapter;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;
import java.util.Collections;

public class AktivitasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView jenisLapangan, aktivitasBaru, aktivitasKosong, semuaAktivitasRecycler;
    private JenisLapanganAdapter jenisLapanganAdapter;

    public AktivitasFragment() {
        // Required empty public constructor
    }

    public void initViews(View view) {
        jenisLapangan = view.findViewById(R.id.mak_recycler_jenis);
        semuaAktivitasRecycler = view.findViewById(R.id.mak_recycler_semua);
        aktivitasBaru = view.findViewById(R.id.mak_recycler_aktivitas_baru);
        aktivitasKosong = view.findViewById(R.id.mak_recycler_aktivitas_kosong);
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

        MainActivity.bottomNav.setOnActionAktivitasOnFrame(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requireContext(), "Aktivitas", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLapangan();
        adapterSemuaAktivitas();
        adapterBaru();
        adapterKosong();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.bottomNav.closeAnimation(BottomNavCustom.ITEM_AKTIVITAS);
            }

        }, 1500);
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

    private void adapterBaru(){
        ArrayList<AktivitasFirstModel> models = new ArrayList<>();
        models.add(new AktivitasFirstModel("test/aktivitas-2.png", "Latihan Bersama Bulutangkis TIF Nganjuk", "GOR Bung Karno", 3, 10, "30 Oktober 2023"));
        models.add(new AktivitasFirstModel("test/aktivitas-1.png", "Kejuaraan Sepak Bola Tingkat Kabupaten Nganjuk", "Stadion Anjuk Ladang", 4, 12, "29 Oktober 2023"));
        models.add(new AktivitasFirstModel("test/aktivitas-3.jpg", "Main Bareng Olahraga Futsal", "Blessing Futsal", 12, 20, "28 Oktober 2023"));

        aktivitasBaru.setAdapter(new AktivitasFirstAdapter(requireContext(), models));

        Collections.shuffle(models);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), aktivitasBaru, models.size(), R.dimen.card_activity_width_java);
    }

    private void adapterKosong(){
        ArrayList<AktivitasFirstModel> models = new ArrayList<>();
        models.add(new AktivitasFirstModel("test/aktivitas-2.png", "Latihan Bersama Bulutangkis TIF Nganjuk", "GOR Bung Karno", 3, 10, "30 Oktober 2023"));
        models.add(new AktivitasFirstModel("test/aktivitas-1.png", "Kejuaraan Sepak Bola Tingkat Kabupaten Nganjuk", "Stadion Anjuk Ladang ", 4, 12, "29 Oktober 2023"));
        models.add(new AktivitasFirstModel("test/aktivitas-3.jpg", "Main Bareng Olahraga Futsal", "Blessing Futsal", 12, 20, "28 Oktober 2023"));

        aktivitasKosong.setAdapter(new AktivitasFirstAdapter(requireContext(), models));

        Collections.shuffle(models);

        ArenaFinder.setRecyclerWidthByItem(requireContext(), aktivitasKosong, models.size(), R.dimen.card_activity_width_java);
    }

    private void adapterSemuaAktivitas() {
        ArrayList<AktivitasThirdModel> models = new ArrayList<>();

        models.add(
                new AktivitasThirdModel("test/aktivitas-1.png", "Latihan Bersama Sepak Bola TIF Polije Nganjuk", "Stadion Anjuk Ladang", "-", "-")
        );
        models.add(
                new AktivitasThirdModel("test/aktivitas-2.png", "Latihan Bersama Bulu Tangkis Klub \"Rajawali\" Nganjuk", "GOR Bung Karno", "-", "-")
        );
        models.add(
                new AktivitasThirdModel("test/aktivitas-3.jpg", "Turnamen Futsal Pelajar SMP Negeri 2 Nganjuk", "Blessing Futasl", "-", "-")
        );
        models.add(
                new AktivitasThirdModel("test/aktivitas-5.jpg", "Turnamen Bulu Tangkis Kota Nganjuk", "GOR Bulutangkis Bhayangkara", "-", "-")
        );
        models.add(
                new AktivitasThirdModel("test/aktivitas-6.jpg", "Pelatihan Renang Kelompok Muda Kura-Kura", "Stadion Anjuk Ladang", "-", "-")
        );
        models.add(
                new AktivitasThirdModel("test/aktivitas-7.jpg", "Kompetisi Lari Jarak Jauh Perkumpulan Lari Cepat", "Kolam Renang Sumber Laut", "-", "-")
        );

        semuaAktivitasRecycler.setAdapter(new AktivitasThirdAdapter(models));

    }
}