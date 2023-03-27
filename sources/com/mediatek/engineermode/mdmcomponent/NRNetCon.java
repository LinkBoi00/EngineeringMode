package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.bandselect.BandModeContent;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRNetCon extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO, MDMContentICD.MSG_ID_NL1_RACH_INFORMATION, MDMContentICD.MSG_ID_NL1_PUCCH_REPORT, MDMContentICD.MSG_ID_NRRC_SIB_READ_INFO, MDMContentICD.MSG_ID_NL1_PUCCH_POWER_CONTROL, MDMContentICD.MSG_ID_NL1_PUSCH_POWER_CONTROL, MDMContentICD.MSG_ID_NL1_SRS_TX_INFORMATION, MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT};
    HashMap<Integer, Integer> BwpMapping = new HashMap<Integer, Integer>() {
        {
            put(0, 15);
            put(1, 30);
            put(2, 60);
            put(3, 120);
            put(4, Integer.valueOf(LastCallFailCause.CALL_BARRED));
        }
    };
    int[][] bwpSizeAddrs = {new int[]{8, 920, 12, 9}, new int[]{8, 920, 12, 9}, new int[]{8, 1176, 12, 9}, new int[]{8, 1176, 12, 9}, new int[]{8, 1432, 12, 9}, new int[]{8, 1464, 12, 9}, new int[]{8, 1496, 12, 9}, new int[]{8, 1496, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1752, 12, 9}, new int[]{8, 1784, 12, 9}, new int[]{8, 1816, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}, new int[]{8, 1944, 12, 9}};
    int[][] dlBandWidthAddrs = {new int[]{8, 96, 8}, new int[]{8, 96, 8}, new int[]{8, 96, 8}, new int[]{8, 96, 8}};
    int[][] dlFreqAddrs = {new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 32}, new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 32}, new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 32}, new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 32}};
    int[] dlNarfcnAddrs = {8, 24, 22};
    int[] guardPeriodAddrs = {8, 82, 4};
    int[] guardPeriodV3Addrs = {8, 56, 32, 4};
    int[] power08Addrs = {8, 24, 96, 16};
    int[] power0EAddrs = {8, 24, 64, 8};
    int[][] power11Addrs = {new int[]{8, 24, 64, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}};
    int[][] prachConfAddrs = {new int[]{8, 56, 43, 8}, new int[]{8, 56, 43, 8}, new int[]{8, 56, 43, 8}, new int[]{8, 56, 43, 8}, new int[]{8, 56, 43, 8}, new int[]{8, 56, 43, 8}, new int[]{8, 56, 42, 9}};
    int[] prach_x = {16, 16, 16, 16, 8, 8, 8, 8, 4, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 16, 16, 8, 8, 8, 8, 4, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 8, 4, 2, 2, 1, 1, 16, 16, 16, 16, 8, 8, 8, 4, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 8, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 16, 8, 8, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    int[][] preFormatAddrs = {new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}, new int[]{8, 56, 34, 4}};
    HashMap<Integer, String> preFormatMapping = new HashMap<Integer, String>() {
        {
            put(0, "0");
            put(1, "1");
            put(2, "2");
            put(3, "3");
            put(4, "A1");
            put(5, "A2");
            put(6, "A3");
            put(7, "B1");
            put(8, "B2");
            put(9, "B3");
            put(10, "B4");
            put(11, "C0");
            put(12, "C2");
        }
    };
    int[] pucchFormatAddrs = {8, 24, 17, 4};
    int[] rrcStateAddrs = {8, 0, 8};
    int[][] subCarSpacAddrs = {new int[]{8, 920, 21, 3}, new int[]{8, 920, 21, 3}, new int[]{8, 1176, 21, 3}, new int[]{8, 1176, 21, 3}, new int[]{8, 1432, 21, 3}, new int[]{8, 1464, 21, 3}, new int[]{8, 1496, 21, 3}, new int[]{8, 1496, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1752, 21, 3}, new int[]{8, 1784, 21, 3}, new int[]{8, 1816, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}, new int[]{8, 1944, 21, 3}};
    int[] subCarrSpacingAddrs = {8, 56, 0, 8};

    public NRNetCon(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Network Configuration";
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
        return new String[]{"5G Connection", "NR NARFCN", "NR BW", "LTE EARFCN", "LTE BW", "Special Sub-Frame GP", "PRACH Fromat", "PUCCH Format", "PBCH Subcarrier Spacing", "Uplink Power Control PUCCH", "Uplink Power Control PUSCH", "Uplink Power Control SRS"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
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

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (str.equals(MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT)) {
            int version = getFieldValueIcdVersion(icdPacket, this.rrcStateAddrs[0]);
            int rrcState = getFieldValueIcd(icdPacket, this.rrcStateAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version + ", values = " + rrcState);
            setData(0, rrcState == 0 ? "None" : (rrcState < 1 || rrcState > 8) ? "NSA" : "SA");
        } else if (str.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int[][] iArr = this.subCarSpacAddrs;
            int version2 = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int dlNarfcn = getFieldValueIcd(icdPacket, this.dlNarfcnAddrs);
            int guardPeriod = getFieldValueIcd(icdPacket, version2 < 3 ? this.guardPeriodAddrs : this.guardPeriodV3Addrs);
            int[][] iArr2 = this.subCarSpacAddrs;
            int subCarSpac = getFieldValueIcd(icdPacket, iArr2[(version2 <= iArr2.length ? version2 : iArr2.length) - 1]);
            int[][] iArr3 = this.bwpSizeAddrs;
            int bwpSize = getFieldValueIcd(icdPacket, iArr3[(version2 <= iArr3.length ? version2 : iArr3.length) - 1]);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version2 + ", condition = " + subCarSpac + ", values = " + dlNarfcn + ", " + guardPeriod + ", " + bwpSize);
            if (dlNarfcn < 0 || dlNarfcn > 599999) {
                if (600000 <= dlNarfcn && dlNarfcn <= 2016666) {
                }
            }
            setData(1, Integer.valueOf(dlNarfcn));
            setData(2, getBandWidth(bwpSize * 12 * (15 << subCarSpac)) + " MHz");
            setData(5, Integer.valueOf(guardPeriod));
        } else if (str.equals(MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO)) {
            int[][] iArr4 = this.dlFreqAddrs;
            int version3 = getFieldValueIcdVersion(icdPacket, iArr4[iArr4.length - 1][0]);
            long reqBandInd = getLongFieldValueIcd(icdPacket, version3, this.dlFreqAddrs, false);
            long bandWidth = (long) getFieldValueIcd(icdPacket, version3, this.dlBandWidthAddrs, false);
            String[] strArr = icdLogInfo;
            StringBuilder sb = new StringBuilder();
            sb.append(getName());
            sb.append(" update,name id = ");
            sb.append(str);
            sb.append(", version = ");
            sb.append(version3);
            sb.append(", values = ");
            long reqBandInd2 = reqBandInd;
            sb.append(reqBandInd2);
            sb.append(",");
            sb.append(bandWidth);
            Elog.d("EmInfo/MDMComponent", strArr, sb.toString());
            String str2 = "";
            setData(3, reqBandInd2 == BandModeContent.LTE_MAX_VALUE ? str2 : Long.valueOf(reqBandInd2));
            if (bandWidth != 255) {
                str2 = (bandWidth / 10) + " MHz";
            }
            setData(4, str2);
        } else if (str.equals(MDMContentICD.MSG_ID_NL1_RACH_INFORMATION)) {
            int[][] iArr5 = this.preFormatAddrs;
            int version4 = getFieldValueIcdVersion(icdPacket, iArr5[iArr5.length - 1][0]);
            int preFormat = getFieldValueIcd(icdPacket, version4, this.preFormatAddrs);
            int prachConf = getFieldValueIcd(icdPacket, version4, this.prachConfAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version4 + ", values = " + preFormat + "," + prachConf);
            setData(6, getValueByMapping(this.preFormatMapping, preFormat));
        } else if (str.equals(MDMContentICD.MSG_ID_NL1_PUCCH_REPORT)) {
            int version5 = getFieldValueIcdVersion(icdPacket, this.pucchFormatAddrs[0]);
            int pucchFormat = getFieldValueIcd(icdPacket, this.pucchFormatAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version5 + ", values = " + pucchFormat);
            setData(7, Integer.valueOf(pucchFormat));
        } else if (str.equals(MDMContentICD.MSG_ID_NRRC_SIB_READ_INFO)) {
            int version6 = getFieldValueIcdVersion(icdPacket, this.subCarrSpacingAddrs[0]);
            int subCarrSpacing = getFieldValueIcd(icdPacket, this.subCarrSpacingAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version6 + ", values = " + subCarrSpacing);
            setData(8, Integer.valueOf(subCarrSpacing));
        } else if (str.equals(MDMContentICD.MSG_ID_NL1_PUCCH_POWER_CONTROL)) {
            int version7 = getFieldValueIcdVersion(icdPacket, this.power0EAddrs[0]);
            int power = getFieldValueIcd(icdPacket, this.power0EAddrs, true);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version7 + ", values = " + power);
            setData(9, Integer.valueOf(power));
        } else if (str.equals(MDMContentICD.MSG_ID_NL1_PUSCH_POWER_CONTROL)) {
            int[][] iArr6 = this.power11Addrs;
            int version8 = getFieldValueIcdVersion(icdPacket, iArr6[iArr6.length - 1][0]);
            int power2 = getFieldValueIcd(icdPacket, version8, this.power11Addrs, true);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version8 + ", values = " + power2);
            setData(10, Integer.valueOf(power2));
        } else {
            int version9 = getFieldValueIcdVersion(icdPacket, this.power08Addrs[0]);
            int power3 = getFieldValueIcd(icdPacket, this.power08Addrs, true);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str + ", version = " + version9 + ", values = " + power3);
            setData(11, Integer.valueOf(power3));
        }
    }
}
