package com.mediatek.engineermode.ltehpueconfigure;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class LteHPUEConfigureActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String CMD_QUERY = "+ESBP:";
    private static final String CMD_QUERY_HPUE = "AT+ESBP=7,\"SBP_HPUE\"";
    private static final String CMD_SET_HPUE = "AT+ESBP=5,\"SBP_HPUE\",";
    private static final int MSG_QUERY_HPUE = 1;
    private static final int MSG_SET = 3;
    private static final String TAG = "LteHPUEConfigureActivity";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LteHPUEConfigureActivity lteHPUEConfigureActivity = LteHPUEConfigureActivity.this;
                    boolean unused = lteHPUEConfigureActivity.mLteHPUEChecked = lteHPUEConfigureActivity.parseData((AsyncResult) msg.obj);
                    LteHPUEConfigureActivity.this.mLteHPUEConfigureCb.setChecked(LteHPUEConfigureActivity.this.mLteHPUEChecked);
                    return;
                case 3:
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception != null) {
                        Elog.e(LteHPUEConfigureActivity.TAG, ar.exception.getMessage());
                        EmUtils.showToast("Failed to set", 0);
                        Elog.w(LteHPUEConfigureActivity.TAG, "Failed to set");
                        return;
                    }
                    EmUtils.showToast("Succeed to set", 0);
                    Elog.i(LteHPUEConfigureActivity.TAG, "Succeed to set");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mLteHPUEChecked = false;
    /* access modifiers changed from: private */
    public CheckBox mLteHPUEConfigureCb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lte_hpue_configure);
        CheckBox checkBox = (CheckBox) findViewById(R.id.lte_hpue_configure);
        this.mLteHPUEConfigureCb = checkBox;
        checkBox.setOnCheckedChangeListener(this);
    }

    public void onResume() {
        super.onResume();
        sendAtCommand(new String[]{CMD_QUERY_HPUE, CMD_QUERY}, 1);
    }

    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        if (view == this.mLteHPUEConfigureCb && this.mLteHPUEChecked != isChecked) {
            this.mLteHPUEChecked = isChecked;
            sendAtCommand(new String[]{CMD_SET_HPUE + (isChecked), ""}, 3);
        }
    }

    private void sendAtCommand(String[] command, int msg) {
        Elog.i(TAG, "sendAtCommand() " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mHandler.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public boolean parseData(AsyncResult ar) {
        if (ar.exception != null) {
            Elog.e(TAG, ar.exception.getMessage());
        } else if (ar.result != null && (ar.result instanceof String[])) {
            String[] data = (String[]) ar.result;
            if (data.length > 0 && data[0].length() > CMD_QUERY.length()) {
                Elog.v(TAG, "data[0] is : " + data[0]);
                try {
                    if (Integer.valueOf(data[0].substring(CMD_QUERY.length()).trim()).intValue() == 1) {
                        return true;
                    }
                    return false;
                } catch (NumberFormatException e) {
                    Elog.e(TAG, e.getMessage());
                }
            }
        }
        EmUtils.showToast("Failed to query current settings", 0);
        return false;
    }
}
