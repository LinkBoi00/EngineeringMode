package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class EUtranNeighborCellInfoUmtsTdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_TDD_EM_MEME_DCH_LTE_CELL_INFO_IND, MDMContent.MSG_ID_TDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND};

    public EUtranNeighborCellInfoUmtsTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "E-UTRAN Neighbor Cell Info (UMTS TDD)";
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
        return new String[]{"EARFCN", "PCI", "RSRP", "RSRQ"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        Msg data = (Msg) msg;
        int i = 4;
        if (str.equals(MDMContent.MSG_ID_TDD_EM_MEME_DCH_LTE_CELL_INFO_IND)) {
            int num = getFieldValue(data, "num_cells");
            clearData();
            int i2 = 0;
            while (i2 < num && i2 < 32) {
                int arfcn = getFieldValue(data, "lte_cell_list" + "[" + i2 + "]." + "EARFCN");
                int pci = getFieldValue(data, "lte_cell_list" + "[" + i2 + "]." + "PCI");
                int rsrp = getFieldValue(data, "lte_cell_list" + "[" + i2 + "]." + "RSRP", true);
                addData(Integer.valueOf(arfcn), Integer.valueOf(pci), Integer.valueOf(rsrp), Integer.valueOf(getFieldValue(data, "lte_cell_list" + "[" + i2 + "]." + "RSRQ", true)));
                i2++;
            }
        } else if (str.equals(MDMContent.MSG_ID_TDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND)) {
            int num2 = getFieldValue(data, "neigh_cell_count");
            if (getFieldValue(data, "RAT_type") == 2) {
                clearData();
                int i3 = 0;
                while (i3 < num2 && i3 < 16) {
                    int arfcn2 = getFieldValue(data, "choice.LTE_neigh_cells" + "[" + i3 + "]." + "earfcn");
                    int pci2 = getFieldValue(data, "choice.LTE_neigh_cells" + "[" + i3 + "]." + "pci");
                    int rsrp2 = getFieldValue(data, "choice.LTE_neigh_cells" + "[" + i3 + "]." + "rsrp", true);
                    int rsrq = getFieldValue(data, "choice.LTE_neigh_cells" + "[" + i3 + "]." + "rsrq", true);
                    Object[] objArr = new Object[i];
                    objArr[0] = Integer.valueOf(arfcn2);
                    objArr[1] = Integer.valueOf(pci2);
                    objArr[2] = Float.valueOf(((float) rsrp2) / 4.0f);
                    objArr[3] = Float.valueOf(((float) rsrq) / 4.0f);
                    addData(objArr);
                    i3++;
                    i = 4;
                }
            }
        }
    }
}
