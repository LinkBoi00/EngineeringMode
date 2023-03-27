package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_4.DataCallFailCause;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRSCellInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT};
    HashMap<Integer, String> bandWidthMapping = new HashMap<Integer, String>() {
        {
            put(0, "5MHz");
            put(1, "10MHz");
            put(2, "15MHz");
            put(3, "20MHz");
            put(4, "25MHz");
            put(5, "30MHz");
            put(6, "40MHz");
            put(7, "50MHz");
            put(8, "60MHz");
            put(9, "80MHz");
            put(10, "90MHz");
            put(11, "100MHz");
            put(12, "200MHz");
            put(13, "400MHz");
        }
    };
    int[][] bwAddrs = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 1848, 0, 672, 12, 9}, new int[]{8, DataCallFailCause.PDP_DUPLICATE, 0, 672, 12, 9}, new int[]{8, DataCallFailCause.RRC_CONNECTION_INVALID_REQUEST, 0, 672, 12, 9}, new int[]{8, DataCallFailCause.PPP_CHAP_FAILURE, 0, 928, 24, 4}, new int[]{8, 2328, 0, 928, 24, 4}, new int[]{8, 3032, 0, 928, 24, 4}, new int[]{8, 3160, 0, 1056, 24, 4}, new int[]{8, 3160, 0, 1056, 24, 4}, new int[]{8, 3160, 0, 1056, 24, 4}, new int[]{8, 3160, 0, 1056, 24, 4}, new int[]{8, 3160, 0, 1056, 24, 4}};
    int[][] carrierTypeAddrs = {new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 11, 4}};
    int[][] dlBandSizeAddrs = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 1848, 0, 32, 7}, new int[]{8, DataCallFailCause.PDP_DUPLICATE, 0, 32, 7}, new int[]{8, DataCallFailCause.RRC_CONNECTION_INVALID_REQUEST, 0, 32, 7}, new int[]{8, DataCallFailCause.PPP_CHAP_FAILURE, 0, 32, 7}, new int[]{8, 2328, 0, 32, 8}, new int[]{8, 3032, 0, 32, 8}, new int[]{8, 3160, 0, 32, 9}, new int[]{8, 3160, 0, 32, 9}, new int[]{8, 3160, 0, 32, 9}, new int[]{8, 3160, 0, 32, 9}, new int[]{8, 3160, 0, 32, 9}};
    int[][] pciAddrs = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 1848, 0, 0, 10}, new int[]{8, DataCallFailCause.PDP_DUPLICATE, 0, 0, 10}, new int[]{8, DataCallFailCause.RRC_CONNECTION_INVALID_REQUEST, 0, 0, 10}, new int[]{8, DataCallFailCause.PPP_CHAP_FAILURE, 0, 0, 10}, new int[]{8, 2328, 0, 0, 10}, new int[]{8, 3032, 0, 0, 10}, new int[]{8, 3160, 0, 0, 10}, new int[]{8, 3160, 0, 0, 10}, new int[]{8, 3160, 0, 0, 10}, new int[]{8, 3160, 0, 0, 10}, new int[]{8, 3160, 0, 0, 10}};
    int[] recordLength = {0, 0, 0, 0, 0, 0, 0, 768, 768, 768, 1152, 1152, 1152, 1280, 1952, 1952, 1952, 1952};
    int[][] rsrpAddrs = {new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}, new int[]{8, 88, 64, 16}};
    int[][] rsrqAddrs = {new int[]{8, 88, 160, 8}, new int[]{8, 88, 160, 8}, new int[]{8, 88, 128, 8}, new int[]{8, 88, 128, 8}};
    int[][] rssiAddrs = {new int[]{8, 88, 224, 16}, new int[]{8, 88, 224, 16}, new int[]{8, 88, 160, 16}, new int[]{8, 88, 160, 16}};
    int[][] scellNumAddrs = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}, new int[]{8, 213, 3}};
    int[][] sinrAddrs = {new int[]{8, 88, 320, 8}, new int[]{8, 88, 320, 8}, new int[]{8, 88, 224, 8}, new int[]{8, 88, 224, 8}};

    public NRSCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "SCell Info";
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
        return new String[]{"INDEX", "BAND", "BW", "PCI", "RSRP", "RSRQ", "SINR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        String[] indexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        String str2 = ",";
        String str3 = ", ";
        String str4 = "EmInfo/MDMComponent";
        if (str.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int[][] iArr = this.bwAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int scellNum = getFieldValueIcd(icdPacket, version, this.scellNumAddrs);
            String[] bandArray = new String[9];
            String[] bwArray = new String[9];
            String[] pciArray = new String[9];
            int i = 0;
            while (true) {
                String[] indexArray2 = indexArray;
                if (i < scellNum) {
                    int[][] iArr2 = this.dlBandSizeAddrs;
                    int scellNum2 = scellNum;
                    int bandSizeIndex = (version > iArr2.length ? iArr2.length : version) - 1;
                    String str5 = str4;
                    String str6 = str2;
                    String str7 = str3;
                    iArr2[bandSizeIndex] = updateElement(iArr2[bandSizeIndex], this.recordLength[bandSizeIndex] * i, 2);
                    bandArray[i] = "" + getFieldValueIcd(icdPacket, version, this.dlBandSizeAddrs);
                    int[][] iArr3 = this.bwAddrs;
                    int bwIndex = (version > iArr3.length ? iArr3.length : version) - 1;
                    int i2 = bandSizeIndex;
                    String[] bandArray2 = bandArray;
                    iArr3[bwIndex] = updateElement(iArr3[bwIndex], this.recordLength[bwIndex] * i, 2);
                    bwArray[i] = "" + getFieldValueIcd(icdPacket, version, this.bwAddrs);
                    int[][] iArr4 = this.pciAddrs;
                    int pciIndex = (version > iArr4.length ? iArr4.length : version) - 1;
                    int i3 = bwIndex;
                    iArr4[pciIndex] = updateElement(iArr4[pciIndex], this.recordLength[pciIndex] * i, 2);
                    pciArray[i] = "" + getFieldValueIcd(icdPacket, version, this.pciAddrs);
                    i++;
                    indexArray = indexArray2;
                    scellNum = scellNum2;
                    str4 = str5;
                    str2 = str6;
                    str3 = str7;
                    bandArray = bandArray2;
                } else {
                    int i4 = scellNum;
                    String[] bandArray3 = bandArray;
                    String[] strArr = icdLogInfo;
                    Elog.d(str4, strArr, getName() + " update,name id = " + str + ", version = " + version + ", values = " + Arrays.toString(bandArray3) + str3 + Arrays.toString(bwArray) + str2 + Arrays.toString(pciArray));
                    setDataAtPosition(0, indexArray2);
                    setDataAtPosition(1, bandArray3);
                    setDataAtPosition(2, bwArray);
                    setDataAtPosition(3, pciArray);
                    ByteBuffer byteBuffer = icdPacket;
                    return;
                }
            }
        } else {
            String str8 = str4;
            String str9 = str2;
            if (str.equals(MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT)) {
                int[][] iArr5 = this.carrierTypeAddrs;
                int version2 = getFieldValueIcdVersion(icdPacket, iArr5[iArr5.length - 1][0]);
                int carrierType = getFieldValueIcd(icdPacket, version2, this.carrierTypeAddrs);
                int rsrp = getFieldValueIcd(icdPacket, version2, this.rsrpAddrs);
                int rsrq = getFieldValueIcd(icdPacket, version2, this.rsrqAddrs);
                int sinr = getFieldValueIcd(icdPacket, version2, this.sinrAddrs);
                ByteBuffer byteBuffer2 = icdPacket;
                Elog.d(str8, icdLogInfo, getName() + " update,name id = " + str + ", version = " + version2 + ", condition = " + carrierType + ", values = " + rsrp + str3 + rsrq + str9 + sinr);
                setDataAtPosition(0, indexArray);
                setDataAtPosition(carrierType, 4, Integer.valueOf(rsrp));
                setDataAtPosition(carrierType, 5, Integer.valueOf(rsrq));
                setDataAtPosition(carrierType, 6, Integer.valueOf(sinr));
                return;
            }
        }
    }

    public void testData() {
        String[] bandArray = new String[8];
        String[] bwArray = new String[8];
        String[] pciArray = new String[8];
        for (int i = 0; i < 8; i++) {
            int[][] iArr = this.dlBandSizeAddrs;
            iArr[1] = updateElement(iArr[1], (i * 768) + 0, -2);
            bandArray[i] = i + "";
            int[][] iArr2 = this.bwAddrs;
            iArr2[1] = updateElement(iArr2[1], (i * 768) + 0, -2);
            bwArray[i] = (i * 2) + "";
            int[][] iArr3 = this.pciAddrs;
            iArr3[1] = updateElement(iArr3[1], (i * 768) + 0, -2);
            pciArray[i] = (i + 12) + "";
        }
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = 9014, version = " + 1 + ", values = " + Arrays.toString(bandArray) + ", " + Arrays.toString(bwArray) + "," + Arrays.toString(pciArray));
        setDataAtPosition(0, bandArray);
        setDataAtPosition(1, bwArray);
        setDataAtPosition(2, pciArray);
    }
}
