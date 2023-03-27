package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.os.SystemProperties;
import android.support.v4.media.MediaPlayer2;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.lang.reflect.Array;
import java.util.HashMap;

/* compiled from: MDMComponent */
class PCellSCellBasicInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND, MDMContent.MSG_ID_EM_EL1_STATUS_IND};
    int[][] DlCellAntPort;
    int[][] DlCellBler;
    int[][] DlCellCqiCw0;
    int[][] DlCellCqiCw1;
    int[][] DlCellImcs;
    int[][] DlCellRb;
    int[][] DlCellTput;
    int[][] DlCellri;
    String FileNamePS;
    String FileNamePS1;
    String FileNamePS2;
    String StartTime;
    int[][] UlCellBler;
    int[][] UlCellImcs;
    int[][] UlCellRb;
    int[][] UlCellTput;
    int[] bandPcell = new int[3];
    int[][] bandScellArr;
    int[] dlBandwidthPcell;
    int[][] dlBandwidthScellArr;
    int[] dlFreqPcell;
    int[][] dlFreqScellArr;
    int[] dlMod0Pcell;
    int[][] dlMod0ScellArr;
    int[] dlMod1Pcell;
    int[][] dlMod1ScellArr;
    long[] earfcnPcell = new long[3];
    long[][] earfcnScellArr = ((long[][]) Array.newInstance(long.class, new int[]{3, 3}));
    boolean isFirstTimeRecord;
    String[] lastTime;
    HashMap<Integer, String> mMappingBW = new HashMap<Integer, String>() {
        {
            put(14, "1.4");
            put(30, "3");
            put(50, "5");
            put(100, "10");
            put(150, "15");
            put(Integer.valueOf(MediaPlayer2.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK), "20");
            put(255, "");
        }
    };
    HashMap<Integer, String> mMappingQam = new HashMap<Integer, String>() {
        {
            put(1, "QPSK");
            put(2, "16QAM");
            put(3, "64QAM");
            put(4, "256QAM");
        }
    };
    int[] oldDlMod0Pcell;
    int[][] oldDlMod0ScellArr;
    int[] oldDlMod1Pcell;
    int[][] oldDlMod1ScellArr;
    int[] oldUlPcell;
    int[][] oldUlScellArr;
    int[] pciPcell;
    int[][] pciScellArr;
    int[] rsrpPcell;
    int[][] rsrpScellArr;
    int[] rsrqPcell;
    int[][] rsrqScellArr;
    int[] snrPcell;
    int[][] snrScellArr;
    String title;
    int[] tmPcell;
    int[][] tmScellArr;
    int[] ulBandwidthPcell;
    int[][] ulBandwidthScellArr;
    int[] ulFreqPcell;
    int[][] ulFreqScellArr;
    int[] ulPcell;
    int[][] ulScellArr;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PCellSCellBasicInfo(Activity context) {
        super(context);
        Class<int> cls = int.class;
        this.bandScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.dlBandwidthPcell = new int[3];
        this.dlBandwidthScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.ulBandwidthPcell = new int[3];
        this.ulBandwidthScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.tmPcell = new int[3];
        this.tmScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.pciPcell = new int[3];
        this.pciScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.snrPcell = new int[3];
        this.snrScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.dlFreqPcell = new int[3];
        this.dlFreqScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.ulFreqPcell = new int[3];
        this.ulFreqScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.rsrpPcell = new int[3];
        this.rsrpScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.rsrqPcell = new int[3];
        this.rsrqScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.dlMod0Pcell = new int[3];
        this.oldDlMod0Pcell = new int[3];
        this.dlMod0ScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.oldDlMod0ScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.dlMod1Pcell = new int[3];
        this.oldDlMod1Pcell = new int[3];
        this.dlMod1ScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.oldDlMod1ScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.ulPcell = new int[3];
        this.oldUlPcell = new int[3];
        this.ulScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.oldUlScellArr = (int[][]) Array.newInstance(cls, new int[]{3, 3});
        this.DlCellAntPort = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellTput = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.UlCellTput = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellImcs = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.UlCellImcs = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellBler = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.UlCellBler = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellRb = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.UlCellRb = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellCqiCw0 = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellCqiCw1 = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.DlCellri = (int[][]) Array.newInstance(cls, new int[]{3, 4});
        this.StartTime = null;
        this.lastTime = new String[2];
        this.FileNamePS = "_Pcell_and_Scell_basic_info.txt";
        this.title = "Time,Pcell_Band, Pcell_DL BW, Pcell_UL BW, Pcell_TM, Pcell_PCI, Pcell_EARFCN, Pcell_SNR, Pcell_DL_Freq, Pcell_UL_Freq, Pcell_RSRP, Pcell_RSRQ,Pcell_ant_port,Pcell_DL_Tput,Pcell_UL_Tput,Pcell_DL_Imcs,Pcell_UL_Imcs,Pcell_DL_bler,Pcell_UL_bler,Pcell_DL_rb,Pcell_UL_rb,Pcell_cqi_cw0,Pcell_cqi_cw1,Pcell_ri,Scell0_Band, Scell0_DL BW, Scell0_UL BW, Scell0_TM, Scell0_PCI, Scell0_EARFCN,Scell0_SNR, Scell0_DL_Freq, Scell0_UL_Freq, Scell0_RSRP, Scell0_RSRQ, Scell0_ant_port,Scell0_DL_Tput,Scell0_UL_Tput,Scell0_DL_Imcs,Scell0_UL_Imcs,Scell0_DL_bler,Scell0_UL_bler,Scell0_DL_rb,Scell0_UL_rb,Scell0_cqi_cw0,Scell0_cqi_cw1,Scell0_ri,Scell1_Band,Scell1_DL BW, Scell1_UL BW, Scell1_TM, Scell1_PCI, Scell1_EARFCN, Scell1_SNR,Scell1_DL_Freq, Scell1_UL_Freq, Scell1_RSRP, Scell1_RSRQ, Scell1_ant_port,Scell1_DL_Tput,Scell1_UL_Tput,Scell1_DL_Imcs,Scell1_UL_Imcs,Scell1_DL_bler,Scell1_UL_bler,Scell1_DL_rb,Scell1_UL_rb,Scell1_cqi_cw0,Scell1_cqi_cw1,Scell1_ri,Scell2_Band,Scell2_DL BW, Scell2_UL BW, Scell2_TM, Scell2_PCI, Scell2_EARFCN, Scell2_SNR,Scell2_DL_Freq, Scell2_UL_Freq,Scell2_RSRP, Scell2_RSRQ,Scell2_ant_port,Scell2_DL_Tput,Scell2_UL_Tput,Scell2_DL_Imcs,Scell2_UL_Imcs,Scell2_DL_bler,Scell2_UL_bler,Scell2_DL_rb,Scell2_UL_rb,Scell2_cqi_cw0,Scell2_cqi_cw1,Scell2_ri";
        this.FileNamePS1 = null;
        this.FileNamePS2 = null;
        this.isFirstTimeRecord = true;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Pcell and Scell basic info";
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
        return new String[]{"", "Pcell", "Scell0", "Scell1", "Scell2"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void LogRecord(int sim_idx) {
        int i = sim_idx;
        String[] Pcell_log = new String[2];
        String[][] Scell_log = (String[][]) Array.newInstance(String.class, new int[]{2, 3});
        String[] CurTime = new String[2];
        String[] Content = new String[2];
        if (ComponentSelectActivity.mAutoRecordFlag.equals("0")) {
            Elog.d("EmInfo/MDMComponent", "Do not save info");
            return;
        }
        if (this.isFirstTimeRecord) {
            this.isFirstTimeRecord = false;
            this.StartTime = getCurrectTime();
            try {
                this.FileNamePS1 = this.StartTime + "_ps_1_" + MDMComponentDetailActivity.mSimMccMnc[0] + this.FileNamePS;
                this.FileNamePS2 = this.StartTime + "_ps_2_" + MDMComponentDetailActivity.mSimMccMnc[1] + this.FileNamePS;
                saveToSDCard("/Download", this.FileNamePS1, this.title, false);
                saveToSDCard("/Download", this.FileNamePS2, this.title, false);
                String[] strArr = this.lastTime;
                String str = this.StartTime;
                strArr[0] = str;
                strArr[1] = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Elog.d("EmInfo/MDMComponent", "isFirstTimeRecord = " + this.isFirstTimeRecord + "," + this.title);
        }
        CurTime[i] = getCurrectTime();
        Pcell_log[i] = CurTime[i] + " ," + this.bandPcell[i] + "," + this.mMappingBW.get(Integer.valueOf(this.dlBandwidthPcell[i])) + "," + this.mMappingBW.get(Integer.valueOf(this.ulBandwidthPcell[i])) + "," + this.tmPcell[i] + "," + this.pciPcell[i] + "," + this.earfcnPcell[i] + "," + (((float) this.snrPcell[i]) / 4.0f) + "," + this.dlFreqPcell[i] + "," + this.ulFreqPcell[i] + "," + (((float) this.rsrpPcell[i]) / 4.0f) + "," + (((float) this.rsrqPcell[i]) / 4.0f) + "," + this.DlCellAntPort[i][0] + "," + this.DlCellTput[i][0] + "," + this.UlCellTput[i][0] + "," + this.DlCellImcs[i][0] + "," + this.UlCellImcs[i][0] + "," + this.DlCellBler[i][0] + "," + this.UlCellBler[i][0] + "," + this.DlCellRb[i][0] + "," + this.UlCellRb[i][0] + "," + this.DlCellCqiCw0[i][0] + "," + this.DlCellCqiCw1[i][0] + "," + this.DlCellri[i][0];
        String[] strArr2 = Scell_log[i];
        StringBuilder sb = new StringBuilder();
        sb.append(this.bandScellArr[i][0]);
        sb.append(",");
        sb.append(this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[i][0])));
        sb.append(",");
        sb.append(this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[i][0])));
        sb.append(",");
        sb.append(this.tmScellArr[i][0]);
        sb.append(",");
        sb.append(this.pciScellArr[i][0]);
        sb.append(",");
        sb.append(this.earfcnScellArr[i][0]);
        sb.append(",");
        sb.append(((float) this.snrScellArr[i][0]) / 4.0f);
        sb.append(",");
        sb.append(this.dlFreqScellArr[i][0]);
        sb.append(",");
        sb.append(this.ulFreqScellArr[i][0]);
        sb.append(",");
        sb.append(((float) this.rsrpScellArr[i][0]) / 4.0f);
        sb.append(",");
        sb.append(((float) this.rsrqScellArr[i][0]) / 4.0f);
        sb.append(",");
        sb.append(this.DlCellAntPort[i][1]);
        sb.append(",");
        sb.append(this.DlCellTput[i][1]);
        sb.append(",");
        sb.append(this.UlCellTput[i][1]);
        sb.append(",");
        sb.append(this.DlCellImcs[i][1]);
        sb.append(",");
        sb.append(this.UlCellImcs[i][1]);
        sb.append(",");
        sb.append(this.DlCellBler[i][1]);
        sb.append(",");
        sb.append(this.UlCellBler[i][1]);
        sb.append(",");
        sb.append(this.DlCellRb[i][1]);
        sb.append(",");
        sb.append(this.UlCellRb[i][1]);
        sb.append(",");
        sb.append(this.DlCellCqiCw0[i][1]);
        sb.append(",");
        sb.append(this.DlCellCqiCw1[i][1]);
        sb.append(",");
        sb.append(this.DlCellri[i][1]);
        strArr2[0] = sb.toString();
        String[] strArr3 = Scell_log[i];
        strArr3[1] = this.bandScellArr[i][1] + "," + this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[i][1])) + "," + this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[i][1])) + "," + this.tmScellArr[i][1] + "," + this.pciScellArr[i][1] + "," + this.earfcnScellArr[i][1] + "," + (((float) this.snrScellArr[i][1]) / 4.0f) + "," + this.dlFreqScellArr[i][1] + "," + this.ulFreqScellArr[i][1] + "," + (((float) this.rsrpScellArr[i][1]) / 4.0f) + "," + (((float) this.rsrqScellArr[i][1]) / 4.0f) + "," + this.DlCellAntPort[i][2] + "," + this.DlCellTput[i][2] + "," + this.UlCellTput[i][2] + "," + this.DlCellImcs[i][2] + "," + this.UlCellImcs[i][2] + "," + this.DlCellBler[i][2] + "," + this.UlCellBler[i][2] + "," + this.DlCellRb[i][2] + "," + this.UlCellRb[i][2] + "," + this.DlCellCqiCw0[i][2] + "," + this.DlCellCqiCw1[i][2] + "," + this.DlCellri[i][2];
        String[] strArr4 = Scell_log[i];
        strArr4[2] = this.bandScellArr[i][2] + "," + this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[i][2])) + "," + this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[i][2])) + "," + this.tmScellArr[i][2] + "," + this.pciScellArr[i][2] + "," + this.earfcnScellArr[i][2] + "," + (((float) this.snrScellArr[i][2]) / 4.0f) + "," + this.dlFreqScellArr[i][2] + "," + this.ulFreqScellArr[i][2] + "," + (((float) this.rsrpScellArr[i][2]) / 4.0f) + "," + (((float) this.rsrqScellArr[i][2]) / 4.0f) + "," + this.DlCellAntPort[i][3] + "," + this.DlCellTput[i][3] + "," + this.UlCellTput[i][3] + "," + this.DlCellImcs[i][3] + "," + this.UlCellImcs[i][3] + "," + this.DlCellBler[i][3] + "," + this.UlCellBler[i][3] + "," + this.DlCellRb[i][3] + "," + this.UlCellRb[i][3] + "," + this.DlCellCqiCw0[i][3] + "," + this.DlCellCqiCw1[i][3] + "," + this.DlCellri[i][3];
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Pcell_log[i]);
        sb2.append(",");
        sb2.append(Scell_log[i][0]);
        sb2.append(",");
        sb2.append(Scell_log[i][1]);
        sb2.append(",");
        sb2.append(Scell_log[i][2]);
        Content[i] = sb2.toString();
        try {
            int recordTimer = Integer.parseInt(SystemProperties.get("persist.radio.record.time", "5"));
            if (diff_time(this.lastTime[i], CurTime[i], recordTimer * 1000)) {
                this.lastTime[i] = CurTime[i];
                Elog.d("EmInfo/MDMComponent", "recordTimer " + recordTimer);
                if (i == 0) {
                    Elog.d("EmInfo/MDMComponent", "save " + Content[i] + " to " + this.FileNamePS1);
                    saveToSDCard("/Download", this.FileNamePS1, Content[i], true);
                } else if (i == 1) {
                    Elog.d("EmInfo/MDMComponent", "save " + Content[i] + " to " + this.FileNamePS2);
                    saveToSDCard("/Download", this.FileNamePS2, Content[i], true);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str = name;
        Msg data = (Msg) msg;
        int sim_idx = data.getSimIdx() - 1;
        if (str.equals(MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND)) {
            String coName = "serving_info.";
            this.earfcnPcell[sim_idx] = (long) getFieldValue(data, coName + "earfcn");
            this.pciPcell[sim_idx] = getFieldValue(data, coName + "pci");
            this.rsrpPcell[sim_idx] = getFieldValue(data, coName + "rsrp", true);
            this.rsrqPcell[sim_idx] = getFieldValue(data, coName + "rsrq", true);
            this.snrPcell[sim_idx] = getFieldValue(data, coName + "rs_snr_in_qdb", true);
            int[] iArr = this.bandPcell;
            StringBuilder sb = new StringBuilder();
            sb.append(coName);
            String str2 = "serv_lte_band";
            sb.append(str2);
            iArr[sim_idx] = getFieldValue(data, sb.toString());
            int i = 0;
            for (int i2 = 3; i < i2; i2 = 3) {
                String str3 = str2;
                String coName2 = coName;
                this.earfcnScellArr[sim_idx][i] = (long) getFieldValue(data, "scell_info_list.scell_info[" + i + "]." + "earfcn");
                this.pciScellArr[sim_idx][i] = getFieldValue(data, "scell_info_list.scell_info[" + i + "]." + "pci");
                this.rsrpScellArr[sim_idx][i] = getFieldValue(data, "scell_info_list.scell_info[" + i + "]." + "rsrp", true);
                this.rsrqScellArr[sim_idx][i] = getFieldValue(data, "scell_info_list.scell_info[" + i + "]." + "rsrq", true);
                this.snrScellArr[sim_idx][i] = getFieldValue(data, "scell_info_list.scell_info[" + i + "]." + "rs_snr_in_qdb", true);
                int[] iArr2 = this.bandScellArr[sim_idx];
                StringBuilder sb2 = new StringBuilder();
                sb2.append("scell_info_list.scell_info[");
                sb2.append(i);
                sb2.append("].");
                String str4 = str3;
                sb2.append(str4);
                iArr2[i] = getFieldValue(data, sb2.toString());
                i++;
                str2 = str4;
                coName = coName2;
            }
        } else if (str.equals(MDMContent.MSG_ID_EM_EL1_STATUS_IND)) {
            String coName3 = "cell_info[0].";
            this.dlBandwidthPcell[sim_idx] = getFieldValue(data, coName3 + MDMContent.EM_EL1_STATUS_CELL_INFO_DL_BW);
            this.ulBandwidthPcell[sim_idx] = getFieldValue(data, coName3 + MDMContent.EM_EL1_STATUS_CELL_INFO_UL_BW);
            this.tmPcell[sim_idx] = getFieldValue(data, coName3 + "tm");
            this.dlFreqPcell[sim_idx] = getFieldValue(data, coName3 + "dlFreq");
            this.ulFreqPcell[sim_idx] = getFieldValue(data, coName3 + "ulFreq");
            this.dlMod0Pcell[sim_idx] = getFieldValue(data, "dl_info[0].DL_Mod0");
            int[] iArr3 = this.oldDlMod0Pcell;
            int[] iArr4 = this.dlMod0Pcell;
            iArr3[sim_idx] = iArr4[sim_idx] == 255 ? iArr3[sim_idx] : iArr4[sim_idx];
            this.dlMod1Pcell[sim_idx] = getFieldValue(data, "dl_info[0].DL_Mod1");
            int[] iArr5 = this.oldDlMod1Pcell;
            int[] iArr6 = this.dlMod1Pcell;
            iArr5[sim_idx] = iArr6[sim_idx] == 255 ? iArr5[sim_idx] : iArr6[sim_idx];
            this.ulPcell[sim_idx] = getFieldValue(data, "ul_info[0].UL_Mod");
            int[] iArr7 = this.oldUlPcell;
            int[] iArr8 = this.ulPcell;
            iArr7[sim_idx] = iArr8[sim_idx] == 255 ? iArr7[sim_idx] : iArr8[sim_idx];
            int i3 = 0;
            while (i3 < 3) {
                this.dlBandwidthScellArr[sim_idx][i3] = getFieldValue(data, "cell_info[" + (i3 + 1) + "]." + MDMContent.EM_EL1_STATUS_CELL_INFO_DL_BW);
                this.ulBandwidthScellArr[sim_idx][i3] = getFieldValue(data, "cell_info[" + (i3 + 1) + "]." + MDMContent.EM_EL1_STATUS_CELL_INFO_UL_BW);
                this.tmScellArr[sim_idx][i3] = getFieldValue(data, "cell_info[" + (i3 + 1) + "]." + "tm");
                this.dlFreqScellArr[sim_idx][i3] = getFieldValue(data, "cell_info[" + (i3 + 1) + "].dlFreq");
                this.ulFreqScellArr[sim_idx][i3] = getFieldValue(data, "cell_info[" + (i3 + 1) + "].ulFreq");
                this.dlMod0ScellArr[sim_idx][i3] = getFieldValue(data, "dl_info[" + (i3 + 1) + "].DL_Mod0");
                int[][] iArr9 = this.oldDlMod0ScellArr;
                int[] iArr10 = iArr9[sim_idx];
                int[][] iArr11 = this.dlMod0ScellArr;
                String coName4 = coName3;
                iArr10[i3] = iArr11[sim_idx][i3] == 255 ? iArr9[sim_idx][i3] : iArr11[sim_idx][i3];
                this.dlMod1ScellArr[sim_idx][i3] = getFieldValue(data, "dl_info[" + (i3 + 1) + "].DL_Mod1");
                int[][] iArr12 = this.oldDlMod1ScellArr;
                int[] iArr13 = iArr12[sim_idx];
                int[][] iArr14 = this.dlMod1ScellArr;
                iArr13[i3] = iArr14[sim_idx][i3] == 255 ? iArr12[sim_idx][i3] : iArr14[sim_idx][i3];
                this.ulScellArr[sim_idx][i3] = getFieldValue(data, "ul_info[" + (i3 + 1) + "].UL_Mod");
                int[][] iArr15 = this.oldUlScellArr;
                int[] iArr16 = iArr15[sim_idx];
                int[][] iArr17 = this.ulScellArr;
                iArr16[i3] = iArr17[sim_idx][i3] == 255 ? iArr15[sim_idx][i3] : iArr17[sim_idx][i3];
                if (this.bandScellArr[sim_idx][i3] == 0) {
                    this.oldDlMod0ScellArr[sim_idx][i3] = 0;
                    this.oldDlMod1ScellArr[sim_idx][i3] = 0;
                    iArr15[sim_idx][i3] = 0;
                }
                i3++;
                String str5 = name;
                coName3 = coName4;
            }
            for (int i4 = 0; i4 < 4; i4++) {
                String coNamedl = "dl_info[" + i4 + "].";
                String coNameul = "ul_info[" + i4 + "].";
                int[] iArr18 = this.DlCellAntPort[sim_idx];
                iArr18[i4] = getFieldValue(data, ("cell_info[" + i4 + "].") + MDMContent.EM_EL1_STATUS_CELL_INFO_ANT_PORT, true);
                this.DlCellTput[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_DL_TPUT);
                this.UlCellTput[sim_idx][i4] = getFieldValue(data, coNameul + MDMContent.EM_EL1_STATUS_UL_INFO_UL_TPUT);
                this.DlCellImcs[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_DL_IMCS, true);
                this.UlCellImcs[sim_idx][i4] = getFieldValue(data, coNameul + MDMContent.EM_EL1_STATUS_UL_INFO_UL_IMCS, true);
                this.DlCellBler[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_DL_BLER, true);
                this.UlCellBler[sim_idx][i4] = getFieldValue(data, coNameul + MDMContent.EM_EL1_STATUS_UL_INFO_UL_BLER, true);
                this.DlCellRb[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_DL_RB, true);
                this.UlCellRb[sim_idx][i4] = getFieldValue(data, coNameul + MDMContent.EM_EL1_STATUS_UL_INFO_UL_RB, true);
                this.DlCellCqiCw0[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_CQI_CW0, true);
                this.DlCellCqiCw1[sim_idx][i4] = getFieldValue(data, coNamedl + MDMContent.EM_EL1_STATUS_DL_INFO_CQI_CW1, true);
                this.DlCellri[sim_idx][i4] = getFieldValue(data, coNamedl + "ri");
            }
        } else {
            Elog.d("EmInfo/MDMComponent", "PCellSCellBasicInfo update invalid name");
            return;
        }
        if (ComponentSelectActivity.mAutoRecordFlag.equals("1") && sim_idx < 2) {
            LogRecord(sim_idx);
        }
        if (sim_idx + 1 == MDMComponentDetailActivity.mModemType) {
            clearData();
            addData("Band", Integer.valueOf(this.bandPcell[sim_idx]), Integer.valueOf(this.bandScellArr[sim_idx][0]), Integer.valueOf(this.bandScellArr[sim_idx][1]), Integer.valueOf(this.bandScellArr[sim_idx][2]));
            addData("DL BW(MHz)", this.mMappingBW.get(Integer.valueOf(this.dlBandwidthPcell[sim_idx])), this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[sim_idx][0])), this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[sim_idx][1])), this.mMappingBW.get(Integer.valueOf(this.dlBandwidthScellArr[sim_idx][2])));
            addData("UL BW(MHz)", this.mMappingBW.get(Integer.valueOf(this.ulBandwidthPcell[sim_idx])), this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[sim_idx][0])), this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[sim_idx][1])), this.mMappingBW.get(Integer.valueOf(this.ulBandwidthScellArr[sim_idx][2])));
            addData("TM", Integer.valueOf(this.tmPcell[sim_idx]), Integer.valueOf(this.tmScellArr[sim_idx][0]), Integer.valueOf(this.tmScellArr[sim_idx][1]), Integer.valueOf(this.tmScellArr[sim_idx][2]));
            addData("PCI", Integer.valueOf(this.pciPcell[sim_idx]), Integer.valueOf(this.pciScellArr[sim_idx][0]), Integer.valueOf(this.pciScellArr[sim_idx][1]), Integer.valueOf(this.pciScellArr[sim_idx][2]));
            addData("EARFCN", Long.valueOf(this.earfcnPcell[sim_idx]), Long.valueOf(this.earfcnScellArr[sim_idx][0]), Long.valueOf(this.earfcnScellArr[sim_idx][1]), Long.valueOf(this.earfcnScellArr[sim_idx][2]));
            addData("RS_SNR", Float.valueOf(((float) this.snrPcell[sim_idx]) / 4.0f), Float.valueOf(((float) this.snrScellArr[sim_idx][0]) / 4.0f), Float.valueOf(((float) this.snrScellArr[sim_idx][1]) / 4.0f), Float.valueOf(((float) this.snrScellArr[sim_idx][2]) / 4.0f));
            addData("DL Freq", Integer.valueOf(this.dlFreqPcell[sim_idx]), Integer.valueOf(this.dlFreqScellArr[sim_idx][0]), Integer.valueOf(this.dlFreqScellArr[sim_idx][1]), Integer.valueOf(this.dlFreqScellArr[sim_idx][2]));
            addData("UL Freq", Integer.valueOf(this.ulFreqPcell[sim_idx]), Integer.valueOf(this.ulFreqScellArr[sim_idx][0]), Integer.valueOf(this.ulFreqScellArr[sim_idx][1]), Integer.valueOf(this.ulFreqScellArr[sim_idx][2]));
            addData("RSRP", Float.valueOf(((float) this.rsrpPcell[sim_idx]) / 4.0f), Float.valueOf(((float) this.rsrpScellArr[sim_idx][0]) / 4.0f), Float.valueOf(((float) this.rsrpScellArr[sim_idx][1]) / 4.0f), Float.valueOf(((float) this.rsrpScellArr[sim_idx][2]) / 4.0f));
            addData("RSRQ", Float.valueOf(((float) this.rsrqPcell[sim_idx]) / 4.0f), Float.valueOf(((float) this.rsrqScellArr[sim_idx][0]) / 4.0f), Float.valueOf(((float) this.rsrqScellArr[sim_idx][1]) / 4.0f), Float.valueOf(((float) this.rsrqScellArr[sim_idx][2]) / 4.0f));
            addData("DL Mod TB1", this.mMappingQam.get(Integer.valueOf(this.oldDlMod0Pcell[sim_idx])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod0ScellArr[sim_idx][0])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod0ScellArr[sim_idx][1])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod0ScellArr[sim_idx][2])));
            addData("DL Mod TB2", this.mMappingQam.get(Integer.valueOf(this.oldDlMod1Pcell[sim_idx])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod1ScellArr[sim_idx][0])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod1ScellArr[sim_idx][1])), this.mMappingQam.get(Integer.valueOf(this.oldDlMod1ScellArr[sim_idx][2])));
            addData("UL Mod", this.mMappingQam.get(Integer.valueOf(this.oldUlPcell[sim_idx])), this.mMappingQam.get(Integer.valueOf(this.oldUlScellArr[sim_idx][0])), this.mMappingQam.get(Integer.valueOf(this.oldUlScellArr[sim_idx][1])), this.mMappingQam.get(Integer.valueOf(this.oldUlScellArr[sim_idx][2])));
        }
    }
}
