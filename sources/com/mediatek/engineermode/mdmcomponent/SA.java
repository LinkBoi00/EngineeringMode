package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class SA extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_TAS_INFORMATION};
    int[][] carrIndexAddrs = {new int[]{8, 56, 0, 3}, new int[]{8, 24, 2, 3}, new int[]{8, 24, 2, 3}};
    int[][] statusAddrs = {new int[]{8, 0, 2}, new int[]{8, 24, 0, 2}, new int[]{8, 24, 0, 2}};
    int[][] txIndex0Addrs = {new int[]{8, 56, 3, 4}, new int[]{8, 24, 5, 5}, new int[]{8, 24, 8, 8}};
    int[][] txIndex1Addrs = {new int[]{8, 56, 8, 4}, new int[]{8, 24, 10, 5}, new int[]{8, 24, 16, 8}};

    public SA(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "SA";
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
        return new String[]{"NR Primary Cell Tx Antenna"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        ByteBuffer icdPacket = (ByteBuffer) msg;
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
        Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + carrIndex + ", " + status + ", values = " + txIndex0 + ", " + txIndex1);
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
        }
    }
}
