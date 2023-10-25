package com.c2.arenafinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.AktivitasFirstModel;
import com.c2.arenafinder.data.model.AktivitasThirdModel;

import java.util.ArrayList;

public class AktivitasThirdAdapter extends RecyclerView.Adapter<AktivitasThirdAdapter.ViewHolder> {

    private final ArrayList<AktivitasThirdModel> models;

    public AktivitasThirdAdapter(ArrayList<AktivitasThirdModel> models){
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

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }

}
