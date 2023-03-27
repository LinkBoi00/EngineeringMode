package com.mediatek.engineermode.wifi;

import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.wifi.WiFi;

public abstract class WifiCapability {
    protected static final String TAG = "Wifi/Capability";

    enum CapChBand {
        CAP_CH_BAND_DEFAULT,
        CAP_CH_BAND_6G
    }

    /* access modifiers changed from: protected */
    public abstract void getCapability();

    /* access modifiers changed from: protected */
    public abstract boolean isAntSwapSupport();

    /* renamed from: com.mediatek.engineermode.wifi.WifiCapability$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType;

        static {
            int[] iArr = new int[WiFi.WifiType.values().length];
            $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType = iArr;
            try {
                iArr[WiFi.WifiType.WIFI_FORMAT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[WiFi.WifiType.WIFI_6632.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[WiFi.WifiType.WIFI_6620.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static WifiCapability getWifiCapability(String wifichip, WiFi.WifiType wifiType) {
        WifiCapability wifiCapbility = null;
        switch (AnonymousClass1.$SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[wifiType.ordinal()]) {
            case 1:
                wifiCapbility = new CapabilityFormat();
                break;
            case 2:
                wifiCapbility = new Capability6632();
                break;
            case 3:
                wifiCapbility = new Capability6620(wifichip);
                break;
            default:
                Elog.e(TAG, "WifiType error");
                break;
        }
        if (wifiCapbility != null) {
            wifiCapbility.getCapability();
        }
        return wifiCapbility;
    }

    /* access modifiers changed from: protected */
    public CapChBand getCapChBand() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public boolean is160cSupport() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public boolean is160ncSupport() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public boolean is11AxSupport() {
        return false;
    }

    public boolean is2by2Support() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isDbdcSupport() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean is11AcSupport() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isHwTxSupport() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isLdpcSupport() {
        throw new UnsupportedOperationException();
    }
}
