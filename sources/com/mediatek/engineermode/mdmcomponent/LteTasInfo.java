package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class LteTasInfo extends NormalTableTasComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};
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

    public LteTasInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE TAS Info";
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
    public String[] getLabels() {
        String[] Lables_Verison_v1_1 = {"TX Antenna", "RSRP_LANT", "RSRP_UANT", "TX Power"};
        String[] Lables_Verison_v2_1 = {"TX Antenna", "RSRP_LANT", "RSRP_UANT", "RSRP_LANT(')", "TX Power"};
        String[] Lables_Verison_v1_2 = {"CC0 TX Antenna", "CC0 RSRP_LANT", "CC0 RSRP_UANT", "CC1 TX Antenna", "CC1 RSRP_LANT", "CC1 RSRP_UANT", "TX Power"};
        String[] Lables_Verison_v2_2 = {"CC0 TX Antenna", "CC0 RSRP_LANT", "CC0 RSRP_UANT", "CC0 RSRP_LANT(')", "CC1 TX Antenna", "CC1 RSRP_LANT", "CC1 RSRP_UANT", "CC1 RSRP_LANT(')", "TX Power"};
        String[] Lables_Tas_1 = {"TAS Enable Info", "Serving Band"};
        String[] Lables_Tas_2 = {"CC1 Serving Band"};
        String[] Labels_Dat = {"DAT Index"};
        int i = this.TasVersion;
        if (i == 1) {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas_1, Lables_Verison_v1_1, Lables_Tas_1.length), Labels_Dat);
        } else if (i == 2) {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas_1, Lables_Verison_v2_1, Lables_Tas_1.length), Labels_Dat);
        } else if (i == 3) {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas_2, conbineLablesByModem(Lables_Tas_1, Lables_Verison_v1_2, Lables_Tas_1.length), -5), Labels_Dat);
        } else if (i == 4) {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas_2, conbineLablesByModem(Lables_Tas_1, Lables_Verison_v2_2, Lables_Tas_1.length), -6), Labels_Dat);
        } else {
            return (String[]) concatAll(conbineLablesByModem(Lables_Tas_1, Lables_Verison_v1_1, Lables_Tas_1.length), Labels_Dat);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String rscpCheck(int value) {
        String value_s = String.valueOf(value);
        if (value == -255) {
            return " ";
        }
        return value_s;
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
        if (bandidx == 0) {
            return "INVALID";
        }
        return "Band" + " " + bandidx;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int bandidx_cc1;
        int bandidx;
        int tasidx;
        int rsrp_u_ant_cc1;
        int i;
        Msg data = (Msg) msg;
        int dl_cc_count = getFieldValue(data, MDMContent.DL_CC_COUNT);
        int ul_cc_count = getFieldValue(data, MDMContent.UL_CC_COUNT);
        int utas_info_valid = getFieldValue(data, "ul_info[0].utas_info_valid");
        setInfoValid(utas_info_valid);
        int i2 = utas_info_valid;
        if (isInfoValid()) {
            clearData();
            this.mAdapter.add(new String[]{"Use " + getName().replace("UTAS", "TAS")});
            return;
        }
        int fieldValue = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_VERISION);
        this.TasVersion = fieldValue;
        this.TasVersion = fieldValue == 0 ? 1 : 2;
        Elog.d("EmInfo/MDMComponent", "dl_cc_count= " + dl_cc_count);
        Elog.d("EmInfo/MDMComponent", "ul_cc_count= " + ul_cc_count);
        Elog.d("EmInfo/MDMComponent", "TasVersion= " + this.TasVersion);
        int antidx = getFieldValue(data, "ul_info[0].tx_ant_type", true);
        int rsrp_l_ant = getFieldValue(data, "ul_info[0].rsrp_l_ant", true);
        int rsrp_u_ant = getFieldValue(data, "ul_info[0].rsrp_u_ant", true);
        int rsrp_l_ant_a = getFieldValue(data, "ul_info[0].rsrp_l_ant_a", true);
        int tx_pwr = getFieldValue(data, "ul_info[0].tx_power", true);
        int antidx_cc1 = getFieldValue(data, "ul_info[1].tx_ant_type", true);
        int fieldValue2 = getFieldValue(data, "ul_info[1].rsrp_l_ant", true);
        int rsrp_u_ant_cc12 = getFieldValue(data, "ul_info[1].rsrp_u_ant", true);
        int rsrp_l_ant_cc1 = getFieldValue(data, "ul_info[1].rsrp_l_ant_a", true);
        int dat_index = getFieldValue(data, "ul_info[0].el1_dat_scenario_index", true);
        if (FeatureSupport.is93ModemAndAbove() != 0) {
            int tasidx2 = getFieldValue(data, "ul_info[0].tas_status");
            int bandidx2 = getFieldValue(data, "cell_info[0].band", true);
            bandidx_cc1 = getFieldValue(data, "cell_info[1].band", true);
            tasidx = tasidx2;
            bandidx = bandidx2;
        } else {
            tasidx = 0;
            bandidx_cc1 = 0;
            bandidx = 0;
        }
        clearData();
        if (dl_cc_count == 0) {
            Msg msg2 = data;
            this.TasVersion = 1;
            addData("", "", "", "", "", "");
            addData("");
            return;
        }
        if (dl_cc_count == 1 && ul_cc_count == 0 && this.TasVersion == 1) {
            addData("", "", "", rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), "");
            addData(Integer.valueOf(dat_index));
        } else if (dl_cc_count >= 1 && ul_cc_count == 1 && this.TasVersion == 1) {
            if (FeatureSupport.is93ModemAndAbove()) {
                addData(tasEableMapping(tasidx), servingBandMapping(bandidx));
            }
            addData(antidxMapping(antidx), rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), Integer.valueOf(tx_pwr));
            addData(Integer.valueOf(dat_index));
        } else {
            int bandidx3 = bandidx;
            if (dl_cc_count == 1 && ul_cc_count == 0) {
                rsrp_u_ant_cc1 = rsrp_u_ant_cc12;
                if (this.TasVersion == 2) {
                    addData("", "", "", rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), rscpCheck(rsrp_l_ant_a), "");
                    addData(Integer.valueOf(dat_index));
                    return;
                }
            } else {
                rsrp_u_ant_cc1 = rsrp_u_ant_cc12;
            }
            if (dl_cc_count >= 1 && ul_cc_count == 1 && this.TasVersion == 2) {
                if (FeatureSupport.is93ModemAndAbove()) {
                    i = 1;
                    addData(tasEableMapping(tasidx), servingBandMapping(bandidx3));
                } else {
                    i = 1;
                }
                Object[] objArr = new Object[5];
                objArr[0] = antidxMapping(antidx);
                objArr[i] = rscpCheck(rsrp_l_ant);
                objArr[2] = rscpCheck(rsrp_u_ant);
                objArr[3] = rscpCheck(rsrp_l_ant_a);
                objArr[4] = Integer.valueOf(tx_pwr);
                addData(objArr);
                Object[] objArr2 = new Object[i];
                objArr2[0] = Integer.valueOf(dat_index);
                addData(objArr2);
            } else if (dl_cc_count > 1 && ul_cc_count == 2 && this.TasVersion == 1) {
                this.TasVersion = 3;
                if (FeatureSupport.is93ModemAndAbove()) {
                    addData(tasEableMapping(tasidx), servingBandMapping(bandidx3), antidxMapping(antidx), rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), servingBandMapping(bandidx_cc1), antidxMapping(antidx_cc1), rscpCheck(rsrp_l_ant_cc1), rscpCheck(rsrp_u_ant_cc1), Integer.valueOf(tx_pwr));
                    addData(Integer.valueOf(dat_index));
                    return;
                }
                addData(antidxMapping(antidx), rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), antidxMapping(antidx_cc1), rscpCheck(rsrp_l_ant_cc1), rscpCheck(rsrp_u_ant_cc1), Integer.valueOf(tx_pwr));
                addData(Integer.valueOf(dat_index));
            } else {
                int bandidx_cc12 = bandidx_cc1;
                int rsrp_u_ant_cc13 = rsrp_u_ant_cc1;
                if (dl_cc_count <= 1 || ul_cc_count != 2) {
                    return;
                }
                int i3 = ul_cc_count;
                if (this.TasVersion == 2) {
                    this.TasVersion = 4;
                    if (FeatureSupport.is93ModemAndAbove()) {
                        addData(tasEableMapping(tasidx), servingBandMapping(bandidx3), antidxMapping(antidx), rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), rscpCheck(rsrp_l_ant_a), servingBandMapping(bandidx_cc12), antidxMapping(antidx_cc1), rscpCheck(rsrp_l_ant_cc1), rscpCheck(rsrp_u_ant_cc13), rscpCheck(0), Integer.valueOf(tx_pwr));
                        addData(Integer.valueOf(dat_index));
                        return;
                    }
                    addData(antidxMapping(antidx), rscpCheck(rsrp_l_ant), rscpCheck(rsrp_u_ant), rscpCheck(rsrp_l_ant_a), antidxMapping(antidx_cc1), rscpCheck(rsrp_l_ant_cc1), rscpCheck(rsrp_u_ant_cc13), rscpCheck(0), Integer.valueOf(tx_pwr));
                    addData(Integer.valueOf(dat_index));
                }
            }
        }
    }
}
