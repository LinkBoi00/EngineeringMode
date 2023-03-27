package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class RsrpLteCandidateCellUmtsFddCurve extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND};

    public RsrpLteCandidateCellUmtsFddCurve(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RSRP (LTE candidate cell)(UMTS FDD)";
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
    public CurveView.AxisConfig configY() {
        this.mYLabel.setText("R");
        int[] colors = {-16776961, -16711936, SupportMenu.CATEGORY_MASK, -16711681, -65281, InputDeviceCompat.SOURCE_ANY, -16777080, -16742400, -7864320, -16742264, -7864184, -7829504, -16777148, -16759808, -12320768, ViewCompat.MEASURED_STATE_MASK};
        CurveView.Config[] configs = new CurveView.Config[16];
        for (int i = 0; i < 16; i++) {
            configs[i] = new CurveView.Config();
            configs[i].color = colors[i];
            configs[i].lineWidth = 3;
            configs[i].nodeType = CurveView.Config.TYPE_CIRCLE;
            configs[i].name = "";
        }
        this.mCurveView.setConfig(configs);
        CurveView.AxisConfig yConfig = new CurveView.AxisConfig();
        yConfig.min = -100;
        yConfig.max = 100;
        yConfig.step = 10;
        yConfig.configMin = true;
        yConfig.configMax = true;
        yConfig.configStep = true;
        return yConfig;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        if (getFieldValue(data, "RAT_type") == 2) {
            int num = getFieldValue(data, "neigh_cell_count");
            int i = 0;
            while (i < num && i < 16) {
                addData(i, ((float) getFieldValue(data, "choice.LTE_neigh_cells[" + i + "]." + "rsrp", true)) / 4096.0f);
                i++;
            }
        }
    }
}
