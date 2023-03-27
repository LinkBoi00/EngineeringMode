package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class IntraFrequencyDetectedSetUmtsFdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND};

    public IntraFrequencyDetectedSetUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Intra-frequency detected set (UMTS FDD)";
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
        return new String[]{"UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", MDMContent.FDD_EM_MEME_DCH_UMTS_ECN0};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int num = getFieldValue(data, "num_cells");
        clearData();
        int i = 0;
        while (i < num && i < 32) {
            if (getFieldValue(data, "umts_cell_list[" + i + "]." + "cell_type") == 2) {
                int urafcn = getFieldValue(data, "umts_cell_list[" + i + "]." + "UARFCN");
                int psc = getFieldValue(data, "umts_cell_list[" + i + "]." + MDMContent.FDD_EM_MEME_DCH_UMTS_PSC);
                long rscp = (long) (getFieldValue(data, "umts_cell_list[" + i + "]." + "RSCP", true) / 4096);
                addData(Integer.valueOf(urafcn), Integer.valueOf(psc), Long.valueOf(rscp), Long.valueOf((long) (getFieldValue(data, "umts_cell_list[" + i + "]." + MDMContent.FDD_EM_MEME_DCH_UMTS_ECN0, true) / 4096)));
            }
            i++;
        }
    }
}
