package com.c2.arenafinder.data.local;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class LogApp {

    @NonNull
    private static String createTag(@NonNull Object object, @NonNull LogTag tag){
        return String.format("--> Class.%s.%s", object.getClass().getSimpleName(), tag.name());
    }

    @NonNull
    private static String createTag(@NonNull Context context, View view, @NonNull LogTag tag){
        if(view == null){
            return String.format("--> Layout.%s.%s", context.getClass().getSimpleName(), tag.name());
        }else{
            return String.format("--> Layout.%s.%s.%s", context.getClass().getSimpleName(), context.getResources().getResourceEntryName(view.getId()), tag.name());
        }
    }

    private static void showToast(@NonNull Context context, @NonNull String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static int showLog(@NonNull Object object, @NonNull Type type, LogTag tag, String msg, Throwable tr){
        // menampilkan log berdasarkan tipe-nya
        switch (type){
            case I :
                return Log.i(createTag(object, tag), msg, tr);
            case W :
                return Log.w(createTag(object, tag), msg, tr);
            case E :
                return Log.e(createTag(object, tag), msg, tr);
            default: return -1;
        }
    }

    public static int info(@NonNull Object object, @NonNull LogTag tag, String msg, Throwable tr){
        return showLog(object, Type.I, tag, msg, tr);
    }

    public static int info(@NonNull Object object, @NonNull LogTag tag, String msg){
        return info(object, tag, msg, null);
    }

    public static int warn(@NonNull Object object, @NonNull LogTag tag, String msg, Throwable tr){
        return showLog(object, Type.W, tag, msg, tr);
    }

    public static int warn(@NonNull Object object, @NonNull LogTag tag, String msg){
        return warn(object, tag, msg, null);
    }

    public static int error(@NonNull Object object, @NonNull LogTag tag, String msg, Throwable tr){
        return showLog(object, Type.E, tag, msg, tr);
    }

    public static int error(@NonNull Object object, @NonNull LogTag tag, String msg){
        return error(object, tag, msg, null);
    }

    private static int showLog(@NonNull Context context, View view, @NonNull Type type, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        // menampilkan pesan ke toast
        if(toast){
            showToast(context, msg);
        }
        // menambahkan pesan error dari throwable
        if(tr != null){
            msg += "\n" + Log.getStackTraceString(tr);
        }

        // menampilkan log berdasarkan tipe-nya
        switch (type){
            case I :
                return Log.i(createTag(context, view, tag), msg, tr);
            case W :
                return Log.w(createTag(context, view, tag), msg, tr);
            case E :
                return Log.e(createTag(context, view, tag), msg, tr);
            default: return -1;
        }
    }

    public static int info(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return showLog(context, view, Type.I, tag, msg, tr, toast);
    }

    public static int info(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return info(context, view, tag, msg, tr, false);
    }

    public static int info(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return info(context, null, tag, msg, tr, toast);
    }

    public static int info(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return info(context, tag, msg, tr, false);
    }

    public static int info(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return info(context, view, tag, msg, null, toast);
    }

    public static int info(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg){
        return info(context, view, tag, msg, false);
    }

    public static int info(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return info(context, null, tag, msg, null, toast);
    }

    public static int info(@NonNull Context context, @NonNull View view, @NonNull String msg){
        return info(context, view, LogTag.PRINT_INFO, msg, false);
    }

    public static int info(@NonNull Context context, @NonNull String msg){
        return info(context, LogTag.PRINT_INFO, msg, false);
    }

    public static int info(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg){
        return info(context, tag, msg, false);
    }

    public static int warn(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return showLog(context, view, Type.W, tag, msg, tr, toast);
    }

    public static int warn(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return warn(context, view, tag, msg, tr, false);
    }

    public static int warn(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return warn(context, null, tag, msg, tr, toast);
    }

    public static int warn(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return warn(context, tag, msg, tr, false);
    }

    public static int warn(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return warn(context, view, tag, msg, null, toast);
    }

    public static int warn(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg){
        return warn(context, view, tag, msg, false);
    }

    public static int warn(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return warn(context, null, tag, msg, null, toast);
    }

    public static int warn(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg){
        return warn(context, tag, msg, false);
    }

    public static int error(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return showLog(context, view, Type.E, tag, msg, tr, toast);
    }

    public static int error(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return error(context, view, tag, msg, tr, false);
    }

    public static int error(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr, boolean toast){
        return error(context, null, tag, msg, tr, toast);
    }

    public static int error(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, Throwable tr){
        return error(context, tag, msg, tr, false);
    }

    public static int error(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return error(context, view, tag, msg, null, toast);
    }

    public static int error(@NonNull Context context, View view, @NonNull LogTag tag, @NonNull String msg){
        return error(context, view, tag, msg, false);
    }

    public static int error(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg, boolean toast){
        return error(context, null, tag, msg, null, toast);
    }

    public static int error(@NonNull Context context, @NonNull LogTag tag, @NonNull String msg){
        return error(context, tag, msg, false);
    }

    public enum Type{
        I,
        W,
        E,
    }
}
