package com.c2.arenafinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.AktivitasMemberModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Digunakan untuk menampilkan list dari data Aktivitas Member pada Detail Aktivitas
 *
 */
public class AktivitasMemberAdapter extends RecyclerView.Adapter<AktivitasMemberAdapter.ViewHolder>{

    private ArrayList<AktivitasMemberModel> models;

    public AktivitasMemberAdapter(ArrayList<AktivitasMemberModel> models){
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_activity_member, parent, false)
        );
    }

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param holder   .
     * @param position .
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get data
        AktivitasMemberModel model = models.get(position);

        // show data
        holder.txtName.setText(model.getFullName());
        holder.txtUsername.setText(model.getUsername());
        holder.setImagePhoto(model.getPhoto());
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

        private final CircleImageView imgPhoto;

        private final TextView txtName, txtUsername;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            imgPhoto = view.findViewById(R.id.iam_photo);
            txtName = view.findViewById(R.id.iam_fullname);
            txtUsername = view.findViewById(R.id.iam_username);
        }

        public void setImagePhoto(String url){
            Glide.with(view)
                    .load(RetrofitClient.USER_PHOTO_URL + url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder_venue_detailed)
                    .into(imgPhoto);
        }

    }
}
