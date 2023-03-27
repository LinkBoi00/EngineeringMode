package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellSDAPThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL2_SDAP_THROUGHPUT_DL, MDMContentICD.MSG_ID_NL2_SDAP_THROUGHPUT_UL};
    int[][] dlSdapThputAddrs = {new int[]{32, 0, 32}, new int[]{32, 32, 64}};
    int[] ulSdapThputAddrs = {32, 0, 32};

    public NRPCellSDAPThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell SDAP Throughput";
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
        return new String[]{"SDAP DL Throughput", "SDAP UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL2_SDAP_THROUGHPUT_DL)) {
            int[][] iArr = this.dlSdapThputAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int dlSdapThput = getFieldValueIcd(icdPacket, version, this.dlSdapThputAddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + dlSdapThput);
            StringBuilder sb = new StringBuilder();
            sb.append(dlSdapThput / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.ulSdapThputAddrs[0]);
        int ulSdapThput = getFieldValueIcd(icdPacket, this.ulSdapThputAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + ulSdapThput);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ulSdapThput / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
