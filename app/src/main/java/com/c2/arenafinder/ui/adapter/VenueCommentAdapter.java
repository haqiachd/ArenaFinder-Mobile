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

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;

import java.util.ArrayList;

public class VenueCommentAdapter extends RecyclerView.Adapter<VenueCommentAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<VenueCommentModel> models;

    private final AdapterActionListener listener;

    public VenueCommentAdapter(Context context, ArrayList<VenueCommentModel> models, AdapterActionListener listener){
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_comment, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VenueCommentModel reviewModel = models.get(position);

        holder.txtUsername.setText(context.getString(R.string.txt_review_username, reviewModel.getUsername()));
        holder.txtFullName.setText(reviewModel.getFullName());
        holder.txtComment.setText(reviewModel.getComment());
        holder.txtDate.setText(ArenaFinder.convertToDate(reviewModel.getDate()));

        holder.setPhotoProfile(context, reviewModel.getPhotoProfile());
        holder.showRatings(context, reviewModel.getRatting());

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION){

            holder.itemView.setOnClickListener(v -> {
                listener.onClickListener(holder.getAdapterPosition());
            });

            holder.imgVertical.setOnClickListener(v -> {

            });

        }

    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final ImageView imgVertical;

        private final ImageView[] imgRattings;

        private final TextView txtFullName, txtUsername, txtDate, txtComment;

        public ViewHolder(View view){
            super(view);

            imgProfile = view.findViewById(R.id.ivr_review_photo);
            imgVertical = view.findViewById(R.id.ivr_vertical_menu);
            // ratting images
            ImageView imgR1 = view.findViewById(R.id.ivr_ratting_1);
            ImageView imgR2 = view.findViewById(R.id.ivr_ratting_2);
            ImageView imgR3 = view.findViewById(R.id.ivr_ratting_3);
            ImageView imgR4 = view.findViewById(R.id.ivr_ratting_4);
            ImageView imgR5 = view.findViewById(R.id.ivr_ratting_5);
            imgRattings = new ImageView[]{imgR1, imgR2, imgR3, imgR4, imgR5};

            txtFullName = view.findViewById(R.id.ivr_review_fullname);
            txtUsername = view.findViewById(R.id.ivr_review_username);
            txtComment = view.findViewById(R.id.ivr_review_comment);
            txtDate = view.findViewById(R.id.ivr_ratting_date);

        }

        private void setPhotoProfile(Context context, String imgName){
            Glide.with(context)
                    .load(RetrofitClient.USER_PHOTO_URL + imgName)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgProfile);
        }

        private void showRatings(Context context, int ratting){
            for (int i = 1; i < ratting; i++){
                imgRattings[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_review_star_yellow));
            }
        }

    }
}
