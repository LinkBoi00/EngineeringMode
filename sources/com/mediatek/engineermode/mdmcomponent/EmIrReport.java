package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class EmIrReport extends ArrayTableComponent {
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTER_CELL_HO = 6;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_CELL_HO = 5;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_LTE_CR = 0;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_LTE_FDDTDD_CR = 18;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_LTE_FDDTDD_REDIRECT = 28;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_LTE_TDDFDD_CR = 19;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_INTRA_LTE_TDDFDD_REDIRECT = 29;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_2G4_CR = 4;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_3G4_CR = 3;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_3G4_HO = 9;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_3G_4GFDD_HO = 16;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_3G_4GTDD_HO = 17;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4G2_CR = 2;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4G2_HO = 8;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4G3_CR = 1;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4G3_HO = 7;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4GFDD_2G_HO = 15;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4GFDD_3G_HO = 13;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4GTDD_2G_HO = 14;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_4GTDD_3G_HO = 12;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD2G4_AFR = 48;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD2G4_BG_SRCH = 52;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD2G4_CR = 26;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD2G4_REDIRECT = 44;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD3G4_AFR = 46;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD3G4_BG_SRCH = 50;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD3G4_CR = 24;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD3G4_REDIRECT = 42;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G2_CR = 22;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G2_R10_REDIRECT = 38;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G2_R8_REDIRECT = 36;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G2_R9_REDIRECT = 37;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G3_CR = 20;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G3_R10_REDIRECT = 32;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G3_R8_REDIRECT = 30;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_FDD4G3_R9_REDIRECT = 31;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD2G4_AFR = 49;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD2G4_BG_SRCH = 53;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD2G4_CR = 27;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD2G4_REDIRECT = 45;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD3G4_AFR = 47;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD3G4_BG_SRCH = 51;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD3G4_CR = 25;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD3G4_REDIRECT = 43;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G2_CR = 23;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G2_R10_REDIRECT = 41;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G2_R8_REDIRECT = 39;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G2_R9_REDIRECT = 40;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G3_CR = 21;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G3_R10_REDIRECT = 35;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G3_R8_REDIRECT = 33;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_IRAT_TDD4G3_R9_REDIRECT = 34;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_LTE_FDD_TDD_CELL_HO = 10;
    private static final int EM_ERRC_SUCCESS_RATE_KPI_LTE_TDD_FDD_CELL_HO = 11;
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_RRM_SUCCESS_RATE_KPI_IND, MDMContent.MSG_ID_EM_RRCE_KPI_STATUS_IND, MDMContent.MSG_ID_EM_ERRC_SUCCESS_RATE_KPI_IND};
    HashMap<Integer, String> mErrcDirectMapping = new HashMap<Integer, String>() {
        {
            put(0, "");
            put(1, "");
            put(2, "");
            put(3, "");
            put(4, "");
            put(5, "");
            put(6, "");
            put(7, "");
            put(8, "");
            put(9, "");
            put(10, "");
            put(11, "");
            put(12, "4GTDD to 3G");
            put(13, "4GFDD to 3G");
            put(14, "4GTDD to 2G");
            put(15, "4GFDD to 2G");
            put(16, "3G to 4GFDD");
            put(17, "3G to 4GTDD");
            put(18, "");
            put(19, "");
            put(20, "4GFDD to 3G");
            put(21, "4GTDD to 3G");
            put(22, "4GFDD to 2G");
            put(23, "4GTDD to 2G");
            put(24, "3G to 4GFDD");
            put(25, "3G to 4GTDD");
            put(26, "2G to 4GFDD");
            put(27, "2G to 4GTDD");
            put(28, "");
            put(29, "");
            put(30, "4GFDD to 3G");
            put(31, "4GFDD to 3G");
            put(32, "4GFDD to 3G");
            put(33, "4GTDD to 3G");
            put(34, "4GTDD to 3G");
            put(35, "4GTDD to 3Gf");
            put(36, "4GFDD to 2G");
            put(37, "4GFDD to 2G");
            put(38, "4GFDD to 2G");
            put(39, "4GTDD to 2G");
            put(40, "4GTDD to 2G");
            put(41, "4GTDD to 2G");
            put(42, "3G to 4GFDD");
            put(43, "3G to 4GTDD");
            put(44, "2G to 4GFDD");
            put(45, "2G to 4GTDD");
            put(46, "3G to 4GFDD");
            put(47, "3G to 4GTDD");
            put(48, "2G to 4GFDD");
            put(49, "2G to 4GTDD");
            put(50, "3G to 4GFDD");
            put(51, "3G to 4GTDD");
            put(52, "2G to 4GFDD");
            put(53, "2G to 4GTDD");
        }
    };
    HashMap<Integer, String> mErrcTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "");
            put(1, "");
            put(2, "");
            put(3, "");
            put(4, "");
            put(5, "");
            put(6, "");
            put(7, "");
            put(8, "");
            put(9, "");
            put(10, "");
            put(11, "");
            put(12, "Handover");
            put(13, "Handover");
            put(14, "Handover");
            put(15, "Handover");
            put(16, "Handover");
            put(17, "Handover");
            put(18, "");
            put(19, "");
            put(20, "Cell Reselection");
            put(21, "Cell Reselection");
            put(22, "Cell Reselection");
            put(23, "Cell Reselection");
            put(24, "Cell Reselection");
            put(25, "Cell Reselection");
            put(26, "Cell Reselection");
            put(27, "Cell Reselection");
            put(28, "");
            put(29, "");
            put(30, "Rediretion Rel8");
            put(31, "Rediretion Rel9");
            put(32, "Rediretion Rel10");
            put(33, "Rediretion Rel8");
            put(34, "Rediretion Rel9");
            put(35, "Rediretion Rel10");
            put(36, "Rediretion Rel8");
            put(37, "Rediretion Rel9");
            put(38, "Rediretion Rel10");
            put(39, "Rediretion Rel8");
            put(40, "Rediretion Rel9");
            put(41, "Rediretion Rel10");
            put(42, "Rediretion");
            put(43, "Rediretion");
            put(44, "Rediretion");
            put(45, "Rediretion");
            put(46, "Auto Fast Return");
            put(47, "Auto Fast Return");
            put(48, "Auto Fast Return");
            put(49, "Auto Fast Return");
            put(50, "Background Search");
            put(51, "Background Search");
            put(52, "Background Search");
            put(53, "Background Search");
        }
    };
    HashMap<Integer, String> mRrceDirectMapping = new HashMap<Integer, String>() {
        {
            put(0, "3G2");
            put(1, "3G2");
            put(2, "3G2");
            put(3, "3G2");
            put(4, "3G2");
            put(5, "3G2");
        }
    };
    HashMap<Integer, String> mRrceTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "Handover");
            put(1, "Cell Reselection");
            put(2, "Cell Reselection");
            put(3, "CCO");
            put(4, "Redirection");
            put(5, "Redirection");
        }
    };
    HashMap<Integer, String> mRrmDirectMapping = new HashMap<Integer, String>() {
        {
            put(0, "2G3");
            put(1, "2G3");
            put(2, "2G3");
            put(3, "2G3");
        }
    };
    HashMap<Integer, String> mRrmTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "Cell Reselection");
            put(1, "CCO");
            put(2, "Handover");
            put(3, "Redirection");
        }
    };
    HashMap<Integer, String> mStatusMapping = new HashMap<Integer, String>() {
        {
            put(0, "Ongoing");
            put(1, "Success");
            put(2, "Failure");
        }
    };

    public EmIrReport(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EM IR Report";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "6. Inter-RAT EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"IR Direction", "IR Type", "Status"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        if (name.equals(MDMContent.MSG_ID_EM_RRM_SUCCESS_RATE_KPI_IND)) {
            int procId = getFieldValue(data, "proc_id");
            int status = getFieldValue(data, "status");
            clearData();
            addData(new String[]{this.mRrmDirectMapping.get(Integer.valueOf(procId)), this.mRrmTypeMapping.get(Integer.valueOf(procId)), this.mStatusMapping.get(Integer.valueOf(status))});
        } else if (name.equals(MDMContent.MSG_ID_EM_RRCE_KPI_STATUS_IND)) {
            int procId2 = getFieldValue(data, "proc_id");
            int status2 = getFieldValue(data, "status");
            clearData();
            addData(new String[]{this.mRrceDirectMapping.get(Integer.valueOf(procId2)), this.mRrceTypeMapping.get(Integer.valueOf(procId2)), this.mStatusMapping.get(Integer.valueOf(status2))});
        } else if (name.equals(MDMContent.MSG_ID_EM_ERRC_SUCCESS_RATE_KPI_IND)) {
            int procId3 = getFieldValue(data, "proc_id");
            int status3 = getFieldValue(data, "status");
            clearData();
            addData(new String[]{this.mErrcDirectMapping.get(Integer.valueOf(procId3)), this.mErrcTypeMapping.get(Integer.valueOf(procId3)), this.mStatusMapping.get(Integer.valueOf(status3))});
        }
    }
}
