package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponent */
class LteServingCellInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND, MDMContent.MSG_ID_EM_ERRC_SERVING_INFO_IND, MDMContent.MSG_ID_EM_EMM_REG_COMMON_INFO_IND, MDMContent.MSG_ID_EM_ERRC_EL1_CONFIG_INFO_IND, MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO};
    int band;
    int[] cellIdAddrs = {8, 216, 32};
    int cell_id;
    int dlBandwidth;
    int dl_earfcn;
    int earfcn;
    int highSpeed;
    String lac = "-";
    HashMap<Integer, String> mBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "6_RB");
            put(1, "15_RB");
            put(2, "25_RB");
            put(3, "50_RB");
            put(4, "75_RB");
            put(5, "100_RB");
            put(255, "Invalid(-1)");
        }
    };
    int pci;
    int powerClass;
    int rsrp;
    int rsrq;
    int servingValid = 0;
    int sinr;
    int ulBandwidth;
    int ul_earfcn;

    public LteServingCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Serving Cell(Primary Cell) Info";
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
        return new String[]{"PCI", "EARFCN (Band)", "SINR", "RSRP", "RSRQ", "FreqBandInd", MDMContent.DL_BANDWIDTH, MDMContent.UL_BANDWIDTH, "dl_earfcn", "ul_earfcn", "powerclass", "HighSpeed", "Cell ID", "LAC"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        if (name.equals(MDMContent.MSG_ID_EM_ERRC_SERVING_INFO_IND)) {
            this.servingValid = getFieldValue((Msg) msg, "is_serving_inf_valid");
            Elog.d("EmInfo/MDMComponent", "is_serving_inf_valid = " + this.servingValid);
        }
        Elog.d("EmInfo/MDMComponent", "servingValid = " + this.servingValid);
        int i = this.servingValid;
        if (i == 0) {
            clearData();
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
            addData("-");
        } else if (i == 1) {
            String str2 = "";
            if (name.equals(MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND)) {
                Msg data = (Msg) msg;
                this.rsrp = getFieldValue(data, "serving_info." + "rsrp", true);
                this.rsrq = getFieldValue(data, "serving_info." + "rsrq", true);
                this.sinr = getFieldValue(data, "serving_info." + "rs_snr_in_qdb", true);
                this.earfcn = getFieldValue(data, "serving_info." + "earfcn");
                this.pci = getFieldValue(data, "serving_info." + "pci");
                this.band = getFieldValue(data, "serving_info." + "serv_lte_band");
                this.dlBandwidth = getFieldValue(data, "serving_info." + MDMContent.DL_BANDWIDTH, true);
                this.ulBandwidth = getFieldValue(data, "serving_info." + MDMContent.UL_BANDWIDTH, true);
            } else if (name.equals(MDMContent.MSG_ID_EM_ERRC_SERVING_INFO_IND)) {
                Msg data2 = (Msg) msg;
                this.highSpeed = getFieldValue(data2, MDMContent.EM_ERRC_HIGH_SPEED_FLG);
                this.cell_id = getFieldValue(data2, "serv_inf.cell_id");
                this.dl_earfcn = getFieldValue(data2, "serv_inf.dl_earfcn");
                this.ul_earfcn = getFieldValue(data2, "serv_inf.ul_earfcn");
            } else if (name.equals(MDMContent.MSG_ID_EM_ERRC_EL1_CONFIG_INFO_IND)) {
                this.powerClass = getFieldValue((Msg) msg, "powerclass");
            } else if (name.equals(MDMContent.MSG_ID_EM_EMM_REG_COMMON_INFO_IND)) {
                Msg data3 = (Msg) msg;
                this.lac = str2;
                for (int i2 = 0; i2 < 2; i2++) {
                    int lac_value = getFieldValue(data3, "lai.la_code[" + i2 + "]");
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.lac);
                    sb.append(Integer.toHexString(lac_value));
                    this.lac = sb.toString();
                }
            } else if (name.equals(MDMContentICD.MSG_ID_ERRC_SERVING_CELL_INFO)) {
                ByteBuffer icdPacket = (ByteBuffer) msg;
                this.lac = str2;
                int version = getFieldValueIcdVersion(icdPacket, this.cellIdAddrs[0]);
                int lac2 = getFieldValueIcd(icdPacket, this.cellIdAddrs);
                Elog.d("EmInfo/MDMComponent", getName() + " update,name id = " + name + ", version = " + version + ", values = " + lac2);
                clearData();
                addData(Integer.valueOf(lac2));
            }
            clearData();
            Object[] objArr = new Object[1];
            objArr[0] = this.earfcn == -1 ? str2 : Integer.valueOf(this.pci);
            addData(objArr);
            if (this.earfcn == -1) {
                str = str2;
            } else {
                str = "EARFCN: " + this.earfcn + " (Band " + this.band + ")";
            }
            addData(str);
            Object[] objArr2 = new Object[1];
            int i3 = this.sinr;
            objArr2[0] = i3 == -1 ? str2 : Float.valueOf(((float) i3) / 4.0f);
            addData(objArr2);
            Object[] objArr3 = new Object[1];
            int i4 = this.rsrp;
            objArr3[0] = i4 == -1 ? str2 : Float.valueOf(((float) i4) / 4.0f);
            addData(objArr3);
            Object[] objArr4 = new Object[1];
            int i5 = this.rsrq;
            objArr4[0] = i5 == -1 ? str2 : Float.valueOf(((float) i5) / 4.0f);
            addData(objArr4);
            Object[] objArr5 = new Object[1];
            int i6 = this.band;
            Object obj = str2;
            if (i6 != 65535) {
                obj = Integer.valueOf(i6);
            }
            objArr5[0] = obj;
            addData(objArr5);
            addData(this.mBandMapping.get(Integer.valueOf(this.dlBandwidth)) + "(" + this.dlBandwidth + ")");
            addData(this.mBandMapping.get(Integer.valueOf(this.ulBandwidth)) + "(" + this.ulBandwidth + ")");
            addData(Integer.valueOf(this.dl_earfcn));
            addData(Integer.valueOf(this.ul_earfcn));
            addData(Integer.valueOf(this.powerClass));
            addData(Integer.valueOf(this.highSpeed));
            addData(String.format("0x%X", new Object[]{Integer.valueOf(this.cell_id)}));
            addData(this.lac);
        }
    }
}
