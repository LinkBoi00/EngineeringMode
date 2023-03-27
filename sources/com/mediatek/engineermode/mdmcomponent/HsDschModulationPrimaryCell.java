package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class HsDschModulationPrimaryCell extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_UL1_HSPA_INFO_GROUP_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "QPSK (0)");
            put(1, "16QAM (1)");
            put(2, "64QAM (2)");
        }
    };

    public HsDschModulationPrimaryCell(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "HS-DSCH Modulation (Primary Cell)";
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
        return new String[]{"Modulation (Pri)"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int mod = getFieldValue((Msg) msg, "primary_hs_dsch_bler." + MDMContent.FDD_EM_UL1_HSPA_DSCH_CURR_MOD);
        clearData();
        addData(this.mMapping.get(Integer.valueOf(mod)));
    }
}
