package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import java.util.HashMap;
import java.util.Random;

/* compiled from: MDMComponent */
abstract class CurveComponent extends MDMComponent {
    CurveView mCurveView;
    HashMap<Integer, float[]> mData = new HashMap<>();
    long mStartTime;
    boolean mStarted;
    View mView;
    TextView mXLabel;
    TextView mYLabel;
    Random rand = new Random();

    public CurveComponent(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        if (this.mView == null) {
            View inflate = this.mActivity.getLayoutInflater().inflate(R.layout.mdm_em_info_curve, (ViewGroup) null);
            this.mView = inflate;
            this.mXLabel = (TextView) inflate.findViewById(R.id.em_info_curve_x_label);
            this.mYLabel = (TextView) this.mView.findViewById(R.id.em_info_curve_y_label);
            this.mCurveView = (CurveView) this.mView.findViewById(R.id.em_info_curve);
            if (!this.mStarted) {
                this.mStartTime = (System.currentTimeMillis() / 10000) * 10;
                this.mStarted = true;
            }
            this.mXLabel.setText("Time");
            CurveView.AxisConfig xConfig = new CurveView.AxisConfig();
            xConfig.base = this.mStartTime;
            xConfig.min = 0;
            xConfig.max = 200;
            xConfig.step = 1;
            xConfig.configMin = true;
            xConfig.configMax = true;
            xConfig.configStep = true;
            xConfig.type = CurveView.AxisConfig.TYPE_TIME;
            this.mCurveView.setAxisConfig(xConfig, configY());
        }
        return this.mView;
    }

    /* access modifiers changed from: package-private */
    public CurveView.AxisConfig configY() {
        this.mYLabel.setText("dBm");
        CurveView.Config[] configs = {new CurveView.Config()};
        configs[0].color = -7864320;
        configs[0].lineWidth = 2;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        this.mCurveView.setConfig(configs);
        CurveView.AxisConfig yConfig = new CurveView.AxisConfig();
        yConfig.min = 0;
        yConfig.max = 30;
        yConfig.step = 2;
        yConfig.configMin = true;
        yConfig.configMax = true;
        yConfig.configStep = true;
        return yConfig;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
    }

    /* access modifiers changed from: package-private */
    public void addData(int index, float data) {
        if (!this.mStarted) {
            this.mStartTime = (System.currentTimeMillis() / 10000) * 10;
            this.mStarted = true;
        }
        long time = System.currentTimeMillis() / 1000;
        float[] d = this.mData.get(Integer.valueOf(index));
        if (d == null) {
            this.mData.put(Integer.valueOf(index), new float[]{(float) (time - this.mStartTime), data});
        } else {
            float[] tmp = new float[(d.length + 2)];
            for (int i = 0; i < d.length; i++) {
                tmp[i] = d[i];
            }
            tmp[d.length] = (float) (time - this.mStartTime);
            tmp[d.length + 1] = data;
            this.mData.put(Integer.valueOf(index), tmp);
        }
        CurveView curveView = this.mCurveView;
        if (curveView != null) {
            curveView.setData(index, this.mData.get(Integer.valueOf(index)));
        }
    }
}
