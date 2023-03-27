package com.mediatek.engineermode.mcfconfig;

import android.widget.TextView;
import com.mediatek.engineermode.Elog;

public final class FileUtils {
    private static final int DECIMAL_NUMBER = 100;
    private static final double ROUNDING_OFF = 0.005d;
    public static final String SEPARATOR = "/";
    private static final String TAG = "McfConfig/FileUtils";
    public static final String UNIT_B = "B";
    public static final String UNIT_GB = "GB";
    private static final int UNIT_INTERVAL = 1024;
    public static final String UNIT_KB = "KB";
    public static final String UNIT_MB = "MB";
    public static final String UNIT_TB = "TB";

    public static String getFileExtension(String fileName) {
        int lastDot;
        if (fileName != null && (lastDot = fileName.lastIndexOf(46)) >= 0) {
            return fileName.substring(lastDot + 1).toLowerCase();
        }
        return null;
    }

    public static String getSuperParent(String fileName) {
        int lastDot;
        if (fileName != null && (lastDot = fileName.indexOf(":")) > 0) {
            return fileName.substring(0, lastDot).toUpperCase();
        }
        return null;
    }

    public static String getFileName(String absolutePath) {
        int sepIndex = absolutePath.lastIndexOf(SEPARATOR);
        if (sepIndex >= 0) {
            return absolutePath.substring(sepIndex + 1);
        }
        return absolutePath;
    }

    public static boolean isDirctory(String absolutePath) {
        if (absolutePath.indexOf(46) >= 0) {
            return false;
        }
        return true;
    }

    public static String getFilePath(String filePath) {
        int sepIndex;
        if (filePath.endsWith(SEPARATOR)) {
            sepIndex = filePath.substring(0, filePath.lastIndexOf(SEPARATOR)).lastIndexOf(SEPARATOR);
        } else {
            sepIndex = filePath.lastIndexOf(SEPARATOR);
        }
        if (sepIndex >= 0) {
            return filePath.substring(0, sepIndex);
        }
        return "";
    }

    public static boolean isFileExist(String filePath, String file) {
        if (filePath.indexOf(file) >= 0) {
            return true;
        }
        return false;
    }

    public static String sizeToString(long size) {
        if (size < 0) {
            return "0 " + UNIT_B;
        } else if (size < 100) {
            return Long.toString(size) + " " + UNIT_B;
        } else {
            String unit = UNIT_KB;
            double sizeDouble = ((double) size) / 1024.0d;
            if (sizeDouble > 1024.0d) {
                sizeDouble /= 1024.0d;
                unit = UNIT_MB;
            }
            if (sizeDouble > 1024.0d) {
                sizeDouble /= 1024.0d;
                unit = UNIT_GB;
            }
            if (sizeDouble > 1024.0d) {
                sizeDouble /= 1024.0d;
                unit = UNIT_TB;
            }
            double formatedSize = ((double) ((long) ((ROUNDING_OFF + sizeDouble) * 100.0d))) / 100.0d;
            Elog.d(TAG, "sizeToString(): " + formatedSize + unit);
            if (formatedSize == 0.0d) {
                return "0 " + unit;
            }
            return Double.toString(formatedSize) + " " + unit;
        }
    }

    public static void fadeOutLongString(TextView textView) {
        if (textView == null) {
            Elog.w(TAG, "#adjustWithLongString(),the view is to be set is null");
        } else if (!(textView instanceof TextView)) {
            Elog.w(TAG, "#adjustWithLongString(),the view instance is not right,execute failed!");
        } else {
            textView.setHorizontalFadingEdgeEnabled(true);
            textView.setSingleLine(true);
            textView.setGravity(3);
            textView.setGravity(16);
        }
    }
}
