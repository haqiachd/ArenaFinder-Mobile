package com.c2.arenafinder.ui.custom;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

/**
 * Custom button that displays a progress bar when the button is in progress.
 *
 * @author Achmad Baihaqi
 */
@SuppressLint("ViewConstructor")
public class ButtonAccountCustom extends View{

    private final ConstraintLayout cons;
    private final MaterialCardView card;
    private final MaterialTextView txtBtnName;
    private final ProgressBar progress;

    public static final int RUN_PROGRESS = 1;
    public static final int KILL_PROGRESS = 2;
    public static final int ENABLE = 3;
    public static final int DISABLE = 4;

    private String btnName;

    public ButtonAccountCustom(Context context, @NonNull View view , String btnName) {
        super(context);
        this.card = view.findViewById(R.id.cus_btn_acc_card);
        this.cons = view.findViewById(R.id.cus_btn_acc_cons);
        this.txtBtnName = view.findViewById(R.id.cus_btn_acc_txt);
        this.progress = view.findViewById(R.id.cus_btn_acc_progress);
        this.setButtonName(btnName);
        this.setStatus(ENABLE);
    }

    public ButtonAccountCustom(Context context, View view, @StringRes int idBtnName){
        this(context, view, context.getString(idBtnName));
    }

    public View getButton(){
        return card;
    }

    public void setButtonName(String value) {
        this.btnName = value;
        txtBtnName.setText(this.btnName);
    }

    private void elevationVisibility(boolean visibility){
        if(visibility){
            card.setElevation(getContext().getResources().getDimension(com.intuit.sdp.R.dimen._3sdp));
            card.setMaxCardElevation(getContext().getResources().getDimension(com.intuit.sdp.R.dimen._5sdp));
        }else {
            card.setCardElevation(0);
            card.setMaxCardElevation(0);
        }
    }

    public void setProgress(int value) {
        switch (value) {
            case RUN_PROGRESS:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is Running");
                progress.setVisibility(VISIBLE);
                txtBtnName.setVisibility(INVISIBLE);
                card.setClickable(false);
                this.elevationVisibility(false);
                break;
            case KILL_PROGRESS:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is killed");
                progress.setVisibility(INVISIBLE);
                txtBtnName.setVisibility(VISIBLE);
                card.setClickable(true);
                this.elevationVisibility(true);
                break;
        }
    }

    public void setStatus(int value) {
        switch (value) {
            case ENABLE:
                LogApp.info(this, LogTag.METHOD, "Status button " + btnName + " is Enable");
                cons.setEnabled(true);
                card.setEnabled(true);
                txtBtnName.setEnabled(true);
                progress.setEnabled(true);
                this.elevationVisibility(true);
                break;
            case DISABLE:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is Disable");
                cons.setEnabled(false);
                card.setEnabled(false);
                txtBtnName.setEnabled(false);
                progress.setEnabled(false);
                this.elevationVisibility(false);
                break;
        }
    }

}
