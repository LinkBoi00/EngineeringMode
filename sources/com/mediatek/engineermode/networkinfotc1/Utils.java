package com.mediatek.engineermode.networkinfotc1;

public class Utils {
    private static final String TAG = "MDMLUtils";

    public static long getIntFromByte(byte[] data, int startpos, int len) {
        long ret = 0;
        if (startpos + len > data.length) {
            return -1;
        }
        for (int i = len - 1; i >= 0; i--) {
            ret += (long) (data[startpos + i] & 255);
            if (i != 0) {
                ret <<= 8;
            }
        }
        return ret;
    }

    public static long getNegLongFromByte(byte[] data, int startpos, int len) {
        byte[] bArr = data;
        long ret = 0;
        long sign = 1;
        long temp = 0;
        if (startpos + len > bArr.length) {
            return -1;
        }
        if ((bArr[(startpos + len) - 1] & 128) != 0) {
            sign = -1;
        }
        for (int i = len - 1; i >= 0; i--) {
            ret += (long) (bArr[startpos + i] & 255);
            if (i != 0) {
                ret <<= 8;
            }
        }
        if (sign != 1) {
            for (int i2 = 8 - len; i2 >= 0; i2--) {
                temp += 255;
                if (i2 != 0) {
                    temp <<= 8;
                }
            }
            for (int i3 = len - 1; i3 >= 0; i3--) {
                temp <<= 8;
            }
        }
        return ret | temp;
    }

    public static long getIntFromByte(byte[] data) {
        if (data == null || data.length <= 0) {
            return 0;
        }
        return getIntFromByte(data, 0, data.length);
    }

    public static long getIntFromByte(byte[] data, boolean sign) {
        if (data == null || data.length <= 0) {
            return 0;
        }
        return getNegLongFromByte(data, 0, data.length);
    }

    public static boolean getBoolFromByte(byte[] data) {
        boolean result = false;
        if (getIntFromByte(data, 0, data.length) == 1) {
            result = true;
        }
        return result;
    }
}
