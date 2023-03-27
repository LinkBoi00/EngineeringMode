package com.mediatek.engineermode.bypass;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.EmUtils;
import java.io.File;
import java.io.IOException;

public class BypassService extends Service {
    private static final boolean DEBUG = true;
    private static final int ID_FORE_GROUND = 20170713;
    private static final String TAG = "BypassService";
    private Bypass mBypass;

    public static void enableService(Context context, boolean on) {
        Intent servIntent = new Intent(context, BypassService.class);
        Elog.i(TAG, "enableService:" + on);
        if (on) {
            context.startForegroundService(servIntent);
        } else {
            context.stopService(servIntent);
        }
    }

    private final class Bypass {
        private static final String ACTION_RADIO_AVAILABLE = "android.intent.action.RADIO_AVAILABLE";
        private static final String ACTION_USB_BYPASS_GETBYPASS = "com.via.bypass.action.getbypass";
        private static final String ACTION_USB_BYPASS_GETBYPASS_RESULT = "com.via.bypass.action.getbypass_result";
        private static final String ACTION_USB_BYPASS_SETBYPASS = "com.via.bypass.action.setbypass";
        private static final String ACTION_USB_BYPASS_SETBYPASS_RESULT = "com.via.bypass.action.setbypass_result";
        private static final String ACTION_USB_BYPASS_SETFUNCTION = "com.via.bypass.action.setfunction";
        private static final String ACTION_USB_BYPASS_SETTETHERFUNCTION = "com.via.bypass.action.settetherfunction";
        private static final String ACTION_VIA_ETS_DEV_CHANGED = "via.cdma.action.ets.dev.changed";
        private static final String ACTION_VIA_SET_ETS_DEV = "via.cdma.action.set.ets.dev";
        private static final String EXTRAL_VIA_ETS_DEV = "via.cdma.extral.ets.dev";
        private static final String VALUE_BYPASS_CODE = "com.via.bypass.bypass_code";
        private static final String VALUE_ENABLE_BYPASS = "com.via.bypass.enable_bypass";
        private static final String VALUE_ISSET_BYPASS = "com.via.bypass.isset_bypass";
        /* access modifiers changed from: private */
        public int mBypassAll;
        private final int[] mBypassCodes = {1, 2, 4, 8, 16};
        private File[] mBypassFiles;
        private final String[] mBypassName;
        private final BroadcastReceiver mBypassReceiver;
        /* access modifiers changed from: private */
        public int mBypassToSet;
        /* access modifiers changed from: private */
        public boolean mEtsDevInUse;
        /* access modifiers changed from: private */
        public UsbManager mUsbManager;

