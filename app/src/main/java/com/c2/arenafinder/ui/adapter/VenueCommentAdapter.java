package com.c2.arenafinder.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.response.EmailReportResponse;
import com.c2.arenafinder.util.AdapterActionListener;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.UsersUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Digunakan untuk menampilkan list dari data Komentar Venue pada Detail Venue
 *
 */
public class VenueCommentAdapter extends RecyclerView.Adapter<VenueCommentAdapter.ViewHolder> {

    private final Activity context;

    private final ArrayList<VenueCommentModel> models;

    private final AdapterActionListener listener;

    private final UsersUtil usersUtil;

    private final String venueId, venueName;

    public VenueCommentAdapter(Activity context, ArrayList<VenueCommentModel> models, AdapterActionListener listener, String venueId, String venueName) {
        this.context = context;
        this.models = models;
        this.listener = listener;
        usersUtil = new UsersUtil(context);
        this.venueId = venueId;
        this.venueName = venueName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_venue_comment, parent, false)
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
        VenueCommentModel reviewModel = models.get(position);

        // show data
        holder.txtUsername.setText(context.getString(R.string.txt_review_username, reviewModel.getUsername()));
        holder.txtFullName.setText(reviewModel.getFullName());
        holder.txtComment.setText(reviewModel.getComment());
        holder.txtDate.setText(ArenaFinder.convertToDate(context, reviewModel.getDate()));

        holder.setPhotoProfile(context, reviewModel.getPhotoProfile());
        holder.showRatings(context, reviewModel.getRatting());

        if (reviewModel.getUsername().equalsIgnoreCase(usersUtil.getUsername())) {
            holder.imgVertical.setVisibility(View.GONE);
        }

        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {

            // aksi saat list di klik
            holder.itemView.setOnClickListener(v -> {
                listener.onClickListener(holder.getAdapterPosition());
            });

            holder.imgVertical.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.menu_review);
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.mdr_laporkan) {
                        // show bottom sheet
                        BottomSheetDialog sheet = new BottomSheetDialog(context, R.style.BottomSheetTheme);
                        View sheetInflater = context.getLayoutInflater().inflate(R.layout.sheet_report_commentar, null);
                        sheet.setContentView(sheetInflater);

                        sheetInflater.findViewById(R.id.src_val_1).setOnClickListener(val1 -> {
                            reportCommentar(reviewModel, R.string.ujaran_kebencian);
                            sheet.dismiss();
                        });

                        sheetInflater.findViewById(R.id.src_val_2).setOnClickListener(val2 -> {
                            reportCommentar(reviewModel, R.string.informasi_palsu);
                            sheet.dismiss();
                        });

                        sheetInflater.findViewById(R.id.src_val_3).setOnClickListener(val3 -> {
                            reportCommentar(reviewModel, R.string.berbau_pornografi);
                            sheet.dismiss();
                        });

                        sheetInflater.findViewById(R.id.src_val_4).setOnClickListener(val4 -> {
                            reportCommentar(reviewModel, R.string.perundungan_sosial);
                            sheet.dismiss();
                        });

                        sheetInflater.findViewById(R.id.src_val_5).setOnClickListener(val5 -> {
                            reportCommentar(reviewModel, R.string.mengandung_ancaman);
                            sheet.dismiss();
                        });

                        // show dialog
                        sheet.show();
                        sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
                        sheet.getWindow().setGravity(Gravity.BOTTOM);
                    }
                    return false;
                });
                popupMenu.show();
            });

        }
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    /**
     * Mengirimkan report ke developer
     *
     * @param model data
     * @param reason alasan report
     */
    private void reportCommentar(VenueCommentModel model, @StringRes int reason) {
        ArenaFinder.playVibrator(context, ArenaFinder.VIBRATOR_SHORT);
        new AlertDialog.Builder(context)
                .setTitle(R.string.dia_title_confirm)
                .setMessage(context.getString(R.string.report_comment, context.getString(reason)))
                .setCancelable(false)
                .setPositiveButton(R.string.dia_positive_laporkan, (dialog, which) -> {
                    dialog.dismiss();
                    sendReport(model, context.getString(reason));
                })
                .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                })
                .create().show();
    }

    private void sendReport(VenueCommentModel model, String reason) {
        var loading = new AlertDialog.Builder(context)
                .setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
                .setCancelable(false)
                .create();
        loading.show();

        RetrofitClient.getInstance().sendReportComment(
                usersUtil.getUsername(), model.getUsername(), model.getComment(), reason,
                venueId, venueName
        ).enqueue(new Callback<EmailReportResponse>() {
            @Override
            public void onResponse(Call<EmailReportResponse> call, Response<EmailReportResponse> response) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.dia_title_inform)
                        .setMessage(R.string.report_accepted)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> dialog.dismiss())
                        .create().show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<EmailReportResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    /**
     * Digunakan untuk menghubungkan antara adapter dengan layout-nya dengan menggunakan id
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final ImageView imgVertical;

        private final ImageView[] imgRattings;

        private final TextView txtFullName, txtUsername, txtDate, txtComment;

        public ViewHolder(View view) {
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

        /**
         * untuk menampilkan gambar dari list
         *
         * @param imgName dari gambar
         */
        private void setPhotoProfile(Context context, String imgName) {
            Glide.with(context)
                    .load(RetrofitClient.USER_PHOTO_URL + imgName)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(imgProfile);
        }

        private void showRatings(Context context, int ratting) {
            for (int i = 0; i < ratting; i++) {
                imgRattings[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_review_star_yellow));
            }
        }

    }
}
