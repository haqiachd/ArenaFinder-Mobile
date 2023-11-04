package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.model.VenueRatingModel;
import com.c2.arenafinder.data.response.VenueReviewsResponse;
import com.c2.arenafinder.ui.adapter.VenueCommentAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.FragmentUtil;
import com.c2.arenafinder.util.UsersUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueReviewFragment extends Fragment {

    private static final String ARG_ID = "id_venue";

    private String id;

    private RecyclerView commentRecycler;

    private TextView txtRatting, txtReviews, txtWriteReview;

    private ImageView star1, star2, star3, star4, star5,
            mStar1, mStar2, mStar3, mStar4, mStar5;

    private ProgressBar prog1, prog2, prog3, prog4, prog5;

    public VenueReviewFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        commentRecycler = view.findViewById(R.id.fvr_recycler_review);
        txtRatting = view.findViewById(R.id.fvr_ratting_val);
        txtReviews = view.findViewById(R.id.fvr_total_review);

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

        mStar1 = view.findViewById(R.id.fvr_rating_1);
        mStar2 = view.findViewById(R.id.fvr_rating_2);
        mStar3 = view.findViewById(R.id.fvr_rating_3);
        mStar4 = view.findViewById(R.id.fvr_rating_4);
        mStar5 = view.findViewById(R.id.fvr_rating_5);

        txtWriteReview = view.findViewById(R.id.fvr_tambah_ulasan);
    }

    public static VenueReviewFragment newInstance(String id) {
        VenueReviewFragment fragment = new VenueReviewFragment();
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
        return inflater.inflate(R.layout.fragment_venue_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        fetchData();
        onClickGroups();
    }

    private void fetchData() {

        RetrofitClient.getInstance().venueComment(new UsersUtil(requireContext()).getId(), id)
                .enqueue(new Callback<VenueReviewsResponse>() {
                    @Override
                    public void onResponse(Call<VenueReviewsResponse> call, Response<VenueReviewsResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                            LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "ON RESPONSE");
                            VenueReviewsResponse.Data data = response.body().getData();

                            // show data
                            showRatting(data.getRating());
                            showComment(data.getComment());
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VenueReviewsResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private void onClickGroups() {

        ImageView[] myRattins = {mStar1, mStar2, mStar3, mStar4, mStar5};

        for (int i = 0; i < myRattins.length; i++) {
            int index = i;
            myRattins[i].setOnClickListener(v -> {
                FragmentUtil.switchFragmentDetailed(
                        requireActivity().getSupportFragmentManager(),
                        WriteReviewFragment.newInstance(id, index),
                        true
                );
            });
        }

        txtWriteReview.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "RATTING", Toast.LENGTH_SHORT).show();
        });

    }

    private void showComment(ArrayList<VenueCommentModel> models) {

        if (models.size() <= 0) {
            commentRecycler.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);
//            progLayout.setVisibility(View.GONE);
            txtRatting.setText(R.string.txt_ratting_na);
            txtReviews.setText(R.string.txt_ratting_is_0);
            txtReviews.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        } else {
            commentRecycler.setAdapter(
                    new VenueCommentAdapter(requireContext(), models, new AdapterActionListener() {
                        @Override
                        public void onClickListener(int position) {
                            Toast.makeText(requireContext(), models.get(position).getFullName(), Toast.LENGTH_SHORT).show();
                        }
                    })
            );
        }


    }

    private void showRatting(VenueRatingModel model) {

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
                e.printStackTrace();
                progressBars[i].setMax(0);
                progressBars[i].setProgress(0);
            }
        }

    }
}