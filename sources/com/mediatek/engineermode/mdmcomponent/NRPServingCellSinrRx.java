package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellSinrRx extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SYNC_SSB_SNR};
    int[][] carTypeAddrs;
    int[] recordLength;
    int[][] recordNumAddrs;
    int[][] sinrRx0Addrs;
    int[][] sinrRx1Addrs;
    int[][] sinrRx2Addrs;
    int[][] sinrRx3Addrs;

    public NRPServingCellSinrRx(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public void initValue() {
        this.sinrRx0Addrs = new int[][]{new int[0], new int[]{8, 24, 0, 16}, new int[]{8, 24, 0, 16}};
        this.sinrRx1Addrs = new int[][]{new int[0], new int[]{8, 24, 16, 16}, new int[]{8, 24, 16, 16}};
        this.sinrRx2Addrs = new int[][]{new int[0], new int[]{8, 24, 32, 16}, new int[]{8, 24, 32, 16}};
        this.sinrRx3Addrs = new int[][]{new int[0], new int[]{8, 24, 48, 16}, new int[]{8, 24, 48, 16}};
        this.carTypeAddrs = new int[][]{new int[0], new int[]{8, 15, 3}, new int[]{8, 16, 4}};
        this.recordNumAddrs = new int[][]{new int[]{8, 10, 5}, new int[]{8, 10, 5}, new int[]{8, 10, 5}};
        this.recordLength = new int[]{96, 192, 192};
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell SINR RX 0/1/2/3";
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
        return new String[]{"SSB - SINR RX0", "SSB - SINR RX1", "SSB - SINR RX2", "SSB - SINR RX3"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        initValue();
        int[][] iArr = this.carTypeAddrs;
        boolean z = true;
        int i = 0;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int carType = getFieldValueIcd(icdPacket, version, this.carTypeAddrs);
        int recordNum = getFieldValueIcd(icdPacket, version, this.recordNumAddrs);
        int maxRx0 = -32768;
        int maxRx1 = -32768;
        int maxRx2 = -32768;
        int maxRx3 = -32768;
        int i2 = 0;
        while (i2 < recordNum) {
            int[][] insertElement = insertElement(this.sinrRx0Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.sinrRx0Addrs = insertElement;
            int sinrRx0 = getFieldValueIcd(icdPacket, version, insertElement, z);
            maxRx0 = (sinrRx0 == 0 || sinrRx0 <= maxRx0) ? maxRx0 : sinrRx0;
            int[][] insertElement2 = insertElement(this.sinrRx1Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.sinrRx1Addrs = insertElement2;
            int sinrRx1 = getFieldValueIcd(icdPacket, version, insertElement2, z);
            maxRx1 = (sinrRx1 == 0 || sinrRx1 <= maxRx1) ? maxRx1 : sinrRx1;
            int[][] iArr2 = this.sinrRx2Addrs;
            int[] iArr3 = this.recordLength;
            if (i2 != 0) {
                i = -1;
            }
            int[][] insertElement3 = insertElement(iArr2, version, iArr3, i);
            this.sinrRx2Addrs = insertElement3;
            int sinrRx2 = getFieldValueIcd(icdPacket, version, insertElement3, z);
            maxRx2 = (sinrRx2 == 0 || sinrRx2 <= maxRx2) ? maxRx2 : sinrRx2;
            int[][] insertElement4 = insertElement(this.sinrRx3Addrs, version, this.recordLength, i2 == 0 ? 0 : -1);
            this.sinrRx3Addrs = insertElement4;
            int sinrRx3 = getFieldValueIcd(icdPacket, version, insertElement4, true);
            maxRx3 = (sinrRx3 == 0 || sinrRx3 <= maxRx3) ? maxRx3 : sinrRx3;
            i2++;
            i = 0;
            z = true;
        }
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + carType + ", values = " + maxRx0 + ", " + maxRx1 + ", " + maxRx2 + ", " + maxRx3);
        if (carType == 0) {
            clearData();
            addData(maxRx0 + "", maxRx1 + "", maxRx2 + "", maxRx3 + "");
        }
    }
}
