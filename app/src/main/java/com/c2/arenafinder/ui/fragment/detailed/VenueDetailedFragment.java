package com.c2.arenafinder.ui.fragment.detailed;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.maps.MapOSM;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.DayOperasionalModel;
import com.c2.arenafinder.data.model.FasilitasModel;
import com.c2.arenafinder.data.model.JamOperasionalModel;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.model.VenueContactModel;
import com.c2.arenafinder.data.model.VenueDetailedModel;
import com.c2.arenafinder.data.model.VenuePhotos;
import com.c2.arenafinder.data.model.VenueRatingModel;
import com.c2.arenafinder.data.response.EmailReportResponse;
import com.c2.arenafinder.data.response.VenueDetailedResponse;
import com.c2.arenafinder.ui.adapter.VenueCommentAdapter;
import com.c2.arenafinder.ui.adapter.VenueContactAdapter;
import com.c2.arenafinder.ui.adapter.VenueFasilitasAdapter;
import com.c2.arenafinder.ui.adapter.VenuePhotoAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.UsersUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.c2.arenafinder.data.model.ReferensiModel.STATUS_BERBAYAR;
import static com.c2.arenafinder.data.model.ReferensiModel.STATUS_DISEWAKAN;
import static com.c2.arenafinder.data.model.ReferensiModel.STATUS_GRATIS;

public class VenueDetailedFragment extends Fragment {

    private static final String ARG_ID = "id";

    private String id, coordinate;

    private ViewTreeObserver.OnScrollChangedListener listener;

    private MapView mapView;
    private AppBarLayout appBarLayout;
    private TextView txtVenueNameAppbar;
    private ImageView btnBackAppbar, btnVerticalAppbar, imgSport, star1, star2, star3, star4, star5;
    private MaterialButton btnFasilitas, btnMap, btnReview;

    private ConstraintLayout progLayout;
    private RecyclerView fasilitasRecycler, contactRecycler, commentRecycler;
    private ProgressBar prog1, prog2, prog3, prog4, prog5;
    private ViewPager2 viewPagerPhoto;

    private View line;
    private ArrayList<TextView> dots = new ArrayList<>();
    private ScrollView scrollView;
    private ImageView btnBack, btnVertical, imgStatus;
    private LinearLayout venueDots;

    private TextView txtPhotoValue, txtTopRating, txtTopViews, txtStatus, txtFasilitasTitle, txtTopSport, txtVenueName, txtVenueDesc,
            txtSenin, txtSelasa, txtRabu, txtKamis, txtJumat, txtSabtu, txtMinggu, txtFasilitas, txtAlamat, txtRatting, txtReviews, txtDistance;

    private ConstraintLayout bottomNav;
    private MaterialButton btnBookingBot;
    private TextView txtHargaBot, txtTopBot, txtRightBot;

    public VenueDetailedFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        appBarLayout = view.findViewById(R.id.fvd_appbar);
        btnBackAppbar = view.findViewById(R.id.fvd_back_appbar);
        btnVerticalAppbar = view.findViewById(R.id.fvd_vertical_menu_appbar);
        txtVenueNameAppbar = view.findViewById(R.id.fvd_nama_lapangan_appbar);

        mapView = view.findViewById(R.id.fvd_map_view);
        viewPagerPhoto = view.findViewById(R.id.fvd_viewpager);
        fasilitasRecycler = view.findViewById(R.id.fvd_recyler_fasilitas);
        contactRecycler = view.findViewById(R.id.fvd_contact_recycler);
        commentRecycler = view.findViewById(R.id.fvd_recycler_review);

        prog1 = view.findViewById(R.id.fvd_prog_ratting_1);
        prog2 = view.findViewById(R.id.fvd_prog_ratting_2);
        prog3 = view.findViewById(R.id.fvd_prog_ratting_3);
        prog4 = view.findViewById(R.id.fvd_prog_ratting_4);
        prog5 = view.findViewById(R.id.fvd_prog_ratting_5);

