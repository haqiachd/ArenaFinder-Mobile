package com.c2.arenafinder.ui.fragment.submain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.response.SportTypeActivityResponse;
import com.c2.arenafinder.data.response.SportTypeVenueResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.adapter.AktivitasSecondAdapter;
import com.c2.arenafinder.ui.adapter.VenueExtendedAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportTypeFragment extends Fragment {

    public static final int TYPE_ACTIVITY = 2, TYPE_VENUE = 3;

    private static final String ARG_ACTION = "action";
    private static final String ARG_SPORT = "sport";

    private int action;
    private String sport;

    private ImageView imgType;
    private RecyclerView venueRecycler;

    public SportTypeFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        imgType = view.findViewById(R.id.fst_image);
        venueRecycler = view.findViewById(R.id.fst_recycler_sport);
    }

    public static SportTypeFragment newInstance(int action, String sport) {
        SportTypeFragment fragment = new SportTypeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ACTION, action);
        args.putString(ARG_SPORT, sport);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            action = getArguments().getInt(ARG_ACTION);
            sport = getArguments().getString(ARG_SPORT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        superAppbar();

        switch (action) {
            case TYPE_ACTIVITY: {
                fetchDataActivity();
                break;
            }
            case TYPE_VENUE: {
                fetchDataVenue();
                break;
            }
        }
    }

    private void fetchDataVenue() {

        RetrofitClient.getInstance().sportType(sport).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SportTypeVenueResponse> call, Response<SportTypeVenueResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                    // show image
                    Glide.with(requireContext())
                            .load(response.body().getData().getImgUrl())
                            .centerCrop()
                            .placeholder(R.drawable.ic_type_football)
                            .into(imgType);

                    // show list
                    var extended = response.body().getData().getVenues();
                    if (extended.size() > 0) {
                        if (isAdded()) {
                            venueRecycler.setAdapter(
                                    new VenueExtendedAdapter(
                                            requireContext(), extended,
                                            new AdapterActionListener() {
                                                @Override
                                                public void onClickListener(int position) {
                                                    startActivity(
                                                            new Intent(requireActivity(), DetailedActivity.class)
                                                                    .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                                                    .putExtra(DetailedActivity.ID, Integer.toString(extended.get(position).getidVenue()))
                                                    );
                                                }
                                            }
                                    )
                            );
                        }
                    }
                } else {
                    ArenaFinder.VibratorToast(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                }
            }

            @Override
            public void onFailure(Call<SportTypeVenueResponse> call, Throwable t) {
                ArenaFinder.VibratorToast(requireContext(), t.getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_MEDIUM);
            }
        });

    }

    private void fetchDataActivity() {

        RetrofitClient.getInstance().sportActivity(sport)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<SportTypeActivityResponse> call, Response<SportTypeActivityResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            Glide.with(requireContext())
                                    .load(response.body().getData().getImgUrl())
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_type_football)
                                    .into(imgType);

                            var extended = response.body().getData().getActivities();
                            if (extended.size() > 0) {
                                if (isAdded()) {
                                    venueRecycler.setAdapter(new AktivitasSecondAdapter(
                                            requireContext(), extended, new AdapterActionListener() {
                                        @Override
                                        public void onClickListener(int position) {
                                            startActivity(
                                                    new Intent(requireActivity(), DetailedActivity.class)
                                                            .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.ACTIVITY)
                                                            .putExtra(DetailedActivity.ID, Integer.toString(extended.get(position).getidAktvitias()))
                                            );
                                        }
                                    }
                                    ));
                                }
                            }
                        } else {
                            ArenaFinder.VibratorToast(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT, ArenaFinder.VIBRATOR_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<SportTypeActivityResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void superAppbar() {
        if (getActivity() != null) {

            LinearLayout layout = getActivity().findViewById(R.id.sub_linear);
            TextView textView = getActivity().findViewById(R.id.sub_title);
            textView.setText(sport);
            layout.setVisibility(View.VISIBLE);
        }
    }
}