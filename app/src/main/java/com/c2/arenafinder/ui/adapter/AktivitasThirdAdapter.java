package com.c2.arenafinder.ui.adapter;

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
import com.c2.arenafinder.data.model.AktivitasFirstModel;
import com.c2.arenafinder.data.model.AktivitasThirdModel;

import java.util.ArrayList;

public class AktivitasThirdAdapter extends RecyclerView.Adapter<AktivitasThirdAdapter.ViewHolder> {

    private final ArrayList<AktivitasThirdModel> models;

    public AktivitasThirdAdapter(ArrayList<AktivitasThirdModel> models){
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aktivitas_thrid, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AktivitasThirdModel model = models.get(position);

        holder.setImage(model.getImage());
        holder.txtNamaAktivitas.setText(model.getName());
        holder.txtVenueAktivitas.setText(model.getVenue());
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView imgAktivitas;

        private final TextView txtNamaAktivitas, txtVenueAktivitas;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            this.imgAktivitas = view.findViewById(R.id.iat_image_aktivitas);
            this.txtNamaAktivitas = view.findViewById(R.id.iat_aktivitas_name);
            this.txtVenueAktivitas = view.findViewById(R.id.iat_aktivitas_venue);
        }

        private void setImage(String uri){

            Glide.with(view)
                    .load(RetrofitClient.AKTIVITAS_URL + uri)
                    .placeholder(R.drawable.ic_profile)
                    .centerCrop()
                    .into(imgAktivitas);

        }

    }

}
