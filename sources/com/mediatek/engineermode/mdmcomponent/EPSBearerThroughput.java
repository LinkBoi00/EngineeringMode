package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class EPSBearerThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_UPCM_STATUS_IND};

    public EPSBearerThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EPS Bearer Throughput";
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
        String[] labels = new String[38];
        for (int i = 0; i <= 36; i += 2) {
            labels[i] = "EPSB[" + ((i / 2) + 5) + "] UL throughput";
            labels[i + 1] = "EPSB[" + ((i / 2) + 5) + "] DL throughput";
        }
        return labels;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        for (int i = 5; i <= 23; i++) {
            int ul_bytes = getFieldValue(data, "upcm.epsb[" + i + "].ul_bytes");
            addData(Integer.valueOf(ul_bytes + getFieldValue(data, "upcm.epsb[" + i + "].ul_bytes_pri")));
            addData(Integer.valueOf(getFieldValue(data, "upcm.epsb[" + i + "].dl_bytes")));
        }
    }
}
