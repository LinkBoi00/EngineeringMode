package com.mediatek.engineermode;

import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.os.EnvironmentCompat;
import com.mediatek.engineermode.hqanfc.NfcCommand;

public class WorldModeUtil {
    public static final String ACTION_WORLD_MODE_CHANGED = "mediatek.intent.action.ACTION_WORLD_MODE_CHANGED";
    private static final int ACTIVE_MD_TYPE_LTG = 4;
    private static final int ACTIVE_MD_TYPE_LWCG = 5;
    private static final int ACTIVE_MD_TYPE_LWG = 3;
    private static final int ACTIVE_MD_TYPE_LfWG = 7;
    private static final int ACTIVE_MD_TYPE_LtTG = 6;
    private static final int ACTIVE_MD_TYPE_TG = 2;
    private static final int ACTIVE_MD_TYPE_UNKNOWN = 0;
    private static final int ACTIVE_MD_TYPE_WG = 1;
    private static final String CDMA = "C";
    public static final String EXTRA_WORLD_MODE_CHANGE_STATE = "worldModeState";
    private static final int MASK_CDMA = 32;
    private static final int MASK_GSM = 1;
    private static final int MASK_LTEFDD = 16;
    private static final int MASK_LTETDD = 8;
    private static final int MASK_NR = 64;
    private static final int MASK_TDSCDMA = 2;
    private static final int MASK_WCDMA = 4;
    private static final int MD_TYPE_FDD = 100;
    private static final int MD_TYPE_LTG = 6;
    private static final int MD_TYPE_LWG = 5;
    private static final int MD_TYPE_TDD = 101;
    private static final int MD_TYPE_TG = 4;
    private static final int MD_TYPE_UNKNOWN = 0;
    private static final int MD_TYPE_WG = 3;
    public static final int MD_WM_CHANGED_END = 1;
    public static final int MD_WM_CHANGED_START = 0;
    public static final int MD_WM_CHANGED_UNKNOWN = -1;
    private static final int MD_WORLD_MODE_LCTG = 16;
    private static final int MD_WORLD_MODE_LFCTG = 21;
    private static final int MD_WORLD_MODE_LFTG = 20;
    private static final int MD_WORLD_MODE_LFWCG = 15;
    private static final int MD_WORLD_MODE_LFWG = 14;
    private static final int MD_WORLD_MODE_LTCTG = 17;
    private static final int MD_WORLD_MODE_LTG = 8;
    private static final int MD_WORLD_MODE_LTTG = 13;
    private static final int MD_WORLD_MODE_LTWCG = 19;
    private static final int MD_WORLD_MODE_LTWG = 18;
    private static final int MD_WORLD_MODE_LWCG = 11;
    private static final int MD_WORLD_MODE_LWCTG = 12;
    private static final int MD_WORLD_MODE_LWG = 9;
    private static final int MD_WORLD_MODE_LWTG = 10;
    private static final int MD_WORLD_MODE_NLTCTG = 26;
    private static final int MD_WORLD_MODE_NLWCG = 25;
    private static final int MD_WORLD_MODE_NLWCTG = 24;
    private static final int MD_WORLD_MODE_NLWG = 22;
    private static final int MD_WORLD_MODE_NLWTG = 23;
    private static final int MD_WORLD_MODE_UNKNOWN = 0;
    private static final String PROPERTY_ACTIVE_MD = "vendor.ril.active.md";
    private static final String PROPERTY_MAJOR_SIM = "persist.vendor.radio.simswitch";
    private static final String PROPERTY_RAT_CONFIG = "ro.vendor.mtk_ps1_rat";
    private static final String STATUS_SYNC_PREFIX = "STATUS_SYNC";
    private static final String TAG = "WorldModeActivity";
    private static final String TDSCDMA = "T";
    private static final int UTRAN_DIVISION_DUPLEX_MODE_FDD = 1;
    private static final int UTRAN_DIVISION_DUPLEX_MODE_TDD = 2;
    private static final int UTRAN_DIVISION_DUPLEX_MODE_UNKNOWN = 0;
    private static final String WCDMA = "W";
    private static final int WORLD_MODE_RESULT_ERROR = 101;
    private static final int WORLD_MODE_RESULT_SUCCESS = 100;
    private static final int WORLD_MODE_RESULT_WM_ID_NOT_SUPPORT = 102;

    public static boolean isWorldPhoneSupport() {
        String rat = SystemProperties.get(PROPERTY_RAT_CONFIG, "");
        if (rat.length() <= 0 || !rat.contains(WCDMA) || !rat.contains(TDSCDMA)) {
            return false;
        }
        return true;
    }

