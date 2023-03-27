package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponentC2k */
class Cdma1xStatisticsInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_STAT_INFO_IND};

    public Cdma1xStatisticsInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT statistics info";
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
        return new String[]{"total_msg", "error_msg", "acc_1", "acc_2", "acc_8", "dpchLoss_count", "dtchLoss_count", "idleHO_count", "hardHO_count", "interFreqldleHO_count", "silentryRetryTimeout_count", "T40_count", "T41_count"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int total_msg = getFieldValue(data, "total_msg");
        int error_msg = getFieldValue(data, "error_msg");
        int acc_1 = getFieldValue(data, "acc_1");
        int acc_2 = getFieldValue(data, "acc_2");
        int acc_8 = getFieldValue(data, "acc_8");
        int dpchLoss_count = getFieldValue(data, "dpchLoss_count");
        int dtchLoss_count = getFieldValue(data, "dtchLoss_count");
        int idleHO_count = getFieldValue(data, "idleHO_count");
        int hardHO_count = getFieldValue(data, "hardHO_count");
        int interFreqldleHO_count = getFieldValue(data, "interFreqldleHO_count");
        int silentryRetryTimeout_count = getFieldValue(data, "silentryRetryTimeout_count", true);
        int T40_count = getFieldValue(data, "T40_count");
        int T41_count = getFieldValue(data, "T41_count");
        StringBuilder sb = new StringBuilder();
        Msg msg2 = data;
        sb.append("T41_count = ");
        sb.append(T41_count);
        Elog.d("EmInfo/MDMComponent", sb.toString());
        clearData();
        addData(Integer.valueOf(total_msg));
        addData(Integer.valueOf(error_msg));
        addData(Integer.valueOf(acc_1));
        addData(Integer.valueOf(acc_2));
        addData(Integer.valueOf(acc_8));
        addData(Integer.valueOf(dpchLoss_count));
        addData(Integer.valueOf(dtchLoss_count));
        addData(Integer.valueOf(idleHO_count));
        addData(Integer.valueOf(hardHO_count));
        addData(Integer.valueOf(interFreqldleHO_count));
        addData(Integer.valueOf(silentryRetryTimeout_count));
        addData(Integer.valueOf(T40_count));
        addData(Integer.valueOf(T41_count));
        notifyDataSetChanged();
    }
}
