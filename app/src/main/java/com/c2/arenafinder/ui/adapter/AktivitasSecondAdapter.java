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
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.AktivitasSecondModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class AktivitasSecondAdapter extends RecyclerView.Adapter<AktivitasSecondAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<AktivitasModel> models;

    private AdapterActionListener listener;

    public AktivitasSecondAdapter(Context context, ArrayList<AktivitasModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aktivitas_second, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AktivitasModel model = models.get(position);

        holder.setImage(model.getPhoto());
        holder.txtNamaAktivitas.setText(model.getNamaAktivitas());
        holder.txtVenueAktivitas.setText(model.getVenueName());
        holder.txtAnggotaAktivitas.setText(context.getString(R.string.txt_aktivitas_anggota_value, model.getJumlahMember(), model.getMaxMember()));
        holder.txtTanggalAktivitas.setText(ArenaFinder.convertToDate(model.getDate()));
        holder.txtTimeAktivitas.setText(context.getString(R.string.txt_aktivitas_jam, model.getStartHour(), model.getEndHour()));
        holder.txtHargaAktivitas.setText(context.getString(R.string.txt_aktivitas_price_value, String.valueOf(model.getPrice())));

        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){

            holder.itemView.setOnClickListener(v -> {
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

        private final ImageView imgAktivitas;

        private final TextView txtNamaAktivitas, txtVenueAktivitas, txtAnggotaAktivitas, txtTanggalAktivitas, txtTimeAktivitas, txtHargaAktivitas;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            this.imgAktivitas = view.findViewById(R.id.ias_aktivitas_image);
            this.txtNamaAktivitas = view.findViewById(R.id.ias_aktivitas_name);
            this.txtVenueAktivitas = view.findViewById(R.id.ias_aktivitas_venue);
            this.txtAnggotaAktivitas = view.findViewById(R.id.ias_aktivitas_member);
            this.txtTanggalAktivitas = view.findViewById(R.id.ias_aktivitas_tgl);
            this.txtTimeAktivitas = view.findViewById(R.id.ias_aktivitas_time);
            this.txtHargaAktivitas = view.findViewById(R.id.ias_aktivitas_price_value);
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
