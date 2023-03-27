package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class ShareNetworkPlmnInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_NWSEL_PLMN_INFO_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "CELL_TYPE_SUITABLE");
            put(1, "CELL_TYPE_ACCEPTABLE");
            put(2, "CELL_TYPE_CAMPED_NOT_ALLOWED");
            put(3, "CELL_TYPE_NOT_APPLICABLE");
        }
    };

    public ShareNetworkPlmnInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Share Network PLMN Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "1. General EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"", "MCC1", "MCC2", "MCC3", "MNC1", "MNC2", "MNC3", "CELL TYPE"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int count = getFieldValue(data, "multi_plmn_count");
        clearData();
        int i = 0;
        while (i < count && i < 6) {
            Object[] objArr = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mcc" + 1))};
            Object[] objArr2 = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mcc" + 2))};
            Object[] objArr3 = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mcc" + 3))};
            Object[] objArr4 = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mnc" + 1))};
            Object[] objArr5 = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mnc" + 2))};
            Object[] objArr6 = {Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "mnc" + 3))};
            HashMap<Integer, String> hashMap = this.mMapping;
            addData("PLMN" + (i + 1), String.format("%X", objArr), String.format("%X", objArr2), String.format("%X", objArr3), String.format("%X", objArr4), String.format("%X", objArr5), String.format("%X", objArr6), hashMap.get(Integer.valueOf(getFieldValue(data, "multi_plmn_id[" + i + "]." + "cell_type"))));
            i++;
        }
    }
}
