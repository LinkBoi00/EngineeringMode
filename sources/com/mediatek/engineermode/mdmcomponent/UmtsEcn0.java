package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.mdmcomponent.CurveView;
import com.mediatek.mdml.Msg;

/* compiled from: MDMComponent */
class UmtsEcn0 extends CurveComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_FDD_EM_CSCE_SERV_CELL_S_STATUS_IND, MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND, MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND};

    public UmtsEcn0(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "UMTS ECN0 (UMTS FDD)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "3. UMTS FDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public CurveView.AxisConfig configY() {
        this.mYLabel.setText("R");
        CurveView.Config[] configs = new CurveView.Config[3];
        configs[0] = new CurveView.Config();
        configs[0].color = -16776961;
        configs[0].lineWidth = 3;
        configs[0].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[0].name = "EcN0(active)";
        configs[0].newLineThreadshold = 11;
        configs[1] = new CurveView.Config();
        configs[1].color = -16776961;
        configs[1].lineWidth = 0;
        configs[1].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[1].name = "EcN0(monitored)";
        configs[1].newLineThreadshold = 11;
        configs[2] = new CurveView.Config();
        configs[2].color = -16776961;
        configs[2].lineWidth = 1;
        configs[2].lineType = CurveView.Config.LINE_DASH;
        configs[2].nodeType = CurveView.Config.TYPE_CIRCLE;
        configs[2].name = "EcN0(detected)";
        configs[2].newLineThreadshold = 11;
        this.mCurveView.setConfig(configs);
        CurveView.AxisConfig yConfig = new CurveView.AxisConfig();
        yConfig.min = -100;
        yConfig.max = 100;
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
        char c;
        String str = name;
        Msg data = (Msg) msg;
        if (str.equals(MDMContent.MSG_ID_FDD_EM_CSCE_SERV_CELL_S_STATUS_IND)) {
            float ecn0 = ((float) getFieldValue(data, "serv_cell." + "ec_no", true)) / 4096.0f;
            if (((float) getFieldValue(data, "serv_cell." + "rscp", true)) / 4096.0f > -120.0f && ecn0 > -25.0f) {
                addData(0, ecn0);
            }
        } else if (str.equals(MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND)) {
            int operation = getFieldValue(data, MDMContent.FDD_EM_CSCE_NEIGH_CELL_OPERATION);
            int rat = getFieldValue(data, "RAT_type");
            if (operation == 1 && rat == 0) {
                int num = getFieldValue(data, "neigh_cell_count");
                int i = 0;
                while (i < num && i < 16) {
                    float ecn02 = ((float) getFieldValue(data, "choice.neigh_cells[" + i + "]." + "ec_no", true)) / 4096.0f;
                    if (((float) getFieldValue(data, "choice.neigh_cells[" + i + "]." + "rscp", true)) / 4096.0f > -120.0f) {
                        c = 0;
                        if (ecn02 > -25.0f) {
                            addData(1, ecn02);
                        }
                    } else {
                        c = 0;
                    }
                    i++;
                    char c2 = c;
                }
            }
        } else if (str.equals(MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND)) {
            int num2 = getFieldValue(data, "num_cells");
            int i2 = 0;
            while (i2 < num2 && i2 < 32) {
                int cellType = getFieldValue(data, "umts_cell_list[" + i2 + "]." + "cell_type");
                int ecn03 = getFieldValue(data, "umts_cell_list[" + i2 + "]." + "ec_no", true) / 4096;
                if (cellType == 0) {
                    addData(0, (float) ecn03);
                } else if (cellType == 1 || cellType == 3) {
                    addData(1, (float) ecn03);
                } else if (cellType == 2) {
                    addData(2, (float) ecn03);
                }
                i2++;
            }
        }
    }
}
