package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class FastDormancyConfiguration extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRCE_FD_CONFIGURATION_STATUS_IND};
    HashMap<Boolean, String> mMapping = new HashMap<Boolean, String>() {
        {
            put(false, "OFF");
            put(true, "ON");
        }
    };

    public FastDormancyConfiguration(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Fast Dormancy Configuration";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "3. UMTS FDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"Fast Dormancy configured"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String value = this.mMapping.get(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_RRCE_FD_CONFIGURATION_FDCFGSTATUS)));
        clearData();
        addData(value == null ? "OFF" : value);
    }
}
