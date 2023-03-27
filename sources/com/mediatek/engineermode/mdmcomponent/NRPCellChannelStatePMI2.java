package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellChannelStatePMI2 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_CSI_REPORT};
    int[] pmi2Addrs = {8, 24, 64, 4};
    int[] pmi2ValidAddrs = {8, 24, 63, 1};

    public NRPCellChannelStatePMI2(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Channel State Info - PMI2";
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
        return new String[]{"PMI2"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.pmi2Addrs[0]);
        int pmi2 = getFieldValueIcd(icdPacket, this.pmi2Addrs);
        int pmi2Valid = getFieldValueIcd(icdPacket, this.pmi2ValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + pmi2Valid + ", values = " + pmi2);
        if (pmi2Valid == 1) {
            clearData();
            addData(pmi2 + "");
        }
    }
}
