package com.c2.arenafinder.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

public class ArenaFinder {

    public static int VIBRATOR_SHORT = 150;
    public static int VIBRATOR_MEDIUM = 500;
    public static int VIBRATOR_LONG = 1000;

    public static void playVibrator(@NonNull Context context, int millis){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // API > 26
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            // API 26 kebawah
            vibrator.vibrate(millis);
        }
    }

    public static boolean isInternetConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static void restartApplication(@NonNull Context context, Class<?> activity){
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        LogApp.info(context, LogTag.APPLICATION, "Aplikasi direstart");
    }


    public static void showAlertDialog(
            Context context, String title, String message, boolean cancelable,
            DialogInterface.OnClickListener positiveAction, DialogInterface.OnClickListener negativeAction
    ){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(context.getString(R.string.dia_positive_ok), positiveAction)
                .setNegativeButton(context.getString(R.string.dia_negative_cancel), negativeAction)
                .create()
                .show();

    }


}
