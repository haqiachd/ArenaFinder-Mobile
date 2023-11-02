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
import com.c2.arenafinder.data.model.VenueFirstModel;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;

public class VenueFirstAdapter extends RecyclerView.Adapter<VenueFirstAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<ReferensiModel> models;

    private final AdapterActionListener listener;

    public VenueFirstAdapter(Context context, ArrayList<ReferensiModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_first, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data
        ReferensiModel kosongModel = models.get(position);

        // show data
        holder.txtNama.setText(kosongModel.getVenueName());
        holder.txtSport.setText(kosongModel.getSport());
        holder.txtRatting.setText(String.valueOf(kosongModel.getRating()));
        holder.txtStatus.setText(kosongModel.getStatus());
        holder.setImage(kosongModel.getVenuePhoto());

        // change status venue color
        switch (kosongModel.getStatus().toLowerCase()){
            case "disewakan" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_disewakan, R.color.venue_status_disewakan);
                break;
            }
            case "gratis" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                break;
            }
            case "berbayar" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                break;
            }
            case "bervariasi" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_variasi, R.color.venue_status_bervariasi);
                break;
            }
        }

        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView imgVenue;

        private final TextView txtNama, txtSport, txtRatting, txtStatus, txtDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            imgVenue = itemView.findViewById(R.id.ihsk_venue_image);
            txtNama = itemView.findViewById(R.id.ihsk_venue_name);
            txtSport = itemView.findViewById(R.id.ihsk_venue_sport);
            txtRatting = itemView.findViewById(R.id.ihsk_ratting_value);
            txtStatus = itemView.findViewById(R.id.ihsk_venue_status);
            txtDesc = itemView.findViewById(R.id.ihsk_venue_desc_second);
        }

        public void setStatusColor(Context context, @DrawableRes int background, @ColorRes int textColor){
            txtStatus.setTextColor(ContextCompat.getColor(context, textColor));
            txtStatus.setBackground(ContextCompat.getDrawable(context, background));
        }

        public void setImage(String url){
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgVenue);
        }
    }
}
