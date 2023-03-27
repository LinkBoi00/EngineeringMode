package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class LteErrcRlfEvent extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_RLF_EVENT_IND};
    String FileNamePS = "_LTE_ERRC_RLF_Event_Info.txt";
    String FileNamePS1 = null;
    String FileNamePS2 = null;
    String StartTime = null;
    boolean isFirstTimeRecord = true;
    int[] rlf_cause = new int[3];
    int[] rlf_causeother = new int[3];
    int[] rlf_counter = new int[3];
    String title = "Time,rlf_counter,rlf_cause,rlf_causeother";

    public LteErrcRlfEvent(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE ERRC ELF Event Info";
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
        return new String[]{"rlf_counter", "rlf_cause", "rlf_causeother"};
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
        Content[sim_idx] = CurTime + "," + this.rlf_counter[sim_idx] + "," + this.rlf_cause[sim_idx] + "," + this.rlf_causeother[sim_idx];
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
        this.rlf_counter[sim_idx] = getFieldValue(data, "rlf_counter");
        this.rlf_cause[sim_idx] = getFieldValue(data, "rlf_cause");
        this.rlf_causeother[sim_idx] = getFieldValue(data, "rlf_causeother");
        Elog.d("EmInfo/MDMComponent", "rlf_counter = " + this.rlf_counter[sim_idx]);
        Elog.d("EmInfo/MDMComponent", "rlf_cause = " + this.rlf_cause[sim_idx]);
        Elog.d("EmInfo/MDMComponent", "rlf_causeother = " + this.rlf_causeother[sim_idx]);
        if (ComponentSelectActivity.mAutoRecordFlag.equals("1") && sim_idx < 2) {
            LogRecord(sim_idx);
        }
        if (sim_idx + 1 == MDMComponentDetailActivity.mModemType) {
            clearData();
            addData(Integer.valueOf(this.rlf_counter[sim_idx]));
            addData(Integer.valueOf(this.rlf_cause[sim_idx]));
            addData(Integer.valueOf(this.rlf_causeother[sim_idx]));
        }
    }
}
