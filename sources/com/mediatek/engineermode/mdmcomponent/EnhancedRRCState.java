package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class EnhancedRRCState extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_URR_3G_GENERAL_STATUS_IND};
    private final HashMap<Integer, String> mStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "N/A");
            put(1, "N/A");
            put(2, "N/A");
            put(3, "N/A");
            put(4, "N/A");
            put(5, "N/A");
            put(6, "");
            put(7, "HSDPA in Cell FACH");
            put(8, "HSDPA in Cell PCH:Receive Data");
            put(9, "HSDPA in Cell PCH:Receive Paging");
            put(10, "HSDPA in URA PCH:Receive Paging");
            put(11, "HSUPA in Cell FACH");
            put(12, "HSUPA in Cell PCH:Receive Data");
            put(13, "READY for HSPA in Cell PCH");
        }
    };

    public EnhancedRRCState(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Enhanced RRC State";
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
        return new String[]{"Enhanced RRC State"};
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
