package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class LteErrcMeasurementReportForEventB1 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_REPORT_INFO_IND};

    public LteErrcMeasurementReportForEventB1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE ERRC Measurement report for event B1";
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
        return new String[]{"Event B1 meas ID"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        if (getFieldValue(data, "em_report_info." + "EventType") == 1) {
            int measId = getFieldValue(data, "em_report_info." + "MeasId");
            Object[] objArr = new Object[1];
            objArr[0] = measId == 0 ? "-" : Integer.valueOf(measId);
            addData(objArr);
        }
    }
}
