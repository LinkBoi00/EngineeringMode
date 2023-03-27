package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class LTEPCellULModulation extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_DCI_INFORMATION};
    HashMap<Integer, String> ModulationTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "QPSK");
            put(1, "16QAM");
            put(2, "64QAM");
            put(3, "256QAM");
        }
    };
    int[] grantValidAddrs = {8, 24, 32, 0, 1};
    int[] modIndexAddrs = {8, 24, 32, 1, 5};
    int[] modTypeAddrs = {8, 24, 32, 22, 2};
    int[] scellIndexAddrs = {8, 56, 17, 3};

    public LTEPCellULModulation(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Primary Cell UL Modulation ";
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
        return new String[]{"Modulation Type", "Modulation Index"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.scellIndexAddrs[0]);
        int scellIndex = getFieldValueIcd(icdPacket, this.scellIndexAddrs);
        int grantValid = getFieldValueIcd(icdPacket, this.grantValidAddrs);
        int modType = getFieldValueIcd(icdPacket, this.modTypeAddrs);
        int modIndex = getFieldValueIcd(icdPacket, this.modIndexAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + scellIndex + ", " + grantValid + ", values = " + modType + ", " + modIndex);
        if (scellIndex == 0 && grantValid == 1) {
            clearData();
            addData(getValueByMapping(this.ModulationTypeMapping, modType));
            addData(Integer.valueOf(modIndex));
        }
    }
}