    public static boolean isWorldModeSupport() {
        return SystemProperties.getInt(FeatureSupport.FK_MD_WM_SUPPORT, 0) == 1;
    }

    public static boolean isC2kSupport() {
        String rat = SystemProperties.get(PROPERTY_RAT_CONFIG, "");
        if (rat.length() <= 0 || !rat.contains(CDMA)) {
            return false;
        }
        return true;
    }

    public static boolean isNrSupported() {
        return RatConfiguration.isNrSupported();
    }

    public static int getWorldModeId() {
        return Integer.valueOf(SystemProperties.get(PROPERTY_ACTIVE_MD, Integer.toString(0))).intValue();
    }

    private static int getActiveModemType() {
        if (!isWorldModeSupport()) {
            switch (getWorldModeId()) {
                case 3:
                    return 1;
                case 4:
                    return 2;
                case 5:
                    return 3;
                case 6:
                    return 4;
                default:
                    return 0;
            }
        } else {
            int modemType = getWorldModeId();
            int activeMode = Integer.valueOf(SystemProperties.get("vendor.ril.nw.worldmode.activemode", "0")).intValue();
            switch (modemType) {
                case 8:
                case 16:
                case 20:
                case 21:
                    return 4;
                case 9:
                case 18:
                case 22:
                    return 3;
                case 10:
                case 12:
                case 23:
                case 24:
                    if (activeMode <= 0) {
                        return 0;
                    }
                    if (activeMode == 1) {
                        return 3;
                    }
                    if (activeMode == 2) {
                        return 4;
                    }
                    return 0;
                case 11:
                case 15:
                case 19:
                case 25:
                    return 5;
                case 13:
                case 17:
                case 26:
                    return 6;
                case 14:
                    return 7;
                default:
                    return 0;
            }
        }
    }

    public static int get3GDivisionDuplexMode() {
        switch (getActiveModemType()) {
            case 1:
            case 3:
            case 5:
            case 7:
                return 1;
            case 2:
            case 4:
            case 6:
                return 2;
            default:
                return 0;
        }
    }

    private static boolean checkWmCapability(int worldMode, int bandMode) {
        int bandMode2;
        int bandMode3;
        int bandMode4;
        int iRat = 0;
        if (worldMode == 8) {
            iRat = 27;
        } else if (worldMode == 13) {
            iRat = 11;
        } else if (worldMode == 10) {
            iRat = 31;
        } else if (worldMode == 14) {
            iRat = 21;
        } else if (worldMode == 9) {
            iRat = 29;
        } else if (worldMode == 12) {
            iRat = 63;
        } else if (worldMode == 16) {
            iRat = 59;
        } else if (worldMode == 17) {
            iRat = 43;
        } else if (worldMode == 15) {
            iRat = 53;
        } else if (worldMode == 11) {
            iRat = 61;
        } else if (worldMode == 18) {
            iRat = 13;
        } else if (worldMode == 19) {
            iRat = 45;
        } else if (worldMode == 20) {
            iRat = 19;
        } else if (worldMode == 21) {
            iRat = 51;
        } else if (worldMode == 22) {
            iRat = 93;
        } else if (worldMode == 23) {
            iRat = 95;
        } else if (worldMode == 24) {
            iRat = 127;
        } else if (worldMode == 25) {
            iRat = 125;
        } else if (worldMode == 26) {
            iRat = NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ;
        }
        if (true == isNrSupported()) {
            bandMode2 = bandMode | 64;
        } else {
            bandMode2 = bandMode & -65;
        }
        if (true == isC2kSupport()) {
            bandMode2 |= 32;
        }
        if (RatConfiguration.isWcdmaSupported()) {
            bandMode3 = bandMode2 | 4;
        } else {
            bandMode3 = bandMode2 & -5;
        }
        if (RatConfiguration.isTdscdmaSupported()) {
            bandMode4 = bandMode3 | 2;
        } else {
            bandMode4 = bandMode3 & -3;
        }
        Elog.d(TAG, "checkWmCapab: modem=" + worldMode + " rat=" + iRat + " bandMode=" + bandMode4);
        if (iRat == (iRat & bandMode4) && (iRat & 32) == (bandMode4 & 32)) {
            return true;
        }
        return false;
    }

    public static int setWorldModeWithBand(int worldMode, int bandMode) {
        if (!checkWmCapability(worldMode, bandMode)) {
            Elog.d(TAG, "setWorldModeWithBand: not match, modem=" + worldMode + " bandMode=" + bandMode);
            return 102;
        }
        setWorldMode(worldMode);
        return 100;
    }

