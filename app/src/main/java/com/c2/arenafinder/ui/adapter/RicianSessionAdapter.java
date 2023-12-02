package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.PaymentDetailModel;
import com.c2.arenafinder.util.ArenaFinder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RicianSessionAdapter extends RecyclerView.Adapter<RicianSessionAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<PaymentDetailModel.RicianSession> ricianSessions;

    public RicianSessionAdapter(Context context, ArrayList<PaymentDetailModel.RicianSession> models) {
        this.context = context;
        this.ricianSessions = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_jadwal_booking, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PaymentDetailModel.RicianSession model = ricianSessions.get(position);

        holder.txtSession.setText(model.getSession());
        holder.txtPrice.setText(context.getString(R.string.txt_price_value, ArenaFinder.toMoneyCase(Integer.parseInt(model.getPrice()))));
    }

    @Override
    public int getItemCount() {
        return ricianSessions != null ? ricianSessions.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtSession, txtPrice;

        public ViewHolder(View view) {
            super(view);

            txtSession = view.findViewById(R.id.ijb_session);
            txtPrice = view.findViewById(R.id.ijb_harga);
        }

    }
}
