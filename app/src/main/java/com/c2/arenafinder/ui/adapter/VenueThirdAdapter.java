package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.model.VenueThirdModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class VenueThirdAdapter extends RecyclerView.Adapter<VenueThirdAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<ReferensiModel> models;

    private final AdapterActionListener listener;

    public VenueThirdAdapter(Context context, ArrayList<ReferensiModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_thrid, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ReferensiModel model = models.get(position);

        holder.txtNama.setText(model.getVenueName());
        holder.txtStatus.setText(model.getStatus());
        holder.txtSport.setText(model.getSport());

        if (model.getRating() <= 0.0){
            holder.txtRatting.setText(R.string.txt_ratting_na);
            holder.txtReview.setVisibility(View.GONE);
        }else {
            holder.txtRatting.setText(ArenaFinder.oneComa(model.getRating()));
            holder.txtReview.setText(context.getString(R.string.txt_ulasan_2, String.valueOf(model.getTotalReview())));
        }


        holder.setImage(model.getVenuePhoto());

        // change status venue color
        switch (model.getStatus().toLowerCase()){
            case "disewakan" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_disewakan, R.color.venue_status_disewakan);
                holder.txtDesc.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                break;
            }
            case "gratis" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                holder.txtDesc.setText(context.getString(R.string.txt_gratis_v));
                break;
            }
            case "berbayar" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                holder.txtDesc.setText(context.getString(R.string.txt_berbayar_val, ArenaFinder.toMoneyCase(model.getHarga())));
                break;
            }
            case "bervariasi" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_variasi, R.color.venue_status_bervariasi);
                break;
            }
        }

        // action listener
        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener(v -> {
                LogApp.info(context, LogTag.ON_CLICK, "adapter clicked on -> " + holder.getAdapterPosition());
                listener.onClickListener(position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView imgVenue;

        private final TextView txtNama, txtSport, txtRatting, txtReview, txtStatus, txtDesc;

        public ViewHolder(View view){
            super(view);

            this.view = view;

            imgVenue = view.findViewById(R.id.ivt_venue_image);
            txtNama = view.findViewById(R.id.ivt_venue_name);
            txtSport = view.findViewById(R.id.ivt_venue_sport);
            txtRatting = view.findViewById(R.id.ivt_ratting_value);
            txtReview = view.findViewById(R.id.ivt_ratting_ulasan);
            txtStatus = view.findViewById(R.id.ivt_venue_status);
            txtDesc = view.findViewById(R.id.ivt_venue_desc_second);
        }

        public void setStatusColor(Context context, @DrawableRes int background, @ColorRes int textColor){
            txtStatus.setTextColor(ContextCompat.getColor(context, textColor));
            txtStatus.setBackground(ContextCompat.getDrawable(context, background));
        }

        private void setImage(String url){
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgVenue);
        }

    }
}
