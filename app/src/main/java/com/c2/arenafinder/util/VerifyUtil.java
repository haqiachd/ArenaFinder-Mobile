package com.c2.arenafinder.util;

import android.content.Context;

import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.VerifyModel;

import java.util.HashMap;
import java.util.Objects;

public class VerifyUtil {

    public static final String TYPE_SIGNUP = "signup";

    public static final String TYPE_CHANGE = "forgot";

    public static int _15_MINUTES = 900_000;

    private static final String NULL = "null";

    private static final int minutes = 60;;

    private final Context context;

    private final DataShared dataShared;

    public VerifyUtil(Context context, VerifyModel model){
        this.context = context;
        dataShared = new DataShared(context);

        if (model != null){
            setOtp(model.getOtp());
            setStartMillis(model.getStartMillis());
            setEndMillis(model.getEndMillis());
            setType(model.getType());
            setDevice(model.getDevice());
            setResend(model.getResend());
            setResendMillis(model.getResendMillis());
        }
    }

    public VerifyUtil(Context context) {
        this(context, null);
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

    public void setResend(int type) {
        dataShared.setData(KEY.VERIFY_RESEND, String.valueOf(type));
    }

    public int getResend() {
        try{
            return Integer.parseInt(getData(KEY.VERIFY_RESEND));
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        return 1;
    }

    public long getResendMillis() {
        try{
            return Long.parseLong(getData(KEY.VERIFY_RESEND_MILLIS));
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        return 1;
    }

    public void setResendMillis(long resendMillis) {
        dataShared.setData(KEY.VERIFY_RESEND_MILLIS, String.valueOf(resendMillis));
    }

    public long countResendMillis(){
        return System.currentTimeMillis() + (60_000L * (getWaitingMinutes() / 60));
    }

    public int getResendSeconds(){
        return (int)((getResendMillis() - System.currentTimeMillis()) / 1000);
    }


    public boolean haveOtp() {
        // cek semua key pada verify exist atau tidak dalam preferences
        boolean have = dataShared.contains(KEY.VERIFY_OTP_CODE) && dataShared.contains(KEY.VERIFY_START_MILLIS) &&
                dataShared.contains(KEY.VERIFY_END_MILLIS) && dataShared.contains(KEY.VERIFY_TYPE) &&
                dataShared.contains(KEY.VERIFY_DEVICE) && dataShared.contains(KEY.VERIFY_RESEND
        );

        // jika semua key exist
        if (have) {
            // mendapatkan semua data verify pada preferences
            HashMap<KEY, String> hash = dataShared.getData(
                    KEY.VERIFY_OTP_CODE, KEY.VERIFY_START_MILLIS, KEY.VERIFY_END_MILLIS,
                    KEY.VERIFY_TYPE, KEY.VERIFY_DEVICE, KEY.VERIFY_RESEND
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

    // TODO : 2 -> 3 -> 5 -> 7 -> 9 -> 11
    public int getWaitingMinutes(){
        int resend = getResend();
        if (resend <= 0){
            return minutes;
        }

        switch (resend){
            case 1 : {
                return minutes * 2;
            }
            case 2 : {
                return minutes  * 3;
            }
            case 3 : {
                return minutes * 5;
            }
            case 4 : {
                return minutes * 7;
            }
            case 5 : {
                return minutes * 9;
            }
            default: {
                return minutes * resend * 2;
            }

        }
    }

}
