package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTEPCellPHYThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT1, MDMContentICD.MSG_ID_EL1_UL_THROUGHPUT};
    int[] PHYTpAddrs = {8, 24, 32};
    int dlPHYTp0 = 0;
    int dlPHYTp1 = 0;
    int[] sCellIndexAddrs = {8, 14, 3};

    public LTEPCellPHYThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Primary Cell PHY Throughput";
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
        return new String[]{"PHY DL Throughput", "PHY UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.PHYTpAddrs[0]);
        int PHYTp = getFieldValueIcd(icdPacket, this.PHYTpAddrs);
        int sCellIndex = getFieldValueIcd(icdPacket, this.sCellIndexAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + sCellIndex + ", values = " + PHYTp);
        if (sCellIndex != 0) {
            return;
        }
        if (name.equals(MDMContentICD.MSG_ID_EL1_UL_THROUGHPUT)) {
            setData(1, (PHYTp / 1000000) + "Mbps");
        } else if (name.equals(MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0)) {
            this.dlPHYTp0 = PHYTp;
            this.dlPHYTp1 = 0;
        } else {
            this.dlPHYTp1 = PHYTp;
            setData(0, ((this.dlPHYTp0 + this.dlPHYTp1) / 1000000) + "Mbps");
        }
    }

    public void testData() {
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + getName() + ", condition = " + 0 + ", values = " + 200000);
        if (0 == 0) {
            setData(0, (200000 / 1000000) + "Mbps");
            setData(1, (200000 / 1000000) + "Mbps");
        }
    }
}
