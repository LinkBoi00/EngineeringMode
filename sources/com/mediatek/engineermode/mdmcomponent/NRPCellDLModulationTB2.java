package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRPCellDLModulationTB2 extends NormalTableComponent {
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
    int[] dlDciValidAddrs = {8, 24, 128, 0, 1};
    int[][] dlMcsIndexAddrs = {new int[]{8, 24, 128, 101, 5}, new int[]{8, 24, 128, 101, 5}, new int[]{8, 24, 128, 81, 5}, new int[]{8, 24, 128, 81, 5}, new int[]{8, 24, 128, 81, 5}, new int[]{8, 24, 128, 81, 5}};
    int[] dlMcsTypeAddrs = {8, 24, 128, 45, 2};

    public NRPCellDLModulationTB2(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell DL Modulation TB2";
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
        int[][] iArr = this.dlMcsIndexAddrs;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int dlMcsIndex = getFieldValueIcd(icdPacket, version, this.dlMcsIndexAddrs);
        int dlMcsType = getFieldValueIcd(icdPacket, this.dlMcsTypeAddrs);
        int dlDciValid = getFieldValueIcd(icdPacket, this.dlDciValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + dlDciValid + ", values = " + dlMcsType + ", " + dlMcsIndex);
        if (dlDciValid == 1) {
            clearData();
            addData(getValueByMapping(this.MCSTypeMapping, dlMcsType), dlMcsIndex + "");
        }
    }
}
