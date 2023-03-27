package com.mediatek.engineermode.bluetooth;

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

public class BtWatchService extends Service {
    private static final int NOTIFICATION_ID = 2020;
    private static final String TAG = "BtWatchService";
    private final BroadcastReceiver mBtStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.SCAN_MODE_CHANGED".equals(intent.getAction())) {
                Elog.d(BtWatchService.TAG, "state " + intent.getIntExtra("android.bluetooth.adapter.extra.SCAN_MODE", 0));
                BtTestTool.setAlwaysVisible(true);
            }
        }
    };

    public static void enableService(Context context, boolean on) {
        Intent servIntent = new Intent(context, BtWatchService.class);
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
        registerReceiver(this.mBtStateReceiver, new IntentFilter("android.bluetooth.adapter.action.SCAN_MODE_CHANGED"));
        Elog.i(TAG, "registerReceiver");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(2020, buildNotification());
        return 1;
    }

    public void onDestroy() {
        unregisterReceiver(this.mBtStateReceiver);
        Elog.i(TAG, "unregisterReceiver");
        super.onDestroy();
    }

    private Notification buildNotification() {
        String title = getString(R.string.bt_title);
        String content = getString(R.string.bt_always_visible);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, EmApplication.getSilentNotificationChannelID());
        builder.setSmallIcon(17301543);
        builder.setContentTitle(title);
        builder.setContentText(content);
        Notification nty = builder.build();
        nty.flags |= 32;
        Intent intent = new Intent(this, BtTestTool.class);
        intent.setFlags(335544320);
        nty.setLatestEventInfo(this, title, content, PendingIntent.getActivity(this, 0, intent, 67108864));
        return nty;
    }
}
