package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.RadioStateManager;
import com.mediatek.engineermode.RatConfiguration;
import java.util.ArrayList;
import java.util.Iterator;

public class RfDesenseTxTest extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, RadioStateManager.RadioListener {
    public static final int CDMD_MODE_1X = 1;
    public static final int CDMD_MODE_EVDO = 2;
    public static final String DEFAULT_CDMA_1X_ATCMD = "AT+ECRFTX=1,384,0,83,0";
    public static final String DEFAULT_CDMA_EVDO_ATCMD = "AT+ERFTX=13,4,384,0,83";
    public static final String DEFAULT_CDMA_EVDO_ATCMD_93before = "AT+ERFTX=1,384,0,83,1";
    public static final int DEFAULT_CHECK_LIMIT = 2;
    public static final String DEFAULT_GSM_ATCMD = "AT+ERFTX=2,1,190,4100,128,5,6,0";
    public static final String DEFAULT_LTE_FDD_ATCMD = "AT+ERFTX=6,0,1,3,3,17475,1,0,0,0,1,0,23";
    public static final String DEFAULT_LTE_TDD_ATCMD = "AT+ERFTX=6,0,1,38,3,25950,0,0,0,0,1,0,23";
    public static final String DEFAULT_NR_ATCMD = "AT+EGMC=1,\"NrForcedTx\",2,1,20,2";
    public static final int DEFAULT_READBACK_INTREVAL = 5;
    public static final String DEFAULT_TDSCDMA_ATCMD = "AT+ERFTX=0,0,1,10087,24";
    public static final int DEFAULT_TEST_COUNT = 1;
    public static final int DEFAULT_TEST_DURATION = 10;
    public static final String DEFAULT_WCDMA_ATCMD = "AT+ERFTX=0,0,1,9750,23";
    private static final int DIG_EXIT_TEST_RESET_MD = 2002;
    public static final int HINT = 0;
    public static final String KEY_CDMA1X_ATCMD_ANT_SWITCH = "cdma1x_at_cmd_ant_switch";
    public static final String KEY_CDMA_1X_ATCMD = "cdma_at_cmd";
    public static final String KEY_CDMA_EVDO_ATCMD = "cdma_evdo_at_cmd";
    public static final String KEY_CHECK_LIMIT = "check_limit";
    public static final String KEY_EVDO_ATCMD_ANT_SWITCH = "evdo_at_cmd_ant_switch";
    public static final String KEY_GSM_ATCMD = "gsm_at_cmd";
    public static final String KEY_GSM_ATCMD_ANT_SWITCH = "gsm_at_cmd_ant_switch";
    public static final String KEY_LTE_FDD_ATCMD = "lte_fdd_at_cmd";
    public static final String KEY_LTE_TDD_ATCMD = "lte_tdd_at_cmd";
    public static final String KEY_NR_ATCMD = "nr_at_cmd";
    public static final String KEY_NR_ATCMD_ANT_SWITCH = "nr_at_cmd_ant_switch";
    public static final String KEY_READBACK_INTREVAL = "readback_interval";
    public static final String KEY_TDSCDMA_ATCMD = "tdscdma_at_cmd";
    public static final String KEY_TDSCDMA_ATCMD_ANT_SWITCH = "tdscdma_at_cmd_ant_switch";
    public static final String KEY_TEST_COUNT = "test_count";
    public static final String KEY_TEST_DURATION = "test_duration";
    public static final String KEY_WCDMA_ATCMD = "wcdma_at_cmd";
    public static final String KEY_WCDMA_ATCMD_ANT_SWITCH = "wcdma_at_cmd_ant_switch";
    public static final int MSG_ANT_SWITCH = 18;
    public static final int MSG_CONTINUE_TX = 2;
    public static final int MSG_ECSRA = 14;
    public static final int MSG_EWMPOLICY_TDSCDMA = 12;
    public static final int MSG_EWMPOLICY_WCDMA = 13;
    public static final int MSG_FORCE_TX_POWER_READ_URC = 7;
    public static final int MSG_NEXT_RAT = 4;
    public static final int MSG_READ_POWER = 10;
    public static final int MSG_START_TX = 1;
    public static final int MSG_START_TX_TEST = 16;
    public static final int MSG_STOP_ALL_TX = 3;
    public static final int MSG_SWITCH_RAT = 8;
    public static final int MSG_SWITCH_RAT_DONE = 15;
    public static final int MSG_TURN_OFF_RF = 9;
    public static final int MSG_TURN_ON_RF = 6;
    public static final int MSG_UPDATE_BUTTON = 5;
    public static final String PREF_FILE = "rfdesense_tx_test";
    public static final int RAT_INDEX_CDMA_1X = 6;
    public static final int RAT_INDEX_CDMA_EVDO = 5;
    public static final int RAT_INDEX_GSM = 0;
    public static final int RAT_INDEX_LTE_FDD = 3;
    public static final int RAT_INDEX_LTE_TDD = 4;
    public static final int RAT_INDEX_NR = 7;
    public static final int RAT_INDEX_TDSCDMA = 1;
    public static final int RAT_INDEX_WCDMA = 2;
    public static final int SIM_CARD_INSERT = 1;
    public static final int STATE_NONE = 0;
    public static final int STATE_STARTED = 1;
    public static final int STATE_STOPPED = 2;
    public static final String TAG = "RfDesense/TxTest";
    public static long mCheckLimit = 0;
    public static RfDesenseRatInfo mCurrectRatInfo = null;
    /* access modifiers changed from: private */
    public static int mKeyStates = 0;
    public static String[] mRatCmdAntSwitch = {"0", "0", "0", "0", "0", "0", "0", "0"};
    public static String[] mRatCmdPowerRead = {"AT+ERFTX=2,6", "AT+ERFTX=0,3", "AT+ERFTX=0,3", "AT+ERFTX=6,1", "AT+ERFTX=6,1", "AT+ERFTX=13,3", "AT+ERFTX=13,3", "AT+EGMC=0,\"NrFetchTxPwr\""};
    public static String[] mRatCmdStart = {DEFAULT_GSM_ATCMD, DEFAULT_TDSCDMA_ATCMD, DEFAULT_WCDMA_ATCMD, DEFAULT_LTE_FDD_ATCMD, DEFAULT_LTE_TDD_ATCMD, DEFAULT_CDMA_EVDO_ATCMD, DEFAULT_CDMA_1X_ATCMD, DEFAULT_NR_ATCMD};
    public static String[] mRatCmdStop = {"AT+ERFTX=2,0", "AT+ERFTX=0,1", "AT+ERFTX=0,1", "AT+ERFTX=6,0,0", "AT+ERFTX=6,0,0", "AT+ERFTX=13,5", "AT+ECRFTX=0", "AT+EGMC=1,\"NrForcedTx\",0"};
    public static String[] mRatName = {"GSM", "TDSCDMA", "WCDMA", "LTE(FDD)", "LTE(TDD)", "CDMA(EVDO)", "CDMA(1X)", "NR"};
    public static long mReadbackInterval = 0;
    public static long mTestCount = 0;
    public static long mTestCountSended = 0;
    public static long mTestDuration = 0;
    public static long mTestDurationSended = 0;
    private static int mTestExitStatus = 0;
    public static int phoneid = 0;
    /* access modifiers changed from: private */
    public int[] band1800_1900 = {30, 28, 26, 24, 22, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2, 0};
    /* access modifiers changed from: private */
    public int[] band850_900 = {0, 0, 0, 0, 0, 33, 31, 29, 27, 25, 23, 21, 19, 17, 15, 13, 11, 9, 7, 5};
    private EditText mEtCheckLimit;
    private EditText mEtReadbackInterval;
    private EditText mEtTestCount;
    private EditText mEtTestDuration;
    private Button mExitButton;
    public RfDesenseRatAdapter mFileListAdapter = null;
    /* access modifiers changed from: private */
    public String[] mGSMBand = {"850", "P900", "E900", "R900", "1800", "1900"};
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String ratband_s;
            String ratPowerSet;
            String result;
            String result2;
            Message message = msg;
            AsyncResult ar = (AsyncResult) message.obj;
            switch (message.what) {
                case 1:
                    if (ar.exception == null) {
                        Elog.d(RfDesenseTxTest.TAG, RfDesenseTxTest.mCurrectRatInfo.getRatName() + " start cmd ok");
                        RfDesenseTxTest.this.showTxStatusUI(RfDesenseTxTest.mCurrectRatInfo.getRatName() + " start cmd ok\n");
                    } else {
                        Elog.e(RfDesenseTxTest.TAG, RfDesenseTxTest.mCurrectRatInfo.getRatName() + " start cmd failed");
                        RfDesenseTxTest.this.showTxStatusUI(RfDesenseTxTest.mCurrectRatInfo.getRatName() + " start cmd failed\n");
                    }
                    RfDesenseTxTest.this.mHandler.sendMessageDelayed(Message.obtain(RfDesenseTxTest.this.mHandler, 2), 1000);
                    return;
                case 2:
                    if (RfDesenseTxTest.mKeyStates == 2) {
                        RfDesenseTxTest.this.mHandler.removeMessages(2);
                        RfDesenseTxTest.this.txTestStop(3);
                        return;
                    }
                    RfDesenseTxTest.mTestDurationSended += RfDesenseTxTest.mReadbackInterval;
                    if (RfDesenseTxTest.mTestDurationSended > RfDesenseTxTest.mTestDuration) {
                        RfDesenseTxTest.mTestDurationSended = 0;
                        RfDesenseTxTest.this.txTestStop(4);
                        return;
                    }
                    RfDesenseTxTest.this.mHandler.sendMessageDelayed(Message.obtain(RfDesenseTxTest.this.mHandler, 2), RfDesenseTxTest.mReadbackInterval * 1000);
                    if (FeatureSupport.is93ModemAndAbove() && RfDesenseTxTest.mCurrectRatInfo != null && !RfDesenseTxTest.mCurrectRatInfo.getRatCmdPowerRead().equals("")) {
                        Elog.d(RfDesenseTxTest.TAG, "send read tx power:" + RfDesenseTxTest.mCurrectRatInfo.getRatCmdPowerRead());
                        RfDesenseTxTest.this.sendAtCommand(RfDesenseTxTest.mCurrectRatInfo.getRatCmdPowerRead(), 10);
                        return;
                    }
                    return;
                case 3:
                    if (ar.exception == null) {
                        Elog.d(RfDesenseTxTest.TAG, "Stop all succeed");
                    } else {
                        Elog.e(RfDesenseTxTest.TAG, "Stop all failed");
                    }
                    RfDesenseTxTest.this.updateUIView();
                    return;
                case 4:
                    if (ar.exception == null) {
                        Elog.d(RfDesenseTxTest.TAG, "stop cmd ok");
                        RfDesenseTxTest.this.showTxStatusUI(RfDesenseTxTest.mCurrectRatInfo.getRatName() + " stop cmd ok \n");
                    } else {
                        Elog.e(RfDesenseTxTest.TAG, "stop cmd failed");
                        RfDesenseTxTest.this.showTxStatusUI(RfDesenseTxTest.mCurrectRatInfo.getRatName() + " stop cmd failed \n");
                    }
                    RfDesenseTxTest.mCurrectRatInfo = RfDesenseTxTest.this.getCurrectRatInfo();
                    if (RfDesenseTxTest.mCurrectRatInfo != null) {
                        RfDesenseTxTest.this.mHandler.sendMessageDelayed(Message.obtain(RfDesenseTxTest.this.mHandler, 16), 2000);
                        return;
                    }
                    RfDesenseTxTest.mTestCountSended++;
                    Elog.d(RfDesenseTxTest.TAG, "send done,mTestCountSended = " + RfDesenseTxTest.mTestCountSended);
                    if (RfDesenseTxTest.mTestCountSended < RfDesenseTxTest.mTestCount) {
                        for (int i = 0; i < RfDesenseTxTest.this.mRatList.size(); i++) {
                            RfDesenseTxTest.this.mRatList.get(i).setRatSendState(false);
                        }
                        RfDesenseTxTest.this.mHandler.sendMessageDelayed(Message.obtain(RfDesenseTxTest.this.mHandler, 16), 2000);
                        return;
                    }
                    RfDesenseTxTest.this.showTxStatusUI("send all rat done\n");
                    RfDesenseTxTest.this.updateUIView();
                    return;
                case 5:
                    RfDesenseTxTest.this.updateButtons();
                    return;
                case 7:
                    int[] data = (int[]) ar.result;
                    float getPower = ((float) data[1]) / 8.0f;
                    int getRatPowerSet = Integer.valueOf(RfDesenseTxTest.mCurrectRatInfo.getRatPowerSet()).intValue();
                    int ratband = Integer.parseInt(RfDesenseTxTest.mCurrectRatInfo.getRatband());
                    if (data[0] == 0) {
                        ratband_s = RfDesenseTxTest.this.mGSMBand[ratband];
                        String ratPowerSet2 = "PCL" + getRatPowerSet + "(";
                        if (ratband == 0 || ratband == 1 || ratband == 2 || ratband == 3) {
                            ratPowerSet = ratPowerSet2 + RfDesenseTxTest.this.band850_900[getRatPowerSet] + ")";
                            getRatPowerSet = RfDesenseTxTest.this.band850_900[getRatPowerSet];
                        } else {
                            ratPowerSet = ratPowerSet2 + RfDesenseTxTest.this.band1800_1900[getRatPowerSet] + ")";
                            getRatPowerSet = RfDesenseTxTest.this.band1800_1900[getRatPowerSet];
                        }
                    } else {
                        ratband_s = RfDesenseTxTest.mCurrectRatInfo.getRatband();
                        ratPowerSet = RfDesenseTxTest.mCurrectRatInfo.getRatPowerSet();
                    }
                    Object obj = "";
                    Object obj2 = "";
                    if (Math.abs(((float) getRatPowerSet) - getPower) > ((float) RfDesenseTxTest.mCheckLimit)) {
                        result = "failed\n";
                    } else {
                        result = "succeed\n";
                    }
                    RfDesenseTxTest.this.showTxStatusUI(String.format("%-20s %-15s %-10s", new Object[]{RfDesenseTxTest.mCurrectRatInfo.getRatName() + "(b" + ratband_s + ")", ratPowerSet, Float.valueOf(getPower)}));
                    RfDesenseTxTest.this.showTxStatusUI(String.format("%10s", new Object[]{result}));
                    return;
                case 10:
                    if (ar.exception == null) {
                        Elog.d(RfDesenseTxTest.TAG, "read tx power succeed");
                        String[] value = (String[]) ar.result;
                        if (value != null && value.length > 0 && RfDesenseTxTest.mCurrectRatInfo != null) {
                            Elog.d(RfDesenseTxTest.TAG, "value = " + value[0]);
                            float getPower2 = Float.parseFloat(value[0].split(",")[1]);
                            int getRatPowerSet2 = Integer.valueOf(RfDesenseTxTest.mCurrectRatInfo.getRatPowerSet()).intValue();
                            String ratband_s2 = RfDesenseTxTest.mCurrectRatInfo.getRatband();
                            if (Math.abs(((float) getRatPowerSet2) - getPower2) > ((float) RfDesenseTxTest.mCheckLimit)) {
                                result2 = "failed\n";
                            } else {
                                result2 = "succeed\n";
                            }
                            RfDesenseTxTest.this.showTxStatusUI(String.format("%-20s %-15s %-10s", new Object[]{RfDesenseTxTest.mCurrectRatInfo.getRatName() + "(b" + ratband_s2 + ")", Integer.valueOf(getRatPowerSet2), Float.valueOf(getPower2)}));
                            if (result2.equals("failed\n")) {
                                RfDesenseTxTest.this.showTxStatusUI(result2);
                                return;
                            }
                            RfDesenseTxTest.this.showTxStatusUI(String.format("%10s", new Object[]{result2}));
                            return;
                        }
                        return;
                    }
                    Elog.d(RfDesenseTxTest.TAG, "read tx power failed");
                    return;
                case 16:
                    Elog.d(RfDesenseTxTest.TAG, "start tx test");
                    RfDesenseTxTest.mCurrectRatInfo = RfDesenseTxTest.this.getCurrectRatInfo();
                    if (RfDesenseTxTest.mCurrectRatInfo == null) {
                        Elog.w(RfDesenseTxTest.TAG, "mCurrectRatInfo is null");
                        return;
                    } else if (RfDesenseTxTest.mCurrectRatInfo.getRatCmdAntSwitch().equals("0")) {
                        RfDesenseTxTest.mCurrectRatInfo.setRatSendState(true);
                        RfDesenseTxTest.this.sendAtCommand(RfDesenseTxTest.mCurrectRatInfo.getRatCmdStart(), 1);
                        Elog.d(RfDesenseTxTest.TAG, "send: " + RfDesenseTxTest.mCurrectRatInfo.getRatName() + " " + RfDesenseTxTest.mCurrectRatInfo.getRatCmdStart());
                        return;
                    } else {
                        RfDesenseTxTest.this.sendAtCommand(RfDesenseTxTest.mCurrectRatInfo.getRatCmdAntSwitch(), 18);
                        Elog.d(RfDesenseTxTest.TAG, "switch ant statrus: " + RfDesenseTxTest.mCurrectRatInfo.getRatCmdAntSwitch());
                        return;
                    }
                case 18:
                    if (ar.exception == null) {
                        Elog.d(RfDesenseTxTest.TAG, "switch ant status succeed");
                        RfDesenseTxTest.mCurrectRatInfo.setRatSendState(true);
                        RfDesenseTxTest.this.sendAtCommand(RfDesenseTxTest.mCurrectRatInfo.getRatCmdStart(), 1);
                        Elog.d(RfDesenseTxTest.TAG, "send: " + RfDesenseTxTest.mCurrectRatInfo.getRatName() + " " + RfDesenseTxTest.mCurrectRatInfo.getRatCmdStart());
                        return;
                    }
                    Elog.e(RfDesenseTxTest.TAG, "switch ant status failed");
                    return;
                default:
                    return;
            }
        }
    };
    private RadioStateManager mRadioStateManager;
    private int mRadioStatesLast = -1;
    public String[] mRatBand = {"", "", "", "", "", "", "", ""};
    public ArrayList<RfDesenseRatInfo> mRatList = new ArrayList<>();
    public String[] mRatPowerSet = {"", "", "", "", "", "", "", ""};
    private Button mStartButton;
    private Button mStopButton;
    private TextView mTvCheckLimit;
    private TextView mTvReadbackInterval;
    private TextView mTvTxStatus;

    public void onRadioPowerOff() {
        Elog.v(TAG, "RADIO_POWER_OFF");
        this.mStartButton.setEnabled(true);
    }

    public void onRadioPowerOn() {
        Elog.v(TAG, "RADIO_POWER_ON");
        if (mTestExitStatus == 1) {
            Elog.d(TAG, "modem reset succeed");
            mTestExitStatus = 2;
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test);
        this.mStopButton = (Button) findViewById(R.id.button_stop_total);
        this.mExitButton = (Button) findViewById(R.id.button_exit_total);
        this.mStartButton = (Button) findViewById(R.id.button_start_total);
        this.mStopButton.setOnClickListener(this);
        this.mExitButton.setOnClickListener(this);
        this.mStartButton.setOnClickListener(this);
        this.mEtTestDuration = (EditText) findViewById(R.id.test_duration);
        this.mEtTestCount = (EditText) findViewById(R.id.test_count);
        this.mTvTxStatus = (TextView) findViewById(R.id.test_result);
        this.mEtCheckLimit = (EditText) findViewById(R.id.check_limit);
        this.mEtReadbackInterval = (EditText) findViewById(R.id.readback_interval);
        this.mTvCheckLimit = (TextView) findViewById(R.id.check_limit_view);
        this.mTvReadbackInterval = (TextView) findViewById(R.id.readback_interval_view);
        ListView simTypeListView = (ListView) findViewById(R.id.list);
        mTestExitStatus = 0;
        if (ModemCategory.isSimReady(-1)) {
            Elog.d(TAG, "some card insert");
            showDialog(1);
        }
        if (!FeatureSupport.is93ModemAndAbove()) {
            this.mEtCheckLimit.setVisibility(8);
            this.mEtReadbackInterval.setVisibility(8);
            this.mTvCheckLimit.setVisibility(8);
            this.mTvReadbackInterval.setVisibility(8);
            mRatCmdStart[5] = DEFAULT_CDMA_EVDO_ATCMD_93before;
            mRatCmdStop[5] = "AT+ECRFTX=0";
        }
        restoreAtCmdState();
        restoreConfigureState();
        for (int i = 0; i < mRatName.length; i++) {
            RfDesenseRatInfo mRatInfo = new RfDesenseRatInfo();
            mRatInfo.setRatName(mRatName[i]);
            mRatInfo.setRatCmdStart(mRatCmdStart[i]);
            mRatInfo.setRatCmdStop(mRatCmdStop[i]);
            mRatInfo.setRatPowerRead(mRatCmdPowerRead[i]);
            mRatInfo.setRatband(this.mRatBand[i]);
            mRatInfo.setRatPowerSet(this.mRatPowerSet[i]);
            mRatInfo.setRatCmdAntSwitch(mRatCmdAntSwitch[i]);
            mRatInfo.setRatCheckState(false);
            mRatInfo.setRatSendState(false);
            int modemType = ModemCategory.getModemType();
            if (modemType == 1) {
                if (i == 1) {
                }
            } else if (modemType == 2 && i == 2) {
            }
            if ((ModemCategory.isCdma() || !(i == 5 || i == 6)) && (RatConfiguration.isNrSupported() || i != 7)) {
                this.mRatList.add(mRatInfo);
            }
        }
        RfDesenseRatAdapter rfDesenseRatAdapter = new RfDesenseRatAdapter(this, this.mRatList);
        this.mFileListAdapter = rfDesenseRatAdapter;
        simTypeListView.setAdapter(rfDesenseRatAdapter);
        simTypeListView.setOnItemClickListener(this);
        setListViewItemsHeight(simTypeListView);
        RadioStateManager radioStateManager = new RadioStateManager(this);
        this.mRadioStateManager = radioStateManager;
        radioStateManager.registerRadioStateChanged(this);
        if (FeatureSupport.is93ModemAndAbove()) {
            Elog.d(TAG, "registerForTxPower");
            EmUtils.registerForTxpowerInfo(this.mHandler, 7);
        }
        this.mTvTxStatus.setText("");
        mKeyStates = 0;
        updateButtons();
        EmUtils.initPoweroffmdMode(true, true);
        if (!EmUtils.ifAirplaneModeEnabled()) {
            Elog.d(TAG, "turn off rf");
            this.mStartButton.setEnabled(false);
            this.mRadioStateManager.setAirplaneModeEnabled(true);
            return;
        }
        Elog.d(TAG, "it is in Airplane Mode");
        this.mStartButton.setEnabled(true);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d(TAG, "onResume");
        restoreAtCmdState();
        updateRatInfo();
    }

    public void onDestroy() {
        this.mRadioStateManager.unregisterRadioStateChanged();
        EmUtils.unregisterForTxpowerInfo();
        Elog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    /* access modifiers changed from: package-private */
    public void exit_test_and_rest_md() {
        mTestExitStatus = 1;
        EmUtils.initPoweroffmdMode(false, true);
        this.mRadioStateManager.rebootModem();
        Elog.d(TAG, "reboot MD");
        EmUtils.setAirplaneModeEnabled(false);
        Elog.d(TAG, "leave AirplaneMode");
    }

    public void onBackPressed() {
        if (mKeyStates == 1) {
            showDialog(0);
            return;
        }
        Elog.d(TAG, "exit ui: " + mTestExitStatus);
        int i = mTestExitStatus;
        if (i == 0) {
            showDialog(2002);
        } else if (i == 2) {
            finish();
        }
    }

    public void onClick(View arg0) {
        if (arg0 == this.mExitButton) {
            int i = mKeyStates;
            if (i == 1) {
                showDialog(0);
            } else if (i == 2 || i == 0) {
                Elog.d(TAG, "exit ui: " + mTestExitStatus);
                int i2 = mTestExitStatus;
                if (i2 == 0) {
                    showDialog(2002);
                } else if (i2 == 2) {
                    finish();
                }
            }
        } else if (arg0 == this.mStartButton) {
            this.mTvTxStatus.setText("");
            if (!isRatsChecked()) {
                Elog.d(TAG, "you must select at least one rat");
                showTxStatusUI("you must select at least one rat\n");
                return;
            }
            setTestParameter();
            mKeyStates = 1;
            Handler handler = this.mHandler;
            handler.sendMessage(Message.obtain(handler, 5));
            showTxStatusUI("Start TX: \n");
            if (FeatureSupport.is93ModemAndAbove()) {
                showTxStatusUI("Rat(band)          Power_Set   Power_Get    Result\n");
            }
            Handler handler2 = this.mHandler;
            handler2.sendMessageDelayed(Message.obtain(handler2, 16), 2000);
        } else if (arg0 == this.mStopButton) {
            Elog.d(TAG, "Stop all");
            mKeyStates = 2;
            Handler handler3 = this.mHandler;
            handler3.sendMessageDelayed(Message.obtain(handler3, 2), 1000);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRatsChecked() {
        for (int index = 0; index < this.mRatList.size(); index++) {
            if (this.mRatList.get(index).getRatCheckState().booleanValue() && this.mRatList.get(index).getRatCheckState().booleanValue()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public RfDesenseRatInfo getCurrectRatInfo() {
        mCurrectRatInfo = null;
        int index = 0;
        while (true) {
            if (index < this.mRatList.size()) {
                if (this.mRatList.get(index).getRatCheckState().booleanValue() && !this.mRatList.get(index).getRatSendState().booleanValue()) {
                    mCurrectRatInfo = this.mRatList.get(index);
                    break;
                }
                index++;
            } else {
                break;
            }
        }
        return mCurrectRatInfo;
    }

    private void updateRatInfo() {
        for (int i = 0; i < mRatName.length; i++) {
            Iterator<RfDesenseRatInfo> it = this.mRatList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RfDesenseRatInfo item = it.next();
                if (mRatName[i].equals(item.getRatName())) {
                    item.setRatCmdStart(mRatCmdStart[i]);
                    item.setRatband(this.mRatBand[i]);
                    item.setRatPowerSet(this.mRatPowerSet[i]);
                    item.setRatCmdAntSwitch(mRatCmdAntSwitch[i]);
                    break;
                }
            }
        }
        this.mFileListAdapter.notifyDataSetInvalidated();
    }

    /* access modifiers changed from: private */
    public void txTestStop(int what) {
        RfDesenseRatInfo rfDesenseRatInfo = mCurrectRatInfo;
        if (rfDesenseRatInfo != null) {
            sendAtCommand(rfDesenseRatInfo.getRatCmdStop(), what);
            Elog.d(TAG, "stop: " + mCurrectRatInfo.getRatName() + " " + mCurrectRatInfo.getRatCmdStop());
            return;
        }
        Elog.d(TAG, "mCurrectRatInfo is null");
        updateUIView();
    }

    private void setTestParameter() {
        String TestDuration = this.mEtTestDuration.getText().toString();
        String TestCount = this.mEtTestCount.getText().toString();
        String CheckLimit = this.mEtCheckLimit.getText().toString();
        String ReadbackInterval = this.mEtReadbackInterval.getText().toString();
        if ("".equals(TestDuration)) {
            this.mEtTestDuration.setText("10");
            mTestDuration = 10;
        } else {
            mTestDuration = (long) Integer.valueOf(TestDuration).intValue();
        }
        if ("".equals(TestCount)) {
            this.mEtTestCount.setText("1");
            mTestCount = 1;
        } else {
            mTestCount = (long) Integer.valueOf(TestCount).intValue();
        }
        if ("".equals(CheckLimit)) {
            this.mEtCheckLimit.setText("2");
            mCheckLimit = 2;
        } else {
            mCheckLimit = (long) Integer.valueOf(CheckLimit).intValue();
        }
        if ("".equals(ReadbackInterval)) {
            this.mEtReadbackInterval.setText(String.valueOf(5));
            mReadbackInterval = 5;
        } else {
            long intValue = (long) Integer.valueOf(ReadbackInterval).intValue();
            mReadbackInterval = intValue;
            if (intValue < 5) {
                mReadbackInterval = 5;
                EmUtils.showToast("mReadbackInterval at least 5s");
                this.mEtReadbackInterval.setText(String.valueOf(mReadbackInterval));
            }
        }
        long j = mReadbackInterval;
        long j2 = mTestDuration;
        if (j > j2) {
            mReadbackInterval = j2;
        }
        mTestCountSended = 0;
        mTestDurationSended = 0;
        saveConfigureState();
    }

    /* access modifiers changed from: package-private */
    public boolean isSendToCdmaCmd(String str) {
        if (mCurrectRatInfo == null || str.equals("AT+EFUN=0")) {
            return false;
        }
        if (str.equals("AT+CPOF")) {
            Elog.d(TAG, "send to cdma rat:");
            return true;
        } else if (str.equals("AT+CFUN=1,1")) {
            return false;
        } else {
            if (mCurrectRatInfo.getRatName().equals(mRatName[5]) || mCurrectRatInfo.getRatName().equals(mRatName[6])) {
                Elog.d(TAG, "send to cdma rat:");
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String str, int what) {
        if (FeatureSupport.is93ModemAndAbove() || !isSendToCdmaCmd(str)) {
            String[] cmd = {str, ""};
            if (str.equals("AT+EGMC=0,\"NrFetchTxPwr\"")) {
                cmd[1] = "+EGMC:";
            }
            EmUtils.invokeOemRilRequestStringsEm(cmd, this.mHandler.obtainMessage(what));
            return;
        }
        EmUtils.invokeOemRilRequestStringsEm(true, new String[]{str, "", "DESTRILD:C2K"}, this.mHandler.obtainMessage(what));
    }

    /* access modifiers changed from: private */
    public void showTxStatusUI(String msg) {
        this.mTvTxStatus.append(msg);
    }

    private void setListViewItemsHeight(ListView listview) {
        if (listview != null) {
            ListAdapter adapter = listview.getAdapter();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View itemView = adapter.getView(i, (View) null, listview);
                itemView.measure(0, 0);
                totalHeight += itemView.getMeasuredHeight();
            }
            int totalHeight2 = totalHeight + ((adapter.getCount() - 1) * listview.getDividerHeight());
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalHeight2;
            listview.setLayoutParams(params);
        }
    }

    /* access modifiers changed from: private */
    public void updateButtons() {
        boolean z = false;
        this.mStartButton.setEnabled(mKeyStates == 2);
        this.mStopButton.setEnabled(mKeyStates == 1);
        Button button = this.mExitButton;
        int i = mKeyStates;
        if (i == 0 || i == 2) {
            z = true;
        }
        button.setEnabled(z);
    }

    /* access modifiers changed from: private */
    public void updateUIView() {
        mKeyStates = 2;
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(Message.obtain(handler, 5), 500);
        for (int i = 0; i < this.mRatList.size(); i++) {
            this.mRatList.get(i).setRatSendState(false);
        }
    }

    private void restoreAtCmdState() {
        SharedPreferences pref = getSharedPreferences(PREF_FILE, 0);
        String[] strArr = mRatCmdStart;
        strArr[0] = pref.getString(KEY_GSM_ATCMD, strArr[0]);
        String[] strArr2 = mRatCmdStart;
        strArr2[1] = pref.getString(KEY_TDSCDMA_ATCMD, strArr2[1]);
        String[] strArr3 = mRatCmdStart;
        strArr3[2] = pref.getString(KEY_WCDMA_ATCMD, strArr3[2]);
        String[] strArr4 = mRatCmdStart;
        strArr4[3] = pref.getString(KEY_LTE_FDD_ATCMD, strArr4[3]);
        String[] strArr5 = mRatCmdStart;
        strArr5[4] = pref.getString(KEY_LTE_TDD_ATCMD, strArr5[4]);
        String[] strArr6 = mRatCmdStart;
        strArr6[5] = pref.getString(KEY_CDMA_EVDO_ATCMD, strArr6[5]);
        String[] strArr7 = mRatCmdStart;
        strArr7[6] = pref.getString(KEY_CDMA_1X_ATCMD, strArr7[6]);
        String[] strArr8 = mRatCmdStart;
        strArr8[7] = pref.getString(KEY_NR_ATCMD, strArr8[7]);
        String[] strArr9 = mRatCmdAntSwitch;
        strArr9[0] = pref.getString(KEY_GSM_ATCMD_ANT_SWITCH, strArr9[0]);
        String[] strArr10 = mRatCmdAntSwitch;
        strArr10[1] = pref.getString(KEY_TDSCDMA_ATCMD_ANT_SWITCH, strArr10[1]);
        String[] strArr11 = mRatCmdAntSwitch;
        strArr11[2] = pref.getString(KEY_WCDMA_ATCMD_ANT_SWITCH, strArr11[2]);
        String[] strArr12 = mRatCmdAntSwitch;
        strArr12[5] = pref.getString(KEY_EVDO_ATCMD_ANT_SWITCH, strArr12[5]);
        String[] strArr13 = mRatCmdAntSwitch;
        strArr13[6] = pref.getString(KEY_CDMA1X_ATCMD_ANT_SWITCH, strArr13[6]);
        String[] strArr14 = mRatCmdAntSwitch;
        strArr14[7] = pref.getString(KEY_NR_ATCMD_ANT_SWITCH, strArr14[7]);
        String[] strArr15 = this.mRatBand;
        strArr15[0] = pref.getInt(RfDesenseTxTestGsm.KEY_BAND, 0) + "";
        this.mRatBand[1] = getResources().getStringArray(R.array.rf_desense_tx_test_td_band_values)[pref.getInt(RfDesenseTxTestTd.KEY_TDD_BAND, 0)];
        this.mRatBand[2] = getResources().getStringArray(R.array.rf_desense_tx_test_fd_band_values)[pref.getInt(RfDesenseTxTestTd.KEY_FDD_BAND, 0)];
        this.mRatBand[3] = getResources().getStringArray(R.array.rf_desense_tx_test_lte_fdd_band_values)[pref.getInt(RfDesenseTxTestLte.KEY_FDD_BAND, 2)];
        this.mRatBand[4] = getResources().getStringArray(R.array.rf_desense_tx_test_lte_tdd_band_values)[pref.getInt(RfDesenseTxTestLte.KEY_TDD_BAND, 5)];
        String[] strArr16 = this.mRatBand;
        strArr16[5] = pref.getInt(RfDesenseTxTestCdma.KEY_EVDO_BAND, 0) + "";
        String[] strArr17 = this.mRatBand;
        strArr17[6] = pref.getInt(RfDesenseTxTestCdma.KEY_1X_BAND, 0) + "";
        String[] strArr18 = this.mRatBand;
        strArr18[7] = RfDesenseTxTestNR.mBandMapping[pref.getInt(RfDesenseTxTestNR.KEY_NR_BAND, 0)] + "";
        this.mRatPowerSet[0] = pref.getString(RfDesenseTxTestGsm.KEY_POWER, "5");
        this.mRatPowerSet[1] = pref.getString(RfDesenseTxTestTd.KEY_TDD_POWER, "24");
        this.mRatPowerSet[2] = pref.getString(RfDesenseTxTestTd.KEY_FDD_POWER, "23");
        this.mRatPowerSet[3] = pref.getString(RfDesenseTxTestLte.KEY_FDD_POWER, "23");
        this.mRatPowerSet[4] = pref.getString(RfDesenseTxTestLte.KEY_TDD_POWER, "23");
        this.mRatPowerSet[5] = pref.getString(RfDesenseTxTestCdma.KEY_EVDO_POWER, "23");
        this.mRatPowerSet[6] = pref.getString(RfDesenseTxTestCdma.KEY_1X_POWER, "23");
        this.mRatPowerSet[7] = pref.getString(RfDesenseTxTestNR.KEY_NR_POWER, "23");
    }

    private void restoreConfigureState() {
        SharedPreferences pref = getSharedPreferences(PREF_FILE, 0);
        mTestDuration = pref.getLong(KEY_TEST_DURATION, 10);
        mTestCount = pref.getLong(KEY_TEST_COUNT, 1);
        this.mEtTestDuration.setText(String.valueOf(mTestDuration));
        this.mEtTestCount.setText(String.valueOf(mTestCount));
        mCheckLimit = pref.getLong(KEY_CHECK_LIMIT, 2);
        mReadbackInterval = pref.getLong(KEY_READBACK_INTREVAL, 5);
        this.mEtCheckLimit.setText(String.valueOf(mCheckLimit));
        this.mEtReadbackInterval.setText(String.valueOf(mReadbackInterval));
    }

    private void saveConfigureState() {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_FILE, 0).edit();
        editor.putLong(KEY_TEST_DURATION, mTestDuration);
        editor.putLong(KEY_TEST_COUNT, mTestCount);
        editor.putLong(KEY_CHECK_LIMIT, mCheckLimit);
        editor.putLong(KEY_READBACK_INTREVAL, mReadbackInterval);
        editor.apply();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        String item = this.mRatList.get(position).getRatName();
        if (item.equals(mRatName[0])) {
            intent.setClass(this, RfDesenseTxTestGsm.class);
        } else if (item.equals(mRatName[1])) {
            intent.putExtra("mModemType", 2);
            intent.setClass(this, RfDesenseTxTestTd.class);
        } else if (item.equals(mRatName[2])) {
            intent.putExtra("mModemType", 1);
            intent.setClass(this, RfDesenseTxTestTd.class);
        } else if (item.equals(mRatName[3])) {
            intent.putExtra("mModemType", 1);
            intent.setClass(this, RfDesenseTxTestLte.class);
        } else if (item.equals(mRatName[4])) {
            intent.putExtra("mModemType", 2);
            intent.setClass(this, RfDesenseTxTestLte.class);
        } else if (item.equals(mRatName[5])) {
            intent.putExtra("mModemType", 2);
            intent.setClass(this, RfDesenseTxTestCdma.class);
        } else if (item.equals(mRatName[6])) {
            intent.putExtra("mModemType", 1);
            intent.setClass(this, RfDesenseTxTestCdma.class);
        } else if (item.equals(mRatName[7])) {
            intent.setClass(this, RfDesenseTxTestNR.class);
        }
        startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (id == 0) {
            return new AlertDialog.Builder(this).setTitle("Hint").setMessage("Please stop the test first!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
        }
        if (id == 1) {
            return new AlertDialog.Builder(this).setTitle("Notice").setMessage("Please pull out the sim card before test").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    RfDesenseTxTest.this.finish();
                }
            }).create();
        }
        if (id == 2002) {
            return new AlertDialog.Builder(this).setTitle("You are leaving the test ui").setMessage("Please wait until md reset succeed").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    RfDesenseTxTest.this.exit_test_and_rest_md();
                }
            }).create();
        }
        if (id == 1001) {
            return this.mRadioStateManager.getRebootModemDialog();
        }
        return super.onCreateDialog(id);
    }
}
