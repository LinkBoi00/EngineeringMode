package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRPCellTxPower extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PUSCH_POWER_CONTROL, MDMContentICD.MSG_ID_NL1_PUCCH_POWER_CONTROL, MDMContentICD.MSG_ID_NL1_SRS_TX_INFORMATION};
    int[] pucchPowerAddrs = {8, 24, 64, 8};
    int[][] puschPowerAddrs = {new int[]{8, 24, 64, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}, new int[]{8, 24, 96, 8}};
    int[] srsPwerAddrs = {8, 24, 96, 16};

    public NRPCellTxPower(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Cell Tx Power";
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
        return new String[]{"PUSCH Tx Power", "PUCCH Tx Power", "SRS Tx Power"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL1_PUSCH_POWER_CONTROL)) {
            int[][] iArr = this.puschPowerAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int puschPower = getFieldValueIcd(icdPacket, version, this.puschPowerAddrs, true);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + puschPower);
            StringBuilder sb = new StringBuilder();
            sb.append(puschPower);
            sb.append("");
            setData(0, sb.toString());
        } else if (name.equals(MDMContentICD.MSG_ID_NL1_PUCCH_POWER_CONTROL)) {
            int version2 = getFieldValueIcdVersion(icdPacket, this.pucchPowerAddrs[0]);
            int pucchPower = getFieldValueIcd(icdPacket, this.pucchPowerAddrs, true);
            String[] strArr2 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + pucchPower);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(pucchPower);
            sb2.append("");
            setData(1, sb2.toString());
        } else {
            int version3 = getFieldValueIcdVersion(icdPacket, this.pucchPowerAddrs[0]);
            int srsPwer = getFieldValueIcd(icdPacket, this.srsPwerAddrs, true);
            String[] strArr3 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr3, getName() + " update,name id = " + name + ", version = " + version3 + ", values = " + srsPwer);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(srsPwer);
            sb3.append("");
            setData(2, sb3.toString());
        }
    }
}