    public static int getMajorSim() {
        String currMajorSim = SystemProperties.get("persist.vendor.radio.simswitch", "");
        if (currMajorSim == null || currMajorSim.equals("")) {
            Elog.d(TAG, "[getMajorSim]: fail to get major SIM");
            return -99;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[getMajorSim]: ");
        sb.append(Integer.parseInt(currMajorSim) - 1);
        Elog.d(TAG, sb.toString());
        return Integer.parseInt(currMajorSim) - 1;
    }

    private static void setWorldMode(int worldMode) {
        Elog.d(TAG, "[setWorldMode] worldMode=" + worldMode);
        if (worldMode != getWorldModeId()) {
            int maxMode = 21;
            if (true == isNrSupported()) {
                maxMode = 26;
            }
            if (worldMode < 8 || worldMode > maxMode) {
                Elog.d(TAG, "Invalid world mode:" + worldMode);
                return;
            }
            String str = "MTK worldmodeid," + String.valueOf(worldMode);
            if (isC2kSupport() || FeatureSupport.is93ModemAndAbove()) {
                EmUtils.invokeOemRilRequestStringsEm(new String[]{STATUS_SYNC_PREFIX, str}, (Message) null);
                return;
            }
            EmUtils.reloadModemType(worldMode);
            EmUtils.storeModemType(worldMode);
            EmUtils.rebootModem();
        } else if (worldMode == 8) {
            Elog.d(TAG, "Already in uTLG mode");
        } else if (worldMode == 9) {
            Elog.d(TAG, "Already in uLWG mode");
        } else if (worldMode == 10) {
            Elog.d(TAG, "Already in uLWTG mode");
        } else if (worldMode == 11) {
            Elog.d(TAG, "Already in uLWCG mode");
        } else if (worldMode == 12) {
            Elog.d(TAG, "Already in uLWTCG mode");
        } else if (worldMode == 13) {
            Elog.d(TAG, "Already in LtTG mode");
        } else if (worldMode == 14) {
            Elog.d(TAG, "Already in LfWG mode");
        } else if (worldMode == 15) {
            Elog.d(TAG, "Already in uLfWCG mode");
        } else if (worldMode == 16) {
            Elog.d(TAG, "Already in uLCTG mode");
        } else if (worldMode == 17) {
            Elog.d(TAG, "Already in uLtCTG mode");
        } else if (worldMode == 18) {
            Elog.d(TAG, "Already in uLtWG mode");
        } else if (worldMode == 19) {
            Elog.d(TAG, "Already in uLtWCG mode");
        } else if (worldMode == 20) {
            Elog.d(TAG, "Already in uLfTG mode");
        } else if (worldMode == 21) {
            Elog.d(TAG, "Already in uLfCTG mode");
        } else if (worldMode == 22) {
            Elog.d(TAG, "Already in uNLWG mode");
        } else if (worldMode == 23) {
            Elog.d(TAG, "Already in uNLWTG mode");
        } else if (worldMode == 24) {
            Elog.d(TAG, "Already in uNLWCTG mode");
        } else if (worldMode == 25) {
            Elog.d(TAG, "Already in uNLWCG mode");
        } else if (worldMode == 26) {
            Elog.d(TAG, "Already in uNLTCTG mode");
        }
    }

    public static String worldModeIdToString(int worldMode) {
        String duplexMode = EnvironmentCompat.MEDIA_UNKNOWN;
        int activeMode = Integer.valueOf(SystemProperties.get("vendor.ril.nw.worldmode.activemode", "0")).intValue();
        if (activeMode == 1) {
            duplexMode = "FDD";
        } else if (activeMode == 2) {
            duplexMode = "TDD";
        }
        switch (worldMode) {
            case 8:
                return "LTG";
            case 9:
                return "LWG";
            case 10:
                return "LWTG(Auto mode:" + duplexMode + ")";
            case 11:
                return "LWCG";
            case 12:
                return "LWCTG(Auto mode:" + duplexMode + ")";
            case 13:
                return "LtTG";
            case 14:
                return "LfWG";
            case 15:
                return "LfWCG";
            case 16:
                return "LCTG";
            case 17:
                return "LtCTG";
            case 18:
                return "LtWG";
            case 19:
                return "LtWCG";
            case 20:
                return "LfTG";
            case 21:
                return "LfCTG";
            case 22:
                return "NLWG";
            case 23:
                return "NLWTG(Auto mode:" + duplexMode + ")";
            case 24:
                return "NLWCTG(Auto Mode:" + duplexMode + ")";
            case 25:
                return "NLWCG";
            case 26:
                return "NLTCTG";
            default:
                return "unknown world mode id";
        }
    }
}
