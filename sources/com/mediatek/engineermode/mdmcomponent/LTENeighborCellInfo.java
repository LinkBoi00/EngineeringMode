package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTENeighborCellInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_NEIGHBOR_CELL_MEASUREMENT};
    int[][] earfcnAddrs = {new int[]{8, 0, 16}, new int[]{8, 0, 17}, new int[]{8, 0, 17}};
    int[][] pciAddrs = {new int[]{8, 56, 32, 0, 9}, new int[]{8, 56, 32, 0, 9}, new int[]{8, 56, 32, 0, 9}};
    int[][] recordsAddrs = {new int[]{8, 24, 5}, new int[]{8, 24, 5}, new int[]{8, 24, 5}};

    public LTENeighborCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Neighbor Cell info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "5. LTE EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"EARFCN", "PCI"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.pciAddrs[0][0]);
        int earfcn = getFieldValueIcd(icdPacket, version, this.earfcnAddrs);
        int pci = getFieldValueIcd(icdPacket, version, this.pciAddrs);
        int records = getFieldValueIcd(icdPacket, version, this.recordsAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + records + ", values = " + earfcn + ", " + pci);
        Object obj = "";
        setData(0, earfcn != 0 ? Integer.valueOf(earfcn) : obj);
        if (records != 0) {
            obj = Integer.valueOf(pci);
        }
        setData(1, obj);
    }
}
