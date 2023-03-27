package com.mediatek.engineermode.connsyspatchinfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemProperties;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnsysPatchInfoActivity extends Activity {
    private static final String BRANCH_PATTERN = "t-neptune.*SOC[a-zA-Z0-9]*_[a-zA-Z0-9]*_";
    private static final String PROPERTY = "persist.vendor.connsys.patch.version";
    private static final String PROPERTY_FORMAT = "persist.vendor.connsys.bt_fw_ver";
    private static final String PROP_INVALID = "-1";
    private static final String TAG = "ConnsysPatchInfo";
    private static final String VER_SPLIT = "-";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connsys_patch_info);
        showVersion();
    }

    private void showVersion() {
        String strInfo = SystemProperties.get(PROPERTY_FORMAT);
        Elog.v(TAG, "strInfo:" + strInfo);
        if (strInfo == null || strInfo.isEmpty() || strInfo.equals(PROP_INVALID)) {
            String strVer = SystemProperties.get(PROPERTY);
            Elog.i(TAG, "version:" + strVer);
            TextView text = (TextView) findViewById(R.id.rom_patch_ver_tv);
            if (strVer != null && !strVer.isEmpty()) {
                text.setText(strVer);
                return;
            }
            return;
        }
        findViewById(R.id.connsys_patch_layout).setVisibility(8);
        findViewById(R.id.connsys_branch_layout).setVisibility(0);
        findViewById(R.id.connsys_ver_layout).setVisibility(0);
        String strBranch = getBranch(strInfo);
        if (strBranch != null) {
            String strBranch2 = strBranch.substring(0, strBranch.length() - 1);
            Elog.v(TAG, "strBranch:" + strBranch2);
            ((TextView) findViewById(R.id.connsys_branch_tv)).setText(strBranch2);
        }
        String strVer2 = getVer(strInfo);
        Elog.v(TAG, "strVer:" + strVer2);
        if (strVer2 != null) {
            ((TextView) findViewById(R.id.connsys_ver_tv)).setText(strVer2);
        }
    }

    private String getBranch(String strInfo) {
        Matcher matcher = Pattern.compile(BRANCH_PATTERN).matcher(strInfo);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    private String getVer(String strInfo) {
        String strVersion = strInfo;
        int index = strInfo.lastIndexOf(VER_SPLIT);
        if (index > 0 && strInfo.length() > index + 1) {
            strVersion = strInfo.substring(index + 1);
        }
        if (strVersion.length() < 8) {
            return null;
        }
        String strYear = strVersion.substring(0, 4);
        String strMonth = strVersion.substring(4, 6);
        String strDay = strVersion.substring(6, 8);
        StringBuilder sbVer = new StringBuilder(strYear);
        sbVer.append(VER_SPLIT);
        sbVer.append(strMonth);
        sbVer.append(VER_SPLIT);
        sbVer.append(strDay);
        if (strVersion.length() > 8) {
            sbVer.append(VER_SPLIT);
            sbVer.append(strVersion.substring(8));
        }
        return sbVer.toString();
    }
}
