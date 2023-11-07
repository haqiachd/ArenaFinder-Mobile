package com.c2.arenafinder.util;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
       LogApp.info(this, LogTag.ON_NOTIFICATION, "Refreshed Token : " + token);
    }

    @Override
    public void onDeletedMessages() {
        LogApp.error(this, LogTag.ON_NOTIFICATION, "ON DELETE");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getData().size() > 0 && message.getNotification() != null){
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Notif Type : " + message.getNotification().getTitle());
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Notif Body : " + message.getNotification().getBody());
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Notif Time : " + message.getSentTime());
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Notif From : " + message.getFrom());
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Notif Click : " + message.getFrom());

            LogApp.info(this, LogTag.ON_NOTIFICATION, "Key 1 : " + message.getData().get("key_1"));
            LogApp.info(this, LogTag.ON_NOTIFICATION, "Key 2 : " + message.getData().get("key_2"));


            var clickAction = message.getNotification().getClickAction();

            if (clickAction != null){
//                startActivity(
//                        new Intent(Intent.ACTION_VIEW, Uri.parse(clickAction))
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                );
            }

        }

    }


}
