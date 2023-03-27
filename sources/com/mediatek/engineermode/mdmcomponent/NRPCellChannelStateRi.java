package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellChannelStateRi extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_CSI_REPORT};
    int[] riAddrs = {8, 24, 38, 8};
    int[] riValidAddrs = {8, 24, 37, 1};

    public NRPCellChannelStateRi(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Channel State Info - RI";
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
        return new String[]{"RI"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.riAddrs[0]);
        int ri = getFieldValueIcd(icdPacket, this.riAddrs);
        int riValid = getFieldValueIcd(icdPacket, this.riValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + riValid + ", values = " + ri);
        if (riValid == 1) {
            clearData();
            addData(ri + "");
        }
    }
}
