package com.mediatek.engineermode.bluetooth;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bluetooth.BtTest;

abstract class BtTestBaseActivity extends Activity {
    private static final int DLG_INIT_CONFIG = 2;
    private static final int DLG_WAIT = 1;
    private static final int MSG_OP_ANT_SWAP = 11;
    private static final int MSG_OP_INIT_CONFIG = 12;
    private static final int MSG_UI_ANT_SWAP_RESULT = 1;
    private static final int MSG_UI_INIT_CONFIG_DONE = 3;
    private static final int MSG_UI_INIT_CONFIG_START = 2;
    private static final String TAG = "BtTestBaseActivity";
    private int mAntCurrentId = R.id.bt_ant0_rb;
    private boolean mAntSwapSupport = false;
    /* access modifiers changed from: private */
    public Handler mBaseUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(BtTestBaseActivity.TAG, "UI handler receive msg of " + msg.what + " param " + msg.arg1);
            switch (msg.what) {
                case 2:
                    BtTestBaseActivity.this.showDialog(2);
                    return;
                case 3:
                    BtTestBaseActivity.this.removeDialog(2);
                    BtTestBaseActivity.this.handleInitResult(BtTest.BtTestResult.values()[msg.arg1]);
                    return;
                default:
                    return;
            }
        }
    };
    private Handler mBaseWorkHandler = null;
    private BluetoothAdapter mBtAdapter;
    protected BtTest mBtTest = new BtTest();
    private boolean mConfigInited = false;
    private boolean mNeedConfigInit = false;
    private RadioGroup mRgAntSwap;
    private HandlerThread mWorkThread = null;

    /* access modifiers changed from: protected */
    public abstract boolean couldExit();

    BtTestBaseActivity() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread handlerThread = new HandlerThread(getLocalClassName());
        this.mWorkThread = handlerThread;
        handlerThread.start();
        this.mBaseWorkHandler = new BaseOpHandler(getWorkLooper());
    }

    /* access modifiers changed from: protected */
    public void showWaitDlg() {
        showDialog(1);
    }

    /* access modifiers changed from: protected */
    public void removeWaitDlg() {
        removeDialog(1);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.mBtAdapter == null) {
            this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (!this.mBtTest.checkInitState(this.mBtAdapter, this)) {
            finish();
        }
        if (!this.mConfigInited) {
            this.mConfigInited = true;
            if (this.mNeedConfigInit) {
                this.mBaseWorkHandler.sendEmptyMessage(12);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                ProgressDialog dlgWait = new ProgressDialog(this);
                dlgWait.setMessage(getString(R.string.bt_operation_wait));
                dlgWait.setCancelable(false);
                dlgWait.setIndeterminate(true);
                return dlgWait;
            case 2:
                ProgressDialog dlgInitConfig = new ProgressDialog(this);
                dlgInitConfig.setMessage(getString(R.string.bt_config_init));
                dlgInitConfig.setCancelable(false);
                dlgInitConfig.setIndeterminate(true);
                return dlgInitConfig;
            default:
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        HandlerThread handlerThread = this.mWorkThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        if (!couldExit()) {
            Toast.makeText(this, R.string.bt_warn_stop, 1).show();
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public Looper getWorkLooper() {
        return this.mWorkThread.getLooper();
    }

    /* access modifiers changed from: protected */
    public void initAntComponent() {
        boolean z = true;
        int i = 0;
        if (getIntent().getIntExtra("ant_num", 1) != 2) {
            z = false;
        }
        this.mAntSwapSupport = z;
        View findViewById = findViewById(R.id.bt_ant_swap_ll);
        if (!this.mAntSwapSupport) {
            i = 8;
        }
        findViewById.setVisibility(i);
        if (this.mAntSwapSupport) {
            this.mRgAntSwap = (RadioGroup) findViewById(R.id.bt_ant_rg);
        }
    }

    private final class BaseOpHandler extends Handler {
        private BaseOpHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Elog.i(BtTestBaseActivity.TAG, "work handler msg:" + msg.what);
            if (msg.what == 12) {
                BtTestBaseActivity.this.mBaseUiHandler.sendEmptyMessage(2);
                Message msgFinish = BtTestBaseActivity.this.mBaseUiHandler.obtainMessage(3);
                BtTestBaseActivity.this.mBtTest.init();
                msgFinish.arg1 = BtTestBaseActivity.this.initParams().ordinal();
                BtTestBaseActivity.this.mBtTest.unInit();
                BtTestBaseActivity.this.mBaseUiHandler.sendMessage(msgFinish);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void enableAntSwap(boolean enabled) {
        if (this.mAntSwapSupport) {
            int antCount = this.mRgAntSwap.getChildCount();
            for (int k = 0; k < antCount; k++) {
                this.mRgAntSwap.getChildAt(k).setEnabled(enabled);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setConfigNeeded(boolean need) {
        this.mNeedConfigInit = need;
    }

    /* access modifiers changed from: protected */
    public BtTest.BtTestResult initParams() {
        return BtTest.BtTestResult.RESULT_SUCCESS;
    }

    /* access modifiers changed from: protected */
    public boolean switchAnt(boolean checkRes) {
        int antIndex = 1;
        if (!this.mAntSwapSupport) {
            return true;
        }
        if (this.mRgAntSwap.getCheckedRadioButtonId() != R.id.bt_ant1_rb) {
            antIndex = 0;
        }
        return this.mBtTest.switchAnt(antIndex, checkRes);
    }

    /* access modifiers changed from: protected */
    public void handleInitResult(BtTest.BtTestResult result) {
    }
}
