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
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class VenueFirstAdapter extends RecyclerView.Adapter<VenueFirstAdapter.ViewHolder> {

    public static final int DEFAULT = 1, RATTING = 2,  SLOT = 6;

    private final Context context;

    private final ArrayList<ReferensiModel> models;

    private final AdapterActionListener listener;

    private final int status;

    public VenueFirstAdapter(Context context, ArrayList<ReferensiModel> models, AdapterActionListener listener, int status){
        this.context = context;
        this.models = models;
        this.listener = listener;
        this.status = status;
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
        ReferensiModel model = models.get(position);

        // show data
        holder.txtNama.setText(model.getVenueName());
        holder.txtSport.setText(model.getSport());
        holder.txtStatus.setText(model.getStatus());
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
                holder.txtDesc.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                break;
            }
            case "gratis" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                holder.txtDesc.setText(R.string.txt_gratis_v);
                break;
            }
            case "berbayar" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                holder.txtDesc.setText(context.getString(R.string.txt_berbayar_val, ArenaFinder.toMoneyCase(model.getHarga())));
                break;
            }
            case "bervariasi" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_variasi, R.color.venue_status_bervariasi);
                holder.txtDesc.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                break;
            }

        }

        switch (status){
            case RATTING: {
                holder.txtDesc.setText(context.getString(R.string.txt_ulasan, String.valueOf(model.getTotalReview())));
                break;
            }
            case SLOT : {
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