        star1 = view.findViewById(R.id.fvd_star_1);
        star2 = view.findViewById(R.id.fvd_star_2);
        star3 = view.findViewById(R.id.fvd_star_3);
        star4 = view.findViewById(R.id.fvd_star_4);
        star5 = view.findViewById(R.id.fvd_star_5);

        venueDots = view.findViewById(R.id.fvd_photo_dots);
        scrollView = view.findViewById(R.id.fvd_scroll);
        btnBack = view.findViewById(R.id.fvd_back);
        btnVertical = view.findViewById(R.id.fvd_vertical_menu);
        txtPhotoValue = view.findViewById(R.id.fvd_photo_value);
        txtTopRating = view.findViewById(R.id.fvd_top_ratting_val);
        txtTopViews = view.findViewById(R.id.fvd_top_view_val);
        txtTopSport = view.findViewById(R.id.fvd_top_sport_val);
        imgSport = view.findViewById(R.id.fvd_top_sport_icon);
        txtVenueName = view.findViewById(R.id.fvd_venue_name);
        txtVenueDesc = view.findViewById(R.id.fvd_venue_desc);
        txtFasilitasTitle = view.findViewById(R.id.fvd_fasilitas_title);
        txtFasilitas = view.findViewById(R.id.fvd_venue_fasilitas_desc);
        txtAlamat = view.findViewById(R.id.fvd_lokasi_desc);
        txtStatus = view.findViewById(R.id.fvd_top_status_val);
        imgStatus = view.findViewById(R.id.fvd_top_status_ic);
        txtDistance = view.findViewById(R.id.fvd_distance_desc);

        txtSenin = view.findViewById(R.id.fvd_senin);
        txtSelasa = view.findViewById(R.id.fvd_selasa);
        txtRabu = view.findViewById(R.id.fvd_rabu);
        txtKamis = view.findViewById(R.id.fvd_kamis);
        txtJumat = view.findViewById(R.id.fvd_jumat);
        txtSabtu = view.findViewById(R.id.fvd_sabtu);
        txtMinggu = view.findViewById(R.id.fvd_minggu);

        txtRatting = view.findViewById(R.id.fvd_ratting_val);
        txtReviews = view.findViewById(R.id.fvd_total_review);

        btnReview = view.findViewById(R.id.fvd_btn_semua_comment);
        btnMap = view.findViewById(R.id.fvd_show_map);

        line = view.findViewById(R.id.fvd_ratting_line);
        progLayout = view.findViewById(R.id.fbv_prog_layout);

