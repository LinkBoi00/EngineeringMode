package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class RatAndServiceStatus extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RAC_INFO_IND};
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(0, "NO SERVICE");
            put(1, "LIMITED SERVICE");
            put(2, "GSM");
            put(3, "UMTS FDD");
            put(4, "UMTS TDD");
            put(5, "LTE  FDD");
            put(6, "LTE  TDD");
            put(7, "NR");
        }
    };

    public RatAndServiceStatus(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RAT and Service Status";
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
    public CurveView.AxisConfig configY() {
        this.mYLabel.setText("R");
        CurveView.Config[] configs = {new CurveView.Config()};
        configs[0].color = -16776961;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[0].name = "Service status";
        this.mCurveView.setConfig(configs);
        CurveView.AxisConfig yConfig = new CurveView.AxisConfig();
        yConfig.min = 0;
        yConfig.max = 7;
        yConfig.step = 1;
        yConfig.configMin = true;
        yConfig.configMax = true;
        yConfig.configStep = true;
        yConfig.customLabel = this.mMapping;
        return yConfig;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        addData(0, (float) getFieldValue((Msg) msg, MDMContent.EM_RAC_ACTIVE_RAT_INFO));
    }
}
