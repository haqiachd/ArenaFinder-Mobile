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
import com.c2.arenafinder.data.model.AktivitasThirdModel;

import java.util.ArrayList;

public class AktivitasThirdAdapter extends RecyclerView.Adapter<AktivitasThirdAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<AktivitasThirdModel> models;

    public AktivitasThirdAdapter(Context context, ArrayList<AktivitasThirdModel> models){
        this.context = context;
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
        holder.txtAnggotaAktivitas.setText(context.getString(R.string.txt_aktivitas_anggota_value, model.getAnggota(), model.getAnggotaMax()));
        holder.txtTanggalAktivitas.setText(model.getTanggal());
        holder.txtTimeAktivitas.setText(model.getTime());
        holder.txtHargaAktivitas.setText(context.getString(R.string.txt_aktivitas_price_value, String.valueOf(model.getPrice())));

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

            this.imgAktivitas = view.findViewById(R.id.iat_aktivitas_image);
            this.txtNamaAktivitas = view.findViewById(R.id.iat_aktivitas_name);
            this.txtVenueAktivitas = view.findViewById(R.id.iat_aktivitas_venue);
            this.txtAnggotaAktivitas = view.findViewById(R.id.iat_aktivitas_member);
            this.txtTanggalAktivitas = view.findViewById(R.id.iat_aktivitas_tgl);
            this.txtTimeAktivitas = view.findViewById(R.id.iat_aktivitas_time);
            this.txtHargaAktivitas = view.findViewById(R.id.iat_aktivitas_price_value);
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
