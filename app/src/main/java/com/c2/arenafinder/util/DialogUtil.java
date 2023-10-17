package com.c2.arenafinder.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

    public static AlertDialog showInformationDialog(
            Context context,
            String titleMsg, String msg,
            String btnPositive, DialogInterface.OnClickListener positiveListener,
            String btnNegative, DialogInterface.OnClickListener negativeListener
    ){
        return new AlertDialog.Builder(context)
                .setTitle(titleMsg)
                .setMessage(msg)
                .setPositiveButton(btnPositive, positiveListener)
                .setNegativeButton(btnNegative, negativeListener)
                .create();
    }

    public static AlertDialog showInformationDialog(
            Context context,
            @StringRes int tittleMsg, @StringRes int msg,
            @StringRes int btnPositive, DialogInterface.OnClickListener positiveListener,
            @StringRes int btnNegative, DialogInterface.OnClickListener negativeListener
    ){
        return showInformationDialog(
                context, context.getString(tittleMsg), context.getString(msg),
                context.getString(btnPositive), positiveListener,
                context.getString(btnNegative), negativeListener
        );
    }

    public static void showConfirmDialog(){
        // TODO : show alert dialog with one option button
    }

    public static void showLoadingDialog(){
        // TODO : show loading dialog
    }
}
