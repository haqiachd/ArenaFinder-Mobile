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
import com.c2.arenafinder.data.model.VenueSecondModel;
import com.c2.arenafinder.util.AdapterActionListener;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VenueSecondAdapter extends RecyclerView.Adapter<VenueSecondAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueSecondModel> models;

    private final AdapterActionListener listener;

    public VenueSecondAdapter(Context context, ArrayList<VenueSecondModel> models, AdapterActionListener listener){
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VenueSecondModel model = models.get(position);

        holder.txtName.setText(model.getVenueName());
        holder.txtStatus.setText(model.getVenueStatus());
        holder.txtSport.setText(model.getVenueSport());
        holder.txtRatting.setText(String.valueOf(model.getVenueRatting()));
        holder.txtDescFirst.setText(model.getVenueDescFirst());
        holder.txtDescSecond.setText(model.getVenueDescSecond());

        holder.setImage(model.getVenueImage());

        // change status venue color
        switch (model.getVenueStatus().toLowerCase()){
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

        // action listener
        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener(v -> {
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

        private ImageView imgLapangan;

        private TextView txtName, txtSport, txtRatting, txtStatus, txtDescFirst, txtDescSecond;

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

        private void setImage(String url){
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgLapangan);
        }

    }

}
