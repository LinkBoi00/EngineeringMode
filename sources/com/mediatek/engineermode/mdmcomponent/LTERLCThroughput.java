package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTERLCThroughput extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL2_RLC_THROUGHPUT_DL, MDMContentICD.MSG_ID_EL2_RLC_THROUGHPUT_UL};
    int[] RLCTpDlddrs = {8, 24, 32};
    int[] RLCTpUlddrs = {8, 24, 32};

    public LTERLCThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE RLC Throughput";
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
        return new String[]{"RLC DL Throughput", "RLC UL Throughput"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_EL2_RLC_THROUGHPUT_DL)) {
            int version = getFieldValueIcdVersion(icdPacket, this.RLCTpDlddrs[0]);
            int RLCTpDl = getFieldValueIcd(icdPacket, this.RLCTpDlddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + RLCTpDl);
            StringBuilder sb = new StringBuilder();
            sb.append(RLCTpDl / 1000000);
            sb.append("Mbps");
            setData(0, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.RLCTpUlddrs[0]);
        int RLCTpUl = getFieldValueIcd(icdPacket, this.RLCTpUlddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + RLCTpUl);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(RLCTpUl / 1000000);
        sb2.append("Mbps");
        setData(1, sb2.toString());
    }
}
