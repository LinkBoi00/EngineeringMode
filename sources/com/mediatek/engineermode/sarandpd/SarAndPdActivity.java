package com.mediatek.engineermode.sarandpd;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class SarAndPdActivity extends Activity implements View.OnClickListener {
    private static final int MSG_FR2_SUPPORT = 3;
    private static final int MSG_QUERY_FBO_SAR_STATE = 5;
    private static final int MSG_QUERY_TA_SAR_STATE = 4;
    private static final int MSG_SET_FBO_SAR_STATE = 6;
    private static final int MSG_SET_TA_SAR_STATE = 7;
    private static final int MSG_SUB6J_FBO_SAR_SUPPORT = 1;
    private static final int MSG_SUB6J_TA_SAR_SUPPORT = 2;
    private static final String TAG = "SarAndPdActivity";
    private final String CMD_FR2_SUPPORT = "AT+XRFTAPDENABLE=?";
    private final String CMD_QUERY_FBO_SAR_STATE = "AT+ERFIDX?";
    private final String CMD_QUERY_TA_SAR_STATE = "AT+XRFTASARENABLE?";
    private final String CMD_SUB6J_FBO_SAR_SUPPORT = "AT+ERFIDX=?";
    private final String CMD_SUB6J_TA_SAR_SUPPORT = "AT+XRFTASARENABLE=?";
    private final String GET_FBO_SAR_COMMAND = "+ERFIDX:";
    private final String GET_TA_SAR_COMMAND = "+XRFTASARENABLE:";
    ArrayAdapter<String> adapter_rf2 = null;
    ArrayAdapter<String> adapter_sub6g = null;
    private EditText et_taeci;
    List<String> fr2_list;
    private Button mButtonGet;
    private Button mButtonSet;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Elog.d(SarAndPdActivity.TAG, "MSG_SUB6J_FBO_SAR_SUPPORT");
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception == null) {
                        SarAndPdActivity.this.sub6g_list.add("FBO-SAR");
                        SarAndPdActivity.this.adapter_sub6g.notifyDataSetChanged();
                        SarAndPdActivity.this.sp_sub6j.setEnabled(true);
                    } else {
                        Elog.e(SarAndPdActivity.TAG, ar.exception.getMessage());
                    }
                    SarAndPdActivity.this.sendAtCommand("AT+XRFTASARENABLE=?", 2);
                    return;
                case 2:
                    Elog.d(SarAndPdActivity.TAG, "MSG_SUB6J_TA_SAR_SUPPORT");
                    AsyncResult ar2 = (AsyncResult) msg.obj;
                    if (ar2.exception == null) {
                        SarAndPdActivity.this.sub6g_list.add("TA-SAR");
                        SarAndPdActivity.this.adapter_sub6g.notifyDataSetChanged();
                        SarAndPdActivity.this.sp_sub6j.setEnabled(true);
                    } else {
                        Elog.e(SarAndPdActivity.TAG, ar2.exception.getMessage());
                    }
                    SarAndPdActivity.this.sendAtCommand("AT+XRFTAPDENABLE=?", 3);
                    return;
                case 3:
                    Elog.d(SarAndPdActivity.TAG, "MSG_FR2_SUPPORT");
                    AsyncResult ar3 = (AsyncResult) msg.obj;
                    if (ar3.exception == null) {
                        SarAndPdActivity.this.fr2_list.add("FBO-PD");
                        SarAndPdActivity.this.fr2_list.add("TA-PD");
                        SarAndPdActivity.this.adapter_rf2.notifyDataSetChanged();
                        SarAndPdActivity.this.sp_fr2.setEnabled(true);
                    } else {
                        Elog.e(SarAndPdActivity.TAG, ar3.exception.getMessage());
                    }
                    SarAndPdActivity.this.sendQueryStateCmd();
                    return;
                case 4:
                    Elog.d(SarAndPdActivity.TAG, "MSG_QUERY_TA_SAR_STATE");
                    SarAndPdActivity sarAndPdActivity = SarAndPdActivity.this;
                    int unused = sarAndPdActivity.query_tasar_result = sarAndPdActivity.parseData((AsyncResult) msg.obj, "+XRFTASARENABLE:");
                    SarAndPdActivity.this.sendAtCommand("AT+ERFIDX?", 5);
                    return;
                case 5:
                    Elog.d(SarAndPdActivity.TAG, "MSG_QUERY_FBO_SAR_STATE");
                    SarAndPdActivity sarAndPdActivity2 = SarAndPdActivity.this;
                    int unused2 = sarAndPdActivity2.query_erfidx_result = sarAndPdActivity2.parseData((AsyncResult) msg.obj, "+ERFIDX:");
                    SarAndPdActivity.this.updateUI();
                    return;
                case 6:
                    Elog.d(SarAndPdActivity.TAG, "MSG_SET_FBO_SAR_STATE");
                    AsyncResult ar4 = (AsyncResult) msg.obj;
                    if (ar4.exception == null) {
                        EmUtils.showToast("set Succeed", 0);
                        return;
                    }
                    Elog.e(SarAndPdActivity.TAG, ar4.exception.getMessage());
                    EmUtils.showToast("set Failed", 0);
                    return;
                case 7:
                    Elog.d(SarAndPdActivity.TAG, "MSG_SET_TA_SAR_STATE");
                    AsyncResult ar5 = (AsyncResult) msg.obj;
                    if (ar5.exception == null) {
                        Elog.d(SarAndPdActivity.TAG, "set AT+XRFTASARENABLE  Succeed");
                        SarAndPdActivity sarAndPdActivity3 = SarAndPdActivity.this;
                        sarAndPdActivity3.SetFBOSARCommend(sarAndPdActivity3.sp_sub6j.getSelectedItem().toString());
                        return;
                    }
                    Elog.e(SarAndPdActivity.TAG, ar5.exception.getMessage());
                    EmUtils.showToast("set Failed", 0);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int query_erfidx_result = -2;
    /* access modifiers changed from: private */
    public int query_tasar_result = -2;
    /* access modifiers changed from: private */
    public Spinner sp_fr2;
    /* access modifiers changed from: private */
    public Spinner sp_sub6j;
    List<String> sub6g_list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_sar_and_pd);
        this.sp_sub6j = (Spinner) findViewById(R.id.sp_sub6g);
        this.sp_fr2 = (Spinner) findViewById(R.id.sp_fr2);
        this.et_taeci = (EditText) findViewById(R.id.et_eci);
        this.mButtonSet = (Button) findViewById(R.id.bt_sar_and_pd_set);
        this.mButtonGet = (Button) findViewById(R.id.bt_sar_and_pd_get);
        this.mButtonSet.setOnClickListener(this);
        this.mButtonGet.setOnClickListener(this);
        ArrayList arrayList = new ArrayList();
        this.sub6g_list = arrayList;
        arrayList.add("OFF");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.sub6g_list);
        this.adapter_sub6g = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.sp_sub6j.setAdapter(this.adapter_sub6g);
        this.sp_sub6j.setEnabled(false);
        ArrayList arrayList2 = new ArrayList();
        this.fr2_list = arrayList2;
        arrayList2.add("OFF");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048, this.fr2_list);
        this.adapter_rf2 = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        this.sp_fr2.setAdapter(this.adapter_rf2);
        this.sp_fr2.setEnabled(false);
        sendQuerySupportCmd();
    }

    public void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        if (v.equals(this.mButtonSet)) {
            SetTASARCommend(this.sp_sub6j.getSelectedItem().toString());
        } else if (v.equals(this.mButtonGet)) {
            sendQueryStateCmd();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void SetTASARCommend(java.lang.String r3) {
        /*
            r2 = this;
            java.lang.String r0 = "SarAndPdActivity"
            java.lang.String r1 = "SetTASARCommend"
            com.mediatek.engineermode.Elog.d(r0, r1)
            int r0 = r3.hashCode()
            switch(r0) {
                case -1828667292: goto L_0x0023;
                case -335347190: goto L_0x0019;
                case 78159: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x002d
        L_0x000f:
            java.lang.String r0 = "OFF"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 0
            goto L_0x002e
        L_0x0019:
            java.lang.String r0 = "FBO-SAR"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 1
            goto L_0x002e
        L_0x0023:
            java.lang.String r0 = "TA-SAR"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 2
            goto L_0x002e
        L_0x002d:
            r0 = -1
        L_0x002e:
            r1 = 7
            switch(r0) {
                case 0: goto L_0x0039;
                case 1: goto L_0x0039;
                case 2: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            goto L_0x003f
        L_0x0033:
            java.lang.String r0 = "AT+XRFTASARENABLE=1"
            r2.sendAtCommand(r0, r1)
            goto L_0x003f
        L_0x0039:
            java.lang.String r0 = "AT+XRFTASARENABLE=0"
            r2.sendAtCommand(r0, r1)
        L_0x003f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.sarandpd.SarAndPdActivity.SetTASARCommend(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void SetFBOSARCommend(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "SarAndPdActivity"
            java.lang.String r1 = "SetFBOSARCommend"
            com.mediatek.engineermode.Elog.d(r0, r1)
            int r0 = r5.hashCode()
            switch(r0) {
                case -1828667292: goto L_0x0023;
                case -335347190: goto L_0x0019;
                case 78159: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x002d
        L_0x000f:
            java.lang.String r0 = "OFF"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 0
            goto L_0x002e
        L_0x0019:
            java.lang.String r0 = "FBO-SAR"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 1
            goto L_0x002e
        L_0x0023:
            java.lang.String r0 = "TA-SAR"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 2
            goto L_0x002e
        L_0x002d:
            r0 = -1
        L_0x002e:
            r1 = 6
            switch(r0) {
                case 0: goto L_0x0056;
                case 1: goto L_0x0033;
                case 2: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            goto L_0x005c
        L_0x0033:
            android.widget.EditText r0 = r4.et_taeci
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.trim()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "AT+ERFIDX=1,"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            r4.sendAtCommand(r2, r1)
            goto L_0x005c
        L_0x0056:
            java.lang.String r0 = "AT+ERFIDX=1,-1"
            r4.sendAtCommand(r0, r1)
        L_0x005c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.sarandpd.SarAndPdActivity.SetFBOSARCommend(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public void updateUI() {
        String state = "";
        Elog.d(TAG, "query_tasar_result = " + this.query_tasar_result + ", query_erfidx_result = " + this.query_erfidx_result);
        int i = this.query_tasar_result;
        if (i == 1) {
            state = "TA-SAR";
        } else if (i == 0 && this.query_erfidx_result == 255) {
            state = "OFF";
        } else if (i == 0 && this.query_erfidx_result != 255) {
            state = "FBO-SAR";
        }
        for (int i2 = 0; i2 < this.sub6g_list.size(); i2++) {
            if (state.equals(this.sub6g_list.get(i2))) {
                this.sp_sub6j.setSelection(i2);
            }
        }
        if (this.query_erfidx_result != -2) {
            EditText editText = this.et_taeci;
            editText.setText(this.query_erfidx_result + "");
            return;
        }
        this.et_taeci.setText("");
    }

    private void sendQuerySupportCmd() {
        sendAtCommand("AT+ERFIDX=?", 1);
    }

    /* access modifiers changed from: private */
    public void sendQueryStateCmd() {
        sendAtCommand("AT+XRFTASARENABLE?", 4);
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String str, int what) {
        Elog.i(TAG, "sendAtCommand() " + str);
        String[] cmd = {str, ""};
        if ("AT+XRFTASARENABLE?".equals(str)) {
            cmd[1] = "+XRFTASARENABLE:";
        } else if ("AT+ERFIDX?".equals(str)) {
            cmd[1] = "+ERFIDX:";
        }
        EmUtils.invokeOemRilRequestStringsEm(cmd, this.mHandler.obtainMessage(what));
    }

    /* access modifiers changed from: private */
    public int parseData(AsyncResult ar, String getCmd) {
        if (ar.exception != null) {
            Elog.e(TAG, ar.exception.getMessage());
        } else if (ar.result != null && (ar.result instanceof String[])) {
            String[] data = (String[]) ar.result;
            if (data.length > 0 && data[0].length() > getCmd.length()) {
                Elog.v(TAG, "data[0] is : " + data[0]);
                if ("+XRFTASARENABLE:".equals(getCmd)) {
                    try {
                        return Integer.valueOf(data[0].substring(getCmd.length()).trim()).intValue();
                    } catch (NumberFormatException e) {
                        Elog.e(TAG, e.getMessage());
                    }
                } else if ("+ERFIDX:".equals(getCmd)) {
                    try {
                        return Integer.valueOf(data[0].substring(getCmd.length()).trim().split(",")[1].trim()).intValue();
                    } catch (NumberFormatException e2) {
                        Elog.e(TAG, e2.getMessage());
                    }
                }
            }
        }
        if ("+XRFTASARENABLE:".equals(getCmd)) {
            EmUtils.showToast("Failed to query TASARENABLE", 0);
            return -2;
        }
        EmUtils.showToast("Failed to query ERFIDX", 0);
        return -2;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Elog.i(TAG, "onDestroy()");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
        }
    }
}
