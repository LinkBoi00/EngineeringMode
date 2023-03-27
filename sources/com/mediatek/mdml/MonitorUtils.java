package com.mediatek.mdml;

class MonitorUtils {
    MonitorUtils() {
    }

    public static int ByteArrayToInt(byte[] b, int offset, int bytes) {
        int val = 0;
        if (b == null || b.length <= offset) {
            return 0;
        }
        if (4 <= b.length - offset && bytes >= 4) {
            val = (0 | (b[offset + 3] & 255)) << 8;
        }
        if (3 <= b.length - offset && bytes >= 3) {
            val = (val | (b[offset + 2] & 255)) << 8;
        }
        if (2 <= b.length - offset && bytes >= 2) {
            val = (val | (b[offset + 1] & 255)) << 8;
        }
        if (1 > b.length - offset || bytes < 1) {
            return val;
        }
        return val | (b[offset] & 255);
    }

    public static long ByteArrayToLong(byte[] b, int offset, int bytes) {
        long val = 0;
        if (b == null || b.length <= offset) {
            return 0;
        }
        if (4 <= b.length - offset && bytes >= 4) {
            val = (0 | ((long) (b[offset + 3] & 255))) << 8;
        }
        if (3 <= b.length - offset && bytes >= 3) {
            val = (val | ((long) (b[offset + 2] & 255))) << 8;
        }
        if (2 <= b.length - offset && bytes >= 2) {
            val = (val | ((long) (b[offset + 1] & 255))) << 8;
        }
        if (1 > b.length - offset || bytes < 1) {
            return val;
        }
        return val | ((long) (b[offset] & 255));
    }

    public static byte[] IntToByteArray(int a) {
        return new byte[]{(byte) (a & 255), (byte) ((a >> 8) & 255), (byte) ((a >> 16) & 255), (byte) ((a >> 24) & 255)};
    }

    public static boolean SetIntToByteArray(byte[] data, int offset, int a, int bytes) {
        if (data == null || bytes < 1) {
            return false;
        }
        for (int i = 0; i < bytes; i++) {
            data[offset + i] = (byte) (a & 255);
            a >>= 8;
        }
        return true;
    }
}
