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
import com.c2.arenafinder.data.model.DatePickerModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class DatePickerAdapter extends RecyclerView.Adapter<DatePickerAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<DatePickerModel> models;

    private final AdapterActionListener listener;

    private int selectedModel = -500;

    public DatePickerAdapter(Context context, ArrayList<DatePickerModel> models, AdapterActionListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_date_picker, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DatePickerModel pickerModel = models.get(position);

        holder.txtDayName.setText(pickerModel.getDayName());
        holder.txtDate.setText(pickerModel.getDate());

        if (pickerModel.isClicked()) {
            holder.setActiveDate(context);
        } else {
            holder.setDeactiveDate(context);
        }

        if (holder.getAdapterPosition() == 0){
            holder.setActiveDate(context);
            models.get(position).setClicked(true);
        }

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            holder.cardView.setOnClickListener(v -> {
                for (int i = 0; i < models.size(); i++) {
                    DatePickerModel model = models.get(i);
                    if (i != holder.getAdapterPosition()){
                        model.setClicked(false);
                        models.set(i, model);
                    }else {
                        model.setClicked(true);
                        models.set(i, model);
                    }
                    notifyItemChanged(i);
                }
                listener.onClickListener(holder.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final MaterialCardView cardView;

        private final TextView txtDayName, txtDate;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            cardView = view.findViewById(R.id.idp_card);
            txtDayName = view.findViewById(R.id.idp_dayname);
            txtDate = view.findViewById(R.id.idp_date);
        }

        private void setActiveDate(Context context) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.booking_date_card_selected));
            cardView.setStrokeColor(ContextCompat.getColor(context, R.color.booking_date_card_selected));
            txtDayName.setTextColor(ContextCompat.getColor(context, R.color.white));
            txtDate.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

        private void setDeactiveDate(Context context) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            cardView.setStrokeColor(ContextCompat.getColor(context, R.color.booking_date_stroke));
            txtDayName.setTextColor(ContextCompat.getColor(context, R.color.black));
            txtDate.setTextColor(ContextCompat.getColor(context, R.color.azure));
        }

    }
}
