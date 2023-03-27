package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponentC2k */
class Cdma1xrttRadioInfo extends NormalTableTasComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_RADIO_INFO_IND};
    protected static final String TAG = "EmInfo";
    HashMap<Integer, String> AntennaMapping = new HashMap<Integer, String>() {
        {
            put(0, "LANT");
            put(1, "UANT");
            put(2, "LANT(')");
            put(3, "UANT");
            put(4, "-");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };
    int TasVersionC2K = 1;

    /* access modifiers changed from: package-private */
    public String antidxMapping(int antidx) {
        if (antidx >= 0 && antidx <= 3) {
            return this.AntennaMapping.get(Integer.valueOf(antidx));
        }
        return this.AntennaMapping.get(4) + "(" + antidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx >= 0 && tasidx <= 1) {
            return this.TasEnableMapping.get(Integer.valueOf(tasidx));
        }
        return this.TasEnableMapping.get(2) + "(" + tasidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String bandClassMapping(int bandidx) {
        if (bandidx < 0 || bandidx > 20) {
            return "-(" + bandidx + ")";
        }
        return "BC" + bandidx;
    }

    public Cdma1xrttRadioInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String[] conbineLablesByModem(String[] lables1, String[] lables2, int position) {
        if (!FeatureSupport.is93ModemAndAbove()) {
            return lables2;
        }
        if (position < 0) {
            return addLablesAtPosition(lables2, lables1, Math.abs(position));
        }
        return addLablesAtPosition(lables1, lables2, position);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT radio info(TAS info)";
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
        String[] Lables_Verison_v1 = {"Channel", "bandClass", "pilotPN", "rxPower_main(dbm)", "rxPower_div(dbm)", "txPower", "tx_Ant", "FER"};
        String[] Lables_Verison_v2 = {"Channel", "bandClass", "Cur ant state", "pilotPN", "rxPower_LANT (dbm)", "rxPower_UANT (dbm)", "rxPower_LANT(') (dbm)", "tx_Ant", "FER"};
        String[] Lables_Tas = {"Tas Enable Info"};
        if (this.TasVersionC2K == 2) {
            return conbineLablesByModem(Lables_Tas, Lables_Verison_v2, Lables_Tas.length);
        }
        return conbineLablesByModem(Lables_Tas, Lables_Verison_v1, Lables_Tas.length);
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int utas_info_valid = getFieldValue(data, MDMContent.C2K_L4_UTAS_ENABLE);
        setInfoValid(utas_info_valid);
        if (isInfoValid()) {
            clearData();
            TableInfoAdapter tableInfoAdapter = this.mAdapter;
            tableInfoAdapter.add(new String[]{"Use " + getName().replace("UTAS", "TAS")});
            return;
        }
        int tasidx = getFieldValue(data, "tas_enable");
        int channel = getFieldValue(data, MDMContent.C2K_L4_CHANNEL);
        int band_class = getFieldValue(data, MDMContent.C2K_L4_BAND_CLASS);
        int pilot_pn_offset = getFieldValue(data, MDMContent.C2K_L4_PILOT_PN_OFFSET);
        int rx_power1 = getFieldValue(data, MDMContent.C2K_L4_RX_POWER, true);
        int rx_power2 = getFieldValue(data, "div_rx_power", true);
        int tx_power = getFieldValue(data, "tx_power", true);
        int tx_ant_id = getFieldValue(data, "tx_ant", true);
        int FER = getFieldValue(data, MDMContent.C2K_L4_FER);
        int tas_ver = getFieldValue(data, MDMContent.C2K_L4_TAS_VER, true);
        if (tas_ver == 1) {
            this.TasVersionC2K = 1;
        } else {
            this.TasVersionC2K = 2;
        }
        Elog.d(TAG, "tas_ver = " + tas_ver);
        int tas_state = getFieldValue(data, MDMContent.C2K_L4_TAS_STATE, true);
        int rx_power_dbmL = getFieldValue(data, "rx_power_dbmL", true);
        int i = utas_info_valid;
        int rx_power_dbmU = getFieldValue(data, "rx_power_dbmU", true);
        int i2 = tas_ver;
        int rx_power_dbmLp = getFieldValue(data, "rx_power_dbmLp", true);
        clearData();
        if (FeatureSupport.is93ModemAndAbove()) {
            addData(tasEableMapping(tasidx));
        }
        Object obj = "";
        Msg msg2 = data;
        if (this.TasVersionC2K == 1) {
            addData(Integer.valueOf(channel));
            addData(bandClassMapping(band_class));
            addData(Integer.valueOf(pilot_pn_offset));
            Object[] objArr = new Object[1];
            objArr[0] = rx_power1 == -150 ? obj : Integer.valueOf(rx_power1);
            addData(objArr);
            int i3 = tasidx;
            Object[] objArr2 = new Object[1];
            objArr2[0] = rx_power2 == -150 ? obj : Integer.valueOf(rx_power2);
            addData(objArr2);
            Object[] objArr3 = new Object[1];
            if (tx_power != -150) {
                obj = Integer.valueOf(tx_power);
            }
            objArr3[0] = obj;
            addData(objArr3);
            addData(Integer.valueOf(tx_ant_id));
            addData(Integer.valueOf(FER));
        } else {
            int i4 = tasidx;
            addData(Integer.valueOf(channel));
            addData(bandClassMapping(band_class));
            addData(antidxMapping(tas_state));
            addData(Integer.valueOf(pilot_pn_offset));
            Object[] objArr4 = new Object[1];
            objArr4[0] = rx_power_dbmL == -150 ? obj : Integer.valueOf(rx_power_dbmL);
            addData(objArr4);
            Object[] objArr5 = new Object[1];
            objArr5[0] = rx_power_dbmU == -150 ? obj : Integer.valueOf(rx_power_dbmU);
            addData(objArr5);
            Object[] objArr6 = new Object[1];
            if (rx_power_dbmLp != -150) {
                obj = Integer.valueOf(rx_power_dbmLp);
            }
            objArr6[0] = obj;
            addData(objArr6);
            addData(Integer.valueOf(tx_ant_id));
            addData(Integer.valueOf(FER));
        }
        notifyDataSetChanged();
    }
}
