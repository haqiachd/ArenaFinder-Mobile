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
import com.c2.arenafinder.data.model.AktivitasFirstModel;

import java.util.ArrayList;

public class AktivitasFirstAdapter extends RecyclerView.Adapter<AktivitasFirstAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<AktivitasFirstModel> models;

    public AktivitasFirstAdapter(Context context, ArrayList<AktivitasFirstModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aktivitas_first, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get data
        AktivitasFirstModel aktivitasModel = models.get(position);

        // show data
        holder.txtNama.setText(aktivitasModel.getAktivitasName());
        holder.txtVenue.setText(aktivitasModel.getAktivitasVenue());
        holder.txtAnggota.setText(context.getString(R.string.txt_aktivitas_anggota_value, aktivitasModel.getAktivitasAnggota(), aktivitasModel.getAktivitasAnggotaMax()));
        holder.txtTanggal.setText(aktivitasModel.getAktivitasTanggal());
        holder.setAktivitasImage(aktivitasModel.getAktivitasImage());

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

     public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final ImageView aktivitasImage;

        private final TextView txtNama, txtVenue, txtAnggota, txtTanggal;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            aktivitasImage = view.findViewById(R.id.iaf_aktivitas_image);
            txtNama = view.findViewById(R.id.iaf_aktivitas_name);
            txtVenue = view.findViewById(R.id.iaf_aktivitas_venue);
            txtAnggota = view.findViewById(R.id.iaf_aktivitas_member);
            txtTanggal = view.findViewById(R.id.iaf_aktivitas_tgl);
        }

        public void setAktivitasImage(String url){
            Glide.with(view)
                    .load(RetrofitClient.AKTIVITAS_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(aktivitasImage);
        }

    }
}
