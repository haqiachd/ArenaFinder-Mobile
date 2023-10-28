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
import com.c2.arenafinder.data.model.VenueThirdModel;

import java.util.ArrayList;

public class VenueThirdAdapter extends RecyclerView.Adapter<VenueThirdAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueThirdModel> models;

    public VenueThirdAdapter(Context context, ArrayList<VenueThirdModel> models){
        this.context = context;
        this.models = models;
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

        VenueThirdModel model = models.get(position);

        holder.txtNama.setText(model.getVenueName());
        holder.txtStatus.setText(model.getVenueStatus());
        holder.txtSport.setText(model.getVenueSport());
        holder.txtRatting.setText(String.valueOf(model.getVenueRatting()));
        holder.txtDesc.setText(model.getVenueDesc());

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

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView imgVenue;

        private final TextView txtNama, txtSport, txtRatting, txtStatus, txtDesc;

        public ViewHolder(View view){
            super(view);

            this.view = view;

            imgVenue = view.findViewById(R.id.ivt_venue_image);
            txtNama = view.findViewById(R.id.ivt_venue_name);
            txtSport = view.findViewById(R.id.ivt_venue_sport);
            txtRatting = view.findViewById(R.id.ivt_ratting_value);
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
