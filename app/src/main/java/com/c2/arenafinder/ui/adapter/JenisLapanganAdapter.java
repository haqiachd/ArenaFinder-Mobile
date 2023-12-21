package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.JenisLapanganModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Digunakan untuk menampilkan list dari data Jenis Lapangan pada MainActivity
 *
 */
public class JenisLapanganAdapter extends RecyclerView.Adapter<JenisLapanganAdapter.ViewHolder>{

    private final Context context;

    private final ArrayList<JenisLapanganModel> models;

    private AdapterActionListener listener;

    public JenisLapanganAdapter(Context context, ArrayList<JenisLapanganModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_jenis_lapangan, parent, false)
        );
    }

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data
        JenisLapanganModel model = models.get(position);

        // show data
        holder.txtNamaLapangan.setText(model.getNamaLapangan());
        holder.iconLapangan.setImageDrawable(ContextCompat.getDrawable(context, model.getIconDrawable()));

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION){
            // aksi saat list di klik
            holder.itemView.setOnClickListener(v -> {
                listener.onClickListener(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    /**
     * Digunakan untuk menghubungkan antara adapter dengan layout-nya dengan menggunakan id
     *
     */
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
