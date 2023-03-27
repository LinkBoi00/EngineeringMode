package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.channellock.ChannelLockReceiver;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class BasicInfoServingGsm extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_MEASUREMENT_REPORT_INFO_IND};
    HashMap<Integer, String> mGprsMapping = new HashMap<Integer, String>() {
        {
            put(0, "PGSM");
            put(1, "EGSM");
        }
    };
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "PGSM");
            put(1, "EGSM");
            put(2, "RGSM");
            put(3, "DCS1800");
            put(4, "PCS1900");
            put(5, "GSM450");
            put(6, "GSM480");
            put(7, "GSM850");
        }
    };
    HashMap<Integer, String> mPbcchMapping = new HashMap<Integer, String>() {
        {
            put(0, "PGSM");
            put(1, "EGSM");
        }
    };

    public BasicInfoServingGsm(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Basic Info (Serving) (GSM)";
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
        return new String[]{"Band", ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN, "BSIC", "GPRS supported", "PBCCH present"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int rrState = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_RR_STATE);
        clearData();
        if (rrState >= 3 && rrState <= 7) {
            int band = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERVING_CURRENT_BAND);
            int arfcn = getFieldValue(data, "rr_em_measurement_report_info." + "serving_arfcn");
            int bsic = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERVING_BSIC);
            int gprs = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_GPRS_SUPPORTED);
            int pbcch = getFieldValue(data, "rr_em_measurement_report_info." + MDMContent.RR_EM_MEASUREMENT_REPORT_INFO_SERV_GPRS_PBCCH_PRESENT);
            addData(this.mMapping.get(Integer.valueOf(band)));
            addData(Integer.valueOf(arfcn));
            addData(Integer.valueOf(bsic));
            addData(this.mGprsMapping.get(Integer.valueOf(gprs)));
            addData(this.mPbcchMapping.get(Integer.valueOf(pbcch)));
        }
        notifyDataSetChanged();
    }
}
