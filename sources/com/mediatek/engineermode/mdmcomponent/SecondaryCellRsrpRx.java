package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondaryCellRsrpRx extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public SecondaryCellRsrpRx(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell RSRP RX 0/1";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "5. LTE EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"RSRP[0]", "RSRP[1]"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int rsrp0 = getFieldValue(data, "dl_info[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_DL_RSRP + "[0]", true);
        int rsrp1 = getFieldValue(data, "dl_info[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_DL_RSRP + "[1]", true);
        clearData();
        addData(Integer.valueOf(rsrp0));
        addData(Integer.valueOf(rsrp1));
    }
}
