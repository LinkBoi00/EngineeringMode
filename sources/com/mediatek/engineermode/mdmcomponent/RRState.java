package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;
import java.util.Map;

/* compiled from: MDMComponent */
class RRState extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_MEASUREMENT_REPORT_INFO_IND};
    private static final String[] LABELS = {"RR State"};
    private final Map<Integer, String> mRrStateMapping = new HashMap<Integer, String>() {
        {
            put(3, "Idle");
            put(5, "Packet Transfer");
            put(6, "Dedicated");
        }
    };

    public RRState(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RR State";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "2. GSM EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return LABELS;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        addData(this.mRrStateMapping.get(Integer.valueOf(getFieldValue((Msg) msg, "rr_em_measurement_report_info.rr_state"))));
    }
}
