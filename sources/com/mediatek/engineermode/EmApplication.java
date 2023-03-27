package com.mediatek.engineermode;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;

public class EmApplication extends Application {
    private static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "mtk_em_default_channel_id";
    private static final String LOW_NOTIFICATION_CHANNEL_ID = "mtk_em_low_channel_id";
    private static final String TAG = "EmApplication";
    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    public static String getSoundNotificationChannelID() {
        NotificationManager notificationManager = (NotificationManager) sContext.getSystemService("notification");
        if (notificationManager.getNotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID) == null) {
            NotificationChannel channel = new NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID, sContext.getString(R.string.app_name), 3);
            channel.setSound(RingtoneManager.getDefaultUri(2), new AudioAttributes.Builder().setUsage(5).build());
            Elog.i(TAG, "get sound " + channel.getSound());
            notificationManager.createNotificationChannel(channel);
            Elog.i(TAG, "create sound notification channel");
        }
        return DEFAULT_NOTIFICATION_CHANNEL_ID;
    }

    public static String getSilentNotificationChannelID() {
        NotificationManager notificationManager = (NotificationManager) sContext.getSystemService("notification");
        if (notificationManager.getNotificationChannel(LOW_NOTIFICATION_CHANNEL_ID) == null) {
            notificationManager.createNotificationChannel(new NotificationChannel(LOW_NOTIFICATION_CHANNEL_ID, sContext.getString(R.string.app_name), 2));
            Elog.i(TAG, "create silent notification channel");
        }
        return LOW_NOTIFICATION_CHANNEL_ID;
    }
}
