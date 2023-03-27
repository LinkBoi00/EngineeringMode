package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellPHYThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_MIMO_PDSCH_THROUGHPUT, MDMContentICD.MSG_ID_NL1_UL_THROUGHPUT};
    int[][] dlPhyThput0Addrs = {new int[]{8, 56, 32}, new int[]{8, 56, 32}, new int[]{8, 56, 32}, new int[]{8, 56, 32}, new int[]{8, 312, 32}, new int[]{8, 56, 32}, new int[]{8, 56, 32}, new int[]{8, 56, 32}};
    int[][] dlPhyThput1Addrs = {new int[]{8, 88, 32}, new int[]{8, 88, 32}, new int[]{8, 88, 32}, new int[]{8, 88, 32}, new int[]{8, 344, 32}, new int[]{8, 88, 32}, new int[]{8, 88, 32}};
    int[] ulPhyThputAddrs = {8, 56, 32};

    public NRPCellPHYThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell PHY Throughput";
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
        return new String[]{"PHY DL Throughput", "PHY UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL1_MIMO_PDSCH_THROUGHPUT)) {
            int version = getFieldValueIcdVersion(icdPacket, this.dlPhyThput0Addrs[0][0]);
            int dlPhyThput0 = getFieldValueIcd(icdPacket, version, this.dlPhyThput0Addrs);
            int dlPhyThput1 = getFieldValueIcd(icdPacket, version, this.dlPhyThput1Addrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + dlPhyThput0 + ", " + dlPhyThput1);
            StringBuilder sb = new StringBuilder();
            sb.append(((dlPhyThput0 + dlPhyThput1) * 8) / 1000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.ulPhyThputAddrs[0]);
        int ulPhyThput = getFieldValueIcd(icdPacket, this.ulPhyThputAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + ulPhyThput);
        StringBuilder sb2 = new StringBuilder();
        sb2.append((ulPhyThput * 8) / 1000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
