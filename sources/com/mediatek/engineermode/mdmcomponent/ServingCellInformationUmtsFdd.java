package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class ServingCellInformationUmtsFdd extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_CSCE_SERV_CELL_S_STATUS_IND};

    public ServingCellInformationUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Serving Cell Information (UMTS FDD)";
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
        return new String[]{"Item", "value"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        Msg data = (Msg) msg;
        String coName = "serv_cell.";
        clearData();
        int uarfcn = getFieldValue(data, coName + "uarfcn_DL");
        int psc = getFieldValue(data, coName + "psc");
        int lac_valid = getFieldValue(data, coName + MDMContent.FDD_EM_CSCE_SERV_LAC_VALID);
        int lac = getFieldValue(data, coName + "lac");
        int rac_valid = getFieldValue(data, coName + "rac_valid");
        int rac = getFieldValue(data, coName + "rac");
        int cell_id = getFieldValue(data, coName + "cell_identity");
        float rscp = ((float) getFieldValue(data, coName + "rscp", true)) / 4096.0f;
        float ecn0 = ((float) getFieldValue(data, coName + "ec_no", true)) / 4096.0f;
        addData("UARFCN", Integer.valueOf(uarfcn));
        addData(MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, Integer.valueOf(psc));
        int value = 0;
        int count = getFieldValue(data, coName + "multi_plmn_count");
        int i = 0;
        while (true) {
            int uarfcn2 = uarfcn;
            str = "].";
            int psc2 = psc;
            if (i >= count) {
                break;
            }
            int count2 = count;
            StringBuilder sb = new StringBuilder();
            sb.append(coName);
            int i2 = value;
            sb.append("multi_plmn_id");
            sb.append("[");
            sb.append(i);
            sb.append(str);
            String secName = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(secName);
            sb2.append("mcc");
            float ecn02 = ecn0;
            sb2.append(1);
            int value2 = getFieldValue(data, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(secName);
            sb3.append("mcc");
            int i3 = value2;
            sb3.append(2);
            String plmn = ("" + value2) + getFieldValue(data, sb3.toString());
            int value3 = getFieldValue(data, secName + "mcc" + 3);
            String plmn2 = plmn + value3;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(secName);
            sb4.append("mnc");
            int i4 = value3;
            sb4.append(1);
            int value4 = getFieldValue(data, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(secName);
            sb5.append("mnc");
            int i5 = value4;
            sb5.append(2);
            String plmn3 = (plmn2 + value4) + getFieldValue(data, sb5.toString());
            value = getFieldValue(data, secName + "mnc" + 3);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(plmn3);
            sb6.append(value == 15 ? "" : Integer.valueOf(value));
            String plmn4 = sb6.toString();
            StringBuilder sb7 = new StringBuilder();
            String str2 = secName;
            sb7.append("PLMN[");
            sb7.append(i);
            sb7.append("]");
            addData(sb7.toString(), plmn4);
            i++;
            uarfcn = uarfcn2;
            psc = psc2;
            count = count2;
            ecn0 = ecn02;
        }
        float ecn03 = ecn0;
        int i6 = value;
        Object[] objArr = new Object[2];
        objArr[0] = "LAC";
        Object obj = "-";
        objArr[1] = lac_valid == 0 ? obj : Integer.valueOf(lac);
        addData(objArr);
        Object[] objArr2 = new Object[2];
        objArr2[0] = "RAC";
        if (rac_valid != 0) {
            obj = Integer.valueOf(rac);
        }
        objArr2[1] = obj;
        addData(objArr2);
        int num_ura_id = getFieldValue(data, coName + "num_ura_id");
        int i7 = 0;
        while (i7 < num_ura_id) {
            String secName2 = coName + "uraIdentity" + "[" + i7 + str;
            StringBuilder sb8 = new StringBuilder();
            sb8.append(secName2);
            String coName2 = coName;
            sb8.append("stringData");
            String str3 = str;
            sb8.append("[0]");
            int stringData0 = getFieldValue(data, sb8.toString());
            int stringData1 = getFieldValue(data, secName2 + "stringData" + "[1]");
            Msg data2 = data;
            int i8 = stringData1;
            StringBuilder sb9 = new StringBuilder();
            int i9 = stringData0;
            sb9.append("URA[");
            sb9.append(i7);
            sb9.append("]");
            addData(sb9.toString(), Integer.valueOf((stringData0 << 8) | stringData1));
            i7++;
            coName = coName2;
            str = str3;
            data = data2;
        }
        String str4 = coName;
        addData("Cell ID", Integer.valueOf(cell_id));
        addData("RSCP_CPICH Power Level", Float.valueOf(rscp));
        addData("Ec/Io", Float.valueOf(ecn03));
    }
}
