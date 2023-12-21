package com.c2.arenafinder.ui.custom;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.c2.arenafinder.R;
import com.c2.arenafinder.ui.fragment.main.AktivitasFragment;
import com.c2.arenafinder.ui.fragment.main.HomeFragment;
import com.c2.arenafinder.ui.fragment.main.ProfileFragment;
import com.c2.arenafinder.ui.fragment.main.ReferensiFragment;
import com.c2.arenafinder.util.FragmentUtil;

/**
 * Method-method umum yang digunakan untuk manippulasi bottom nav
 *
 * @author Achmad Baihaqi
 */
public class BottomNavCustom {

    // parent activity
    private AppCompatActivity activity;

    // second icon visibility
    private boolean isHomeSecond = false;
    private boolean isAktivitasSecond = false;
    private boolean isReferensiSecond = false;
    private boolean isProfileSecond = false;

    // button action status
    private boolean isHomeOnFrame = false;
    private boolean isAktivitasOnFrame = false;
    private boolean isReferensiOnFrame = false;
    private boolean isProfileOnFrame = false;

    // item code
    public static final int ITEM_HOME = 1;
    public static final int ITEM_AKTIVITAS = 2;
    public static final int ITEM_REFERENSI = 3;
    public static final int ITEM_PROFILE = 4;

    // item home
    private final ConstraintLayout itemHomeButton;
    private final LottieAnimationView itemHomeLottie;
    private final ImageView itemHomeImage;
    private final TextView itemHomeText;
    private final ImageView itemHomeImageSecond;

    // item aktivitas
    private final ConstraintLayout itemAktivitasButton;
    private final LottieAnimationView itemAktivitasLottie;
    private final ImageView itemAktivitasImage;
    private final TextView itemAktivitasText;
    private final ImageView itemAktivitasImageSecond;

    // item referensi
    private final ConstraintLayout itemReferensiButton;
    private final LottieAnimationView itemReferensiLottie;
    private final ImageView itemReferensiImage;
    private final TextView itemReferensiText;
    private final ImageView itemReferensiImageSecond;

    // item profile
    private final ConstraintLayout itemProfileButton;
    private final LottieAnimationView itemProfileLottie;
    private final ImageView itemProfileImage;
    private final TextView itemProfileText;
    private final ImageView itemProfileImageSecond;

    private TextView txtSearchTitle;

    // handlers
    private final Handler handlerHome = new Handler();
    private final Handler handlerAktivitas = new Handler();
    private final Handler handlerReferensi = new Handler();
    private final Handler handlerProfile = new Handler();

    // runnable for the action when on frame
    private Runnable actionHomeOnFrame = null;
    private Runnable actionAktivitasOnFrame = null;
    private Runnable actionReferensiOnFrame = null;
    private Runnable actionProfileOnFrame = null;

    private final Runnable actionHome = () -> {
        hideAllSecondIcon();
        playAnimation(ITEM_HOME);
        setActivatedItem(ITEM_HOME);
        setDeactivatedOnFrame(ITEM_HOME);
        FragmentUtil.switchFragmentMain(activity.getSupportFragmentManager(), new HomeFragment(), false);
        txtSearchTitle.setText(R.string.app_appbar_home);
    };

    private final Runnable actionAktivitas = () -> {
        hideAllSecondIcon();
        playAnimation(ITEM_AKTIVITAS);
        setActivatedItem(ITEM_AKTIVITAS);
        setDeactivatedOnFrame(ITEM_AKTIVITAS);
        FragmentUtil.switchFragmentMain(activity.getSupportFragmentManager(), new AktivitasFragment(), false);
        txtSearchTitle.setText(R.string.app_appbar_aktivitas);
    };

    private final Runnable actionReferensi = () -> {
        hideAllSecondIcon();
        playAnimation(ITEM_REFERENSI);
        setActivatedItem(ITEM_REFERENSI);
        setDeactivatedOnFrame(ITEM_REFERENSI);
        FragmentUtil.switchFragmentMain(activity.getSupportFragmentManager(), new ReferensiFragment(), false);
        txtSearchTitle.setText(R.string.app_appbar_referensi);
    };

