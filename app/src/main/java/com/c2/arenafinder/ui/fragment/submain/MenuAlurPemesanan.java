package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c2.arenafinder.R;

public class MenuAlurPemesanan extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuAlurPemesanan() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MenuAlurPemesanan newInstance(String param1, String param2) {
        MenuAlurPemesanan fragment = new MenuAlurPemesanan();
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
        return inflater.inflate(R.layout.fragment_menu_alur_pemesanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAppbar();
    }

    private void getAppbar(){
        if (getActivity() != null){
            LinearLayout linearLayout = getActivity().findViewById(R.id.sub_linear);
            linearLayout.setVisibility(View.GONE);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            txtTitle.setText(R.string.txt_menu_alur_pesan);
        }
    }
}