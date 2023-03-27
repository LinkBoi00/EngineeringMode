package com.mediatek.engineermode.networkinfotc1;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContent;
import com.mediatek.engineermode.networkinfotc1.MDMCoreOperation;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.List;

public class AntennaDiversity extends Activity implements View.OnClickListener, MDMCoreOperation.IMDMSeiviceInterface {
    private static final int ANT_BOTH = 3;
    private static final int ANT_DRX = 2;
    private static final int ANT_PRX = 1;
    private static final String DEFAULT_VALUE = "---";
    private static final int DIALOG_WAIT_INIT = 0;
    private static final int DIALOG_WAIT_SUBSCRIB = 1;
    private static final int DIALOG_WAIT_UNSUBSCRIB = 2;
    private static final int MODE_INDEX_BASE_3G = 10;
    private static final int MSG_NW_INFO_QUERY_3G_4G_ANT = 1;
    private static final int MSG_NW_INFO_QUERY_CDMA_ANT = 2;
    private static final int MSG_NW_INFO_SET_ANT = 3;
    private static final int MSG_NW_INFO_SET_ANT_CDMA = 4;
    private static final String TAG = "AntennaDiversity";
    private String[] SubscribeMsgIdNameCDMA = {MDMContent.MSG_ID_EM_C2K_L4_RTT_RADIO_INFO_IND, MDMContent.MSG_ID_EM_C2K_L4_EVDO_SERVING_INFO_IND};
    private String[] SubscribeMsgIdNameFDD = {MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND};
    private String[] SubscribeMsgIdNameLTE = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};
    private List<MdmBaseComponent> componentsArray = new ArrayList();
    private ToggleButton m1xBoth;
    private ToggleButton m1xDrx;
    private ToggleButton m1xPrx;
    private TextView m1xRssiAnt0;
    private TextView m1xRssiAnt1;
    private TextView m1xRssiCombine;
    private ToggleButton m3GBoth;
    private ToggleButton m3GDrx;
    private ToggleButton m3GPrx;
    private int m3gAnt = -1;
    private ToggleButton m4GBoth;
    private ToggleButton m4GDrx;
    private ToggleButton m4GPrx;
    private int m4gAnt = -1;
    private Handler mAtCmdHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception == null) {
                        String[] result = (String[]) ar.result;
                        for (String access$000 : result) {
                            AntennaDiversity.this.parseCurrentMode(access$000);
                        }
                        AntennaDiversity.this.updateButtons();
                        return;
                    }
                    Elog.e(AntennaDiversity.TAG, "Query 3G/4G antenna currect mode failed.");
                    return;
                case 2:
                    AsyncResult ar2 = (AsyncResult) msg.obj;
                    if (ar2.exception == null) {
                        String[] data = (String[]) ar2.result;
                        Elog.v(AntennaDiversity.TAG, "cdma,data[0] = " + data[0]);
                        try {
                            String mode = data[0].substring(AntennaDiversity.this.mExtent.length());
                            Elog.v(AntennaDiversity.TAG, "data[0] = " + mode);
                            int unused = AntennaDiversity.this.mCdmaAnt = Integer.parseInt(mode);
                            AntennaDiversity.this.updateButtons();
                            return;
                        } catch (NumberFormatException e) {
                            return;
                        }
                    } else {
                        Elog.e(AntennaDiversity.TAG, "exception");
                        return;
                    }
                case 3:
                    if (((AsyncResult) msg.obj).exception == null) {
                        Elog.d(AntennaDiversity.TAG, "set 3G/4G antenna mode succeed.");
                        return;
                    } else {
                        Elog.e(AntennaDiversity.TAG, "set 3G/4G antenna mode failed.");
                        return;
                    }
                case 4:
                    if (((AsyncResult) msg.obj).exception == null) {
                        Elog.d(AntennaDiversity.TAG, "set cdma antenna mode succeed.on/off RF");
                        EmUtils.setAirplaneModeEnabled(true);
                        EmUtils.setAirplaneModeEnabled(false);
                        return;
                    }
                    Elog.e(AntennaDiversity.TAG, "set cdma antenna mode failed.");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mCdmaAnt = -1;
    private ToggleButton mEvdoBoth;
    private ToggleButton mEvdoDrx;
    private ToggleButton mEvdoPrx;
    private TextView mEvdoRssiAnt0;
    private TextView mEvdoRssiAnt1;
    private TextView mEvdoRssiCombine;
    /* access modifiers changed from: private */
    public String mExtent = "+ERXTESTMODE:";
    private TextView mLteRsrpAnt0;
    private TextView mLteRsrpAnt1;
    private TextView mLteRsrpCombine;
    private TextView mLteRsrqAnt0;
    private TextView mLteRsrqAnt1;
    private TextView mLteRsrqCombine;
    private TextView mLteRssiAnt0;
    private TextView mLteRssiAnt1;
    private TextView mLteRssiCombine;
    private TextView mLteSinrAnt0;
    private TextView mLteSinrAnt1;
    private TextView mLteSinrCombine;
    private ProgressDialog mProgressDialog;
    private ProgressDialog mProgressDialog1;
    private ProgressDialog mProgressDialog2;
    private int mSimTypeToShow = 0;
    private TextView mWcdmaRscpAnt0;
    private TextView mWcdmaRscpAnt1;
    private TextView mWcdmaRscpCombine;
    private TextView mWcdmaRssiAnt0;
    private TextView mWcdmaRssiAnt1;
    private TextView mWcdmaRssiCombine;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antenna_diversity);
        this.mWcdmaRscpAnt0 = (TextView) findViewById(R.id.antenna_diversity_rscp_ant0);
        this.mWcdmaRscpAnt1 = (TextView) findViewById(R.id.antenna_diversity_rscp_ant1);
        this.mWcdmaRscpCombine = (TextView) findViewById(R.id.antenna_diversity_rscp_combined);
        this.mWcdmaRssiAnt0 = (TextView) findViewById(R.id.antenna_diversity_pssi_ant0);
        this.mWcdmaRssiAnt1 = (TextView) findViewById(R.id.antenna_diversity_pssi_ant1);
        this.mWcdmaRssiCombine = (TextView) findViewById(R.id.antenna_diversity_pssi_combined);
        this.mLteRsrpAnt0 = (TextView) findViewById(R.id.antenna_diversity_rsrp_ant0);
        this.mLteRsrpAnt1 = (TextView) findViewById(R.id.antenna_diversity_rsrp_ant1);
        this.mLteRsrpCombine = (TextView) findViewById(R.id.antenna_diversity_rsrp_combined);
        this.mLteRsrqAnt0 = (TextView) findViewById(R.id.antenna_diversity_rsrq_ant0);
        this.mLteRsrqAnt1 = (TextView) findViewById(R.id.antenna_diversity_rsrq_ant1);
        this.mLteRsrqCombine = (TextView) findViewById(R.id.antenna_diversity_rsrq_combined);
        this.mLteRssiAnt0 = (TextView) findViewById(R.id.antenna_diversity_rssi_ant0);
        this.mLteRssiAnt1 = (TextView) findViewById(R.id.antenna_diversity_rssi_ant1);
        this.mLteRssiCombine = (TextView) findViewById(R.id.antenna_diversity_rssi_combined);
        this.mLteSinrAnt0 = (TextView) findViewById(R.id.antenna_diversity_sinr_ant0);
        this.mLteSinrAnt1 = (TextView) findViewById(R.id.antenna_diversity_sinr_ant1);
        this.mLteSinrCombine = (TextView) findViewById(R.id.antenna_diversity_sinr_combined);
        this.m1xRssiAnt0 = (TextView) findViewById(R.id.antenna_diversity_1x_rssi_ant0);
        this.m1xRssiAnt1 = (TextView) findViewById(R.id.antenna_diversity_1x_rssi_ant1);
        this.m1xRssiCombine = (TextView) findViewById(R.id.antenna_diversity_1x_rssi_combined);
        this.mEvdoRssiAnt0 = (TextView) findViewById(R.id.antenna_diversity_evdo_rssi_ant0);
        this.mEvdoRssiAnt1 = (TextView) findViewById(R.id.antenna_diversity_evdo_rssi_ant1);
        this.mEvdoRssiCombine = (TextView) findViewById(R.id.antenna_diversity_evdo_rssi_combined);
        this.m3GPrx = (ToggleButton) findViewById(R.id.antenna_diversity_3g_prx);
        this.m3GDrx = (ToggleButton) findViewById(R.id.antenna_diversity_3g_drx);
        this.m3GBoth = (ToggleButton) findViewById(R.id.antenna_diversity_3g_prx_drx);
        this.m4GPrx = (ToggleButton) findViewById(R.id.antenna_diversity_4g_prx);
        this.m4GDrx = (ToggleButton) findViewById(R.id.antenna_diversity_4g_drx);
        this.m4GBoth = (ToggleButton) findViewById(R.id.antenna_diversity_4g_prx_drx);
        this.m1xPrx = (ToggleButton) findViewById(R.id.antenna_diversity_1x_prx);
        this.m1xDrx = (ToggleButton) findViewById(R.id.antenna_diversity_1x_drx);
        this.m1xBoth = (ToggleButton) findViewById(R.id.antenna_diversity_1x_prx_drx);
        this.mEvdoPrx = (ToggleButton) findViewById(R.id.antenna_diversity_evdo_prx);
        this.mEvdoDrx = (ToggleButton) findViewById(R.id.antenna_diversity_evdo_drx);
        this.mEvdoBoth = (ToggleButton) findViewById(R.id.antenna_diversity_evdo_prx_drx);
        this.m3GPrx.setOnClickListener(this);
        this.m3GDrx.setOnClickListener(this);
        this.m3GBoth.setOnClickListener(this);
        this.m4GPrx.setOnClickListener(this);
        this.m4GDrx.setOnClickListener(this);
        this.m4GBoth.setOnClickListener(this);
        this.m1xPrx.setOnClickListener(this);
        this.m1xDrx.setOnClickListener(this);
        this.m1xBoth.setOnClickListener(this);
        this.mEvdoPrx.setOnClickListener(this);
        this.mEvdoDrx.setOnClickListener(this);
        this.mEvdoBoth.setOnClickListener(this);
        this.mSimTypeToShow = 0;
        showDialog(0);
        MDMCoreOperation.getInstance().mdmInitialize(this);
        MDMCoreOperation.getInstance().setOnMDMChangedListener(this);
        MDMCoreOperation.getInstance().mdmParametersSeting(this.componentsArray, this.mSimTypeToShow);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        QueryAntennaStatus();
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        Elog.d(TAG, "onDestroy");
    }

    public void onBackPressed() {
        showDialog(2);
        MDMCoreOperation.getInstance().mdmlUnSubscribe();
    }

    public void onClick(View arg0) {
        String cmd = "";
        int old3gAnt = this.m3gAnt;
        int old4gAnt = this.m4gAnt;
        int oldCdmaAnt = this.mCdmaAnt;
        if (arg0.getId() == this.m3GPrx.getId()) {
            this.m3gAnt = 1;
            cmd = "AT+ERXPATH=10";
        } else if (arg0.getId() == this.m3GDrx.getId()) {
            this.m3gAnt = 2;
            cmd = "AT+ERXPATH=11";
        } else if (arg0.getId() == this.m3GBoth.getId()) {
            this.m3gAnt = 3;
            cmd = "AT+ERXPATH=12";
        } else if (arg0.getId() == this.m4GPrx.getId()) {
            this.m4gAnt = 1;
            cmd = FeatureSupport.is95ModemAndAbove() ? "AT+EGMC=1,\"rx_path\", 1, 0, 1, 1, 1, 1" : "AT+ERXPATH=1";
        } else if (arg0.getId() == this.m4GDrx.getId()) {
            this.m4gAnt = 2;
            cmd = FeatureSupport.is95ModemAndAbove() ? "AT+EGMC=1,\"rx_path\", 1, 0, 2, 2, 2, 2" : "AT+ERXPATH=2";
        } else if (arg0.getId() == this.m4GBoth.getId()) {
            this.m4gAnt = 3;
            cmd = FeatureSupport.is95ModemAndAbove() ? "AT+EGMC=1,\"rx_path\", 1, 0, 3, 3, 3, 3" : "AT+ERXPATH=0";
        } else if (arg0.getId() == this.m1xPrx.getId()) {
            this.mCdmaAnt = 1;
        } else if (arg0.getId() == this.m1xDrx.getId()) {
            this.mCdmaAnt = 2;
        } else if (arg0.getId() == this.m1xBoth.getId()) {
            this.mCdmaAnt = 3;
        } else if (arg0.getId() == this.mEvdoPrx.getId()) {
            this.mCdmaAnt = 1;
        } else if (arg0.getId() == this.mEvdoDrx.getId()) {
            this.mCdmaAnt = 2;
        } else if (arg0.getId() == this.mEvdoBoth.getId()) {
            this.mCdmaAnt = 3;
        }
        if (old3gAnt != this.m3gAnt) {
            sendATCommand(new String[]{cmd, ""}, 3);
        } else if (old4gAnt != this.m4gAnt) {
            sendATCommand(new String[]{cmd, ""}, 3);
        } else if (oldCdmaAnt != this.mCdmaAnt) {
            sendATCommand(ModemCategory.getCdmaCmdArr(new String[]{"AT+ERXTESTMODE=" + this.mCdmaAnt, "", "DESTRILD:C2K"}), 4);
        }
        setDefaultText();
        updateButtons();
    }

    private void QueryAntennaStatus() {
        sendATCommand(ModemCategory.getCdmaCmdArr(new String[]{"AT+ERXTESTMODE?", "+ERXTESTMODE:", "DESTRILD:C2K"}), 2);
        sendATCommand(new String[]{"AT+ERXPATH?", "+ERXPATH:"}, 1);
    }

    private void registerNetworkInfo() {
        MdmBaseComponent components = new MdmBaseComponent();
        components.setEmComponentName(this.SubscribeMsgIdNameCDMA);
        this.componentsArray.add(components);
        MdmBaseComponent components2 = new MdmBaseComponent();
        components2.setEmComponentName(this.SubscribeMsgIdNameLTE);
        this.componentsArray.add(components2);
        MdmBaseComponent components3 = new MdmBaseComponent();
        components3.setEmComponentName(this.SubscribeMsgIdNameFDD);
        this.componentsArray.add(components3);
        MDMCoreOperation.getInstance().mdmlSubscribe();
    }

    private void setDefaultText() {
        this.mWcdmaRscpAnt0.setText(DEFAULT_VALUE);
        this.mWcdmaRssiAnt0.setText(DEFAULT_VALUE);
        this.mWcdmaRscpAnt1.setText(DEFAULT_VALUE);
        this.mWcdmaRssiAnt1.setText(DEFAULT_VALUE);
        this.mWcdmaRscpCombine.setText(DEFAULT_VALUE);
        this.mWcdmaRssiCombine.setText(DEFAULT_VALUE);
        this.mLteRsrpAnt0.setText(DEFAULT_VALUE);
        this.mLteRsrqAnt0.setText(DEFAULT_VALUE);
        this.mLteRssiAnt0.setText(DEFAULT_VALUE);
        this.mLteSinrAnt0.setText(DEFAULT_VALUE);
        this.mLteRsrpAnt1.setText(DEFAULT_VALUE);
        this.mLteRsrqAnt1.setText(DEFAULT_VALUE);
        this.mLteRssiAnt1.setText(DEFAULT_VALUE);
        this.mLteSinrAnt1.setText(DEFAULT_VALUE);
        this.mLteRsrpCombine.setText(DEFAULT_VALUE);
        this.mLteRsrqCombine.setText(DEFAULT_VALUE);
        this.mLteRssiCombine.setText(DEFAULT_VALUE);
        this.mLteSinrCombine.setText(DEFAULT_VALUE);
        this.m1xRssiAnt0.setText(DEFAULT_VALUE);
        this.m1xRssiAnt1.setText(DEFAULT_VALUE);
        this.m1xRssiCombine.setText(DEFAULT_VALUE);
        this.mEvdoRssiAnt0.setText(DEFAULT_VALUE);
        this.mEvdoRssiAnt1.setText(DEFAULT_VALUE);
        this.mEvdoRssiCombine.setText(DEFAULT_VALUE);
    }

    /* access modifiers changed from: private */
    public void updateButtons() {
        boolean z = false;
        this.m3GPrx.setChecked(this.m3gAnt == 1);
        this.m3GDrx.setChecked(this.m3gAnt == 2);
        this.m3GBoth.setChecked(this.m3gAnt == 3);
        this.m4GPrx.setChecked(this.m4gAnt == 1);
        this.m4GDrx.setChecked(this.m4gAnt == 2);
        this.m4GBoth.setChecked(this.m4gAnt == 3);
        this.m1xPrx.setChecked(this.mCdmaAnt == 1);
        this.m1xDrx.setChecked(this.mCdmaAnt == 2);
        this.m1xBoth.setChecked(this.mCdmaAnt == 3);
        this.mEvdoPrx.setChecked(this.mCdmaAnt == 1);
        this.mEvdoDrx.setChecked(this.mCdmaAnt == 2);
        ToggleButton toggleButton = this.mEvdoBoth;
        if (this.mCdmaAnt == 3) {
            z = true;
        }
        toggleButton.setChecked(z);
    }

    /* access modifiers changed from: private */
    public void parseCurrentMode(String data) {
        Elog.d(TAG, "parseCurrentMode data= " + data);
        try {
            int mode = Integer.valueOf(data.substring("+ERXPATH:".length()).trim()).intValue();
            if (mode >= 10) {
                this.m3gAnt = (mode - 10) + 1;
                Elog.d(TAG, "parseCurrent3GMode is: " + this.m3gAnt);
                return;
            }
            if (mode == 0) {
                this.m4gAnt = 3;
            } else if (mode == 1) {
                this.m4gAnt = 1;
            } else if (mode == 2) {
                this.m4gAnt = 2;
            } else {
                this.m4gAnt = -1;
            }
            Elog.d(TAG, "parseCurrentLteMode is: " + this.m4gAnt);
        } catch (Exception e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
        }
    }

    private void sendATCommand(String[] atCommand, int msg) {
        Elog.v(TAG, "sendATCommand = " + atCommand[0]);
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mAtCmdHandler.obtainMessage(msg));
    }

    public void onUpdateMDMStatus(int msg_id) {
        switch (msg_id) {
            case 0:
                Elog.d(TAG, "MDM Service init done");
                registerNetworkInfo();
                removeDialog(0);
                showDialog(1);
                return;
            case 1:
                Elog.d(TAG, "Subscribe message id done");
                removeDialog(1);
                MDMCoreOperation.getInstance().mdmlEnableSubscribe();
                return;
            case 4:
                Elog.d(TAG, "UnSubscribe message id done");
                removeDialog(2);
                MDMCoreOperation.getInstance().mdmlClosing();
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public int getFieldValue(Msg data, String key, boolean signed) {
        return MDMCoreOperation.getInstance().getFieldValue(data, key, signed);
    }

    /* access modifiers changed from: package-private */
    public int getFieldValue(Msg data, String key) {
        return MDMCoreOperation.getInstance().getFieldValue(data, key, false);
    }

    /* access modifiers changed from: package-private */
    public void updateLTEInfo(Msg data) {
        Msg msg = data;
        int dl_rsrp0 = getFieldValue(msg, "dl_info[0].dl_rsrp[0]", true);
        int dl_rsrq0 = getFieldValue(msg, "dl_info[0].dl_rsrq[0]", true);
        int dl_rssi0 = getFieldValue(msg, "dl_info[0].dl_rssi[0]", true);
        int dl_sinr0 = getFieldValue(msg, "dl_info[0].dl_sinr[0]", true);
        int dl_rsrp1 = getFieldValue(msg, "dl_info[0].dl_rsrp[1]", true);
        int dl_rsrq1 = getFieldValue(msg, "dl_info[0].dl_rsrq[1]", true);
        int dl_rssi1 = getFieldValue(msg, "dl_info[0].dl_rssi[1]", true);
        int dl_sinr1 = getFieldValue(msg, "dl_info[0].dl_sinr[1]", true);
        int dl_rsrp = getFieldValue(msg, "dl_info[0].rsrp", true);
        int dl_rsrq = getFieldValue(msg, "dl_info[0].rsrq", true);
        int dl_rssi = dl_rssi0 > dl_rssi1 ? dl_rssi0 : dl_rssi1;
        int dl_sinr = getFieldValue(msg, "dl_info[0].sinr", true);
        Elog.d(TAG, "dl_rsrp0 = " + dl_rsrp0);
        Elog.d(TAG, "dl_rsrq0 = " + dl_rsrq0);
        Elog.d(TAG, "dl_rssi0 = " + dl_rssi0);
        Elog.d(TAG, "dl_sinr0 = " + dl_sinr0);
        Elog.d(TAG, "dl_rsrp1 = " + dl_rsrp1);
        Elog.d(TAG, "dl_rsrq1 = " + dl_rsrq1);
        Elog.d(TAG, "dl_rssi1 = " + dl_rssi1);
        Elog.d(TAG, "dl_sinr1 = " + dl_sinr1);
        Elog.d(TAG, "dl_rsrp = " + dl_rsrp);
        Elog.d(TAG, "dl_rsrq = " + dl_rsrq);
        Elog.d(TAG, "dl_rssi = " + dl_rssi);
        Elog.d(TAG, "dl_sinr = " + dl_sinr);
        switch (this.m4gAnt) {
            case 1:
                TextView textView = this.mLteRsrpAnt0;
                textView.setText(dl_rsrp0 + "");
                TextView textView2 = this.mLteRsrqAnt0;
                textView2.setText(dl_rsrq0 + "");
                TextView textView3 = this.mLteRssiAnt0;
                textView3.setText(dl_rssi0 + "");
                TextView textView4 = this.mLteSinrAnt0;
                textView4.setText(dl_sinr0 + "");
                return;
            case 2:
                TextView textView5 = this.mLteRsrpAnt1;
                textView5.setText(dl_rsrp1 + "");
                TextView textView6 = this.mLteRsrqAnt1;
                textView6.setText(dl_rsrq1 + "");
                TextView textView7 = this.mLteRssiAnt1;
                textView7.setText(dl_rssi1 + "");
                TextView textView8 = this.mLteSinrAnt1;
                textView8.setText(dl_sinr1 + "");
                return;
            case 3:
                TextView textView9 = this.mLteRsrpCombine;
                textView9.setText(dl_rsrp + "");
                TextView textView10 = this.mLteRsrqCombine;
                textView10.setText(dl_rsrq + "");
                TextView textView11 = this.mLteRssiCombine;
                textView11.setText(dl_rssi + "");
                TextView textView12 = this.mLteSinrCombine;
                textView12.setText(dl_sinr + "");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateWcdmaInfo(Msg data) {
        Msg msg = data;
        int main_ant_idx = getFieldValue(msg, "EmUl1Tas.main_ant_idx");
        int rscp_0 = getFieldValue(msg, "EmUl1Tas.rscp_0", true);
        int rscp_1 = getFieldValue(msg, "EmUl1Tas.rscp_1", true);
        int rscp_2 = getFieldValue(msg, "EmUl1Tas.rscp_2", true);
        int rssi_0 = getFieldValue(msg, "EmUl1Tas.rssi_0", true);
        int rssi_1 = getFieldValue(msg, "EmUl1Tas.rssi_1", true);
        int rssi_2 = getFieldValue(msg, "EmUl1Tas.rssi_2", true);
        int rscp_max = getFieldValue(msg, "EmUl1Tas.rscp_max", true);
        int rssi_max = getFieldValue(msg, "EmUl1Tas.rssi_max", true);
        int rscp_main = 0;
        int rssi_main = 0;
        int rscp_div = 0;
        int rssi_div = 0;
        if (main_ant_idx == 0) {
            rscp_main = rscp_0;
            rssi_main = rssi_0;
            rscp_div = rscp_1;
            rssi_div = rssi_1;
        } else if (main_ant_idx == 1) {
            rscp_main = rscp_1;
            rssi_main = rssi_1;
            rscp_div = rscp_0;
            rssi_div = rssi_0;
        } else if (main_ant_idx == 2) {
            rscp_main = rscp_2;
            rssi_main = rssi_2;
            rscp_div = rscp_1;
            rssi_div = rssi_1;
        } else if (main_ant_idx == 3) {
            rscp_main = rscp_1;
            rssi_main = rssi_1;
            rscp_div = rscp_2;
            rssi_div = rssi_2;
        }
        Elog.d(TAG, "rscp_main = " + rscp_main);
        StringBuilder sb = new StringBuilder();
        int i = main_ant_idx;
        sb.append("rssi_main = ");
        sb.append(rssi_main);
        Elog.d(TAG, sb.toString());
        Elog.d(TAG, "rscp_div = " + rscp_div);
        Elog.d(TAG, "rssi_div = " + rssi_div);
        switch (this.m3gAnt) {
            case 1:
                TextView textView = this.mWcdmaRscpAnt0;
                textView.setText(rscp_main + "");
                TextView textView2 = this.mWcdmaRssiAnt0;
                textView2.setText(rssi_main + "");
                return;
            case 2:
                TextView textView3 = this.mWcdmaRscpAnt1;
                textView3.setText(rscp_div + "");
                TextView textView4 = this.mWcdmaRssiAnt1;
                textView4.setText(rssi_div + "");
                return;
            case 3:
                TextView textView5 = this.mWcdmaRscpCombine;
                textView5.setText(rscp_max + "");
                TextView textView6 = this.mWcdmaRssiCombine;
                textView6.setText(rssi_max + "");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCdma1xInfo(Msg data) {
        int prx_power = getFieldValue(data, MDMContent.C2K_L4_RX_POWER, true);
        int drx_power = getFieldValue(data, "div_rx_power", true);
        int rx_power = prx_power > drx_power ? prx_power : drx_power;
        Elog.d(TAG, "prx_power = " + prx_power);
        Elog.d(TAG, "drx_power = " + rx_power);
        Elog.d(TAG, "rx_power = " + rx_power);
        switch (this.mCdmaAnt) {
            case 1:
                TextView textView = this.m1xRssiAnt0;
                textView.setText(prx_power + "");
                return;
            case 2:
                TextView textView2 = this.m1xRssiAnt1;
                textView2.setText(rx_power + "");
                return;
            case 3:
                TextView textView3 = this.m1xRssiCombine;
                textView3.setText(rx_power + "");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCdmaEvdoInfo(Msg data) {
        int prx_power = getFieldValue(data, "rssi_dbm", true) / 128;
        int drx_power = getFieldValue(data, "div_rssi", true) / 128;
        int rx_power = prx_power > drx_power ? prx_power : drx_power;
        Elog.d(TAG, "prx_power = " + prx_power);
        Elog.d(TAG, "drx_power = " + drx_power);
        Elog.d(TAG, "rx_power = " + rx_power);
        switch (this.mCdmaAnt) {
            case 1:
                TextView textView = this.mEvdoRssiAnt0;
                textView.setText(prx_power + "");
                return;
            case 2:
                TextView textView2 = this.mEvdoRssiAnt1;
                textView2.setText(drx_power + "");
                return;
            case 3:
                TextView textView3 = this.mEvdoRssiCombine;
                textView3.setText(rx_power + "");
                return;
            default:
                return;
        }
    }

    public void onUpdateMDMData(String name, Msg data) {
        Elog.d(TAG, "update = " + name);
        if (name.equals(MDMContent.MSG_ID_EM_C2K_L4_RTT_RADIO_INFO_IND)) {
            updateCdma1xInfo(data);
        } else if (name.equals(MDMContent.MSG_ID_EM_C2K_L4_EVDO_SERVING_INFO_IND)) {
            updateCdmaEvdoInfo(data);
        } else if (name.equals(MDMContent.MSG_ID_EM_EL1_STATUS_IND)) {
            updateLTEInfo(data);
        } else if (name.equals(MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND)) {
            updateWcdmaInfo(data);
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case 0:
                Elog.d(TAG, "Wait MDML init");
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.mProgressDialog = progressDialog;
                progressDialog.setTitle("Waiting");
                this.mProgressDialog.setMessage("Wait MDML init");
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setIndeterminate(true);
                return this.mProgressDialog;
            case 1:
                Elog.d(TAG, "Before Wait subscribe message..");
                ProgressDialog progressDialog2 = new ProgressDialog(this);
                this.mProgressDialog1 = progressDialog2;
                progressDialog2.setTitle("Waiting");
                this.mProgressDialog1.setMessage("Wait subscribe message..");
                this.mProgressDialog1.setCancelable(false);
                this.mProgressDialog1.setIndeterminate(true);
                return this.mProgressDialog1;
            case 2:
                Elog.d(TAG, "Before Wait Unsubscribe message..");
                ProgressDialog progressDialog3 = new ProgressDialog(this);
                this.mProgressDialog2 = progressDialog3;
                progressDialog3.setTitle("Waiting");
                this.mProgressDialog2.setMessage("Wait Unsubscribe message..");
                this.mProgressDialog2.setCancelable(false);
                this.mProgressDialog2.setIndeterminate(true);
                return this.mProgressDialog2;
            default:
                return super.onCreateDialog(id);
        }
    }
}
