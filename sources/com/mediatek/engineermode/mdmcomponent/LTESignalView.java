package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

/* compiled from: MDMComponentICD */
class LTESignalView extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0, MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT1, MDMContentICD.MSG_ID_EL1_PUCCH_CSF};
    int dlBler0 = 0;
    int[] dlBler0Addrs = {8, 56, 7};
    int dlBler1 = 0;
    int[] dlBler1Addrs = {8, 56, 7};
    int lteDlTb0 = 0;
    int lteDlTb1 = 0;
    int[] recordsAddrs = {8, 0, 5};
    int[] riAddrs = {8, 24, 29, 2};
    int[][] tbNum0Addrs = {new int[0], new int[]{8, 63, 10}, new int[]{8, 70, 10}};
    int[][] tbNum1Addrs = {new int[0], new int[]{8, 63, 10}, new int[]{8, 70, 10}};
    int[] tput0Addrs = {8, 24, 32};
    int[] tput1Addrs = {8, 24, 32};

    public LTESignalView(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Signal View";
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
        return new String[]{"PDSCH BLER", "RI"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (str.equals(MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0)) {
            int version = getFieldValueIcdVersion(icdPacket, this.dlBler0Addrs[0]);
            if (version < 2) {
                String[] strArr = icdLogInfo;
                Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + str + ", version = " + version + " not support, return");
                return;
            }
            this.dlBler0 = getFieldValueIcd(icdPacket, this.dlBler0Addrs);
            int[][] iArr = this.tbNum0Addrs;
            this.lteDlTb0 = getFieldValueIcd(icdPacket, iArr[(version > iArr.length ? iArr.length : version) - 1]);
            String[] strArr2 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + str + ", version = " + version + ", condition = " + this.dlBler0 + ", " + this.lteDlTb0);
        } else if (str.equals(MDMContentICD.MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT1)) {
            int version2 = getFieldValueIcdVersion(icdPacket, this.dlBler1Addrs[0]);
            if (version2 < 2) {
                String[] strArr3 = icdLogInfo;
                Elog.d("EmInfo/MDMComponent", strArr3, getName() + " update,name id = " + str + ", version = " + version2 + " not support, return");
                return;
            }
            this.dlBler1 = getFieldValueIcd(icdPacket, this.dlBler1Addrs);
            int fieldValueIcd = getFieldValueIcd(icdPacket, version2, this.tbNum0Addrs);
            this.lteDlTb1 = fieldValueIcd;
            int i = this.dlBler0;
            int i2 = this.lteDlTb0;
            int lteDlNack0 = i * i2;
            int lteDlNack1 = this.dlBler1 * fieldValueIcd;
            int lteDlTb = i2 + fieldValueIcd;
            float lteDlBler = lteDlTb != 0 ? (float) ((lteDlNack0 + lteDlNack1) / lteDlTb) : 0.0f;
            String[] strArr4 = icdLogInfo;
            StringBuilder sb = new StringBuilder();
            int i3 = lteDlTb;
            sb.append(getName());
            sb.append(" update,name id = ");
            sb.append(str);
            sb.append(", version = ");
            sb.append(version2);
            sb.append(", condition = ");
            sb.append(this.dlBler0);
            sb.append(", ");
            sb.append(this.lteDlTb0);
            sb.append(", ");
            sb.append(this.dlBler1);
            sb.append(", ");
            sb.append(this.lteDlTb1);
            sb.append(", values = ");
            sb.append(lteDlNack0);
            sb.append(", ");
            sb.append(lteDlNack1);
            sb.append(", ");
            sb.append(lteDlBler);
            sb.append("%");
            Elog.d("EmInfo/MDMComponent", strArr4, sb.toString());
            DecimalFormat df = new DecimalFormat("#.##");
            setData(0, df.format((double) lteDlBler) + "%");
        } else {
            int[] recordsAddrs2 = {8, 0, 5};
            int version3 = getFieldValueIcdVersion(icdPacket, recordsAddrs2[0]);
            int records = getFieldValueIcd(icdPacket, recordsAddrs2);
            int ri = getFieldValueIcd(icdPacket, new int[]{8, 24, 29, 2});
            String[] strArr5 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr5, getName() + " update,name id = " + str + ", version = " + version3 + ", condition = " + records + ", values = " + ri);
            setData(1, records != 0 ? Integer.valueOf(ri) : "");
        }
    }
}
