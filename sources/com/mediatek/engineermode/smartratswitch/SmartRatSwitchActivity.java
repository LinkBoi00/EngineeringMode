package com.mediatek.engineermode.smartratswitch;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class SmartRatSwitchActivity extends Activity implements View.OnClickListener {
    public static final String DEBUG_PROPERTY = "persist.vendor.smartratswitch.debug";
    private static final String TAG = "SmartRatSwitch";
    private Button mFeatureDisableButton;
    private Button mFeatureEnableButton;
    private int mFeatureStatus = 0;
    private Button mLogDisableButton;
    private Button mLogEnableButton;
    private int mLogStatus = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_rat_switch);
        this.mFeatureEnableButton = (Button) findViewById(R.id.smartratswitch_enable);
        this.mFeatureDisableButton = (Button) findViewById(R.id.smartratswitch_disable);
        this.mLogEnableButton = (Button) findViewById(R.id.smartratswitchdebug_enable);
        this.mLogDisableButton = (Button) findViewById(R.id.smartratswitchdebug_disable);
        this.mFeatureEnableButton.setOnClickListener(this);
        this.mFeatureDisableButton.setOnClickListener(this);
        this.mLogEnableButton.setOnClickListener(this);
        this.mLogDisableButton.setOnClickListener(this);
        querySmartRatSwitchStatus();
        updateFeatureButtonStatus();
        queryDebugLogStatus();
        updateDebugLogButtonStatus();
    }

    public void onClick(View arg0) {
        if (R.id.smartratswitch_enable == arg0.getId() || R.id.smartratswitch_disable == arg0.getId()) {
            if (R.id.smartratswitch_enable == arg0.getId()) {
                updateSmartRatSwitchStatus(1);
            } else if (R.id.smartratswitch_disable == arg0.getId()) {
                updateSmartRatSwitchStatus(0);
            }
            querySmartRatSwitchStatus();
            updateFeatureButtonStatus();
        } else if (R.id.smartratswitchdebug_enable == arg0.getId() || R.id.smartratswitchdebug_disable == arg0.getId()) {
            if (R.id.smartratswitchdebug_enable == arg0.getId()) {
                updateDebugLogStatus(1);
            } else if (R.id.smartratswitchdebug_disable == arg0.getId()) {
                updateDebugLogStatus(0);
            }
            queryDebugLogStatus();
            updateDebugLogButtonStatus();
        }
    }

    private void updateSmartRatSwitchStatus(int isEnabled) {
        Settings.Global.putInt(getContentResolver(), "smart_rat_switch_enabled", isEnabled);
    }

    private void querySmartRatSwitchStatus() {
        this.mFeatureStatus = Settings.Global.getInt(getContentResolver(), "smart_rat_switch_enabled", 1);
        Elog.d(TAG, "querySmartRatSwitchStatus, The SmartRatSwitch setting is : " + this.mFeatureStatus);
    }

    private void updateFeatureButtonStatus() {
        if (this.mFeatureStatus == 0) {
            this.mFeatureEnableButton.setEnabled(true);
            this.mFeatureDisableButton.setEnabled(false);
            return;
        }
        this.mFeatureEnableButton.setEnabled(false);
        this.mFeatureDisableButton.setEnabled(true);
    }

    private void updateDebugLogStatus(int isEnabled) {
        Settings.Global.putInt(getContentResolver(), "smart_rat_switch_debug", isEnabled);
    }

    private void queryDebugLogStatus() {
        this.mLogStatus = Settings.Global.getInt(getContentResolver(), "smart_rat_switch_debug", 0);
        Elog.d(TAG, "queryDebugLogStatus, The debug setting is : " + this.mLogStatus);
    }

    private void updateDebugLogButtonStatus() {
        if (this.mLogStatus == 0) {
            this.mLogEnableButton.setEnabled(true);
            this.mLogDisableButton.setEnabled(false);
            return;
        }
        this.mLogEnableButton.setEnabled(false);
        this.mLogDisableButton.setEnabled(true);
    }
}
