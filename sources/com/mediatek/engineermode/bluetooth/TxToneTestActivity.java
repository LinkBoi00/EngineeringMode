package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

public class TxToneTestActivity extends BtTestBaseActivity implements View.OnClickListener {
    private static final int DEFAULT_POWER_VALUE = 8;
    private static final int FREQ_MAX_VALUE = 78;
    private static final int MSG_OP_BT_START = 11;
    private static final int MSG_OP_BT_STOP = 12;
    private static final int MSG_UI_START_DONE = 1;
    private static final int MSG_UI_STOP_DONE = 2;
    private static final String TAG = "TxTone";
    private Button mBtnStart;
    private Button mBtnStop;
    private EditText mEtFreq;
    private int mFreq;
    /* access modifiers changed from: private */
    public View mLayoutPower;
    private RadioGroup mRgPowerLevel;
    private Spinner mSpModulation;
    private Spinner mSpPattern;
    private Spinner mSpPower;
    private Spinner mSpType;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal()) {
                        TxToneTestActivity txToneTestActivity = TxToneTestActivity.this;
                        Toast.makeText(txToneTestActivity, txToneTestActivity.getText(R.string.bt_tx_tone_start_fail), 1).show();
                    } else {
                        TxToneTestActivity.this.enableUI(false);
                    }
                    TxToneTestActivity.this.removeWaitDlg();
                    return;
                case 2:
                    TxToneTestActivity.this.enableUI(true);
                    TxToneTestActivity.this.removeWaitDlg();
                    return;
                default:
                    return;
            }
        }
    };
    private Handler mWorkHandler = null;

    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStart.isEnabled();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_tx_tone_test);
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
                TxToneTestActivity.this.mLayoutPower.setVisibility(checkedId == R.id.bt_power_cus_rb ? 0 : 8);
            }
        });
        this.mSpType = (Spinner) findViewById(R.id.bt_tx_tone_type_sp);
        this.mEtFreq = (EditText) findViewById(R.id.bt_tx_tone_freq_et);
        this.mSpModulation = (Spinner) findViewById(R.id.bt_tx_tone_modulation_sp);
        this.mSpPattern = (Spinner) findViewById(R.id.bt_tx_tone_pattern_sp);
        Button button = (Button) findViewById(R.id.bt_tx_tone_start_btn);
        this.mBtnStart = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.bt_tx_tone_stop_btn);
        this.mBtnStop = button2;
        button2.setOnClickListener(this);
        enableUI(true);
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        this.mBtnStart.setEnabled(enabled);
        this.mBtnStop.setEnabled(!enabled);
        this.mSpType.setEnabled(enabled);
        this.mEtFreq.setEnabled(enabled);
        this.mSpModulation.setEnabled(enabled);
        this.mSpPattern.setEnabled(enabled);
        int count = this.mRgPowerLevel.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgPowerLevel.getChildAt(k).setEnabled(enabled);
        }
        this.mSpPower.setEnabled(enabled);
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnStart)) {
            showWaitDlg();
            if (!checkAndGetFreq()) {
                Toast.makeText(this, getText(R.string.bt_tx_tone_freq_invalid), 1).show();
                removeWaitDlg();
                return;
            }
            this.mWorkHandler.sendEmptyMessage(11);
        } else if (v.equals(this.mBtnStop)) {
            showWaitDlg();
            this.mWorkHandler.sendEmptyMessage(12);
        }
    }

    private boolean checkAndGetFreq() {
        String strFreq = this.mEtFreq.getText().toString();
        if (strFreq == null || strFreq.isEmpty()) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(strFreq);
            this.mFreq = parseInt;
            if (parseInt < 0 || parseInt > 78) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            Elog.e(TAG, e.getMessage());
            return false;
        }
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    Message msgResultStart = TxToneTestActivity.this.mUiHandler.obtainMessage(1);
                    msgResultStart.arg1 = TxToneTestActivity.this.startTest().ordinal();
                    TxToneTestActivity.this.mUiHandler.sendMessage(msgResultStart);
                    return;
                case 12:
                    TxToneTestActivity.this.stopTest();
                    TxToneTestActivity.this.mUiHandler.sendEmptyMessage(2);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult startTest() {
        if (!this.mBtTest.init()) {
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
        if (!switchAnt(true)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        } else if (this.mRgPowerLevel.getCheckedRadioButtonId() != R.id.bt_power_cus_rb || !setPower(this.mSpPower.getSelectedItemPosition()).equals(BtTest.BtTestResult.RESULT_TEST_FAIL)) {
            int type = this.mSpType.getSelectedItemPosition() == 1 ? 4 : 0;
            int modulation = this.mSpModulation.getSelectedItemPosition();
            char[] cmd = {1, 213, 252, 5, (char) this.mFreq, (char) type, (char) (modulation >= 3 ? 1 : 0), (char) modulation, (char) this.mSpPattern.getSelectedItemPosition()};
            if (this.mBtTest.hciCommandRun(cmd, cmd.length) != null) {
                return BtTest.BtTestResult.RESULT_SUCCESS;
            }
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        } else {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
    }

    /* access modifiers changed from: private */
    public void stopTest() {
        this.mBtTest.runHCIResetCmd();
        this.mBtTest.unInit();
    }

    private BtTest.BtTestResult setPower(int level) {
        char[] cmd = {1, 'y', 252, 6, (char) level, 0, 0, 0, (char) level, (char) level};
        return this.mBtTest.hciCommandRun(cmd, cmd.length) != null ? BtTest.BtTestResult.RESULT_SUCCESS : BtTest.BtTestResult.RESULT_TEST_FAIL;
    }
}
