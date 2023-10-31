package com.c2.arenafinder.ui.fragment.detailed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.DatePickerModel;
import com.c2.arenafinder.data.model.JadwalPickerModel;
import com.c2.arenafinder.data.model.VenueBookingModel;
import com.c2.arenafinder.ui.adapter.DatePickerAdapter;
import com.c2.arenafinder.ui.adapter.JadwalPickerAdapter;
import com.c2.arenafinder.ui.adapter.VenueBookingAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookingVenueFragment extends Fragment {

    private static final String ARG_ID = "id";

    private String id;

    private RecyclerView recyclerDate, recyclerVenue;

    public BookingVenueFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        recyclerDate = view.findViewById(R.id.fbv_recycler_date);
        recyclerVenue = view.findViewById(R.id.fbv_recycler_lapangan);
    }


    public static BookingVenueFragment newInstance(String id) {
        BookingVenueFragment fragment = new BookingVenueFragment();
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
        return inflater.inflate(R.layout.fragment_booking_venue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        ArenaFinder.setStatusBarColor(requireActivity(), ArenaFinder.WHITE_STATUS_BAR, R.color.white, true);

        showDatePicker();
        showBookingVenue();
    }

    private void showDatePicker() {

        ArrayList<DatePickerModel> models = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("E", Locale.getDefault());
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM", Locale.getDefault());

        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            // Format tanggal menjadi "yyyy-MM-dd"
            String currentDate = sdf.format(calendar.getTime());

            // Mengambil nama hari dan format "dd MMM" dari tanggal saat ini
            String dayOfWeek = sdfDayOfWeek.format(calendar.getTime());
            String formattedDate = sdfDate.format(calendar.getTime());

            // Membuat objek DatePickerModel dan menambahkannya ke dalam list
            models.add(new DatePickerModel(formattedDate, dayOfWeek, currentDate, false));

            // Menambah satu hari ke tanggal saat ini
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        DatePickerAdapter pickerAdapter = new DatePickerAdapter(requireContext(), models, new AdapterActionListener() {
            @Override
            public void onClickListener(int position) {
                Toast.makeText(requireContext(), models.get(position).getDateMonth(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerDate.setAdapter(pickerAdapter);

    }

    private void showBookingVenue(){

        ArrayList<JadwalPickerModel> pickerModels = new ArrayList<>();
        pickerModels.add(new JadwalPickerModel(1, "07:00 - 08:000", 75000, false,false));
        pickerModels.add(new JadwalPickerModel(1, "07:00 - 08:000", 75000, false,false));
        pickerModels.add(new JadwalPickerModel(1, "07:00 - 08:000", 75000, false,false));

        ArrayList<VenueBookingModel> bookingModels = new ArrayList<>();

        bookingModels.add(new VenueBookingModel(
                1, "", "Lapangan 1", "11 Slot Kosong", pickerModels

        ));
        bookingModels.add(new VenueBookingModel(
                2, "", "Lapangan 2", "12 Slot Kosong", pickerModels

        ));

        recyclerVenue.setAdapter(new VenueBookingAdapter(requireContext(), bookingModels, new AdapterActionListener() {
            @Override
            public void onClickListener(int position) {

            }
        }));

    }
}