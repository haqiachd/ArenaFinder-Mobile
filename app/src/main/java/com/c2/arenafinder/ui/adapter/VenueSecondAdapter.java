package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Digunakan untuk menampilkan list dari data Rekomendasi dan Sedang Kosong pada MainActivity
 *
 */
public class VenueSecondAdapter extends RecyclerView.Adapter<VenueSecondAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<ReferensiModel> models;

    private final AdapterActionListener listener;

    public VenueSecondAdapter(Context context, ArrayList<ReferensiModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_second, parent, false)
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
        ReferensiModel model = models.get(position);

        // show data
        holder.txtName.setText(model.getVenueName());
        holder.txtSport.setText(ArenaFinder.localizationSport(model.getSport()));
        holder.txtRatting.setText(String.valueOf(model.getRating()));

        holder.setImage(model.getVenuePhoto());

        if (model.getRating() <= 0.0){
            holder.txtRatting.setText(R.string.txt_ratting_na);
        }else {
            holder.txtRatting.setText(ArenaFinder.oneComa(model.getRating()));
        }

        // change status venue color
        switch (model.getStatus().toLowerCase()){
            case "disewakan" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_disewakan, R.color.venue_status_disewakan);
                holder.txtDescFirst.setText(context.getString(R.string.slot_kosong_values, model.getSlotKosong()));
                holder.txtDescSecond.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                holder.txtStatus.setText(R.string.status_disewakan);
                break;
            }
            case "gratis" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                holder.txtDescSecond.setText(R.string.txt_gratis_v);
                holder.txtDescFirst.setText("");
                holder.txtStatus.setText(R.string.status_gratis);
                break;
            }
            case "berbayar" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                holder.txtDescSecond.setText("");
                holder.txtDescFirst.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHarga())));
                holder.txtStatus.setText(R.string.status_berbayar);
                break;
            }
            case "bervariasi" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_variasi, R.color.venue_status_bervariasi);
                holder.txtStatus.setText(R.string.status_bervariasi);
                break;
            }
        }

        // action listener
        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){
            // aksi saat list di klik
            holder.itemView.setOnClickListener(v -> {
                LogApp.info(context, LogTag.ON_CLICK, "adapter clicked on -> " + holder.getAdapterPosition());
                listener.onClickListener(holder.getAdapterPosition());
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

        private final ImageView imgLapangan;

        private final TextView txtName, txtSport, txtRatting, txtStatus, txtDescFirst, txtDescSecond;

        public ViewHolder(View view){
            super(view);
            this.view = view;

            imgLapangan = view.findViewById(R.id.ivs_venue_image);
            txtName = view.findViewById(R.id.ivs_venue_name);
            txtSport = view.findViewById(R.id.ivs_venue_sport);
            txtRatting = view.findViewById(R.id.ivs_ratting_value);
            txtStatus = view.findViewById(R.id.ivs_venue_status);
            txtDescFirst = view.findViewById(R.id.ivs_venue_desc_first);
            txtDescSecond = view.findViewById(R.id.ivs_venue_desc_second);

        }

        public void setStatusColor(Context context, @DrawableRes int background, @ColorRes int textColor){
            txtStatus.setTextColor(ContextCompat.getColor(context, textColor));
            txtStatus.setBackground(ContextCompat.getDrawable(context, background));
        }

        /**
         * untuk menampilkan gambar dari list
         *
         * @param url dari gambar
         */
        private void setImage(String url){
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder_venue_detailed)
                    .into(imgLapangan);
        }

    }

}
