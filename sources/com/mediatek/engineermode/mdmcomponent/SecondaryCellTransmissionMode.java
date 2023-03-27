package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondaryCellTransmissionMode extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public SecondaryCellTransmissionMode(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell Transmission Mode";
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
        return new String[]{"TM"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int value = getFieldValue((Msg) msg, "cell_info[1]." + "tm");
        clearData();
        addData(Integer.valueOf(value));
    }
}
