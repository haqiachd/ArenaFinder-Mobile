package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.custom.BottomNavCustom;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.appbar.AppBarLayout;

public class VenueDetailedFragment extends Fragment {

    private static final String ARG_ID = "id";

    private String id;

    private AppBarLayout appBarLayout;
    private ScrollView scrollView;

    public VenueDetailedFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        appBarLayout = view.findViewById(R.id.fvd_appbar);
        scrollView = view.findViewById(R.id.fvd_scroll);
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

    private boolean isAppBarVisible = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        TextView textView = view.findViewById(R.id.dvd_venue_name);
        TextView textView1 = view.findViewById(R.id.fvd_nama_lapangan);
        textView.setText(id);
        textView1.setText(id);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
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
        });


    }
}