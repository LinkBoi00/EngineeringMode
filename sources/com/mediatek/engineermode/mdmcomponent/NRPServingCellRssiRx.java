package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellRssiRx extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SERVING_CELL_RSSI};
    int[] recordLength;
    int[][] recordNumAddrs;
    int[][] rssiRx0Addrs;
    int[][] rssiRx1Addrs;
    int[][] rssiRx2Addrs;
    int[][] rssiRx3Addrs;

    public NRPServingCellRssiRx(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public void initValue() {
        this.rssiRx0Addrs = new int[][]{new int[]{8, 24, 16, 16}, new int[]{8, 40, 16}, new int[]{8, 40, 16}, new int[]{8, 40, 16}, new int[]{8, 40, 16}};
        this.rssiRx1Addrs = new int[][]{new int[]{8, 24, 32, 16}, new int[]{8, 56, 16}, new int[]{8, 56, 16}, new int[]{8, 56, 16}, new int[]{8, 56, 16}};
        this.rssiRx2Addrs = new int[][]{new int[]{8, 24, 48, 16}, new int[]{8, 72, 16}, new int[]{8, 72, 16}, new int[]{8, 72, 16}, new int[]{8, 72, 16}};
        this.rssiRx3Addrs = new int[][]{new int[]{8, 24, 64, 16}, new int[]{8, 88, 16}, new int[]{8, 88, 16}, new int[]{8, 88, 16}, new int[]{8, 88, 16}};
        this.recordNumAddrs = new int[][]{new int[]{8, 19, 5}, new int[0], new int[0], new int[0], new int[0]};
        this.recordLength = new int[]{96, 0};
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell RSSI RX 0/1/2/3";
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
        return new String[]{"SSB - RSSI RX0", "SSB - RSSI RX1", "SSB - RSSI RX2", "SSB - RSSI RX3"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        initValue();
        int[][] iArr = this.rssiRx0Addrs;
        int i = 0;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int recordNum = version == 1 ? getFieldValueIcd(icdPacket, version, this.recordNumAddrs) : 1;
        int maxRssiRx0 = -150;
        int maxRssiRx1 = -150;
        int maxRssiRx2 = -150;
        int maxRssiRx3 = -150;
        int i2 = 0;
        while (i2 < recordNum) {
            int[][] insertElement = insertElement(this.rssiRx0Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rssiRx0Addrs = insertElement;
            int rssiRx0 = getFieldValueIcd(icdPacket, version, insertElement, true);
            maxRssiRx0 = (rssiRx0 == 0 || rssiRx0 <= maxRssiRx0) ? maxRssiRx0 : rssiRx0;
            int[][] insertElement2 = insertElement(this.rssiRx1Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rssiRx1Addrs = insertElement2;
            int rssiRx1 = getFieldValueIcd(icdPacket, version, insertElement2, true);
            maxRssiRx1 = (rssiRx1 == 0 || rssiRx1 <= maxRssiRx1) ? maxRssiRx1 : rssiRx1;
            int[][] insertElement3 = insertElement(this.rssiRx2Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rssiRx2Addrs = insertElement3;
            int rssiRx2 = getFieldValueIcd(icdPacket, version, insertElement3, true);
            maxRssiRx2 = (rssiRx2 == 0 || rssiRx2 <= maxRssiRx2) ? maxRssiRx2 : rssiRx0;
            int[][] iArr2 = this.rssiRx3Addrs;
            int[] iArr3 = this.recordLength;
            if (i2 != 0) {
                i = -1;
            }
            int[][] insertElement4 = insertElement(iArr2, version, iArr3, i);
            this.rssiRx3Addrs = insertElement4;
            int rssiRx3 = getFieldValueIcd(icdPacket, version, insertElement4, true);
            maxRssiRx3 = (rssiRx3 == 0 || rssiRx3 <= maxRssiRx3) ? maxRssiRx3 : rssiRx3;
            i2++;
            i = 0;
        }
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", " + recordNum + ", values = " + maxRssiRx0 + ", " + maxRssiRx2 + ", " + maxRssiRx3 + ", " + maxRssiRx3);
        clearData();
        StringBuilder sb = new StringBuilder();
        sb.append(maxRssiRx0);
        sb.append("");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(maxRssiRx1);
        sb2.append("");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(maxRssiRx2);
        sb3.append("");
        StringBuilder sb4 = new StringBuilder();
        sb4.append(maxRssiRx3);
        sb4.append("");
        addData(sb.toString(), sb2.toString(), sb3.toString(), sb4.toString());
    }
}
