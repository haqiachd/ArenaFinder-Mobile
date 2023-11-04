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
import com.c2.arenafinder.data.model.FasilitasModel;

import java.util.ArrayList;

public class VenueFasilitasAdapter extends RecyclerView.Adapter<VenueFasilitasAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<FasilitasModel> models;

    public VenueFasilitasAdapter(Context context, ArrayList<FasilitasModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_fasilitas_tempat, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FasilitasModel model = models.get(position);

        holder.txtFasilitas.setText(model.getNamaFasilitas());
        holder.showFasilitasImage(context, model.getFasilitasPhoto());

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFasilitas;

        private final TextView txtFasilitas;

        public ViewHolder(View itemView) {
            super(itemView);

            imgFasilitas = itemView.findViewById(R.id.ift_foto_fasilitas);
            txtFasilitas = itemView.findViewById(R.id.ift_nama_fasilitias);
        }

        private void showFasilitasImage(Context context, String imgUrl){
            Glide.with(context)
                    .load(RetrofitClient.USER_PHOTO_URL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgFasilitas);
        }
    }
}
