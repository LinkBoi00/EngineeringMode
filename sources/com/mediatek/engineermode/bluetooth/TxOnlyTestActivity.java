package com.mediatek.engineermode.bluetooth;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TxOnlyTestActivity extends BtTestBaseActivity implements View.OnClickListener {
    private static final char[] CHANNEL_HOP_20 = {0, 1, 2};
    private static final int DEFAULT_POWER_VALUE = 8;
    private static final int ET_TYPE_PKT_LEN = 2;
    private static final int ET_TYPE_TX_CH = 1;
    private static final int MSG_OP_TEST_START = 11;
    private static final int MSG_OP_TEST_STOP = 12;
    private static final int MSG_UI_START_DONE = 1;
    private static final int MSG_UI_STOP_DONE = 2;
    private static final int POWER_INVALID = -1;
    private static final char[] SUPPORT_RESPONSE = {4, 14, 4, 1, 144, 253, 0};
    private static final String TAG = "BtTxOnlyTest";
    private static final int TX_PKT_DATA_MAX_INDEX = 2;
    private static final int TX_PKT_DATA_MIN_INDEX = 1;
    private static final int TX_PKT_DATA_NAME_INDEX = 0;
    private Button mBtnStart = null;
    private Button mBtnStop = null;
    private int mChannel;
    private EditText mEtFreq = null;
    private EditText mEtPktLength = null;
    /* access modifiers changed from: private */
    public View mLayoutPower;
    private int mPattern;
    /* access modifiers changed from: private */
    public ArrayList<TxPktData> mPktDataList = new ArrayList<>();
    private int mPktType;
    private int mPktTypeLen;
    private RadioGroup mRgPowerLevel;
    private Spinner mSpChannels = null;
    private Spinner mSpPattern = null;
    private Spinner mSpPktTypes = null;
    private Spinner mSpPower;
    /* access modifiers changed from: private */
    public TextView mTvPktLength = null;
    private int mTxCh;
    /* access modifiers changed from: private */
    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(TxOnlyTestActivity.TAG, "receive msg of " + msg.what);
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == BtTest.BtTestResult.RESULT_SUCCESS.ordinal()) {
                        TxOnlyTestActivity.this.enableUI(false);
                    } else {
                        Toast.makeText(TxOnlyTestActivity.this, R.string.bt_tx_start_failed, 1).show();
                    }
                    TxOnlyTestActivity.this.removeWaitDlg();
                    return;
                case 2:
                    TxOnlyTestActivity.this.enableUI(true);
                    TxOnlyTestActivity.this.removeWaitDlg();
                    return;
                default:
                    return;
            }
        }
    };
    private Handler mWorkHandler = null;

    private enum ParamValid {
        PARAM_VALID_VALID,
        PARAM_VALID_PKT_LEN_EXCEED,
        PARAM_VALID_PKT_LEN_NO_VALUE,
        PARAM_VALID_TX_CH_NO_VALUE
    }

    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_tx_only_test);
        this.mWorkHandler = new WorkHandler(getWorkLooper());
        initComponent();
        setConfigNeeded(true);
    }

    private final class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Elog.i(TxOnlyTestActivity.TAG, "work handler msg:" + msg.what);
            switch (msg.what) {
                case 11:
                    Message msgResultStart = TxOnlyTestActivity.this.mUiHandler.obtainMessage(1);
                    msgResultStart.arg1 = TxOnlyTestActivity.this.startTest().ordinal();
                    TxOnlyTestActivity.this.mUiHandler.sendMessage(msgResultStart);
                    return;
                case 12:
                    TxOnlyTestActivity.this.mBtTest.runHCIResetCmd();
                    TxOnlyTestActivity.this.mBtTest.unInit();
                    TxOnlyTestActivity.this.mUiHandler.sendEmptyMessage(2);
                    return;
                default:
                    return;
            }
        }
    }

    private BtTest.BtTestResult setPower(int level) {
        char[] cmd = {1, 'y', 252, 6, (char) level, 0, 0, 0, (char) level, (char) level};
        return this.mBtTest.hciCommandRun(cmd, cmd.length) != null ? BtTest.BtTestResult.RESULT_SUCCESS : BtTest.BtTestResult.RESULT_TEST_FAIL;
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
                TxOnlyTestActivity.this.mLayoutPower.setVisibility(checkedId == R.id.bt_power_cus_rb ? 0 : 8);
            }
        });
        this.mSpPattern = (Spinner) findViewById(R.id.bt_tx_pattern_sp);
        ArrayAdapter<CharSequence> adapterPattern = ArrayAdapter.createFromResource(this, R.array.bt_tx_pattern, 17367048);
        adapterPattern.setDropDownViewResource(17367049);
        this.mSpPattern.setAdapter(adapterPattern);
        this.mTvPktLength = (TextView) findViewById(R.id.bt_tx_pkt_length_tv);
        this.mSpPktTypes = (Spinner) findViewById(R.id.bt_tx_pkt_type_sp);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        initPktDataList();
        Iterator<TxPktData> it = this.mPktDataList.iterator();
        while (it.hasNext()) {
            arrayAdapter.add(it.next().mName);
        }
        this.mSpPktTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String limits;
                TxPktData data = (TxPktData) TxOnlyTestActivity.this.mPktDataList.get(position);
                new String();
                if (data.mMin == data.mMax) {
                    limits = "[" + data.mMin + "]";
                } else {
                    limits = "[" + data.mMin + "~" + data.mMax + "]";
                }
                TxOnlyTestActivity.this.mTvPktLength.setText(TxOnlyTestActivity.this.getResources().getString(R.string.bt_tx_pkt_length) + limits);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpPktTypes.setAdapter(arrayAdapter);
        this.mSpChannels = (Spinner) findViewById(R.id.bt_tx_channel_sp);
        Button button = (Button) findViewById(R.id.bt_tx_start_btn);
        this.mBtnStart = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.bt_tx_stop_btn);
        this.mBtnStop = button2;
        button2.setOnClickListener(this);
        this.mEtFreq = (EditText) findViewById(R.id.bt_tx_freq_et);
        this.mEtPktLength = (EditText) findViewById(R.id.bt_tx_pkt_length_et);
        enableUI(true);
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enabled) {
        enableAntSwap(enabled);
        this.mBtnStart.setEnabled(enabled);
        this.mBtnStop.setEnabled(!enabled);
        this.mSpChannels.setEnabled(enabled);
        this.mSpPattern.setEnabled(enabled);
        this.mSpPktTypes.setEnabled(enabled);
        this.mEtFreq.setEnabled(enabled);
        this.mEtPktLength.setEnabled(enabled);
        int count = this.mRgPowerLevel.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgPowerLevel.getChildAt(k).setEnabled(enabled);
        }
        this.mSpPower.setEnabled(enabled);
    }

    /* access modifiers changed from: private */
    public BtTest.BtTestResult startTest() {
        Elog.i(TAG, "PocketType:" + this.mPktType + " Frequency:" + this.mTxCh);
        this.mBtTest.init();
        if (!switchAnt(true)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        } else if (this.mRgPowerLevel.getCheckedRadioButtonId() == R.id.bt_power_cus_rb && setPower(this.mSpPower.getSelectedItemPosition()).equals(BtTest.BtTestResult.RESULT_TEST_FAIL)) {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        } else if (this.mBtTest.doTxOnlyTest(this.mPattern, this.mChannel, this.mPktType, this.mPktTypeLen, this.mTxCh)) {
            return BtTest.BtTestResult.RESULT_SUCCESS;
        } else {
            this.mBtTest.unInit();
            return BtTest.BtTestResult.RESULT_TEST_FAIL;
        }
    }

    private ParamValid getEditBoxValue(EditText editText, int flag) {
        String str = editText.getText().toString();
        if (str == null || str.isEmpty()) {
            Elog.i(TAG, "editText:" + editText + " str " + str + " flag:" + flag);
            if (flag == 1) {
                return ParamValid.PARAM_VALID_TX_CH_NO_VALUE;
            }
            return ParamValid.PARAM_VALID_PKT_LEN_NO_VALUE;
        }
        int value = Integer.parseInt(str);
        if (flag == 1) {
            this.mTxCh = value;
        } else if (flag == 2) {
            this.mPktTypeLen = value;
        }
        return ParamValid.PARAM_VALID_VALID;
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnStart)) {
            showWaitDlg();
            ParamValid checkResult = checkAndGetParameters();
            int resId = -1;
            switch (AnonymousClass4.$SwitchMap$com$mediatek$engineermode$bluetooth$TxOnlyTestActivity$ParamValid[checkResult.ordinal()]) {
                case 1:
                    resId = R.string.bt_param_pkt_lenth_exceed;
                    break;
                case 2:
                    resId = R.string.bt_param_no_pkt_length;
                    break;
                case 3:
                    resId = R.string.bt_param_no_tx_ch;
                    break;
            }
            if (checkResult != ParamValid.PARAM_VALID_VALID) {
                Toast.makeText(this, getText(resId), 1).show();
                removeWaitDlg();
                return;
            }
            this.mWorkHandler.sendEmptyMessage(11);
        } else if (v.equals(this.mBtnStop)) {
            showWaitDlg();
            this.mWorkHandler.sendEmptyMessage(12);
        }
    }

    /* renamed from: com.mediatek.engineermode.bluetooth.TxOnlyTestActivity$4  reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$bluetooth$TxOnlyTestActivity$ParamValid;

        static {
            int[] iArr = new int[ParamValid.values().length];
            $SwitchMap$com$mediatek$engineermode$bluetooth$TxOnlyTestActivity$ParamValid = iArr;
            try {
                iArr[ParamValid.PARAM_VALID_PKT_LEN_EXCEED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$bluetooth$TxOnlyTestActivity$ParamValid[ParamValid.PARAM_VALID_PKT_LEN_NO_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$bluetooth$TxOnlyTestActivity$ParamValid[ParamValid.PARAM_VALID_TX_CH_NO_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private ParamValid checkAndGetParameters() {
        ParamValid result = getEditBoxValue(this.mEtFreq, 1);
        if (result != ParamValid.PARAM_VALID_VALID) {
            return result;
        }
        ParamValid result2 = getEditBoxValue(this.mEtPktLength, 2);
        if (result2 != ParamValid.PARAM_VALID_VALID) {
            return result2;
        }
        int selectedItemPosition = this.mSpPktTypes.getSelectedItemPosition();
        this.mPktType = selectedItemPosition;
        if (this.mPktTypeLen > this.mPktDataList.get(selectedItemPosition).mMax || this.mPktTypeLen < this.mPktDataList.get(this.mPktType).mMin) {
            return ParamValid.PARAM_VALID_PKT_LEN_EXCEED;
        }
        this.mPattern = this.mSpPattern.getSelectedItemPosition();
        this.mChannel = this.mSpChannels.getSelectedItemPosition();
        return ParamValid.PARAM_VALID_VALID;
    }

    /* access modifiers changed from: protected */
    public boolean couldExit() {
        return this.mBtnStart.isEnabled();
    }

    /* access modifiers changed from: protected */
    public void handleInitResult(BtTest.BtTestResult result) {
        Resources res = getResources();
        ArrayList<String> arrayCh = new ArrayList<>();
        arrayCh.addAll(Arrays.asList(res.getStringArray(R.array.bt_tx_channels)));
        if (result.equals(BtTest.BtTestResult.RESULT_CHANNEL_HOP_20_SUPPORT)) {
            arrayCh.addAll(Arrays.asList(res.getStringArray(R.array.bt_tx_channels_20)));
        }
        ArrayAdapter<String> adapterChannels = new ArrayAdapter<>(this, 17367048, arrayCh);
        adapterChannels.setDropDownViewResource(17367049);
        this.mSpChannels.setAdapter(adapterChannels);
    }

    /* access modifiers changed from: protected */
    public BtTest.BtTestResult initParams() {
        BtTest.BtTestResult result = BtTest.BtTestResult.RESULT_CHANNEL_HOP_20_SUPPORT;
        for (char value : CHANNEL_HOP_20) {
            char[] cmd = {1, 144, 253, 1, value};
            char[] res = this.mBtTest.hciCommandRun(cmd, cmd.length);
            int length = res.length;
            char[] cArr = SUPPORT_RESPONSE;
            if (length != cArr.length || !Arrays.equals(cArr, res)) {
                return BtTest.BtTestResult.RESULT_CHANNEL_HOP_20_NO_SUPPORT;
            }
        }
        return result;
    }

    private void initPktDataList() {
        for (String strPktData : getResources().getStringArray(R.array.bt_tx_pkt_data)) {
            String[] arrayData = strPktData.split(",");
            this.mPktDataList.add(new TxPktData(arrayData[0], Integer.valueOf(arrayData[1]).intValue(), Integer.valueOf(arrayData[2]).intValue()));
        }
    }

    private class TxPktData {
        /* access modifiers changed from: private */
        public int mMax;
        /* access modifiers changed from: private */
        public int mMin;
        /* access modifiers changed from: private */
        public String mName;

        TxPktData(String name, int min, int max) {
            this.mName = name;
            this.mMin = min;
            this.mMax = max;
            Elog.i(TxOnlyTestActivity.TAG, "init pkt data with " + name + " " + min + " " + max);
        }

        /* access modifiers changed from: package-private */
        public String getName() {
            return this.mName;
        }

        /* access modifiers changed from: package-private */
        public int getMin() {
            return this.mMin;
        }

        /* access modifiers changed from: package-private */
        public int getMax() {
            return this.mMax;
        }
    }
}
