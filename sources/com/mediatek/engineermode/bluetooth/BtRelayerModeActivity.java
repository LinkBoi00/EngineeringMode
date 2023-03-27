package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

public class BtRelayerModeActivity extends BtTestBaseActivity implements View.OnClickListener {
    private static final int INDEX_USB = 4;
    private static final int MSG_OP_TEST_START = 11;
    private static final int MSG_OP_TEST_STOP = 12;
    private static final int MSG_UI_TEST_START = 1;
    private static final int MSG_UI_TEST_STOP = 2;
    private static final String TAG = "BtRelayerMode";
    /* access modifiers changed from: private */
    public int mBaudrate = 9600;
    private Button mBtnStart;
    private Button mBtnStop;
    /* access modifiers changed from: private */
    public int mPortNumber = 3;
    /* access modifiers changed from: private */
    public Spinner mSpBaudrate;
    private Spinner mSpUartPort;
    /* access modifiers changed from: private */
    public final Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal()) {
                        Toast.makeText(BtRelayerModeActivity.this, R.string.bt_test_start_failed, 0).show();
                    } else {
                        BtRelayerModeActivity.this.enableUI(false);
                    }
                    BtRelayerModeActivity.this.removeWaitDlg();
                    return;
                case 2:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal()) {
                        BtRelayerModeActivity btRelayerModeActivity = BtRelayerModeActivity.this;
                        Toast.makeText(btRelayerModeActivity, btRelayerModeActivity.getText(R.string.bt_test_stop_failed), 1).show();
                    } else {
                        BtRelayerModeActivity.this.enableUI(true);
                    }
                    BtRelayerModeActivity.this.removeWaitDlg();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_relayer_mode);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
    }

    private void initComponent() {
        initAntComponent();
        this.mSpBaudrate = (Spinner) findViewById(R.id.bt_relayer_baudrate_sp);
        Spinner spinner = (Spinner) findViewById(R.id.bt_relayer_port_sp);
        this.mSpUartPort = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (arg2 == 4) {
                    BtRelayerModeActivity.this.mSpBaudrate.setEnabled(false);
                } else {
                    BtRelayerModeActivity.this.mSpBaudrate.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mBtnStart = (Button) findViewById(R.id.bt_relayer_start_btn);
        this.mBtnStop = (Button) findViewById(R.id.bt_relayer_stop_btn);
        this.mBtnStart.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        enableUI(true);
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        if (!enabled || (enabled && this.mSpUartPort.getSelectedItemPosition() != 4)) {
            this.mSpBaudrate.setEnabled(enabled);
        }
        this.mSpUartPort.setEnabled(enabled);
        this.mBtnStart.setEnabled(enabled);
        this.mBtnStop.setEnabled(!enabled);
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 11) {
                Message msgResult = BtRelayerModeActivity.this.mUiHandler.obtainMessage(1);
                if (!BtRelayerModeActivity.this.mBtTest.startRelayerMode(BtRelayerModeActivity.this.mPortNumber, BtRelayerModeActivity.this.mBaudrate) || !BtRelayerModeActivity.this.switchAnt(false)) {
                    msgResult.arg1 = BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal();
                } else {
                    msgResult.arg1 = BtTest.BtTestResult.RESULT_SUCCESS.ordinal();
                }
                Elog.i(BtRelayerModeActivity.TAG, "-->relayerStart-" + BtRelayerModeActivity.this.mBaudrate + " uart " + BtRelayerModeActivity.this.mPortNumber + "result= " + msgResult.arg1);
                BtRelayerModeActivity.this.mUiHandler.sendMessage(msgResult);
            } else if (msg.what == 12) {
                Message msgResult2 = BtRelayerModeActivity.this.mUiHandler.obtainMessage(2);
                msgResult2.arg1 = (BtRelayerModeActivity.this.mBtTest.stopRelayerMode() ? BtTest.BtTestResult.RESULT_SUCCESS : BtTest.BtTestResult.RESULT_TEST_FAIL).ordinal();
                BtRelayerModeActivity.this.mUiHandler.sendMessage(msgResult2);
            }
        }
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnStart)) {
            showWaitDlg();
            if (!checkAndGetParameters()) {
                Toast.makeText(this, getText(R.string.bt_invalid_param), 1).show();
                removeWaitDlg();
                return;
            }
            this.mWorkHandler.sendEmptyMessage(11);
        } else if (v.equals(this.mBtnStop)) {
            showWaitDlg();
            this.mWorkHandler.sendEmptyMessage(12);
        }
    }

    private boolean checkAndGetParameters() {
        try {
            this.mBaudrate = Integer.parseInt(this.mSpBaudrate.getSelectedItem().toString().trim());
            this.mPortNumber = Long.valueOf(this.mSpUartPort.getSelectedItemId()).intValue();
            return true;
        } catch (NumberFormatException e) {
            Elog.e(TAG, e.getMessage());
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStart.isEnabled();
    }
}
