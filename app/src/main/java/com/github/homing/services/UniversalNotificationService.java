package com.github.homing.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.github.homing.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

public class UniversalNotificationService extends FirebaseMessagingService {
    private int notificationCount = 1;

    @Override
    public void onNewToken(String token) {
        Log.i("FcmToken", token);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        RemoteMessage.Notification notification = message.getNotification();
        if (notification == null) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(String.valueOf(R.string.channel_id),
                            "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Log.i("FcmToken", "Message Notification Body: " + message.getNotification().getBody());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                String.valueOf(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(notificationCount, builder.build());
        if (R.integer.max_foreground_notifications > notificationCount) {
            notificationCount++;
        } else {
            notificationCount = 0;
        }
    }
}
