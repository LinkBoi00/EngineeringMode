package com.mediatek.engineermode.wifi;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.R;

public class WifiWatchService extends Service {
    private static final int NOTIFICATION_ID = 2070;
    private static final String TAG = "WifiWatchService";
    private final BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                int state = intent.getIntExtra("wifi_state", 0);
                Elog.i(WifiWatchService.TAG, "state" + state);
                switch (state) {
                    case 1:
                        WifiWatchService.this.onWifiDisabled(context);
                        return;
                    case 3:
                        WifiWatchService.this.onWifiEnbled(context);
                        return;
                    default:
                        return;
                }
            }
        }
    };

    public static void enableService(Context context, boolean on) {
        Intent servIntent = new Intent(context, WifiWatchService.class);
        Elog.i(TAG, "enableService:" + on);
        if (on) {
            context.startForegroundService(servIntent);
        } else {
            context.stopService(servIntent);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(this.mWifiStateReceiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        Elog.i(TAG, "registerReceiver");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(2070, buildNotification());
        return 1;
    }

    public void onDestroy() {
        unregisterReceiver(this.mWifiStateReceiver);
        Elog.i(TAG, "unregisterReceiver");
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void onWifiEnbled(Context context) {
        if (MtkCTIATestDialog.isWifiCtiaEnabled(context)) {
            MtkCTIATestDialog.initWifiCtiaOnEnabled(context);
            MtkCTIATestDialog.notifyCtiaEnabled(context);
        }
    }

    /* access modifiers changed from: private */
    public void onWifiDisabled(Context context) {
        MtkCTIATestDialog.dismissCtiaEnabledNotify(context);
    }

    private Notification buildNotification() {
        String title = getString(R.string.wifi);
        String content = getString(R.string.wifi_state_watch);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, EmApplication.getSilentNotificationChannelID());
        builder.setSmallIcon(17301543);
        builder.setContentTitle(title);
        builder.setContentText(content);
        Notification nty = builder.build();
        nty.flags |= 32;
        Intent intent = new Intent(this, WifiTestSetting.class);
        intent.setFlags(335544320);
        nty.setLatestEventInfo(this, title, content, PendingIntent.getActivity(this, 0, intent, 67108864));
        return nty;
    }
}
