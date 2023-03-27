package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.rfdesense.RfDesenseTxTestCdma;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRPServingCellInfo2 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_TPC_REPORT, MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_ID_NL1_SYNC_SERVING_BEAM_MEASUREMENT, MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NL2_PDCP_CONFIGURATION_EVENT, MDMContentICD.MSG_ID_NRRC_CAPABILITY_EVENT, MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT, MDMContentICD.MSG_ID_NRRC_SCG_RECONFIGURATION_EVENT, MDMContentICD.MSG_ID_NL2_MAC_TIMING_ADVANCE_EVENT};
    HashMap<Integer, Integer> BwpMapping = new HashMap<Integer, Integer>() {
        {
            put(0, 15);
            put(1, 30);
            put(2, 60);
            put(3, 120);
            put(4, Integer.valueOf(LastCallFailCause.CALL_BARRED));
        }
    };
    int[] PRlcEntityAddrs = {32, 32, 29, 1};
    int[][] TAAddrs = {new int[]{8, 14, 6}, new int[]{8, 15, 6}, new int[]{8, 15, 6}};
    int[] UpLayerIndAddrs = {8, 112, 8};
    int[][] beamFlagAddrs;
    int[] carIndexAddrs = {8, 16, 4};
    int[][] carTypeAddrs = {new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 11, 4}};
    int[] cfgRlcEntityAddrs = {32, 32, 27, 2};
    HashMap<Integer, String> cfgRlcEntityDLMapping = new HashMap<Integer, String>() {
        {
            put(0, "DL: LTE");
            put(1, "DL: NR");
            put(2, "DL: LTE & NR");
            put(-1, "");
        }
    };
    HashMap<Integer, String> cfgRlcEntityULMapping = new HashMap<Integer, String>() {
        {
            put(0, "UL: LTE");
            put(1, "UL: NR");
            put(-1, "");
        }
    };
    int[][] cpTypeAddr = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.DIAL_MODIFIED_TO_DIAL, 1}, new int[]{8, LastCallFailCause.RADIO_OFF, 1}, new int[]{8, LastCallFailCause.RADIO_OFF, 1}, new int[]{8, LastCallFailCause.RADIO_OFF, 1}, new int[]{8, LastCallFailCause.RADIO_OFF, 1}, new int[]{8, LastCallFailCause.RADIO_OFF, 1}};
    int[][] curEndcPowerAddrs = {new int[0], new int[]{8, 136, 16}};
    int[][] dlBwpSizeAddrs = {new int[]{8, 920, 12, 9}, new int[]{8, 920, 12, 9}, new int[]{8, 1176, 12, 9}, new int[]{8, 1176, 12, 9}, new int[]{8, 1432, 12, 9}, new int[]{8, 1464, 12, 9}, new int[]{8, 1496, 12, 9}, new int[]{8, 1496, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1784, 12, 9}, new int[]{8, 1816, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}};
    int[][] dlSubCarrSpacingAddrs = {new int[]{8, 920, 21, 3}, new int[]{8, 920, 21, 3}, new int[]{8, 1176, 21, 3}, new int[]{8, 1176, 21, 3}, new int[]{8, 1432, 21, 3}, new int[]{8, 1464, 21, 3}, new int[]{8, 1496, 21, 3}, new int[]{8, 1496, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1784, 21, 3}, new int[]{8, 1816, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}};
    int[][] maxMcsAddr = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 664, 3, 1}, new int[]{8, 664, 3, 1}, new int[]{8, 664, 3, 1}, new int[]{8, 920, 3, 1}, new int[]{8, 920, 3, 1}, new int[]{8, 920, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}, new int[]{8, 952, 3, 1}};
    int[] rbCfgIndexAddrs = {32, 32, 0, 8};
    int[] recordLength = {RfDesenseTxTestCdma.DEFAULT_CHANNEL_VALUE, RfDesenseTxTestCdma.DEFAULT_CHANNEL_VALUE, 256};
    int[][] recordNumAddrs = {new int[]{8, 56, 5}, new int[]{8, 56, 5}, new int[]{8, 56, 5}, new int[]{8, 56, 5}};
    int[] rrcStateAddrs = {8, 0, 8};
    HashMap<Integer, String> rrcStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "null");
            put(1, "idleCampedOnAnyCell");
            put(2, "idleCampedNormally");
            put(3, "idlConnecting");
            put(4, "connectedNormally");
            put(5, "releasing");
            put(6, "atmptOutbndMobility");
            put(7, "atmptInbndMobility");
            put(8, "inactive");
            put(9, "endcScgAdded");
        }
    };
    int[][] rsrpAddrs;
    int[] rsrpAvgAddrs = {8, 56, 0, Cea708CCParser.Const.CODE_C1_SPA, 16};
    int[] rsrpRecordsAddrs = {8, 10, 5};
    int[][] rsrqAddrs;
    int[][] rssiAddrs;
    int[][] sinrAddrs;
    int[] snrAvgAddrs = {8, 56, 0, 64, 16};
    int[][] ssbIndexAddrs;
    int[] ueEndcAddrs = {8, 0, 8};
    int[][] ulBwpSizeAddrs = {new int[]{8, 856, 12, 9}, new int[]{8, 856, 12, 9}, new int[]{8, 856, 12, 9}, new int[]{8, 856, 12, 9}, new int[]{8, 856, 12, 9}, new int[]{8, 1400, 12, 9}, new int[]{8, 1400, 12, 9}, new int[]{8, 1400, 12, 9}, new int[]{8, 1656, 12, 9}, new int[]{8, 1656, 12, 9}, new int[]{8, 1656, 12, 9}, new int[]{8, 1688, 12, 9}, new int[]{8, 1720, 12, 9}, new int[]{8, 1848, 12, 9}, new int[]{8, 1848, 12, 9}, new int[]{8, 1848, 12, 9}, new int[]{8, 1848, 12, 9}, new int[]{8, 1848, 12, 9}};
    int[] ulDataThresholdAddrs = {32, 32, 32, 32};
    int[][] ulSubCarrSpacingAddrs = {new int[]{8, 856, 21, 3}, new int[]{8, 856, 21, 3}, new int[]{8, 856, 21, 3}, new int[]{8, 856, 21, 3}, new int[]{8, 856, 21, 3}, new int[]{8, 1400, 21, 3}, new int[]{8, 1400, 21, 3}, new int[]{8, 1400, 21, 3}, new int[]{8, 1656, 21, 3}, new int[]{8, 1656, 21, 3}, new int[]{8, 1656, 21, 3}, new int[]{8, 1688, 21, 3}, new int[]{8, 1720, 21, 3}, new int[]{8, 1848, 21, 3}, new int[]{8, 1848, 21, 3}, new int[]{8, 1848, 21, 3}, new int[]{8, 1848, 21, 3}, new int[]{8, 1848, 21, 3}};

    /* access modifiers changed from: package-private */
    public void initValue() {
        this.rsrpAddrs = new int[][]{new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}};
        this.rsrqAddrs = new int[][]{new int[]{8, 88, 160, 8}, new int[]{8, 88, 160, 8}, new int[]{8, 88, 128, 8}, new int[]{8, 88, 128, 8}};
        this.rssiAddrs = new int[][]{new int[]{8, 88, 224, 16}, new int[]{8, 88, 224, 16}, new int[]{8, 88, 160, 16}, new int[]{8, 88, 160, 16}};
        this.sinrAddrs = new int[][]{new int[]{8, 88, 320, 8}, new int[]{8, 88, 320, 8}, new int[]{8, 88, 224, 8}, new int[]{8, 88, 224, 8}};
    }

    /* access modifiers changed from: package-private */
    public int getBandWidth(int YYY) {
        if (YYY < 5000) {
            return 5;
        }
        if (YYY < 10000) {
            return 10;
        }
        if (YYY < 15000) {
            return 15;
        }
        if (YYY < 20000) {
            return 20;
        }
        if (YYY < 25000) {
            return 25;
        }
        if (YYY < 30000) {
            return 30;
        }
        if (YYY < 40000) {
            return 40;
        }
        if (YYY < 50000) {
            return 50;
        }
        if (YYY < 60000) {
            return 60;
        }
        if (YYY < 70000) {
            return 70;
        }
        if (YYY < 80000) {
            return 80;
        }
        if (YYY < 90000) {
            return 90;
        }
        if (YYY < 100000) {
            return 100;
        }
        return 0;
    }

    public NRPServingCellInfo2(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell Info 2";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "8. NR EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"RRC Status", "NR ENDC available", "EN-DC Status", "EN-DC Total Tx Pwr", "RSRP(idle)", "SINR(idle)", "RSRP(connected)", "SNR(connected)", "DL Data Split Path", "UL Data Split Path", "NR_DL_256QAM", "Cyclic Prefix", "DL Bandwidth", "UL Bandwidth", "DL Subcarrier Spacing", "UL Subcarrier Spacing", "NR_TA"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        String str2 = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        String str3 = ", values = ";
        String str4 = ", ";
        if (str2.equals(MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT)) {
            initValue();
            int[][] iArr = this.carTypeAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int carType = getFieldValueIcd(icdPacket, version, this.carTypeAddrs);
            int recordNum = getFieldValueIcd(icdPacket, version, this.recordNumAddrs);
            int maxRsrp = -32768;
            int maxSinr = -128;
            String str5 = "EmInfo/MDMComponent";
            int maxRsrq = -128;
            int i = 0;
            int maxRssi = -32768;
            while (i < recordNum) {
                String str6 = str3;
                int recordNum2 = recordNum;
                String str7 = str4;
                int[][] insertElement = insertElement(this.rsrpAddrs, version, this.recordLength, i == 0 ? 0 : -1);
                this.rsrpAddrs = insertElement;
                int rsrp = getFieldValueIcd(icdPacket, version, insertElement, true);
                maxRsrp = rsrp > maxRsrp ? rsrp : maxRsrp;
                int i2 = rsrp;
                int[][] insertElement2 = insertElement(this.rsrqAddrs, version, this.recordLength, i == 0 ? 0 : -1);
                this.rsrqAddrs = insertElement2;
                int rsrq = getFieldValueIcd(icdPacket, version, insertElement2, true);
                maxRsrq = rsrq > maxRsrq ? rsrq : maxRsrq;
                int i3 = rsrq;
                int[][] insertElement3 = insertElement(this.rssiAddrs, version, this.recordLength, i == 0 ? 0 : -1);
                this.rssiAddrs = insertElement3;
                int rssi = getFieldValueIcd(icdPacket, version, insertElement3, true);
                maxRssi = rssi > maxRssi ? rssi : maxRssi;
                int i4 = rssi;
                int[][] insertElement4 = insertElement(this.sinrAddrs, version, this.recordLength, i == 0 ? 0 : -1);
                this.sinrAddrs = insertElement4;
                int sinr = getFieldValueIcd(icdPacket, version, insertElement4, true);
                maxSinr = sinr > maxSinr ? sinr : maxSinr;
                int i5 = sinr;
                this.ssbIndexAddrs = insertElement(this.ssbIndexAddrs, version, this.recordLength, i == 0 ? 0 : -1);
                i++;
                str3 = str6;
                recordNum = recordNum2;
                str4 = str7;
            }
            String str8 = str3;
            String[] strArr = icdLogInfo;
            StringBuilder sb = new StringBuilder();
            sb.append(getName());
            sb.append(" update,name id = ");
            sb.append(str2);
            sb.append(", version = ");
            sb.append(version);
            sb.append(", condition = ");
            sb.append(carType);
            String str9 = str4;
            sb.append(str9);
            sb.append(recordNum);
            sb.append(str8);
            sb.append(0);
            sb.append(str9);
            sb.append(0);
            sb.append(str9);
            sb.append(0);
            sb.append(str9);
            sb.append(maxRsrp);
            sb.append(str9);
            sb.append(maxRsrq);
            sb.append(str9);
            sb.append(maxRssi);
            sb.append(str9);
            sb.append(maxSinr);
            sb.append(str9);
            sb.append(0);
            sb.append(str9);
            sb.append(0);
            sb.append(str9);
            sb.append(0);
            Elog.d(str5, strArr, sb.toString());
            if (carType == 0) {
                setData(4, maxRsrp + "");
                setData(5, maxSinr + "");
            }
            ByteBuffer byteBuffer = icdPacket;
            return;
        }
        String str10 = str3;
        String str11 = "EmInfo/MDMComponent";
        if (str2.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int version2 = getFieldValueIcdVersion(icdPacket, this.dlBwpSizeAddrs[0][0]);
            int dlSubCarrSpacing = getFieldValueIcd(icdPacket, version2, this.dlSubCarrSpacingAddrs);
            int dlBwpSize = getFieldValueIcd(icdPacket, version2, this.dlBwpSizeAddrs);
            int YYY = dlBwpSize * 12 * (15 << dlSubCarrSpacing);
            setData(12, getBandWidth(YYY) + " MHz");
            setData(14, this.BwpMapping.get(Integer.valueOf(dlSubCarrSpacing)) + " kHz");
            int ulSubCarrSpacing = getFieldValueIcd(icdPacket, version2, this.ulSubCarrSpacingAddrs);
            int ulbwpSize = getFieldValueIcd(icdPacket, version2, this.ulBwpSizeAddrs);
            int XXX = ulbwpSize * 12 * (15 << ulSubCarrSpacing);
            int maxMcs = getFieldValueIcd(icdPacket, version2, this.maxMcsAddr);
            String str12 = str11;
            int cpType = getFieldValueIcd(icdPacket, version2, this.cpTypeAddr);
            int i6 = version2;
            ByteBuffer icdPacket2 = icdPacket;
            Elog.d(str12, icdLogInfo, getName() + " update,name id = " + str2 + ", condition = " + YYY + str4 + XXX + str10 + dlSubCarrSpacing + str4 + dlBwpSize + str4 + ulSubCarrSpacing + str4 + ulbwpSize + str4 + maxMcs + str4 + cpType);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getBandWidth(XXX));
            sb2.append(" MHz");
            setData(13, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.BwpMapping.get(Integer.valueOf(ulSubCarrSpacing)));
            sb3.append(" kHz");
            setData(15, sb3.toString());
            setData(10, maxMcs == 1 ? "Enable" : "Disable");
            setData(11, cpType == 0 ? "Normal" : "Extended");
            ByteBuffer byteBuffer2 = icdPacket2;
            return;
        }
        ByteBuffer icdPacket3 = icdPacket;
        String str13 = str11;
        if (str2.equals(MDMContentICD.MSG_ID_NL2_PDCP_CONFIGURATION_EVENT)) {
            ByteBuffer icdPacket4 = icdPacket3;
            int version3 = getFieldValueIcdVersion(icdPacket4, this.cfgRlcEntityAddrs[0]);
            int rbCfgIndex = getFieldValueIcd(icdPacket4, this.rbCfgIndexAddrs);
            int cfgRlcEntity = getFieldValueIcd(icdPacket4, this.cfgRlcEntityAddrs);
            int PRlcEntity = getFieldValueIcd(icdPacket4, this.PRlcEntityAddrs);
            int ulDataThreshold = getFieldValueIcd(icdPacket4, this.ulDataThresholdAddrs);
            Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version3 + ", condition = " + rbCfgIndex + str10 + cfgRlcEntity + str4 + PRlcEntity + str4 + ulDataThreshold);
            if (rbCfgIndex == 255) {
                setData(8, getValueByMapping(this.cfgRlcEntityDLMapping, -1));
                setData(9, getValueByMapping(this.cfgRlcEntityULMapping, -1));
            } else {
                setData(8, getValueByMapping(this.cfgRlcEntityDLMapping, cfgRlcEntity));
                if (cfgRlcEntity == 2) {
                    if (ulDataThreshold != -1) {
                        str = "UL: LTE & NR";
                    } else {
                        str = PRlcEntity == 0 ? "UL: LTE" : "UL: NR";
                    }
                    setData(9, str);
                } else {
                    setData(9, getValueByMapping(this.cfgRlcEntityULMapping, cfgRlcEntity));
                }
            }
            ByteBuffer byteBuffer3 = icdPacket3;
        } else if (str2.equals(MDMContentICD.MSG_ID_NRRC_CAPABILITY_EVENT)) {
            ByteBuffer icdPacket5 = icdPacket3;
            int version4 = getFieldValueIcdVersion(icdPacket5, this.ueEndcAddrs[0]);
            int ueEndc = getFieldValueIcd(icdPacket5, this.ueEndcAddrs);
            Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version4 + str10 + ueEndc);
            setData(1, ueEndc == 0 ? "FALSE" : "TRUE");
        } else {
            ByteBuffer icdPacket6 = icdPacket3;
            if (str2.equals(MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT)) {
                int version5 = getFieldValueIcdVersion(icdPacket6, this.rrcStateAddrs[0]);
                int rrcState = getFieldValueIcd(icdPacket6, this.rrcStateAddrs);
                Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version5 + str10 + rrcState);
                setData(0, getValueByMapping(this.rrcStateMapping, rrcState));
                setData(2, rrcState == 9 ? "ON" : "OFF");
            } else if (str2.equals(MDMContentICD.MSG_ID_NL1_TPC_REPORT)) {
                int[][] iArr2 = this.curEndcPowerAddrs;
                int version6 = getFieldValueIcdVersion(icdPacket6, iArr2[iArr2.length - 1][0]);
                int curEndcPower = getFieldValueIcd(icdPacket6, version6, this.curEndcPowerAddrs, true);
                Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version6 + str10 + curEndcPower);
                setData(3, Integer.valueOf(curEndcPower / 32));
            } else if (str2.equals(MDMContentICD.MSG_ID_NL2_MAC_TIMING_ADVANCE_EVENT)) {
                int[][] iArr3 = this.TAAddrs;
                int version7 = getFieldValueIcdVersion(icdPacket6, iArr3[iArr3.length - 1][0]);
                int TA = getFieldValueIcd(icdPacket6, version7, this.TAAddrs);
                Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version7 + ", value = " + TA);
                setData(16, Integer.valueOf(TA));
            } else if (str2.equals(MDMContentICD.MSG_ID_NL1_SYNC_SERVING_BEAM_MEASUREMENT)) {
                int version8 = getFieldValueIcdVersion(icdPacket6, this.rsrpRecordsAddrs[0]);
                int recordNum3 = getFieldValueIcd(icdPacket6, this.rsrpRecordsAddrs);
                int carIndex = getFieldValueIcd(icdPacket6, this.carIndexAddrs);
                int max_rsrp = -32678;
                int max_snr = -32678;
                int i7 = 0;
                while (i7 < recordNum3) {
                    int recordNum4 = recordNum3;
                    String str14 = str13;
                    int[] rsrpAddrs2 = updateElement(this.rsrpAvgAddrs, i7 * 160, 2);
                    String str15 = str4;
                    int[] snrAddrs = updateElement(this.snrAvgAddrs, i7 * 160, 2);
                    int rsrp2 = getFieldValueIcd(icdPacket6, rsrpAddrs2, true);
                    int[] iArr4 = rsrpAddrs2;
                    int snr = getFieldValueIcd(icdPacket6, snrAddrs, true);
                    max_rsrp = rsrp2 > max_rsrp ? rsrp2 : max_rsrp;
                    max_snr = snr > max_snr ? snr : max_snr;
                    i7++;
                    recordNum3 = recordNum4;
                    str13 = str14;
                    str4 = str15;
                }
                int i8 = recordNum3;
                Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version8 + ", condition = " + carIndex + ", value = " + max_rsrp + str4 + max_snr);
                if (carIndex == 0) {
                    setData(6, Integer.valueOf(max_rsrp));
                    setData(7, Integer.valueOf(max_snr));
                }
            } else {
                Elog.d(str13, icdLogInfo, getName() + " update,name id = " + str2 + " unhandled MSI_ID, return! ");
            }
        }
    }
}
