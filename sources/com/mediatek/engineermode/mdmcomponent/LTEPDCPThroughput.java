package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTEPDCPThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL2_PDCP_THROUGHPUT_DL, MDMContentICD.MSG_ID_EL2_PDCP_THROUGHPUT_UL};
    int[] PDCPTpDlddrs = {32, 0, 32};
    int[] PDCPTpUlddrs = {32, 0, 32};

    public LTEPDCPThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE PDCP Throughput";
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
        return new String[]{"PDCP DL Throughput", "PDCP UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_EL2_PDCP_THROUGHPUT_DL)) {
            int version = getFieldValueIcdVersion(icdPacket, this.PDCPTpDlddrs[0]);
            int PDCPTpDl = getFieldValueIcd(icdPacket, this.PDCPTpDlddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + PDCPTpDl);
            StringBuilder sb = new StringBuilder();
            sb.append(PDCPTpDl / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.PDCPTpUlddrs[0]);
        int PDCPTpUl = getFieldValueIcd(icdPacket, this.PDCPTpUlddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + PDCPTpUl);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(PDCPTpUl / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
