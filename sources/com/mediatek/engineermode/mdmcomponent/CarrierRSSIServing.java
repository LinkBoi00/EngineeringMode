package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class CarrierRSSIServing extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_MEASUREMENT_REPORT_INFO_IND};

    public CarrierRSSIServing(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Cell measurement (Serving)";
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
        return new String[]{"BCCH RLA(Dedicated)", "RLA", "Reported", "RX level full", "RX quality sub", "RX quality full"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        char c;
        Msg data = (Msg) msg;
        int rrState = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_RR_STATE);
        int rla = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_RLA_IN_QUARTER_DBM, true);
        int reported = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_RLA_REPORTED_VALUE);
        int bcchRlaValid = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_BCCH_RLA_VALID);
        int bcchRla = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_BCCH_RLA_IN_DEDI_STATE, true);
        int full = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_RLA_FULL_VALUE_IN_QUATER_DBM, true);
        int rxSub = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_RXQUAL_SUB);
        int rxFull = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_RXQUAL_FULL);
        clearData();
        if (rrState >= 3 && rrState <= 7) {
            if (bcchRlaValid <= 0) {
                addData("-");
            } else if (bcchRla == -1000) {
                addData("-");
            } else {
                addData(String.format("%.2f", new Object[]{Float.valueOf(((float) bcchRla) / 4.0f)}));
            }
            if (rla == -1000) {
                addData("-");
            } else {
                addData(String.format("%.2f", new Object[]{Float.valueOf(((float) rla) / 4.0f)}));
            }
            if (rla == -1000) {
                addData("-");
            } else {
                addData(Integer.valueOf(reported));
            }
            if (rrState != 6) {
                addData("-");
            } else {
                addData(String.format("%.2f", new Object[]{Float.valueOf(((float) full) / 4.0f)}));
            }
            if (rxSub == 255) {
                addData("-");
                c = 0;
            } else {
                c = 0;
                addData(Integer.valueOf(rxSub));
            }
            if (rxFull == 255) {
                addData("-");
            } else {
                Object[] objArr = new Object[1];
                objArr[c] = Integer.valueOf(rxFull);
                addData(objArr);
            }
        }
        notifyDataSetChanged();
    }
}
