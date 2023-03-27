package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class LteErrcMeasurementConfigForEventB1 extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_CONFIG_INFO_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "GSM RSSI");
            put(2, "UMTS RSCP");
            put(3, "UMTS ECN0");
        }
    };

    public LteErrcMeasurementConfigForEventB1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE ERRC Measurement config for event B1";
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
        return new String[]{"Event B1 meas ID", "Other RAT meas quantity", "Other RAT threshold"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int num = getFieldValue(data, "num_event_info");
        clearData();
        int i = 0;
        while (i < num && i < 2) {
            if (getFieldValue(data, "em_event_info[" + i + "]." + "EventType") == 1) {
                int measId = getFieldValue(data, "em_event_info[" + i + "]." + "MeasId");
                int measQtyOtherRat = getFieldValue(data, "em_event_info[" + i + "]." + "MeasQtyOtherRAT");
                int threshold = getFieldValue(data, "em_event_info[" + i + "]." + "ThresholdOtherSystem", true);
                Object[] objArr = new Object[3];
                Object obj = "-";
                objArr[0] = measId == 0 ? obj : Integer.valueOf(measId);
                objArr[1] = this.mMapping.get(Integer.valueOf(measQtyOtherRat));
                if (threshold != 0) {
                    obj = Float.valueOf(((float) threshold) / 4.0f);
                }
                objArr[2] = obj;
                addData(objArr);
            }
            i++;
        }
    }
}
