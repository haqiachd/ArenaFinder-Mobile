package com.c2.arenafinder.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.c2.arenafinder.util.LanguagesUtil;

import java.util.HashMap;

/**
 * Digunakan untuk menyimpan data-data dari aplikasi contohnya data user ke dalam file system device dari user
 *
 */
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

    /**
     * Digunakan untuk mengecek apakah suaty key data ada dialam file atau tidak
     *
     * @param key dari data
     * @return status key
     */
    public boolean contains(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "cek data '"+key.name()+"' ada atau tidak dari preferences");
        return this.sharedPrefs.contains(key.name());
    }

    /**
     * Digunakan untuk mendapatkan data dari file berdasarkan key
     *
     * @param key dari data
     * @return value dari data yang diambil
     */
    public String getData(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "ambil data '"+key.name()+"' dari preferences");
        return this.sharedPrefs.getString(key.name(), null);
    }

    /**
     * Digunakan untuk mendapatkan semua data yang ada didalam file
     * @param  keys dari data
     *
     * @return semua data yang ada didalam file
     */
    public HashMap<KEY, String> getData(@NonNull KEY... keys){
        HashMap<KEY, String> data = new HashMap<>();
        for (KEY key : keys){
            if (contains(key)){
                data.put(key, getData(key));
            }else {
                data.put(key, "null");
            }
        }
        return data;
    }

    /**
     * Digunakan untuk mengedit nilai dari data yang ada didalam file
     *
     * @param key key dari data yang akan diedit
     * @param value value baru dari data
     */
    public void setData(@NonNull KEY key, @NonNull String value){
        LogApp.info(this, LogTag.METHOD, "edit data '"+key.name()+"' dari preferences");
        this.sharedEditor.putString(key.name(), value).apply();
    }

    /**
     * Menghapus nilai dari data berdasarkan key
     *
     * @param key dari data yang akan dihapus
     */
    public void setNullData(@NonNull KEY key){
        this.setData(key, "");
    }

    @Deprecated
    public void remove(@NonNull KEY key){
        LogApp.info(this, LogTag.METHOD, "hapus data '"+key.name()+"' dari preferences");
        this.sharedEditor.remove(key.name()).apply();
    }

    public void resetDefaultValue(){
        KEY[] keyArray = KEY.values();
        for (KEY key : keyArray) {
            setNullData(key);
        }

        setData(KEY.APP_LANGUAGE, LanguagesUtil.INDONESIAN);
        setData(KEY.IS_FIRST_TIME_INSTALL, "no");
    }

    public enum KEY {

        APP_LAUNCH_FROM,
        SAVED_DEVICE_TOKEN,
        ACC_ID_USER,
        ACC_USERNAME,
        ACC_EMAIL,
        ACC_FULL_NAME,
        ACC_LEVEL,
        ACC_EMAIL_VERIFY,
        ACC_CREATED,
        ACC_PHOTO,
        ACC_NO_HP,
        ACC_ALAMAT,
        VERIFY_EMAIL,
        VERIFY_OTP_CODE,
        VERIFY_START_MILLIS,
        VERIFY_END_MILLIS,
        VERIFY_TYPE,
        VERIFY_RESEND,
        VERIFY_CREATED,
        VERIFY_DEVICE,
        IS_FIRST_TIME_INSTALL,
        APP_LANGUAGE,
    }

}
