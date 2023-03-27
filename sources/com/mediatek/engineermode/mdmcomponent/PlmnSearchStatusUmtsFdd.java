package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class PlmnSearchStatusUmtsFdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_URR_3G_GENERAL_STATUS_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "");
            put(1, "Search any PLMN");
            put(2, "Search given PLMN");
            put(3, "Search any PLMN success");
            put(4, "Search any PLMN failure");
            put(5, "Search given PLMN success");
            put(6, "Search given PLMN failure");
            put(7, "Search PLMN abort");
        }
    };

    public PlmnSearchStatusUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "PLMN Search status (UMTS FDD)";
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
        return new String[]{"PLMN search status"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        HashMap<Integer, String> hashMap = this.mMapping;
        addData(hashMap.get(Integer.valueOf(getFieldValue((Msg) msg, "uas_3g_general_status." + MDMContent.FDD_EM_URR_3G_GENERAL_PLMN_SEARCH_STATUS))));
    }
}
