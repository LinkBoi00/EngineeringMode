package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class HsdpaConfiguredUmtsFdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRCE_HSPA_CONFIG_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "FALSE");
            put(1, "TRUE");
        }
    };

    public HsdpaConfiguredUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "HSDPA configured (UMTS FDD)";
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
        return new String[]{"HSDPA configured"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String hsdpa = this.mMapping.get(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_ERRC_HSPA_HSDPA_CONFIG)));
        clearData();
        addData(hsdpa == null ? "FALSE" : hsdpa);
    }
}
