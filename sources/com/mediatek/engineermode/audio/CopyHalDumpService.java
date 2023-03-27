package com.mediatek.engineermode.audio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mcfconfig.FileUtils;

public class CopyHalDumpService extends Service implements OnCopyProgressChangeListener {
    private static final String EMAUDIO_NOTIFICATION_NAME = "CopyAudioHalDumpService is running";
    private static final int ID_EMAUDIO_SERVICE = 197459;
    public static final int MSG_COPY_DONE = 12;
    public static final int MSG_COPY_PROGRESS_UPDATE = 10;
    public static final int MSG_DELETE_DONE = 13;
    public static final int MSG_DELETE_PROGRESS_UPDATE = 11;
    private static final String NOTIFICATION_CHANNEL = "CopyAudioHalDumpService";
    public static final String TAG = "Audio/Service";
    /* access modifiers changed from: private */
    public Handler mActivityHandler;
    private final IBinder mBinder = new LocalBinder();
    private DUMP_STATUS mStatus = DUMP_STATUS.DEFAULT;

    enum DUMP_STATUS {
        DEFAULT,
        COPY_HAL_DUMP,
        DELETE_HAL_DUMP,
        COPY_DUMP_DONE,
        DELETE_DUMP_DONE
    }

    public void onCreate() {
        super.onCreate();
        Elog.d(TAG, "onCreate");
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL, EMAUDIO_NOTIFICATION_NAME, 2));
        startForeground(ID_EMAUDIO_SERVICE, new Notification.Builder(getApplicationContext()).setContentTitle(EMAUDIO_NOTIFICATION_NAME).setSmallIcon(17301659).setChannelId(NOTIFICATION_CHANNEL).build());
        return super.onStartCommand(intent, flag, startId);
    }

    public void onDestroy() {
        Elog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public CopyHalDumpService getService(Handler handler) {
            Handler unused = CopyHalDumpService.this.mActivityHandler = handler;
            return CopyHalDumpService.this;
        }
    }

    public void removeUiHandler() {
        this.mActivityHandler = null;
        if (this.mStatus == DUMP_STATUS.COPY_DUMP_DONE || this.mStatus == DUMP_STATUS.DELETE_DUMP_DONE) {
            Elog.v(TAG, "reset dump status");
            this.mStatus = DUMP_STATUS.DEFAULT;
        }
    }

    public DUMP_STATUS getCopyDumpStatus() {
        return this.mStatus;
    }

    public void onCopyProgressChanged(String fileName, float readSize, float tSize) {
        if (this.mActivityHandler == null) {
            Elog.v(TAG, "mActivityHandler is null, don't need update COPY UI");
            return;
        }
        Elog.v(TAG, fileName + ":" + readSize + FileUtils.SEPARATOR + tSize);
        if ("SUCCESS".equals(fileName)) {
            Elog.v(TAG, "[onCopyProgressChanged] done");
            this.mActivityHandler.sendEmptyMessage(12);
            this.mStatus = DUMP_STATUS.COPY_DUMP_DONE;
            return;
        }
        this.mActivityHandler.sendMessage(this.mActivityHandler.obtainMessage(10, getString(R.string.Audio_copy_progress, new Object[]{fileName, Float.valueOf(readSize), Float.valueOf(tSize)})));
        this.mStatus = DUMP_STATUS.COPY_HAL_DUMP;
    }

    public void onDeleteProgressChanged(String fileName) {
        if (this.mActivityHandler == null) {
            Elog.v(TAG, "mActivityHandler is null, don't need update DELETE UI");
            return;
        }
        Elog.v(TAG, fileName);
        if ("SUCCESS".equals(fileName)) {
            Elog.v(TAG, "[onDeleteProgressChanged] done");
            this.mActivityHandler.sendEmptyMessage(13);
            this.mStatus = DUMP_STATUS.DELETE_DUMP_DONE;
            return;
        }
        Elog.v(TAG, "[onDeleteProgressChanged] delete" + fileName);
        this.mActivityHandler.sendMessage(this.mActivityHandler.obtainMessage(10, getString(R.string.Audio_delete_progress, new Object[]{fileName})));
        this.mStatus = DUMP_STATUS.DELETE_HAL_DUMP;
    }
}
