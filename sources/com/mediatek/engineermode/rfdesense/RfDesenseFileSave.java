package com.mediatek.engineermode.rfdesense;

import android.content.Context;
import android.os.Environment;
import com.mediatek.engineermode.Elog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RfDesenseFileSave {
    static String RfdesenseFiletName = null;
    private static final String TAG = "RfDesense/FileSave";
    private static SimpleDateFormat mCurrectTime = null;
    static final String mEventLogSavePath = "/rfdesense";

    private static String getCurrectTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");
        mCurrectTime = simpleDateFormat;
        return simpleDateFormat.format(new Date());
    }

    public static void saveRatTestResult(Context context, String RfdesenseContent) {
        try {
            saveToSDCard(context, mEventLogSavePath, RfdesenseFiletName, RfdesenseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elog.d(TAG, RfdesenseFiletName + " saved");
    }

    public static void setRfdesenseFiletName() {
        RfdesenseFiletName = getCurrectTime() + "_rfdesenseTx.txt";
    }

    private static void saveToSDCard(Context context, String dirName, String fileName, String content) throws IOException {
        String path = Environment.getExternalStorageDirectory().getPath() + dirName;
        File filedDir = new File(path);
        if (!filedDir.exists()) {
            filedDir.mkdir();
        }
        File file = new File(path, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
        new SingleMediaScanner(context, file);
    }
}
