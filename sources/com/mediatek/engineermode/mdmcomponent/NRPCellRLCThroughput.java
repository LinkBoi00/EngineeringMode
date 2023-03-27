package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellRLCThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL2_RLC_THROUGHPUT_DL, MDMContentICD.MSG_ID_NL2_RLC_THROUGHPUT_UL};
    int[][] dlRlcThputAddrs = {new int[]{8, 24, 32}, new int[]{8, 56, 64}};
    int[] ulRlcThputAddrs = {8, 24, 32};

    public NRPCellRLCThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell RLC Throughput";
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
        return new String[]{"RLC DL Throughput", "RLC UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL2_RLC_THROUGHPUT_DL)) {
            int[][] iArr = this.dlRlcThputAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int dlRlcThput = getFieldValueIcd(icdPacket, version, this.dlRlcThputAddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + dlRlcThput);
            StringBuilder sb = new StringBuilder();
            sb.append(dlRlcThput / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.ulRlcThputAddrs[0]);
        int ulRlcThput = getFieldValueIcd(icdPacket, this.ulRlcThputAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + ulRlcThput);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ulRlcThput / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
