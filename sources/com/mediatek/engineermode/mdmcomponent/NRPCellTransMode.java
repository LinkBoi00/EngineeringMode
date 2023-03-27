package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellTransMode extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION};
    int[][] antTransModeAddrs = {new int[]{8, 824, 0, 4}, new int[]{8, 824, 0, 4}, new int[]{8, 1080, 0, 4}, new int[]{8, 1080, 0, 4}, new int[]{8, 1336, 0, 4}, new int[]{8, 1368, 0, 4}, new int[]{8, 1368, 0, 4}, new int[]{8, 1368, 0, 4}, new int[]{8, 1624, 0, 4}, new int[]{8, 1624, 0, 4}, new int[]{8, 1624, 0, 4}, new int[]{8, 1656, 0, 4}, new int[]{8, 1656, 0, 4}, new int[]{8, 1784, 0, 4}, new int[]{8, 1784, 0, 4}, new int[]{8, 1784, 0, 4}, new int[]{8, 1784, 0, 4}, new int[]{8, 1784, 0, 4}};

    public NRPCellTransMode(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Transmission Mode";
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
        return new String[]{"Transmission Mode"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.antTransModeAddrs[0][0]);
        int antTransMode = getFieldValueIcd(icdPacket, version, this.antTransModeAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + antTransMode);
        clearData();
        StringBuilder sb = new StringBuilder();
        sb.append(antTransMode);
        sb.append("");
        addData(sb.toString());
    }
}
