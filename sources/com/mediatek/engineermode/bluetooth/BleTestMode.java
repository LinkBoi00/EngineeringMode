package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class BleTestMode extends BtTestBaseActivity implements View.OnClickListener {
    private static final int ARG_RX = 0;
    private static final int ARG_TX = 1;
    private static final int CHANNEL_NUM = 40;
    private static final int MSG_OP_TEST_START = 11;
    private static final int MSG_OP_TEST_STOP = 12;
    private static final int MSG_UI_START_DONE = 1;
    private static final int MSG_UI_STOP_DONE = 2;
    private static final String TAG = "BleTestMode";
    private Button mBtnStart = null;
    private Button mBtnStop = null;
    /* access modifiers changed from: private */
    public byte mChannelValue = 0;
    /* access modifiers changed from: private */
    public byte mPatternValue = 0;
    /* access modifiers changed from: private */
    public String mResultStr = "R:";
    private RadioGroup mRgMode = null;
    private Spinner mSpChannel = null;
    private Spinner mSpPattern = null;
    /* access modifiers changed from: private */
    public TextView mTvResult = null;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(BleTestMode.TAG, "receive msg of " + msg.what);
            switch (msg.what) {
                case 1:
                    BleTestMode.this.enableUI(false);
                    BleTestMode.this.removeWaitDlg();
                    return;
                case 2:
                    if (msg.arg1 == 0) {
                        BleTestMode.this.mTvResult.setText(BleTestMode.this.mResultStr);
                    }
                    BleTestMode.this.enableUI(true);
                    BleTestMode.this.removeWaitDlg();
                    return;
                default:
                    return;
            }
        }
    };
    private WorkHandler mWorkHandler = null;

    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.bt_le_test_mode);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
    }

    private void initComponent() {
        initAntComponent();
        this.mBtnStart = (Button) findViewById(R.id.ble_test_start_btn);
        this.mBtnStop = (Button) findViewById(R.id.ble_test_stop_btn);
        this.mTvResult = (TextView) findViewById(R.id.ble_test_result_tv);
        this.mRgMode = (RadioGroup) findViewById(R.id.ble_test_mode_rg);
        this.mSpChannel = (Spinner) findViewById(R.id.ble_test_ch_sp);
        this.mSpPattern = (Spinner) findViewById(R.id.ble_test_pattern_sp);
        this.mBtnStart.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        ArrayAdapter<String> mSpnChannelAdapter = new ArrayAdapter<>(this, 17367048);
        mSpnChannelAdapter.setDropDownViewResource(17367049);
        for (int i = 0; i < 40; i++) {
            mSpnChannelAdapter.add(getString(R.string.bt_le_test_channnel) + i);
        }
        this.mSpChannel.setAdapter(mSpnChannelAdapter);
        this.mSpChannel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Elog.i(BleTestMode.TAG, "mSpChannel item " + arg2);
                byte unused = BleTestMode.this.mChannelValue = (byte) arg2;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        arrayAdapter.add(getResources().getStringArray(R.array.bt_le_test_pattern));
        this.mSpPattern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Elog.i(BleTestMode.TAG, "mSpPattern item " + arg2);
                byte unused = BleTestMode.this.mPatternValue = (byte) arg2;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        enableUI(true);
    }

    private char[] sendTestStopCmd() {
        char[] cmd = {1, 31, ' ', 0};
        return this.mBtTest.hciCommandRun(cmd, cmd.length);
    }

    /* access modifiers changed from: private */
    public void startTxTest() {
        this.mBtTest.init();
        switchAnt(true);
        this.mBtTest.hciCommandRun(new char[]{1, 30, ' ', 3, (char) this.mChannelValue, '%', (char) this.mPatternValue}, 7);
    }

    /* access modifiers changed from: private */
    public void startRxTest() {
        this.mBtTest.init();
        switchAnt(true);
        char[] cmd = {1, 29, ' ', 1, (char) this.mChannelValue};
        this.mBtTest.hciCommandRun(cmd, cmd.length);
    }

    /* access modifiers changed from: private */
    public void stopTest(int type) {
        Elog.v(TAG, "handleRxTestStop");
        char[] response = sendTestStopCmd();
        if (type == 0 && response != null) {
            this.mResultStr = String.format("***Packet Count: %d", new Object[]{Long.valueOf((((long) response[8]) * 256) + ((long) response[7]))});
        }
        this.mBtTest.unInit();
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnStart)) {
            showWaitDlg();
            Message msg = this.mWorkHandler.obtainMessage(11);
            if (this.mRgMode.getCheckedRadioButtonId() == R.id.ble_test_tx_rb) {
                msg.arg1 = 1;
            } else {
                msg.arg1 = 0;
            }
            this.mWorkHandler.sendMessage(msg);
        } else if (v.equals(this.mBtnStop)) {
            showWaitDlg();
            Message msg2 = this.mWorkHandler.obtainMessage(12);
            if (this.mRgMode.getCheckedRadioButtonId() == R.id.ble_test_tx_rb) {
                msg2.arg1 = 1;
            } else {
                msg2.arg1 = 0;
            }
            this.mWorkHandler.sendMessage(msg2);
        }
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        int count = this.mRgMode.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgMode.getChildAt(k).setEnabled(enabled);
        }
        this.mSpChannel.setEnabled(enabled);
        this.mSpPattern.setEnabled(enabled);
        this.mBtnStart.setEnabled(enabled);
        this.mBtnStop.setEnabled(!enabled);
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    if (msg.arg1 == 1) {
                        BleTestMode.this.startTxTest();
                    } else {
                        BleTestMode.this.startRxTest();
                    }
                    BleTestMode.this.mUiHandler.sendEmptyMessage(1);
                    return;
                case 12:
                    BleTestMode.this.stopTest(msg.arg1);
                    Message msgStopResult = BleTestMode.this.mUiHandler.obtainMessage(2);
                    msgStopResult.arg1 = msg.arg1;
                    BleTestMode.this.mUiHandler.sendMessage(msgStopResult);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStart.isEnabled();
    }
}
