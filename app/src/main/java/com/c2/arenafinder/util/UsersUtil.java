package com.c2.arenafinder.util;

import android.content.Context;

import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.model.UserModel;

public class UsersUtil {

    private final DataShared dataShared;

    public UsersUtil(Context context, UserModel model){
        dataShared = new DataShared(context);

        if (model != null){
            setId(String.valueOf(model.getIdUser()));
            setUsername(model.getUsername());
            setEmail(model.getEmail());
            setFullName(model.getNama());
            setLevel(model.getLevel());
            setVerified(model.getVerified());
            setUserPhoto(model.getUserPhoto());
            setCreated(model.getCreatedAt());
        }
    }

    public UsersUtil(Context context){
        this(context, null);
    }

    public boolean isSignIn(){
        return  dataShared.contains(KEY.ACC_USERNAME) &&
                !dataShared.getData(KEY.ACC_USERNAME).isEmpty();
    }

    public String getId(){
        return dataShared.getData(KEY.ACC_ID_USER);
    }

    public void setId(String id){
        dataShared.setData(KEY.ACC_ID_USER, id);
    }

    public String getUsername(){
        return dataShared.getData(KEY.ACC_USERNAME);
    }

    public void setUsername(String username){
        dataShared.setData(KEY.ACC_USERNAME, username);
    }

    public String getEmail(){
        return dataShared.getData(KEY.ACC_EMAIL);
    }

    public void setEmail(String email){
        dataShared.setData(KEY.ACC_EMAIL, email);
    }

    public String getFullName(){
        return dataShared.getData(KEY.ACC_FULL_NAME);
    }

    public void setFullName(String name){
        dataShared.setData(KEY.ACC_FULL_NAME, name);
    }

    public String getLevel(){
        return dataShared.getData(KEY.ACC_LEVEL);
    }

    public void setLevel(String level){
        dataShared.setData(KEY.ACC_LEVEL, level);
    }

    public String getVerified(){
        return dataShared.getData(KEY.ACC_EMAIL_VERIFY);
    }

    public void setVerified(String verified){
        dataShared.setData(KEY.ACC_EMAIL_VERIFY, verified);
    }

    public void setUserPhoto(String newUserPhoto){
        dataShared.setData(KEY.ACC_PHOTO, newUserPhoto);
    }

    public String getUserPhoto(){
        return dataShared.getData(KEY.ACC_PHOTO);
    }

    public String getCreated(){
        return dataShared.getData(KEY.ACC_CREATED);
    }

    public void setCreated(String created){
        dataShared.setData(KEY.ACC_CREATED, created);
    }

    public void signOut(){
        dataShared.setNullData(KEY.ACC_ID_USER);
        dataShared.setNullData(KEY.ACC_USERNAME);
        dataShared.setNullData(KEY.ACC_EMAIL);
        dataShared.setNullData(KEY.ACC_FULL_NAME);
        dataShared.setNullData(KEY.ACC_LEVEL);
        dataShared.setNullData(KEY.ACC_PHOTO);
        dataShared.setNullData(KEY.ACC_EMAIL_VERIFY);
        dataShared.setNullData(KEY.ACC_CREATED);
    }

}
