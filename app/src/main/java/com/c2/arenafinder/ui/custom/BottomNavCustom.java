package com.c2.arenafinder.ui.custom;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.c2.arenafinder.R;

/**
 * Bottom navigation custom
 *
 * @author Achmad Baihaqi
 */
public class BottomNavCustom {

    private final AppCompatActivity activity;

    public static final int ITEM_HOME = 1;
    public static final int ITEM_AKTIVITAS = 2;
    public static final int ITEM_REFERENSI = 3;
    public static final int ITEM_PROFILE = 4;

    // item home
    private final ConstraintLayout itemHomeButton;
    private final LottieAnimationView itemHomeLottie;
    private final ImageView itemHomeImage;
    private final TextView itemHomeText;

    // item aktivitas
    private final ConstraintLayout itemAktivitasButton;
    private final LottieAnimationView itemAktivitasLottie;
    private final ImageView itemAktivitasImage;
    private final TextView itemAktivitasText;

    // item referensi
    private final ConstraintLayout itemReferensiButton;
    private final LottieAnimationView itemReferensiLottie;
    private final ImageView itemReferensiImage;
    private final TextView itemReferensiText;

    // item profile
    private final ConstraintLayout itemProfileButton;
    private final LottieAnimationView itemProfileLottie;
    private final ImageView itemProfileImage;
    private final TextView itemProfileText;

    // handlers
    private final Handler handlerHome = new Handler();
    private final Handler handlerAktivitas = new Handler();
    private final Handler handlerReferensi = new Handler();
    private final Handler handlerProfile = new Handler();

    private final Runnable actionHome = () -> {
        playAnimation(ITEM_HOME);
        setActivatedItem(ITEM_HOME);
    };

    private final Runnable actionAktivitas = () -> {
        playAnimation(ITEM_AKTIVITAS);
        setActivatedItem(ITEM_AKTIVITAS);
    };

    private final Runnable actionReferensi = () -> {
        playAnimation(ITEM_REFERENSI);
        setActivatedItem(ITEM_REFERENSI);
    };

    private final Runnable actionProfile = () -> {
        playAnimation(ITEM_PROFILE);
        setActivatedItem(ITEM_PROFILE);
    };

    public BottomNavCustom(AppCompatActivity view) {
        this.activity = view;

        // initialize item home
        itemHomeButton = view.findViewById(R.id.btn_nav_home);
        itemHomeLottie = view.findViewById(R.id.btn_nav_home_anim);
        itemHomeImage = view.findViewById(R.id.btn_nav_home_icon);
        itemHomeText = view.findViewById(R.id.btn_nav_home_title);

        // initialize item aktivitas
        itemAktivitasButton = view.findViewById(R.id.btn_nav_aktivitas);
        itemAktivitasLottie = view.findViewById(R.id.btn_nav_aktivitas_anim);
        itemAktivitasImage = view.findViewById(R.id.btn_nav_aktivitas_icon);
        itemAktivitasText = view.findViewById(R.id.btn_nav_aktivitas_title);

        // initialize item referensi
        itemReferensiButton = view.findViewById(R.id.btn_nav_referensi);
        itemReferensiLottie = view.findViewById(R.id.btn_nav_referensi_anim);
        itemReferensiImage = view.findViewById(R.id.btn_nav_referensi_icon);
        itemReferensiText = view.findViewById(R.id.btn_nav_referensi_title);

        // initialize item profile
        itemProfileButton = view.findViewById(R.id.btn_nav_profile);
        itemProfileLottie = view.findViewById(R.id.btn_nav_profile_anim);
        itemProfileImage = view.findViewById(R.id.btn_nav_profile_icon);
        itemProfileText = view.findViewById(R.id.btn_nav_profile_title);
    }

    private void playAnim(LottieAnimationView lottie, ImageView image, TextView text) {
        image.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        lottie.setVisibility(View.VISIBLE);
        lottie.playAnimation();
    }

    public void playAnimation(int item) {
        switch (item) {
            case ITEM_HOME:
                playAnim(itemHomeLottie, itemHomeImage, itemHomeText);
                break;
            case ITEM_AKTIVITAS:
                playAnim(itemAktivitasLottie, itemAktivitasImage, itemAktivitasText);
                break;
            case ITEM_REFERENSI:
                playAnim(itemReferensiLottie, itemReferensiImage, itemReferensiText);
                break;
            case ITEM_PROFILE:
                playAnim(itemProfileLottie, itemProfileImage, itemProfileText);
                break;
        }
    }

