package com.mediatek.engineermode.wifi;

import android.os.SystemProperties;

/* compiled from: WifiCapability */
class Capability6632 extends WifiCapability {
    private static final String[] CHIP_SUPPORT_DBDC = {"0x6779", "0x6885", "0x6873"};
    private static final String PROPERTY = "persist.vendor.connsys.chipid";
    private boolean mCapAntSwap = false;
    private boolean mCapDbdc = false;

    Capability6632() {
    }

    /* access modifiers changed from: protected */
    public void getCapability() {
        this.mCapAntSwap = EMWifi.isAntSwapSupport();
        String id = SystemProperties.get(PROPERTY);
        for (String strId : CHIP_SUPPORT_DBDC) {
            if (strId.equalsIgnoreCase(id)) {
                this.mCapDbdc = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAntSwapSupport() {
        return this.mCapAntSwap;
    }

    /* access modifiers changed from: protected */
    public boolean isDbdcSupport() {
        return this.mCapDbdc;
    }
}
