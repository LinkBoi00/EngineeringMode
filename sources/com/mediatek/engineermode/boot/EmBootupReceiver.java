package com.mediatek.engineermode.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTestTool;
import com.mediatek.engineermode.bluetooth.BtWatchService;
import com.mediatek.engineermode.bypass.BypassService;
import com.mediatek.engineermode.bypass.BypassSettings;
import com.mediatek.engineermode.wifi.MtkCTIATestDialog;
import com.mediatek.engineermode.wifi.WifiWatchService;

public class EmBootupReceiver extends BroadcastReceiver {
    private static final String MODEM_FILTER_SHAREPRE = "telephony_modem_filter_settings";
    private static final String TAG = "BootupReceiver";

    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            onBootupCompleted(context, intent);
        }
    }

    private void onBootupCompleted(Context context, Intent intent) {
        Elog.d(TAG, "Start onBootupCompleted");
        writeSharedPreference(context, false);
        writeShortVolteInterruptPreference(context);
        writeDsdaFloatingWindowPreference(context);
        tryInvokeBypassService(context);
        if (MtkCTIATestDialog.isWifiCtiaEnabled(context)) {
            WifiWatchService.enableService(context, true);
        }
        if (BtTestTool.getAlwaysVisible(context)) {
            BtTestTool.setAlwaysVisible(true);
            BtWatchService.enableService(context, true);
        }
        Elog.d(TAG, "End onBootupCompleted");
    }

    private void tryInvokeBypassService(Context context) {
        if (context.getSharedPreferences(BypassSettings.PREF_SERV_ENABLE, 0).getBoolean(BypassSettings.PREF_SERV_ENABLE, false)) {
            BypassService.enableService(context, true);
            Elog.w(TAG, "ready to start BypassService");
        }
    }

    private void writeSharedPreference(Context context, boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MODEM_FILTER_SHAREPRE, 0).edit();
        editor.putBoolean(context.getString(R.string.enable_md_filter_sim1), flag);
        editor.putBoolean(context.getString(R.string.enable_md_filter_sim2), flag);
        editor.commit();
    }

    private void writeShortVolteInterruptPreference(Context context) {
        Elog.d(TAG, "writeShortVolteInterruptPreference false");
        SharedPreferences.Editor mPrefEditor = context.getSharedPreferences("GwsdConfigure", 0).edit();
        mPrefEditor.putBoolean("gwsd_dual_capability", false);
        mPrefEditor.commit();
        SharedPreferences.Editor mPrefEditor2 = context.getSharedPreferences("LteGwsdConfigure", 0).edit();
        mPrefEditor2.putBoolean("gwsd_dual_capability", false);
        mPrefEditor2.commit();
    }

    private void writeDsdaFloatingWindowPreference(Context context) {
        Elog.d(TAG, "writeDsdaFloatingWindowPreference false");
        SharedPreferences.Editor mPrefEditor = context.getSharedPreferences("DsdaMonitor", 0).edit();
        mPrefEditor.putBoolean("Dsda_floating", false);
        mPrefEditor.commit();
    }
}
