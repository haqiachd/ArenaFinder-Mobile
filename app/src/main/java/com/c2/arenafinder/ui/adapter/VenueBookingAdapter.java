package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.VenueBookingModel;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class VenueBookingAdapter extends RecyclerView.Adapter<VenueBookingAdapter.ViewHolder>{

    private final Context context;

    private final ArrayList<VenueBookingModel> models;

    private final AdapterActionListener listener;

    public VenueBookingAdapter(Context context, ArrayList<VenueBookingModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_booking, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VenueBookingModel bookingModel = models.get(position);

        holder.txtNamaLapangan.setText(bookingModel.getLapanganName());
        holder.txtSlotKosong.setText(bookingModel.getTotalSlot());

        holder.recyclerJadwal.setAdapter(new JadwalPickerAdapter(context, bookingModel.getJadwal(), new AdapterActionListener() {
            @Override
            public void onClickListener(int position) {
                AdapterActionListener.super.onClickListener(position);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNamaLapangan, txtSlotKosong;

        private RecyclerView recyclerJadwal;

        public ViewHolder(View view) {
            super(view);

            txtNamaLapangan = view.findViewById(R.id.ivb_lapangan_name);
            txtSlotKosong = view.findViewById(R.id.ivb_lapangan_slot);
            recyclerJadwal = view.findViewById(R.id.ivb_recycler);
        }

    }
}
