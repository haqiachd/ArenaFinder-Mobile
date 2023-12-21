package com.c2.arenafinder.ui.fragment.submain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.c2.arenafinder.ui.adapter.BookingTabAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCommunityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TabLayout tabLayuot;
    private ViewPager viewPager;

    public MenuCommunityFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        tabLayuot = view.findViewById(R.id.fmk_tab);
        viewPager = view.findViewById(R.id.fmk_viewpager);
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
        getAppbar();

        BookingTabAdapter adapter = new BookingTabAdapter(requireActivity().getSupportFragmentManager());

        // tab tab status on going, finished
        adapter.addFragment(new TabActivityOngoingFragment(), getString(R.string.history_activity_comingsoon));
        adapter.addFragment(new TabActivityFinishedFragment(), getString(R.string.history_activity_finished));

        viewPager.setAdapter(adapter);
        tabLayuot.setupWithViewPager(viewPager);
    }

    private void getAppbar(){
        if (getActivity() != null){

            LinearLayout linearLayout = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            txtTitle.setText(R.string.txt_menu_aktivitas_komunitas);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }

}