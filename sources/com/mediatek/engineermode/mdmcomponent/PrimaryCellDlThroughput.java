package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class PrimaryCellDlThroughput extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public PrimaryCellDlThroughput(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Primary Cell DL Throughput";
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
        CurveView.Config[] configs = {new CurveView.Config()};
        configs[0].color = -16776961;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[0].name = "DL Throughput (0.0)";
        this.mCurveView.setConfig(configs);
        CurveView.AxisConfig yConfig = new CurveView.AxisConfig();
        yConfig.min = 0;
        yConfig.max = 200;
        yConfig.step = 10;
        yConfig.configMin = true;
        yConfig.configMax = true;
        yConfig.configStep = true;
        yConfig.type = CurveView.AxisConfig.TYPE_AUTO_SCALE;
        return yConfig;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int value = getFieldValue((Msg) msg, "dl_info[0]." + MDMContent.EM_EL1_STATUS_DL_INFO_DL_TPUT);
        Elog.d("EmInfo/MDMComponent", "[MDMComponent ][PrimaryCellDlThroughput][update] name: " + name + " value : " + value);
        addData(0, (float) value);
    }
}
