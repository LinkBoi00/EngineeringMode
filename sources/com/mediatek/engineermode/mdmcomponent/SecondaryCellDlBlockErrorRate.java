package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class SecondaryCellDlBlockErrorRate extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public SecondaryCellDlBlockErrorRate(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell DL Block Error Rate";
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
        this.mYLabel.setText("D");
        CurveView.Config[] configs = {new CurveView.Config()};
        configs[0].color = -16776961;
        configs[0].lineWidth = 2;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[0].name = "UL BLER";
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
        addData(0, (float) getFieldValue((Msg) msg, "dl_info[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_DL_BLER, true));
    }
}
