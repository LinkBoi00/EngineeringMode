package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.widget.AbsListView;
import android.widget.TextView;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponent */
class WcdmaUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND, MDMContent.MSG_ID_FDD_EM_UL1_UTAS_INFO_IND};
    HashMap<Integer, String> CcmStateMapping = new HashMap<Integer, String>() {
        {
            put(3, "PCH");
            put(4, "FACH");
            put(5, "DCH");
        }
    };
    HashMap<Integer, String> RxSystemMapping = new HashMap<Integer, String>() {
        {
            put(1, "OneRX");
            put(2, "TwoRX");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
        }
    };
    String[] labelsKey = {"Tas Common", "TX Info", "ANT Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS Enable Info", "Serving Band", "Current Ant State", "Previous Ant State", "Rx System", "RRC State"});
            add(new String[]{"TX Index", "RX Index"});
            add(new String[]{"ANT Index", "TX Pwr dBm", "Pwr Hdr DB", "RSCP dBm"});
        }
    };
    private String[] tabTitle;
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(WcdmaUTasInfo.this.initHashMap((Object[]) WcdmaUTasInfo.this.labelsList.get(0)));
            add(WcdmaUTasInfo.this.initHashMap((Object[]) WcdmaUTasInfo.this.labelsList.get(1)));
            add(WcdmaUTasInfo.this.initArrayHashMap((Object[]) WcdmaUTasInfo.this.labelsList.get(2)));
        }
    };

    public WcdmaUTasInfo(Activity context) {
        super(context);
        String[] strArr = {"Common", "Detail"};
        this.tabTitle = strArr;
        initTableComponent(strArr);
    }

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx >= 0 && tasidx <= 1) {
            return this.TasEnableMapping.get(Integer.valueOf(tasidx));
        }
        return "-(" + tasidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        return "Band" + " " + bandidx;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "WCDMA UTAS Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "3. UMTS FDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
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
        StringBuilder sb;
        StringBuilder sb2;
        String str;
        String str2;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        String str3;
        String str4;
        int i;
        String str5;
        String str6;
        String str7;
        Msg data = (Msg) msg;
        clearData();
        if (name.equals(MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND)) {
            setInfoValid(0);
        } else {
            setInfoValid(1);
        }
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
        int tas_enable = getFieldValue(data, "tas_enable");
        int surving_band = getFieldValue(data, "band");
        int current_ant_state = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_CURRENT_ANT_STATE);
        int previous_ant_state = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_PREVIOUS_ANT_STATE);
        int rx_system = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_RX_SYSTEM);
        int ccm_state = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_CCMSTATE);
        String str8 = this.labelsKey[0];
        String[] strArr = this.labelsList.get(0);
        String[] strArr2 = new String[6];
        strArr2[0] = tasEableMapping(tas_enable);
        strArr2[1] = servingBandMapping(surving_band);
        if (current_ant_state < 0 || current_ant_state > 23) {
            sb = new StringBuilder();
            sb.append("-(");
            sb.append(current_ant_state);
            sb.append(")");
        } else {
            sb = new StringBuilder();
            sb.append(current_ant_state);
            sb.append("");
        }
        int i2 = tas_enable;
        strArr2[2] = sb.toString();
        if (previous_ant_state < 0 || previous_ant_state > 23) {
            sb2 = new StringBuilder();
            sb2.append("-(");
            sb2.append(previous_ant_state);
            sb2.append(")");
        } else {
            sb2 = new StringBuilder();
            sb2.append(previous_ant_state);
            sb2.append("");
        }
        strArr2[3] = sb2.toString();
        if (this.RxSystemMapping.containsKey(Integer.valueOf(rx_system))) {
            str = this.RxSystemMapping.get(Integer.valueOf(rx_system));
        } else {
            str = "-(" + rx_system + ")";
        }
        strArr2[4] = str;
        if (this.CcmStateMapping.containsKey(Integer.valueOf(ccm_state))) {
            str2 = this.CcmStateMapping.get(Integer.valueOf(ccm_state));
        } else {
            str2 = "-(" + ccm_state + ")";
        }
        strArr2[5] = str2;
        setHashMapKeyValues(str8, 0, strArr, (Object[]) strArr2);
        addData(this.labelsKey[0], 0);
        int tx_index = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_TX_INDEX);
        int rx_index = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_RX_INDEX);
        String str9 = this.labelsKey[1];
        String[] strArr3 = this.labelsList.get(1);
        String[] strArr4 = new String[2];
        if (tx_index < 0 || tx_index > 11) {
            sb3 = new StringBuilder();
            sb3.append("-(");
            sb3.append(tx_index);
            sb3.append(")");
        } else {
            sb3 = new StringBuilder();
            sb3.append(tx_index);
            sb3.append("");
        }
        strArr4[0] = sb3.toString();
        if (rx_system <= 1 || rx_index < 0 || rx_index > 11) {
            sb4 = new StringBuilder();
            sb4.append("-(");
            sb4.append(rx_index);
            sb4.append(")");
        } else {
            sb4 = new StringBuilder();
            sb4.append(rx_index);
            sb4.append("");
        }
        int i3 = surving_band;
        strArr4[1] = sb4.toString();
        setHashMapKeyValues(str9, 1, strArr3, (Object[]) strArr4);
        addData(this.labelsKey[1], 1);
        int tx_index_txp = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_TX_INDEX_TXP, true);
        int rx_index_txp = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_RX_INDEX_TXP, true);
        int tx_index_pwrhdr = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_TX_INDEX_PWRHDR, true);
        int rx_index_pwrhdr = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_RX_INDEX_PWRHDR, true);
        int i4 = current_ant_state;
        int tx_index_rscp = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_TX_INDEX_RSCP, true);
        int i5 = previous_ant_state;
        int rx_index_rscp = getFieldValue(data, MDMContent.FDD_EM_UL1_TAS_RX_INDEX_RSCP, true);
        Msg msg2 = data;
        String str10 = this.labelsKey[2];
        int i6 = ccm_state;
        String[] strArr5 = this.labelsList.get(2);
        int rx_index_rscp2 = rx_index_rscp;
        String[] strArr6 = new String[4];
        if (tx_index < 0 || tx_index > 11) {
            sb5 = new StringBuilder();
            sb5.append("-(");
            sb5.append(tx_index);
            sb5.append(")");
        } else {
            sb5 = new StringBuilder();
            sb5.append(tx_index);
            sb5.append("");
        }
        strArr6[0] = sb5.toString();
        String str11 = "-";
        if (tx_index_txp == -128) {
            str3 = str11;
        } else {
            str3 = tx_index_txp + "";
        }
        strArr6[1] = str3;
        if (tx_index_pwrhdr == -128) {
            str4 = str11;
        } else {
            str4 = tx_index_pwrhdr + "";
        }
        strArr6[2] = str4;
        strArr6[3] = tx_index_rscp == -480 ? str11 : String.valueOf(tx_index_rscp);
        setHashMapKeyValues(str10, 1, strArr5, (Object[]) strArr6);
        if (rx_system > 1) {
            String str12 = this.labelsKey[2];
            String[] strArr7 = this.labelsList.get(2);
            String[] strArr8 = new String[4];
            if (rx_index < 0 || rx_index > 11) {
                str5 = "-(" + rx_index + ")";
            } else {
                str5 = rx_index + "";
            }
            strArr8[0] = str5;
            if (rx_index_txp == -128) {
                str6 = str11;
            } else {
                str6 = rx_index_txp + "";
            }
            strArr8[1] = str6;
            if (rx_index_pwrhdr == -128) {
                str7 = str11;
            } else {
                str7 = rx_index_pwrhdr + "";
            }
            strArr8[2] = str7;
            int rx_index_rscp3 = rx_index_rscp2;
            if (rx_index_rscp3 != -480) {
                str11 = String.valueOf(rx_index_rscp3);
            }
            strArr8[3] = str11;
            i = 1;
            setHashMapKeyValues(str12, 1, strArr7, (Object[]) strArr8);
        } else {
            i = 1;
            int i7 = rx_index_rscp2;
        }
        addData(this.labelsKey[2], i);
    }
}
