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
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class AktivitasFirstAdapter extends RecyclerView.Adapter<AktivitasFirstAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<AktivitasModel> models;

    private final AdapterActionListener listener;

    public AktivitasFirstAdapter(Context context, ArrayList<AktivitasModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
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
        AktivitasModel aktivitasModel = models.get(position);

        // show data
        holder.txtNama.setText(aktivitasModel.getNamaAktivitas());
        holder.txtVenue.setText(aktivitasModel.getVenueName());
//        holder.txtAnggota.setText(context.getString(R.string.txt_aktivitas_anggota_value, aktivitasModel.getJumlahMember(), aktivitasModel.getMaxMember()));
        holder.txtAnggota.setText("" + aktivitasModel.getJumlahMember() + " Anggota");
        holder.txtTanggal.setText(ArenaFinder.convertToDate(aktivitasModel.getDate()));
        holder.setAktivitasImage(aktivitasModel.getPhoto());

        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){

            holder.itemView.setOnClickListener(v -> {
                LogApp.info(context, LogTag.ON_CLICK, "on click" + holder.getAdapterPosition());
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
