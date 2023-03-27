package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellRsrpRx extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SYNC_SSB_SNR};
    int[][] carTypeAddrs;
    int[] recordLength;
    int[][] recordNumAddrs;
    int[][] rsrpRx0Addrs;
    int[][] rsrpRx1Addrs;
    int[][] rsrpRx2Addrs;
    int[][] rsrpRx3Addrs;

    public NRPServingCellRsrpRx(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell RSRP RX 0/1/2/3";
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
    public void initValue() {
        this.carTypeAddrs = new int[][]{new int[]{8, 15, 3}, new int[]{8, 15, 3}, new int[]{8, 16, 4}};
        this.rsrpRx0Addrs = new int[][]{new int[0], new int[]{8, 24, 80, 16}, new int[]{8, 24, 80, 16}};
        this.rsrpRx1Addrs = new int[][]{new int[0], new int[]{8, 24, 96, 16}, new int[]{8, 24, 96, 16}};
        this.rsrpRx2Addrs = new int[][]{new int[0], new int[]{8, 24, 112, 16}, new int[]{8, 24, 112, 16}};
        this.rsrpRx3Addrs = new int[][]{new int[0], new int[]{8, 24, 128, 16}, new int[]{8, 24, 128, 16}};
        this.recordNumAddrs = new int[][]{new int[]{8, 10, 5}, new int[]{8, 10, 5}, new int[]{8, 10, 5}};
        this.recordLength = new int[]{96, 192, 192};
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"SSB - RSRP RX0", "SSB - RSRP RX1", "SSB - RSRP RX2", "SSB - RSRP RX3"};
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
            int[][] insertElement = insertElement(this.rsrpRx0Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rsrpRx0Addrs = insertElement;
            int rsrpRx0 = getFieldValueIcd(icdPacket, version, insertElement, z);
            maxRx0 = (rsrpRx0 == 0 || rsrpRx0 <= maxRx0) ? maxRx0 : rsrpRx0;
            int[][] insertElement2 = insertElement(this.rsrpRx1Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rsrpRx1Addrs = insertElement2;
            int rsrpRx1 = getFieldValueIcd(icdPacket, version, insertElement2, z);
            maxRx1 = (rsrpRx1 == 0 || rsrpRx1 <= maxRx1) ? maxRx1 : rsrpRx1;
            int[][] iArr2 = this.rsrpRx2Addrs;
            int[] iArr3 = this.recordLength;
            if (i2 != 0) {
                i = -1;
            }
            int[][] insertElement3 = insertElement(iArr2, version, iArr3, i);
            this.rsrpRx2Addrs = insertElement3;
            int rsrpRx2 = getFieldValueIcd(icdPacket, version, insertElement3, z);
            maxRx2 = (rsrpRx2 == 0 || rsrpRx2 <= maxRx2) ? maxRx2 : rsrpRx2;
            int[][] insertElement4 = insertElement(this.rsrpRx3Addrs, version, this.recordLength, i2 == 0 ? 0 : -1);
            this.rsrpRx3Addrs = insertElement4;
            int rsrpRx3 = getFieldValueIcd(icdPacket, version, insertElement4, true);
            maxRx3 = (rsrpRx3 == 0 || rsrpRx3 <= maxRx3) ? maxRx3 : rsrpRx3;
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
