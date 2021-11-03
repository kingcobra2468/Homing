package com.github.homing;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class UniversalNotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.i("TOKEN", token);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        if (message.getNotification() != null) {
            Log.i("TOKEN", "Message Notification Body: " + message.getNotification().getBody());
        }
    }
}
