package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JenisLapanganAdapter extends RecyclerView.Adapter<JenisLapanganAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<JenisLapanganModel> models;

    public JenisLapanganAdapter(Context context, ArrayList<JenisLapanganModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_jenis_lapangan, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JenisLapanganModel model = models.get(position);
        holder.txtNamaLapangan.setText(model.getNamaLapangan());
        holder.iconLapangan.setImageDrawable(ContextCompat.getDrawable(context, model.getIconDrawable()));
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        public MaterialCardView cardView;
        public ImageView iconLapangan;
        public TextView txtNamaLapangan;

        public ViewHolder(View view){
            super(view);
            this.view = view;

            this.cardView = view.findViewById(R.id.ijl_card);
            this.iconLapangan = view.findViewById(R.id.ijl_image);
            this.txtNamaLapangan = view.findViewById(R.id.ijl_title);
        }


    }
}
