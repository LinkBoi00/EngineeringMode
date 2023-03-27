package com.mediatek.engineermode.audio;

public class AudioTuningJni {
    public static native boolean CustXmlEnableChanged(int i);

    public static native void cancleCopyAudioHalDumpFile();

    public static native void copyAudioHalDumpFilesToSdcard(OnCopyProgressChangeListener onCopyProgressChangeListener);

    public static native void delAudioHalDumpFiles(OnCopyProgressChangeListener onCopyProgressChangeListener);

    public static native int getAudioCommand(int i);

    public static native int getAudioData(int i, int i2, byte[] bArr);

    public static native String getCategory(String str, String str2);

    public static native String getChecklist(String str, String str2, String str3);

    public static native int getEmParameter(byte[] bArr, int i);

    public static native String getFeatureValue(String str);

    public static native String getParams(String str, String str2, String str3);

    public static native boolean isFeatureOptionEnabled(String str);

    public static native boolean isFeatureSupported(String str);

    public static native boolean registerXmlChangedCallback();

    public static native boolean saveToWork(String str);

    public static native int setAudioCommand(int i, int i2);

    public static native int setAudioData(int i, int i2, byte[] bArr);

    public static native int setEmParameter(byte[] bArr, int i);

    public static native boolean setParams(String str, String str2, String str3, String str4);

    static {
        System.loadLibrary("em_audio_jni");
    }
}