    private final Runnable actionProfile = () -> {
        hideAllSecondIcon();
        playAnimation(ITEM_PROFILE);
        setActivatedItem(ITEM_PROFILE);
        setDeactivatedOnFrame(ITEM_PROFILE);
        FragmentUtil.switchFragmentMain(activity.getSupportFragmentManager(), new ProfileFragment(), false);
        txtSearchTitle.setText(R.string.app_appbar_referensi);
    };

    public BottomNavCustom(AppCompatActivity view) {
        this.activity = view;

        // initialize item home
        itemHomeButton = view.findViewById(R.id.btn_nav_home);
        itemHomeLottie = view.findViewById(R.id.btn_nav_home_anim);
        itemHomeImage = view.findViewById(R.id.btn_nav_home_icon);
        itemHomeText = view.findViewById(R.id.btn_nav_home_title);
        itemHomeImageSecond = view.findViewById(R.id.btn_nav_home_icon_second);

        // initialize item aktivitas
        itemAktivitasButton = view.findViewById(R.id.btn_nav_aktivitas);
        itemAktivitasLottie = view.findViewById(R.id.btn_nav_aktivitas_anim);
        itemAktivitasImage = view.findViewById(R.id.btn_nav_aktivitas_icon);
        itemAktivitasText = view.findViewById(R.id.btn_nav_aktivitas_title);
        itemAktivitasImageSecond = view.findViewById(R.id.btn_nav_aktivitas_icon_second);

        // initialize item referensi
        itemReferensiButton = view.findViewById(R.id.btn_nav_referensi);
        itemReferensiLottie = view.findViewById(R.id.btn_nav_referensi_anim);
        itemReferensiImage = view.findViewById(R.id.btn_nav_referensi_icon);
        itemReferensiText = view.findViewById(R.id.btn_nav_referensi_title);
        itemReferensiImageSecond = view.findViewById(R.id.btn_nav_referensi_icon_second);

        // initialize item profile
        itemProfileButton = view.findViewById(R.id.btn_nav_profile);
        itemProfileLottie = view.findViewById(R.id.btn_nav_profile_anim);
        itemProfileImage = view.findViewById(R.id.btn_nav_profile_icon);
        itemProfileText = view.findViewById(R.id.btn_nav_profile_title);
        itemProfileImageSecond = view.findViewById(R.id.btn_nav_profile_icon_second);

        txtSearchTitle = view.findViewById(R.id.main_appbar_search_txt);
    }

    private void playAnim(LottieAnimationView lottie, ImageView image, TextView text, ImageView imageSecond) {
//        image.setVisibility(View.INVISIBLE);
//        imageSecond.setVisibility(View.INVISIBLE);
//        text.setVisibility(View.INVISIBLE);
//        lottie.setVisibility(View.VISIBLE);
//        lottie.playAnimation();
    }

    public void playAnimation(int item) {
        switch (item) {
            case ITEM_HOME:
                playAnim(itemHomeLottie, itemHomeImage, itemHomeText, itemHomeImageSecond);
                break;
            case ITEM_AKTIVITAS:
                playAnim(itemAktivitasLottie, itemAktivitasImage, itemAktivitasText, itemAktivitasImageSecond);
                break;
            case ITEM_REFERENSI:
                playAnim(itemReferensiLottie, itemReferensiImage, itemReferensiText, itemReferensiImageSecond);
                break;
            case ITEM_PROFILE:
                playAnim(itemProfileLottie, itemProfileImage, itemProfileText, itemProfileImageSecond);
                break;
        }
    }

    private void closeAnim(LottieAnimationView lottie, ImageView image, TextView text, ImageView imageSec, boolean secondVisibility) {
        lottie.cancelAnimation();
        lottie.setVisibility(View.INVISIBLE);
        if (secondVisibility) {
            imageSec.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
        } else {
            imageSec.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }

    }

