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
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.google.android.material.button.MaterialButton;

import com.c2.arenafinder.R;

import java.util.ArrayList;

public class StatusAktivitasAdapter extends RecyclerView.Adapter<StatusAktivitasAdapter.ViewHolder>{

    private final Context context;

    private final ArrayList<AktivitasModel> models;

    private final AdapterActionListener listener;

    private final boolean isFinished;

    public StatusAktivitasAdapter(Context context, ArrayList<AktivitasModel> models, AdapterActionListener listener, boolean isFinished){
        this.context = context;
        this.models = models;
        this.listener = listener;
        this.isFinished = isFinished;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_status_aktivitas, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AktivitasModel model = models.get(position);

        holder.txtAktivitasName.setText(model.getNamaAktivitas());
        holder.txtPrice.setText(context.getString(R.string.txt_total_price_booking, ArenaFinder.toMoneyCase(model.getPrice())));
        holder.txtVenue.setText(context.getString(R.string.def_titik_dua, model.getVenueName()));
        holder.txtJamMain.setText(context.getString(R.string.txt_jam_main_val2, model.getJamMain()));
        holder.txtTanggal.setText(context.getString(R.string.def_titik_dua, ArenaFinder.convertToDate(model.getDate())));

        holder.setImageAktivitas(model.getPhoto());

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION && listener != null){
            holder.btnLeave.setOnClickListener(v -> {
                listener.onClickListener(position);
            });
        }

        if (isFinished){
            holder.line.setVisibility(View.GONE);
            holder.btnLeave.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final View view, line;

        private final ImageView imgActivity;

        private final TextView txtAktivitasName, txtPrice, txtVenue, txtJamMain, txtTanggal;

        private final MaterialButton btnLeave;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.line = view.findViewById(R.id.isa_line);
            this.imgActivity = view.findViewById(R.id.isa_venue_photo);
            this.txtAktivitasName = view.findViewById(R.id.isa_aktivitas_title);
            this.txtPrice = view.findViewById(R.id.isa_price);
            this.txtVenue = view.findViewById(R.id.isa_lokasi);
            this.txtJamMain = view.findViewById(R.id.isa_jadwal);
            this.txtTanggal = view.findViewById(R.id.isa_date);
            this.btnLeave = view.findViewById(R.id.isa_btn_pesan);

        }

        private void setImageAktivitas(String url){
            Glide.with(view)
                    .load(RetrofitClient.AKTIVITAS_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgActivity);
        }

    }
}
