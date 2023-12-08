package com.c2.arenafinder.ui.fragment.detailed;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.EditCommentModel;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.model.VenueRatingModel;
import com.c2.arenafinder.data.response.VenueReviewsResponse;
import com.c2.arenafinder.ui.adapter.VenueCommentAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.UsersUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueReviewFragment extends Fragment {

    private static final String ARG_ID = "id_venue";
    private static final String ARG_VENUE_NAME = "venue";

    private UsersUtil usersUtil;

    private String id;
    private String venueName;

    private SwipeRefreshLayout refreshLayout;

    private ShimmerFrameLayout shimmerLayout;

    private RecyclerView commentRecycler;

    private ConstraintLayout myCommentLayout;

    private LinearLayout writeLayout, cantComment, noComment;

    private TextView txtRatting, txtReviews, txtWriteReview, txtMyRatting, txtMyComment, txtMyDate, txtEditMy;

    private ImageView star1, star2, star3, star4, star5,
            gStar1, gStar2, gStar3, gStar4, gStar5,
            mStar1, mStar2, mStar3, mStar4, mStar5;

    private ProgressBar prog1, prog2, prog3, prog4, prog5;

    public VenueReviewFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        refreshLayout = view.findViewById(R.id.fvr_refresh);
        shimmerLayout = view.findViewById(R.id.fvr_shimmer);
        writeLayout = view.findViewById(R.id.fvr_write_comment_layout);
        myCommentLayout = view.findViewById(R.id.fvr_mycomment_layout);
        commentRecycler = view.findViewById(R.id.fvr_recycler_review);
        txtRatting = view.findViewById(R.id.fvr_ratting_val);
        txtReviews = view.findViewById(R.id.fvr_total_review);
        txtMyRatting = view.findViewById(R.id.fvr_my_ratting);
        txtMyComment = view.findViewById(R.id.fvr_review_comment);
        txtMyDate = view.findViewById(R.id.fvr_ratting_date);
        txtEditMy = view.findViewById(R.id.fvr_edit_ulasan);
        cantComment = view.findViewById(R.id.fvr_cant_commnet);
        noComment = view.findViewById(R.id.fvr_no_comment);

        prog1 = view.findViewById(R.id.fvr_prog_ratting_1);
        prog2 = view.findViewById(R.id.fvr_prog_ratting_2);
        prog3 = view.findViewById(R.id.fvr_prog_ratting_3);
        prog4 = view.findViewById(R.id.fvr_prog_ratting_4);
        prog5 = view.findViewById(R.id.fvr_prog_ratting_5);

        star1 = view.findViewById(R.id.fvr_star_1);
        star2 = view.findViewById(R.id.fvr_star_2);
        star3 = view.findViewById(R.id.fvr_star_3);
        star4 = view.findViewById(R.id.fvr_star_4);
        star5 = view.findViewById(R.id.fvr_star_5);

        gStar1 = view.findViewById(R.id.fvr_grating_1);
        gStar2 = view.findViewById(R.id.fvr_grating_2);
        gStar3 = view.findViewById(R.id.fvr_grating_3);
        gStar4 = view.findViewById(R.id.fvr_grating_4);
        gStar5 = view.findViewById(R.id.fvr_grating_5);

        mStar1 = view.findViewById(R.id.fvr_mratting_1);
        mStar2 = view.findViewById(R.id.fvr_mratting_2);
        mStar3 = view.findViewById(R.id.fvr_mratting_3);
        mStar4 = view.findViewById(R.id.fvr_mratting_4);
        mStar5 = view.findViewById(R.id.fvr_mratting_5);

        txtWriteReview = view.findViewById(R.id.fvr_tambah_ulasan);
    }

    public static VenueReviewFragment newInstance(String id, String venueName) {
        VenueReviewFragment fragment = new VenueReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_VENUE_NAME, venueName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
            venueName = getArguments().getString(ARG_VENUE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venue_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.WHITE_STATUS_BAR, com.otpview.R.color.grey, false);

        refreshLayout.setOnRefreshListener(() -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                fetchData();
                refreshLayout.setRefreshing(false);
            }, 1500L);
        });

        showShimmer(true);
        fetchData();
        onClickGroups();
    }

    private void showShimmer(boolean show){
        if (show){
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            refreshLayout.setVisibility(View.GONE);
        }else {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    private void fetchData() {

        RetrofitClient.getInstance().venueComment(new UsersUtil(requireContext()).getId(), id)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<VenueReviewsResponse> call, Response<VenueReviewsResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                            LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "ON RESPONSE");
                            VenueReviewsResponse.Data data = response.body().getData();

                            // show data
                            try {
                                showMyComment(data.getMyComment(), data.isCanComment());
                                showRatting(data.getRating());
                                showComment(data.getComment());

                                showShimmer(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(requireContext(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                                showShimmer(false);
                            }
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            showShimmer(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<VenueReviewsResponse> call, Throwable t) {
                        t.printStackTrace();
                        showShimmer(false);
                    }
                });

    }

    private void onClickGroups() {

        ImageView[] myRattins = {gStar1, gStar2, gStar3, gStar4, gStar5};

        for (int i = 0; i < myRattins.length; i++) {
            int index = i;
            myRattins[i].setOnClickListener(v -> {
                showRattingStar(myRattins, index + 1, R.drawable.ic_ratting_start);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentUtil.switchFragmentDetailed(
                                requireActivity().getSupportFragmentManager(),
                                WriteReviewFragment.newInstance(id, usersUtil.getId(), index + 1, ""),
                                true
                        );
                    }
                }, 500L);
            });
        }

        txtWriteReview.setOnClickListener(v -> {

            FragmentUtil.switchFragmentDetailed(
                    requireActivity().getSupportFragmentManager(),
                    WriteReviewFragment.newInstance(id, usersUtil.getId(), 0, ""),
                    true
            );
        });
    }

    private void showMyComment(VenueCommentModel model, boolean canComment) throws NumberFormatException, NullPointerException {

        if (!canComment) {
            writeLayout.setVisibility(View.GONE);
            myCommentLayout.setVisibility(View.GONE);
            txtMyComment.setVisibility(View.VISIBLE);
            cantComment.setVisibility(View.VISIBLE);
            return;
        }

        txtMyComment.setVisibility(View.VISIBLE);
        cantComment.setVisibility(View.GONE);

        if (model.getIdReview() > 0) {
            writeLayout.setVisibility(View.GONE);
            myCommentLayout.setVisibility(View.VISIBLE);
            showRattingStar(
                    new ImageView[]{mStar1, mStar2, mStar3, mStar4, mStar5},
                    model.getRatting(),
                    R.drawable.ic_review_star_yellow
            );
            txtMyComment.setText(model.getComment());
            txtMyDate.setText(ArenaFinder.convertToDate(requireContext(), model.getDate()));
            txtMyRatting.setText(R.string.txt_review_kamu);

            txtEditMy.setOnClickListener(v -> {
                BottomSheetDialog sheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);
                View sheetInflater = getLayoutInflater().inflate(R.layout.sheet_edit_comment, null);
                sheet.setContentView(sheetInflater);

                sheetInflater.findViewById(R.id.sec_btn_edit).setOnClickListener(dani -> {
                    FragmentUtil.switchFragmentDetailed(
                            requireActivity().getSupportFragmentManager(),
                            WriteReviewFragment.newInstance(id, usersUtil.getId(), model.getRatting(), model.getComment()),
                            true
                    );
                    sheet.dismiss();
                });

                sheetInflater.findViewById(R.id.sec_btn_hapus).setOnClickListener(mudi -> {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    new AlertDialog.Builder(requireContext())
                            .setTitle(R.string.dia_title_confirm)
                            .setMessage(R.string.delete_comment)
                            .setCancelable(false)
                            .setPositiveButton(R.string.dia_btn_hapus, (dialog, which) -> deleteComment())
                            .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                            })
                            .create().show();
                    sheet.dismiss();
                });

                sheet.show();
                sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
                sheet.getWindow().setGravity(Gravity.BOTTOM);

            });

        } else {
            writeLayout.setVisibility(View.VISIBLE);
            myCommentLayout.setVisibility(View.GONE);
            txtMyRatting.setText(R.string.txt_tuliskan_ratting);
        }
    }

    private void deleteComment() {
        RetrofitClient.getInstance().deleteComment(new EditCommentModel(id, usersUtil.getId()))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<VenueReviewsResponse> call, Response<VenueReviewsResponse> response) {

                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                            fetchData();
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VenueReviewsResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showComment(ArrayList<VenueCommentModel> models) {

        if (models.size() <= 0) {
            commentRecycler.setVisibility(View.GONE);
            noComment.setVisibility(View.VISIBLE);
            txtRatting.setText(R.string.txt_ratting_na);
            txtReviews.setText(R.string.txt_ratting_is_0);
            txtReviews.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            commentRecycler.setVisibility(View.VISIBLE);
            noComment.setVisibility(View.GONE);
            commentRecycler.setAdapter(
                    new VenueCommentAdapter(requireActivity(), models, new AdapterActionListener() {
                        @Override
                        public void onClickListener(int position) {
//                            Toast.makeText(requireContext(), models.get(position).getFullName(), Toast.LENGTH_SHORT).show();
                        }
                    }, id, venueName)
            );
        }


    }

    private void showRattingStar(ImageView[] ratings, int rating, @DrawableRes int ratingIcon) {

        for (int i = 0; i < rating; i++) {
            ratings[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), ratingIcon));
        }
    }

    private void showRatting(VenueRatingModel model) throws NumberFormatException, NullPointerException {

        ProgressBar[] progressBars = {prog1, prog2, prog3, prog4, prog5};
        ImageView[] stars = {star1, star2, star3, star4, star5};
        String[] values = {model.getRating1(), model.getRating2(), model.getRating3(), model.getRating4(), model.getRating5()};

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
                progressBars[i].setMax(0);
                progressBars[i].setProgress(0);
                throw new NumberFormatException("error konversi ke float");
            }
        }

    }
}