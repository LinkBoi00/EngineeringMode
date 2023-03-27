package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellBLER extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_UL_THROUGHPUT, MDMContentICD.MSG_ID_NL1_MIMO_PDSCH_THROUGHPUT};
    int[] dlBlerAddrs = {8, 17, 7};
    int[] ulBlerAddrs = {8, 17, 7};

    public NRPCellBLER(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell BLER";
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
        return new String[]{"DL BLER", "UL BLER"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL1_UL_THROUGHPUT)) {
            int version = getFieldValueIcdVersion(icdPacket, this.ulBlerAddrs[0]);
            int ulBler = getFieldValueIcd(icdPacket, this.ulBlerAddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + ulBler);
            StringBuilder sb = new StringBuilder();
            sb.append(ulBler);
            sb.append("%");
            setData(1, sb.toString());
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.dlBlerAddrs[0]);
        int dlBler = getFieldValueIcd(icdPacket, this.dlBlerAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + dlBler);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(dlBler);
        sb2.append("%");
        setData(0, sb2.toString());
    }
}
