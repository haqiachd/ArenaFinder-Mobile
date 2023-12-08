package com.c2.arenafinder.ui.fragment.detailed;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.maps.MapOSM;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.AktivitasMemberModel;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.VenueContactModel;
import com.c2.arenafinder.data.response.AktivitasDetailedResponse;
import com.c2.arenafinder.data.response.AktivitasMemberResponse;
import com.c2.arenafinder.ui.adapter.AktivitasMemberAdapter;
import com.c2.arenafinder.ui.adapter.VenueContactAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedFragment extends Fragment {

    private static final String ID = "id";

    private final String JOIN = "join", LEAVE = "leave";

    private String id;

    private ViewTreeObserver.OnScrollChangedListener listener;
    private UsersUtil usersUtil;

    private MapView mapView;
    private MapOSM mapOSM;

    private AppBarLayout appBarLayout;
    private TextView txtAktivitasNameAppbar;
    private ImageView btnBackAppbar, btnVerticalAppbar;

    private ConstraintLayout contentLayout;
    private ShimmerFrameLayout shimmerLayout;

    private ScrollView scrollView;

    private ImageView imgBack, imgPhoto, imgVertical, imgSport;

    private TextView txtAktivitasName, txtVenueName, txtDesc, txtSport, txtDateStart, txtJamMain, txtStatusAnggota, txtHarga, txtLokasi, txtDistance;

    private MaterialButton btnMap, btnJoin;

    private RecyclerView recyclerContact, recyclerMember;

    public ActivityDetailedFragment() {
        // Required empty public constructor
    }

    private void initViews(View root) {
        appBarLayout = root.findViewById(R.id.fad_appbar);
        contentLayout = root.findViewById(R.id.fad_content);
        shimmerLayout = root.findViewById(R.id.fad_shimmer);
        btnBackAppbar = root.findViewById(R.id.fad_back_appbar);
        btnVerticalAppbar = root.findViewById(R.id.fad_vertical_menu_appbar);
        txtAktivitasNameAppbar = root.findViewById(R.id.fad_nama_lapangan_appbar);
        scrollView = root.findViewById(R.id.fad_scroll);
        mapView = root.findViewById(R.id.fad_map_view);

        imgBack = root.findViewById(R.id.fad_back);
        imgPhoto = root.findViewById(R.id.fad_image);
        imgVertical = root.findViewById(R.id.fad_vertical_menu);
        txtAktivitasName = root.findViewById(R.id.fad_aktivitas_name);
        txtDesc = root.findViewById(R.id.fad_venue_desc);
        txtVenueName = root.findViewById(R.id.fad_venue_name);
        txtSport = root.findViewById(R.id.fad_top_sport_val);
        txtDateStart = root.findViewById(R.id.fad_tgl_mulai);
        txtJamMain = root.findViewById(R.id.fad_jam_main);
        txtStatusAnggota = root.findViewById(R.id.fad_membership);
        txtHarga = root.findViewById(R.id.fad_txt_harga);
        txtLokasi = root.findViewById(R.id.fad_lokasi_desc);
        btnMap = root.findViewById(R.id.fad_show_map);
        btnJoin = root.findViewById(R.id.fad_nav_button);
        recyclerContact = root.findViewById(R.id.fad_contact_recycler);
        recyclerMember = root.findViewById(R.id.fad_member_recycler);
        imgSport = root.findViewById(R.id.fad_top_sport_icon);
        txtDistance = root.findViewById(R.id.fad_distance_desc);

    }

    public static ActivityDetailedFragment newInstance(String id) {
        ActivityDetailedFragment fragment = new ActivityDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        showShimmer(true);
        fetchData();
        onClickGroups();

        listener = () -> {
            int currentScroll = scrollView.getScrollY();

            if (currentScroll < getResources().getDimensionPixelOffset(com.intuit.sdp.R.dimen._190sdp)) {
                ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, false);
                appBarLayout.setVisibility(View.GONE);
            } else {
                ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.WHITE_STATUS_BAR, R.color.primary_color_darker, false);
                appBarLayout.setVisibility(View.VISIBLE);
            }

        };

        if (getActivity() != null) {
            ConstraintLayout bot = getActivity().findViewById(R.id.fvd_bottom_nav);
            bot.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(listener);
        ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollView.getViewTreeObserver().removeOnScrollChangedListener(listener);
    }

    private void showShimmer(boolean show){
        if (show){
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            contentLayout.setVisibility(View.GONE);
        }else {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onClickGroups() {

        imgBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnBackAppbar.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        imgVertical.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Menu Vertical", Toast.LENGTH_SHORT).show();
        });

        btnVerticalAppbar.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Menu Vertical", Toast.LENGTH_SHORT).show();
        });

    }

    private void fetchData() {

        RetrofitClient.getInstance().aktivitasDetailed(id, usersUtil.getEmail()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AktivitasDetailedResponse> call, Response<AktivitasDetailedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                    AktivitasDetailedResponse.Data data = response.body().getData();

                    // get detailed data
                    AktivitasModel aktivitasData = data.getAktivitasData();
                    ArrayList<VenueContactModel> aktivitasContact = data.getAktivitasContact();
                    ArrayList<AktivitasMemberModel> aktivitasMember = data.getAktivitasMember();

                    showAktivitasData(aktivitasData);
                    showContact(aktivitasContact);
                    showAktivitasMember(aktivitasMember);

                    if (data.isJoined()) {
                        btnJoin.setText(R.string.btn_aktivitas_keluar);
                        btnJoin.setOnClickListener(v -> {
                            buttonAction(LEAVE);
                        });
                    } else {
                        btnJoin.setText(R.string.btn_aktivitas_gabung);
                        btnJoin.setOnClickListener(v -> {
                            buttonAction(JOIN);
                        });
                    }

                    btnMap.setOnClickListener(v -> {
                        String url = "https://www.google.com/maps/@" + aktivitasData.getCoordinate() + ",19z?entry=ttu";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    });

                    showMap(aktivitasData.getVenueName(), aktivitasData.getCoordinate());

                    showShimmer(false);

                } else {
                    showShimmer(false);
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AktivitasDetailedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                showShimmer(false);
            }
        });

    }

    private void showAktivitasData(AktivitasModel model) {
        if (isAdded()) {

            Glide.with(requireContext())
                    .load(RetrofitClient.AKTIVITAS_URL + model.getPhoto())
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgPhoto);

            txtAktivitasName.setText(model.getNamaAktivitas());
            txtAktivitasNameAppbar.setText(model.getNamaAktivitas());
            txtVenueName.setText(model.getVenueName());
            txtDesc.setText(model.getDescAktivitas());
            txtSport.setText(ArenaFinder.localizationSport(model.getJenisOlahraga()));
            txtDateStart.setText(requireContext().getString(R.string.txt_tanggal_val, ArenaFinder.convertToDate(requireContext(), model.getDate())));
            txtJamMain.setText(requireContext().getString(R.string.txt_jam_main_val2, model.getJamMain()));
            txtStatusAnggota.setText(requireContext().getString(R.string.btn_status_anggota_val, model.getMembership()));
            txtHarga.setText(requireContext().getString(R.string.txt_total_price_booking_val, ArenaFinder.toMoneyCase(model.getPrice())));
            txtLokasi.setText(model.getLocation());

            var coordinate = model.getCoordinate();
            var distance = MapOSM.calculateDistance(
                    ArenaFinder.getLatitude(coordinate), ArenaFinder.getLongitude(coordinate)
            );
            txtDistance.setText(getString(
                    R.string.distance_and_minutes_2,
                    String.format(Locale.ENGLISH, "%.1f", distance),
                    MapOSM.calculateMileage(distance)
            ));

            switch (model.getJenisOlahraga().toLowerCase()) {
                case "futsal": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_futsal));
                    break;
                }
                case "badminton": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_badminton));
                    break;
                }
                case "sepak bola": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_football));
                    break;
                }
                case "bola basket": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_basket));
                    break;
                }
                case "bola voli": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_voli));
                    break;
                }
                case "tenis lapangan": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_tenis));
                    break;
                }
                case "tenis meja": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_tenis_table));
                    break;
                }
                case "atletik": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_atletik));
                    break;
                }
                case "fitness": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_fitnes));
                    break;
                }
                case "renang": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_swiming));
                    break;
                }
                case "silat": {
                    imgSport.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_sport_silat));
                    break;
                }
            }


        }
    }

    private void showContact(ArrayList<VenueContactModel> models) {

        if (models.size() <= 0) {
            recyclerContact.setVisibility(View.GONE);
        } else {
            if (isAdded()) {
                recyclerContact.setAdapter(new VenueContactAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        var whatsappUri = Uri.parse("https://wa.me/" + models.get(position).getNoHp().substring(1));

                        Intent intent = new Intent(Intent.ACTION_VIEW, whatsappUri);
                        startActivity(intent);
                    }
                }
                ));
            }
        }

    }

    private void showAktivitasMember(ArrayList<AktivitasMemberModel> models) {
        if (models.size() <= 0) {
            recyclerMember.setVisibility(View.GONE);
        } else {
            recyclerMember.setAdapter(new AktivitasMemberAdapter(
                    models
            ));
        }
    }

    private void buttonAction(String actionStatus) {

        Call<AktivitasMemberResponse> action;

        switch (actionStatus) {
            case JOIN: {
                action = RetrofitClient.getInstance().joinActivity(
                        new AktivitasMemberModel(id, usersUtil.getEmail())
                );
                break;
            }
            case LEAVE: {
                action = RetrofitClient.getInstance().leaveActivity(
                        new AktivitasMemberModel(id, usersUtil.getEmail())
                );
                break;
            }
            default: {
                action = null;
            }
        }

        assert action != null;
        action.enqueue(new Callback<AktivitasMemberResponse>() {
            @Override
            public void onResponse(Call<AktivitasMemberResponse> call, Response<AktivitasMemberResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    new AlertDialog.Builder(requireActivity())
                            .setTitle(R.string.dia_title_inform)
                            .setMessage(response.body().getMessage())
                            .setCancelable(false)
                            .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fetchData();
                                }
                            })
                            .create().show();
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AktivitasMemberResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showMap(String venueName, String coordinate) {

        double latitude = ArenaFinder.getLatitude(coordinate),
                longitude = ArenaFinder.getLongitude(coordinate);

        mapOSM = new MapOSM(requireActivity(), mapView);
        mapOSM.initializeMap(latitude, longitude, 15.0, false);
        mapOSM.setCenterMap();
        mapOSM.addMarker(
                latitude, longitude, venueName, R.drawable.ic_map_maker,
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }
        );

    }

}