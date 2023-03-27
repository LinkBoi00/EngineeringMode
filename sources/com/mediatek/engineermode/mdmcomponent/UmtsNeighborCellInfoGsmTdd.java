package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class UmtsNeighborCellInfoGsmTdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_IR_3G_NEIGHBOR_MEAS_STATUS_IND};
    HashMap<Integer, String> mCellTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "NORMAL");
            put(1, "CSG");
            put(2, "HYBRID");
        }
    };
    HashMap<Integer, String> mFailTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "IR_INITIAL_VALUE");
            put(1, "NEVER_ALLOWED");
            put(2, "PLMN_ID_MISMATCHED");
            put(3, "LA_NOT_ALLOWED");
            put(4, "CELL_BARRED");
            put(5, "TEMP_FAILURE");
            put(6, "CRITERIA3_NOT_SATISFIED");
            put(7, "TA_NOT_ALLOWED");
            put(8, "CELL_BARRED_FREQ");
            put(9, "CELL_BARRED_RESV_OPERATOR");
            put(10, "CELL_BARRED_RESV_OPERATOR_FREQ");
            put(11, "CSG_NOT_ALLOWED");
        }
    };

    public UmtsNeighborCellInfoGsmTdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "UMTS Neighbor Cell Info (GSM TDD)";
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
        return new String[]{"cell_type", "uarfcn", MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_PHY_ID, MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_STRENGTH, MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_QUALITY, "rep_value", "non_rep_value", "fail_type"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        for (int i = 0; i < 6; i++) {
            if (getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "is_valid") > 0) {
                int cellType = getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "cell_type");
                int failType = getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "fail_type");
                addData(this.mCellTypeMapping.get(Integer.valueOf(cellType)));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "uarfcn")));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_PHY_ID)));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_STRENGTH, true)));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + MDMContent.RRM_IR_3G_NEIGHBOR_MEAS_STATUS_QUALITY, true)));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "rep_value")));
                addData(Integer.valueOf(getFieldValue(data, "ir_3g_neighbor_meas_status[" + i + "]." + "non_rep_value")));
                addData(this.mFailTypeMapping.get(Integer.valueOf(failType)));
            }
        }
        notifyDataSetChanged();
    }
}
