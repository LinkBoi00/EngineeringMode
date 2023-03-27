package com.mediatek.engineermode.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public abstract class WiFiTestActivity extends Activity {
    static final int ATPARAM_INDEX_ANTENNA = 5;
    static final int ATPARAM_INDEX_ANTENNA_SWAP = 153;
    static final int ATPARAM_INDEX_BANDWIDTH = 15;
    static final long ATPARAM_INDEX_CHANNEL0 = 18;
    static final long ATPARAM_INDEX_CHANNEL1 = 65554;
    static final int ATPARAM_INDEX_CHANNEL_BANDWIDTH = 71;
    static final int ATPARAM_INDEX_COMMAND = 1;
    static final int ATPARAM_INDEX_CWMODE = 65;
    static final int ATPARAM_INDEX_DATA_BANDWIDTH = 72;
    static final int ATPARAM_INDEX_GI = 16;
    static final int ATPARAM_INDEX_NSS = 114;
    static final int ATPARAM_INDEX_PACKCONTENT = 12;
    static final int ATPARAM_INDEX_PACKCOUNT = 7;
    static final int ATPARAM_INDEX_PACKINTERVAL = 8;
    static final int ATPARAM_INDEX_PACKLENGTH = 6;
    static final int ATPARAM_INDEX_POWER = 2;
    static final int ATPARAM_INDEX_POWER_UNIT = 31;
    static final int ATPARAM_INDEX_PREAMBLE = 4;
    static final int ATPARAM_INDEX_PRIMARY_SETTING = 73;
    static final int ATPARAM_INDEX_QOS_QUEUE = 14;
    static final int ATPARAM_INDEX_RATE = 3;
    static final int ATPARAM_INDEX_RETRY_LIMIT = 13;
    static final int ATPARAM_INDEX_RX = 106;
    static final int ATPARAM_INDEX_SET_DBDC_BAND_IDX = 104;
    static final int ATPARAM_INDEX_SET_DBDC_ENABLE = 110;
    static final int ATPARAM_INDEX_TEMP_COMPENSATION = 9;
    static final int ATPARAM_INDEX_TRANSMITCOUNT_BAND0 = 32;
    static final int ATPARAM_INDEX_TRANSMITCOUNT_BAND1 = 288;
    static final int ATPARAM_INDEX_TXOP_LIMIT = 10;
    static final int ATPARAM_INDEX_TX_POWER = 136;
    static final int ATPARAM_INDEX_WF0 = 113;
    private static final int DIALOG_STOP_TEST = 4;
    private static final int DIALOG_WIFI_FAIL = 2;
    private static final int DIALOG_WIFI_MODE_ERROR = 3;
    private static final String TAG = "WifiTestActivity";
    private static final long TIME_1000 = 1000;
    private WifiManager mWifiManager;
    private BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int currentState = intent.getIntExtra("wifi_state", 4);
            Elog.i(WiFiTestActivity.TAG, "Wifi state:" + currentState);
            if (EMWifi.isInTestMode() && currentState != 3 && currentState != 2) {
                EMWifi.stopTestMode();
                WiFiTestActivity.this.showDialog(3);
            }
        }
    };

    enum EnumRfAtAntswp {
        RF_AT_ANTSWP_MAIN,
        RF_AT_ANTSWP_AUX
    }

    /* access modifiers changed from: package-private */
    public abstract boolean isInTestProcess();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mWifiManager = (WifiManager) getSystemService("wifi");
        if (!initTest()) {
            finish();
        }
        registerReceiver(this.mWifiStateReceiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unregisterReceiver(this.mWifiStateReceiver);
        Elog.i(TAG, "ondestroy and stop test mode");
        EMWifi.stopTestMode();
        super.onDestroy();
    }

    public void onBackPressed() {
        Elog.i(TAG, "onBackPressed");
        if (isInTestProcess()) {
            showDialog(4);
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void showWifiModeErrDlg() {
        showDialog(3);
    }

    /* access modifiers changed from: protected */
    public void updateWifiChannel(ChannelInfo channel, ArrayAdapter<String> channelAdapter, Spinner channelSpinner) {
        int selectedId = channel.getSelectedChannelId();
        Elog.i(TAG, "enter updateWifiChannel: " + selectedId);
        if (!EMWifi.isInTestMode()) {
            Elog.w(TAG, "Wifi is not initialized");
            showDialog(3);
        } else if (channelAdapter.getCount() != 0) {
            channelSpinner.setSelection(0);
            String name = channelAdapter.getItem(0);
            int chId = ChannelInfo.parseChannelId(name);
            channel.setSelectedChannel(name);
            int number = ChannelInfo.getChannelFrequency(chId);
            EMWifi.setChannel((long) number);
            Elog.i(TAG, "The channel freq =" + number);
        }
    }

    private boolean initTest() {
        Elog.i(TAG, "oncreate and start test mode");
        switch (AnonymousClass4.$SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[EMWifi.startTestMode(this.mWifiManager).ordinal()]) {
            case 1:
                showDialog(2);
                return false;
            case 2:
                showDialog(3);
                return false;
            case 3:
                return true;
            default:
                return false;
        }
    }

    /* renamed from: com.mediatek.engineermode.wifi.WiFiTestActivity$4  reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState;

        static {
            int[] iArr = new int[WifiEmState.values().length];
            $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState = iArr;
            try {
                iArr[WifiEmState.WIFI_EM_STATE_ENABLE_FAIL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[WifiEmState.WIFI_EM_STATE_SET_TM_FAIL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[WifiEmState.WIFI_EM_STATE_READY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.em_error);
                builder.setCancelable(false);
                builder.setMessage(getString(R.string.wifi_dialog_fail_message));
                builder.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WiFiTestActivity.this.finish();
                    }
                });
                return builder.create();
            case 3:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.em_error);
                builder2.setCancelable(false);
                builder2.setMessage(getString(R.string.wifi_not_in_test_mode));
                builder2.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WiFiTestActivity.this.finish();
                    }
                });
                return builder2.create();
            case 4:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle(R.string.wifi);
                builder3.setCancelable(false);
                builder3.setMessage(getString(R.string.wifi_reminder_stop_test));
                builder3.setPositiveButton(R.string.em_ok, (DialogInterface.OnClickListener) null);
                return builder3.create();
            default:
                return null;
        }
    }
}
