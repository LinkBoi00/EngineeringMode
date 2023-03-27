package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondHsDschServingCell extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_UL1_HSPA_INFO_GROUP_IND};

    public SecondHsDschServingCell(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "2nd HS-DSCH Serving Cell";
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
        return new String[]{"Dual Cell", "Freq", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "64 QAM"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int dcOn = getFieldValue(data, "secondary_hs_dsch_configuration_status." + MDMContent.FDD_EM_UL1_HSPA_PRIMARY_SEC_HS_DSCH_CONFIG_STATUS_DC_ON);
        int freq = getFieldValue(data, "secondary_hs_dsch_configuration_status." + MDMContent.FDD_EM_UL1_HSPA_PRIMARY_SEC_HS_DSCH_CONFIG_STATUS_DL_FREQ);
        int psc = getFieldValue(data, "secondary_hs_dsch_configuration_status." + "psc");
        int dlOn = getFieldValue(data, "secondary_hs_dsch_configuration_status." + MDMContent.FDD_EM_UL1_HSPA_PRIMARY_SEC_HS_DSCH_CONFIG_STATUS_DL_64QAM_ON);
        clearData();
        int i = 1;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(dcOn > 0 ? 1 : 0);
        addData(objArr);
        addData(Integer.valueOf(freq));
        addData(Integer.valueOf(psc));
        Object[] objArr2 = new Object[1];
        if (dlOn <= 0) {
            i = 0;
        }
        objArr2[0] = Integer.valueOf(i);
        addData(objArr2);
    }
}
