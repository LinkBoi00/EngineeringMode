package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponentC2k */
class EvdoServingInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_SERVING_INFO_IND};

    public EvdoServingInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO Serving Info";
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
        return new String[]{"bandClass", "Channel", "pilotPN", "PhySubtype", "rssi_dbm", "div_rssi", "tx_Ant", "SectorID", "SubnetMask", "ColorCode", "UATI", "PilotInc", "ActiveSetWindow", "NeighborSetWindow", "RemainSetWindow", "sameFreq_T_ADD", "sameFreq_T_DROP", "sameFreq_T_tDROP", "diffFreq_T_ADD", "diffFreq_T_DROP", "diffFreq_T_tDROP"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int band_class = getFieldValue(data, MDMContent.C2K_L4_BAND_CLASS);
        int channel = getFieldValue(data, MDMContent.C2K_L4_CHANNEL);
        int pilotPN = getFieldValue(data, "pilotPN");
        int phy_subtype = getFieldValue(data, "phy_subtype", true);
        int rssi_dbm = getFieldValue(data, "rssi_dbm", true) / 128;
        int div_rssi = getFieldValue(data, "div_rssi", true) / 128;
        int tx_ant_id = getFieldValue(data, "tx_ant_id", true);
        char[] sectorlD = new char[34];
        for (int i = 0; i < 34; i++) {
            sectorlD[i] = (char) getFieldValue(data, "sectorlD[" + i + "]");
        }
        String sectorlD_s = new String(sectorlD);
        int subnetMask = getFieldValue(data, "subnetMask");
        int colorCode = getFieldValue(data, "colorCode", true);
        char[] uati = new char[34];
        char[] cArr = sectorlD;
        int i2 = 0;
        for (int i3 = 34; i2 < i3; i3 = 34) {
            uati[i2] = (char) getFieldValue(data, "uati[" + i2 + "]");
            i2++;
            colorCode = colorCode;
        }
        int colorCode2 = colorCode;
        String uati_s = new String(uati);
        int pilotlnc = getFieldValue(data, "pilotlnc", true);
        int activeSetSchWin = getFieldValue(data, "activeSetSchWin", true);
        char[] cArr2 = uati;
        int neighborSetSchWin = getFieldValue(data, "neighborSetSchWin", true);
        int remainSetSchWin = getFieldValue(data, "remainSetSchWin");
        int neighborSetSchWin2 = neighborSetSchWin;
        int sameFreq_T_ADD = getFieldValue(data, "sameFreq_T_ADD", true);
        int sameFreq_T_DROP = getFieldValue(data, "sameFreq_T_DROP", true);
        int sameFreq_T_tDROP = getFieldValue(data, "sameFreq_T_tDROP", true);
        int diffFreq_T_ADD = getFieldValue(data, "diffFreq_T_ADD", true);
        int diffFreq_T_DROP = getFieldValue(data, "diffFreq_T_DROP", true);
        int diffFreq_T_tDROP = getFieldValue(data, "diffFreq_T_tDROP", true);
        StringBuilder sb = new StringBuilder();
        Msg msg2 = data;
        sb.append("band_class = ");
        sb.append(band_class);
        Elog.d("EmInfo/MDMComponent", sb.toString());
        clearData();
        addData(Integer.valueOf(band_class));
        addData(Integer.valueOf(channel));
        addData(Integer.valueOf(pilotPN));
        addData(Integer.valueOf(phy_subtype));
        addData(Integer.valueOf(rssi_dbm));
        addData(Integer.valueOf(div_rssi));
        addData(Integer.valueOf(tx_ant_id));
        addData(sectorlD_s);
        addData(Integer.valueOf(subnetMask));
        addData(Integer.valueOf(colorCode2));
        addData(uati_s);
        addData(Integer.valueOf(pilotlnc));
        addData(Integer.valueOf(activeSetSchWin));
        addData(Integer.valueOf(neighborSetSchWin2));
        addData(Integer.valueOf(remainSetSchWin));
        int sameFreq_T_ADD2 = sameFreq_T_ADD;
        int sameFreq_T_ADD3 = band_class;
        addData(Float.valueOf(((float) sameFreq_T_ADD2) / -2.0f));
        int sameFreq_T_DROP2 = sameFreq_T_DROP;
        int sameFreq_T_DROP3 = sameFreq_T_ADD2;
        addData(Float.valueOf(((float) sameFreq_T_DROP2) / -2.0f));
        addData(Integer.valueOf(sameFreq_T_tDROP));
        int diffFreq_T_ADD2 = diffFreq_T_ADD;
        int diffFreq_T_ADD3 = sameFreq_T_DROP2;
        addData(Float.valueOf(((float) diffFreq_T_ADD2) / -2.0f));
        int diffFreq_T_DROP2 = diffFreq_T_DROP;
        int diffFreq_T_DROP3 = diffFreq_T_ADD2;
        addData(Float.valueOf(((float) diffFreq_T_DROP2) / -2.0f));
        addData(Integer.valueOf(diffFreq_T_tDROP));
        notifyDataSetChanged();
    }
}
