package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class UmtsRrcMeasurementControlForE3a extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_TDD_EM_MEME_EVENT_TYPE_3_PARAMETER_INFO_IND};
    HashMap<Integer, String> mQtyMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "UMTS RSCP");
            put(2, "UMTS ECN0");
            put(3, "UMTS PATHLOSS");
        }
    };
    HashMap<Integer, String> mQtyOtherRatMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "GSM RSSI");
            put(2, "LTE RSRP");
            put(3, "LTE RSRQ");
        }
    };

    public UmtsRrcMeasurementControlForE3a(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "UMTS RRC Measurement control for e3a (UMTS TDD)";
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
        return new String[]{"e3a meas ID", "UMTS RAT meas quantity", "Other RAT meas quantity", "UMTS RAT threshold", "Other RAT threshold"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int num = getFieldValue(data, "num_event_info");
        clearData();
        int i = 0;
        while (i < num && i < 4) {
            int measId = getFieldValue(data, "em_event_info[" + i + "]." + "MeasId");
            int eventType = getFieldValue(data, "em_event_info[" + i + "]." + "EventType");
            int measQty = getFieldValue(data, "em_event_info[" + i + "]." + "MeasQty");
            int measQtyOtherRAT = getFieldValue(data, "em_event_info[" + i + "]." + "MeasQtyOtherRAT");
            int thresholdOwnSystem = getFieldValue(data, "em_event_info[" + i + "]." + "ThresholdOwnSystem", true);
            int thresholdOtherSystem = getFieldValue(data, "em_event_info[" + i + "]." + "ThresholdOtherSystem", true);
            if (eventType == 1) {
                if (measId == 0) {
                    addData("-");
                } else {
                    addData(Integer.valueOf(measId));
                }
                addData(this.mQtyMapping.get(Integer.valueOf(measQty)));
                addData(this.mQtyOtherRatMapping.get(Integer.valueOf(measQtyOtherRAT)));
                if (thresholdOwnSystem == 65535) {
                    addData("-");
                } else {
                    addData(Float.valueOf(((float) thresholdOwnSystem) / 4.0f));
                }
                if (thresholdOtherSystem == 65535) {
                    addData("-");
                } else {
                    addData(Float.valueOf(((float) thresholdOtherSystem) / 4.0f));
                }
            }
            i++;
        }
        notifyDataSetChanged();
    }
}
