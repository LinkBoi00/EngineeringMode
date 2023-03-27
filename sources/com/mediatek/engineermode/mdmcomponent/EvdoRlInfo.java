package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.text.DecimalFormat;

/* compiled from: MDMComponentC2k */
class EvdoRlInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_RL_INFO_IND};

    public EvdoRlInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO RL info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "7. CDMA EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"averageTbsize", "rtcRetransmitCount", "rtcTransmitTotalCount", "txPower", "pilotPower", "rab_1_ratio"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int averageTbsize = getFieldValue(data, "averageTbsize");
        int rtcRetransmitCount = getFieldValue(data, "rtcRetransmitCount");
        int rtcTransmitTotalCount = getFieldValue(data, "rtcTransmitTotalCount");
        int txPower = getFieldValue(data, "txPower", true);
        int pilotPower = getFieldValue(data, "pilotPower", true);
        int rab_1_ratio = getFieldValue(data, "rab_1_ratio");
        DecimalFormat df = new DecimalFormat("#0.00");
        String txPowerFloat = df.format((double) (((float) txPower) / 128.0f));
        String pilotPowerFloat = df.format((double) (((float) pilotPower) / 128.0f));
        clearData();
        addData(Integer.valueOf(averageTbsize));
        addData(Integer.valueOf(rtcRetransmitCount));
        addData(Integer.valueOf(rtcTransmitTotalCount));
        addData(txPowerFloat);
        addData(pilotPowerFloat);
        addData(Integer.valueOf(rab_1_ratio));
        notifyDataSetChanged();
    }
}
