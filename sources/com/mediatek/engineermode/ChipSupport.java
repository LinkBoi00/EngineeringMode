package com.mediatek.engineermode;

public class ChipSupport {
    public static final int MTK_6580_SUPPORT = 2;
    public static final int MTK_6735_SUPPORT = 1;
    public static final int MTK_COMMON_CHIP_SUPPORT = 20;
    public static final int MTK_INIERNAL_LOAD = 0;
    public static final int MTK_TC1_COMMON_SERVICE = 1;

    public static native String getAudioTuningVersion();

    public static native int getChip();

    public static native boolean isFeatureSupported(int i);

    public static native int lcmOff();

    public static native int lcmOn();

    public static boolean isCurrentChipEquals(int targetChip) {
        return getChip() == targetChip;
    }

    private ChipSupport() {
    }

    static {
        System.loadLibrary("em_support_jni");
    }
}
