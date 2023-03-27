package com.mediatek.engineermode.mdlowpowermonitor;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class MDLowPowerMonitor extends Activity implements View.OnClickListener {
    private static final String MD_MONITOR_CONFIG_CMD = "AT+EGCMD=9527,4,";
    private static final String MD_MONITOR_DISABLE_CMD = "AT+EGCMD=9453";
    private static final String MD_MONITOR_ENABLE_CMD = "AT+EGCMD=9487";
    private static final int MSG_MONITOR_CONFIG_SET = 103;
    private static final int MSG_MONITOR_DISABLE = 102;
    private static final int MSG_MONITOR_ENABLE = 101;
    private static final String TAG = "MDLowPowerMonitor";
    public final int RET_FAILED = 0;
    public final int RET_SUCCESS = 1;
    private Button buttonSet = null;
    private Handler mCommandHander = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(MDLowPowerMonitor.TAG, "Receive msg from modem");
            AsyncResult asyncResult = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 101:
                    if (asyncResult.exception == null) {
                        MDLowPowerMonitor.this.showToast("MD Low Power Monitor Enable Succeed!");
                        Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Enable Succeed!");
                        return;
                    }
                    MDLowPowerMonitor.this.showToast("MD Low Power Monitor Enable Failed!");
                    Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Enable Failed!");
                    return;
                case 102:
                    if (asyncResult.exception == null) {
                        MDLowPowerMonitor.this.showToast("MD Low Power Monitor Disabled Succeed!");
                        Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Disabled Succeed!");
                        return;
                    }
                    MDLowPowerMonitor.this.showToast("MD Low Power Monitor Disabled Failed!");
                    Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Disabled Failed!");
                    return;
                case 103:
                    if (asyncResult.exception == null) {
                        MDLowPowerMonitor.this.showToast("MD Low Power Monitor Config Succeed!");
                        Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Config Succeed!");
                        return;
                    }
                    MDLowPowerMonitor.this.showToast("MD Low Power Monitor Config Failed!");
                    Elog.d(MDLowPowerMonitor.TAG, "MD Low Power Monitor Config Failed!");
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mFirstchecked = true;
    private RadioGroup mRadioBtn = null;
    private Button mRadioBtnDisabled = null;
    private Button mRadioBtnEnabled = null;
    private Spinner mSpinConfigValues = null;
    private Toast mToast = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_low_power_monitor);
        this.mFirstchecked = true;
        this.mRadioBtnEnabled = (Button) findViewById(R.id.md_low_power_monitor_enabled);
        this.mRadioBtnDisabled = (Button) findViewById(R.id.md_low_power_monitor_disabled);
        this.mSpinConfigValues = (Spinner) findViewById(R.id.sampling_rate_values_md_low_power);
        this.buttonSet = (Button) findViewById(R.id.md_low_power_monitor_set_button);
        this.mRadioBtnEnabled.setOnClickListener(this);
        this.mRadioBtnDisabled.setOnClickListener(this);
        this.buttonSet.setOnClickListener(this);
    }

    private int setMDLowPowerMonitorValue(int value) {
        return 0;
    }

    private void sendCommand(String[] command, int msg) {
        Elog.d(TAG, "Send Command " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mCommandHander.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.md_low_power_monitor_enabled) {
            sendCommand(new String[]{MD_MONITOR_ENABLE_CMD, ""}, 101);
        } else if (v.getId() == R.id.md_low_power_monitor_disabled) {
            sendCommand(new String[]{MD_MONITOR_DISABLE_CMD, ""}, 102);
        } else if (v.getId() == R.id.md_low_power_monitor_set_button) {
            Spinner spinner = this.mSpinConfigValues;
            String value = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            Elog.e(TAG, "value = " + value);
            String msg_value = String.format("\"%08x\"", new Object[]{Integer.valueOf(Integer.parseInt(value) * 1000 * 1000)});
            Elog.e(TAG, "msg_value = " + msg_value);
            sendCommand(new String[]{MD_MONITOR_CONFIG_CMD + msg_value, ""}, 103);
        }
    }
}
