package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponentC2k */
class Cdma1xSchInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_SCH_INFO_IND};

    public Cdma1xSchInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT SCH info";
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
        return new String[]{"for_sch_mux", "for_sch_rc", "for_sch_status", "for_sch_duration", "for_sch_rate", "rev_sch_mux", "rev_sch_rc", "rev_sch_status", "rev_sch_duration", "rev_sch_rate"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int for_sch_mux = getFieldValue(data, "for_sch_mux");
        int for_sch_rc = getFieldValue(data, "for_sch_rc");
        int for_sch_status = getFieldValue(data, "for_sch_status");
        int for_sch_duration = getFieldValue(data, "for_sch_duration");
        int for_sch_rate = getFieldValue(data, "for_sch_rate");
        int rev_sch_mux = getFieldValue(data, "rev_sch_mux");
        int rev_sch_rc = getFieldValue(data, "rev_sch_rc");
        int rev_sch_status = getFieldValue(data, "rev_sch_status");
        int rev_sch_duration = getFieldValue(data, "rev_sch_duration");
        int rev_sch_rate = getFieldValue(data, "rev_sch_rate");
        Elog.d("EmInfo/MDMComponent", "rev_sch_rate = " + rev_sch_rate);
        clearData();
        addData(Integer.valueOf(for_sch_mux));
        addData(Integer.valueOf(for_sch_rc));
        addData(Integer.valueOf(for_sch_status));
        addData(Integer.valueOf(for_sch_duration));
        addData(Integer.valueOf(for_sch_rate));
        addData(Integer.valueOf(rev_sch_mux));
        addData(Integer.valueOf(rev_sch_rc));
        addData(Integer.valueOf(rev_sch_status));
        addData(Integer.valueOf(rev_sch_duration));
        addData(Integer.valueOf(rev_sch_rate));
        notifyDataSetChanged();
    }
}
