package com.mediatek.engineermode;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.LocationManager;
import android.os.RemoteException;
import android.os.SystemProperties;

public class FeatureSupport {
    public static final String ENG_LOAD = "eng";
    public static final String FK_AAL_SUPPORT = "ro.vendor.mtk_aal_support";
    public static final String FK_APC_SUPPORT = "vendor.ril.apc.support";
    public static final String FK_BOARD_PLATFORM = "ro.board.platform";
    public static final String FK_BUILD_TYPE = "ro.build.type";
    public static final String FK_CT4GREG_APP = "ro.vendor.mtk_ct4greg_app";
    public static final String FK_DEVREG_APP = "ro.vendor.mtk_devreg_app";
    public static final String FK_FD_SUPPORT = "ro.vendor.mtk_fd_support";
    public static final String FK_GMO_RAM_OPTIMIZE = "ro.vendor.gmo.ram_optimize";
    public static final String FK_GNSS_L5_SUPPORT = "vendor.debug.gps.support.l5";
    public static final String FK_GWSD_SUPPORT = "ro.vendor.mtk_gwsd_support";
    public static final String FK_IMS_SUPPORT = "persist.vendor.ims_support";
    public static final String FK_MD_WM_SUPPORT = "ro.vendor.mtk_md_world_mode_support";
    public static final String FK_MTK_93_SUPPORT = "ro.vendor.mtk_ril_mode";
    public static final String FK_MTK_95_SUPPORT = "persist.vendor.ss.modem_version";
    public static final String FK_MTK_DSDA_SUPPORT = "persist.vendor.radio.multisim.config";
    public static final String FK_MTK_TELEPHONY_ADD_ON_POLICY = "ro.vendor.mtk_telephony_add_on_policy";
    public static final String FK_MTK_TEL_LOG_SUPPORT = "persist.vendor.log.tel_log_ctrl";
    public static final String FK_MTK_WFC_SUPPORT = "persist.vendor.mtk_wfc_support";
    public static final String FK_SINGLE_BIN_MODEM_SUPPORT = "ro.vendor.mtk_single_bin_modem_support";
    public static final String FK_VILTE_SUPPORT = "persist.vendor.vilte_support";
    public static final String FK_VODATA_SUPPORT = "ro.vendor.vodata_support";
    public static final String FK_WFD_SUPPORT = "ro.vendor.mtk_wfd_support";
    private static final String[] MODEM_GEN95_ARRAY = {"mt6785", "mt6779", "mt6781"};
    private static final String[] MODEM_GEN97_ARRAY = {"mt6885", "mt6873", "mt6853", "mt6893", "mt6833", "mt6877"};
    private static final String[] MODEM_GEN98_ARRAY = {"mt6983", "mt6879", "mt6895"};
    public static final String PK_CDS_EM = "com.mediatek.connectivity";
    private static final String SUPPORTED = "1";
    private static final String TAG = "FeatureSupport";
    public static final String USERDEBUG_LOAD = "userdebug";
    public static final String USER_LOAD = "user";
    public static boolean is_support_3GOnly_md = false;
    public static boolean is_support_90_md = false;
    public static boolean is_support_91_md = false;
    public static boolean is_support_92_md = false;
    public static boolean is_support_93_md = false;

    public static boolean is3GOnlyModem() {
        if (ChipSupport.getChip() == 2) {
            is_support_3GOnly_md = true;
        } else {
            is_support_3GOnly_md = false;
        }
        return is_support_3GOnly_md;
    }

    public static boolean is90Modem() {
        if (ChipSupport.getChip() == 1) {
            is_support_90_md = true;
        } else {
            is_support_90_md = false;
        }
        return is_support_90_md;
    }

    public static boolean is91Modem() {
        return false;
    }

    public static boolean is92Modem() {
        if (ChipSupport.getChip() <= 2 || is93ModemAndAbove()) {
            is_support_92_md = false;
        } else {
            is_support_92_md = true;
        }
        return is_support_92_md;
    }

