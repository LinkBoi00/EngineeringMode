package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class ERRCState extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_STATE_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(1, "-");
            put(2, "Idle");
            put(3, "Connected");
            put(4, "-");
            put(5, "Idle");
            put(6, "Connected");
        }
    };

    public ERRCState(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "ERRC State";
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
        return new String[]{"RRC State"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        addData(this.mMapping.get(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_ERRC_STATE_ERRC_STS))));
    }
}
