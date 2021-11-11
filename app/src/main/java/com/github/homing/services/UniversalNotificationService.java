package com.github.homing.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.github.homing.R;
import com.github.homing.network.UcrsCallback;
import com.github.homing.network.UcrsRepository;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.core.app.NotificationCompat;

public class UniversalNotificationService extends FirebaseMessagingService {
    private int notificationCount = 1;
    private final UcrsRepository ucrsRepository = UcrsRepository.getInstance();

    @Override
    public void onNewToken(String token) {
        ExecutorService executor;
        HandlerThread ht;
        Handler uiHandler, tokenPushHandler;

        executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });

        ht = new HandlerThread("temperatureRefresh");
        ht.start();

        uiHandler = new Handler(Looper.getMainLooper());
        tokenPushHandler = new Handler(ht.getLooper());

        executor.execute(new PushTokenRunnable(token, uiHandler, tokenPushHandler));
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

    private class PushTokenRunnable implements Runnable {
        private final Handler uiHandler;
        private final Handler tokenPushHandler;
        private final String token;

        public PushTokenRunnable(String token, Handler uiHandler, Handler tokenPushHandler) {
            this.token = token;
            this.uiHandler = uiHandler;
            this.tokenPushHandler = tokenPushHandler;
        }

        public void run() {
            ucrsRepository.registerToken(token, new UcrsCallback() {
                @Override
                public void onSuccess() {
                    uiHandler.post(() -> {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "Device token registered.",
                                duration);
                        toast.show();
                    });
                }

                @Override
                public void onError(int code, String message) {
                    uiHandler.post(() -> {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "Device token registration " +
                                        "failed. Unable to connect to UCRS.",
                                duration);
                        toast.show();

                        tokenPushHandler.postDelayed(PushTokenRunnable.this, 10000);
                    });
                }
            });
        }
    }
}
