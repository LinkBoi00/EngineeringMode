package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class TddServingCellInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_TDD_EM_CSCE_SERV_CELL_S_STATUS_IND, MDMContent.MSG_ID_TDD_EM_MEME_DCH_UMTS_CELL_INFO_IND};

    public TddServingCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "TD-SCDMA Serving Cell Info (UMTS TDD)";
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
        return new String[]{"UARFCN", "CellParamId", "RSCP"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        Msg data = (Msg) msg;
        clearData();
        if (str.equals(MDMContent.MSG_ID_TDD_EM_CSCE_SERV_CELL_S_STATUS_IND)) {
            addData(Integer.valueOf(getFieldValue(data, "serv_cell." + "uarfcn_DL")), Integer.valueOf(getFieldValue(data, "serv_cell." + "psc")), Float.valueOf(((float) getFieldValue(data, "serv_cell." + "rscp", true)) / 4096.0f));
        } else if (str.equals(MDMContent.MSG_ID_TDD_EM_MEME_DCH_UMTS_CELL_INFO_IND)) {
            for (int i = 0; i < 64; i++) {
                String coName = "umts_cell_list[" + i + "].";
                int uarfcn = getFieldValue(data, coName + "UARFCN");
                int cellParaId = getFieldValue(data, coName + MDMContent.EM_MEME_DCH_UMTS_CELL_LIST_CELLPARAID);
                int rscp = getFieldValue(data, coName + "RSCP", true);
                if (getFieldValue(data, coName + MDMContent.EM_MEME_DCH_UMTS_CELL_LIST_IS_SERVING_CELL) != 0 && rscp > -120) {
                    addData(Integer.valueOf(uarfcn), Integer.valueOf(cellParaId), Integer.valueOf(rscp));
                }
            }
        }
    }
}
