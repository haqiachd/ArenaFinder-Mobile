package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c2.arenafinder.R;

public class SportTypeFragment extends Fragment {

    public static int TYPE_ALL = 1, TYPE_ACTIVITY = 2, TYPE_VENUE = 3;

    private static final String ARG_ACTION = "action";
    private static final String ARG_SPORT = "sport";

    // TODO: Rename and change types of parameters
    private int action;
    private String sport;

    public SportTypeFragment() {
        // Required empty public constructor
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

        superAppbar();
    }

    private void superAppbar(){
        if (getActivity() != null){

            TextView textView = getActivity().findViewById(R.id.sub_title);
            textView.setText(sport);

        }
    }
}