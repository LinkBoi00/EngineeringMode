package com.mediatek.engineermode.rfdesense;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mediatek.engineermode.Elog;

public class RfDesenseBroadcastReceiver extends BroadcastReceiver {
    public static String ARGUMENTS = "argument";
    private String START_ACTION = "com.mediatek.engineermode.rfdesenseServiceStart";
    private String STOP_ACTION = "com.mediatek.engineermode.rfdesenseServiceStop";
    public final String TAG = "RfDesense/BroadcastReceiver";

    public void onReceive(Context context, Intent intent) {
        Elog.d("RfDesense/BroadcastReceiver", "onReceive");
        String action = intent.getAction();
        if (this.START_ACTION.equalsIgnoreCase(action)) {
            Intent intent_service = new Intent(context, RfDesenseService.class);
            String str = ARGUMENTS;
            intent_service.putExtra(str, intent.getStringExtra(str));
            Elog.d("RfDesense/BroadcastReceiver", "RfDesenseService start ");
            context.startService(intent_service);
        } else if (this.STOP_ACTION.equalsIgnoreCase(action)) {
            Elog.d("RfDesense/BroadcastReceiver", "RfDesenseService stop");
            context.stopService(new Intent(context, RfDesenseService.class));
        }
    }
}
