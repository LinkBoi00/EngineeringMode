package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellChannelStateCqi1 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_CSI_REPORT};
    int[] cqi1Addrs = {8, 24, 28, 4};
    int[] cqi1ValidAddrs = {8, 24, 27, 1};

    public NRPCellChannelStateCqi1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Channel State Info - CQI1";
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
        return new String[]{"CQI1"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.cqi1Addrs[0]);
        int cqi1 = getFieldValueIcd(icdPacket, this.cqi1Addrs);
        int cqi1Valid = getFieldValueIcd(icdPacket, this.cqi1ValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + cqi1Valid + ", values = " + cqi1);
        if (cqi1Valid == 1) {
            clearData();
            addData(cqi1 + "");
        }
    }
}
