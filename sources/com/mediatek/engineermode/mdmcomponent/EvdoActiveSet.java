package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.text.DecimalFormat;

/* compiled from: MDMComponentC2k */
class EvdoActiveSet extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_ACTIVE_SET_INFO_IND};

    public EvdoActiveSet(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO Acitve Set";
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
        return new String[]{"pilotPN", "pilotEclo", "DRC Cover"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        int num_evdo_active_set = getFieldValue(data, "num_evdo_active_set");
        addData("num_evdo_active_set(" + num_evdo_active_set + ")");
        for (int i = 0; i < num_evdo_active_set; i++) {
            int activePilotPN = getFieldValue(data, "evdo_active_set[" + i + "].activePilotPN");
            String activepilotEcloFloat = new DecimalFormat("#0.00").format((double) (((float) getFieldValue(data, "evdo_active_set[" + i + "].activepilotEclo", true)) / 8.0f));
            addData(Integer.valueOf(activePilotPN), activepilotEcloFloat, Integer.valueOf(getFieldValue(data, "evdo_active_set[" + i + "].activeDrcCover")));
        }
    }
}
