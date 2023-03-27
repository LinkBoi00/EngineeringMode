package com.mediatek.engineermode.nonsleep;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;

public class EMWakeLockService extends Service {
    private static final int ID_FORE_GROUND_NOTIF = 11;
    private static final String TAG = "NonSleep/WakeLock";
    private final IBinder mBinder = new LocalBinder();
    private PowerManager.WakeLock mWakeLock = null;

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        /* access modifiers changed from: package-private */
        public EMWakeLockService getService() {
            return EMWakeLockService.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        Elog.d(TAG, "onCreate()");
    }

    public void onDestroy() {
        Elog.d(TAG, "onDestroy()");
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        PowerManager pm = (PowerManager) getSystemService("power");
        if (pm == null) {
            return null;
        }
        this.mWakeLock = pm.newWakeLock(268435482, TAG);
        return this.mBinder;
    }

    public boolean isHeld() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        return wakeLock != null && wakeLock.isHeld();
    }

    public void release() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLock.release();
            stopForeground(true);
        }
    }

    public void acquire(Class<? extends Activity> targetClass) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && !wakeLock.isHeld()) {
            this.mWakeLock.acquire();
            startForeground(11, buildNotification(targetClass));
        }
    }

    public void acquire(Class<? extends Activity> targetClass, long milliSec) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && !wakeLock.isHeld()) {
            this.mWakeLock.acquire(milliSec);
            startForeground(11, buildNotification(targetClass));
        }
    }

    private Notification buildNotification(Class<? extends Activity> clazz) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, EmApplication.getSilentNotificationChannelID());
        builder.setSmallIcon(17301543).setContentTitle("No Sleep Mode is Enabled").setContentText("Click Here to Switch No Sleep Mode");
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, clazz).addFlags(536870912), 67108864));
        return builder.build();
    }
}
