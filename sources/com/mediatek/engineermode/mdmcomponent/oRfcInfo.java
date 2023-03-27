package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class oRfcInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NRRC_RLF_EVENT, MDMContentICD.MSG_ID_NRRC_SCG_FAILURE_EVENT, MDMContentICD.MSG_ID_NRRC_DYNAMIC_SPECTRUM_SHARING_CONFIGURATION_EVENT};
    int[][] dlFreBandAddr = {new int[]{8, 8, 16}};
    int[][] dlNrarfcnAddr = {new int[]{8, 24, 32}};
    int[][] dssEnabledAddr = {new int[]{8, 72, 8}};
    String[] icdLogInfo = new String[8];
    int[][] phyCellIdAddr = {new int[]{8, 56, 16}};
    int[][] rlfCauseAddr = {new int[]{8, 8, 8}};
    HashMap<Integer, String> rlfCauseMapping = new HashMap<Integer, String>() {
        {
            put(0, "Physical layer problem");
            put(1, "RA problem");
            put(2, "Maximum RLC re-transmissions");
        }
    };
    int[][] scgFailCauseAddr = {new int[]{8, 8, 8}, new int[]{8, 8, 8}, new int[]{8, 8, 8}, new int[]{8, 8, 8}};
    HashMap<Integer, String> scgFailCauseMapping = new HashMap<Integer, String>() {
        {
            put(0, "t310-Expiry");
            put(1, "randomAccessProblem");
            put(2, "rlc-MaxNumRetx");
            put(2, "synchReconfigFailure-SCG");
            put(2, "scg-reconfigFailure");
            put(2, "srb3-IntegrityFailure");
        }
    };

    public oRfcInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Other RRC information";
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
        return new String[]{"RLF Cause", "SCG Failure Cause", "DSS_STATUS"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_NRRC_RLF_EVENT)) {
            int[][] iArr = this.rlfCauseAddr;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int rlfCause = getFieldValueIcd(icdPacket, version, this.rlfCauseAddr);
            Elog.d("EmInfo/MDMComponent", this.icdLogInfo, getName() + " update,name id = " + name + ", version = " + version + ", value = " + rlfCause);
            setData(0, getValueByMapping(this.rlfCauseMapping, rlfCause));
        } else if (name.equals(MDMContentICD.MSG_ID_NRRC_SCG_FAILURE_EVENT)) {
            int[][] iArr2 = this.scgFailCauseAddr;
            int version2 = getFieldValueIcdVersion(icdPacket, iArr2[iArr2.length - 1][0]);
            int scgFailCause = getFieldValueIcd(icdPacket, version2, this.scgFailCauseAddr);
            Elog.d("EmInfo/MDMComponent", this.icdLogInfo, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + scgFailCause);
            setData(1, getValueByMapping(this.scgFailCauseMapping, scgFailCause));
        } else if (name.equals(MDMContentICD.MSG_ID_NRRC_DYNAMIC_SPECTRUM_SHARING_CONFIGURATION_EVENT)) {
            int[][] iArr3 = this.dlFreBandAddr;
            int version3 = getFieldValueIcdVersion(icdPacket, iArr3[iArr3.length - 1][0]);
            int dlFreBand = getFieldValueIcd(icdPacket, version3, this.dlFreBandAddr);
            int dlNrarfcn = getFieldValueIcd(icdPacket, version3, this.dlNrarfcnAddr);
            int phyCellId = getFieldValueIcd(icdPacket, version3, this.phyCellIdAddr);
            int dssEnabled = getFieldValueIcd(icdPacket, version3, this.dssEnabledAddr);
            Elog.d("EmInfo/MDMComponent", this.icdLogInfo, getName() + " update,name id = " + name + ", version = " + version3 + ", conditions = " + dlFreBand + ", " + dlNrarfcn + ", " + phyCellId + ", values = " + dssEnabled);
            if (dssEnabled == 0) {
                str = "Disable";
            } else {
                str = "Enable, " + dlFreBand + ", " + dlNrarfcn + ", " + phyCellId;
            }
            setData(2, str);
        }
    }
}
