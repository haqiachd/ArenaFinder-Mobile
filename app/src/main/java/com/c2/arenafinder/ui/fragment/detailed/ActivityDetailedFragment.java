package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c2.arenafinder.R;

public class ActivityDetailedFragment extends Fragment {

    private static final String ID = "id";

    private String id;

    public ActivityDetailedFragment() {
        // Required empty public constructor
    }

    public static ActivityDetailedFragment newInstance(String id) {
        ActivityDetailedFragment fragment = new ActivityDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getActivity() != null){
            ConstraintLayout bot = getActivity().findViewById(R.id.detailed_bottom_nav);
            bot.setVisibility(View.GONE);
        }
    }
}