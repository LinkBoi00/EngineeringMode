package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class PrimaryCellOsSnr extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public PrimaryCellOsSnr(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Primary Cell OS SNR";
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
        return new String[]{"SINR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int osSnr = getFieldValue((Msg) msg, "dl_info[0]." + MDMContent.EM_EL1_STATUS_DL_INFO_SINR, true);
        clearData();
        addData(Integer.valueOf(osSnr));
    }
}
