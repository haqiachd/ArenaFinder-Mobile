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

    @Override
    public void onBindViewHolder(@NonNull HomeInfoAdapter.ViewHolder holder, int position) {
        holder.setImage(models.get(position).getImgUrl());
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ItemHomeInfoBinding binding;

        public ViewHolder(ItemHomeInfoBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setImage(String url){
            Glide.with(binding.getRoot())
                    .load(RetrofitClient.HOME_PAGER + url)
                    .centerCrop()
                    .into(binding.ihiImage);
        }

    }

}
