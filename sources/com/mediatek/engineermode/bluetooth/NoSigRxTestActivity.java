package com.mediatek.engineermode.bluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

public class NoSigRxTestActivity extends BtTestBaseActivity implements View.OnClickListener {
    private static final int DEFAULT_ADDR = 10875075;
    private static final int FREQ_MAX_VALUE = 78;
    private static final int MSG_OP_BT_START = 11;
    private static final int MSG_OP_BT_STOP = 12;
    private static final int MSG_UI_START_DONE = 2;
    private static final int MSG_UI_STOP_DONE = 4;
    private static final String TAG = "BtNoSigRxTest";
    private int mAddr = -1;
    private Button mBtnStartTest;
    private Button mBtnStopTest;
    private EditText mEtAddr;
    private EditText mEtFreq;
    private int mFreq = 0;
    private int mPattern = -1;
    private int mPocketType = -1;
    /* access modifiers changed from: private */
    public int[] mResult = null;
    private Spinner mSpPattern;
    private Spinner mSpPocketType;
    /* access modifiers changed from: private */
    public TextView mTvBitErrRate;
    /* access modifiers changed from: private */
    public TextView mTvPackCnt;
    /* access modifiers changed from: private */
    public TextView mTvPackErrRate;
    /* access modifiers changed from: private */
    public TextView mTvRxByteCnt;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(NoSigRxTestActivity.TAG, "receive msg of " + msg.what);
            switch (msg.what) {
                case 2:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_TEST_FAIL.ordinal()) {
                        NoSigRxTestActivity noSigRxTestActivity = NoSigRxTestActivity.this;
                        Toast.makeText(noSigRxTestActivity, noSigRxTestActivity.getText(R.string.bt_rx_start_fail), 1).show();
                    } else {
                        NoSigRxTestActivity.this.enableUI(false);
                    }
                    NoSigRxTestActivity.this.removeWaitDlg();
                    return;
                case 4:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_SUCCESS.ordinal()) {
                        NoSigRxTestActivity.this.mTvPackCnt.setText(String.valueOf(NoSigRxTestActivity.this.mResult[0]));
                        TextView access$400 = NoSigRxTestActivity.this.mTvPackErrRate;
                        access$400.setText(String.format("%.2f", new Object[]{Double.valueOf(((double) NoSigRxTestActivity.this.mResult[1]) / 100.0d)}) + "%");
                        NoSigRxTestActivity.this.mTvRxByteCnt.setText(String.valueOf(NoSigRxTestActivity.this.mResult[2]));
                        TextView access$600 = NoSigRxTestActivity.this.mTvBitErrRate;
                        access$600.setText(String.format("%.2f", new Object[]{Double.valueOf(((double) NoSigRxTestActivity.this.mResult[3]) / 100.0d)}) + "%");
                        NoSigRxTestActivity.this.enableUI(true);
                    } else {
                        NoSigRxTestActivity noSigRxTestActivity2 = NoSigRxTestActivity.this;
                        Toast.makeText(noSigRxTestActivity2, noSigRxTestActivity2.getText(R.string.bt_rx_stop_fail), 1).show();
                    }
                    NoSigRxTestActivity.this.removeWaitDlg();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_no_sig_rx_test);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        this.mSpPattern.setEnabled(enabled);
        this.mSpPocketType.setEnabled(enabled);
        this.mEtFreq.setEnabled(enabled);
        this.mEtAddr.setEnabled(enabled);
        this.mBtnStartTest.setEnabled(enabled);
        this.mBtnStopTest.setEnabled(!enabled);
    }

    private void initComponent() {
        initAntComponent();
        this.mEtFreq = (EditText) findViewById(R.id.bt_rx_freq_et);
        this.mEtAddr = (EditText) findViewById(R.id.bt_rx_addr_et);
        this.mTvPackCnt = (TextView) findViewById(R.id.bt_rx_pkt_cnt_tv);
        this.mTvPackErrRate = (TextView) findViewById(R.id.bt_rx_pkt_err_rate_tv);
        this.mTvRxByteCnt = (TextView) findViewById(R.id.bt_rx_pkt_byte_cnt_tv);
        this.mTvBitErrRate = (TextView) findViewById(R.id.bt_rx_bit_err_rate_tv);
        this.mSpPattern = (Spinner) findViewById(R.id.bt_rx_pattern_sp);
        ArrayAdapter<CharSequence> adapterPattern = ArrayAdapter.createFromResource(this, R.array.bt_rx_pattern, 17367048);
        adapterPattern.setDropDownViewResource(17367049);
        this.mSpPattern.setAdapter(adapterPattern);
        this.mSpPocketType = (Spinner) findViewById(R.id.bt_rx_pocket_sp);
        ArrayAdapter<CharSequence> adapterPocketType = ArrayAdapter.createFromResource(this, R.array.bt_rx_pocket_type, 17367048);
        adapterPocketType.setDropDownViewResource(17367049);
        this.mSpPocketType.setAdapter(adapterPocketType);
        this.mBtnStartTest = (Button) findViewById(R.id.bt_rx_start_btn);
        this.mBtnStopTest = (Button) findViewById(R.id.bt_rx_stop_btn);
        this.mBtnStartTest.setOnClickListener(this);
        this.mBtnStopTest.setOnClickListener(this);
        enableUI(true);
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    Message msgResultStart = NoSigRxTestActivity.this.mUiHandler.obtainMessage(2);
                    msgResultStart.arg1 = NoSigRxTestActivity.this.startTest().ordinal();
                    NoSigRxTestActivity.this.mUiHandler.sendMessage(msgResultStart);
                    return;
                case 12:
                    Message msgResultStop = NoSigRxTestActivity.this.mUiHandler.obtainMessage(4);
                    msgResultStop.arg1 = NoSigRxTestActivity.this.stopTest().ordinal();
                    NoSigRxTestActivity.this.mUiHandler.sendMessage(msgResultStop);
                    return;
                default:
                    return;
            }
        }
    }

    public void onClick(View arg0) {
        if (arg0.equals(this.mBtnStartTest)) {
            showWaitDlg();
            if (!checkAndGetParameters()) {
                Toast.makeText(this, getText(R.string.bt_invalid_param), 1).show();
                removeWaitDlg();
                return;
            }
            this.mWorkHandler.sendEmptyMessage(11);
        } else if (arg0.equals(this.mBtnStopTest)) {
            showWaitDlg();
            this.mWorkHandler.sendEmptyMessage(12);
        }
    }

    private boolean checkAndGetParameters() {
        String strAddr;
        String strFreq = this.mEtFreq.getText().toString();
        if (strFreq == null || strFreq.isEmpty() || (strAddr = this.mEtAddr.getText().toString()) == null || strAddr.isEmpty()) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(strFreq);
            this.mFreq = parseInt;
            if (parseInt >= 0) {
                if (parseInt <= 78) {
                    int parseLong = (int) Long.parseLong(strAddr, 16);
                    this.mAddr = parseLong;
                    if (parseLong == 0) {
                        this.mAddr = DEFAULT_ADDR;
                        this.mEtAddr.setText(String.valueOf(DEFAULT_ADDR));
                    }
                    this.mPattern = this.mSpPattern.getSelectedItemPosition();
                    this.mPocketType = this.mSpPocketType.getSelectedItemPosition();
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException e) {
            Elog.e(TAG, e.getMessage());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult startTest() {
        this.mBtTest.init();
        if (!switchAnt(true)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        } else if (this.mBtTest.noSigRxTestStart(this.mPattern, this.mPocketType, this.mFreq, this.mAddr)) {
            return BtTest.BtTestResult.RESULT_SUCCESS;
        } else {
            this.mBtTest.unInit();
            Elog.e(TAG, "no signal rx test failed.");
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult stopTest() {
        this.mResult = this.mBtTest.noSigRxTestResult();
        this.mBtTest.unInit();
        if (this.mResult != null) {
            return BtTest.BtTestResult.RESULT_SUCCESS;
        }
        Elog.e(TAG, "no signal rx test failed.");
        return BtTest.BtTestResult.RESULT_TEST_FAIL;
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStartTest.isEnabled();
    }
}
