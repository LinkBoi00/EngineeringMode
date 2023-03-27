package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.text.NumberFormat;
import java.util.HashMap;

/* compiled from: MDMComponentC2k */
class EvdoSprintInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_SPRINT_EVDO_INFO_IND};
    HashMap<Integer, String> AtMapping = new HashMap<Integer, String>() {
        {
            put(0, "INACTIVE");
            put(1, "ACQUISITION");
            put(2, "SYNC");
            put(3, "IDLE");
            put(4, "ACCESS");
            put(5, "CONNECTED");
            put(6, "STATE NUM");
        }
    };

    public EvdoSprintInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO Sprint info";
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
        return new String[]{"DO_state", "Mac_index", "Channel", "Color_Code", "Sector_ID", "PN", "Rx_Pwr", "Rx_PER", MDMContent.PILOT_ENERGY, "DRC", "SINR", "AN_AAA", "IPv4_Address", "IPv6_Address"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int DO_state = getFieldValue(data, "DO_state");
        int Mac_index = getFieldValue(data, "Mac_index");
        int Channel = getFieldValue(data, "Channel");
        int Color_Code = getFieldValue(data, "Color_Code");
        char[] Sector_ID = new char[34];
        for (int i = 0; i < 34; i++) {
            Sector_ID[i] = (char) getFieldValue(data, "Sector_ID[" + i + "]");
        }
        String Sector_ID_s = new String(Sector_ID);
        int PN = getFieldValue(data, "bandPNClass");
        int Rx_Pwr = getFieldValue(data, "Rx_Pwr", true) / 128;
        int Rx_PER = getFieldValue(data, "Rx_PER");
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        String Rx_PER_DOUBLE = fmt.format(((double) Rx_PER) / 25600.0d);
        int Pilot_Energy = getFieldValue(data, MDMContent.PILOT_ENERGY, true);
        int DRC = getFieldValue(data, "DRC");
        char[] cArr = Sector_ID;
        int i2 = Rx_PER;
        int SINR = getFieldValue(data, "SINR", true);
        int AN_AAA = getFieldValue(data, "AN_AAA");
        NumberFormat numberFormat = fmt;
        int SINR2 = SINR;
        char[] IPv4_Address = new char[16];
        int DRC2 = DRC;
        int i3 = 0;
        for (int i4 = 16; i3 < i4; i4 = 16) {
            IPv4_Address[i3] = (char) getFieldValue(data, "IPv4_Address[" + i3 + "]");
            i3++;
            Pilot_Energy = Pilot_Energy;
        }
        int Pilot_Energy2 = Pilot_Energy;
        String IPv4_Address_s = new String(IPv4_Address);
        char[] IPv6_Address = new char[64];
        char[] cArr2 = IPv4_Address;
        int i5 = 0;
        for (int i6 = 64; i5 < i6; i6 = 64) {
            IPv6_Address[i5] = (char) getFieldValue(data, "IPv6_Address[" + i5 + "]");
            i5++;
            IPv4_Address_s = IPv4_Address_s;
        }
        String IPv6_Address_s = new String(IPv6_Address);
        Elog.d("EmInfo/MDMComponent", "AN_AAA = " + AN_AAA);
        clearData();
        addData(this.AtMapping.get(Integer.valueOf(DO_state)));
        addData(Integer.valueOf(Mac_index));
        addData(Integer.valueOf(Channel));
        addData(Integer.valueOf(Color_Code));
        addData(Sector_ID_s);
        addData(Integer.valueOf(PN));
        addData(Integer.valueOf(Rx_Pwr));
        addData(Rx_PER_DOUBLE);
        addData(Integer.valueOf(Pilot_Energy2));
        addData(Integer.valueOf(DRC2));
        addData(Integer.valueOf(SINR2));
        addData(Integer.valueOf(AN_AAA));
        addData(IPv4_Address_s);
        addData(IPv6_Address_s);
        notifyDataSetChanged();
    }
}
