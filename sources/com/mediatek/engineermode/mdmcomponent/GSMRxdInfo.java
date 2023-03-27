package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class GSMRxdInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_GSM_RXD_INFO_IND};
    HashMap<Integer, String> RxModeMapping = new HashMap<Integer, String>() {
        {
            put(-1, "-");
            put(0, "MODE_LEGACY");
            put(1, "MODE_1RX_DESENSE");
            put(3, "MODE_RXD");
            put(17, "MODE_1RX_DESENSE_CROSS");
            put(19, "RXD_CROSS");
        }
    };
    HashMap<Integer, String> RxdEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(1, "Band 850");
            put(2, "Band 900");
            put(3, "Band 1800");
            put(4, "Band 1900");
            put(5, "-");
        }
    };

    public GSMRxdInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String rxdModeMapping(int rxMode) {
        if (this.RxModeMapping.keySet().contains(Integer.valueOf(rxMode))) {
            return this.RxModeMapping.get(Integer.valueOf(rxMode));
        }
        return this.RxModeMapping.get(-1) + "(" + "" + ")";
    }

    /* access modifiers changed from: package-private */
    public String rxdEnableMapping(int tasidx) {
        if (tasidx >= 0 && tasidx <= 1) {
            return this.RxdEnableMapping.get(Integer.valueOf(tasidx));
        }
        return this.RxdEnableMapping.get(2) + "(" + tasidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        if (bandidx < 1 || bandidx > 4) {
            return " ";
        }
        return this.ServingBandMapping.get(Integer.valueOf(bandidx));
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "GSM RXD Info";
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
        return new String[]{"RxD Enable", "Serving ARFCN", "Serving Band", "RxD Mode", "Current Primary RxLev", "Current Diversity RxLev"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int rxdEnable = getFieldValue(data, MDMContent.GSM_RXD_ENABLE);
        int servingArfcn = getFieldValue(data, "serving_arfcn", true);
        int currentSurvingBand = getFieldValue(data, MDMContent.GSM_SERVING_BAND);
        int rxdMode = getFieldValue(data, MDMContent.GSM_RXD_MODE);
        int curPriRxLev = getFieldValue(data, MDMContent.GSM_RXLEV_PRX, true);
        int curDivRxLev = getFieldValue(data, MDMContent.GSM_RXLEV_DRX, true);
        clearData();
        addData(rxdEnableMapping(rxdEnable));
        addData(Integer.valueOf(servingArfcn));
        addData(servingBandMapping(currentSurvingBand));
        if (rxdEnable == 1) {
            addData(rxdModeMapping(rxdMode));
            addData(curPriRxLev + " dBm");
            addData(curDivRxLev + " dBm");
        }
        notifyDataSetChanged();
    }
}
