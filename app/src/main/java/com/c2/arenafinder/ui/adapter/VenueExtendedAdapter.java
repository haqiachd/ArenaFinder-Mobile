package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.c2.arenafinder.data.model.VenueExtendedModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class VenueExtendedAdapter extends RecyclerView.Adapter<VenueExtendedAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueExtendedModel> models;

    private final AdapterActionListener listener;

    public VenueExtendedAdapter(Context context, ArrayList<VenueExtendedModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public VenueExtendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VenueExtendedAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_extended, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VenueExtendedAdapter.ViewHolder holder, int position) {
        VenueExtendedModel model = models.get(position);

        holder.txtName.setText(model.getVenueName());
        holder.txtStatus.setText(model.getStatus());
        holder.txtSport.setText(model.getSport());
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
                holder.txtDescFirst.setText("1 Slot Kosong");
                holder.txtDescSecond.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                break;
            }
            case "gratis" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                holder.txtDescSecond.setText(R.string.txt_gratis_v);
                holder.txtDescFirst.setText("");
                break;
            }
            case "berbayar" : {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                holder.txtDescSecond.setText("");
                holder.txtDescFirst.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHarga())));
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

        private final ImageView imgLapangan;

        private final TextView txtName, txtSport, txtRatting, txtStatus, txtDescFirst, txtDescSecond;

        public ViewHolder(View view){
            super(view);
            this.view = view;

            imgLapangan = view.findViewById(R.id.ive_venue_image);
            txtName = view.findViewById(R.id.ive_venue_name);
            txtSport = view.findViewById(R.id.ive_venue_sport);
            txtRatting = view.findViewById(R.id.ive_ratting_value);
            txtStatus = view.findViewById(R.id.ive_venue_status);
            txtDescFirst = view.findViewById(R.id.ive_venue_desc_first);
            txtDescSecond = view.findViewById(R.id.ive_venue_desc_second);

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
                    .into(imgLapangan);
        }

    }
    
}
