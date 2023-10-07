package com.c2.arenafinder.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

@SuppressLint("ViewConstructor")
public class ButtonAccountCustom extends View {

    public Button button;
    private ConstraintLayout cons;
    private CardView card;
    private ProgressBar prog;
    private String btnName;
    private Integer progressStatus;
    private Integer buttonStatus;
    private String buttonName;

    public static final int RUN_PROGRESS = 1;
    public static final int KILL_PROGRESS = 2;
    public static final int ENABLE = 3;
    public static final int DISABLE = 4;

    public ButtonAccountCustom(Context context, View view , String btnName) {
        super(context);
        this.cons = view.findViewById(R.id.cus_btn_acc_cons);
        this.card = view.findViewById(R.id.cus_btn_acc_card);
        this.prog = view.findViewById(R.id.cus_btn_acc_prog);
        this.button = view.findViewById(R.id.cus_btn_acc_butt);
        this.btnName = btnName;
        this.buttonName = btnName;
    }

    public String getButtonName() {
        return btnName;
    }

    public void setButtonName(String value) {
        button.setText(value);
    }

    public Integer getProgress() {
        return progressStatus;
    }

    public void setProgress(Integer value) {
        progressStatus = value;
        switch (value) {
            case RUN_PROGRESS:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is Running");
                prog.setVisibility(VISIBLE);
                button.setVisibility(INVISIBLE);
                break;
            case KILL_PROGRESS:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is killed");
                prog.setVisibility(INVISIBLE);
                button.setVisibility(VISIBLE);
                break;
        }
    }

    public Integer getStatus() {
        return buttonStatus;
    }

    public void setStatus(Integer value) {
        buttonStatus = value;
        switch (value) {
            case ENABLE:
                LogApp.info(this, LogTag.METHOD, "Status button " + btnName + " is Enable");
                cons.setEnabled(true);
                card.setEnabled(true);
                button.setEnabled(true);
                prog.setEnabled(true);
                break;
            case DISABLE:
                LogApp.info(this, LogTag.METHOD, "Progress button " + btnName + " is Disable");
                cons.setEnabled(false);
                card.setEnabled(false);
                button.setEnabled(false);
                prog.setEnabled(false);
                break;
        }
    }
}
