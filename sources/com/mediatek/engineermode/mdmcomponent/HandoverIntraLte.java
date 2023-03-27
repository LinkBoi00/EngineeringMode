package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class HandoverIntraLte extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_SUCCESS_RATE_KPI_IND};
    String FileNamePS = "_Handover_Intra_LTE.txt";
    String FileNamePS1 = null;
    String FileNamePS2 = null;
    String StartTime = null;
    int[] attempt = new int[3];
    String[] event = new String[3];
    boolean isFirstTimeRecord = true;
    int[] procId = new int[3];
    int[] status = new int[3];
    int[] success = new int[3];
    String title = "Time,success,procId,status";

    public HandoverIntraLte(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Handover (Intra-LTE)";
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
        return new String[]{"Attempt", "Success", "Fail"};
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
        Content[sim_idx] = CurTime + "," + this.success[sim_idx] + "," + this.procId[sim_idx] + "," + this.status[sim_idx];
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
        if (MDMContent.MSG_ID_EM_ERRC_SUCCESS_RATE_KPI_IND.equals(name)) {
            this.attempt[sim_idx] = getFieldValue(data, "attempt");
            this.success[sim_idx] = getFieldValue(data, "success");
            this.procId[sim_idx] = getFieldValue(data, "proc_id");
            this.status[sim_idx] = getFieldValue(data, "status");
            Elog.d("EmInfo/MDMComponent", "HandoverIntraLte,success = " + this.success[sim_idx]);
        }
        if (ComponentSelectActivity.mAutoRecordFlag.equals("1") && sim_idx < 2) {
            LogRecord(sim_idx);
        }
        if (sim_idx + 1 == MDMComponentDetailActivity.mModemType) {
            clearData();
            addData(Integer.valueOf(this.attempt[sim_idx]), Integer.valueOf(this.success[sim_idx]), Integer.valueOf(this.attempt[sim_idx] - this.success[sim_idx]));
        }
    }
}
