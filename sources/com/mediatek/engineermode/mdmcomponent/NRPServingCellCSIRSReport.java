package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPServingCellCSIRSReport extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SERVING_CELL_CSIRS_MEASUREMENT};
    int[][] rsrpAddrs = {new int[]{8, 24, 32, 16}, new int[]{8, 56, 32, 16}, new int[]{8, 56, 32, 16}};
    int[][] sinrAddrs = {new int[]{8, 24, 288, 8}, new int[]{8, 56, 288, 8}, new int[]{8, 56, 288, 8}};

    public NRPServingCellCSIRSReport(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Serving Cell CSI-RS Report";
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
        return new String[]{"CSI-RS RSRP", "SINR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int[][] iArr = this.rsrpAddrs;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int rsrp = getFieldValueIcd(icdPacket, version, this.rsrpAddrs, true);
        int sinr = getFieldValueIcd(icdPacket, version, this.sinrAddrs, true);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", value = " + rsrp + ", " + sinr);
        setData(0, Integer.valueOf(rsrp));
        setData(1, Integer.valueOf(sinr));
    }
}
