package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class LteErrcMeasurementConfigForEventB2 extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_CONFIG_INFO_IND};
    HashMap<Integer, String> mMeasQtyMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "LTE RSRP");
            put(2, "LTE RSRQ");
        }
    };
    HashMap<Integer, String> mMeasQtyOtherMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "GSM RSSI");
            put(2, "UMTS RSCP");
            put(3, "UMTS ECN0");
        }
    };

    public LteErrcMeasurementConfigForEventB2(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE ERRC Measurement config for event B2";
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
        return new String[]{"Event B2 meas ID", "LTE RAT meas quantity", "Other RAT meas quantity", "LTE RAT threshold", "Other RAT threshold"};
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
            if (getFieldValue(data, "em_event_info[" + i + "]." + "EventType") == 2) {
                int measId = getFieldValue(data, "em_event_info[" + i + "]." + "MeasId");
                int measQty = getFieldValue(data, "em_event_info[" + i + "]." + "MeasQty");
                int measQtyOtherRat = getFieldValue(data, "em_event_info[" + i + "]." + "MeasQtyOtherRAT");
                int threshold = getFieldValue(data, "em_event_info[" + i + "]." + "ThresholdOwnSystem", true);
                int thresholdOtherSystem = getFieldValue(data, "em_event_info[" + i + "]." + "ThresholdOtherSystem", true);
                Object[] objArr = new Object[5];
                Object obj = "-";
                objArr[0] = measId == 0 ? obj : Integer.valueOf(measId);
                objArr[1] = this.mMeasQtyMapping.get(Integer.valueOf(measQty));
                objArr[2] = this.mMeasQtyOtherMapping.get(Integer.valueOf(measQtyOtherRat));
                objArr[3] = threshold == 0 ? obj : Float.valueOf(((float) threshold) / 4.0f);
                if (thresholdOtherSystem != 0) {
                    obj = Float.valueOf(((float) thresholdOtherSystem) / 4.0f);
                }
                objArr[4] = obj;
                addData(objArr);
            }
            i++;
        }
    }
}
