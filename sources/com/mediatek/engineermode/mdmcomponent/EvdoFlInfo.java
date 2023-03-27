package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.text.DecimalFormat;

/* compiled from: MDMComponentC2k */
class EvdoFlInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_FL_INFO_IND};

    public EvdoFlInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO FL info";
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
        return new String[]{"c_i", "drcAverageValue", "ftcCrcErrorCount", "ftcTotalCount", "syncCrcErrorRatio"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int c_i = getFieldValue(data, "c_i", true);
        int drcAverageValue = getFieldValue(data, "drcAverageValue");
        int ftcCrcErrorCount = getFieldValue(data, "ftcCrcErrorCount");
        int ftcTotalCount = getFieldValue(data, "ftcTotalCount");
        int syncCrcErrorRatio = getFieldValue(data, "syncCrcErrorRatio");
        String c_iFloat = new DecimalFormat("#0.00").format((double) (((float) c_i) / 64.0f));
        Elog.d("EmInfo/MDMComponent", "syncCrcErrorRatio = " + syncCrcErrorRatio);
        clearData();
        addData(c_iFloat);
        addData(Integer.valueOf(drcAverageValue));
        addData(Integer.valueOf(ftcCrcErrorCount));
        addData(Integer.valueOf(ftcTotalCount));
        addData(Integer.valueOf(syncCrcErrorRatio));
        notifyDataSetChanged();
    }
}
