package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.StatusPesananModel;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

/**
 * Digunakan untuk menampilkan list dari data Status 'Status Diterima' dan 'Status Ditolak' atau 'Status Diterima'
 * pada MainActivity
 *
 */
public class StatusPesananAdapter extends RecyclerView.Adapter<StatusPesananAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<StatusPesananModel> models;

    public StatusPesananAdapter(Context context, ArrayList<StatusPesananModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_status_pesanan, parent, false)
        );
    }

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get data
        StatusPesananModel model = models.get(position);

        // show data
        holder.txtVenue.setText(model.getVenueName());
        holder.txtPrice.setText(context.getString(R.string.txt_total_price_booking, ArenaFinder.toMoneyCase(model.getTotalPrice())));
        holder.txtJadwal.setText(context.getString(R.string.txt_total_jadwal_val, model.getTotalJadwal()));
        holder.txtMethod.setText(context.getString(R.string.txt_payment_method_val, model.getPaymentMethod()));
        holder.txtTanggal.setText(context.getString(R.string.txt_tanggal_val, ArenaFinder.convertToDate(context, model.getTanggalPesan())));
        holder.setImage(model.getVenuePhoto());

        // tapilkan status pesanan
        switch (model.getPaymentStatus().toLowerCase()) {
            case "pending": {
                holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.status_pesan_dipesan));
                holder.txtStatus.setText(context.getString(R.string.txt_status_booking_dipesan));
                break;
            }
            case "rejected": {
                holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.status_pesan_ditolak));
                holder.txtStatus.setText(context.getString(R.string.txt_status_booking_ditolak, ArenaFinder.convertToDate(context, model.getTanggalPesan())));
                break;
            }
            case "accepted": {
                holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.status_pesan_disetujui));
                holder.txtStatus.setText(context.getString(R.string.txt_status_booking_disetujui, ArenaFinder.convertToDate(context, model.getTanggalPesan())));
                break;
            }
        }

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {

            // aksi saat list di klik
            holder.btnPesan.setOnClickListener(v -> {
                context.startActivity(
                        new Intent(context, DetailedActivity.class)
                                .putExtra(DetailedActivity.FRAGMENT, DetailedActivity.VENUE)
                                .putExtra(DetailedActivity.ID, Integer.toString(model.getIdVenue()))
                );
            });
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    /**
     * Digunakan untuk menghubungkan antara adapter dengan layout-nya dengan menggunakan id
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView imgVenue;

        private final MaterialCardView cardView;

        private final TextView txtVenue, txtPrice, txtMethod, txtJadwal, txtTanggal, txtStatus;

        private final MaterialButton btnPesan;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            cardView = view.findViewById(R.id.isp_card);
            imgVenue = view.findViewById(R.id.isp_venue_photo);
            txtVenue = view.findViewById(R.id.isp_venue_title);
            txtPrice = view.findViewById(R.id.isp_price);
            txtMethod = view.findViewById(R.id.isp_payment);
            txtJadwal = view.findViewById(R.id.isp_jadwal);
            txtTanggal = view.findViewById(R.id.isp_date);
            txtStatus = view.findViewById(R.id.isp_desc);
            btnPesan = view.findViewById(R.id.isp_btn_pesan);
        }

        /**
         * untuk menampilkan gambar dari list
         *
         * @param url dari gambar
         */
        public void setImage(String url) {
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgVenue);
        }

    }
}
