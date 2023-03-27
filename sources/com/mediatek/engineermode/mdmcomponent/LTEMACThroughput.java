package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTEMACThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL2_MAC_THROUGHPUT_DL, MDMContentICD.MSG_ID_EL2_MAC_THROUGHPUT_UL};
    int[] MACTpDlddrs = {8, 24, 32};
    int[] MACTpUlddrs = {8, 24, 32};

    public LTEMACThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE MAC Throughput";
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
        return new String[]{"MAC DL Throughput", "MAC UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_EL2_MAC_THROUGHPUT_DL)) {
            int version = getFieldValueIcdVersion(icdPacket, this.MACTpDlddrs[0]);
            int MACTpDl = getFieldValueIcd(icdPacket, this.MACTpDlddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + MACTpDl);
            StringBuilder sb = new StringBuilder();
            sb.append(MACTpDl / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.MACTpUlddrs[0]);
        int MACTpUl = getFieldValueIcd(icdPacket, this.MACTpUlddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + MACTpUl);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(MACTpUl / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
