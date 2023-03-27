package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class El2FeatureDetection extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL2_FEATURE_DETECTION_IND};
    StringBuilder mDetectedFeature = new StringBuilder("");
    HashMap<Integer, String> mDisplay = new HashMap<>();
    HashMap<Integer, String> mMappingFD = new HashMap<Integer, String>() {
        {
            put(0, "ERRC_FEAT_MFBI_PRIORITIZATION");
        }
    };

    public El2FeatureDetection(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EL2 Feature Detection";
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
    public String[] getLabels() {
        return new String[]{"Detected Feature"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        if (msg == null) {
            clearData();
            StringBuilder sb = this.mDetectedFeature;
            sb.delete(0, sb.toString().length() - 1);
            this.mDisplay.clear();
            return;
        }
        int index = getFieldValue((Msg) msg, MDMContent.DETECTED_FEATURE);
        if (this.mDisplay.get(Integer.valueOf(index)) == null) {
            clearData();
            this.mDisplay.put(Integer.valueOf(index), this.mMappingFD.get(Integer.valueOf(index)));
            this.mDetectedFeature.append(this.mMappingFD.get(Integer.valueOf(index)));
            addData(this.mDetectedFeature.toString() + "\n");
        }
    }
}
