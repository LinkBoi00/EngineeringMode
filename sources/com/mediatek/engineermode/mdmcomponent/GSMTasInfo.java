package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class GSMTasInfo extends NormalTableTasComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_GSM_TAS_INFO_IND, MDMContent.MSG_ID_EM_GSM_UTAS_INFO_IND};
    HashMap<Integer, String> AntennaMapping = new HashMap<Integer, String>() {
        {
            put(0, "LANT");
            put(1, "UANT");
            put(2, "LANT(')");
            put(3, "UANT");
            put(4, "-");
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
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };

    public GSMTasInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String antidxMapping(int antidx) {
        if (antidx >= 0 && antidx <= 3) {
            return this.AntennaMapping.get(Integer.valueOf(antidx));
        }
        return this.AntennaMapping.get(4) + "(" + antidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx >= 0 && tasidx <= 1) {
            return this.TasEnableMapping.get(Integer.valueOf(tasidx));
        }
        return this.TasEnableMapping.get(2) + "(" + tasidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        if (bandidx < 1 || bandidx > 4) {
            return " ";
        }
        return this.ServingBandMapping.get(Integer.valueOf(bandidx));
    }

    /* access modifiers changed from: package-private */
    public String[] conbineLablesByModem(String[] lables1, String[] lables2, int position) {
        if (!FeatureSupport.is93ModemAndAbove()) {
            return lables2;
        }
        if (position < 0) {
            return addLablesAtPosition(lables2, lables1, Math.abs(position));
        }
        return addLablesAtPosition(lables1, lables2, position);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "GSM TAS Info";
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
        String[] Lables_Tas = {"TAS Enable Info", "Serving Band"};
        return (String[]) concatAll(conbineLablesByModem(Lables_Tas, new String[]{"Antenna Index", "Other Antenna Index", "Current Antenna Power", "Other Antenna Power", "Current Average SNR", "Other Average SNR", "Current Average SNR(dB)", "Other Average SNR(dB)"}, Lables_Tas.length), new String[]{"DAT Index"});
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        if (name.equals(MDMContent.MSG_ID_EM_GSM_UTAS_INFO_IND)) {
            setInfoValid(1);
        } else {
            setInfoValid(0);
        }
        if (isInfoValid()) {
            clearData();
            TableInfoAdapter tableInfoAdapter = this.mAdapter;
            tableInfoAdapter.add(new String[]{"Use " + getName().replace("UTAS", "TAS")});
            return;
        }
        int tasEnableInfo = getFieldValue(data, "tas_enable");
        int currentSurvingBand = getFieldValue(data, MDMContent.GSM_SERVING_BAND);
        int antennaIdx = getFieldValue(data, MDMContent.GSM_ANTENNA);
        int otherAntennaIdx = getFieldValue(data, MDMContent.GSM_OTHER_ANTENNA);
        int currentAntRxLevel = getFieldValue(data, MDMContent.GSM_CURRENT_ANTENNA_RXLEVEL, true);
        int otherAntRxLevel = getFieldValue(data, MDMContent.GSM_OTHER_ANTENNA_RXLEVEL, true);
        int currentAverageSNR = getFieldValue(data, MDMContent.GSM_CURRENT_AVERAGE_SNR, true);
        int otherAverageSNR = getFieldValue(data, MDMContent.GSM_OTHER_ANTENNA_SNR, true);
        int currentAverageSNRDb = (int) (Math.log10(((double) currentAverageSNR) / 4.0d) * 10.0d);
        int otherAverageSNRDb = (int) (Math.log10(((double) otherAverageSNR) / 4.0d) * 10.0d);
        int dat_index = getFieldValue(data, MDMContent.GSM_DAT_SCENARIO_INDEX, true);
        clearData();
        if (FeatureSupport.is93ModemAndAbove()) {
            addData(tasEableMapping(tasEnableInfo));
            addData(servingBandMapping(currentSurvingBand));
        }
        addData(antidxMapping(antennaIdx));
        addData(antidxMapping(otherAntennaIdx));
        addData(Integer.valueOf(currentAntRxLevel));
        addData(Integer.valueOf(otherAntRxLevel));
        addData(Integer.valueOf(currentAverageSNR));
        addData(Integer.valueOf(otherAverageSNR));
        addData(Integer.valueOf(currentAverageSNRDb));
        addData(Integer.valueOf(otherAverageSNRDb));
        addData(Integer.valueOf(dat_index));
        notifyDataSetChanged();
    }
}
