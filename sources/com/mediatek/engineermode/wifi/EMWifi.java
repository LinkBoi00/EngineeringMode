package com.mediatek.engineermode.wifi;

import android.net.wifi.WifiManager;
import android.os.SystemClock;
import com.mediatek.engineermode.Elog;

public class EMWifi {
    private static final long DEFAULT_WAIT_TIME = 100;
    static final int DUAL_BAND_MODE = 2;
    private static final long IOCTL_CMD_ID_CTIA_OFF = hex2Long("FFFF1235");
    private static final long IOCTL_CMD_ID_CTIA_ON = hex2Long("FFFF1234");
    private static final long IOCTL_CMD_ID_CTIA_SET_RATE = hex2Long("FFFF0123");
    static final int SINGLE_BAND_MODE = 1;
    private static final String TAG = "EMWifi";
    private static boolean sInTestMode = false;

    public static native int doCTIATestGet(long j, long[] jArr);

    public static native int doCTIATestSet(long j, long j2);

    public static native int eepromReadByteStr(long j, long j2, byte[] bArr);

    public static native int eepromWriteByteStr(long j, long j2, String str);

    public static native int getATParam(long j, long[] jArr);

    public static native String getFwManifestVersion();

    public static native int getPacketRxStatus(long[] jArr, int i);

    public static native int getSupportChannelList(long[] jArr);

    public static native int hqaDbdcContinuousTX(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    public static native int hqaDbdcSetChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static native int hqaDbdcSetTXContent(int i, int i2);

    public static native int hqaDbdcStartRXExt(int i, int i2, int i3, int i4);

    public static native int hqaDbdcStartTX(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public static native int hqaDbdcStopRX(int i);

    public static native int hqaDbdcStopTX(int i);

    public static native int hqaDbdcTXTone(int i, int i2, int i3, int i4);

    public static native int hqaGetAFactor(int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5);

    static native int hqaGetChipCapability(int[] iArr);

    public static native int hqaGetRXStatisticsAllExt(int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    public static native int hqaGetTxInfo(int i, int[] iArr);

    public static native int hqaGetTxPower(int i, int i2);

    static native String hqaGetWlanCfgEm(int[] iArr);

    static native int hqaSetAntSwap(int i);

    public static native int hqaSetBandMode(int i);

    public static native int hqaSetNss(int i);

    public static native int hqaSetPreamble(int i);

    public static native int hqaSetRUSettings(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public static native int hqaSetRate(int i);

    public static native int hqaSetRxPath(int i, int i2);

    public static native int hqaSetTxPath(int i, int i2);

    public static native int hqaSetTxPowerExt(int i, int i2, int i3, int i4, int i5);

    static native int hqaSetWlanCfgEm(int i, String str);

    public static native int initial();

    public static native boolean isAntSwapSupport();

    public static native int readEEPRom16(long j, long[] jArr);

    public static native int readMCR32(long j, long[] jArr);

    public static native int readTxPowerFromEEPromEx(long j, long j2, long[] jArr, int i);

    public static native int setATParam(long j, long j2);

    public static native int setChannel(long j);

    public static native int setEEPRomSize(long j);

    public static native int setEEPromCKSUpdated();

    public static native int setNormalMode();

    public static native int setPnpPower(long j);

    public static native int setStandBy();

    public static native int setTestMode();

    public static native int unInitial();

    public static native int writeEEPRom16(long j, long j2);

    public static native int writeMCR32(long j, long j2);

    static {
        System.loadLibrary("em_wifi_jni");
    }

    public static WifiEmState startTestMode(WifiManager wifiMgr) {
        if (!enableWifi(wifiMgr)) {
            return WifiEmState.WIFI_EM_STATE_ENABLE_FAIL;
        }
        Elog.i(TAG, "Wifi state: " + wifiMgr.getWifiState());
        if (sInTestMode) {
            return WifiEmState.WIFI_EM_STATE_READY;
        }
        initial();
        if (setTestMode() != 0) {
            return WifiEmState.WIFI_EM_STATE_SET_TM_FAIL;
        }
        sInTestMode = true;
        return WifiEmState.WIFI_EM_STATE_READY;
    }

    static boolean enableWifi(WifiManager wifiMgr) {
        if (wifiMgr == null) {
            return false;
        }
        if (wifiMgr.getWifiState() == 3) {
            return true;
        }
        if (wifiMgr.setWifiEnabled(true)) {
            while (wifiMgr.getWifiState() != 3) {
                Elog.i(TAG, "state:" + wifiMgr.getWifiState());
                SystemClock.sleep(DEFAULT_WAIT_TIME);
            }
            return true;
        }
        Elog.e(TAG, "enable wifi failed");
        return false;
    }

    public static void stopTestMode() {
        if (sInTestMode) {
            setNormalMode();
            unInitial();
            sInTestMode = false;
        }
    }

    static boolean isInTestMode() {
        Elog.i(TAG, "in test mode:" + sInTestMode);
        return sInTestMode;
    }

    public static boolean doCtiaTestOn() {
        return doCTIATestSet(IOCTL_CMD_ID_CTIA_ON, 0) == 0;
    }

    public static boolean doCtiaTestOff() {
        return doCTIATestSet(IOCTL_CMD_ID_CTIA_OFF, 0) == 0;
    }

    public static boolean doCtiaTestRate(int rate) {
        return doCTIATestSet(IOCTL_CMD_ID_CTIA_SET_RATE, (long) rate) == 0;
    }

    private static long hex2Long(String str) {
        return Long.parseLong(str, 16);
    }
}
