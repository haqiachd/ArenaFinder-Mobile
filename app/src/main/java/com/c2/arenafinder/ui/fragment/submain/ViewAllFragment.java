package com.c2.arenafinder.ui.fragment.submain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenueExtendedModel;
import com.c2.arenafinder.data.response.VenueExtendedResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.adapter.VenueExtendedAdapter;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllFragment extends Fragment {

    public static String ARG_ACTION = "ac";

    public static final int VENUE_BARU = 1, VENUE_REKOMENDASI = 2, VENUE_LOKASI = 3, VENUE_RATING = 4,
            VENUE_KOSONG = 5, VENUE_GRATIS = 6, VENUE_BERBAYAR = 7, VENUE_DISEWAKAN = 8,
            AKTIVITAS_SERU = 9, AKTIVITAS_KOSONG = 10, ALTIVITAS_ALL = 11;

    private @StringRes int actionName = R.string.sub_main_activity;

    private int action;

    private RecyclerView recyclerAll;

    public ViewAllFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        recyclerAll = view.findViewById(R.id.fva_recycler);
    }

    public static ViewAllFragment newInstance(int action) {
        ViewAllFragment fragment = new ViewAllFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            action = getArguments().getInt(ARG_ACTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        switch (action) {
            case VENUE_BARU: {
                actionBaru();
                actionName = R.string.sub_venue_baru;
                break;
            }
            case VENUE_REKOMENDASI: {
                actionRekomendasi();
                actionName = R.string.sub_venue_rekomendasi;
                break;
            }
            case VENUE_RATING: {
                actionRating();
                actionName = R.string.sub_venue_rating;
                break;
            }
            case VENUE_KOSONG: {
                actionKosong();
                actionName = R.string.sub_venue_kosong;
                break;
            }
            case VENUE_LOKASI: {
                actionLokasi();
                actionName = R.string.sub_venue_lokasi;
                break;
            }
            case VENUE_GRATIS: {
                actionGratis();
                actionName = R.string.sub_venue_gratis;
                break;
            }
            case VENUE_BERBAYAR: {
                actionBebayar();
                actionName = R.string.sub_venue_berbayar;
                break;
            }
            case VENUE_DISEWAKAN: {
                actionDisewakan();
                actionName = R.string.sub_venue_disewakan;
                break;
            }
            default: {
                Toast.makeText(requireContext(), "ERROR DEFAULT", Toast.LENGTH_SHORT).show();
            }
        }

        getAppbar();

    }

    private void getAppbar(){

        if (getActivity() != null){

            TextView title = getActivity().findViewById(R.id.sub_title);
            ImageView imgExtend = getActivity().findViewById(R.id.sub_detailed);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);
            LinearLayout linearLayout = getActivity().findViewById(R.id.sub_linear);
            linearLayout.setVisibility(View.VISIBLE);

            title.setText(actionName);
            imgExtend.setVisibility(View.GONE);

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });

        }

    }

    private void openDetailedVenue(String id) {
        startActivity(
                new Intent(requireContext(), DetailedActivity.class)
                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                        .putExtra(DetailedActivity.ID, id)
        );
    }

    private void showRecyclerVenue(ArrayList<VenueExtendedModel> models) {

        // show data
        if (models.size() > 0) {
            recyclerAll.setAdapter(new VenueExtendedAdapter(
                    requireContext(), models, new AdapterActionListener() {
                @Override
                public void onClickListener(int position) {
                    openDetailedVenue(Integer.toString(models.get(position).getidVenue()));
                }
            }
            ));
        } else {
            // TODO : show error when data is 0
        }
    }

    private void actionRating() {

        // request data
        RetrofitClient.getInstance().getAllRating().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionBaru() {

        // request data
        RetrofitClient.getInstance().getAllBaru().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionRekomendasi() {

        // request data
        RetrofitClient.getInstance().getAllRekomendasi().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionKosong() {

        // request data
        RetrofitClient.getInstance().getAllKosong().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionLokasi(){

        // request data
        RetrofitClient.getInstance().getAllLokasi().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    // show recycler view
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionGratis(){

        // request data
        RetrofitClient.getInstance().getAllGratis().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    // show recycler view
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionBebayar(){

        // request data
        RetrofitClient.getInstance().getAllBebayar().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    // show recycler view
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void actionDisewakan(){

        // request data
        RetrofitClient.getInstance().getAllDisewakan().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {
                    // show recycler view
                    showRecyclerVenue(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}