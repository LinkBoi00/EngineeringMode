package com.mediatek.engineermode.worldmode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.RatConfiguration;
import com.mediatek.engineermode.WorldModeUtil;
import com.mediatek.engineermode.bandselect.BandModeContent;

public class WorldModeActivity extends Activity implements View.OnClickListener {
    private static final String ACTION_SERVICE_STATE_CHANGED = "android.intent.action.SERVICE_STATE";
    private static final String ACTION_SET_RADIO_CAPABILITY_DONE = "android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE";
    private static final String ACTION_WORLD_MODE_CHANGED = "mediatek.intent.action.ACTION_WORLD_MODE_CHANGED";
    private static final int BAND_GSM_INDEX = 0;
    private static final int BAND_LTEFDD_INDEX = 2;
    private static final int BAND_LTETDD_INDEX = 3;
    private static final int BAND_UTMS_INDEX = 1;
    private static final int C2K_WORLD_MODE_AUTO = 12;
    private static final int DIALOG_WAIT = 1;
    private static final int ERROR = 3;
    private static final int EVENT_QUERY_CURRENT = 0;
    private static final int INDEX_BAND_MAX = 5;
    private static final boolean IS_NR_SUPPORT = RatConfiguration.isNrSupported();
    private static final int MASK_GSM = 1;
    private static final int MASK_LTEFDD = 16;
    private static final int MASK_LTETDD = 8;
    private static final int MASK_TDSCDMA = 2;
    private static final int MASK_WCDMA = 4;
    private static final int REBOOT_RF = 0;
    private static final String TAG = "WorldModeActivity";
    private static final int UNSUPPORT = 2;
    private static final int WORLD_MODE_AUTO = 10;
    private static final int WORLD_MODE_LFWG = 14;
    private static final int WORLD_MODE_LTCTG = 17;
    private static final int WORLD_MODE_LTG = 8;
    private static final int WORLD_MODE_LTTG = 13;
    private static final int WORLD_MODE_LWCG = 11;
    private static final int WORLD_MODE_LWG = 9;
    private static final int WORLD_MODE_RESULT_ERROR = 101;
    private static final int WORLD_MODE_RESULT_SUCCESS = 100;
    private static final int WORLD_MODE_RESULT_WM_ID_NOT_SUPPORT = 102;
    /* access modifiers changed from: private */
    public static int mBandMode = 0;
    /* access modifiers changed from: private */
    public static int mMode = 0;
    private static int mOldMode = 0;
    /* access modifiers changed from: private */
    public static int mSetWorldMode = 0;
    private final int WAIT_TIME = 15;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Elog.d(WorldModeActivity.TAG, "[Receiver]+" + action);
            if (WorldModeActivity.ACTION_SET_RADIO_CAPABILITY_DONE.equals(action)) {
                WorldModeActivity.this.updateUi();
            } else if ("mediatek.intent.action.ACTION_WORLD_MODE_CHANGED".equals(action) && intent.getIntExtra(WorldModeUtil.EXTRA_WORLD_MODE_CHANGE_STATE, -1) == 1) {
                WorldModeActivity.this.updateUi();
            }
            Elog.d(WorldModeActivity.TAG, "[Receiver]-");
        }
    };
    private final BroadcastReceiver mBroadcastReceiverServiceStateChanged = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.d(WorldModeActivity.TAG, "[Receiver]+");
            ServiceState ss = ServiceState.newFromBundle(intent.getExtras());
            Elog.d(WorldModeActivity.TAG, "ss.getState(): " + ss.getState() + ",mStateFlag = " + WorldModeActivity.this.mStateFlag);
            Elog.d(WorldModeActivity.TAG, "[Receiver ACTION_SERVICE_STATE_CHANGED]");
            if (ss.getState() != 3) {
                if (WorldModeActivity.mSetWorldMode == 1 && WorldModeActivity.this.mStateFlag) {
                    WorldModeActivity.this.updateUi();
                    boolean unused = WorldModeActivity.this.mStateFlag = false;
                    if (WorldModeActivity.this.timer != null) {
                        WorldModeActivity.this.timer.cancel();
                        CountDownTimer unused2 = WorldModeActivity.this.timer = null;
                    }
                    if (WorldModeActivity.this.mProgressDialog != null) {
                        WorldModeActivity.this.mProgressDialog.dismiss();
                        int unused3 = WorldModeActivity.mSetWorldMode = 0;
                    }
                }
            } else if (WorldModeActivity.mSetWorldMode == 1 && !WorldModeActivity.this.mStateFlag) {
                boolean unused4 = WorldModeActivity.this.mStateFlag = true;
            }
            Elog.d(WorldModeActivity.TAG, "[Receiver]-");
        }
    };
    private Button mButtonSet;
    private Button mButtonSetValue;
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onRadioPowerStateChanged(int state) {
            Elog.d(WorldModeActivity.TAG, "RadioPowerState:" + state);
            if (state == 1) {
                WorldModeActivity.this.updateUi();
            }
        }
    };
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    private RadioButton mRadioAuto;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioLtctg;
    private RadioButton mRadioLttg;
    private RadioButton mRadioLwcg;
    private RadioButton mRadioLwg;
    private final Handler mResponseHander = new Handler() {
        public void handleMessage(Message msg) {
            Message message = msg;
            switch (message.what) {
                case 0:
                    AsyncResult asyncResult = (AsyncResult) message.obj;
                    if (asyncResult.exception != null) {
                        EmUtils.showToast("Failed to query band");
                        Elog.e(WorldModeActivity.TAG, "Failed to query band");
                        return;
                    }
                    String[] getDigitalVal = ((String[]) asyncResult.result)[0].substring(7).split(",");
                    long[] values = new long[5];
                    if (getDigitalVal != null && getDigitalVal.length > 1) {
                        for (int i = 0; i < values.length; i++) {
                            if (getDigitalVal.length <= i || getDigitalVal[i] == null) {
                                values[i] = 0;
                            } else {
                                try {
                                    values[i] = Long.valueOf(getDigitalVal[i].trim()).longValue();
                                } catch (NumberFormatException e) {
                                    values[i] = 0;
                                }
                            }
                        }
                    }
                    if (RatConfiguration.isGsmSupported() && values[0] > 0) {
                        WorldModeActivity.access$012(1);
                    }
                    if (RatConfiguration.isTdscdmaSupported() && (values[1] & 63) > 0) {
                        WorldModeActivity.access$012(2);
                    }
                    if (RatConfiguration.isWcdmaSupported() && (values[1] & 960) > 0) {
                        WorldModeActivity.access$012(4);
                    }
                    if (RatConfiguration.isLteFddSupported() && values[2] > 0) {
                        WorldModeActivity.access$012(16);
                    }
                    if (RatConfiguration.isLteTddSupported() && values[3] > 0) {
                        WorldModeActivity.access$012(8);
                    }
                    Elog.d(WorldModeActivity.TAG, "mBandMode = " + WorldModeActivity.mBandMode);
                    WorldModeActivity.this.showDialog(0);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mStateFlag = false;
    /* access modifiers changed from: private */
    public TelephonyManager mTelephonyManager;
    private TextView mTextCurrentMode;
    private TextView mTextMainProtocol;
    private TextView mTextWorldModeHint;
    private Toast mToast;
    private EditText mValue;
    private String[] mWorldMode = {EnvironmentCompat.MEDIA_UNKNOWN, "lwg", "lttg"};
    private String[] mWorldModeFor6M = {EnvironmentCompat.MEDIA_UNKNOWN, "LWCG", "LtCTG"};
    /* access modifiers changed from: private */
    public CountDownTimer timer;

    static /* synthetic */ int access$012(int x0) {
        int i = mBandMode + x0;
        mBandMode = i;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.world_mode);
        this.mRadioLtctg = (RadioButton) findViewById(R.id.world_mode_ltctg);
        this.mRadioLttg = (RadioButton) findViewById(R.id.world_mode_lttg);
        this.mRadioLwg = (RadioButton) findViewById(R.id.world_mode_lwg);
        this.mRadioLwcg = (RadioButton) findViewById(R.id.world_mode_lwcg);
        this.mRadioAuto = (RadioButton) findViewById(R.id.world_mode_auto_switch);
        this.mRadioGroup = (RadioGroup) findViewById(R.id.world_mode_radio_group);
        this.mButtonSet = (Button) findViewById(R.id.world_mode_set);
        this.mTextCurrentMode = (TextView) findViewById(R.id.world_mode_current_value);
        this.mTextMainProtocol = (TextView) findViewById(R.id.world_mode_main_protocol);
        TextView textView = (TextView) findViewById(R.id.world_mode_hint);
        this.mTextWorldModeHint = textView;
        textView.setText(R.string.world_mode_hint_aosp);
        this.mValue = (EditText) findViewById(R.id.world_mode_value);
        this.mButtonSetValue = (Button) findViewById(R.id.world_mode_set_value);
        if (is6m()) {
            this.mRadioLwg.setVisibility(8);
            this.mRadioLttg.setVisibility(8);
        } else {
            this.mRadioLtctg.setVisibility(8);
            this.mRadioLwcg.setVisibility(8);
        }
        if (true == IS_NR_SUPPORT) {
            this.mRadioLtctg.setVisibility(8);
            this.mRadioLttg.setVisibility(8);
            this.mRadioLwg.setVisibility(8);
            this.mRadioLttg.setVisibility(8);
            this.mRadioLwcg.setVisibility(8);
            this.mRadioAuto.setVisibility(8);
            this.mButtonSet.setVisibility(8);
            this.mTextWorldModeHint.setVisibility(8);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("mediatek.intent.action.ACTION_WORLD_MODE_CHANGED");
        intentFilter.addAction(ACTION_SET_RADIO_CAPABILITY_DONE);
        registerReceiver(this.mBroadcastReceiver, intentFilter);
        IntentFilter intentFilterServiceStateChanged = new IntentFilter();
        intentFilterServiceStateChanged.addAction(ACTION_SERVICE_STATE_CHANGED);
        registerReceiver(this.mBroadcastReceiverServiceStateChanged, intentFilterServiceStateChanged);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        telephonyManager.listen(this.mPhoneStateListener, 8388608);
        updateUi();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (ModemCategory.getCapabilitySim() == 0) {
            this.mTextMainProtocol.setText(String.format("%sSIM1", new Object[]{getResources().getString(R.string.world_mode_main_protocol)}));
        } else if (ModemCategory.getCapabilitySim() == 1) {
            this.mTextMainProtocol.setText(String.format("%sSIM2", new Object[]{getResources().getString(R.string.world_mode_main_protocol)}));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Elog.d(TAG, "onDestroy()");
        unregisterReceiver(this.mBroadcastReceiver);
        unregisterReceiver(this.mBroadcastReceiverServiceStateChanged);
        this.mResponseHander.removeCallbacksAndMessages((Object) null);
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void updateUi() {
        String modeString;
        int mode = getWorldMode();
        int currentMode = WorldModeUtil.get3GDivisionDuplexMode();
        Elog.d(TAG, "MTK Get world mode: " + mode);
        if (true == IS_NR_SUPPORT) {
            modeString = WorldModeUtil.worldModeIdToString(mode);
        } else if (mode == 10 || mode == 12) {
            String modeString2 = getString(R.string.world_mode_auto);
            if (is6m()) {
                modeString = modeString2 + "(" + this.mWorldModeFor6M[currentMode] + ")";
            } else {
                modeString = modeString2 + "(" + this.mWorldMode[currentMode] + ")";
            }
            this.mRadioAuto.setChecked(true);
        } else if (mode == 8) {
            modeString = getString(R.string.world_mode_ltg);
            this.mRadioGroup.check(-1);
        } else if (mode == 9) {
            modeString = getString(R.string.world_mode_lwg);
            this.mRadioLwg.setChecked(true);
        } else if (mode == 13) {
            modeString = getString(R.string.world_mode_lttg);
            this.mRadioLttg.setChecked(true);
        } else if (mode == 17) {
            modeString = getString(R.string.world_mode_ltctg);
            this.mRadioLtctg.setChecked(true);
        } else if (mode == 11) {
            modeString = getString(R.string.world_mode_lwcg);
            this.mRadioLwcg.setChecked(true);
        } else if (mode == 14) {
            modeString = getString(R.string.world_mode_lfwg);
            this.mRadioGroup.check(-1);
        } else {
            modeString = this.mWorldMode[0];
            this.mRadioGroup.check(-1);
        }
        this.mTextCurrentMode.setText(getString(R.string.world_mode_current_value) + modeString);
        this.mValue.setText(String.valueOf(mode));
        mOldMode = mode;
    }

    public void onClick(View v) {
        if (v == this.mButtonSetValue) {
            try {
                mMode = Integer.parseInt(this.mValue.getText().toString());
            } catch (NumberFormatException e) {
                Elog.w(TAG, "Invalid format: " + this.mValue.getText());
                showToast("Invalid value");
            }
        } else if (v == this.mButtonSet) {
            if (this.mRadioLwg.isChecked()) {
                mMode = 9;
            } else if (this.mRadioLttg.isChecked()) {
                mMode = 13;
            } else if (this.mRadioAuto.isChecked()) {
                if (is6m()) {
                    mMode = 12;
                } else {
                    mMode = 10;
                }
            } else if (this.mRadioLtctg.isChecked()) {
                mMode = 17;
            } else if (this.mRadioLwcg.isChecked()) {
                mMode = 11;
            } else {
                return;
            }
        }
        if (mOldMode != mMode) {
            mBandMode = 0;
            queryCurrentBandModeAndSetNewWorldMode();
            return;
        }
        Elog.i(TAG, "The mode is not changed!");
    }

    public void startTimer() {
        AnonymousClass5 r0 = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinishied) {
                Elog.d(WorldModeActivity.TAG, "WAIT_TIME = 15");
                Elog.d(WorldModeActivity.TAG, "millisUntilFinishied = " + millisUntilFinishied);
            }

            public void onFinish() {
                Elog.d(WorldModeActivity.TAG, "millisUntilFinishied finish");
                WorldModeActivity.this.mProgressDialog.dismiss();
                WorldModeActivity.this.timer.cancel();
                CountDownTimer unused = WorldModeActivity.this.timer = null;
            }
        };
        this.timer = r0;
        r0.start();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle("Hint").setMessage("This will cost several seconds,\nUI will hang during reboot RF!").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            int ret = 101;
                            int radioState = WorldModeActivity.this.mTelephonyManager.getRadioPowerState();
                            if (radioState != 2) {
                                ret = WorldModeUtil.setWorldModeWithBand(WorldModeActivity.mMode, WorldModeActivity.mBandMode);
                            }
                            Elog.i(WorldModeActivity.TAG, "set world mode radioState:" + radioState + ", ret:" + ret);
                            if (ret == 100) {
                                int unused = WorldModeActivity.mSetWorldMode = 1;
                                WorldModeActivity.this.startTimer();
                                WorldModeActivity.this.showDialog(1);
                            } else if (ret == 102) {
                                WorldModeActivity.this.showDialog(2);
                            } else if (ret == 101) {
                                WorldModeActivity.this.showDialog(3);
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
            case 1:
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.mProgressDialog = progressDialog;
                progressDialog.setTitle("Waiting");
                this.mProgressDialog.setMessage("Waiting rf reboot");
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setIndeterminate(true);
                Elog.d(TAG, "After timer.start(");
                return this.mProgressDialog;
            case 2:
                return new AlertDialog.Builder(this).setTitle("Unsupport").setMessage("The band is not support the world mode id!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle("Error").setMessage("The world mode is set error!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private void queryCurrentBandModeAndSetNewWorldMode() {
        String[] modeString = {BandModeContent.QUERY_CURRENT_COMMAND, BandModeContent.SAME_COMMAND};
        Elog.d(TAG, "AT String:" + modeString[0]);
        sendATCommand(modeString, 0);
    }

    private void sendATCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mResponseHander.obtainMessage(msg));
    }

    private int getWorldMode() {
        return WorldModeUtil.getWorldModeId();
    }

    private void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    private boolean is6m() {
        if (ModemCategory.isCdma()) {
            return true;
        }
        return false;
    }
}
