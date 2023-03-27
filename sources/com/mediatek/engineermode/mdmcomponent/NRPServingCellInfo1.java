package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellInfo1 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO, MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NRRC_SERVING_CELL_EVENT, MDMContentICD.MSG_ID_NRRC_SCG_RECONFIGURATION_EVENT};
    int[][] TAAddrs = {new int[]{8, 14, 6}, new int[]{8, 15, 6}, new int[]{8, 15, 6}};
    int[][] TACodeAddrs = {new int[]{8, 56, 24}, new int[]{8, 56, 24}, new int[]{8, 120, 24}};
    int[] UpLayerIndAddrs = {8, 112, 8};
    int[][] carTypeAddrs = {new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 11, 4}};
    int[][] cellGroupIdAddrs = {new int[]{8, 0, 8}, new int[]{8, 0, 8}, new int[]{8, 0, 8}};
    int[][] cellId0Addrs = {new int[]{8, 88, 8}, new int[]{8, 80, 8}, new int[]{8, Cea708CCParser.Const.CODE_C1_SPA, 8}};
    int[][] cellId1Addrs = {new int[]{8, 96, 8}, new int[]{8, 88, 8}, new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 8}};
    int[][] cellId2Addrs = {new int[]{8, 104, 8}, new int[]{8, 96, 8}, new int[]{8, 160, 8}};
    int[][] cellId3Addrs = {new int[]{8, 112, 8}, new int[]{8, 104, 8}, new int[]{8, 168, 8}};
    int[][] cellId4Addrs = {new int[]{8, 120, 8}, new int[]{8, 112, 8}, new int[]{8, 176, 8}};
    int[][] cellIdAddrs = {new int[]{8, 88, 40}, new int[]{8, 80, 36}, new int[]{8, Cea708CCParser.Const.CODE_C1_SPA, 36}};
    int[][] cellMCCAddrs = {new int[]{8, 8, 16}, new int[]{8, 8, 16}, new int[]{8, 24, 16}};
    int[][] cellMNCAddrs = {new int[]{8, 40, 16}, new int[]{8, 40, 16}, new int[]{8, 104, 16}};
    int[][] cellMNCNumAddrs = {new int[]{8, 32, 8}, new int[]{8, 32, 8}, new int[]{8, 96, 8}};
    int[][] dlFreqBandAddr = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 49, 7}, new int[]{8, 49, 7}, new int[]{8, 49, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 8}, new int[]{8, 46, 8}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}};
    int[][] mncNumAddrs = {new int[]{8, 32, 8}, new int[]{8, 32, 8}, new int[]{8, 96, 8}};
    int[] narfcnAddrs = {8, 24, 22};
    int[] pciAddrs = {8, 0, 10};
    int[][] servCellIndexAddrs = {new int[]{8, Cea708CCParser.Const.CODE_C1_DF0, 0, 8}, new int[]{8, 120, 0, 8}, new int[]{8, 184, 0, 8}};
    int[][] srb3ConfStatusAddrs = {new int[0], new int[]{8, 32, 8}};

    public NRPServingCellInfo1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell Info 1";
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
        return new String[]{"NARFCN", "PCI", "Global Cell ID", "Vendor ID", "gNB ID", "Cell ID", "mcc", "mnc", "BAND", "NR-SRB3", "Upper Layer Indication Status-SIB2", "NT_TAC"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket;
        String str;
        int mncVar;
        int mncVar2;
        int mnc1;
        int mnc3;
        int vendorID;
        String str2 = name;
        ByteBuffer icdPacket2 = (ByteBuffer) msg;
        if (str2.equals(MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT)) {
            int[][] iArr = this.carTypeAddrs;
            int version = getFieldValueIcdVersion(icdPacket2, iArr[iArr.length - 1][0]);
            int carType = getFieldValueIcd(icdPacket2, version, this.carTypeAddrs);
            int narfcna = getFieldValueIcd(icdPacket2, this.narfcnAddrs);
            int pci = getFieldValueIcd(icdPacket2, this.pciAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version + ", condition = " + carType + ", values = " + narfcna + ", " + pci);
            if (carType == 0) {
                setData(0, narfcna + "");
                setData(1, pci + "");
            }
            ByteBuffer byteBuffer = icdPacket2;
        } else if (str2.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int[][] iArr2 = this.dlFreqBandAddr;
            setData(8, Integer.valueOf(getFieldValueIcd(icdPacket2, getFieldValueIcdVersion(icdPacket2, iArr2[iArr2.length - 1][0]), this.dlFreqBandAddr)));
            ByteBuffer byteBuffer2 = icdPacket2;
        } else if (str2.equals(MDMContentICD.MSG_ID_NRRC_SERVING_CELL_EVENT)) {
            int[][] iArr3 = this.cellGroupIdAddrs;
            int version2 = getFieldValueIcdVersion(icdPacket2, iArr3[iArr3.length - 1][0]);
            int cellGroupId = getFieldValueIcd(icdPacket2, version2, this.cellGroupIdAddrs);
            int servCellIndex = getFieldValueIcd(icdPacket2, version2, this.servCellIndexAddrs);
            int mncNum = getFieldValueIcd(icdPacket2, version2, this.cellMNCNumAddrs);
            if (cellGroupId == 0 && servCellIndex == 0 && mncNum > 0) {
                int mcc = getFieldValueIcd(icdPacket2, version2, this.cellMCCAddrs);
                int mnc = getFieldValueIcd(icdPacket2, version2, this.cellMNCAddrs);
                Object obj = "";
                String str3 = "EmInfo/MDMComponent";
                int cellGroupId2 = cellGroupId;
                int servCellIndex2 = servCellIndex;
                long cellId = getLongFieldValueIcd(icdPacket2, version2, this.cellIdAddrs, false);
                int cellId0 = getFieldValueIcd(icdPacket2, version2, this.cellId0Addrs);
                int cellId1 = getFieldValueIcd(icdPacket2, version2, this.cellId1Addrs);
                int cellId2 = getFieldValueIcd(icdPacket2, version2, this.cellId2Addrs);
                int cellId3 = getFieldValueIcd(icdPacket2, version2, this.cellId3Addrs);
                int cellId4 = getFieldValueIcd(icdPacket2, version2, this.cellId4Addrs);
                int TACode = getFieldValueIcd(icdPacket2, version2, this.TACodeAddrs);
                icdPacket = icdPacket2;
                int[][] iArr4 = this.cellIdAddrs;
                int index = (version2 <= iArr4.length ? version2 : iArr4.length) - 1;
                int cellId02 = cellId0;
                int TACode2 = TACode;
                String str4 = ", ";
                long j = cellId >> (iArr4[index][iArr4.length - 1] - 22);
                int vendorID2 = (int) ((cellId >> (iArr4[index][iArr4.length - 1] - 2)) & 3);
                int mcc2 = mcc & 4095;
                int mcc3 = mnc & 4095;
                int mnc2 = mcc2 & 15;
                int i = index;
                int mcc22 = (mcc2 & LastCallFailCause.CALL_BARRED) >> 4;
                int mcc1 = (mcc2 & 3840) >> 8;
                int gNBID = (int) (j & 8388607);
                int mccVar = (mcc1 * 100) + (mcc22 * 10) + mnc2;
                if (mncNum == 2) {
                    int mnc12 = (mcc3 & LastCallFailCause.CALL_BARRED) >> 4;
                    int mnc22 = mcc3 & 15;
                    mncVar2 = (mnc12 * 10) + mnc22;
                    mnc1 = mnc12;
                    mncVar = vendorID2;
                    str = ", values = ";
                    vendorID = mnc22;
                    mnc3 = 0;
                } else if (mncNum == 3) {
                    int mnc13 = (mcc3 & 3840) >> 8;
                    int mnc23 = (mcc3 & LastCallFailCause.CALL_BARRED) >> 4;
                    int mnc32 = mcc3 & 15;
                    mncVar2 = (mnc13 * 100) + (mnc23 * 10) + mnc32;
                    mnc1 = mnc13;
                    mncVar = vendorID2;
                    str = ", values = ";
                    vendorID = mnc23;
                    mnc3 = mnc32;
                } else {
                    mncVar2 = 0;
                    mnc1 = 0;
                    mncVar = vendorID2;
                    str = ", values = ";
                    vendorID = 0;
                    mnc3 = 0;
                }
                int mnc33 = mnc3;
                String[] strArr = icdLogInfo;
                StringBuilder sb = new StringBuilder();
                int mnc24 = vendorID;
                sb.append(getName());
                sb.append(" update,name id = ");
                sb.append(str2);
                sb.append(", version = ");
                sb.append(version2);
                sb.append(", condition = ");
                sb.append(cellGroupId2);
                String str5 = str4;
                sb.append(str5);
                int servCellIndex3 = servCellIndex2;
                sb.append(servCellIndex3);
                sb.append(str5);
                sb.append(mncNum);
                sb.append(str5);
                sb.append(cellId);
                sb.append("(");
                int i2 = version2;
                sb.append(Integer.toBinaryString((int) cellId));
                sb.append(")");
                sb.append(mcc2);
                sb.append("(");
                sb.append(Integer.toBinaryString(mcc2));
                sb.append(")");
                sb.append(mcc3);
                sb.append("(");
                sb.append(Integer.toBinaryString(mcc3));
                sb.append("), ");
                sb.append(mcc1);
                sb.append(mcc22);
                sb.append(mnc2);
                sb.append(str5);
                sb.append(mnc1);
                int i3 = servCellIndex3;
                sb.append(mnc24);
                int mnc34 = mnc33;
                sb.append(mnc34);
                String str6 = " update,name id = ";
                sb.append(str);
                sb.append(Integer.toBinaryString(mncVar));
                sb.append(str5);
                int gNBID2 = gNBID;
                sb.append(gNBID2);
                sb.append("(");
                int mnc35 = mnc34;
                sb.append(Integer.toBinaryString(gNBID2));
                sb.append("), ");
                sb.append(cellId);
                sb.append("(");
                sb.append(Long.toBinaryString(cellId));
                sb.append("), ");
                int mncVar3 = mncVar2;
                sb.append(mncVar3);
                sb.append(str5);
                int mccVar2 = mccVar;
                sb.append(mccVar2);
                sb.append(str5);
                int TACode3 = TACode2;
                sb.append(TACode3);
                sb.append(str5);
                sb.append(String.format("%X, %X, %X, %X, %X, %X, %X", new Object[]{Integer.valueOf(mcc2), Integer.valueOf(mcc3), Integer.valueOf(cellId02), Integer.valueOf(cellId1), Integer.valueOf(cellId2), Integer.valueOf(cellId3), Integer.valueOf(cellId4)}));
                String str7 = str3;
                Elog.d(str7, strArr, sb.toString());
                int i4 = mcc2;
                setData(3, Integer.valueOf(mncVar));
                setData(4, Integer.valueOf(gNBID2));
                setData(5, Long.valueOf(cellId));
                setData(6, Integer.valueOf(mccVar2));
                setData(7, Integer.valueOf(mncVar3));
                setData(11, Integer.valueOf(TACode3));
                if (mncNum == 2) {
                    setData(2, String.format("[%X%X%X][%X%X][%X]", new Object[]{Integer.valueOf(mcc1), Integer.valueOf(mcc22), Integer.valueOf(mnc2), Integer.valueOf(mnc1), Integer.valueOf(mnc24), Long.valueOf(cellId)}));
                } else if (mncNum == 3) {
                    setData(2, String.format("[%X%X%X][%X%X%X][%X]", new Object[]{Integer.valueOf(mcc1), Integer.valueOf(mcc22), Integer.valueOf(mnc2), Integer.valueOf(mnc1), Integer.valueOf(mnc24), Integer.valueOf(mnc35), Long.valueOf(cellId)}));
                } else {
                    String[] strArr2 = icdLogInfo;
                    StringBuilder sb2 = new StringBuilder();
                    int i5 = mcc1;
                    sb2.append(getName());
                    sb2.append(str6);
                    int i6 = mnc35;
                    sb2.append(name);
                    sb2.append(" Invalid format, not thing to generate! mncNum = ");
                    sb2.append(mncNum);
                    Elog.d(str7, strArr2, sb2.toString());
                }
            } else {
                icdPacket = icdPacket2;
                int i7 = version2;
                int i8 = cellGroupId;
                int i9 = servCellIndex;
            }
            String str8 = name;
            ByteBuffer byteBuffer3 = icdPacket;
        } else {
            ByteBuffer icdPacket3 = icdPacket2;
            String str9 = "EmInfo/MDMComponent";
            String str10 = " update,name id = ";
            String str11 = ", values = ";
            if (str2.equals(MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO)) {
                ByteBuffer icdPacket4 = icdPacket3;
                int version3 = getFieldValueIcdVersion(icdPacket4, this.UpLayerIndAddrs[0]);
                int UpLayerInd = getFieldValueIcd(icdPacket4, this.UpLayerIndAddrs);
                setData(10, UpLayerInd == 0 ? "FALSE" : "TRUE");
                Elog.d(str9, icdLogInfo, getName() + str10 + str2 + ", version = " + version3 + str11 + UpLayerInd);
                return;
            }
            ByteBuffer icdPacket5 = icdPacket3;
            if (str2.equals(MDMContentICD.MSG_ID_NRRC_SCG_RECONFIGURATION_EVENT)) {
                int[][] iArr5 = this.srb3ConfStatusAddrs;
                int version4 = getFieldValueIcdVersion(icdPacket5, iArr5[iArr5.length - 1][0]);
                int srb3ConfStatus = getFieldValueIcd(icdPacket5, version4, this.srb3ConfStatusAddrs);
                Elog.d(str9, icdLogInfo, getName() + str10 + str2 + ", version = " + version4 + str11 + srb3ConfStatus);
                setData(9, srb3ConfStatus == 0 ? "Not configured" : "Configured");
                return;
            }
            Elog.d(str9, icdLogInfo, getName() + str10 + str2 + " unhandled MSG_ID, return! ");
        }
    }
}
