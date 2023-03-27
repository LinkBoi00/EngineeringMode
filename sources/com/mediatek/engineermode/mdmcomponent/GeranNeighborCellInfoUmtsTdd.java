package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class GeranNeighborCellInfoUmtsTdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_TDD_EM_MEME_DCH_GSM_CELL_INFO_IND, MDMContent.MSG_ID_TDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "dcs1800");
            put(1, "pcs1900");
        }
    };

    public GeranNeighborCellInfoUmtsTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "GERAN Neighbor Cell Info (UMTS TDD)";
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
        return new String[]{"BSIC", "Frequency band", "BCCH ARFCN"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        Msg data = (Msg) msg;
        String str2 = "";
        if (str.equals(MDMContent.MSG_ID_TDD_EM_MEME_DCH_GSM_CELL_INFO_IND)) {
            String coName = "gsm_cell_list";
            int num = getFieldValue(data, "num_cells");
            clearData();
            int i = 0;
            while (i < num && i < 6) {
                int band = getFieldValue(data, coName + "[" + i + "]." + MDMContent.EM_MEME_DCH_GSM_CELL_LIST_FREQUENCY_BAND);
                int arfcn = getFieldValue(data, coName + "[" + i + "]." + "arfcn");
                int bsic = getFieldValue(data, coName + "[" + i + "]." + "bsic");
                String coName2 = coName;
                int bcc = bsic & 7;
                String str3 = str2;
                int ncc = (bsic >> 3) & 7;
                int i2 = bsic;
                int num2 = num;
                String bandString = this.mMapping.get(Integer.valueOf(band));
                int i3 = band;
                Object[] objArr = new Object[3];
                objArr[0] = "bcc: " + bcc + " ncc: " + ncc;
                objArr[1] = bandString == null ? str3 : bandString;
                objArr[2] = String.valueOf(arfcn);
                addData(objArr);
                i++;
                coName = coName2;
                str2 = str3;
                num = num2;
            }
            int i4 = num;
            Msg msg2 = data;
            return;
        }
        String str4 = str2;
        if (str.equals(MDMContent.MSG_ID_TDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND)) {
            String coName3 = "choice.GSM_neigh_cells";
            int num3 = getFieldValue(data, "neigh_cell_count");
            clearData();
            int i5 = 0;
            while (i5 < num3 && i5 < 16) {
                int arfcn2 = getFieldValue(data, coName3 + "[" + i5 + "]." + "arfcn");
                int bsic2 = getFieldValue(data, coName3 + "[" + i5 + "]." + "bsic");
                Msg data2 = data;
                String coName4 = coName3;
                String bandString2 = this.mMapping.get(Integer.valueOf(getFieldValue(data, coName3 + "[" + i5 + "]." + MDMContent.EM_CSCE_NEIGH_CELL_CHOICE_GSM_NEIGH_CELLS_FREQ_BAND)));
                int num4 = num3;
                Object[] objArr2 = new Object[3];
                objArr2[0] = "bcc: " + (bsic2 & 7) + " ncc: " + ((bsic2 >> 3) & 7);
                objArr2[1] = bandString2 == null ? str4 : bandString2;
                objArr2[2] = String.valueOf(arfcn2);
                addData(objArr2);
                i5++;
                String str5 = name;
                num3 = num4;
                data = data2;
                coName3 = coName4;
            }
            String str6 = coName3;
            int i6 = num3;
            return;
        }
    }
}
