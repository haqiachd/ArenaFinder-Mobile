package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
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

public class AktivitasSecondAdapter extends RecyclerView.Adapter<AktivitasSecondAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<AktivitasModel> models;

    private final AdapterActionListener listener;

    private final String search;

    public AktivitasSecondAdapter(Context context, ArrayList<AktivitasModel> models, AdapterActionListener listener, String search){
        this.context = context;
        this.models = models;
        this.listener = listener;
        if (search != null){
            this.search = search.toLowerCase();
        }else {
            this.search = search;
        }
    }

    public AktivitasSecondAdapter(Context context, ArrayList<AktivitasModel> models, AdapterActionListener listener){
        this(context, models, listener, null);
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
//        holder.txtAnggotaAktivitas.setText(context.getString(R.string.txt_aktivitas_anggota_value, model.getJumlahMember(), model.getMaxMember()));
        holder.txtAnggotaAktivitas.setText(context.getString(R.string.txt_anggota_aktivitas_val, model.getJumlahMember()));
        holder.txtTanggalAktivitas.setText(ArenaFinder.convertToDate(context, model.getDate()));
//        holder.txtTimeAktivitas.setText(context.getString(R.string.txt_aktivitas_jam, model.getStartHour(), model.getEndHour()));
        holder.txtTimeAktivitas.setText(context.getString(R.string.txt_jam_main_val, model.getJamMain()));
        holder.txtHargaAktivitas.setText(context.getString(R.string.txt_aktivitas_price_value, String.valueOf(model.getPrice())));

        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION){

            holder.itemView.setOnClickListener(v -> {
                listener.onClickListener(holder.getAdapterPosition());
            });

        }

        try {

            if (search != null && !search.isEmpty() && !search.isBlank()) {
                String text = model.getNamaAktivitas().toLowerCase();
                int index = text.indexOf(search);
                int endIndex = index + search.length();

                if (index == -1) {
                    index = 0;
                    endIndex++;
                }

                LogApp.info(this, LogTag.LIFEFCYLE, "INDEX --> " + index);
                LogApp.info(this, LogTag.LIFEFCYLE, "END INDEX --> " + endIndex);

                SpannableString spannable = new SpannableString(model.getNamaAktivitas());
                spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), index, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.txtNamaAktivitas.setText(spannable);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogApp.error(this, LogTag.LIFEFCYLE, "ERROR INDEX --> ", e);
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
