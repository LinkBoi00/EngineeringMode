package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class RejectCauseCode extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_MM_INFO_IND, MDMContent.MSG_ID_EM_GMM_INFO_IND};
    int attach_rej_cause;
    int mm_cause;
    int rau_rej_cause;

    public RejectCauseCode(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Reject Cause Code";
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
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"MM Reject Cause Code", "GMM Attach Rejec Cause Code", "GMM RAU Reject Cause"};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        if (name.equals(MDMContent.MSG_ID_EM_MM_INFO_IND)) {
            this.mm_cause = getFieldValue(data, MDMContent.EM_MM_CAUSE);
        } else if (name.equals(MDMContent.MSG_ID_EM_GMM_INFO_IND)) {
            this.attach_rej_cause = getFieldValue(data, MDMContent.EM_MM_ATTACH_REJ_CAUSE);
            this.rau_rej_cause = getFieldValue(data, MDMContent.EM_MM_RAU_REJ_CAUSE);
        }
        addData(Integer.valueOf(this.mm_cause), Integer.valueOf(this.attach_rej_cause), Integer.valueOf(this.rau_rej_cause));
    }
}
