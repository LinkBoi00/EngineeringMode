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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.aospradio.EmRadioHidlAosp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import vendor.mediatek.hardware.engineermode.V1_0.IEmCallback;

public class McfConfigActivity98<arrayOfOtaFile> extends Activity implements View.OnClickListener {
    private final String CERTIFICATION_FILE_KEY = "cert_file";
    private final String CER_OTA_CHECK_COMMAND = "AT+EMCFC=4,0,0";
    private final String CHECK_GEN_OPOTA_COMMAND = "AT+EMCFC=4,2,0";
    private final String CHECK_HW_OTA_COMMAND = "AT+EMCFC=4,3,0";
    private final String CHECK_OPOTA_COMMAND = "AT+EMCFC=4,1,0";
    private final String CHECK_OTA_COMMAND = "AT+EMCFC=4,0,0";
    private final String CMD_OTA_RETURN = "+EMCFC:";
    private final String CMD_OTA_URC = "+EMCFRPT:";
    private final String DUMP_OTA_COMMAND = "AT+EMCFC=5";
    private final String GENERAL_OPOTA_DEFAULT_PATH_KEY = "general_opota_default_path";
    private final String GENERAL_OPOTA_IS_OTAPATH_KEY = "general_opota_is_otapath";
    private final String INIT_SET_GEN_OPOTA_COMMAND = "AT+EMCFC=6,2,0,\"\",0,1";
    private final String INIT_SET_GEN_OPOTA_MD_COMMAND = "AT+EMCFC=6,1,0,\"\",0,1";
    private final String INIT_SET_OPOTA_COMMAND = "AT+EMCFC=6,1,0,\"\",0,1";
    private final String INIT_SET_OTA_COMMAND = "AT+EMCFC=6,0,0,\"\",0,1";
    private final String INI_REFRESH_COMMAND = "AT+EMCFC=7";
    private final int INI_REFRESH_WAIT_TIME = ScanIntervalRange.MAX;
    private final String MCF_CONFIG_SHAREPRE = "mcf_config_settings";
    private final String MODEM_DUMP_OTA_COMMAND = "AT+EMCFC=12";
    private final int MSG_CHECK_HW_OTA_FILEPATH = 5;
    private final int MSG_CHECK_OTA_FILEPATH = 4;
    private final int MSG_DUMP_OTA_FILE = 9;
    private final int MSG_DUMP_OTA_TO_MODEM = 21;
    private final int MSG_G98_OTA_LOAD = 13;
    protected final int MSG_GET_MCF_STATUS_URC = 22;
    private final int MSG_INIT_SET_OTA_FILEPATH = 0;
    private final int MSG_INI_REFRESH = 10;
    private final int MSG_MCF_LOAD_INFO = 14;
    private final int MSG_MCF_MODEM_STATUS_CHANGED = 102;
    private final int MSG_MCF_RADIO_STATE_CHANGED = 100;
    private final int MSG_MODEM_REBOOT_COMPLETE = 101;
    private final int MSG_QUERY_HW_OTA_FILEPATH = 7;
    private final int MSG_QUERY_OTA_FILEPATH = 6;
    private final int MSG_QUERY_TABLE_OTA_FILEPATH = 8;
    private final int MSG_RESET_OTA_FILEPATH = 3;
    private final int MSG_SET_CERTIFICATION = 23;
    private final int MSG_SET_OTA_FILEPATH = 1;
    private final String OTA_DEFAULT_PATH_KEY = "default_ota_path";
    private final String OTA_IS_OTAPATH_KEY = "ota_is_otapath";
    private final String OTA_OTA_FILEPATH_KEY = "ota_file_otapath";
    private final String OTA_OTA_SET_KEY = "ota_file_otaset";
    private final String OTA_PATH = "/vendor/etc/mdota";
    private final String OTA_PATH_CUSTOM = "/vendor/etc/mdota/custom";
    private final String OTA_PATH_DEFAULT = "/vendor/etc/mdota/mtk_default";
    private final String OTA_PATH_TYPE_KEY = "ota_path_type";
    private final String OTA_RUNTIME_FILEPATH_KEY = "ota_file_runtimepath";
    private final String OTA_RUNTIME_SET_KEY = "ota_file_runtimeset";
    private final int OTA_WAIT_TIME = 180;
    private final HashMap<String, String> OtaFilePathType = new HashMap<String, String>() {
        {
            put("0", "Android OTA path");
            put("1", "Runtime path");
        }
    };
    private final String QUERY_GEN_OPOTA_COMMAND = "AT+EMCFC=4,2,1";
    private final String QUERY_HW_OTA_COMMAND = "AT+EMCFC=4,3,1";
    private final String QUERY_OPOTA_COMMAND = "AT+EMCFC=4,1,1";
    private final String QUERY_TABLE_GEN_OPOTA_COMMAND = "AT+EMCFC=4,2,1";
    private final String QUERY_TABLE_OPOTA_COMMAND = "AT+EMCFC=4,1,1";
    private final String QUERY_TABLE_OTA_COMMAND = "AT+EMCFC=4,0,1";
    private final int QUERY_TABLE_OTA_MAX = 10;
    private final int REBOOT_MODEM_WAIT_TIME = 180;
    private final String RESET_GEN_OPOTA_COMMAND = "AT+EMCFC=6,2,0,\"\",0,0";
    private final String RESET_OPOTA_COMMAND = "AT+EMCFC=6,1,0,\"\",1,0";
    private final String RESET_OTA_COMMAND = "AT+EMCFC=6,0,0,\"\",0,0";
    private final String RUNTIME_PATH = "/mnt/vendor/nvcfg/mdota";
    private final String SET_CERT_CMD = "AT+EMCFC=6,0,";
    private final String SET_GEN_OPOTA_COMMAND = "AT+EMCFC=6,2,2,";
    private final String SET_OPOTA_COMMAND = "AT+EMCFC=6,1,2,";
    private final String SET_OTA_COMMAND = "AT+EMCFC=6,0,2,";
    private final int SHOW_CERTIFICATION_VIEW = 3;
    private final int SHOW_GENERAL_SIM_VIEW = 2;
    private final int SHOW_GENERAL_VIEW = 1;
    private final int SHOW_OPOTA_VIEW = 0;
    private final String SIM1_OPOTA_DEFAULT_PATH_KEY = "sim1_opota_default_path";
    private final String SIM1_OPOTA_IS_OTAPATH_KEY = "sim2_opota_is_otapath";
    private final String SIM2_OPOTA_DEFAULT_PATH_KEY = "sim2_opota_default_path";
    private final String SIM2_OPOTA_IS_OTAPATH_KEY = "sim2_opota_is_otapath";
    private final int SIM_COUNT = 2;
    private final int STATE_DUMP_OTA = 1;
    private final int STATE_DUMP_OTA_TO_MODEM = 5;
    private final int STATE_INI_REFRESH = 3;
    private final int STATE_QUERY_OTA = 4;
    private final int STATE_REBOOT_MODEM = 2;
    private final String TAG = "McfConfig";
    private Button addOtaBtn;
    private Button applyGenOpOtaBtn;
    /* access modifiers changed from: private */
    public int applyNum;
    private Button applyOpOtaBtn;
    private Button applyOtaBtn;
    /* access modifiers changed from: private */
    public ArrayList<FileInfo> arrayOfOtaFile = new ArrayList<>();
    private Button certBtn;
    private LinearLayout certificationView;
    private Button certotaQueryBtn;
    private Button checkGenOpOtaBtn;
    private Button checkHwOtaBtn;
    private Button checkOpOtaBtn;
    private Button checkOtaBtn;
    private String curOtaFile;
    private FileInfo curOtaFileInfo;
    /* access modifiers changed from: private */
    public ArrayList<String> curQueryList;
    private String defaultOtaFile;
    private Map<Integer, ProgressDialog> dialogMap = new HashMap();
    long dumpBegain;
    long dumpEnd;
    private Button dumpOta;
    private Button dumpOtaToModem;
    ArrayList<String> fileList = new ArrayList<>();
    /* access modifiers changed from: private */
    public FileInfo firstConstFile;
    /* access modifiers changed from: private */
    public RadioGroup genOpotaPathRgroup;
    private CheckBox genOpotaResetMdEnable;
    private LinearLayout generalOpOtaView;
    private LinearLayout generalView;
    private Button iniRefreshBtn;
    /* access modifiers changed from: private */
    public int iniStatus;
    private RadioGroup iniStatusGroup;
    private String inputOp;
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
            McfConfigActivity98.this.updateFileList(dataStr);
            return true;
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:304:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:305:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:306:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x02bb, code lost:
            r2 = r5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:127:0x0656  */
        /* JADX WARNING: Removed duplicated region for block: B:130:0x0694  */
        /* JADX WARNING: Removed duplicated region for block: B:135:0x06cd  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r25) {
            /*
                r24 = this;
                r0 = r24
                r1 = r25
                r2 = 0
                r3 = 0
                r4 = 0
                int r5 = r1.what
                java.lang.String r6 = "0"
                java.lang.String r7 = "OPOTA"
                java.lang.String r8 = "General OPOTA"
                java.lang.String r9 = " Succeed!"
                java.lang.String r10 = " Failed!\n"
                java.lang.String r11 = "AT Command Failed: "
                java.lang.String r12 = "\n"
                java.lang.String r14 = ": "
                java.lang.String r15 = "OTA"
                java.lang.String r13 = ","
                r17 = r2
                java.lang.String r2 = " "
                r19 = r3
                java.lang.String r3 = ":"
                r20 = r4
                java.lang.String r4 = ""
                r21 = r7
                java.lang.String r7 = "McfConfig"
                switch(r5) {
                    case 0: goto L_0x0d07;
                    case 1: goto L_0x0b25;
                    case 3: goto L_0x09a2;
                    case 4: goto L_0x0828;
                    case 5: goto L_0x0828;
                    case 6: goto L_0x0828;
                    case 7: goto L_0x0828;
                    case 8: goto L_0x0828;
                    case 9: goto L_0x07a0;
                    case 10: goto L_0x0736;
                    case 14: goto L_0x05b6;
                    case 21: goto L_0x07a0;
                    case 22: goto L_0x02c0;
                    case 23: goto L_0x013d;
                    case 101: goto L_0x00b2;
                    case 102: goto L_0x0048;
                    default: goto L_0x0030;
                }
            L_0x0030:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "unsupport msg :"
                r2.append(r3)
                int r3 = r1.what
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                goto L_0x0d96
            L_0x0048:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x0095
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x0095
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
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r5 = r5.mIsModemEnabled
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.v(r7, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r4 = r4.mIsModemEnabled
                if (r4 != 0) goto L_0x00ac
                if (r3 == 0) goto L_0x00ac
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                android.os.Handler r4 = r4.mHandler
                r5 = 101(0x65, float:1.42E-43)
                r4.sendEmptyMessage(r5)
                goto L_0x00ac
            L_0x0095:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Reboot Modem returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r7, r4)
            L_0x00ac:
                r3 = r19
                r4 = r20
                goto L_0x0d9c
            L_0x00b2:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r2 = r2.mIsModemEnabled
                if (r2 != 0) goto L_0x00f4
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r2 = r2.resetMd
                if (r2 == 0) goto L_0x00f4
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.Map r2 = r2.timerMap
                java.util.Set r2 = r2.keySet()
                r3 = 2
                java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
                boolean r2 = r2.contains(r4)
                if (r2 != 0) goto L_0x00d8
                goto L_0x00f4
            L_0x00d8:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r2.removeStateMap(r3)
                java.lang.String r2 = "Reset Modem Success!"
                com.mediatek.engineermode.Elog.e(r7, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r3 = "Reset Modem"
                java.lang.String r4 = "Reset Modem Completed!"
                r5 = 0
                r2.showDialog(r3, r4, r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r3 = 1
                boolean unused = r2.mIsModemEnabled = r3
                goto L_0x0d96
            L_0x00f4:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "SIM"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r3 = r3.phoneid
                r2.append(r3)
                java.lang.String r3 = " received MODEM_REBOOT_COMPLETE, but skiped! mIsModemEnabled:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r3 = r3.mIsModemEnabled
                r2.append(r3)
                java.lang.String r3 = " resetMd:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r3 = r3.resetMd
                r2.append(r3)
                java.lang.String r3 = " taskSet:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.Map r3 = r3.timerMap
                java.util.Set r3 = r3.keySet()
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                return
            L_0x013d:
                java.lang.Object r5 = r1.obj
                android.os.AsyncResult r5 = (android.os.AsyncResult) r5
                int r6 = r1.what
                int r6 = r6 + -23
                r8 = 0
                java.lang.Throwable r9 = r5.exception
                java.lang.String r14 = "Certification with Sim"
                if (r9 != 0) goto L_0x01ed
                java.lang.Object r3 = r5.result
                if (r3 == 0) goto L_0x01ea
                java.lang.Object r3 = r5.result
                boolean r3 = r3 instanceof java.lang.String[]
                if (r3 == 0) goto L_0x01ea
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                int r3 = r3.length
                if (r3 <= 0) goto L_0x01ea
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r9 = "Certification Result: "
                r3.append(r9)
                java.lang.Object r9 = r5.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                java.lang.String r9 = java.util.Arrays.toString(r9)
                r3.append(r9)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                r9 = 0
                r3 = r3[r9]
                java.lang.String[] r3 = r3.split(r13)
                if (r3 == 0) goto L_0x01e5
                r9 = 1
                r11 = r3[r9]
                if (r11 == 0) goto L_0x01e5
                r11 = 2
                r13 = r3[r11]
                if (r13 == 0) goto L_0x01e5
                r13 = r3[r9]
                java.lang.String r9 = r13.trim()
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                int r9 = r9.intValue()
                r11 = r3[r11]
                java.lang.String r11 = r11.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                if (r11 != 0) goto L_0x01b4
                if (r9 != 0) goto L_0x01b4
                r8 = 1
                r3 = r19
                goto L_0x01e9
            L_0x01b4:
                r8 = 0
                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                r13.<init>()
                java.lang.String r15 = "Certification Ota: "
                r13.append(r15)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r15 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r16 = r3
                java.util.HashMap r3 = r15.mcfApplyOtaResult
                java.lang.String r3 = r15.getStringValueFromMap(r9, r3)
                r13.append(r3)
                java.lang.String r3 = "\nDsbp: "
                r13.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.HashMap r15 = r3.mcfApplyDsbpResult
                java.lang.String r3 = r3.getStringValueFromMap(r11, r15)
                r13.append(r3)
                java.lang.String r3 = r13.toString()
                goto L_0x01e9
            L_0x01e5:
                r16 = r3
                r3 = r19
            L_0x01e9:
                goto L_0x0236
            L_0x01ea:
                r3 = r19
                goto L_0x0236
            L_0x01ed:
                java.lang.Throwable r9 = r5.exception
                java.lang.String r9 = r9.toString()
                java.lang.String[] r9 = r9.split(r3)
                r13 = 1
                r9 = r9[r13]
                if (r9 == 0) goto L_0x0218
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r11)
                java.lang.Throwable r11 = r5.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r3 = r11.split(r3)
                r3 = r3[r13]
                r9.append(r3)
                java.lang.String r3 = r9.toString()
                goto L_0x0219
            L_0x0218:
                r3 = r4
            L_0x0219:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r14)
                r9.append(r6)
                java.lang.String r11 = " returned exception:"
                r9.append(r11)
                java.lang.Throwable r11 = r5.exception
                r9.append(r11)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.e(r7, r9)
            L_0x0236:
                if (r8 == 0) goto L_0x0278
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                r2.append(r14)
                r2.append(r6)
                java.lang.String r4 = " succeed!"
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r4 = "Certification"
                java.lang.String r7 = "Certification Succeed!"
                r9 = 0
                r2.showDialog(r4, r7, r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98$saveOTAPathTask r2 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity98$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r2.<init>()
                r4 = 1
                java.lang.Integer[] r4 = new java.lang.Integer[r4]
                int r7 = r1.what
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
                r4[r9] = r7
                r2.execute(r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r4 = r2.resetMd
                r2.rebootModem(r4)
                goto L_0x02bb
            L_0x0278:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r14)
                r9.append(r6)
                r9.append(r10)
                if (r3 != 0) goto L_0x0289
                goto L_0x0298
            L_0x0289:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r12)
                r4.append(r3)
                java.lang.String r4 = r4.toString()
            L_0x0298:
                r9.append(r4)
                java.lang.String r4 = r9.toString()
                com.mediatek.engineermode.Elog.e(r7, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r14)
                r9.append(r6)
                r9.append(r2)
                java.lang.String r2 = r9.toString()
                r9 = 0
                r7.showDialog(r2, r4, r9)
            L_0x02bb:
                r2 = r5
                r4 = r20
                goto L_0x0d9c
            L_0x02c0:
                java.lang.Object r5 = r1.obj
                android.os.AsyncResult r5 = (android.os.AsyncResult) r5
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.Object r8 = r5.result
                byte[] r8 = (byte[]) r8
                java.lang.String r6 = r6.byteArrayToStr(r8)
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Readback from urc = "
                r8.append(r9)
                r8.append(r6)
                java.lang.String r8 = r8.toString()
                com.mediatek.engineermode.Elog.d(r7, r8)
                java.lang.String r8 = "+EMCFRPT:"
                boolean r8 = r6.startsWith(r8)
                if (r8 == 0) goto L_0x05ac
                java.lang.String[] r3 = r6.split(r3)
                int r3 = r3.length
                r8 = 2
                if (r3 >= r8) goto L_0x02f6
                r23 = r5
                goto L_0x05ae
            L_0x02f6:
                java.lang.String r3 = "+EMCFRPT:"
                int r3 = r3.length()
                java.lang.String r3 = r6.substring(r3)
                java.lang.String r2 = r3.replaceAll(r2, r4)
                java.lang.String[] r2 = r2.split(r13)
                r3 = 0
                r8 = r2[r3]
                java.lang.String r3 = r8.trim()
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                int r3 = r3.intValue()
                r8 = 1
                r9 = r2[r8]
                java.lang.String r8 = r9.trim()
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                int r8 = r8.intValue()
                switch(r3) {
                    case 1: goto L_0x04f7;
                    case 2: goto L_0x0329;
                    case 3: goto L_0x04aa;
                    case 4: goto L_0x0331;
                    case 5: goto L_0x04f7;
                    default: goto L_0x0329;
                }
            L_0x0329:
                r17 = r2
                r22 = r3
                r23 = r5
                goto L_0x05ae
            L_0x0331:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "OTA QUERY with result:"
                r9.append(r10)
                java.lang.String r10 = java.util.Arrays.toString(r2)
                r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.d(r7, r9)
                int r9 = r2.length
                r10 = 7
                if (r9 >= r10) goto L_0x0351
                r23 = r5
                goto L_0x05ae
            L_0x0351:
                r9 = 2
                r10 = r2[r9]
                java.lang.String r9 = r10.trim()
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                int r9 = r9.intValue()
                r10 = 4
                r11 = r2[r10]
                java.lang.String r10 = r11.trim()
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                int r10 = r10.intValue()
                r11 = 5
                r11 = r2[r11]
                java.lang.String r11 = r11.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                r13 = 6
                r13 = r2[r13]
                java.lang.String r13 = r13.trim()
                r17 = r2
                java.lang.String r2 = "\""
                java.lang.String r2 = r13.replace(r2, r4)
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r15)
                r4.append(r11)
                r4.append(r14)
                r4.append(r2)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.d(r7, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r4 = r4.curQueryList
                r4.add(r2)
                if (r11 != r10) goto L_0x04a2
                java.lang.String r4 = "ATCommand with ota query success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r4)
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r13 = "OTA: "
                r4.append(r13)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r13 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r13 = r13.curQueryList
                java.lang.Object[] r13 = r13.toArray()
                java.lang.String r13 = java.util.Arrays.toString(r13)
                r4.append(r13)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.d(r7, r4)
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r13 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r13 = r13.viewId
                r21 = r2
                r2 = 1
                if (r13 == r2) goto L_0x03ff
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.viewId
                r13 = 3
                if (r2 != r13) goto L_0x03f1
                goto L_0x03ff
            L_0x03f1:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.viewId
                r13 = 2
                if (r2 != r13) goto L_0x03fd
                java.lang.String r15 = "General OTA by OP"
                goto L_0x03ff
            L_0x03fd:
                java.lang.String r15 = "OTA by OP"
            L_0x03ff:
                r4.append(r15)
                java.lang.String r2 = ": \n"
                r4.append(r2)
                java.lang.String r2 = r4.toString()
                r4 = 0
            L_0x040c:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r13 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r13 = r13.curQueryList
                int r13 = r13.size()
                if (r4 >= r13) goto L_0x045c
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r13 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                com.mediatek.engineermode.mcfconfig.FileInfo r15 = new com.mediatek.engineermode.mcfconfig.FileInfo
                r22 = r3
                java.util.ArrayList r3 = r13.curQueryList
                java.lang.Object r3 = r3.get(r4)
                java.lang.String r3 = (java.lang.String) r3
                r23 = r5
                r5 = 1
                r15.<init>((java.lang.String) r3, (boolean) r5)
                com.mediatek.engineermode.mcfconfig.FileInfo unused = r13.firstConstFile = r15
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r2)
                r3.append(r4)
                r3.append(r14)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r5 = r5.curQueryList
                java.lang.Object r5 = r5.get(r4)
                java.lang.String r5 = (java.lang.String) r5
                r3.append(r5)
                r3.append(r12)
                java.lang.String r2 = r3.toString()
                int r4 = r4 + 1
                r3 = r22
                r5 = r23
                goto L_0x040c
            L_0x045c:
                r22 = r3
                r23 = r5
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r3 = r3.opId
                r4 = 13
                if (r3 != r4) goto L_0x0492
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "OTA Query "
                r3.append(r4)
                r3.append(r2)
                java.lang.String r4 = ", "
                r3.append(r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r4 = r4.opId
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r3.initOtaListViewContent()
                goto L_0x049a
            L_0x0492:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r4 = "OTA Query "
                r5 = 0
                r3.showDialog(r4, r2, r5)
            L_0x049a:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r4 = 4
                r3.removeStateMap(r4)
                goto L_0x05ae
            L_0x04a2:
                r21 = r2
                r22 = r3
                r23 = r5
                goto L_0x05ae
            L_0x04aa:
                r17 = r2
                r22 = r3
                r23 = r5
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Ini Refresh end with result:"
                r2.append(r3)
                r2.append(r8)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                if (r8 != 0) goto L_0x04cb
                java.lang.String r3 = "Success!"
                goto L_0x04e8
            L_0x04cb:
                r3 = 1
                if (r8 != r3) goto L_0x04d1
                java.lang.String r3 = "Reset Modem"
                goto L_0x04e8
            L_0x04d1:
                r3 = 2
                if (r8 != r3) goto L_0x04d7
                java.lang.String r3 = "AP retry"
                goto L_0x04e8
            L_0x04d7:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Failed with error_code: "
                r3.append(r4)
                r3.append(r8)
                java.lang.String r3 = r3.toString()
            L_0x04e8:
                java.lang.String r4 = "Ini Refresh"
                r5 = 0
                r2.showDialog(r4, r3, r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r3 = 3
                r2.removeStateMap(r3)
                goto L_0x05ae
            L_0x04f7:
                r17 = r2
                r22 = r3
                r23 = r5
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                long r3 = java.lang.System.currentTimeMillis()
                r2.dumpEnd = r3
                java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
                java.lang.String r3 = "HH:mm:ss"
                r2.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Dump OTA file end :"
                r3.append(r4)
                java.util.Date r4 = new java.util.Date
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                long r9 = r5.dumpBegain
                r4.<init>(r9)
                java.lang.String r4 = r2.format(r4)
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                if (r8 != 0) goto L_0x0539
                java.lang.String r5 = "Success!"
                goto L_0x056f
            L_0x0539:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r7 = "Failed with Error Code: "
                r5.append(r7)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.HashMap r7 = r7.mcfOtaDumpResult
                java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
                boolean r7 = r7.containsKey(r9)
                if (r7 == 0) goto L_0x0564
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.HashMap r7 = r7.mcfOtaDumpResult
                java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
                java.lang.Object r7 = r7.get(r9)
                java.io.Serializable r7 = (java.io.Serializable) r7
                goto L_0x0568
            L_0x0564:
                java.lang.Integer r7 = java.lang.Integer.valueOf(r8)
            L_0x0568:
                r5.append(r7)
                java.lang.String r5 = r5.toString()
            L_0x056f:
                r4.append(r5)
                java.lang.String r5 = "\nDump costs "
                r4.append(r5)
                r5 = 1
                java.lang.Object[] r7 = new java.lang.Object[r5]
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                long r9 = r5.dumpEnd
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                long r11 = r5.dumpBegain
                long r9 = r9 - r11
                float r5 = (float) r9
                r9 = 1148846080(0x447a0000, float:1000.0)
                float r5 = r5 / r9
                java.lang.Float r5 = java.lang.Float.valueOf(r5)
                r9 = 0
                r7[r9] = r5
                java.lang.String r5 = "%.2f"
                java.lang.String r5 = java.lang.String.format(r5, r7)
                r4.append(r5)
                java.lang.String r5 = " s"
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                java.lang.String r5 = "Dump Result"
                r3.showDialog(r5, r4, r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r4 = 1
                r3.removeStateMap(r4)
                goto L_0x05ae
            L_0x05ac:
                r23 = r5
            L_0x05ae:
                r3 = r19
                r4 = r20
                r2 = r23
                goto L_0x0d9c
            L_0x05b6:
                java.lang.Object r5 = r1.obj
                android.os.AsyncResult r5 = (android.os.AsyncResult) r5
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.LinkedHashMap r6 = r6.mcfLoadInfoCmd
                java.util.Set r6 = r6.keySet()
                java.lang.Object[] r6 = r6.toArray()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r8 = r8.applyNum
                r8 = r6[r8]
                java.lang.String r8 = r8.toString()
                java.lang.String[] r8 = r8.split(r3)
                java.lang.Throwable r9 = r5.exception
                if (r9 != 0) goto L_0x06d6
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                r9 = 0
                r3 = r3[r9]
                java.lang.String r9 = "+EMCFC:"
                int r9 = r9.length()
                java.lang.String r3 = r3.substring(r9)
                java.lang.String r2 = r3.replaceAll(r2, r4)
                java.lang.String[] r2 = r2.split(r13)
                if (r2 == 0) goto L_0x0650
                int r3 = r2.length
                if (r3 <= 0) goto L_0x0650
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "MCF Load info: "
                r3.append(r4)
                java.lang.Object r4 = r5.result
                java.lang.String[] r4 = (java.lang.String[]) r4
                java.lang.String r4 = java.util.Arrays.toString(r4)
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                if (r2 == 0) goto L_0x0650
                r3 = 1
                r4 = r2[r3]
                if (r4 == 0) goto L_0x0650
                r4 = 3
                r7 = r2[r4]
                if (r7 == 0) goto L_0x0650
                r4 = 1
                r7 = r2[r3]
                java.lang.String r3 = r7.trim()
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                int r3 = r3.intValue()
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r9 = 0
                r10 = r8[r9]
                r7.append(r10)
                r7.append(r14)
                if (r3 != 0) goto L_0x0645
                r9 = 3
                r9 = r2[r9]
                goto L_0x0647
            L_0x0645:
                java.lang.String r9 = "No information"
            L_0x0647:
                r7.append(r9)
                java.lang.String r7 = r7.toString()
                r3 = r7
                goto L_0x0654
            L_0x0650:
                r3 = r19
                r4 = r20
            L_0x0654:
                if (r4 != 0) goto L_0x0673
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r9 = 0
                r9 = r8[r9]
                r7.append(r9)
                r7.append(r14)
                java.lang.Object r9 = r5.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                java.lang.String r9 = java.util.Arrays.toString(r9)
                r7.append(r9)
                java.lang.String r3 = r7.toString()
            L_0x0673:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r2 = r2.curQueryList
                r2.add(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98.access$108(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.applyNum
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.LinkedHashMap r7 = r7.mcfLoadInfoCmd
                int r7 = r7.size()
                if (r2 < r7) goto L_0x06cd
                java.lang.String r2 = ""
                r7 = 0
            L_0x0697:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r9 = r9.curQueryList
                int r9 = r9.size()
                if (r7 >= r9) goto L_0x06c4
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r10 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r10 = r10.curQueryList
                java.lang.Object r10 = r10.get(r7)
                java.lang.String r10 = (java.lang.String) r10
                r9.append(r10)
                r9.append(r12)
                java.lang.String r2 = r9.toString()
                int r7 = r7 + 1
                goto L_0x0697
            L_0x06c4:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r9 = "MCF Load info: "
                r10 = 1
                r7.showDialog(r9, r2, r10)
                goto L_0x06d3
            L_0x06cd:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r2.showMcfLoadInfo()
            L_0x06d3:
                r2 = r5
                goto L_0x0d9c
            L_0x06d6:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                r9 = 0
                r9 = r8[r9]
                r2.append(r9)
                r2.append(r14)
                java.lang.Throwable r9 = r5.exception
                java.lang.String r9 = r9.toString()
                java.lang.String[] r9 = r9.split(r3)
                r10 = 1
                r9 = r9[r10]
                if (r9 == 0) goto L_0x070f
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r11)
                java.lang.Throwable r9 = r5.exception
                java.lang.String r9 = r9.toString()
                java.lang.String[] r3 = r9.split(r3)
                r3 = r3[r10]
                r4.append(r3)
                java.lang.String r4 = r4.toString()
                goto L_0x0710
            L_0x070f:
            L_0x0710:
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "check MCF Load info returned exception:"
                r3.append(r4)
                java.lang.Throwable r4 = r5.exception
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.e(r7, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r4 = "MCF Load info failed: "
                r7 = 1
                r3.showDialog(r4, r2, r7)
                return
            L_0x0736:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x0767
                r3 = 1
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x077e
                java.lang.Object r4 = r2.result
                boolean r4 = r4 instanceof java.lang.String[]
                if (r4 == 0) goto L_0x077e
                java.lang.Object r4 = r2.result
                java.lang.String[] r4 = (java.lang.String[]) r4
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "AT with ini refresh returned:"
                r5.append(r6)
                java.lang.String r6 = java.util.Arrays.toString(r4)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r7, r5)
                goto L_0x077e
            L_0x0767:
                r3 = 0
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "AT with ini refresh returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r7, r4)
            L_0x077e:
                if (r3 == 0) goto L_0x078f
                java.lang.String r4 = "ATCommand with ini refresh success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r5 = 300(0x12c, float:4.2E-43)
                r6 = 3
                r4.startTimer(r6, r5)
                goto L_0x00ac
            L_0x078f:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r5 = "INI Refresh"
                java.lang.String r6 = "INI Refresh failed!"
                r8 = 0
                r4.showDialog(r5, r6, r8)
                java.lang.String r4 = "ATCommand with INI Refresh failed!"
                com.mediatek.engineermode.Elog.e(r7, r4)
                goto L_0x00ac
            L_0x07a0:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                r4 = -1
                java.lang.Throwable r5 = r2.exception
                if (r5 != 0) goto L_0x07ef
                java.lang.Object r5 = r2.result
                if (r5 == 0) goto L_0x0806
                java.lang.Object r5 = r2.result
                boolean r5 = r5 instanceof java.lang.String[]
                if (r5 == 0) goto L_0x0806
                java.lang.Object r5 = r2.result
                java.lang.String[] r5 = (java.lang.String[]) r5
                r8 = 0
                r5 = r5[r8]
                java.lang.String[] r5 = r5.split(r13)
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "ATCommand with Dump OTA returned: "
                r8.append(r9)
                java.lang.Object r9 = r2.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                java.lang.String r9 = java.util.Arrays.toString(r9)
                r8.append(r9)
                java.lang.String r8 = r8.toString()
                com.mediatek.engineermode.Elog.d(r7, r8)
                r8 = 1
                r9 = r5[r8]
                boolean r8 = r9.equals(r6)
                if (r8 != 0) goto L_0x07ed
                r8 = 2
                r8 = r5[r8]
                boolean r6 = r8.equals(r6)
                if (r6 == 0) goto L_0x07ee
            L_0x07ed:
                r3 = 1
            L_0x07ee:
                goto L_0x0806
            L_0x07ef:
                r3 = 0
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "ATCommand with Dump OTA returned exception:"
                r5.append(r6)
                java.lang.Throwable r6 = r2.exception
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.e(r7, r5)
            L_0x0806:
                if (r3 == 0) goto L_0x0817
                java.lang.String r5 = "ATCommand with Dump OTA success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r6 = 180(0xb4, float:2.52E-43)
                r7 = 1
                r5.startTimer(r7, r6)
                goto L_0x00ac
            L_0x0817:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.String r6 = "Dump OTA"
                java.lang.String r8 = "Dump OTA failed!"
                r9 = 0
                r5.showDialog(r6, r8, r9)
                java.lang.String r5 = "ATCommand with Dump OTA failed!"
                com.mediatek.engineermode.Elog.e(r7, r5)
                goto L_0x00ac
            L_0x0828:
                java.lang.Object r3 = r1.obj
                android.os.AsyncResult r3 = (android.os.AsyncResult) r3
                r5 = 0
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Query "
                r8.append(r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r9 = r9.viewId
                r10 = 1
                if (r9 == r10) goto L_0x0858
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r9 = r9.viewId
                r10 = 3
                if (r9 != r10) goto L_0x084a
                goto L_0x0858
            L_0x084a:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r9 = r9.viewId
                r10 = 2
                if (r9 != r10) goto L_0x0856
                java.lang.String r15 = "General OTA by OP"
                goto L_0x0858
            L_0x0856:
                java.lang.String r15 = "OTA by OP"
            L_0x0858:
                r8.append(r15)
                java.lang.String r8 = r8.toString()
                java.lang.Throwable r9 = r3.exception
                if (r9 != 0) goto L_0x091d
                java.lang.Object r9 = r3.result
                if (r9 == 0) goto L_0x0934
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "Query OTA ("
                r9.append(r10)
                int r10 = r1.what
                r9.append(r10)
                java.lang.String r10 = ", "
                r9.append(r10)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r10 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r10 = r10.viewId
                r9.append(r10)
                java.lang.String r10 = "): "
                r9.append(r10)
                java.lang.Object r10 = r3.result
                java.lang.String[] r10 = (java.lang.String[]) r10
                java.lang.String r10 = java.util.Arrays.toString(r10)
                r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.d(r7, r9)
                java.lang.Object r9 = r3.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                r10 = 0
                r9 = r9[r10]
                java.lang.String r10 = "+EMCFC:"
                int r10 = r10.length()
                java.lang.String r9 = r9.substring(r10)
                java.lang.String r2 = r9.replaceAll(r2, r4)
                java.lang.String[] r2 = r2.split(r13)
                if (r2 == 0) goto L_0x091c
                r5 = 1
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "AT with ota query "
                r9.append(r10)
                java.lang.String r10 = java.util.Arrays.toString(r2)
                r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.d(r7, r9)
                int r9 = r2.length
                r10 = 4
                if (r9 < r10) goto L_0x08d7
                r9 = 3
                r9 = r2[r9]
                goto L_0x08d8
            L_0x08d7:
                r9 = r4
            L_0x08d8:
                boolean r6 = r9.equals(r6)
                if (r6 != 0) goto L_0x08fa
                boolean r4 = r9.equals(r4)
                if (r4 == 0) goto L_0x08e5
                goto L_0x08fa
            L_0x08e5:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r6 = "ATCommand with ota query success! OTA file num is "
                r4.append(r6)
                r4.append(r9)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r4)
                goto L_0x091c
            L_0x08fa:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r8)
                java.lang.String r10 = " num is "
                r6.append(r10)
                r6.append(r9)
                java.lang.String r6 = r6.toString()
                java.lang.String r10 = "OTA Query"
                r11 = 0
                r4.showDialog(r10, r6, r11)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r6 = 4
                r4.removeStateMap(r6)
            L_0x091c:
                goto L_0x0934
            L_0x091d:
                r5 = 0
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r4 = "AT with ota query returned exception:"
                r2.append(r4)
                java.lang.Throwable r4 = r3.exception
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.e(r7, r2)
            L_0x0934:
                if (r5 != 0) goto L_0x099b
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.opId
                r4 = 13
                if (r2 != r4) goto L_0x097c
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r4 = "OTA Query "
                r2.append(r4)
                r2.append(r8)
                java.lang.String r4 = ", "
                r2.append(r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r4 = r4.opId
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                r2.append(r8)
                java.lang.String r4 = " failed!"
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r2.initOtaListViewContent()
                goto L_0x0995
            L_0x097c:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r8)
                java.lang.String r6 = " failed!"
                r4.append(r6)
                java.lang.String r4 = r4.toString()
                java.lang.String r6 = "OTA Query"
                r7 = 0
                r2.showDialog(r6, r4, r7)
            L_0x0995:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r4 = 4
                r2.removeStateMap(r4)
            L_0x099b:
                r2 = r3
                r3 = r19
                r4 = r20
                goto L_0x0d9c
            L_0x09a2:
                java.lang.Object r5 = r1.obj
                android.os.AsyncResult r5 = (android.os.AsyncResult) r5
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r6 = r6.viewId
                r14 = 1
                if (r6 != r14) goto L_0x09b0
                goto L_0x09bc
            L_0x09b0:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r6 = r6.viewId
                if (r6 != 0) goto L_0x09bb
                r15 = r21
                goto L_0x09bc
            L_0x09bb:
                r15 = r8
            L_0x09bc:
                r6 = r15
                r8 = 0
                java.lang.Throwable r14 = r5.exception
                java.lang.String r15 = "Clear "
                if (r14 != 0) goto L_0x0a6c
                java.lang.Object r3 = r5.result
                if (r3 == 0) goto L_0x0a67
                java.lang.Object r3 = r5.result
                boolean r3 = r3 instanceof java.lang.String[]
                if (r3 == 0) goto L_0x0a67
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                int r3 = r3.length
                if (r3 <= 0) goto L_0x0a67
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r11 = "Clear Result: "
                r3.append(r11)
                java.lang.Object r11 = r5.result
                java.lang.String[] r11 = (java.lang.String[]) r11
                java.lang.String r11 = java.util.Arrays.toString(r11)
                r3.append(r11)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                r11 = 0
                r3 = r3[r11]
                java.lang.String[] r3 = r3.split(r13)
                if (r3 == 0) goto L_0x0a60
                r11 = 1
                r13 = r3[r11]
                if (r13 == 0) goto L_0x0a60
                r13 = 2
                r14 = r3[r13]
                if (r14 == 0) goto L_0x0a60
                r11 = r3[r11]
                java.lang.String r11 = r11.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                r13 = r3[r13]
                java.lang.String r13 = r13.trim()
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
                int r13 = r13.intValue()
                if (r13 != 0) goto L_0x0a2e
                if (r11 != 0) goto L_0x0a2e
                r8 = 1
                r18 = r4
                r3 = r19
                goto L_0x0a66
            L_0x0a2e:
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                r16 = r3
                java.lang.String r3 = "Mcf Ota: "
                r14.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r18 = r4
                java.util.HashMap r4 = r3.mcfApplyOtaResult
                java.lang.String r3 = r3.getStringValueFromMap(r11, r4)
                r14.append(r3)
                java.lang.String r3 = "\nDsbp: "
                r14.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.HashMap r4 = r3.mcfApplyDsbpResult
                java.lang.String r3 = r3.getStringValueFromMap(r13, r4)
                r14.append(r3)
                java.lang.String r3 = r14.toString()
                goto L_0x0a66
            L_0x0a60:
                r16 = r3
                r18 = r4
                r3 = r19
            L_0x0a66:
                goto L_0x0ab8
            L_0x0a67:
                r18 = r4
                r3 = r19
                goto L_0x0ab8
            L_0x0a6c:
                r18 = r4
                java.lang.Throwable r4 = r5.exception
                java.lang.String r4 = r4.toString()
                java.lang.String[] r4 = r4.split(r3)
                r13 = 1
                r4 = r4[r13]
                if (r4 == 0) goto L_0x0a99
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r11)
                java.lang.Throwable r11 = r5.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r3 = r11.split(r3)
                r3 = r3[r13]
                r4.append(r3)
                java.lang.String r3 = r4.toString()
                goto L_0x0a9b
            L_0x0a99:
                r3 = r18
            L_0x0a9b:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r15)
                r4.append(r6)
                java.lang.String r11 = " returned exception:"
                r4.append(r11)
                java.lang.Throwable r11 = r5.exception
                r4.append(r11)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r7, r4)
            L_0x0ab8:
                if (r8 == 0) goto L_0x0adf
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r2.initOtaListView()
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                r2.append(r15)
                r2.append(r6)
                r2.append(r9)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r4 = r2.resetMd
                r2.rebootModem(r4)
                goto L_0x02bb
            L_0x0adf:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r15)
                r4.append(r6)
                r4.append(r10)
                if (r3 != 0) goto L_0x0af2
                r9 = r18
                goto L_0x0b01
            L_0x0af2:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r12)
                r9.append(r3)
                java.lang.String r9 = r9.toString()
            L_0x0b01:
                r4.append(r9)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r7, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r15)
                r9.append(r6)
                r9.append(r2)
                java.lang.String r2 = r9.toString()
                r9 = 0
                r7.showDialog(r2, r4, r9)
                goto L_0x02bb
            L_0x0b25:
                r18 = r4
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r4 = r4.viewId
                r5 = 1
                if (r4 != r5) goto L_0x0b31
                goto L_0x0b3d
            L_0x0b31:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r4 = r4.viewId
                if (r4 != 0) goto L_0x0b3c
                r15 = r21
                goto L_0x0b3d
            L_0x0b3c:
                r15 = r8
            L_0x0b3d:
                r4 = r15
                java.lang.Object r5 = r1.obj
                android.os.AsyncResult r5 = (android.os.AsyncResult) r5
                r6 = 0
                java.lang.Throwable r8 = r5.exception
                java.lang.String r14 = "Apply "
                if (r8 != 0) goto L_0x0be9
                java.lang.Object r3 = r5.result
                if (r3 == 0) goto L_0x0be6
                java.lang.Object r3 = r5.result
                boolean r3 = r3 instanceof java.lang.String[]
                if (r3 == 0) goto L_0x0be6
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                int r3 = r3.length
                if (r3 <= 0) goto L_0x0be6
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r8 = "Apply Result: "
                r3.append(r8)
                java.lang.Object r8 = r5.result
                java.lang.String[] r8 = (java.lang.String[]) r8
                java.lang.String r8 = java.util.Arrays.toString(r8)
                r3.append(r8)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r7, r3)
                java.lang.Object r3 = r5.result
                java.lang.String[] r3 = (java.lang.String[]) r3
                r8 = 0
                r3 = r3[r8]
                java.lang.String[] r3 = r3.split(r13)
                if (r3 == 0) goto L_0x0be1
                r8 = 1
                r11 = r3[r8]
                if (r11 == 0) goto L_0x0be1
                r11 = 2
                r13 = r3[r11]
                if (r13 == 0) goto L_0x0be1
                r13 = r3[r8]
                java.lang.String r8 = r13.trim()
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                int r8 = r8.intValue()
                r11 = r3[r11]
                java.lang.String r11 = r11.trim()
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                int r11 = r11.intValue()
                if (r11 != 0) goto L_0x0bb1
                if (r8 != 0) goto L_0x0bb1
                r6 = 1
                r3 = r19
                goto L_0x0be5
            L_0x0bb1:
                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                r13.<init>()
                java.lang.String r15 = "Mcf Ota: "
                r13.append(r15)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r15 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                r16 = r3
                java.util.HashMap r3 = r15.mcfApplyOtaResult
                java.lang.String r3 = r15.getStringValueFromMap(r8, r3)
                r13.append(r3)
                java.lang.String r3 = "\nDsbp: "
                r13.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.HashMap r15 = r3.mcfApplyDsbpResult
                java.lang.String r3 = r3.getStringValueFromMap(r11, r15)
                r13.append(r3)
                java.lang.String r3 = r13.toString()
                goto L_0x0be5
            L_0x0be1:
                r16 = r3
                r3 = r19
            L_0x0be5:
                goto L_0x0c33
            L_0x0be6:
                r3 = r19
                goto L_0x0c33
            L_0x0be9:
                java.lang.Throwable r8 = r5.exception
                java.lang.String r8 = r8.toString()
                java.lang.String[] r8 = r8.split(r3)
                r13 = 1
                r8 = r8[r13]
                if (r8 == 0) goto L_0x0c14
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r11)
                java.lang.Throwable r11 = r5.exception
                java.lang.String r11 = r11.toString()
                java.lang.String[] r3 = r11.split(r3)
                r3 = r3[r13]
                r8.append(r3)
                java.lang.String r3 = r8.toString()
                goto L_0x0c16
            L_0x0c14:
                r3 = r18
            L_0x0c16:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r14)
                r8.append(r4)
                java.lang.String r11 = " returned exception:"
                r8.append(r11)
                java.lang.Throwable r11 = r5.exception
                r8.append(r11)
                java.lang.String r8 = r8.toString()
                com.mediatek.engineermode.Elog.e(r7, r8)
            L_0x0c33:
                if (r6 == 0) goto L_0x0cc1
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98.access$108(r8)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r8 = r8.applyNum
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r10 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.util.ArrayList r10 = r10.arrayOfOtaFile
                int r10 = r10.size()
                if (r8 < r10) goto L_0x0c81
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r14)
                r8.append(r4)
                r8.append(r2)
                java.lang.String r2 = r8.toString()
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r14)
                r8.append(r4)
                r8.append(r9)
                java.lang.String r8 = r8.toString()
                r9 = 0
                r7.showDialog(r2, r8, r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                boolean r7 = r2.resetMd
                r2.rebootModem(r7)
                goto L_0x02bb
            L_0x0c81:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r8 = "Apply  "
                r2.append(r8)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r8 = r8.applyNum
                r2.append(r8)
                r2.append(r9)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r7, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.viewId
                r7 = 1
                if (r2 != r7) goto L_0x0caa
                java.lang.String r2 = "AT+EMCFC=6,0,2,"
                goto L_0x0cb7
            L_0x0caa:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r2 = r2.viewId
                if (r2 != 0) goto L_0x0cb5
                java.lang.String r2 = "AT+EMCFC=6,1,2,"
                goto L_0x0cb7
            L_0x0cb5:
                java.lang.String r2 = "AT+EMCFC=6,2,2,"
            L_0x0cb7:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r8 = r1.what
                r7.applyOtaFilePath(r2, r8)
                goto L_0x02bb
            L_0x0cc1:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                r8.append(r14)
                r8.append(r4)
                r8.append(r10)
                if (r3 != 0) goto L_0x0cd4
                r9 = r18
                goto L_0x0ce3
            L_0x0cd4:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r12)
                r9.append(r3)
                java.lang.String r9 = r9.toString()
            L_0x0ce3:
                r8.append(r9)
                java.lang.String r8 = r8.toString()
                com.mediatek.engineermode.Elog.e(r7, r8)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r9.append(r14)
                r9.append(r4)
                r9.append(r2)
                java.lang.String r2 = r9.toString()
                r9 = 0
                r7.showDialog(r2, r8, r9)
                goto L_0x02bb
            L_0x0d07:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r3 = r3.viewId
                r4 = 1
                if (r3 != r4) goto L_0x0d11
                goto L_0x0d1d
            L_0x0d11:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                int r3 = r3.viewId
                if (r3 != 0) goto L_0x0d1c
                r15 = r21
                goto L_0x0d1d
            L_0x0d1c:
                r15 = r8
            L_0x0d1d:
                r3 = r15
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                r5 = 0
                java.lang.Throwable r6 = r4.exception
                if (r6 != 0) goto L_0x0d44
                r5 = 1
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r8 = "Init apply Result: "
                r6.append(r8)
                java.lang.Object r8 = r4.result
                java.lang.String[] r8 = (java.lang.String[]) r8
                java.lang.String r8 = java.util.Arrays.toString(r8)
                r6.append(r8)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.d(r7, r6)
            L_0x0d44:
                if (r5 == 0) goto L_0x0d5e
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Init Apply "
                r2.append(r6)
                r2.append(r3)
                r2.append(r9)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r2)
                goto L_0x0d90
            L_0x0d5e:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r8 = "Init Apply "
                r6.append(r8)
                r6.append(r3)
                r6.append(r10)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.e(r7, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity98 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity98.this
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Init Apply "
                r8.append(r9)
                r8.append(r3)
                r8.append(r2)
                java.lang.String r2 = r8.toString()
                r8 = 0
                r7.showDialog(r2, r6, r8)
            L_0x0d90:
                r2 = r4
                r3 = r19
                r4 = r20
                goto L_0x0d9c
            L_0x0d96:
                r2 = r17
                r3 = r19
                r4 = r20
            L_0x0d9c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mcfconfig.McfConfigActivity98.AnonymousClass6.handleMessage(android.os.Message):void");
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsModemEnabled = true;
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
    private McfCertification mcfCertObj;
    private SharedPreferences mcfConfigSh;
    /* access modifiers changed from: private */
    public final LinkedHashMap<String, String> mcfLoadInfoCmd = new LinkedHashMap<String, String>() {
        {
            put("MCF Load: MD_PROJECT_NAME", "AT+EMCFC=8,0,\"MD_PROJECT_NAME\",\"\"");
            put("MCF IMG MD Label: MCF_IMG_MD_LABEL", "AT+EMCFC=8,0,\"MCF_IMG_MD_LABEL\",\"\"");
            put("MCF IMG Build Time: MCF_IMG_BUILD_TIME", "AT+EMCFC=8,0,\"MCF_IMG_BUILD_TIME\",\"\"");
        }
    };
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
    private String mcfResult;
    /* access modifiers changed from: private */
    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.gen_opota_ota_path:
                    boolean unused = McfConfigActivity98.this.isGenopotaOtaPath = true;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,2,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.gen_opota_runtime_path:
                    boolean unused2 = McfConfigActivity98.this.isGenopotaOtaPath = false;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,2,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ini_disable:
                    int unused3 = McfConfigActivity98.this.iniStatus = 0;
                    return;
                case R.id.ini_enable:
                    int unused4 = McfConfigActivity98.this.iniStatus = 1;
                    return;
                case R.id.opota_ota_path:
                    boolean unused5 = McfConfigActivity98.this.isOpotaOtaPath = true;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,1,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.opota_runtime_path:
                    boolean unused6 = McfConfigActivity98.this.isOpotaOtaPath = false;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,1,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ota_ota_path:
                    boolean unused7 = McfConfigActivity98.this.isOtaOtaPath = true;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,0,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.ota_path_custom:
                    int unused8 = McfConfigActivity98.this.otaPathType = 1;
                    new FileLoadTask().execute(new String[]{"/vendor/etc/mdota/custom"});
                    return;
                case R.id.ota_path_default:
                    int unused9 = McfConfigActivity98.this.otaPathType = 0;
                    new FileLoadTask().execute(new String[]{"/vendor/etc/mdota/mtk_default"});
                    return;
                case R.id.ota_runtime_path:
                    boolean unused10 = McfConfigActivity98.this.isOtaOtaPath = false;
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,0,1", 8);
                    new loadOTAPathTask().execute(new Integer[]{1});
                    return;
                case R.id.reset_ota:
                    McfConfigActivity98.this.initOtaListView();
                    McfConfigActivity98.this.loadOtaFileList("AT+EMCFC=4,0,1", 8);
                    return;
                case R.id.runtime_path:
                    int unused11 = McfConfigActivity98.this.otaPathType = 2;
                    new FileLoadTask().execute(new String[]{"/mnt/vendor/nvcfg/mdota"});
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int opId;
    /* access modifiers changed from: private */
    public RadioGroup opOtaPathRgroup;
    private CheckBox opotaResetMdEnable;
    private LinearLayout opotaView;
    private McfOtaFileAdapter otaFileAdapter;
    private McfFileSelectListView otaFileList;
    /* access modifiers changed from: private */
    public RadioGroup otaPathRgroup;
    /* access modifiers changed from: private */
    public int otaPathType = 0;
    /* access modifiers changed from: private */
    public RadioGroup pathRgroup;
    /* access modifiers changed from: private */
    public int phoneid;
    private Button queryHwOtaBtn;
    /* access modifiers changed from: private */
    public int readySim;
    private Button removeOtaBtn;
    /* access modifiers changed from: private */
    public boolean resetMd = false;
    private Button resetOtaBtn;
    private EditText sertInputEt;
    int simType;
    private String targetOtaFile;
    /* access modifiers changed from: private */
    public Map<Integer, CountDownTimer> timerMap = new HashMap();
    /* access modifiers changed from: private */
    public int viewId;

