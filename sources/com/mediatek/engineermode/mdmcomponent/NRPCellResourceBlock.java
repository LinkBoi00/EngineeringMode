package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellResourceBlock extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_DCI_INFORMATION};
    int[] dlNumRbAddrs = {8, 24, 128, 12, 9};
    int[] dlValidAddrs = {8, 24, 128, 0, 1};
    int[] ulNumRbAddrs = {8, 24, 32, 12, 9};
    int[] ulValidAddrs = {8, 24, 32, 0, 1};

    public NRPCellResourceBlock(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Resource Block";
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
        return new String[]{"DL Resource Block", "UL Resource Block"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.dlNumRbAddrs[0]);
        int dlNumRb = getFieldValueIcd(icdPacket, this.dlNumRbAddrs);
        int dlValid = getFieldValueIcd(icdPacket, this.dlValidAddrs);
        int ulNumRb = getFieldValueIcd(icdPacket, this.ulNumRbAddrs);
        int ulValid = getFieldValueIcd(icdPacket, this.ulValidAddrs);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + dlValid + ", " + ulValid + ", values = " + dlNumRb + ", " + ulNumRb);
        if (dlValid == 1) {
            setData(0, dlNumRb + "");
        }
        if (ulValid == 1) {
            setData(1, ulNumRb + "");
        }
    }
}
