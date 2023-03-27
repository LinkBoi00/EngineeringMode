package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.rfdesense.RfDesenseTxTestCdma;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellRsrqRx extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SERVING_CELL_MEASUREMENT};
    int[][] carTypeAddrs = {new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 10, 3}, new int[]{8, 11, 4}};
    int[] recordLength;
    int[][] recordNumAddrs;
    int[][] rsrqRx0Addrs;
    int[][] rsrqRx1Addrs;
    int[][] rsrqRx2Addrs;
    int[][] rsrqRx3Addrs;

    public NRPServingCellRsrqRx(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell RSRQ RX 0/1/2/3";
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
        return new String[]{"SSB - RSRQ RX0", "SSB - RSRQ RX1", "SSB - RSRQ RX2", "SSB - RSRQ RX3"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void initValue() {
        this.rsrqRx0Addrs = new int[][]{new int[]{8, 88, 168, 8}, new int[]{8, 88, 168, 8}, new int[]{8, 88, 136, 8}, new int[]{8, 88, 136, 8}};
        this.rsrqRx1Addrs = new int[][]{new int[]{8, 88, 176, 8}, new int[]{8, 88, 176, 8}, new int[]{8, 88, Cea708CCParser.Const.CODE_C1_SPA, 8}, new int[]{8, 88, Cea708CCParser.Const.CODE_C1_SPA, 8}};
        this.rsrqRx2Addrs = new int[][]{new int[]{8, 88, 184, 8}, new int[]{8, 88, 184, 8}, new int[0], new int[0]};
        this.rsrqRx3Addrs = new int[][]{new int[]{8, 88, 192, 8}, new int[]{8, 88, 192, 8}, new int[0], new int[0]};
        this.recordNumAddrs = new int[][]{new int[]{8, 56, 5}, new int[]{8, 56, 5}, new int[]{8, 56, 5}, new int[]{8, 56, 5}};
        this.recordLength = new int[]{RfDesenseTxTestCdma.DEFAULT_CHANNEL_VALUE, RfDesenseTxTestCdma.DEFAULT_CHANNEL_VALUE, 256, 256};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        initValue();
        int[][] iArr = this.carTypeAddrs;
        boolean z = true;
        int i = 0;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int carType = getFieldValueIcd(icdPacket, this.carTypeAddrs[0]);
        int recordNum = getFieldValueIcd(icdPacket, version, this.recordNumAddrs);
        int maxRsrqRx0 = -32768;
        int maxRsrqRx1 = -32768;
        int maxRsrqRx2 = -32768;
        int maxRsrqRx3 = -32768;
        int i2 = 0;
        while (i2 < recordNum) {
            int[][] insertElement = insertElement(this.rsrqRx0Addrs, version, this.recordLength, i2 == 0 ? i : -1);
            this.rsrqRx0Addrs = insertElement;
            int rsrqRx0 = getFieldValueIcd(icdPacket, version, insertElement, z);
            maxRsrqRx0 = (rsrqRx0 == 0 || rsrqRx0 <= maxRsrqRx0) ? maxRsrqRx0 : rsrqRx0;
            int[][] iArr2 = this.rsrqRx1Addrs;
            int[] iArr3 = this.recordLength;
            if (i2 != 0) {
                i = -1;
            }
            int[][] insertElement2 = insertElement(iArr2, version, iArr3, i);
            this.rsrqRx1Addrs = insertElement2;
            int rsrqRx1 = getFieldValueIcd(icdPacket, version, insertElement2, z);
            maxRsrqRx1 = (rsrqRx1 == 0 || rsrqRx1 <= maxRsrqRx1) ? maxRsrqRx1 : rsrqRx1;
            int[][] insertElement3 = insertElement(this.rsrqRx2Addrs, version, this.recordLength, i2 == 0 ? 0 : -1);
            this.rsrqRx2Addrs = insertElement3;
            int rsrqRx2 = version <= 2 ? getFieldValueIcd(icdPacket, version, insertElement3, true) : -128;
            maxRsrqRx2 = (rsrqRx2 == 0 || rsrqRx2 <= maxRsrqRx2) ? maxRsrqRx2 : rsrqRx2;
            int[][] insertElement4 = insertElement(this.rsrqRx3Addrs, version, this.recordLength, i2 == 0 ? 0 : -1);
            this.rsrqRx3Addrs = insertElement4;
            int rsrqRx3 = version <= 2 ? getFieldValueIcd(icdPacket, version, insertElement4, true) : -128;
            maxRsrqRx3 = (rsrqRx3 == 0 || rsrqRx3 <= maxRsrqRx3) ? maxRsrqRx3 : rsrqRx3;
            i2++;
            i = 0;
            z = true;
        }
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + carType + ", values = " + maxRsrqRx0 + ", " + maxRsrqRx1 + ", " + maxRsrqRx2 + ", " + maxRsrqRx3);
        if (carType == 0) {
            clearData();
            addData(maxRsrqRx0 + "", maxRsrqRx1 + "", maxRsrqRx2 + "", maxRsrqRx3 + "");
        }
    }
}
