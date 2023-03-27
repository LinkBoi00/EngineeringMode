package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRSessionInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_VGNAS_SM_CONTEXT_INFO};
    String[][] ambrArrays = ((String[][]) Array.newInstance(String.class, new int[]{15, 3}));
    int[][] ambrLegthAddrs = {new int[]{8, 56, 8}, new int[0], new int[0]};
    int[][] dlAmbrAddrs = {new int[]{8, 96, 16}, new int[]{8, 56, 32, 32}, new int[]{8, 1240, 160, 32, 32}};
    int[][] dlAmbrUnit = {new int[]{8, 88, 8}, new int[]{8, 56, 0, 8}, new int[]{8, 1240, 160, 0, 8}};
    int[] sessionIdAddrs = {8, 0, 8};
    int[][] ulAmbrAddrs = {new int[]{8, 72, 16}, new int[]{8, 56, 64, 32}, new int[]{8, 1240, 160, 64, 32}};
    int[][] ulAmbrUnit = {new int[]{8, 64, 8}, new int[]{8, 56, 8, 8}, new int[]{8, 1240, 160, 8, 8}};

    public NRSessionInfo(Activity context) {
        super(context);
        clearData();
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Session - AMBR";
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
        return new String[]{"Session ID", "DL Session-AMBR", "UL Session-AMBR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.ambrLegthAddrs[0][0]);
        int sessionId = getFieldValueIcd(icdPacket, this.sessionIdAddrs);
        int length = getFieldValueIcd(icdPacket, version, this.ambrLegthAddrs);
        int ulUnit = getFieldValueIcd(icdPacket, version, this.ulAmbrUnit);
        int ulAmbr = getFieldValueIcd(icdPacket, version, this.ulAmbrAddrs);
        int dlUnit = getFieldValueIcd(icdPacket, version, this.dlAmbrUnit);
        int dlAmbr = getFieldValueIcd(icdPacket, version, this.dlAmbrAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + sessionId + ", values = " + length + ", " + ulUnit + ", " + ulAmbr + ", " + dlUnit + ", " + dlAmbr);
        StringBuilder sb = new StringBuilder();
        sb.append(sessionId);
        sb.append("");
        StringBuilder sb2 = new StringBuilder();
        sb2.append((dlAmbr * dlUnit) / 1000000);
        sb2.append("");
        StringBuilder sb3 = new StringBuilder();
        sb3.append((ulAmbr * ulUnit) / 1000000);
        sb3.append("");
        this.ambrArrays[sessionId + -1] = new String[]{sb.toString(), sb2.toString(), sb3.toString()};
        if (sessionId == 15) {
            addData(this.ambrArrays);
        }
    }
}
