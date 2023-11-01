package com.c2.arenafinder.util;

import android.util.Log;
import android.widget.Toast;

import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.NotifResponse;
import com.c2.arenafinder.ui.activity.SplashScreenActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        RetrofitClient.getInstance().notif(token)
                .enqueue(new Callback<NotifResponse>() {
                    @Override
                    public void onResponse(Call<NotifResponse> call, Response<NotifResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<NotifResponse> call, Throwable t) {

                    }
                });
    }

}

