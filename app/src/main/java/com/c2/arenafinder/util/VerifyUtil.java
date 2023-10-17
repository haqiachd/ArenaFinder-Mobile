package com.c2.arenafinder.util;

import android.content.Context;

import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;

import java.util.HashMap;
import java.util.Objects;

public class VerifyUtil {

    private static final String NULL = "null";

    private final Context context;

    private final DataShared dataShared;

    public VerifyUtil(Context context) {
        this.context = context;
        dataShared = new DataShared(context);
    }

    public boolean haveOtp() {
        // cek semua key pada verify exist atau tidak dalam preferences
        boolean have = dataShared.contains(KEY.VERIFY_OTP_CODE) && dataShared.contains(KEY.VERIFY_START_MILLIS) &&
                dataShared.contains(KEY.VERIFY_END_MILLIS) && dataShared.contains(KEY.VERIFY_TYPE) &&
                dataShared.contains(KEY.VERIFY_DEVICE);

        // jika semua key exist
        if (have) {
            // mendapatkan semua data verify pada preferences
            HashMap<KEY, String> hash = dataShared.getData(
                    KEY.VERIFY_OTP_CODE, KEY.VERIFY_START_MILLIS, KEY.VERIFY_END_MILLIS, KEY.VERIFY_TYPE, KEY.VERIFY_DEVICE
            );

            // cek apakah data verify null atau tidak
            for (KEY key : hash.keySet()) {
                String data = hash.get(key);
                if (data == null || data.isEmpty() || data.isBlank() || data.contains(NULL)) {
                    return false;
                }
            }

            // cek apakah otp masih berlaku atau tidak
            try {
                return Long.parseLong(Objects.requireNonNull(hash.get(KEY.VERIFY_END_MILLIS)))
                        >
                       System.currentTimeMillis();
            } catch (Throwable t) {
                LogApp.error(context, LogTag.METHOD, Objects.requireNonNull(t.getMessage()));
            }

        }
        return false;
    }

    private String getData(KEY key) {
        if (dataShared.contains(key)) {
            String data = dataShared.getData(key);
            if (data != null && !data.isEmpty() && !data.isBlank()) {
                return data;
            }
            return NULL;
        }
        return NULL;
    }

    public String getOtp() {
        return getData(KEY.VERIFY_OTP_CODE);
    }

    public void setOtp(String otp) {
        dataShared.setData(KEY.VERIFY_OTP_CODE, otp);
    }

    public long getStartMillis() {
        try{
            return Long.parseLong(getData(KEY.VERIFY_START_MILLIS));
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        return 0L;
    }

    public void setStartMillis(long startMillis) {
        dataShared.setData(KEY.VERIFY_START_MILLIS, String.valueOf(startMillis));
    }

    public long getEndMillis() {
        try{
            return Long.parseLong(getData(KEY.VERIFY_END_MILLIS));
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        return 0L;
    }

    public void setEndMillis(long endMillis) {
        dataShared.setData(KEY.VERIFY_END_MILLIS, String.valueOf(endMillis));
    }

    public String getType() {
        return getData(KEY.VERIFY_TYPE);
    }

    public void setType(String type) {
        dataShared.setData(KEY.VERIFY_TYPE, type);
    }

    public String getDevice() {
        return getData(KEY.VERIFY_DEVICE);
    }

    public void setDevice(String device) {
        dataShared.setData(KEY.VERIFY_DEVICE, device);
    }

}
