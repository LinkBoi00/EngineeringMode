package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondaryCellCqi extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public SecondaryCellCqi(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell CQI";
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
        CurveView.Config[] configs = new CurveView.Config[2];
        configs[0] = new CurveView.Config();
        configs[0].color = -16776961;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[0].name = "CQI_CW0";
        configs[1] = new CurveView.Config();
        configs[1].color = -16711936;
        configs[1].lineWidth = 3;
        configs[1].lineType = CurveView.Config.LINE_DASH;
        configs[1].nodeType = CurveView.Config.TYPE_CROSS;
        configs[1].name = "CQI_CW1";
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
        int cw0 = getFieldValue(data, "dl_info[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_CQI_CW0, true);
        int cw1 = getFieldValue(data, "dl_info[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_CQI_CW1, true);
        addData(0, (float) cw0);
        addData(0, (float) cw1);
    }
}
