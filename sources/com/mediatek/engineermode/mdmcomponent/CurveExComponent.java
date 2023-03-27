package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.subtitle.Cea708CCParser;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.mdmcomponent.CurveViewEx;
import java.util.HashMap;

/* compiled from: MDMComponent */
abstract class CurveExComponent extends MDMComponent {
    CurveViewEx mCurveView;
    HashMap<Integer, float[]> mData = new HashMap<>();
    View mView;
    TextView mXLabel;
    TextView mYLabel;

    public CurveExComponent(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        if (this.mView == null) {
            View inflate = this.mActivity.getLayoutInflater().inflate(R.layout.mdm_em_info_curveex, (ViewGroup) null);
            this.mView = inflate;
            this.mXLabel = (TextView) inflate.findViewById(R.id.em_info_curve_x_label);
            this.mYLabel = (TextView) this.mView.findViewById(R.id.em_info_curve_y_label);
            this.mCurveView = (CurveViewEx) this.mView.findViewById(R.id.em_info_curve);
            this.mCurveView.setAxisConfig(configX(), configY());
            this.mCurveView.getRSRPSINRConfig();
        }
        return this.mView;
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
        configs[0].color = -16776961;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveViewEx.Config.TYPE_CIRCLE;
        configs[0].name = "PCell";
        configs[1] = new CurveViewEx.Config();
        configs[1].color = SupportMenu.CATEGORY_MASK;
        configs[1].lineWidth = 3;
        configs[1].nodeType = CurveViewEx.Config.TYPE_CIRCLE;
        configs[1].name = "SCell";
        configs[2] = new CurveViewEx.Config();
        configs[2].color = Color.rgb(43, 101, 171);
        configs[2].lineWidth = 3;
        configs[2].nodeType = CurveViewEx.Config.TYPE_RHOMBUS;
        configs[2].name = "Strength";
        configs[3] = new CurveViewEx.Config();
        configs[3].color = Color.rgb(NfcCommand.CommandType.MTK_NFC_EM_MSG_END, Cea708CCParser.Const.CODE_C1_DF1, 0);
        configs[3].lineWidth = 3;
        configs[3].nodeType = CurveViewEx.Config.TYPE_SQUARE;
        configs[3].name = "MediumWeak";
        configs[4] = new CurveViewEx.Config();
        configs[4].color = Color.rgb(Cea708CCParser.Const.CODE_C1_DF0, Cea708CCParser.Const.CODE_C1_DF0, 186);
        configs[4].lineWidth = 3;
        configs[4].nodeType = CurveViewEx.Config.TYPE_TRIANGLE;
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
    public void removeView() {
    }

    /* access modifiers changed from: package-private */
    public void addData(int index, float dataX, float dataY, float dataA, float dataB) {
        float[] d = this.mData.get(Integer.valueOf(index));
        if (d == null) {
            this.mData.put(Integer.valueOf(index), new float[]{dataX, dataY, dataA, dataB});
        } else {
            float[] tmp = new float[(d.length + 4)];
            for (int i = 0; i < d.length; i++) {
                tmp[i] = d[i];
            }
            tmp[d.length] = dataX;
            tmp[d.length + 1] = dataY;
            tmp[d.length + 2] = dataA;
            tmp[d.length + 3] = dataB;
            this.mData.put(Integer.valueOf(index), tmp);
        }
        CurveViewEx curveViewEx = this.mCurveView;
        if (curveViewEx != null) {
            curveViewEx.setData(index, this.mData.get(Integer.valueOf(index)));
        }
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        this.mData.clear();
    }
}
