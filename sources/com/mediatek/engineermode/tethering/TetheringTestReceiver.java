package com.mediatek.engineermode.tethering;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mediatek.engineermode.Elog;

public class TetheringTestReceiver extends BroadcastReceiver {
    private static final String ACTION_ENABLE_NSIOT = "com.mediatek.intent.action.ACTION_ENABLE_NSIOT_TESTING";
    private static final String TAG = "TetheringTestReceiver";

    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Elog.e(TAG, "Intent is null");
            return;
        }
        Elog.i(TAG, "TetheringTestReceiver:" + intent.getAction());
        if (ACTION_ENABLE_NSIOT.equals(intent.getAction())) {
            boolean enabled = intent.getBooleanExtra("nsiot_enabled", true);
            Elog.i(TAG, "enabled:" + enabled);
            Intent i = new Intent(context, TetheringTestService.class);
            if (enabled) {
                context.startForegroundService(i);
            } else {
                context.stopService(i);
            }
        }
    }
}
