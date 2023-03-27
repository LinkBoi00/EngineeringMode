package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.text.DecimalFormat;

/* compiled from: MDMComponentC2k */
class EvdoNghdrSet extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_NGHDR_SET_INFO_IND};

    public EvdoNghdrSet(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO Nghdr Set";
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
        return new String[]{"Band", "Channel", "PilotPN", "PilotEclo"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        int num_evdo_nghdr_set = getFieldValue(data, "num_evdo_nghdr_set");
        addData("num_evdo_nghdr_set(" + num_evdo_nghdr_set + ")");
        for (int i = 0; i < num_evdo_nghdr_set; i++) {
            int Band = getFieldValue(data, "evdo_nghdr_set[" + i + "].Band");
            int Channel = getFieldValue(data, "evdo_nghdr_set[" + i + "].Channel");
            int PilotPN = getFieldValue(data, "evdo_nghdr_set[" + i + "].PilotPN");
            addData(Integer.valueOf(Band), Integer.valueOf(Channel), Integer.valueOf(PilotPN), new DecimalFormat("#0.00").format((double) (((float) getFieldValue(data, "evdo_nghdr_set[" + i + "].PilotEclo", true)) / 8.0f)));
        }
    }
}
