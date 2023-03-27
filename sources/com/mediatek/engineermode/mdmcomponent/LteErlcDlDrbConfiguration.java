package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class LteErlcDlDrbConfiguration extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL2_PUB_STATUS_IND};

    public LteErlcDlDrbConfiguration(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE eRLC DL DRB Congiguration";
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
        return new String[]{"DRB ID", "RB ID", "EPS bearer ID", "Logical channel ID", "RLC mode", "LI length 15 bits"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        for (int i = 0; i < 8; i++) {
            addData(Integer.valueOf(i), Integer.valueOf(getFieldValue(data, "erlc_stats.dl_rb_cfg" + "[" + (i + 2) + "]." + MDMContent.RB_ID)), Integer.valueOf(getFieldValue(data, "erlc_stats.dl_rb_cfg" + "[" + (i + 2) + "]." + MDMContent.EPSB_ID)), Integer.valueOf(getFieldValue(data, "erlc_stats.dl_rb_cfg" + "[" + (i + 2) + "]." + MDMContent.LGCH_ID)), Integer.valueOf(getFieldValue(data, "erlc_stats.dl_rb_cfg" + "[" + (i + 2) + "]." + MDMContent.RLC_MODE)), Integer.valueOf(getFieldValue(data, "erlc_stats.dl_rb_cfg" + "[" + (i + 2) + "]." + MDMContent.LI_LEN_15_BIT)));
        }
    }
}
