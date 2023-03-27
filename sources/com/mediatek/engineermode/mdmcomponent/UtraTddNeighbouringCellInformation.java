package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class UtraTddNeighbouringCellInformation extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTERRAT_UTRAN_INFO_IND};

    public UtraTddNeighbouringCellInformation(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "UTRA-TDD neighbouring cell information";
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
        return new String[]{"UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", "EcN0"};
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
        int freqNum = getFieldValue(data, "freq_num");
        Elog.d("EmInfo/MDMComponent", " freqNum: " + freqNum);
        int i = 0;
        while (i < freqNum && i < 16) {
            int valid = getFieldValue(data, "inter_freq[" + i + "]." + "valid");
            int uarfcn = getFieldValue(data, "inter_freq[" + i + "]." + "uarfcn");
            int cellNum = getFieldValue(data, "inter_freq[" + i + "]." + MDMContent.ERRC_MOB_MEAS_INTERRAT_UTRAN_UCELL_NUM);
            String secName = "inter_freq[" + i + "]." + MDMContent.ERRC_MOB_MEAS_INTERRAT_UTRAN_UCELL + "[";
            int j = 0;
            while (j < cellNum && j < 6) {
                int valid2 = getFieldValue(data, secName + j + "]." + "valid");
                int psc = getFieldValue(data, secName + j + "]." + "psc");
                int rscp = getFieldValue(data, secName + j + "]." + "rscp");
                StringBuilder sb = new StringBuilder();
                sb.append(secName);
                sb.append(j);
                sb.append("].");
                int freqNum2 = freqNum;
                sb.append(MDMContent.ERRC_MOB_MEAS_INTERRAT_UTRAN_UCELL_EC_N0);
                int ecn0 = getFieldValue(data, sb.toString());
                Object[] objArr = new Object[4];
                Object obj2 = "";
                objArr[0] = valid > 0 ? Integer.valueOf(uarfcn) : obj2;
                objArr[1] = valid2 > 0 ? Integer.valueOf(psc) : obj2;
                Msg data2 = data;
                if (valid2 <= 0 || rscp == -1) {
                    obj = obj2;
                } else {
                    obj = Float.valueOf(((float) rscp) / 4.0f);
                }
                objArr[2] = obj;
                if (valid2 > 0 && ecn0 != -1) {
                    obj2 = Float.valueOf(((float) ecn0) / 4.0f);
                }
                objArr[3] = obj2;
                addData(objArr);
                j++;
                freqNum = freqNum2;
                data = data2;
            }
            i++;
            freqNum = freqNum;
            data = data;
        }
        int i2 = freqNum;
    }
}
