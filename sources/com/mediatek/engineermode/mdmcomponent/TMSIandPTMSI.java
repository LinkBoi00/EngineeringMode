package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class TMSIandPTMSI extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_MM_INFO_IND, MDMContent.MSG_ID_EM_GMM_INFO_IND};
    String ptmsi = "-";
    String tmsi = "-";

    public TMSIandPTMSI(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "TMSI and P-TMSI";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "3. UMTS FDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"TMSI", "P-TMSI"};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        if (name.equals(MDMContent.MSG_ID_EM_MM_INFO_IND)) {
            this.tmsi = "";
            for (int i = 0; i < 4; i++) {
                int value = getFieldValue(data, "tmsi[" + i + "]");
                this.tmsi += Integer.toHexString(value) + " ";
            }
        } else if (name.equals(MDMContent.MSG_ID_EM_GMM_INFO_IND)) {
            this.ptmsi = "";
            for (int i2 = 0; i2 < 4; i2++) {
                int value2 = getFieldValue(data, "ptmsi[" + i2 + "]");
                this.ptmsi += Integer.toHexString(value2) + " ";
            }
        }
        addData(this.tmsi, this.ptmsi);
    }
}
