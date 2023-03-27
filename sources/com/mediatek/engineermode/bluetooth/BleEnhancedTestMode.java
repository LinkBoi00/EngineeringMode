package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

public class BleEnhancedTestMode extends BtTestBaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int ARG_RX = 0;
    private static final int ARG_TX = 1;
    private static final int CHANNEL_NUM = 40;
    private static final int MSG_OP_TEST_START = 11;
    private static final int MSG_OP_TEST_STOP = 12;
    private static final int MSG_UI_START_DONE = 1;
    private static final int MSG_UI_STOP_DONE = 2;
    private static final int PL_LENGTH_MAX = 255;
    private static final int PL_LENGTH_MIN = 0;
    private static final String TAG = "BLEEhTestMode";
    private boolean m2MPhySupport = false;
    private Button mBtnStart;
    private Button mBtnStop;
    private int mChannelValue;
    private boolean mCodedPhySupport = false;
    private EditText mEtPlLength;
    private int mPatternValue;
    private int mPhyValue;
    private int mPlLength;
    private RadioGroup mRGTestMode;
    /* access modifiers changed from: private */
    public String mResultStr;
    private Spinner mSpChannel;
    private Spinner mSpPattern;
    private Spinner mSpPhy;
    private ArrayAdapter<String> mSpPhyRxAdapter;
    private ArrayAdapter<String> mSpPhyTxAdapter;
    /* access modifiers changed from: private */
    public TextView mTvResult;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(BleEnhancedTestMode.TAG, "Rec ui msg " + msg);
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_SUCCESS.ordinal()) {
                        BleEnhancedTestMode.this.enableUI(false);
                    } else {
                        Toast.makeText(BleEnhancedTestMode.this, R.string.bt_test_start_failed, 1).show();
                    }
                    BleEnhancedTestMode.this.removeWaitDlg();
                    return;
                case 2:
                    if (msg.arg1 == 0) {
                        BleEnhancedTestMode.this.mTvResult.setText(BleEnhancedTestMode.this.mResultStr);
                    }
                    BleEnhancedTestMode.this.enableUI(true);
                    BleEnhancedTestMode.this.removeWaitDlg();
                    return;
                default:
                    return;
            }
        }
    };
    private View mViewPattern;
    private View mViewPlLength;
    private WorkHandler mWorkHandler = null;

    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.bt_le_enhanced_test_mode);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
        setConfigNeeded(true);
    }

    private void initComponent() {
        initAntComponent();
        this.mViewPattern = findViewById(R.id.ble_eh_test_pattern_layout);
        this.mViewPlLength = findViewById(R.id.ble_eh_test_pl_length_layout);
        this.mBtnStart = (Button) findViewById(R.id.ble_eh_test_start_btn);
        this.mBtnStop = (Button) findViewById(R.id.ble_eh_test_stop_btn);
        this.mTvResult = (TextView) findViewById(R.id.ble_eh_test_result_tv);
        this.mSpChannel = (Spinner) findViewById(R.id.ble_eh_test_channel_sp);
        this.mSpPattern = (Spinner) findViewById(R.id.ble_eh_test_pattern_sp);
        this.mSpPhy = (Spinner) findViewById(R.id.ble_eh_test_phy_sp);
        this.mEtPlLength = (EditText) findViewById(R.id.ble_eh_test_pl_length_et);
        this.mBtnStart.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        ArrayAdapter<String> spChannelAdapter = new ArrayAdapter<>(this, 17367048);
        spChannelAdapter.setDropDownViewResource(17367049);
        for (int i = 0; i < 40; i++) {
            spChannelAdapter.add(getString(R.string.bt_le_eh_test_channnel) + i);
        }
        this.mSpChannel.setAdapter(spChannelAdapter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mSpPhyTxAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mSpPhyRxAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.ble_eh_test_mode_rg);
        this.mRGTestMode = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        this.mRGTestMode.check(R.id.ble_eh_test_tx_rb);
        enableUI(true);
    }

    private void updatePhyList() {
        this.mSpPhyTxAdapter.add(getResources().getString(R.string.bt_le_eh_test_1m_phy));
        this.mSpPhyRxAdapter.add(getResources().getString(R.string.bt_le_eh_test_1m_phy));
        if (this.m2MPhySupport) {
            this.mSpPhyTxAdapter.add(getResources().getString(R.string.bt_le_eh_test_2m_phy));
            this.mSpPhyRxAdapter.add(getResources().getString(R.string.bt_le_eh_test_2m_phy));
        }
        if (this.mCodedPhySupport) {
            this.mSpPhyTxAdapter.add(getResources().getString(R.string.bt_le_eh_test_coded_phy_8));
            this.mSpPhyTxAdapter.add(getResources().getString(R.string.bt_le_eh_test_coded_phy_2));
            this.mSpPhyRxAdapter.add(getResources().getString(R.string.bt_le_eh_test_coded_phy));
        }
        if (this.mRGTestMode.getCheckedRadioButtonId() == R.id.ble_eh_test_tx_rb) {
            this.mSpPhy.setAdapter(this.mSpPhyTxAdapter);
        } else {
            this.mSpPhy.setAdapter(this.mSpPhyRxAdapter);
        }
    }

    private char[] runStopTestCmd() {
        char[] cmd = {1, 31, ' ', 0};
        return this.mBtTest.hciCommandRun(cmd, cmd.length);
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        int count = this.mRGTestMode.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRGTestMode.getChildAt(k).setEnabled(enabled);
        }
        this.mSpChannel.setEnabled(enabled);
        this.mSpPattern.setEnabled(enabled);
        this.mSpPhy.setEnabled(enabled);
        this.mEtPlLength.setEnabled(enabled);
        this.mBtnStart.setEnabled(enabled);
        this.mBtnStop.setEnabled(!enabled);
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult startTxTest() {
        if (!this.mBtTest.init()) {
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
        if (!switchAnt(true)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
        char[] cmd = {1, '4', ' ', 4, (char) this.mChannelValue, (char) this.mPlLength, (char) this.mPatternValue, (char) this.mPhyValue};
        if (this.mBtTest.hciCommandRun(cmd, cmd.length) != null) {
            return BtTest.BtTestResult.RESULT_SUCCESS;
        }
        this.mBtTest.unInit();
        return BtTest.BtTestResult.RESULT_TEST_FAIL;
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult startRxTest() {
        if (!this.mBtTest.init()) {
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
        if (!switchAnt(true)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
        char[] cmd = {1, '3', ' ', 3, (char) this.mChannelValue, (char) this.mPhyValue, 0};
        if (this.mBtTest.hciCommandRun(cmd, cmd.length) != null) {
            return BtTest.BtTestResult.RESULT_SUCCESS;
        }
        this.mBtTest.unInit();
        return BtTest.BtTestResult.RESULT_TEST_FAIL;
    }

    /* access modifiers changed from: private */
    public void stopTest(int type) {
        char[] response = runStopTestCmd();
        if (type == 0 && response != null && response.length >= 9) {
            this.mResultStr = String.format("***Packet Count: %d", new Object[]{Long.valueOf((((long) response[8]) * 256) + ((long) response[7]))});
        }
        this.mBtTest.unInit();
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    Message msgResultStart = BleEnhancedTestMode.this.mUiHandler.obtainMessage(1);
                    if (msg.arg1 == 1) {
                        msgResultStart.arg1 = BleEnhancedTestMode.this.startTxTest().ordinal();
                    } else {
                        msgResultStart.arg1 = BleEnhancedTestMode.this.startRxTest().ordinal();
                    }
                    BleEnhancedTestMode.this.mUiHandler.sendMessage(msgResultStart);
                    return;
                case 12:
                    Message msgResultStop = BleEnhancedTestMode.this.mUiHandler.obtainMessage(2);
                    BleEnhancedTestMode.this.stopTest(msg.arg1);
                    msgResultStop.arg1 = msg.arg1;
                    BleEnhancedTestMode.this.mUiHandler.sendMessage(msgResultStop);
                    return;
                default:
                    return;
            }
        }
    }

    private void listPhy() {
        char[] cmd = {1, 3, ' ', 0};
        char[] response = this.mBtTest.hciCommandRun(cmd, cmd.length);
        if (response != null && response.length >= 3 && response[6] == 0) {
            boolean z = true;
            this.m2MPhySupport = (response[8] & 1) != 0;
            if ((8 & response[8]) == 0) {
                z = false;
            }
            this.mCodedPhySupport = z;
        }
    }

    private boolean checkAndGetParameters() {
        this.mChannelValue = this.mSpChannel.getSelectedItemPosition();
        this.mPhyValue = this.mSpPhy.getSelectedItemPosition() + 1;
        if (this.mRGTestMode.getCheckedRadioButtonId() == R.id.ble_eh_test_tx_rb) {
            try {
                int intValue = Integer.valueOf(this.mEtPlLength.getText().toString()).intValue();
                this.mPlLength = intValue;
                if (intValue < 0 || intValue > 255) {
                    return false;
                }
                this.mPatternValue = this.mSpPattern.getSelectedItemPosition();
            } catch (NumberFormatException e) {
                return false;
            }
        }
        this.mTvResult.setText("");
        return true;
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnStart)) {
            Elog.i(TAG, "click start");
            showWaitDlg();
            if (!checkAndGetParameters()) {
                Toast.makeText(this, getText(R.string.bt_invalid_param), 1).show();
                removeWaitDlg();
                return;
            }
            Message msg = this.mWorkHandler.obtainMessage(11);
            if (this.mRGTestMode.getCheckedRadioButtonId() == R.id.ble_eh_test_tx_rb) {
                msg.arg1 = 1;
            } else {
                msg.arg1 = 0;
            }
            this.mWorkHandler.sendMessage(msg);
        } else if (v.equals(this.mBtnStop)) {
            Elog.i(TAG, "click stop");
            showWaitDlg();
            Message msg2 = this.mWorkHandler.obtainMessage(12);
            if (this.mRGTestMode.getCheckedRadioButtonId() == R.id.ble_eh_test_tx_rb) {
                msg2.arg1 = 1;
            } else {
                msg2.arg1 = 0;
            }
            this.mWorkHandler.sendMessage(msg2);
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.ble_eh_test_tx_rb) {
            this.mViewPlLength.setVisibility(0);
            this.mViewPattern.setVisibility(0);
            this.mSpPhy.setAdapter(this.mSpPhyTxAdapter);
            return;
        }
        this.mViewPlLength.setVisibility(8);
        this.mViewPattern.setVisibility(8);
        this.mSpPhy.setAdapter(this.mSpPhyRxAdapter);
    }

    /* access modifiers changed from: protected */
    public BtTest.BtTestResult initParams() {
        listPhy();
        return BtTest.BtTestResult.RESULT_SUCCESS;
    }

    /* access modifiers changed from: protected */
    public void handleInitResult(BtTest.BtTestResult result) {
        updatePhyList();
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStart.isEnabled();
    }
}
