package com.mediatek.engineermode.atci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v4.os.EnvironmentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;

public class atciActivity extends Activity {
    private static final String ADB_ENABLE = "persist.sys.usb.config";
    private static final String ATCI_AUTO_START = "persist.vendor.service.atci.autostart";
    private static final String ATCI_USERMODE = "persist.vendor.service.atci.usermode";
    private static final int DISABLE_ATCI = 2;
    private static final int ENABLE_ALWAYS = 1;
    private static final int ENABLE_ONCE = 0;
    private static final String RADIO_PORT_INDEX = "persist.vendor.radio.port_index";
    private static final String RO_BUILD_TYPE = "ro.build.type";
    private static final String TAG = "EM/ATCI";
    private static final String USB_ACM_INDEX = "vendor.usb.acm_idx";
    private Button mAlwaysEnableButton;
    private View.OnClickListener mAlwaysEnableListener = new View.OnClickListener() {
        public void onClick(View v) {
            atciActivity.this.enable_ATCI(1);
        }
    };
    private Button mDisableButton;
    private View.OnClickListener mDisableListener = new View.OnClickListener() {
        public void onClick(View v) {
            atciActivity.this.enable_ATCI(2);
        }
    };
    private Button mEnableButton;
    private View.OnClickListener mEnableListener = new View.OnClickListener() {
        public void onClick(View v) {
            atciActivity.this.enable_ATCI(0);
        }
    };

    /* access modifiers changed from: private */
    public void enable_ATCI(int action) {
        String acmIdx = SystemProperties.get(USB_ACM_INDEX, EnvironmentCompat.MEDIA_UNKNOWN);
        Elog.d(TAG, "acmIdx:" + acmIdx);
        if (1 == action || action == 0) {
            SystemProperties.set(ATCI_USERMODE, "1");
            if (acmIdx.contains("4")) {
                SystemProperties.set(RADIO_PORT_INDEX, "1,4");
            } else {
                SystemProperties.set(RADIO_PORT_INDEX, "1");
            }
        } else if (2 == action) {
            SystemProperties.set(ATCI_USERMODE, "0");
            if (acmIdx.contains("4")) {
                SystemProperties.set(RADIO_PORT_INDEX, "4");
            } else {
                SystemProperties.set(RADIO_PORT_INDEX, "");
            }
        }
        String type = SystemProperties.get("ro.build.type", EnvironmentCompat.MEDIA_UNKNOWN);
        String isAutoStart = SystemProperties.get(ATCI_AUTO_START);
        Elog.v(TAG, "action: " + action + ", build type: " + type);
        if (type.equals(FeatureSupport.ENG_LOAD)) {
            Toast.makeText(this, "Eng load, ATCI is enabled", 1).show();
        } else if (2 == action) {
            SystemProperties.set(ATCI_AUTO_START, "0");
            Elog.v(TAG, "stop atcid-daemon-u");
            SystemProperties.set("ctl.stop", "atcid-daemon-u");
            Elog.v(TAG, "stop atci_service");
            SystemProperties.set("ctl.stop", "atci_service");
            Toast.makeText(this, "Disable ATCI Success", 1).show();
            return;
        } else {
            if (1 == action) {
                if (isAutoStart.equals("1")) {
                    Elog.v(TAG, "atuo start enabled.");
                    Toast.makeText(this, "You have enabled ATCI", 1).show();
                    return;
                }
                SystemProperties.set(ATCI_AUTO_START, "1");
                Toast.makeText(this, "Always enable ATCI Success", 1).show();
            } else if (action == 0) {
                SystemProperties.set(ATCI_AUTO_START, "0");
            } else {
                Elog.v(TAG, "unknown action.");
                return;
            }
            Elog.v(TAG, "start atcid-daemon-u");
            SystemProperties.set("ctl.start", "atcid-daemon-u");
            Elog.v(TAG, "start atci_service");
            SystemProperties.set("ctl.start", "atci_service");
        }
        Intent intent = new Intent("com.mediatek.atci.service.startup");
        intent.setClassName("com.mediatek.atci.service", "com.mediatek.atci.service.AtciIntentReceiver");
        sendBroadcast(intent);
        Toast.makeText(this, "Enable ATCI Success", 1).show();
    }

    /* access modifiers changed from: protected */
    public void findViews() {
        this.mEnableButton = (Button) findViewById(R.id.ATCI_enable);
        this.mAlwaysEnableButton = (Button) findViewById(R.id.ATCI_enable_always);
        this.mDisableButton = (Button) findViewById(R.id.ATCI_disable);
    }

    /* access modifiers changed from: protected */
    public void setActionListener() {
        this.mEnableButton.setOnClickListener(this.mEnableListener);
        this.mAlwaysEnableButton.setOnClickListener(this.mAlwaysEnableListener);
        this.mDisableButton.setOnClickListener(this.mDisableListener);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atci);
        findViews();
        setActionListener();
    }
}
