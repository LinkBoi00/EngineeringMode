package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NSA extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_TAS_INFORMATION, MDMContentICD.MSG_ID_NL1_TAS_INFORMATION};
    int[][] carrIndexAddrs = {new int[]{8, 56, 0, 3}, new int[]{8, 24, 2, 3}, new int[]{8, 24, 2, 3}};
    int[] servCellIndexAddrs = {8, 24, 0, 3};
    int[][] statusAddrs = {new int[]{8, 0, 2}, new int[]{8, 24, 0, 2}, new int[]{8, 24, 0, 2}};
    int[][] txAntAddrs = {new int[]{8, 24, 3, 3}, new int[]{8, 24, 3, 4}};
    int[][] txIndex0Addrs = {new int[]{8, 56, 3, 4}, new int[]{8, 24, 5, 5}, new int[]{8, 24, 8, 8}};
    int[][] txIndex1Addrs = {new int[]{8, 56, 8, 4}, new int[]{8, 24, 10, 5}, new int[]{8, 24, 16, 8}};

    public NSA(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NSA";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "9. Tx Antenna Info";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"NR Primary Cell Tx Antenna", "LTE Primary Cell Tx Antenna"};
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
        if (str2.equals(MDMContentICD.MSG_ID_NL1_TAS_INFORMATION)) {
            int[][] iArr = this.carrIndexAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int[][] iArr2 = this.carrIndexAddrs;
            int carrIndex = getFieldValueIcd(icdPacket, iArr2[(version < iArr2.length ? version : iArr2.length) - 1]);
            int[][] iArr3 = this.txIndex0Addrs;
            int txIndex0 = getFieldValueIcd(icdPacket, iArr3[(version < iArr3.length ? version : iArr3.length) - 1]);
            int[][] iArr4 = this.txIndex1Addrs;
            int txIndex1 = getFieldValueIcd(icdPacket, iArr4[(version < iArr4.length ? version : iArr4.length) - 1]);
            int[][] iArr5 = this.statusAddrs;
            int status = getFieldValueIcd(icdPacket, iArr5[(version < iArr5.length ? version : iArr5.length) - 1]);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version + ", condition = " + carrIndex + ", " + status + ", values = " + txIndex0 + ", " + txIndex1);
            if (carrIndex == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(txIndex0);
                if (status == 2) {
                    str = "," + txIndex1;
                } else {
                    str = "";
                }
                sb.append(str);
                setData(0, sb.toString());
                return;
            }
            return;
        }
        int[][] iArr6 = this.txAntAddrs;
        int version2 = getFieldValueIcdVersion(icdPacket, iArr6[iArr6.length - 1][0]);
        int servCellIndex = getFieldValueIcd(icdPacket, this.servCellIndexAddrs);
        int txAnt = getFieldValueIcd(icdPacket, version2, this.txAntAddrs);
        Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version2 + ", condition = " + servCellIndex + ", values = " + txAnt);
        if (servCellIndex == 0) {
            setData(1, Integer.valueOf(txAnt));
        }
    }
}
