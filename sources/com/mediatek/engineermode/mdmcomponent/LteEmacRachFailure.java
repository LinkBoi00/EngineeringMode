package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class LteEmacRachFailure extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EMAC_RACH_FAILURE_IND};
    String FileNamePS = "_LTE_EMAC_Rach_Failure_Info.txt";
    String FileNamePS1 = null;
    String FileNamePS2 = null;
    String StartTime = null;
    boolean isFirstTimeRecord = true;
    int[] num_of_rach_try = new int[3];
    String title = "Time,num_of_rach_try";

    public LteEmacRachFailure(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE EMAC Rach Failure Info";
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
        return new String[]{"num_of_rach_try"};
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
        Content[sim_idx] = CurTime + "," + this.num_of_rach_try[sim_idx];
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
        this.num_of_rach_try[sim_idx] = getFieldValue(data, "num_of_rach_try");
        Elog.d("EmInfo/MDMComponent", "num_of_rach_try = " + this.num_of_rach_try[sim_idx]);
        if (ComponentSelectActivity.mAutoRecordFlag.equals("1") && sim_idx < 2) {
            LogRecord(sim_idx);
        }
        if (sim_idx + 1 == MDMComponentDetailActivity.mModemType) {
            clearData();
            addData(Integer.valueOf(this.num_of_rach_try[sim_idx]));
        }
    }
}
