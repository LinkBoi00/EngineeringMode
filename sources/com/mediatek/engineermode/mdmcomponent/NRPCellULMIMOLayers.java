package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class NRPCellULMIMOLayers extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_DCI_INFORMATION};
    HashMap<Integer, String> MIMOLayersMapping = new HashMap<Integer, String>() {
        {
            put(0, "1 layer");
            put(1, "2 layers");
            put(2, "3 layers");
            put(3, "4 layers");
        }
    };
    int[] dlDciValidAddrs = {8, 24, 128, 0, 1};
    int[][] dlNumsAddrs = {new int[]{8, 24, 128, 94, 2}, new int[]{8, 24, 128, 94, 2}, new int[]{8, 24, 128, 62, 2}, new int[]{8, 24, 128, 62, 2}, new int[]{8, 24, 128, 62, 2}, new int[]{8, 24, 128, 62, 2}};
    int[] ulDciValidAddrs = {8, 24, 32, 0, 1};
    int[][] ulNumsAddrs = {new int[]{8, 24, 32, 71, 1}, new int[]{8, 24, 32, 71, 1}, new int[]{8, 24, 32, 61, 2}, new int[]{8, 24, 32, 61, 2}, new int[]{8, 24, 32, 61, 2}, new int[]{8, 24, 32, 61, 2}};

    public NRPCellULMIMOLayers(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell MIMO Layers";
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
        return new String[]{"DL MIMO Layers", "UL MIMO Layers"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.dlDciValidAddrs[0]);
        int dlDciValid = getFieldValueIcd(icdPacket, this.dlDciValidAddrs);
        int dlNums = getFieldValueIcd(icdPacket, version, this.dlNumsAddrs);
        int ulNums = getFieldValueIcd(icdPacket, version, this.ulNumsAddrs);
        int ulDciValid = getFieldValueIcd(icdPacket, this.ulDciValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", conditions = " + dlDciValid + ", " + ulDciValid + ", values = " + dlNums + ", " + ulNums);
        if (dlDciValid == 1) {
            setData(0, getValueByMapping(this.MIMOLayersMapping, dlNums));
        }
        if (ulDciValid == 1) {
            setData(1, getValueByMapping(this.MIMOLayersMapping, ulNums));
        }
    }
}
