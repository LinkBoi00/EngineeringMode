package com.mediatek.engineermode.bluetooth;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.mediatek.engineermode.Elog;

public class BleScanService extends Service {
    private static final String TAG = "BleScanService";
    private final BroadcastReceiver mBtStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                Elog.d(BleScanService.TAG, "state " + state);
                if (state == 0) {
                    BtTestTool.setAlwaysScanning(-1);
                }
            }
        }
    };

    public static void enableService(Context context, boolean on) {
        Intent servIntent = new Intent(context, BleScanService.class);
        Elog.i(TAG, "enableService:" + on);
        if (on) {
            context.startService(servIntent);
        } else {
            context.stopService(servIntent);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(this.mBtStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        Elog.i(TAG, "registerReceiver");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    public void onDestroy() {
        unregisterReceiver(this.mBtStateReceiver);
        Elog.i(TAG, "unregisterReceiver");
        super.onDestroy();
    }
}
