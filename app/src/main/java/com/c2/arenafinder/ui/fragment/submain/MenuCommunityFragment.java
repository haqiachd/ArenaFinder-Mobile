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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.response.AktivitasResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.adapter.AktivitasSecondAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCommunityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    public MenuCommunityFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        recyclerView = view.findViewById(R.id.fmk_recycler);
    }

    public static MenuCommunityFragment newInstance(String param1, String param2) {
        MenuCommunityFragment fragment = new MenuCommunityFragment();
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
        return inflater.inflate(R.layout.fragment_menu_komunitas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fetchData();
        getAppbar();
    }

    private void getAppbar(){
        if (getActivity() != null){

            LinearLayout linearLayout = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            txtTitle.setText(R.string.txt_menu_aktivitas_komunitas);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }

    private void fetchData() {

        RetrofitClient.getInstance().aktivitasPage().enqueue(new Callback<AktivitasResponse>() {
            @Override
            public void onResponse(Call<AktivitasResponse> call, Response<AktivitasResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                    LogApp.info(this, LogTag.RETROFIT_ON_RESPONSE, "ON RESPONSE");
                    AktivitasResponse.Data data = response.body().getData();

                    // get data model
                    ArrayList<AktivitasModel> aktivitasBaru = data.getAktivitasBaru();
                    ArrayList<AktivitasModel> akivitasKosong = data.getAktivitasKosong();
                    ArrayList<AktivitasModel> semuaAktivitas = data.getSemuaAktivitas();

                    if (aktivitasBaru.size() == 0 && akivitasKosong.size() == 0 && semuaAktivitas.size() == 0) {

                    } else {

                        // show recycler data
                        showSemuaAktivitasyList(semuaAktivitas);
                    }

                } else {
                    Toast.makeText(requireActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AktivitasResponse> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ArenaFinder.VibratorToast(requireActivity(), t.getMessage(), Toast.LENGTH_LONG, ArenaFinder.VIBRATOR_MEDIUM);
            }
        });
    }

    private void showSemuaAktivitasyList(ArrayList<AktivitasModel> models) {

        if (models.size() == 0) {
        } else {
            if (isAdded()) {
                LogApp.info(requireContext(), "semua aktivitas size -> " + models.size());
                recyclerView.setAdapter(new AktivitasSecondAdapter(
                        requireContext(), models, new AdapterActionListener() {
                    @Override
                    public void onClickListener(int position) {
                        startActivity(
                                new Intent(requireActivity(), DetailedActivity.class)
                                        .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.ACTIVITY)
                                        .putExtra(DetailedActivity.ID, Integer.toString(models.get(position).getidAktvitias()))
                        );
                    }
                }
                ));
            }
        }

    }

}