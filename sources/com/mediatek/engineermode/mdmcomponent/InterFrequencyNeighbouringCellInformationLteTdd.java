package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class InterFrequencyNeighbouringCellInformationLteTdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND};

    public InterFrequencyNeighbouringCellInformationLteTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Inter-frequency neighbouring cell information (LTE TDD)";
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
        return new String[]{"EARFCN", "PCI", "RSCP", MDMContent.FDD_EM_MEME_DCH_UMTS_ECN0};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Object obj;
        Msg data = (Msg) msg;
        clearData();
        String coName = "inter_info.";
        int freqNum = getFieldValue(data, coName + "freq_num");
        int i = 0;
        while (i < freqNum && i < 4) {
            String coNameNew = coName + "inter_freq" + "[" + i + "].";
            int valid = getFieldValue(data, coNameNew + "valid");
            int earfcn = getFieldValue(data, coNameNew + "earfcn");
            int cellNum = getFieldValue(data, coNameNew + "cell_num");
            int j = 0;
            while (j < cellNum && j < 6) {
                String interName = coNameNew + MDMContent.EM_ERRC_MOB_MEAS_INTRARAT_INTER_INFO_INTER_CELL + "[" + j + "].";
                int pci = getFieldValue(data, interName + "pci");
                int rsrp = getFieldValue(data, interName + "rsrp", true);
                StringBuilder sb = new StringBuilder();
                sb.append(interName);
                String coName2 = coName;
                sb.append("rsrq");
                int rsrq = getFieldValue(data, sb.toString(), true);
                Msg data2 = data;
                Object[] objArr = new Object[4];
                Object obj2 = "";
                objArr[0] = valid > 0 ? Integer.valueOf(earfcn) : obj2;
                objArr[1] = valid > 0 ? Integer.valueOf(pci) : obj2;
                if (valid <= 0 || rsrp == -1) {
                    obj = obj2;
                } else {
                    obj = Float.valueOf(((float) rsrp) / 4.0f);
                }
                objArr[2] = obj;
                if (valid > 0 && rsrq != -1) {
                    obj2 = Float.valueOf(((float) rsrq) / 4.0f);
                }
                objArr[3] = obj2;
                addData(objArr);
                j++;
                data = data2;
                coName = coName2;
            }
            i++;
            data = data;
            coName = coName;
        }
        String str = coName;
    }
}