    public void closeAnimation(int item) {
        switch (item) {
            case ITEM_HOME:
                closeAnim(itemHomeLottie, itemHomeImage, itemHomeText, itemHomeImageSecond, isHomeSecond);
                handlerHome.removeCallbacks(actionHome);
                break;
            case ITEM_AKTIVITAS:
                closeAnim(itemAktivitasLottie, itemAktivitasImage, itemAktivitasText, itemAktivitasImageSecond, isAktivitasSecond);
                handlerAktivitas.removeCallbacks(actionAktivitas);
                break;
            case ITEM_REFERENSI:
                closeAnim(itemReferensiLottie, itemReferensiImage, itemReferensiText, itemReferensiImageSecond, isReferensiSecond);
                handlerReferensi.removeCallbacks(actionReferensi);
                break;
            case ITEM_PROFILE:
                closeAnim(itemProfileLottie, itemProfileImage, itemProfileText, itemProfileImageSecond, isProfileSecond);
                handlerAktivitas.removeCallbacks(actionProfile);
                break;
        }
    }

    private void closeAllAnimation(int elseItem) {
        int[] items = {ITEM_HOME, ITEM_AKTIVITAS, ITEM_REFERENSI, ITEM_PROFILE};

        for (int item : items) {
            if (item != elseItem) {
                closeAnimation(item);
            }
        }
    }

