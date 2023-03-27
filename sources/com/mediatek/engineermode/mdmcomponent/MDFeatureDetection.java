package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class MDFeatureDetection extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_L4C_MD_EVENT_IND};
    String FileNamePS = "_md_feature_detection.txt";
    String FileNamePS1 = null;
    String FileNamePS2 = null;
    String StartTime = null;
    String[] event = new String[3];
    boolean isFirstTimeRecord = true;
    String title = "Time,event";

    public MDFeatureDetection(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "MD Feature Detection";
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
        return new String[]{"Event"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void LogRecord(int sim_idx) {
        String[] Content = new String[2];
        if (this.isFirstTimeRecord) {
            this.isFirstTimeRecord = false;
            this.StartTime = getCurrectTime();
            try {
                this.FileNamePS1 = this.StartTime + "_ps_1_" + MDMComponentDetailActivity.mSimMccMnc[0] + this.FileNamePS;
                this.FileNamePS2 = this.StartTime + "_ps_2_" + MDMComponentDetailActivity.mSimMccMnc[1] + this.FileNamePS;
                saveToSDCard("/Download", this.FileNamePS1, this.title, false);
                saveToSDCard("/Download", this.FileNamePS2, this.title, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Elog.d("EmInfo/MDMComponent", "isFirstTimeRecord = " + this.isFirstTimeRecord + "," + this.title);
        }
        String CurTime = getCurrectTime();
        Content[sim_idx] = CurTime + "," + this.event[sim_idx].trim();
        if (sim_idx == 0) {
            try {
                Elog.d("EmInfo/MDMComponent", "save " + Content[sim_idx] + " to " + this.FileNamePS1);
                saveToSDCard("/Download", this.FileNamePS1, Content[sim_idx], true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Elog.d("EmInfo/MDMComponent", "save " + Content[sim_idx] + " to " + this.FileNamePS2);
            saveToSDCard("/Download", this.FileNamePS2, Content[sim_idx], true);
        }
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int sim_idx = data.getSimIdx() - 1;
        this.event[sim_idx] = "";
        if (MDMContent.MSG_ID_EM_L4C_MD_EVENT_IND.equals(name)) {
            for (int i = 0; i < 50; i++) {
                StringBuilder sb = new StringBuilder();
                String[] strArr = this.event;
                sb.append(strArr[sim_idx]);
                sb.append((char) getFieldValue(data, "event[" + i + "]"));
                strArr[sim_idx] = sb.toString();
            }
            Elog.d("EmInfo/MDMComponent", "MD Event Lte,event = " + this.event[sim_idx].trim());
        }
        if (ComponentSelectActivity.mAutoRecordFlag.equals("1") && sim_idx < 2) {
            LogRecord(sim_idx);
        }
        if (sim_idx + 1 == MDMComponentDetailActivity.mModemType) {
            clearData();
            addData(this.event[sim_idx].trim());
        }
    }
}
