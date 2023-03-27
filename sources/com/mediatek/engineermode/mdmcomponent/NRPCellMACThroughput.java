package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellMACThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL2_MAC_THROUGHPUT_DL, MDMContentICD.MSG_ID_NL2_MAC_THROUGHPUT_UL};
    int[][] dlMacThputAddrs = {new int[]{8, 24, 32}, new int[]{8, 24, 32}, new int[]{8, 24, 32}, new int[]{8, 56, 64}};
    int[] ulMacThputAddrs = {8, 24, 32};

    public NRPCellMACThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell MAC Throughput";
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
        return new String[]{"MAC DL Throughput", "MAC UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL2_MAC_THROUGHPUT_DL)) {
            int[][] iArr = this.dlMacThputAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int dlMacThput = getFieldValueIcd(icdPacket, version, this.dlMacThputAddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + dlMacThput);
            StringBuilder sb = new StringBuilder();
            sb.append(dlMacThput / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.ulMacThputAddrs[0]);
        int ulMacThput = getFieldValueIcd(icdPacket, this.ulMacThputAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + ulMacThput);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ulMacThput / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
