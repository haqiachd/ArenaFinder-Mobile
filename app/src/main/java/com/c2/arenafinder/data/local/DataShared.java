package com.c2.arenafinder.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class DataShared {

    private final SharedPreferences sharedPrefs;

    private final SharedPreferences.Editor sharedEditor;

    public static final String NAME = "com.c2.arenafinder.PREFERENCES";

    public DataShared(Context context){
        LogApp.info(this, LogTag.CONSTRUCTOR, "membuat object DataShared");

        // membuat object sharedpreferences
        this.sharedPrefs = context.getSharedPreferences(DataShared.NAME, Context.MODE_PRIVATE);
        this.sharedEditor = this.sharedPrefs.edit();
    }

    public boolean contains(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "cek data '"+key.name()+"' ada atau tidak dari preferences");
        return this.sharedPrefs.contains(key.name());
    }

    public String getData(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "ambil data '"+key.name()+"' dari preferences");
        return this.sharedPrefs.getString(key.name(), null);
    }

    public void setData(@NonNull KEY key, @NonNull String value){
        LogApp.info(this, LogTag.METHOD, "edit data '"+key.name()+"' dari preferences");
        this.sharedEditor.putString(key.name(), value).apply();
    }

    public void setNullData(@NonNull KEY key){
        this.setData(key, "");
    }

    @Deprecated
    public void remove(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "hapus data '"+key.name()+"' dari preferences");
        this.sharedEditor.remove(key.name()).apply();
    }

    public enum KEY {

        APP_LAUNCH_FROM,
        ACC_ID_USER,
        ACC_USERNAME,
        ACC_PASSWORD,
        ACC_EMAIL,
        ACC_FULL_NAME,
        ACC_LEVEL,
        ACC_GOOGLE_ID,
        ACC_EMAIL_VERIFY,
        ACC_CREATED,
        ACC_UPDATED,
        ACC_PHOTO,
        ACC_OTP_EMAIL,
        ACC_OTP_CODE,
        ACC_OTP_MILLIS_EXPIRED,
        ACC_OTP_RESENDING_MILLIS,
        ACC_OTP_RESENDING_COUNT,
        IS_FIRST_TIME_INSTALL,
        APP_LANGUAGE,
    }

}
