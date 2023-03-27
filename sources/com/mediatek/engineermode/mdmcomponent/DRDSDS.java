package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class DRDSDS extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_MULTISIM_INFORMATION};
    int[][] drAllowedAddr = {new int[]{8, 0, 1}};
    HashMap<Integer, String> drAllowedMapping = new HashMap<Integer, String>() {
        {
            put(0, "not allowed");
            put(1, " allowed");
        }
    };

    public DRDSDS(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "DR-DSDS";
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
        return new String[]{"DR Allowed"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int[][] iArr = this.drAllowedAddr;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int drAllowed = getFieldValueIcd(icdPacket, version, this.drAllowedAddr);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", value = " + drAllowed);
        setData(0, getValueByMapping(this.drAllowedMapping, drAllowed));
    }
}
