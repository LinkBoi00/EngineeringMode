package com.mediatek.engineermode.bypass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class BypassSettings extends Activity {
    private static final String ACTION_MTK_LOGGER = "com.mediatek.mtklogger.bypass";
    private static final String ACTION_MTK_LOGGER_RESULT = "com.via.bypass.loggerui";
    private static final String ACTION_USB_BYPASS_GETBYPASS = "com.via.bypass.action.getbypass";
    private static final String ACTION_USB_BYPASS_GETBYPASS_RESULT = "com.via.bypass.action.getbypass_result";
    private static final String ACTION_USB_BYPASS_SETBYPASS = "com.via.bypass.action.setbypass";
    private static final String ACTION_USB_BYPASS_SETBYPASS_RESULT = "com.via.bypass.action.setbypass_result";
    private static final String ACTION_USB_BYPASS_SETFUNCTION = "com.via.bypass.action.setfunction";
    private static final String ACTION_USB_BYPASS_SETTETHERFUNCTION = "com.via.bypass.action.settetherfunction";
    private static final int BYPASS_CODE_ETS = 8;
    private static final String CMD_CODE = "cmd_code";
    private static final String CMD_CODE_ALL = "all";
    private static final String CMD_FILE = "cmd_file";
    private static final String CMD_NAME = "cmd_name";
    private static final String CMD_NAME_C2K_STATUS = "get_c2klog_status";
    private static final String CMD_NAME_DISABLE = "disable";
    private static final String CMD_NAME_ENABLE = "enable";
    private static final String CMD_NAME_QUERY = "query";
    private static final String CMD_RESULT = "cmd_result";
    private static final boolean DBG = true;
    private static final int DIALOG_WARNING = 1;
    private static final int OPT_MENU_ITEM_BYPASS_ALL = 1;
    private static final int OPT_MENU_ITEM_CLOSS_BYPASS = 2;
    public static final String PREF_SERV_ENABLE = "service_enable";
    private static final String TAG = "BypassSettings";
    public static final String USBMANAGER_ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    private static final String USBMANAGER_USB_CONNECTED = "connected";
    private static final String USB_FUNCTION_BYPASS = "via_bypass";
    private static final String VALUE_BYPASS_CODE = "com.via.bypass.bypass_code";
    private static final String VALUE_ENABLE_BYPASS = "com.via.bypass.enable_bypass";
    private static final String VALUE_ISSET_BYPASS = "com.via.bypass.isset_bypass";
    private BypassAdapter<String> mBypassAdapter;
    private int mBypassAllCode = 0;
    /* access modifiers changed from: private */
    public int mBypassCode = 0;
    /* access modifiers changed from: private */
    public int[] mBypassCodes;
    /* access modifiers changed from: private */
    public boolean mBypassEnabled = false;
    /* access modifiers changed from: private */
    public String[] mBypassNames;
    private final int mBypassNone = 0;
    private CheckBox mCBBypassServiceEnable;
    /* access modifiers changed from: private */
    public CheckBox mCbRndis;
    private CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.cb_ets_rndis) {
                BypassSettings.this.mLvBypass.setEnabled(isChecked ^ BypassSettings.DBG);
                if (!isChecked) {
                    BypassSettings.this.enableRndis(false);
                } else if (BypassSettings.this.mBypassCode > 0) {
                    BypassSettings.this.enableBypass(false);
                    BypassSettings.this.setBypassMode(0);
                    BypassSettings.this.updateBypassList(0);
                    boolean unused = BypassSettings.this.mIsPreparingForRndis = BypassSettings.DBG;
                } else {
                    BypassSettings.this.enableRndis(Boolean.valueOf(BypassSettings.DBG));
                }
            }
        }
    };
    private String mCmdCode;
    private String mCmdFile;
    private String mCmdName;
    private Dialog mDialog = null;
    /* access modifiers changed from: private */
    public boolean mIsBypassMode = false;
    /* access modifiers changed from: private */
    public boolean mIsPreparingForRndis = false;
    /* access modifiers changed from: private */
    public boolean mIsQueryingBypass = false;
    /* access modifiers changed from: private */
    public boolean mIsQueryingMtkLogger = false;
    /* access modifiers changed from: private */
    public boolean mIsSettingBypass = false;
    /* access modifiers changed from: private */
    public boolean mIsSettingRndis = false;
    /* access modifiers changed from: private */
    public boolean mIsSettingUsb = false;
    /* access modifiers changed from: private */
    public boolean mLaunchByCommand;
    private AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            CheckedTextView checkedTextView = (CheckedTextView) view;
            int bypassSet = BypassSettings.this.mBypassCode;
            if ((BypassSettings.this.mBypassCode & BypassSettings.this.mBypassCodes[position]) != 0) {
                int bypassSet2 = bypassSet ^ BypassSettings.this.mBypassCodes[position];
                if (BypassSettings.this.mIsBypassMode && bypassSet2 == 0) {
                    BypassSettings.this.enableBypass(false);
                }
                BypassSettings.this.setBypassMode(bypassSet2);
            } else if (BypassSettings.this.mBypassCodes[position] == 8) {
                int unused = BypassSettings.this.mPendingBypassCode = bypassSet | 8;
                boolean unused2 = BypassSettings.this.mIsSettingRndis = false;
                BypassSettings.this.queryMtkLogger();
                BypassSettings bypassSettings = BypassSettings.this;
                bypassSettings.updateBypassList(bypassSettings.mBypassCode);
            } else {
                int bypassSet3 = bypassSet | BypassSettings.this.mBypassCodes[position];
                if (!BypassSettings.this.mIsBypassMode) {
                    BypassSettings.this.enableBypass(Boolean.valueOf(BypassSettings.DBG));
                }
                BypassSettings.this.setBypassMode(bypassSet3);
            }
        }
    };
    /* access modifiers changed from: private */
    public Object mLock = new Object();
    /* access modifiers changed from: private */
    public ListView mLvBypass;
    /* access modifiers changed from: private */
    public int mPendingBypassCode = 0;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x01de, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                com.mediatek.engineermode.bypass.BypassSettings r0 = com.mediatek.engineermode.bypass.BypassSettings.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                java.lang.String r1 = "BypassSettings"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01df }
                r2.<init>()     // Catch:{ all -> 0x01df }
                java.lang.String r3 = "onReceive = "
                r2.append(r3)     // Catch:{ all -> 0x01df }
                java.lang.String r3 = r9.getAction()     // Catch:{ all -> 0x01df }
                r2.append(r3)     // Catch:{ all -> 0x01df }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.Elog.i(r1, r2)     // Catch:{ all -> 0x01df }
                java.lang.String r1 = r9.getAction()     // Catch:{ all -> 0x01df }
                java.lang.String r2 = "android.hardware.usb.action.USB_STATE"
                boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x01df }
                r2 = 0
                if (r1 == 0) goto L_0x0082
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean unused = r1.mIsSettingUsb = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                android.os.Bundle r2 = r9.getExtras()     // Catch:{ all -> 0x01df }
                java.lang.String r3 = "connected"
                boolean r2 = r2.getBoolean(r3)     // Catch:{ all -> 0x01df }
                boolean unused = r1.mUsbConnected = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                android.os.Bundle r2 = r9.getExtras()     // Catch:{ all -> 0x01df }
                java.lang.String r3 = "via_bypass"
                boolean r2 = r2.getBoolean(r3)     // Catch:{ all -> 0x01df }
                boolean unused = r1.mIsBypassMode = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                r1.queryBypassMode()     // Catch:{ all -> 0x01df }
                java.lang.String r1 = "BypassSettings"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01df }
                r2.<init>()     // Catch:{ all -> 0x01df }
                java.lang.String r3 = "UsbConnected = "
                r2.append(r3)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r3 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r3 = r3.mUsbConnected     // Catch:{ all -> 0x01df }
                r2.append(r3)     // Catch:{ all -> 0x01df }
                java.lang.String r3 = ", mIsBypassMode = "
                r2.append(r3)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r3 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r3 = r3.mIsBypassMode     // Catch:{ all -> 0x01df }
                r2.append(r3)     // Catch:{ all -> 0x01df }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.Elog.w(r1, r2)     // Catch:{ all -> 0x01df }
                goto L_0x01dd
            L_0x0082:
                java.lang.String r1 = r9.getAction()     // Catch:{ all -> 0x01df }
                java.lang.String r3 = "com.via.bypass.action.setbypass_result"
                boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x01df }
                r3 = 1
                if (r1 == 0) goto L_0x00f3
                android.os.Bundle r1 = r9.getExtras()     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "com.via.bypass.isset_bypass"
                boolean r1 = r1.getBoolean(r4)     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "BypassSettings"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01df }
                r5.<init>()     // Catch:{ all -> 0x01df }
                java.lang.String r6 = "Set bypass mode is "
                r5.append(r6)     // Catch:{ all -> 0x01df }
                r5.append(r1)     // Catch:{ all -> 0x01df }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.Elog.w(r4, r5)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean unused = r4.mIsSettingBypass = r2     // Catch:{ all -> 0x01df }
                if (r1 == 0) goto L_0x00bd
                r4 = 2131363294(0x7f0a05de, float:1.8346393E38)
                com.mediatek.engineermode.EmUtils.showToast((int) r4, (int) r2)     // Catch:{ all -> 0x01df }
                goto L_0x00c3
            L_0x00bd:
                r4 = 2131362513(0x7f0a02d1, float:1.8344809E38)
                com.mediatek.engineermode.EmUtils.showToast((int) r4, (int) r2)     // Catch:{ all -> 0x01df }
            L_0x00c3:
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                java.lang.String r5 = "com.via.bypass.bypass_code"
                int r6 = r4.mBypassCode     // Catch:{ all -> 0x01df }
                int r5 = r9.getIntExtra(r5, r6)     // Catch:{ all -> 0x01df }
                int unused = r4.mBypassCode = r5     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                int r5 = r4.mBypassCode     // Catch:{ all -> 0x01df }
                r4.updateBypassList(r5)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r4 = r4.mIsPreparingForRndis     // Catch:{ all -> 0x01df }
                if (r4 == 0) goto L_0x00f1
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean unused = r4.mIsPreparingForRndis = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r2 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x01df }
                r2.enableRndis(r3)     // Catch:{ all -> 0x01df }
            L_0x00f1:
                goto L_0x01dd
            L_0x00f3:
                java.lang.String r1 = r9.getAction()     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "com.via.bypass.action.getbypass_result"
                boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x01df }
                if (r1 == 0) goto L_0x012b
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean unused = r1.mIsQueryingBypass = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                java.lang.String r2 = "com.via.bypass.bypass_code"
                int r3 = r1.mBypassCode     // Catch:{ all -> 0x01df }
                int r2 = r9.getIntExtra(r2, r3)     // Catch:{ all -> 0x01df }
                int unused = r1.mBypassCode = r2     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                int r2 = r1.mBypassCode     // Catch:{ all -> 0x01df }
                r1.updateBypassList(r2)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r1 = r1.mLaunchByCommand     // Catch:{ all -> 0x01df }
                if (r1 == 0) goto L_0x01dd
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                r1.doCommand()     // Catch:{ all -> 0x01df }
                goto L_0x01dd
            L_0x012b:
                java.lang.String r1 = r9.getAction()     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "com.via.bypass.loggerui"
                boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x01df }
                if (r1 == 0) goto L_0x01dd
                java.lang.String r1 = "BypassSettings"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01df }
                r4.<init>()     // Catch:{ all -> 0x01df }
                java.lang.String r5 = "mIsQueryingMtkLogger is "
                r4.append(r5)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r5 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r5 = r5.mIsQueryingMtkLogger     // Catch:{ all -> 0x01df }
                r4.append(r5)     // Catch:{ all -> 0x01df }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.Elog.w(r1, r4)     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r1 = r1.mIsQueryingMtkLogger     // Catch:{ all -> 0x01df }
                if (r1 != 0) goto L_0x015d
                monitor-exit(r0)     // Catch:{ all -> 0x01df }
                return
            L_0x015d:
                com.mediatek.engineermode.bypass.BypassSettings r1 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean unused = r1.mIsQueryingMtkLogger = r2     // Catch:{ all -> 0x01df }
                java.lang.String r1 = "cmd_name"
                java.lang.String r1 = r9.getStringExtra(r1)     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "cmd_result"
                int r2 = r9.getIntExtra(r4, r2)     // Catch:{ all -> 0x01df }
                java.lang.String r4 = "BypassSettings"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01df }
                r5.<init>()     // Catch:{ all -> 0x01df }
                java.lang.String r6 = "cmd_nameis "
                r5.append(r6)     // Catch:{ all -> 0x01df }
                r5.append(r1)     // Catch:{ all -> 0x01df }
                java.lang.String r6 = ", "
                r5.append(r6)     // Catch:{ all -> 0x01df }
                java.lang.String r6 = "cmd_result"
                r5.append(r6)     // Catch:{ all -> 0x01df }
                java.lang.String r6 = " is "
                r5.append(r6)     // Catch:{ all -> 0x01df }
                r5.append(r2)     // Catch:{ all -> 0x01df }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x01df }
                com.mediatek.engineermode.Elog.w(r4, r5)     // Catch:{ all -> 0x01df }
                if (r1 == 0) goto L_0x01d4
                java.lang.String r4 = "get_c2klog_status"
                boolean r4 = r1.equals(r4)     // Catch:{ all -> 0x01df }
                if (r4 == 0) goto L_0x01d4
                if (r2 != r3) goto L_0x01a8
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                r4.showDialog(r3)     // Catch:{ all -> 0x01df }
                goto L_0x01d4
            L_0x01a8:
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r4 = r4.mIsBypassMode     // Catch:{ all -> 0x01df }
                if (r4 != 0) goto L_0x01cb
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                boolean r4 = r4.mIsSettingRndis     // Catch:{ all -> 0x01df }
                if (r4 == 0) goto L_0x01c2
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x01df }
                r4.enableBypassTether(r3)     // Catch:{ all -> 0x01df }
                goto L_0x01cb
            L_0x01c2:
                com.mediatek.engineermode.bypass.BypassSettings r4 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x01df }
                r4.enableBypass(r3)     // Catch:{ all -> 0x01df }
            L_0x01cb:
                com.mediatek.engineermode.bypass.BypassSettings r3 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                int r4 = r3.mPendingBypassCode     // Catch:{ all -> 0x01df }
                r3.setBypassMode(r4)     // Catch:{ all -> 0x01df }
            L_0x01d4:
                com.mediatek.engineermode.bypass.BypassSettings r3 = com.mediatek.engineermode.bypass.BypassSettings.this     // Catch:{ all -> 0x01df }
                int r4 = r3.mBypassCode     // Catch:{ all -> 0x01df }
                r3.updateBypassList(r4)     // Catch:{ all -> 0x01df }
            L_0x01dd:
                monitor-exit(r0)     // Catch:{ all -> 0x01df }
                return
            L_0x01df:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x01df }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.bypass.BypassSettings.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private View.OnClickListener mServEnablePressed = new View.OnClickListener() {
        public void onClick(View v) {
            Elog.w(BypassSettings.TAG, "isChecked " + ((CheckBox) v).isChecked());
            BypassSettings.this.savePrefs();
            EmUtils.showToast("reboot to make change available", 0);
        }
    };
    /* access modifiers changed from: private */
    public boolean mUsbConnected = false;

    /* access modifiers changed from: private */
    public void savePrefs() {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_SERV_ENABLE, 0).edit();
        editor.putBoolean(PREF_SERV_ENABLE, this.mCBBypassServiceEnable.isChecked());
        editor.commit();
    }

    private void restorePrefsAndUpdateCheckBox() {
        boolean prefServEnable = getSharedPreferences(PREF_SERV_ENABLE, 0).getBoolean(PREF_SERV_ENABLE, false);
        Elog.w(TAG, "prefServEnable : " + prefServEnable);
        if (prefServEnable) {
            this.mCBBypassServiceEnable.setChecked(DBG);
        } else {
            this.mCBBypassServiceEnable.setChecked(false);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bypass_settings);
        this.mBypassNames = getResources().getStringArray(R.array.bypass_names);
        this.mBypassCodes = getResources().getIntArray(R.array.bypass_codes);
        int i = 0;
        while (true) {
            int[] iArr = this.mBypassCodes;
            if (i >= iArr.length) {
                break;
            }
            this.mBypassAllCode += iArr[i];
            i++;
        }
        this.mLvBypass = (ListView) findViewById(R.id.lv_bypass);
        BypassAdapter<String> bypassAdapter = new BypassAdapter<>(this);
        this.mBypassAdapter = bypassAdapter;
        this.mLvBypass.setAdapter(bypassAdapter);
        boolean z = false;
        this.mLvBypass.setItemsCanFocus(false);
        this.mLvBypass.setChoiceMode(2);
        this.mLvBypass.setOnItemClickListener(this.mListener);
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_ets_rndis);
        this.mCbRndis = checkBox;
        checkBox.setOnCheckedChangeListener(this.mCheckedChangeListener);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.bypass_service_eanble);
        this.mCBBypassServiceEnable = checkBox2;
        checkBox2.setOnClickListener(this.mServEnablePressed);
        restorePrefsAndUpdateCheckBox();
        this.mCbRndis.setVisibility(8);
        this.mCmdName = getIntent().getStringExtra(CMD_NAME);
        this.mCmdCode = getIntent().getStringExtra(CMD_CODE);
        this.mCmdFile = getIntent().getStringExtra(CMD_FILE);
        String str = this.mCmdName;
        if (str != null && str.length() > 0) {
            z = DBG;
        }
        this.mLaunchByCommand = z;
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                AlertDialog create = new AlertDialog.Builder(this).setTitle("Warning").setMessage("MD logger is running, enable ETS may cause Elog lost. Please stop MD logger first.\n\nEnable ETS anyway? (Not recommended)").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean access$1400 = BypassSettings.this.mIsSettingRndis;
                        Boolean valueOf = Boolean.valueOf(BypassSettings.DBG);
                        if (access$1400) {
                            BypassSettings.this.enableBypassTether(valueOf);
                        } else {
                            BypassSettings.this.enableBypass(valueOf);
                        }
                        BypassSettings bypassSettings = BypassSettings.this;
                        bypassSettings.setBypassMode(bypassSettings.mPendingBypassCode);
                    }
                }).setNegativeButton("No", (DialogInterface.OnClickListener) null).create();
                this.mDialog = create;
                return create;
            default:
                return super.onCreateDialog(dialogId);
        }
    }

    private void setBypassStatus(int bypassmode) {
        Elog.i(TAG, "setEnabled " + this.mBypassEnabled);
        this.mLvBypass.setEnabled(this.mBypassEnabled);
        for (int i = 0; i < this.mLvBypass.getCount(); i++) {
            this.mLvBypass.setItemChecked(i, (this.mBypassCodes[i] & bypassmode) != 0 ? DBG : false);
        }
    }

    /* access modifiers changed from: private */
    public void queryBypassMode() {
        Elog.i(TAG, "queryBypassMode()");
        this.mIsQueryingBypass = DBG;
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_USB_BYPASS_GETBYPASS));
    }

    /* access modifiers changed from: private */
    public void queryMtkLogger() {
        Elog.i(TAG, "queryBypassMode()");
        this.mIsQueryingMtkLogger = DBG;
        Intent intent = new Intent(ACTION_MTK_LOGGER);
        intent.putExtra(CMD_NAME, CMD_NAME_C2K_STATUS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        EmUtils.showToast("Checking MTK logger status...", 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Bypass all");
        menu.add(0, 2, 0, "Close bypass");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        Elog.i(TAG, "onPrepareOptionsMenu() Enable = " + this.mBypassEnabled);
        boolean z = DBG;
        menu.findItem(1).setEnabled(this.mBypassEnabled && !this.mCbRndis.isChecked());
        MenuItem findItem = menu.findItem(2);
        if (!this.mBypassEnabled || this.mCbRndis.isChecked()) {
            z = false;
        }
        findItem.setEnabled(z);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int i;
        int i2;
        Elog.i(TAG, "onOptionsItemSelected");
        if (item.getItemId() != 1 || (i = this.mBypassCode) == (i2 = this.mBypassAllCode)) {
            if (item.getItemId() == 2 && this.mIsBypassMode) {
                enableBypass(false);
                setBypassMode(0);
            }
        } else if ((i & 8) == 0) {
            this.mPendingBypassCode = i2;
            this.mIsSettingRndis = false;
            queryMtkLogger();
            updateBypassList(this.mBypassCode);
            return super.onOptionsItemSelected(item);
        } else {
            if (!this.mIsBypassMode) {
                enableBypass(Boolean.valueOf(DBG));
            }
            setBypassMode(this.mBypassAllCode);
        }
        return super.onOptionsItemSelected(item);
    }

    /* access modifiers changed from: private */
    public void updateBypassList(int bypassmode) {
        Elog.i(TAG, "updateBypassList() mUsbConnected = " + this.mUsbConnected + ", mIsSettingBypass = " + this.mIsSettingBypass + ", mIsSettingUsb = " + this.mIsSettingUsb + ", mIsQueryingBypass = " + this.mIsQueryingBypass + ", mIsQueryingMtkLogger = " + this.mIsQueryingMtkLogger);
        this.mBypassEnabled = (!this.mUsbConnected || this.mIsSettingBypass || this.mIsSettingUsb || this.mIsQueryingBypass || this.mIsQueryingMtkLogger) ? false : DBG;
        Elog.i(TAG, "updateBypassList() bypassmode = " + bypassmode + ", mBypassEnabled = " + this.mBypassEnabled);
        setBypassStatus(bypassmode);
        this.mBypassAdapter.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        Elog.w(TAG, "onResume()");
        queryBypassMode();
        updateBypassList(this.mBypassCode);
        restorePrefsAndUpdateCheckBox();
        registerReceiver(this.mReceiver, new IntentFilter(USBMANAGER_ACTION_USB_STATE));
        IntentFilter bypass_intentFilter = new IntentFilter(ACTION_USB_BYPASS_SETBYPASS_RESULT);
        bypass_intentFilter.addAction(ACTION_USB_BYPASS_GETBYPASS_RESULT);
        bypass_intentFilter.addAction(ACTION_MTK_LOGGER_RESULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, bypass_intentFilter);
    }

    public void onPause() {
        super.onPause();
        Elog.i(TAG, "onPause()");
        if (!this.mLaunchByCommand) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        }
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mLaunchByCommand) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        }
    }

    /* access modifiers changed from: private */
    public void enableBypass(Boolean enable) {
        Elog.i(TAG, "enableBypass(" + enable + ")");
        this.mIsSettingUsb = DBG;
        Intent intent = new Intent(ACTION_USB_BYPASS_SETFUNCTION);
        intent.putExtra(VALUE_ENABLE_BYPASS, enable);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void enableBypassTether(Boolean enable) {
        Elog.i(TAG, "enableBypassTether(" + enable + ")");
        this.mIsSettingUsb = DBG;
        Intent intent = new Intent(ACTION_USB_BYPASS_SETTETHERFUNCTION);
        intent.putExtra(VALUE_ENABLE_BYPASS, enable);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void enableRndis(Boolean enable) {
        Elog.i(TAG, "enableRndis(" + enable + ")");
        if (!enable.booleanValue()) {
            enableBypassTether(false);
            setBypassMode(0);
            updateBypassList(0);
        } else if (!this.mLaunchByCommand) {
            this.mPendingBypassCode = 8;
            this.mIsSettingRndis = DBG;
            queryMtkLogger();
            updateBypassList(this.mBypassCode);
        } else {
            enableBypassTether(Boolean.valueOf(DBG));
            setBypassMode(8);
            updateBypassList(8);
        }
    }

    /* access modifiers changed from: private */
    public void setBypassMode(int bypassmode) {
        Elog.i(TAG, "setBypassMode(" + bypassmode + ")");
        this.mIsSettingBypass = DBG;
        Intent intent = new Intent(ACTION_USB_BYPASS_SETBYPASS);
        intent.putExtra(VALUE_BYPASS_CODE, bypassmode);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00a0, code lost:
        if (r1.equals(CMD_NAME_DISABLE) != false) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x016a, code lost:
        if (r12.equals(CMD_NAME_DISABLE) != false) goto L_0x017e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doCommand() {
        /*
            r15 = this;
            java.lang.String r0 = r15.mCmdName
            if (r0 == 0) goto L_0x0228
            java.lang.String r0 = r15.mCmdCode
            if (r0 == 0) goto L_0x0228
            java.lang.String r0 = r15.mCmdFile
            if (r0 != 0) goto L_0x000e
            goto L_0x0228
        L_0x000e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "mCmdName is "
            r0.append(r1)
            java.lang.String r1 = r15.mCmdName
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "BypassSettings"
            com.mediatek.engineermode.Elog.w(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "mCmdCode is "
            r0.append(r2)
            java.lang.String r2 = r15.mCmdCode
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.mediatek.engineermode.Elog.w(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "mCmdFile is "
            r0.append(r2)
            java.lang.String r2 = r15.mCmdFile
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.mediatek.engineermode.Elog.w(r1, r0)
            r0 = -1
            java.lang.String r2 = "sys.usb.config"
            java.lang.String r2 = android.os.SystemProperties.get(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "sys.usb.config: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            com.mediatek.engineermode.Elog.i(r1, r3)
            java.lang.String r3 = "rndis"
            boolean r4 = r2.contains(r3)
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0081
            java.lang.String r4 = "via_bypass"
            boolean r4 = r2.contains(r4)
            if (r4 == 0) goto L_0x0081
            r4 = r5
            goto L_0x0082
        L_0x0081:
            r4 = r6
        L_0x0082:
            java.lang.String r7 = r15.mCmdCode
            boolean r3 = r7.equals(r3)
            r7 = 2
            java.lang.String r8 = "disable"
            java.lang.String r9 = "query"
            java.lang.String r10 = "enable"
            r11 = -1
            if (r3 == 0) goto L_0x00f3
            java.lang.String r1 = r15.mCmdName
            int r3 = r1.hashCode()
            switch(r3) {
                case -1298848381: goto L_0x00ab;
                case 107944136: goto L_0x00a3;
                case 1671308008: goto L_0x009c;
                default: goto L_0x009b;
            }
        L_0x009b:
            goto L_0x00b3
        L_0x009c:
            boolean r1 = r1.equals(r8)
            if (r1 == 0) goto L_0x009b
            goto L_0x00b4
        L_0x00a3:
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x009b
            r7 = r6
            goto L_0x00b4
        L_0x00ab:
            boolean r1 = r1.equals(r10)
            if (r1 == 0) goto L_0x009b
            r7 = r5
            goto L_0x00b4
        L_0x00b3:
            r7 = r11
        L_0x00b4:
            switch(r7) {
                case 0: goto L_0x00ec;
                case 1: goto L_0x00c8;
                case 2: goto L_0x00b8;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            goto L_0x00f1
        L_0x00b8:
            if (r4 == 0) goto L_0x00c6
            android.widget.ListView r1 = r15.mLvBypass
            r1.setEnabled(r5)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r6)
            r15.enableRndis(r1)
        L_0x00c6:
            r0 = 0
            goto L_0x00f1
        L_0x00c8:
            if (r4 != 0) goto L_0x00ea
            android.widget.ListView r1 = r15.mLvBypass
            r1.setEnabled(r6)
            int r1 = r15.mBypassCode
            if (r1 <= 0) goto L_0x00e3
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r6)
            r15.enableBypass(r1)
            r15.setBypassMode(r6)
            r15.updateBypassList(r6)
            r15.mIsPreparingForRndis = r5
            goto L_0x00ea
        L_0x00e3:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r5)
            r15.enableRndis(r1)
        L_0x00ea:
            r0 = 0
            goto L_0x00f1
        L_0x00ec:
            if (r4 == 0) goto L_0x00ef
            r6 = r5
        L_0x00ef:
            r0 = r6
        L_0x00f1:
            goto L_0x01c7
        L_0x00f3:
            r3 = -1
            java.lang.String r12 = r15.mCmdCode
            java.lang.String r13 = "all"
            boolean r12 = r12.equals(r13)
            if (r12 == 0) goto L_0x0101
            int r3 = r15.mBypassAllCode
            goto L_0x0118
        L_0x0101:
            r12 = 0
        L_0x0102:
            java.lang.String[] r13 = r15.mBypassNames
            int r14 = r13.length
            if (r12 >= r14) goto L_0x0118
            java.lang.String r14 = r15.mCmdCode
            r13 = r13[r12]
            boolean r13 = r14.equals(r13)
            if (r13 == 0) goto L_0x0115
            int[] r13 = r15.mBypassCodes
            r3 = r13[r12]
        L_0x0115:
            int r12 = r12 + 1
            goto L_0x0102
        L_0x0118:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "code is "
            r12.append(r13)
            r12.append(r3)
            java.lang.String r12 = r12.toString()
            com.mediatek.engineermode.Elog.w(r1, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "mBypassCode is "
            r12.append(r13)
            int r13 = r15.mBypassCode
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.mediatek.engineermode.Elog.w(r1, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "mBypassAllCode is "
            r12.append(r13)
            int r13 = r15.mBypassAllCode
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.mediatek.engineermode.Elog.w(r1, r12)
            if (r3 == r11) goto L_0x01c7
            int r1 = r15.mBypassCode
            java.lang.String r12 = r15.mCmdName
            int r13 = r12.hashCode()
            switch(r13) {
                case -1298848381: goto L_0x0175;
                case 107944136: goto L_0x016d;
                case 1671308008: goto L_0x0166;
                default: goto L_0x0165;
            }
        L_0x0165:
            goto L_0x017d
        L_0x0166:
            boolean r8 = r12.equals(r8)
            if (r8 == 0) goto L_0x0165
            goto L_0x017e
        L_0x016d:
            boolean r7 = r12.equals(r9)
            if (r7 == 0) goto L_0x0165
            r7 = r6
            goto L_0x017e
        L_0x0175:
            boolean r7 = r12.equals(r10)
            if (r7 == 0) goto L_0x0165
            r7 = r5
            goto L_0x017e
        L_0x017d:
            r7 = r11
        L_0x017e:
            switch(r7) {
                case 0: goto L_0x01b9;
                case 1: goto L_0x019f;
                case 2: goto L_0x0182;
                default: goto L_0x0181;
            }
        L_0x0181:
            goto L_0x01c7
        L_0x0182:
            if (r4 == 0) goto L_0x0186
            r0 = -1
            goto L_0x01c7
        L_0x0186:
            int r7 = r15.mBypassCode
            r7 = r7 & r3
            if (r7 == 0) goto L_0x019d
            int r7 = ~r3
            r1 = r1 & r7
            boolean r7 = r15.mIsBypassMode
            if (r7 == 0) goto L_0x019a
            if (r1 != 0) goto L_0x019a
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)
            r15.enableBypass(r6)
        L_0x019a:
            r15.setBypassMode(r1)
        L_0x019d:
            r0 = 0
            goto L_0x01c7
        L_0x019f:
            if (r4 == 0) goto L_0x01a3
            r0 = -1
            goto L_0x01c7
        L_0x01a3:
            int r6 = r15.mBypassCode
            r6 = r6 & r3
            if (r6 == r3) goto L_0x01b7
            r1 = r1 | r3
            boolean r6 = r15.mIsBypassMode
            if (r6 != 0) goto L_0x01b4
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r5)
            r15.enableBypass(r6)
        L_0x01b4:
            r15.setBypassMode(r1)
        L_0x01b7:
            r0 = 0
            goto L_0x01c7
        L_0x01b9:
            int r7 = r15.mBypassAllCode
            if (r3 != r7) goto L_0x01c0
            int r0 = r15.mBypassCode
            goto L_0x01c7
        L_0x01c0:
            int r7 = r15.mBypassCode
            r7 = r7 & r3
            if (r7 <= 0) goto L_0x01c6
            r6 = r5
        L_0x01c6:
            r0 = r6
        L_0x01c7:
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = r15.mCmdFile
            r3.append(r6)
            java.lang.String r6 = ".tmp"
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            r3 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x01fd }
            r6.<init>(r1, r5)     // Catch:{ IOException -> 0x01fd }
            r3 = r6
            java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x01fd }
            byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x01fd }
            r3.write(r5)     // Catch:{ IOException -> 0x01fd }
            r3.close()     // Catch:{ IOException -> 0x01f6 }
        L_0x01f5:
            goto L_0x020b
        L_0x01f6:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x01f5
        L_0x01fb:
            r5 = move-exception
            goto L_0x021d
        L_0x01fd:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x01fb }
            r1.delete()     // Catch:{ all -> 0x01fb }
            if (r3 == 0) goto L_0x020b
            r3.close()     // Catch:{ IOException -> 0x01f6 }
            goto L_0x01f5
        L_0x020b:
            java.io.File r5 = new java.io.File
            java.lang.String r6 = r15.mCmdFile
            r5.<init>(r6)
            r1.renameTo(r5)
            r5 = 0
            r15.mCmdName = r5
            r15.mCmdCode = r5
            r15.mCmdFile = r5
            return
        L_0x021d:
            if (r3 == 0) goto L_0x0227
            r3.close()     // Catch:{ IOException -> 0x0223 }
            goto L_0x0227
        L_0x0223:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0227:
            throw r5
        L_0x0228:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.bypass.BypassSettings.doCommand():void");
    }

    public int enableBypassModeWait(int mask) {
        int i;
        synchronized (this.mLock) {
            queryBypassModeWait(mask);
            int newMode = this.mBypassCode | mask;
            if (!this.mIsBypassMode) {
                enableBypass(Boolean.valueOf(DBG));
            }
            setBypassMode(newMode);
            waitForFinished();
            i = (this.mBypassCode & mask) != 0 ? 0 : -1;
        }
        return i;
    }

    public int disableBypassModeWait(int mask) {
        int i;
        synchronized (this.mLock) {
            queryBypassModeWait(mask);
            int newMode = this.mBypassCode & (~mask);
            i = 0;
            if (newMode == 0) {
                enableBypass(false);
            }
            setBypassMode(newMode);
            waitForFinished();
            if ((this.mBypassCode & mask) != 0) {
                i = -1;
            }
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0040, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int queryBypassModeWait(int r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            android.content.IntentFilter r1 = new android.content.IntentFilter     // Catch:{ all -> 0x0043 }
            java.lang.String r2 = "android.hardware.usb.action.USB_STATE"
            r1.<init>(r2)     // Catch:{ all -> 0x0043 }
            android.content.BroadcastReceiver r2 = r6.mReceiver     // Catch:{ all -> 0x0043 }
            r6.registerReceiver(r2, r1)     // Catch:{ all -> 0x0043 }
            android.content.IntentFilter r2 = new android.content.IntentFilter     // Catch:{ all -> 0x0043 }
            java.lang.String r3 = "com.via.bypass.action.setbypass_result"
            r2.<init>(r3)     // Catch:{ all -> 0x0043 }
            java.lang.String r3 = "com.via.bypass.action.getbypass_result"
            r2.addAction(r3)     // Catch:{ all -> 0x0043 }
            android.support.v4.content.LocalBroadcastManager r3 = android.support.v4.content.LocalBroadcastManager.getInstance(r6)     // Catch:{ all -> 0x0043 }
            android.content.BroadcastReceiver r4 = r6.mReceiver     // Catch:{ all -> 0x0043 }
            r3.registerReceiver(r4, r2)     // Catch:{ all -> 0x0043 }
            java.lang.String r3 = "BypassSettings"
            java.lang.String r4 = "registerReceiver"
            com.mediatek.engineermode.Elog.i(r3, r4)     // Catch:{ all -> 0x0043 }
            r6.queryBypassMode()     // Catch:{ all -> 0x0043 }
            r6.waitForFinished()     // Catch:{ all -> 0x0043 }
            int r3 = r6.mBypassCode     // Catch:{ all -> 0x0043 }
            r3 = r3 & r7
            r4 = r7 & 15
            r5 = 15
            if (r4 == r5) goto L_0x0041
            if (r3 <= 0) goto L_0x003e
            r4 = 1
            goto L_0x003f
        L_0x003e:
            r4 = 0
        L_0x003f:
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return r4
        L_0x0041:
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return r3
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.bypass.BypassSettings.queryBypassModeWait(int):int");
    }

    private void waitForFinished() {
        while (true) {
            if (this.mIsSettingBypass || this.mIsSettingUsb || this.mIsQueryingBypass) {
                Elog.i(TAG, "wait... " + this.mIsSettingBypass + " " + this.mIsSettingUsb + " " + this.mIsQueryingBypass);
                try {
                    this.mLock.wait(100);
                } catch (InterruptedException e) {
                }
            } else {
                return;
            }
        }
    }

    private class BypassAdapter<T> extends BaseAdapter {
        public BypassAdapter(Context context) {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            boolean z = false;
            CheckedTextView ctview = (CheckedTextView) ((LayoutInflater) BypassSettings.this.getSystemService("layout_inflater")).inflate(17367056, parent, false);
            ctview.setText(BypassSettings.this.mBypassNames[position]);
            if (BypassSettings.this.mBypassEnabled && !BypassSettings.this.mCbRndis.isChecked()) {
                z = BypassSettings.DBG;
            }
            ctview.setEnabled(z);
            return ctview;
        }

        public int getCount() {
            return BypassSettings.this.mBypassNames.length;
        }

        public Object getItem(int arg0) {
            return BypassSettings.this.mBypassNames[arg0];
        }

        public long getItemId(int arg0) {
            return (long) BypassSettings.this.mBypassCodes[arg0];
        }
    }
}