        public Bypass() {
            String[] strArr = {"gps", "pcv", "atc", "ets", JsonCmd.STR_DATA_KEY};
            this.mBypassName = strArr;
            this.mBypassAll = 0;
            this.mEtsDevInUse = false;
            this.mBypassReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    int bypass;
                    Elog.i(BypassService.TAG, "onReceive=" + intent.getAction());
                    if (intent.getAction() == null) {
                        return;
                    }
                    if (intent.getAction().equals(Bypass.ACTION_USB_BYPASS_SETFUNCTION)) {
                        if (Boolean.valueOf(intent.getBooleanExtra(Bypass.VALUE_ENABLE_BYPASS, false)).booleanValue()) {
                            UsbManager unused = Bypass.this.mUsbManager = (UsbManager) context.getSystemService("usb");
                            if (Bypass.this.mUsbManager == null) {
                                Elog.w(BypassService.TAG, "Cannot get mUsbManager.");
                            } else {
                                Bypass.this.mUsbManager.setCurrentFunction("via_bypass", false);
                            }
                        } else {
                            Bypass.this.closeBypassFunction();
                        }
                    } else if (intent.getAction().equals(Bypass.ACTION_USB_BYPASS_SETTETHERFUNCTION)) {
                        Elog.w(BypassService.TAG, "intent - ACTION_USB_BYPASS_SETTETHERFUNCTION");
                    } else if (intent.getAction().equals(Bypass.ACTION_USB_BYPASS_SETBYPASS)) {
                        int bypasscode = intent.getIntExtra(Bypass.VALUE_BYPASS_CODE, -1);
                        if (bypasscode < 0 || bypasscode > Bypass.this.mBypassAll) {
                            Bypass.this.notifySetBypassResult(false, Bypass.this.getCurrentBypassMode());
                        } else {
                            Bypass.this.setBypassMode(bypasscode);
                        }
                    } else if (intent.getAction().equals(Bypass.ACTION_USB_BYPASS_GETBYPASS)) {
                        Intent reintent = new Intent(Bypass.ACTION_USB_BYPASS_GETBYPASS_RESULT);
                        reintent.putExtra(Bypass.VALUE_BYPASS_CODE, Bypass.this.getCurrentBypassMode());
                        LocalBroadcastManager.getInstance(BypassService.this).sendBroadcast(reintent);
                    } else if (intent.getAction().equals(Bypass.ACTION_VIA_ETS_DEV_CHANGED)) {
                        if (intent.getBooleanExtra("set.ets.dev.result", false)) {
                            bypass = Bypass.this.mBypassToSet;
                        } else {
                            bypass = Bypass.this.getCurrentBypassMode();
                        }
                        Bypass.this.setBypass(bypass);
                    } else if (intent.getAction().equals(Bypass.ACTION_RADIO_AVAILABLE)) {
                        if (Bypass.this.mEtsDevInUse) {
                            Intent reintent2 = new Intent(Bypass.ACTION_VIA_SET_ETS_DEV);
                            reintent2.putExtra(Bypass.EXTRAL_VIA_ETS_DEV, 1);
                            LocalBroadcastManager.getInstance(BypassService.this).sendBroadcast(reintent2);
                        }
                    } else if (intent.getAction().equals(BypassSettings.USBMANAGER_ACTION_USB_STATE) && !intent.getBooleanExtra("connected", false)) {
                        Bypass.this.updateBypassMode(0);
                    }
                }
            };
            this.mUsbManager = (UsbManager) BypassService.this.getSystemService(UsbManager.class);
            this.mBypassFiles = new File[strArr.length];
            for (int i = 0; i < this.mBypassName.length; i++) {
                this.mBypassFiles[i] = new File("/sys/class/usb_rawbulk/" + this.mBypassName[i] + "/enable");
                this.mBypassAll = this.mBypassAll + this.mBypassCodes[i];
            }
            IntentFilter intent = new IntentFilter(ACTION_USB_BYPASS_SETFUNCTION);
            intent.addAction(ACTION_USB_BYPASS_SETTETHERFUNCTION);
            intent.addAction(ACTION_USB_BYPASS_SETBYPASS);
            intent.addAction(ACTION_USB_BYPASS_GETBYPASS);
            intent.addAction(ACTION_VIA_ETS_DEV_CHANGED);
            intent.addAction(ACTION_RADIO_AVAILABLE);
            intent.addAction(BypassSettings.USBMANAGER_ACTION_USB_STATE);
            LocalBroadcastManager.getInstance(BypassService.this).registerReceiver(this.mBypassReceiver, intent);
        }

        /* access modifiers changed from: private */
        public int getCurrentBypassMode() {
            int bypassmode = 0;
            for (int i = 0; i < this.mBypassCodes.length; i++) {
                String code = readTextFile(i);
                Elog.d(BypassService.TAG, "'" + this.mBypassFiles[i].getAbsolutePath() + "' value is " + code);
                if (code != null && code.trim().equals("1")) {
                    bypassmode |= this.mBypassCodes[i];
                }
            }
            Elog.d(BypassService.TAG, "getCurrentBypassMode()=" + bypassmode);
            return bypassmode;
        }

        private String readTextFile(int index) {
            try {
                if (ShellExe.readTextFile(index) == 0) {
                    return ShellExe.getOutput();
                }
                return ShellExe.getOutput();
            } catch (IOException e) {
                return "ERROR.IO_JE";
            }
        }

        /* access modifiers changed from: private */
        public void setBypass(int bypassmode) {
            Elog.d(BypassService.TAG, "setBypass bypass = " + bypassmode);
            int bypassResult = getCurrentBypassMode();
            Boolean valueOf = Boolean.valueOf(BypassService.DEBUG);
            if (bypassmode == bypassResult) {
                Elog.d(BypassService.TAG, "setBypass bypass == oldbypass!!");
                notifySetBypassResult(valueOf, bypassResult);
                return;
            }
            int i = 0;
            while (true) {
                int[] iArr = this.mBypassCodes;
                if (i < iArr.length) {
                    if ((iArr[i] & bypassmode) != 0) {
                        Elog.d(BypassService.TAG, "Write '" + this.mBypassFiles[i].getAbsolutePath() + "1");
                        try {
                            EmUtils.getEmHidlService().setBypassEn(Integer.toString(i));
                        } catch (Exception e) {
                            Elog.e(BypassService.TAG, "set property failed ...");
                            e.printStackTrace();
                        }
                        bypassResult |= this.mBypassCodes[i];
                    } else {
                        Elog.d(BypassService.TAG, "Write '" + this.mBypassFiles[i].getAbsolutePath() + "0");
                        try {
                            EmUtils.getEmHidlService().setBypassDis(Integer.toString(i));
                        } catch (Exception e2) {
                            Elog.e(BypassService.TAG, "set property failed ...");
                            e2.printStackTrace();
                        }
                        int[] iArr2 = this.mBypassCodes;
                        if ((iArr2[i] & bypassResult) != 0) {
                            bypassResult ^= iArr2[i];
                        }
                    }
                    Elog.d(BypassService.TAG, "Write '" + this.mBypassFiles[i].getAbsolutePath() + "' successsfully!");
                    i++;
                } else {
                    notifySetBypassResult(valueOf, bypassResult);
                    Elog.d(BypassService.TAG, "setBypass success bypassResult = " + bypassResult);
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void updateBypassMode(int bypassmode) {
            Elog.d(BypassService.TAG, "updateBypassMode");
            if (!setEtsDev(bypassmode)) {
                setBypass(bypassmode);
                return;
            }
            Elog.d(BypassService.TAG, "updateBypassMode mBypassToSet = " + this.mBypassToSet);
            this.mBypassToSet = bypassmode;
        }

        private boolean setEtsDev(int bypass) {
            int oldBypass = getCurrentBypassMode();
            Elog.d(BypassService.TAG, "setEtsDev bypass = " + bypass + " oldBypass = " + oldBypass);
            int[] iArr = this.mBypassCodes;
            if ((iArr[3] & bypass) != 0 && (iArr[3] & oldBypass) == 0) {
                Elog.d(BypassService.TAG, "setEtsDev mEtsDevInUse = true");
                Intent reintent = new Intent(ACTION_VIA_SET_ETS_DEV);
                reintent.putExtra(EXTRAL_VIA_ETS_DEV, 1);
                LocalBroadcastManager.getInstance(BypassService.this).sendBroadcast(reintent);
                this.mEtsDevInUse = BypassService.DEBUG;
                return BypassService.DEBUG;
            } else if ((iArr[3] & bypass) != 0 || (iArr[3] & oldBypass) == 0) {
                return false;
            } else {
                Elog.d(BypassService.TAG, "setEtsDev mEtsDevInUse = false");
                Intent reintent2 = new Intent(ACTION_VIA_SET_ETS_DEV);
                reintent2.putExtra(EXTRAL_VIA_ETS_DEV, 0);
                LocalBroadcastManager.getInstance(BypassService.this).sendBroadcast(reintent2);
                this.mEtsDevInUse = false;
                return BypassService.DEBUG;
            }
        }

        /* access modifiers changed from: private */
        public void setBypassMode(int bypassmode) {
            Elog.d(BypassService.TAG, "setBypassMode()=" + bypassmode);
            updateBypassMode(bypassmode);
        }

        /* access modifiers changed from: private */
        public void notifySetBypassResult(Boolean isset, int bypassCode) {
            Intent intent = new Intent(ACTION_USB_BYPASS_SETBYPASS_RESULT);
            intent.putExtra(VALUE_ISSET_BYPASS, isset);
            intent.putExtra(VALUE_BYPASS_CODE, bypassCode);
            LocalBroadcastManager.getInstance(BypassService.this).sendBroadcast(intent);
        }

        /* access modifiers changed from: private */
        public void closeBypassFunction() {
            this.mUsbManager.setCurrentFunction((String) null, false);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Elog.w(TAG, "onCreate");
        this.mBypass = new Bypass();
        try {
            EmUtils.getEmHidlService().setBypassService("1");
        } catch (Exception e) {
            Elog.e(TAG, "set property failed ...");
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(ID_FORE_GROUND, buildNotification());
        Elog.w(TAG, "onStartCommand");
        return 1;
    }

    public void onDestroy() {
        stopService(new Intent(this, BypassService.class));
        super.onDestroy();
        Elog.w(TAG, "onDestroy");
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, EmApplication.getSilentNotificationChannelID());
        builder.setSmallIcon(17301543);
        builder.setContentTitle(TAG);
        builder.setContentText("Enable BypassService");
        Notification nty = builder.build();
        nty.flags |= 32;
        Intent intent = new Intent(this, BypassService.class);
        intent.setFlags(335544320);
        nty.setLatestEventInfo(this, TAG, "Enable BypassService", PendingIntent.getActivity(this, 0, intent, 67108864));
        return nty;
    }
}
