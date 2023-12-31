package com.c2.arenafinder.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.VenueExtendedModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

/**
 * Digunakan untuk menampilkan list dari data list pada activity Sub Main
 *
 */
public class VenueExtendedAdapter extends RecyclerView.Adapter<VenueExtendedAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueExtendedModel> models;

    private final AdapterActionListener listener;

    private final String search;

    public VenueExtendedAdapter(Context context, ArrayList<VenueExtendedModel> models, AdapterActionListener listener, String search) {
        this.context = context;
        this.models = models;
        this.listener = listener;
        if (search != null){
            this.search = search.toLowerCase();
        }else {
            this.search = search;
        }
    }

    public VenueExtendedAdapter(Context context, ArrayList<VenueExtendedModel> models, AdapterActionListener listener) {
        this(context, models, listener, null);
    }


    @NonNull
    @Override
    public VenueExtendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VenueExtendedAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_extended, parent, false)
        );
    }

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull VenueExtendedAdapter.ViewHolder holder, int position) {

        // get data
        VenueExtendedModel model = models.get(position);

        // show data
        holder.txtName.setText(model.getVenueName());
        holder.txtStatus.setText(model.getStatus());
        holder.txtSport.setText(model.getSport());
        holder.txtRatting.setText(String.valueOf(model.getRating()));

        holder.setImage(model.getVenuePhoto());

        if (model.getRating() <= 0.0) {
            holder.txtRatting.setText(R.string.txt_ratting_na);
        } else {
            holder.txtRatting.setText(ArenaFinder.oneComa(model.getRating()));
        }

        holder.txtDescFirst.setVisibility(View.GONE);
        // change status venue color
        switch (model.getStatus().toLowerCase()) {
            case "disewakan": {
                holder.setStatusColor(context, R.drawable.bg_venue_status_disewakan, R.color.venue_status_disewakan);
//                holder.txtDescFirst.setText("1 Slot Kosong");
                holder.txtDescSecond.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHargaSewa())));
                break;
            }
            case "gratis": {
                holder.setStatusColor(context, R.drawable.bg_venue_status_gratis, R.color.venue_status_gratis);
                holder.txtDescSecond.setText(R.string.txt_gratis_v);
                holder.txtDescFirst.setText("");
                break;
            }
            case "berbayar": {
                holder.setStatusColor(context, R.drawable.bg_venue_status_berbayar, R.color.venue_status_berbayar);
                holder.txtDescSecond.setText("");
                holder.txtDescFirst.setText(context.getString(R.string.txt_disewakan_v, ArenaFinder.toMoneyCase(model.getHarga())));
                break;
            }
            case "bervariasi": {
                holder.setStatusColor(context, R.drawable.bg_venue_status_variasi, R.color.venue_status_bervariasi);
                break;
            }
        }

        // action listener / aksi saat list di klik
        if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener(v -> {
                LogApp.info(context, LogTag.ON_CLICK, "adapter clicked on -> " + holder.getAdapterPosition());
                listener.onClickListener(holder.getAdapterPosition());
            });
        }

        try {

            if (search != null && !search.isEmpty() && !search.isBlank()) {
                String text = model.getVenueName().toLowerCase();
                int index = text.indexOf(search);
                int endIndex = index + search.length();

                if (index == -1) {
                    index = 0;
                    endIndex++;
                }

                LogApp.info(this, LogTag.LIFEFCYLE, "INDEX --> " + index);
                LogApp.info(this, LogTag.LIFEFCYLE, "END INDEX --> " + endIndex);

                SpannableString spannable = new SpannableString(model.getVenueName());
                spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), index, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.txtName.setText(spannable);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogApp.error(this, LogTag.LIFEFCYLE, "ERROR INDEX --> ", e);
        }


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

        private final View view;

        private final ImageView imgLapangan;

        private final TextView txtName, txtSport, txtRatting, txtStatus, txtDescFirst, txtDescSecond;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            imgLapangan = view.findViewById(R.id.ive_venue_image);
            txtName = view.findViewById(R.id.ive_venue_name);
            txtSport = view.findViewById(R.id.ive_venue_sport);
            txtRatting = view.findViewById(R.id.ive_ratting_value);
            txtStatus = view.findViewById(R.id.ive_venue_status);
            txtDescFirst = view.findViewById(R.id.ive_venue_desc_first);
            txtDescSecond = view.findViewById(R.id.ive_venue_desc_second);

        }

        public void setStatusColor(Context context, @DrawableRes int background, @ColorRes int textColor) {
            txtStatus.setTextColor(ContextCompat.getColor(context, textColor));
            txtStatus.setBackground(ContextCompat.getDrawable(context, background));
        }

        /**
         * untuk menampilkan gambar dari list
         *
         * @param url dari gambar
         */
        private void setImage(String url) {
            Glide.with(view)
                    .load(RetrofitClient.VENUE_IMG_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgLapangan);
        }

    }

}
