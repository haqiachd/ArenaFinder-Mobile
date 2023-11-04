package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c2.arenafinder.R;

public class WriteReviewFragment extends Fragment {

    private static final String ARG_VENUE = "id_venue";
    private static final String ARG_STAR = "star";

    private String idVenue;
    private int star;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    public static WriteReviewFragment newInstance(String id, int star) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VENUE, id);
        args.putInt(ARG_STAR, star);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idVenue = getArguments().getString(ARG_VENUE);
            star = getArguments().getInt(ARG_STAR);
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

    }
}