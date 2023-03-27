package com.mediatek.engineermode.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.wifi.WifiCapability;
import com.mediatek.engineermode.wifi.WifiFormatConfig;
import java.util.LinkedHashMap;
import java.util.List;

public class WiFiTxFormat extends WiFiTestActivity implements View.OnClickListener {
    private static final int DEFAULT_AIFS = 50;
    private static final int DEFAULT_PKT_CNT = 0;
    private static final int DEFAULT_PKT_LENGTH = 1024;
    private static final int DEFAULT_POWER = 22;
    private static final int DEFAULT_PRIM_CH = 0;
    private static final int DEFAULT_RU_STA_ID = 0;
    private static final int DUTY_DEFAULT_PKT_CNT = 100;
    private static final int MAX_AIFS = 255;
    private static final int MSG_EVENT_FINISH = 4;
    private static final int MSG_EVENT_GO = 1;
    private static final int MSG_EVENT_STOP = 2;
    private static final int MSG_EVENT_UPDATE_TX_COUNT = 3;
    private static final int MSG_GET_TX_POWER = 5;
    private static final int ONE_SECOND = 1000;
    private static final String TAG = "WifiTX";
    private static final int TX_CMD_START = 1;
    private static final int TX_CMD_STOP = 0;
    private int mBandValue = 0;
    private Button mBtnGo;
    private Button mBtnStop;
    private boolean mCap11ax = false;
    private boolean mCap2by2 = false;
    private boolean mCapAntSwap = false;
    private boolean mCapBw160c = false;
    private boolean mCapBw160nc = false;
    private boolean mCapCh6G = false;
    private boolean mCapHwTx = false;
    private boolean mCapLdpc = false;
    private CheckBox mCbHwTx;
    private CheckBox mCbWf1;
    private CheckBox mCbWf2;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mCbwAdapter = null;
    private List<ChannelDataFormat> mChList;
    private ArrayAdapter<String> mChannelAdapter = null;
    /* access modifiers changed from: private */
    public WifiFormatConfig.Preamble mCurPreamble = null;
    private boolean mDbdcMode = false;
    private ArrayAdapter<String> mDbwAdapter = null;
    /* access modifiers changed from: private */
    public EditText mEtInterPktGap;
    /* access modifiers changed from: private */
    public EditText mEtPktCnt;
    private EditText mEtPktLength;
    /* access modifiers changed from: private */
    public EditText mEtPower;
    private EditText mEtRuStaId;
    private ArrayAdapter<String> mFecAdapter = null;
    private ArrayAdapter<String> mGiAdapter = null;
    /* access modifiers changed from: private */
    public WifiFormatConfig mParamConfig;
    private TxParam mPowerParam;
    /* access modifiers changed from: private */
    public ArrayAdapter<WifiFormatConfig.Preamble> mPreambleAdapter = null;
    private ArrayAdapter<String> mRateAdapter = null;
    private RadioButton mRbNss2;
    private RadioGroup mRgAnt;
    private RadioGroup mRgNss;
    private ArrayAdapter<String> mRuAllocationAdapter = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<WifiFormatConfig.RuConfig> mRuConfigAdapter = null;
    private ArrayAdapter<Integer> mRuIndexAdapter = null;
    /* access modifiers changed from: private */
    public Spinner mSpCbw;
    private Spinner mSpCh;
    private Spinner mSpDbw;
    private Spinner mSpFec;
    private Spinner mSpGi;
    /* access modifiers changed from: private */
    public Spinner mSpMode;
    private Spinner mSpPreamble;
    private Spinner mSpPrimCh;
    /* access modifiers changed from: private */
    public Spinner mSpRate;
    private Spinner mSpRuAllocation;
    private Spinner mSpRuConfig;
    private Spinner mSpRuIndex;
    /* access modifiers changed from: private */
    public TXTestMode mStopMode = null;
    /* access modifiers changed from: private */
    public int mTarPktCnt = 0;
    /* access modifiers changed from: private */
    public TXTestMode mTestMode;
    private ArrayAdapter<TXTestMode> mTestModeAdapter = null;
    private TextView mTvAFactor;
    private TextView mTvLSig;
    private TextView mTvLdpcExtraSym;
    private TextView mTvPeDisa;
    private TextView mTvTxPe;
    /* access modifiers changed from: private */
    public TextView mTvTxedCnt;
    /* access modifiers changed from: private */
    public TxHandler mTxHandler;
    /* access modifiers changed from: private */
    public GetTxInfoType mTxInfoBand = GetTxInfoType.TX_INFO_BAND0;
    /* access modifiers changed from: private */
    public TxParam mTxParam;
    private HandlerThread mTxThread;
    private View mViewRu;
    private CompoundButton.OnCheckedChangeListener mWfPathListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Elog.i(WiFiTxFormat.TAG, "getTxPower wf path with " + isChecked);
            WiFiTxFormat.this.getTxPower();
        }
    };

    private enum GetTxInfoType {
        TX_INFO_NONE,
        TX_INFO_BAND0,
        TX_INFO_BAND1
    }

    private class TxParam {
        int mAntIndex;
        int mCbwIndex;
        int mChID;
        int mChannelType;
        int mDbwIndex;
        int mFecValue;
        int mGiIndex;
        int mHwTx;
        int mInterPktGap;
        int mNssValue;
        int mPktLength;
        int mPower;
        int mPreambleIndex;
        int mPrimCh;
        int mRateIndex;
        int mWfSelect;

        private TxParam() {
        }

        public String toString() {
            return "mChID:" + this.mChID + " mCbwIndex:" + this.mCbwIndex + " mDbwIndex:" + this.mDbwIndex + " mPrimCh:" + this.mPrimCh + " mWfSelect:" + this.mWfSelect + " mChannelType:" + this.mChannelType + " mPktLength:" + this.mPktLength + " mPower:" + this.mPower + " mRateIndex:" + this.mRateIndex + " mPreambleIndex:" + this.mPreambleIndex + " mFecValue:" + this.mFecValue + " mGiIndex:" + this.mGiIndex + " mNssValue:" + this.mNssValue + " mInterPktGap:" + this.mInterPktGap + " mHwTx:" + this.mHwTx;
        }
    }

    private TxParam getPowerUpdateParam() {
        if (this.mSpRate.getSelectedItemPosition() < 0 || this.mSpGi.getSelectedItemPosition() < 0) {
            return null;
        }
        TxParam param = new TxParam();
        int chPos = this.mSpCh.getSelectedItemPosition();
        if (chPos >= this.mChList.size()) {
            Elog.i(TAG, "getPowerUpdateParam null for ch not ready");
            return null;
        }
        ChannelDataFormat channel = this.mChList.get(chPos);
        param.mChID = channel.getId();
        param.mChannelType = channel.getChBandType().ordinal();
        int i = 1;
        if (this.mCbWf1.isChecked()) {
            param.mWfSelect = this.mCbWf2.isChecked() ? 3 : 1;
        } else if (this.mCbWf2.isChecked()) {
            param.mWfSelect = 2;
        }
        param.mCbwIndex = this.mParamConfig.getMapBw().get(this.mSpCbw.getSelectedItem().toString()).intValue();
        param.mDbwIndex = this.mParamConfig.getMapBw().get(this.mSpDbw.getSelectedItem().toString()).intValue();
        param.mPrimCh = 0;
        try {
            param.mPrimCh = Integer.parseInt(this.mSpPrimCh.getSelectedItem().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        param.mRateIndex = this.mParamConfig.getMapRate().get(this.mSpRate.getSelectedItem().toString()).intValue();
        WifiFormatConfig.Preamble preamble = (WifiFormatConfig.Preamble) this.mSpPreamble.getSelectedItem();
        Elog.i(TAG, "preamble:" + preamble + " " + preamble.getIndex());
        param.mPreambleIndex = preamble.getIndex();
        param.mGiIndex = this.mSpGi.getSelectedItemPosition();
        if (this.mRgNss.getCheckedRadioButtonId() != R.id.wifi_format_tx_nss1_rb) {
            i = 2;
        }
        param.mNssValue = i;
        return param;
    }

    private TxParam getTxParam() {
        TxParam param = getPowerUpdateParam();
        if (param == null) {
            return null;
        }
        param.mPktLength = 1024;
        try {
            param.mPktLength = Integer.parseInt(this.mEtPktLength.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        param.mPower = 22;
        try {
            param.mPower = (int) (Float.parseFloat(this.mEtPower.getText().toString()) * 2.0f);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        param.mFecValue = getFecValue();
        param.mAntIndex = this.mRgAnt.getCheckedRadioButtonId() == R.id.wifi_format_tx_ant_main_rb ? 0 : 1;
        param.mInterPktGap = Integer.parseInt(this.mEtInterPktGap.getText().toString());
        param.mHwTx = this.mCbHwTx.isChecked() ? 1 : 0;
        return param;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        String mode = it.getStringExtra("mode");
        boolean z = true;
        if (mode != null) {
            this.mDbdcMode = true;
            if (mode.equals("TX Band1")) {
                this.mBandValue = 1;
                this.mTxInfoBand = GetTxInfoType.TX_INFO_BAND1;
            }
            Elog.i(TAG, "wifi tx in dbdc mode");
        }
        EMWifi.hqaSetBandMode(this.mDbdcMode ? 2 : 1);
        this.mCapAntSwap = it.getBooleanExtra("key_ant_swap", false);
        this.mCap11ax = it.getBooleanExtra("key_11ax", false);
        this.mCap2by2 = it.getBooleanExtra("key_2by2", false);
        this.mCapHwTx = it.getBooleanExtra("key_hw_tx", false);
        this.mCapLdpc = it.getBooleanExtra("key_ldpc", false);
        this.mCapBw160c = it.getBooleanExtra("key_bw_160c", false);
        this.mCapBw160nc = it.getBooleanExtra("key_bw_160nc", false);
        if (WifiCapability.CapChBand.CAP_CH_BAND_6G.ordinal() != it.getIntExtra("key_ch_band", WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT.ordinal())) {
            z = false;
        }
        this.mCapCh6G = z;
        this.mParamConfig = new WifiFormatConfig(this.mCapCh6G);
        setContentView(R.layout.wifi_tx_format);
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mTxThread = handlerThread;
        handlerThread.start();
        this.mTxHandler = new TxHandler(this.mTxThread.getLooper());
        initComponent();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Elog.i(TAG, "ondestory with mTxInfoBand " + this.mTxInfoBand);
        TxHandler txHandler = this.mTxHandler;
        if (txHandler != null) {
            txHandler.removeMessages(3);
        }
        this.mTxThread.quit();
        super.onDestroy();
    }

    private void initComponent() {
        this.mCbWf1 = (CheckBox) findViewById(R.id.wifi_format_tx_wf0);
        this.mCbWf2 = (CheckBox) findViewById(R.id.wifi_format_tx_wf1);
        this.mCbWf1.setChecked(true);
        this.mCbWf1.setOnCheckedChangeListener(this.mWfPathListener);
        this.mCbWf2.setOnCheckedChangeListener(this.mWfPathListener);
        this.mRgNss = (RadioGroup) findViewById(R.id.wifi_format_tx_nss_rg);
        this.mRbNss2 = (RadioButton) findViewById(R.id.wifi_format_tx_nss2_rb);
        this.mRgNss.check(R.id.wifi_format_tx_nss1_rb);
        this.mRgNss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Elog.i(WiFiTxFormat.TAG, "getTxPower nss");
                WiFiTxFormat.this.updateRateItems();
                WiFiTxFormat.this.getTxPower();
            }
        });
        if (!this.mCap2by2) {
            findViewById(R.id.wifi_format_tx_wf_ll).setVisibility(8);
            findViewById(R.id.wifi_format_tx_nss_ll).setVisibility(8);
        }
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.wifi_format_tx_ant_swap_rg);
        this.mRgAnt = radioGroup;
        radioGroup.check(R.id.wifi_format_tx_ant_main_rb);
        if (!this.mCapAntSwap) {
            findViewById(R.id.wifi_format_tx_ant_swap_ll).setVisibility(8);
        }
        this.mSpCh = (Spinner) findViewById(R.id.wifi_format_tx0_channel_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mSpCh.setAdapter(this.mChannelAdapter);
        this.mSpCh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Elog.i(WiFiTxFormat.TAG, "getTxPower ch for pos:" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mDbwAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) findViewById(R.id.wifi_format_tx_dbw_spinner);
        this.mSpDbw = spinner;
        spinner.setAdapter(this.mDbwAdapter);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, 17367048);
        this.mCbwAdapter = arrayAdapter3;
        arrayAdapter3.setDropDownViewResource(17367049);
        Spinner spinner2 = (Spinner) findViewById(R.id.wifi_format_tx_cbw_spinner);
        this.mSpCbw = spinner2;
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Elog.i(WiFiTxFormat.TAG, "mSpCbw onItemSelected:" + position);
                WiFiTxFormat.this.updateParamWithCbw(position);
                Elog.i(WiFiTxFormat.TAG, "getTxPower cbw with pos:" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpCbw.setAdapter(this.mCbwAdapter);
        this.mViewRu = findViewById(R.id.wifi_11_ax_ll);
        this.mSpPrimCh = (Spinner) findViewById(R.id.wifi_format_tx_prim_ch_spinner);
        this.mSpMode = (Spinner) findViewById(R.id.wifi_format_mode_spinner);
        ArrayAdapter<TXTestMode> arrayAdapter4 = new ArrayAdapter<>(this, 17367048);
        this.mTestModeAdapter = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(17367049);
        this.mTestModeAdapter.addAll(TXTestMode.values());
        this.mSpMode.setAdapter(this.mTestModeAdapter);
        this.mSpMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WiFiTxFormat.this.checkIfShowRuUI();
                if (((TXTestMode) WiFiTxFormat.this.mSpMode.getSelectedItem()).equals(TXTestMode.TM_100_PERCENT_DUTY)) {
                    WiFiTxFormat.this.mEtPktCnt.setText(String.valueOf(100));
                    WiFiTxFormat.this.mEtPktCnt.setEnabled(false);
                    return;
                }
                WiFiTxFormat.this.mEtPktCnt.setText(String.valueOf(0));
                WiFiTxFormat.this.mEtPktCnt.setEnabled(true);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Spinner spinner3 = (Spinner) findViewById(R.id.wifi_format_tx_preamble_spinner);
        this.mSpPreamble = spinner3;
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WiFiTxFormat wiFiTxFormat = WiFiTxFormat.this;
                WifiFormatConfig.Preamble unused = wiFiTxFormat.mCurPreamble = (WifiFormatConfig.Preamble) wiFiTxFormat.mPreambleAdapter.getItem(position);
                WiFiTxFormat wiFiTxFormat2 = WiFiTxFormat.this;
                wiFiTxFormat2.updateParamWithPreamble(wiFiTxFormat2.mCurPreamble);
                WiFiTxFormat.this.checkIfShowRuUI();
                Elog.i(WiFiTxFormat.TAG, "getTxPower preamble pos" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<WifiFormatConfig.Preamble> arrayAdapter5 = new ArrayAdapter<>(this, 17367048);
        this.mPreambleAdapter = arrayAdapter5;
        arrayAdapter5.setDropDownViewResource(17367049);
        this.mPreambleAdapter.addAll(this.mParamConfig.getPreambles(this.mCapLdpc, true, this.mCap11ax, this.mCapBw160c, this.mCapBw160nc));
        this.mSpPreamble.setAdapter(this.mPreambleAdapter);
        Spinner spinner4 = (Spinner) findViewById(R.id.wifi_format_rate_spinner);
        this.mSpRate = spinner4;
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String obj = WiFiTxFormat.this.mSpRate.getSelectedItem().toString();
                WiFiTxFormat.this.updateGiItems();
                Elog.i(WiFiTxFormat.TAG, "getTxPower rate pos:" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, 17367048);
        this.mRateAdapter = arrayAdapter6;
        arrayAdapter6.setDropDownViewResource(17367049);
        this.mSpRate.setAdapter(this.mRateAdapter);
        this.mSpGi = (Spinner) findViewById(R.id.wifi_format_guard_interval_spinner);
        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<>(this, 17367048);
        this.mGiAdapter = arrayAdapter7;
        arrayAdapter7.setDropDownViewResource(17367049);
        this.mSpGi.setAdapter(this.mGiAdapter);
        Spinner spinner5 = (Spinner) findViewById(R.id.wifi_ru_category_spinner);
        this.mSpRuConfig = spinner5;
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int index = WiFiTxFormat.this.mParamConfig.getMapBw().get((String) WiFiTxFormat.this.mCbwAdapter.getItem(WiFiTxFormat.this.mSpCbw.getSelectedItemPosition())).intValue();
                WiFiTxFormat.this.updateParamWithRuConfig(index, (WifiFormatConfig.RuConfig) WiFiTxFormat.this.mRuConfigAdapter.getItem(position));
                Elog.i(WiFiTxFormat.TAG, "getTxPower ru config pos:" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<WifiFormatConfig.RuConfig> arrayAdapter8 = new ArrayAdapter<>(this, 17367048);
        this.mRuConfigAdapter = arrayAdapter8;
        arrayAdapter8.setDropDownViewResource(17367049);
        this.mSpRuConfig.setAdapter(this.mRuConfigAdapter);
        this.mSpRuAllocation = (Spinner) findViewById(R.id.wifi_ru_allocation_spinner);
        ArrayAdapter<String> arrayAdapter9 = new ArrayAdapter<>(this, 17367048);
        this.mRuAllocationAdapter = arrayAdapter9;
        arrayAdapter9.setDropDownViewResource(17367049);
        this.mSpRuAllocation.setAdapter(this.mRuAllocationAdapter);
        this.mSpRuAllocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Elog.i(WiFiTxFormat.TAG, "getTxPower ru alloc pos:" + position);
                WiFiTxFormat.this.getTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpRuIndex = (Spinner) findViewById(R.id.wifi_ru_index_spinner);
        ArrayAdapter<Integer> arrayAdapter10 = new ArrayAdapter<>(this, 17367048);
        this.mRuIndexAdapter = arrayAdapter10;
        arrayAdapter10.setDropDownViewResource(17367049);
        this.mSpRuIndex.setAdapter(this.mRuIndexAdapter);
        this.mSpFec = (Spinner) findViewById(R.id.wifi_format_fec_spinner);
        ArrayAdapter<String> arrayAdapter11 = new ArrayAdapter<>(this, 17367048);
        this.mFecAdapter = arrayAdapter11;
        arrayAdapter11.setDropDownViewResource(17367049);
        this.mSpFec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WiFiTxFormat.this.updateRateItems();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpFec.setAdapter(this.mFecAdapter);
        EditText editText = (EditText) findViewById(R.id.wif_format_pkt_length_et);
        this.mEtPktLength = editText;
        editText.setText(String.valueOf(1024));
        EditText editText2 = (EditText) findViewById(R.id.wif_format_pkt_cnt_et);
        this.mEtPktCnt = editText2;
        editText2.setText(String.valueOf(0));
        EditText editText3 = (EditText) findViewById(R.id.wifi_format_tx_power_et);
        this.mEtPower = editText3;
        editText3.setText(String.valueOf(22));
        EditText editText4 = (EditText) findViewById(R.id.wifi_tx_ru_sta_id_et);
        this.mEtRuStaId = editText4;
        editText4.setText(String.valueOf(0));
        this.mTvLSig = (TextView) findViewById(R.id.wifi_11ax_tb_l_sig_len_tv);
        this.mTvLdpcExtraSym = (TextView) findViewById(R.id.wifi_11ax_tb_ldpc_extra_sym_tv);
        this.mTvPeDisa = (TextView) findViewById(R.id.wifi_11ax_tb_pe_disambiguity_tv);
        this.mTvAFactor = (TextView) findViewById(R.id.wifi_11ax_tb_a_factor_tv);
        this.mTvTxPe = (TextView) findViewById(R.id.wifi_11ax_tb_tx_pe_tv);
        this.mTvTxedCnt = (TextView) findViewById(R.id.wifi_format_tx_count_tv);
        Button button = (Button) findViewById(R.id.wifi_format_tx_go_btn);
        this.mBtnGo = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.wifi_format_tx_stop_btn);
        this.mBtnStop = button2;
        button2.setOnClickListener(this);
        if (this.mDbdcMode || !this.mCap2by2) {
            findViewById(R.id.wifi_format_tx_wf_ll).setVisibility(8);
            findViewById(R.id.wifi_format_tx_nss_ll).setVisibility(8);
        }
        EditText editText5 = (EditText) findViewById(R.id.wifi_format_tx_inter_pkt_gap);
        this.mEtInterPktGap = editText5;
        editText5.setText(String.valueOf(50));
        this.mEtInterPktGap.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0 && Integer.parseInt(s.toString()) > 255) {
                    WiFiTxFormat.this.mEtInterPktGap.setText(String.valueOf(255));
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mCbHwTx = (CheckBox) findViewById(R.id.wifi_format_tx_hw_enable_cb);
        if (!this.mCapHwTx) {
            findViewById(R.id.wifi_format_tx_inter_packet_gap_ll).setVisibility(8);
            findViewById(R.id.wifi_format_tx_hw_enable_ll).setVisibility(8);
        }
        setUiEnable(true);
    }

    /* access modifiers changed from: private */
    public void updateParamWithPreamble(WifiFormatConfig.Preamble preamble) {
        Elog.i(TAG, "updateParamWithPreamble");
        this.mCbwAdapter.clear();
        this.mCbwAdapter.addAll(preamble.getBwList());
        boolean z = true;
        if (this.mSpCbw.getSelectedItemPosition() >= this.mSpCbw.getCount()) {
            Spinner spinner = this.mSpCbw;
            spinner.setSelection(spinner.getCount() - 1);
        }
        this.mCbwAdapter.notifyDataSetChanged();
        boolean supportNss2 = preamble.isSupportNss2();
        if (this.mRbNss2.getVisibility() != 0) {
            z = false;
        }
        boolean showNss2 = z;
        if (showNss2 != supportNss2) {
            if (showNss2) {
                this.mRbNss2.setVisibility(4);
            } else {
                this.mRbNss2.setVisibility(0);
            }
        }
        updateRateItems();
        updateFecItems();
        updateGiItems();
    }

    /* access modifiers changed from: private */
    public void updateGiItems() {
        Elog.i(TAG, "updateGiItems:" + this.mCurPreamble + " position:" + this.mSpRate.getSelectedItemPosition());
        if (this.mSpRate.getSelectedItemPosition() >= 0 && this.mCurPreamble != null) {
            this.mGiAdapter.clear();
            this.mGiAdapter.addAll(this.mCurPreamble.getGiList(this.mParamConfig.getMapRate().get(this.mSpRate.getSelectedItem().toString()).intValue()));
            this.mGiAdapter.notifyDataSetChanged();
        }
    }

    private int getFecValue() {
        return WifiFormatConfig.getFecValue((String) this.mSpFec.getSelectedItem());
    }

    /* access modifiers changed from: private */
    public void updateRateItems() {
        this.mRateAdapter.clear();
        this.mRateAdapter.addAll(this.mCurPreamble.getRateList(this.mRgNss.getCheckedRadioButtonId() == R.id.wifi_format_tx_nss1_rb ? 1 : 2, getFecValue()));
        if (this.mSpRate.getSelectedItemPosition() >= this.mSpRate.getCount()) {
            Spinner spinner = this.mSpRate;
            spinner.setSelection(spinner.getCount() - 1);
        }
        this.mRateAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void updateParamWithCbw(int position) {
        String strCbw = this.mCbwAdapter.getItem(position);
        this.mChList = this.mParamConfig.getChannel(strCbw);
        this.mChannelAdapter.clear();
        for (ChannelDataFormat chData : this.mChList) {
            this.mChannelAdapter.add(chData.getName());
        }
        this.mChannelAdapter.notifyDataSetChanged();
        int index = this.mParamConfig.getMapBw().get(strCbw).intValue();
        LinkedHashMap<WifiFormatConfig.RuConfig, Integer> ruConfig = this.mParamConfig.getRuConfig(index);
        if (ruConfig != null) {
            int oldSelection = this.mSpRuConfig.getSelectedItemPosition();
            this.mRuConfigAdapter.clear();
            this.mRuConfigAdapter.addAll(ruConfig.keySet());
            this.mRuConfigAdapter.notifyDataSetChanged();
            if (oldSelection < 0 || oldSelection >= this.mRuConfigAdapter.getCount()) {
                this.mSpRuConfig.setSelection(0);
            } else {
                updateParamWithRuConfig(index, this.mRuConfigAdapter.getItem(this.mSpRuConfig.getSelectedItemPosition()));
            }
        }
        this.mDbwAdapter.clear();
        for (int k = 0; k <= position; k++) {
            this.mDbwAdapter.add(this.mCbwAdapter.getItem(k));
        }
        this.mDbwAdapter.notifyDataSetChanged();
        updateFecItems();
    }

    private void updateFecItems() {
        WifiFormatConfig.Preamble preamble;
        Elog.i(TAG, "updateFecItems:" + this.mCurPreamble + " position:" + this.mSpCbw.getSelectedItemPosition());
        if (this.mSpCbw.getSelectedItemPosition() >= 0 && (preamble = this.mCurPreamble) != null) {
            List<String> data = preamble.getFecList(this.mParamConfig.getMapBw().get(this.mSpCbw.getSelectedItem().toString()).intValue());
            if (fecOptionsChange(data)) {
                this.mFecAdapter.clear();
                this.mFecAdapter.addAll(data);
                this.mSpFec.setAdapter((SpinnerAdapter) null);
                this.mSpFec.setAdapter(this.mFecAdapter);
            }
        }
    }

    private boolean fecOptionsChange(List<String> data) {
        int oriCount = this.mFecAdapter.getCount();
        if (oriCount != data.size()) {
            return true;
        }
        for (int k = 0; k < oriCount; k++) {
            if (!this.mFecAdapter.getItem(k).equals(data.get(k))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void checkIfShowRuUI() {
        if (this.mCurPreamble != null) {
            this.mViewRu.setVisibility((!this.mCurPreamble.supportRu() || !((TXTestMode) this.mSpMode.getSelectedItem()).equals(TXTestMode.TM_CONTINUES)) ? 8 : 0);
        }
    }

    /* access modifiers changed from: private */
    public void updateParamWithRuConfig(int cbwIndex, WifiFormatConfig.RuConfig config) {
        if (config != null) {
            this.mRuAllocationAdapter.clear();
            ArrayAdapter<String> arrayAdapter = this.mRuAllocationAdapter;
            arrayAdapter.add("0x" + Integer.toHexString(config.getAllocation()));
            this.mRuAllocationAdapter.notifyDataSetChanged();
            this.mSpRuAllocation.setSelection(0);
            this.mRuIndexAdapter.clear();
            LinkedHashMap<WifiFormatConfig.RuConfig, Integer> configMap = this.mParamConfig.getRuConfig(cbwIndex);
            if (configMap != null) {
                int maxIndex = configMap.get(config).intValue();
                int offSet = config.getOffset();
                this.mRuIndexAdapter.clear();
                for (int k = 0; k <= maxIndex; k++) {
                    this.mRuIndexAdapter.add(Integer.valueOf(k + offSet));
                }
                this.mRuIndexAdapter.notifyDataSetChanged();
                this.mSpRuIndex.setSelection(0);
            }
        }
    }

    private class TxHandler extends Handler {
        public TxHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    switch (AnonymousClass14.$SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode[WiFiTxFormat.this.mTestMode.ordinal()]) {
                        case 1:
                            WiFiTxFormat.this.startContinuesTx();
                            return;
                        case 2:
                            WiFiTxFormat.this.startDutyTx();
                            return;
                        case 3:
                            WiFiTxFormat.this.startCwToneTx();
                            return;
                        default:
                            return;
                    }
                case 2:
                    WiFiTxFormat.this.stopTx();
                    return;
                case 3:
                    int[] pkgCnt = {0};
                    EMWifi.hqaGetTxInfo(WiFiTxFormat.this.mTxInfoBand.ordinal(), pkgCnt);
                    Elog.i(WiFiTxFormat.TAG, "hqaGetTxInfo:" + GetTxInfoType.TX_INFO_BAND0.ordinal() + " " + pkgCnt[0]);
                    WiFiTxFormat.this.updateTxCountView(String.valueOf(pkgCnt[0]));
                    if (WiFiTxFormat.this.mTarPktCnt == 0 || WiFiTxFormat.this.mTarPktCnt != pkgCnt[0]) {
                        WiFiTxFormat.this.mTxHandler.sendEmptyMessageDelayed(3, 1000);
                        return;
                    } else if (WiFiTxFormat.this.mTestMode.equals(TXTestMode.TM_CONTINUES)) {
                        WiFiTxFormat.this.runOnUiThread(new Runnable() {
                            public void run() {
                                WiFiTxFormat.this.setUiEnable(true);
                            }
                        });
                        return;
                    } else if (WiFiTxFormat.this.mTestMode.equals(TXTestMode.TM_100_PERCENT_DUTY)) {
                        WiFiTxFormat wiFiTxFormat = WiFiTxFormat.this;
                        wiFiTxFormat.ctrlDutyTx(wiFiTxFormat.mTxParam, 1);
                        TXTestMode unused = WiFiTxFormat.this.mStopMode = TXTestMode.TM_100_PERCENT_DUTY;
                        return;
                    } else {
                        return;
                    }
                case 5:
                    WiFiTxFormat.this.queryTxPowerFromSettings();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.mediatek.engineermode.wifi.WiFiTxFormat$14  reason: invalid class name */
    static /* synthetic */ class AnonymousClass14 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode;

        static {
            int[] iArr = new int[TXTestMode.values().length];
            $SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode = iArr;
            try {
                iArr[TXTestMode.TM_CONTINUES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode[TXTestMode.TM_100_PERCENT_DUTY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode[TXTestMode.TM_CW_TONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateTxCountView(final String text) {
        runOnUiThread(new Runnable() {
            public void run() {
                WiFiTxFormat.this.mTvTxedCnt.setText(text);
            }
        });
    }

    /* access modifiers changed from: private */
    public void stopTx() {
        if (this.mStopMode != null) {
            switch (AnonymousClass14.$SwitchMap$com$mediatek$engineermode$wifi$WiFiTxFormat$TXTestMode[this.mStopMode.ordinal()]) {
                case 1:
                    EMWifi.hqaDbdcStopTX(this.mBandValue);
                    Elog.i(TAG, "hqaDbdcStopTX:" + this.mBandValue);
                    this.mTxHandler.removeMessages(3);
                    break;
                case 2:
                    ctrlDutyTx(this.mTxParam, 0);
                    break;
                case 3:
                    ctrlCwToneTx(this.mTxParam, 0);
                    break;
                default:
                    return;
            }
            this.mStopMode = null;
        }
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnGo)) {
            if (this.mCbWf1.isChecked() || this.mCbWf2.isChecked()) {
                String interPktGap = this.mEtInterPktGap.getText().toString();
                if (interPktGap == null || interPktGap.length() <= 0) {
                    this.mEtInterPktGap.setText(String.valueOf(50));
                }
                TxParam txParam = getTxParam();
                this.mTxParam = txParam;
                if (txParam == null) {
                    Toast.makeText(this, R.string.wifi_tx_param_not_ready, 1).show();
                    return;
                }
                this.mTestMode = (TXTestMode) this.mSpMode.getSelectedItem();
                TxHandler txHandler = this.mTxHandler;
                if (txHandler != null) {
                    txHandler.sendEmptyMessage(1);
                    setUiEnable(false);
                    return;
                }
                return;
            }
            Toast.makeText(this, R.string.wifi_dialog_no_path_select, 1).show();
        } else if (v.equals(this.mBtnStop)) {
            this.mTxHandler.sendEmptyMessage(2);
            setUiEnable(true);
        }
    }

    /* access modifiers changed from: private */
    public void setUiEnable(boolean enable) {
        this.mViewRu.setEnabled(enable);
        this.mBtnGo.setEnabled(enable);
        this.mBtnStop.setEnabled(!enable);
        this.mSpCh.setEnabled(enable);
        this.mSpMode.setEnabled(enable);
        this.mSpCbw.setEnabled(enable);
        this.mSpDbw.setEnabled(enable);
        this.mSpPrimCh.setEnabled(enable);
        this.mSpPreamble.setEnabled(enable);
        this.mSpRate.setEnabled(enable);
        this.mSpGi.setEnabled(enable);
        this.mSpRuConfig.setEnabled(enable);
        this.mSpRuAllocation.setEnabled(enable);
        this.mSpRuIndex.setEnabled(enable);
        this.mSpFec.setEnabled(enable);
        this.mEtPktLength.setEnabled(enable);
        if (((TXTestMode) this.mSpMode.getSelectedItem()).equals(TXTestMode.TM_100_PERCENT_DUTY)) {
            this.mEtPktCnt.setEnabled(false);
        } else {
            this.mEtPktCnt.setEnabled(enable);
        }
        this.mEtPower.setEnabled(enable);
        this.mEtRuStaId.setEnabled(enable);
        this.mCbWf1.setEnabled(enable);
        this.mCbWf2.setEnabled(enable);
        this.mRgNss.setEnabled(enable);
        int nssCnt = this.mRgNss.getChildCount();
        for (int k = 0; k < nssCnt; k++) {
            this.mRgNss.getChildAt(k).setEnabled(enable);
        }
        int antCnt = this.mRgAnt.getChildCount();
        for (int k2 = 0; k2 < antCnt; k2++) {
            this.mRgAnt.getChildAt(k2).setEnabled(enable);
        }
        this.mEtInterPktGap.setEnabled(enable);
        this.mCbHwTx.setEnabled(enable);
    }

    private void setChannel(TxParam param) {
        EMWifi.hqaSetTxPath(param.mWfSelect, this.mBandValue);
        EMWifi.hqaSetRxPath(param.mWfSelect, this.mBandValue);
        Elog.i(TAG, "hqaSetTxPath:" + param.mWfSelect + " " + this.mBandValue);
        Elog.i(TAG, "hqaSetRxPath:" + param.mWfSelect + " " + this.mBandValue);
        if (this.mCapAntSwap) {
            EMWifi.hqaSetAntSwap(param.mAntIndex);
            Elog.i(TAG, "hqaSetAntSwap:" + param.mAntIndex);
        }
        EMWifi.hqaDbdcSetChannel(this.mBandValue, param.mChID, 0, param.mCbwIndex, param.mDbwIndex, param.mPrimCh, param.mChannelType);
        Elog.i(TAG, "hqaDbdcSetChannel:" + this.mBandValue + " " + param.mChID + " " + 0 + " " + param.mCbwIndex + " " + param.mDbwIndex + " " + param.mPrimCh + " " + param.mChannelType);
    }

    private void setTxContent(TxParam param) {
        EMWifi.hqaDbdcSetTXContent(this.mBandValue, param.mPktLength);
        Elog.i(TAG, "hqaDbdcSetTXContent:" + this.mBandValue + " " + param.mPktLength);
    }

    private boolean isRuWidgetReady() {
        if (this.mViewRu.getVisibility() != 0) {
            return true;
        }
        if (this.mSpRuConfig.getSelectedItemPosition() < 0 || this.mSpRuIndex.getSelectedItemPosition() < 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void getTxPower() {
        TxParam powerUpdateParam = getPowerUpdateParam();
        this.mPowerParam = powerUpdateParam;
        if (powerUpdateParam == null || !isRuWidgetReady()) {
            Elog.i(TAG, "get power param invalid");
            return;
        }
        Elog.i(TAG, this.mPowerParam.toString());
        this.mTxHandler.sendEmptyMessage(5);
    }

    /* access modifiers changed from: private */
    public void queryTxPowerFromSettings() {
        setChannel(this.mPowerParam);
        if (this.mViewRu.getVisibility() == 0) {
            setRuSettings(this.mPowerParam);
        }
        EMWifi.hqaSetPreamble(this.mPowerParam.mPreambleIndex);
        EMWifi.hqaSetRate(this.mPowerParam.mRateIndex);
        EMWifi.hqaSetNss(this.mPowerParam.mNssValue);
        Elog.i(TAG, "hqaSetparam: preamble" + this.mPowerParam.mPreambleIndex + " rateindex:" + this.mPowerParam.mRateIndex + " nssValue:" + this.mPowerParam.mNssValue);
        final int power = EMWifi.hqaGetTxPower(this.mPowerParam.mChID, this.mBandValue);
        runOnUiThread(new Runnable() {
            public void run() {
                Elog.i(WiFiTxFormat.TAG, "power get:" + power);
                WiFiTxFormat.this.mEtPower.setText(String.valueOf(((double) power) * 0.5d));
            }
        });
    }

    private void setTxPower(TxParam param) {
        EMWifi.hqaSetTxPowerExt(param.mPower, this.mBandValue, param.mChID, param.mChannelType, param.mWfSelect);
        Elog.i(TAG, "hqaSetTxPowerExt:" + param.mPower + " " + this.mBandValue + " " + param.mChID + " " + param.mChannelType + " " + param.mWfSelect);
    }

    private void setRuSettings(TxParam param) {
        WifiFormatConfig.RuConfig config = (WifiFormatConfig.RuConfig) this.mSpRuConfig.getSelectedItem();
        int stad = 0;
        try {
            stad = Integer.parseInt(this.mEtRuStaId.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        EMWifi.hqaSetRUSettings(this.mBandValue, config.getIndex(), config.getAllocation(), stad, this.mRuIndexAdapter.getItem(this.mSpRuIndex.getSelectedItemPosition()).intValue(), param.mRateIndex, param.mFecValue, param.mNssValue, 1, param.mPktLength, param.mPower);
        Elog.i(TAG, "hqaSetRUSettings:" + this.mBandValue + " " + config.getIndex() + " " + config.getAllocation() + " " + stad + " " + this.mRuIndexAdapter.getItem(this.mSpRuIndex.getSelectedItemPosition()) + " " + param.mRateIndex + " " + param.mFecValue + " " + param.mNssValue + " " + 1 + " " + param.mPktLength + " " + param.mPower);
    }

    private void startTx(TxParam param, int pktCnt) {
        this.mTarPktCnt = pktCnt;
        EMWifi.hqaDbdcStartTX(this.mBandValue, pktCnt, param.mPreambleIndex, param.mRateIndex, param.mPower, param.mFecValue, param.mInterPktGap, param.mGiIndex, param.mWfSelect, param.mNssValue, param.mHwTx);
        Elog.i(TAG, "hqaDbdcStartTX:" + this.mBandValue + " " + pktCnt + " " + param.mPreambleIndex + " " + param.mRateIndex + " " + param.mPower + " " + param.mFecValue + " " + param.mInterPktGap + " " + param.mGiIndex + " " + param.mWfSelect + " " + param.mNssValue + " " + param.mHwTx);
    }

    /* access modifiers changed from: private */
    public void ctrlDutyTx(TxParam param, int cmd) {
        EMWifi.hqaDbdcContinuousTX(this.mBandValue, cmd, param.mWfSelect, param.mPreambleIndex, param.mDbwIndex, param.mPrimCh, param.mChID, param.mRateIndex);
        Elog.i(TAG, "hqaDbdcContinuousTX:" + this.mBandValue + " " + cmd + " " + param.mWfSelect + " " + param.mPreambleIndex + " " + param.mDbwIndex + " " + param.mPrimCh + " " + param.mChID + " " + param.mRateIndex);
    }

    private void ctrlCwToneTx(TxParam param, int cmd) {
        EMWifi.hqaDbdcTXTone(this.mBandValue, param.mChannelType, cmd, param.mWfSelect);
        Elog.i(TAG, "hqaDbdcTXTone:" + this.mBandValue + " " + param.mChannelType + " " + cmd + " " + param.mWfSelect);
    }

    /* access modifiers changed from: private */
    public void startContinuesTx() {
        Elog.i(TAG, "startContinuesTx");
        updateTxCountView("");
        setChannel(this.mTxParam);
        setTxContent(this.mTxParam);
        setTxPower(this.mTxParam);
        if (this.mViewRu.getVisibility() == 0) {
            setRuSettings(this.mTxParam);
        }
        int pktCnt = 0;
        try {
            pktCnt = Integer.parseInt(this.mEtPktCnt.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        startTx(this.mTxParam, pktCnt);
        this.mStopMode = TXTestMode.TM_CONTINUES;
        if (this.mViewRu.getVisibility() == 0) {
            get11axInfo();
        }
        this.mTxHandler.sendEmptyMessageDelayed(3, 1000);
    }

    /* access modifiers changed from: private */
    public void startDutyTx() {
        updateTxCountView("");
        setChannel(this.mTxParam);
        setTxContent(this.mTxParam);
        setTxPower(this.mTxParam);
        startTx(this.mTxParam, 100);
        this.mStopMode = TXTestMode.TM_CONTINUES;
        this.mTxHandler.sendEmptyMessageDelayed(3, 1000);
    }

    /* access modifiers changed from: private */
    public void startCwToneTx() {
        updateTxCountView("NA");
        setChannel(this.mTxParam);
        ctrlCwToneTx(this.mTxParam, 1);
        this.mStopMode = TXTestMode.TM_CW_TONE;
    }

    private void get11axInfo() {
        int[] factorValue = {0};
        int[] ldpcExtraSys = {0};
        int[] peDisamp = {0};
        int[] txPe = {0};
        int[] lSig = {0};
        Elog.i(TAG, "start hqaGetAFactor");
        if (EMWifi.hqaGetAFactor(this.mBandValue, factorValue, ldpcExtraSys, peDisamp, txPe, lSig) == 0) {
            Elog.i(TAG, "hqaGetAFactor:" + this.mBandValue + " " + factorValue[0] + " " + ldpcExtraSys[0] + " " + peDisamp[0] + " " + txPe[0] + " " + lSig[0]);
            this.mTvAFactor.setText(String.valueOf(factorValue[0]));
            this.mTvLdpcExtraSym.setText(String.valueOf(ldpcExtraSys[0]));
            this.mTvPeDisa.setText(String.valueOf(peDisamp[0]));
            TextView textView = this.mTvTxPe;
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(txPe[0] * 4));
            sb.append("us");
            textView.setText(sb.toString());
            this.mTvLSig.setText(String.valueOf(lSig[0]));
        }
    }

    enum TXTestMode {
        TM_CONTINUES("continuous packet tx"),
        TM_100_PERCENT_DUTY("100% duty cycle"),
        TM_CW_TONE("CW tone");
        
        private String mName;

        private TXTestMode(String name) {
            this.mName = name;
        }

        public String toString() {
            return this.mName;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }
}
