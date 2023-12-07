package com.c2.arenafinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenuePhotos;
import com.c2.arenafinder.databinding.ItemVenuePhotoBinding;

import java.util.ArrayList;

public class VenuePhotoAdapter extends RecyclerView.Adapter<VenuePhotoAdapter.ViewHolder> {

    private final ArrayList<VenuePhotos> models;

    public VenuePhotoAdapter(ArrayList<VenuePhotos> models){
        this.models = models;
    }

    @NonNull
    @Override
    public VenuePhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VenuePhotoAdapter.ViewHolder(
                ItemVenuePhotoBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VenuePhotoAdapter.ViewHolder holder, int position) {
        holder.setImage(models.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemVenuePhotoBinding itemView;

        public ViewHolder(ItemVenuePhotoBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        private void setImage(String url){
            Glide.with(itemView.getRoot())
                    .load(RetrofitClient.PUBLIC_IMG + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder_venue_detailed)
                    .into(itemView.ivpImage);
        }
    }
}