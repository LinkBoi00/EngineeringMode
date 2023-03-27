package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class TddTasInfo extends NormalTableTasComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_TDD_TAS_INFO_IND};
    HashMap<Integer, String> AntennaMapping = new HashMap<Integer, String>() {
        {
            put(0, "LANT");
            put(1, "UANT");
            put(2, "LANT(')");
            put(3, "-");
        }
    };
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "Band 34");
            put(1, "Band 39");
            put(2, "-");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };
    int TasVersion = 1;

    public TddTasInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String antidxMapping(int antidx) {
        if (antidx >= 0 && antidx <= 2) {
            return this.AntennaMapping.get(Integer.valueOf(antidx));
        }
        return this.AntennaMapping.get(3) + "(" + antidx + ")";
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
        if (bandidx >= 0 && bandidx <= 1) {
            return this.ServingBandMapping.get(Integer.valueOf(bandidx));
        }
        return this.ServingBandMapping.get(2) + "(" + bandidx + ")";
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
        return "TDD TAS Info";
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
        String[] Lables_Verison_v1 = {"force tx en", "force ant state", "cur ant state", "RSCP_LANT", "RSSI_LANT", "SINR_LANT", "RSCP_UANT", "RSSI_UANT", "SINR_UANT", "tx pwr"};
        String[] Lables_Verison_v2 = {"force tx en", "force ant state", "cur ant state", "RSCP_LANT", "RSSI_LANT", "SINR_LANT", "RSCP_UANT", "RSSI_UANT", "SINR_UANT", "RSCP_LANT(')", "RSSI_LANT(')", "SINR_LANT(')", "tx pwr"};
        String[] Lables_Tas = {"Tas Enable Info", "Serving Band"};
        if (this.TasVersion == 2) {
            return conbineLablesByModem(Lables_Tas, Lables_Verison_v2, Lables_Tas.length);
        }
        return conbineLablesByModem(Lables_Tas, Lables_Verison_v1, Lables_Tas.length);
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int cur_ant_idx = getFieldValue(data, "cur_ant_state");
        int utas_info_valid = (cur_ant_idx >> 8) == 3 ? 1 : 0;
        setInfoValid(utas_info_valid);
        if (isInfoValid()) {
            clearData();
            TableInfoAdapter tableInfoAdapter = this.mAdapter;
            tableInfoAdapter.add(new String[]{"Use " + getName().replace("UTAS", "TAS")});
            return;
        }
        this.TasVersion = ((cur_ant_idx >> 8) & 3) == 2 ? 2 : 1;
        Elog.d("EmInfo/MDMComponent", "TasVersion " + this.TasVersion);
        int tas_enable = getFieldValue(data, "tas_enable_info");
        int serving_band = getFieldValue(data, "current_serving_band");
        int force_tx_en = getFieldValue(data, MDMContent.TDD_FORCE_TX_EN);
        int force_ant_idx = getFieldValue(data, "force_ant_state");
        int ant0_rscp = getFieldValue(data, MDMContent.TDD_ANT0_RSCP, true);
        int ant0_rssi = getFieldValue(data, MDMContent.TDD_ANT0_RSSI, true);
        int ant0_sinr = getFieldValue(data, MDMContent.TDD_ANT0_SINR, true);
        int ant1_rscp = getFieldValue(data, MDMContent.TDD_ANT1_RSCP, true);
        int ant1_rssi = getFieldValue(data, "ant1_rssi", true);
        int ant1_sinr = getFieldValue(data, MDMContent.TDD_ANT1_SINR, true);
        int txpower = getFieldValue(data, "tx_pwr", true);
        int ant2_rscp = 0;
        int ant2_rssi = 0;
        int ant2_sinr = 0;
        int i = utas_info_valid;
        if (this.TasVersion == 2) {
            ant2_rscp = getFieldValue(data, MDMContent.TDD_ANT2_RSCP, true);
            ant2_rssi = getFieldValue(data, "ant1_rssi", true);
            ant2_sinr = getFieldValue(data, MDMContent.TDD_ANT2_SINR, true);
        }
        clearData();
        if (FeatureSupport.is93ModemAndAbove()) {
            addData(tasEableMapping(tas_enable), servingBandMapping(serving_band));
        }
        if (this.TasVersion == 2) {
            addData(tasEableMapping(force_tx_en), antidxMapping(force_ant_idx), Integer.valueOf(cur_ant_idx & 255), Integer.valueOf(ant0_rscp), Integer.valueOf(ant0_rssi), Integer.valueOf(ant0_sinr), Integer.valueOf(ant1_rscp), Integer.valueOf(ant1_rssi), Integer.valueOf(ant1_sinr), Integer.valueOf(ant2_rscp), Integer.valueOf(ant2_rssi), Integer.valueOf(ant2_sinr), Integer.valueOf(txpower));
        } else {
            addData(tasEableMapping(force_tx_en), antidxMapping(force_ant_idx), Integer.valueOf(cur_ant_idx & 255), Integer.valueOf(ant0_rscp), Integer.valueOf(ant0_rssi), Integer.valueOf(ant0_sinr), Integer.valueOf(ant1_rscp), Integer.valueOf(ant1_rssi), Integer.valueOf(ant1_sinr), Integer.valueOf(txpower));
        }
        notifyDataSetChanged();
    }
}
