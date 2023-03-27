package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class CellSupportPsInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_GMM_INFO_IND};

    public CellSupportPsInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Cell Support PS INFO";
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
    public String[] getLabels() {
        return new String[]{"cell support PS"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        clearData();
        addData(Integer.valueOf(getFieldValue((Msg) msg, MDMContent.EM_GMM_CELL_SUPPORT_PS)));
    }
}
