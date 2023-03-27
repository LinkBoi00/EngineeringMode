package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class UmtsRrcMeasurementReportForE3b extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_TDD_EM_MEME_REPORT_INFO_IND};

    public UmtsRrcMeasurementReportForE3b(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "UMTS RRC Measurement report for e3b (UMTS TDD)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "4. UMTS TDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"e3b meas ID"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int measId = getFieldValue(data, "em_report_info." + "MeasId");
        int eventType = getFieldValue(data, "em_report_info." + "EventType");
        clearData();
        if (eventType == 2) {
            if (measId == 0) {
                addData("-");
            } else {
                addData(Integer.valueOf(measId));
            }
        }
        notifyDataSetChanged();
    }
}
