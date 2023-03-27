package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTESCellPHYThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT1, MDMContentICD.MSG_ID_EL1_UL_THROUGHPUT};
    int dlPHYTp0 = 0;
    int dlPHYTp1 = 0;
    int[] sCellIndexAddrs = {8, 14, 3};
    int[] thputAddrs = {8, 24, 32};

    public LTESCellPHYThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Secondary Cell PHY Throughput";
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
        return new String[]{"Cell index", "PHY DL Throughput", "Cell index", "PHY UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.thputAddrs[0]);
        int PHYTp = getFieldValueIcd(icdPacket, this.thputAddrs);
        int sCellIndex = getFieldValueIcd(icdPacket, this.sCellIndexAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + sCellIndex + ", values = " + PHYTp);
        if (sCellIndex != 0) {
            if (name.equals(MDMContentICD.MSG_ID_EL1_UL_THROUGHPUT)) {
                setData(2, Integer.valueOf(sCellIndex));
                setData(3, (PHYTp / 1000000) + "Mbps");
            } else if (name.equals(MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0)) {
                this.dlPHYTp0 = PHYTp;
            } else {
                this.dlPHYTp1 = PHYTp;
                setData(0, Integer.valueOf(sCellIndex));
                setData(1, ((this.dlPHYTp0 + this.dlPHYTp1) / 1000000) + "Mbps");
            }
        }
    }
}
