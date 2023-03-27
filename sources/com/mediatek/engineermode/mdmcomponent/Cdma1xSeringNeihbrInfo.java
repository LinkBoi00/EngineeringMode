package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponentC2k */
class Cdma1xSeringNeihbrInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_SERVING_NEIGHBR_SET_INFO_IND};

    public Cdma1xSeringNeihbrInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT Serving/Neighbr info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "7. CDMA EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"pn", "ecio", "phase"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        float f;
        Msg data = (Msg) msg;
        int[] value = new int[3];
        clearData();
        int num_in_active_set = getFieldValue(data, "num_in_active_set");
        int num_candidate_set = getFieldValue(data, "num_candidate_set");
        int num_neighbor_set = getFieldValue(data, "num_neighbor_set");
        addData("in_active_set(" + num_in_active_set + ")");
        int i = 0;
        while (true) {
            f = 2.0f;
            if (i >= num_in_active_set) {
                break;
            }
            value[0] = getFieldValue(data, "in_active_set[" + i + "].pilot_pn");
            value[1] = getFieldValue(data, "in_active_set[" + i + "].pilot_ecio");
            value[2] = getFieldValue(data, "in_active_set[" + i + "].pilot_phase");
            addData(Integer.valueOf(value[0]), Float.valueOf(-(((float) value[1]) / 2.0f)), Integer.valueOf(value[2]));
            i++;
        }
        addData("candidate_set(" + num_candidate_set + ")");
        int i2 = 0;
        while (i2 < num_candidate_set) {
            value[0] = getFieldValue(data, "candidate_set[" + i2 + "].pilot_pn");
            value[1] = getFieldValue(data, "candidate_set[" + i2 + "].pilot_ecio");
            value[2] = getFieldValue(data, "candidate_set[" + i2 + "].pilot_phase");
            addData(Integer.valueOf(value[0]), Float.valueOf(-(((float) value[1]) / f)), Integer.valueOf(value[2]));
            i2++;
            f = 2.0f;
        }
        addData("neighbor_set(" + num_neighbor_set + ")");
        for (int i3 = 0; i3 < num_neighbor_set; i3++) {
            value[0] = getFieldValue(data, "neighbor_set[" + i3 + "].pilot_pn");
            value[1] = getFieldValue(data, "neighbor_set[" + i3 + "].pilot_ecio");
            value[2] = getFieldValue(data, "neighbor_set[" + i3 + "].pilot_phase");
            addData(Integer.valueOf(value[0]), Float.valueOf(-(((float) value[1]) / 2.0f)), Integer.valueOf(value[2]));
        }
        Elog.d("EmInfo/MDMComponent", "num_neighbor_set = " + num_neighbor_set);
    }
}
