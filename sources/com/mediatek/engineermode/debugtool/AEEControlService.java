package com.mediatek.engineermode.debugtool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AEEControlService extends Service {
    protected static final String COMMAND_CLEAN_DATA = "rm -r /data/core/ /data/anr";
    protected static final String COMMAND_CLEAR_DAL = "aee -c dal";
    private static final String TAG = "AEEControlService/Debugutils";
    protected static final String sAEE_CONVERT_VERSION = "ro.vendor.aee.convert64";
    private final IBinder mBinder = new LocalBinder();
    protected Map<String, String> mCommandMap;

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        /* access modifiers changed from: package-private */
        public AEEControlService getService() {
            return AEEControlService.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        this.mCommandMap = buildCommandList("AEECleanData#rm -r /data/core /data/anr /data/tombstones", "AEEClearDAL#aee -c dal", "MediatekEngineer#aee -m 1", "AEEClearDAL_V2#aee_v2 -c dal", "MediatekEngineer_V2#aee_v2 -m 1", "MediatekUser#aee -m 2", "CustomerEngineer#aee -m 3", "MediatekUser_V2#aee_v2 -m 2", "CustomerEngineer_V2#aee_v2 -m 3", "CustomerUser#aee -m 4", "EnableDAL#aee -s on", "DisableDAL#aee -s off", "CustomerUser_V2#aee_v2 -m 4", "EnableDAL_V2#aee_v2 -s on", "DisableDAL_V2#aee_v2 -s off");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Elog.i("AEEControlService", "Received start id " + startId + ": " + intent);
        return 1;
    }

    public void onDestroy() {
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void changeAEEMode(String modevalue) {
        String command = this.mCommandMap.get(modevalue);
        String command_v2 = this.mCommandMap.get(modevalue + "_V2");
        String aeeversion = getProperty(sAEE_CONVERT_VERSION);
        if (aeeversion != null && aeeversion.equals("0") && command != null) {
            systemexec(command);
        } else if (aeeversion == null || !aeeversion.equals("1") || command_v2 == null) {
            Elog.e(TAG, "Fail to changed AEE mode.");
        } else {
            systemexec(command_v2);
        }
    }

    public void dalSetting(String dalOption) {
        String command = this.mCommandMap.get(dalOption);
        String command_v2 = this.mCommandMap.get(dalOption + "_V2");
        String aeeversion = getProperty(sAEE_CONVERT_VERSION);
        if (aeeversion != null && aeeversion.equals("0") && command != null) {
            systemexec(command);
        } else if (aeeversion == null || !aeeversion.equals("1") || command_v2 == null) {
            Elog.e(TAG, "Fail to setted dal option.");
        } else {
            systemexec(command_v2);
        }
    }

    public void clearDAL() {
        String command = this.mCommandMap.get("AEEClearDAL");
        String command_v2 = this.mCommandMap.get("AEEClearDAL_V2");
        String aeeversion = getProperty(sAEE_CONVERT_VERSION);
        if (aeeversion != null && aeeversion.equals("0") && command != null) {
            systemexec(command);
        } else if (aeeversion == null || !aeeversion.equals("1") || command_v2 == null) {
            Elog.e(TAG, "Fail to cleared AEE red screen.");
            return;
        } else {
            systemexec(command_v2);
        }
        Elog.d(TAG, "Device AEE red screen cleared.");
    }

    public void cleanData() {
        String command = this.mCommandMap.get("AEECleanData");
        if (command != null) {
            systemexec(command);
        }
        Elog.i(TAG, "Device /data partition cleaned up.");
        Toast.makeText(this, "Device /data partition cleaned up.", 0).show();
    }

    private static StringBuffer systemexec(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            new String();
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                output.append(line + "\n");
            }
            Elog.d(TAG, output.toString());
            process.waitFor();
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Elog.e(TAG, "Operation failed.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            Elog.e(TAG, "Operation failed.");
        }
        return output;
    }

    private static Map<String, String> buildCommandList(String... cmdentries) {
        Map<String, String> commandMap = new HashMap<>();
        for (String cmdentry : cmdentries) {
            String[] kv = cmdentry.split("#");
            commandMap.put(kv[0], kv[1]);
        }
        return commandMap;
    }

    private static String getProperty(String prop) {
        return SystemProperties.get(prop);
    }
}
