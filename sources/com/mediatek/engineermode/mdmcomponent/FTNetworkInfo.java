package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.radio.V1_0.SmsAcknowledgeFailCause;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaPlayer2;
import android.support.v4.media.subtitle.Cea708CCParser;
import android.telephony.TelephonyManager;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.WorldModeUtil;
import com.mediatek.engineermode.channellock.ChannelLockReceiver;
import com.mediatek.engineermode.mdmcomponent.CombinationViewComponent;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: MDMComponentFT */
class FTNetworkInfo extends CombinationViewComponent {
    private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EMM_PLMNSEL_INFO_IND, MDMContent.MSG_ID_EM_EL2_OV_STATUS_IND, MDMContent.MSG_ID_EM_ERRC_SERVING_INFO_IND, MDMContent.MSG_ID_EM_EL1_STATUS_IND, MDMContent.MSG_ID_EM_ERRC_STATE_IND, MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND, MDMContent.MSG_ID_EM_RRM_MEASUREMENT_REPORT_INFO_IND, MDMContent.MSG_ID_EM_GSM_TAS_INFO_IND, MDMContent.MSG_ID_EM_C2K_L4_EVDO_SERVING_INFO_IND, MDMContent.MSG_ID_EM_C2K_L4_RTT_RADIO_INFO_IND, MDMContent.MSG_ID_EM_MM_INFO_IND, MDMContent.MSG_ID_TDD_EM_CSCE_SERV_CELL_S_STATUS_IND, MDMContent.MSG_ID_EM_TDD_TAS_INFO_IND, MDMContent.MSG_ID_FDD_EM_CSCE_SERV_CELL_S_STATUS_IND, MDMContent.MSG_ID_FDD_EM_MEME_DCH_UMTS_CELL_INFO_IND, MDMContent.MSG_ID_FDD_EM_URR_3G_GENERAL_STATUS_IND, MDMContent.MSG_ID_FDD_EM_UL1_TAS_INFO_IND, MDMContent.MSG_ID_TDD_EM_URR_3G_GENERAL_STATUS_IND, MDMContent.MSG_ID_EM_RRM_LAI_INFO_IND, MDMContent.MSG_ID_EM_C2K_L4_SPRINT_EVDO_INFO_IND, MDMContent.MSG_ID_EM_C2K_L4_SPRINT_XRTT_INFO_IND, MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTERRAT_UTRAN_INFO_IND, MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTERRAT_GERAN_INFO_IND, MDMContent.MSG_ID_TDD_EM_MEME_DCH_LTE_CELL_INFO_IND, MDMContent.MSG_ID_TDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND, MDMContent.MSG_ID_TDD_EM_MEME_DCH_UMTS_CELL_INFO_IND, MDMContent.MSG_ID_TDD_EM_MEME_DCH_GSM_CELL_INFO_IND, MDMContent.MSG_ID_EM_RRM_IR_4G_NEIGHBOR_MEAS_STATUS_IND, MDMContent.MSG_ID_EM_RRM_IR_3G_NEIGHBOR_MEAS_STATUS_IND, MDMContent.MSG_ID_FDD_EM_CSCE_NEIGH_CELL_S_STATUS_IND, MDMContent.MSG_ID_FDD_EM_MEME_DCH_LTE_CELL_INFO_IND, MDMContent.MSG_ID_FDD_EM_MEME_DCH_GSM_CELL_INFO_IND, MDMContent.MSG_ID_FDD_EM_UL1_HSPA_INFO_GROUP_IND, MDMContent.MSG_ID_EM_CSR_STATUS_IND, MDMContent.MSG_ID_EM_GMM_INFO_IND, MDMContent.MSG_ID_EM_MM_LU_INFO_IND, MDMContent.MSG_ID_EM_SM_NSAPI5_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI6_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI7_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI8_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI9_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI10_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI11_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI12_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI13_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI14_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI15_STATUS_IND, MDMContent.MSG_ID_EM_SPEECH_INFO_SPH_CODEC_IND, MDMContent.MSG_ID_EM_EMM_REG_ATTACH_INFO_IND, MDMContent.MSG_ID_EM_EMM_REG_TAU_INFO_IND, MDMContent.MSG_ID_EM_ESM_ESM_INFO_IND, MDMContent.MSG_ID_EM_TCM_INFO_IND};
    private static final String INTENT_KEY_ICC_STATE = "ss";
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    private static final int SIM_STATE_ABSENT = 1;
    private static final int SIM_STATE_UNKNOWN = 0;
    HashMap<Integer, String> AntennaMapping = new HashMap<Integer, String>() {
        {
            put(0, "LANT");
            put(1, "UANT");
            put(2, "LANT(')");
            put(3, "UANT");
            put(4, "-");
        }
    };
    HashMap<Integer, String> ERRCStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "Initial");
            put(1, "Standby");
            put(2, "Idle");
            put(3, "Connected");
            put(4, "Flight");
            put(5, "Idle");
            put(6, "Connected");
        }
    };
    /* access modifiers changed from: private */
    public String[] Labels;
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "Band 34");
            put(1, "Band 39");
            put(2, "-");
        }
    };
    private BroadcastReceiver SimCardChangedReceiver = null;
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
            put(2, "-");
        }
    };
    int TasVersion = 1;
    HashMap<Integer, String> attachTypeMapping = new HashMap<Integer, String>() {
        {
            put(1, "GPRS_ATTACH");
            put(2, "GPRS_ATTACH_WHILE_IMSI_ATTACH");
            put(3, "COMBINED_ATTACH");
            put(4, "EMERGENCY_ATTACH");
            put(5, "INVALID");
        }
    };
    HashMap<Integer, String> bandMapping = new HashMap<Integer, String>() {
        {
            put(0, "PGSM");
            put(1, "EGSM");
            put(2, "RGSM");
            put(3, "DCS1800");
            put(4, "PCS1900");
            put(5, "GSM450");
            put(6, "GSM480");
            put(7, "GSM850");
        }
    };
    private String[] cids = new String[11];
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    HashMap<Integer, String> gmmStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "NULL");
            put(1, "DEREG_NORMAL_SERVICE");
            put(2, "DEREG_SUSPEND");
            put(3, "DEREG_LTD_SERVICE");
            put(4, "DEREG_ATTACH_NEEDED");
            put(5, "DEREG_ATTEMPT_TO_ATTACH");
            put(6, "DEREG_NO_IMSI");
            put(7, "DEREG_NO_CELL");
            put(8, "DEREG_PLMN_SEARCH");
            put(9, "REG_INIT");
            put(10, "REG_NORMAL_SERVICE");
            put(11, "REG_SUSPEND");
            put(12, "REG_UPDATE_NEEDED");
            put(13, "REG_ATTEMPT_TO_UPDATE");
            put(14, "REG_ATTEMPT_TO_UPDATE_MM");
            put(15, "REG_NO_CELL");
            put(16, "REG_LTD_SERVICE");
            put(17, "DEREG_INIT");
            put(18, "RAU_INIT");
            put(19, "REG_IMSI_DETACH_INIT");
            put(20, "SERVICE_REQ_INIT");
            put(21, "REG_PLMN_SEARCH");
        }
    };
    HashMap<Integer, String> gsmBandMapping = new HashMap<Integer, String>() {
        {
            put(0, "DCS1800");
            put(1, "PCS1900");
            put(2, "-");
        }
    };
    private String[] linkedCids = new String[11];
    private LocationManager locationManager = null;
    HashMap<Integer, String> lteAttachTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "NONE");
            put(1, "EPS_ATTACH");
            put(2, "COMBINED_ATTACH");
            put(3, "EMERGENCY_ATTACH");
        }
    };
    HashMap<Integer, String> lteTAUTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "TAU");
            put(1, "COMBINED_TAU");
            put(2, "COMBINED_TAU_IMSI_ATTACH");
            put(3, "PERIODIC_TAU");
            put(4, "INVALID");
        }
    };
    private String[] lte_ebi_s = new String[11];
    private String[] lte_index_s = new String[15];
    private String[] lte_pdpapn_s = new String[11];
    private String[] lte_ptebi_s = new String[15];
    private String[] lte_ptlinkebi_s = new String[15];
    HashMap<Integer, String> luTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "MM_NORMAL_LU");
            put(1, "MM_PERIODIC_LU");
            put(2, "MM_IMSI_ATTACH_LU");
            put(3, "MM_NONE_ATTEMPTED");
        }
    };
    /* access modifiers changed from: private */
    public boolean mFirstBroadcast = true;
    private final HashMap<String, String[]> mKeyWordMapping = new HashMap<String, String[]>() {
        {
            put("UMTS FDD", new String[]{"Cell ID", "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC});
            put("UMTS TDD", new String[]{"Cell ID", "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC});
        }
    };
    public LocationListener mLocListener = new LocationListener() {
        public void onLocationChanged(Location mLocation) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
            FTNetworkInfo fTNetworkInfo = FTNetworkInfo.this;
            fTNetworkInfo.location = latitude + ":" + longitude;
        }

        public void onProviderDisabled(String provider) {
            Elog.v("EmInfo/MDMComponent", "Enter onProviderDisabled function");
        }

        public void onProviderEnabled(String provider) {
            Elog.v("EmInfo/MDMComponent", "Enter onProviderEnabled function");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Elog.v("EmInfo/MDMComponent", "Enter onStatusChanged function");
        }
    };
    HashMap<Integer, String> mLteMappingQam = new HashMap<Integer, String>() {
        {
            put(1, "QPSK");
            put(2, "16QAM");
            put(3, "64QAM");
            put(4, "256QAM");
        }
    };
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
            put(4, "128QAM");
            put(5, "256QAM");
        }
    };
    HashMap<Integer, String> mMappingSpeechCodec = new HashMap<Integer, String>() {
        {
            put(0, "FR");
            put(1, "HR");
            put(2, "EFR");
            put(3, "AMR_12_20");
            put(4, "AMR_10_20");
            put(5, "AMR_7_95");
            put(6, "AMR_7_40");
            put(7, "AMR_6_70");
            put(8, "AMR_5_90");
            put(9, "AMR_5_15");
            put(10, "AMR_4_75");
            put(11, "AMR_SID");
            put(12, "AMR_NODATA");
            put(32, "AMRWB_6_60");
            put(33, "AMRWB_8_85");
            put(34, "AMRWB_12_65");
            put(35, "AMRWB_14_25");
            put(36, "AMRWB_15_85");
            put(37, "AMRWB_18_25");
            put(38, "AMRWB_19_85");
            put(39, "AMRWB_23_05");
            put(40, "AMRWB_23_85");
            put(41, "AMRWB_SID");
            put(42, "SLOST_FRAME");
            put(48, "SC2K_SO1");
            put(49, "SC2K_SO3");
            put(50, "SC2K_SO17");
            put(51, "SC2K_SO68");
            put(52, "SC2K_SO73");
            put(53, "SC2K_SO73WB");
            put(96, "SG711");
            put(97, "SG722");
            put(98, "SG723_1");
            put(99, "SG726");
            put(100, "G729");
            put(128, "EVS_08K_005_9/SPH_CODEC_EVS_08K_002_8");
            put(129, "EVS_08K_007_2");
            put(130, "EVS_08K_008_0");
            put(131, "EVS_08K_009_6");
            put(132, "EVS_08K_013_2");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_CW5), "EVS_08K_016_4");
            put(134, "EVS_08K_024_4");
            put(135, "EVS_08K_032_0");
            put(136, "EVS_08K_048_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DSW), "EVS_08K_064_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_HDW), "EVS_08K_096_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_TGW), "EVS_08K_128_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLW), "EVS_08K_002_4_SID");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLY), "EVS_08K_000_0_REV");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLC), "EVS_08K_000_0_LOST");
            put(143, "EVS_08K_000_0_NODATA");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPA), "EVS_16K_005_9/SPH_CODEC_EVS_16K_002_8");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPC), "EVS_16K_007_2");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPL), "EVS_16K_008_0");
            put(147, "EVS_16K_009_6");
            put(148, "EVS_16K_013_2");
            put(149, "EVS_16K_016_4");
            put(150, "EVS_16K_024_4");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SWA), "EVS_16K_032_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF0), "EVS_16K_048_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF1), "EVS_16K_064_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF2), "EVS_16K_096_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF3), "EVS_16K_128_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF4), "EVS_16K_002_4_SID");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF5), "EVS_16K_000_0_REV");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF6), "EVS_16K_000_0_LOST");
            put(159, "EVS_16K_000_0_NODATA");
            put(160, "EVS_32K_005_9/SPH_CODEC_EVS_32K_002_8");
            put(161, "EVS_32K_007_2");
            put(162, "EVS_32K_008_0");
            put(163, "EVS_32K_009_6");
            put(164, "EVS_32K_013_2");
            put(165, "EVS_32K_016_4");
            put(166, "EVS_32K_024_4");
            put(167, "EVS_32K_032_0");
            put(168, "EVS_32K_048_0");
            put(169, "EVS_32K_064_0");
            put(170, "EVS_32K_096_0");
            put(171, "EVS_32K_128_0");
            put(172, "EVS_32K_002_4_SID");
            put(173, "EVS_32K_000_0_REV");
            put(174, "EVS_32K_000_0_LOST");
            put(175, "EVS_32K_000_0_NODATA");
            put(176, "EVS_48K_005_9/SPH_CODEC_EVS_48K_002_8");
            put(177, "EVS_48K_007_2");
            put(178, "EVS_48K_008_0");
            put(179, "EVS_48K_009_6");
            put(180, "EVS_48K_013_2");
            put(181, "EVS_48K_016_4");
            put(182, "EVS_48K_024_4");
            put(183, "EVS_48K_032_0");
            put(184, "EVS_48K_048_0");
            put(185, "EVS_48K_064_0");
            put(186, "EVS_48K_096_0");
            put(187, "EVS_48K_128_0");
            put(188, "EVS_48K_002_4_SID");
            put(189, "EVS_48K_000_0_REV");
            put(190, "EVS_48K_000_0_LOST");
            put(191, "EVS_48K_000_0_NODATA");
            put(208, "EVS_AWB_06_60");
            put(209, "EVS_AWB_08_85");
            put(210, "EVS_AWB_12_65");
            put(Integer.valueOf(SmsAcknowledgeFailCause.MEMORY_CAPACITY_EXCEEDED), "EVS_AWB_14_25");
            put(212, "EVS_AWB_15_85");
            put(213, "EVS_AWB_18_25");
            put(214, "EVS_AWB_19_85");
            put(215, "EVS_AWB_23_05");
            put(216, "EVS_AWB_23_85");
            put(217, "EVS_AWB_02_00_SID");
            put(218, "EVS_AWB_00_00_REV0");
            put(219, "EVS_AWB_00_00_REV1");
            put(220, "EVS_AWB_00_00_REV2");
            put(221, "EVS_AWB_00_00_REV3");
            put(222, "EVS_AWB_00_00_LOST");
            put(223, "EVS_AWB_00_00_NODATA");
            put(255, "CODEC_NONE");
        }
    };
    private final Map<Integer, String> mRrStateMapping = new HashMap<Integer, String>() {
        {
            put(3, "Idle");
            put(5, "Packet Transfer");
            put(6, "Dedicated");
        }
    };
    private final HashMap<Integer, String> mStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "Idle");
            put(1, "Cell FACH");
            put(2, "Cell PCH");
            put(3, "URA PCH");
            put(4, "Cell DCH");
            put(5, "INACTIVE");
            put(6, "NO_CHANGE");
            put(7, "Cell FACH");
            put(8, "Cell PCH");
            put(9, "Cell PCH");
            put(10, "URA PCH");
            put(11, "Cell FACH");
            put(12, "Cell PCH");
            put(13, "Cell PCH");
        }
    };
    private String[] networkInfo = {"UnKnown", "UnKnown"};
    private int oldDlMod0Pcell = 0;
    private int oldDlMod1Pcell = 0;
    private int oldUlMode = 0;
    private String[] pdpIndex = new String[11];
    private String[] pdpStatus = new String[11];
    HashMap<Integer, String> pdpStatusMapping = new HashMap<Integer, String>() {
        {
            put(0, "INACTIVE");
            put(1, "ACTIVE_PENDING");
            put(2, "INACTIVE_PENDING");
            put(3, "ACTIVE");
            put(4, "MODIFY_PENDING");
        }
    };
    private String[] pdpapn_s = new String[11];
    Set<String> repeateMsgArray = new HashSet<String>() {
        {
            add(MDMContent.MSG_ID_FDD_EM_URR_3G_GENERAL_STATUS_IND);
            add(MDMContent.MSG_ID_TDD_EM_URR_3G_GENERAL_STATUS_IND);
        }
    };
    HashMap<Integer, String> speechCodecMapping = new HashMap<Integer, String>() {
        {
            put(0, "AMR_4_75");
            put(1, "AMR_5_15");
            put(2, "AMR_5_90");
            put(3, "AMR_6_70");
            put(4, "AMR_7_40");
            put(5, "AMR_7_95");
            put(6, "AMR_10_20");
            put(7, "AMR_12_20");
            put(8, "AMR_SID");
            put(9, "GSM_EFR_SID");
            put(10, "TDMA_EFR_SID");
            put(11, "PDC_EFR_SID");
            put(12, "RESERVE_1");
            put(13, "RESERVE_2");
            put(14, "RESERVE_3");
            put(15, "AMR_NODATA");
            put(16, "AMRWB_6_60");
            put(17, "AMRWB_8_85");
            put(18, "AMRWB_12_65");
            put(19, "AMRWB_14_25");
            put(20, "AMRWB_15_85");
            put(21, "AMRWB_18_25");
            put(22, "AMRWB_19_85");
            put(23, "AMRWB_23_05");
            put(24, "AMRWB_23_85");
            put(25, "AMRWB_SID");
            put(255, "CODEC_NONE");
        }
    };
    private boolean[] status = new boolean[11];

    public void resetPublicValues() {
        this.TasVersion = 1;
        this.oldDlMod0Pcell = 0;
        this.oldDlMod1Pcell = 0;
        this.oldUlMode = 0;
        this.pdpIndex = new String[11];
        this.pdpapn_s = new String[11];
        this.pdpStatus = new String[11];
        this.cids = new String[11];
        this.linkedCids = new String[11];
        this.status = new boolean[11];
        this.lte_pdpapn_s = new String[11];
        this.lte_ebi_s = new String[11];
        this.lte_index_s = new String[15];
        this.lte_ptebi_s = new String[15];
        this.lte_ptlinkebi_s = new String[15];
    }

    private String getValueFromMapping(int key, HashMap<Integer, String> map) {
        if (map.containsKey(Integer.valueOf(key))) {
            return map.get(Integer.valueOf(key));
        }
        return "-(" + key + ")";
    }

    public FTNetworkInfo(Activity context, int simCount) {
        super(context, simCount);
    }

    public void registeListener() {
        initImsiList(getSupportSimCount());
        initLocation();
        startLocation();
        registSimReceiver(this.mContext);
    }

    public void unRegisteListener() {
        this.imsi = new String[getSupportSimCount()];
        if (this.SimCardChangedReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.SimCardChangedReceiver);
            } catch (Exception e) {
                Elog.e("EmInfo/MDMComponent", "mContext.unregisterReceiver SimCardChangedReceiver: " + e.getMessage());
            } catch (Throwable th) {
                this.SimCardChangedReceiver = null;
                throw th;
            }
            this.SimCardChangedReceiver = null;
        }
        LocationManager locationManager2 = this.locationManager;
        if (locationManager2 != null) {
            try {
                locationManager2.removeUpdates(this.mLocListener);
            } catch (Exception e2) {
                Elog.e("EmInfo/MDMComponent", "locationManager.removeUpdates : " + e2.getMessage());
            } catch (Throwable th2) {
                this.locationManager = null;
                throw th2;
            }
            this.locationManager = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void initImsiList(int simID) {
        Activity activity = this.mActivity;
        Activity activity2 = this.mActivity;
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService("phone");
        String str = "";
        if (isValidSimID(simID)) {
            if (simID >= this.imsi.length) {
                String[] oldImsi = this.imsi;
                this.imsi = new String[simID];
                System.arraycopy(oldImsi, 0, this.imsi, 0, oldImsi.length);
            }
            String mImsi = !isSimCardAbsent(simID) ? telephonyManager.getSubscriberId(simID) : str;
            String[] strArr = this.imsi;
            if (mImsi != null) {
                str = mImsi;
            }
            strArr[simID] = str;
            return;
        }
        this.imsi = null;
        this.imsi = new String[getSupportSimCount()];
        for (int i = 0; i < getSupportSimCount(); i++) {
            String mImsi2 = !isSimCardAbsent(i) ? telephonyManager.getSubscriberId(i) : str;
            this.imsi[i] = mImsi2 != null ? mImsi2 : str;
        }
    }

    public boolean isSimCardAbsent(int simID) {
        Activity activity = this.mActivity;
        Activity activity2 = this.mActivity;
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService("phone");
        if (telephonyManager.getSimState() == 1 || telephonyManager.getSimState() == 0) {
            return true;
        }
        return false;
    }

    public void stopGetLocation() {
        LocationManager locationManager2 = this.locationManager;
        if (locationManager2 != null) {
            locationManager2.removeUpdates(this.mLocListener);
            this.locationManager = null;
        }
    }

    public void startLocation() {
        LocationManager locationManager2 = (LocationManager) this.mContext.getSystemService("location");
        this.locationManager = locationManager2;
        if (locationManager2 != null) {
            if (locationManager2.isProviderEnabled("gps")) {
                this.locationManager.requestLocationUpdates("gps", 0, 0.0f, this.mLocListener);
            }
            if (this.locationManager.isProviderEnabled("network")) {
                this.locationManager.requestLocationUpdates("network", 0, 0.0f, this.mLocListener);
            }
        }
    }

    public void initLocation() {
        try {
            LocationManager locationManager2 = (LocationManager) this.mContext.getSystemService("location");
            this.locationManager = locationManager2;
            if (locationManager2 == null) {
                Elog.e("EmInfo/MDMComponent", "[initLocation]new mLocationManager failed");
            } else if (locationManager2.isProviderEnabled("gps")) {
                Location mLocation = this.locationManager.getLastKnownLocation("gps");
                if (mLocation != null) {
                    double latitude = mLocation.getLatitude();
                    double longitude = mLocation.getLongitude();
                    this.location = latitude + ":" + longitude;
                }
            } else if (this.locationManager.isProviderEnabled("network")) {
                Location mLocation2 = this.locationManager.getLastKnownLocation("network");
                if (mLocation2 != null) {
                    double latitude2 = mLocation2.getLatitude();
                    double longitude2 = mLocation2.getLongitude();
                    this.location = latitude2 + ":" + longitude2;
                }
            }
        } catch (SecurityException e) {
            Toast.makeText(this.mContext, "security exception", 1).show();
            Elog.e("EmInfo/MDMComponent", "[initLocation]SecurityException: " + e.getMessage());
        } catch (IllegalArgumentException e2) {
            Elog.e("EmInfo/MDMComponent", "[initLocation]IllegalArgumentException: " + e2.getMessage());
        } catch (Throwable th) {
            this.locationManager = null;
            throw th;
        }
        this.locationManager = null;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "FT Network Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "1. General EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] initLabels() {
        return new String[]{"LTE-Pcell", "LTE-Tas Info", "LTE-Scell", "LTE-Neighbor Cell", "LTE-Neighbor Cell.1", "LTE-Neighbor Cell.2", "LTE-Pcell and Scell basic info", "LTE-PDP Context Information", "GSM-Serving Cell", "GSM-Tas Info", "GSM-Neighbor Cell", "GSM-Neighbor Cell.1", "GSM-Neighbor Cell.2", "EVDO-Basic Info", "1xRTT-Basic Info", "1xRTT-Tas Info", "UMTS TDD-Serving cell", "UMTS TDD-Tas Info", "UMTS TDD-Neighbor Cell", "UMTS TDD-Neighbor Cell.1", "UMTS TDD-Neighbor Cell.2", "UMTS FDD-Serving cell", "UMTS FDD-Active Set", "UMTS FDD-Tas Info", "UMTS FDD-DC Info", "UMTS FDD-PDN Context List", "UMTS FDD-Neighbor Cell", "UMTS FDD-Neighbor Cell.1", "UMTS FDD-Neighbor Cell.2"};
    }

    /* access modifiers changed from: package-private */
    public ArrayList<LinkedHashMap> initHashMapValues() {
        ArrayList<LinkedHashMap> valuesHashMap = new ArrayList<>();
        valuesHashMap.add(initHashMap((Object[]) new String[]{"PLMN", "TAC", "Cell ID", "EARFCN", "PCI", "Band", "DL_BW", "RSRP", "RSRQ", "SINR", "RSSI", "ERRC State", "Antenna", "RI", "TM", "DL Imcs", "DL Mod TB1", "DL Mod TB2", "UL Imcs", "UL Mod", "UL speech codec", "DL speech codec", "Attach Type", "TAU Type"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"TAS Enable", "TX Antenna", "TX Power", "RSRP_LANT", "RSRP_UANT"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"EARFCN", "PCI", "Band", "RSRP", "RSRQ", "SINR"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"LTE", "EARFCN", "PCI", "RSRP", "RSRQ"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"UMTS", "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", "Ec/No"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"GSM", ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN, "BSIC", "RSSI"}));
        String str = "GSM";
        String str2 = ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN;
        String str3 = "BSIC";
        valuesHashMap.add(initHashMap((Object[]) new String[]{"", "Pcell", "Scell0", "Scell1", "Scell2"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"Index", "APN", "Bearer ID", "Dedicated Bearer ID", "Dedicated Linked Bearer ID"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"PLMN", "LAC", "Cell ID", ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN, "BSIC", "band", "RLA", "RX quality sub", "RR state", "gmm state", "UL speech codec", "DL speech codec"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"TAS Enable", "Antenna Index", "Current Antenna Power", "Other Antenna Power"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"LTE", "EARFCN", "PCI", "RSRP", "RSRQ"}));
        String str4 = "Ec/No";
        String str5 = "UMTS";
        valuesHashMap.add(initHashMap((Object[]) new String[]{str5, "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", str4}));
        String str6 = str4;
        String str7 = "RSSI";
        valuesHashMap.add(initHashMap((Object[]) new String[]{str, str2, str3, str7}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"Channel", "pilotPN", MDMContent.C2K_L4_BAND_CLASS, str7, "ecio"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"Channel", "pilotPN", MDMContent.C2K_L4_BAND_CLASS, str7, "ecio"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"TAS Enable", "tx_Ant", "rxPower_LANT", "rxPower_UANT"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"PLMN", "LAC", "Cell ID", "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "band", "RSCP", "RSSI", "RRC state"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"TAS Enable", "TX Antenna", "TX Power", "RSRP_LANT", "RSRP_UANT"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"LTE", "EARFCN", "PCI", "RSRP", "RSRQ"}));
        String str8 = str5;
        valuesHashMap.add(initHashMap((Object[]) new String[]{str8, "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP"}));
        String str9 = str;
        String str10 = str2;
        String str11 = str3;
        valuesHashMap.add(initHashMap((Object[]) new String[]{str9, str10, str11, str7}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"PLMN", "LAC", "Cell ID", "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "band", "RSCP", "Ec/No", "RSSI", "RRC state", "RAC", "UL speech codec", "DL speech codec", "Attach Type", "Location Update Type"}));
        String str12 = str7;
        String str13 = str6;
        valuesHashMap.add(initHashMap((Object[]) new String[]{"UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", str13}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"TAS Enable", "TX Antenna", "TX Power", "RSRP_LANT", "RSRP_UANT", "RSRP_UANT(')"}));
        String str14 = str11;
        valuesHashMap.add(initHashMap((Object[]) new String[]{"State", "P-carrier UARFCN", "P-carrier PSC", "S-carrier UARFCN", "S-carrier PSC"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"NSAPI Index", "APN", "State", "Cid", "Linked Cid"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{"LTE", "EARFCN", "PCI", "RSRP", "RSRQ"}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{str8, "UARFCN", MDMContent.FDD_EM_MEME_DCH_UMTS_PSC, "RSCP", str13}));
        valuesHashMap.add(initHashMap((Object[]) new String[]{str9, str10, str14, str12}));
        return valuesHashMap;
    }

    public ArrayList<String> getArrayTypeLabels() {
        ArrayList<String> arrayTypeKeys = new ArrayList<>();
        arrayTypeKeys.add("LTE-Scell");
        arrayTypeKeys.add("LTE-Neighbor Cell");
        arrayTypeKeys.add("GSM-Neighbor Cell");
        arrayTypeKeys.add("UMTS FDD-Active Set");
        arrayTypeKeys.add("UMTS TDD-Neighbor Cell");
        arrayTypeKeys.add("UMTS FDD-Neighbor Cell");
        arrayTypeKeys.add("UMTS FDD-PDN Context List");
        arrayTypeKeys.add("LTE-Pcell and Scell basic info");
        arrayTypeKeys.add("LTE-PDP Context Information");
        return arrayTypeKeys;
    }

    /* access modifiers changed from: package-private */
    public String rscpCheck(int value) {
        String value_s = String.valueOf(value);
        if (value == -255) {
            return " ";
        }
        return value_s;
    }

    /* access modifiers changed from: package-private */
    public String antidxMapping(int antidx) {
        if (antidx >= 0 && antidx <= 3) {
            return this.AntennaMapping.get(Integer.valueOf(antidx));
        }
        return this.AntennaMapping.get(4) + "(" + antidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx < 0 || tasidx > 1) {
            return this.TasEnableMapping.get(2);
        }
        return this.TasEnableMapping.get(Integer.valueOf(tasidx));
    }

    /* access modifiers changed from: package-private */
    public int parsIndex(String name) {
        return Integer.parseInt(name.substring(18).split("_")[0]) - 5;
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        if (bandidx >= 0 && bandidx <= 1) {
            return this.ServingBandMapping.get(Integer.valueOf(bandidx));
        }
        return this.ServingBandMapping.get(2) + "(" + bandidx + ")";
    }

    /* access modifiers changed from: package-private */
    public int getIndexOfEmTypes(String name) {
        int i = 0;
        while (true) {
            String[] strArr = EM_TYPES;
            if (i >= strArr.length) {
                return -1;
            }
            if (strArr[i].equals(name)) {
                return i;
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public String getBandMapping(int bandidx) {
        if (bandidx == 0 || bandidx == 1) {
            return this.gsmBandMapping.get(Integer.valueOf(bandidx));
        }
        return this.gsmBandMapping.get(2) + "(" + bandidx + ")";
    }

    /* access modifiers changed from: package-private */
    public void registSimReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SIM_STATE_CHANGED);
        AnonymousClass23 r1 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (FTNetworkInfo.this.mFirstBroadcast) {
                    boolean unused = FTNetworkInfo.this.mFirstBroadcast = false;
                } else if (action != null && action.equals(FTNetworkInfo.ACTION_SIM_STATE_CHANGED)) {
                    String newState = intent.getStringExtra(FTNetworkInfo.INTENT_KEY_ICC_STATE);
                    int changeSlot = intent.getIntExtra("slot", 0);
                    Elog.d("EmInfo/MDMComponent", "SIM state change changeSlot=" + changeSlot + " new state =" + newState);
                    FTNetworkInfo.this.initImsiList(changeSlot);
                    FTNetworkInfo fTNetworkInfo = FTNetworkInfo.this;
                    fTNetworkInfo.updateCommonView(fTNetworkInfo.getCurrentSimID());
                }
            }
        };
        this.SimCardChangedReceiver = r1;
        context.registerReceiver(r1, intentFilter);
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int simID;
        CombinationViewComponent.Task task;
        Msg data = (Msg) msg;
        long currentTimeMillis = System.currentTimeMillis();
        if (this.Labels == null) {
            this.Labels = initLabels();
        }
        if ((data.getSimIdx() - 1) % 2 == 0) {
            simID = ModemCategory.getCapabilitySim() % 2;
        } else {
            simID = (ModemCategory.getCapabilitySim() + 1) % 2;
        }
        if (this.repeateMsgArray.contains(name)) {
            task = new CombinationViewComponent.Task(1, name, data, simID, true);
        } else {
            task = new CombinationViewComponent.Task(1, name, data, simID);
        }
        this.mUpdateTaskDriven.appendTask(task);
    }

    /* access modifiers changed from: package-private */
    public void startUpdateProcess(CombinationViewComponent.Task task) {
        new UpdateViewTask().execute(new CombinationViewComponent.Task[]{task});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:354:0x1cde, code lost:
        r90 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:703:0x41c9, code lost:
        r39 = r13;
        r41 = r14;
        r4 = r17;
        r15 = r48;
        r6 = r85;
        r48 = r32;
        r32 = r77;
        r100 = r37;
        r37 = r35;
        r35 = r100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:785:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:815:?, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.HashMap<java.lang.String, java.lang.Boolean> getUpdateMap(java.lang.String[] r102, int r103, java.lang.String r104, java.lang.Object r105) {
        /*
            r101 = this;
            r0 = r101
            r1 = r103
            r2 = r104
            r3 = r105
            com.mediatek.mdml.Msg r3 = (com.mediatek.mdml.Msg) r3
            java.lang.String r4 = ""
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            java.lang.String r6 = ""
            java.lang.String r7 = ""
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 2
            r14 = 0
            r15 = 0
            java.lang.String r16 = ""
            r17 = r4
            int r4 = r0.getIndexOfEmTypes(r2)
            r18 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r19 = r8
            java.lang.String r8 = "Sim"
            r7.append(r8)
            r7.append(r1)
            java.lang.String r8 = ", update: "
            r7.append(r8)
            r7.append(r4)
            java.lang.String r8 = ","
            r7.append(r8)
            r7.append(r2)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r8, r7)
            r0.initHashMap((int) r1)
            r7 = 0
            r20 = r9
            r9 = 3
            r21 = r10
            long[] r10 = new long[r9]
            r22 = r11
            r11 = 0
            r23 = r12
            int[] r12 = new int[r9]
            r24 = r13
            r13 = 0
            r25 = r14
            int[] r14 = new int[r9]
            r26 = r15
            r15 = 0
            r27 = r7
            int[] r7 = new int[r9]
            r8 = 0
            r29 = r11
            int[] r11 = new int[r9]
            r30 = r11
            r11 = 0
            r31 = r11
            int[] r11 = new int[r9]
            r32 = r8
            r8 = 0
            r33 = r8
            int[] r8 = new int[r9]
            r34 = r7
            r7 = 0
            r35 = r7
            int[] r7 = new int[r9]
            r36 = r7
            r7 = 0
            r37 = r7
            int[] r7 = new int[r9]
            r38 = r7
            r7 = 0
            r39 = r7
            int[] r7 = new int[r9]
            r40 = r15
            r15 = 0
            r41 = r15
            int[] r15 = new int[r9]
            r42 = 0
            r43 = r14
            r14 = 0
            r44 = r14
            int[] r14 = new int[r9]
            r45 = r14
            int[] r14 = new int[r9]
            r46 = 0
            r47 = r14
            r14 = 0
            r48 = r14
            int[] r14 = new int[r9]
            r49 = r14
            int[] r14 = new int[r9]
            r50 = 0
            r51 = r14
            r14 = 0
            r52 = r14
            int[] r14 = new int[r9]
            r53 = r14
            int[] r14 = new int[r9]
            r9 = 4
            r55 = r14
            int[] r14 = new int[r9]
            r56 = r14
            int[] r14 = new int[r9]
            r57 = r14
            int[] r14 = new int[r9]
            r58 = r14
            int[] r14 = new int[r9]
            r59 = r14
            int[] r14 = new int[r9]
            r60 = r14
            int[] r14 = new int[r9]
            r61 = r14
            int[] r14 = new int[r9]
            r62 = r14
            int[] r14 = new int[r9]
            r63 = r14
            int[] r14 = new int[r9]
            r64 = r14
            int[] r14 = new int[r9]
            r65 = r14
            int[] r14 = new int[r9]
            r66 = r14
            int[] r14 = new int[r9]
            java.lang.String r9 = "UARFCN"
            r68 = r14
            java.lang.String r14 = "mnc"
            r70 = r13
            java.lang.String r13 = "mcc"
            r71 = r12
            java.lang.String r12 = "RSRQ"
            r72 = r8
            java.lang.String r8 = "RSRP"
            r73 = r15
            java.lang.String r15 = "PCI"
            r74 = r7
            java.lang.String r7 = "EARFCN"
            r75 = r11
            r78 = 1082130432(0x40800000, float:4.0)
            java.lang.String r11 = "["
            r81 = r10
            r82 = 0
            java.lang.String r10 = "]."
            r85 = r6
            java.lang.String r6 = ""
            r86 = r14
            r14 = 1
            r87 = r13
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r14)
            switch(r4) {
                case 0: goto L_0x4093;
                case 1: goto L_0x4078;
                case 2: goto L_0x3fd4;
                case 3: goto L_0x3420;
                case 4: goto L_0x33b6;
                case 5: goto L_0x2876;
                case 6: goto L_0x2667;
                case 7: goto L_0x25e8;
                case 8: goto L_0x2585;
                case 9: goto L_0x249a;
                case 10: goto L_0x2342;
                case 11: goto L_0x2254;
                case 12: goto L_0x21be;
                case 13: goto L_0x1f49;
                case 14: goto L_0x1cf3;
                case 15: goto L_0x1c74;
                case 16: goto L_0x1b08;
                case 17: goto L_0x1ac2;
                case 18: goto L_0x193b;
                case 19: goto L_0x1908;
                case 20: goto L_0x18d1;
                case 21: goto L_0x1692;
                case 22: goto L_0x1503;
                case 23: goto L_0x1387;
                case 24: goto L_0x110d;
                case 25: goto L_0x1016;
                case 26: goto L_0x0f19;
                case 27: goto L_0x0de8;
                case 28: goto L_0x0cb4;
                case 29: goto L_0x08c0;
                case 30: goto L_0x07a6;
                case 31: goto L_0x06bb;
                case 32: goto L_0x060a;
                case 33: goto L_0x05f2;
                case 34: goto L_0x05a6;
                case 35: goto L_0x0573;
                case 36: goto L_0x049c;
                case 37: goto L_0x049c;
                case 38: goto L_0x049c;
                case 39: goto L_0x049c;
                case 40: goto L_0x049c;
                case 41: goto L_0x049c;
                case 42: goto L_0x049c;
                case 43: goto L_0x049c;
                case 44: goto L_0x049c;
                case 45: goto L_0x049c;
                case 46: goto L_0x049c;
                case 47: goto L_0x0421;
                case 48: goto L_0x03f0;
                case 49: goto L_0x03bf;
                case 50: goto L_0x01de;
                case 51: goto L_0x0145;
                default: goto L_0x012d;
            }
        L_0x012d:
            r2 = r1
            r90 = r3
            r88 = r4
            r1 = r5
            r77 = r32
            r13 = r39
            r14 = r41
            r32 = r48
            r48 = r40
            r100 = r37
            r37 = r35
            r35 = r100
            goto L_0x41c9
        L_0x0145:
            r7 = 25
            r7 = r102[r7]
            r5.put(r7, r13)
            java.lang.String r8 = "pdp.nsapi"
            int r8 = r0.getFieldValue(r3, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "pdp.nsapi: "
            r9.append(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            java.lang.String r10 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r10, r9)
            r9 = 5
            if (r8 < r9) goto L_0x01b5
            boolean[] r9 = r0.status
            int r10 = r8 + -5
            boolean r9 = r9[r10]
            if (r9 == 0) goto L_0x01b5
            java.lang.String[] r9 = r0.pdpIndex
            int r10 = r8 + -5
            r9 = r9[r10]
            if (r9 == 0) goto L_0x01b5
            java.lang.String[] r9 = r0.cids
            int r10 = r8 + -5
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "pdp.cid"
            int r12 = r0.getFieldValue(r3, r12)
            r11.append(r12)
            r11.append(r6)
            java.lang.String r11 = r11.toString()
            r9[r10] = r11
            java.lang.String r9 = "pdp.primary_context_id"
            int r9 = r0.getFieldValue(r3, r9)
            r10 = 255(0xff, float:3.57E-43)
            if (r9 == r10) goto L_0x01b5
            java.lang.String[] r10 = r0.linkedCids
            int r11 = r8 + -5
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r9)
            r12.append(r6)
            java.lang.String r6 = r12.toString()
            r10[r11] = r6
        L_0x01b5:
            java.lang.String[] r6 = r0.cids
            java.lang.String r9 = "Cid"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r9, (java.lang.String[]) r6)
            java.lang.String[] r6 = r0.linkedCids
            java.lang.String r9 = "Linked Cid"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r9, (java.lang.String[]) r6)
            r2 = r1
            r90 = r3
            r88 = r4
            r1 = r5
            r4 = r7
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x01de:
            r7 = 7
            r7 = r102[r7]
            r5.put(r7, r13)
            r0.resetHashMapKeyValues(r7, r1)
            r8 = 11
            java.lang.String[] r8 = new java.lang.String[r8]
            r0.lte_pdpapn_s = r8
            r8 = 11
            java.lang.String[] r8 = new java.lang.String[r8]
            r0.lte_ebi_s = r8
            r8 = 15
            java.lang.String[] r9 = new java.lang.String[r8]
            r0.lte_index_s = r9
            java.lang.String[] r9 = new java.lang.String[r8]
            r0.lte_ptebi_s = r9
            java.lang.String[] r8 = new java.lang.String[r8]
            r0.lte_ptlinkebi_s = r8
            r8 = 0
        L_0x0202:
            r9 = 11
            if (r8 >= r9) goto L_0x0381
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r11 = "esm_epsbc["
            r9.append(r11)
            r9.append(r8)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r9)
            java.lang.String r12 = "is_active"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            if (r11 != 0) goto L_0x0237
            r88 = r4
            r54 = r9
            goto L_0x0378
        L_0x0237:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r9)
            java.lang.String r13 = "is_active: "
            r12.append(r13)
            r12.append(r11)
            java.lang.String r12 = r12.toString()
            java.lang.String r13 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r13, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r9)
            java.lang.String r13 = "apn.length"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r3, r12)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r9)
            java.lang.String r15 = "bearer_type"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r3, r13)
            if (r13 != r14) goto L_0x02a9
            java.lang.String[] r15 = r0.lte_ebi_s
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r88 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r9)
            r17 = r11
            java.lang.String r11 = "ebi"
            r4.append(r11)
            java.lang.String r4 = r4.toString()
            int r4 = r0.getFieldValue(r3, r4)
            r14.append(r4)
            r14.append(r6)
            java.lang.String r4 = r14.toString()
            r15[r8] = r4
            goto L_0x02d8
        L_0x02a9:
            r88 = r4
            r17 = r11
            r4 = 2
            if (r13 != r4) goto L_0x02d8
            java.lang.String[] r4 = r0.lte_ptebi_s
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r9)
            java.lang.String r15 = "ebi"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r3, r14)
            r11.append(r14)
            r11.append(r6)
            java.lang.String r11 = r11.toString()
            r4[r8] = r11
        L_0x02d8:
            java.lang.String[] r4 = r0.lte_ptlinkebi_s
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r9)
            java.lang.String r15 = "linked_ebi"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r3, r14)
            r11.append(r14)
            r11.append(r6)
            java.lang.String r11 = r11.toString()
            r4[r8] = r11
            java.lang.String[] r4 = r0.lte_index_s
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r8)
            r11.append(r6)
            java.lang.String r11 = r11.toString()
            r4[r8] = r11
            java.lang.String[] r4 = r0.lte_pdpapn_s
            r4[r8] = r6
            r4 = 0
        L_0x0318:
            if (r4 >= r12) goto L_0x0376
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r9)
            java.lang.String r14 = "apn.data["
            r11.append(r14)
            r11.append(r4)
            java.lang.String r14 = "]"
            r11.append(r14)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            if (r4 <= 0) goto L_0x0357
            r14 = 31
            if (r11 >= r14) goto L_0x0357
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String[] r15 = r0.lte_pdpapn_s
            r54 = r9
            r9 = r15[r8]
            r14.append(r9)
            java.lang.String r9 = "."
            r14.append(r9)
            java.lang.String r9 = r14.toString()
            r15[r8] = r9
            goto L_0x0371
        L_0x0357:
            r54 = r9
            char r9 = (char) r11
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String[] r15 = r0.lte_pdpapn_s
            r67 = r11
            r11 = r15[r8]
            r14.append(r11)
            r14.append(r9)
            java.lang.String r11 = r14.toString()
            r15[r8] = r11
        L_0x0371:
            int r4 = r4 + 1
            r9 = r54
            goto L_0x0318
        L_0x0376:
            r54 = r9
        L_0x0378:
            int r8 = r8 + 1
            r85 = r54
            r4 = r88
            r14 = 1
            goto L_0x0202
        L_0x0381:
            r88 = r4
            java.lang.String[] r4 = r0.lte_index_s
            java.lang.String r6 = "Index"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r6, (java.lang.String[]) r4)
            java.lang.String[] r4 = r0.lte_pdpapn_s
            java.lang.String r6 = "APN"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r6, (java.lang.String[]) r4)
            java.lang.String[] r4 = r0.lte_ebi_s
            java.lang.String r6 = "Bearer ID"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r6, (java.lang.String[]) r4)
            java.lang.String[] r4 = r0.lte_ptebi_s
            java.lang.String r6 = "Dedicated Bearer ID"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r6, (java.lang.String[]) r4)
            java.lang.String[] r4 = r0.lte_ptlinkebi_s
            java.lang.String r6 = "Dedicated Linked Bearer ID"
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r1, (java.lang.String) r6, (java.lang.String[]) r4)
            r2 = r1
            r90 = r3
            r1 = r5
            r4 = r7
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x03bf:
            r88 = r4
            r4 = r102[r82]
            r5.put(r4, r13)
            java.lang.String r6 = "eps_update_type"
            int r6 = r0.getFieldValue(r3, r6)
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.lteTAUTypeMapping
            java.lang.String r6 = r0.getValueFromMapping(r6, r7)
            java.lang.String r7 = "TAU Type"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r7, (java.lang.Object) r6)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x03f0:
            r88 = r4
            r4 = r102[r82]
            r5.put(r4, r13)
            java.lang.String r6 = "eps_attach_type"
            int r6 = r0.getFieldValue(r3, r6)
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.lteAttachTypeMapping
            java.lang.String r6 = r0.getValueFromMapping(r6, r7)
            java.lang.String r7 = "Attach Type"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r7, (java.lang.Object) r6)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x0421:
            r88 = r4
            r4 = r102[r82]
            r5.put(r4, r13)
            java.lang.String r6 = "ulSphCodec"
            int r6 = r0.getFieldValue(r3, r6)
            java.lang.String r7 = "dlSphCodec"
            int r7 = r0.getFieldValue(r3, r7)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.mMappingSpeechCodec
            java.lang.String r8 = r0.getValueFromMapping(r6, r8)
            java.lang.String r9 = "UL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.mMappingSpeechCodec
            java.lang.String r8 = r0.getValueFromMapping(r7, r8)
            java.lang.String r9 = "DL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            r8 = 8
            r4 = r102[r8]
            r5.put(r4, r13)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.speechCodecMapping
            java.lang.String r8 = r0.getValueFromMapping(r6, r8)
            java.lang.String r9 = "UL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.speechCodecMapping
            java.lang.String r8 = r0.getValueFromMapping(r7, r8)
            java.lang.String r9 = "DL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            r8 = 21
            r4 = r102[r8]
            r5.put(r4, r13)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.mMappingSpeechCodec
            java.lang.String r8 = r0.getValueFromMapping(r6, r8)
            java.lang.String r9 = "UL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            java.util.HashMap<java.lang.Integer, java.lang.String> r8 = r0.mMappingSpeechCodec
            java.lang.String r8 = r0.getValueFromMapping(r7, r8)
            java.lang.String r9 = "DL speech codec"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.Object) r8)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x049c:
            r88 = r4
            r4 = 25
            r4 = r102[r4]
            r5.put(r4, r13)
            int r7 = r0.parsIndex(r2)
            java.lang.String r8 = "pdp.pdp_context_status"
            int r8 = r0.getFieldValue(r3, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "index: "
            r9.append(r10)
            r9.append(r7)
            java.lang.String r10 = " pdp_context_status:"
            r9.append(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            java.lang.String r10 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r10, r9)
            if (r8 == 0) goto L_0x04d8
            r9 = 2
            if (r8 == r9) goto L_0x04d8
            boolean[] r9 = r0.status
            r10 = 1
            r9[r7] = r10
            goto L_0x04dc
        L_0x04d8:
            boolean[] r9 = r0.status
            r9[r7] = r82
        L_0x04dc:
            boolean[] r9 = r0.status
            boolean r9 = r9[r7]
            r10 = 1
            if (r9 != r10) goto L_0x0546
            java.lang.String[] r9 = r0.pdpapn_s
            r9[r7] = r6
            r6 = 0
        L_0x04e8:
            r9 = 100
            if (r6 >= r9) goto L_0x051f
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "pdp.apn["
            r9.append(r10)
            r9.append(r6)
            java.lang.String r10 = "]"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r3, r9)
            char r9 = (char) r9
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String[] r11 = r0.pdpapn_s
            r12 = r11[r7]
            r10.append(r12)
            r10.append(r9)
            java.lang.String r10 = r10.toString()
            r11[r7] = r10
            int r6 = r6 + 1
            goto L_0x04e8
        L_0x051f:
            java.lang.String[] r6 = r0.pdpStatus
            java.lang.String r9 = "pdp.pdp_context_status"
            int r9 = r0.getFieldValue(r3, r9)
            java.util.HashMap<java.lang.Integer, java.lang.String> r10 = r0.pdpStatusMapping
            java.lang.String r9 = r0.getValueFromMapping(r9, r10)
            r6[r7] = r9
            java.lang.String[] r6 = r0.pdpIndex
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "SM_NSAP"
            r9.append(r10)
            int r10 = r7 + 5
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r6[r7] = r9
        L_0x0546:
            java.lang.String[] r6 = r0.pdpIndex
            java.lang.String r9 = "NSAPI Index"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.String[]) r6)
            java.lang.String[] r6 = r0.pdpapn_s
            java.lang.String r9 = "APN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.String[]) r6)
            java.lang.String[] r6 = r0.pdpStatus
            java.lang.String r9 = "State"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r9, (java.lang.String[]) r6)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x0573:
            r88 = r4
            r4 = 21
            r4 = r102[r4]
            r5.put(r4, r13)
            java.lang.String r6 = "lu_type"
            int r6 = r0.getFieldValue(r3, r6)
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.luTypeMapping
            java.lang.String r6 = r0.getValueFromMapping(r6, r7)
            java.lang.String r7 = "Location Update Type"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r7, (java.lang.Object) r6)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x05a6:
            r88 = r4
            r4 = 21
            r4 = r102[r4]
            r5.put(r4, r13)
            java.lang.String r6 = "attach_type"
            int r6 = r0.getFieldValue(r3, r6)
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.attachTypeMapping
            java.lang.String r6 = r0.getValueFromMapping(r6, r7)
            java.lang.String r7 = "Attach Type"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r7, (java.lang.Object) r6)
            r6 = 8
            r4 = r102[r6]
            r5.put(r4, r13)
            java.lang.String r6 = "gmm_state"
            int r6 = r0.getFieldValue(r3, r6)
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.gmmStateMapping
            java.lang.String r6 = r0.getValueFromMapping(r6, r7)
            java.lang.String r7 = "gmm state"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r7, (java.lang.Object) r6)
            r2 = r1
            r90 = r3
            r1 = r5
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x05f2:
            r88 = r4
            r2 = r1
            r90 = r3
            r1 = r5
            r77 = r32
            r13 = r39
            r14 = r41
            r32 = r48
            r48 = r40
            r100 = r37
            r37 = r35
            r35 = r100
            goto L_0x41c9
        L_0x060a:
            r88 = r4
            r4 = 24
            r4 = r102[r4]
            r5.put(r4, r13)
            java.lang.String r7 = "secondary_hs_dsch_configuration_status."
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            java.lang.String r9 = "dc_on"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r3, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            java.lang.String r10 = "dl_freq"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r3, r9)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r7)
            java.lang.String r11 = "psc"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            int r10 = r0.getFieldValue(r3, r10)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            if (r8 <= 0) goto L_0x0660
            r12 = 1
            goto L_0x0662
        L_0x0660:
            r12 = r82
        L_0x0662:
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            java.lang.String r12 = "State"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r12, (java.lang.Object) r11)
            if (r8 <= 0) goto L_0x0699
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            java.lang.String r12 = "S-carrier UARFCN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r10)
            java.lang.String r6 = r11.toString()
            java.lang.String r11 = "S-carrier PSC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r11, (java.lang.Object) r6)
            goto L_0x06a4
        L_0x0699:
            java.lang.String r11 = "S-carrier UARFCN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r11, (java.lang.Object) r6)
            java.lang.String r11 = "S-carrier PSC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r1, (java.lang.String) r11, (java.lang.Object) r6)
        L_0x06a4:
            r2 = r1
            r90 = r3
            r1 = r5
            r6 = r7
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x06bb:
            r88 = r4
            r4 = 28
            r4 = r102[r4]
            r5.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            java.lang.String r7 = "num_cells"
            int r14 = r0.getFieldValue(r3, r7)
            java.lang.String r7 = "gsm_cell_list["
            r8 = 0
        L_0x06d0:
            if (r8 >= r14) goto L_0x078a
            r9 = 6
            if (r8 >= r9) goto L_0x078a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r8)
            r9.append(r10)
            java.lang.String r11 = "arfcn"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r3, r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r7)
            r11.append(r8)
            r11.append(r10)
            java.lang.String r12 = "bsic"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r7)
            r12.append(r8)
            r12.append(r10)
            java.lang.String r13 = "rssi"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r13 = 1
            int r12 = r0.getFieldValue(r3, r12, r13)
            r13 = r11 & 7
            int r15 = r11 >> 3
            r15 = r15 & 7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r17 = r7
            java.lang.String r7 = "GSM"
            r0.addHashMapKeyValues(r4, r1, r7, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r9)
            java.lang.String r2 = r2.toString()
            java.lang.String r7 = "ARFCN"
            r0.addHashMapKeyValues(r4, r1, r7, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r15)
            r2.append(r6)
            r2.append(r13)
            java.lang.String r2 = r2.toString()
            java.lang.String r7 = "BSIC"
            r0.addHashMapKeyValues(r4, r1, r7, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r12)
            java.lang.String r2 = r2.toString()
            java.lang.String r7 = "RSSI"
            r0.addHashMapKeyValues(r4, r1, r7, r2)
            int r8 = r8 + 1
            r2 = r104
            r7 = r17
            goto L_0x06d0
        L_0x078a:
            r17 = r7
            r2 = r1
            r90 = r3
            r1 = r5
            r25 = r14
            r6 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x07a6:
            r88 = r4
            r2 = 26
            r4 = r102[r2]
            r5.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            java.lang.String r2 = "num_cells"
            int r14 = r0.getFieldValue(r3, r2)
            java.lang.String r2 = "lte_cell_list["
            r9 = 0
        L_0x07bb:
            if (r9 >= r14) goto L_0x089f
            r11 = 32
            if (r9 >= r11) goto L_0x089f
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r2)
            r11.append(r9)
            r11.append(r10)
            r11.append(r7)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r2)
            r13.append(r9)
            r13.append(r10)
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r3, r13)
            r17 = r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r9)
            r14.append(r10)
            r14.append(r8)
            java.lang.String r14 = r14.toString()
            r89 = r5
            r5 = 1
            int r14 = r0.getFieldValue(r3, r14, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r2)
            r5.append(r9)
            r5.append(r10)
            r5.append(r12)
            java.lang.String r5 = r5.toString()
            r25 = r2
            r2 = 1
            int r5 = r0.getFieldValue(r3, r5, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r9)
            java.lang.String r2 = r2.toString()
            r90 = r10
            java.lang.String r10 = "LTE"
            r0.addHashMapKeyValues(r4, r1, r10, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            r0.addHashMapKeyValues(r4, r1, r7, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            r2.append(r13)
            java.lang.String r2 = r2.toString()
            r0.addHashMapKeyValues(r4, r1, r15, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            float r10 = (float) r14
            r54 = 1166016512(0x45800000, float:4096.0)
            float r10 = r10 / r54
            r2.append(r10)
            java.lang.String r2 = r2.toString()
            r0.addHashMapKeyValues(r4, r1, r8, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            float r10 = (float) r5
            float r10 = r10 / r54
            r2.append(r10)
            java.lang.String r2 = r2.toString()
            r0.addHashMapKeyValues(r4, r1, r12, r2)
            int r9 = r9 + 1
            r14 = r17
            r2 = r25
            r5 = r89
            r10 = r90
            goto L_0x07bb
        L_0x089f:
            r25 = r2
            r89 = r5
            r17 = r14
            r2 = r1
            r90 = r3
            r9 = r20
            r10 = r21
            r12 = r23
            r6 = r25
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r1 = r89
            r25 = r17
            goto L_0x41eb
        L_0x08c0:
            r88 = r4
            r89 = r5
            r90 = r10
            java.lang.String r2 = "operation"
            int r2 = r0.getFieldValue(r3, r2)
            java.lang.String r4 = "RAT_type"
            int r4 = r0.getFieldValue(r3, r4)
            if (r4 != 0) goto L_0x0a25
            r5 = 27
            r5 = r102[r5]
            r10 = r89
            r10.put(r5, r13)
            r0.resetHashMapKeyValues(r5, r1)
            r7 = 1
            if (r2 == r7) goto L_0x0900
            r7 = 2
            if (r2 != r7) goto L_0x08e7
            goto L_0x0900
        L_0x08e7:
            r2 = r1
            r90 = r3
            r4 = r5
            r1 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x0900:
            java.lang.String r7 = "neigh_cell_count"
            int r14 = r0.getFieldValue(r3, r7)
            java.lang.String r7 = "choice.neigh_cells["
            r8 = 0
        L_0x0909:
            if (r8 >= r14) goto L_0x0a06
            r11 = 16
            if (r8 >= r11) goto L_0x0a06
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r7)
            r11.append(r8)
            r12 = r90
            r11.append(r12)
            java.lang.String r13 = "uarfcn_DL"
            r11.append(r13)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            r13.append(r8)
            r13.append(r12)
            java.lang.String r15 = "psc"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r3, r13)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r8)
            r15.append(r12)
            r17 = r14
            java.lang.String r14 = "rscp"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r3, r14, r15)
            float r14 = (float) r14
            r15 = 1166016512(0x45800000, float:4096.0)
            float r14 = r14 / r15
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r8)
            r15.append(r12)
            r25 = r7
            java.lang.String r7 = "ec_no"
            r15.append(r7)
            java.lang.String r7 = r15.toString()
            r15 = 1
            int r7 = r0.getFieldValue(r3, r7, r15)
            float r7 = (float) r7
            r15 = 1166016512(0x45800000, float:4096.0)
            float r7 = r7 / r15
            r15 = -1024458752(0xffffffffc2f00000, float:-120.0)
            int r15 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r15 <= 0) goto L_0x09fc
            r15 = -1043857408(0xffffffffc1c80000, float:-25.0)
            int r15 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r15 <= 0) goto L_0x09fc
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            r15.append(r8)
            java.lang.String r15 = r15.toString()
            r90 = r12
            java.lang.String r12 = "UMTS"
            r0.addHashMapKeyValues(r5, r1, r12, r15)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r11)
            java.lang.String r12 = r12.toString()
            r0.addHashMapKeyValues(r5, r1, r9, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.lang.String r15 = "PSC"
            r0.addHashMapKeyValues(r5, r1, r15, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            java.lang.String r15 = "RSCP"
            r0.addHashMapKeyValues(r5, r1, r15, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r7)
            java.lang.String r12 = r12.toString()
            java.lang.String r15 = "Ec/No"
            r0.addHashMapKeyValues(r5, r1, r15, r12)
            goto L_0x09fe
        L_0x09fc:
            r90 = r12
        L_0x09fe:
            int r8 = r8 + 1
            r14 = r17
            r7 = r25
            goto L_0x0909
        L_0x0a06:
            r25 = r7
            r17 = r14
            r2 = r1
            r90 = r3
            r4 = r5
            r1 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r6 = r25
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r25 = r17
            goto L_0x41eb
        L_0x0a25:
            r10 = r89
            r5 = r90
            r9 = 1
            if (r4 != r9) goto L_0x0b3a
            r7 = 28
            r7 = r102[r7]
            r10.put(r7, r13)
            r0.resetHashMapKeyValues(r7, r1)
            if (r2 == r9) goto L_0x0a55
            r8 = 2
            if (r2 != r8) goto L_0x0a3c
            goto L_0x0a55
        L_0x0a3c:
            r2 = r1
            r90 = r3
            r4 = r7
            r1 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x0a55:
            java.lang.String r8 = "choice.GSM_neigh_cells["
            java.lang.String r9 = "neigh_cell_count"
            int r14 = r0.getFieldValue(r3, r9)
            r9 = 0
        L_0x0a5e:
            if (r9 >= r14) goto L_0x0b1d
            r11 = 16
            if (r9 >= r11) goto L_0x0b1d
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r8)
            r11.append(r9)
            r11.append(r5)
            java.lang.String r12 = "arfcn"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r3, r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r8)
            r12.append(r9)
            r12.append(r5)
            java.lang.String r13 = "bsic"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r3, r12)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r8)
            r13.append(r9)
            r13.append(r5)
            java.lang.String r15 = "rssi"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            r15 = 1
            int r13 = r0.getFieldValue(r3, r13, r15)
            r15 = r12 & 7
            int r17 = r12 >> 3
            r54 = r8
            r8 = r17 & 7
            r17 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r9)
            java.lang.String r12 = r12.toString()
            r25 = r14
            java.lang.String r14 = "GSM"
            r0.addHashMapKeyValues(r7, r1, r14, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r11)
            java.lang.String r12 = r12.toString()
            java.lang.String r14 = "ARFCN"
            r0.addHashMapKeyValues(r7, r1, r14, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r8)
            r12.append(r6)
            r12.append(r15)
            java.lang.String r12 = r12.toString()
            java.lang.String r14 = "BSIC"
            r0.addHashMapKeyValues(r7, r1, r14, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.lang.String r14 = "RSSI"
            r0.addHashMapKeyValues(r7, r1, r14, r12)
            int r9 = r9 + 1
            r14 = r25
            r8 = r54
            goto L_0x0a5e
        L_0x0b1d:
            r54 = r8
            r25 = r14
            r2 = r1
            r90 = r3
            r4 = r7
            r1 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r54
            r13 = r70
            goto L_0x41eb
        L_0x0b3a:
            r9 = 2
            if (r4 != r9) goto L_0x0c97
            r11 = 26
            r11 = r102[r11]
            r10.put(r11, r13)
            r0.resetHashMapKeyValues(r11, r1)
            if (r2 != r9) goto L_0x0c77
            java.lang.String r9 = "neigh_cell_count"
            int r14 = r0.getFieldValue(r3, r9)
            java.lang.String r9 = "choice.LTE_neigh_cells["
            r13 = 0
        L_0x0b52:
            if (r13 >= r14) goto L_0x0c51
            r54 = r2
            r2 = 16
            if (r13 >= r2) goto L_0x0c46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r9)
            r2.append(r13)
            r2.append(r5)
            r67 = r4
            java.lang.String r4 = "earfcn"
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            int r2 = r0.getFieldValue(r3, r2)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r9)
            r4.append(r13)
            r4.append(r5)
            r17 = r14
            java.lang.String r14 = "pci"
            r4.append(r14)
            java.lang.String r4 = r4.toString()
            int r4 = r0.getFieldValue(r3, r4)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r9)
            r14.append(r13)
            r14.append(r5)
            r89 = r10
            java.lang.String r10 = "rsrp"
            r14.append(r10)
            java.lang.String r10 = r14.toString()
            r14 = 1
            int r10 = r0.getFieldValue(r3, r10, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r9)
            r14.append(r13)
            r14.append(r5)
            r25 = r9
            java.lang.String r9 = "rsrq"
            r14.append(r9)
            java.lang.String r9 = r14.toString()
            r14 = 1
            int r9 = r0.getFieldValue(r3, r9, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r13)
            java.lang.String r14 = r14.toString()
            r90 = r3
            java.lang.String r3 = "LTE"
            r0.addHashMapKeyValues(r11, r1, r3, r14)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            r0.addHashMapKeyValues(r11, r1, r7, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.addHashMapKeyValues(r11, r1, r15, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            float r14 = (float) r10
            r69 = 1166016512(0x45800000, float:4096.0)
            float r14 = r14 / r69
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            r0.addHashMapKeyValues(r11, r1, r8, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            float r14 = (float) r9
            float r14 = r14 / r69
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            r0.addHashMapKeyValues(r11, r1, r12, r3)
            int r13 = r13 + 1
            r14 = r17
            r9 = r25
            r2 = r54
            r4 = r67
            r10 = r89
            r3 = r90
            goto L_0x0b52
        L_0x0c46:
            r90 = r3
            r67 = r4
            r25 = r9
            r89 = r10
            r17 = r14
            goto L_0x0c5d
        L_0x0c51:
            r54 = r2
            r90 = r3
            r67 = r4
            r25 = r9
            r89 = r10
            r17 = r14
        L_0x0c5d:
            r2 = r1
            r4 = r11
            r9 = r20
            r10 = r21
            r12 = r23
            r6 = r25
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r1 = r89
            r25 = r17
            goto L_0x41eb
        L_0x0c77:
            r54 = r2
            r90 = r3
            r67 = r4
            r89 = r10
            r2 = r1
            r4 = r11
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            r1 = r89
            goto L_0x41eb
        L_0x0c97:
            r54 = r2
            r90 = r3
            r67 = r4
            r89 = r10
            r2 = r1
            r77 = r32
            r13 = r39
            r14 = r41
            r32 = r48
            r1 = r89
            r48 = r40
            r100 = r37
            r37 = r35
            r35 = r100
            goto L_0x41c9
        L_0x0cb4:
            r90 = r3
            r88 = r4
            r89 = r5
            r5 = r10
            r2 = 11
            r4 = r102[r2]
            r2 = r89
            r2.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            java.lang.String r3 = "ir_3g_neighbor_meas_status["
            r7 = 0
        L_0x0cca:
            r8 = 6
            if (r7 >= r8) goto L_0x0dcb
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            r8.append(r7)
            r8.append(r5)
            java.lang.String r10 = "is_valid"
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            r10 = r90
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r3)
            r11.append(r7)
            r11.append(r5)
            java.lang.String r12 = "uarfcn"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r3)
            r12.append(r7)
            r12.append(r5)
            java.lang.String r13 = "phy_id"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r3)
            r13.append(r7)
            r13.append(r5)
            java.lang.String r14 = "strength"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r10, r13, r14)
            float r13 = (float) r13
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r7)
            r15.append(r5)
            java.lang.String r14 = "quailty"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r10, r14, r15)
            float r14 = (float) r14
            if (r8 <= 0) goto L_0x0dc1
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            r15.append(r7)
            java.lang.String r15 = r15.toString()
            r17 = r3
            java.lang.String r3 = "UMTS"
            r0.addHashMapKeyValues(r4, r1, r3, r15)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r0.addHashMapKeyValues(r4, r1, r9, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            java.lang.String r15 = "PSC"
            r0.addHashMapKeyValues(r4, r1, r15, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r13)
            java.lang.String r3 = r3.toString()
            java.lang.String r15 = "RSCP"
            r0.addHashMapKeyValues(r4, r1, r15, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            java.lang.String r15 = "Ec/No"
            r0.addHashMapKeyValues(r4, r1, r15, r3)
            goto L_0x0dc3
        L_0x0dc1:
            r17 = r3
        L_0x0dc3:
            int r7 = r7 + 1
            r90 = r10
            r3 = r17
            goto L_0x0cca
        L_0x0dcb:
            r17 = r3
            r10 = r90
            r6 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r100 = r2
            r2 = r1
            r1 = r100
            goto L_0x41eb
        L_0x0de8:
            r88 = r4
            r2 = r5
            r5 = r10
            r10 = r3
            r3 = 10
            r4 = r102[r3]
            r2.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            java.lang.String r3 = "ir_4g_neighbor_meas_status["
            r9 = 0
        L_0x0dfa:
            r11 = 6
            if (r9 >= r11) goto L_0x0efc
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r3)
            r11.append(r9)
            r11.append(r5)
            java.lang.String r13 = "is_valid"
            r11.append(r13)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r3)
            r13.append(r9)
            r13.append(r5)
            java.lang.String r14 = "earfcn"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r89 = r2
            java.lang.String r2 = "pci"
            r14.append(r2)
            java.lang.String r2 = r14.toString()
            int r2 = r0.getFieldValue(r10, r2)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r79 = r12
            java.lang.String r12 = "rsrp"
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            r14 = 1
            int r12 = r0.getFieldValue(r10, r12, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r17 = r3
            java.lang.String r3 = "rsrq"
            r14.append(r3)
            java.lang.String r3 = r14.toString()
            r14 = 1
            int r3 = r0.getFieldValue(r10, r3, r14)
            if (r11 <= 0) goto L_0x0eef
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r9)
            java.lang.String r14 = r14.toString()
            r54 = r11
            java.lang.String r11 = "LTE"
            r0.addHashMapKeyValues(r4, r1, r11, r14)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r13)
            java.lang.String r11 = r11.toString()
            r0.addHashMapKeyValues(r4, r1, r7, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r2)
            java.lang.String r11 = r11.toString()
            r0.addHashMapKeyValues(r4, r1, r15, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r0.addHashMapKeyValues(r4, r1, r8, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r3)
            java.lang.String r11 = r11.toString()
            r14 = r79
            r0.addHashMapKeyValues(r4, r1, r14, r11)
            goto L_0x0ef3
        L_0x0eef:
            r54 = r11
            r14 = r79
        L_0x0ef3:
            int r9 = r9 + 1
            r12 = r14
            r3 = r17
            r2 = r89
            goto L_0x0dfa
        L_0x0efc:
            r89 = r2
            r17 = r3
            r2 = r1
            r90 = r10
            r6 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r1 = r89
            goto L_0x41eb
        L_0x0f19:
            r88 = r4
            r89 = r5
            r5 = r10
            r10 = r3
            r2 = 20
            r4 = r102[r2]
            r2 = r89
            r2.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            java.lang.String r3 = "gsm_cell_list"
            java.lang.String r7 = "num_cells"
            int r14 = r0.getFieldValue(r10, r7)
            r7 = 0
        L_0x0f34:
            if (r7 >= r14) goto L_0x0ff7
            r8 = 6
            if (r7 >= r8) goto L_0x0ff7
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            r8.append(r11)
            r8.append(r7)
            r8.append(r5)
            java.lang.String r9 = "arfcn"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r11)
            r9.append(r7)
            r9.append(r5)
            java.lang.String r12 = "bsic"
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r3)
            r12.append(r11)
            r12.append(r7)
            r12.append(r5)
            java.lang.String r13 = "rssi"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r13 = 1
            int r12 = r0.getFieldValue(r10, r12, r13)
            r13 = r9 & 7
            int r15 = r9 >> 3
            r15 = r15 & 7
            r17 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r25 = r9
            java.lang.String r9 = "GSM"
            r0.addHashMapKeyValues(r4, r1, r9, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r8)
            java.lang.String r3 = r3.toString()
            java.lang.String r9 = "ARFCN"
            r0.addHashMapKeyValues(r4, r1, r9, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r15)
            r3.append(r6)
            r3.append(r13)
            java.lang.String r3 = r3.toString()
            java.lang.String r9 = "BSIC"
            r0.addHashMapKeyValues(r4, r1, r9, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            java.lang.String r9 = "RSSI"
            r0.addHashMapKeyValues(r4, r1, r9, r3)
            int r7 = r7 + 1
            r3 = r17
            goto L_0x0f34
        L_0x0ff7:
            r17 = r3
            r90 = r10
            r25 = r14
            r6 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r100 = r2
            r2 = r1
            r1 = r100
            goto L_0x41eb
        L_0x1016:
            r88 = r4
            r2 = r5
            r5 = r10
            r10 = r3
            r3 = 19
            r4 = r102[r3]
            r2.put(r4, r13)
            r0.resetHashMapKeyValues(r4, r1)
            r3 = 0
        L_0x1026:
            r7 = 64
            if (r3 >= r7) goto L_0x10f2
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "umts_cell_list["
            r7.append(r8)
            r7.append(r3)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r7)
            java.lang.String r12 = "CELLPARAID"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r7)
            java.lang.String r13 = "RSCP"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r13 = 1
            int r12 = r0.getFieldValue(r10, r12, r13)
            float r12 = (float) r12
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            java.lang.String r14 = "isServingCell"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            if (r13 == 0) goto L_0x10ec
            r14 = -1024458752(0xffffffffc2f00000, float:-120.0)
            int r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r14 <= 0) goto L_0x10ec
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r3)
            java.lang.String r14 = r14.toString()
            java.lang.String r15 = "UMTS"
            r0.addHashMapKeyValues(r4, r1, r15, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r8)
            java.lang.String r14 = r14.toString()
            r0.addHashMapKeyValues(r4, r1, r9, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r11)
            java.lang.String r14 = r14.toString()
            java.lang.String r15 = "PSC"
            r0.addHashMapKeyValues(r4, r1, r15, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r15 = 1166016512(0x45800000, float:4096.0)
            float r15 = r12 / r15
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            java.lang.String r15 = "RSCP"
            r0.addHashMapKeyValues(r4, r1, r15, r14)
        L_0x10ec:
            int r3 = r3 + 1
            r85 = r7
            goto L_0x1026
        L_0x10f2:
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            r100 = r2
            r2 = r1
            r1 = r100
            goto L_0x41eb
        L_0x110d:
            r88 = r4
            r2 = r5
            r5 = r10
            r14 = r12
            r10 = r3
            r3 = 18
            r3 = r102[r3]
            java.lang.String r4 = "choice.LTE_neigh_cells"
            java.lang.String r9 = "neigh_cell_count"
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.String r12 = "RAT_type"
            int r12 = r0.getFieldValue(r10, r12)
            r79 = r14
            r14 = 2
            if (r12 != r14) goto L_0x1284
            r2.put(r3, r13)
            r0.resetHashMapKeyValues(r3, r1)
            r14 = 16
            if (r9 >= r14) goto L_0x1135
            r14 = r9
        L_0x1135:
            java.lang.String[] r14 = new java.lang.String[r14]
            r17 = r12
            r12 = 16
            if (r9 >= r12) goto L_0x113e
            r12 = r9
        L_0x113e:
            java.lang.String[] r12 = new java.lang.String[r12]
            r89 = r2
            r2 = 16
            if (r9 >= r2) goto L_0x1147
            r2 = r9
        L_0x1147:
            java.lang.String[] r2 = new java.lang.String[r2]
            r90 = r13
            r13 = 16
            if (r9 >= r13) goto L_0x1150
            r13 = r9
        L_0x1150:
            java.lang.String[] r13 = new java.lang.String[r13]
            r91 = r8
            r8 = 16
            if (r9 >= r8) goto L_0x115a
            r8 = r9
            goto L_0x115c
        L_0x115a:
            r8 = 16
        L_0x115c:
            java.lang.String[] r8 = new java.lang.String[r8]
            r25 = 0
            r92 = r15
            r15 = r25
        L_0x1164:
            if (r15 >= r9) goto L_0x125e
            r25 = r9
            r9 = 16
            if (r15 >= r9) goto L_0x1257
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r11)
            r9.append(r15)
            r9.append(r5)
            r93 = r7
            java.lang.String r7 = "earfcn"
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r11)
            r9.append(r15)
            r9.append(r5)
            java.lang.String r1 = "pci"
            r9.append(r1)
            java.lang.String r1 = r9.toString()
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r11)
            r9.append(r15)
            r9.append(r5)
            r54 = r3
            java.lang.String r3 = "rsrp"
            r9.append(r3)
            java.lang.String r3 = r9.toString()
            r9 = 1
            int r3 = r0.getFieldValue(r10, r3, r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r11)
            r9.append(r15)
            r9.append(r5)
            r67 = r4
            java.lang.String r4 = "rsrq"
            r9.append(r4)
            java.lang.String r4 = r9.toString()
            r9 = 1
            int r4 = r0.getFieldValue(r10, r4, r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            r9.append(r15)
            java.lang.String r9 = r9.toString()
            r14[r15] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
            r12[r15] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            r2[r15] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            r69 = r1
            float r1 = (float) r3
            float r1 = r1 / r78
            r9.append(r1)
            java.lang.String r1 = r9.toString()
            r13[r15] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            float r9 = (float) r4
            float r9 = r9 / r78
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            r8[r15] = r1
            int r15 = r15 + 1
            r1 = r103
            r9 = r25
            r3 = r54
            r4 = r67
            r7 = r93
            goto L_0x1164
        L_0x1257:
            r54 = r3
            r67 = r4
            r93 = r7
            goto L_0x1266
        L_0x125e:
            r54 = r3
            r67 = r4
            r93 = r7
            r25 = r9
        L_0x1266:
            java.lang.String r1 = "LTE"
            r3 = r103
            r4 = r54
            r0.addHashMapKeyValues(r4, r3, r1, r14)
            r1 = r93
            r0.addHashMapKeyValues(r4, r3, r1, r12)
            r7 = r92
            r0.addHashMapKeyValues(r4, r3, r7, r2)
            r9 = r91
            r0.addHashMapKeyValues(r4, r3, r9, r13)
            r15 = r79
            r0.addHashMapKeyValues(r4, r3, r15, r8)
            goto L_0x1290
        L_0x1284:
            r89 = r2
            r67 = r4
            r25 = r9
            r17 = r12
            r90 = r13
            r4 = r3
            r3 = r1
        L_0x1290:
            r1 = 20
            r4 = r102[r1]
            r2 = r89
            r8 = r90
            r2.put(r4, r8)
            java.lang.String r1 = "choice.GSM_neigh_cells"
            java.lang.String r7 = "neigh_cell_count"
            int r14 = r0.getFieldValue(r10, r7)
            r0.resetHashMapKeyValues(r4, r3)
            r7 = 0
        L_0x12a7:
            if (r7 >= r14) goto L_0x136b
            r8 = 16
            if (r7 >= r8) goto L_0x136b
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r11)
            r8.append(r7)
            r8.append(r5)
            java.lang.String r9 = "arfcn"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r1)
            r9.append(r11)
            r9.append(r7)
            r9.append(r5)
            java.lang.String r12 = "bsic"
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r1)
            r12.append(r11)
            r12.append(r7)
            r12.append(r5)
            java.lang.String r13 = "rssi"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r13 = 1
            int r12 = r0.getFieldValue(r10, r12, r13)
            r13 = r9 & 7
            int r15 = r9 >> 3
            r15 = r15 & 7
            r25 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            r54 = r9
            java.lang.String r9 = "GSM"
            r0.addHashMapKeyValues(r4, r3, r9, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            java.lang.String r9 = "ARFCN"
            r0.addHashMapKeyValues(r4, r3, r9, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r15)
            r1.append(r6)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            java.lang.String r9 = "BSIC"
            r0.addHashMapKeyValues(r4, r3, r9, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.String r9 = "RSSI"
            r0.addHashMapKeyValues(r4, r3, r9, r1)
            int r7 = r7 + 1
            r1 = r25
            goto L_0x12a7
        L_0x136b:
            r25 = r1
            r1 = r2
            r2 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r6 = r25
            r7 = r27
            r11 = r29
            r15 = r40
            r13 = r70
            r25 = r14
            r14 = r44
            goto L_0x41eb
        L_0x1387:
            r88 = r4
            r2 = r5
            r9 = r8
            r5 = r10
            r8 = r13
            r10 = r3
            r3 = r1
            r1 = r7
            r7 = r15
            r15 = r12
            r4 = 18
            r4 = r102[r4]
            r2.put(r4, r8)
            r0.resetHashMapKeyValues(r4, r3)
            java.lang.String r8 = "lte_cell_list"
            java.lang.String r12 = "num_cells"
            int r14 = r0.getFieldValue(r10, r12)
            r12 = 32
            if (r14 >= r12) goto L_0x13aa
            r13 = r14
            goto L_0x13ab
        L_0x13aa:
            r13 = r12
        L_0x13ab:
            java.lang.String[] r13 = new java.lang.String[r13]
            if (r14 >= r12) goto L_0x13b0
            r12 = r14
        L_0x13b0:
            java.lang.String[] r12 = new java.lang.String[r12]
            r89 = r2
            r2 = 32
            if (r14 >= r2) goto L_0x13b9
            r2 = r14
        L_0x13b9:
            java.lang.String[] r2 = new java.lang.String[r2]
            r3 = 32
            if (r14 >= r3) goto L_0x13c0
            r3 = r14
        L_0x13c0:
            java.lang.String[] r3 = new java.lang.String[r3]
            r17 = r4
            r4 = 32
            if (r14 >= r4) goto L_0x13ca
            r4 = r14
            goto L_0x13cc
        L_0x13ca:
            r4 = 32
        L_0x13cc:
            java.lang.String[] r4 = new java.lang.String[r4]
            r25 = 0
            r54 = r4
            r4 = r25
        L_0x13d4:
            if (r4 >= r14) goto L_0x14c2
            r25 = r14
            r14 = 32
            if (r4 >= r14) goto L_0x14b9
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r8)
            r14.append(r11)
            r14.append(r4)
            r14.append(r5)
            r14.append(r1)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r10, r14)
            r93 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r8)
            r1.append(r11)
            r1.append(r4)
            r1.append(r5)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r10, r1)
            r92 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r8)
            r7.append(r11)
            r7.append(r4)
            r7.append(r5)
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            r91 = r9
            r9 = 1
            int r7 = r0.getFieldValue(r10, r7, r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r8)
            r9.append(r11)
            r9.append(r4)
            r9.append(r5)
            r9.append(r15)
            java.lang.String r9 = r9.toString()
            r67 = r8
            r8 = 1
            int r9 = r0.getFieldValue(r10, r9, r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r4)
            java.lang.String r8 = r8.toString()
            r13[r4] = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r14)
            java.lang.String r8 = r8.toString()
            r12[r4] = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            r2[r4] = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            r3[r4] = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r54[r4] = r8
            int r4 = r4 + 1
            r14 = r25
            r8 = r67
            r9 = r91
            r7 = r92
            r1 = r93
            goto L_0x13d4
        L_0x14b9:
            r93 = r1
            r92 = r7
            r67 = r8
            r91 = r9
            goto L_0x14cc
        L_0x14c2:
            r93 = r1
            r92 = r7
            r67 = r8
            r91 = r9
            r25 = r14
        L_0x14cc:
            java.lang.String r1 = "LTE"
            r4 = r103
            r5 = r17
            r0.addHashMapKeyValues(r5, r4, r1, r13)
            r1 = r93
            r0.addHashMapKeyValues(r5, r4, r1, r12)
            r7 = r92
            r0.addHashMapKeyValues(r5, r4, r7, r2)
            r9 = r91
            r0.addHashMapKeyValues(r5, r4, r9, r3)
            r1 = r54
            r0.addHashMapKeyValues(r5, r4, r15, r1)
            r2 = r4
            r4 = r5
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r67
            r13 = r70
            r1 = r89
            goto L_0x41eb
        L_0x1503:
            r88 = r4
            r89 = r5
            r5 = r10
            r8 = r13
            r4 = r1
            r10 = r3
            r1 = 5
            r1 = r102[r1]
            r2 = r89
            r2.put(r1, r8)
            r0.resetHashMapKeyValues(r1, r4)
            java.lang.String r3 = "gcell"
            java.lang.String r7 = "total_gcell_num"
            int r14 = r0.getFieldValue(r10, r7)
            r7 = 6
            if (r14 >= r7) goto L_0x1523
            r8 = r14
            goto L_0x1524
        L_0x1523:
            r8 = r7
        L_0x1524:
            java.lang.String[] r8 = new java.lang.String[r8]
            if (r14 >= r7) goto L_0x152a
            r9 = r14
            goto L_0x152b
        L_0x152a:
            r9 = r7
        L_0x152b:
            java.lang.String[] r9 = new java.lang.String[r9]
            if (r14 >= r7) goto L_0x1531
            r12 = r14
            goto L_0x1532
        L_0x1531:
            r12 = r7
        L_0x1532:
            java.lang.String[] r12 = new java.lang.String[r12]
            if (r14 >= r7) goto L_0x1538
            r7 = r14
            goto L_0x1539
        L_0x1538:
            r7 = 6
        L_0x1539:
            java.lang.String[] r7 = new java.lang.String[r7]
            r13 = 0
        L_0x153c:
            if (r13 >= r14) goto L_0x1655
            r15 = 6
            if (r13 >= r15) goto L_0x1655
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r13)
            r15.append(r5)
            r17 = r14
            java.lang.String r14 = "valid"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            int r14 = r0.getFieldValue(r10, r14)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r13)
            r15.append(r5)
            r89 = r2
            java.lang.String r2 = "band_ind"
            r15.append(r2)
            java.lang.String r2 = r15.toString()
            int r2 = r0.getFieldValue(r10, r2)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r13)
            r15.append(r5)
            r25 = r2
            java.lang.String r2 = "arfcn"
            r15.append(r2)
            java.lang.String r2 = r15.toString()
            int r2 = r0.getFieldValue(r10, r2)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r13)
            r15.append(r5)
            r54 = r1
            java.lang.String r1 = "bsic"
            r15.append(r1)
            java.lang.String r1 = r15.toString()
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r13)
            r15.append(r5)
            r67 = r3
            java.lang.String r3 = "rssi"
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            r15 = 1
            int r3 = r0.getFieldValue(r10, r3, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            r15.append(r13)
            java.lang.String r15 = r15.toString()
            r8[r13] = r15
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            if (r14 <= 0) goto L_0x1608
            java.lang.Integer r69 = java.lang.Integer.valueOf(r2)
            r100 = r69
            r69 = r2
            r2 = r100
            goto L_0x160b
        L_0x1608:
            r69 = r2
            r2 = r6
        L_0x160b:
            r15.append(r2)
            java.lang.String r2 = r15.toString()
            r9[r13] = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            if (r14 <= 0) goto L_0x1623
            java.lang.Integer r15 = java.lang.Integer.valueOf(r1)
            goto L_0x1624
        L_0x1623:
            r15 = r6
        L_0x1624:
            r2.append(r15)
            java.lang.String r2 = r2.toString()
            r12[r13] = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r6)
            if (r14 <= 0) goto L_0x163f
            float r15 = (float) r3
            float r15 = r15 / r78
            java.lang.Float r15 = java.lang.Float.valueOf(r15)
            goto L_0x1640
        L_0x163f:
            r15 = r6
        L_0x1640:
            r2.append(r15)
            java.lang.String r2 = r2.toString()
            r7[r13] = r2
            int r13 = r13 + 1
            r14 = r17
            r1 = r54
            r3 = r67
            r2 = r89
            goto L_0x153c
        L_0x1655:
            r54 = r1
            r89 = r2
            r67 = r3
            r17 = r14
            java.lang.String r1 = "GSM"
            r2 = r54
            r0.addHashMapKeyValues(r2, r4, r1, r8)
            java.lang.String r1 = "ARFCN"
            r0.addHashMapKeyValues(r2, r4, r1, r9)
            java.lang.String r1 = "BSIC"
            r0.addHashMapKeyValues(r2, r4, r1, r12)
            java.lang.String r1 = "RSSI"
            r0.addHashMapKeyValues(r2, r4, r1, r7)
            r90 = r10
            r25 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r67
            r13 = r70
            r1 = r89
            r100 = r4
            r4 = r2
            r2 = r100
            goto L_0x41eb
        L_0x1692:
            r88 = r4
            r89 = r5
            r5 = r10
            r8 = r13
            r4 = r1
            r10 = r3
            r1 = 4
            r1 = r102[r1]
            r2 = r89
            r2.put(r1, r8)
            r0.resetHashMapKeyValues(r1, r4)
            java.lang.String r3 = "freq_num"
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.String r7 = "inter_freq["
            r8 = 0
            r14 = r25
        L_0x16b0:
            if (r8 >= r3) goto L_0x18ac
            r12 = 16
            if (r8 >= r12) goto L_0x18ac
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r7)
            r12.append(r8)
            r12.append(r5)
            java.lang.String r13 = "valid"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            r13.append(r8)
            r13.append(r5)
            java.lang.String r15 = "uarfcn"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r8)
            r15.append(r5)
            r17 = r3
            java.lang.String r3 = "ucell_num"
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            int r14 = r0.getFieldValue(r10, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r7)
            r3.append(r8)
            r3.append(r5)
            java.lang.String r15 = "ucell"
            r3.append(r15)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r15 = 6
            if (r14 >= r15) goto L_0x1727
            r15 = r14
        L_0x1727:
            java.lang.String[] r15 = new java.lang.String[r15]
            r54 = r7
            r7 = 6
            if (r14 >= r7) goto L_0x172f
            r7 = r14
        L_0x172f:
            java.lang.String[] r7 = new java.lang.String[r7]
            r89 = r11
            r11 = 6
            if (r14 >= r11) goto L_0x1737
            r11 = r14
        L_0x1737:
            java.lang.String[] r11 = new java.lang.String[r11]
            r90 = r2
            r2 = 6
            if (r14 >= r2) goto L_0x173f
            r2 = r14
        L_0x173f:
            java.lang.String[] r2 = new java.lang.String[r2]
            r25 = r8
            r8 = 6
            if (r14 >= r8) goto L_0x1748
            r8 = r14
            goto L_0x1749
        L_0x1748:
            r8 = 6
        L_0x1749:
            java.lang.String[] r8 = new java.lang.String[r8]
            r67 = 0
            r80 = r9
            r9 = r67
        L_0x1751:
            if (r9 >= r14) goto L_0x1872
            r67 = r14
            r14 = 6
            if (r9 >= r14) goto L_0x1869
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r69 = r1
            java.lang.String r1 = "valid"
            r14.append(r1)
            java.lang.String r1 = r14.toString()
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            java.lang.String r4 = "psc"
            r14.append(r4)
            java.lang.String r4 = r14.toString()
            int r4 = r0.getFieldValue(r10, r4)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r76 = r2
            java.lang.String r2 = "rscp"
            r14.append(r2)
            java.lang.String r2 = r14.toString()
            int r2 = r0.getFieldValue(r10, r2)
            float r2 = (float) r2
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r9)
            r14.append(r5)
            r79 = r3
            java.lang.String r3 = "ec_n0"
            r14.append(r3)
            java.lang.String r3 = r14.toString()
            int r3 = r0.getFieldValue(r10, r3)
            float r3 = (float) r3
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r9)
            r14.append(r6)
            java.lang.String r14 = r14.toString()
            r15[r9] = r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            if (r12 <= 0) goto L_0x17f2
            java.lang.Integer r82 = java.lang.Integer.valueOf(r13)
            r100 = r82
            r82 = r12
            r12 = r100
            goto L_0x17f5
        L_0x17f2:
            r82 = r12
            r12 = r6
        L_0x17f5:
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            r8[r9] = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            if (r1 <= 0) goto L_0x180d
            java.lang.Integer r14 = java.lang.Integer.valueOf(r4)
            goto L_0x180e
        L_0x180d:
            r14 = r6
        L_0x180e:
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            r7[r9] = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            if (r1 <= 0) goto L_0x182e
            r14 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r14 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r14 == 0) goto L_0x182e
            float r14 = r2 / r78
            java.lang.Float r14 = java.lang.Float.valueOf(r14)
            goto L_0x182f
        L_0x182e:
            r14 = r6
        L_0x182f:
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            r11[r9] = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            if (r1 <= 0) goto L_0x184f
            r14 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r14 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1))
            if (r14 == 0) goto L_0x184f
            float r14 = r3 / r78
            java.lang.Float r14 = java.lang.Float.valueOf(r14)
            goto L_0x1850
        L_0x184f:
            r14 = r6
        L_0x1850:
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            r76[r9] = r12
            int r9 = r9 + 1
            r4 = r103
            r14 = r67
            r1 = r69
            r2 = r76
            r3 = r79
            r12 = r82
            goto L_0x1751
        L_0x1869:
            r69 = r1
            r76 = r2
            r79 = r3
            r82 = r12
            goto L_0x187c
        L_0x1872:
            r69 = r1
            r76 = r2
            r79 = r3
            r82 = r12
            r67 = r14
        L_0x187c:
            java.lang.String r1 = "UMTS"
            r2 = r103
            r3 = r69
            r0.addHashMapKeyValues(r3, r2, r1, r15)
            r1 = r80
            r0.addHashMapKeyValues(r3, r2, r1, r8)
            java.lang.String r4 = "PSC"
            r0.addHashMapKeyValues(r3, r2, r4, r7)
            java.lang.String r4 = "RSCP"
            r0.addHashMapKeyValues(r3, r2, r4, r11)
            java.lang.String r4 = "Ec/No"
            r9 = r76
            r0.addHashMapKeyValues(r3, r2, r4, r9)
            int r8 = r25 + 1
            r9 = r1
            r4 = r2
            r1 = r3
            r3 = r17
            r7 = r54
            r14 = r67
            r11 = r89
            r2 = r90
            goto L_0x16b0
        L_0x18ac:
            r90 = r2
            r17 = r3
            r2 = r4
            r54 = r7
            r25 = r8
            r3 = r1
            r4 = r3
            r25 = r14
            r9 = r20
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r54
            r13 = r70
            r1 = r90
            r90 = r10
            r10 = r21
            goto L_0x41eb
        L_0x18d1:
            r2 = r1
            r10 = r3
            r88 = r4
            r90 = r5
            r8 = r13
            r1 = 14
            r4 = r102[r1]
            r3 = r90
            r3.put(r4, r8)
            java.lang.String r1 = "EcIo"
            r5 = 1
            int r1 = r0.getFieldValue(r10, r1, r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            java.lang.String r6 = "ecio"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r5)
            r1 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x1908:
            r2 = r1
            r10 = r3
            r88 = r4
            r3 = r5
            r8 = r13
            r1 = 13
            r4 = r102[r1]
            r3.put(r4, r8)
            java.lang.String r1 = "Pilot_Energy"
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            java.lang.String r6 = "ecio"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r5)
            r1 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x193b:
            r2 = r1
            r10 = r3
            r88 = r4
            r3 = r5
            r8 = r13
            r1 = 8
            r4 = r102[r1]
            r3.put(r4, r8)
            java.lang.String r1 = "rr_em_lai_info."
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r1)
            java.lang.String r7 = "lac"
            r5.append(r7)
            java.lang.String r7 = "[0]"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r1)
            java.lang.String r8 = "lac"
            r7.append(r8)
            java.lang.String r8 = "[1]"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            int r7 = r0.getFieldValue(r10, r7)
            r8 = 1
            java.lang.Object[] r9 = new java.lang.Object[r8]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)
            r9[r82] = r11
            java.lang.String r11 = "%d"
            java.lang.String r9 = java.lang.String.format(r11, r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r9)
            java.lang.Object[] r8 = new java.lang.Object[r8]
            java.lang.Integer r12 = java.lang.Integer.valueOf(r7)
            r8[r82] = r12
            java.lang.String r12 = "%d"
            java.lang.String r8 = java.lang.String.format(r12, r8)
            r11.append(r8)
            java.lang.String r8 = r11.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r1)
            java.lang.String r11 = "cell_id"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r1)
            r12 = r87
            r11.append(r12)
            java.lang.String r13 = "[0]"
            r11.append(r13)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r1)
            r13.append(r12)
            java.lang.String r14 = "[1]"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r14.append(r12)
            java.lang.String r12 = "[2]"
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r15 = r86
            r14.append(r15)
            r17 = r5
            java.lang.String r5 = "[0]"
            r14.append(r5)
            java.lang.String r5 = r14.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r14.append(r15)
            r54 = r7
            java.lang.String r7 = "[1]"
            r14.append(r7)
            java.lang.String r7 = r14.toString()
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r14.append(r15)
            java.lang.String r15 = "[2]"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r10, r14)
            r15 = 15
            if (r11 != r15) goto L_0x1a6f
            if (r13 != r15) goto L_0x1a6f
            if (r12 != r15) goto L_0x1a6f
            if (r5 != r15) goto L_0x1a6f
            if (r7 != r15) goto L_0x1a6f
            if (r14 != r15) goto L_0x1a6f
            java.lang.String r6 = "PLMN"
            java.lang.String r15 = "-"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r15)
            r67 = r1
            goto L_0x1a9d
        L_0x1a6f:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            r15.append(r11)
            r15.append(r13)
            r15.append(r12)
            r15.append(r5)
            r15.append(r7)
            r67 = r1
            r1 = 15
            if (r14 != r1) goto L_0x1a8d
            goto L_0x1a91
        L_0x1a8d:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r14)
        L_0x1a91:
            r15.append(r6)
            java.lang.String r1 = r15.toString()
            java.lang.String r6 = "PLMN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r1)
        L_0x1a9d:
            java.lang.String r1 = "LAC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r1, (java.lang.Object) r8)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
            java.lang.String r6 = "Cell ID"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r1)
            r1 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r67
            r13 = r70
            goto L_0x41eb
        L_0x1ac2:
            r2 = r1
            r10 = r3
            r88 = r4
            r3 = r5
            r89 = r11
            r8 = r13
            r1 = 16
            r4 = r102[r1]
            r3.put(r4, r8)
            java.lang.String r6 = "uas_3g_general_status.umts_rrc_state"
            int r1 = r0.getFieldValue(r10, r6)
            r5 = 6
            if (r1 == r5) goto L_0x1cde
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.mStateMapping
            java.lang.String r5 = r0.getValueFromMapping(r1, r5)
            java.lang.String r7 = "RRC state"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r7, (java.lang.Object) r5)
            r3.put(r4, r8)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r11 = r89
            r7.append(r11)
            r7.append(r4)
            java.lang.String r8 = "]RRC State : "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r8, r7)
            goto L_0x1cde
        L_0x1b08:
            r2 = r1
            r10 = r3
            r88 = r4
            r3 = r5
            r8 = r13
            r1 = 23
            r1 = r102[r1]
            r3.put(r1, r8)
            java.lang.String r4 = "EmUl1Tas."
            boolean r5 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r5 == 0) goto L_0x1b33
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r4)
            java.lang.String r7 = "tas_enable"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            goto L_0x1b34
        L_0x1b33:
            r5 = 2
        L_0x1b34:
            r13 = r5
            boolean r5 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r5 == 0) goto L_0x1b51
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r4)
            java.lang.String r7 = "band"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            goto L_0x1b52
        L_0x1b51:
            r5 = -1
        L_0x1b52:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r4)
            java.lang.String r9 = "main_ant_idx"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            java.lang.String r11 = "tx_pwr"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            r11 = 1
            int r12 = r0.getFieldValue(r10, r9, r11)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            java.lang.String r14 = "rscp_0"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9, r11)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r4)
            java.lang.String r15 = "rscp_1"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r10, r14, r11)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r4)
            java.lang.String r11 = "rscp_2"
            r15.append(r11)
            java.lang.String r11 = r15.toString()
            r15 = 1
            int r11 = r0.getFieldValue(r10, r11, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r4)
            r17 = r4
            java.lang.String r4 = "tas_version"
            r15.append(r4)
            java.lang.String r4 = r15.toString()
            r15 = 1
            int r4 = r0.getFieldValue(r10, r4, r15)
            r15 = 2
            if (r4 != r15) goto L_0x1bdb
            r4 = 2
            goto L_0x1bdc
        L_0x1bdb:
            r4 = 1
        L_0x1bdc:
            r0.TasVersion = r4
            java.lang.String r4 = r0.tasEableMapping(r13)
            java.lang.String r15 = "TAS Enable"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r4)
            java.lang.String r4 = r0.antidxMapping(r7)
            java.lang.String r15 = "TX Antenna"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r4)
            r4 = -128(0xffffffffffffff80, float:NaN)
            if (r12 != r4) goto L_0x1bf6
            r4 = r6
            goto L_0x1bfa
        L_0x1bf6:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r12)
        L_0x1bfa:
            java.lang.String r15 = "TX Power"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r4)
            r4 = -480(0xfffffffffffffe20, float:NaN)
            if (r9 != r4) goto L_0x1c05
            r4 = r6
            goto L_0x1c0b
        L_0x1c05:
            int r4 = r9 / 4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
        L_0x1c0b:
            java.lang.String r15 = "RSRP_LANT"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r4)
            r4 = -480(0xfffffffffffffe20, float:NaN)
            if (r14 != r4) goto L_0x1c16
            r4 = r6
            goto L_0x1c1c
        L_0x1c16:
            int r4 = r14 / 4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
        L_0x1c1c:
            java.lang.String r15 = "RSRP_UANT"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r4)
            int r4 = r0.TasVersion
            r15 = 2
            if (r4 != r15) goto L_0x1c37
            r4 = -480(0xfffffffffffffe20, float:NaN)
            if (r11 != r4) goto L_0x1c2c
            goto L_0x1c32
        L_0x1c2c:
            int r4 = r11 / 4
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
        L_0x1c32:
            java.lang.String r4 = "RSRP_UANT(')"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r4, (java.lang.Object) r6)
        L_0x1c37:
            r4 = 21
            r4 = r102[r4]
            r3.put(r4, r8)
            r1 = -1
            if (r5 != r1) goto L_0x1c45
            java.lang.String r1 = "="
            goto L_0x1c56
        L_0x1c45:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "Band"
            r1.append(r6)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
        L_0x1c56:
            java.lang.String r6 = "band"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r1)
            r1 = r3
            r19 = r7
            r90 = r10
            r24 = r13
            r6 = r17
            r9 = r20
            r10 = r21
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x1c74:
            r2 = r1
            r10 = r3
            r88 = r4
            r3 = r5
            r8 = r13
            r1 = 21
            r4 = r102[r1]
            java.lang.String r6 = "uas_3g_general_status."
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r5 = "umts_rrc_state"
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r11)
            r5.append(r4)
            java.lang.String r7 = "]rrState: "
            r5.append(r7)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            java.lang.String r7 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r7, r5)
            r5 = 6
            if (r1 == r5) goto L_0x1cde
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.mStateMapping
            java.lang.String r5 = r0.getValueFromMapping(r1, r5)
            java.lang.String r7 = "RRC state"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r7, (java.lang.Object) r5)
            r3.put(r4, r8)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r11)
            r7.append(r4)
            java.lang.String r8 = "]RRC State : "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r8, r7)
        L_0x1cde:
            r1 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x1cf3:
            r2 = r1
            r88 = r4
            r1 = r9
            r8 = r13
            r100 = r10
            r10 = r3
            r3 = r5
            r5 = r100
            r4 = 22
            r4 = r102[r4]
            r3.put(r4, r8)
            java.lang.String r7 = "umts_cell_list["
            java.lang.String r9 = "num_cells"
            int r9 = r0.getFieldValue(r10, r9)
            r0.resetHashMapKeyValues(r4, r2)
            r11 = 32
            if (r9 >= r11) goto L_0x1d16
            r11 = r9
            goto L_0x1d18
        L_0x1d16:
            r11 = 32
        L_0x1d18:
            r12 = 2
            int[] r13 = new int[r12]
            r12 = 1
            r13[r12] = r11
            r11 = 4
            r13[r82] = r11
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            java.lang.Object r11 = java.lang.reflect.Array.newInstance(r11, r13)
            java.lang.String[][] r11 = (java.lang.String[][]) r11
            r12 = 0
        L_0x1d2a:
            if (r12 >= r9) goto L_0x1df9
            r13 = 32
            if (r12 >= r13) goto L_0x1df9
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            r13.append(r12)
            r13.append(r5)
            r13.append(r1)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r12)
            r14.append(r5)
            java.lang.String r15 = "PSC"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            int r14 = r0.getFieldValue(r10, r14)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r12)
            r15.append(r5)
            r17 = r9
            java.lang.String r9 = "RSCP"
            r15.append(r9)
            java.lang.String r9 = r15.toString()
            r15 = 1
            int r9 = r0.getFieldValue(r10, r9, r15)
            float r9 = (float) r9
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r12)
            r15.append(r5)
            r67 = r7
            java.lang.String r7 = "ECN0"
            r15.append(r7)
            java.lang.String r7 = r15.toString()
            r15 = 1
            int r7 = r0.getFieldValue(r10, r7, r15)
            float r7 = (float) r7
            r25 = r11[r82]
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r13)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            r25[r12] = r15
            r15 = 1
            r25 = r11[r15]
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r14)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            r25[r12] = r15
            r15 = 2
            r25 = r11[r15]
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r9)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            r25[r12] = r15
            r15 = 3
            r25 = r11[r15]
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            r25[r12] = r15
            int r12 = r12 + 1
            r9 = r17
            r7 = r67
            goto L_0x1d2a
        L_0x1df9:
            r67 = r7
            r17 = r9
            r7 = r11[r82]
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r1, (java.lang.String[]) r7)
            r7 = 1
            r9 = r11[r7]
            java.lang.String r7 = "PSC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r7, (java.lang.String[]) r9)
            r7 = 2
            r7 = r11[r7]
            java.lang.String r9 = "RSCP"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.String[]) r7)
            r7 = 3
            r7 = r11[r7]
            java.lang.String r9 = "Ec/No"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.String[]) r7)
            r7 = 27
            r4 = r102[r7]
            r3.put(r4, r8)
            r0.resetHashMapKeyValues(r4, r2)
            java.lang.String r7 = "num_cells"
            int r14 = r0.getFieldValue(r10, r7)
            java.lang.String r7 = "umts_cell_list["
            r8 = 0
        L_0x1e2d:
            if (r8 >= r14) goto L_0x1f2c
            r9 = 32
            if (r8 >= r9) goto L_0x1f2c
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r8)
            r9.append(r5)
            java.lang.String r12 = "cell_type"
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r7)
            r12.append(r8)
            r12.append(r5)
            r12.append(r1)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            r13.append(r8)
            r13.append(r5)
            java.lang.String r15 = "PSC"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r8)
            r15.append(r5)
            r17 = r9
            java.lang.String r9 = "RSCP"
            r15.append(r9)
            java.lang.String r9 = r15.toString()
            r15 = 1
            int r9 = r0.getFieldValue(r10, r9, r15)
            float r9 = (float) r9
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r8)
            r15.append(r5)
            r25 = r7
            java.lang.String r7 = "ECN0"
            r15.append(r7)
            java.lang.String r7 = r15.toString()
            r15 = 1
            int r7 = r0.getFieldValue(r10, r7, r15)
            float r7 = (float) r7
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r6)
            r15.append(r8)
            java.lang.String r15 = r15.toString()
            r54 = r11
            java.lang.String r11 = "UMTS"
            r0.addHashMapKeyValues(r4, r2, r11, r15)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r0.addHashMapKeyValues(r4, r2, r1, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r13)
            java.lang.String r11 = r11.toString()
            java.lang.String r15 = "PSC"
            r0.addHashMapKeyValues(r4, r2, r15, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            java.lang.String r15 = "RSCP"
            r0.addHashMapKeyValues(r4, r2, r15, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            r11.append(r7)
            java.lang.String r11 = r11.toString()
            java.lang.String r15 = "Ec/No"
            r0.addHashMapKeyValues(r4, r2, r15, r11)
            int r8 = r8 + 1
            r7 = r25
            r11 = r54
            goto L_0x1e2d
        L_0x1f2c:
            r25 = r7
            r54 = r11
            r1 = r3
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r6 = r25
            r7 = r27
            r11 = r29
            r15 = r40
            r13 = r70
            r25 = r14
            r14 = r44
            goto L_0x41eb
        L_0x1f49:
            r2 = r1
            r88 = r4
            r1 = r9
            r8 = r13
            r15 = r86
            r12 = r87
            r100 = r10
            r10 = r3
            r3 = r5
            r5 = r100
            r4 = 21
            r4 = r102[r4]
            r3.put(r4, r8)
            java.lang.String r7 = "serv_cell."
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            java.lang.String r13 = "multi_plmn_count"
            r9.append(r13)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            r13 = 0
        L_0x1f77:
            if (r13 >= r9) goto L_0x2089
            r14 = 6
            if (r13 >= r14) goto L_0x2089
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r17 = r7
            java.lang.String r7 = "multi_plmn_id"
            r14.append(r7)
            r14.append(r11)
            r14.append(r13)
            r14.append(r5)
            java.lang.String r7 = r14.toString()
            java.lang.String r16 = ""
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r12)
            r67 = r9
            r9 = 1
            r14.append(r9)
            java.lang.String r9 = r14.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r12)
            r90 = r5
            r5 = 2
            r14.append(r5)
            java.lang.String r5 = r14.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r12)
            r89 = r11
            r11 = 3
            r14.append(r11)
            java.lang.String r11 = r14.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r15)
            r87 = r12
            r12 = 1
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r15)
            r86 = r3
            r3 = 2
            r14.append(r3)
            java.lang.String r3 = r14.toString()
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r7)
            r14.append(r15)
            r69 = r7
            r7 = 3
            r14.append(r7)
            java.lang.String r7 = r14.toString()
            int r7 = r0.getFieldValue(r10, r7)
            r14 = 15
            if (r9 != r14) goto L_0x2048
            if (r5 != r14) goto L_0x2048
            if (r11 != r14) goto L_0x2048
            if (r12 != r14) goto L_0x2048
            if (r3 != r14) goto L_0x2048
            if (r7 != r14) goto L_0x2048
            java.lang.String r14 = "PLMN"
            r76 = r15
            java.lang.String r15 = "-"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r14, (java.lang.Object) r15)
            goto L_0x2077
        L_0x2048:
            r76 = r15
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r9)
            r14.append(r5)
            r14.append(r11)
            r14.append(r12)
            r14.append(r3)
            r15 = 15
            if (r7 != r15) goto L_0x2067
            r15 = r6
            goto L_0x206b
        L_0x2067:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)
        L_0x206b:
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            java.lang.String r15 = "PLMN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r15, (java.lang.Object) r14)
        L_0x2077:
            int r13 = r13 + 1
            r7 = r17
            r9 = r67
            r15 = r76
            r3 = r86
            r12 = r87
            r11 = r89
            r5 = r90
            goto L_0x1f77
        L_0x2089:
            r86 = r3
            r17 = r7
            r67 = r9
            java.lang.String r6 = "serv_cell."
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            java.lang.String r5 = "uarfcn_DL"
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r6)
            java.lang.String r7 = "psc"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r6)
            java.lang.String r9 = "rscp"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            r9 = 1
            int r7 = r0.getFieldValue(r10, r7, r9)
            float r7 = (float) r7
            r11 = 1166016512(0x45800000, float:4096.0)
            float r7 = r7 / r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            java.lang.String r12 = "ec_no"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11, r9)
            float r9 = (float) r11
            r11 = 1166016512(0x45800000, float:4096.0)
            float r9 = r9 / r11
            r11 = -1024458752(0xffffffffc2f00000, float:-120.0)
            int r11 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r11 <= 0) goto L_0x211c
            r11 = -1043857408(0xffffffffc1c80000, float:-25.0)
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 <= 0) goto L_0x211c
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r1, (java.lang.Object) r11)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.String r11 = "PSC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r11, (java.lang.Object) r1)
            java.lang.Float r1 = java.lang.Float.valueOf(r7)
            java.lang.String r11 = "RSCP"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r11, (java.lang.Object) r1)
            java.lang.Float r1 = java.lang.Float.valueOf(r9)
            java.lang.String r11 = "Ec/No"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r11, (java.lang.Object) r1)
        L_0x211c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r11 = "cell_identity"
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r10, r1)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r1)
            java.lang.String r12 = "Cell ID"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            java.lang.String r12 = "rssi"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r11)
            java.lang.String r13 = "RSSI"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r13, (java.lang.Object) r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r6)
            java.lang.String r13 = "rac_valid"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            r13 = 1
            if (r12 != r13) goto L_0x218e
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r6)
            java.lang.String r14 = "rac"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.Integer r14 = java.lang.Integer.valueOf(r13)
            java.lang.String r15 = "RAC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r15, (java.lang.Object) r14)
        L_0x218e:
            r13 = 24
            r4 = r102[r13]
            r13 = r86
            r13.put(r4, r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            java.lang.String r14 = "P-carrier UARFCN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r14, (java.lang.Object) r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            java.lang.String r14 = "P-carrier PSC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r14, (java.lang.Object) r8)
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x21be:
            r2 = r1
            r10 = r3
            r88 = r4
            r8 = r13
            r13 = r5
            r1 = 16
            r1 = r102[r1]
            r13.put(r1, r8)
            boolean r3 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r3 == 0) goto L_0x21d8
            java.lang.String r3 = "current_serving_band"
            int r3 = r0.getFieldValue(r10, r3)
            goto L_0x21d9
        L_0x21d8:
            r3 = 2
        L_0x21d9:
            java.lang.String r4 = r0.servingBandMapping(r3)
            java.lang.String r5 = "band"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r5, (java.lang.Object) r4)
            r4 = 17
            r4 = r102[r4]
            r13.put(r4, r8)
            boolean r1 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r1 == 0) goto L_0x21f6
            java.lang.String r1 = "tas_enable_info"
            int r1 = r0.getFieldValue(r10, r1)
            goto L_0x21f7
        L_0x21f6:
            r1 = 2
        L_0x21f7:
            java.lang.String r5 = "tx_pwr"
            r6 = 1
            int r12 = r0.getFieldValue(r10, r5, r6)
            java.lang.String r5 = "force_ant_state"
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.String r7 = "ant0_rscp"
            int r7 = r0.getFieldValue(r10, r7, r6)
            java.lang.String r8 = "ant1_rscp"
            int r6 = r0.getFieldValue(r10, r8, r6)
            java.lang.String r8 = r0.tasEableMapping(r1)
            java.lang.String r9 = "TAS Enable"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.Object) r8)
            java.lang.String r8 = r0.antidxMapping(r5)
            java.lang.String r9 = "TX Antenna"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.Object) r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r12)
            java.lang.String r9 = "TX Power"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.Object) r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r7)
            java.lang.String r9 = "RSRP_LANT"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.Object) r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r6)
            java.lang.String r9 = "RSRP_UANT"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r9, (java.lang.Object) r8)
            r24 = r1
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x2254:
            r2 = r1
            r10 = r3
            r88 = r4
            r1 = r9
            r8 = r13
            r13 = r5
            r3 = 16
            r3 = r102[r3]
            r13.put(r3, r8)
            java.lang.String r6 = "serv_cell."
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r6)
            java.lang.String r5 = "uarfcn_DL"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            int r4 = r0.getFieldValue(r10, r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r6)
            java.lang.String r7 = "psc"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r6)
            java.lang.String r9 = "rscp"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            r9 = 1
            int r7 = r0.getFieldValue(r10, r7, r9)
            float r7 = (float) r7
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            java.lang.String r11 = "cell_identity"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r6)
            java.lang.String r12 = "rssi"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r10, r11)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r5)
            java.lang.String r14 = "PSC"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r14, (java.lang.Object) r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r1, (java.lang.Object) r12)
            r12 = 1166016512(0x45800000, float:4096.0)
            float r12 = r7 / r12
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            java.lang.String r14 = "RSCP"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r14, (java.lang.Object) r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r9)
            java.lang.String r14 = "Cell ID"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r14, (java.lang.Object) r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r11)
            java.lang.String r14 = "RSSI"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r14, (java.lang.Object) r12)
            r12 = 19
            r3 = r102[r12]
            r13.put(r3, r8)
            r0.resetHashMapKeyValues(r3, r2)
            java.lang.String r8 = "UMTS"
            java.lang.String r12 = "0"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r8, (java.lang.Object) r12)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r4)
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r1, (java.lang.Object) r8)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.String r8 = "PSC"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r8, (java.lang.Object) r1)
            r1 = 1166016512(0x45800000, float:4096.0)
            float r1 = r7 / r1
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            java.lang.String r8 = "RSCP"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r8, (java.lang.Object) r1)
            r4 = r3
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            goto L_0x41eb
        L_0x2342:
            r2 = r1
            r10 = r3
            r88 = r4
            r8 = r13
            r76 = r86
            r13 = r5
            r1 = 16
            r1 = r102[r1]
            r13.put(r1, r8)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r4 = r85
            r3.append(r4)
            r5 = r87
            r3.append(r5)
            java.lang.String r7 = "[0]"
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r4)
            r7.append(r5)
            java.lang.String r9 = "[1]"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r5)
            java.lang.String r5 = "[2]"
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r11 = r76
            r9.append(r11)
            java.lang.String r12 = "[0]"
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r4)
            r12.append(r11)
            java.lang.String r14 = "[1]"
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r4)
            r14.append(r11)
            java.lang.String r11 = "[2]"
            r14.append(r11)
            java.lang.String r11 = r14.toString()
            int r11 = r0.getFieldValue(r10, r11)
            r14 = 15
            if (r3 != r14) goto L_0x23fd
            if (r7 != r14) goto L_0x23fd
            if (r5 != r14) goto L_0x23fd
            if (r9 != r14) goto L_0x23fd
            if (r12 != r14) goto L_0x23fd
            if (r11 != r14) goto L_0x23fd
            java.lang.String r6 = "PLMN"
            java.lang.String r14 = "-"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r6, (java.lang.Object) r14)
            goto L_0x2429
        L_0x23fd:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r14.append(r3)
            r14.append(r7)
            r14.append(r5)
            r14.append(r9)
            r14.append(r12)
            r15 = 15
            if (r11 != r15) goto L_0x2419
            goto L_0x241d
        L_0x2419:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r11)
        L_0x241d:
            r14.append(r6)
            java.lang.String r6 = r14.toString()
            java.lang.String r14 = "PLMN"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r14, (java.lang.Object) r6)
        L_0x2429:
            r6 = 1
            java.lang.Object[] r14 = new java.lang.Object[r6]
            java.lang.String r15 = "loc[0]"
            int r15 = r0.getFieldValue(r10, r15)
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            r14[r82] = r15
            java.lang.String r15 = "%d"
            java.lang.String r14 = java.lang.String.format(r15, r14)
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r15 = "loc[1]"
            int r15 = r0.getFieldValue(r10, r15)
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            r6[r82] = r15
            java.lang.String r15 = "%d"
            java.lang.String r6 = java.lang.String.format(r15, r6)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r14)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            r17 = r3
            java.lang.String r3 = "LAC"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r3, (java.lang.Object) r15)
            r3 = 21
            r1 = r102[r3]
            r13.put(r1, r8)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r14)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            java.lang.String r8 = "LAC"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r8, (java.lang.Object) r3)
            r6 = r4
            r90 = r10
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r4 = r1
            r1 = r13
            r13 = r70
            goto L_0x41eb
        L_0x249a:
            r2 = r1
            r10 = r3
            r88 = r4
            r8 = r13
            r4 = r85
            r13 = r5
            r1 = 14
            r1 = r102[r1]
            r13.put(r1, r8)
            java.lang.String r3 = "channel"
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.String r5 = "band_class"
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.String r7 = "pilot_pn_offset"
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.String r9 = "rx_power"
            r11 = 1
            int r9 = r0.getFieldValue(r10, r9, r11)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)
            java.lang.String r12 = "RSSI"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)
            java.lang.String r12 = "Channel"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r7)
            java.lang.String r12 = "pilotPN"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)
            java.lang.String r12 = "band_class"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            r11 = 15
            r1 = r102[r11]
            r13.put(r1, r8)
            boolean r8 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r8 == 0) goto L_0x24fc
            java.lang.String r8 = "tas_enable"
            int r8 = r0.getFieldValue(r10, r8)
            r84 = r8
            goto L_0x24fe
        L_0x24fc:
            r84 = 2
        L_0x24fe:
            r8 = r84
            java.lang.String r11 = "tx_ant"
            r12 = 1
            int r11 = r0.getFieldValue(r10, r11, r12)
            java.lang.String r14 = "rx_power_dbmL"
            int r14 = r0.getFieldValue(r10, r14, r12)
            java.lang.String r15 = "rx_power_dbmU"
            int r15 = r0.getFieldValue(r10, r15, r12)
            r17 = r3
            java.lang.String r3 = "rx_power_dbmLp"
            int r3 = r0.getFieldValue(r10, r3, r12)
            r85 = r4
            java.lang.String r4 = "tas_ver"
            int r4 = r0.getFieldValue(r10, r4, r12)
            if (r4 == r12) goto L_0x2557
            r12 = -150(0xffffffffffffff6a, float:NaN)
            if (r14 != r12) goto L_0x252c
            r12 = r6
            goto L_0x2530
        L_0x252c:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r14)
        L_0x2530:
            r24 = r4
            java.lang.String r4 = "rxPower_LANT"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r4, (java.lang.Object) r12)
            r4 = -150(0xffffffffffffff6a, float:NaN)
            if (r15 != r4) goto L_0x253e
            r4 = r6
            goto L_0x2542
        L_0x253e:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)
        L_0x2542:
            java.lang.String r12 = "rxPower_UANT"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r12, (java.lang.Object) r4)
            r4 = -150(0xffffffffffffff6a, float:NaN)
            if (r3 != r4) goto L_0x254d
            goto L_0x2551
        L_0x254d:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)
        L_0x2551:
            java.lang.String r4 = "rxPower_UANT(')"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r4, (java.lang.Object) r6)
            goto L_0x2559
        L_0x2557:
            r24 = r4
        L_0x2559:
            java.lang.String r4 = r0.tasEableMapping(r8)
            java.lang.String r6 = "TAS Enable"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r6, (java.lang.Object) r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r11)
            java.lang.String r6 = "tx_Ant"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r6, (java.lang.Object) r4)
            r4 = r1
            r24 = r8
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x2585:
            r2 = r1
            r10 = r3
            r88 = r4
            r8 = r13
            r13 = r5
            r1 = 13
            r4 = r102[r1]
            r13.put(r4, r8)
            java.lang.String r1 = "rssi_dbm"
            r3 = 1
            int r1 = r0.getFieldValue(r10, r1, r3)
            int r1 = r1 / 128
            java.lang.String r3 = "bandClass"
            int r3 = r0.getFieldValue(r10, r3)
            java.lang.String r5 = "channel"
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.String r6 = "pilotPN"
            int r6 = r0.getFieldValue(r10, r6)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)
            java.lang.String r8 = "Channel"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r8, (java.lang.Object) r7)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
            java.lang.String r8 = "pilotPN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r8, (java.lang.Object) r7)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            java.lang.String r8 = "band_class"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r8, (java.lang.Object) r7)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
            java.lang.String r8 = "RSSI"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r8, (java.lang.Object) r7)
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x25e8:
            r2 = r1
            r10 = r3
            r88 = r4
            r8 = r13
            r13 = r5
            r1 = 9
            r4 = r102[r1]
            r13.put(r4, r8)
            boolean r1 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r1 == 0) goto L_0x2602
            java.lang.String r1 = "tas_enable"
            int r1 = r0.getFieldValue(r10, r1)
            goto L_0x2603
        L_0x2602:
            r1 = 2
        L_0x2603:
            java.lang.String r3 = "gsm_antenna"
            int r8 = r0.getFieldValue(r10, r3)
            java.lang.String r3 = "gsm_current_antenna_rxLevel"
            r5 = 1
            int r3 = r0.getFieldValue(r10, r3, r5)
            java.lang.String r7 = "gsm_other_antenna_rxLevel"
            int r7 = r0.getFieldValue(r10, r7, r5)
            r9 = 4
            java.lang.String[] r9 = new java.lang.String[r9]
            java.lang.String r11 = r0.tasEableMapping(r1)
            r9[r82] = r11
            java.lang.String r11 = r0.antidxMapping(r8)
            r9[r5] = r11
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r11 = 2
            r9[r11] = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = 3
            r9[r6] = r5
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String[]) r9)
            r24 = r1
            r19 = r8
            r90 = r10
            r1 = r13
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r13 = r70
            r6 = r85
            goto L_0x41eb
        L_0x2667:
            r2 = r1
            r10 = r3
            r88 = r4
            r89 = r11
            r8 = r13
            r13 = r5
            r1 = 8
            r1 = r102[r1]
            r13.put(r1, r8)
            java.lang.String r3 = "rr_em_measurement_report_info."
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            java.lang.String r5 = "rr_state"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            int r4 = r0.getFieldValue(r10, r4)
            r5 = 3
            if (r4 < r5) goto L_0x2769
            r5 = 7
            if (r4 > r5) goto L_0x2769
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            java.lang.String r7 = "serving_current_band"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r3)
            java.lang.String r9 = "serving_arfcn"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            int r7 = r0.getFieldValue(r10, r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            java.lang.String r11 = "serving_bsic"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r3)
            java.lang.String r12 = "serv_rla_in_quarter_dbm"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r12 = 1
            int r11 = r0.getFieldValue(r10, r11, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r3)
            java.lang.String r14 = "rxqual_sub"
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            int r12 = r0.getFieldValue(r10, r12)
            java.util.Map<java.lang.Integer, java.lang.String> r14 = r0.mRrStateMapping
            java.lang.String r15 = "rr_em_measurement_report_info.rr_state"
            int r15 = r0.getFieldValue(r10, r15)
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            java.lang.Object r14 = r14.get(r15)
            java.lang.String r14 = (java.lang.String) r14
            r15 = -1000(0xfffffffffffffc18, float:NaN)
            if (r11 != r15) goto L_0x2718
            java.lang.String r15 = "-"
            r17 = r3
            goto L_0x272c
        L_0x2718:
            r17 = r3
            r15 = 1
            java.lang.Object[] r3 = new java.lang.Object[r15]
            float r15 = (float) r11
            float r15 = r15 / r78
            java.lang.Float r15 = java.lang.Float.valueOf(r15)
            r3[r82] = r15
            java.lang.String r15 = "%.2f"
            java.lang.String r15 = java.lang.String.format(r15, r3)
        L_0x272c:
            java.lang.String r3 = "RLA"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r3, (java.lang.Object) r15)
            r3 = 255(0xff, float:3.57E-43)
            if (r12 != r3) goto L_0x2739
            java.lang.String r3 = "-"
            goto L_0x273d
        L_0x2739:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r12)
        L_0x273d:
            java.lang.String r15 = "RX quality sub"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r7)
            java.lang.String r15 = "ARFCN"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r9)
            java.lang.String r15 = "BSIC"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r3)
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.bandMapping
            java.lang.Integer r15 = java.lang.Integer.valueOf(r5)
            java.lang.Object r3 = r3.get(r15)
            java.lang.String r15 = "band"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r15, (java.lang.Object) r3)
            java.lang.String r3 = "RR state"
            r0.setHashMapKeyValues((java.lang.String) r1, (int) r2, (java.lang.String) r3, (java.lang.Object) r14)
            goto L_0x276b
        L_0x2769:
            r17 = r3
        L_0x276b:
            r3 = 12
            r1 = r102[r3]
            r13.put(r1, r8)
            r0.resetHashMapKeyValues(r1, r2)
            java.lang.String r3 = "rr_em_measurement_report_info."
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            java.lang.String r7 = "num_of_carriers"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            int r14 = r0.getFieldValue(r10, r5)
            r5 = 0
        L_0x278d:
            if (r5 >= r14) goto L_0x2858
            r7 = 32
            if (r5 >= r7) goto L_0x2858
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            java.lang.String r9 = "nc_arfcn"
            r8.append(r9)
            r11 = r89
            r8.append(r11)
            r8.append(r5)
            java.lang.String r9 = "]"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            java.lang.String r12 = "nc_bsic"
            r9.append(r12)
            r9.append(r11)
            r9.append(r5)
            java.lang.String r12 = "]"
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r10, r9)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r3)
            java.lang.String r15 = "rla_in_quarter_dbm"
            r12.append(r15)
            r12.append(r11)
            r12.append(r5)
            java.lang.String r15 = "]"
            r12.append(r15)
            java.lang.String r12 = r12.toString()
            r15 = 1
            int r12 = r0.getFieldValue(r10, r12, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r5)
            r15.append(r6)
            java.lang.String r15 = r15.toString()
            java.lang.String r7 = "GSM"
            r0.addHashMapKeyValues(r1, r2, r7, r15)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r8)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            java.lang.String r15 = "ARFCN"
            r0.addHashMapKeyValues(r1, r2, r15, r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r9)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            java.lang.String r15 = "BSIC"
            r0.addHashMapKeyValues(r1, r2, r15, r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            float r15 = (float) r12
            r54 = r3
            r17 = r4
            double r3 = (double) r15
            r77 = 4616189618054758400(0x4010000000000000, double:4.0)
            double r3 = r3 / r77
            r7.append(r3)
            r7.append(r6)
            java.lang.String r3 = r7.toString()
            java.lang.String r4 = "RSSI"
            r0.addHashMapKeyValues(r1, r2, r4, r3)
            int r5 = r5 + 1
            r4 = r17
            r3 = r54
            goto L_0x278d
        L_0x2858:
            r54 = r3
            r17 = r4
            r4 = r1
            r90 = r10
            r1 = r13
            r25 = r14
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r15 = r40
            r14 = r44
            r6 = r54
            r13 = r70
            goto L_0x41eb
        L_0x2876:
            r2 = r1
            r88 = r4
            r1 = r7
            r9 = r8
            r90 = r10
            r8 = r13
            r7 = r15
            r10 = r3
            r13 = r5
            r15 = r12
            r3 = 2
            r4 = r102[r3]
            r13.put(r4, r8)
            r0.resetHashMapKeyValues(r4, r2)
            java.lang.String r3 = "scell_info_list.scell_info"
            java.lang.String r5 = "scell_info_list.num_scell"
            int r5 = r0.getFieldValue(r10, r5)
            r12 = 0
        L_0x2894:
            if (r12 >= r5) goto L_0x29db
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r11)
            r14.append(r12)
            r17 = r5
            r5 = r90
            r14.append(r5)
            r76 = r6
            java.lang.String r6 = "earfcn"
            r14.append(r6)
            java.lang.String r6 = r14.toString()
            int r6 = r0.getFieldValue(r10, r6)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r11)
            r14.append(r12)
            r14.append(r5)
            r90 = r8
            java.lang.String r8 = "pci"
            r14.append(r8)
            java.lang.String r8 = r14.toString()
            int r8 = r0.getFieldValue(r10, r8)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r11)
            r14.append(r12)
            r14.append(r5)
            r89 = r13
            java.lang.String r13 = "rsrp"
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r10, r13, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r11)
            r14.append(r12)
            r14.append(r5)
            r79 = r15
            java.lang.String r15 = "rsrq"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r10, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r12)
            r15.append(r5)
            r25 = r14
            java.lang.String r14 = "rs_snr_in_qdb"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r10, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r3)
            r15.append(r11)
            r15.append(r12)
            r15.append(r5)
            r85 = r3
            java.lang.String r3 = "serv_lte_band"
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            int r3 = r0.getFieldValue(r10, r3)
            if (r6 == 0) goto L_0x29c4
            if (r8 == 0) goto L_0x29c4
            r15 = -1
            if (r6 == r15) goto L_0x29c4
            java.lang.Integer r15 = java.lang.Integer.valueOf(r6)
            r0.addHashMapKeyValues(r4, r2, r1, r15)
            java.lang.Integer r15 = java.lang.Integer.valueOf(r8)
            r0.addHashMapKeyValues(r4, r2, r7, r15)
            r15 = 65535(0xffff, float:9.1834E-41)
            if (r3 != r15) goto L_0x297b
            r15 = r76
            goto L_0x297f
        L_0x297b:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r3)
        L_0x297f:
            r86 = r3
            java.lang.String r3 = "Band"
            r0.addHashMapKeyValues(r4, r2, r3, r15)
            r3 = -1
            if (r13 != r3) goto L_0x298d
            r3 = r76
            goto L_0x2994
        L_0x298d:
            float r3 = (float) r13
            float r3 = r3 / r78
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
        L_0x2994:
            r0.addHashMapKeyValues(r4, r2, r9, r3)
            r3 = r25
            r15 = -1
            if (r3 != r15) goto L_0x29a0
            r15 = r76
            goto L_0x29a7
        L_0x29a0:
            float r15 = (float) r3
            float r15 = r15 / r78
            java.lang.Float r15 = java.lang.Float.valueOf(r15)
        L_0x29a7:
            r25 = r3
            r3 = r79
            r0.addHashMapKeyValues(r4, r2, r3, r15)
            r15 = -1
            if (r14 != r15) goto L_0x29b5
            r15 = r76
            goto L_0x29bc
        L_0x29b5:
            float r15 = (float) r14
            float r15 = r15 / r78
            java.lang.Float r15 = java.lang.Float.valueOf(r15)
        L_0x29bc:
            r79 = r6
            java.lang.String r6 = "SINR"
            r0.addHashMapKeyValues(r4, r2, r6, r15)
            goto L_0x29ca
        L_0x29c4:
            r86 = r3
            r3 = r79
            r79 = r6
        L_0x29ca:
            int r12 = r12 + 1
            r15 = r3
            r6 = r76
            r3 = r85
            r13 = r89
            r8 = r90
            r90 = r5
            r5 = r17
            goto L_0x2894
        L_0x29db:
            r85 = r3
            r17 = r5
            r76 = r6
            r89 = r13
            r3 = r15
            r5 = r90
            r90 = r8
            r6 = 3
            r4 = r102[r6]
            r6 = r89
            r6.put(r4, r8)
            r0.resetHashMapKeyValues(r4, r2)
            java.lang.String r12 = "inter_info."
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "freq_num"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            r14 = 0
            r15 = 0
        L_0x2a0c:
            if (r15 >= r13) goto L_0x2c0a
            r25 = r13
            r13 = 4
            if (r15 >= r13) goto L_0x2bf4
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            r79 = r12
            java.lang.String r12 = "inter_freq"
            r13.append(r12)
            r13.append(r11)
            r13.append(r15)
            r13.append(r5)
            java.lang.String r12 = r13.toString()
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            r26 = r14
            java.lang.String r14 = "valid"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r10, r13)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r12)
            r89 = r6
            java.lang.String r6 = "earfcn"
            r14.append(r6)
            java.lang.String r6 = r14.toString()
            int r6 = r0.getFieldValue(r10, r6)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r12)
            r90 = r8
            java.lang.String r8 = "cell_num"
            r14.append(r8)
            java.lang.String r8 = r14.toString()
            int r8 = r0.getFieldValue(r10, r8)
            r14 = 6
            if (r8 >= r14) goto L_0x2a78
            r14 = r8
        L_0x2a78:
            java.lang.String[] r14 = new java.lang.String[r14]
            r86 = r3
            r3 = 6
            if (r8 >= r3) goto L_0x2a80
            r3 = r8
        L_0x2a80:
            java.lang.String[] r3 = new java.lang.String[r3]
            r91 = r9
            r9 = 6
            if (r8 >= r9) goto L_0x2a88
            r9 = r8
        L_0x2a88:
            java.lang.String[] r9 = new java.lang.String[r9]
            r92 = r7
            r7 = 6
            if (r8 >= r7) goto L_0x2a90
            r7 = r8
        L_0x2a90:
            java.lang.String[] r7 = new java.lang.String[r7]
            r93 = r1
            r1 = 6
            if (r8 >= r1) goto L_0x2a99
            r1 = r8
            goto L_0x2a9a
        L_0x2a99:
            r1 = 6
        L_0x2a9a:
            java.lang.String[] r1 = new java.lang.String[r1]
            r17 = 0
            r85 = r4
            r4 = r17
            r2 = r26
        L_0x2aa4:
            if (r4 >= r8) goto L_0x2bae
            r17 = r8
            r8 = 6
            if (r4 >= r8) goto L_0x2ba3
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r12)
            r87 = r12
            java.lang.String r12 = "inter_cell"
            r8.append(r12)
            r8.append(r11)
            r8.append(r4)
            r8.append(r5)
            java.lang.String r8 = r8.toString()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r8)
            r94 = r5
            java.lang.String r5 = "pci"
            r12.append(r5)
            java.lang.String r5 = r12.toString()
            int r5 = r0.getFieldValue(r10, r5)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r8)
            r95 = r11
            java.lang.String r11 = "rsrp"
            r12.append(r11)
            java.lang.String r11 = r12.toString()
            r12 = 1
            int r11 = r0.getFieldValue(r10, r11, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r8)
            r26 = r8
            java.lang.String r8 = "rsrq"
            r12.append(r8)
            java.lang.String r8 = r12.toString()
            r12 = 1
            int r8 = r0.getFieldValue(r10, r8, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r96 = r10
            r10 = r76
            r12.append(r10)
            r12.append(r2)
            java.lang.String r12 = r12.toString()
            r14[r4] = r12
            if (r13 <= 0) goto L_0x2b84
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r10)
            r12.append(r6)
            java.lang.String r12 = r12.toString()
            r1[r4] = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r10)
            r12.append(r5)
            java.lang.String r12 = r12.toString()
            r3[r4] = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r10)
            r76 = r5
            r5 = -1
            if (r11 == r5) goto L_0x2b5c
            float r5 = (float) r11
            float r5 = r5 / r78
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            goto L_0x2b5d
        L_0x2b5c:
            r5 = r10
        L_0x2b5d:
            r12.append(r5)
            java.lang.String r5 = r12.toString()
            r9[r4] = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r10)
            r12 = -1
            if (r8 == r12) goto L_0x2b79
            float r12 = (float) r8
            float r12 = r12 / r78
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            goto L_0x2b7a
        L_0x2b79:
            r12 = r10
        L_0x2b7a:
            r5.append(r12)
            java.lang.String r5 = r5.toString()
            r7[r4] = r5
            goto L_0x2b90
        L_0x2b84:
            r76 = r5
            r7[r15] = r10
            r9[r15] = r10
            r3[r15] = r10
            r1[r15] = r10
            r14[r15] = r10
        L_0x2b90:
            int r2 = r2 + 1
            int r4 = r4 + 1
            r76 = r10
            r8 = r17
            r12 = r87
            r5 = r94
            r11 = r95
            r10 = r96
            goto L_0x2aa4
        L_0x2ba3:
            r94 = r5
            r96 = r10
            r95 = r11
            r87 = r12
            r10 = r76
            goto L_0x2bba
        L_0x2bae:
            r94 = r5
            r17 = r8
            r96 = r10
            r95 = r11
            r87 = r12
            r10 = r76
        L_0x2bba:
            java.lang.String r4 = "LTE"
            r26 = r2
            r5 = r85
            r2 = r103
            r0.setHashMapKeyValues((java.lang.String) r5, (int) r2, (java.lang.String) r4, (java.lang.String[]) r14)
            r4 = r93
            r0.setHashMapKeyValues((java.lang.String) r5, (int) r2, (java.lang.String) r4, (java.lang.String[]) r1)
            r8 = r92
            r0.setHashMapKeyValues((java.lang.String) r5, (int) r2, (java.lang.String) r8, (java.lang.String[]) r3)
            r11 = r91
            r0.setHashMapKeyValues((java.lang.String) r5, (int) r2, (java.lang.String) r11, (java.lang.String[]) r9)
            r12 = r86
            r0.setHashMapKeyValues((java.lang.String) r5, (int) r2, (java.lang.String) r12, (java.lang.String[]) r7)
            int r15 = r15 + 1
            r1 = r4
            r4 = r5
            r7 = r8
            r76 = r10
            r9 = r11
            r3 = r12
            r13 = r25
            r14 = r26
            r12 = r79
            r6 = r89
            r8 = r90
            r5 = r94
            r11 = r95
            r10 = r96
            goto L_0x2a0c
        L_0x2bf4:
            r94 = r5
            r89 = r6
            r90 = r8
            r96 = r10
            r95 = r11
            r79 = r12
            r26 = r14
            r10 = r76
            r12 = r3
            r5 = r4
            r8 = r7
            r11 = r9
            r4 = r1
            goto L_0x2c21
        L_0x2c0a:
            r94 = r5
            r89 = r6
            r90 = r8
            r96 = r10
            r95 = r11
            r79 = r12
            r25 = r13
            r26 = r14
            r10 = r76
            r12 = r3
            r5 = r4
            r8 = r7
            r11 = r9
            r4 = r1
        L_0x2c21:
            java.lang.String r1 = "intra_info."
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r1)
            java.lang.String r6 = "cell_num"
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r6 = r96
            int r14 = r0.getFieldValue(r6, r3)
            r3 = 16
            if (r14 >= r3) goto L_0x2c40
            r7 = r14
            goto L_0x2c41
        L_0x2c40:
            r7 = r3
        L_0x2c41:
            java.lang.String[] r7 = new java.lang.String[r7]
            if (r14 >= r3) goto L_0x2c47
            r9 = r14
            goto L_0x2c48
        L_0x2c47:
            r9 = r3
        L_0x2c48:
            java.lang.String[] r9 = new java.lang.String[r9]
            if (r14 >= r3) goto L_0x2c4e
            r13 = r14
            goto L_0x2c4f
        L_0x2c4e:
            r13 = r3
        L_0x2c4f:
            java.lang.String[] r13 = new java.lang.String[r13]
            if (r14 >= r3) goto L_0x2c55
            r15 = r14
            goto L_0x2c56
        L_0x2c55:
            r15 = r3
        L_0x2c56:
            java.lang.String[] r15 = new java.lang.String[r15]
            if (r14 >= r3) goto L_0x2c5c
            r3 = r14
            goto L_0x2c5e
        L_0x2c5c:
            r3 = 16
        L_0x2c5e:
            java.lang.String[] r3 = new java.lang.String[r3]
            r79 = r12
            java.lang.String r12 = "serving_info.earfcn"
            int r12 = r0.getFieldValue(r6, r12)
            r17 = 0
            r92 = r8
            r91 = r11
            r8 = r17
            r11 = r26
        L_0x2c72:
            if (r8 >= r14) goto L_0x2d8c
            r17 = r14
            r14 = 16
            if (r8 >= r14) goto L_0x2d85
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r26 = r1
            java.lang.String r1 = "intra_cell"
            r14.append(r1)
            r1 = r95
            r14.append(r1)
            r14.append(r8)
            r1 = r94
            r14.append(r1)
            java.lang.String r14 = r14.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r93 = r4
            java.lang.String r4 = "valid"
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r6, r1)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r14)
            java.lang.String r2 = "pci"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            int r2 = r0.getFieldValue(r6, r2)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r14)
            r85 = r5
            java.lang.String r5 = "rsrp"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 1
            int r4 = r0.getFieldValue(r6, r4, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r14)
            r76 = r14
            java.lang.String r14 = "rsrq"
            r5.append(r14)
            java.lang.String r5 = r5.toString()
            r14 = 1
            int r5 = r0.getFieldValue(r6, r5, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            r14.append(r11)
            java.lang.String r14 = r14.toString()
            r7[r8] = r14
            if (r1 <= 0) goto L_0x2d68
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            r14.append(r12)
            java.lang.String r14 = r14.toString()
            r9[r8] = r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            r14.append(r2)
            java.lang.String r14 = r14.toString()
            r13[r8] = r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r10)
            r86 = r1
            r1 = -1
            if (r4 == r1) goto L_0x2d40
            float r1 = (float) r4
            float r1 = r1 / r78
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            goto L_0x2d41
        L_0x2d40:
            r1 = r10
        L_0x2d41:
            r14.append(r1)
            java.lang.String r1 = r14.toString()
            r15[r8] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r10)
            r14 = -1
            if (r5 == r14) goto L_0x2d5d
            float r14 = (float) r5
            float r14 = r14 / r78
            java.lang.Float r14 = java.lang.Float.valueOf(r14)
            goto L_0x2d5e
        L_0x2d5d:
            r14 = r10
        L_0x2d5e:
            r1.append(r14)
            java.lang.String r1 = r1.toString()
            r3[r8] = r1
            goto L_0x2d74
        L_0x2d68:
            r86 = r1
            r3[r8] = r10
            r15[r8] = r10
            r13[r8] = r10
            r9[r8] = r10
            r7[r8] = r10
        L_0x2d74:
            int r11 = r11 + 1
            int r8 = r8 + 1
            r2 = r103
            r14 = r17
            r1 = r26
            r5 = r85
            r4 = r93
            goto L_0x2c72
        L_0x2d85:
            r26 = r1
            r93 = r4
            r85 = r5
            goto L_0x2d94
        L_0x2d8c:
            r26 = r1
            r93 = r4
            r85 = r5
            r17 = r14
        L_0x2d94:
            java.lang.String r1 = "LTE"
            r2 = r103
            r4 = r85
            r0.addHashMapKeyValues(r4, r2, r1, r7)
            r1 = r93
            r0.addHashMapKeyValues(r4, r2, r1, r9)
            r5 = r92
            r0.addHashMapKeyValues(r4, r2, r5, r13)
            r8 = r91
            r0.addHashMapKeyValues(r4, r2, r8, r15)
            r14 = r79
            r0.addHashMapKeyValues(r4, r2, r14, r3)
            r69 = 6
            r4 = r102[r69]
            r76 = r3
            r77 = r7
            r3 = r89
            r7 = r90
            r3.put(r4, r7)
            r0.resetHashMapKeyValues(r4, r2)
            java.lang.String r7 = "serving_info."
            r26 = r9
            java.lang.String r9 = "scell_info_list.scell_info["
            r79 = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r7)
            r83 = r12
            java.lang.String r12 = "earfcn"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r6, r11)
            long r11 = (long) r11
            r85 = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            r86 = r15
            java.lang.String r15 = "pci"
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r6, r13)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            java.lang.String r3 = "rsrp"
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            r15 = 1
            int r3 = r0.getFieldValue(r6, r3, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r69 = r14
            java.lang.String r14 = "rsrq"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r6, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r18 = r14
            java.lang.String r14 = "rs_snr_in_qdb"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r6, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r87 = r7
            java.lang.String r7 = "serv_lte_band"
            r15.append(r7)
            java.lang.String r7 = r15.toString()
            int r7 = r0.getFieldValue(r6, r7)
            r15 = 0
        L_0x2e5a:
            r27 = r3
            r3 = 3
            if (r15 >= r3) goto L_0x2f23
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r91 = r8
            r8 = r94
            r3.append(r8)
            r28 = r14
            java.lang.String r14 = "earfcn"
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r6, r3)
            r92 = r11
            long r11 = (long) r3
            r81[r15] = r11
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r3.append(r8)
            java.lang.String r11 = "pci"
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r6, r3)
            r75[r15] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r3.append(r8)
            java.lang.String r11 = "rsrp"
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r11 = 1
            int r3 = r0.getFieldValue(r6, r3, r11)
            r74[r15] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r3.append(r8)
            java.lang.String r12 = "rsrq"
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r6, r3, r11)
            r73[r15] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r3.append(r8)
            java.lang.String r12 = "rs_snr_in_qdb"
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r6, r3, r11)
            r72[r15] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r15)
            r3.append(r8)
            java.lang.String r11 = "serv_lte_band"
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r6, r3)
            r71[r15] = r3
            int r15 = r15 + 1
            r3 = r27
            r14 = r28
            r8 = r91
            r11 = r92
            goto L_0x2e5a
        L_0x2f23:
            r91 = r8
            r92 = r11
            r28 = r14
            r3 = 5
            java.lang.String[] r8 = new java.lang.String[r3]
            java.lang.String r3 = "Band"
            r8[r82] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 1
            r8[r11] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r12 = r71[r82]
            r3.append(r12)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r12 = 2
            r8[r12] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r14 = r71[r11]
            r3.append(r14)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 3
            r8[r11] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r11 = r71[r12]
            r3.append(r11)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 4
            r8[r11] = r3
            r0.addHashMapKeyValues(r4, r2, r8)
            r3 = 5
            java.lang.String[] r8 = new java.lang.String[r3]
            java.lang.String r3 = "DL BW(MHz)"
            r8[r82] = r3
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mMappingBW
            r12 = r70
            java.lang.String r3 = r0.getValueFromMapping(r12, r3)
            r11 = 1
            r8[r11] = r3
            r3 = r43[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r14 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r14)
            r14 = 2
            r8[r14] = r3
            r3 = r43[r11]
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r11)
            r11 = 3
            r8[r11] = r3
            r3 = r43[r14]
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r11)
            r11 = 4
            r8[r11] = r3
            r0.addHashMapKeyValues(r4, r2, r8)
            r3 = 5
            java.lang.String[] r8 = new java.lang.String[r3]
            java.lang.String r3 = "UL BW(MHz)"
            r8[r82] = r3
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mMappingBW
            r14 = r40
            java.lang.String r3 = r0.getValueFromMapping(r14, r3)
            r11 = 1
            r8[r11] = r3
            r3 = r34[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r15 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r15)
            r15 = 2
            r8[r15] = r3
            r3 = r34[r11]
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r11)
            r11 = 3
            r8[r11] = r3
            r3 = r34[r15]
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.String r3 = r0.getValueFromMapping(r3, r11)
            r11 = 4
            r8[r11] = r3
            r0.addHashMapKeyValues(r4, r2, r8)
            r3 = 5
            java.lang.String[] r8 = new java.lang.String[r3]
            java.lang.String r3 = "TM"
            r8[r82] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r15 = r32
            r3.append(r15)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 1
            r8[r11] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r11 = r30[r82]
            r3.append(r11)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 2
            r8[r11] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r29 = 1
            r11 = r30[r29]
            r3.append(r11)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r11 = 3
            r8[r11] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r29 = r7
            r11 = 2
            r7 = r30[r11]
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 4
            r8[r7] = r3
            r0.addHashMapKeyValues(r4, r2, r8)
            r3 = 5
            java.lang.String[] r7 = new java.lang.String[r3]
            r7[r82] = r5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r13)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 1
            r7[r5] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r8 = r75[r82]
            r3.append(r8)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r8 = 2
            r7[r8] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r11 = r75[r5]
            r3.append(r11)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 3
            r7[r5] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = r75[r8]
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 4
            r7[r5] = r3
            r0.addHashMapKeyValues(r4, r2, r7)
            r3 = 5
            java.lang.String[] r5 = new java.lang.String[r3]
            r5[r82] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r7 = r92
            r1.append(r7)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r3 = 1
            r5[r3] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r11 = r4
            r3 = r81[r82]
            r1.append(r3)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r3 = 2
            r5[r3] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r4 = 1
            r7 = r81[r4]
            r1.append(r7)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r4 = 3
            r5[r4] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r7 = r81[r3]
            r1.append(r7)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r3 = 4
            r5[r3] = r1
            r1 = r11
            r0.addHashMapKeyValues(r1, r2, r5)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r3 = "RS_SNR"
            r4[r82] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = r28
            float r7 = (float) r5
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 1
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = r72[r82]
            float r7 = (float) r7
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 2
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = 1
            r8 = r72[r7]
            float r7 = (float) r8
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 3
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = 2
            r8 = r72[r7]
            float r7 = (float) r8
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 4
            r4[r7] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r3 = "DL Freq"
            r4[r82] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r11 = r35
            r3.append(r11)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 1
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r8 = r36[r82]
            r3.append(r8)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r8 = 2
            r4[r8] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r8 = r36[r7]
            r3.append(r8)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 3
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = 2
            r8 = r36[r7]
            r3.append(r8)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 4
            r4[r7] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r3 = "UL Freq"
            r4[r82] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r8 = r37
            r3.append(r8)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 1
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = r38[r82]
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 2
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r28 = 1
            r7 = r38[r28]
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 3
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r28 = r5
            r7 = 2
            r5 = r38[r7]
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 4
            r4[r5] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            r4[r82] = r91
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = r27
            float r7 = (float) r5
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 1
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = r74[r82]
            float r7 = (float) r7
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 2
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = 1
            r5 = r74[r7]
            float r5 = (float) r5
            float r5 = r5 / r78
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 3
            r4[r5] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = 2
            r7 = r74[r5]
            float r5 = (float) r7
            float r5 = r5 / r78
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 4
            r4[r5] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            r4[r82] = r69
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = r18
            float r7 = (float) r5
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 1
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = r73[r82]
            float r7 = (float) r7
            float r7 = r7 / r78
            r3.append(r7)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7 = 2
            r4[r7] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r7 = 1
            r5 = r73[r7]
            float r5 = (float) r5
            float r5 = r5 / r78
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 3
            r4[r5] = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r5 = 2
            r7 = r73[r5]
            float r5 = (float) r7
            float r5 = r5 / r78
            r3.append(r5)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r5 = 4
            r4[r5] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r3 = "DL Mod TB1"
            r4[r82] = r3
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mLteMappingQam
            r5 = r44
            java.lang.String r3 = r0.getValueFromMapping(r5, r3)
            r7 = 1
            r4[r7] = r3
            r3 = r47[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r10 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r3, r10)
            r10 = 2
            r4[r10] = r3
            r3 = r47[r7]
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r3, r7)
            r7 = 3
            r4[r7] = r3
            r3 = r47[r10]
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r3, r7)
            r7 = 4
            r4[r7] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]
            java.lang.String r3 = "DL Mod TB2"
            r4[r82] = r3
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mLteMappingQam
            r10 = r48
            java.lang.String r3 = r0.getValueFromMapping(r10, r3)
            r7 = 1
            r4[r7] = r3
            r3 = r51[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r3, r7)
            r7 = 2
            r4[r7] = r3
            r3 = 1
            r7 = r51[r3]
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r7, r3)
            r7 = 3
            r4[r7] = r3
            r3 = 2
            r7 = r51[r3]
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r0.mLteMappingQam
            java.lang.String r3 = r0.getValueFromMapping(r7, r3)
            r7 = 4
            r4[r7] = r3
            r0.addHashMapKeyValues(r1, r2, r4)
            r3 = 5
            java.lang.String[] r3 = new java.lang.String[r3]
            java.lang.String r4 = "UL Mod"
            r3[r82] = r4
            java.util.HashMap<java.lang.Integer, java.lang.String> r4 = r0.mLteMappingQam
            r7 = r52
            java.lang.String r4 = r0.getValueFromMapping(r7, r4)
            r31 = 1
            r3[r31] = r4
            r4 = r55[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r4, r5)
            r5 = 2
            r3[r5] = r4
            r4 = r55[r31]
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r4, r5)
            r5 = 3
            r3[r5] = r4
            r4 = 2
            r4 = r55[r4]
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r4, r5)
            r5 = 4
            r3[r5] = r4
            r0.addHashMapKeyValues(r1, r2, r3)
            r4 = r1
            r90 = r6
            r31 = r13
            r25 = r17
            r41 = r18
            r10 = r21
            r39 = r27
            r33 = r28
            r11 = r29
            r26 = r79
            r6 = r87
            r1 = r89
            r7 = r92
            r18 = r9
            r13 = r12
            r15 = r14
            r9 = r20
            r12 = r23
            r14 = r44
            goto L_0x41eb
        L_0x33b6:
            r2 = r1
            r6 = r3
            r88 = r4
            r89 = r5
            r95 = r11
            r7 = r13
            r15 = r32
            r11 = r35
            r8 = r37
            r14 = r40
            r10 = r48
            r3 = r52
            r12 = r70
            r4 = r102[r82]
            r9 = r89
            r9.put(r4, r7)
            java.lang.String r1 = "errc_sts"
            int r1 = r0.getFieldValue(r6, r1)
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.ERRCStateMapping
            java.lang.String r5 = r0.getValueFromMapping(r1, r5)
            java.lang.String r7 = "ERRC State"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r7, (java.lang.Object) r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r95
            r7.append(r13)
            r7.append(r4)
            java.lang.String r13 = "]ERRC State : "
            r7.append(r13)
            r7.append(r1)
            java.lang.String r13 = ",state: "
            r7.append(r13)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.String r13 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r13, r7)
            r90 = r6
            r1 = r9
            r13 = r12
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r6 = r85
            r15 = r14
            r14 = r44
            goto L_0x41eb
        L_0x3420:
            r2 = r1
            r88 = r4
            r9 = r5
            r1 = r7
            r91 = r8
            r8 = r10
            r69 = r12
            r7 = r13
            r5 = r15
            r15 = r32
            r14 = r40
            r4 = r44
            r32 = r48
            r12 = r70
            r10 = r6
            r13 = r11
            r11 = r35
            r35 = r37
            r6 = r3
            r3 = r52
            r3 = r102[r82]
            r9.put(r3, r7)
            java.lang.String r4 = "cell_info[0].dl_bw"
            int r4 = r0.getFieldValue(r6, r4)
            r37 = r11
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            boolean r11 = r11.containsKey(r12)
            if (r11 == 0) goto L_0x3465
            java.util.HashMap<java.lang.Integer, java.lang.String> r11 = r0.mMappingBW
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Object r11 = r11.get(r12)
            goto L_0x3466
        L_0x3465:
            r11 = r10
        L_0x3466:
            java.lang.String r12 = "DL_BW"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r12, (java.lang.Object) r11)
            java.lang.String r11 = "dl_info[0]."
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r11)
            r17 = r4
            java.lang.String r4 = "rsrp"
            r12.append(r4)
            java.lang.String r4 = r12.toString()
            r12 = 1
            int r4 = r0.getFieldValue(r6, r4, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r4)
            r12.append(r10)
            java.lang.String r12 = r12.toString()
            r40 = r4
            r4 = r91
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r4, (java.lang.Object) r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r11)
            r48 = r14
            java.lang.String r14 = "rsrq"
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            r14 = 1
            int r12 = r0.getFieldValue(r6, r12, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r12)
            r14.append(r10)
            java.lang.String r14 = r14.toString()
            r76 = r12
            r12 = r69
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r12, (java.lang.Object) r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r11)
            r77 = r15
            java.lang.String r15 = "sinr"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r6, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r14)
            r15.append(r10)
            java.lang.String r15 = r15.toString()
            r69 = r14
            java.lang.String r14 = "SINR"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r14, (java.lang.Object) r15)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r11)
            java.lang.String r15 = "dl_rssi"
            r14.append(r15)
            java.lang.String r15 = "[0]"
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r15 = 1
            int r14 = r0.getFieldValue(r6, r14, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r11)
            r79 = r12
            java.lang.String r12 = "dl_rssi"
            r15.append(r12)
            java.lang.String r12 = "[1]"
            r15.append(r12)
            java.lang.String r12 = r15.toString()
            r15 = 1
            int r12 = r0.getFieldValue(r6, r12, r15)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r14)
            r85 = r14
            java.lang.String r14 = ","
            r15.append(r14)
            r15.append(r12)
            java.lang.String r14 = r15.toString()
            java.lang.String r15 = "RSSI"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r15, (java.lang.Object) r14)
            java.lang.String r14 = "cell_info[0].ant_port"
            int r14 = r0.getFieldValue(r6, r14)
            r15 = 0
            r86 = 0
            r100 = r86
            r86 = r12
            r12 = r100
        L_0x3559:
            r91 = r4
            r4 = 2
            if (r12 >= r4) goto L_0x3592
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r11)
            r93 = r1
            java.lang.String r1 = "dl_rssi"
            r4.append(r1)
            r4.append(r13)
            r4.append(r12)
            java.lang.String r1 = "]"
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r4 = 1
            int r1 = r0.getFieldValue(r6, r1, r4)
            r4 = 18
            if (r1 <= r4) goto L_0x3589
            r4 = -140(0xffffffffffffff74, float:NaN)
            if (r1 < r4) goto L_0x358b
        L_0x3589:
            int r15 = r15 + 1
        L_0x358b:
            int r12 = r12 + 1
            r4 = r91
            r1 = r93
            goto L_0x3559
        L_0x3592:
            r93 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            java.lang.String r4 = "x"
            r1.append(r4)
            r1.append(r15)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "Antenna"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r4, (java.lang.Object) r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r11)
            java.lang.String r4 = "ri"
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r4 = 1
            int r1 = r0.getFieldValue(r6, r1, r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            java.lang.String r12 = "RI"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r12, (java.lang.Object) r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r11)
            java.lang.String r12 = "tm"
            r4.append(r12)
            java.lang.String r4 = r4.toString()
            r12 = 1
            int r4 = r0.getFieldValue(r6, r4, r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "TM"
            r12.append(r13)
            r12.append(r4)
            java.lang.String r12 = r12.toString()
            java.lang.String r13 = "TM"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r13, (java.lang.Object) r12)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r11)
            java.lang.String r13 = "DL_Imcs"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r13 = 1
            int r12 = r0.getFieldValue(r6, r12, r13)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r10)
            r13.append(r12)
            java.lang.String r13 = r13.toString()
            r87 = r1
            java.lang.String r1 = "DL Imcs"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r1, (java.lang.Object) r13)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r11)
            java.lang.String r13 = "DL_Mod0"
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r6, r1)
            r13 = 255(0xff, float:3.57E-43)
            if (r1 != r13) goto L_0x364b
            r13 = r44
            goto L_0x364c
        L_0x364b:
            r13 = r1
        L_0x364c:
            r42 = r1
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            r89 = r4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            boolean r1 = r1.containsKey(r4)
            if (r1 == 0) goto L_0x3669
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            java.lang.Object r1 = r1.get(r4)
            goto L_0x366a
        L_0x3669:
            r1 = r10
        L_0x366a:
            java.lang.String r4 = "DL Mod TB1"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r4, (java.lang.Object) r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r11)
            java.lang.String r4 = "DL_Mod1"
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            int r1 = r0.getFieldValue(r6, r1)
            r4 = 255(0xff, float:3.57E-43)
            if (r1 != r4) goto L_0x368b
            r4 = r32
            goto L_0x368c
        L_0x368b:
            r4 = r1
        L_0x368c:
            r44 = r1
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            r90 = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r4)
            boolean r1 = r1.containsKey(r11)
            if (r1 == 0) goto L_0x36a9
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            java.lang.Integer r11 = java.lang.Integer.valueOf(r4)
            java.lang.Object r1 = r1.get(r11)
            goto L_0x36aa
        L_0x36a9:
            r1 = r10
        L_0x36aa:
            java.lang.String r11 = "DL Mod TB2"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r11, (java.lang.Object) r1)
            java.lang.String r1 = "ul_info[0].UL_Imcs"
            r11 = 1
            int r1 = r0.getFieldValue(r6, r1, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r10)
            r11.append(r1)
            java.lang.String r11 = r11.toString()
            r32 = r1
            java.lang.String r1 = "UL Imcs"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r1, (java.lang.Object) r11)
            java.lang.String r1 = "ul_info[0].UL_Mod"
            r11 = 1
            int r1 = r0.getFieldValue(r6, r1, r11)
            r11 = 255(0xff, float:3.57E-43)
            if (r1 != r11) goto L_0x36da
            int r11 = r0.oldUlMode
            goto L_0x36db
        L_0x36da:
            r11 = r1
        L_0x36db:
            r0.oldUlMode = r11
            r46 = r1
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            boolean r1 = r1.containsKey(r11)
            if (r1 == 0) goto L_0x36f9
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mMappingQam
            int r11 = r0.oldUlMode
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            java.lang.Object r1 = r1.get(r11)
            goto L_0x36fa
        L_0x36f9:
            r1 = r10
        L_0x36fa:
            java.lang.String r11 = "UL Mod"
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String) r11, (java.lang.Object) r1)
            r1 = 1
            r3 = r102[r1]
            r9.put(r3, r7)
            r0.resetHashMapKeyValues(r3, r2)
            java.lang.String r1 = "dl_cc_count"
            int r1 = r0.getFieldValue(r6, r1)
            java.lang.String r11 = "ul_cc_count"
            int r11 = r0.getFieldValue(r6, r11)
            r92 = r4
            java.lang.String r4 = "tas_version"
            int r4 = r0.getFieldValue(r6, r4)
            r0.TasVersion = r4
            if (r4 != 0) goto L_0x3722
            r4 = 1
            goto L_0x3723
        L_0x3722:
            r4 = 2
        L_0x3723:
            r0.TasVersion = r4
            java.lang.String r4 = "ul_info[0].tx_ant_type"
            r94 = r12
            r12 = 1
            int r4 = r0.getFieldValue(r6, r4, r12)
            r95 = r13
            java.lang.String r13 = "ul_info[0].rsrp_l_ant"
            int r13 = r0.getFieldValue(r6, r13, r12)
            r96 = r14
            java.lang.String r14 = "ul_info[0].rsrp_u_ant"
            int r14 = r0.getFieldValue(r6, r14, r12)
            r97 = r15
            java.lang.String r15 = "ul_info[0].tx_power"
            int r15 = r0.getFieldValue(r6, r15, r12)
            boolean r12 = com.mediatek.engineermode.FeatureSupport.is93ModemAndAbove()
            if (r12 == 0) goto L_0x3753
            java.lang.String r12 = "ul_info[0].tas_status"
            int r12 = r0.getFieldValue(r6, r12)
            goto L_0x3754
        L_0x3753:
            r12 = 2
        L_0x3754:
            if (r1 != 0) goto L_0x3779
            r98 = r5
            r5 = 1
            r0.TasVersion = r5
            r99 = r8
            r5 = 5
            java.lang.String[] r8 = new java.lang.String[r5]
            java.lang.String r5 = r0.tasEableMapping(r12)
            r8[r82] = r5
            r5 = 1
            r8[r5] = r10
            r19 = 2
            r8[r19] = r10
            r19 = 3
            r8[r19] = r10
            r19 = 4
            r8[r19] = r10
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String[]) r8)
            goto L_0x37e1
        L_0x3779:
            r98 = r5
            r99 = r8
            r5 = 1
            if (r1 != r5) goto L_0x37a5
            if (r11 != 0) goto L_0x37a5
            r8 = 5
            java.lang.String[] r5 = new java.lang.String[r8]
            java.lang.String r8 = r0.tasEableMapping(r12)
            r5[r82] = r8
            r8 = 1
            r5[r8] = r10
            r8 = 2
            r5[r8] = r10
            java.lang.String r8 = r0.rscpCheck(r13)
            r19 = 3
            r5[r19] = r8
            java.lang.String r8 = r0.rscpCheck(r14)
            r19 = 4
            r5[r19] = r8
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String[]) r5)
            goto L_0x37e1
        L_0x37a5:
            r5 = 1
            if (r1 < r5) goto L_0x37e1
            if (r11 < r5) goto L_0x37e1
            r8 = 5
            java.lang.String[] r5 = new java.lang.String[r8]
            java.lang.String r8 = r0.tasEableMapping(r12)
            r5[r82] = r8
            java.lang.String r8 = r0.antidxMapping(r4)
            r19 = 1
            r5[r19] = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r15)
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            r19 = 2
            r5[r19] = r8
            java.lang.String r8 = r0.rscpCheck(r13)
            r19 = 3
            r5[r19] = r8
            java.lang.String r8 = r0.rscpCheck(r14)
            r19 = 4
            r5[r19] = r8
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r2, (java.lang.String[]) r5)
        L_0x37e1:
            r5 = 6
            r3 = r102[r5]
            r9.put(r3, r7)
            r0.resetHashMapKeyValues(r3, r2)
            java.lang.String r5 = "cell_info[0]."
            java.lang.String r7 = "cell_info["
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r5)
            r83 = r1
            java.lang.String r1 = "dl_bw"
            r8.append(r1)
            java.lang.String r1 = r8.toString()
            int r1 = r0.getFieldValue(r6, r1)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r5)
            r19 = r4
            java.lang.String r4 = "ul_bw"
            r8.append(r4)
            java.lang.String r4 = r8.toString()
            int r4 = r0.getFieldValue(r6, r4)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r5)
            r90 = r11
            java.lang.String r11 = "tm"
            r8.append(r11)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r6, r8)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            r20 = r12
            java.lang.String r12 = "dlFreq"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            int r11 = r0.getFieldValue(r6, r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r5)
            r21 = r5
            java.lang.String r5 = "ulFreq"
            r12.append(r5)
            java.lang.String r5 = r12.toString()
            int r5 = r0.getFieldValue(r6, r5)
            java.lang.String r12 = "dl_info[0].DL_Mod0"
            int r12 = r0.getFieldValue(r6, r12)
            r42 = r13
            r13 = 255(0xff, float:3.57E-43)
            if (r12 != r13) goto L_0x3870
            r13 = r95
            goto L_0x3871
        L_0x3870:
            r13 = r12
        L_0x3871:
            r18 = r12
            java.lang.String r12 = "dl_info[0].DL_Mod1"
            int r12 = r0.getFieldValue(r6, r12)
            r44 = r14
            r14 = 255(0xff, float:3.57E-43)
            if (r12 != r14) goto L_0x3882
            r14 = r92
            goto L_0x3883
        L_0x3882:
            r14 = r12
        L_0x3883:
            r23 = r12
            java.lang.String r12 = "ul_info[0].UL_Mod"
            int r12 = r0.getFieldValue(r6, r12)
            r92 = r15
            r15 = 255(0xff, float:3.57E-43)
            if (r12 != r15) goto L_0x3894
            r15 = r52
            goto L_0x3895
        L_0x3894:
            r15 = r12
        L_0x3895:
            r24 = 0
            r35 = r12
            r12 = r24
        L_0x389b:
            r95 = r9
            r9 = 3
            if (r12 >= r9) goto L_0x39d2
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r24 = r15
            int r15 = r12 + 1
            r9.append(r15)
            r15 = r99
            r9.append(r15)
            r37 = r14
            java.lang.String r14 = "dl_bw"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r43[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            int r14 = r12 + 1
            r9.append(r14)
            r9.append(r15)
            java.lang.String r14 = "ul_bw"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r34[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            int r14 = r12 + 1
            r9.append(r14)
            r9.append(r15)
            java.lang.String r14 = "tm"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r30[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            int r14 = r12 + 1
            r9.append(r14)
            java.lang.String r14 = "].dlFreq"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r36[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            int r14 = r12 + 1
            r9.append(r14)
            java.lang.String r14 = "].ulFreq"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r38[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "dl_info["
            r9.append(r14)
            int r14 = r12 + 1
            r9.append(r14)
            java.lang.String r14 = "].DL_Mod0"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r45[r12] = r9
            r9 = r45[r12]
            r14 = 255(0xff, float:3.57E-43)
            if (r9 != r14) goto L_0x3962
            r9 = r47[r12]
            goto L_0x3964
        L_0x3962:
            r9 = r45[r12]
        L_0x3964:
            r47[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "dl_info["
            r9.append(r14)
            int r14 = r12 + 1
            r9.append(r14)
            java.lang.String r14 = "].DL_Mod1"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r49[r12] = r9
            r9 = r49[r12]
            r14 = 255(0xff, float:3.57E-43)
            if (r9 != r14) goto L_0x398d
            r9 = r51[r12]
            goto L_0x398f
        L_0x398d:
            r9 = r49[r12]
        L_0x398f:
            r51[r12] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "ul_info["
            r9.append(r14)
            int r14 = r12 + 1
            r9.append(r14)
            java.lang.String r14 = "].UL_Mod"
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            r53[r12] = r9
            r9 = r53[r12]
            r14 = 255(0xff, float:3.57E-43)
            if (r9 != r14) goto L_0x39b8
            r9 = r55[r12]
            goto L_0x39ba
        L_0x39b8:
            r9 = r53[r12]
        L_0x39ba:
            r55[r12] = r9
            r9 = r71[r12]
            if (r9 != 0) goto L_0x39c6
            r47[r12] = r82
            r51[r12] = r82
            r55[r12] = r82
        L_0x39c6:
            int r12 = r12 + 1
            r99 = r15
            r15 = r24
            r14 = r37
            r9 = r95
            goto L_0x389b
        L_0x39d2:
            r37 = r14
            r24 = r15
            r15 = r99
            r9 = 0
        L_0x39d9:
            r12 = 4
            if (r9 >= r12) goto L_0x3b43
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "dl_info["
            r12.append(r14)
            r12.append(r9)
            r12.append(r15)
            java.lang.String r12 = r12.toString()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r99 = r7
            java.lang.String r7 = "ul_info["
            r14.append(r7)
            r14.append(r9)
            r14.append(r15)
            java.lang.String r7 = r14.toString()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r48 = r13
            java.lang.String r13 = "cell_info["
            r14.append(r13)
            r14.append(r9)
            r14.append(r15)
            java.lang.String r13 = r14.toString()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r13)
            r50 = r13
            java.lang.String r13 = "ant_port"
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r56[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "DL_Tput"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r6, r13)
            r57[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            java.lang.String r14 = "UL_Tput"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r6, r13)
            r58[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "DL_Imcs"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r59[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            java.lang.String r14 = "UL_Imcs"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r60[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "DL_bler"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r61[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            java.lang.String r14 = "UL_bler"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r62[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "DL_rb"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r63[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r7)
            java.lang.String r14 = "UL_rb"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r64[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "cqi_cw0"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r65[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "cqi_cw1"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r14 = 1
            int r13 = r0.getFieldValue(r6, r13, r14)
            r66[r9] = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            java.lang.String r14 = "ri"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            int r13 = r0.getFieldValue(r6, r13)
            r68[r9] = r13
            int r9 = r9 + 1
            r13 = r48
            r7 = r99
            goto L_0x39d9
        L_0x3b43:
            r99 = r7
            r48 = r13
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "Band"
            r9[r82] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r12 = r29
            r7.append(r12)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 1
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r71[r82]
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 2
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r71[r13]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 3
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r71[r14]
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "DL BW(MHz)"
            r9[r82] = r7
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r1, r7)
            r13 = 1
            r9[r13] = r7
            r7 = r43[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r14 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r14)
            r14 = 2
            r9[r14] = r7
            r7 = r43[r13]
            java.util.HashMap<java.lang.Integer, java.lang.String> r13 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r13)
            r13 = 3
            r9[r13] = r7
            r7 = r43[r14]
            java.util.HashMap<java.lang.Integer, java.lang.String> r13 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r13)
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "UL BW(MHz)"
            r9[r82] = r7
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r4, r7)
            r13 = 1
            r9[r13] = r7
            r7 = r34[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r14 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r14)
            r14 = 2
            r9[r14] = r7
            r7 = r34[r13]
            java.util.HashMap<java.lang.Integer, java.lang.String> r13 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r13)
            r13 = 3
            r9[r13] = r7
            r7 = r34[r14]
            java.util.HashMap<java.lang.Integer, java.lang.String> r13 = r0.mMappingBW
            java.lang.String r7 = r0.getValueFromMapping(r7, r13)
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "TM"
            r9[r82] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r8)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 1
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r30[r82]
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 2
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r30[r13]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 3
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r30[r14]
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            r9[r82] = r98
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r31
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 1
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r75[r82]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r15 = 2
            r9[r15] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r75[r14]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 3
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = 2
            r15 = r75[r14]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 4
            r9[r14] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            r9[r82] = r93
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r27
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r27 = 1
            r9[r27] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r12 = r81[r82]
            r7.append(r12)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r12 = 2
            r9[r12] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r12 = r81[r27]
            r7.append(r12)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r12 = 3
            r9[r12] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r27 = r14
            r12 = 2
            r13 = r81[r12]
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r12 = 4
            r9[r12] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "RS_SNR"
            r9[r82] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r12 = r33
            float r13 = (float) r12
            float r13 = r13 / r78
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 1
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r72[r82]
            float r13 = (float) r13
            float r13 = r13 / r78
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 2
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = 1
            r14 = r72[r13]
            float r13 = (float) r14
            float r13 = r13 / r78
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 3
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = 2
            r14 = r72[r13]
            float r13 = (float) r14
            float r13 = r13 / r78
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "DL Freq"
            r9[r82] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r11)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 1
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r36[r82]
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 2
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r36[r13]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 3
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r36[r14]
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            java.lang.String r7 = "UL Freq"
            r9[r82] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r5)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 1
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r38[r82]
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 2
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r38[r13]
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 3
            r9[r13] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r38[r14]
            r7.append(r13)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r13 = 4
            r9[r13] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            r9[r82] = r91
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r13 = r39
            float r14 = (float) r13
            float r14 = r14 / r78
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 1
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r74[r82]
            float r14 = (float) r14
            float r14 = r14 / r78
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 2
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = 1
            r15 = r74[r14]
            float r14 = (float) r15
            float r14 = r14 / r78
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 3
            r9[r14] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = 2
            r15 = r74[r14]
            float r14 = (float) r15
            float r14 = r14 / r78
            r7.append(r14)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r14 = 4
            r9[r14] = r7
            r0.addHashMapKeyValues(r3, r2, r9)
            r7 = 5
            java.lang.String[] r9 = new java.lang.String[r7]
            r9[r82] = r79
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r14 = r41
            float r15 = (float) r14
            float r15 = r15 / r78
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r15 = 1
            r9[r15] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r15 = r73[r82]
            float r15 = (float) r15
            float r15 = r15 / r78
            r7.append(r15)
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r15 = 2
            r9[r15] = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r33 = r1
            r15 = 1
            r1 = r73[r15]
            float r1 = (float) r1
            float r1 = r1 / r78
            r7.append(r1)
            r7.append(r10)
            java.lang.String r1 = r7.toString()
            r7 = 3
            r9[r7] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r7 = 2
            r15 = r73[r7]
            float r7 = (float) r15
            float r7 = r7 / r78
            r1.append(r7)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r7 = 4
            r9[r7] = r1
            r0.addHashMapKeyValues(r3, r2, r9)
            r1 = 5
            java.lang.String[] r7 = new java.lang.String[r1]
            java.lang.String r1 = "DL Mod TB1"
            r7[r82] = r1
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mLteMappingQam
            r9 = r48
            java.lang.String r1 = r0.getValueFromMapping(r9, r1)
            r10 = 1
            r7[r10] = r1
            r1 = r47[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r15 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r1, r15)
            r15 = 2
            r7[r15] = r1
            r1 = r47[r10]
            java.util.HashMap<java.lang.Integer, java.lang.String> r10 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r1, r10)
            r10 = 3
            r7[r10] = r1
            r1 = r47[r15]
            java.util.HashMap<java.lang.Integer, java.lang.String> r10 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r1, r10)
            r10 = 4
            r7[r10] = r1
            r0.addHashMapKeyValues(r3, r2, r7)
            r1 = 5
            java.lang.String[] r7 = new java.lang.String[r1]
            java.lang.String r1 = "DL Mod TB2"
            r7[r82] = r1
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mLteMappingQam
            r10 = r37
            java.lang.String r1 = r0.getValueFromMapping(r10, r1)
            r15 = 1
            r7[r15] = r1
            r1 = r51[r82]
            java.util.HashMap<java.lang.Integer, java.lang.String> r15 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r1, r15)
            r15 = 2
            r7[r15] = r1
            r1 = 1
            r15 = r51[r1]
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r15, r1)
            r15 = 3
            r7[r15] = r1
            r1 = 2
            r15 = r51[r1]
            java.util.HashMap<java.lang.Integer, java.lang.String> r1 = r0.mLteMappingQam
            java.lang.String r1 = r0.getValueFromMapping(r15, r1)
            r15 = 4
            r7[r15] = r1
            r0.addHashMapKeyValues(r3, r2, r7)
            r1 = 5
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r7 = "UL Mod"
            r1[r82] = r7
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            r15 = r24
            java.lang.String r7 = r0.getValueFromMapping(r15, r7)
            r24 = 1
            r1[r24] = r7
            r7 = r55[r82]
            r39 = r4
            java.util.HashMap<java.lang.Integer, java.lang.String> r4 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r7, r4)
            r7 = 2
            r1[r7] = r4
            r4 = r55[r24]
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r4, r7)
            r7 = 3
            r1[r7] = r4
            r4 = 2
            r4 = r55[r4]
            java.util.HashMap<java.lang.Integer, java.lang.String> r7 = r0.mLteMappingQam
            java.lang.String r4 = r0.getValueFromMapping(r4, r7)
            r7 = 4
            r1[r7] = r4
            r0.addHashMapKeyValues(r3, r2, r1)
            r4 = r3
            r37 = r5
            r90 = r6
            r32 = r8
            r48 = r10
            r52 = r15
            r24 = r20
            r6 = r21
            r46 = r23
            r7 = r27
            r50 = r35
            r15 = r39
            r10 = r44
            r1 = r95
            r14 = r9
            r35 = r11
            r39 = r13
            r11 = r29
            r13 = r33
            r9 = r42
            r33 = r12
            r42 = r18
            r12 = r92
            r18 = r99
            goto L_0x41eb
        L_0x3fd4:
            r2 = r1
            r88 = r4
            r95 = r5
            r10 = r6
            r93 = r7
            r7 = r13
            r98 = r15
            r77 = r32
            r12 = r33
            r13 = r39
            r14 = r41
            r32 = r48
            r6 = r3
            r48 = r40
            r100 = r37
            r37 = r35
            r35 = r100
            r4 = r102[r82]
            r1 = r95
            r1.put(r4, r7)
            java.lang.String r3 = "serv_inf.dl_earfcn"
            int r3 = r0.getFieldValue(r6, r3)
            java.lang.String r5 = "serv_inf.pci"
            int r5 = r0.getFieldValue(r6, r5)
            java.lang.String r7 = "serv_inf.band"
            int r7 = r0.getFieldValue(r6, r7)
            java.lang.String r8 = "serv_inf.cell_id"
            int r8 = r0.getFieldValue(r6, r8)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            java.lang.String r11 = "Cell ID"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r11, (java.lang.Object) r9)
            r9 = -1
            if (r3 != r9) goto L_0x401f
            r11 = r10
            goto L_0x4023
        L_0x401f:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)
        L_0x4023:
            r15 = r93
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r15, (java.lang.Object) r11)
            if (r3 != r9) goto L_0x402c
            r9 = r10
            goto L_0x4030
        L_0x402c:
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
        L_0x4030:
            r11 = r98
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r11, (java.lang.Object) r9)
            r9 = 65535(0xffff, float:9.1834E-41)
            if (r7 != r9) goto L_0x403c
            r9 = r10
            goto L_0x404d
        L_0x403c:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Band"
            r9.append(r10)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
        L_0x404d:
            java.lang.String r10 = "Band"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r10, (java.lang.Object) r9)
            r90 = r6
            r33 = r12
            r39 = r13
            r41 = r14
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r14 = r44
            r15 = r48
            r13 = r70
            r6 = r85
            r48 = r32
            r32 = r77
            r100 = r37
            r37 = r35
            r35 = r100
            goto L_0x41eb
        L_0x4078:
            r2 = r1
            r6 = r3
            r88 = r4
            r1 = r5
            r77 = r32
            r12 = r33
            r13 = r39
            r14 = r41
            r32 = r48
            r48 = r40
            r100 = r37
            r37 = r35
            r35 = r100
            r90 = r6
            goto L_0x41c9
        L_0x4093:
            r2 = r1
            r88 = r4
            r1 = r5
            r10 = r6
            r7 = r13
            r77 = r32
            r12 = r33
            r13 = r39
            r14 = r41
            r32 = r48
            r11 = r86
            r5 = r87
            r6 = r3
            r3 = r35
            r35 = r37
            r48 = r40
            r4 = r102[r82]
            r1.put(r4, r7)
            java.lang.String r7 = "selectedPlmn."
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            r8.append(r5)
            r9 = 1
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r6, r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r5)
            r15 = 2
            r9.append(r15)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r6, r9)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r5)
            r5 = 3
            r15.append(r5)
            java.lang.String r5 = r15.toString()
            int r5 = r0.getFieldValue(r6, r5)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r11)
            r37 = r3
            r3 = 1
            r15.append(r3)
            java.lang.String r3 = r15.toString()
            int r3 = r0.getFieldValue(r6, r3)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r11)
            r12 = 2
            r15.append(r12)
            java.lang.String r12 = r15.toString()
            int r12 = r0.getFieldValue(r6, r12)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r7)
            r15.append(r11)
            r11 = 3
            r15.append(r11)
            java.lang.String r11 = r15.toString()
            int r11 = r0.getFieldValue(r6, r11)
            java.lang.String r15 = "tac"
            int r15 = r0.getFieldValue(r6, r15)
            r90 = r6
            r6 = 15
            if (r8 != r6) goto L_0x4161
            if (r9 != r6) goto L_0x4161
            if (r5 != r6) goto L_0x4161
            if (r3 != r6) goto L_0x4161
            if (r12 != r6) goto L_0x4161
            if (r11 != r6) goto L_0x4161
            java.lang.String r6 = "PLMN"
            java.lang.String r10 = "-"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r10)
            r17 = r3
            goto L_0x4190
        L_0x4161:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r10)
            r6.append(r8)
            r6.append(r9)
            r6.append(r5)
            r6.append(r3)
            r6.append(r12)
            r17 = r3
            r3 = 15
            if (r11 != r3) goto L_0x4180
            r3 = r10
            goto L_0x4184
        L_0x4180:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)
        L_0x4184:
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            java.lang.String r6 = "PLMN"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r3)
        L_0x4190:
            r3 = 65534(0xfffe, float:9.1833E-41)
            if (r15 == r3) goto L_0x41a2
            if (r15 != 0) goto L_0x4198
            goto L_0x41a2
        L_0x4198:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r15)
            java.lang.String r6 = "TAC"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r6, (java.lang.Object) r3)
            goto L_0x41a9
        L_0x41a2:
            java.lang.String r3 = "TAC"
            java.lang.String r6 = "-"
            r0.setHashMapKeyValues((java.lang.String) r4, (int) r2, (java.lang.String) r3, (java.lang.Object) r6)
        L_0x41a9:
            r6 = r7
            r39 = r13
            r41 = r14
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r14 = r44
            r15 = r48
            r13 = r70
            r48 = r32
            r32 = r77
            r100 = r37
            r37 = r35
            r35 = r100
            goto L_0x41eb
        L_0x41c9:
            r39 = r13
            r41 = r14
            r4 = r17
            r9 = r20
            r10 = r21
            r12 = r23
            r7 = r27
            r11 = r29
            r14 = r44
            r15 = r48
            r13 = r70
            r6 = r85
            r48 = r32
            r32 = r77
            r100 = r37
            r37 = r35
            r35 = r100
        L_0x41eb:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.getUpdateMap(java.lang.String[], int, java.lang.String, java.lang.Object):java.util.HashMap");
    }

    /* compiled from: MDMComponentFT */
    private class UpdateViewTask extends AsyncTask<CombinationViewComponent.Task, Object[], Void> {
        private UpdateViewTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (FTNetworkInfo.this.Labels == null) {
                FTNetworkInfo fTNetworkInfo = FTNetworkInfo.this;
                String[] unused = fTNetworkInfo.Labels = fTNetworkInfo.initLabels();
            }
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0108, code lost:
            if (r8.contains(r15.split("-")[0]) != false) goto L_0x0124;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(com.mediatek.engineermode.mdmcomponent.CombinationViewComponent.Task... r22) {
            /*
                r21 = this;
                r0 = r21
                r1 = 0
                java.lang.Boolean r2 = java.lang.Boolean.valueOf(r1)
                r3 = r22[r1]
                java.lang.String r3 = r3.getExtraName()
                r4 = r22[r1]
                java.lang.Object r4 = r4.getExtraMsg()
                r5 = r22[r1]
                int r5 = r5.getExtraSimID()
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r6 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                java.lang.String[] r7 = r6.Labels
                java.util.HashMap r6 = r6.getUpdateMap(r7, r5, r3, r4)
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r7 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                java.lang.String[] r7 = r7.getSurpportedLabel(r5)
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r8 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                java.util.Set r8 = r8.getSupportedLabelsByKeyWord(r5)
                java.util.HashSet r9 = new java.util.HashSet
                r9.<init>()
                r10 = 0
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r11 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                java.lang.String[] r11 = r11.Labels
                int r12 = r11.length
                r13 = r1
            L_0x003d:
                java.lang.String r14 = "EmInfo/MDMComponent"
                java.lang.String r1 = "Unknown"
                r17 = 2
                if (r13 >= r12) goto L_0x0186
                r15 = r11[r13]
                r19 = r3
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r3 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                com.mediatek.engineermode.mdmcomponent.CombinationViewComponent$UpdateTaskDriven r3 = r3.mUpdateTaskDriven
                boolean r3 = r3.isDriverDead()
                if (r3 == 0) goto L_0x0063
                r1 = 1
                r0.cancel(r1)
                java.lang.String r1 = "Exit doInBackground, Driver dead"
                com.mediatek.engineermode.Elog.e(r14, r1)
                com.mediatek.engineermode.mdmcomponent.FTNetworkInfo r1 = com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.this
                r1.taskDone()
                r1 = 0
                return r1
            L_0x0063:
                r3 = 0
                r14 = r7[r3]
                boolean r3 = r14.equals(r1)
                java.lang.String r14 = "-"
                r20 = r4
                if (r3 == 0) goto L_0x00e1
                r3 = 1
                r4 = r7[r3]
                boolean r1 = r4.equals(r1)
                if (r1 == 0) goto L_0x00de
                java.util.Set r1 = r6.keySet()
                boolean r1 = r1.contains(r15)
                if (r1 == 0) goto L_0x00c2
                boolean r1 = r8.contains(r15)
                if (r1 != 0) goto L_0x009c
                java.lang.String[] r1 = r15.split(r14)
                r3 = 0
                r1 = r1[r3]
                boolean r1 = r8.contains(r1)
                if (r1 == 0) goto L_0x0097
                goto L_0x009c
            L_0x0097:
                r1 = 1
                r14 = 3
                r16 = 0
                goto L_0x00c6
            L_0x009c:
                r1 = 1
                java.lang.Object[][] r3 = new java.lang.Object[r1][]
                r4 = 4
                java.lang.Object[] r4 = new java.lang.Object[r4]
                java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
                r16 = 0
                r4[r16] = r14
                r4[r1] = r15
                java.lang.Boolean r14 = java.lang.Boolean.valueOf(r1)
                r4[r17] = r14
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                r14 = 3
                r4[r14] = r1
                r3[r16] = r4
                r0.publishProgress(r3)
                r1 = 1
                r10 = r1
                goto L_0x017d
            L_0x00c2:
                r1 = 1
                r14 = 3
                r16 = 0
            L_0x00c6:
                java.lang.Object[][] r3 = new java.lang.Object[r1][]
                java.lang.Object[] r4 = new java.lang.Object[r14]
                java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
                r4[r16] = r14
                r4[r1] = r15
                r4[r17] = r2
                r3[r16] = r4
                r0.publishProgress(r3)
                r9.add(r15)
                goto L_0x017d
            L_0x00de:
                r16 = 0
                goto L_0x00e3
            L_0x00e1:
                r16 = 0
            L_0x00e3:
                r1 = r7[r16]
                int r1 = r15.indexOf(r1)
                if (r1 >= 0) goto L_0x00f7
                r1 = 1
                r3 = r7[r1]
                int r1 = r15.indexOf(r3)
                if (r1 < 0) goto L_0x00f5
                goto L_0x00f7
            L_0x00f5:
                r3 = 0
                goto L_0x010b
            L_0x00f7:
                boolean r1 = r8.contains(r15)
                if (r1 != 0) goto L_0x0124
                java.lang.String[] r1 = r15.split(r14)
                r3 = 0
                r1 = r1[r3]
                boolean r1 = r8.contains(r1)
                if (r1 == 0) goto L_0x010b
                goto L_0x0124
            L_0x010b:
                r1 = 1
                java.lang.Object[][] r4 = new java.lang.Object[r1][]
                r14 = 3
                java.lang.Object[] r14 = new java.lang.Object[r14]
                java.lang.Integer r16 = java.lang.Integer.valueOf(r5)
                r14[r3] = r16
                r14[r1] = r15
                r14[r17] = r2
                r4[r3] = r14
                r0.publishProgress(r4)
                r9.add(r15)
                goto L_0x017d
            L_0x0124:
                r1 = 1
                java.util.Set r3 = r6.keySet()
                boolean r3 = r3.contains(r15)
                if (r3 == 0) goto L_0x015e
                java.lang.Object r3 = r6.get(r15)
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                if (r3 == 0) goto L_0x015e
                r3 = 1
                java.lang.Object[][] r4 = new java.lang.Object[r3][]
                r10 = 4
                java.lang.Object[] r10 = new java.lang.Object[r10]
                java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
                r16 = 0
                r10[r16] = r14
                r10[r3] = r15
                java.lang.Boolean r14 = java.lang.Boolean.valueOf(r3)
                r10[r17] = r14
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
                r14 = 3
                r10[r14] = r3
                r4[r16] = r10
                r0.publishProgress(r4)
                goto L_0x017c
            L_0x015e:
                r3 = 1
                r16 = 0
                java.lang.Object[][] r4 = new java.lang.Object[r3][]
                r10 = 4
                java.lang.Object[] r10 = new java.lang.Object[r10]
                java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
                r10[r16] = r14
                r10[r3] = r15
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
                r10[r17] = r3
                r3 = 3
                r10[r3] = r2
                r4[r16] = r10
                r0.publishProgress(r4)
            L_0x017c:
                r10 = r1
            L_0x017d:
                int r13 = r13 + 1
                r3 = r19
                r4 = r20
                r1 = 0
                goto L_0x003d
            L_0x0186:
                r19 = r3
                r20 = r4
                if (r10 != 0) goto L_0x01fd
                java.util.Iterator r3 = r9.iterator()
            L_0x0190:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L_0x01fd
                java.lang.Object r4 = r3.next()
                java.lang.String r4 = (java.lang.String) r4
                r11 = 0
                r12 = r7[r11]
                int r12 = r4.indexOf(r12)
                if (r12 >= 0) goto L_0x01c9
                r12 = 1
                r13 = r7[r12]
                int r13 = r4.indexOf(r13)
                if (r13 >= 0) goto L_0x01c9
                r13 = r7[r11]
                boolean r11 = r13.equals(r1)
                if (r11 == 0) goto L_0x01c4
                r11 = r7[r12]
                boolean r11 = r11.equals(r1)
                if (r11 == 0) goto L_0x01bf
                goto L_0x01c9
            L_0x01bf:
                r11 = 1
                r13 = 3
                r16 = 0
                goto L_0x01fc
            L_0x01c4:
                r11 = 1
                r13 = 3
                r16 = 0
                goto L_0x01fc
            L_0x01c9:
                r11 = 1
                java.lang.Object[][] r12 = new java.lang.Object[r11][]
                r13 = 3
                java.lang.Object[] r15 = new java.lang.Object[r13]
                java.lang.Integer r18 = java.lang.Integer.valueOf(r5)
                r16 = 0
                r15[r16] = r18
                r15[r11] = r4
                r15[r17] = r2
                r12[r16] = r15
                r0.publishProgress(r12)
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                r12.<init>()
                java.lang.String r15 = "[showView] Sim"
                r12.append(r15)
                r12.append(r5)
                java.lang.String r15 = " has no valid labels, show label:"
                r12.append(r15)
                r12.append(r4)
                java.lang.String r12 = r12.toString()
                com.mediatek.engineermode.Elog.d(r14, r12)
            L_0x01fc:
                goto L_0x0190
            L_0x01fd:
                r1 = 0
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.FTNetworkInfo.UpdateViewTask.doInBackground(com.mediatek.engineermode.mdmcomponent.CombinationViewComponent$Task[]):java.lang.Void");
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Object[]... values) {
            super.onProgressUpdate(values);
            if (FTNetworkInfo.this.mUpdateTaskDriven.isDriverDead()) {
                cancel(true);
                FTNetworkInfo.this.taskDone();
                return;
            }
            int simID = Integer.valueOf(values[0][0].toString()).intValue();
            String mLabel = values[0][1].toString();
            boolean updateViewFlag = values[0][2].booleanValue();
            if (updateViewFlag && values[0][3].booleanValue()) {
                FTNetworkInfo.this.clearData(mLabel, simID);
                FTNetworkInfo.this.addData(mLabel, simID);
            }
            FTNetworkInfo.this.displayView(mLabel, simID, updateViewFlag);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            FTNetworkInfo.this.taskDone();
        }
    }

    /* access modifiers changed from: private */
    public Set<String> getSupportedLabelsByKeyWord(int simID) {
        String[] keyWords;
        Set<String> validLabels = new HashSet<>();
        if (isValidSimID(simID)) {
            for (String mLabel : initLabels()) {
                boolean isValid = true;
                if (this.mKeyWordMapping.containsKey(mLabel) || this.mKeyWordMapping.containsKey(mLabel.split("-")[0])) {
                    if (this.mKeyWordMapping.get(mLabel) != null) {
                        keyWords = this.mKeyWordMapping.get(mLabel);
                    } else {
                        keyWords = this.mKeyWordMapping.get(mLabel.split("-")[0]);
                    }
                    if (!isLabelArrayType(mLabel)) {
                        int length = keyWords.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            String key = keyWords[i];
                            if (((LinkedHashMap) ((LinkedHashMap) this.hmapLabelsList.get(simID)).get(mLabel)).get(key) == null || ((LinkedHashMap) ((LinkedHashMap) this.hmapLabelsList.get(simID)).get(mLabel)).get(key).equals("") || ((LinkedHashMap) ((LinkedHashMap) this.hmapLabelsList.get(simID)).get(mLabel)).get(key).equals("0")) {
                                isValid = false;
                            } else {
                                i++;
                            }
                        }
                        isValid = false;
                    } else if (((LinkedHashMap) ((LinkedHashMap) this.hmapLabelsList.get(simID)).get(mLabel)).size() <= 1) {
                    }
                }
                if (isValid) {
                    validLabels.add(this.mKeyWordMapping.containsKey(mLabel.split("-")[0]) ? mLabel.split("-")[0] : mLabel);
                }
            }
        }
        return validLabels;
    }

    /* access modifiers changed from: private */
    public String[] getSurpportedLabel(int simID) {
        int mask;
        int[] networkType = getNetworkType(simID);
        String[] rtString = new String[networkType.length];
        int i = 0;
        while (true) {
            String rt3GString = "UMTS FDD";
            if (i < networkType.length) {
                switch (networkType[i]) {
                    case 0:
                        rtString[i] = "Unknow";
                        break;
                    case 1:
                        rtString[i] = "GSM";
                        break;
                    case 2:
                        rtString[i] = "GSM";
                        break;
                    case 3:
                        rtString[i] = "UMTS";
                        break;
                    case 4:
                        rtString[i] = "1xRTT";
                        break;
                    case 5:
                        rtString[i] = "EVDO";
                        break;
                    case 6:
                        rtString[i] = "EVDO";
                        break;
                    case 7:
                        rtString[i] = "1xRTT";
                        break;
                    case 8:
                        rtString[i] = rt3GString;
                        break;
                    case 9:
                        rtString[i] = rt3GString;
                        break;
                    case 10:
                        rtString[i] = rt3GString;
                        break;
                    case 12:
                        rtString[i] = "EVDO";
                        break;
                    case 13:
                        rtString[i] = "LTE";
                        break;
                    case 14:
                        rtString[i] = "EVDO";
                        break;
                    case 15:
                        rtString[i] = rt3GString;
                        break;
                    case 16:
                        rtString[i] = "GSM";
                        break;
                    case 17:
                        rtString[i] = rt3GString;
                        break;
                    case 19:
                        rtString[i] = "LTE";
                        break;
                    default:
                        rtString[i] = this.networkInfo[i];
                        Elog.e("EmInfo/MDMComponent", "Unexpected radioTechnology");
                        break;
                }
                i++;
            } else {
                if ((rtString[0].indexOf("UMTS") >= 0 || rtString[1].indexOf("UMTS") >= 0) && (mask = WorldModeUtil.get3GDivisionDuplexMode()) != 0) {
                    if (mask != 1) {
                        rt3GString = "UMTS TDD";
                    }
                    if (ModemCategory.getCapabilitySim() != simID || mask == 1) {
                        rt3GString = "UMTS FDD";
                    }
                    rtString[0] = rtString[0].indexOf("UMTS") >= 0 ? rt3GString : rtString[0];
                    rtString[1] = rtString[1].indexOf("UMTS") >= 0 ? rt3GString : rtString[1];
                }
                return rtString;
            }
        }
    }

    private int[] getNetworkType(int simID) {
        Activity activity = this.mActivity;
        Activity activity2 = this.mActivity;
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService("phone");
        if (telephonyManager == null) {
            Elog.e("EmInfo/MDMComponent", "[getNetworkType] telephonyManager == null");
            return new int[]{0, 0};
        }
        int dataNetworkType = telephonyManager.getDataNetworkType(simID);
        int voiceNetworkType = telephonyManager.getVoiceNetworkType(simID);
        Elog.d("EmInfo/MDMComponent", "[getNetworkType] SIM" + simID + ", getDataNetworkType " + dataNetworkType + ", getVoiceNetworkType " + voiceNetworkType);
        return new int[]{dataNetworkType, voiceNetworkType};
    }

    /* access modifiers changed from: package-private */
    public boolean isLabelArrayType(String label) {
        if (getArrayTypeLabels().contains(label.indexOf(".") > 0 ? label.substring(0, label.indexOf(".")) : label)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        Elog.d("EmInfo/MDMComponent", "[clearData]");
        unRegisteListener();
        if (this.layout != null && this.layout.getChildCount() > 0) {
            this.layout.removeAllViews();
            this.scrollView.removeAllViews();
        }
        this.commonView.setAdapter((ListAdapter) null);
        this.commonInfoAdapter.clear();
        if (this.mUpdateTaskDriven.isTaskRunning()) {
            taskStop();
            return;
        }
        resetPublicValues();
        clearViewData(-1);
    }
}
