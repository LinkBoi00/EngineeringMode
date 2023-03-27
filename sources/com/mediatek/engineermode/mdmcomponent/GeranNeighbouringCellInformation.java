package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.channellock.ChannelLockReceiver;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class GeranNeighbouringCellInformation extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_IND};
    HashMap<Integer, String> bandMapping = new HashMap<Integer, String>() {
        {
            put(0, "DCS1800");
            put(1, "PCS1900");
            put(2, "-");
        }
    };

    public GeranNeighbouringCellInformation(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getBandMapping(int bandidx) {
        if (bandidx == 0 || bandidx == 1) {
            return this.bandMapping.get(Integer.valueOf(bandidx));
        }
        return this.bandMapping.get(2) + "(" + bandidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "GERAN neighbouring cell information";
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
        return new String[]{"BAND INDICATOR", ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN, "BSIC", "RSSI"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        int cellNum = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_TOTAL_GCELL_NUM);
        int i = 0;
        while (i < cellNum && i < 6) {
            int valid = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_GCELL + "[" + i + "]." + "valid");
            int band = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_GCELL + "[" + i + "]." + MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_BAND_IND);
            int arfcn = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_GCELL + "[" + i + "]." + "arfcn");
            int bsic = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_GCELL + "[" + i + "]." + "bsic");
            int rssi = getFieldValue(data, MDMContent.EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_GCELL + "[" + i + "]." + "rssi");
            Object[] objArr = new Object[4];
            Object obj = "";
            objArr[0] = valid > 0 ? getBandMapping(band) : obj;
            objArr[1] = valid > 0 ? Integer.valueOf(arfcn) : obj;
            objArr[2] = valid > 0 ? Integer.valueOf(bsic) : obj;
            if (valid > 0) {
                obj = Float.valueOf(((float) rssi) / 4.0f);
            }
            objArr[3] = obj;
            addData(objArr);
            i++;
        }
    }
}