        bottomNav = view.findViewById(R.id.fvd_bottom_nav);
        txtHargaBot = view.findViewById(R.id.fvd_nav_txt_harga);
        btnBookingBot = view.findViewById(R.id.fvd_nav_button);
        txtTopBot = view.findViewById(R.id.fvd_nav_txt_top);
        txtRightBot = view.findViewById(R.id.fvd_nav_txt_right);
    }

    public static VenueDetailedFragment newInstance(String id) {
        VenueDetailedFragment fragment = new VenueDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venue_detailed, container, false);
    }

    private void showPopMenu(View view) {
        PopupMenu menuAppbar = new PopupMenu(requireContext(), view);
        menuAppbar.inflate(R.menu.menu_detailed_top);
        menuAppbar.show();

        menuAppbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.mdt_laporkan) {
                // show bottom sheet
                BottomSheetDialog sheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);
                View sheetInflater = requireActivity().getLayoutInflater().inflate(R.layout.sheet_report_venue, null);
                sheet.setContentView(sheetInflater);

                sheetInflater.findViewById(R.id.srv_val_1).setOnClickListener(v -> {
                    reportVenue(R.string.r_venue_1);
                    sheet.dismiss();
                });

                sheetInflater.findViewById(R.id.srv_val_2).setOnClickListener(v -> {
                    reportVenue(R.string.r_venue_2);
                    sheet.dismiss();
                });

                sheetInflater.findViewById(R.id.srv_val_3).setOnClickListener(v -> {
                    reportVenue(R.string.r_venue_3);
                    sheet.dismiss();
                });

                sheetInflater.findViewById(R.id.srv_val_4).setOnClickListener(v -> {
                    reportVenue(R.string.r_venue_4);
                    sheet.dismiss();
                });

                sheetInflater.findViewById(R.id.srv_val_5).setOnClickListener(v -> {
                    reportVenue(R.string.r_venue_5);
                    sheet.dismiss();
                });

                // show dialog
                sheet.show();
                sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
                sheet.getWindow().setGravity(Gravity.BOTTOM);
            }
            return true;
        });
    }

    private void reportVenue(@StringRes int reason) {

        ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.dia_title_confirm)
                .setMessage(requireContext().getString(R.string.report_venue, getString(reason)))
                .setCancelable(false)
                .setPositiveButton(R.string.dia_positive_laporkan, (dialog, which) -> {
                    sendReport(getString(reason));
                })
                .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                })
                .create().show();
    }

    private void sendReport(String reason) {
        var loading = new AlertDialog.Builder(requireContext())
                .setView(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null))
                .setCancelable(false)
                .create();
        loading.show();

        // send report
        RetrofitClient.getInstance().sendReportVenue(
                new UsersUtil(requireContext()).getEmail(), reason,
                id, txtVenueName.getText().toString()
        ).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EmailReportResponse> call, Response<EmailReportResponse> response) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.dia_title_inform)
                        .setMessage(R.string.report_accepted)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> dialog.dismiss())
                        .create().show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<EmailReportResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

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

        fetchData(id);
        onClickGroups();
        pagerAction();

    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(listener);
        ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, false);
