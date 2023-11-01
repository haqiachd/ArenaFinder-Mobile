package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.JadwalPickerModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class JadwalPickerAdapter extends RecyclerView.Adapter<JadwalPickerAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<JadwalPickerModel> models;

    private final AdapterActionListener listener;

    private int selectedItem = 0;

    public JadwalPickerAdapter(Context context, ArrayList<JadwalPickerModel> models, AdapterActionListener listener) {
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

        if (pickerModel.isSelected()) {
            holder.setSelectedItem(context);
        } else {
            holder.setUnselectedItem(context);
        }

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener(v -> {
                JadwalPickerModel model = models.get(holder.getAdapterPosition());
                if (model.isSelected()) {
                    selectedItem--;
                } else {
                    selectedItem++;
                }
                model.setSelected(!model.isSelected());
                models.set(holder.getAdapterPosition(), model);
                notifyItemChanged(holder.getAdapterPosition());
                listener.onClickListener(position);
            });
        }

    }

    public int getSelectedItem(){
        return selectedItem;
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView layout;

        private final TextView txtSession, txtPrice;

        private final ImageView imgSelect;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.ijp_layout);
            txtPrice = view.findViewById(R.id.ijp_price);
            txtSession = view.findViewById(R.id.ijp_session);
            imgSelect = view.findViewById(R.id.ijp_icon_select);
        }

        private void setSelectedItem(Context context) {
            layout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.booking_card_background_selected));
            layout.setStrokeColor(ContextCompat.getColor(context, R.color.azure));
            txtPrice.setTextColor(ContextCompat.getColor(context, R.color.azure));
            txtSession.setTextColor(ContextCompat.getColor(context, R.color.azure));
            imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_booking_checked));
        }

        private void setUnselectedItem(Context context) {
            layout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background_light));
            layout.setStrokeColor(ContextCompat.getColor(context, R.color.booking_card_stroke));
            txtPrice.setTextColor(ContextCompat.getColor(context, R.color.booking_textsize_color));
            txtSession.setTextColor(ContextCompat.getColor(context, R.color.booking_textsize_color));
            imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_booking_unchecked));
        }

    }
}
