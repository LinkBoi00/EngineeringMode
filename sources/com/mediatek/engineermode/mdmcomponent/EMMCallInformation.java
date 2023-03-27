package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class EMMCallInformation extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EMM_CALL_INFO_IND};

    public EMMCallInformation(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EMM Call information";
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
        return new String[]{"Bearer active", "PS instance", "CS instance", "SMS establishment trigger", "Keep SMS establishment trigger", "Paging trigger", "Re-establishment request", "Call type(SR/EXSR)", "Establishment type(R11)", "RRC Establishment cause(R12)", "RRC Establishment type(R12)", "# of ESM msg's", "# of SMS msg's", "CS MO Trigger", "CS MT Trigger"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        Msg msg2 = data;
        addData(Integer.valueOf(getFieldValue(data, MDMContent.IS_BEARER_ACTIVE)), Integer.valueOf(getFieldValue(data, MDMContent.IS_PS_MO_MT_INSTANCE)), Integer.valueOf(getFieldValue(data, MDMContent.IS_CS_MO_MT_INSTANCE)), Integer.valueOf(getFieldValue(data, MDMContent.IS_SMS_EST_TRIGGER)), Integer.valueOf(getFieldValue(data, MDMContent.IS_KEEP_SMS_EST)), Integer.valueOf(getFieldValue(data, MDMContent.PAGE_INDE_FLG)), Integer.valueOf(getFieldValue(data, MDMContent.REEST_REQ_FLG)), Integer.valueOf(getFieldValue(data, MDMContent.CALL_TYPE)), Integer.valueOf(getFieldValue(data, MDMContent.EST_CAUSE)), Integer.valueOf(getFieldValue(data, MDMContent.ESTABLISHMENT_CAUSE)), Integer.valueOf(getFieldValue(data, MDMContent.ESTABLISHMENT_TYPE)), Integer.valueOf(getFieldValue(data, MDMContent.WAIT_SND_ESM_MSG_NUM)), Integer.valueOf(getFieldValue(data, MDMContent.WAIT_SND_SMS_MSG_NUM)), Integer.valueOf(getFieldValue(data, MDMContent.CS_MO_TRIGGER)), Integer.valueOf(getFieldValue(data, MDMContent.CS_MT_TRIGGER)));
    }
}
