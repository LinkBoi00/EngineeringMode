package com.mediatek.engineermode.wifi;

import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.wifi.WifiCapability;
import java.util.Arrays;

/* compiled from: WifiCapability */
class CapabilityFormat extends WifiCapability {
    private static final int CAPABILITY_LENGTH = 32;
    private static final int INDEX_CAP_ANT_NUM = 1;
    private static final int INDEX_CAP_ANT_SWAP = 16;
    private static final int INDEX_CAP_BW = 5;
    private static final int INDEX_CAP_CH_BAND = 4;
    private static final int INDEX_CAP_CODING = 3;
    private static final int INDEX_CAP_DBDC = 2;
    private static final int INDEX_CAP_PROTOCOL = 0;
    private static final int MASK_BW_160C = 8;
    private static final int MASK_BW_160NC = 16;
    private static final int MASK_CAP_11AX = 8;
    private static final int MASK_CAP_ANT_SWAP = 1;
    private static final int MASK_CAP_DBDC = 1;
    private static final int MASK_CAP_HW_TX = 2;
    private static final int MASK_CAP_LDPC = 3;
    private static final int MASK_CH_BAND_6G = 4;
    private boolean mCap11ax = false;
    private boolean mCap2by2 = false;
    private boolean mCapAntSwap = false;
    private boolean mCapBw160c = false;
    private boolean mCapBw160nc = false;
    private WifiCapability.CapChBand mCapChBand = WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT;
    private boolean mCapDbdc = false;
    private boolean mCapHwTx = false;
    private boolean mCapLdpc = false;

    CapabilityFormat() {
    }

    /* access modifiers changed from: protected */
    public void getCapability() {
        int[] capability = new int[32];
        boolean z = false;
        Arrays.fill(capability, 0);
        if (EMWifi.hqaGetChipCapability(capability) != 0) {
            Elog.e("Wifi/Capability", "Query capability failed");
            return;
        }
        Elog.i("Wifi/Capability", "capability:" + Arrays.toString(capability));
        this.mCap11ax = (capability[0] & 8) > 0;
        this.mCap2by2 = capability[1] > 1;
        this.mCapDbdc = (capability[2] & 1) > 0;
        this.mCapLdpc = (3 & capability[3]) > 0;
        this.mCapAntSwap = (capability[16] & 1) > 0;
        this.mCapHwTx = (2 & capability[16]) > 0;
        this.mCapChBand = (4 & capability[4]) > 0 ? WifiCapability.CapChBand.CAP_CH_BAND_6G : WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT;
        this.mCapBw160c = (capability[5] & 8) > 0;
        if ((capability[5] & 16) > 0) {
            z = true;
        }
        this.mCapBw160nc = z;
    }

    /* access modifiers changed from: protected */
    public boolean isAntSwapSupport() {
        return this.mCapAntSwap;
    }

    /* access modifiers changed from: protected */
    public boolean is11AxSupport() {
        return this.mCap11ax;
    }

    /* access modifiers changed from: protected */
    public boolean isDbdcSupport() {
        return this.mCapDbdc;
    }

    public boolean is2by2Support() {
        return this.mCap2by2;
    }

    /* access modifiers changed from: protected */
    public boolean isHwTxSupport() {
        return this.mCapHwTx;
    }

    /* access modifiers changed from: protected */
    public WifiCapability.CapChBand getCapChBand() {
        return this.mCapChBand;
    }

    /* access modifiers changed from: protected */
    public boolean is160cSupport() {
        return this.mCapBw160c;
    }

    /* access modifiers changed from: protected */
    public boolean is160ncSupport() {
        return this.mCapBw160nc;
    }

    /* access modifiers changed from: protected */
    public boolean isLdpcSupport() {
        return this.mCapLdpc;
    }
}
