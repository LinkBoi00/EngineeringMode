package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

/* compiled from: MDMComponent */
class RsrqLteCandidateCellUmtsFdd extends MDMComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND};
    private RsrqLteCandidateCellUmtsFddArray mArrayComponent;
    private LinearLayout mComponentView;
    private RsrqLteCandidateCellUmtsFddCurve mCurveComponent;

    public RsrqLteCandidateCellUmtsFdd(Activity context) {
        super(context);
        this.mArrayComponent = new RsrqLteCandidateCellUmtsFddArray(context);
        this.mCurveComponent = new RsrqLteCandidateCellUmtsFddCurve(context);
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        if (this.mComponentView == null) {
            LinearLayout linearLayout = new LinearLayout(this.mActivity);
            this.mComponentView = linearLayout;
            linearLayout.setOrientation(1);
        } else {
            removeView();
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0, 2.0f);
        this.mComponentView.addView(this.mArrayComponent.getView(), layoutParams);
        this.mComponentView.addView(this.mCurveComponent.getView(), layoutParams2);
        return this.mComponentView;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        this.mArrayComponent.removeView();
        this.mCurveComponent.removeView();
        this.mComponentView.removeAllViews();
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "RSRQ (LTE candidate cell)(UMTS FDD)";
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
    public void clearData() {
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        this.mArrayComponent.update(name, msg);
        this.mCurveComponent.update(name, msg);
    }
}
