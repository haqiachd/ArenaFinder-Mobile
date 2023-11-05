package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.EditCommentModel;
import com.c2.arenafinder.data.response.VenueReviewsResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewFragment extends Fragment {

    private static final String ARG_VENUE = "id_venue";
    private static final String ARG_USER = "id_user";
    private static final String ARG_STAR = "star";
    private static final String ARG_COMMENT = "comment";

    private String idVenue;
    private String idUser;
    private String comment;
    private int star;

    private int newStar = 0;

    private TextInputEditText inpComment;

    private MaterialButton btnPosting;

    private ImageView star1, star2, star3, star4, star5;

    private ImageView[] myRattins;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    private void intiViews(View view){
        inpComment = view.findViewById(R.id.fwr_input_comment);
        btnPosting = view.findViewById(R.id.fwr_btn_posting);

        star1 = view.findViewById(R.id.fwr_rating_1);
        star2 = view.findViewById(R.id.fwr_rating_2);
        star3 = view.findViewById(R.id.fwr_rating_3);
        star4 = view.findViewById(R.id.fwr_rating_4);
        star5 = view.findViewById(R.id.fwr_rating_5);
    }

    public static WriteReviewFragment newInstance(String idVenue, String idUser, int star, String comment) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VENUE, idVenue);
        args.putString(ARG_USER, idUser);
        args.putInt(ARG_STAR, star);
        args.putString(ARG_COMMENT, comment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idVenue = getArguments().getString(ARG_VENUE);
            idUser = getArguments().getString(ARG_USER);
            star = getArguments().getInt(ARG_STAR);
            comment = getArguments().getString(ARG_COMMENT);
            newStar = star;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiViews(view);

        myRattins = new ImageView[]{star1, star2, star3, star4, star5};

        inpComment.setText(comment);
        showRattingStar(myRattins, star, R.drawable.ic_ratting_start);

        onClickGroups();
    }

    private void onClickGroups(){

        btnPosting.setOnClickListener(v -> {
            RetrofitClient.getInstance().editComment(new EditCommentModel(idVenue, idUser, Integer.toString(newStar), Objects.requireNonNull(inpComment.getText()).toString()))
                    .enqueue(new Callback<VenueReviewsResponse>() {
                        @Override
                        public void onResponse(Call<VenueReviewsResponse> call, Response<VenueReviewsResponse> response) {
                            if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)){
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                requireActivity().onBackPressed();
                            }else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                requireActivity().onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Call<VenueReviewsResponse> call, Throwable t) {
                            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }
                    });
        });

        for (int i = 0; i < myRattins.length; i++) {
            int index = i;
            myRattins[i].setOnClickListener(v -> {
                showRattingStar(myRattins, index + 1, R.drawable.ic_ratting_start);
                newStar = index + 1;
            });
        }

    }

    private void showRattingStar(ImageView[] ratings, int rating, @DrawableRes int ratingIcon) {

        for (ImageView imageView : ratings) {
            imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_kosong));
        }

        for (int i = 0; i < rating; i++) {
            ratings[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), ratingIcon));
        }
    }
}