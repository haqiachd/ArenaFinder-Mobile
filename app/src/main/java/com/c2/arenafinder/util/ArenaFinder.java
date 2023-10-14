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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

public class ArenaFinder {

    public static final int VIBRATOR_SHORT = 150;
    public static final int VIBRATOR_MEDIUM = 500;
    public static final int VIBRATOR_LONG = 1000;
    public static final int MILLIS_OF_REFRESHING = 1500;

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

    public static void closeApplication(@NonNull AppCompatActivity app){
        app.finishAffinity();
        System.exit(0);
        LogApp.info(app, LogTag.APPLICATION, "Aplikasi ditutup");
    }

    public static void closeApplication(@NonNull FragmentActivity app){
        app.finishAffinity();
        System.exit(0);
        LogApp.info(app, LogTag.APPLICATION, "Aplikasi ditutup");
    }

    public static void VibratorToast(Context context, String msg, int toastLong, int vibratorLong){
        playVibrator(context, vibratorLong);
        Toast.makeText(context, msg, toastLong).show();
        LogApp.info(context, LogTag.ON_VIBRATOR_TOAST, msg);
    }

    public static void VibratorToast(Context context, @StringRes int msg, int toastLong, int vibratorLong){
        VibratorToast(context, context.getString(msg), toastLong, vibratorLong);
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

    public static void showLoadingDialog(){

    }


}
