package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.app.NotificationManagerCompat;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponentC2k */
class Cdma1xrttInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_INFO_IND};
    HashMap<Integer, String> CPMapping = new HashMap<Integer, String>() {
        {
            put(0, "CP_DISABLED");
            put(1, "CP_SYS_DETERMINATION");
            put(2, "CP_PILOT_ACQUISITION");
            put(3, "CP_SYNC_ACQUISITION");
            put(4, "CP_TIMING_CHANGE");
            put(5, "CP_IDLE");
            put(6, "CP_UPDATE_OHD_INFO");
            put(7, "CP_ORD_MSG_RESP");
            put(8, "CP_ORIGINATION");
            put(9, "CP_REGISTRATION");
            put(10, "CP_MSG_TRANSMISSION");
            put(11, "CP_TC_INIT");
            put(12, "CP_TC_WAIT_ORDER");
            put(13, "CP_TC_WAIT_ANSWER");
            put(14, "CP_TC_CONVERSATION");
            put(15, "CP_TC_RELEASE");
            put(16, "CP_NST");
            put(17, "CP_FROZEN");
            put(18, "CP_TC_FROZEN");
        }
    };
    HashMap<Integer, String> StateMapping = new HashMap<Integer, String>() {
        {
            put(0, "CALIBRATED");
            put(1, "NOT_CALIBRATED");
            put(2, "FILE_NOT_EXIST");
        }
    };
    HashMap<Integer, String> ValueMapping = new HashMap<Integer, String>() {
        {
            put(0, "FALSE");
            put(1, "TRUE");
        }
    };

    public Cdma1xrttInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "7. CDMA EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"cp_state", "rf_file_cal_state", "RfFileMajorVersion", "RfFileMinorVersion", "RfFileValueVersion", "RfFileCustVersion", "sid", "nid", "sys_det_ind", "reg_zone", "base_lat", "base_long", "nwk_pref_sci", "qpch_mode", "mcc", "imsi_11_12", "pkt_zone_id", "service_option", "t_add", "t_drop", "t_comp", "t_tdrop"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    private String decodeMcc(int value) {
        int value2 = value + 111;
        if (value2 % 10 == 0) {
            value2 -= 10;
        }
        if ((value2 / 10) % 10 == 0) {
            value2 -= 100;
        }
        if ((value2 / 100) % 10 == 0) {
            value2 += NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
        return ("000" + value2).substring(String.valueOf(value2).length());
    }

    private String decodeMnc(int value) {
        int value2 = value + 11;
        if (value2 % 10 == 0) {
            value2 -= 10;
        }
        if ((value2 / 10) % 10 == 0) {
            value2 -= 100;
        }
        return ("00" + value2).substring(String.valueOf(value2).length());
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int cp_state = getFieldValue(data, "cp_state");
        int rf_file_cal_state = getFieldValue(data, "rf_file_cal_state");
        int major_ver = getFieldValue(data, "rf_file_ver.major_ver");
        int minor_ver = getFieldValue(data, "rf_file_ver.minor_ver");
        int value_ver = getFieldValue(data, "rf_file_ver.value_ver");
        int cust_ver = getFieldValue(data, "rf_file_ver.cust_ver");
        int sid = getFieldValue(data, "sid");
        int nid = getFieldValue(data, "nid");
        int sys_det_ind = getFieldValue(data, "sys_det_ind");
        int reg_zone = getFieldValue(data, "reg_zone");
        int base_lat = getFieldValue(data, "base_lat");
        int base_long = getFieldValue(data, "base_long");
        int nwk_pref_sci = getFieldValue(data, "nwk_pref_sci");
        int qpch_mode = getFieldValue(data, "qpch_mode");
        int mcc = getFieldValue(data, "mcc");
        int imsi_11_12 = getFieldValue(data, "imsi_11_12");
        int pkt_zone_id = getFieldValue(data, "pkt_zone_id");
        int service_option = getFieldValue(data, "service_option");
        int t_add = getFieldValue(data, "t_add");
        int t_drop = getFieldValue(data, "t_drop");
        int t_comp = getFieldValue(data, "t_comp");
        int t_tdrop = getFieldValue(data, "t_tdrop");
        Msg msg2 = data;
        StringBuilder sb = new StringBuilder();
        int nwk_pref_sci2 = nwk_pref_sci;
        sb.append("t_tdrop = ");
        sb.append(t_tdrop);
        Elog.d("EmInfo/MDMComponent", sb.toString());
        clearData();
        addData(this.CPMapping.get(Integer.valueOf(cp_state)));
        addData(this.StateMapping.get(Integer.valueOf(rf_file_cal_state)));
        addData(Integer.valueOf(major_ver));
        addData(Integer.valueOf(minor_ver));
        addData(Integer.valueOf(value_ver));
        addData(Integer.valueOf(cust_ver));
        addData(Integer.valueOf(sid));
        addData(Integer.valueOf(nid));
        addData(Integer.valueOf(sys_det_ind));
        addData(Integer.valueOf(reg_zone));
        addData(Integer.valueOf(base_lat));
        addData(Integer.valueOf(base_long));
        addData(Integer.valueOf(nwk_pref_sci2));
        addData(this.ValueMapping.get(Integer.valueOf(qpch_mode)));
        addData(decodeMcc(mcc));
        addData(decodeMnc(imsi_11_12));
        int i = cp_state;
        addData(Integer.valueOf(pkt_zone_id));
        addData(Integer.valueOf(service_option));
        int t_add2 = t_add;
        int t_add3 = rf_file_cal_state;
        addData(Float.valueOf(((float) t_add2) / -2.0f));
        int t_drop2 = t_drop;
        int t_drop3 = t_add2;
        addData(Float.valueOf(((float) t_drop2) / -2.0f));
        addData(Integer.valueOf(t_comp));
        addData(Integer.valueOf(t_tdrop));
        notifyDataSetChanged();
    }
}
