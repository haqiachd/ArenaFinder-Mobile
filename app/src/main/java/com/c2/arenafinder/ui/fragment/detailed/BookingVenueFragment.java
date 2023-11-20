package com.c2.arenafinder.ui.fragment.detailed;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.DatePickerModel;
import com.c2.arenafinder.data.model.JadwalPickerModel;
import com.c2.arenafinder.data.model.VenueBookingModel;
import com.c2.arenafinder.data.response.CreateBookingResponse;
import com.c2.arenafinder.data.response.VenueBookingResponse;
import com.c2.arenafinder.ui.activity.MainActivity;
import com.c2.arenafinder.ui.adapter.DatePickerAdapter;
import com.c2.arenafinder.ui.adapter.VenueBookingAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingVenueFragment extends Fragment {

    private static final String ARG_ID = "id";

    private String id;

    private VenueBookingAdapter venueBookingAdapter;

    private RecyclerView recyclerDate, recyclerVenue;

    private ImageView btnChooseDate;

    private UsersUtil usersUtil;

    private String dateChooser;

    private ArrayList<JadwalPickerModel> pickerModels;

    public BookingVenueFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        recyclerDate = view.findViewById(R.id.fbv_recycler_date);
        recyclerVenue = view.findViewById(R.id.fbv_recycler_lapangan);
        btnChooseDate = view.findViewById(R.id.fbv_date_picker);
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

        usersUtil = new UsersUtil(requireContext());

        showDatePicker();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String currentDate = sdf.format(calendar.getTime());
        showJadwal(currentDate);
        updateBottomNav(0);

        onClickGroups();
    }

    private void onClickGroups() {

        btnChooseDate.setOnClickListener(v -> {

            Toast.makeText(requireContext(), "Date Pick", Toast.LENGTH_SHORT).show();

           View custom = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_material_calendar, null);
           CalendarView calendarView = custom.findViewById(R.id.dia_calendar);

           new AlertDialog.Builder(requireContext())
                   .setView(custom)
                   .create().show();

           // set minimun date
            Calendar cMin = Calendar.getInstance();
            cMin.set(cMin.get(Calendar.YEAR), cMin.get(Calendar.MONTH), cMin.get(Calendar.DAY_OF_MONTH)-1);
            calendarView.setMinimumDate(cMin);
            Calendar c = Calendar.getInstance();

            // set date now background
            List<CalendarDay> dys = new ArrayList<>();
            CalendarDay cday = new CalendarDay(c);
            cday.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_calendar_view_red));
            dys.add(cday);
            calendarView.setCalendarDays(dys);

            calendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(@NonNull EventDay eventDay) {
                    Calendar clickedDayCalendar = eventDay.getCalendar();
                    Calendar kemarin = Calendar.getInstance();
                    kemarin.set(kemarin.get(Calendar.YEAR), kemarin.get(Calendar.MONTH), kemarin.get(Calendar.DAY_OF_MONTH)-1);
                    if (clickedDayCalendar.before(kemarin)){
                        Toast.makeText(requireContext(), "TOLOL", Toast.LENGTH_SHORT).show();
                    }else {
                        String day = "" + clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
                        Toast.makeText(requireContext(), "Date -> " + day, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

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

                showJadwal(models.get(position).getDateMonth());
                dateChooser = models.get(position).getDateMonth();

            }
        });
        recyclerDate.setAdapter(pickerAdapter);

    }

    private void showJadwal(String tanggal) {
        RetrofitClient.getInstance().getJadwal(id, "1", tanggal)
                .enqueue(new Callback<VenueBookingResponse>() {
                    @Override
                    public void onResponse(Call<VenueBookingResponse> call, Response<VenueBookingResponse> response) {

                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            ArrayList<VenueBookingModel> bookingModels = new ArrayList<>();
                            bookingModels.add(response.body().getData());

                            venueBookingAdapter = new VenueBookingAdapter(requireContext(), bookingModels, new AdapterActionListener() {
                                @Override
                                public void onClickListener(int position) {
                                    updateBottomNav(venueBookingAdapter.getSelectedItem());
                                }
                            });

                            recyclerVenue.setAdapter(venueBookingAdapter);

                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<VenueBookingResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveBooking(int item) {
        pickerModels = venueBookingAdapter.getPicker();

        RetrofitClient.getInstance().createBooking(
                id, usersUtil.getEmail(), String.valueOf((75_000 * item))
        ).enqueue(new Callback<CreateBookingResponse>() {
            @Override
            public void onResponse(Call<CreateBookingResponse> call, Response<CreateBookingResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                    for (JadwalPickerModel model : pickerModels) {
                        LogApp.info(this, LogTag.LIFEFCYLE, "----------");
                        LogApp.info(this, LogTag.LIFEFCYLE, "id_price -> " + model.getIdJadwal());
                        LogApp.info(this, LogTag.LIFEFCYLE, "price -> " + model.getPrice());
                        LogApp.info(this, LogTag.LIFEFCYLE, "session -> " + model.getSession());

                        RetrofitClient.getInstance().bookingDetail(
                                Integer.toString(response.body().getData().getIdBooking()), dateChooser,
                                Integer.toString(model.getIdJadwal())
                        ).enqueue(new Callback<CreateBookingResponse>() {
                            @Override
                            public void onResponse(Call<CreateBookingResponse> call, Response<CreateBookingResponse> response) {
                                if (response.body().getStatus().equalsIgnoreCase("success")) {
//                                            LogApp.info(requireContext(), LogTag.LIFEFCYLE, "saving id price -> " + model.getPrice());
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateBookingResponse> call, Throwable t) {

                            }
                        });

                    }

                    new AlertDialog.Builder(requireContext())
                            .setTitle(R.string.dia_title_inform)
                            .setMessage("Booking berhasil, Silahkan tunggu admin pengelola lapangan mengkonfirmasi pesanan booking kamu.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(
                                            new Intent(requireContext(), MainActivity.class)
                                    );
                                }
                            }).setCancelable(false)
                            .create().show();

                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBookingResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateBottomNav(int item) {
        if (getActivity() != null) {
            TextView txtTop, txtData, txtRight;
            MaterialButton button;
            txtTop = getActivity().findViewById(R.id.dtld_nav_txt_top);
            txtRight = getActivity().findViewById(R.id.dtld_nav_txt_right);
            txtData = getActivity().findViewById(R.id.dtld_nav_txt_data);
            button = getActivity().findViewById(R.id.dtld_nav_button);

            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            txtTop.setText("Total Harga");
            txtRight.setText(" dari " + item + " Jadwal");
            txtData.setText("Rp. " + (decimalFormat.format(75_000 * item)));

            button.setOnClickListener(v -> {
                ArenaFinder.playVibrator(getActivity(), ArenaFinder.VIBRATOR_SHORT);
                BottomSheetDialog sheet = new BottomSheetDialog(getActivity(), R.style.BottomSheetTheme);
                View sheetInflater = getActivity().getLayoutInflater().inflate(R.layout.sheet_choose_payment, null);
                sheet.setContentView(sheetInflater);

                sheetInflater.findViewById(R.id.scp_button).setOnClickListener(view -> {
                    saveBooking(item);
                });

                sheet.show();
                sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
                sheet.getWindow().setGravity(Gravity.BOTTOM);
            });

        }
    }

}