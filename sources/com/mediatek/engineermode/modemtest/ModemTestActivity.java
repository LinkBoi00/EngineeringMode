package com.mediatek.engineermode.modemtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;

public class ModemTestActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final int ATTACH_MODE_NOT_SPECIFY = -1;
    private static final int CMD_LENGTH = 6;
    private static final int DOCOMO_OPTION = 128;
    private static final int FLAG_NOT_DETECT_CDMA_CARD = 1048576;
    private static final int FLAG_UNLOCK = 2097152;
    private static final int INDEX_SPIRENT = 1;
    private static final int MODEM_CDMA = 8;
    private static final int MODEM_CLSC = 10;
    private static final int MODEM_CTA = 1;
    private static final int MODEM_FACTORY = 6;
    private static final int MODEM_FTA = 2;
    private static final int MODEM_IOT = 3;
    private static final int MODEM_LAA = 12;
    private static final int MODEM_NONE = 0;
    private static final int MODEM_OPERATOR = 5;
    private static final int MODEM_QUERY = 4;
    private static final int MODEM_QUERY_CDMA = 7;
    private static final int MODEM_QUERY_CLSC = 9;
    private static final int MODEM_QUERY_LAA = 11;
    private static final int MODE_LENGTH = 3;
    private static final int PCH_CALL_PREFER = 1;
    private static final int PCH_DATA_PREFER = 0;
    private static final int REBOOT_DIALOG = 2000;
    private static final int SOFTBANK_OPTION = 256;
    public static final String TAG = "ModemTest";
    private final Handler mATCmdHander = new Handler() {
        public void handleMessage(Message msg) {
            boolean rebootFlag = false;
            switch (msg.what) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                    if (((AsyncResult) msg.obj).exception != null) {
                        EmUtils.showToast(ModemTestActivity.this.getToastString(msg.what) + " AT cmd failed.");
                        break;
                    } else {
                        EmUtils.showToast(ModemTestActivity.this.getToastString(msg.what) + " AT cmd success.");
                        rebootFlag = true;
                        break;
                    }
                case 4:
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception != null) {
                        EmUtils.showToast(" Query failed.");
                        break;
                    } else {
                        Elog.d(ModemTestActivity.TAG, "Query success.");
                        ModemTestActivity.this.handleQuery((String[]) ar.result);
                        if (ModemCategory.isCdma()) {
                            ModemTestActivity.this.queryCdmaOption();
                            ModemTestActivity.this.queryUnlockOption();
                            break;
                        }
                    }
                    break;
                case 7:
                    AsyncResult ar2 = (AsyncResult) msg.obj;
                    if (ar2.exception != null) {
                        EmUtils.showToast(" Query failed.");
                        break;
                    } else {
                        Elog.i(ModemTestActivity.TAG, "Query success.");
                        ModemTestActivity.this.handleQueryCdma((String[]) ar2.result);
                        break;
                    }
                case 8:
                    if (((AsyncResult) msg.obj).exception != null) {
                        EmUtils.showToast("AT cmd failed.");
                        break;
                    } else {
                        EmUtils.showToast("AT cmd success.");
                        rebootFlag = true;
                        break;
                    }
                case 9:
                    AsyncResult ar3 = (AsyncResult) msg.obj;
                    if (ar3.exception != null) {
                        EmUtils.showToast("Query failed.");
                        break;
                    } else {
                        Elog.i(ModemTestActivity.TAG, "Query success.");
                        ModemTestActivity.this.handleQueryUnlock((String[]) ar3.result);
                        break;
                    }
                case 10:
                    if (((AsyncResult) msg.obj).exception != null) {
                        EmUtils.showToast("AT cmd failed.");
                        break;
                    } else {
                        EmUtils.showToast("AT cmd success.");
                        break;
                    }
                case 11:
                    AsyncResult ar4 = (AsyncResult) msg.obj;
                    if (ar4.exception != null) {
                        EmUtils.showToast("Query LAA failed.");
                        break;
                    } else {
                        Elog.i(ModemTestActivity.TAG, "Query LAA success.");
                        ModemTestActivity.this.handleQueryLaa((String[]) ar4.result);
                        break;
                    }
                case 12:
                    if (((AsyncResult) msg.obj).exception != null) {
                        EmUtils.showToast("LAA AT cmd failed.");
                        break;
                    } else {
                        EmUtils.showToast("LAA AT cmd success.");
                        break;
                    }
            }
            if (rebootFlag) {
                Elog.i(ModemTestActivity.TAG, "disableAllButton.");
                ModemTestActivity.this.disableAllButton();
            }
        }
    };
    private Button mC2kBtn;
    /* access modifiers changed from: private */
    public int mCdmaOption = 0;
    private CheckBox mCdmaSimSettingCheckBox;
    private Button mCtaBtn;
    /* access modifiers changed from: private */
    public int mCtaOption = 0;
    private int mCurrentFlag = 0;
    /* access modifiers changed from: private */
    public int mCurrentMode = 0;
    private Button mFactoryBtn;
    private boolean mFirstEntry = true;
    private Button mFtaBtn;
    /* access modifiers changed from: private */
    public int mFtaOption = 0;
    private String[] mFtaOptionsArray;
    private Button mIotBtn;
    /* access modifiers changed from: private */
    public int mIotOption = 0;
    private String[] mIotOptionsArray;
    private CheckBox mLaaCheckBox;
    private Button mNoneBtn;
    private Button mOperatorBtn;
    /* access modifiers changed from: private */
    public int mOperatorOption = 0;
    private String[] mOperatorOptionsArray;
    private TextView mTextView;
    private CheckBox mUnlockCheckBox;

    /* access modifiers changed from: private */
    public String getToastString(int what) {
        switch (what) {
            case 0:
                return "MODEM_NONE";
            case 1:
                return "MODEM_CTA";
            case 2:
                return "MODEM_FTA";
            case 3:
                return "MODEM_IOT";
            case 5:
                return "MODEM_OPERATOR";
            case 6:
                return "MODEM_FACTORY";
            default:
                return "";
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modem_test_activity_6589);
        String[] cmd = {"AT+EPCT?", "+EPCT:"};
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.modem_test_c2k_btn:
                        ModemTestActivity.this.showDialog(8);
                        return;
                    case R.id.modem_test_cta_btn:
                        ModemTestActivity.this.sendATCommad("1", 0, 1);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                            return;
                        }
                        return;
                    case R.id.modem_test_factory_btn:
                        ModemTestActivity.this.sendATCommad("5", 0, 6);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                            return;
                        }
                        return;
                    case R.id.modem_test_fta_btn:
                        ModemTestActivity.this.showDialog(2);
                        return;
                    case R.id.modem_test_iot_btn:
                        ModemTestActivity.this.showDialog(3);
                        return;
                    case R.id.modem_test_none_btn:
                        ModemTestActivity.this.sendATCommad("0", 0, 0);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                            return;
                        }
                        return;
                    case R.id.modem_test_operator_btn:
                        ModemTestActivity.this.showDialog(5);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mTextView = (TextView) findViewById(R.id.modem_test_textview);
        this.mNoneBtn = (Button) findViewById(R.id.modem_test_none_btn);
        Button button = (Button) findViewById(R.id.modem_test_factory_btn);
        this.mFactoryBtn = button;
        button.setOnClickListener(listener);
        Button button2 = (Button) findViewById(R.id.modem_test_cta_btn);
        this.mCtaBtn = button2;
        button2.setOnClickListener(listener);
        this.mFtaBtn = (Button) findViewById(R.id.modem_test_fta_btn);
        this.mIotBtn = (Button) findViewById(R.id.modem_test_iot_btn);
        this.mOperatorBtn = (Button) findViewById(R.id.modem_test_operator_btn);
        this.mC2kBtn = (Button) findViewById(R.id.modem_test_c2k_btn);
        this.mUnlockCheckBox = (CheckBox) findViewById(R.id.modem_test_unlock);
        this.mCdmaSimSettingCheckBox = (CheckBox) findViewById(R.id.modem_test_cdma_sim_setting);
        this.mNoneBtn.setOnClickListener(listener);
        this.mFtaBtn.setOnClickListener(listener);
        this.mIotBtn.setOnClickListener(listener);
        this.mOperatorBtn.setOnClickListener(listener);
        this.mC2kBtn.setOnClickListener(listener);
        this.mUnlockCheckBox.setOnCheckedChangeListener(this);
        this.mCdmaSimSettingCheckBox.setOnCheckedChangeListener(this);
        CheckBox checkBox = (CheckBox) findViewById(R.id.modem_test_laa_setting);
        this.mLaaCheckBox = checkBox;
        checkBox.setOnCheckedChangeListener(this);
        queryLaaSettings();
        this.mTextView.setText("The current mode is unknown");
        this.mFtaOptionsArray = getResources().getStringArray(R.array.modem_test_fta_options);
        this.mIotOptionsArray = getResources().getStringArray(R.array.modem_test_iot_options_6589);
        this.mOperatorOptionsArray = getResources().getStringArray(R.array.modem_test_operator_options_6589);
        if (!ModemCategory.isCdma()) {
            this.mC2kBtn.setVisibility(8);
            this.mUnlockCheckBox.setVisibility(8);
        }
        EmUtils.invokeOemRilRequestStringsEm(cmd, this.mATCmdHander.obtainMessage(4));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mCtaOption = 0;
        this.mIotOption = 0;
        this.mFtaOption = 0;
        this.mOperatorOption = 0;
        this.mCdmaOption = 0;
    }

    /* access modifiers changed from: private */
    public void sendATCommad(String str, int flag, int message) {
        this.mCurrentFlag = (this.mCurrentFlag & 16711680) | flag;
        String[] cmd = {"AT+EPCT=" + str + "," + this.mCurrentFlag, ""};
        StringBuilder sb = new StringBuilder();
        sb.append("cmd = ");
        sb.append(cmd[0]);
        Elog.v(TAG, sb.toString());
        EmUtils.invokeOemRilRequestStringsEm(cmd, this.mATCmdHander.obtainMessage(message));
    }

    /* access modifiers changed from: private */
    public void handleQuery(String[] data) {
        String[] strArr = data;
        if (strArr == null) {
            EmUtils.showToast("The returned data is wrong.");
            return;
        }
        Elog.i(TAG, "data length is " + strArr.length);
        int i = 0;
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = strArr[i2];
            if (str != null) {
                Elog.i(TAG, "data[" + i + "] is : " + str);
            }
            i++;
        }
        if (strArr.length <= 0 || strArr[0] == null || strArr[0].length() <= 6) {
            Elog.i(TAG, "The data returned is not right.");
            return;
        }
        String mode = strArr[0].substring(7, strArr[0].length());
        Elog.i(TAG, "mode is " + mode);
        if (mode.length() >= 3) {
            int i3 = 1;
            String subMode = mode.substring(0, 1);
            String subCtaMode = mode.substring(2, mode.length());
            Elog.i(TAG, "subMode is " + subMode);
            Elog.i(TAG, "subCtaMode is " + subCtaMode);
            this.mCurrentMode = Integer.parseInt(subMode);
            int parseInt = Integer.parseInt(subCtaMode);
            this.mCurrentFlag = parseInt;
            handleQueryCdmaSimSetting(parseInt);
            if ("0".equals(subMode)) {
                this.mTextView.setText("The current mode is none");
            } else if ("1".equals(subMode)) {
                this.mTextView.setText("The current mode is Integrity Off");
            } else if ("2".equals(subMode)) {
                this.mTextView.setText("The current mode is FTA:");
                try {
                    int ftaLength = this.mFtaOptionsArray.length;
                    Elog.i(TAG, "ftaLength is " + ftaLength);
                    int val = Integer.valueOf(subCtaMode).intValue();
                    Elog.i(TAG, "val is " + val);
                    String text = "The current mode is FTA: ";
                    int j = 0;
                    while (j < ftaLength) {
                        Elog.i(TAG, "j ==== " + j);
                        Elog.i(TAG, "(val & (1 << j)) is " + (val & (i3 << j)));
                        if ((val & (1 << j)) != 0) {
                            text = text + this.mFtaOptionsArray[j] + ",";
                        }
                        j++;
                        i3 = 1;
                    }
                    this.mTextView.setText(text.substring(0, text.length() - 1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Elog.i(TAG, "Exception when transfer subFtaMode");
                }
            } else if ("3".equals(subMode)) {
                this.mTextView.setText("The current mode is IOT:");
                try {
                    int iotLength = this.mIotOptionsArray.length;
                    Elog.i(TAG, "iotLength is " + iotLength);
                    int val2 = Integer.valueOf(subCtaMode).intValue();
                    Elog.i(TAG, "val is " + val2);
                    String text2 = "The current mode is IOT: ";
                    for (int j2 = 0; j2 < iotLength - 1; j2++) {
                        Elog.i(TAG, "j ==== " + j2);
                        Elog.i(TAG, "(val & (1 << j)) is " + ((1 << j2) & val2));
                        if (((1 << j2) & val2) != 0) {
                            text2 = text2 + this.mIotOptionsArray[j2 + 1] + ",";
                        }
                    }
                    this.mTextView.setText(text2.substring(0, text2.length() - 1));
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    Elog.i(TAG, "Exception when transfer subIotMode");
                }
            } else if ("4".equals(subMode)) {
                this.mTextView.setText("The current mode is Operator.");
                try {
                    int operatorLength = this.mOperatorOptionsArray.length;
                    Elog.i(TAG, "operatorLength is " + operatorLength);
                    int val3 = Integer.valueOf(subCtaMode).intValue();
                    Elog.i(TAG, "val is " + val3);
                    String text3 = "The current mode is Operator: ";
                    for (int j3 = 0; j3 < operatorLength; j3++) {
                        Elog.i(TAG, "j ==== " + j3);
                        Elog.i(TAG, "(val & (1 << j)) is " + (val3 & (1 << j3)));
                        if ((val3 & (1 << j3)) != 0) {
                            text3 = text3 + this.mOperatorOptionsArray[j3] + ",";
                        }
                    }
                    this.mTextView.setText(text3.substring(0, text3.length() - 1));
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                    Elog.i(TAG, "Exception when transfer subFtaMode");
                }
            } else if ("5".equals(subMode)) {
                this.mTextView.setText("The current mode is Factory.");
            }
        } else {
            Elog.i(TAG, "mode len is " + mode.length());
        }
    }

    /* access modifiers changed from: private */
    public void disableAllButton() {
        this.mNoneBtn.setEnabled(false);
        this.mFactoryBtn.setEnabled(false);
        this.mCtaBtn.setEnabled(false);
        this.mFtaBtn.setEnabled(false);
        this.mIotBtn.setEnabled(false);
        this.mOperatorBtn.setEnabled(false);
        this.mC2kBtn.setEnabled(false);
        this.mUnlockCheckBox.setEnabled(false);
        showDialog(2000);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setMultiChoiceItems(R.array.modem_test_cta_options, new boolean[]{false, false, false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                        Elog.v(ModemTestActivity.TAG, "whichButton = " + whichButton);
                        Elog.v(ModemTestActivity.TAG, "isChecked = " + isChecked);
                        if (isChecked) {
                            ModemTestActivity modemTestActivity = ModemTestActivity.this;
                            int unused = modemTestActivity.mCtaOption = modemTestActivity.mCtaOption + (1 << whichButton);
                        } else {
                            ModemTestActivity modemTestActivity2 = ModemTestActivity.this;
                            int unused2 = modemTestActivity2.mCtaOption = modemTestActivity2.mCtaOption - (1 << whichButton);
                        }
                        Elog.v(ModemTestActivity.TAG, "mCtaOption = " + ModemTestActivity.this.mCtaOption);
                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModemTestActivity modemTestActivity = ModemTestActivity.this;
                        modemTestActivity.sendATCommad("1", modemTestActivity.mCtaOption, 1);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int unused = ModemTestActivity.this.mCtaOption = 0;
                    }
                }).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setMultiChoiceItems(R.array.modem_test_fta_options, new boolean[]{false, false, false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                        Elog.v(ModemTestActivity.TAG, "whichButton = " + whichButton);
                        Elog.v(ModemTestActivity.TAG, "isChecked = " + isChecked);
                        if (isChecked) {
                            ModemTestActivity modemTestActivity = ModemTestActivity.this;
                            int unused = modemTestActivity.mFtaOption = modemTestActivity.mFtaOption + (1 << whichButton);
                        } else {
                            ModemTestActivity modemTestActivity2 = ModemTestActivity.this;
                            int unused2 = modemTestActivity2.mFtaOption = modemTestActivity2.mFtaOption - (1 << whichButton);
                        }
                        Elog.v(ModemTestActivity.TAG, "mFtaOption = " + ModemTestActivity.this.mFtaOption);
                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModemTestActivity modemTestActivity = ModemTestActivity.this;
                        modemTestActivity.sendATCommad("2", modemTestActivity.mFtaOption, 2);
                        ModemTestActivity.this.setGprsTransferType(0);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int unused = ModemTestActivity.this.mFtaOption = 0;
                    }
                }).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setMultiChoiceItems(R.array.modem_test_iot_options_6589, new boolean[]{false, false, false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                        Elog.v(ModemTestActivity.TAG, "whichButton = " + whichButton);
                        Elog.v(ModemTestActivity.TAG, "isChecked = " + isChecked);
                        if (whichButton > 0) {
                            if (isChecked) {
                                ModemTestActivity modemTestActivity = ModemTestActivity.this;
                                int unused = modemTestActivity.mIotOption = modemTestActivity.mIotOption + (1 << (whichButton - 1));
                            } else {
                                ModemTestActivity modemTestActivity2 = ModemTestActivity.this;
                                int unused2 = modemTestActivity2.mIotOption = modemTestActivity2.mIotOption - (1 << (whichButton - 1));
                            }
                        }
                        Elog.v(ModemTestActivity.TAG, "mIotOption = " + ModemTestActivity.this.mIotOption);
                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModemTestActivity modemTestActivity = ModemTestActivity.this;
                        modemTestActivity.sendATCommad("3", modemTestActivity.mIotOption, 3);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int unused = ModemTestActivity.this.mIotOption = 0;
                    }
                }).create();
            case 5:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setMultiChoiceItems(R.array.modem_test_operator_options_6589, new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                        Elog.v(ModemTestActivity.TAG, "whichButton = " + whichButton);
                        Elog.v(ModemTestActivity.TAG, "isChecked = " + isChecked);
                        if (isChecked) {
                            ModemTestActivity modemTestActivity = ModemTestActivity.this;
                            int unused = modemTestActivity.mOperatorOption = modemTestActivity.mOperatorOption + (1 << whichButton);
                        } else {
                            ModemTestActivity modemTestActivity2 = ModemTestActivity.this;
                            int unused2 = modemTestActivity2.mOperatorOption = modemTestActivity2.mOperatorOption - (1 << whichButton);
                        }
                        Elog.v(ModemTestActivity.TAG, "mOperatorOption = " + ModemTestActivity.this.mOperatorOption);
                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModemTestActivity.this.attachOrDetachGprs();
                        ModemTestActivity modemTestActivity = ModemTestActivity.this;
                        modemTestActivity.sendATCommad("4", modemTestActivity.mOperatorOption, 5);
                        if (ModemTestActivity.this.mCurrentMode == 2) {
                            ModemTestActivity.this.setGprsTransferType(1);
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int unused = ModemTestActivity.this.mOperatorOption = 0;
                    }
                }).create();
            case 8:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setSingleChoiceItems(R.array.modem_test_c2k_options, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Elog.v(ModemTestActivity.TAG, "whichButton = " + whichButton);
                        int unused = ModemTestActivity.this.mCdmaOption = whichButton;
                        Elog.v(ModemTestActivity.TAG, "mCdmaOption = " + ModemTestActivity.this.mCdmaOption);
                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModemTestActivity.this.setCdmaOption();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int unused = ModemTestActivity.this.mCdmaOption = 0;
                    }
                }).create();
            case 2000:
                return new AlertDialog.Builder(this).setTitle("MODEM TEST").setMessage("Please reboot the phone!").setPositiveButton("OK", (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public void attachOrDetachGprs() {
        int i = this.mOperatorOption;
        if ((i & 128) == 0 && (i & 256) == 0) {
            Elog.v(TAG, "Dettach GPRS for DoCoMo/Softband");
            SystemProperties.set("persist.vendor.radio.gprs.attach.type", "0");
            EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+EGTYPE=0,1", ""}, (Message) null);
            return;
        }
        Elog.v(TAG, "Attach GPRS for DoCoMo/Softband");
        SystemProperties.set("persist.vendor.radio.gprs.attach.type", "1");
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+EGTYPE=1,1", ""}, (Message) null);
    }

    /* access modifiers changed from: private */
    public void setGprsTransferType(int type) {
        String property = type == 0 ? "1" : "0";
        Elog.v(TAG, "Change persist.radio.gprs.prefer to " + property);
        try {
            EmUtils.getEmHidlService().setPreferGprsMode(property);
        } catch (Exception e) {
            e.printStackTrace();
            Elog.e(TAG, "set property failed ...");
        }
        for (int i = 0; i < TelephonyManager.getDefault().getPhoneCount(); i++) {
            EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+EGTP=" + type, ""}, (Message) null);
            EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+EMPPCH=" + type, ""}, (Message) null);
        }
    }

    /* access modifiers changed from: private */
    public void queryUnlockOption() {
        EmUtils.invokeOemRilRequestStringsEm(true, ModemCategory.getCdmaCmdArr(new String[]{"AT+ECLSC?", "+ECLSC:", "DESTRILD:C2K"}), this.mATCmdHander.obtainMessage(9));
    }

    private void setUnlockOption(boolean unlock) {
        int i = this.mCurrentFlag;
        this.mCurrentFlag = unlock ? i | 2097152 : i & -2097153;
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+EPCT=" + this.mCurrentMode + "," + this.mCurrentFlag, ""}, this.mATCmdHander.obtainMessage(10));
        String[] strArr = new String[3];
        StringBuilder sb = new StringBuilder();
        sb.append("AT+ECLSC=");
        sb.append(unlock ? "1" : "0");
        strArr[0] = sb.toString();
        strArr[1] = "";
        strArr[2] = "DESTRILD:C2K";
        EmUtils.invokeOemRilRequestStringsEm(true, ModemCategory.getCdmaCmdArr(strArr), this.mATCmdHander.obtainMessage(10));
        EmUtils.invokeOemRilRequestStringsEm(true, ModemCategory.getCdmaCmdArr(new String[]{"AT+RFSSYNC", "", "DESTRILD:C2K"}), (Message) null);
    }

    /* access modifiers changed from: private */
    public void queryCdmaOption() {
        EmUtils.invokeOemRilRequestStringsEm(true, ModemCategory.getCdmaCmdArr(new String[]{"AT+ECTM?", "+ECTM:", "DESTRILD:C2K"}), this.mATCmdHander.obtainMessage(7));
    }

    /* access modifiers changed from: private */
    public void setCdmaOption() {
        if (this.mCdmaOption == 1) {
            sendATCommandCdma("\"SPIRENT\"", 8);
        } else {
            sendATCommandCdma("\"NONE\"", 8);
        }
    }

    private void handleQueryCdmaSimSetting(int flag) {
        if ((1048576 & flag) != 0) {
            Elog.d(TAG, "handleQueryCdmaSimSetting set check true");
            this.mFirstEntry = true;
            this.mCdmaSimSettingCheckBox.setChecked(true);
            return;
        }
        Elog.d(TAG, "handleQueryCdmaSimSetting set check false");
        this.mFirstEntry = false;
        this.mCdmaSimSettingCheckBox.setChecked(false);
    }

    private void setCdmaSimSettingOption(boolean check) {
        int i;
        if (check) {
            i = this.mCurrentFlag | 1048576;
        } else {
            i = this.mCurrentFlag & -1048577;
        }
        this.mCurrentFlag = i;
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+EPCT=" + this.mCurrentMode + "," + this.mCurrentFlag, ""}, this.mATCmdHander.obtainMessage(10));
    }

    private void setLaaSettings(boolean check) {
        Elog.d(TAG, "setLaaSettings = " + check);
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+ESBP=9,2", ""}, (Message) null);
        String[] strArr = new String[2];
        StringBuilder sb = new StringBuilder();
        sb.append("AT+ESBP=5,\"SBP_MP_LTE_DOWNLINK_LAA_r13\",");
        sb.append(check ? "1" : "0");
        strArr[0] = sb.toString();
        strArr[1] = "";
        EmUtils.invokeOemRilRequestStringsEm(strArr, this.mATCmdHander.obtainMessage(12));
    }

    private void queryLaaSettings() {
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+ESBP=7,\"SBP_MP_LTE_DOWNLINK_LAA_r13\"", "+ESBP:"}, this.mATCmdHander.obtainMessage(11));
    }

    /* access modifiers changed from: private */
    public void handleQueryLaa(String[] data) {
        boolean z = false;
        if (data == null || data.length <= 0) {
            Toast.makeText(this, "The returned data is wrong.", 0).show();
        } else if (data[0].length() > "+ESBP:".length()) {
            String str = data[0].substring("+ESBP:".length()).trim();
            Elog.i(TAG, "laa vale is " + str);
            int value = Integer.parseInt(str);
            this.mLaaCheckBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
            CheckBox checkBox = this.mLaaCheckBox;
            if (value == 1) {
                z = true;
            }
            checkBox.setChecked(z);
            this.mLaaCheckBox.setOnCheckedChangeListener(this);
        } else {
            Elog.i(TAG, "LAA: The data returned is not right.");
        }
    }

    private void sendATCommandCdma(String str, int message) {
        String[] cmd = ModemCategory.getCdmaCmdArr(new String[]{"AT+ECTM=" + str, "", "DESTRILD:C2K"});
        EmUtils.invokeOemRilRequestStringsEm(true, cmd, this.mATCmdHander.obtainMessage(message));
        cmd[0] = "AT+RFSSYNC";
        EmUtils.invokeOemRilRequestStringsEm(true, cmd, (Message) null);
    }

    /* access modifiers changed from: private */
    public void handleQueryUnlock(String[] data) {
        boolean z = false;
        if (data == null || data.length <= 0) {
            Toast.makeText(this, "The returned data is wrong.", 0).show();
        } else if (data[0].length() > "+ECLSC:".length()) {
            String str = data[0].substring("+ECLSC:".length()).trim();
            Elog.i(TAG, "unlock setting is " + str);
            int value = Integer.parseInt(str);
            this.mUnlockCheckBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
            CheckBox checkBox = this.mUnlockCheckBox;
            if (value == 1) {
                z = true;
            }
            checkBox.setChecked(z);
            this.mUnlockCheckBox.setOnCheckedChangeListener(this);
        } else {
            Elog.i(TAG, "The data returned is not right.");
        }
    }

    /* access modifiers changed from: private */
    public void handleQueryCdma(String[] data) {
        if (data == null) {
            Toast.makeText(this, "The returned data is wrong.", 0).show();
            return;
        }
        Elog.i(TAG, "data length is " + data.length);
        int i = 0;
        int length = data.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = data[i2];
            if (str != null) {
                Elog.i(TAG, "data[" + i + "] is : " + str);
            }
            i++;
        }
        if (data[0].length() > 6) {
            String mode = data[0].substring(6, data[0].length()).trim();
            String mode2 = mode.substring(1, mode.length() - 1);
            Elog.i(TAG, "mode is " + mode2);
            String text = this.mTextView.getText().toString();
            this.mTextView.setText(text + "\nThe current C2K mode is " + mode2);
            return;
        }
        Elog.i(TAG, "The data returned is not right.");
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.modem_test_unlock) {
            setUnlockOption(isChecked);
        }
        if (buttonView.getId() == R.id.modem_test_cdma_sim_setting) {
            setCdmaSimSettingOption(isChecked);
            if (this.mFirstEntry) {
                this.mFirstEntry = false;
            } else {
                showDialog(2000);
            }
        } else if (buttonView.getId() == R.id.modem_test_laa_setting) {
            setLaaSettings(isChecked);
        }
    }
}
