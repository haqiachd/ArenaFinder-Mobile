package com.c2.arenafinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.HomeInfoModel;
import com.c2.arenafinder.databinding.ItemHomeInfoBinding;

import java.util.ArrayList;

/**
 * Digunakan untuk menampilkan list dari data Informasi Aplikasi pada MainActivity
 *
 */
public class HomeInfoAdapter extends RecyclerView.Adapter<HomeInfoAdapter.ViewHolder> {

    private final ArrayList<HomeInfoModel> models;

    public HomeInfoAdapter(ArrayList<HomeInfoModel> models){
        this.models = models;
    }

    @NonNull
    @Override
    public HomeInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemHomeInfoBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                )
        );
    }

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull HomeInfoAdapter.ViewHolder holder, int position) {

        // menampilkan gambar
        holder.setImage(models.get(position).getImgUrl());
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

        public final ItemHomeInfoBinding binding;

        public ViewHolder(ItemHomeInfoBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * untuk menampilkan gambar dari list
         *
         * @param url dari gambar
         */
        public void setImage(String url){
            Glide.with(binding.getRoot())
                    .load(RetrofitClient.HOME_PAGER + url)
                    .centerCrop()
                    .into(binding.ihiImage);
        }

    }

}
