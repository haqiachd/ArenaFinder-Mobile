package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.JadwalPickerModel;
import com.c2.arenafinder.util.AdapterActionListener;

import java.util.ArrayList;

public class JadwalPickerAdapter extends RecyclerView.Adapter<JadwalPickerAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<JadwalPickerModel> models;

    private final AdapterActionListener listener;

    public JadwalPickerAdapter(Context context, ArrayList<JadwalPickerModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JadwalPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JadwalPickerAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_jadwal_picker, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalPickerAdapter.ViewHolder holder, int position) {

        JadwalPickerModel pickerModel = models.get(position);

        holder.txtPrice.setText("Rp. " + pickerModel.getPrice());
        holder.txtSession.setText(pickerModel.getSession());

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtSession, txtPrice;

        public ViewHolder(View view){
            super(view);

            txtPrice = view.findViewById(R.id.ijp_price);
            txtSession = view.findViewById(R.id.ijp_session);
        }

    }
}
