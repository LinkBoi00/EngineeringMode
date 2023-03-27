package com.mediatek.engineermode.anttunerdebug;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class AntTunerDebugMIPI extends Activity implements View.OnClickListener {
    private static final int DIALOG_NOTICE = 0;
    private static final String MIPI_CHECK_FALSE_CMD = "AT+ERFTX=12,0,1,0,1,0,0,0";
    private static final String MIPI_CHECK_TRUE_CMD = "AT+ERFTX=12,0,1,0,1,0,0,1";
    private static final int MIPI_NUM = 3;
    public static final int OP_MIPI_CHECK = 8;
    private static final int OP_MIPI_NON_SIGNALING = 7;
    public static final int OP_MIPI_READ = 0;
    public static final int OP_MIPI_READ1 = 1;
    public static final int OP_MIPI_READ2 = 2;
    private static final int OP_MIPI_SIGNALING = 6;
    public static final int OP_MIPI_WRITE = 3;
    public static final int OP_MIPI_WRITE1 = 4;
    public static final int OP_MIPI_WRITE2 = 5;
    private static final String RESPONSE_CMD = "+ERFTX: ";
    private static final String STR_OP_CHECK = "op_checked";
    private static final String TAG = "AntTunerDebugMIPI";
    ArrayAdapter<CharSequence> adapterPattern = null;
    private final Handler mATHandler = new Handler() {
        private String[] mReturnData = new String[2];

        public void handleMessage(Message msg) {
            if (msg.what >= 0 && msg.what < 3) {
                int index = msg.what - 0;
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugMIPI.TAG, "MIPI read" + index + " successful.");
                    this.mReturnData = (String[]) ar.result;
                    Elog.d(AntTunerDebugMIPI.TAG, "mReturnData = " + this.mReturnData[0]);
                    EmUtils.showToast(this.mReturnData[0], 0);
                    AntTunerDebugMIPI.this.handleQuery(this.mReturnData, index);
                    return;
                }
                EmUtils.showToast("MIPI read" + index + " failed.", 0);
                Elog.e(AntTunerDebugMIPI.TAG, "MIPI read" + index + " failed.");
            } else if (msg.what >= 3 && msg.what < 6) {
                int index2 = msg.what - 3;
                if (((AsyncResult) msg.obj).exception == null) {
                    Elog.d(AntTunerDebugMIPI.TAG, "MIPI write" + index2 + " successful.");
                    return;
                }
                EmUtils.showToast("MIPI write" + index2 + " failed.", 0);
                Elog.e(AntTunerDebugMIPI.TAG, "MIPI write" + index2 + " failed.");
            } else if (msg.what == 6) {
                if (((AsyncResult) msg.obj).exception == null) {
                    Elog.d(AntTunerDebugMIPI.TAG, "MIPI Signaling successful.");
                    return;
                }
                EmUtils.showToast("MIPI Signaling failed.", 0);
                Elog.e(AntTunerDebugMIPI.TAG, "MIPI Signaling failed.");
            } else if (msg.what == 7) {
                if (((AsyncResult) msg.obj).exception == null) {
                    Elog.d(AntTunerDebugMIPI.TAG, "MIPI Non-Signaling successful.");
                    return;
                }
                EmUtils.showToast("MIPI Non-Signaling failed.", 0);
                Elog.e(AntTunerDebugMIPI.TAG, "MIPI Non-Signaling failed.");
            } else if (msg.what != 8) {
            } else {
                if (((AsyncResult) msg.obj).exception == null) {
                    Elog.d(AntTunerDebugMIPI.TAG, "MIPI set  successful.");
                    AntTunerDebugMIPI.this.showDialog(0);
                    return;
                }
                EmUtils.showToast("MIPI Check set failed.", 0);
                Elog.e(AntTunerDebugMIPI.TAG, "MIPI Check set  failed.");
            }
        }
    };
    private String[] mAddress = new String[3];
    private Button mBtnMipiRead;
    private Button mBtnMipiWrite;
    private CheckBox mCbMipi;
    private String[] mData = new String[3];
    private EditText[] mEdAddress = new EditText[3];
    private EditText[] mEdData = new EditText[3];
    private EditText[] mEdMipiPort = new EditText[3];
    private EditText[] mEdUsid = new EditText[3];
    private String mMipiMode = null;
    private String[] mPort = new String[3];
    /* access modifiers changed from: private */
    public SharedPreferences mPref;
    private String mRWType = null;
    private Spinner mSpRWType;
    private TextView mTvAddress;
    private String[] mUsid = new String[3];
    private Button mbtnMipiNonSignaling;
    private Button mbtnMipiSignaling;

    /* access modifiers changed from: private */
    public void handleQuery(String[] result, int index) {
        if (result == null || result.length <= 0 || index >= 3) {
            Elog.e(TAG, "Modem return error");
            return;
        }
        Elog.i(TAG, "Modem return: " + result[0]);
        String[] values = result[0].substring(RESPONSE_CMD.length(), result[0].length()).trim().split(",");
        if (values != null && values.length > 0 && values[2] != null) {
            values[2] = Integer.toHexString(Integer.parseInt(values[2]));
            this.mEdData[index].setText(values[2]);
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.c2k_ir_dialog);
                builder.setCancelable(false);
                builder.setMessage(getString(R.string.c2k_ir_notice));
                builder.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                return builder.create();
            default:
                Elog.d(TAG, "error dialog ID");
                return null;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ant_tuner_debug_mipi);
        this.mSpRWType = (Spinner) findViewById(R.id.ant_tuner_debug_mipi_rw_type);
        this.mCbMipi = (CheckBox) findViewById(R.id.mipi_check);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(this, R.array.ant_tuner_debug_mipi_rw_type, 17367048);
        this.adapterPattern = createFromResource;
        createFromResource.setDropDownViewResource(17367049);
        this.mSpRWType.setAdapter(this.adapterPattern);
        this.mEdMipiPort[0] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_port);
        this.mEdUsid[0] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_usid);
        this.mEdAddress[0] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_address);
        this.mEdData[0] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_data);
        this.mEdMipiPort[1] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_port1);
        this.mEdUsid[1] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_usid1);
        this.mEdAddress[1] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_address1);
        this.mEdData[1] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_data1);
        this.mEdMipiPort[2] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_port2);
        this.mEdUsid[2] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_usid2);
        this.mEdAddress[2] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_address2);
        this.mEdData[2] = (EditText) findViewById(R.id.ant_tuner_debug_mipi_data2);
        this.mTvAddress = (TextView) findViewById(R.id.ant_tuner_debug_mipi_address_tv);
        this.mBtnMipiRead = (Button) findViewById(R.id.ant_tuner_debug_mipi_read);
        this.mBtnMipiWrite = (Button) findViewById(R.id.ant_tuner_debug_mipi_write);
        this.mBtnMipiRead.setOnClickListener(this);
        this.mBtnMipiWrite.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(TAG, 0);
        this.mPref = sharedPreferences;
        this.mCbMipi.setChecked(sharedPreferences.getBoolean(STR_OP_CHECK, false));
        this.mCbMipi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = AntTunerDebugMIPI.this.mPref.edit();
                editor.putBoolean(AntTunerDebugMIPI.STR_OP_CHECK, isChecked);
                editor.commit();
                String[] cmd = new String[2];
                cmd[0] = isChecked ? AntTunerDebugMIPI.MIPI_CHECK_TRUE_CMD : AntTunerDebugMIPI.MIPI_CHECK_FALSE_CMD;
                cmd[1] = "+ERFTX:";
                AntTunerDebugMIPI.this.sendAtCommand(cmd, 8);
            }
        });
        this.mbtnMipiSignaling = (Button) findViewById(R.id.mipi_signaling);
        this.mbtnMipiNonSignaling = (Button) findViewById(R.id.mipi_non_signaling);
        this.mbtnMipiSignaling.setOnClickListener(this);
        this.mbtnMipiNonSignaling.setOnClickListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void readMIPI() {
        for (int i = 0; i < 3; i++) {
            if (!valueGetAndCheck(0, i)) {
                Elog.d(TAG, "value check fail " + i + ", read MIPI return");
            } else {
                String[] strArr = this.mUsid;
                strArr[i] = Integer.toString(Integer.parseInt(strArr[i], 16));
                String[] strArr2 = this.mAddress;
                strArr2[i] = Integer.toString(Integer.parseInt(strArr2[i], 16));
                sendAtCommand(new String[]{"AT+ERFTX=12,0," + this.mMipiMode + "," + this.mPort[i] + "," + this.mRWType + "," + this.mUsid[i] + "," + this.mAddress[i], "+ERFTX:"}, i + 0);
            }
        }
    }

    public void writeMIPI() {
        for (int i = 0; i < 3; i++) {
            if (!valueGetAndCheck(1, i)) {
                Elog.d(TAG, "value[" + i + "] check fail , write MIPI return");
            } else {
                String[] strArr = this.mUsid;
                strArr[i] = Integer.toString(Integer.parseInt(strArr[i], 16));
                String[] strArr2 = this.mAddress;
                strArr2[i] = Integer.toString(Integer.parseInt(strArr2[i], 16));
                String[] strArr3 = this.mData;
                strArr3[i] = Integer.toString(Integer.parseInt(strArr3[i], 16));
                sendAtCommand(new String[]{"AT+ERFTX=12,0," + this.mMipiMode + "," + this.mPort[i] + "," + this.mRWType + "," + this.mUsid[i] + "," + this.mAddress[i] + "," + this.mData[i], ""}, i + 3);
            }
        }
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ant_tuner_debug_mipi_read:
                readMIPI();
                return;
            case R.id.ant_tuner_debug_mipi_write:
                writeMIPI();
                return;
            case R.id.mipi_non_signaling:
                sendAtCommand(new String[]{"AT+CFUN=0", ""}, 7);
                sendAtCommand(new String[]{"AT+EGCMD=53", ""}, 7);
                return;
            case R.id.mipi_signaling:
                sendAtCommand(new String[]{"AT+CFUN=1", ""}, 6);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand() " + command[0]);
        EmUtils.showToast("sendAtCommand: " + command[0], 0);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mATHandler.obtainMessage(msg));
    }

    private boolean valueGetAndCheck(int flag, int index) {
        this.mPort[index] = this.mEdMipiPort[index].getText().toString();
        this.mRWType = this.mSpRWType.getSelectedItemPosition() == 0 ? "0" : "1";
        this.mUsid[index] = this.mEdUsid[index].getText().toString();
        this.mAddress[index] = this.mEdAddress[index].getText().toString();
        this.mData[index] = this.mEdData[index].getText().toString();
        this.mMipiMode = flag == 0 ? "0" : "1";
        if (this.mPort[index].equals("") && this.mUsid[index].equals("") && this.mAddress[index].equals("") && (flag == 0 || this.mData[index].equals(""))) {
            return false;
        }
        if (this.mPort[index].equals("")) {
            EmUtils.showToast("MIPI port should not be empty", 0);
            return false;
        } else if (this.mRWType.equals("")) {
            EmUtils.showToast("RW_TYPE should not be empty", 0);
            return false;
        } else if (this.mUsid[index].equals("")) {
            EmUtils.showToast("USID should not be empty", 0);
            return false;
        } else if (this.mAddress[index].equals("")) {
            EmUtils.showToast("Address should not be empty", 0);
            return false;
        } else {
            try {
                int value = Integer.parseInt(this.mUsid[index], 16);
                if (value < 0 || value > 15) {
                    EmUtils.showToast("Usid should be 0x0~0xf ", 0);
                    return false;
                }
                try {
                    int value2 = Integer.parseInt(this.mAddress[index], 16);
                    if (this.mRWType.equals("0")) {
                        this.mTvAddress.setText(getString(R.string.ant_tuner_debug_mipi_address));
                        if (value2 < 0 || value2 > 31) {
                            EmUtils.showToast("mAddress should be 0x0~0x1f ", 0);
                            return false;
                        }
                    } else if (this.mRWType.equals("1")) {
                        this.mTvAddress.setText(getString(R.string.ant_tuner_debug_mipi_address1));
                        if (value2 < 0 || value2 > 255) {
                            EmUtils.showToast("mAddress should be 0x0~0xff ", 0);
                            return false;
                        }
                    }
                    if (!this.mMipiMode.equals("1")) {
                        return true;
                    }
                    if (this.mData[index].equals("")) {
                        EmUtils.showToast("Data should not be empty", 0);
                        return false;
                    }
                    try {
                        int value3 = Integer.parseInt(this.mData[index], 16);
                        if (value3 >= 0) {
                            if (value3 <= 255) {
                                return true;
                            }
                        }
                        EmUtils.showToast("Usid should be 0x0~0xff ", 0);
                        return false;
                    } catch (NumberFormatException e) {
                        EmUtils.showToast("mData should be 16 HEX", 0);
                        return false;
                    }
                } catch (NumberFormatException e2) {
                    EmUtils.showToast("mAddress should be 16 HEX", 0);
                    return false;
                }
            } catch (NumberFormatException e3) {
                EmUtils.showToast("Usid should be 16 HEX", 0);
                return false;
            }
        }
    }
}
