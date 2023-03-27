package com.mediatek.engineermode.tethering;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.mediatek.engineermode.Elog;
import java.util.List;
import vendor.mediatek.hardware.netdagent.V1_0.INetdagent;

public class TetheringTestService extends Service {
    private static final String TAG = "TetheringTestService";
    /* access modifiers changed from: private */
    public ConnectivityManager mCm;
    private TetheringManager.TetheringEventCallback mTetheringCallback;
    /* access modifiers changed from: private */
    public TetheringHandler mTetheringHandler;
    private TetheringManager mTm;

    public void onCreate() {
        super.onCreate();
        Elog.d(TAG, "onCreate");
        this.mCm = (ConnectivityManager) getSystemService("connectivity");
        this.mTm = (TetheringManager) getSystemService("tethering");
        HandlerThread tetheringThread = new HandlerThread("TetheringTestService-TetheringThread");
        tetheringThread.start();
        this.mTetheringHandler = new TetheringHandler(tetheringThread.getLooper());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            Elog.e(TAG, "onStartCommand: intent is null");
            return 2;
        }
        String action = intent.getAction();
        Elog.d(TAG, "onStartCommand:" + action);
        ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel(TAG, TAG, 2));
        startForeground(1, new Notification.Builder(this).setWhen(System.currentTimeMillis()).setChannelId(TAG).setSmallIcon(17302884).setContentTitle(TAG).build());
        this.mTm.registerTetheringEventCallback(new HandlerExecutor(this.mTetheringHandler), this.mTetheringHandler);
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        if (!this.mTetheringHandler.runWithScissors(new Runnable() {
            public void run() {
                TetheringTestService.this.mTetheringHandler.enableNsIotTest(false);
            }
        }, 3000)) {
            Elog.e(TAG, "Disabling NSIOT timed out!");
        }
        super.onDestroy();
    }

    private class TetheringHandler extends Handler implements TetheringManager.TetheringEventCallback {
        private static final int EVENT_TETHERED_INTERFACES_CHANGED = 1;
        private static final int EVENT_UPSTREAM_NETWORK_CHANGED = 0;
        private final String[] USB_REGEXES = {"usb\\d", "rndis\\d"};
        private String mDownstreamIface;
        private boolean mIsNsIotCmdSent;
        private String mUpstreamIface;

        public TetheringHandler(Looper l) {
            super(l);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String str = null;
                    if (msg.obj != null) {
                        LinkProperties lp = TetheringTestService.this.mCm.getLinkProperties((Network) msg.obj);
                        if (lp != null) {
                            str = lp.getInterfaceName();
                        }
                        this.mUpstreamIface = str;
                    } else {
                        this.mUpstreamIface = null;
                    }
                    handleTestStateChanged();
                    return;
                case 1:
                    this.mDownstreamIface = chooseDownstream((List) msg.obj);
                    handleTestStateChanged();
                    return;
                default:
                    return;
            }
        }

        public void enableNsIotTest(boolean enable) {
            String cmd;
            String downstreamIp;
            Elog.i(TetheringTestService.TAG, "enableNsIotTest: up=" + this.mUpstreamIface + ", down=" + this.mDownstreamIface);
            if (TextUtils.isEmpty(this.mDownstreamIface) || TextUtils.isEmpty(this.mUpstreamIface)) {
                Elog.e(TetheringTestService.TAG, "Not all ifaces are ready");
                return;
            }
            try {
                INetdagent agent = INetdagent.getService();
                if (agent == null) {
                    Elog.e(TetheringTestService.TAG, "agent is null");
                    return;
                }
                if (enable) {
                    int count = 3;
                    do {
                        agent.dispatchNetdagentCmd("netdagent firewall get_usb_client " + this.mDownstreamIface);
                        Thread.sleep(500);
                        downstreamIp = SystemProperties.get("vendor.net.rndis.client");
                        count += -1;
                        if (count != 0 || !TextUtils.isEmpty(downstreamIp)) {
                        }
                        agent.dispatchNetdagentCmd("netdagent firewall get_usb_client " + this.mDownstreamIface);
                        Thread.sleep(500);
                        downstreamIp = SystemProperties.get("vendor.net.rndis.client");
                        count += -1;
                        break;
                    } while (!TextUtils.isEmpty(downstreamIp));
                    if (TextUtils.isEmpty(downstreamIp)) {
                        Elog.e(TetheringTestService.TAG, "Failed to get downstream IP address");
                        return;
                    }
                    Elog.i(TetheringTestService.TAG, "Downstream IP address: " + downstreamIp);
                    cmd = "netdagent firewall set_udp_forwarding " + this.mDownstreamIface + " " + this.mUpstreamIface + " " + downstreamIp;
                } else {
                    cmd = "netdagent firewall clear_udp_forwarding " + this.mDownstreamIface + " " + this.mUpstreamIface;
                }
                Elog.i(TetheringTestService.TAG, "Send " + cmd);
                agent.dispatchNetdagentCmd(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onUpstreamChanged(Network network) {
            sendMessage(obtainMessage(0, network));
        }

        public void onTetheredInterfacesChanged(List<String> interfaces) {
            sendMessage(obtainMessage(1, interfaces));
        }

        private void handleTestStateChanged() {
            if (hasBothDownAndUpStreams()) {
                enableNsIotTest(true);
                this.mIsNsIotCmdSent = true;
            } else if (this.mIsNsIotCmdSent) {
                enableNsIotTest(false);
                this.mIsNsIotCmdSent = false;
            }
        }

        private boolean hasBothDownAndUpStreams() {
            return !TextUtils.isEmpty(this.mUpstreamIface) && !TextUtils.isEmpty(this.mDownstreamIface);
        }

        private String chooseDownstream(List<String> downstreams) {
            if (downstreams == null) {
                return null;
            }
            for (String regex : this.USB_REGEXES) {
                for (String iface : downstreams) {
                    if (iface.matches(regex)) {
                        return iface;
                    }
                }
            }
            return null;
        }
    }
}
