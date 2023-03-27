package com.mediatek.engineermode.mcfconfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.radio.V1_2.ScanIntervalRange;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.aospradio.EmRadioHidlAosp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import vendor.mediatek.hardware.engineermode.V1_0.IEmCallback;

public class McfConfigActivity extends Activity implements View.OnClickListener {
    private final String CERTIFICATION_OPID_INPUT_KEY = "input_op_id";
    private final String CERTIFICATION_OPID_KEY = "op_id";
    private final String CMD_OTA_RETURN = "+EMCFC:";
    private final String CMD_OTA_URC = "+EMCFRPT:";
    private final String DUMP_OTA_COMMAND = "AT+EMCFC=5";
    private final String G98_CER_OTA_QUERY_COMMAND = "AT+EMCFC=4,0,0";
    private final String G98_GEN_OPOTA_QUERY_COMMAND = "AT+EMCFC=4,2,0";
    private final String G98_OPOTA_QUERY_COMMAND = "AT+EMCFC=4,1,0";
    private final String G98_OTA_QUERY_COMMAND = "AT+EMCFC=4,0,0";
    private final String GENERAL_OPOTA_IS_OTAPATH_KEY = "general_opota_is_otapath";
    private final String GENERAL_OPOTA_OTA_FILEPATH_KEY = "general_opota_otapath";
    private final String GENERAL_OPOTA_RUNTIME_FILEPATH_KEY = "general_opota_runtimepath";
    private final String GEN_OPOTA_QUERY_COMMAND = "AT+EMCFC=4,2";
    private final String INI_REFRESH_COMMAND = "AT+EMCFC=7";
    private final int INI_REFRESH_WAIT_TIME = ScanIntervalRange.MAX;
    private final String IS_OTAPATH_KEY = "is_otapath";
    private final String MCF_CONFIG_SHAREPRE = "mcf_config_settings";
    private final String MODEM_DUMP_OTA_COMMAND = "AT+EMCFC=12";
    private final int MSG_CLEAR_GEN_OPOTA_FILEPATH = 5;
    private final int MSG_CLEAR_OPOTA_FILEPATH = 3;
    private final int MSG_CLEAR_OTA_FILEPATH = 1;
    private final int MSG_DUMP_OTA_FILE = 6;
    private final int MSG_DUMP_OTA_TO_MODEM = 15;
    private final int MSG_G98_CER_OTA_QUERY = 13;
    private final int MSG_G98_GEN_OPOTA_QUERY = 14;
    private final int MSG_G98_OPOTA_QUERY = 12;
    private final int MSG_G98_OTA_QUERY = 11;
    private final int MSG_GEN_OPOTA_QUERY = 10;
    protected final int MSG_GET_MCF_STATUS_URC = 16;
    private final int MSG_INI_REFRESH = 7;
    private final int MSG_MCF_MODEM_STATUS_CHANGED = 102;
    private final int MSG_MCF_RADIO_STATE_CHANGED = 100;
    private final int MSG_MODEM_REBOOT_COMPLETE = 101;
    private final int MSG_OPOTA_QUERY = 9;
    private final int MSG_OTA_QUERY = 8;
    private final int MSG_SET_CERT_CERTIFICATIN = 22;
    private final int MSG_SET_CMCC_CERTIFICATIN = 19;
    private final int MSG_SET_CT_CERTIFICATIN = 20;
    private final int MSG_SET_CU_CERTIFICATIN = 21;
    private final int MSG_SET_GEN_OPOTA_FILEPATH = 4;
    private final int MSG_SET_OM_CERTIFICATIN = 18;
    private final int MSG_SET_OPOTA_FILEPATH = 2;
    private final int MSG_SET_OTA_FILEPATH = 0;
    private final int MSG_SET_USER_CERTIFICATIN = 17;
    private final String OPOTA_QUERY_COMMAND = "AT+EMCFC=4,1";
    private final String OTA_IS_OTAPATH_KEY = "ota_is_otapath";
    private final String OTA_OTA_FILEPATH_KEY = "ota_file_otapath";
    private final String OTA_PATH = "/vendor/etc/mdota/";
    private final String OTA_QUERY_COMMAND = "AT+EMCFC=4,0";
    private final String OTA_RUNTIME_FILEPATH_KEY = "ota_file_runtimepath";
    private final int OTA_WAIT_TIME = 180;
    /* access modifiers changed from: private */
    public final HashMap<String, String> OtaFilePathType = new HashMap<String, String>() {
        {
            put("0", "Android OTA path");
            put("1", "Runtime path");
        }
    };
    private final int REBOOT_MODEM_WAIT_TIME = 180;
    private final String RUNTIME_PATH = "/mnt/vendor/nvcfg/mdota/";
    private final String SET_CERT_CMD = "AT+EMCFC=6";
    private final String SET_GEN_OPOTA_COMMAND = "AT+EMCFC=6,2,";
    private final String SET_OPOTA_COMMAND = "AT+EMCFC=6,1,";
    private final String SET_OTA_COMMAND = "AT+EMCFC=6,0,";
    private final int SHOW_CERTIFICATION_VIEW = 2;
    private final int SHOW_GENERAL_VIEW = 1;
    private final int SHOW_OPOTA_VIEW = 0;
    private final String SIM1_OPOTA_IS_OTAPATH_KEY = "sim1_opota_is_otapath";
    private final String SIM1_OPOTA_OTA_FILEPATH_KEY = "sim1_opota_file_otapath";
    private final String SIM1_OPOTA_RUNTIME_FILEPATH_KEY = "sim1_opota_file_runtimepath";
    private final String SIM2_OPOTA_IS_OTAPATH_KEY = "sim2_opota_is_otapath";
    private final String SIM2_OPOTA_OTA_FILEPATH_KEY = "sim2_opota_file_otapath";
    private final String SIM2_OPOTA_RUNTIME_FILEPATH_KEY = "sim2_opota_file_runtimepath";
    private final int SIM_COUNT = 2;
    private final int STATE_DUMP_OTA = 1;
    private final int STATE_DUMP_OTA_TO_MODEM = 5;
    private final int STATE_INI_REFRESH = 3;
    private final int STATE_QUERY_OTA = 4;
    private final int STATE_REBOOT_MODEM = 2;
    private final String TAG = "McfConfig";
    private Button addGenOpOtaBtn;
    private Button addOpOtaBtn;
    private Button addOtaBtn;
    private Button applyGenOpOtaBtn;
    private Button applyOpOtaBtn;
    private Button applyOtaBtn;
    private Button certBtn;
    private LinearLayout certificationView;
    private Button certotaQueryBtn;
    private Button certotaQueryBtnG98;
    private Button clearGenOpOtaBtn;
    private Button clearOpOtaBtn;
    private Button clearOtaBtn;
    /* access modifiers changed from: private */
    public ArrayList<String> curG98OtaFile;
    /* access modifiers changed from: private */
    public String curGenOpOtaFile;
    /* access modifiers changed from: private */
    public String curOpOtaFile;
    /* access modifiers changed from: private */
    public String curOtaFile;
    long dumpBegain;
    long dumpEnd;
    private Button dumpOta;
    private Button dumpOtaToModem;
    /* access modifiers changed from: private */
    public TextView genOpOtaFile;
    private Button genOpOtaQueryBtnG98;
    /* access modifiers changed from: private */
    public RadioGroup genOpotaPathRgroup;
    private CheckBox genOpotaResetMdEnable;
    private LinearLayout generalView;
    private Button iniRefreshBtn;
    /* access modifiers changed from: private */
    public int iniStatus;
    private RadioGroup iniStatusGroup;
    /* access modifiers changed from: private */
    public String inputOp;
    boolean isG98;
    private boolean isGenOpOtaPathValid = false;
    /* access modifiers changed from: private */
    public boolean isGenopotaOtaPath = true;
    RadioButton isGenopotaOtaPathBt;
    RadioButton isGenopotaRuntimePathBt;
    private boolean isOpOtaPathValid = false;
    /* access modifiers changed from: private */
    public boolean isOpotaOtaPath = true;
    RadioButton isOpotaOtaPathBt;
    RadioButton isOpotaRuntimePathBt;
    /* access modifiers changed from: private */
    public boolean isOtaOtaPath = true;
    RadioButton isOtaOtaPathBt;
    /* access modifiers changed from: private */
    public boolean isOtaPath = true;
    private boolean isOtaPathValid = false;
    RadioButton isOtaRuntimePathBt;
    HashMap<Integer, String> mApplyUrcTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "SUCCESS");
            put(1, "MCF_NOT_SUPPORT");
            put(2, "VERSION_NOT_MATCH");
            put(3, "WRONG_BUFFER_SIZE");
            put(4, "INVALID_PARAMETER");
            put(5, "READ_NVRAM_FAIL");
            put(6, "WRITE_NVRAM_FAIL");
            put(7, "READ_OTA_FILE_FAIL");
            put(8, "INVALID_SBP_TAG");
            put(9, "INVALID_FILE");
            put(10, "INVALID_ATTR");
            put(11, "TAKE_READ_LOCK_FAIL");
            put(12, "ALLOCATE_BUFFER_FAIL");
            put(13, "ENCRYPTION_FAIL");
            put(14, "DECRYPTION_FAIL");
            put(15, "CHECKSUM_ERROR");
            put(16, "WRITE_DISK_FAIL");
            put(17, "READ_INI_FILE_FAIL");
            put(18, "INVALID_INI_ITEM");
            put(19, "WRITE_INI_FILE_FAIL");
            put(50, "DSBP_FAIL");
            put(51, "FAIL_MCF_DSBP_ONGOING");
        }
    };
    /* access modifiers changed from: private */
    public IEmCallback mEmCallback = new IEmCallback.Stub() {
        public boolean callbackToClient(String dataStr) throws RemoteException {
            Elog.d("McfConfig", "callbackToClient data = " + dataStr);
            McfConfigActivity.this.mcfCertObj.setFileList(dataStr);
            return true;
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x067a, code lost:
            r2 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:339:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:340:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:341:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:342:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x03b6, code lost:
            r3 = r2;
            r2 = r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r26) {
            /*
                r25 = this;
                r0 = r25
                r1 = r26
                r2 = 0
                r3 = 0
                int r4 = r1.what
                java.lang.String r5 = " Failed!\n"
                java.lang.String r6 = " returned exception:"
                java.lang.String r8 = "General OTA by OP"
                java.lang.String r9 = "OTA by OP"
                java.lang.String r10 = "OTA Query"
                java.lang.String r11 = "AT Command Failed: "
                java.lang.String r13 = "\n"
                r14 = 4
                java.lang.Integer r15 = java.lang.Integer.valueOf(r14)
                java.lang.String r14 = ","
                java.lang.String r12 = " "
                java.lang.String r7 = "OTA"
                r18 = r2
                java.lang.String r2 = ":"
                r19 = r3
                r3 = 3
                r20 = r8
                java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
                java.lang.String r3 = ""
                r21 = r9
                r9 = 2
                r22 = r10
                java.lang.Integer r10 = java.lang.Integer.valueOf(r9)
                java.lang.String r9 = "McfConfig"
                r24 = r8
                r8 = 1
                switch(r4) {
                    case 0: goto L_0x0c41;
                    case 1: goto L_0x0a73;
                    case 2: goto L_0x0c41;
                    case 3: goto L_0x0a73;
                    case 4: goto L_0x0c41;
                    case 5: goto L_0x0a73;
                    case 6: goto L_0x09ec;
                    case 7: goto L_0x0978;
                    case 8: goto L_0x07af;
                    case 9: goto L_0x07af;
                    case 10: goto L_0x07af;
                    case 11: goto L_0x067f;
                    case 12: goto L_0x067f;
                    case 13: goto L_0x067f;
                    case 14: goto L_0x067f;
                    case 15: goto L_0x09ec;
                    case 16: goto L_0x03ba;
                    case 17: goto L_0x0157;
                    case 18: goto L_0x0157;
                    case 19: goto L_0x0157;
                    case 20: goto L_0x0157;
                    case 21: goto L_0x0157;
                    case 22: goto L_0x0157;
                    case 23: goto L_0x0157;
                    case 24: goto L_0x0157;
                    case 25: goto L_0x0157;
                    case 26: goto L_0x0157;
                    case 27: goto L_0x0157;
                    case 28: goto L_0x0157;
                    case 101: goto L_0x00c1;
                    case 102: goto L_0x0059;
                    default: goto L_0x0041;
                }
            L_0x0041:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "unsupport msg :"
                r2.append(r3)
                int r3 = r1.what
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                goto L_0x0dd5
            L_0x0059:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x00a6
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x00a6
                java.lang.Object r4 = r2.result
                java.lang.Boolean r4 = (java.lang.Boolean) r4
                boolean r3 = r4.booleanValue()
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "IS MODEMENABLE: "
                r4.append(r5)
                r4.append(r3)
                java.lang.String r5 = ", mIsModemEnabled: "
                r4.append(r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r5 = r5.mIsModemEnabled
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.v(r9, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r4 = r4.mIsModemEnabled
                if (r4 != 0) goto L_0x00bd
                if (r3 == 0) goto L_0x00bd
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.os.Handler r4 = r4.mHandler
                r5 = 101(0x65, float:1.42E-43)
                r4.sendEmptyMessage(r5)
                goto L_0x00bd
            L_0x00a6:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Reboot Modem returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r9, r4)
            L_0x00bd:
                r3 = r19
                goto L_0x0dd9
            L_0x00c1:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r2 = r2.mIsModemEnabled
                if (r2 != 0) goto L_0x010e
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r2 = r2.resetMd
                if (r2 == 0) goto L_0x010e
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r2 = r2.taskSet
                boolean r2 = r2.contains(r10)
                if (r2 != 0) goto L_0x00de
                goto L_0x010e
            L_0x00de:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r2 = r2.taskSet
                r2.remove(r10)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.os.CountDownTimer r2 = r2.timer
                if (r2 == 0) goto L_0x00f9
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.os.CountDownTimer r2 = r2.timer
                r2.cancel()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3 = 0
                r2.timer = r3
            L_0x00f9:
                java.lang.String r2 = "Reset Modem Success!"
                com.mediatek.engineermode.Elog.e(r9, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r3 = "Reset Modem"
                java.lang.String r4 = "Reset Modem Completed!"
                r2.showDialog(r3, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean unused = r2.mIsModemEnabled = r8
                goto L_0x0dd5
            L_0x010e:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "SIM"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r3 = r3.phoneid
                r2.append(r3)
                java.lang.String r3 = " received MODEM_REBOOT_COMPLETE, but skiped! mIsModemEnabled:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r3 = r3.mIsModemEnabled
                r2.append(r3)
                java.lang.String r3 = " resetMd:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r3 = r3.resetMd
                r2.append(r3)
                java.lang.String r3 = " taskSet:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r3 = r3.taskSet
                java.lang.String r3 = r3.toString()
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                return
            L_0x0157:
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                int r7 = r1.what
                int r7 = r7 + -17
                r10 = 6
                int r7 = r7 / r10
                r10 = 0
                java.lang.Throwable r15 = r4.exception
                java.lang.String r8 = "Certification with Sim"
                if (r15 != 0) goto L_0x0209
                java.lang.Object r2 = r4.result
                if (r2 == 0) goto L_0x0206
                java.lang.Object r2 = r4.result
                boolean r2 = r2 instanceof java.lang.String[]
                if (r2 == 0) goto L_0x0206
                java.lang.Object r2 = r4.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                int r2 = r2.length
                if (r2 <= 0) goto L_0x0206
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Certification Result: "
                r2.append(r6)
                java.lang.Object r6 = r4.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                java.lang.String r6 = java.util.Arrays.toString(r6)
                r2.append(r6)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                java.lang.Object r2 = r4.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                r6 = 0
                r2 = r2[r6]
                java.lang.String[] r2 = r2.split(r14)
                if (r2 == 0) goto L_0x0201
                r6 = 1
                r11 = r2[r6]
                if (r11 == 0) goto L_0x0201
                r11 = 2
                r14 = r2[r11]
                if (r14 == 0) goto L_0x0201
                r14 = r2[r6]
                java.lang.String r6 = r14.trim()
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                int r6 = r6.intValue()
                r14 = r2[r11]
                java.lang.String r11 = r14.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                if (r11 != 0) goto L_0x01ce
                if (r6 != 0) goto L_0x01ce
                r10 = 1
                goto L_0x0203
            L_0x01ce:
                r10 = 0
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                java.lang.String r15 = "Certification Ota: "
                r14.append(r15)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r15 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r16 = r2
                java.util.HashMap r2 = r15.mcfApplyOtaResult
                java.lang.String r2 = r15.getStringValueFromMap(r6, r2)
                r14.append(r2)
                java.lang.String r2 = "\nDsbp: "
                r14.append(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r15 = r2.mcfApplyDsbpResult
                java.lang.String r2 = r2.getStringValueFromMap(r11, r15)
                r14.append(r2)
                java.lang.String r2 = r14.toString()
                r19 = r2
                goto L_0x0203
            L_0x0201:
                r16 = r2
            L_0x0203:
                r2 = r19
                goto L_0x0250
            L_0x0206:
                r2 = r19
                goto L_0x0250
            L_0x0209:
                java.lang.Throwable r14 = r4.exception
                java.lang.String r14 = r14.toString()
                java.lang.String[] r14 = r14.split(r2)
                r15 = 1
                r14 = r14[r15]
                if (r14 == 0) goto L_0x0234
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                r14.append(r11)
                java.lang.Throwable r11 = r4.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r2 = r11.split(r2)
                r2 = r2[r15]
                r14.append(r2)
                java.lang.String r2 = r14.toString()
                goto L_0x0235
            L_0x0234:
                r2 = r3
            L_0x0235:
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                r11.append(r8)
                r11.append(r7)
                r11.append(r6)
                java.lang.Throwable r6 = r4.exception
                r11.append(r6)
                java.lang.String r6 = r11.toString()
                com.mediatek.engineermode.Elog.e(r9, r6)
            L_0x0250:
                if (r10 == 0) goto L_0x0360
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r8)
                r3.append(r7)
                java.lang.String r5 = " Cmd "
                r3.append(r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r5 = r5.mcfCertObj
                int r5 = r5.getCurrentCmd(r7)
                r3.append(r5)
                java.lang.String r5 = " succeed!"
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r9, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r3 = r3.mcfCertObj
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r5 = r5.mcfCertObj
                int r5 = r5.getCurrentCmd(r7)
                r6 = 1
                r3.setCmdResult(r7, r5, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r3 = r3.isSimReady(r7)
                if (r3 != 0) goto L_0x02c9
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r5 = r7 + 1
                r6 = 2
                int r5 = r5 % r6
                boolean r3 = r3.isSimReady(r5)
                if (r3 != 0) goto L_0x02c9
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r5 = "Certification"
                java.lang.String r6 = "Certification Succeed!"
                r3.showDialog(r5, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask r3 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.<init>()
                r5 = 1
                java.lang.Integer[] r6 = new java.lang.Integer[r5]
                int r8 = r1.what
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                r9 = 0
                r6[r9] = r8
                r3.execute(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.rebootModem(r5)
                goto L_0x03b6
            L_0x02c9:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r3 = r3.mcfCertObj
                int r3 = r3.getCurrentCmd(r7)
                r5 = 3
                if (r3 >= r5) goto L_0x02f3
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r3 = r3.mcfCertObj
                int r3 = r3.getCurrentCmd(r7)
                r5 = 1
                if (r3 != r5) goto L_0x02ec
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r5 = r7 + 1
                r6 = 2
                int r5 = r5 % r6
                r3.doCertificationAction(r5)
            L_0x02ec:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.doCertificationAction(r7)
                goto L_0x03b6
            L_0x02f3:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r3 = r3.mcfCertObj
                int r5 = r7 + 1
                r6 = 2
                int r5 = r5 % r6
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r6 = r6.mcfCertObj
                int r6 = r6.getCurrentCmd(r7)
                boolean r3 = r3.getCmdResult(r5, r6)
                if (r3 == 0) goto L_0x0348
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r3 = r3.mcfCertObj
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r5 = r5.mcfCertObj
                int r5 = r5.getCurrentCmd(r7)
                boolean r3 = r3.getCmdResult(r7, r5)
                if (r3 == 0) goto L_0x0348
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r5 = "Certification"
                java.lang.String r6 = "Certification Succeed!"
                r3.showDialog(r5, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask r3 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.<init>()
                r5 = 1
                java.lang.Integer[] r6 = new java.lang.Integer[r5]
                int r8 = r1.what
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                r9 = 0
                r6[r9] = r8
                r3.execute(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.rebootModem(r5)
                goto L_0x03b6
            L_0x0348:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r8)
                r3.append(r7)
                java.lang.String r5 = " succeed!"
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r9, r3)
                goto L_0x03b6
            L_0x0360:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r6 = r6.mcfCertObj
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r11 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                com.mediatek.engineermode.mcfconfig.McfCertification r11 = r11.mcfCertObj
                int r11 = r11.getCurrentCmd(r7)
                r14 = 0
                r6.setCmdResult(r7, r11, r14)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r8)
                r6.append(r7)
                r6.append(r5)
                if (r2 != 0) goto L_0x0385
                goto L_0x0394
            L_0x0385:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r13)
                r3.append(r2)
                java.lang.String r3 = r3.toString()
            L_0x0394:
                r6.append(r3)
                java.lang.String r3 = r6.toString()
                com.mediatek.engineermode.Elog.e(r9, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r8)
                r6.append(r7)
                r6.append(r12)
                java.lang.String r6 = r6.toString()
                r5.showDialog(r6, r3)
            L_0x03b6:
                r3 = r2
                r2 = r4
                goto L_0x0dd9
            L_0x03ba:
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.Object r6 = r4.result
                byte[] r6 = (byte[]) r6
                java.lang.String r5 = r5.byteArrayToStr(r6)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r8 = "Readback from urc = "
                r6.append(r8)
                r6.append(r5)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.d(r9, r6)
                java.lang.String r6 = "+EMCFRPT:"
                boolean r6 = r5.startsWith(r6)
                if (r6 == 0) goto L_0x067a
                java.lang.String[] r2 = r5.split(r2)
                int r2 = r2.length
                r6 = 2
                if (r2 >= r6) goto L_0x03ee
                goto L_0x067a
            L_0x03ee:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.os.CountDownTimer r2 = r2.timer
                if (r2 == 0) goto L_0x0400
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.os.CountDownTimer r2 = r2.timer
                r2.cancel()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r6 = 0
                r2.timer = r6
            L_0x0400:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.app.ProgressDialog r2 = r2.mProgressDialog
                if (r2 == 0) goto L_0x0417
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.app.ProgressDialog r2 = r2.mProgressDialog
                r2.dismiss()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r6 = 0
                android.app.ProgressDialog unused = r2.mProgressDialog = r6
            L_0x0417:
                java.lang.String r2 = "+EMCFRPT:"
                int r2 = r2.length()
                java.lang.String r2 = r5.substring(r2)
                java.lang.String r2 = r2.replaceAll(r12, r3)
                java.lang.String[] r2 = r2.split(r14)
                r3 = 0
                r6 = r2[r3]
                java.lang.String r3 = r6.trim()
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                int r3 = r3.intValue()
                r6 = 1
                r8 = r2[r6]
                java.lang.String r6 = r8.trim()
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                int r6 = r6.intValue()
                switch(r3) {
                    case 1: goto L_0x05bf;
                    case 2: goto L_0x044a;
                    case 3: goto L_0x0571;
                    case 4: goto L_0x0450;
                    case 5: goto L_0x05bf;
                    default: goto L_0x044a;
                }
            L_0x044a:
                r16 = r2
                r17 = r3
                goto L_0x067a
            L_0x0450:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r10 = "OTA QUERY with result:"
                r8.append(r10)
                java.lang.String r10 = java.util.Arrays.toString(r2)
                r8.append(r10)
                java.lang.String r8 = r8.toString()
                com.mediatek.engineermode.Elog.d(r9, r8)
                int r8 = r2.length
                r10 = 7
                if (r8 >= r10) goto L_0x046e
                goto L_0x067a
            L_0x046e:
                r8 = 2
                r8 = r2[r8]
                java.lang.String r8 = r8.trim()
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                int r8 = r8.intValue()
                r10 = 4
                r10 = r2[r10]
                java.lang.String r10 = r10.trim()
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                int r10 = r10.intValue()
                r11 = 5
                r11 = r2[r11]
                java.lang.String r11 = r11.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                r12 = 6
                r12 = r2[r12]
                java.lang.String r12 = r12.trim()
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                r14.append(r7)
                r14.append(r11)
                r16 = r2
                java.lang.String r2 = ": "
                r14.append(r2)
                r14.append(r12)
                java.lang.String r2 = r14.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.ArrayList r2 = r2.curG98OtaFile
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                r14.append(r11)
                r17 = r3
                java.lang.String r3 = ": "
                r14.append(r3)
                r14.append(r12)
                java.lang.String r3 = r14.toString()
                r2.add(r3)
                if (r11 != r10) goto L_0x067a
                java.lang.String r2 = "ATCommand with ota query success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r2)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "OTA: "
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.ArrayList r3 = r3.curG98OtaFile
                java.lang.Object[] r3 = r3.toArray()
                java.lang.String r3 = java.util.Arrays.toString(r3)
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                if (r8 != 0) goto L_0x050e
                goto L_0x0516
            L_0x050e:
                r3 = 1
                if (r8 != r3) goto L_0x0514
                r7 = r21
                goto L_0x0516
            L_0x0514:
                r7 = r20
            L_0x0516:
                r2.append(r7)
                java.lang.String r3 = ": \n"
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r3 = 0
            L_0x0523:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.ArrayList r7 = r7.curG98OtaFile
                int r7 = r7.size()
                if (r3 >= r7) goto L_0x0550
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r7.append(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.ArrayList r9 = r9.curG98OtaFile
                java.lang.Object r9 = r9.get(r3)
                java.lang.String r9 = (java.lang.String) r9
                r7.append(r9)
                r7.append(r13)
                java.lang.String r2 = r7.toString()
                int r3 = r3 + 1
                goto L_0x0523
            L_0x0550:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r7.append(r2)
                r7.append(r13)
                java.lang.String r7 = r7.toString()
                java.lang.String r9 = "OTA Query "
                r3.showDialog(r9, r7)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r3 = r3.taskSet
                r3.remove(r15)
                goto L_0x067a
            L_0x0571:
                r16 = r2
                r17 = r3
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Ini Refresh end with result:"
                r2.append(r3)
                r2.append(r6)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                if (r6 != 0) goto L_0x0590
                java.lang.String r3 = "Success!"
                goto L_0x05ad
            L_0x0590:
                r3 = 1
                if (r6 != r3) goto L_0x0596
                java.lang.String r3 = "Reset Modem"
                goto L_0x05ad
            L_0x0596:
                r3 = 2
                if (r6 != r3) goto L_0x059c
                java.lang.String r3 = "AP retry"
                goto L_0x05ad
            L_0x059c:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r7 = "Failed with error_code: "
                r3.append(r7)
                r3.append(r6)
                java.lang.String r3 = r3.toString()
            L_0x05ad:
                java.lang.String r7 = "Ini Refresh"
                r2.showDialog(r7, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r2 = r2.taskSet
                r3 = r24
                r2.remove(r3)
                goto L_0x067a
            L_0x05bf:
                r16 = r2
                r17 = r3
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                long r7 = java.lang.System.currentTimeMillis()
                r2.dumpEnd = r7
                java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
                java.lang.String r3 = "HH:mm:ss"
                r2.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r7 = "Dump OTA file end :"
                r3.append(r7)
                java.util.Date r7 = new java.util.Date
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                long r10 = r8.dumpBegain
                r7.<init>(r10)
                java.lang.String r7 = r2.format(r7)
                r3.append(r7)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r9, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                if (r6 != 0) goto L_0x05ff
                java.lang.String r8 = "Success!"
                goto L_0x0635
            L_0x05ff:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Failed with Error Code: "
                r8.append(r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r9 = r9.mcfOtaDumpResult
                java.lang.Integer r10 = java.lang.Integer.valueOf(r6)
                boolean r9 = r9.containsKey(r10)
                if (r9 == 0) goto L_0x062a
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r9 = r9.mcfOtaDumpResult
                java.lang.Integer r10 = java.lang.Integer.valueOf(r6)
                java.lang.Object r9 = r9.get(r10)
                java.io.Serializable r9 = (java.io.Serializable) r9
                goto L_0x062e
            L_0x062a:
                java.lang.Integer r9 = java.lang.Integer.valueOf(r6)
            L_0x062e:
                r8.append(r9)
                java.lang.String r8 = r8.toString()
            L_0x0635:
                r7.append(r8)
                java.lang.String r8 = "\nDump costs "
                r7.append(r8)
                r8 = 1
                java.lang.Object[] r9 = new java.lang.Object[r8]
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                long r10 = r8.dumpEnd
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                long r12 = r8.dumpBegain
                long r10 = r10 - r12
                float r8 = (float) r10
                r10 = 1148846080(0x447a0000, float:1000.0)
                float r8 = r8 / r10
                java.lang.Float r8 = java.lang.Float.valueOf(r8)
                r10 = 0
                r9[r10] = r8
                java.lang.String r8 = "%.2f"
                java.lang.String r8 = java.lang.String.format(r8, r9)
                r7.append(r8)
                java.lang.String r8 = " s"
                r7.append(r8)
                java.lang.String r7 = r7.toString()
                java.lang.String r8 = "Dump Result"
                r3.showDialog(r8, r7)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r3 = r3.taskSet
                r7 = 1
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
                r3.remove(r7)
            L_0x067a:
                r2 = r4
                r3 = r19
                goto L_0x0dd9
            L_0x067f:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r4 = 0
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Query "
                r5.append(r6)
                int r6 = r1.what
                r8 = 11
                if (r6 == r8) goto L_0x06a7
                int r6 = r1.what
                r8 = 13
                if (r6 != r8) goto L_0x069b
                goto L_0x06a7
            L_0x069b:
                int r6 = r1.what
                r7 = 14
                if (r6 != r7) goto L_0x06a4
                r8 = r20
                goto L_0x06a8
            L_0x06a4:
                r8 = r21
                goto L_0x06a8
            L_0x06a7:
                r8 = r7
            L_0x06a8:
                r5.append(r8)
                java.lang.String r5 = r5.toString()
                java.lang.Throwable r6 = r2.exception
                if (r6 != 0) goto L_0x077c
                java.lang.Object r6 = r2.result
                if (r6 == 0) goto L_0x0779
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "Query OTA ("
                r6.append(r7)
                int r7 = r1.what
                r6.append(r7)
                java.lang.String r7 = " ): "
                r6.append(r7)
                java.lang.Object r7 = r2.result
                java.lang.String[] r7 = (java.lang.String[]) r7
                java.lang.String r7 = java.util.Arrays.toString(r7)
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.d(r9, r6)
                java.lang.Object r6 = r2.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                r7 = 0
                r6 = r6[r7]
                java.lang.String r7 = "+EMCFC:"
                int r7 = r7.length()
                java.lang.String r6 = r6.substring(r7)
                java.lang.String r6 = r6.replaceAll(r12, r3)
                java.lang.String[] r6 = r6.split(r14)
                if (r6 == 0) goto L_0x0776
                r4 = 1
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = "AT with ota query "
                r7.append(r8)
                java.lang.String r8 = java.util.Arrays.toString(r6)
                r7.append(r8)
                java.lang.String r7 = r7.toString()
                com.mediatek.engineermode.Elog.d(r9, r7)
                int r7 = r6.length
                r8 = 4
                if (r7 < r8) goto L_0x0719
                r7 = 3
                r7 = r6[r7]
                goto L_0x071a
            L_0x0719:
                r7 = r3
            L_0x071a:
                java.lang.String r8 = "0"
                boolean r8 = r7.equals(r8)
                if (r8 != 0) goto L_0x075a
                boolean r3 = r7.equals(r3)
                if (r3 == 0) goto L_0x0729
                goto L_0x075a
            L_0x0729:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r8 = "ATCommand with ota query success! OTA file num is "
                r3.append(r8)
                r3.append(r7)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r8 = 180(0xb4, float:2.52E-43)
                r9 = 4
                r3.startTimer(r9, r8)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r8 = "Waitting"
                java.lang.String r9 = "Query OTA is ongoing......"
                r3.showProgressDialog(r8, r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r3 = r3.taskSet
                r3.add(r15)
                r10 = r22
                goto L_0x0778
            L_0x075a:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r5)
                java.lang.String r9 = " num is "
                r8.append(r9)
                r8.append(r7)
                java.lang.String r8 = r8.toString()
                r10 = r22
                r3.showDialog(r10, r8)
                goto L_0x0778
            L_0x0776:
                r10 = r22
            L_0x0778:
                goto L_0x0795
            L_0x0779:
                r10 = r22
                goto L_0x0795
            L_0x077c:
                r10 = r22
                r4 = 0
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "AT with ota query returned exception:"
                r3.append(r6)
                java.lang.Throwable r6 = r2.exception
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.e(r9, r3)
            L_0x0795:
                if (r4 != 0) goto L_0x00bd
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r5)
                java.lang.String r7 = " failed!"
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                r3.showDialog(r10, r6)
                goto L_0x00bd
            L_0x07af:
                r10 = r22
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                r5 = 0
                java.lang.Throwable r6 = r4.exception
                if (r6 != 0) goto L_0x087c
                java.lang.Object r6 = r4.result
                if (r6 == 0) goto L_0x0893
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r8 = "Query OTA ("
                r6.append(r8)
                int r8 = r1.what
                r6.append(r8)
                java.lang.String r8 = " ): "
                r6.append(r8)
                java.lang.Object r8 = r4.result
                java.lang.String[] r8 = (java.lang.String[]) r8
                java.lang.String r8 = java.util.Arrays.toString(r8)
                r6.append(r8)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.d(r9, r6)
                java.lang.Object r6 = r4.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                r8 = 0
                r6 = r6[r8]
                java.lang.String r8 = "+EMCFC:"
                int r8 = r8.length()
                java.lang.String r6 = r6.substring(r8)
                java.lang.String r6 = r6.replaceAll(r12, r3)
                java.lang.String[] r6 = r6.split(r14)
                if (r6 == 0) goto L_0x087b
                r5 = 1
                int r8 = r6.length
                r11 = 2
                if (r8 < r11) goto L_0x0808
                r8 = 1
                r8 = r6[r8]
                goto L_0x0809
            L_0x0808:
                r8 = r3
            L_0x0809:
                int r11 = r6.length
                r12 = 3
                if (r11 < r12) goto L_0x082b
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r11 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r11 = r11.OtaFilePathType
                r12 = 2
                r13 = r6[r12]
                boolean r11 = r11.containsKey(r13)
                if (r11 == 0) goto L_0x082b
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r11 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r11 = r11.OtaFilePathType
                r12 = r6[r12]
                java.lang.Object r11 = r11.get(r12)
                java.lang.String r11 = (java.lang.String) r11
                goto L_0x082c
            L_0x082b:
                r11 = r3
            L_0x082c:
                int r12 = r6.length
                r13 = 4
                if (r12 < r13) goto L_0x0834
                r12 = 3
                r12 = r6[r12]
                goto L_0x0835
            L_0x0834:
                r12 = r3
            L_0x0835:
                boolean r3 = r12.equals(r3)
                if (r3 == 0) goto L_0x083f
                java.lang.String r2 = "No File"
                goto L_0x0851
            L_0x083f:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r11)
                r3.append(r2)
                r3.append(r12)
                java.lang.String r2 = r3.toString()
            L_0x0851:
                java.lang.String r3 = "0"
                boolean r3 = r8.equals(r3)
                if (r3 == 0) goto L_0x0860
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r3.curOtaFile = r2
                goto L_0x087b
            L_0x0860:
                java.lang.String r3 = "1"
                boolean r3 = r8.equals(r3)
                if (r3 == 0) goto L_0x086e
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r3.curOpOtaFile = r2
                goto L_0x087b
            L_0x086e:
                java.lang.String r3 = "2"
                boolean r3 = r8.equals(r3)
                if (r3 == 0) goto L_0x087b
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r3.curGenOpOtaFile = r2
            L_0x087b:
                goto L_0x0893
            L_0x087c:
                r5 = 0
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "AT with ota query returned exception:"
                r2.append(r3)
                java.lang.Throwable r3 = r4.exception
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.e(r9, r2)
            L_0x0893:
                if (r5 == 0) goto L_0x092d
                int r2 = r1.what
                r3 = 8
                if (r2 != r3) goto L_0x08a6
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3 = 10
                java.lang.String r6 = "AT+EMCFC=4,2"
                r2.sendATCommand(r6, r3)
                goto L_0x067a
            L_0x08a6:
                java.lang.String r2 = "ATCommand with ota query success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r2)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "OTA: "
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r3 = r3.curOtaFile
                r2.append(r3)
                java.lang.String r3 = ", OPOTA: "
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r3 = r3.curOpOtaFile
                r2.append(r3)
                java.lang.String r3 = ", General OPOTA: "
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r3 = r3.curGenOpOtaFile
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r3 = r1.what
                r6 = 10
                if (r3 != r6) goto L_0x090f
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "OTA: \n"
                r3.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r6 = r6.curOtaFile
                r3.append(r6)
                java.lang.String r6 = "\nGeneral OTA by OP:\n"
                r3.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r6 = r6.curGenOpOtaFile
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                goto L_0x0926
            L_0x090f:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "OTA by OP: "
                r3.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r6 = r6.curOpOtaFile
                r3.append(r6)
                java.lang.String r3 = r3.toString()
            L_0x0926:
                java.lang.String r6 = "OTA Query "
                r2.showDialog(r6, r3)
                goto L_0x067a
            L_0x092d:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "Query "
                r3.append(r6)
                int r6 = r1.what
                r8 = 8
                if (r6 != r8) goto L_0x0941
                r8 = r7
                goto L_0x094c
            L_0x0941:
                int r6 = r1.what
                r7 = 10
                if (r6 != r7) goto L_0x094a
                r8 = r20
                goto L_0x094c
            L_0x094a:
                r8 = r21
            L_0x094c:
                r3.append(r8)
                java.lang.String r6 = " failed!"
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                r2.showDialog(r10, r3)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "ATCommand with "
                r2.append(r3)
                int r3 = r1.what
                r2.append(r3)
                java.lang.String r3 = " query failed!"
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.e(r9, r2)
                goto L_0x067a
            L_0x0978:
                r3 = r24
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r4 = 0
                java.lang.Throwable r5 = r2.exception
                if (r5 != 0) goto L_0x09ab
                r4 = 1
                java.lang.Object r5 = r2.result
                if (r5 == 0) goto L_0x09c2
                java.lang.Object r5 = r2.result
                boolean r5 = r5 instanceof java.lang.String[]
                if (r5 == 0) goto L_0x09c2
                java.lang.Object r5 = r2.result
                java.lang.String[] r5 = (java.lang.String[]) r5
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "AT with ini refresh returned:"
                r6.append(r7)
                java.lang.String r7 = java.util.Arrays.toString(r5)
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.d(r9, r6)
                goto L_0x09c2
            L_0x09ab:
                r4 = 0
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "AT with ini refresh returned exception:"
                r5.append(r6)
                java.lang.Throwable r6 = r2.exception
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.e(r9, r5)
            L_0x09c2:
                if (r4 == 0) goto L_0x09dc
                java.lang.String r5 = "ATCommand with ini refresh success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r6 = 300(0x12c, float:4.2E-43)
                r7 = 3
                r5.startTimer(r7, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r5 = r5.taskSet
                r5.add(r3)
                goto L_0x00bd
            L_0x09dc:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r5 = "INI Refresh"
                java.lang.String r6 = "INI Refresh failed!"
                r3.showDialog(r5, r6)
                java.lang.String r3 = "ATCommand with INI Refresh failed!"
                com.mediatek.engineermode.Elog.e(r9, r3)
                goto L_0x00bd
            L_0x09ec:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x0a1d
                r3 = 1
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x0a34
                java.lang.Object r4 = r2.result
                boolean r4 = r4 instanceof java.lang.String[]
                if (r4 == 0) goto L_0x0a34
                java.lang.Object r4 = r2.result
                java.lang.String[] r4 = (java.lang.String[]) r4
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "ATCommand with Dump OTA returned: "
                r5.append(r6)
                java.lang.String r6 = java.util.Arrays.toString(r4)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r9, r5)
                goto L_0x0a34
            L_0x0a1d:
                r3 = 0
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "ATCommand with Dump OTA returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r9, r4)
            L_0x0a34:
                if (r3 == 0) goto L_0x0a63
                int r4 = r1.what
                r5 = 6
                if (r4 != r5) goto L_0x0a3d
                r8 = 1
                goto L_0x0a3e
            L_0x0a3d:
                r8 = 5
            L_0x0a3e:
                r4 = r8
                java.lang.String r5 = "ATCommand with Dump OTA success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r6 = 180(0xb4, float:2.52E-43)
                r5.startTimer(r4, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r6 = "Waitting"
                java.lang.String r7 = "Dump OTA is ongoing......"
                r5.showProgressDialog(r6, r7)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.Set r5 = r5.taskSet
                java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
                r5.add(r6)
                goto L_0x00bd
            L_0x0a63:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String r5 = "Dump OTA"
                java.lang.String r6 = "Dump OTA failed!"
                r4.showDialog(r5, r6)
                java.lang.String r4 = "ATCommand with Dump OTA failed!"
                com.mediatek.engineermode.Elog.e(r9, r4)
                goto L_0x00bd
            L_0x0a73:
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                int r8 = r1.what
                r10 = 1
                if (r8 != r10) goto L_0x0a7d
                goto L_0x0a87
            L_0x0a7d:
                int r7 = r1.what
                r8 = 3
                if (r7 != r8) goto L_0x0a85
                java.lang.String r7 = "OPOTA"
                goto L_0x0a87
            L_0x0a85:
                java.lang.String r7 = "General OPOTA"
            L_0x0a87:
                r8 = 0
                java.lang.Throwable r10 = r4.exception
                java.lang.String r15 = "Clear "
                if (r10 != 0) goto L_0x0b2f
                java.lang.Object r2 = r4.result
                if (r2 == 0) goto L_0x0b2c
                java.lang.Object r2 = r4.result
                boolean r2 = r2 instanceof java.lang.String[]
                if (r2 == 0) goto L_0x0b2c
                java.lang.Object r2 = r4.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                int r2 = r2.length
                if (r2 <= 0) goto L_0x0b2c
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Clear Result: "
                r2.append(r6)
                java.lang.Object r6 = r4.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                java.lang.String r6 = java.util.Arrays.toString(r6)
                r2.append(r6)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                java.lang.Object r2 = r4.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                r6 = 0
                r2 = r2[r6]
                java.lang.String[] r2 = r2.split(r14)
                if (r2 == 0) goto L_0x0b27
                r6 = 1
                r10 = r2[r6]
                if (r10 == 0) goto L_0x0b27
                r10 = 2
                r11 = r2[r10]
                if (r11 == 0) goto L_0x0b27
                r11 = r2[r6]
                java.lang.String r6 = r11.trim()
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                int r6 = r6.intValue()
                r11 = r2[r10]
                java.lang.String r10 = r11.trim()
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                int r10 = r10.intValue()
                if (r10 != 0) goto L_0x0af5
                if (r6 != 0) goto L_0x0af5
                r8 = 1
                goto L_0x0b29
            L_0x0af5:
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                java.lang.String r14 = "Mcf Ota: "
                r11.append(r14)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r14 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r16 = r2
                java.util.HashMap r2 = r14.mcfApplyOtaResult
                java.lang.String r2 = r14.getStringValueFromMap(r6, r2)
                r11.append(r2)
                java.lang.String r2 = "\nDsbp: "
                r11.append(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r14 = r2.mcfApplyDsbpResult
                java.lang.String r2 = r2.getStringValueFromMap(r10, r14)
                r11.append(r2)
                java.lang.String r2 = r11.toString()
                r19 = r2
                goto L_0x0b29
            L_0x0b27:
                r16 = r2
            L_0x0b29:
                r2 = r19
                goto L_0x0b76
            L_0x0b2c:
                r2 = r19
                goto L_0x0b76
            L_0x0b2f:
                java.lang.Throwable r10 = r4.exception
                java.lang.String r10 = r10.toString()
                java.lang.String[] r10 = r10.split(r2)
                r14 = 1
                r10 = r10[r14]
                if (r10 == 0) goto L_0x0b5a
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                r10.append(r11)
                java.lang.Throwable r11 = r4.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r2 = r11.split(r2)
                r2 = r2[r14]
                r10.append(r2)
                java.lang.String r2 = r10.toString()
                goto L_0x0b5b
            L_0x0b5a:
                r2 = r3
            L_0x0b5b:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                r10.append(r15)
                r10.append(r7)
                r10.append(r6)
                java.lang.Throwable r6 = r4.exception
                r10.append(r6)
                java.lang.String r6 = r10.toString()
                com.mediatek.engineermode.Elog.e(r9, r6)
            L_0x0b76:
                if (r8 == 0) goto L_0x0bfe
                int r5 = r1.what
                r6 = 1
                if (r5 != r6) goto L_0x0b8c
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.widget.TextView r5 = r5.otaFile
                r5.setText(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r5.targetOtaFile = r3
                goto L_0x0bae
            L_0x0b8c:
                int r5 = r1.what
                r6 = 3
                if (r5 != r6) goto L_0x0ba0
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.widget.TextView r5 = r5.opOtaFile
                r5.setText(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r5.targetOpOtaFile = r3
                goto L_0x0bae
            L_0x0ba0:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                android.widget.TextView r5 = r5.genOpOtaFile
                r5.setText(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.String unused = r5.targetGenOpOtaFile = r3
            L_0x0bae:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask r3 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.<init>()
                r5 = 1
                java.lang.Integer[] r6 = new java.lang.Integer[r5]
                int r9 = r1.what
                if (r9 != r5) goto L_0x0bbf
                r23 = 0
                goto L_0x0bc9
            L_0x0bbf:
                int r5 = r1.what
                r9 = 3
                if (r5 != r9) goto L_0x0bc7
                r23 = 1
                goto L_0x0bc9
            L_0x0bc7:
                r23 = 2
            L_0x0bc9:
                java.lang.Integer r5 = java.lang.Integer.valueOf(r23)
                r9 = 0
                r6[r9] = r5
                r3.execute(r6)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r15)
                r3.append(r7)
                java.lang.String r5 = " Succeed!"
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r5 = r1.what
                r6 = 1
                if (r5 != r6) goto L_0x0bf3
                r5 = 1
                goto L_0x0bf9
            L_0x0bf3:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r5 = r5.resetMd
            L_0x0bf9:
                r3.rebootModem(r5)
                goto L_0x03b6
            L_0x0bfe:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r15)
                r6.append(r7)
                r6.append(r5)
                if (r2 != 0) goto L_0x0c0f
                goto L_0x0c1e
            L_0x0c0f:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r13)
                r3.append(r2)
                java.lang.String r3 = r3.toString()
            L_0x0c1e:
                r6.append(r3)
                java.lang.String r3 = r6.toString()
                com.mediatek.engineermode.Elog.e(r9, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r15)
                r6.append(r7)
                r6.append(r12)
                java.lang.String r6 = r6.toString()
                r5.showDialog(r6, r3)
                goto L_0x03b6
            L_0x0c41:
                int r4 = r1.what
                if (r4 != 0) goto L_0x0c46
                goto L_0x0c50
            L_0x0c46:
                int r4 = r1.what
                r7 = 2
                if (r4 != r7) goto L_0x0c4e
                java.lang.String r7 = "OPOTA"
                goto L_0x0c50
            L_0x0c4e:
                java.lang.String r7 = "General OPOTA"
            L_0x0c50:
                r4 = r7
                java.lang.Object r7 = r1.obj
                android.os.AsyncResult r7 = (android.os.AsyncResult) r7
                r8 = 0
                java.lang.Throwable r10 = r7.exception
                if (r10 != 0) goto L_0x0cf5
                java.lang.Object r2 = r7.result
                if (r2 == 0) goto L_0x0cf2
                java.lang.Object r2 = r7.result
                boolean r2 = r2 instanceof java.lang.String[]
                if (r2 == 0) goto L_0x0cf2
                java.lang.Object r2 = r7.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                int r2 = r2.length
                if (r2 <= 0) goto L_0x0cf2
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Apply Result: "
                r2.append(r6)
                java.lang.Object r6 = r7.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                java.lang.String r6 = java.util.Arrays.toString(r6)
                r2.append(r6)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r9, r2)
                java.lang.Object r2 = r7.result
                java.lang.String[] r2 = (java.lang.String[]) r2
                r6 = 0
                r2 = r2[r6]
                java.lang.String[] r2 = r2.split(r14)
                if (r2 == 0) goto L_0x0cef
                r6 = 1
                r10 = r2[r6]
                if (r10 == 0) goto L_0x0cef
                r10 = 2
                r11 = r2[r10]
                if (r11 == 0) goto L_0x0cef
                r11 = r2[r6]
                java.lang.String r6 = r11.trim()
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                int r6 = r6.intValue()
                r11 = r2[r10]
                java.lang.String r10 = r11.trim()
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                int r10 = r10.intValue()
                if (r10 != 0) goto L_0x0cc0
                if (r6 != 0) goto L_0x0cc0
                r8 = 1
                goto L_0x0cef
            L_0x0cc0:
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                java.lang.String r14 = "Mcf Ota: "
                r11.append(r14)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r14 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r15 = r14.mcfApplyOtaResult
                java.lang.String r14 = r14.getStringValueFromMap(r6, r15)
                r11.append(r14)
                java.lang.String r14 = "\nDsbp: "
                r11.append(r14)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r14 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.util.HashMap r15 = r14.mcfApplyDsbpResult
                java.lang.String r14 = r14.getStringValueFromMap(r10, r15)
                r11.append(r14)
                java.lang.String r11 = r11.toString()
                r19 = r11
            L_0x0cef:
                r2 = r19
                goto L_0x0d3e
            L_0x0cf2:
                r2 = r19
                goto L_0x0d3e
            L_0x0cf5:
                java.lang.Throwable r10 = r7.exception
                java.lang.String r10 = r10.toString()
                java.lang.String[] r10 = r10.split(r2)
                r14 = 1
                r10 = r10[r14]
                if (r10 == 0) goto L_0x0d20
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                r10.append(r11)
                java.lang.Throwable r11 = r7.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r2 = r11.split(r2)
                r2 = r2[r14]
                r10.append(r2)
                java.lang.String r2 = r10.toString()
                goto L_0x0d21
            L_0x0d20:
                r2 = r3
            L_0x0d21:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r11 = "Apply "
                r10.append(r11)
                r10.append(r4)
                r10.append(r6)
                java.lang.Throwable r6 = r7.exception
                r10.append(r6)
                java.lang.String r6 = r10.toString()
                com.mediatek.engineermode.Elog.e(r9, r6)
            L_0x0d3e:
                if (r8 == 0) goto L_0x0d8c
                com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask r3 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                r3.<init>()
                r5 = 1
                java.lang.Integer[] r6 = new java.lang.Integer[r5]
                int r9 = r1.what
                if (r9 != 0) goto L_0x0d50
                r9 = 0
                goto L_0x0d58
            L_0x0d50:
                int r9 = r1.what
                r10 = 2
                if (r9 != r10) goto L_0x0d57
                r9 = r5
                goto L_0x0d58
            L_0x0d57:
                r9 = r10
            L_0x0d58:
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                r10 = 0
                r6[r10] = r9
                r3.execute(r6)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "Apply  "
                r3.append(r6)
                r3.append(r4)
                java.lang.String r6 = " Succeed!"
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                int r6 = r1.what
                if (r6 != 0) goto L_0x0d82
                goto L_0x0d88
            L_0x0d82:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                boolean r5 = r5.resetMd
            L_0x0d88:
                r3.rebootModem(r5)
                goto L_0x0dd2
            L_0x0d8c:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r10 = "Apply "
                r6.append(r10)
                r6.append(r4)
                r6.append(r5)
                if (r2 != 0) goto L_0x0d9f
                goto L_0x0dae
            L_0x0d9f:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r13)
                r3.append(r2)
                java.lang.String r3 = r3.toString()
            L_0x0dae:
                r6.append(r3)
                java.lang.String r3 = r6.toString()
                com.mediatek.engineermode.Elog.e(r9, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r9 = "Apply "
                r6.append(r9)
                r6.append(r4)
                r6.append(r12)
                java.lang.String r6 = r6.toString()
                r5.showDialog(r6, r3)
            L_0x0dd2:
                r3 = r2
                r2 = r7
                goto L_0x0dd9
            L_0x0dd5:
                r2 = r18
                r3 = r19
            L_0x0dd9:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mcfconfig.McfConfigActivity.AnonymousClass5.handleMessage(android.os.Message):void");
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsModemEnabled = true;
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    /* access modifiers changed from: private */
    public Spinner mSpinner;
    private TelephonyManager mTelephonyManager;
    /* access modifiers changed from: private */
    public final HashMap<Integer, String> mcfApplyDsbpResult = new HashMap<Integer, String>() {
        {
            put(0, "SUCCESS");
            put(1, "MCF_DSBP_ONGOING");
            put(2, "SIM_SWITCH_ONGOING");
            put(3, "ONGOING_CALL_OR_ECBM");
            put(4, "NO_SIM");
            put(5, "NOT_MODE2");
            put(6, "SIM_ERROR");
            put(7, "UNKNOWN");
        }
    };
    /* access modifiers changed from: private */
    public final HashMap<Integer, String> mcfApplyOtaResult = new HashMap<Integer, String>() {
        {
            put(0, "SUCCESS");
            put(1, "MCF_NOT_SUPPORT");
            put(2, "VERSION_NOT_MATCH");
            put(3, "WRONG_BUFFER_SIZE");
            put(4, "INVALID_PARAMETER");
            put(5, "READ_NVRAM_FAIL");
            put(6, "WRITE_NVRAM_FAIL");
            put(7, "READ_OTA_FILE_FAIL");
            put(8, "INVALID_SBP_TAG");
            put(9, "INVALID_FILE");
            put(10, "INVALID_ATTR");
            put(11, "TAKE_READ_LOCK_FAIL");
            put(12, "ALLOCATE_BUFFER_FAIL");
            put(13, "ENCRYPTION_FAIL");
            put(14, "DECRYPTION_FAIL");
            put(15, "CHECKSUM_ERROR");
            put(16, "DISK_FULL");
            put(17, "DUMP_SOME_ERROR");
        }
    };
    /* access modifiers changed from: private */
    public McfCertification mcfCertObj;
    private SharedPreferences mcfConfigSh;
    /* access modifiers changed from: private */
    public final HashMap<Integer, String> mcfOtaDumpResult = new HashMap<Integer, String>() {
        {
            put(0, "SUCCESS");
            put(1, "MCF_NOT_SUPPORT");
            put(2, "VERSION_NOT_MATCH");
            put(3, "WRONG_BUFFER_SIZE");
            put(4, "INVALID_PARAMETER");
            put(5, "READ_NVRAM_FAIL");
            put(6, "WRITE_NVRAM_FAIL");
            put(7, "READ_OTA_FILE_FAIL");
            put(8, "INVALID_SBP_TAG");
            put(9, "INVALID_FILE");
            put(10, "INVALID_ATTR");
            put(11, "TAKE_READ_LOCK_FAIL");
            put(12, "ALLOCATE_BUFFER_FAIL");
            put(13, "ENCRYPTION_FAIL");
            put(14, "DECRYPTION_FAIL");
            put(15, "CHECKSUM_ERROR");
            put(16, "WRITE_DISK_FAIL");
        }
    };
    /* access modifiers changed from: private */
    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.gen_opota_ota_path:
                    boolean unused = McfConfigActivity.this.isGenopotaOtaPath = true;
                    McfConfigActivity.this.genOpOtaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.gen_opota_runtime_path:
                    boolean unused2 = McfConfigActivity.this.isGenopotaOtaPath = false;
                    McfConfigActivity.this.genOpOtaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ini_disable:
                    int unused3 = McfConfigActivity.this.iniStatus = 0;
                    return;
                case R.id.ini_enable:
                    int unused4 = McfConfigActivity.this.iniStatus = 1;
                    return;
                case R.id.opota_ota_path:
                    boolean unused5 = McfConfigActivity.this.isOpotaOtaPath = true;
                    McfConfigActivity.this.opOtaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.opota_runtime_path:
                    boolean unused6 = McfConfigActivity.this.isOpotaOtaPath = false;
                    McfConfigActivity.this.opOtaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ota_ota_path:
                    boolean unused7 = McfConfigActivity.this.isOtaOtaPath = true;
                    McfConfigActivity.this.otaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ota_path:
                    boolean unused8 = McfConfigActivity.this.isOtaPath = true;
                    return;
                case R.id.ota_runtime_path:
                    boolean unused9 = McfConfigActivity.this.isOtaOtaPath = false;
                    McfConfigActivity.this.otaFile.setText("");
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.runtime_path:
                    boolean unused10 = McfConfigActivity.this.isOtaPath = false;
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int opId;
    /* access modifiers changed from: private */
    public TextView opOtaFile;
    /* access modifiers changed from: private */
    public RadioGroup opOtaPathRgroup;
    private Button opOtaQueryBtn;
    private Button opOtaQueryBtnG98;
    private CheckBox opotaResetMdEnable;
    private LinearLayout opotaView;
    /* access modifiers changed from: private */
    public TextView otaFile;
    /* access modifiers changed from: private */
    public RadioGroup otaPathRgroup;
    private Button otaQueryBtn;
    private Button otaQueryBtnG98;
    /* access modifiers changed from: private */
    public RadioGroup pathRgroup;
    /* access modifiers changed from: private */
    public int phoneid;
    /* access modifiers changed from: private */
    public int readySim;
    /* access modifiers changed from: private */
    public boolean resetMd = true;
    /* access modifiers changed from: private */
    public EditText sertInputEt;
    int simType;
    /* access modifiers changed from: private */
    public String targetGenOpOtaFile;
    /* access modifiers changed from: private */
    public String targetOpOtaFile;
    /* access modifiers changed from: private */
    public String targetOtaFile;
    /* access modifiers changed from: private */
    public Set taskSet = new HashSet();
    CountDownTimer timer;
    /* access modifiers changed from: private */
    public int viewId;

    /* access modifiers changed from: package-private */
    public String getStringValueFromMap(int key, HashMap<Integer, String> sourceSet) {
        if (sourceSet.containsKey(Integer.valueOf(key))) {
            return sourceSet.get(Integer.valueOf(key));
        }
        return "" + key;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int readySim2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_mcf_config);
        this.isG98 = FeatureSupport.is98Modem();
        this.opotaResetMdEnable = (CheckBox) findViewById(R.id.resetmd_enable);
        this.genOpotaResetMdEnable = (CheckBox) findViewById(R.id.gen_resetmd_enable);
        this.opotaView = (LinearLayout) findViewById(R.id.mcf_opota_view);
        this.generalView = (LinearLayout) findViewById(R.id.mcf_general_view);
        this.certificationView = (LinearLayout) findViewById(R.id.mcf_certification_view);
        this.addOtaBtn = (Button) findViewById(R.id.add_ota_browser);
        this.addOpOtaBtn = (Button) findViewById(R.id.add_op_ota_browser);
        this.addGenOpOtaBtn = (Button) findViewById(R.id.add_general_opota_browser);
        this.otaQueryBtn = (Button) findViewById(R.id.ota_query);
        this.certotaQueryBtn = (Button) findViewById(R.id.cert_ota_query);
        this.opOtaQueryBtn = (Button) findViewById(R.id.opota_query);
        this.otaQueryBtnG98 = (Button) findViewById(R.id.g98_ota_query);
        this.certotaQueryBtnG98 = (Button) findViewById(R.id.g98_cert_ota_query);
        this.opOtaQueryBtnG98 = (Button) findViewById(R.id.g98_opota_query);
        this.genOpOtaQueryBtnG98 = (Button) findViewById(R.id.g98_gen_opota_query);
        this.addOtaBtn.setOnClickListener(this);
        this.addOpOtaBtn.setOnClickListener(this);
        this.addGenOpOtaBtn.setOnClickListener(this);
        this.applyOtaBtn = (Button) findViewById(R.id.ota_apply);
        this.clearOtaBtn = (Button) findViewById(R.id.ota_clear);
        this.applyOpOtaBtn = (Button) findViewById(R.id.opota_apply);
        this.clearOpOtaBtn = (Button) findViewById(R.id.opota_clear);
        this.applyGenOpOtaBtn = (Button) findViewById(R.id.gen_opota_apply);
        this.clearGenOpOtaBtn = (Button) findViewById(R.id.gen_opota_clear);
        this.dumpOta = (Button) findViewById(R.id.ota_dump);
        this.dumpOtaToModem = (Button) findViewById(R.id.ota_dump_modem);
        this.iniRefreshBtn = (Button) findViewById(R.id.ini_refresh);
        this.iniStatusGroup = (RadioGroup) findViewById(R.id.ini_refresh_group);
        this.certBtn = (Button) findViewById(R.id.cert_bt);
        this.pathRgroup = (RadioGroup) findViewById(R.id.path_rgroup);
        this.otaPathRgroup = (RadioGroup) findViewById(R.id.gen_ota_path_rgroup);
        this.opOtaPathRgroup = (RadioGroup) findViewById(R.id.opota_path_rgroup);
        this.genOpotaPathRgroup = (RadioGroup) findViewById(R.id.gen_opota_path_rgroup);
        this.isOtaOtaPathBt = (RadioButton) findViewById(R.id.ota_ota_path);
        this.isOtaRuntimePathBt = (RadioButton) findViewById(R.id.ota_runtime_path);
        this.isGenopotaOtaPathBt = (RadioButton) findViewById(R.id.gen_opota_ota_path);
        this.isGenopotaRuntimePathBt = (RadioButton) findViewById(R.id.gen_opota_runtime_path);
        this.isOpotaOtaPathBt = (RadioButton) findViewById(R.id.opota_ota_path);
        this.isOpotaRuntimePathBt = (RadioButton) findViewById(R.id.opota_runtime_path);
        this.mSpinner = (Spinner) findViewById(R.id.op_select);
        this.sertInputEt = (EditText) findViewById(R.id.cert_input_et);
        this.applyOtaBtn.setOnClickListener(this);
        this.clearOtaBtn.setOnClickListener(this);
        this.applyOpOtaBtn.setOnClickListener(this);
        this.clearOpOtaBtn.setOnClickListener(this);
        this.applyGenOpOtaBtn.setOnClickListener(this);
        this.clearGenOpOtaBtn.setOnClickListener(this);
        this.dumpOta.setOnClickListener(this);
        this.dumpOtaToModem.setOnClickListener(this);
        this.iniRefreshBtn.setOnClickListener(this);
        this.otaQueryBtn.setOnClickListener(this);
        this.certotaQueryBtn.setOnClickListener(this);
        this.opOtaQueryBtn.setOnClickListener(this);
        this.otaQueryBtnG98.setOnClickListener(this);
        this.certotaQueryBtnG98.setOnClickListener(this);
        this.opOtaQueryBtnG98.setOnClickListener(this);
        this.genOpOtaQueryBtnG98.setOnClickListener(this);
        this.certBtn.setOnClickListener(this);
        this.sertInputEt.setVisibility(8);
        this.curG98OtaFile = new ArrayList<>();
        if (this.isG98) {
            this.otaQueryBtn.setVisibility(8);
            this.certotaQueryBtn.setVisibility(8);
            this.opOtaQueryBtn.setVisibility(8);
            this.dumpOta.setText(getResources().getString(R.string.str_dump_runtime));
        } else {
            this.otaQueryBtnG98.setVisibility(8);
            this.certotaQueryBtnG98.setVisibility(8);
            this.opOtaQueryBtnG98.setVisibility(8);
            this.dumpOtaToModem.setVisibility(8);
            this.genOpOtaQueryBtnG98.setVisibility(8);
        }
        this.opOtaFile = (TextView) findViewById(R.id.op_ota_file);
        this.otaFile = (TextView) findViewById(R.id.ota_file);
        this.genOpOtaFile = (TextView) findViewById(R.id.general_opota_file);
        this.isOtaPath = false;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mcf_cert_arrays, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mSpinner.setAdapter(adapter);
        int intExtra = getIntent().getIntExtra("mSimType", 0);
        this.simType = intExtra;
        if (intExtra == McfSimSelectActivity.SHOW_GENERAL_VIEW) {
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(0);
            this.certificationView.setVisibility(8);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 1;
        } else if (this.simType == McfSimSelectActivity.SHOW_CERTIFICATION_VIEW) {
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(8);
            this.certificationView.setVisibility(0);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 2;
            this.mSpinner.setSelection(1);
        } else {
            this.opotaView.setVisibility(0);
            this.generalView.setVisibility(8);
            this.certificationView.setVisibility(8);
            this.phoneid = this.simType;
            this.viewId = 0;
        }
        if (isSimReady(this.phoneid)) {
            readySim2 = this.phoneid;
        } else if (isSimReady((this.phoneid + 1) % 2)) {
            readySim2 = (this.phoneid + 1) % 2;
        } else {
            readySim2 = ModemCategory.getCapabilitySim();
        }
        Elog.d("McfConfig", "Selected: Sim" + this.phoneid + ", Ready: Sim" + readySim2);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        EmUtils.registerForModemStatusChanged(readySim2, this.mHandler, 102);
        EmUtils.registerForUrcInfo(readySim2, this.mHandler, 16);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        this.mcfConfigSh = getSharedPreferences("mcf_config_settings", 0);
        new loadOTAPathTask().execute(new Integer[]{0});
        new loadOTAPathTask().execute(new Integer[]{2});
        this.mcfCertObj = new McfCertification();
        this.iniStatusGroup.setOnCheckedChangeListener(this.onCheckedChangeListener);
    }

    public boolean isSimReady(int slotId) {
        int status;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        if (slotId < 0) {
            status = telephonyManager.getSimState();
        } else {
            status = telephonyManager.getSimState(slotId);
        }
        if (status == 1) {
            return false;
        }
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        Elog.d("McfConfig", "onDestroy");
        this.taskSet.clear();
        EmUtils.unregisterForUrcInfo(this.readySim);
        EmUtils.unregisterForModemStatusChanged(this.readySim);
    }

    public void onBackPressed() {
        if (this.taskSet.contains(2)) {
            showDialog("Please Wait", "Reboot modem is ongoing......");
            return;
        }
        this.taskSet.clear();
        finish();
    }

    private void selectFile(String targetPath, int requestCode) {
        Elog.d("McfConfig", "[selectFile] storagePath: " + targetPath);
        McfFileSelectActivity.actionStart(this, targetPath, requestCode);
    }

    /* access modifiers changed from: private */
    public void showDialog(String title, String msg) {
        if (!isFinishing()) {
            new AlertDialog.Builder(this).setCancelable(true).setTitle(title).setMessage(msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create().show();
        }
    }

    /* access modifiers changed from: private */
    public void showProgressDialog(String title, String msg) {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this);
        }
        this.mProgressDialog.setTitle(title);
        this.mProgressDialog.setMessage(msg);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.show();
        Elog.d("McfConfig", "After timer.start");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            Elog.e("McfConfig", "[onActivityResult] error, resultCode: " + resultCode);
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        Uri uri = data.getData();
        StringBuilder sb = new StringBuilder();
        sb.append("[getSelectedFilePath] uri: ");
        sb.append(uri != null ? uri.toString() : "NULL");
        Elog.d("McfConfig", sb.toString());
        if (uri != null) {
            String srcOtaPath = uri.getPath();
            Elog.i("McfConfig", "[onActivityResult] otaFile: " + srcOtaPath);
            switch (requestCode) {
                case 0:
                    boolean checkPathValid = checkPathValid(srcOtaPath, McfFileSelectActivity.OTA_SUFFIX);
                    this.isOtaPathValid = checkPathValid;
                    if (!checkPathValid) {
                        this.otaFile.setText("");
                        showDialog("Select OTA Path: ", "Invalid File! (ex:*.mcfota)");
                        break;
                    } else {
                        this.targetOtaFile = srcOtaPath;
                        this.otaFile.setText(srcOtaPath);
                        Elog.d("McfConfig", "isOtaPathValid: " + this.isOtaPathValid + ",targetOtaPath :" + this.targetOtaFile);
                        break;
                    }
                case 1:
                    boolean checkPathValid2 = checkPathValid(srcOtaPath, McfFileSelectActivity.OP_OTA_SUFFIX);
                    this.isOpOtaPathValid = checkPathValid2;
                    if (!checkPathValid2) {
                        this.opOtaFile.setText("");
                        showDialog("Select OP-OTA Path: ", "Invalid File! (ex:*.mcfopota)");
                        break;
                    } else {
                        this.targetOpOtaFile = srcOtaPath;
                        this.opOtaFile.setText(srcOtaPath);
                        Elog.d("McfConfig", "isOpOtaPathValid: " + this.isOpOtaPathValid + ",targetOpOtaPath :" + this.targetOpOtaFile + ", " + this.opOtaFile.getText());
                        break;
                    }
                case 2:
                    boolean checkPathValid3 = checkPathValid(srcOtaPath, McfFileSelectActivity.OP_OTA_SUFFIX);
                    this.isGenOpOtaPathValid = checkPathValid3;
                    if (!checkPathValid3) {
                        this.genOpOtaFile.setText("");
                        showDialog("Select OP-OTA Path: ", "Invalid File! (ex:*.mcfopota)");
                        break;
                    } else {
                        this.targetGenOpOtaFile = srcOtaPath;
                        this.genOpOtaFile.setText(srcOtaPath);
                        Elog.d("McfConfig", "isGenOpOtaPathValid: " + this.isGenOpOtaPathValid + ",targetGenOpOtaFile :" + this.targetGenOpOtaFile);
                        break;
                    }
                default:
                    Elog.d("McfConfig", "unsupport requestCode :" + requestCode);
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPathValid(String filePath, String[] suffix) {
        if (suffix == null) {
            return true;
        }
        String fileExtension = FileUtils.getFileExtension(filePath);
        if (fileExtension != null && Arrays.asList(suffix).contains(fileExtension)) {
            return true;
        }
        Elog.e("McfConfig", "[checkPathValid] file path is InValid " + filePath + " suffixList:" + Arrays.toString(suffix));
        return false;
    }

    /* access modifiers changed from: private */
    public void sendATCommand(String cmd, int what) {
        String[] atCmd = {cmd, "+EMCFC:"};
        Elog.e("McfConfig", "[sendATCommand] cmd: " + Arrays.toString(atCmd) + ", what:" + what);
        EmUtils.invokeOemRilRequestStringsEm(this.phoneid, atCmd, this.mHandler.obtainMessage(what));
    }

    private void sendATCommand(int phoneNum, String cmd, int what) {
        String[] atCmd = {cmd, "+EMCFC:"};
        if (!this.mcfCertObj.getCurCmdResult(phoneNum)) {
            if (this.mcfCertObj.getCurrentCmd(phoneNum) == 1 || isSimReady(phoneNum)) {
                Elog.e("McfConfig", "[sendATCommand] cmd: " + Arrays.toString(atCmd) + ", what:" + what + ", phoneid: " + phoneNum);
                EmUtils.invokeOemRilRequestStringsEm(phoneNum, atCmd, this.mHandler.obtainMessage((phoneNum * 6) + what));
                return;
            }
            this.mcfCertObj.setCmdResult(phoneNum, new boolean[]{true, true, true});
            Elog.e("McfConfig", "[sendATCommand], sim" + phoneNum + " is absent!");
        }
    }

    /* access modifiers changed from: private */
    public void rebootModem(boolean needReset) {
        Elog.d("McfConfig", "[rebootModem] begining ... needReset:" + needReset);
        if (needReset) {
            this.taskSet.add(2);
            EmUtils.rebootModem();
            this.mIsModemEnabled = false;
            startTimer(2, 180);
        }
    }

    public void getSelectedFilePath(Uri contentUri) {
        StringBuilder sb = new StringBuilder();
        sb.append("[getSelectedFilePath] uri: ");
        sb.append(contentUri != null ? contentUri.toString() : "NULL");
        Elog.d("McfConfig", sb.toString());
        if (contentUri != null) {
            Elog.d("McfConfig", "[getUriForFile] path :" + contentUri.getPath());
        }
    }

    public void startTimer(int type, int waitTime) {
        Elog.d("McfConfig", "waitTime = " + waitTime + " s");
        final int i = type;
        AnonymousClass7 r2 = new CountDownTimer(1000 * ((long) waitTime), 1000) {
            public void onTick(long millisUntilFinishied) {
                int curId;
                if (i == 2) {
                    McfConfigActivity mcfConfigActivity = McfConfigActivity.this;
                    if (mcfConfigActivity.isSimReady(mcfConfigActivity.readySim)) {
                        curId = McfConfigActivity.this.readySim;
                    } else {
                        McfConfigActivity mcfConfigActivity2 = McfConfigActivity.this;
                        curId = mcfConfigActivity2.isSimReady(mcfConfigActivity2.readySim + 1) ? (McfConfigActivity.this.readySim + 1) % 2 : ModemCategory.getCapabilitySim();
                    }
                    EmRadioHidlAosp.getModemStatus(curId);
                }
            }

            public void onFinish() {
                Elog.d("McfConfig", "timer Timeout.......");
                boolean unused = McfConfigActivity.this.mIsModemEnabled = true;
                if (McfConfigActivity.this.timer != null) {
                    McfConfigActivity.this.timer.cancel();
                }
                if (McfConfigActivity.this.mProgressDialog != null) {
                    McfConfigActivity.this.mProgressDialog.dismiss();
                    ProgressDialog unused2 = McfConfigActivity.this.mProgressDialog = null;
                }
                int i = i;
                if (i == 1) {
                    EmUtils.showToast("Dump OTA timeout!");
                } else if (i == 3) {
                    McfConfigActivity.this.showDialog("Ini Refresh ", "Ini Refresh timeout!");
                } else if (i == 2) {
                    McfConfigActivity.this.showDialog("Reboot Modem", "Reboot modem timeout!");
                } else if (i == 4) {
                    McfConfigActivity.this.showDialog("Query OTA", "Query OTA timeout!");
                }
                McfConfigActivity.this.timer = null;
                McfConfigActivity.this.taskSet.remove(Integer.valueOf(i));
            }
        };
        this.timer = r2;
        r2.start();
        Elog.d("McfConfig", "Start timer for " + type + ", WAIT_TIME=" + waitTime + "s");
    }

    public void onClick(View v) {
        String setGenOpOtaCmd;
        int opDirPos;
        String setOpOtaCmd;
        int opDirPos2;
        String setOtaCmd;
        int dirPos;
        String str = "";
        String str2 = "/vendor/etc/mdota/";
        switch (v.getId()) {
            case R.id.add_general_opota_browser:
                if (!this.isGenopotaOtaPath) {
                    str2 = "/mnt/vendor/nvcfg/mdota/";
                }
                selectFile(str2, 2);
                return;
            case R.id.add_op_ota_browser:
                if (!this.isOpotaOtaPath) {
                    str2 = "/mnt/vendor/nvcfg/mdota/";
                }
                selectFile(str2, 1);
                return;
            case R.id.add_ota_browser:
                if (!this.isOtaOtaPath) {
                    str2 = "/mnt/vendor/nvcfg/mdota/";
                }
                selectFile(str2, 0);
                return;
            case R.id.cert_bt:
                if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                }
                Elog.d("McfConfig", "certification begain " + this.opId + ", isOtaPath: " + this.isOtaPath);
                McfCertification mcfCertification = new McfCertification();
                this.mcfCertObj = mcfCertification;
                mcfCertification.setOpid(this.opId);
                this.mcfCertObj.setCmdResult(new boolean[][]{new boolean[]{false, false, false}, new boolean[]{false, false, false}});
                switch (this.opId) {
                    case 17:
                        this.inputOp = this.sertInputEt.getText().toString().replace(" ", str).toUpperCase();
                        Elog.d("McfConfig", "Select operator is " + this.inputOp);
                        this.mcfCertObj.setOpName(this.inputOp);
                        this.mcfCertObj.setOtaFileName("MTK_OTA_" + this.inputOp + ".mcfota");
                        this.mcfCertObj.setOpotaFileName("MTK_OPOTA_" + this.inputOp + ".mcfopota");
                        McfCertification mcfCertification2 = this.mcfCertObj;
                        if (!this.isOtaPath) {
                            str2 = "/mnt/vendor/nvcfg/mdota/";
                        }
                        mcfCertification2.setFilePath(str2);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        new FileLoadTask().execute(new String[]{this.mcfCertObj.getFilePath()});
                        return;
                    case 18:
                        this.mcfCertObj.setOpName(str);
                        this.mcfCertObj.setCurrentCmd(this.phoneid, 0);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        doCertificationAction(this.phoneid);
                        return;
                    case 19:
                        this.mcfCertObj.setOpName("CMCC");
                        this.mcfCertObj.setOtaFileName("MTK_OTA_CMCC.mcfota");
                        this.mcfCertObj.setOpotaFileName("MTK_OPOTA_CMCC.mcfopota");
                        McfCertification mcfCertification3 = this.mcfCertObj;
                        if (!this.isOtaPath) {
                            str2 = "/mnt/vendor/nvcfg/mdota/";
                        }
                        mcfCertification3.setFilePath(str2);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        new FileLoadTask().execute(new String[]{this.mcfCertObj.getFilePath()});
                        return;
                    case 20:
                        this.mcfCertObj.setOpName("CT");
                        this.mcfCertObj.setOtaFileName("MTK_OTA_CT.mcfota");
                        this.mcfCertObj.setOpotaFileName("MTK_OPOTA_CT.mcfopota");
                        McfCertification mcfCertification4 = this.mcfCertObj;
                        if (!this.isOtaPath) {
                            str2 = "/mnt/vendor/nvcfg/mdota/";
                        }
                        mcfCertification4.setFilePath(str2);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        new FileLoadTask().execute(new String[]{this.mcfCertObj.getFilePath()});
                        return;
                    case 21:
                        this.mcfCertObj.setOpName("CU");
                        this.mcfCertObj.setOtaFileName("MTK_OTA_CU.mcfota");
                        this.mcfCertObj.setOpotaFileName("MTK_OPOTA_CU.mcfopota");
                        McfCertification mcfCertification5 = this.mcfCertObj;
                        if (!this.isOtaPath) {
                            str2 = "/mnt/vendor/nvcfg/mdota/";
                        }
                        mcfCertification5.setFilePath(str2);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        new FileLoadTask().execute(new String[]{this.mcfCertObj.getFilePath()});
                        return;
                    case 22:
                        this.mcfCertObj.setOpName("CERT_COMMON");
                        this.mcfCertObj.setOtaFileName("MTK_OTA_CERT_COMMON.mcfota");
                        this.mcfCertObj.setOpotaFileName("MTK_OPOTA_CERT_COMMON.mcfopota");
                        McfCertification mcfCertification6 = this.mcfCertObj;
                        if (!this.isOtaPath) {
                            str2 = "/mnt/vendor/nvcfg/mdota/";
                        }
                        mcfCertification6.setFilePath(str2);
                        this.mcfCertObj.setPathFlag(this.isOtaPath ^ true ? 1 : 0);
                        new FileLoadTask().execute(new String[]{this.mcfCertObj.getFilePath()});
                        return;
                    default:
                        return;
                }
            case R.id.cert_ota_query:
                Elog.d("McfConfig", "Cert Ota query begain");
                this.opId = 8;
                sendATCommand("AT+EMCFC=4,0", 8);
                return;
            case R.id.g98_cert_ota_query:
                Elog.d("McfConfig", "G98 Cert Ota query begain");
                this.opId = 13;
                this.curG98OtaFile.clear();
                sendATCommand("AT+EMCFC=4,0,0", 13);
                return;
            case R.id.g98_gen_opota_query:
                Elog.d("McfConfig", "G98 General OpOta query begain");
                this.opId = 14;
                this.curG98OtaFile.clear();
                sendATCommand("AT+EMCFC=4,2,0", 14);
                return;
            case R.id.g98_opota_query:
                Elog.d("McfConfig", "G98 OpOta query begain");
                this.opId = 12;
                this.curG98OtaFile.clear();
                sendATCommand("AT+EMCFC=4,1,0", 12);
                return;
            case R.id.g98_ota_query:
                Elog.d("McfConfig", "G98 Ota query begain");
                this.opId = 11;
                this.curG98OtaFile.clear();
                sendATCommand("AT+EMCFC=4,0,0", 11);
                return;
            case R.id.gen_opota_apply:
                String str3 = (String) this.genOpOtaFile.getText();
                this.targetGenOpOtaFile = str3;
                this.isGenOpOtaPathValid = checkPathValid(str3, McfFileSelectActivity.OP_OTA_SUFFIX);
                String setGenOpOtaCmd2 = "AT+EMCFC=6,2," + (this.isGenopotaOtaPath ^ true ? 1 : 0) + ",";
                if (!this.isGenOpOtaPathValid) {
                    showDialog("Apply General Op-OTA Error", "OP-OTA File is invalid!");
                    return;
                } else if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    String str4 = this.targetGenOpOtaFile;
                    if (str4 == null || str4.trim().equals(str)) {
                        setGenOpOtaCmd = setGenOpOtaCmd2 + "\"\",0";
                    } else {
                        if (this.isGenopotaOtaPath) {
                            opDirPos = this.targetGenOpOtaFile.lastIndexOf(str2) + str2.length();
                        } else {
                            opDirPos = this.targetGenOpOtaFile.lastIndexOf("/mnt/vendor/nvcfg/mdota/") + "/mnt/vendor/nvcfg/mdota/".length();
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(setGenOpOtaCmd2);
                        sb.append("\"");
                        if (this.isGenOpOtaPathValid && opDirPos >= 0) {
                            str = this.targetGenOpOtaFile.substring(opDirPos);
                        }
                        sb.append(str);
                        sb.append("\",0");
                        setGenOpOtaCmd = sb.toString();
                    }
                    this.resetMd = this.genOpotaResetMdEnable.isChecked();
                    this.opId = 4;
                    sendATCommand(setGenOpOtaCmd + (this.resetMd ^ true ? 1 : 0), 4);
                    return;
                }
            case R.id.gen_opota_clear:
                if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                }
                this.resetMd = this.genOpotaResetMdEnable.isChecked();
                this.opId = 5;
                sendATCommand(("AT+EMCFC=6,2," + (this.isGenopotaOtaPath ^ true ? 1 : 0) + ",\"\",") + (this.resetMd ^ true ? 1 : 0), 5);
                return;
            case R.id.ini_refresh:
                Elog.d("McfConfig", "Ini refresh begain, iniStatus:" + this.iniStatus);
                this.opId = 7;
                sendATCommand("AT+EMCFC=7," + this.iniStatus, 7);
                return;
            case R.id.opota_apply:
                String str5 = (String) this.opOtaFile.getText();
                this.targetOpOtaFile = str5;
                this.isOpOtaPathValid = checkPathValid(str5, McfFileSelectActivity.OP_OTA_SUFFIX);
                String setOpOtaCmd2 = "AT+EMCFC=6,1," + (this.isOpotaOtaPath ^ true ? 1 : 0) + ",";
                if (!this.isOpOtaPathValid) {
                    showDialog("Apply Op-OTA Error", "OP-OTA File is invalid!");
                    return;
                } else if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    String str6 = this.targetOpOtaFile;
                    if (str6 == null || str6.trim().equals(str)) {
                        setOpOtaCmd = setOpOtaCmd2 + "\"\",0";
                    } else {
                        if (this.isOpotaOtaPath) {
                            opDirPos2 = this.targetOpOtaFile.lastIndexOf(str2) + str2.length();
                        } else {
                            opDirPos2 = this.targetOpOtaFile.lastIndexOf("/mnt/vendor/nvcfg/mdota/") + "/mnt/vendor/nvcfg/mdota/".length();
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(setOpOtaCmd2);
                        sb2.append("\"");
                        if (this.isOpOtaPathValid && opDirPos2 >= 0) {
                            str = this.targetOpOtaFile.substring(opDirPos2);
                        }
                        sb2.append(str);
                        sb2.append("\",");
                        setOpOtaCmd = sb2.toString();
                    }
                    this.resetMd = this.opotaResetMdEnable.isChecked();
                    this.opId = 2;
                    sendATCommand(setOpOtaCmd + (this.resetMd ^ true ? 1 : 0), 2);
                    return;
                }
            case R.id.opota_clear:
                if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                }
                this.resetMd = this.opotaResetMdEnable.isChecked();
                this.opId = 3;
                sendATCommand(("AT+EMCFC=6,1," + (this.isOpotaOtaPath ^ true ? 1 : 0) + ",\"\",") + (this.resetMd ^ true ? 1 : 0), 3);
                return;
            case R.id.opota_query:
                Elog.d("McfConfig", "OpOta query begain");
                this.opId = 9;
                sendATCommand("AT+EMCFC=4,1", 9);
                return;
            case R.id.ota_apply:
                this.resetMd = true;
                this.opId = 0;
                String str7 = (String) this.otaFile.getText();
                this.targetOtaFile = str7;
                this.isOtaPathValid = checkPathValid(str7, McfFileSelectActivity.OTA_SUFFIX);
                String setOtaCmd2 = "AT+EMCFC=6,0," + (this.isOtaOtaPath ^ true ? 1 : 0) + ",";
                if (!this.isOtaPathValid) {
                    showDialog("Apply OTA Error", "OTA File is invalid!");
                    return;
                } else if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    String str8 = this.targetOtaFile;
                    if (str8 == null || str8.trim().equals(str)) {
                        setOtaCmd = setOtaCmd2 + "\"\",0";
                    } else {
                        if (this.isOtaOtaPath) {
                            dirPos = this.targetOtaFile.lastIndexOf(str2) + str2.length();
                        } else {
                            dirPos = this.targetOtaFile.lastIndexOf("/mnt/vendor/nvcfg/mdota/") + "/mnt/vendor/nvcfg/mdota/".length();
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(setOtaCmd2);
                        sb3.append("\"");
                        if (this.isOtaPathValid && dirPos >= 0) {
                            str = this.targetOtaFile.substring(dirPos);
                        }
                        sb3.append(str);
                        sb3.append("\",0");
                        setOtaCmd = sb3.toString();
                    }
                    Elog.d("McfConfig", "ATCommand: " + setOtaCmd);
                    sendATCommand(setOtaCmd, 0);
                    return;
                }
            case R.id.ota_clear:
                this.resetMd = true;
                this.opId = 1;
                if (this.taskSet.contains(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                }
                sendATCommand("AT+EMCFC=6,0," + (this.isOtaOtaPath ^ true ? 1 : 0) + ",\"\",0", 1);
                return;
            case R.id.ota_dump:
                this.dumpBegain = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Elog.d("McfConfig", "Dump OTA file begain :" + df.format(new Date(this.dumpBegain)));
                this.opId = 6;
                sendATCommand("AT+EMCFC=5", 6);
                return;
            case R.id.ota_dump_modem:
                this.dumpBegain = System.currentTimeMillis();
                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
                Elog.d("McfConfig", "Dump OTA file begain :" + df1.format(new Date(this.dumpBegain)));
                this.opId = 6;
                sendATCommand("AT+EMCFC=12", 6);
                return;
            case R.id.ota_query:
                Elog.d("McfConfig", "Ota query begain");
                this.opId = 8;
                sendATCommand("AT+EMCFC=4,0", 8);
                return;
            default:
                return;
        }
    }

    class saveOTAPathTask extends AsyncTask<Integer, Void, Void> {
        saveOTAPathTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Integer... params) {
            Elog.d("McfConfig", "[saveOTAPathTask] " + params[0]);
            McfConfigActivity.this.saveSharedPreference(params[0].intValue());
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            Elog.d("McfConfig", "Save OTA file path success!");
        }
    }

    class loadOTAPathTask extends AsyncTask<Integer, Void, Integer> {
        loadOTAPathTask() {
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Integer... params) {
            switch (params[0].intValue()) {
                case 0:
                    McfConfigActivity.this.getSharedPreference();
                    break;
                case 1:
                    McfConfigActivity.this.reloadOtaPathSharedPreference();
                    break;
                case 2:
                    McfConfigActivity.this.getCertPathSharedPreference();
                    break;
            }
            return params[0];
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            if (McfConfigActivity.this.viewId == 1) {
                if (result.intValue() == 0) {
                    McfConfigActivity.this.otaPathRgroup.clearCheck();
                    McfConfigActivity.this.otaPathRgroup.check((McfConfigActivity.this.isOtaOtaPath ? McfConfigActivity.this.isOtaOtaPathBt : McfConfigActivity.this.isOtaRuntimePathBt).getId());
                    McfConfigActivity.this.genOpotaPathRgroup.clearCheck();
                    McfConfigActivity.this.genOpotaPathRgroup.check((McfConfigActivity.this.isGenopotaOtaPath ? McfConfigActivity.this.isGenopotaOtaPathBt : McfConfigActivity.this.isGenopotaRuntimePathBt).getId());
                    McfConfigActivity.this.otaPathRgroup.setOnCheckedChangeListener(McfConfigActivity.this.onCheckedChangeListener);
                    McfConfigActivity.this.genOpotaPathRgroup.setOnCheckedChangeListener(McfConfigActivity.this.onCheckedChangeListener);
                }
                McfConfigActivity.this.otaFile.setText(McfConfigActivity.this.targetOtaFile);
                McfConfigActivity.this.genOpOtaFile.setText(McfConfigActivity.this.targetGenOpOtaFile);
            } else if (McfConfigActivity.this.viewId == 0) {
                if (result.intValue() == 0) {
                    McfConfigActivity.this.opOtaPathRgroup.clearCheck();
                    McfConfigActivity.this.opOtaPathRgroup.check((McfConfigActivity.this.isOpotaOtaPath ? McfConfigActivity.this.isOpotaOtaPathBt : McfConfigActivity.this.isOpotaRuntimePathBt).getId());
                    McfConfigActivity.this.opOtaPathRgroup.setOnCheckedChangeListener(McfConfigActivity.this.onCheckedChangeListener);
                }
                McfConfigActivity.this.opOtaFile.setText(McfConfigActivity.this.targetOpOtaFile);
            } else if (McfConfigActivity.this.viewId == 2) {
                McfConfigActivity.this.pathRgroup.clearCheck();
                McfConfigActivity.this.pathRgroup.check(McfConfigActivity.this.isOtaPath ? R.id.ota_path : R.id.runtime_path);
                McfConfigActivity.this.mSpinner.setSelection(McfConfigActivity.this.opId - 17);
                McfConfigActivity.this.pathRgroup.setOnCheckedChangeListener(McfConfigActivity.this.onCheckedChangeListener);
                McfConfigActivity.this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        int unused = McfConfigActivity.this.opId = arg2 + 17;
                        if (McfConfigActivity.this.opId == 17) {
                            McfConfigActivity.this.sertInputEt.setVisibility(0);
                        } else {
                            McfConfigActivity.this.sertInputEt.setVisibility(8);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                if (McfConfigActivity.this.opId % 17 == 0) {
                    McfConfigActivity.this.sertInputEt.setText(McfConfigActivity.this.inputOp);
                    McfConfigActivity.this.sertInputEt.setVisibility(0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void getCertPathSharedPreference() {
        this.isOtaPath = this.mcfConfigSh.getBoolean("is_otapath", true);
        this.opId = this.mcfConfigSh.getInt("op_id", 18);
        this.inputOp = this.mcfConfigSh.getString("input_op_id", "");
    }

    /* access modifiers changed from: private */
    public void getSharedPreference() {
        boolean z = this.mcfConfigSh.getBoolean("ota_is_otapath", true);
        this.isOtaOtaPath = z;
        this.targetOtaFile = this.mcfConfigSh.getString(z ? "ota_file_otapath" : "ota_file_runtimepath", "");
        boolean z2 = this.mcfConfigSh.getBoolean("general_opota_is_otapath", true);
        this.isGenopotaOtaPath = z2;
        this.targetGenOpOtaFile = this.mcfConfigSh.getString(z2 ? "general_opota_otapath" : "general_opota_runtimepath", "");
        if (this.phoneid == 1) {
            boolean z3 = this.mcfConfigSh.getBoolean("sim2_opota_is_otapath", true);
            this.isOpotaOtaPath = z3;
            this.targetOpOtaFile = this.mcfConfigSh.getString(z3 ? "sim2_opota_file_otapath" : "sim2_opota_file_runtimepath", "");
            return;
        }
        boolean z4 = this.mcfConfigSh.getBoolean("sim1_opota_is_otapath", true);
        this.isOpotaOtaPath = z4;
        this.targetOpOtaFile = this.mcfConfigSh.getString(z4 ? "sim1_opota_file_otapath" : "sim1_opota_file_runtimepath", "");
    }

    /* access modifiers changed from: private */
    public void reloadOtaPathSharedPreference() {
        this.targetOtaFile = this.mcfConfigSh.getString(this.isOtaOtaPath ? "ota_file_otapath" : "ota_file_runtimepath", "");
        this.targetGenOpOtaFile = this.mcfConfigSh.getString(this.isGenopotaOtaPath ? "general_opota_otapath" : "general_opota_runtimepath", "");
        if (this.phoneid == 1) {
            this.targetOpOtaFile = this.mcfConfigSh.getString(this.isOpotaOtaPath ? "sim2_opota_file_otapath" : "sim2_opota_file_runtimepath", "");
        } else {
            this.targetOpOtaFile = this.mcfConfigSh.getString(this.isOpotaOtaPath ? "sim1_opota_file_otapath" : "sim1_opota_file_runtimepath", "");
        }
    }

    /* access modifiers changed from: private */
    public void saveSharedPreference(int actionCode) {
        String str;
        String str2;
        String str3;
        String str4;
        SharedPreferences.Editor editor = this.mcfConfigSh.edit();
        if (actionCode == 1) {
            if (this.phoneid == 1) {
                if (this.isOpotaOtaPath) {
                    str4 = "sim2_opota_file_otapath";
                } else {
                    str4 = "sim2_opota_file_runtimepath";
                }
                editor.putString(str4, this.targetOpOtaFile);
                editor.putBoolean("sim2_opota_is_otapath", this.isOpotaOtaPath);
            } else {
                if (this.isOpotaOtaPath) {
                    str3 = "sim1_opota_file_otapath";
                } else {
                    str3 = "sim1_opota_file_runtimepath";
                }
                editor.putString(str3, this.targetOpOtaFile);
                editor.putBoolean("sim1_opota_is_otapath", this.isOpotaOtaPath);
            }
            Elog.d("McfConfig", "[saveSharedPreference] Save opOtaFile success ! opota_file_path: SIM" + this.phoneid + ":[" + this.targetOpOtaFile + "]," + this.isOpotaOtaPath);
        } else if (actionCode == 0) {
            if (this.isOtaOtaPath) {
                str2 = "ota_file_otapath";
            } else {
                str2 = "ota_file_runtimepath";
            }
            editor.putString(str2, this.targetOtaFile);
            editor.putBoolean("ota_is_otapath", this.isOtaOtaPath);
            Elog.d("McfConfig", "[saveSharedPreference] Save otaFile success " + this.targetOtaFile + "," + this.isOtaOtaPath);
        } else if (actionCode == 2) {
            if (this.isGenopotaOtaPath) {
                str = "general_opota_otapath";
            } else {
                str = "general_opota_runtimepath";
            }
            editor.putString(str, this.targetGenOpOtaFile);
            editor.putBoolean("general_opota_is_otapath", this.isGenopotaOtaPath);
            Elog.d("McfConfig", "[saveSharedPreference] Save genOpOtaFile success ! " + this.targetGenOpOtaFile + "," + this.isGenopotaOtaPath);
        } else if (actionCode >= 17 && actionCode <= 28) {
            editor.putBoolean("is_otapath", this.isOtaPath);
            editor.putInt("op_id", this.opId);
            if (this.sertInputEt.getVisibility() == 0) {
                editor.putString("input_op_id", this.inputOp);
            }
            Elog.d("McfConfig", "[saveSharedPreference] Save Certification success ! " + this.isOtaPath + ", " + this.opId + ", " + this.inputOp);
        }
        editor.commit();
    }

    public String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray);
    }

    /* access modifiers changed from: private */
    public void doCertificationAction(int phoneNum) {
        String atMsg;
        Elog.d("McfConfig", "doCertificationAction :" + phoneNum + ", " + (this.mcfCertObj.getCurrentCmd(phoneNum) + 1));
        switch (this.mcfCertObj.getCurrentCmd(phoneNum)) {
            case 0:
                this.mcfCertObj.setCurrentCmd(new int[]{1, 1});
                if (this.mcfCertObj.getOpid() == 18) {
                    sendATCommand(phoneNum, "AT+EMCFC=6,0," + this.mcfCertObj.getPathFlag() + ",\"\",0", this.mcfCertObj.getOpid());
                    return;
                } else if (FileUtils.isFileExist(this.mcfCertObj.getFileList(), this.mcfCertObj.getOtaFilePath())) {
                    sendATCommand(phoneNum, "AT+EMCFC=6,0," + this.mcfCertObj.getPathFlag() + ",\"" + this.mcfCertObj.getOtaFileName() + "\",0", this.mcfCertObj.getOpid());
                    return;
                } else {
                    sendATCommand(phoneNum, "AT+EMCFC=6,0," + this.mcfCertObj.getPathFlag() + ",\"\",0", this.mcfCertObj.getOpid());
                    return;
                }
            case 1:
                this.mcfCertObj.setCurrentCmd(phoneNum, 2);
                if (this.mcfCertObj.getOpid() == 18) {
                    atMsg = "AT+EMCFC=6,1," + this.mcfCertObj.getPathFlag() + ",\"\",0";
                } else if (FileUtils.isFileExist(this.mcfCertObj.getFileList(), this.mcfCertObj.getOpotaFilePath())) {
                    atMsg = "AT+EMCFC=6,1," + this.mcfCertObj.getPathFlag() + ",\"" + this.mcfCertObj.getOpotaFileName() + "\",0";
                } else {
                    atMsg = "AT+EMCFC=6,1," + this.mcfCertObj.getPathFlag() + ",\"\",0";
                }
                sendATCommand(phoneNum, atMsg, this.mcfCertObj.getOpid());
                return;
            case 2:
                this.mcfCertObj.setCurrentCmd(phoneNum, 3);
                sendATCommand(phoneNum, "AT+EMCFC=6,2," + this.mcfCertObj.getPathFlag() + ",\"\",0", this.mcfCertObj.getOpid());
                return;
            default:
                return;
        }
    }

    class FileLoadTask extends AsyncTask<String, Void, Void> {
        FileLoadTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            McfConfigActivity.this.showDialog(0);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... params) {
            try {
                EmUtils.getEmHidlService().getFilePathListWithCallBack(params[0], McfConfigActivity.this.mEmCallback);
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            McfConfigActivity.this.removeDialog(0);
            McfConfigActivity.this.mcfCertObj.setCurrentCmd(new int[]{0, 0});
            McfConfigActivity mcfConfigActivity = McfConfigActivity.this;
            mcfConfigActivity.doCertificationAction(mcfConfigActivity.phoneid);
        }
    }
}
