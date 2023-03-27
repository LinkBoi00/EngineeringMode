package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class RsrpLteCandidateCellUmtsFddArray extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND};

    public RsrpLteCandidateCellUmtsFddArray(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RSRP (LTE candidate cell)(UMTS FDD)";
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
        return new String[]{"RSRP (LTE)", "Earfcn", "PCI", "RSRP"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        if (getFieldValue(data, "RAT_type") == 2) {
            int num = getFieldValue(data, "neigh_cell_count");
            int i = 0;
            while (i < num && i < 16) {
                int earfcn = getFieldValue(data, "choice.LTE_neigh_cells[" + i + "]." + "earfcn");
                int pci = getFieldValue(data, "choice.LTE_neigh_cells[" + i + "]." + "pci");
                addData(Integer.valueOf(earfcn), Integer.valueOf(pci), Float.valueOf(((float) getFieldValue(data, "choice.LTE_neigh_cells[" + i + "]." + "rsrp", true)) / 4096.0f));
                i++;
            }
        }
    }
}
