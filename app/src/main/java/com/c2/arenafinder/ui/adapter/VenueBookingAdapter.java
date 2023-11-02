package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.VenueBookingModel;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;


public class VenueBookingAdapter extends RecyclerView.Adapter<VenueBookingAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueBookingModel> models;

    private final AdapterActionListener listener;

    private JadwalPickerAdapter jadwalPickerAdapter;

    public VenueBookingAdapter(Context context, ArrayList<VenueBookingModel> models, AdapterActionListener listener) {
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

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            holder.lapanganLayout.setOnClickListener(v -> {
                if (holder.jadwalPickerLayout.getVisibility() == View.VISIBLE) {
                    holder.jadwalPickerLayout.setVisibility(View.GONE);
                    holder.imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_expanded));
                } else {
                    holder.jadwalPickerLayout.setVisibility(View.VISIBLE);
                    holder.imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_collapse));
                }
            });

            jadwalPickerAdapter = new JadwalPickerAdapter(context, models.get(position).getJadwal(), new AdapterActionListener() {
                @Override
                public void onClickListener(int position) {
                    listener.onClickListener(position);
                }
            });

            holder.recyclerJadwal.setAdapter(jadwalPickerAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public int getSelectedItem() {
        return jadwalPickerAdapter.getSelectedItem();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout lapanganLayout;

        private final LinearLayout jadwalPickerLayout;

        private final TextView txtNamaLapangan, txtSlotKosong;

        private final ImageView imgStatus;

        private final RecyclerView recyclerJadwal;

        public ViewHolder(View view) {
            super(view);

            lapanganLayout = view.findViewById(R.id.ivb_lapangan_layout);
            jadwalPickerLayout = view.findViewById(R.id.ivb_jadwal_picker);
            txtNamaLapangan = view.findViewById(R.id.ivb_lapangan_name);
            txtSlotKosong = view.findViewById(R.id.ivb_lapangan_slot);
            recyclerJadwal = view.findViewById(R.id.ivb_recycler);
            imgStatus = view.findViewById(R.id.ivb_card_status);


        }

    }
}
