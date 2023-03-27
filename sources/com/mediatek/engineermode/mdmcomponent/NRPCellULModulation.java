package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRPCellULModulation extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_DCI_INFORMATION};
    HashMap<Integer, String> MCSTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "BPSK");
            put(1, "QPSK");
            put(2, "16QAM");
            put(3, "64QAM");
            put(4, "256QAM");
        }
    };
    int[] ulDciValidAddrs = {8, 24, 32, 0, 1};
    int[][] ulMcsIndexAddrs = {new int[]{8, 24, 32, 40, 5}, new int[]{8, 24, 32, 40, 5}, new int[]{8, 24, 32, 40, 5}, new int[]{8, 24, 32, 40, 5}, new int[]{8, 24, 32, 40, 5}, new int[]{8, 24, 32, 40, 5}};
    int[] ulMcsTypeAddrs = {8, 24, 32, 37, 3};

    public NRPCellULModulation(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell UL Modulation";
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
        return new String[]{"Modulation Type", "Modulation Index"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int[][] iArr = this.ulMcsIndexAddrs;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int ulMcsIndex = getFieldValueIcd(icdPacket, version, this.ulMcsIndexAddrs);
        int ulMcsType = getFieldValueIcd(icdPacket, this.ulMcsTypeAddrs);
        int ulDciValid = getFieldValueIcd(icdPacket, this.ulDciValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + ulDciValid + ", values = " + ulMcsType + ", " + ulMcsIndex);
        if (ulDciValid == 1) {
            clearData();
            addData(getValueByMapping(this.MCSTypeMapping, ulMcsType), ulMcsIndex + "");
        }
    }
}
