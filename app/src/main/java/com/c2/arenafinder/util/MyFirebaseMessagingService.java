package com.c2.arenafinder.util;

import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.NotifResponse;
import com.google.firebase.messaging.FirebaseMessagingService;

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

