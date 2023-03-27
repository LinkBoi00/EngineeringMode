package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class RRCState extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_URR_3G_GENERAL_STATUS_IND};
    private final HashMap<Integer, String> mStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "Idle");
            put(1, "Cell FACH");
            put(2, "Cell PCH");
            put(3, "URA PCH");
            put(4, "Cell DCH");
            put(5, "");
            put(6, "NO_CHANGE");
            put(7, "Cell FACH");
            put(8, "Cell PCH");
            put(9, "Cell PCH");
            put(10, "URA PCH");
            put(11, "Cell FACH");
            put(12, "Cell PCH");
            put(13, "Cell PCH");
        }
    };

    public RRCState(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RRC State";
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
        return new String[]{"RRC State"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        int state = getFieldValue((Msg) msg, "uas_3g_general_status." + "umts_rrc_state");
        if (state != 6) {
            clearData();
            addData(this.mStateMapping.get(Integer.valueOf(state)));
        }
    }
}
