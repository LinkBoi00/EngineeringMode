package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class LTENeighborCellInfoGSM extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_IR_4G_NEIGHBOR_MEAS_STATUS_IND};
    HashMap<Integer, String> mBandTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "-");
            put(1, "FDD");
            put(2, "TDD");
        }
    };
    HashMap<Integer, String> mCellTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "Normal");
            put(1, "CSG");
            put(2, "Hybrid");
        }
    };
    HashMap<Integer, String> mFailTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "None");
            put(1, "Never");
            put(2, "PLMN");
            put(3, "LA");
            put(4, "C_BAR");
            put(5, "TEMP");
            put(6, "CRI3");
            put(7, "TA");
            put(8, "FREQ");
            put(9, "RES_OP");
            put(10, "RES_OP_FRE");
            put(11, "CSG");
            put(12, "GEMINI");
            put(13, "NAS");
        }
    };

    public LTENeighborCellInfoGSM(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Neighbor Cell Info (GSM)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "2. GSM EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"cell_type", "earfcn", "pci", MDMContent.EM_RRM_IR_4G_NEIGHBOR_MEAS_BAND_TYPE, "rsrp", "rsrq", "rep_value", "non_rep_value", "fail_type"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String coName;
        Msg data;
        Msg data2 = (Msg) msg;
        String coName2 = "ir_4g_neighbor_meas_status[";
        clearData();
        int i = 0;
        while (i < 6) {
            int isValid = getFieldValue(data2, coName2 + i + "]." + "is_valid");
            int cellType = getFieldValue(data2, coName2 + i + "]." + "cell_type");
            long earfcn = (long) getFieldValue(data2, coName2 + i + "]." + "earfcn");
            int pci = getFieldValue(data2, coName2 + i + "]." + "pci");
            int bandType = getFieldValue(data2, coName2 + i + "]." + MDMContent.EM_RRM_IR_4G_NEIGHBOR_MEAS_BAND_TYPE);
            int rsrp = getFieldValue(data2, coName2 + i + "]." + "rsrp", true);
            int rsrq = getFieldValue(data2, coName2 + i + "]." + "rsrq", true);
            int repValue = getFieldValue(data2, coName2 + i + "]." + "rep_value");
            int nonRepValue = getFieldValue(data2, coName2 + i + "]." + "non_rep_value");
            int failType = getFieldValue(data2, coName2 + i + "]." + "fail_type");
            if (isValid > 0) {
                data = data2;
                String cell = this.mCellTypeMapping.get(Integer.valueOf(cellType));
                coName = coName2;
                int i2 = isValid;
                addData(cell);
                String str = cell;
                addData(Long.valueOf(earfcn));
                addData(Integer.valueOf(pci));
                addData(this.mBandTypeMapping.get(Integer.valueOf(bandType)));
                addData(Integer.valueOf(rsrp));
                addData(Integer.valueOf(rsrq));
                addData(Integer.valueOf(repValue));
                addData(Integer.valueOf(nonRepValue));
                addData(this.mFailTypeMapping.get(Integer.valueOf(failType)));
            } else {
                data = data2;
                coName = coName2;
                int i3 = isValid;
            }
            i++;
            data2 = data;
            coName2 = coName;
        }
        notifyDataSetChanged();
    }
}
