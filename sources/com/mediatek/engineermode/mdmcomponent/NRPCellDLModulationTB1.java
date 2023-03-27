package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRPCellDLModulationTB1 extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_DCI_INFORMATION};
    HashMap<Integer, String> MCSTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "QPSK");
            put(1, "16QAM");
            put(2, "64QAM");
            put(3, "256QAM");
        }
    };
    int[] dlDciValidAddrs = {8, 24, 128, 0, 1};
    int[][] dlMcsIndexAddrs = {new int[]{8, 24, 128, 96, 5}, new int[]{8, 24, 128, 96, 5}, new int[]{8, 24, 128, 76, 5}, new int[]{8, 24, 128, 76, 5}, new int[]{8, 24, 128, 76, 5}, new int[]{8, 24, 128, 76, 5}};
    int[] dlMcsTypeAddrs = {8, 24, 128, 40, 2};

    public NRPCellDLModulationTB1(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell DL Modulation TB1";
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
        int version = getFieldValueIcdVersion(icdPacket, this.dlMcsIndexAddrs[0][0]);
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
