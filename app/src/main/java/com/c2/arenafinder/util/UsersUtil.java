package com.c2.arenafinder.util;

import android.content.Context;

import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;

public class UsersUtil {

    private final DataShared dataShared;

    public UsersUtil(Context context){
        dataShared = new DataShared(context);
    }

    public boolean isSignIn(){
        return  dataShared.contains(KEY.ACC_USERNAME) &&
                !dataShared.getData(KEY.ACC_USERNAME).isEmpty();
    }

    public String getUsername(){
        return dataShared.getData(KEY.ACC_USERNAME);
    }

    public String getEmail(){
        return dataShared.getData(KEY.ACC_EMAIL);
    }

    public String getFullName(){
        return dataShared.getData(KEY.ACC_FULL_NAME);
    }

    public String getLevel(){
        return dataShared.getData(KEY.ACC_LEVEL);
    }

    public void setUserPhoto(String newUserPhoto){
        dataShared.setData(KEY.ACC_PHOTO, newUserPhoto);
    }

    public String getUserPhoto(){
        return dataShared.getData(KEY.ACC_PHOTO);
    }

    public void signOut(){
        dataShared.setNullData(KEY.ACC_USERNAME);
        dataShared.setNullData(KEY.ACC_EMAIL);
        dataShared.setNullData(KEY.ACC_FULL_NAME);
        dataShared.setNullData(KEY.ACC_LEVEL);
        dataShared.setNullData(KEY.ACC_PHOTO);
    }

}
