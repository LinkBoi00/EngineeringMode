package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class RFCalibrationStatusCheck extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_MMRF_CDDC_INFO_IND};

    public RFCalibrationStatusCheck(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RF Calibration Status Check";
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
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"The Result:"};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        char[] cddc_info = new char[512];
        for (int i = 0; i < 512; i++) {
            cddc_info[i] = (char) getFieldValue(data, "cddc_info[" + i + "]");
        }
        String cddc_info_s = new String(cddc_info);
        clearData();
        addData(new String[]{cddc_info_s.replace("\\n", "\n")});
    }
}