    private void showSecondIcon(ImageView imageView, @DrawableRes int drawable) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(ContextCompat.getDrawable(activity, drawable));
    }

    public void showSecondIcon(int item, @DrawableRes int drawable) {
        switch (item) {
            case ITEM_HOME: {
                isHomeSecond = true;
                showSecondIcon(itemHomeImageSecond, drawable);
                itemHomeImage.setVisibility(View.INVISIBLE);
                itemHomeText.setVisibility(View.INVISIBLE);
                break;
            }
            case ITEM_AKTIVITAS: {
                isAktivitasSecond = true;
                showSecondIcon(itemAktivitasImageSecond, drawable);
                itemAktivitasImage.setVisibility(View.INVISIBLE);
                itemAktivitasText.setVisibility(View.INVISIBLE);
                break;
            }
            case ITEM_REFERENSI: {
                isReferensiSecond = true;
                showSecondIcon(itemReferensiImageSecond, drawable);
                itemReferensiImage.setVisibility(View.INVISIBLE);
                itemReferensiText.setVisibility(View.INVISIBLE);
                break;
            }
            case ITEM_PROFILE: {
                isProfileSecond = true;
                showSecondIcon(itemProfileImageSecond, drawable);
                itemProfileImage.setVisibility(View.INVISIBLE);
                itemProfileText.setVisibility(View.INVISIBLE);
                break;
            }

        }
    }

    private void hideSecondIcon(ImageView imageView) {
        imageView.setVisibility(View.INVISIBLE);
    }

    public void hideSecondIcon(int item) {
        switch (item) {
            case ITEM_HOME: {
                isHomeSecond = false;
                hideSecondIcon(itemHomeImageSecond);
                itemHomeImage.setVisibility(View.VISIBLE);
                itemHomeText.setVisibility(View.VISIBLE);
                break;
            }
            case ITEM_AKTIVITAS: {
                isAktivitasSecond = false;
                hideSecondIcon(itemAktivitasImageSecond);
                itemAktivitasImage.setVisibility(View.VISIBLE);
                itemAktivitasText.setVisibility(View.VISIBLE);
                break;
            }
            case ITEM_REFERENSI: {
                isReferensiSecond = false;
                hideSecondIcon(itemReferensiImageSecond);
                itemReferensiImage.setVisibility(View.VISIBLE);
                itemReferensiText.setVisibility(View.VISIBLE);
                break;
            }
            case ITEM_PROFILE: {
                isProfileSecond = false;
                hideSecondIcon(itemProfileImageSecond);
                itemProfileImage.setVisibility(View.VISIBLE);
                itemProfileText.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void hideAllSecondIcon() {
        int[] images = {ITEM_HOME, ITEM_AKTIVITAS, ITEM_REFERENSI, ITEM_PROFILE};

        for (int image : images) {
            hideSecondIcon(image);
        }
    }

    private void deactivatedItem() {
        itemHomeImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_deactivated));
        itemAktivitasImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_aktivitas_deactivated));
        itemReferensiImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_referensi_deactivated));
        itemProfileImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_profile_deactivated));

        TextView[] texts = {itemHomeText, itemAktivitasText, itemReferensiText, itemProfileText};

        for (TextView textView : texts) {
            textView.setTextColor(ContextCompat.getColor(activity, R.color.granite_grey));
        }
    }

    public void setActivatedItem(int item) {
        deactivatedItem();
        switch (item) {
            case ITEM_HOME: {
                itemHomeImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_home_active));
                itemHomeText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_AKTIVITAS: {
                itemAktivitasImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_aktivitas_active));
                itemAktivitasText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_REFERENSI: {
                itemReferensiImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_referensi_active));
                itemReferensiText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
            case ITEM_PROFILE: {
                itemProfileImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_navigation_profile_active));
                itemProfileText.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            }
        }
    }

    private void activatedOnFrame() {
        isHomeOnFrame = true;
        isAktivitasOnFrame = true;
        isReferensiOnFrame = true;
        isProfileOnFrame = true;
    }

    public void setDeactivatedOnFrame(int item) {
        activatedOnFrame();
        switch (item) {
            case ITEM_HOME: {
                isHomeOnFrame = false;
                break;
            }
            case ITEM_AKTIVITAS: {
                isAktivitasOnFrame = false;
                break;
            }
            case ITEM_REFERENSI: {
                isReferensiOnFrame = false;
                break;
            }
            case ITEM_PROFILE: {
                isProfileOnFrame = false;
                break;
            }
        }
    }

    public void setOnItemClickListener(OnItemListener itemListener) {

        // action when item home clicked
        itemHomeButton.setOnClickListener(v -> {
            if (isHomeOnFrame){
                closeAllAnimation(ITEM_HOME);
                itemListener.itemHome();
                handlerHome.post(actionHome);
            }else {
                if (actionHomeOnFrame != null){
                    actionHomeOnFrame.run();
                }
            }
        });

        // action when item aktivitas clicked
        itemAktivitasButton.setOnClickListener(v -> {
            if (isAktivitasOnFrame){
                closeAllAnimation(ITEM_AKTIVITAS);
                itemListener.itemAktivitas();
                handlerAktivitas.post(actionAktivitas);
            }else {
                if (actionAktivitasOnFrame != null){
                    actionAktivitasOnFrame.run();
                }
            }
        });

        // action when item referensi clicked
        itemReferensiButton.setOnClickListener(v -> {
            if (isReferensiOnFrame){
                closeAllAnimation(ITEM_REFERENSI);
                itemListener.itemReferensi();
                handlerReferensi.post(actionReferensi);
            }else {
                if (actionReferensiOnFrame != null){
                    actionReferensiOnFrame.run();
                }
            }
        });

        // action when item profile clicked
        itemProfileButton.setOnClickListener(v -> {
            if (isProfileOnFrame){
                closeAllAnimation(ITEM_PROFILE);
                itemListener.itemProfile();
                handlerProfile.post(actionProfile);
            }else {
                if (actionProfileOnFrame != null){
                    actionProfileOnFrame.run();
                }
            }
        });

    }

    public void setOnActionHomeOnFrame(Runnable runnable){
        this.actionHomeOnFrame = runnable;
    }

    public void setOnActionAktivitasOnFrame(Runnable runnable){
        this.actionAktivitasOnFrame = runnable;
    }

    public void setOnActionReferensiOnFrame(Runnable runnable){
        this.actionReferensiOnFrame = runnable;
    }

    public void setOnActionProfileOnFrame(Runnable runnable){
        this.actionProfileOnFrame = runnable;
    }

    public interface OnItemListener {
        void itemHome();

        void itemAktivitas();

        void itemReferensi();

        void itemProfile();
    }

}