//        updateBottomNav();
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollView.getViewTreeObserver().removeOnScrollChangedListener(listener);
        venueDots.removeAllViews();
        dots = new ArrayList<>();
    }

    private void onClickGroups() {

        btnMap.setOnClickListener(v -> {
            String url = "https://www.google.com/maps/@" + coordinate + ",19z?entry=ttu";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        btnReview.setOnClickListener(v -> {
            FragmentUtil.switchFragmentDetailed(
                    requireActivity().getSupportFragmentManager(),
                    VenueReviewFragment.newInstance(id, txtVenueName.getText().toString()),
                    true
//                    R.anim.anim_top_to_bottom, R.anim.anim_bottom_to_top
            );
        });

        btnVertical.setOnClickListener(this::showPopMenu);

        btnVerticalAppbar.setOnClickListener(this::showPopMenu);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnBackAppbar.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnBookingBot.setOnClickListener(v -> {
            FragmentUtil.switchFragmentDetailed(
                    requireActivity().getSupportFragmentManager(),
                    BookingVenueFragment.newInstance(id, txtVenueName.getText().toString()),
                    true
            );
        });

    }

    private void addDotsTextView(int size) {
        if (isAdded()) {
            Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold);
            // add dots sesuai jumlah gambar
            for (int i = 0; i < size; i++) {
                TextView dot = new TextView(requireActivity());
                dot.setText("•");
                dot.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                dot.setTextSize(getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp));
                dot.setTypeface(typeface);
                dot.setPadding(0, 0, 3, 0);

                venueDots.addView(dot);
                dots.add(dot);
            }
            txtPhotoValue.setText("1 / " + (size - 1));
        }

    }

    private void setSelectedColor(int posisi) {
        if (isAdded()) {
            for (int i = 0; i < dots.size(); i++) {
                if (i == posisi) {
                    dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.scarlet));
                } else {
                    dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                }
            }
        }
    }

    private void changePosisi(int posisi) {
        viewPagerPhoto.setCurrentItem(posisi);
        setSelectedColor(posisi);
    }

    private void pagerAction() {

        viewPagerPhoto.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                changePosisi(position);
                txtPhotoValue.setText((position + 1) + " / " + dots.size());
            }
        });

    }

    private void fetchData(String idSelected) {

        RetrofitClient.getInstance().venueDetailed(idSelected).enqueue(new Callback<VenueDetailedResponse>() {
            @Override
            public void onResponse(Call<VenueDetailedResponse> call, Response<VenueDetailedResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    VenueDetailedResponse.Data data = response.body().getData();

                    // show data
                    showPhotos(data.getPhoto());
                    showVenueData(data.getVenue());
                    showDayOperasional(data.getJamOperasional());
                    showFasilitas(data.getFasilitas());
                    showMap(data.getVenue().getVenueName(), data.getVenue().getCoordinate());
                    showRatting(data.getRating());
                    showComment(data.getComment());
                    showContact(data.getContact());

                } else {
                    LogApp.warn(requireActivity(), LogTag.RETROFIT_ON_FAILURE, response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<VenueDetailedResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void showPhotos(ArrayList<VenuePhotos> venuePhotos) {

        if (venuePhotos.size() > 0) {
            viewPagerPhoto.setAdapter(
                    new VenuePhotoAdapter(venuePhotos)
            );

            addDotsTextView(venuePhotos.size());
        }

    }

    private void showVenueData(VenueDetailedModel model) {

        if (isAdded()) {

            coordinate = model.getCoordinate();
            txtVenueName.setText(model.getVenueName());
            txtVenueDesc.setText(model.getDescVenue());
            txtVenueNameAppbar.setText(model.getVenueName());
            txtFasilitas.setText(model.getDescFacility());
            txtAlamat.setText(model.getLocation());
            txtTopSport.setText(ArenaFinder.localizationSport(model.getSport()));
            txtTopViews.setText(getString(R.string.txt_total_views, model.getViews()));

            String coordinate = model.getCoordinate();
            double distance = MapOSM.calculateDistance(
                    ArenaFinder.getLatitude(coordinate), ArenaFinder.getLongitude(coordinate)
            );
            txtDistance.setText(getString(
                    R.string.distance_and_minutes_2,
                    String.format(Locale.ENGLISH,"%.1f", distance),
                    MapOSM.calculateMileage(distance)
            ));

            switch (model.getSport().toLowerCase()) {
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

            switch (model.getStatus().toLowerCase()) {
                case STATUS_DISEWAKAN: {
                    txtStatus.setText(R.string.txt_disewakan);
                    txtTopBot.setText(R.string.fvd_mulai_dari);
                    imgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_dtd_status_rent));
                    txtHargaBot.setText(getString(R.string.txt_price_value, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                    break;
                }
                case STATUS_GRATIS: {
                    txtStatus.setText(R.string.txt_gratis);
                    bottomNav.setVisibility(View.GONE);
                    imgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_dtd_status_free));
                    break;
                }
                case STATUS_BERBAYAR: {
                    txtStatus.setText(R.string.txt_berbayar);
                    txtRightBot.setVisibility(View.GONE);
                    btnBookingBot.setVisibility(View.GONE);
                    txtTopBot.setText(R.string.txt_harga_masuk);
                    imgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_dtd_status_price));
                    txtHargaBot.setText(getString(R.string.txt_price_value, ArenaFinder.toMoneyCase(model.getPrice())));
                    break;
                }
            }
        }

    }

    private void showJamOperasional(TextView textView, DayOperasionalModel jam) {
        if (isAdded()) {
            if (jam.getOpened().equalsIgnoreCase("tutup") || jam.getClosed().equalsIgnoreCase("tutup")) {
                textView.setText(R.string.txt_tutup);
                textView.setGravity(Gravity.CENTER);
            } else {
                textView.setText(getString(R.string.txt_jam_operasional, jam.getOpened(), jam.getClosed()));
                textView.setGravity(Gravity.END);
            }
        }
    }

    private void showDayOperasional(JamOperasionalModel model) {
        showJamOperasional(txtSenin, model.getSenin());
        showJamOperasional(txtSelasa, model.getSelasa());
        showJamOperasional(txtRabu, model.getRabu());
        showJamOperasional(txtKamis, model.getKamis());
        showJamOperasional(txtJumat, model.getJumat());
        showJamOperasional(txtSabtu, model.getSabtu());
        showJamOperasional(txtMinggu, model.getMinggu());
    }

    private void showFasilitas(ArrayList<FasilitasModel> model) {
        if (isAdded()) {
            if (model.size() <= 0) {
                fasilitasRecycler.setVisibility(View.GONE);
                txtFasilitasTitle.setVisibility(View.GONE);
                txtFasilitas.setVisibility(View.GONE);
            } else {
                fasilitasRecycler.setAdapter(
                        new VenueFasilitasAdapter(requireContext(), model)
                );
            }
        }
    }

    private void showMap(String venueName, String coordinate) {

        if (isAdded()) {
            double latitude = ArenaFinder.getLatitude(coordinate),
                    longitude = ArenaFinder.getLongitude(coordinate);

            MapOSM mapOSM = new MapOSM(requireActivity(), mapView);
            mapOSM.initializeMap(latitude, longitude, 15.0, false);
            mapOSM.setCenterMap();
            mapOSM.addMarker(latitude, longitude, venueName, R.drawable.ic_map_maker,
                    () -> {
                    }
            );
        }
    }

    private void showRatting(VenueRatingModel model) {

        if (isAdded()) {
            ProgressBar[] progressBars = {prog1, prog2, prog3, prog4, prog5};
            ImageView[] stars = {star1, star2, star3, star4, star5};
            String[] values = {model.getRating1(), model.getRating2(), model.getRating3(), model.getRating4(), model.getRating5()};

            txtTopRating.setText(String.valueOf(model.getRating()));
            txtRatting.setText(String.valueOf(model.getRating()));
            txtReviews.setText(getString(R.string.txt_ulasan_val, model.getTotalReview()));

            int rating = (int) Float.parseFloat(model.getRating());

            for (int i = 0; i < rating; i++) {
                stars[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_ratting_start));
            }

            if ((Float.parseFloat(model.getRating()) % 1) != 0) {
                stars[rating].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_review_star_orange_half));
            }

            for (int i = 0; i < progressBars.length; i++) {
                try {
                    progressBars[i].setProgress(Integer.parseInt(values[i]));
                    progressBars[i].setMax(Integer.parseInt(model.getTotalReview()));
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBars[i].setMax(0);
                    progressBars[i].setProgress(0);
                }
            }

        }
    }

    private void showComment(ArrayList<VenueCommentModel> models) {

        if (isAdded()) {
            if (models.size() <= 0) {
                btnReview.setText(R.string.btn_give_rat);
                commentRecycler.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);
//            progLayout.setVisibility(View.GONE);
                txtRatting.setText(R.string.txt_ratting_na);
                txtReviews.setText(R.string.txt_ratting_is_0);
                txtReviews.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            } else {
                commentRecycler.setAdapter(
                        new VenueCommentAdapter(requireActivity(), models, new AdapterActionListener() {
                            @Override
                            public void onClickListener(int position) {
//                            Toast.makeText(requireContext(), models.get(position).getFullName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                                , id, txtVenueName.getText().toString())
                );
            }
        }

    }

    private void showContact(ArrayList<VenueContactModel> model) {

        if (isAdded()) {
            contactRecycler.setAdapter(
                    new VenueContactAdapter(requireContext(), model, new AdapterActionListener() {
                        @Override
                        public void onClickListener(int position) {
                            try {
                                var whatsappUri = Uri.parse("https://wa.me/62" + model.get(position).getNoHp().substring(1));
                                Intent intent = new Intent(Intent.ACTION_VIEW, whatsappUri);
                                startActivity(intent);
                            } catch (Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
            );
        }

    }

}