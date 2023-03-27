package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

public class TestModeActivity extends BtTestBaseActivity {
    private static final int DEFAULT_POWER_VALUE = 8;
    private static final int MSG_OP_TEST_START = 11;
    private static final int MSG_OP_TEST_STOP = 12;
    private static final int MSG_UI_TEST_START = 1;
    private static final int MSG_UI_TEST_STOP = 2;
    private static final int POWER_INVALID = -1;
    private static final String TAG = "BtTestMode";
    private Button mBtnDisableTm;
    private Button mBtnEnableTm;
    /* access modifiers changed from: private */
    public View mLayoutPower;
    /* access modifiers changed from: private */
    public RadioGroup mRgPowerLevel;
    /* access modifiers changed from: private */
    public Spinner mSpPower;
    /* access modifiers changed from: private */
    public final Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(TestModeActivity.TAG, "receive msg of " + msg.what);
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal()) {
                        Toast.makeText(TestModeActivity.this, R.string.bt_tm_start_failed, 0).show();
                    } else {
                        TestModeActivity.this.enableUI(false);
                    }
                    TestModeActivity.this.removeWaitDlg();
                    return;
                case 2:
                    TestModeActivity.this.enableUI(true);
                    TestModeActivity.this.removeWaitDlg();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public WorkHandler mWorkHandler = null;

    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_test_mode);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
    }

    private void initComponent() {
        initAntComponent();
        this.mLayoutPower = findViewById(R.id.bt_power_level_ll);
        Spinner spinner = (Spinner) findViewById(R.id.bt_power_level_sp);
        this.mSpPower = spinner;
        spinner.setSelection(8);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bt_power_rg);
        this.mRgPowerLevel = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TestModeActivity.this.mLayoutPower.setVisibility(checkedId == R.id.bt_power_cus_rb ? 0 : 8);
            }
        });
        Button button = (Button) findViewById(R.id.bt_tm_enable);
        this.mBtnEnableTm = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TestModeActivity.this.showWaitDlg();
                TestModeActivity.this.mWorkHandler.sendEmptyMessage(11);
            }
        });
        Button button2 = (Button) findViewById(R.id.bt_tm_disable);
        this.mBtnDisableTm = button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TestModeActivity.this.showWaitDlg();
                TestModeActivity.this.mWorkHandler.sendEmptyMessage(12);
            }
        });
        enableUI(true);
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        int count = this.mRgPowerLevel.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgPowerLevel.getChildAt(k).setEnabled(enabled);
        }
        this.mSpPower.setEnabled(enabled);
        this.mBtnEnableTm.setEnabled(enabled);
        this.mBtnDisableTm.setEnabled(!enabled);
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 11) {
                Message msgResult = TestModeActivity.this.mUiHandler.obtainMessage(1);
                TestModeActivity.this.mBtTest.init();
                int power = -1;
                if (TestModeActivity.this.mRgPowerLevel.getCheckedRadioButtonId() == R.id.bt_power_cus_rb) {
                    power = TestModeActivity.this.mSpPower.getSelectedItemPosition();
                }
                if (!TestModeActivity.this.mBtTest.enableTestMode(power)) {
                    TestModeActivity.this.mBtTest.unInit();
                    msgResult.arg1 = BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal();
                } else if (!TestModeActivity.this.switchAnt(true)) {
                    TestModeActivity.this.mBtTest.unInit();
                    msgResult.arg1 = BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal();
                } else {
                    TestModeActivity.this.mBtTest.pollingStart();
                    msgResult.arg1 = BtTest.BtTestResult.RESULT_SUCCESS.ordinal();
                }
                TestModeActivity.this.mUiHandler.sendMessage(msgResult);
            } else if (msg.what == 12) {
                TestModeActivity.this.mBtTest.pollingStop();
                TestModeActivity.this.mBtTest.runHCIResetCmd();
                TestModeActivity.this.mBtTest.unInit();
                TestModeActivity.this.mUiHandler.sendEmptyMessage(2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnEnableTm.isEnabled();
    }
}
