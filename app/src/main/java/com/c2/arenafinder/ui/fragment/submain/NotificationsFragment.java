package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.NotificationModel;
import com.c2.arenafinder.ui.adapter.NotificationAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fno_recycler);

        ArrayList<NotificationModel> models = new ArrayList<>();
        models.add(new NotificationModel(
                1, "Pesanan Diterima", "Pesanan kamu pada 2023-10-10 telah diterima olah pengelola lapangan","Pesanan-Diterima", "2020-10-10")
        );
        models.add(new NotificationModel(
                1, "Pesanan Diterima", "Pesanan kamu pada 2023-10-10 telah diterima olah pengelola lapangan","Pesanan-Diterima", "2020-10-10")
        );
        models.add(new NotificationModel(
                1, "Pesanan Diterima", "Pesanan kamu pada 2023-10-10 telah diterima olah pengelola lapangan","Pesanan-Diterima", "2020-10-10")
        );

        recyclerView.setAdapter(new NotificationAdapter(
                models
        ));


    }
}