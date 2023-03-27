package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class LTEPCellDLModulationTB2 extends NormalTableComponent {
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
    int[] crcResultAddrs = {8, 56, 64, 96, 0, 1};
    int[] modIndexAddrs = {8, 56, 64, 96, 20, 5};
    int[] modTypeAddrs = {8, 56, 64, 96, 68, 2};
    int[] streamNumAddrs = {8, 56, 62, 2};

    public LTEPCellDLModulationTB2(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Primary Cell DL Modulation TB2";
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
        int streamNums = getFieldValueIcd(icdPacket, this.streamNumAddrs);
        int crcResult = getFieldValueIcd(icdPacket, this.crcResultAddrs);
        int carrierIndex = getFieldValueIcd(icdPacket, this.carrierIndexAddrs);
        int modType = getFieldValueIcd(icdPacket, this.modTypeAddrs);
        int modIndex = getFieldValueIcd(icdPacket, this.modIndexAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + carrierIndex + ", " + crcResult + ", " + streamNums + ", values = " + modType + ", " + modIndex);
        if (streamNums > 1 && carrierIndex == 1 && crcResult == 1) {
            clearData();
            addData(getValueByMapping(this.ModulationTypeMapping, modType));
            addData(Integer.valueOf(modIndex));
        }
    }

    public void testData() {
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + getName() + ", conditions = " + 0 + ", " + 1 + ", values = " + 3 + ", " + 2);
        if (0 == 0 && 1 == 1) {
            clearData();
            addData(getValueByMapping(this.ModulationTypeMapping, 3));
            addData(2);
        }
    }
}
