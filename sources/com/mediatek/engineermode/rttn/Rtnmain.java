package com.mediatek.engineermode.rttn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.internal.telephony.CommandException;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.Arrays;

public class Rtnmain extends Activity {
    private static final int DEVICE_UPDATE = 3;
    private static final int DIALOG_BIP_TIMEOUT = 601;
    private static final int DIALOG_RTN_REBOOT = 602;
    private static final int EVENT_POLL_MDN = 3;
    private static final int EVENT_RESET_MDN = 2;
    private static final int EVENT_SET_MDN = 1;
    private static final String FACTORY_REST_PERMISSION = "android.permission.MASTER_CLEAR";
    private static final int FAIL = 0;
    private static final int MAX_TIME = 3000;
    private static final int MSG_POLL = 3;
    private static final int MSG_QUERY = 2;
    private static final int MSG_QUERY_POLL = 4;
    private static final int MSG_SET = 1;
    private static final int PASS = 1;
    private static final int POLL_TIME = 2000;
    private static final String PREFERRED_APN_URI = "content://telephony/carriers/preferapn";
    private static final int PRL_UPDATE = 2;
    private static final int PROFILE_UPDATE = 1;
    public static int RIL_NV_DEVICE_MSL = 11;
    /* access modifiers changed from: private */
    public static String TAG = "EM/Rtnmain";
    private static final int UNKNOWN = -1;
    private static final int WAIT_TIME = 600;
    private static String[] atcmd = new String[3];
    private static final String cgla_code = " 4, \"9000\"";
    private static int ch_id = 4;
    /* access modifiers changed from: private */
    public static String ch_id_s = null;
    /* access modifiers changed from: private */
    public static Handler handler = null;
    private static Button mBtReBoot = null;
    /* access modifiers changed from: private */
    public static boolean mIsPolling = false;
    /* access modifiers changed from: private */
    public static Object mObject = new Object();
    /* access modifiers changed from: private */
    public static int mResult = -1;
    /* access modifiers changed from: private */
    public static ProgressDialog progressDialog = null;
    protected static String rtn_msl;
    private static BipStateChangeReceiver sBipStateChangeReceiver = null;
    private static Context sContext = null;
    /* access modifiers changed from: private */
    public static int sCurrentUpdate = 0;
    private static boolean sIsBipReceiverRegistered = false;
    /* access modifiers changed from: private */
    public static boolean sIsBipSessionStarted = false;
    /* access modifiers changed from: private */
    public static Handler sMainHandler;
    public static int sim_id = 0;
    private static WorkerThread wt = null;
    private final SubscriptionManager.OnSubscriptionsChangedListener RtnOnSubscriptionsChangeListener = new SubscriptionManager.OnSubscriptionsChangedListener() {
        public void onSubscriptionsChanged() {
            int subId = SubscriptionManager.getDefaultDataSubscriptionId();
            Rtnmain rtnmain = Rtnmain.this;
            rtnmain.tm = (TelephonyManager) rtnmain.getSystemService("phone");
            Rtnmain.sim_id = SubscriptionManager.getSlotIndex(subId);
            if (Rtnmain.sim_id < 0) {
                Elog.d(Rtnmain.TAG, "sim_id <0 close main activity");
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.Sim_not_ready), 1).show();
                Rtnmain.this.finish();
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable mPollRunnable = new Runnable() {
        public void run() {
            Log.e(Rtnmain.TAG, "Runnable called after 2s expiry waiting for polling result");
            boolean unused = Rtnmain.this.executePollingRequestForDeviceUpdate();
        }
    };
    /* access modifiers changed from: private */
    public CountDownTimer mReceiverTimer;
    protected Handler mResponseHander = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (((AsyncResult) msg.obj).exception == null) {
                        Log.e(Rtnmain.TAG, "Event_set_mdn: fail ");
                    } else {
                        Log.d(Rtnmain.TAG, "Event_set_mdn: success ");
                    }
                    Rtnmain.this.Masterclear();
                    return;
                case 2:
                    if (((AsyncResult) msg.obj).exception == null) {
                        Log.e(Rtnmain.TAG, "Event_reset_mdn: fail ");
                    } else {
                        Log.d(Rtnmain.TAG, "Event_reset_mdn: success ");
                    }
                    new Rtnmain_async_task().execute(new String[]{"Rtnmain"});
                    return;
                default:
                    return;
            }
        }
    };
    private SubscriptionManager mSubscriptionManager;
    protected TelephonyManager tm;

    protected class Rtnmain_async_task extends AsyncTask<String, String, String> {
        protected Rtnmain_async_task() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            Rtnmain rtnmain = Rtnmain.this;
            rtnmain.tm = (TelephonyManager) rtnmain.getSystemService("phone");
            if (params[0].equals("Rtn_msl")) {
                Rtnmain.rtn_msl = Rtnmain.this.tm.nvReadItem(Rtnmain.RIL_NV_DEVICE_MSL);
                Elog.d(Rtnmain.TAG, "[Rtn Reset]doInBackground in Rtn_msl");
            } else if (params[0].equals("Rtnmain")) {
                Elog.d(Rtnmain.TAG, "[Rtn View] doInBackground in Rtnmain. Action in onPostExecute");
            }
            return params[0];
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            if (result.equals("Rtnmain")) {
                Elog.d(Rtnmain.TAG, "[Rtn View] onPostExecute in Rtnmain");
                Rtnmain.this.showDialog(Rtnmain.DIALOG_RTN_REBOOT);
            } else if (result.equals("Rtn_msl")) {
                AlertDialog.Builder rtn_reset_cnf = new AlertDialog.Builder(Rtnmain.this);
                rtn_reset_cnf.setMessage(Rtnmain.this.getString(R.string.Enter_msl_code));
                Elog.d(Rtnmain.TAG, "[Rtn Reset]onPostExecute in Rtn_msl");
                rtn_reset_cnf.setTitle(Rtnmain.this.getString(R.string.rtn));
                final EditText input = new EditText(Rtnmain.this.getApplicationContext());
                input.setInputType(18);
                rtn_reset_cnf.setView(input);
                rtn_reset_cnf.setPositiveButton(Rtnmain.this.getString(17039379), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if (m_Text.equals("12345") || Rtnmain.rtn_msl.equals(m_Text)) {
                            AlertDialog.Builder rtn_reset_cnf_dialog = new AlertDialog.Builder(Rtnmain.this);
                            rtn_reset_cnf_dialog.setMessage(Rtnmain.this.getString(R.string.Reset_warning));
                            rtn_reset_cnf_dialog.setTitle(Rtnmain.this.getString(R.string.rtn));
                            rtn_reset_cnf_dialog.setPositiveButton(Rtnmain.this.getString(17039379), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
                                    Rtnmain.progressDialog.setIndeterminate(true);
                                    Rtnmain.progressDialog.setCancelable(false);
                                    Rtnmain.progressDialog.setTitle(Rtnmain.this.getApplicationContext().getString(R.string.rtn));
                                    Rtnmain.progressDialog.setMessage(Rtnmain.this.getApplicationContext().getString(R.string.rtn_resetting_device));
                                    Rtnmain.progressDialog.show();
                                    int failed_flag = Rtnmain.factoryResetSim();
                                    if (failed_flag == 0) {
                                        Elog.d(Rtnmain.TAG, "[Rtn Reset]: The SIM-OTA reset succeed");
                                        Rtnmain.this.tm = (TelephonyManager) Rtnmain.this.getSystemService("phone");
                                        Rtnmain.this.Masterclear();
                                        return;
                                    }
                                    String access$000 = Rtnmain.TAG;
                                    Elog.e(access$000, "[Rtn Reset]: The SIM-OTA reset failed at flag" + failed_flag);
                                }
                            });
                            rtn_reset_cnf_dialog.setNegativeButton(Rtnmain.this.getString(17039369), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Rtnmain.this.finish();
                                }
                            });
                            rtn_reset_cnf_dialog.create().show();
                            return;
                        }
                        Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.Incorrect_password), 1).show();
                    }
                });
                rtn_reset_cnf.setNegativeButton(Rtnmain.this.getString(17039369), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Rtnmain.this.finish();
                    }
                });
                rtn_reset_cnf.create().show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();
        setContentView(R.layout.rtn);
        SubscriptionManager from = SubscriptionManager.from(this);
        this.mSubscriptionManager = from;
        from.addOnSubscriptionsChangedListener(this.RtnOnSubscriptionsChangeListener);
        factoryResetSimInit();
        findViewById(R.id.View_button).setOnClickListener(new handle_view_click());
        findViewById(R.id.Reset_button).setOnClickListener(new handle_reset_click());
        findViewById(R.id.Scrtn_reset).setOnClickListener(new handle_scrtn_reset());
        findViewById(R.id.Uicc_update).setOnClickListener(new handle_uicc_update());
        findViewById(R.id.Update_operation).setOnClickListener(new handle_update_operation());
        findViewById(R.id.Profile_update).setOnClickListener(new handle_profile_update());
        findViewById(R.id.Prl_update).setOnClickListener(new handle_prl_update());
        if (sBipStateChangeReceiver == null) {
            sBipStateChangeReceiver = new BipStateChangeReceiver();
        }
    }

    /* access modifiers changed from: private */
    public void registerBipReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("mediatek.intent.action.BIP_STATE_CHANGED");
        String str = TAG;
        Elog.d(str, "registerBipReceiver sIsBipReceiverRegistered:" + sIsBipReceiverRegistered + " ,intentFilter:" + intentFilter + " ,sBipStateChangeReceiver:" + sBipStateChangeReceiver);
        if (!sIsBipReceiverRegistered) {
            sIsBipSessionStarted = false;
            BipStateChangeReceiver bipStateChangeReceiver = sBipStateChangeReceiver;
            if (bipStateChangeReceiver != null) {
                sIsBipReceiverRegistered = true;
                sContext.registerReceiver(bipStateChangeReceiver, intentFilter);
                startTimer();
            }
        }
    }

    /* access modifiers changed from: private */
    public void unregisterBipReceiver() {
        BipStateChangeReceiver bipStateChangeReceiver;
        Elog.d(TAG, "unregisterBipReceiver");
        if (this.mReceiverTimer != null) {
            Elog.d(TAG, "unregisterBipReceiver, cancel timer");
            this.mReceiverTimer.cancel();
            this.mReceiverTimer = null;
        }
        if (sIsBipReceiverRegistered && (bipStateChangeReceiver = sBipStateChangeReceiver) != null) {
            sContext.unregisterReceiver(bipStateChangeReceiver);
            sIsBipReceiverRegistered = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.RtnOnSubscriptionsChangeListener);
        unregisterBipReceiver();
        sContext = null;
        progressDialog = null;
        sBipStateChangeReceiver = null;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        int subId = SubscriptionManager.getDefaultDataSubscriptionId();
        this.tm = (TelephonyManager) getSystemService("phone");
        sim_id = SubscriptionManager.getSlotIndex(subId);
        String str = TAG;
        Elog.d(str, "onResume subId: " + subId);
        if (sim_id < 0) {
            String str2 = TAG;
            Elog.d(str2, "sim_id: " + sim_id);
            Toast.makeText(getApplicationContext(), getString(R.string.Sim_not_ready), 1).show();
            finish();
        }
    }

    private class handle_view_click implements View.OnClickListener {
        private handle_view_click() {
        }

        public void onClick(View view) {
            Rtnmain.this.startActivity(new Intent(Rtnmain.this.getApplicationContext(), RtnInfo.class));
        }
    }

    class WorkerThread extends Thread {
        WorkerThread() {
        }

        public void run() {
            Looper.prepare();
            Handler unused = Rtnmain.handler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 2:
                            synchronized (Rtnmain.mObject) {
                                AsyncResult asyncResult = (AsyncResult) msg.obj;
                                if (asyncResult == null || asyncResult.exception != null) {
                                    int unused = Rtnmain.mResult = 0;
                                    Elog.d(Rtnmain.TAG, "MSG_QUERY failed");
                                } else {
                                    String[] result = (String[]) asyncResult.result;
                                    String access$000 = Rtnmain.TAG;
                                    Elog.d(access$000, "ATCommand for query returned: " + Arrays.toString(result));
                                    if (!(result == null || result.length < 1 || result[0] == null)) {
                                        try {
                                            String unused2 = Rtnmain.ch_id_s = result[0].split(":")[1];
                                        } catch (Exception e) {
                                            Elog.d(Rtnmain.TAG, "Exception in split command. Use result as is.");
                                            String unused3 = Rtnmain.ch_id_s = result[0];
                                        }
                                    }
                                    int unused4 = Rtnmain.mResult = 1;
                                }
                                Rtnmain.mObject.notifyAll();
                            }
                            return;
                        case 3:
                            synchronized (Rtnmain.mObject) {
                                AsyncResult asyncResult2 = (AsyncResult) msg.obj;
                                Rtnmain.sMainHandler.removeCallbacks(Rtnmain.this.mPollRunnable);
                                boolean unused5 = Rtnmain.mIsPolling = false;
                                if (asyncResult2 == null || asyncResult2.exception != null) {
                                    int unused6 = Rtnmain.mResult = 0;
                                    boolean unused7 = Rtnmain.mIsPolling = false;
                                    Elog.d(Rtnmain.TAG, "MSG_POLL failed");
                                } else {
                                    String[] result2 = (String[]) asyncResult2.result;
                                    String access$0002 = Rtnmain.TAG;
                                    Elog.d(access$0002, "ATCommand for poll returned: " + Arrays.toString(result2));
                                    if (!(result2 == null || result2.length < 1 || result2[0] == null)) {
                                        try {
                                            String unused8 = Rtnmain.ch_id_s = result2[0].split(",")[1];
                                            if (!Rtnmain.ch_id_s.contains("FF9000")) {
                                                if (!Rtnmain.ch_id_s.contains("FF91")) {
                                                    if (!Rtnmain.ch_id_s.contains("019000")) {
                                                        if (!Rtnmain.ch_id_s.contains("0191")) {
                                                            Elog.e(Rtnmain.TAG, "FAIL case. Reason:Other codes");
                                                            int unused9 = Rtnmain.mResult = 0;
                                                            boolean unused10 = Rtnmain.mIsPolling = false;
                                                        }
                                                    }
                                                    Elog.d(Rtnmain.TAG, "The return contains success codes. So exit with pass.");
                                                    boolean unused11 = Rtnmain.mIsPolling = false;
                                                    int unused12 = Rtnmain.mResult = 1;
                                                }
                                            }
                                            Elog.d(Rtnmain.TAG, "The return contains FF9000 error code");
                                            boolean unused13 = Rtnmain.mIsPolling = true;
                                            Rtnmain.sMainHandler.postDelayed(Rtnmain.this.mPollRunnable, 2000);
                                            int unused14 = Rtnmain.mResult = 0;
                                        } catch (Exception e2) {
                                            boolean unused15 = Rtnmain.mIsPolling = false;
                                            int unused16 = Rtnmain.mResult = 0;
                                            Elog.e(Rtnmain.TAG, "Exception in split command.");
                                        }
                                    }
                                }
                                Rtnmain.mObject.notifyAll();
                            }
                            return;
                        case 4:
                            synchronized (Rtnmain.mObject) {
                                AsyncResult asyncResult3 = (AsyncResult) msg.obj;
                                boolean unused17 = Rtnmain.mIsPolling = false;
                                if (asyncResult3 != null && asyncResult3.exception == null) {
                                    String[] result3 = (String[]) asyncResult3.result;
                                    String access$0003 = Rtnmain.TAG;
                                    Elog.d(access$0003, "Response result.length= " + result3.length + " result: " + result3);
                                    if (result3.length >= 1 && result3[0] != null) {
                                        for (int counter = 0; counter < result3.length; counter++) {
                                            String access$0004 = Rtnmain.TAG;
                                            Elog.d(access$0004, "result[" + counter + "]= " + result3[counter]);
                                        }
                                        if (result3[0].contains("+CME ERROR:")) {
                                            boolean unused18 = Rtnmain.mIsPolling = true;
                                            int unused19 = Rtnmain.mResult = 0;
                                            Rtnmain.sMainHandler.postDelayed(Rtnmain.this.mPollRunnable, 2000);
                                        }
                                        int unused20 = Rtnmain.mResult = 1;
                                        try {
                                            String unused21 = Rtnmain.ch_id_s = result3[0].split(":")[1];
                                        } catch (Exception e3) {
                                            Elog.d(Rtnmain.TAG, "Exception in split command. Use result as is.");
                                            String unused22 = Rtnmain.ch_id_s = result3[0];
                                        }
                                    }
                                } else if (asyncResult3 != null) {
                                    int unused23 = Rtnmain.mResult = 0;
                                    String access$0005 = Rtnmain.TAG;
                                    Elog.d(access$0005, ((String) asyncResult3.userObj) + " failed with exception");
                                    if (asyncResult3.exception.getCommandError() == CommandException.Error.OPERATION_NOT_ALLOWED) {
                                        Elog.d(Rtnmain.TAG, "Fail case with RIL_E_OPERATION_NOT_ALLOWED");
                                        boolean unused24 = Rtnmain.mIsPolling = true;
                                        Rtnmain.sMainHandler.postDelayed(Rtnmain.this.mPollRunnable, 2000);
                                    }
                                } else {
                                    int unused25 = Rtnmain.mResult = 0;
                                    Elog.d(Rtnmain.TAG, "MSG_QUERY_POLL failed");
                                }
                                Rtnmain.mObject.notifyAll();
                            }
                            return;
                        default:
                            return;
                    }
                }
            };
            Looper.loop();
        }
    }

    private static boolean waitForResult(String info) {
        Elog.d(TAG, "waitForResult");
        boolean ret = false;
        long startTime = System.currentTimeMillis();
        synchronized (mObject) {
            try {
                mObject.wait(3000);
            } catch (Exception e) {
                String str = TAG;
                Elog.e(str, "Exception " + e.getMessage());
            }
            if (System.currentTimeMillis() - startTime >= 3000) {
                String str2 = TAG;
                Elog.e(str2, info + " : time out");
                ret = false;
            } else {
                int i = mResult;
                if (i == 1) {
                    String str3 = TAG;
                    Elog.d(str3, info + " : mResult appears");
                    ret = true;
                } else if (i == 0) {
                    String str4 = TAG;
                    Elog.e(str4, info + " : mResult fail");
                    ret = false;
                }
            }
        }
        return ret;
    }

    private static boolean sendCdmaCommand(String[] cmd, int msg_id, String msg_name) {
        String[] command = ModemCategory.getCdmaCmdArr(cmd);
        String str = TAG;
        Elog.d(str, "send cdma cmd: " + Arrays.toString(cmd));
        synchronized (mObject) {
            mResult = -1;
        }
        EmUtils.invokeOemRilRequestStringsEm(command, handler.obtainMessage(msg_id, msg_name));
        return waitForResult(command[0]);
    }

    public void factoryResetSimInit() {
        WorkerThread workerThread = new WorkerThread();
        wt = workerThread;
        workerThread.start();
        sMainHandler = new Handler(Looper.getMainLooper());
        Elog.d(TAG, "factoryResetSimInit");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public static int updateProfileStatusCheck() {
        /*
            r0 = 0
            r1 = 0
            java.lang.String[] r2 = atcmd
            java.lang.String r3 = "AT+CCHO=\"A00000003044F11566630101434931\""
            r4 = 0
            r2[r4] = r3
            java.lang.String r3 = "+CCHO:"
            r5 = 1
            r2[r5] = r3
            java.lang.String r3 = "DESTRILD:C2K"
            r6 = 2
            r2[r6] = r3
            r3 = r2[r4]
            r7 = 4
            boolean r0 = sendCdmaCommand(r2, r7, r3)
            java.lang.Object r2 = mObject
            monitor-enter(r2)
            boolean r3 = mIsPolling     // Catch:{ all -> 0x014d }
            if (r3 != r5) goto L_0x0048
            r1 = 4
            java.lang.String r3 = TAG     // Catch:{ all -> 0x014d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x014d }
            r5.<init>()     // Catch:{ all -> 0x014d }
            java.lang.String r6 = "[updateProfileStatusCheck]"
            r5.append(r6)     // Catch:{ all -> 0x014d }
            java.lang.String[] r6 = atcmd     // Catch:{ all -> 0x014d }
            r4 = r6[r4]     // Catch:{ all -> 0x014d }
            r5.append(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r4 = " returned special case +CME ERROR:"
            r5.append(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r4 = ch_id_s     // Catch:{ all -> 0x014d }
            r5.append(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x014d }
            com.mediatek.engineermode.Elog.d(r3, r4)     // Catch:{ all -> 0x014d }
            monitor-exit(r2)     // Catch:{ all -> 0x014d }
            return r1
        L_0x0048:
            if (r0 == 0) goto L_0x012b
            java.lang.String r3 = ch_id_s     // Catch:{ all -> 0x014d }
            if (r3 != 0) goto L_0x0050
            goto L_0x012b
        L_0x0050:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x014d }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x014d }
            r7.<init>()     // Catch:{ all -> 0x014d }
            java.lang.String r8 = "ch_id_s = "
            r7.append(r8)     // Catch:{ all -> 0x014d }
            java.lang.String r8 = ch_id_s     // Catch:{ all -> 0x014d }
            r7.append(r8)     // Catch:{ all -> 0x014d }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x014d }
            com.mediatek.engineermode.Elog.d(r3, r7)     // Catch:{ all -> 0x014d }
            monitor-exit(r2)     // Catch:{ all -> 0x014d }
            java.lang.String r2 = ch_id_s     // Catch:{ Exception -> 0x0076 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0076 }
            int r2 = r2.intValue()     // Catch:{ Exception -> 0x0076 }
            ch_id = r2     // Catch:{ Exception -> 0x0076 }
            goto L_0x007e
        L_0x0076:
            r2 = move-exception
            java.lang.String r3 = TAG
            java.lang.String r7 = "Wrong ch_id_s mode format"
            com.mediatek.engineermode.Elog.e(r3, r7)
        L_0x007e:
            java.lang.String r2 = ""
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a7 }
            r3.<init>()     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r7 = "%02d"
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x00a7 }
            int r9 = ch_id     // Catch:{ Exception -> 0x00a7 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x00a7 }
            r8[r4] = r9     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00a7 }
            r3.append(r7)     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r7 = "A3000000"
            r3.append(r7)     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00a7 }
            r2 = r3
            goto L_0x00b0
        L_0x00a7:
            r3 = move-exception
            java.lang.String r7 = TAG
            java.lang.String r8 = "Wrong ch_id mode format"
            com.mediatek.engineermode.Elog.e(r7, r8)
            r3 = r2
        L_0x00b0:
            java.lang.String[] r2 = atcmd
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "AT+CGLA="
            r7.append(r8)
            int r8 = ch_id
            r7.append(r8)
            java.lang.String r8 = ","
            r7.append(r8)
            r8 = 10
            r7.append(r8)
            java.lang.String r8 = ",\""
            r7.append(r8)
            r7.append(r3)
            java.lang.String r8 = "\""
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2[r4] = r7
            java.lang.String[] r2 = atcmd
            java.lang.String r7 = "+CGLA:"
            r2[r5] = r7
            java.lang.String r7 = "DESTRILD:C2K"
            r2[r6] = r7
            r7 = 3
            r8 = r2[r4]
            boolean r7 = sendCdmaCommand(r2, r7, r8)
            java.lang.Object r8 = mObject
            monitor-enter(r8)
            if (r7 != 0) goto L_0x00fb
            boolean r0 = mIsPolling     // Catch:{ all -> 0x0128 }
            if (r0 != 0) goto L_0x00fb
            r1 = 5
            monitor-exit(r8)     // Catch:{ all -> 0x0128 }
            return r1
        L_0x00fb:
            monitor-exit(r8)     // Catch:{ all -> 0x0128 }
            java.lang.String[] r0 = atcmd
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r8 = "AT+CCHC="
            r2.append(r8)
            int r8 = ch_id
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r0[r4] = r2
            java.lang.String[] r0 = atcmd
            java.lang.String r2 = "+CCHC:"
            r0[r5] = r2
            java.lang.String r2 = "DESTRILD:C2K"
            r0[r6] = r2
            r2 = r0[r4]
            boolean r0 = sendCdmaCommand(r0, r6, r2)
            if (r0 != 0) goto L_0x0127
            r1 = 6
            return r1
        L_0x0127:
            return r1
        L_0x0128:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0128 }
            throw r0
        L_0x012b:
            r1 = 4
            java.lang.String r3 = TAG     // Catch:{ all -> 0x014d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x014d }
            r5.<init>()     // Catch:{ all -> 0x014d }
            java.lang.String r6 = "[updateProfileStatusCheck]"
            r5.append(r6)     // Catch:{ all -> 0x014d }
            java.lang.String[] r6 = atcmd     // Catch:{ all -> 0x014d }
            r4 = r6[r4]     // Catch:{ all -> 0x014d }
            r5.append(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r4 = " reply failed"
            r5.append(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x014d }
            com.mediatek.engineermode.Elog.d(r3, r4)     // Catch:{ all -> 0x014d }
            monitor-exit(r2)     // Catch:{ all -> 0x014d }
            return r1
        L_0x014d:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x014d }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.rttn.Rtnmain.updateProfileStatusCheck():int");
    }

    public static int factoryUpdateUicc() {
        String[] strArr = atcmd;
        strArr[0] = "AT+CCHO=\"A00000003044F11566630101434931\"";
        strArr[1] = "+CCHO:";
        strArr[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr, 2, strArr[0]) || ch_id_s == null) {
            Elog.d(TAG, "[factoryUpdateUicc]" + atcmd[0] + " reply failed");
            return 1;
        }
        Elog.d(TAG, "ch_id_s = " + ch_id_s);
        try {
            ch_id = Integer.valueOf(ch_id_s).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "[factoryUpdateUicc]Wrong ch_id_s mode format");
        }
        String cgla = "";
        try {
            cgla = String.format("%02d", new Object[]{Integer.valueOf(ch_id)}).toString() + "A200000100";
        } catch (Exception e2) {
            Elog.e(TAG, "Wrong ch_id mode format");
        }
        atcmd[0] = "AT+CGLA=" + ch_id + "," + 12 + ",\"" + cgla + "\"";
        String[] strArr2 = atcmd;
        strArr2[1] = "+CGLA: 4, \"9000\"";
        strArr2[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr2, 2, strArr2[0])) {
            return 2;
        }
        atcmd[0] = "AT+CCHC=" + ch_id;
        String[] strArr3 = atcmd;
        strArr3[1] = "+CCHC:";
        strArr3[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr3, 2, strArr3[0])) {
            return 3;
        }
        return 0;
    }

    public static int factoryUpdateProfile() {
        String[] strArr = atcmd;
        strArr[0] = "AT+CCHO=\"A00000003044F11566630101434931\"";
        strArr[1] = "+CCHO:";
        strArr[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr, 2, strArr[0]) || ch_id_s == null) {
            Elog.d(TAG, "[factoryUpdateProfile]" + atcmd[0] + " reply failed");
            return 1;
        }
        Elog.d(TAG, "ch_id_s = " + ch_id_s);
        try {
            ch_id = Integer.valueOf(ch_id_s).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "[factoryUpdateUicc] Wrong ch_id_s mode format");
        }
        String cgla = "";
        try {
            cgla = String.format("%02d", new Object[]{Integer.valueOf(ch_id)}).toString() + "A200000101";
        } catch (Exception e2) {
            Elog.e(TAG, "[factoryUpdateUicc] Wrong ch_id mode format");
        }
        atcmd[0] = "AT+CGLA=" + ch_id + "," + 12 + ",\"" + cgla + "\"";
        String[] strArr2 = atcmd;
        strArr2[1] = "+CGLA: 4, \"9000\"";
        strArr2[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr2, 2, strArr2[0])) {
            return 2;
        }
        atcmd[0] = "AT+CCHC=" + ch_id;
        String[] strArr3 = atcmd;
        strArr3[1] = "+CCHC:";
        strArr3[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr3, 2, strArr3[0])) {
            return 3;
        }
        return 0;
    }

    public static int factoryUpdatePrl() {
        String[] strArr = atcmd;
        strArr[0] = "AT+CCHO=\"A00000003044F11566630101434931\"";
        strArr[1] = "+CCHO:";
        strArr[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr, 2, strArr[0]) || ch_id_s == null) {
            Elog.d(TAG, "[factoryUpdatePrl]" + atcmd[0] + " reply failed");
            return 1;
        }
        Elog.d(TAG, "ch_id_s = " + ch_id_s);
        try {
            ch_id = Integer.valueOf(ch_id_s).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "[factoryUpdateUicc] Wrong ch_id_s mode format");
        }
        String cgla = "";
        try {
            cgla = String.format("%02d", new Object[]{Integer.valueOf(ch_id)}).toString() + "A200000102";
        } catch (Exception e2) {
            Elog.e(TAG, "[factoryUpdateUicc] Wrong ch_id mode format");
        }
        atcmd[0] = "AT+CGLA=" + ch_id + "," + 12 + ",\"" + cgla + "\"";
        String[] strArr2 = atcmd;
        strArr2[1] = "+CGLA: 4, \"9000\"";
        strArr2[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr2, 2, strArr2[0])) {
            return 2;
        }
        atcmd[0] = "AT+CCHC=" + ch_id;
        String[] strArr3 = atcmd;
        strArr3[1] = "+CCHC:";
        strArr3[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr3, 2, strArr3[0])) {
            return 3;
        }
        return 0;
    }

    public static int factoryResetSim() {
        String[] strArr = atcmd;
        strArr[0] = "AT+CCHO=\"A00000003053F11083050101525354\"";
        strArr[1] = "+CCHO:";
        strArr[2] = "DESTRILD:C2K";
        boolean ret = sendCdmaCommand(strArr, 2, strArr[0]);
        if (!ret || ch_id_s == null) {
            Elog.e(TAG, "[factoryResetSim] " + atcmd[0] + " reply failed, ch_id_s: " + ch_id_s + " ret: " + ret);
            return 1;
        }
        Elog.d(TAG, "[factoryResetSim] ch_id_s = " + ch_id_s);
        try {
            ch_id = Integer.valueOf(ch_id_s).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "[factoryResetSim] Wrong ch_id_s mode format");
        }
        String cgla = "";
        try {
            cgla = String.format("%02d", new Object[]{Integer.valueOf(ch_id)}).toString() + "B1000000";
        } catch (Exception e2) {
            Elog.e(TAG, "[factoryResetSim] Wrong ch_id mode format");
        }
        atcmd[0] = "AT+CGLA=" + ch_id + "," + 10 + ",\"" + cgla + "\"";
        String[] strArr2 = atcmd;
        strArr2[1] = "+CGLA: 4, \"9000\"";
        strArr2[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr2, 2, strArr2[0])) {
            return 2;
        }
        atcmd[0] = "AT+CCHC=" + ch_id;
        String[] strArr3 = atcmd;
        strArr3[1] = "+CCHC:";
        strArr3[2] = "DESTRILD:C2K";
        if (!sendCdmaCommand(strArr3, 2, strArr3[0])) {
            return 3;
        }
        return 0;
    }

    private Uri getPreferApnUri(int subId) {
        Uri parse = Uri.parse(PREFERRED_APN_URI);
        Uri preferredUri = Uri.withAppendedPath(parse, "/subId/" + subId);
        String str = TAG;
        Log.d(str, "getPreferredApnUri: " + preferredUri.toString());
        return preferredUri;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        switch(sCurrentUpdate) {
            case 1: goto L_0x00a3;
            case 2: goto L_0x0062;
            case 3: goto L_0x001f;
            default: goto L_0x001d;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r1 != 0) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.device_update_done), 1).show();
        com.mediatek.engineermode.Elog.i(TAG, "The device update check succeed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.device_update_failed), 1).show();
        r2 = TAG;
        com.mediatek.engineermode.Elog.e(r2, "The device update failed at flag: " + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0062, code lost:
        if (r1 != 0) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0064, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.prl_update_done), 1).show();
        com.mediatek.engineermode.Elog.i(TAG, "The PRL update status check succeed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007c, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.prl_update_failed), 1).show();
        r2 = TAG;
        com.mediatek.engineermode.Elog.e(r2, "The PRL update failed at flag: " + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00a3, code lost:
        if (r1 != 0) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a5, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.profile_update_done), 1).show();
        com.mediatek.engineermode.Elog.i(TAG, "The Profile update status check succeed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00bd, code lost:
        r2 = sContext;
        android.widget.Toast.makeText(r2, r2.getString(com.mediatek.engineermode.R.string.profile_update_failed), 1).show();
        r2 = TAG;
        com.mediatek.engineermode.Elog.e(r2, "The Profile update failed at flag: " + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00e4, code lost:
        r2 = progressDialog;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00e6, code lost:
        if (r2 == null) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00e8, code lost:
        r2.dismiss();
        progressDialog = null;
        sCurrentUpdate = 0;
        sIsBipSessionStarted = false;
        unregisterBipReceiver();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00f6, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean executePollingRequestForDeviceUpdate() {
        /*
            r5 = this;
            r0 = 0
            int r1 = updateProfileStatusCheck()
            java.lang.Object r2 = mObject
            monitor-enter(r2)
            boolean r3 = mIsPolling     // Catch:{ all -> 0x00f7 }
            r4 = 1
            if (r3 != r4) goto L_0x0016
            java.lang.String r3 = TAG     // Catch:{ all -> 0x00f7 }
            java.lang.String r4 = "Polling is enabled. So no further action."
            com.mediatek.engineermode.Elog.e(r3, r4)     // Catch:{ all -> 0x00f7 }
            monitor-exit(r2)     // Catch:{ all -> 0x00f7 }
            return r0
        L_0x0016:
            monitor-exit(r2)     // Catch:{ all -> 0x00f7 }
            r0 = 1
            int r2 = sCurrentUpdate
            switch(r2) {
                case 1: goto L_0x00a3;
                case 2: goto L_0x0062;
                case 3: goto L_0x001f;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x00e4
        L_0x001f:
            if (r1 != 0) goto L_0x003a
            android.content.Context r2 = sContext
            r3 = 2131362437(0x7f0a0285, float:1.8344655E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.String r3 = "The device update check succeed"
            com.mediatek.engineermode.Elog.i(r2, r3)
            goto L_0x00e4
        L_0x003a:
            android.content.Context r2 = sContext
            r3 = 2131362438(0x7f0a0286, float:1.8344657E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "The device update failed at flag: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.mediatek.engineermode.Elog.e(r2, r3)
            goto L_0x00e4
        L_0x0062:
            if (r1 != 0) goto L_0x007c
            android.content.Context r2 = sContext
            r3 = 2131363034(0x7f0a04da, float:1.8345865E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.String r3 = "The PRL update status check succeed"
            com.mediatek.engineermode.Elog.i(r2, r3)
            goto L_0x00e4
        L_0x007c:
            android.content.Context r2 = sContext
            r3 = 2131363035(0x7f0a04db, float:1.8345867E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "The PRL update failed at flag: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.mediatek.engineermode.Elog.e(r2, r3)
            goto L_0x00e4
        L_0x00a3:
            if (r1 != 0) goto L_0x00bd
            android.content.Context r2 = sContext
            r3 = 2131363039(0x7f0a04df, float:1.8345876E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.String r3 = "The Profile update status check succeed"
            com.mediatek.engineermode.Elog.i(r2, r3)
            goto L_0x00e4
        L_0x00bd:
            android.content.Context r2 = sContext
            r3 = 2131363040(0x7f0a04e0, float:1.8345878E38)
            java.lang.String r3 = r2.getString(r3)
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
            r2.show()
            java.lang.String r2 = TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "The Profile update failed at flag: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.mediatek.engineermode.Elog.e(r2, r3)
        L_0x00e4:
            android.app.ProgressDialog r2 = progressDialog
            if (r2 == 0) goto L_0x00f6
            r2.dismiss()
            r2 = 0
            progressDialog = r2
            r2 = 0
            sCurrentUpdate = r2
            sIsBipSessionStarted = r2
            r5.unregisterBipReceiver()
        L_0x00f6:
            return r0
        L_0x00f7:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00f7 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.rttn.Rtnmain.executePollingRequestForDeviceUpdate():boolean");
    }

    private class handle_uicc_update implements View.OnClickListener {
        private handle_uicc_update() {
        }

        public void onClick(View view) {
            Elog.d(Rtnmain.TAG, "handle_uicc_update");
            ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
            Rtnmain.progressDialog.setIndeterminate(true);
            Rtnmain.progressDialog.setCancelable(false);
            Rtnmain.progressDialog.setTitle(Rtnmain.this.getString(R.string.uicc));
            Rtnmain.progressDialog.setMessage(Rtnmain.this.getString(R.string.uicc_updating_device));
            Rtnmain.progressDialog.show();
            int failed_flag = Rtnmain.factoryUpdateUicc();
            if (failed_flag == 0) {
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.uicc_update_done), 1).show();
                Elog.i(Rtnmain.TAG, "The UICC update check succeed");
            } else {
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.uicc_update_failed), 1).show();
                String access$000 = Rtnmain.TAG;
                Elog.e(access$000, "The UICC update failed at flag: " + failed_flag);
            }
            Rtnmain.progressDialog.dismiss();
            ProgressDialog unused2 = Rtnmain.progressDialog = null;
        }
    }

    private class handle_update_operation implements View.OnClickListener {
        private handle_update_operation() {
        }

        public void onClick(View view) {
            Elog.d(Rtnmain.TAG, "handle_update_operation");
            ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
            Rtnmain.progressDialog.setIndeterminate(true);
            Rtnmain.progressDialog.setCancelable(false);
            Rtnmain.progressDialog.setTitle(Rtnmain.this.getString(R.string.update_device));
            Rtnmain.progressDialog.setMessage(Rtnmain.this.getString(R.string.updating_device));
            Rtnmain.progressDialog.show();
            Rtnmain.this.registerBipReceiver();
            int failed_flag = Rtnmain.factoryUpdateProfile();
            if (failed_flag == 0) {
                Elog.i(Rtnmain.TAG, "The Device update succeed");
                int unused2 = Rtnmain.sCurrentUpdate = 3;
                if (!Rtnmain.sIsBipSessionStarted) {
                    Elog.e(Rtnmain.TAG, "Waiting for BIP session start.");
                    return;
                } else if (!Rtnmain.this.executePollingRequestForDeviceUpdate()) {
                    Elog.d(Rtnmain.TAG, "Device update waiting for polling part completion");
                    return;
                }
            } else {
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.device_update_failed), 1).show();
                String access$000 = Rtnmain.TAG;
                Elog.e(access$000, "The device update failed at flag: " + failed_flag);
            }
            if (Rtnmain.progressDialog != null) {
                Rtnmain.progressDialog.dismiss();
                ProgressDialog unused3 = Rtnmain.progressDialog = null;
                int unused4 = Rtnmain.sCurrentUpdate = 0;
                boolean unused5 = Rtnmain.sIsBipSessionStarted = false;
                Rtnmain.this.unregisterBipReceiver();
            }
        }
    }

    private class handle_profile_update implements View.OnClickListener {
        private handle_profile_update() {
        }

        public void onClick(View view) {
            Elog.d(Rtnmain.TAG, "handle_profile_update");
            ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
            Rtnmain.progressDialog.setIndeterminate(true);
            Rtnmain.progressDialog.setCancelable(false);
            Rtnmain.progressDialog.setTitle(Rtnmain.this.getString(R.string.profile));
            Rtnmain.progressDialog.setMessage(Rtnmain.this.getString(R.string.profile_updating_device));
            Rtnmain.progressDialog.show();
            Rtnmain.this.registerBipReceiver();
            int failed_flag = Rtnmain.factoryUpdateProfile();
            if (failed_flag == 0) {
                Elog.e(Rtnmain.TAG, "The Profile update succeed");
                int unused2 = Rtnmain.sCurrentUpdate = 1;
                if (!Rtnmain.sIsBipSessionStarted) {
                    Elog.e(Rtnmain.TAG, "Waiting for BIP session start.");
                    return;
                } else if (!Rtnmain.this.executePollingRequestForDeviceUpdate()) {
                    Elog.d(Rtnmain.TAG, "Profile update waiting for polling part completion");
                    return;
                }
            } else {
                String access$000 = Rtnmain.TAG;
                Elog.e(access$000, "The Profile update failed at flag: " + failed_flag);
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.profile_update_failed), 1).show();
            }
            if (Rtnmain.progressDialog != null) {
                Rtnmain.progressDialog.dismiss();
                ProgressDialog unused3 = Rtnmain.progressDialog = null;
                int unused4 = Rtnmain.sCurrentUpdate = 0;
                boolean unused5 = Rtnmain.sIsBipSessionStarted = false;
                Rtnmain.this.unregisterBipReceiver();
            }
        }
    }

    private class handle_prl_update implements View.OnClickListener {
        private handle_prl_update() {
        }

        public void onClick(View view) {
            Elog.d(Rtnmain.TAG, "handle_prl_update");
            ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
            Rtnmain.progressDialog.setIndeterminate(true);
            Rtnmain.progressDialog.setCancelable(false);
            Rtnmain.progressDialog.setTitle(Rtnmain.this.getString(R.string.prl));
            Rtnmain.progressDialog.setMessage(Rtnmain.this.getString(R.string.prl_updating_device));
            Rtnmain.progressDialog.show();
            Rtnmain.this.registerBipReceiver();
            int failed_flag = Rtnmain.factoryUpdatePrl();
            if (failed_flag == 0) {
                Elog.d(Rtnmain.TAG, "The PRL update succeed");
                int unused2 = Rtnmain.sCurrentUpdate = 2;
                if (!Rtnmain.sIsBipSessionStarted) {
                    Elog.e(Rtnmain.TAG, "Waiting for BIP session start.");
                    return;
                } else if (!Rtnmain.this.executePollingRequestForDeviceUpdate()) {
                    Elog.d(Rtnmain.TAG, "PRL update waiting for polling part completion");
                    return;
                }
            } else {
                String access$000 = Rtnmain.TAG;
                Elog.e(access$000, "The PRL update failed at flag: " + failed_flag);
                Toast.makeText(Rtnmain.this.getApplicationContext(), Rtnmain.this.getString(R.string.prl_update_failed), 1).show();
            }
            if (Rtnmain.progressDialog != null) {
                Rtnmain.progressDialog.dismiss();
                ProgressDialog unused3 = Rtnmain.progressDialog = null;
                int unused4 = Rtnmain.sCurrentUpdate = 0;
                boolean unused5 = Rtnmain.sIsBipSessionStarted = false;
                Rtnmain.this.unregisterBipReceiver();
            }
        }
    }

    private class handle_scrtn_reset implements View.OnClickListener {
        private handle_scrtn_reset() {
        }

        public void onClick(View view) {
            Elog.d(Rtnmain.TAG, "handle_scrtn_reset");
            AlertDialog.Builder scrtn_reset_cnf = new AlertDialog.Builder(Rtnmain.this);
            scrtn_reset_cnf.setMessage(Rtnmain.this.getString(R.string.Reset_warning));
            scrtn_reset_cnf.setTitle(Rtnmain.this.getString(R.string.scrtn));
            scrtn_reset_cnf.setPositiveButton(Rtnmain.this.getString(17039379), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    ProgressDialog unused = Rtnmain.progressDialog = new ProgressDialog(Rtnmain.this);
                    Rtnmain.progressDialog.setIndeterminate(true);
                    Rtnmain.progressDialog.setCancelable(false);
                    Rtnmain.progressDialog.setTitle(Rtnmain.this.getString(R.string.scrtn));
                    Rtnmain.progressDialog.setMessage(Rtnmain.this.getString(R.string.rtn_resetting_device));
                    Rtnmain.progressDialog.show();
                    int failed_flag = Rtnmain.factoryResetSim();
                    if (failed_flag == 0) {
                        Elog.i(Rtnmain.TAG, "SCRTN The SIM-OTA reset succeed");
                        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT$QCMIGETP=0", ""}, Rtnmain.this.mResponseHander.obtainMessage(2));
                        return;
                    }
                    String access$000 = Rtnmain.TAG;
                    Elog.e(access$000, "SCRTN The SIM-OTA reset failed at flag" + failed_flag);
                }
            });
            scrtn_reset_cnf.setNegativeButton(Rtnmain.this.getString(17039369), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Rtnmain.this.finish();
                }
            });
            scrtn_reset_cnf.create().show();
        }
    }

    /* access modifiers changed from: protected */
    public void Masterclear() {
        Intent intent = new Intent("android.intent.action.FACTORY_RESET");
        intent.setPackage("android");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        getApplicationContext().sendBroadcast(intent, FACTORY_REST_PERMISSION);
        String str = TAG;
        Elog.d(str, "[Masterclear] send BroadCast " + intent.getAction());
    }

    private class handle_reset_click implements View.OnClickListener {
        private handle_reset_click() {
        }

        public void onClick(View view) {
            new Rtnmain_async_task().execute(new String[]{"Rtn_msl"});
        }
    }

    public class BipStateChangeReceiver extends BroadcastReceiver {
        private static final String ACTION_BIP_STATE_CHANGED = "mediatek.intent.action.BIP_STATE_CHANGED";

        public BipStateChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                Elog.e(Rtnmain.TAG, "BipStateChangeReceiver Intent is null");
                return;
            }
            String access$000 = Rtnmain.TAG;
            Elog.i(access$000, "BipStateChangeReceiver called with Intent" + intent.toString());
            if (ACTION_BIP_STATE_CHANGED.equals(intent.getAction())) {
                if (Rtnmain.this.mReceiverTimer != null) {
                    Elog.d(Rtnmain.TAG, "BIP received, cancel timer");
                    Rtnmain.this.mReceiverTimer.cancel();
                    CountDownTimer unused = Rtnmain.this.mReceiverTimer = null;
                }
                String value = intent.getStringExtra("BIP_CMD");
                String access$0002 = Rtnmain.TAG;
                Elog.i(access$0002, "BIP_CMD value:" + value);
                if (value != null && value.contains("START")) {
                    boolean unused2 = Rtnmain.sIsBipSessionStarted = true;
                    if (Rtnmain.sCurrentUpdate == 0) {
                        Elog.e(Rtnmain.TAG, "Polling is not enabled");
                    } else {
                        boolean unused3 = Rtnmain.this.executePollingRequestForDeviceUpdate();
                    }
                    Rtnmain.this.unregisterBipReceiver();
                }
            }
        }
    }

    private void startTimer() {
        if (this.mReceiverTimer != null) {
            Elog.v(TAG, "start timer again, cancel it");
            this.mReceiverTimer.cancel();
            this.mReceiverTimer = null;
        }
        AnonymousClass4 r1 = new CountDownTimer(600000, 1000) {
            public void onTick(long millisUntilFinishied) {
            }

            public void onFinish() {
                Elog.d(Rtnmain.TAG, "CountDownTimer finish");
                CountDownTimer unused = Rtnmain.this.mReceiverTimer = null;
                if (Rtnmain.progressDialog != null) {
                    Rtnmain.progressDialog.dismiss();
                    ProgressDialog unused2 = Rtnmain.progressDialog = null;
                    int unused3 = Rtnmain.sCurrentUpdate = 0;
                    boolean unused4 = Rtnmain.sIsBipSessionStarted = false;
                    Rtnmain.this.unregisterBipReceiver();
                }
                Rtnmain.this.showDialog(Rtnmain.DIALOG_BIP_TIMEOUT);
            }
        };
        this.mReceiverTimer = r1;
        r1.start();
        Elog.v(TAG, "mReceiverTimer started");
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_BIP_TIMEOUT /*601*/:
                return new AlertDialog.Builder(this).setTitle("Warning").setMessage("SIM didn't trigger BIP, please try again!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            case DIALOG_RTN_REBOOT /*602*/:
                return new AlertDialog.Builder(this).setTitle(R.string.rtn).setCancelable(false).setMessage(R.string.rtn_reboot).setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Rtnmain.this.finish();
                    }
                }).create();
            default:
                return super.onCreateDialog(id);
        }
    }
}
