package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.widget.AbsListView;
import android.widget.TextView;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponentC2k */
class Cdma1xrttRadioUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_RTT_RADIO_INFO_IND};
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "B34");
            put(1, "B39");
            put(5, "-");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };
    String[] labelsKey = {"Tas Common", "Main Info", "ANT Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS_EN", "TAS Version", "BandClass", "Channel", "PilotPN", "FER"});
            add(new String[]{"Cur Ant State", "TX Ant"});
            add(new String[]{"ANT Index", "RxPower(dBm)", "TxPower(dBm)", "PHR(dB)"});
        }
    };
    private String[] tabTitle = {"Common", "Detail"};
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(Cdma1xrttRadioUTasInfo.this.initHashMap((Object[]) Cdma1xrttRadioUTasInfo.this.labelsList.get(0)));
            add(Cdma1xrttRadioUTasInfo.this.initHashMap((Object[]) Cdma1xrttRadioUTasInfo.this.labelsList.get(1)));
            add(Cdma1xrttRadioUTasInfo.this.initArrayHashMap((Object[]) Cdma1xrttRadioUTasInfo.this.labelsList.get(2)));
        }
    };

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx < 0 || tasidx > 1) {
            return " ";
        }
        return this.TasEnableMapping.get(Integer.valueOf(tasidx));
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        if (this.ServingBandMapping.containsKey(Integer.valueOf(bandidx))) {
            return this.ServingBandMapping.get(Integer.valueOf(bandidx));
        }
        return " ";
    }

    public Cdma1xrttRadioUTasInfo(Activity context) {
        super(context);
        initTableComponent(this.tabTitle);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "1xRTT radio info(UTAS info)";
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
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> getArrayTypeKey() {
        ArrayList<String> arrayTypeKeys = new ArrayList<>();
        arrayTypeKeys.add(this.labelsKey[2]);
        return arrayTypeKeys;
    }

    /* access modifiers changed from: package-private */
    public boolean isLabelArrayType(String label) {
        if (getArrayTypeKey().contains(label)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, LinkedHashMap> getHashMapLabels(int index) {
        LinkedHashMap<String, LinkedHashMap> hashMapkeyValues = new LinkedHashMap<>();
        switch (index) {
            case 0:
                hashMapkeyValues.put(this.labelsKey[0], this.valuesHashMap.get(0));
                break;
            case 1:
                hashMapkeyValues.put(this.labelsKey[1], this.valuesHashMap.get(1));
                hashMapkeyValues.put(this.labelsKey[2], this.valuesHashMap.get(2));
                break;
        }
        return hashMapkeyValues;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        String str2;
        StringBuilder sb;
        Msg data;
        int tx_ant;
        String coName;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        Msg data2 = (Msg) msg;
        clearData();
        int utas_info_valid = getFieldValue(data2, MDMContent.C2K_L4_UTAS_ENABLE);
        setInfoValid(utas_info_valid);
        if (!isInfoValid()) {
            resetView();
            TextView textView = new TextView(this.mContext);
            textView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
            textView.setPadding(20, 0, 20, 0);
            textView.setText("Use " + getName().replace("UTAS", "TAS"));
            textView.setTextSize(16.0f);
            this.scrollView.addView(textView);
            return;
        }
        int tas_enable = getFieldValue(data2, "tas_enable");
        int tas_ver = getFieldValue(data2, MDMContent.C2K_L4_TAS_VER);
        int band_class = getFieldValue(data2, MDMContent.C2K_L4_BAND_CLASS);
        int channel = getFieldValue(data2, MDMContent.C2K_L4_CHANNEL);
        int pilot_pn_offset = getFieldValue(data2, MDMContent.C2K_L4_PILOT_PN_OFFSET);
        int fer = getFieldValue(data2, MDMContent.C2K_L4_FER);
        String str3 = this.labelsKey[0];
        String[] strArr = this.labelsList.get(0);
        String[] strArr2 = new String[6];
        strArr2[0] = tasEableMapping(tas_enable);
        if (tas_ver < 1 || tas_ver > 3) {
            str = "-(" + tas_ver + ")";
        } else {
            str = tas_ver + ".0";
        }
        strArr2[1] = str;
        int i = utas_info_valid;
        strArr2[2] = band_class + "";
        strArr2[3] = channel + "";
        strArr2[4] = pilot_pn_offset + "";
        strArr2[5] = fer + "";
        setHashMapKeyValues(str3, 0, strArr, (Object[]) strArr2);
        int tas_state = getFieldValue(data2, MDMContent.C2K_L4_TAS_STATE);
        int tx_ant2 = getFieldValue(data2, "tx_ant");
        String str4 = this.labelsKey[1];
        String[] strArr3 = this.labelsList.get(1);
        int i2 = tas_enable;
        String[] strArr4 = new String[2];
        if (tas_state >= 0) {
            StringBuilder sb5 = new StringBuilder();
            int i3 = tas_ver;
            sb5.append("State");
            sb5.append(tas_state);
            str2 = sb5.toString();
        } else {
            str2 = "-(" + tas_state + ")";
        }
        strArr4[0] = str2;
        if (tx_ant2 < 0 || tx_ant2 > 4) {
            sb = new StringBuilder();
            sb.append("-(");
            sb.append(tx_ant2);
            sb.append(")");
        } else {
            sb = new StringBuilder();
            sb.append(tx_ant2);
            sb.append("");
        }
        strArr4[1] = sb.toString();
        setHashMapKeyValues(str4, 1, strArr3, (Object[]) strArr4);
        String coName2 = MDMContent.C2K_L4_EM_C2K_L4_RTT_ANT_INFO;
        int i4 = 0;
        while (i4 < 8) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(coName2);
            sb6.append("[");
            sb6.append(i4);
            sb6.append("].");
            int tas_state2 = tas_state;
            sb6.append("valid");
            int valid = getFieldValue(data2, sb6.toString());
            if (valid == 0) {
                data = data2;
                coName = coName2;
                tx_ant = tx_ant2;
            } else {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(coName2);
                sb7.append("[");
                sb7.append(i4);
                sb7.append("].");
                int i5 = valid;
                sb7.append(MDMContent.C2K_L4_RX_POWER);
                int rx_power = getFieldValue(data2, sb7.toString(), true);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(coName2);
                sb8.append("[");
                sb8.append(i4);
                sb8.append("].");
                tx_ant = tx_ant2;
                sb8.append("tx_power");
                int tx_power = getFieldValue(data2, sb8.toString(), true);
                int phr = getFieldValue(data2, coName2 + "[" + i4 + "]." + MDMContent.C2K_L4_PHR, true);
                String str5 = this.labelsKey[2];
                data = data2;
                String[] strArr5 = this.labelsList.get(2);
                coName = coName2;
                String[] strArr6 = new String[4];
                strArr6[0] = i4 + "";
                if (rx_power > -35 || rx_power < -110) {
                    sb2 = new StringBuilder();
                    sb2.append("-(");
                    sb2.append(rx_power);
                    sb2.append(")");
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(rx_power);
                    sb2.append("");
                }
                strArr6[1] = sb2.toString();
                if (tx_power > 24 || tx_power < -60) {
                    sb3 = new StringBuilder();
                    sb3.append("-(");
                    sb3.append(tx_power);
                    sb3.append(")");
                } else {
                    sb3 = new StringBuilder();
                    sb3.append(tx_power);
                    sb3.append("");
                }
                strArr6[2] = sb3.toString();
                if (phr > 85 || phr < -50) {
                    sb4 = new StringBuilder();
                    sb4.append("-(");
                    sb4.append(phr);
                    sb4.append(")");
                } else {
                    sb4 = new StringBuilder();
                    sb4.append(phr);
                    sb4.append("");
                }
                strArr6[3] = sb4.toString();
                setHashMapKeyValues(str5, 1, strArr5, (Object[]) strArr6);
            }
            i4++;
            coName2 = coName;
            tas_state = tas_state2;
            tx_ant2 = tx_ant;
            data2 = data;
        }
        int i6 = tas_state;
        String str6 = coName2;
        addData(this.labelsKey[0], 0);
        addData(this.labelsKey[1], 1);
        addData(this.labelsKey[2], 1);
    }
}
