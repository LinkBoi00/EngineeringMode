package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondaryCell extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_CONN_INFO_IND};

    public SecondaryCell(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell";
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
        return new String[]{"EARFCN", "PCI", "Band", "Bandwidth", "Belongs to STAG"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Object obj;
        Msg data = (Msg) msg;
        for (int i = 0; i < 3; i++) {
            int earfcn = getFieldValue(data, "scell_earfcn[" + i + "]");
            int pci = getFieldValue(data, "scell_pci[" + i + "]");
            int band = getFieldValue(data, "scell_band[" + i + "]");
            int bandwidth = getFieldValue(data, "scell_bw[" + i + "]");
            int stag = getFieldValue(data, "scell_belongs_to_stag[" + i + "]");
            Object[] objArr = new Object[5];
            objArr[0] = earfcn == 0 ? "-" : Integer.valueOf(earfcn);
            Object obj2 = "";
            objArr[1] = pci == 0 ? obj2 : Integer.valueOf(pci);
            if (band == 0) {
                obj = obj2;
            } else {
                obj = band + "(LTE-U)";
            }
            objArr[2] = obj;
            objArr[3] = bandwidth == 0 ? obj2 : Float.valueOf(((float) bandwidth) / 10.0f);
            if (stag != 0) {
                obj2 = Integer.valueOf(stag);
            }
            objArr[4] = obj2;
            addData(objArr);
        }
    }
}
