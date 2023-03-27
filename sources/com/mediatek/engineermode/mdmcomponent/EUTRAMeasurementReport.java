package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class EUTRAMeasurementReport extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO, MDMContentICD.MSG_ID_ERRC_MEAS_REPORT_INFO};
    int ucDlBandWidth = 0;
    int ucFreqBandInd = 0;
    int ucUlBandWidth = 0;
    int wMCC = 0;
    int wMNC = 0;
    int wServEarfcn = 0;
    int wServPhysCellId = 0;
    int wServRsrp = 0;
    int wServRsrq = 0;
    int wServRssnr = 0;

    public EUTRAMeasurementReport(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EUTRAMeasurementReport";
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
        return new String[]{MDMContentICD.MSG_VALUE_ICD_MCC, MDMContentICD.MSG_VALUE_ICD_MNC, "ucFreqBandInd", "ucDlBandwidth", "ucUlBandwidth", "wServEarfcn", "wServPhysCellId", "ucServRsrp", "ucServRsrq", "ucServRssnr"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        clearData();
        Elog.d("EmInfo/MDMComponent", "EUTRAMeasurementReport update,name id = " + name);
        if (name.equals(MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO)) {
            int version = getFieldValueIcdVersion(icdPacket, 8);
            this.wMCC = getFieldValueIcd(icdPacket, 64, 16, false);
            this.wMNC = getFieldValueIcd(icdPacket, 80, 16, false);
            this.ucFreqBandInd = getFieldValueIcd(icdPacket, 16, 16, false);
            this.ucDlBandWidth = getFieldValueIcd(icdPacket, 104, 8, false);
            this.ucUlBandWidth = getFieldValueIcd(icdPacket, 112, 8, false);
            Elog.d("EmInfo/MDMComponent", "version = " + version);
        } else if (name.equals(MDMContentICD.MSG_ID_ERRC_MEAS_REPORT_INFO)) {
            int version2 = getFieldValueIcdVersion(icdPacket, 8);
            this.wServEarfcn = getFieldValueIcd(icdPacket, 32, 32, false);
            this.wServPhysCellId = getFieldValueIcd(icdPacket, 16, 16, false);
            this.wServRsrp = getFieldValueIcd(icdPacket, 64, 8, false);
            this.wServRsrq = getFieldValueIcd(icdPacket, 72, 8, false);
            this.wServRssnr = getFieldValueIcd(icdPacket, 80, 8, false);
            Elog.d("EmInfo/MDMComponent", "version = " + version2);
        }
        addData(Integer.valueOf(this.wMCC));
        addData(Integer.valueOf(this.wMNC));
        addData(Integer.valueOf(this.ucFreqBandInd));
        addData(Integer.valueOf(this.ucDlBandWidth));
        addData(Integer.valueOf(this.ucUlBandWidth));
        addData(Integer.valueOf(this.wServEarfcn));
        addData(Integer.valueOf(this.wServPhysCellId));
        addData(Integer.valueOf(this.wServRsrp));
        addData(Integer.valueOf(this.wServRsrq));
        addData(Integer.valueOf(this.wServRssnr));
    }
}
