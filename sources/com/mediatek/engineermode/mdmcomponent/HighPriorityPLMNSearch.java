package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class HighPriorityPLMNSearch extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_NWSEL_PLMN_INFO_IND};

    public HighPriorityPLMNSearch(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "High Priority PLMN Search";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "1. General EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"High Priority PLMN Search"};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int value = getFieldValue((Msg) msg, MDMContent.IS_HIGHER_PRI_PLMN_SRCH);
        clearData();
        addData(Integer.valueOf(value));
    }
}
