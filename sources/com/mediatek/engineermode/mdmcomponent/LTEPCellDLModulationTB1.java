package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class LTEPCellDLModulationTB1 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_PDSCH_DECODING_RESULTS};
    HashMap<Integer, String> ModulationTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "QPSK");
            put(1, "16QAM");
            put(2, "64QAM");
            put(3, "256QAM");
        }
    };
    int[] carrierIndexAddrs = {8, 56, 14, 3};
    int[] crcResultAddrs = {8, 56, 64, 0, 1};
    int[] modIndexAddrs = {8, 56, 64, 20, 5};
    int[] modTypeAddrs = {8, 56, 64, 68, 2};

    public LTEPCellDLModulationTB1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Primary Cell DL Modulation TB1";
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
        int version = getFieldValueIcdVersion(icdPacket, this.crcResultAddrs[0]);
        int crcResult = getFieldValueIcd(icdPacket, this.crcResultAddrs);
        int carrierIndex = getFieldValueIcd(icdPacket, this.carrierIndexAddrs);
        int modType = getFieldValueIcd(icdPacket, this.modTypeAddrs);
        int modIndex = getFieldValueIcd(icdPacket, this.modIndexAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + carrierIndex + ", " + crcResult + ", values = " + modType + ", " + modIndex);
        if (carrierIndex == 0 && crcResult == 1) {
            clearData();
            addData(getValueByMapping(this.ModulationTypeMapping, modType));
            addData(Integer.valueOf(modIndex));
        }
    }

    public void testData() {
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + getName() + ", conditions = " + 0 + ", " + 1 + ", values = " + 1 + ", " + 2);
        if (0 == 0 && 1 == 1) {
            clearData();
            addData(getValueByMapping(this.ModulationTypeMapping, 1));
            addData(2);
        }
    }
}
