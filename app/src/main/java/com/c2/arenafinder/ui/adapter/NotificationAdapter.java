package com.c2.arenafinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private ArrayList<NotificationModel> models;

    public NotificationAdapter(ArrayList<NotificationModel> models){
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

        holder.txtTitle.setText(model.getTitle());
        holder.txtBody.setText(model.getBody());
        holder.txtDate.setText(model.getDate());

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitle, txtBody, txtDate;

        public ViewHolder(View view){
            super(view);

            txtTitle = view.findViewById(R.id.ino_title);
            txtBody = view.findViewById(R.id.ino_body);
            txtDate = view.findViewById(R.id.ino_date);
        }

    }
}
