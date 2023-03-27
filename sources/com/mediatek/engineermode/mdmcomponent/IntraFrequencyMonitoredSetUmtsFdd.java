package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.Arrays;

/* compiled from: MDMComponent */
class IntraFrequencyMonitoredSetUmtsFdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND};

    public IntraFrequencyMonitoredSetUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Monitored set Information(UMTS FDD)";
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
        return new String[]{"Monitored set Information: "};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data;
        String coName;
        int num_cells;
        int rac;
        String str;
        Msg data2 = (Msg) msg;
        int num_cells2 = getFieldValue(data2, "num_cells");
        clearData();
        int num_monitored = 0;
        if (name.equals(MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND)) {
            String coName2 = "umts_cell_list[";
            int i = 0;
            while (i < num_cells2) {
                int cellType = getFieldValue(data2, coName2 + i + "]." + "cell_type");
                StringBuilder sb = new StringBuilder();
                sb.append("cellType = ");
                sb.append(cellType);
                Elog.d("EmInfo/MDMComponent", sb.toString());
                if (cellType == 1) {
                    int uarfcn = getFieldValue(data2, coName2 + i + "]." + "UARFCN");
                    int psc = getFieldValue(data2, coName2 + i + "]." + MDMContent.FDD_EM_MEME_DCH_UMTS_PSC);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(coName2);
                    sb2.append(i);
                    sb2.append("].");
                    sb2.append(MDMContent.FDD_EM_MEME_DCH_UMTS_ECN0);
                    long rscp = (long) (getFieldValue(data2, coName2 + i + "]." + "RSCP", true) / 4096);
                    long ecn0 = (long) (getFieldValue(data2, sb2.toString(), true) / 4096);
                    int lac = getFieldValue(data2, coName2 + i + "]." + "lac");
                    num_cells = num_cells2;
                    int rac2 = getFieldValue(data2, coName2 + i + "]." + "rac");
                    int i2 = cellType;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(coName2);
                    sb3.append(i);
                    sb3.append("].");
                    long ecn02 = ecn0;
                    sb3.append(MDMContent.FDD_EM_MEME_DCH_UMTS_CELL_IDENTITY);
                    int cell_id = getFieldValue(data2, sb3.toString());
                    int num_plmn_id = getFieldValue(data2, coName2 + i + "]." + MDMContent.FDD_EM_MEME_DCH_UMTS_NUM_PLMN_ID);
                    String[] plmn_a = new String[num_plmn_id];
                    int cell_id2 = cell_id;
                    int j = 0;
                    while (true) {
                        rac = rac2;
                        str = "[";
                        if (j >= num_plmn_id) {
                            break;
                        }
                        int num_plmn_id2 = num_plmn_id;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(coName2);
                        sb4.append(i);
                        sb4.append("].");
                        int lac2 = lac;
                        sb4.append(MDMContent.FDD_EM_MEME_DCH_UMTS_PLMN_ID_LIST);
                        sb4.append(str);
                        sb4.append(j);
                        sb4.append("].");
                        String secName = sb4.toString();
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(secName);
                        int psc2 = psc;
                        sb5.append("mcc");
                        String plmn = "" + getFieldValue(data2, sb5.toString());
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(secName);
                        String str2 = secName;
                        sb6.append("mnc");
                        int value = getFieldValue(data2, sb6.toString());
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(plmn);
                        String str3 = plmn;
                        sb7.append(String.format("%02d", new Object[]{Integer.valueOf(value)}));
                        plmn_a[j] = sb7.toString();
                        j++;
                        rac2 = rac;
                        num_plmn_id = num_plmn_id2;
                        lac = lac2;
                        psc = psc2;
                    }
                    int lac3 = lac;
                    int psc3 = psc;
                    int i3 = num_plmn_id;
                    int num_ura_id = getFieldValue(data2, coName2 + i + "]." + "num_ura_id");
                    int[] ura = new int[num_ura_id];
                    int j2 = 0;
                    while (j2 < num_ura_id) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(coName2);
                        sb8.append(i);
                        sb8.append("].");
                        String coName3 = coName2;
                        sb8.append("uraIdentity");
                        sb8.append(str);
                        sb8.append(j2);
                        sb8.append("].");
                        String secName2 = sb8.toString();
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(secName2);
                        String str4 = str;
                        sb9.append("stringData");
                        int num_ura_id2 = num_ura_id;
                        sb9.append("[0]");
                        int stringData0 = getFieldValue(data2, sb9.toString());
                        int stringData1 = getFieldValue(data2, secName2 + "stringData" + "[1]");
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("stringData0 = ");
                        sb10.append(stringData0);
                        Elog.d("EmInfo/MDMComponent", sb10.toString());
                        Elog.d("EmInfo/MDMComponent", "stringData1 = " + stringData1);
                        ura[j2] = (stringData0 << 8) | stringData1;
                        j2++;
                        coName2 = coName3;
                        str = str4;
                        num_ura_id = num_ura_id2;
                        data2 = data2;
                    }
                    data = data2;
                    coName = coName2;
                    int i4 = num_ura_id;
                    addData("ActiveSetUmtsFdd(" + num_monitored + "): ");
                    num_monitored++;
                    addData("uarfcn", "psc", "plmn", "lac", "rac");
                    addData(Integer.valueOf(uarfcn), Integer.valueOf(psc3), Arrays.toString(plmn_a), Integer.valueOf(lac3), Integer.valueOf(rac));
                    addData("ura", MDMContent.CELL_ID, "rscp", "ecn0", "-");
                    addData(Arrays.toString(ura), Integer.valueOf(cell_id2), Long.valueOf(rscp), Long.valueOf(ecn02), "-");
                } else {
                    data = data2;
                    num_cells = num_cells2;
                    coName = coName2;
                    int i5 = cellType;
                }
                i++;
                String str5 = name;
                num_cells2 = num_cells;
                coName2 = coName;
                data2 = data;
            }
            int i6 = num_cells2;
            String str6 = coName2;
            return;
        }
        int i7 = num_cells2;
    }
}
