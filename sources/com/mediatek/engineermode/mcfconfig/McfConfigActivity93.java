package com.mediatek.engineermode.mcfconfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.aospradio.EmRadioHidlAosp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class McfConfigActivity93 extends Activity implements View.OnClickListener {
    private final String CMD_OTA_RETURN = "+EMCFC:";
    private final String DEFAULT_FILE_PATH = "/mnt/vendor/nvdata/md/mdota";
    private final String DUMP_OTA_COMMAND = "AT+EMCFC=5";
    private final String GENERAL_OPOTA_FILEPATH_KEY = "general_opota_path";
    private final String MCF_CONFIG_SHAREPRE = "mcf_config_settings";
    private final int MSG_CLEAR_OPOTA_FILEPATH = 3;
    private final int MSG_CLEAR_OTA_FILEPATH = 1;
    private final int MSG_DUMP_OTA_FILE = 6;
    protected final int MSG_GET_MCF_STATUS_URC = 11;
    private final int MSG_MCF_MODEM_STATUS_CHANGED = 102;
    private final int MSG_MCF_RADIO_STATE_CHANGED = 100;
    private final int MSG_MODEM_REBOOT_COMPLETE = 101;
    private final int MSG_OPOTA_QUERY = 9;
    private final int MSG_OTA_QUERY = 8;
    private final int MSG_SET_OPOTA_FILEPATH = 2;
    private final int MSG_SET_OTA_FILEPATH = 0;
    private final String OPOTA_QUERY_COMMAND = "AT+EMCFC=4,1";
    private final String OTA_FILEPATH_KEY = "ota_file_path";
    private final String OTA_QUERY_COMMAND = "AT+EMCFC=4,0";
    private final int OTA_WAIT_TIME = 180;
    /* access modifiers changed from: private */
    public final HashMap<String, String> OtaFilePathType = new HashMap<String, String>() {
        {
            put("0", "Android OTA path");
            put("1", "Runtime path");
        }
    };
    private final int REBOOT_MODEM_WAIT_TIME = 60;
    private final String SET_OPOTA_COMMAND = "AT+EMCFC=2";
    private final String SET_OPOTA_RSTMD_COMMAND = "AT+EMCFC=3,1,1";
    private final String SET_OTA_COMMAND = "AT+EMCFC=1";
    private final int SHOW_GENERAL_VIEW = 1;
    private final int SHOW_OPOTA_VIEW = 0;
    private final String SIM1_OPOTA_FILEPATH_KEY = "sim1_opota_file_path";
    private final String SIM2_OPOTA_FILEPATH_KEY = "sim2_opota_file_path";
    private final int SIM_COUNT = 2;
    private final int STATE_DUMP_OTA = 1;
    private final int STATE_REBOOT_MODEM = 0;
    private final String TAG = "McfConfig/McfConfigActivity93";
    private Button addOpOtaBtn;
    private Button addOtaBtn;
    private Button applyOpOtaBtn;
    private Button applyOtaBtn;
    private Button clearOpOtaBtn;
    private Button clearOtaBtn;
    /* access modifiers changed from: private */
    public String curOpOtaFile;
    /* access modifiers changed from: private */
    public String curOtaFile;
    long dumpBegain;
    long dumpEnd;
    private Button dumpOta;
    private LinearLayout generalView;
    private boolean isOpOtaPathValid = false;
    private boolean isOtaPathValid = false;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:190:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:191:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r24) {
            /*
                r23 = this;
                r0 = r23
                r1 = r24
                r2 = 0
                r3 = 0
                int r4 = r1.what
                java.lang.String r5 = "\nDsbp: "
                java.lang.String r6 = "Mcf Ota: "
                java.lang.String r7 = " Succeed!"
                java.lang.String r8 = " Failed!\n\n"
                java.lang.String r9 = " returned exception:"
                java.lang.String r10 = "AT Command Failed: "
                java.lang.String r11 = "OPOTA"
                java.lang.String r14 = "OTA"
                java.lang.String r15 = ","
                java.lang.String r12 = " "
                java.lang.String r13 = ":"
                r17 = r2
                r18 = 0
                java.lang.Integer r2 = java.lang.Integer.valueOf(r18)
                r19 = r3
                java.lang.String r3 = ""
                r20 = r11
                java.lang.String r11 = "McfConfig/McfConfigActivity93"
                r21 = r14
                r14 = 1
                r22 = r8
                java.lang.Integer r8 = java.lang.Integer.valueOf(r14)
                switch(r4) {
                    case 0: goto L_0x0623;
                    case 1: goto L_0x0486;
                    case 2: goto L_0x0623;
                    case 3: goto L_0x0486;
                    case 6: goto L_0x040c;
                    case 8: goto L_0x028e;
                    case 9: goto L_0x028e;
                    case 11: goto L_0x014e;
                    case 101: goto L_0x00b8;
                    case 102: goto L_0x0052;
                    default: goto L_0x003a;
                }
            L_0x003a:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "unsupport msg :"
                r2.append(r3)
                int r3 = r1.what
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r11, r2)
                goto L_0x07b4
            L_0x0052:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x00a0
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x00a0
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
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r5 = r5.mIsModemEnabled
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.v(r11, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r4 = r4.mIsModemEnabled
                if (r4 != 0) goto L_0x028a
                if (r3 == 0) goto L_0x028a
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.os.Handler r4 = r4.mHandler
                r5 = 101(0x65, float:1.42E-43)
                r4.sendEmptyMessage(r5)
                goto L_0x028a
            L_0x00a0:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Reboot Modem returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r11, r4)
                goto L_0x028a
            L_0x00b8:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r3 = r3.mIsModemEnabled
                if (r3 != 0) goto L_0x0105
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r3 = r3.resetMd
                if (r3 == 0) goto L_0x0105
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.Set r3 = r3.taskSet
                boolean r3 = r3.contains(r2)
                if (r3 != 0) goto L_0x00d5
                goto L_0x0105
            L_0x00d5:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.Set r3 = r3.taskSet
                r3.remove(r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.os.CountDownTimer r2 = r2.timer
                if (r2 == 0) goto L_0x00f0
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.os.CountDownTimer r2 = r2.timer
                r2.cancel()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r3 = 0
                r2.timer = r3
            L_0x00f0:
                java.lang.String r2 = "Reset Modem Success!"
                com.mediatek.engineermode.Elog.e(r11, r2)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r3 = "Reset Modem"
                java.lang.String r4 = "Reset Modem Completed!"
                r2.showDialog(r3, r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r2 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean unused = r2.mIsModemEnabled = r14
                goto L_0x07b4
            L_0x0105:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "SIM"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                int r3 = r3.phoneid
                r2.append(r3)
                java.lang.String r3 = " received MODEM_REBOOT_COMPLETE, but skiped! mIsModemEnabled:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r3 = r3.mIsModemEnabled
                r2.append(r3)
                java.lang.String r3 = " resetMd:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r3 = r3.resetMd
                r2.append(r3)
                java.lang.String r3 = " taskSet:"
                r2.append(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.Set r3 = r3.taskSet
                java.lang.String r3 = r3.toString()
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.mediatek.engineermode.Elog.d(r11, r2)
                return
            L_0x014e:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.Object r5 = r2.result
                byte[] r5 = (byte[]) r5
                java.lang.String r4 = r4.byteArrayToStr(r5)
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Readback from urc = "
                r5.append(r6)
                r5.append(r4)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r11, r5)
                java.lang.String r5 = "+EMCFRPT"
                boolean r5 = r4.startsWith(r5)
                if (r5 == 0) goto L_0x028a
                java.lang.String[] r5 = r4.split(r13)
                int r5 = r5.length
                r6 = 2
                if (r5 >= r6) goto L_0x0182
                goto L_0x028a
            L_0x0182:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.os.CountDownTimer r5 = r5.timer
                if (r5 == 0) goto L_0x0194
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.os.CountDownTimer r5 = r5.timer
                r5.cancel()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r6 = 0
                r5.timer = r6
            L_0x0194:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.app.ProgressDialog r5 = r5.mProgressDialog
                if (r5 == 0) goto L_0x01ab
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.app.ProgressDialog r5 = r5.mProgressDialog
                r5.dismiss()
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r6 = 0
                android.app.ProgressDialog unused = r5.mProgressDialog = r6
            L_0x01ab:
                java.lang.String[] r5 = r4.split(r13)
                r5 = r5[r14]
                java.lang.String r3 = r5.replaceAll(r12, r3)
                java.lang.String[] r4 = r3.split(r15)
                r5 = r4[r18]
                java.lang.String r5 = r5.trim()
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                int r5 = r5.intValue()
                r6 = r4[r14]
                java.lang.String r6 = r6.trim()
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                int r6 = r6.intValue()
                switch(r5) {
                    case 1: goto L_0x01da;
                    default: goto L_0x01d8;
                }
            L_0x01d8:
                goto L_0x028a
            L_0x01da:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                long r9 = java.lang.System.currentTimeMillis()
                r7.dumpEnd = r9
                java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat
                java.lang.String r9 = "HH:mm:ss"
                r7.<init>(r9)
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "Dump OTA file end :"
                r9.append(r10)
                java.util.Date r10 = new java.util.Date
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r12 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                long r12 = r12.dumpBegain
                r10.<init>(r12)
                java.lang.String r10 = r7.format(r10)
                r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.d(r11, r9)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                if (r6 != 0) goto L_0x0216
                java.lang.String r11 = "Success!"
                goto L_0x024c
            L_0x0216:
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                java.lang.String r12 = "Failed with Error Code: "
                r11.append(r12)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r12 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r12 = r12.mcfOtaDumpResult
                java.lang.Integer r13 = java.lang.Integer.valueOf(r6)
                boolean r12 = r12.containsKey(r13)
                if (r12 == 0) goto L_0x0241
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r12 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r12 = r12.mcfOtaDumpResult
                java.lang.Integer r13 = java.lang.Integer.valueOf(r6)
                java.lang.Object r12 = r12.get(r13)
                java.io.Serializable r12 = (java.io.Serializable) r12
                goto L_0x0245
            L_0x0241:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r6)
            L_0x0245:
                r11.append(r12)
                java.lang.String r11 = r11.toString()
            L_0x024c:
                r10.append(r11)
                java.lang.String r11 = "\nDump costs "
                r10.append(r11)
                java.lang.Object[] r11 = new java.lang.Object[r14]
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r12 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                long r12 = r12.dumpEnd
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r14 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                long r14 = r14.dumpBegain
                long r12 = r12 - r14
                float r12 = (float) r12
                r13 = 1148846080(0x447a0000, float:1000.0)
                float r12 = r12 / r13
                java.lang.Float r12 = java.lang.Float.valueOf(r12)
                r11[r18] = r12
                java.lang.String r12 = "%.2f"
                java.lang.String r11 = java.lang.String.format(r12, r11)
                r10.append(r11)
                java.lang.String r11 = " s"
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                java.lang.String r11 = "Dump Result"
                r9.showDialog(r11, r10)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r9 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.Set r9 = r9.taskSet
                r9.remove(r8)
            L_0x028a:
                r3 = r19
                goto L_0x07b8
            L_0x028e:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r4 = 0
                java.lang.Throwable r5 = r2.exception
                if (r5 != 0) goto L_0x0347
                java.lang.Object r5 = r2.result
                if (r5 == 0) goto L_0x035e
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Query OTA ("
                r5.append(r6)
                int r6 = r1.what
                r5.append(r6)
                java.lang.String r6 = " ): "
                r5.append(r6)
                java.lang.Object r6 = r2.result
                java.lang.String[] r6 = (java.lang.String[]) r6
                java.lang.String r6 = java.util.Arrays.toString(r6)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r11, r5)
                java.lang.Object r5 = r2.result
                java.lang.String[] r5 = (java.lang.String[]) r5
                r5 = r5[r18]
                java.lang.String r6 = "+EMCFC:"
                int r6 = r6.length()
                java.lang.String r5 = r5.substring(r6)
                java.lang.String r5 = r5.replaceAll(r12, r3)
                java.lang.String[] r5 = r5.split(r15)
                if (r5 == 0) goto L_0x0346
                r4 = 1
                int r6 = r5.length
                r7 = 2
                if (r6 < r7) goto L_0x02e3
                r6 = r5[r14]
                goto L_0x02e4
            L_0x02e3:
                r6 = r3
            L_0x02e4:
                int r7 = r5.length
                r8 = 3
                if (r7 < r8) goto L_0x0306
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r7 = r7.OtaFilePathType
                r8 = 2
                r9 = r5[r8]
                boolean r7 = r7.containsKey(r9)
                if (r7 == 0) goto L_0x0306
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r7 = r7.OtaFilePathType
                r8 = r5[r8]
                java.lang.Object r7 = r7.get(r8)
                java.lang.String r7 = (java.lang.String) r7
                goto L_0x0307
            L_0x0306:
                r7 = r3
            L_0x0307:
                int r8 = r5.length
                r9 = 4
                if (r8 < r9) goto L_0x030f
                r8 = 3
                r8 = r5[r8]
                goto L_0x0310
            L_0x030f:
                r8 = r3
            L_0x0310:
                boolean r3 = r8.equals(r3)
                if (r3 == 0) goto L_0x0319
                java.lang.String r3 = "No File"
                goto L_0x032b
            L_0x0319:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r7)
                r3.append(r13)
                r3.append(r8)
                java.lang.String r3 = r3.toString()
            L_0x032b:
                java.lang.String r8 = "0"
                boolean r8 = r6.equals(r8)
                if (r8 == 0) goto L_0x0339
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String unused = r8.curOtaFile = r3
                goto L_0x0346
            L_0x0339:
                java.lang.String r8 = "1"
                boolean r8 = r6.equals(r8)
                if (r8 == 0) goto L_0x0346
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r8 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String unused = r8.curOpOtaFile = r3
            L_0x0346:
                goto L_0x035e
            L_0x0347:
                r4 = 0
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r5 = "AT with ota query returned exception:"
                r3.append(r5)
                java.lang.Throwable r5 = r2.exception
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.e(r11, r3)
            L_0x035e:
                r3 = 8
                if (r4 == 0) goto L_0x03c9
                java.lang.String r5 = "ATCommand with ota query success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r5)
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "OTA: "
                r5.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r7 = r7.curOtaFile
                r5.append(r7)
                java.lang.String r7 = ", OPOTA: "
                r5.append(r7)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r7 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r7 = r7.curOpOtaFile
                r5.append(r7)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r11, r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                int r7 = r1.what
                if (r7 != r3) goto L_0x03ab
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r6 = r6.curOtaFile
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                goto L_0x03c2
            L_0x03ab:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "OTA by OP: "
                r3.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r6 = r6.curOpOtaFile
                r3.append(r6)
                java.lang.String r3 = r3.toString()
            L_0x03c2:
                java.lang.String r6 = "OTA Query "
                r5.showDialog(r6, r3)
                goto L_0x028a
            L_0x03c9:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "Query "
                r6.append(r7)
                int r7 = r1.what
                if (r7 != r3) goto L_0x03dc
                r14 = r21
                goto L_0x03de
            L_0x03dc:
                java.lang.String r14 = "OTA by OP"
            L_0x03de:
                r6.append(r14)
                java.lang.String r3 = " failed!"
                r6.append(r3)
                java.lang.String r3 = r6.toString()
                java.lang.String r6 = "OTA Query"
                r5.showDialog(r6, r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r5 = "ATCommand with "
                r3.append(r5)
                int r5 = r1.what
                r3.append(r5)
                java.lang.String r5 = " query failed!"
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.e(r11, r3)
                goto L_0x028a
            L_0x040c:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                r3 = 0
                java.lang.Throwable r4 = r2.exception
                if (r4 != 0) goto L_0x043d
                r3 = 1
                java.lang.Object r4 = r2.result
                if (r4 == 0) goto L_0x0454
                java.lang.Object r4 = r2.result
                boolean r4 = r4 instanceof java.lang.String[]
                if (r4 == 0) goto L_0x0454
                java.lang.Object r4 = r2.result
                java.lang.String[] r4 = (java.lang.String[]) r4
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "ATCommand with Dump OTA returned: "
                r5.append(r6)
                java.lang.String r6 = java.util.Arrays.toString(r4)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.d(r11, r5)
                goto L_0x0454
            L_0x043d:
                r3 = 0
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "ATCommand with Dump OTA returned exception:"
                r4.append(r5)
                java.lang.Throwable r5 = r2.exception
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.mediatek.engineermode.Elog.e(r11, r4)
            L_0x0454:
                if (r3 == 0) goto L_0x0476
                java.lang.String r4 = "ATCommand with Dump OTA success!"
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r4)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r5 = 180(0xb4, float:2.52E-43)
                r4.startTimer(r14, r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r5 = "Waitting"
                java.lang.String r6 = "Dump OTA is ongoing......"
                r4.showProgressDialog(r5, r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.Set r4 = r4.taskSet
                r4.add(r8)
                goto L_0x028a
            L_0x0476:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r4 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String r5 = "Dump OTA"
                java.lang.String r6 = "Dump OTA failed!"
                r4.showDialog(r5, r6)
                java.lang.String r4 = "ATCommand with Dump OTA failed!"
                com.mediatek.engineermode.Elog.e(r11, r4)
                goto L_0x028a
            L_0x0486:
                java.lang.Object r2 = r1.obj
                android.os.AsyncResult r2 = (android.os.AsyncResult) r2
                int r4 = r1.what
                if (r4 != r14) goto L_0x0490
                r20 = r21
            L_0x0490:
                r4 = r20
                r8 = 0
                java.lang.Throwable r14 = r2.exception
                r17 = r8
                java.lang.String r8 = "Clear "
                if (r14 != 0) goto L_0x053c
                r9 = 1
                java.lang.Object r10 = r2.result
                if (r10 == 0) goto L_0x0537
                java.lang.Object r10 = r2.result
                boolean r10 = r10 instanceof java.lang.String[]
                if (r10 == 0) goto L_0x0537
                java.lang.Object r10 = r2.result
                java.lang.String[] r10 = (java.lang.String[]) r10
                int r10 = r10.length
                if (r10 <= 0) goto L_0x0537
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r13 = "Clear Result: "
                r10.append(r13)
                java.lang.Object r13 = r2.result
                java.lang.String[] r13 = (java.lang.String[]) r13
                java.lang.String r13 = java.util.Arrays.toString(r13)
                r10.append(r13)
                java.lang.String r10 = r10.toString()
                com.mediatek.engineermode.Elog.d(r11, r10)
                java.lang.Object r10 = r2.result
                java.lang.String[] r10 = (java.lang.String[]) r10
                r10 = r10[r18]
                java.lang.String[] r10 = r10.split(r15)
                if (r10 == 0) goto L_0x0532
                r13 = 1
                r14 = r10[r13]
                if (r14 == 0) goto L_0x0532
                r14 = 2
                r15 = r10[r14]
                if (r15 == 0) goto L_0x0532
                r15 = r10[r13]
                java.lang.String r13 = r15.trim()
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
                int r13 = r13.intValue()
                r14 = r10[r14]
                java.lang.String r14 = r14.trim()
                java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
                int r14 = r14.intValue()
                if (r14 != 0) goto L_0x0503
                if (r13 != 0) goto L_0x0503
                r5 = 1
                r17 = r5
                goto L_0x0534
            L_0x0503:
                r9 = 0
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>()
                r15.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r17 = r9
                java.util.HashMap r9 = r6.mcfApplyOtaResult
                java.lang.String r6 = r6.getStringValueFromMap(r13, r9)
                r15.append(r6)
                r15.append(r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r6 = r5.mcfApplyDsbpResult
                java.lang.String r5 = r5.getStringValueFromMap(r14, r6)
                r15.append(r5)
                java.lang.String r5 = r15.toString()
                r19 = r5
                goto L_0x0534
            L_0x0532:
                r17 = r9
            L_0x0534:
                r5 = r19
                goto L_0x0582
            L_0x0537:
                r17 = r9
                r5 = r19
                goto L_0x0582
            L_0x053c:
                java.lang.Throwable r5 = r2.exception
                java.lang.String r5 = r5.toString()
                java.lang.String[] r5 = r5.split(r13)
                r6 = 1
                r5 = r5[r6]
                if (r5 == 0) goto L_0x0567
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r5.append(r10)
                java.lang.Throwable r10 = r2.exception
                java.lang.String r10 = r10.toString()
                java.lang.String[] r10 = r10.split(r13)
                r10 = r10[r6]
                r5.append(r10)
                java.lang.String r5 = r5.toString()
                goto L_0x0568
            L_0x0567:
                r5 = r3
            L_0x0568:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r8)
                r6.append(r4)
                r6.append(r9)
                java.lang.Throwable r9 = r2.exception
                r6.append(r9)
                java.lang.String r6 = r6.toString()
                com.mediatek.engineermode.Elog.e(r11, r6)
            L_0x0582:
                if (r17 == 0) goto L_0x05ee
                int r6 = r1.what
                r9 = 1
                if (r6 != r9) goto L_0x0598
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.widget.TextView r6 = r6.otaFile
                r6.setText(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String unused = r6.targetOtaFile = r3
                goto L_0x05ab
            L_0x0598:
                int r6 = r1.what
                r9 = 3
                if (r6 != r9) goto L_0x05ab
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                android.widget.TextView r6 = r6.opOtaFile
                r6.setText(r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.String unused = r6.targetOpOtaFile = r3
            L_0x05ab:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93$saveOTAPathTask r3 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity93$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r3.<init>()
                r6 = 1
                java.lang.Integer[] r9 = new java.lang.Integer[r6]
                int r10 = r1.what
                if (r10 != r6) goto L_0x05bc
                r6 = r18
                goto L_0x05bd
            L_0x05bc:
                r6 = 1
            L_0x05bd:
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                r9[r18] = r6
                r3.execute(r9)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r8)
                r3.append(r4)
                r3.append(r7)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                int r6 = r1.what
                r7 = 1
                if (r6 != r7) goto L_0x05e4
                r14 = 1
                goto L_0x05ea
            L_0x05e4:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r14 = r6.resetMd
            L_0x05ea:
                r3.rebootModem(r14)
                goto L_0x0620
            L_0x05ee:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r8)
                r3.append(r4)
                r14 = r22
                r3.append(r14)
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.e(r11, r3)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r7.append(r8)
                r7.append(r4)
                r7.append(r12)
                java.lang.String r7 = r7.toString()
                r6.showDialog(r7, r3)
            L_0x0620:
                r3 = r5
                goto L_0x07b8
            L_0x0623:
                r14 = r22
                int r2 = r1.what
                if (r2 != 0) goto L_0x062b
                r20 = r21
            L_0x062b:
                r2 = r20
                java.lang.Object r4 = r1.obj
                android.os.AsyncResult r4 = (android.os.AsyncResult) r4
                r8 = 0
                r16 = r3
                java.lang.Throwable r3 = r4.exception
                r17 = r8
                java.lang.String r8 = "Apply "
                if (r3 != 0) goto L_0x06f3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r9 = "ATCmd -> Return "
                r3.append(r9)
                java.lang.Object r9 = r4.result
                r3.append(r9)
                java.lang.String r3 = r3.toString()
                com.mediatek.engineermode.Elog.d(r11, r3)
                r3 = 1
                java.lang.Object r9 = r4.result
                if (r9 == 0) goto L_0x06ee
                java.lang.Object r9 = r4.result
                boolean r9 = r9 instanceof java.lang.String[]
                if (r9 == 0) goto L_0x06ee
                java.lang.Object r9 = r4.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                int r9 = r9.length
                if (r9 <= 0) goto L_0x06ee
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "Apply Result: "
                r9.append(r10)
                java.lang.Object r10 = r4.result
                java.lang.String[] r10 = (java.lang.String[]) r10
                java.lang.String r10 = java.util.Arrays.toString(r10)
                r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.mediatek.engineermode.Elog.d(r11, r9)
                java.lang.Object r9 = r4.result
                java.lang.String[] r9 = (java.lang.String[]) r9
                r9 = r9[r18]
                java.lang.String[] r9 = r9.split(r15)
                if (r9 == 0) goto L_0x06e9
                r10 = 1
                r13 = r9[r10]
                if (r13 == 0) goto L_0x06e9
                r13 = 2
                r15 = r9[r13]
                if (r15 == 0) goto L_0x06e9
                r15 = r9[r10]
                java.lang.String r10 = r15.trim()
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                int r10 = r10.intValue()
                r13 = r9[r13]
                java.lang.String r13 = r13.trim()
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
                int r13 = r13.intValue()
                if (r13 != 0) goto L_0x06bc
                if (r10 != 0) goto L_0x06bc
                r3 = 1
                r16 = r3
                r3 = r19
                goto L_0x06ed
            L_0x06bc:
                r3 = 0
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>()
                r15.append(r6)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r16 = r3
                java.util.HashMap r3 = r6.mcfApplyOtaResult
                java.lang.String r3 = r6.getStringValueFromMap(r10, r3)
                r15.append(r3)
                r15.append(r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r3 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.util.HashMap r5 = r3.mcfApplyDsbpResult
                java.lang.String r3 = r3.getStringValueFromMap(r13, r5)
                r15.append(r3)
                java.lang.String r3 = r15.toString()
                goto L_0x06ed
            L_0x06e9:
                r16 = r3
                r3 = r19
            L_0x06ed:
                goto L_0x073c
            L_0x06ee:
                r16 = r3
                r3 = r19
                goto L_0x073c
            L_0x06f3:
                java.lang.Throwable r3 = r4.exception
                java.lang.String r3 = r3.toString()
                java.lang.String[] r3 = r3.split(r13)
                r5 = 1
                r3 = r3[r5]
                if (r3 == 0) goto L_0x071e
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r10)
                java.lang.Throwable r6 = r4.exception
                java.lang.String r6 = r6.toString()
                java.lang.String[] r6 = r6.split(r13)
                r6 = r6[r5]
                r3.append(r6)
                java.lang.String r3 = r3.toString()
                goto L_0x0720
            L_0x071e:
                r3 = r16
            L_0x0720:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r5.append(r8)
                r5.append(r2)
                r5.append(r9)
                java.lang.Throwable r6 = r4.exception
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.e(r11, r5)
                r16 = r17
            L_0x073c:
                if (r16 == 0) goto L_0x0782
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93$saveOTAPathTask r5 = new com.mediatek.engineermode.mcfconfig.McfConfigActivity93$saveOTAPathTask
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                r5.<init>()
                r13 = 1
                java.lang.Integer[] r6 = new java.lang.Integer[r13]
                int r8 = r1.what
                if (r8 != 0) goto L_0x074f
                r8 = r18
                goto L_0x0750
            L_0x074f:
                r8 = r13
            L_0x0750:
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                r6[r18] = r8
                r5.execute(r6)
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Apply  "
                r5.append(r6)
                r5.append(r2)
                r5.append(r7)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.EmUtils.showToast((java.lang.String) r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r5 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                int r6 = r1.what
                if (r6 != 0) goto L_0x0778
                r14 = r13
                goto L_0x077e
            L_0x0778:
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                boolean r14 = r6.resetMd
            L_0x077e:
                r5.rebootModem(r14)
                goto L_0x07b2
            L_0x0782:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r5.append(r8)
                r5.append(r2)
                r5.append(r14)
                r5.append(r3)
                java.lang.String r5 = r5.toString()
                com.mediatek.engineermode.Elog.e(r11, r5)
                com.mediatek.engineermode.mcfconfig.McfConfigActivity93 r6 = com.mediatek.engineermode.mcfconfig.McfConfigActivity93.this
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r7.append(r8)
                r7.append(r2)
                r7.append(r12)
                java.lang.String r7 = r7.toString()
                r6.showDialog(r7, r5)
            L_0x07b2:
                r2 = r4
                goto L_0x07b8
            L_0x07b4:
                r2 = r17
                r3 = r19
            L_0x07b8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mcfconfig.McfConfigActivity93.AnonymousClass5.handleMessage(android.os.Message):void");
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsModemEnabled = true;
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    int mRadioStates;
    /* access modifiers changed from: private */
    public final HashMap<Integer, String> mcfApplyDsbpResult = new HashMap<Integer, String>() {
        {
            put(0, "SUCCESS");
            put(0, "FAIL_MCF_DSBP_ONGOING");
            put(0, "FAIL_SIM_SWITCH_ONGOING");
            put(0, "FAIL_ONGOING_CALL_OR_ECBM");
            put(0, "FAIL_NO_SIM");
            put(0, "FAIL_NOT_MODE2");
            put(0, "FAIL_SIM_ERROR");
            put(0, "FAIL_UNKNOWN");
            put(0, "MAX");
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
            put(16, "WRITE_DISK_FAIL");
            put(17, "READ_INI_FILE_FAIL");
            put(18, "INVALID_INI_ITEM");
            put(19, "WRITE_INI_FILE_FAIL");
            put(20, "FILE_NOT_CHANGE");
            put(21, "MAX");
        }
    };
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
    public TextView opOtaFile;
    private Button opOtaQueryBtn;
    private CheckBox opotaResetMdEnable;
    private LinearLayout opotaView;
    /* access modifiers changed from: private */
    public TextView otaFile;
    private Button otaQueryBtn;
    /* access modifiers changed from: private */
    public int phoneid;
    /* access modifiers changed from: private */
    public int readySim;
    /* access modifiers changed from: private */
    public boolean resetMd = true;
    /* access modifiers changed from: private */
    public String targetOpOtaFile;
    /* access modifiers changed from: private */
    public String targetOtaFile;
    /* access modifiers changed from: private */
    public Set taskSet = new HashSet();
    CountDownTimer timer;
    private int viewId;

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
        setContentView(R.layout.em_mcf_config_for93);
        this.opotaResetMdEnable = (CheckBox) findViewById(R.id.resetmd_enable);
        this.opotaView = (LinearLayout) findViewById(R.id.mcf_opota_view);
        this.generalView = (LinearLayout) findViewById(R.id.mcf_general_view);
        this.addOtaBtn = (Button) findViewById(R.id.add_ota_browser);
        this.addOpOtaBtn = (Button) findViewById(R.id.add_op_ota_browser);
        this.otaQueryBtn = (Button) findViewById(R.id.ota_query);
        this.opOtaQueryBtn = (Button) findViewById(R.id.opota_query);
        this.addOtaBtn.setOnClickListener(this);
        this.addOpOtaBtn.setOnClickListener(this);
        this.applyOtaBtn = (Button) findViewById(R.id.ota_apply);
        this.clearOtaBtn = (Button) findViewById(R.id.ota_clear);
        this.applyOpOtaBtn = (Button) findViewById(R.id.opota_apply);
        this.clearOpOtaBtn = (Button) findViewById(R.id.opota_clear);
        this.dumpOta = (Button) findViewById(R.id.ota_dump);
        this.applyOtaBtn.setOnClickListener(this);
        this.clearOtaBtn.setOnClickListener(this);
        this.applyOpOtaBtn.setOnClickListener(this);
        this.clearOpOtaBtn.setOnClickListener(this);
        this.dumpOta.setOnClickListener(this);
        this.otaQueryBtn.setOnClickListener(this);
        this.opOtaQueryBtn.setOnClickListener(this);
        this.opOtaFile = (TextView) findViewById(R.id.op_ota_file);
        this.otaFile = (TextView) findViewById(R.id.ota_file);
        int simType = getIntent().getIntExtra("mSimType", 0);
        if (simType == -1) {
            this.opotaView.setVisibility(8);
            this.generalView.setVisibility(0);
            this.phoneid = ModemCategory.getCapabilitySim();
            this.viewId = 1;
        } else {
            this.opotaView.setVisibility(0);
            this.generalView.setVisibility(8);
            this.phoneid = simType;
            this.viewId = 0;
        }
        if (isSimReady(this.phoneid)) {
            readySim2 = this.phoneid;
        } else {
            readySim2 = isSimReady((this.phoneid + 1) % 2) ? (this.phoneid + 1) % 2 : ModemCategory.getCapabilitySim();
        }
        Elog.d("McfConfig/McfConfigActivity93", "Selected: Sim" + this.phoneid + ", Ready: Sim" + readySim2);
        EmUtils.registerForModemStatusChanged(readySim2, this.mHandler, 102);
        EmUtils.registerForUrcInfo(readySim2, this.mHandler, 11);
        this.mcfConfigSh = getSharedPreferences("mcf_config_settings", 0);
        new loadOTAPathTask().execute(new Void[0]);
    }

    public boolean isSimReady(int slotId) {
        int status;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        if (slotId < 0) {
            status = telephonyManager.getSimState();
        } else {
            status = telephonyManager.getSimState(slotId);
        }
        Elog.i("McfConfig/McfConfigActivity93", "slotId = " + slotId + ",simStatus = " + status);
        if (status == 1) {
            return false;
        }
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        Elog.d("McfConfig/McfConfigActivity93", "onDestroy");
        this.taskSet.clear();
        EmUtils.unregisterForUrcInfo(this.readySim);
        EmUtils.unregisterForModemStatusChanged(this.readySim);
    }

    public void onBackPressed() {
        if (this.taskSet.contains(0)) {
            showDialog("Please Wait", "Reboot modem is ongoing......");
            return;
        }
        this.taskSet.clear();
        finish();
    }

    private void selectFile(String targetPath, int requestCode) {
        Elog.d("McfConfig/McfConfigActivity93", "[selectFile] storagePath: " + targetPath);
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
        Elog.d("McfConfig/McfConfigActivity93", "After timer.start");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            Elog.e("McfConfig/McfConfigActivity93", "[onActivityResult] error, resultCode: " + resultCode);
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        Uri uri = data.getData();
        StringBuilder sb = new StringBuilder();
        sb.append("[getSelectedFilePath] uri: ");
        sb.append(uri != null ? uri.toString() : "NULL");
        Elog.d("McfConfig/McfConfigActivity93", sb.toString());
        if (uri != null) {
            String srcOtaPath = uri.getPath();
            Elog.i("McfConfig/McfConfigActivity93", "[onActivityResult] otaFile: " + srcOtaPath);
            switch (requestCode) {
                case 0:
                    boolean checkPathValid = checkPathValid(srcOtaPath, McfFileSelectActivity.OTA_SUFFIX);
                    this.isOtaPathValid = checkPathValid;
                    if (!checkPathValid) {
                        this.otaFile.setText("");
                        showDialog("Select OTA Path: ", "Invalid File! (ex:*.mcfota)");
                        break;
                    } else {
                        this.otaFile.setText(srcOtaPath);
                        this.targetOtaFile = srcOtaPath;
                        Elog.d("McfConfig/McfConfigActivity93", "isOtaPathValid: " + this.isOtaPathValid + ",targetOtaPath :" + this.targetOtaFile);
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
                        this.opOtaFile.setText(srcOtaPath);
                        this.targetOpOtaFile = srcOtaPath;
                        Elog.d("McfConfig/McfConfigActivity93", "isOpOtaPathValid: " + this.isOpOtaPathValid + ",targetOpOtaPath :" + this.targetOpOtaFile);
                        break;
                    }
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
        Elog.e("McfConfig/McfConfigActivity93", "[checkPathValid] file path is InValid " + filePath + " suffixList:" + Arrays.toString(suffix));
        return false;
    }

    private void sendATCommand(String cmd, int what) {
        String[] atCmd = {cmd, "+EMCFC:"};
        Elog.e("McfConfig/McfConfigActivity93", "[sendATCommand] cmd: " + Arrays.toString(atCmd));
        EmUtils.invokeOemRilRequestStringsEm(this.phoneid, atCmd, this.mHandler.obtainMessage(what));
    }

    /* access modifiers changed from: private */
    public void rebootModem(boolean needReset) {
        Elog.d("McfConfig/McfConfigActivity93", "[rebootModem] begining ... needReset:" + needReset);
        if (needReset) {
            this.taskSet.add(0);
            EmUtils.rebootModem();
            this.mIsModemEnabled = false;
            startTimer(0, 60);
        }
    }

    public void getSelectedFilePath(Uri contentUri) {
        StringBuilder sb = new StringBuilder();
        sb.append("[getSelectedFilePath] uri: ");
        sb.append(contentUri != null ? contentUri.toString() : "NULL");
        Elog.d("McfConfig/McfConfigActivity93", sb.toString());
        if (contentUri != null) {
            Elog.d("McfConfig/McfConfigActivity93", "[getUriForFile] path :" + contentUri.getPath());
        }
    }

    public void startTimer(int type, int waitTime) {
        Elog.d("McfConfig/McfConfigActivity93", "waitTime = " + waitTime + "ms");
        final int i = type;
        AnonymousClass6 r2 = new CountDownTimer(1000 * ((long) waitTime), 1000) {
            public void onTick(long millisUntilFinishied) {
                int curId;
                if (i == 0) {
                    McfConfigActivity93 mcfConfigActivity93 = McfConfigActivity93.this;
                    if (mcfConfigActivity93.isSimReady(mcfConfigActivity93.readySim)) {
                        curId = McfConfigActivity93.this.readySim;
                    } else {
                        McfConfigActivity93 mcfConfigActivity932 = McfConfigActivity93.this;
                        curId = mcfConfigActivity932.isSimReady(mcfConfigActivity932.readySim + 1) ? (McfConfigActivity93.this.readySim + 1) % 2 : ModemCategory.getCapabilitySim();
                    }
                    EmRadioHidlAosp.getModemStatus(curId);
                }
            }

            public void onFinish() {
                Elog.d("McfConfig/McfConfigActivity93", "timer Timeout.......");
                boolean unused = McfConfigActivity93.this.mIsModemEnabled = true;
                if (McfConfigActivity93.this.timer != null) {
                    McfConfigActivity93.this.timer.cancel();
                }
                if (McfConfigActivity93.this.mProgressDialog != null) {
                    McfConfigActivity93.this.mProgressDialog.dismiss();
                    ProgressDialog unused2 = McfConfigActivity93.this.mProgressDialog = null;
                }
                int i = i;
                if (i == 1) {
                    EmUtils.showToast("Dump OTA timeout!");
                } else if (i == 0) {
                    McfConfigActivity93.this.showDialog("Reboot Modem", "Reboot modem timeout!");
                }
                McfConfigActivity93.this.timer = null;
                McfConfigActivity93.this.taskSet.remove(Integer.valueOf(i));
            }
        };
        this.timer = r2;
        r2.start();
        Elog.d("McfConfig/McfConfigActivity93", "Start timer for " + type + ", WAIT_TIME=" + waitTime + "s");
    }

    public void onClick(View v) {
        String setOpOtaCmd;
        String clearOpOtaCmd;
        String setOtaCmd;
        String str = "";
        switch (v.getId()) {
            case R.id.add_op_ota_browser:
                selectFile("/mnt/vendor/nvdata/md/mdota", 1);
                return;
            case R.id.add_ota_browser:
                selectFile("/mnt/vendor/nvdata/md/mdota", 0);
                return;
            case R.id.opota_apply:
                Elog.d("McfConfig/McfConfigActivity93", "opota_apply begain");
                this.resetMd = this.opotaResetMdEnable.isChecked();
                String str2 = (String) this.opOtaFile.getText();
                this.targetOpOtaFile = str2;
                boolean checkPathValid = checkPathValid(str2, McfFileSelectActivity.OP_OTA_SUFFIX);
                this.isOpOtaPathValid = checkPathValid;
                if (!checkPathValid) {
                    showDialog("Apply Op-OTA Error", "OP-OTA File is invalid!");
                    return;
                } else if (this.taskSet.contains(0)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    if (this.resetMd) {
                        String str3 = this.targetOpOtaFile;
                        if (str3 == null || str3.trim().equals(str)) {
                            setOpOtaCmd = "AT+EMCFC=2," + "\"\",\"\"";
                        } else {
                            int opDirPos = this.targetOpOtaFile.lastIndexOf("/mnt/vendor/nvdata/md/mdota") + "/mnt/vendor/nvdata/md/mdota".length() + 1;
                            StringBuilder sb = new StringBuilder();
                            sb.append("AT+EMCFC=2,");
                            sb.append("\"");
                            sb.append(this.targetOpOtaFile);
                            sb.append("\",\"");
                            if (this.isOpOtaPathValid && opDirPos >= 0) {
                                str = this.targetOpOtaFile.substring(opDirPos);
                            }
                            sb.append(str);
                            sb.append("\"");
                            setOpOtaCmd = sb.toString();
                        }
                    } else {
                        String str4 = this.targetOpOtaFile;
                        if (str4 == null || str4.trim().equals(str)) {
                            setOpOtaCmd = "AT+EMCFC=3,1,1," + "\"\"";
                        } else {
                            int opDirPos2 = this.targetOpOtaFile.lastIndexOf("/mnt/vendor/nvdata/md/mdota") + "/mnt/vendor/nvdata/md/mdota".length() + 1;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("AT+EMCFC=3,1,1,");
                            sb2.append("\"");
                            if (this.isOpOtaPathValid && opDirPos2 >= 0) {
                                str = this.targetOpOtaFile.substring(opDirPos2);
                            }
                            sb2.append(str);
                            sb2.append("\"");
                            setOpOtaCmd = sb2.toString();
                        }
                    }
                    sendATCommand(setOpOtaCmd, 2);
                    return;
                }
            case R.id.opota_clear:
                Elog.d("McfConfig/McfConfigActivity93", "opota_clear begain");
                this.resetMd = this.opotaResetMdEnable.isChecked();
                if (this.taskSet.contains(0)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                }
                if (this.resetMd) {
                    clearOpOtaCmd = "AT+EMCFC=2,\"\",\"\"";
                } else {
                    clearOpOtaCmd = "AT+EMCFC=3,1,1,\"\"";
                }
                sendATCommand(clearOpOtaCmd, 3);
                return;
            case R.id.opota_query:
                Elog.d("McfConfig/McfConfigActivity93", "OpOta query begain");
                sendATCommand("AT+EMCFC=4,1", 9);
                return;
            case R.id.ota_apply:
                Elog.d("McfConfig/McfConfigActivity93", "ota_apply begain");
                this.resetMd = true;
                String str5 = (String) this.otaFile.getText();
                this.targetOtaFile = str5;
                boolean checkPathValid2 = checkPathValid(str5, McfFileSelectActivity.OTA_SUFFIX);
                this.isOtaPathValid = checkPathValid2;
                if (!checkPathValid2) {
                    showDialog("Apply OTA Error", "OTA File is invalid!");
                    return;
                } else if (this.taskSet.contains(0)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    String str6 = this.targetOtaFile;
                    if (str6 == null || str6.trim().equals(str)) {
                        setOtaCmd = "AT+EMCFC=1," + "\"\",\"\"";
                    } else {
                        int dirPos = this.targetOtaFile.lastIndexOf("/mnt/vendor/nvdata/md/mdota") + "/mnt/vendor/nvdata/md/mdota".length() + 1;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("AT+EMCFC=1,");
                        sb3.append("\"");
                        sb3.append(this.targetOtaFile);
                        sb3.append("\",\"");
                        if (this.isOtaPathValid && dirPos >= 0) {
                            str = this.targetOtaFile.substring(dirPos);
                        }
                        sb3.append(str);
                        sb3.append("\"");
                        setOtaCmd = sb3.toString();
                    }
                    Elog.d("McfConfig/McfConfigActivity93", "ATCommand: " + setOtaCmd);
                    sendATCommand(setOtaCmd, 0);
                    return;
                }
            case R.id.ota_clear:
                Elog.d("McfConfig/McfConfigActivity93", "ota_clear begain");
                this.resetMd = true;
                if (this.taskSet.contains(0)) {
                    showDialog("Please Wait", "Reboot modem is ongoing......");
                    return;
                } else {
                    sendATCommand("AT+EMCFC=1,\"\",\"\"", 1);
                    return;
                }
            case R.id.ota_dump:
                this.dumpBegain = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Elog.d("McfConfig/McfConfigActivity93", "Dump OTA file begain :" + df.format(new Date(this.dumpBegain)));
                sendATCommand("AT+EMCFC=5", 6);
                return;
            case R.id.ota_query:
                Elog.d("McfConfig/McfConfigActivity93", "Ota query begain");
                sendATCommand("AT+EMCFC=4,0", 8);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void getSharedPreference() {
        this.targetOtaFile = this.mcfConfigSh.getString("ota_file_path", "");
        if (this.phoneid == 1) {
            this.targetOpOtaFile = this.mcfConfigSh.getString("sim2_opota_file_path", "");
        } else {
            this.targetOpOtaFile = this.mcfConfigSh.getString("sim1_opota_file_path", "");
        }
    }

    /* access modifiers changed from: private */
    public void saveSharedPreference(int actionCode) {
        SharedPreferences.Editor editor = this.mcfConfigSh.edit();
        if (actionCode == 1) {
            if (this.phoneid == 1) {
                editor.putString("sim2_opota_file_path", this.targetOpOtaFile);
            } else {
                editor.putString("sim1_opota_file_path", this.targetOpOtaFile);
            }
            Elog.d("McfConfig/McfConfigActivity93", "[saveSharedPreference] Save opOtaFile success ! opota_file_path: SIM" + this.phoneid + ":[" + this.targetOpOtaFile + "]");
        } else if (actionCode == 0) {
            editor.putString("ota_file_path", this.targetOtaFile);
            Elog.d("McfConfig/McfConfigActivity93", "[saveSharedPreference] Save otaFile success ota_file_path:" + this.targetOtaFile);
        }
        editor.commit();
    }

    public String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray);
    }

    class saveOTAPathTask extends AsyncTask<Integer, Void, Void> {
        saveOTAPathTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Integer... params) {
            Elog.d("McfConfig/McfConfigActivity93", "[saveOTAPathTask] " + params[0]);
            McfConfigActivity93.this.saveSharedPreference(params[0].intValue());
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            Elog.d("McfConfig/McfConfigActivity93", "Save OTA file path success!");
        }
    }

    class loadOTAPathTask extends AsyncTask<Void, Void, Void> {
        loadOTAPathTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            McfConfigActivity93.this.getSharedPreference();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            McfConfigActivity93.this.otaFile.setText(McfConfigActivity93.this.targetOtaFile);
            McfConfigActivity93.this.opOtaFile.setText(McfConfigActivity93.this.targetOpOtaFile);
            Elog.d("McfConfig/McfConfigActivity93", "Load OTA file path success! OtaFile: " + McfConfigActivity93.this.targetOtaFile + ", OpOtaFile: " + McfConfigActivity93.this.targetOpOtaFile);
        }
    }
}
