package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.mdmcomponent.CurveViewEx;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class CellStrength extends CurveExComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};

    public CellStrength(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "CC0/CC1 RSRP and SINR";
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
    public CurveViewEx.AxisConfig configX() {
        this.mXLabel.setText("RSRP");
        CurveViewEx.AxisConfig xConfig = new CurveViewEx.AxisConfig();
        xConfig.min = -140;
        xConfig.max = -30;
        xConfig.step = 10;
        xConfig.configMin = true;
        xConfig.configMax = true;
        xConfig.configStep = true;
        return xConfig;
    }

    /* access modifiers changed from: package-private */
    public CurveViewEx.AxisConfig configY() {
        this.mYLabel.setText("SNR");
        CurveViewEx.Config[] configs = new CurveViewEx.Config[5];
        configs[0] = new CurveViewEx.Config();
        configs[0].color = SupportMenu.CATEGORY_MASK;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveViewEx.Config.TYPE_CIRCLE;
        configs[0].name = "CC0";
        configs[1] = new CurveViewEx.Config();
        configs[1].color = -16776961;
        configs[1].lineWidth = 3;
        configs[1].nodeType = CurveViewEx.Config.TYPE_TRIANGLE;
        configs[1].name = "CC1";
        configs[2] = new CurveViewEx.Config();
        configs[2].color = Color.rgb(43, 101, 171);
        configs[2].lineWidth = 3;
        configs[2].nodeType = CurveViewEx.Config.TYPE_NONE;
        configs[2].name = "Strong";
        configs[3] = new CurveViewEx.Config();
        configs[3].color = Color.rgb(NfcCommand.CommandType.MTK_NFC_EM_MSG_END, Cea708CCParser.Const.CODE_C1_DF1, 0);
        configs[3].lineWidth = 3;
        configs[3].nodeType = CurveViewEx.Config.TYPE_NONE;
        configs[3].name = "MediumWeak";
        configs[4] = new CurveViewEx.Config();
        configs[4].color = Color.rgb(Cea708CCParser.Const.CODE_C1_DF0, Cea708CCParser.Const.CODE_C1_DF0, 186);
        configs[4].lineWidth = 3;
        configs[4].nodeType = CurveViewEx.Config.TYPE_NONE;
        configs[4].name = "Weak";
        this.mCurveView.setConfig(configs);
        CurveViewEx.AxisConfig yConfig = new CurveViewEx.AxisConfig();
        yConfig.min = -20;
        yConfig.max = 30;
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
        int pcellRsrp = getFieldValue(data, MDMContent.EM_EL1_STATUS_DL_INFO + "[0]." + "rsrp", true);
        int pcellSinr = getFieldValue(data, MDMContent.EM_EL1_STATUS_DL_INFO + "[0]." + MDMContent.EM_EL1_STATUS_DL_INFO_SINR, true);
        int pcellPci = getFieldValue(data, "cell_info" + "[0]." + "pci", true);
        long pcellEarfcn = (long) getFieldValue(data, "cell_info" + "[0]." + "earfcn");
        int scellRsrp = getFieldValue(data, MDMContent.EM_EL1_STATUS_DL_INFO + "[1]." + "rsrp", true);
        int scellSinr = getFieldValue(data, MDMContent.EM_EL1_STATUS_DL_INFO + "[1]." + MDMContent.EM_EL1_STATUS_DL_INFO_SINR, true);
        int scellPci = getFieldValue(data, "cell_info" + "[1]." + "pci", true);
        long scellEarfcn = (long) getFieldValue(data, "cell_info" + "[1]." + "earfcn");
        clearData();
        Elog.d("EmInfo/MDMComponent", "pcellRsrp" + pcellRsrp + " pcellSinr" + pcellSinr + " pcellPci" + pcellPci + " pcellEarfcn" + pcellEarfcn + " scellRsrp" + scellRsrp + " scellSinr" + scellSinr + " scellPci" + scellPci + " scellEarfcn" + scellEarfcn);
        Msg msg2 = data;
        long scellEarfcn2 = scellEarfcn;
        int scellRsrp2 = scellRsrp;
        float f = (float) pcellRsrp;
        float f2 = (float) pcellSinr;
        int scellSinr2 = scellSinr;
        float f3 = (float) pcellPci;
        Object obj = MDMContent.EM_EL1_STATUS_DL_INFO;
        int scellPci2 = scellPci;
        addData(0, f, f2, f3, (float) pcellEarfcn);
        int scellRsrp3 = scellRsrp2;
        int i = scellRsrp3;
        addData(1, (float) scellRsrp3, (float) scellSinr2, (float) scellPci2, (float) scellEarfcn2);
    }
}