    static /* synthetic */ int access$108(McfConfigActivity98 x0) {
        int i = x0.applyNum;
        x0.applyNum = i + 1;
        return i;
    }

    /* access modifiers changed from: package-private */
    public String getStringValueFromMap(int key, HashMap<Integer, String> sourceSet) {
        if (sourceSet.containsKey(Integer.valueOf(key))) {
            return sourceSet.get(Integer.valueOf(key));
        }
        return "" + key;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_mcf_config_for98);
        this.opotaResetMdEnable = (CheckBox) findViewById(R.id.resetmd_enable);
        this.genOpotaResetMdEnable = (CheckBox) findViewById(R.id.gen_resetmd_enable);
        this.opotaView = (LinearLayout) findViewById(R.id.mcf_opota_view);
        this.generalView = (LinearLayout) findViewById(R.id.mcf_general_view);
        this.generalOpOtaView = (LinearLayout) findViewById(R.id.mcf_general_opota_view);
        this.certificationView = (LinearLayout) findViewById(R.id.mcf_certification_view);
        this.certotaQueryBtn = (Button) findViewById(R.id.cert_ota_query);
        this.checkOpOtaBtn = (Button) findViewById(R.id.opota_check);
        this.checkOtaBtn = (Button) findViewById(R.id.ota_check);
        this.queryHwOtaBtn = (Button) findViewById(R.id.ota_hw_query);
        this.checkHwOtaBtn = (Button) findViewById(R.id.ota_hw_check);
        this.checkGenOpOtaBtn = (Button) findViewById(R.id.gen_opota_check);
        this.applyOtaBtn = (Button) findViewById(R.id.ota_apply);
        this.applyOpOtaBtn = (Button) findViewById(R.id.opota_apply);
        this.applyGenOpOtaBtn = (Button) findViewById(R.id.gen_opota_apply);
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
        this.applyOpOtaBtn.setOnClickListener(this);
        this.applyGenOpOtaBtn.setOnClickListener(this);
        this.dumpOta.setOnClickListener(this);
        this.dumpOtaToModem.setOnClickListener(this);
        this.iniRefreshBtn.setOnClickListener(this);
        this.queryHwOtaBtn.setOnClickListener(this);
        this.checkHwOtaBtn.setOnClickListener(this);
        this.checkOtaBtn.setOnClickListener(this);
        this.checkOpOtaBtn.setOnClickListener(this);
        this.checkGenOpOtaBtn.setOnClickListener(this);
        this.certotaQueryBtn.setOnClickListener(this);
        this.certBtn.setOnClickListener(this);
        this.sertInputEt.setVisibility(8);
        this.otaPathType = 0;
        this.simType = getIntent().getIntExtra("mSimType", 0);
        if (isSimReady(this.phoneid)) {
            i = this.phoneid;
        } else if (isSimReady((this.phoneid + 1) % 2)) {
            i = (this.phoneid + 1) % 2;
        } else {
            i = ModemCategory.getCapabilitySim();
        }
        this.readySim = i;
        Elog.d("McfConfig", "Selected: Sim" + this.phoneid + ", Ready: Sim" + this.readySim);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        EmUtils.registerForModemStatusChanged(this.readySim, this.mHandler, 102);
        EmUtils.registerForUrcInfo(this.readySim, this.mHandler, 22);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        this.mcfConfigSh = getSharedPreferences("mcf_config_settings", 0);
        this.iniStatusGroup.setOnCheckedChangeListener(this.onCheckedChangeListener);
        this.curQueryList = new ArrayList<>();
        if (this.simType == McfSimSelectActivity.SHOW_LOAD_INFO_VIEW) {
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(8);
            this.certificationView.setVisibility(8);
            this.generalOpOtaView.setVisibility(8);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.applyNum = 0;
            this.viewId = this.simType;
            this.curQueryList.clear();
            showMcfLoadInfo();
        } else if (this.simType == McfSimSelectActivity.SHOW_GENERAL_VIEW) {
            this.otaFileList = (McfFileSelectListView) findViewById(R.id.ota_listview);
            this.addOtaBtn = (Button) findViewById(R.id.add_ota);
            this.removeOtaBtn = (Button) findViewById(R.id.remove_ota);
            this.resetOtaBtn = (Button) findViewById(R.id.reset_ota);
            initOtaListView();
            initOtaCommonView();
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(0);
            this.certificationView.setVisibility(8);
            this.generalOpOtaView.setVisibility(8);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 1;
            loadOtaFileList("AT+EMCFC=4,0,1", 8);
        } else if (this.simType == McfSimSelectActivity.SHOW_CERTIFICATION_VIEW) {
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(8);
            this.generalOpOtaView.setVisibility(8);
            this.certificationView.setVisibility(0);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 3;
            new loadOTAPathTask().execute(new Integer[]{2});
            this.mcfCertObj = new McfCertification();
            this.mSpinner.setSelection(1);
        } else if (this.simType == McfSimSelectActivity.SHOW_GENERAL_SIM_VIEW) {
            this.otaFileList = (McfFileSelectListView) findViewById(R.id.general_opota_listview);
            this.addOtaBtn = (Button) findViewById(R.id.add_general_opota);
            this.removeOtaBtn = (Button) findViewById(R.id.remove_general_opota);
            this.resetOtaBtn = (Button) findViewById(R.id.reset_general_opota);
            initOtaListView();
            initOtaCommonView();
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(8);
            this.certificationView.setVisibility(8);
            this.generalOpOtaView.setVisibility(0);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 2;
            loadOtaFileList("AT+EMCFC=4,2,1", 8);
        } else {
            this.otaFileList = (McfFileSelectListView) findViewById(R.id.opota_listview);
            this.addOtaBtn = (Button) findViewById(R.id.add_opota);
            this.removeOtaBtn = (Button) findViewById(R.id.remove_opota);
            this.resetOtaBtn = (Button) findViewById(R.id.reset_opota);
            initOtaListView();
            initOtaCommonView();
            this.opotaView.setVisibility(0);
            this.generalView.setVisibility(8);
            this.certificationView.setVisibility(8);
            this.generalOpOtaView.setVisibility(8);
            this.phoneid = this.simType;
            this.viewId = 0;
            loadOtaFileList("AT+EMCFC=4,1,1", 8);
        }
        new loadOTAPathTask().execute(new Integer[]{0});
    }

    public void showMcfLoadInfo() {
        sendATCommand(this.mcfLoadInfoCmd.get(this.mcfLoadInfoCmd.keySet().toArray()[this.applyNum].toString()), 14);
    }

    public void initOtaListView() {
        this.arrayOfOtaFile.clear();
        McfOtaFileAdapter mcfOtaFileAdapter = new McfOtaFileAdapter(this, this.arrayOfOtaFile);
        this.otaFileAdapter = mcfOtaFileAdapter;
        this.otaFileList.setAdapter(mcfOtaFileAdapter);
    }

    public void initOtaListViewContent() {
        this.arrayOfOtaFile.clear();
        for (int i = 0; i < this.curQueryList.size(); i++) {
            this.arrayOfOtaFile.add(new FileInfo(this.curQueryList.get(i), -1));
        }
        if (this.arrayOfOtaFile.size() > 0) {
            FileInfo fileInfo = this.arrayOfOtaFile.get(0);
            this.curOtaFileInfo = fileInfo;
            this.firstConstFile = fileInfo;
        } else {
            this.arrayOfOtaFile.add(new FileInfo((String) null, false));
        }
        new saveOTAPathTask().execute(new Integer[]{Integer.valueOf(this.viewId)});
        this.otaFileAdapter.resetAdapter(this.arrayOfOtaFile);
    }

    public void initOtaCommonView() {
        this.addOtaBtn.setOnClickListener(this);
        this.removeOtaBtn.setOnClickListener(this);
        this.resetOtaBtn.setOnClickListener(this);
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
        resetStateMap();
        EmUtils.unregisterForUrcInfo(this.readySim);
        EmUtils.unregisterForModemStatusChanged(this.readySim);
    }

    public void onBackPressed() {
        if (this.timerMap.containsKey(2)) {
            showDialog("Please Wait", "Reboot modem is ongoing......", false);
            Elog.d("McfConfig", "taskSet is " + this.timerMap);
            return;
        }
        resetStateMap();
        finish();
    }

    private void selectFile(String targetPath, int requestCode) {
        Elog.d("McfConfig", "[selectFile] storagePath: " + targetPath);
        McfFileSelectActivity.actionStart(this, targetPath, requestCode, true);
    }

    /* access modifiers changed from: private */
    public void showDialog(String title, String msg, final boolean needFinish) {
        if (!isFinishing()) {
            new AlertDialog.Builder(this).setCancelable(true).setTitle(title).setMessage(msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (needFinish) {
                        McfConfigActivity98.this.finish();
                    }
                }
            }).create().show();
        }
    }

    /* access modifiers changed from: private */
    public void removeStateMap(int type) {
        if (this.timerMap.get(Integer.valueOf(type)) != null) {
            this.timerMap.get(Integer.valueOf(type)).cancel();
            this.timerMap.remove(Integer.valueOf(type));
        }
        if (this.dialogMap.get(Integer.valueOf(type)) != null) {
            this.dialogMap.get(Integer.valueOf(type)).dismiss();
            this.dialogMap.remove(Integer.valueOf(type));
        }
    }

    private void resetStateMap() {
        for (Integer intValue : this.timerMap.keySet()) {
            int type = intValue.intValue();
            if (this.timerMap.get(Integer.valueOf(type)) != null) {
                this.timerMap.get(Integer.valueOf(type)).cancel();
            }
            if (this.dialogMap.get(Integer.valueOf(type)) != null) {
                this.dialogMap.get(Integer.valueOf(type)).dismiss();
            }
        }
        this.timerMap.clear();
        this.dialogMap.clear();
    }

    private void showProgressDialog(int type, String title, String msg) {
        ProgressDialog mProgressDialog = this.dialogMap.get(Integer.valueOf(type)) == null ? new ProgressDialog(this) : this.dialogMap.get(Integer.valueOf(type));
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        this.dialogMap.put(Integer.valueOf(type), mProgressDialog);
        Elog.d("McfConfig", "After timer.start, add dialog " + type);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str;
        if (resultCode != -1) {
            Elog.e("McfConfig", "[onActivityResult] error, resultCode: " + resultCode);
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        ArrayList<Uri> arrayListUri = new ArrayList<>();
        Uri uri = data.getData();
        ArrayList<Uri> arrayList = data.getParcelableArrayListExtra("multipleFile");
        if (uri != null || (arrayList != null && arrayList.size() != 0)) {
            if (uri != null) {
                arrayListUri.add(uri);
            } else {
                arrayListUri = arrayList;
            }
            Elog.d("McfConfig", "[getSelectedFilePath] uri: " + Arrays.toString(arrayListUri.toArray()));
            for (int i = 0; i < arrayListUri.size(); i++) {
                String srcOtaPath = arrayListUri.get(i).getPath();
                if (srcOtaPath.indexOf(46) < 0) {
                    StringBuilder sb = new StringBuilder();
                    if (this.isOtaOtaPath) {
                        str = McfFileSelectActivity.OTA_PARENT;
                    } else {
                        str = McfFileSelectActivity.RUNTIME_PARENT;
                    }
                    sb.append(str);
                    sb.append(srcOtaPath);
                    sb.append(FileUtils.SEPARATOR);
                    sb.append(McfFileSelectActivity.OTA_AUTO_SELECT);
                    srcOtaPath = sb.toString();
                }
                Elog.i("McfConfig", "[onActivityResult] otaFile: " + srcOtaPath);
                this.curOtaFileInfo = new FileInfo(srcOtaPath.replace("//", FileUtils.SEPARATOR), -1);
                switch (requestCode) {
                    case 0:
                        boolean checkPathValid = checkPathValid(srcOtaPath, McfFileSelectActivity.OTA_SUFFIX, true);
                        this.isOtaPathValid = checkPathValid;
                        if (!checkPathValid) {
                            showDialog("Select OTA Path: ", "Invalid File! (ex:*.mcfota)", false);
                            break;
                        } else {
                            this.arrayOfOtaFile.add(this.curOtaFileInfo);
                            Elog.d("McfConfig", "isOtaPathValid: " + this.isOtaPathValid + ",targetOtaPath :" + srcOtaPath);
                            break;
                        }
                    case 1:
                        boolean checkPathValid2 = checkPathValid(srcOtaPath, McfFileSelectActivity.OP_OTA_SUFFIX, true);
                        this.isOpOtaPathValid = checkPathValid2;
                        if (!checkPathValid2) {
                            showDialog("Select OP-OTA Path: ", "Invalid File! (ex:*.mcfopota)", false);
                            break;
                        } else {
                            this.arrayOfOtaFile.add(this.curOtaFileInfo);
                            Elog.d("McfConfig", "isOpOtaPathValid: " + this.isOpOtaPathValid + ",targetOpOtaPath :" + srcOtaPath);
                            break;
                        }
                    case 2:
                        boolean checkPathValid3 = checkPathValid(srcOtaPath, McfFileSelectActivity.OP_OTA_SUFFIX, true);
                        this.isGenOpOtaPathValid = checkPathValid3;
                        if (!checkPathValid3) {
                            showDialog("Select OP-OTA Path: ", "Invalid File! (ex:*.mcfopota)", false);
                            break;
                        } else {
                            this.arrayOfOtaFile.add(this.curOtaFileInfo);
                            Elog.d("McfConfig", "isGenOpOtaPathValid: " + this.isGenOpOtaPathValid + ",targetGenOpOtaFile :" + srcOtaPath);
                            break;
                        }
                    default:
                        Elog.d("McfConfig", "unsupport requestCode :" + requestCode);
                        break;
                }
            }
            this.otaFileAdapter.notifyDataSetChanged();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPathValid(String filePath, String[] strList, boolean byExtension) {
        String fileStr;
        if (strList == null || FileUtils.isDirctory(filePath)) {
            return true;
        }
        if (byExtension) {
            fileStr = FileUtils.getFileExtension(filePath);
        } else {
            fileStr = FileUtils.getSuperParent(filePath);
        }
        if (fileStr != null && Arrays.asList(strList).contains(fileStr)) {
            return true;
        }
        Elog.e("McfConfig", "[checkPathValid] file path is InValid " + filePath + " strList:" + Arrays.toString(strList) + " byExtension:" + byExtension);
        return false;
    }

    private int sendATCommand(String cmd, int what) {
        if (this.timerMap.keySet().contains(2)) {
            showDialog("Please Wait", "Reboot modem is ongoing......", false);
            return 0;
        }
        String[] atCmd = {cmd, "+EMCFC:"};
        Elog.e("McfConfig", "[sendATCommand] cmd: " + Arrays.toString(atCmd) + ", what:" + what);
        EmUtils.invokeOemRilRequestStringsEm(this.phoneid, atCmd, this.mHandler.obtainMessage(what));
        return 1;
    }

    private int sendATCommand(int phoneNum, String cmd, int what) {
        if (this.timerMap.keySet().contains(2)) {
            showDialog("Please Wait", "Reboot modem is ongoing......", false);
            return 0;
        }
        String[] atCmd = {cmd, "+EMCFC:"};
        if (this.mcfCertObj.getCurCmdResult(phoneNum)) {
            return 0;
        }
        if (this.mcfCertObj.getCurrentCmd(phoneNum) == 1 || isSimReady(phoneNum)) {
            Elog.e("McfConfig", "[sendATCommand] cmd: " + Arrays.toString(atCmd) + ", what:" + what + ", phoneid: " + phoneNum);
            EmUtils.invokeOemRilRequestStringsEm(phoneNum, atCmd, this.mHandler.obtainMessage((phoneNum * 6) + what));
        } else {
            this.mcfCertObj.setCmdResult(phoneNum, new boolean[]{true, true, true});
            Elog.e("McfConfig", "[sendATCommand], sim" + phoneNum + " is absent!");
        }
        return 1;
    }

    /* access modifiers changed from: private */
    public void rebootModem(boolean needReset) {
        Elog.d("McfConfig", "[rebootModem] begining ... needReset:" + needReset);
        if (needReset) {
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
        AnonymousClass9 r2 = new CountDownTimer(1000 * ((long) waitTime), 1000) {
            public void onTick(long millisUntilFinishied) {
                int curId;
                if (i == 2) {
                    McfConfigActivity98 mcfConfigActivity98 = McfConfigActivity98.this;
                    if (mcfConfigActivity98.isSimReady(mcfConfigActivity98.readySim)) {
                        curId = McfConfigActivity98.this.readySim;
                    } else {
                        McfConfigActivity98 mcfConfigActivity982 = McfConfigActivity98.this;
                        curId = mcfConfigActivity982.isSimReady(mcfConfigActivity982.readySim + 1) ? (McfConfigActivity98.this.readySim + 1) % 2 : ModemCategory.getCapabilitySim();
                    }
                    EmRadioHidlAosp.getModemStatus(curId);
                }
            }

            public void onFinish() {
                Elog.d("McfConfig", "timer Timeout.......");
                boolean unused = McfConfigActivity98.this.mIsModemEnabled = true;
                McfConfigActivity98.this.removeStateMap(i);
                int i = i;
                if (i == 1) {
                    EmUtils.showToast("Dump OTA timeout!");
                } else if (i == 3) {
                    McfConfigActivity98.this.showDialog("Ini Refresh ", "Ini Refresh timeout!", false);
                } else if (i == 2) {
                    McfConfigActivity98.this.showDialog("Reboot Modem", "Reboot modem timeout!", false);
                } else if (i == 4) {
                    if (McfConfigActivity98.this.opId == 13) {
                        EmUtils.showToast("Query OTA timeout!");
                        McfConfigActivity98.this.curQueryList.add("");
                        McfConfigActivity98.this.initOtaListViewContent();
                    } else {
                        McfConfigActivity98.this.showDialog("Query OTA", "Query OTA timeout!", false);
                    }
                }
                McfConfigActivity98.this.timerMap.remove(Integer.valueOf(i));
            }
        };
        r2.start();
        this.timerMap.put(Integer.valueOf(type), r2);
        String msg = "Time-consuming operation ";
        if (type == 1 || type == 5) {
            msg = "Dump OTA ";
        } else if (type == 3) {
            msg = "Ini Refresh ";
        } else if (type == 2) {
            msg = "Reboot modem ";
        } else if (type == 4) {
            msg = "Query OTA ";
        }
        showProgressDialog(type, "Waitting", msg + "is ongoing......");
        Elog.d("McfConfig", "Start timer for " + type + ", WAIT_TIME=" + waitTime + "s");
    }

    public void onClick(View v) {
        String str;
        String str2 = "/vendor/etc/mdota";
        switch (v.getId()) {
            case R.id.add_general_opota:
                if (!this.isGenopotaOtaPath) {
                    str2 = "/mnt/vendor/nvcfg/mdota";
                }
                selectFile(str2, 2);
                return;
            case R.id.add_opota:
                if (!this.isOpotaOtaPath) {
                    str2 = "/mnt/vendor/nvcfg/mdota";
                }
                selectFile(str2, 1);
                return;
            case R.id.add_ota:
                if (this.isOtaOtaPath == 0) {
                    str2 = "/mnt/vendor/nvcfg/mdota";
                }
                selectFile(str2, 0);
                return;
            case R.id.cert_bt:
                if (this.timerMap.containsKey(2)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......", false);
                    return;
                }
                int selectItem = this.mSpinner.getSelectedItemPosition();
                this.opId = 23;
                this.resetMd = true;
                Elog.d("McfConfig", "certification begain " + this.opId + ", otaPathType: " + this.otaPathType);
                McfCertification mcfCertification = new McfCertification();
                this.mcfCertObj = mcfCertification;
                mcfCertification.setOpName("");
                this.mcfCertObj.setOpid(this.opId);
                this.mcfCertObj.setPathFlag(this.otaPathType);
                if (selectItem == 0) {
                    this.mcfCertObj.setFilePath("");
                    this.mcfCertObj.setOtaFileName(this.fileList.get(selectItem));
                } else {
                    McfCertification mcfCertification2 = this.mcfCertObj;
                    int i = this.otaPathType;
                    if (i == 0) {
                        str = "/vendor/etc/mdota/mtk_default";
                    } else if (i == 1) {
                        str = "/vendor/etc/mdota/custom";
                    } else {
                        str = "/mnt/vendor/nvcfg/mdota";
                    }
                    mcfCertification2.setFilePath(str);
                    this.mcfCertObj.setOtaFileName(this.arrayOfOtaFile.get(selectItem - 1).getFileAbsolutePath().substring((this.otaPathType == 2 ? "/mnt/vendor/nvcfg/mdota".length() : str2.length()) + 1));
                }
                doCertificationAction();
                return;
            case R.id.cert_ota_query:
                Elog.d("McfConfig", "G98 Cert Ota query begain");
                this.opId = 6;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,0,0", 6);
                return;
            case R.id.gen_opota_apply:
                this.resetMd = this.genOpotaResetMdEnable.isChecked();
                this.opId = 1;
                applyOtaFilePathList(new String[]{"AT+EMCFC=6,1,0,\"\",0,1", "AT+EMCFC=6,1,2,"}, new int[]{0, 1});
                return;
            case R.id.gen_opota_check:
                Elog.d("McfConfig", "G98 General OpOta query begain");
                this.opId = 4;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,2,0", 4);
                return;
            case R.id.ini_refresh:
                Elog.d("McfConfig", "Ini refresh begain, iniStatus:" + this.iniStatus);
                this.opId = 10;
                sendATCommand("AT+EMCFC=7," + this.iniStatus, 10);
                return;
            case R.id.opota_apply:
                this.resetMd = this.opotaResetMdEnable.isChecked();
                this.opId = 1;
                applyOtaFilePathList(new String[]{"AT+EMCFC=6,1,0,\"\",0,1", "AT+EMCFC=6,1,2,"}, new int[]{0, 1});
                return;
            case R.id.opota_check:
                Elog.d("McfConfig", "G98 OpOta query begain");
                this.opId = 6;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,1,0", 4);
                return;
            case R.id.ota_apply:
                this.resetMd = true;
                this.opId = 1;
                applyOtaFilePathList(new String[]{"AT+EMCFC=6,0,0,\"\",0,1", "AT+EMCFC=6,0,2,"}, new int[]{1, 1});
                return;
            case R.id.ota_check:
                Elog.d("McfConfig", "Ota query begain");
                this.opId = 6;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,0,0", 4);
                return;
            case R.id.ota_dump:
                this.dumpBegain = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Elog.d("McfConfig", "Dump OTA file begain :" + df.format(new Date(this.dumpBegain)));
                this.opId = 9;
                sendATCommand("AT+EMCFC=5", 9);
                return;
            case R.id.ota_dump_modem:
                this.dumpBegain = System.currentTimeMillis();
                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
                Elog.d("McfConfig", "Dump OTA to modem begain :" + df1.format(new Date(this.dumpBegain)));
                this.opId = 21;
                sendATCommand("AT+EMCFC=12", 21);
                return;
            case R.id.ota_hw_check:
                Elog.d("McfConfig", "G98 Ota query begain");
                this.opId = 5;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,3,0", 5);
                return;
            case R.id.ota_hw_query:
                Elog.d("McfConfig", "G98 Ota check begain");
                this.opId = 7;
                this.curQueryList.clear();
                startTimer(4, 180);
                sendATCommand("AT+EMCFC=4,3,1", 7);
                return;
            case R.id.remove_general_opota:
            case R.id.remove_opota:
            case R.id.remove_ota:
                ArrayList<FileInfo> list = this.otaFileAdapter.getList();
                this.arrayOfOtaFile = list;
                int len = list.size();
                int index = 1;
                while (index < len) {
                    Elog.d("McfConfig", "remove " + index + FileUtils.SEPARATOR + len + " " + this.arrayOfOtaFile.get(index).isChecked());
                    if (this.arrayOfOtaFile.get(index).isChecked()) {
                        Elog.d("McfConfig", "remove " + index + FileUtils.SEPARATOR + this.arrayOfOtaFile.size());
                        this.arrayOfOtaFile.remove(index);
                        len += -1;
                    } else {
                        index++;
                    }
                }
                this.otaFileAdapter.refresh(this.arrayOfOtaFile);
                return;
            case R.id.reset_general_opota:
                this.resetMd = this.genOpotaResetMdEnable.isChecked();
                this.opId = 3;
                if (sendATCommand("AT+EMCFC=6,2,0,\"\",0,0", 3) != 0) {
                    initOtaListView();
                    loadOtaFileList("AT+EMCFC=4,2,1", 8);
                    return;
                }
                return;
            case R.id.reset_opota:
                this.opId = 3;
                this.resetMd = this.opotaResetMdEnable.isChecked();
                if (sendATCommand("AT+EMCFC=6,1,0,\"\",1,0", 3) != 0) {
                    initOtaListView();
                    loadOtaFileList("AT+EMCFC=4,1,1", 8);
                    return;
                }
                return;
            case R.id.reset_ota:
                this.opId = 3;
                this.resetMd = true;
                if (sendATCommand("AT+EMCFC=6,0,0,\"\",0,0", 3) != 0) {
                    initOtaListView();
                    loadOtaFileList("AT+EMCFC=4,0,1", 8);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void applyOtaFilePathList(String[] msg, int[] what) {
        if (msg.length != what.length || msg.length != 2) {
            Elog.d("McfConfig", "applyOtaFilePath params error");
        } else if (sendATCommand(msg[0], what[0]) == 1) {
            this.applyNum = 1;
            applyOtaFilePath(msg[1], what[1]);
        }
    }

    /* access modifiers changed from: package-private */
    public void applyOtaFilePath(String msg, int what) {
        String setOtaCmd;
        String[] strArr;
        int i;
        String str;
        String setOtaCmd2 = msg;
        if (this.applyNum >= this.arrayOfOtaFile.size()) {
            Elog.d("McfConfig", "applyOtaFilePath fail: " + this.applyNum + ", " + this.arrayOfOtaFile.size());
            return;
        }
        this.targetOtaFile = this.arrayOfOtaFile.get(this.applyNum).getFileAbsolutePath();
        Elog.d("McfConfig", "Apply OTA, OTA path is  " + this.targetOtaFile);
        String str2 = this.targetOtaFile;
        if (str2 != null && !str2.trim().equals("")) {
            boolean checkPathValid = checkPathValid(this.targetOtaFile, McfFileSelectActivity.OTA_DEFAULT, false);
            this.isOtaPathValid = checkPathValid;
            if (checkPathValid) {
                setOtaCmd = setOtaCmd2 + "\"" + this.targetOtaFile;
            } else {
                String str3 = this.targetOtaFile;
                if (this.viewId == 1) {
                    strArr = McfFileSelectActivity.OTA_SUFFIX;
                } else {
                    strArr = McfFileSelectActivity.OP_OTA_SUFFIX;
                }
                boolean checkPathValid2 = checkPathValid(str3, strArr, true);
                this.isOtaPathValid = checkPathValid2;
                if (checkPathValid2) {
                    if (this.isOtaOtaPath) {
                        i = this.targetOtaFile.lastIndexOf("/vendor/etc/mdota") + "/vendor/etc/mdota".length();
                    } else {
                        i = this.targetOtaFile.lastIndexOf("/mnt/vendor/nvcfg/mdota") + "/mnt/vendor/nvcfg/mdota".length();
                    }
                    int dirPos = i;
                    StringBuilder sb = new StringBuilder();
                    sb.append(setOtaCmd2);
                    sb.append("\"");
                    if (this.isOtaOtaPath) {
                        str = McfFileSelectActivity.OTA_PARENT;
                    } else {
                        str = McfFileSelectActivity.RUNTIME_PARENT;
                    }
                    sb.append(str);
                    sb.append(this.targetOtaFile.substring(dirPos));
                    setOtaCmd = sb.toString();
                } else {
                    showDialog("Apply OTA Error", "OTA File is invalid! " + this.targetOtaFile, false);
                    return;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(setOtaCmd);
            sb2.append("\",");
            sb2.append((this.resetMd || this.applyNum < this.arrayOfOtaFile.size() - 1) ? "0" : "1");
            sb2.append(",0");
            String setOtaCmd3 = sb2.toString();
            Elog.d("McfConfig", "ATCommand: " + setOtaCmd3);
            sendATCommand(setOtaCmd3, what);
        }
    }

    class saveOTAPathTask extends AsyncTask<Integer, Void, Void> {
        saveOTAPathTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Integer... params) {
            Elog.d("McfConfig", "[saveOTAPathTask] " + params[0]);
            McfConfigActivity98.this.saveSharedPreference(params[0].intValue());
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
                    McfConfigActivity98.this.getSharedPreference();
                    break;
                case 1:
                    McfConfigActivity98.this.reloadOtaPathSharedPreference();
                    break;
                case 2:
                    McfConfigActivity98.this.getCertPathSharedPreference();
                    break;
            }
            return params[0];
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            int i;
            if (McfConfigActivity98.this.viewId == 2) {
                if (result.intValue() == 0) {
                    McfConfigActivity98.this.genOpotaPathRgroup.clearCheck();
                    McfConfigActivity98.this.genOpotaPathRgroup.check((McfConfigActivity98.this.isGenopotaOtaPath ? McfConfigActivity98.this.isGenopotaOtaPathBt : McfConfigActivity98.this.isGenopotaRuntimePathBt).getId());
                    McfConfigActivity98.this.genOpotaPathRgroup.setOnCheckedChangeListener(McfConfigActivity98.this.onCheckedChangeListener);
                }
            } else if (McfConfigActivity98.this.viewId == 1) {
                if (result.intValue() == 0) {
                    McfConfigActivity98.this.otaPathRgroup.clearCheck();
                    McfConfigActivity98.this.otaPathRgroup.check((McfConfigActivity98.this.isOtaOtaPath ? McfConfigActivity98.this.isOtaOtaPathBt : McfConfigActivity98.this.isOtaRuntimePathBt).getId());
                    McfConfigActivity98.this.otaPathRgroup.setOnCheckedChangeListener(McfConfigActivity98.this.onCheckedChangeListener);
                }
            } else if (McfConfigActivity98.this.viewId == 0) {
                if (result.intValue() == 0) {
                    McfConfigActivity98.this.opOtaPathRgroup.clearCheck();
                    McfConfigActivity98.this.opOtaPathRgroup.check((McfConfigActivity98.this.isOpotaOtaPath ? McfConfigActivity98.this.isOpotaOtaPathBt : McfConfigActivity98.this.isOpotaRuntimePathBt).getId());
                    McfConfigActivity98.this.opOtaPathRgroup.setOnCheckedChangeListener(McfConfigActivity98.this.onCheckedChangeListener);
                }
            } else if (McfConfigActivity98.this.viewId == 3) {
                McfConfigActivity98.this.pathRgroup.clearCheck();
                RadioGroup access$3300 = McfConfigActivity98.this.pathRgroup;
                if (McfConfigActivity98.this.otaPathType == 0) {
                    i = R.id.ota_path_default;
                } else {
                    i = McfConfigActivity98.this.otaPathType == 1 ? R.id.ota_path_custom : R.id.runtime_path;
                }
                access$3300.check(i);
                McfConfigActivity98.this.pathRgroup.setOnCheckedChangeListener(McfConfigActivity98.this.onCheckedChangeListener);
                Elog.d("McfConfig", "CERTIFICATION opId: " + McfConfigActivity98.this.opId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void getCertPathSharedPreference() {
        this.otaPathType = this.mcfConfigSh.getInt("ota_path_type", 0);
    }

    /* access modifiers changed from: private */
    public void getSharedPreference() {
        int i = this.viewId;
        if (i == 1) {
            this.isOtaOtaPath = this.mcfConfigSh.getBoolean("ota_is_otapath", true);
            this.defaultOtaFile = this.mcfConfigSh.getString("default_ota_path", "");
        } else if (i == 0) {
            if (this.phoneid == 1) {
                this.isOpotaOtaPath = this.mcfConfigSh.getBoolean("sim2_opota_is_otapath", true);
                this.defaultOtaFile = this.mcfConfigSh.getString("sim2_opota_default_path", "");
                return;
            }
            this.isOpotaOtaPath = this.mcfConfigSh.getBoolean("sim2_opota_is_otapath", true);
            this.defaultOtaFile = this.mcfConfigSh.getString("sim1_opota_default_path", "");
        } else if (i == 2) {
            this.isGenopotaOtaPath = this.mcfConfigSh.getBoolean("general_opota_is_otapath", true);
            this.defaultOtaFile = this.mcfConfigSh.getString("general_opota_default_path", "");
        }
    }

    /* access modifiers changed from: private */
    public void loadOtaFileList(String ATcmd, int msg) {
        Elog.d("McfConfig", "load Ota file begain");
        this.curQueryList.clear();
        this.opId = 13;
        if (sendATCommand(ATcmd, msg) == 1) {
            startTimer(4, 180);
        }
    }

    /* access modifiers changed from: private */
    public void reloadOtaPathSharedPreference() {
        int i = this.viewId;
        if (i == 1) {
            this.defaultOtaFile = this.mcfConfigSh.getString("default_ota_path", "");
        } else if (i == 1) {
            if (this.phoneid == 1) {
                this.defaultOtaFile = this.mcfConfigSh.getString("sim2_opota_default_path", "");
                return;
            }
            this.isOpotaOtaPath = this.mcfConfigSh.getBoolean("sim2_opota_is_otapath", true);
            this.defaultOtaFile = this.mcfConfigSh.getString("sim1_opota_default_path", "");
        } else if (i == 0) {
            this.defaultOtaFile = this.mcfConfigSh.getString("general_opota_default_path", "");
        }
    }

    /* access modifiers changed from: private */
    public void saveSharedPreference(int actionCode) {
        if (this.firstConstFile != null) {
            SharedPreferences.Editor editor = this.mcfConfigSh.edit();
            if (actionCode == 0) {
                if (this.phoneid == 1) {
                    editor.putBoolean("sim2_opota_is_otapath", this.isOpotaOtaPath);
                    editor.putString("sim2_opota_default_path", this.firstConstFile.getFileAbsolutePath());
                } else {
                    editor.putBoolean("sim2_opota_is_otapath", this.isOpotaOtaPath);
                    editor.putString("sim1_opota_default_path", this.firstConstFile.getFileAbsolutePath());
                }
                Elog.d("McfConfig", "[saveSharedPreference] Save opOtaFile success ! opota_file_path: SIM" + this.phoneid + ":[" + this.firstConstFile.getFileAbsolutePath() + "]," + this.isOpotaOtaPath);
            } else if (actionCode == 1) {
                editor.putString("default_ota_path", this.firstConstFile.getFileAbsolutePath());
                editor.putBoolean("ota_is_otapath", this.isOtaOtaPath);
                Elog.d("McfConfig", "[saveSharedPreference] Save otaFile success " + this.targetOtaFile + "," + this.isOtaOtaPath);
            } else if (actionCode == 2) {
                editor.putBoolean("general_opota_is_otapath", this.isGenopotaOtaPath);
                editor.putString("general_opota_default_path", this.firstConstFile.getFileAbsolutePath());
                Elog.d("McfConfig", "[saveSharedPreference] Save genOpOtaFile success ! " + this.firstConstFile.getFileAbsolutePath() + "," + this.isGenopotaOtaPath);
            } else if (actionCode == 23) {
                editor.putInt("ota_path_type", this.otaPathType);
                editor.putString("cert_file", this.mcfCertObj.getOtaFileName());
                Elog.d("McfConfig", "[saveSharedPreference] Save Certification success ! " + this.otaPathType + ", " + this.opId);
            }
            editor.commit();
        }
    }

    public String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray);
    }

    public void updateFileList(String mReturnString) {
        this.fileList.clear();
        this.arrayOfOtaFile.clear();
        this.fileList.add("OM(Default)");
        if (mReturnString == null || mReturnString.trim().equals("")) {
            Elog.d("McfConfig", "[setFilePathListFromServer] return empty");
        } else {
            Elog.i("McfConfig", "add fileInfo:" + mReturnString);
            String[] fileArray = mReturnString.split(";");
            for (String split : fileArray) {
                String file = split.split(":")[0];
                if (file.indexOf(McfFileSelectActivity.MTK_OTA_CERT) >= 0) {
                    this.fileList.add(file.substring(file.lastIndexOf(FileUtils.SEPARATOR) + McfFileSelectActivity.MTK_OTA_CERT.length() + 1, file.lastIndexOf(".")));
                    this.arrayOfOtaFile.add(new FileInfo(file, -1));
                }
            }
        }
        Elog.d("McfConfig", "setSpinnerFromServer :" + Arrays.toString(this.fileList.toArray()));
    }

    private void doCertificationAction() {
        String atMsg;
        if (this.mcfCertObj.getFilePath().equals("")) {
            atMsg = "AT+EMCFC=6,0," + (this.mcfCertObj.getPathFlag() / 2) + ",\"\",0,0";
        } else {
            atMsg = "AT+EMCFC=6,0," + (this.mcfCertObj.getPathFlag() / 2) + ",\"" + this.mcfCertObj.getOtaFileName() + "\",0,1";
        }
        sendATCommand(atMsg, this.mcfCertObj.getOpid());
    }

    class FileLoadTask extends AsyncTask<String, Void, Void> {
        FileLoadTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            McfConfigActivity98.this.showDialog(0);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... params) {
            try {
                EmUtils.getEmHidlService().getFilePathListWithCallBack(params[0], McfConfigActivity98.this.mEmCallback);
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            McfConfigActivity98.this.removeDialog(0);
            McfConfigActivity98 mcfConfigActivity98 = McfConfigActivity98.this;
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(mcfConfigActivity98, 17367048, mcfConfigActivity98.fileList);
            adapter.setDropDownViewResource(17367049);
            McfConfigActivity98.this.mSpinner.setAdapter(adapter);
        }
    }
}
