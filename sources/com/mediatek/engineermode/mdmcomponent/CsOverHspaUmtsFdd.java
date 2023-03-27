package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class CsOverHspaUmtsFdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRCE_CS_OVER_HSPA_STATUS_IND};
    HashMap<Boolean, String> mMapping = new HashMap<Boolean, String>() {
        {
            put(false, "OFF");
            put(true, "ON");
        }
    };

    public CsOverHspaUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "CS over HSPA (UMTS FDD)";
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
        return new String[]{"CS over HSPA"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String status = this.mMapping.get(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_RRCE_CS_OVER_HSPA_CS_OVER_HSPA_STATUS)));
        clearData();
        addData(status == null ? "FALSE" : status);
    }
}
