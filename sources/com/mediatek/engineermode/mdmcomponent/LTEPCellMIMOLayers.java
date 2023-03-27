package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.io.PrintStream;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class LTEPCellMIMOLayers extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_PDSCH_DECODING_RESULTS, MDMContentICD.MSG_ID_EL1_PUSCH_REPORT};
    int[] dlCarIndexAddrs = {8, 56, 14, 3};
    int[] dlCrcResultddrs = {8, 56, 64, 0, 1};
    int[] dlLayerAddrs = {8, 56, 51, 3};
    int[] ulCarIndexAddrs = {8, 9, 3};
    int[] ulLayerAddrs = {8, 24, 101, 1};

    public LTEPCellMIMOLayers(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE Primary Cell MIMO Layers";
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
        return new String[]{" DL MIMO Layers", "UL MIMO Layers"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (str.equals(MDMContentICD.MSG_ID_EL1_PDSCH_DECODING_RESULTS)) {
            int version = getFieldValueIcdVersion(icdPacket, this.dlCrcResultddrs[0]);
            int dlCrcResult = getFieldValueIcd(icdPacket, this.dlCrcResultddrs);
            int dlCarIndex = getFieldValueIcd(icdPacket, this.dlCarIndexAddrs);
            int dlLayer = getFieldValueIcd(icdPacket, this.dlLayerAddrs);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + str + ", version = " + version + ", conditions = " + dlCrcResult + ", " + dlCarIndex + ", values = " + dlLayer);
            if (dlCrcResult == 1 && dlCarIndex == 0) {
                setData(0, dlLayer + "");
                return;
            }
            return;
        }
        int version2 = getFieldValueIcdVersion(icdPacket, this.ulCarIndexAddrs[0]);
        int ulCarIndex = getFieldValueIcd(icdPacket, this.ulCarIndexAddrs);
        int ulLayer = getFieldValueIcd(icdPacket, this.ulLayerAddrs);
        String[] strArr2 = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + str + ", version = " + version2 + ", conditions = " + ulCarIndex + ", values = " + ulLayer);
        if (ulCarIndex == 0) {
            setData(1, ulLayer + "");
        }
    }

    public void testData() {
        PrintStream printStream = System.out;
        printStream.println(" update update,name id = 700D conditions = " + 1 + ", " + 0 + ", values = " + 3);
        if (1 == 1 && 0 == 0) {
            setData(0, 3 + "");
        }
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = 7014, version = 1, conditions = " + 0 + ", values = " + 2);
        if (0 == 0) {
            setData(1, 2 + "");
        }
    }
}
