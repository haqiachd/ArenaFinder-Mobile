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
import com.c2.arenafinder.data.model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private final Context context;

    private final ArrayList<NotificationModel> models;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_notification, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationModel model = models.get(position);

        holder.txtDate.setText(model.getTanggal());

        switch (model.getStatus().toLowerCase()){
            case "accepted" : {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_notif_acc));
                holder.txtTitle.setText(context.getString(R.string.notif_diterima));
                holder.txtBody.setText(context.getString(R.string.notif_body_diterima, model.getTanggalKonfirmasi()));
                break;
            }
            case "rejected" : {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_notif_rej));
                holder.txtTitle.setText(R.string.notif_ditolak);
                holder.txtBody.setText(context.getString(R.string.notif_body_ditolak, model.getTanggalKonfirmasi()));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtTitle, txtBody, txtDate;

        public final ImageView imageView;

        public ViewHolder(View view){
            super(view);

            txtTitle = view.findViewById(R.id.ino_title);
            txtBody = view.findViewById(R.id.ino_body);
            txtDate = view.findViewById(R.id.ino_date);
            imageView = view.findViewById(R.id.ino_icon);
        }

    }
}
