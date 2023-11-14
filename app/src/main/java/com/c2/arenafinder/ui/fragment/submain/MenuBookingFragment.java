package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.adapter.BookingTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class MenuBookingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TabLayout tabLayuot;
    private ViewPager viewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuBookingFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        tabLayuot = view.findViewById(R.id.fmb_tab);
        viewPager = view.findViewById(R.id.fmb_viewpager);
    }

    public static MenuBookingFragment newInstance(String param1, String param2) {
        MenuBookingFragment fragment = new MenuBookingFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu_booking, container, false);

        initViews(view);

        BookingTabAdapter adapter = new BookingTabAdapter(requireActivity().getSupportFragmentManager());

        adapter.addFragment(new TabDipesanFragment(), "Di pesan");
        adapter.addFragment(new TabDisetujuiFragment(), "Di setujui");
        adapter.addFragment(new TabDitolakFragment(), "Di tolak");

        // Tambahkan fragment-fragment lainnya
        viewPager.setAdapter(adapter);
        tabLayuot.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAppbar();
    }

    private void getAppbar(){
        if (getActivity() != null){
            LinearLayout linearLayout = getActivity().findViewById(R.id.sub_linear);
            linearLayout.setVisibility(View.VISIBLE);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            txtTitle.setText(R.string.txt_histori_booking);
        }
    }
}