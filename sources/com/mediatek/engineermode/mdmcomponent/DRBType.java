package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class DRBType extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NL2_PDCP_CONFIGURATION_EVENT};
    int[][] cfgRlcEntityAddr = {new int[]{32, 32, 27, 2}, new int[]{32, 32, 27, 2}, new int[]{32, 32, 27, 2}};
    HashMap<Integer, String> cfgRlcEntityMapping = new HashMap<Integer, String>() {
        {
            put(0, "MCG");
            put(1, " SCG");
            put(2, "SPLIT");
        }
    };

    public DRBType(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "DRB Type";
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
        return new String[]{"DRB TYPE"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int[][] iArr = this.cfgRlcEntityAddr;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int cfgRlcEntity = getFieldValueIcd(icdPacket, version, this.cfgRlcEntityAddr);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", value = " + cfgRlcEntity);
        setData(0, getValueByMapping(this.cfgRlcEntityMapping, cfgRlcEntity));
    }
}
