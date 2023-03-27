package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class IntraFrequencyNeighbouringCellInformationLteTdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND};

    public IntraFrequencyNeighbouringCellInformationLteTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Intra-frequency neighbouring cell information (LTE TDD)";
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
        return new String[]{"PCI", "RSCP", MDMContent.FDD_EM_MEME_DCH_UMTS_ECN0};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        int cellNum = getFieldValue(data, "intra_info." + "cell_num");
        int i = 0;
        while (i < cellNum && i < 16) {
            String coNameNew = "intra_info." + MDMContent.EM_ERRC_MOB_MEAS_INTRARAT_INTRA_INFO_INTRA_CELL + "[" + i + "].";
            int valid = getFieldValue(data, coNameNew + "valid");
            int pci = getFieldValue(data, coNameNew + "pci");
            int rsrp = getFieldValue(data, coNameNew + "rsrp", true);
            int rsrq = getFieldValue(data, coNameNew + "rsrq", true);
            Object[] objArr = new Object[3];
            Object obj = "";
            objArr[0] = valid > 0 ? Integer.valueOf(pci) : obj;
            objArr[1] = (valid <= 0 || rsrp == -1) ? obj : Float.valueOf(((float) rsrp) / 4.0f);
            if (valid > 0 && rsrq != -1) {
                obj = Float.valueOf(((float) rsrq) / 4.0f);
            }
            objArr[2] = obj;
            addData(objArr);
            i++;
        }
    }
}
