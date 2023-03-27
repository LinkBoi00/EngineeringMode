package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class HsDschServingCellUmtsFdd extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_MEME_DCH_H_SERVING_CELL_INFO_IND};

    public HsDschServingCellUmtsFdd(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "HS-DSCH Serving cell (UMTS FDD)";
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
        return new String[]{"HS-DSCH Serving Cell"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int uarfcn = getFieldValue(data, MDMContent.FDD_EM_MEME_DCH_H_SERVING_HSDSCH_UARFCN);
        int psc = getFieldValue(data, MDMContent.FDD_EM_MEME_DCH_H_SERVING_HSDSCH_PSC);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        Object obj = "-";
        sb.append(uarfcn == 65535 ? obj : Integer.valueOf(uarfcn));
        String value = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(value);
        sb2.append(" / ");
        if (psc != 65535) {
            obj = Integer.valueOf(psc);
        }
        sb2.append(obj);
        String value2 = sb2.toString();
        clearData();
        addData(value2);
    }
}
