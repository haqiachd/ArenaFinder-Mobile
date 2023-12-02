package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.PaymentDetailModel;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class DetailTransaksiAdapter extends RecyclerView.Adapter<DetailTransaksiAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<PaymentDetailModel> models;

    public DetailTransaksiAdapter(Context context, ArrayList<PaymentDetailModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_detail_transaksi, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PaymentDetailModel model = models.get(position);

        holder.txtNamaLapangan.setText(context.getString(R.string.total_jadwal, model.getTotalSesion()));
        holder.txtDate.setText(ArenaFinder.convertToDate(context, model.getTanggal()));

        holder.recyclerView.setAdapter(new RicianSessionAdapter(
                context, model.getRician()
        ));

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtNamaLapangan, txtDate, txtHarga;

        private final RecyclerView recyclerView;

        public ViewHolder(View view){
            super(view);

            txtNamaLapangan = view.findViewById(R.id.idt_nama_lapangan);
            txtDate = view.findViewById(R.id.idt_tgl_pesan);
            txtHarga = view.findViewById(R.id.idt_total_harga);
            recyclerView = view.findViewById(R.id.idt_jadwal_dipilih);
        }

    }
}
