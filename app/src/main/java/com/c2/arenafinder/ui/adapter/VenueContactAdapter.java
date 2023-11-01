package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenueContactModel;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;

public class VenueContactAdapter extends RecyclerView.Adapter<VenueContactAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueContactModel> models;

    private final AdapterActionListener listener;

    public VenueContactAdapter(Context context, ArrayList<VenueContactModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_contact, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VenueContactModel contactModel = models.get(position);

        holder.txtNoHp.setText(contactModel.getNoHp());
        holder.txtFullName.setText(contactModel.getFullName());
        holder.showProfile(context, contactModel.getImgProfile());

        if(holder.getAdapterPosition() != RecyclerView.NO_POSITION){

            holder.imgWhatsapp.setOnClickListener(v -> {
                listener.onClickListener(holder.getAdapterPosition());
            });
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile, imgWhatsapp;

        private final TextView txtFullName, txtNoHp;

        public ViewHolder(View view){
            super(view);

            this.imgProfile = view.findViewById(R.id.ivc_photo);
            this.imgWhatsapp = view.findViewById(R.id.ivc_whatsapp);
            this.txtFullName = view.findViewById(R.id.ivc_fullname);
            this.txtNoHp = view.findViewById(R.id.ivc_nohp);

        }

        private void showProfile(Context context, String img){
            Glide.with(context)
                    .load(RetrofitClient.USER_PHOTO_URL + img)
                    .centerCrop()
                    .into(imgProfile);
        }

    }

}
