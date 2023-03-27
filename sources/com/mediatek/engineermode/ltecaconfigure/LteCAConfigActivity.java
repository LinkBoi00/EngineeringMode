package com.mediatek.engineermode.ltecaconfigure;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class LteCAConfigActivity extends Activity {
    private static final String FORE_CMD = "+ECASW:";
    private static final int MSG_QUERY_CA_CMD = 101;
    private static final int MSG_SET_CA_CMD = 102;
    private static final String QUERY_CA_CMD = "AT+ECASW?";
    private static final String SET_CA_CMD = "AT+ECASW=";
    private static final String TAG = "LteCAConfigActivity";
    private final Handler mCommandHander = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(LteCAConfigActivity.TAG, "Receive msg from modem");
            AsyncResult asyncResult = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 101:
                    if (asyncResult == null || asyncResult.exception != null || asyncResult.result == null) {
                        LteCAConfigActivity.this.showToast("Query lte CA status failed.");
                        Elog.d(LteCAConfigActivity.TAG, "Query lte CA status failed.");
                        return;
                    }
                    String[] result = (String[]) asyncResult.result;
                    Elog.d(LteCAConfigActivity.TAG, "Query lte CA status succeed,result = " + result[0]);
                    int mode = LteCAConfigActivity.this.parseCurrentLteCAMode(result[0]);
                    Elog.d(LteCAConfigActivity.TAG, "mode = " + mode);
                    if (mode == 0) {
                        LteCAConfigActivity.this.mRadioBtnOff.setChecked(true);
                        return;
                    } else {
                        LteCAConfigActivity.this.mRadioBtnOn.setChecked(true);
                        return;
                    }
                case 102:
                    if (asyncResult.exception == null) {
                        LteCAConfigActivity.this.showToast("set LTE CA Succeed!");
                        Elog.d(LteCAConfigActivity.TAG, "set LTE CA Succeed!");
                        return;
                    }
                    LteCAConfigActivity.this.showToast("set LTE CA failed!");
                    Elog.d(LteCAConfigActivity.TAG, "set LTE CA failed!");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioButton mRadioBtnOff;
    /* access modifiers changed from: private */
    public RadioButton mRadioBtnOn;
    private Toast mToast = null;

    /* access modifiers changed from: private */
    public int parseCurrentLteCAMode(String data) {
        try {
            return Integer.valueOf(data.substring(FORE_CMD.length()).trim()).intValue();
        } catch (NumberFormatException e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lte_ca_config_activity);
        this.mRadioBtnOn = (RadioButton) findViewById(R.id.lte_ca_on_radio);
        this.mRadioBtnOff = (RadioButton) findViewById(R.id.lte_ca_off_radio);
        queryCAStatus();
        ((Button) findViewById(R.id.lte_ca_set_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (LteCAConfigActivity.this.mRadioBtnOn.isChecked()) {
                    LteCAConfigActivity.this.sendAtCommand("1");
                    Elog.i(LteCAConfigActivity.TAG, "Set LTE CA Status : on");
                } else if (LteCAConfigActivity.this.mRadioBtnOff.isChecked()) {
                    LteCAConfigActivity.this.sendAtCommand("0");
                    Elog.i(LteCAConfigActivity.TAG, "Set LTE CA Status : off");
                }
            }
        });
    }

    private void queryCAStatus() {
        sendCommand(new String[]{QUERY_CA_CMD, FORE_CMD}, 101);
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String cmd) {
        sendCommand(new String[]{SET_CA_CMD + cmd, ""}, 102);
    }

    private void sendCommand(String[] command, int msg) {
        Elog.d(TAG, "sendCommand " + command[0]);
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
}