    public static boolean is93ModemAndAbove() {
        boolean z = "c6m_1rild".equals(SystemProperties.get(FK_MTK_93_SUPPORT));
        is_support_93_md = z;
        return z;
    }

    public static boolean is95ModemAndAbove() {
        return "1".equals(SystemProperties.get(FK_MTK_95_SUPPORT));
    }

    public static boolean is95Modem() {
        String value = EmUtils.systemPropertyGet(FK_BOARD_PLATFORM, "0");
        Elog.d(TAG, "ro.board.platform = " + value);
        String[] strArr = MODEM_GEN95_ARRAY;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals(value)) {
                Elog.d(TAG, "it is Gen95 modem");
                return true;
            }
        }
        return false;
    }

    public static boolean is97ModemAndAbove() {
        return is95ModemAndAbove() && !is95Modem();
    }

    public static boolean is97Modem() {
        String value = EmUtils.systemPropertyGet(FK_BOARD_PLATFORM, "0");
        Elog.d(TAG, "ro.board.platform = " + value);
        String[] strArr = MODEM_GEN97_ARRAY;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals(value)) {
                Elog.d(TAG, "it is Gen97 modem");
                return true;
            }
        }
        return false;
    }

    public static boolean is98Modem() {
        String value = EmUtils.systemPropertyGet(FK_BOARD_PLATFORM, "0");
        Elog.d(TAG, "ro.board.platform = " + value);
        String[] strArr = MODEM_GEN98_ARRAY;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals(value)) {
                Elog.d(TAG, "it is Gen98 modem");
                return true;
            }
        }
        return false;
    }

    public static boolean isSupported(String featureKey) {
        Elog.i(TAG, featureKey + " support:" + SystemProperties.get(featureKey));
        return "1".equals(SystemProperties.get(featureKey));
    }

    public static String getProperty(String propertyName) {
        return SystemProperties.get(propertyName);
    }

    public static boolean isSupportedEmSrv() {
        if (!"1".equals(SystemProperties.get(FK_GMO_RAM_OPTIMIZE)) || ENG_LOAD.equals(SystemProperties.get(FK_BUILD_TYPE))) {
            return true;
        }
        return false;
    }

    public static boolean isPackageExisted(Context context, String packageName) {
        for (ApplicationInfo ai : context.getPackageManager().getInstalledApplications(0)) {
            if (ai.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEngLoad() {
        return ENG_LOAD.equals(SystemProperties.get(FK_BUILD_TYPE));
    }

    public static boolean isUserLoad() {
        Elog.i(TAG, "FK_BUILD_TYPE:" + SystemProperties.get(FK_BUILD_TYPE));
        return USER_LOAD.equals(SystemProperties.get(FK_BUILD_TYPE));
    }

    public static boolean isUserDebugLoad() {
        return USERDEBUG_LOAD.equals(SystemProperties.get(FK_BUILD_TYPE));
    }

    public static boolean isSupportWfc() {
        return SystemProperties.get(FK_MTK_WFC_SUPPORT, "0").equals("1");
    }

    public static boolean isWifiSupport(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.wifi");
    }

    public static boolean isEmBTSupport() {
        try {
            if (EmUtils.getEmHidlService().btIsEmSupport() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNfcSupport() {
        try {
            if (EmUtils.getEmHidlService().isNfcSupport() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean isGpsSupport(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService("location");
        if (locManager == null || locManager.getProvider("gps") == null) {
            return false;
        }
        return true;
    }

    public static boolean isGauge30Support() {
        try {
            if (EmUtils.getEmHidlService().isGauge30Support() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMtkTelephonyAddOnPolicyEnable() {
        return SystemProperties.get(FK_MTK_TELEPHONY_ADD_ON_POLICY, "0").equals("0");
    }
}
