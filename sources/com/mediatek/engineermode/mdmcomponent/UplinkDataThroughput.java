package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class UplinkDataThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_UPCM_PS_TPUT_INFO_IND};

    public UplinkDataThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Uplink Data Throughput";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "1. General EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"IP Rate UL (bytes/s)"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        addData(Long.valueOf((long) getFieldValue((Msg) msg, MDMContent.EM_UPCM_PS_TPUT_TOTAL_TX_BYTE_PER_SECOND)));
    }
}
