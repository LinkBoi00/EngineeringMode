package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class SRSTxStatus extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_SRS_TX_INFORMATION, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_ID_EL1_SRS_TX_INFORMATION};
    int[] ltesrsTriggerTypeAddrs = {8, 24, 64, 2};
    int[][] srsResourceUsageAddrs = {new int[]{8, 664, 4, 2}, new int[]{8, 664, 4, 2}, new int[]{8, 664, 4, 2}, new int[]{8, 664, 4, 2}, new int[]{8, 664, 4, 2}, new int[]{8, 696, 4, 2}, new int[]{8, 696, 4, 2}, new int[]{8, 696, 4, 2}, new int[]{8, 952, 4, 2}, new int[]{8, 952, 4, 2}, new int[]{8, 952, 4, 2}, new int[]{8, 984, 4, 2}, new int[]{8, 984, 4, 2}, new int[]{8, 1112, 4, 2}, new int[]{8, 1112, 4, 2}, new int[]{8, 1112, 4, 2}, new int[]{8, 1112, 4, 2}, new int[]{8, 1112, 4, 2}};
    int[][] srsTriggerTypeAddrs = {new int[]{8, 24, 64, 2}, new int[]{8, 24, 54, 2}, new int[]{8, 24, 54, 2}, new int[]{8, 24, 54, 2}, new int[]{8, 24, 54, 2}};
    HashMap<Integer, String> srsTriggerTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "Type 0");
            put(1, "Type 1 DCI0");
            put(1, "Type 1 DCI1A2B2C");
            put(1, "Type 1 DCI4");
        }
    };

    public SRSTxStatus(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "SRS Tx Status";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "9. Tx Antenna Info";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"NR Trigger Type", "NR Antenna Switch support", "LTE Trigger Type"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NL1_SRS_TX_INFORMATION)) {
            int[][] iArr = this.srsTriggerTypeAddrs;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int[][] iArr2 = this.srsTriggerTypeAddrs;
            int srsTriggerType = getFieldValueIcd(icdPacket, iArr2[(version < iArr2.length ? version : iArr2.length) - 1]);
            String[] strArr = icdLogInfo;
            StringBuilder sb = new StringBuilder();
            sb.append(getName());
            sb.append(" update,name id = ");
            sb.append(name);
            sb.append(", version = ");
            sb.append(version);
            sb.append(", condition = ");
            sb.append(srsTriggerType);
            sb.append(", values = ");
            String str2 = "periodic";
            if (srsTriggerType == 0) {
                str = str2;
            } else {
                str = "aperiodic: " + srsTriggerType;
            }
            sb.append(str);
            Elog.d("EmInfo/MDMComponent", strArr, sb.toString());
            if (srsTriggerType != 0) {
                str2 = "aperiodic: " + srsTriggerType;
            }
            setData(0, str2);
        } else if (name.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int[][] iArr3 = this.srsResourceUsageAddrs;
            int version2 = getFieldValueIcdVersion(icdPacket, iArr3[iArr3.length - 1][0]);
            int srsResourceUsage = getFieldValueIcd(icdPacket, version2, this.srsResourceUsageAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + srsResourceUsage);
            setData(1, Integer.valueOf(srsResourceUsage));
        } else if (name.equals(MDMContentICD.MSG_ID_EL1_SRS_TX_INFORMATION)) {
            int[][] iArr4 = this.srsTriggerTypeAddrs;
            int version3 = getFieldValueIcdVersion(icdPacket, iArr4[iArr4.length - 1][0]);
            int ltesrsTriggerType = getFieldValueIcd(icdPacket, this.ltesrsTriggerTypeAddrs);
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + name + ", version = " + version3 + ", condition = " + ltesrsTriggerType);
            setData(2, getValueByMapping(this.srsTriggerTypeMapping, ltesrsTriggerType));
        }
    }
}
