package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class Tai extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EMM_PLMNSEL_INFO_IND};

    public Tai(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "TAI";
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
        return new String[]{"PLMN", "TAC"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        for (int i = 0; i < 1; i++) {
            int mcc1 = getFieldValue(data, "selectedPlmn." + "mcc" + 1);
            int mcc2 = getFieldValue(data, "selectedPlmn." + "mcc" + 2);
            int mcc3 = getFieldValue(data, "selectedPlmn." + "mcc" + 3);
            int mnc1 = getFieldValue(data, "selectedPlmn." + "mnc" + 1);
            int mnc2 = getFieldValue(data, "selectedPlmn." + "mnc" + 2);
            int mnc3 = getFieldValue(data, "selectedPlmn." + "mnc" + 3);
            int tac = getFieldValue(data, MDMContent.EM_EMM_L4C_PLMNSEL_PARA__MNC);
            if (mcc1 == 15 && mcc2 == 15 && mcc3 == 15 && mnc1 == 15 && mnc2 == 15 && mnc3 == 15) {
                addData("-");
            } else {
                StringBuilder sb = new StringBuilder();
                String str = "";
                sb.append(str);
                sb.append(mcc1);
                sb.append(mcc2);
                sb.append(mcc3);
                sb.append(mnc1);
                sb.append(mnc2);
                Object obj = str;
                if (mnc3 != 15) {
                    obj = Integer.valueOf(mnc3);
                }
                sb.append(obj);
                addData(sb.toString());
            }
            if (tac == 65534 || tac == 0) {
                addData("-");
            } else {
                addData(String.format("0x%X", new Object[]{Integer.valueOf(tac)}));
            }
        }
    }
}
