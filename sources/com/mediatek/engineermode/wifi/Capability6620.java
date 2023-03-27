package com.mediatek.engineermode.wifi;

/* compiled from: WifiCapability */
class Capability6620 extends WifiCapability {
    private boolean mCap11ac = false;
    private boolean mCapAntSwap = false;
    private String mWifiChip;

    Capability6620(String wifiChip) {
        this.mWifiChip = wifiChip;
    }

    /* access modifiers changed from: protected */
    public void getCapability() {
        String[] supportedChips = {"6630", "6797", "6759"};
        for (String equals : supportedChips) {
            if (equals.equals(this.mWifiChip)) {
                this.mCap11ac = true;
            }
        }
        this.mCapAntSwap = EMWifi.isAntSwapSupport();
    }

    /* access modifiers changed from: protected */
    public boolean isAntSwapSupport() {
        return this.mCapAntSwap;
    }

    /* access modifiers changed from: protected */
    public boolean is11AcSupport() {
        return this.mCap11ac;
    }
}
