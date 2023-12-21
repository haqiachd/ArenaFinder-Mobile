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

/**
 * Digunakan untuk menampilkan list dari data list Jadwal pada Booking
 *
 */
public class JadwalPickerAdapter extends RecyclerView.Adapter<JadwalPickerAdapter.ViewHolder> {

    private final Context context;

    private final String lapangan;

    private final ArrayList<JadwalPickerModel> models;

    private final AdapterActionListener listener;

    private static ArrayList<JadwalPickerModel> jadwalDipilih = new ArrayList<>();

    public static ArrayList<String> itemDetails = new ArrayList<>();

    private static int totalHarga = 0;

    public JadwalPickerAdapter(Context context, String lapangan, ArrayList<JadwalPickerModel> models, AdapterActionListener listener) {
        this.context = context;
        this.lapangan = lapangan;
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

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull JadwalPickerAdapter.ViewHolder holder, int position) {

        JadwalPickerModel pickerModel = models.get(position);

        holder.txtPrice.setText(context.getString(R.string.txt_price_value_2, pickerModel.getPrice()));
        holder.txtPrice.setVisibility(View.GONE);
        holder.txtBooked.setText(String.valueOf(pickerModel.isBooked()));
        holder.txtSession.setText(pickerModel.getSession());

        if (pickerModel.isBooked()) {
            holder.txtPrice.setVisibility(View.GONE);
            holder.txtBooked.setText(R.string.status_booking_adapter);
            holder.txtBooked.setVisibility(View.VISIBLE);
        } else {
            holder.txtPrice.setVisibility(View.VISIBLE);
            holder.txtBooked.setVisibility(View.GONE);
        }

        if (pickerModel.isSelected()) {
            holder.setSelectedItem(context);
        } else {
            holder.setUnselectedItem(context);
        }

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {

            // handler action when item jadwal clicked
            holder.itemView.setOnClickListener(v -> {
                // get selected jadwal
                JadwalPickerModel model = models.get(holder.getAdapterPosition());
                String idHashNew = createTokenId(model.getSession(), model.getPrice());
                // jika jadwal belum di booking
                if (!model.isBooked()) {
                    // membatalkan pemilihan jadwal
                    if (model.isSelected()) {
                        jadwalDipilih.remove(model);
                        itemDetails.remove(idHashNew);
                        totalHarga -= model.getPrice();
                    }
                    // memiliih jadwal
                    else {
                        jadwalDipilih.add(model);
                        itemDetails.add(idHashNew);
                        totalHarga += model.getPrice();
                    }

                    // refersh layout setelah user memilih jadwal
                    model.setSelected(!model.isSelected());
                    models.set(holder.getAdapterPosition(), model);
                    notifyItemChanged(holder.getAdapterPosition());
                    listener.onClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    /**
     * create token for item details
     * @param session jadwal yang dipilih
     * @param price harga jadwal
     * @return generte token id
     */
    private String createTokenId(String session, int price) {
        return lapangan.replaceAll(" ", ".") + "-" + session.replaceAll(" ", "") + "-" + price;
    }

    /**
     * @return mendapatkan list jadwal lapangan yang dipilih
     */
    public ArrayList<JadwalPickerModel> getJadwalDipilih() {
        return jadwalDipilih;
    }

    /**
     * @return mendapatkan list jadwal lapangan yang dipilih dalam string token
     */
    public ArrayList<String> getItemDetails() {
        return itemDetails;
    }

    public void resetTotalHarga(){
        jadwalDipilih = new ArrayList<>();
        itemDetails = new ArrayList<>();
        this.totalHarga = 0;
    }

    /**
     * @return mendapatkan total semua harga
     */
    public int getTotalHarga() {
        return totalHarga;
    }

    /**
     * Digunakan untuk menghubungkan antara adapter dengan layout-nya dengan menggunakan id
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView layout;

        private final TextView txtSession, txtPrice, txtBooked;

        private final ImageView imgSelect;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.ijp_layout);
            txtPrice = view.findViewById(R.id.ijp_price);
            txtSession = view.findViewById(R.id.ijp_session);
            imgSelect = view.findViewById(R.id.ijp_icon_select);
            txtBooked = view.findViewById(R.id.ijp_booked);
        }

        /**
         * menganti warna saat user memilih sebuah jadwal yang dipilih menjadi warna biru
         *
         * @param context untuk menganti warna
         */
        private void setSelectedItem(Context context) {
            layout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.booking_card_background_selected));
            layout.setStrokeColor(ContextCompat.getColor(context, R.color.azure));
            txtPrice.setTextColor(ContextCompat.getColor(context, R.color.azure));
            txtSession.setTextColor(ContextCompat.getColor(context, R.color.azure));
            imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_booking_checked));
        }

        /**
         * menganti warna saat user membatalkan memilih sebuah jadwal yang dipilih menjadi warna primary color
         *
         * @param context untuk menganti warna
         */
        private void setUnselectedItem(Context context) {
            layout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background_light));
            layout.setStrokeColor(ContextCompat.getColor(context, R.color.booking_card_stroke));
            txtPrice.setTextColor(ContextCompat.getColor(context, R.color.booking_textsize_color));
            txtSession.setTextColor(ContextCompat.getColor(context, R.color.booking_textsize_color));
            imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_booking_unchecked));
        }

    }
}
