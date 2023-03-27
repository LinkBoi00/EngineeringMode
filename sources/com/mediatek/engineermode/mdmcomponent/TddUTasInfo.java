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
class TddUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_TDD_TAS_INFO_IND};
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "B34");
            put(1, "B39");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
        }
    };
    String[] labelsKey = {"Tas Common", "Main Info", "ANT Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS Enable Info", "Force TX Enable", "Force Ant State", "Current Ant State"});
            add(new String[]{"Current Band", "Main Index"});
            add(new String[]{"ANT Index", "TX Pwr dBm", "SINR dB", "RSCP dBm"});
        }
    };
    private String[] tabTitle;
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(TddUTasInfo.this.initHashMap((Object[]) TddUTasInfo.this.labelsList.get(0)));
            add(TddUTasInfo.this.initHashMap((Object[]) TddUTasInfo.this.labelsList.get(1)));
            add(TddUTasInfo.this.initArrayHashMap((Object[]) TddUTasInfo.this.labelsList.get(2)));
        }
    };

    public TddUTasInfo(Activity context) {
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
        if (this.ServingBandMapping.containsKey(Integer.valueOf(bandidx))) {
            return this.ServingBandMapping.get(Integer.valueOf(bandidx));
        }
        return "-(" + bandidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "TDD UTAS Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "4. UMTS TDD EM Component";
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
        String str;
        StringBuilder sb2;
        StringBuilder sb3;
        String str2;
        StringBuilder sb4;
        StringBuilder sb5;
        StringBuilder sb6;
        Msg data = (Msg) msg;
        clearData();
        int cur_ant_idx = getFieldValue(data, "cur_ant_state");
        int utas_info_valid = (cur_ant_idx >> 8) == 3 ? 1 : 0;
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
        int tas_enable = getFieldValue(data, "tas_enable_info");
        int force_tx_en = getFieldValue(data, MDMContent.TDD_FORCE_TX_EN);
        int force_ant_state = getFieldValue(data, "force_ant_state");
        int ant_state = getFieldValue(data, "cur_ant_state");
        int cur_ant_state = ant_state & 255;
        String str3 = this.labelsKey[0];
        String[] strArr = this.labelsList.get(0);
        String[] strArr2 = new String[4];
        strArr2[0] = tasEableMapping(tas_enable);
        strArr2[1] = tasEableMapping(force_tx_en);
        if (force_ant_state < 0 || force_ant_state > 23) {
            sb = new StringBuilder();
            sb.append("-(");
            sb.append(force_ant_state);
            sb.append(")");
        } else {
            sb = new StringBuilder();
            sb.append(force_ant_state);
            sb.append("");
        }
        int i = ant_state;
        strArr2[2] = sb.toString();
        if (cur_ant_state < 0 || cur_ant_state > 23) {
            str = "-(" + cur_ant_state + ")";
        } else {
            str = cur_ant_state + "";
        }
        strArr2[3] = str;
        setHashMapKeyValues(str3, 0, strArr, (Object[]) strArr2);
        int serving_band = getFieldValue(data, "current_serving_band");
        int cur_ant_index = getFieldValue(data, MDMContent.TDD_CUR_ANT_INDEX);
        int cur_ant_index_h = cur_ant_index >> 8;
        int cur_ant_index_l = cur_ant_index & 255;
        int i2 = cur_ant_idx;
        String str4 = this.labelsKey[1];
        int i3 = utas_info_valid;
        String[] strArr3 = this.labelsList.get(1);
        int i4 = tas_enable;
        String[] strArr4 = new String[2];
        strArr4[0] = servingBandMapping(serving_band);
        if (cur_ant_index_l < 0 || cur_ant_index_l > 7) {
            sb2 = new StringBuilder();
            sb2.append("-(");
            sb2.append(cur_ant_index_l);
            sb2.append(")");
        } else {
            sb2 = new StringBuilder();
            sb2.append(cur_ant_index_l);
            sb2.append("");
        }
        int i5 = force_tx_en;
        strArr4[1] = sb2.toString();
        setHashMapKeyValues(str4, 1, strArr3, (Object[]) strArr4);
        String antNumCoName = serving_band == 1 ? MDMContent.TDD_B39_AVAILABLE_ANT_NUM : MDMContent.TDD_B34_AVAILABLE_ANT_NUM;
        int cellNum = getFieldValue(data, antNumCoName);
        String coName = serving_band == 1 ? MDMContent.TDD_B39_AVAILABLE_ANT : MDMContent.TDD_B34_AVAILABLE_ANT;
        int i6 = 0;
        while (i6 < cellNum) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(coName);
            String antNumCoName2 = antNumCoName;
            sb7.append("[");
            sb7.append(i6);
            sb7.append("]");
            int ant_index = getFieldValue(data, sb7.toString());
            int cellNum2 = cellNum;
            StringBuilder sb8 = new StringBuilder();
            String coName2 = coName;
            sb8.append("tx_power[");
            sb8.append(i6);
            sb8.append("]");
            int tx_power = getFieldValue(data, sb8.toString(), true);
            StringBuilder sb9 = new StringBuilder();
            int force_ant_state2 = force_ant_state;
            sb9.append("ant_sinr[");
            sb9.append(i6);
            sb9.append("]");
            int ant_sinr = getFieldValue(data, sb9.toString(), true);
            StringBuilder sb10 = new StringBuilder();
            int cur_ant_state2 = cur_ant_state;
            sb10.append("ant_rscp[");
            sb10.append(i6);
            sb10.append("]");
            int ant_rscp = getFieldValue(data, sb10.toString(), true);
            String str5 = this.labelsKey[2];
            Msg data2 = data;
            String[] strArr5 = this.labelsList.get(2);
            int serving_band2 = serving_band;
            String[] strArr6 = new String[4];
            if (ant_index > 7 || ant_index < 0) {
                sb3 = new StringBuilder();
                sb3.append("-(");
                sb3.append(ant_index);
                sb3.append(")");
            } else {
                sb3 = new StringBuilder();
                sb3.append(ant_index);
                sb3.append("");
            }
            strArr6[0] = sb3.toString();
            if (i6 == cur_ant_index_h) {
                if (tx_power > 32 || tx_power < -50) {
                    sb6 = new StringBuilder();
                    sb6.append("-(");
                    sb6.append(tx_power);
                    sb6.append(")");
                } else {
                    sb6 = new StringBuilder();
                    sb6.append(tx_power);
                    sb6.append("");
                }
                str2 = sb6.toString();
            } else {
                str2 = "";
            }
            strArr6[1] = str2;
            if (ant_sinr > 32 || ant_sinr < -50) {
                sb4 = new StringBuilder();
                sb4.append("-(");
                sb4.append(ant_sinr);
                sb4.append(")");
            } else {
                sb4 = new StringBuilder();
                sb4.append(ant_sinr);
                sb4.append("");
            }
            strArr6[2] = sb4.toString();
            if (ant_rscp > -40 || ant_rscp < -140) {
                sb5 = new StringBuilder();
                sb5.append("-(");
                sb5.append(ant_rscp);
                sb5.append(")");
            } else {
                sb5 = new StringBuilder();
                sb5.append(ant_rscp);
                sb5.append("");
            }
            strArr6[3] = sb5.toString();
            setHashMapKeyValues(str5, 1, strArr5, (Object[]) strArr6);
            i6++;
            serving_band = serving_band2;
            antNumCoName = antNumCoName2;
            cellNum = cellNum2;
            coName = coName2;
            force_ant_state = force_ant_state2;
            cur_ant_state = cur_ant_state2;
            data = data2;
        }
        String str6 = antNumCoName;
        int i7 = cellNum;
        addData(this.labelsKey[0], 0);
        addData(this.labelsKey[1], 1);
        addData(this.labelsKey[2], 1);
    }
}
