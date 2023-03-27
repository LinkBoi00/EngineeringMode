package com.mediatek.engineermode;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class EngineerModeReceiver extends BroadcastReceiver {
    private static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
    private static final String TAG = "EngineerModeReceiver";
    private final Uri mEmUri = Uri.parse("android_secret_code://3646633");
    private final Uri mMTKLoggerUri = Uri.parse("android_secret_code://98685");

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            Elog.e(TAG, "Null action");
        } else if (intent.getAction().equals(SECRET_CODE_ACTION)) {
            Uri uri = intent.getData();
            Elog.i(TAG, "Receive secret code intent and uri is " + uri);
            if (uri.equals(this.mEmUri)) {
                Intent intentEm = new Intent(context, EngineerMode.class);
                intentEm.setFlags(268435456);
                context.startActivity(intentEm);
            } else if (uri.equals(this.mMTKLoggerUri)) {
                Intent intentMTKLogger = new Intent();
                intentMTKLogger.setAction("android.intent.action.MAIN");
                intentMTKLogger.addCategory("android.intent.category.LAUNCHER");
                intentMTKLogger.setComponent(new ComponentName("com.debug.loggerui", "com.debug.loggerui.MainActivity"));
                intentMTKLogger.setFlags(268435456);
                Elog.i(TAG, "Before start MTKLogger MainActivity");
                context.startActivity(intentMTKLogger);
            }
        }
    }
}
