package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class Fdd3gSpeechCodec extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_CSR_STATUS_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "AMR_4_75");
            put(1, "AMR_5_15");
            put(2, "AMR_5_90");
            put(3, "AMR_6_70");
            put(4, "AMR_7_40");
            put(5, "AMR_7_95");
            put(6, "AMR_10_20");
            put(7, "AMR_12_20");
            put(8, "AMR_SID");
            put(9, "GSM_EFR_SID");
            put(10, "TDMA_EFR_SID");
            put(11, "PDC_EFR_SID");
            put(12, "RESERVE_1");
            put(13, "RESERVE_2");
            put(14, "RESERVE_3");
            put(15, "AMR_NODATA");
            put(16, "AMRWB_6_60");
            put(17, "AMRWB_8_85");
            put(18, "AMRWB_12_65");
            put(19, "AMRWB_14_25");
            put(20, "AMRWB_15_85");
            put(21, "AMRWB_18_25");
            put(22, "AMRWB_19_85");
            put(23, "AMRWB_23_05");
            put(24, "AMRWB_23_85");
            put(25, "AMRWB_SID");
        }
    };

    public Fdd3gSpeechCodec(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "3G FDD Speech Codec";
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
    public String[] getLabels() {
        return new String[]{"UL speech codec", "DL speech codec"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        addData(this.mMapping.get(Integer.valueOf(getFieldValue(data, MDMContent.EM_CSR_ULAMRTYPE))));
        addData(this.mMapping.get(Integer.valueOf(getFieldValue(data, MDMContent.EM_CSR_DLAMRTYPE))));
    }
}
