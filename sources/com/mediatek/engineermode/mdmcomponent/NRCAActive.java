package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRCAActive extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NL2_MAC_CARRIER_AGGREGATION_EVENT};
    int[] caAddrs = {8, 0, 1};
    int sCellNum = -1;
    int[] sCellNumAddrs = {8, 213, 3};

    public NRCAActive(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR CA Active";
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
        return new String[]{"DL CA State"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int version = getFieldValueIcdVersion(icdPacket, this.sCellNumAddrs[0]);
            this.sCellNum = version >= 8 ? getFieldValueIcd(icdPacket, this.sCellNumAddrs) : this.sCellNum;
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + this.sCellNum);
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.caAddrs[0]);
        int ca = getFieldValueIcd(icdPacket, this.caAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", condition = " + this.sCellNum + ", values = " + ca);
        if (this.sCellNum > 0) {
            setData(0, ca == 1 ? "Configured" : "Active");
        } else {
            setData(0, "Release");
        }
    }
}