    private void closeAnim(LottieAnimationView lottie, ImageView image, TextView text) {
        lottie.cancelAnimation();
        lottie.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
    }

    public void closeAnimation(int item) {
        switch (item) {
            case ITEM_HOME:
                closeAnim(itemHomeLottie, itemHomeImage, itemHomeText);
                break;
            case ITEM_AKTIVITAS:
                closeAnim(itemAktivitasLottie, itemAktivitasImage, itemAktivitasText);
                break;
            case ITEM_REFERENSI:
                closeAnim(itemReferensiLottie, itemReferensiImage, itemReferensiText);
                break;
            case ITEM_PROFILE:
                closeAnim(itemProfileLottie, itemProfileImage, itemProfileText);
                break;
        }
    }

    public void closeHome() {
        closeAnimation(ITEM_HOME);
        handlerHome.removeCallbacks(actionHome);
    }

    public void closeAktivitas() {
        closeAnimation(ITEM_AKTIVITAS);
        handlerAktivitas.removeCallbacks(actionAktivitas);
    }

    public void closeReferensi() {
        closeAnimation(ITEM_REFERENSI);
        handlerReferensi.removeCallbacks(actionReferensi);
    }

    public void closeProfile() {
        closeAnimation(ITEM_PROFILE);
        handlerAktivitas.removeCallbacks(actionProfile);
    }

    private void deactivatedItem() {
        itemHomeImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_black));
        itemAktivitasImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_black));
        itemReferensiImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_black));
        itemProfileImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_black));

        TextView[] texts = {itemHomeText, itemAktivitasText, itemReferensiText, itemProfileText};

        for (TextView textView : texts) {
            textView.setTextColor(ContextCompat.getColor(activity, R.color.granite_grey));
        }
    }

    public void setActivatedItem(int item) {
        deactivatedItem();
        switch (item) {
            case ITEM_HOME: {
                itemHomeImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_green));
                itemHomeText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_AKTIVITAS: {
                itemAktivitasImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_green));
                itemAktivitasText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_REFERENSI: {
                itemReferensiImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_green));
                itemReferensiText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_PROFILE: {
                itemProfileImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_green));
                itemProfileText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
        }
    }

    private void activatedClickable(){
        itemHomeButton.setClickable(true);
        itemAktivitasButton.setClickable(true);
        itemReferensiButton.setClickable(true);
        itemProfileButton.setClickable(true);
    }

    public void disabledClickable(int item){
        activatedClickable();
        switch (item){
            case ITEM_HOME: {
                itemHomeButton.setClickable(false);
                break;
            }
            case ITEM_AKTIVITAS: {
                itemAktivitasButton.setClickable(false);
                break;
            }
            case ITEM_REFERENSI: {
                itemReferensiButton.setClickable(false);
                break;
            }
            case ITEM_PROFILE: {
                itemProfileButton.setClickable(false);
                break;
            }
        }
    }

    public void setOnItemClickListener(OnItemListener itemListener) {

        itemHomeButton.setOnClickListener(v -> {
            closeAktivitas();
            closeReferensi();
            closeProfile();
            itemListener.itemHome();
            handlerHome.post(actionHome);
        });

        itemAktivitasButton.setOnClickListener(v -> {
            closeHome();
            closeReferensi();
            closeProfile();
            itemListener.itemAktivitas();
            handlerAktivitas.post(actionAktivitas);
        });

        itemReferensiButton.setOnClickListener(v -> {
            closeHome();
            closeAktivitas();
            closeProfile();
            itemListener.itemReferensi();
            handlerReferensi.post(actionReferensi);
        });

        itemProfileButton.setOnClickListener(v -> {
            closeHome();
            closeAktivitas();
            closeReferensi();
            itemListener.itemProfile();
            handlerProfile.post(actionProfile);
        });

    }

    public interface OnItemListener {
        void itemHome();

        void itemAktivitas();

        void itemReferensi();

        void itemProfile();
    }

}

