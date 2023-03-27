package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class DrxConfiguredUmtsFdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRCE_HSPA_CONFIG_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "FALSE");
            put(1, "TRUE");
        }
    };

    public DrxConfiguredUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "DRX configured (UMTS FDD)";
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
        return new String[]{"DRX configured"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String drxConfig = this.mMapping.get(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_ERRC_HSPA_DRX_CONFIG)));
        clearData();
        addData(drxConfig == null ? "FALSE" : drxConfig);
    }
}
