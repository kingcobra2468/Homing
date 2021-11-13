package com.github.homing.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.github.homing.R;
import com.github.homing.network.UcrsCallback;
import com.github.homing.network.UcrsRepository;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class TokenHeartbeatWorker extends Worker {
    private final UcrsRepository ucrsRepository;
    private final SharedPreferences preferences;

    public TokenHeartbeatWorker(@NonNull Context context,
                                @NonNull WorkerParameters params) {
        super(context, params);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ucrsRepository = UcrsRepository.getInstance()
                .setAddress(preferences.getString("ucrs_hostname", "127.0.0.1"))
                .setPort(preferences.getString("ucrs_port", "8080"))
                .setGatewayHost(preferences.getString("service_name", "ucrs"))
                .setGatewayKey(preferences.getString("gateway_key", ""))
                .build();
    }

    public Result doWork() {
        String token =
                getApplicationContext().getSharedPreferences("Homing", MODE_PRIVATE).getString(
                "token", "");
        /// no token set and thus heartbeat is not necessary
        if (token == "") return Result.success();
        ucrsRepository.heartbeat(token, new UcrsCallback() {
            @Override
            public void onSuccess() {
                sendNotification("performed heartbeat");
            }

            @Override
            public void onError(int code, String message) {
                sendNotification("Unable to perform heatbeat.");
            }
        });
        return Result.success();
    }

    private void sendNotification(String message) {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(String.valueOf(R.string.channel_id),
                            "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext().getApplicationContext(),
                        String.valueOf(R.string.channel_id))
                        .setSmallIcon(R.drawable.stethoscope)
                        .setContentTitle("Heartbeat")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(100, builder.build());
    }
}
