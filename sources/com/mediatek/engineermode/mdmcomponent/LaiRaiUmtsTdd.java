package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class LaiRaiUmtsTdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_MM_INFO_IND};

    public LaiRaiUmtsTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LAI and RAI (UMTS TDD)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "4. UMTS TDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"PLMN", "LAI", "RAI"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int rac = getFieldValue(data, "rac");
        String plmn = "";
        String loc = "";
        for (int i = 0; i < 3; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(plmn);
            sb.append(getFieldValue(data, "mcc[" + i + "]"));
            sb.append(" ");
            plmn = sb.toString();
        }
        for (int i2 = 0; i2 < 3; i2++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(plmn);
            sb2.append(getFieldValue(data, "mnc[" + i2 + "]"));
            sb2.append(" ");
            plmn = sb2.toString();
        }
        for (int i3 = 0; i3 < 2; i3++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(loc);
            sb3.append(getFieldValue(data, "loc[" + i3 + "]"));
            sb3.append(" ");
            loc = sb3.toString();
        }
        clearData();
        addData(plmn, loc, Integer.valueOf(rac));
    }
}
