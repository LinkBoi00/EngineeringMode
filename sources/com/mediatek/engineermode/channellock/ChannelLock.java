package com.mediatek.engineermode.channellock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.RadioStateManager;

public class ChannelLock extends Activity implements View.OnClickListener {
    private static final int MSG_CHANNEL_LOCK = 1;
    private static final int MSG_QUERY_CHANNEL_LOCK = 2;
    private static final int PROGRESS_DIALOG = 2001;
    private static final int REBOOT_DIALOG = 2000;
    private static final String TAG = "ChannelLock";
    private final Handler mATCmdHander = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 1:
                    if (ar.exception != null) {
                        Elog.i(ChannelLock.TAG, "Failed to set Channel Lock");
                        EmUtils.showToast("Failed to set Channel Lock");
                        return;
                    }
                    Elog.i(ChannelLock.TAG, "set Channel Lock success");
                    EmUtils.showToast("set Channel Lock success");
                    ChannelLock.this.showDialog(2000);
                    return;
                case 2:
                    if (ar.exception == null) {
                        Elog.i(ChannelLock.TAG, "Query success.");
                        String[] data = (String[]) ar.result;
                        if (data == null) {
                            EmUtils.showToast("The returned data is wrong.");
                            Elog.e(ChannelLock.TAG, "The returned data is wrong.");
                            return;
                        }
                        ChannelLock.this.handleQuery(data);
                        return;
                    }
                    EmUtils.showToast("Query failed.");
                    Elog.d(ChannelLock.TAG, "Query failed.");
                    return;
                default:
                    return;
            }
        }
    };
    private String mEMMCHLCKcommand = "AT+EMMCHLCK=";
    private RadioButton mRadioGSM1900NoButton;
    private RadioButton mRadioGSM1900YesButton;
    private RadioStateManager.RadioListener mRadioListener = new RadioStateManager.RadioListener() {
        public void onRadioPowerOff() {
            Elog.v(ChannelLock.TAG, "onRadioPowerOff");
            ChannelLock.this.mRadioStateManager.setAirplaneModeEnabled(false);
        }

        public void onRadioPowerOn() {
            Elog.v(ChannelLock.TAG, "onRadioPowerOn");
            ChannelLock.this.mSetButtonReset.setEnabled(true);
            if (ChannelLock.this.mResetApModeDialog != null) {
                ChannelLock.this.mResetApModeDialog.dismiss();
            }
            EmUtils.showToast("Reset the airplane Mode succeed,please try");
        }
    };
    private RadioButton mRadioLockDisableButton;
    private RadioButton mRadioLockEnableButton;
    private RadioButton mRadioLockModeIdleConnected;
    private RadioButton mRadioLockModeIdleOnly;
    private RadioButton mRadioLockModeUnchanged;
    private RadioButton mRadioRatGSMButton;
    private RadioButton mRadioRatLTEButton;
    private RadioButton mRadioRatNRButton;
    private RadioButton mRadioRatUMTSButton;
    /* access modifiers changed from: private */
    public RadioStateManager mRadioStateManager;
    /* access modifiers changed from: private */
    public ProgressDialog mResetApModeDialog;
    private Button mSetButtonApply;
    /* access modifiers changed from: private */
    public Button mSetButtonReset;
    private int mSimType;
    private EditText mTextARFCNnumber;
    private EditText mTextCELLIDNnumber;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_lock);
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        Elog.d(TAG, "mSimType: " + this.mSimType);
        this.mSetButtonApply = (Button) findViewById(R.id.channel_lock_apply_button);
        Button button = (Button) findViewById(R.id.channellock_reset_button);
        this.mSetButtonReset = button;
        button.setOnClickListener(this);
        this.mSetButtonApply.setOnClickListener(this);
        this.mRadioLockEnableButton = (RadioButton) findViewById(R.id.channel_lock_lock_enable_radio);
        this.mRadioLockDisableButton = (RadioButton) findViewById(R.id.channel_lock_lock_disable_radio);
        this.mRadioRatGSMButton = (RadioButton) findViewById(R.id.rat_gsm_radio);
        this.mRadioRatUMTSButton = (RadioButton) findViewById(R.id.rat_umts_radio);
        this.mRadioRatLTEButton = (RadioButton) findViewById(R.id.rat_lte_radio);
        this.mRadioRatNRButton = (RadioButton) findViewById(R.id.rat_nr_radio);
        this.mRadioGSM1900YesButton = (RadioButton) findViewById(R.id.gsm1900_yes_radio);
        this.mRadioGSM1900NoButton = (RadioButton) findViewById(R.id.gsm1900_no_radio);
        this.mTextARFCNnumber = (EditText) findViewById(R.id.channel_lock_ARCFN_number_text);
        this.mTextCELLIDNnumber = (EditText) findViewById(R.id.channel_lock_cellid_number_text);
        this.mRadioLockModeUnchanged = (RadioButton) findViewById(R.id.lock_mode_unchanged);
        this.mRadioLockModeIdleOnly = (RadioButton) findViewById(R.id.lock_mode_idle_only);
        this.mRadioLockModeIdleConnected = (RadioButton) findViewById(R.id.lock_mode_idle_connected);
        RadioStateManager radioStateManager = new RadioStateManager(this);
        this.mRadioStateManager = radioStateManager;
        radioStateManager.registerRadioStateChanged(this.mRadioListener);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        sendATCommand(new String[]{"AT+EMMCHLCK?", "+EMMCHLCK:"}, 2);
    }

    public void onDestroy() {
        Elog.v(TAG, "onDestroy");
        this.mRadioStateManager.unregisterRadioStateChanged();
        super.onDestroy();
    }

    public void onClick(View arg0) {
        this.mEMMCHLCKcommand = "AT+EMMCHLCK=";
        if (arg0 == this.mSetButtonReset) {
            this.mRadioStateManager.setAirplaneModeEnabled(true);
            this.mSetButtonReset.setEnabled(false);
            showDialog(2001);
        } else if (arg0 == this.mSetButtonApply) {
            if (this.mRadioLockDisableButton.isChecked()) {
                this.mEMMCHLCKcommand += "0";
            } else if (this.mRadioLockEnableButton.isChecked()) {
                this.mEMMCHLCKcommand += "1,";
                if (this.mRadioRatGSMButton.isChecked()) {
                    this.mEMMCHLCKcommand += "0,";
                } else if (this.mRadioRatUMTSButton.isChecked()) {
                    this.mEMMCHLCKcommand += "2,";
                } else if (this.mRadioRatLTEButton.isChecked()) {
                    this.mEMMCHLCKcommand += "7,";
                } else if (this.mRadioRatNRButton.isChecked()) {
                    this.mEMMCHLCKcommand += "11,";
                } else {
                    EmUtils.showToast("ERROR in RAT");
                }
                if (this.mRadioGSM1900YesButton.isChecked()) {
                    this.mEMMCHLCKcommand += "1,";
                } else if (this.mRadioGSM1900NoButton.isChecked()) {
                    this.mEMMCHLCKcommand += "0,";
                } else {
                    EmUtils.showToast("ERROR in GSM1900");
                }
                this.mEMMCHLCKcommand += this.mTextARFCNnumber.getText();
                String cellIdNnumber = this.mTextCELLIDNnumber.getText().toString();
                if (!this.mRadioLockModeUnchanged.isChecked()) {
                    if (this.mRadioLockModeIdleOnly.isChecked()) {
                        this.mEMMCHLCKcommand += "," + cellIdNnumber + ",0";
                    } else if (this.mRadioLockModeIdleConnected.isChecked()) {
                        this.mEMMCHLCKcommand += "," + cellIdNnumber + ",3";
                    }
                } else if (cellIdNnumber != null && !cellIdNnumber.isEmpty()) {
                    this.mEMMCHLCKcommand += "," + cellIdNnumber;
                }
            } else {
                EmUtils.showToast("ERROR in Lock");
            }
            sendATCommand(new String[]{this.mEMMCHLCKcommand, ""}, 1);
        }
    }

    private void sendATCommand(String[] atCommand, int msg) {
        Elog.d(TAG, "sendATCommand " + atCommand[0]);
        EmUtils.invokeOemRilRequestStringsEm(this.mSimType, atCommand, this.mATCmdHander.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public void handleQuery(String[] data) {
        Elog.v(TAG, "data = " + data[0]);
        String str = data[0].substring("+EMMCHLCK:".length()).trim();
        Elog.d(TAG, "response value:" + str);
        String[] arrayval = str.split(",");
        int lockStatus = 0;
        int ret = 0;
        int gsm1900 = 0;
        int arfcn = 0;
        int cellId = 0;
        int lockMode = 0;
        try {
            lockStatus = Integer.parseInt(arrayval[0]);
            Elog.v(TAG, "lockStatus = " + lockStatus);
            if (lockStatus != 0) {
                ret = Integer.parseInt(arrayval[1]);
                gsm1900 = Integer.parseInt(arrayval[2]);
                arfcn = Integer.parseInt(arrayval[3]);
                cellId = Integer.parseInt(arrayval[4]);
                lockMode = Integer.parseInt(arrayval[5]);
                Elog.v(TAG, "ret = " + ret);
                Elog.v(TAG, "gsm1900 = " + gsm1900);
                Elog.v(TAG, "arfcn = " + arfcn);
                Elog.v(TAG, "cellId = " + cellId);
                Elog.v(TAG, "lockMode = " + lockMode);
            }
        } catch (Exception e) {
            Elog.e(TAG, "response value parse failed," + e.getMessage());
        }
        if (lockStatus == 0) {
            this.mRadioLockDisableButton.setChecked(true);
        } else if (lockStatus == 1) {
            this.mRadioLockEnableButton.setChecked(true);
            if (ret == 0) {
                this.mRadioRatGSMButton.setChecked(true);
            } else if (ret == 2) {
                this.mRadioRatUMTSButton.setChecked(true);
            } else if (ret == 7) {
                this.mRadioRatLTEButton.setChecked(true);
            } else if (ret == 11) {
                this.mRadioRatNRButton.setChecked(true);
            } else {
                Elog.e(TAG, "Invalid Channel Lock RAT value");
            }
            if (gsm1900 == 1) {
                this.mRadioGSM1900YesButton.setChecked(true);
            } else if (gsm1900 == 0) {
                this.mRadioGSM1900NoButton.setChecked(true);
            } else {
                Elog.e(TAG, "Invalid Channel Lock GSM1900 value");
            }
            this.mTextARFCNnumber.setText(String.valueOf(arfcn));
            this.mTextCELLIDNnumber.setText(String.valueOf(cellId));
            if (lockMode == 0) {
                this.mRadioLockModeIdleOnly.setChecked(true);
            } else if (lockMode == 3) {
                this.mRadioLockModeIdleConnected.setChecked(true);
            } else {
                Elog.e(TAG, "Invalid Channel Lock mode value");
            }
        } else {
            Elog.e(TAG, "Invalid Channel Lock value");
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 2000:
                return new AlertDialog.Builder(this).setTitle("Channel lock:").setMessage("Please click Reset by Airplane Mode button to take effect!").setPositiveButton("OK", (DialogInterface.OnClickListener) null).create();
            case 2001:
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.mResetApModeDialog = progressDialog;
                progressDialog.setTitle("Waiting");
                this.mResetApModeDialog.setMessage("Please wait until Airplane Mode reset succeed");
                this.mResetApModeDialog.setCancelable(false);
                this.mResetApModeDialog.setIndeterminate(true);
                return this.mResetApModeDialog;
            default:
                return null;
        }
    }
}
