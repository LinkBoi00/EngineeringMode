package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class WcdmaTasInfo extends NormalTableTasComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND, MDMContent.MSG_ID_FDD_EM_UL1_UTAS_INFO_IND};
    HashMap<Integer, String> AntennaMapping = new HashMap<Integer, String>() {
        {
            put(0, "LANT");
            put(1, "UANT");
            put(2, "LANT(')");
            put(3, "UANT");
            put(4, "-");
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

    public WcdmaTasInfo(Activity context) {
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
        return "Band" + " " + bandidx;
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
        return "WCDMA TAS Info";
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
        String[] Lables_Verison_v1 = {"TX Antenna", "Antenna State", "RSCP_Diff", "RSCP_LANT", "RSCP_UANT", "TX Power", "DPCCH TX Power"};
        String[] Lables_Verison_v2 = {"TX Antenna", "Antenna State", "RSCP_Diff", "RSCP_LANT", "RSCP_UANT", "RSCP_LANT(')", "TX Power", "DPCCH TX Power"};
        String[] Lables_Tas = {"Tas Enable Info", "Serving Band", "Serving UARFCN"};
        String[] Labels_Dat = {"DAT Index"};
        if (this.TasVersion == 2) {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas, Lables_Verison_v2, Lables_Tas.length), Labels_Dat);
        }
        return (String[]) concatAll(conbineLablesByModem(Lables_Tas, Lables_Verison_v1, Lables_Tas.length), Labels_Dat);
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String rscp_diff_s;
        String rscp_0_s;
        String rscp_1_s;
        String rscp_2_s;
        String tx_pwr_s;
        String dpcch_tx_pwr_s;
        int i;
        Msg data = (Msg) msg;
        if (name.equals(MDMContent.MSG_ID_FDD_EM_UL1_UTAS_INFO_IND)) {
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
        int tasidx = getFieldValue(data, "EmUl1Tas." + "tas_enable");
        int bandidx = getFieldValue(data, "EmUl1Tas." + "band");
        int uarfcnidx = getFieldValue(data, "EmUl1Tas." + "uarfcn");
        int antidx = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_MAIN_ANT_IDX);
        int rscp_diff = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_RSCP_DIFF, true);
        int dat_index = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_DAT_SCENARIO_INDEX, true);
        if (rscp_diff == -480) {
            rscp_diff_s = " ";
        } else {
            rscp_diff_s = String.valueOf(rscp_diff / 4);
        }
        int rscp_0 = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_RSCP0, true);
        if (rscp_0 == -480) {
            rscp_0_s = " ";
        } else {
            rscp_0_s = String.valueOf(rscp_0 / 4);
        }
        int rscp_1 = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_RSCP1, true);
        if (rscp_1 == -480) {
            rscp_1_s = " ";
        } else {
            rscp_1_s = String.valueOf(rscp_1 / 4);
        }
        int rscp_2 = getFieldValue(data, "EmUl1Tas." + MDMContent.FDD_EM_UL1_TAS_RSCP2, true);
        if (rscp_2 == -480) {
            rscp_2_s = " ";
        } else {
            rscp_2_s = String.valueOf(rscp_2 / 4);
        }
        int i2 = rscp_2;
        StringBuilder sb = new StringBuilder();
        sb.append("EmUl1Tas.");
        int i3 = rscp_1;
        sb.append("tx_pwr");
        int tx_pwr = getFieldValue(data, sb.toString(), true);
        if (tx_pwr == -128) {
            tx_pwr_s = " ";
        } else {
            tx_pwr_s = String.valueOf(tx_pwr);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("EmUl1Tas.");
        int i4 = tx_pwr;
        sb2.append(MDMContent.FDD_EM_UL1_TAS_DPCCH_TX_PWR);
        int dpcch_tx_pwr = getFieldValue(data, sb2.toString(), true);
        if (dpcch_tx_pwr == -128) {
            dpcch_tx_pwr_s = " ";
        } else {
            dpcch_tx_pwr_s = String.valueOf(dpcch_tx_pwr);
        }
        int i5 = dpcch_tx_pwr;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("EmUl1Tas.");
        Object obj = "EmUl1Tas.";
        sb3.append(MDMContent.FDD_EM_UL1_TAS_VERISION);
        this.TasVersion = getFieldValue(data, sb3.toString(), true) == 2 ? 2 : 1;
        clearData();
        if (FeatureSupport.is93ModemAndAbove()) {
            addData(tasEableMapping(tasidx), servingBandMapping(bandidx), Integer.valueOf(uarfcnidx));
        }
        if (this.TasVersion == 2) {
            addData(antidxMapping(antidx), Integer.valueOf(antidx), rscp_diff_s, rscp_0_s, rscp_1_s, rscp_2_s, tx_pwr_s, dpcch_tx_pwr_s);
            Msg msg2 = data;
            i = 1;
        } else {
            Msg msg3 = data;
            i = 1;
            addData(antidxMapping(antidx), Integer.valueOf(antidx), rscp_diff_s, rscp_0_s, rscp_1_s, tx_pwr_s, dpcch_tx_pwr_s);
        }
        Object[] objArr = new Object[i];
        objArr[0] = Integer.valueOf(dat_index);
        addData(objArr);
        notifyDataSetChanged();
    }
}
