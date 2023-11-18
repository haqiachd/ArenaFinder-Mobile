package com.c2.arenafinder.ui.fragment.detailed;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.c2.arenafinder.data.response.VenueDetailedResponse;
import com.c2.arenafinder.ui.adapter.VenueCommentAdapter;
import com.c2.arenafinder.ui.adapter.VenueContactAdapter;
import com.c2.arenafinder.ui.adapter.VenueFasilitasAdapter;
import com.c2.arenafinder.ui.adapter.VenuePhotoAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import org.osmdroid.views.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueDetailedFragment extends Fragment {

    private static final String ARG_ID = "id";

    private String id, coordinate;

    private ViewTreeObserver.OnScrollChangedListener listener;

    private MapView mapView;
    private MapOSM mapOSM;
    private AppBarLayout appBarLayout;
    private TextView txtVenueNameAppbar;
    private ImageView btnBackAppbar, btnVerticalAppbar, imgSport, star1, star2, star3, star4, star5;
    private MaterialButton btnFasilitas, btnMap, btnReview;

    private View line;
    private ConstraintLayout progLayout;
    private RecyclerView fasilitasRecycler, contactRecycler, commentRecycler;
    private ProgressBar prog1, prog2, prog3, prog4, prog5;
    private ViewPager2 viewPagerPhoto;

    private ArrayList<TextView> dots = new ArrayList<>();
    private ScrollView scrollView;
    private ImageView btnBack, btnVertical;
    private LinearLayout venueDots;

    private TextView txtPhotoValue, txtTopRating, txtTopViews, txtTopShared, txtTopSport, txtVenueName, txtVenueDesc,
            txtSenin, txtSelasa, txtRabu, txtKamis, txtJumat, txtSabtu, txtMinggu, txtFasilitas, txtAlamat, txtRatting, txtReviews;

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
        txtTopShared = view.findViewById(R.id.fvd_shared_val);
        txtTopSport = view.findViewById(R.id.fvd_top_sport_val);
        imgSport = view.findViewById(R.id.fvd_top_sport_icon);
        txtVenueName = view.findViewById(R.id.fvd_venue_name);
        txtVenueDesc = view.findViewById(R.id.fvd_venue_desc);
        txtFasilitas = view.findViewById(R.id.fvd_venue_fasilitas_desc);
        txtAlamat = view.findViewById(R.id.fvd_lokasi_desc);

        txtSenin = view.findViewById(R.id.fvd_senin);
        txtSelasa = view.findViewById(R.id.fvd_selasa);
        txtRabu = view.findViewById(R.id.fvd_rabu);
        txtKamis = view.findViewById(R.id.fvd_kamis);
        txtJumat = view.findViewById(R.id.fvd_jumat);
        txtSabtu = view.findViewById(R.id.fvd_sabtu);
        txtMinggu = view.findViewById(R.id.fvd_minggu);

        txtRatting = view.findViewById(R.id.fvd_ratting_val);
        txtReviews = view.findViewById(R.id.fvd_total_review);

        btnFasilitas = view.findViewById(R.id.fvd_show_fasilitas);
        btnReview = view.findViewById(R.id.fvd_btn_semua_comment);
        btnMap = view.findViewById(R.id.fvd_show_map);

        line = view.findViewById(R.id.fvd_ratting_line);
        progLayout = view.findViewById(R.id.fbv_prog_layout);
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        listener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int currentScroll = scrollView.getScrollY();

                if (currentScroll < getResources().getDimensionPixelOffset(com.intuit.sdp.R.dimen._190sdp)) {
                    ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, false);
                    appBarLayout.setVisibility(View.GONE);
                } else {
                    ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.WHITE_STATUS_BAR, R.color.primary_color_darker, false);
                    appBarLayout.setVisibility(View.VISIBLE);
                }

            }
        };

        updateBottomNav();
        fetchData(id);
        onClickGroups();
        pagerAction();

    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(listener);
        ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.TRANSPARENT_STATUS_BAR, R.color.transparent, false);
        updateBottomNav();
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
                    requireActivity().getSupportFragmentManager(), VenueReviewFragment.newInstance(id), true
            );
        });

        btnFasilitas.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Button Fasilitas", Toast.LENGTH_SHORT).show();
        });

        btnVertical.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Button Vertical", Toast.LENGTH_SHORT).show();
        });

        btnVerticalAppbar.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Button Vertical Appbar", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnBackAppbar.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

    }

    private void addDotsTextView(int size) {
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold);
        // add dots sesuai jumlah gambar
        for (int i = 0; i < size; i++) {
            dots.add(new TextView(requireActivity()));
            dots.get(i).setText("•");
            dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            dots.get(i).setTextSize(getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp));
            dots.get(i).setTypeface(typeface);
            dots.get(i).setPadding(0, 0, 3, 0);
            venueDots.addView(dots.get(i));
        }
        txtPhotoValue.setText("1 / "+ (size - 1));
    }

    private void setSelectedColor(int posisi) {
        for (int i = 0; i < dots.size(); i++) {
            if (i == posisi) {
                dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.scarlet));
            } else {
                dots.get(i).setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
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
                txtPhotoValue.setText((position + 1) + " / "+ dots.size());
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

        coordinate = model.getCoordinate();
        txtVenueName.setText(model.getVenueName());
        txtVenueDesc.setText(model.getDescVenue());
        txtVenueNameAppbar.setText(model.getVenueName());
        txtFasilitas.setText(model.getDescFacility());
        txtAlamat.setText(model.getLocation());
        txtTopSport.setText(model.getSport());
        txtTopViews.setText(getString(R.string.txt_total_views, model.getViews()));
        txtTopShared.setText(getString(R.string.txt_total_shared, model.getShared()));

    }

    private void showJamOperasional(TextView textView, DayOperasionalModel jam) {
        if (jam.getOpened().equalsIgnoreCase("tutup") || jam.getClosed().equalsIgnoreCase("tutup")) {
            textView.setText(R.string.txt_tutup);
            textView.setGravity(Gravity.CENTER);
        } else {
            textView.setText(getString(R.string.txt_jam_operasional, jam.getOpened(), jam.getClosed()));
            textView.setGravity(Gravity.END);
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

        if (model.size() <= 0) {
            fasilitasRecycler.setVisibility(View.GONE);
            btnFasilitas.setVisibility(View.GONE);
        } else {
            fasilitasRecycler.setAdapter(
                    new VenueFasilitasAdapter(requireContext(), model)
            );
        }
    }

    private void showMap(String venueName, String coordinate){

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

    private void showRatting(VenueRatingModel model) {

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

    private void showComment(ArrayList<VenueCommentModel> models) {

        if (models.size() <= 0) {
            btnReview.setText(R.string.btn_give_rat);
            commentRecycler.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);
//            progLayout.setVisibility(View.GONE);
            txtRatting.setText(R.string.txt_ratting_na);
            txtReviews.setText(R.string.txt_ratting_is_0);
            txtReviews.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            ImageView[] starts = {star1, star2, star3, star4, star5};

//            for (ImageView view : starts) {
//                view.setVisibility(View.GONE);
//            }

        } else {
            commentRecycler.setAdapter(
                    new VenueCommentAdapter(requireContext(), models, new AdapterActionListener() {
                        @Override
                        public void onClickListener(int position) {
//                            Toast.makeText(requireContext(), models.get(position).getFullName(), Toast.LENGTH_SHORT).show();
                        }
                    })
            );
        }


    }

    private void showContact(ArrayList<VenueContactModel> model) {

        contactRecycler.setAdapter(
                new VenueContactAdapter(requireContext(), model, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
//                        Toast.makeText(requireContext(), "PHONE -> " + model.get(position).getNoHp(), Toast.LENGTH_SHORT).show();
                        var whatsappUri = Uri.parse("https://wa.me/"+model.get(position).getNoHp().substring(1));

                        Intent intent = new Intent(Intent.ACTION_VIEW, whatsappUri);
                        startActivity(intent);
                    }
                })
        );

    }


    private void updateBottomNav() {
        if (getActivity() != null) {
            TextView txtTop, txtData, txtRight;
            MaterialButton button;
            txtTop = getActivity().findViewById(R.id.dtld_nav_txt_top);
            txtRight = getActivity().findViewById(R.id.dtld_nav_txt_right);
            txtData = getActivity().findViewById(R.id.dtld_nav_txt_data);
            button = getActivity().findViewById(R.id.dtld_nav_button);

            txtTop.setText("Mulai dari");
            txtRight.setText(" / Sesi");
            txtData.setText("Rp. 75.000");
            button.setText("BOOKING");
        }
    }


}