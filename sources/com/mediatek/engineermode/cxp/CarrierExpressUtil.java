package com.mediatek.engineermode.cxp;

import android.os.SystemProperties;

public class CarrierExpressUtil {
    public static boolean isCarrierExpressSupported() {
        if ("no".equals(SystemProperties.get("ro.vendor.mtk_carrierexpress_pack", "no")) || SystemProperties.getInt("ro.vendor.mtk_cxp_switch_mode", 0) == 2) {
            return false;
        }
        return true;
    }
}
