package com.mediatek.engineermode.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.wifi.WiFiTestActivity;
import java.util.HashMap;

public class WiFiTx6632 extends WiFiTestActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int BAND_WIDTH_160NC_INDEX = 6;
    private static final int BAND_WIDTH_160_INDEX = 5;
    private static final int BAND_WIDTH_20_INDEX = 2;
    private static final int BAND_WIDTH_40_INDEX = 3;
    private static final int BAND_WIDTH_80_INDEX = 4;
    private static final int COMMAND_INDEX_LOCALFREQ = 5;
    private static final int COMMAND_INDEX_OUTPUTPOWER = 4;
    private static final int COMMAND_INDEX_STARTTX = 1;
    private static final int COMMAND_INDEX_STOPTEST = 0;
    private static final int DEFAULT_PKT_CNT = 0;
    private static final int DEFAULT_PKT_LEN = 1024;
    private static final int DEFAULT_TX_GAIN = 22;
    private static final int HANDLER_EVENT_FINISH = 4;
    private static final int HANDLER_EVENT_GO = 1;
    private static final int HANDLER_EVENT_STOP = 2;
    private static final int HANDLER_EVENT_TIMER = 3;
    private static final int ONE_SENCOND = 1000;
    private static final int POWER_UNIT_DBM = 0;
    private static final int RATE_MODE_MASK = 31;
    private static final String TAG = "WifiTx";
    private static final int TEST_MODE_CW_TONE = 2;
    private static final int TEST_MODE_DUTY = 1;
    private static final int TEST_MODE_TX = 0;
    private static final int WIFI_MODE_DBDC_TX0 = 2;
    private static final int WIFI_MODE_DBDC_TX1 = 3;
    private static final int WIFI_MODE_NORMAL = 1;
    private static final String[] mGuardInterval = {"normal GI", "short GI"};
    private static final String[] mMode = {"continuous packet tx", "100% duty cycle", "CW tone"};
    private static final String[] mPreamble = {"Normal", "CCK short", "802.11n mixed mode", "802.11n green field", "802.11ac"};
    private static final String[] sBandWidth = {"5MHz", "10MHz", "20MHz", "40MHz", "80MHz", "160MHz", "160NC"};
    /* access modifiers changed from: private */
    public static final String[] sBandWidthDBW = {"BW5", "BW10", "BW20", "BW40", "BW80", "BW160"};
    /* access modifiers changed from: private */
    public static final HashMap<String, Integer> sMapDbwValue = new HashMap<String, Integer>() {
        {
            put("BW5", 5);
            put("BW10", 6);
            put("BW20", 0);
            put("BW40", 1);
            put("BW80", 2);
            put("BW160", 3);
        }
    };
    private boolean mAntSwapSupport = false;
    /* access modifiers changed from: private */
    public int mBandwidthIndex = 0;
    private Button mBtnGo = null;
    private Button mBtnStop = null;
    private ChannelInfo mChannel = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mChannelAdapter = null;
    /* access modifiers changed from: private */
    public int mChannelTx0Freq;
    /* access modifiers changed from: private */
    public int mChannelTx1Freq;
    private final CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.wifi_duplicate_mode_tx) {
                if (isChecked) {
                    WiFiTx6632.this.mCkWf0.setChecked(true);
                    WiFiTx6632.this.mCkWf1.setChecked(true);
                }
            } else if (buttonView.getId() == R.id.wifi_wfx_0) {
                if (!isChecked) {
                    WiFiTx6632.this.mCkDuplicateMode.setChecked(false);
                }
                WiFiTx6632.this.updateTxPower();
            } else if (buttonView.getId() == R.id.wifi_wfx_1) {
                if (!isChecked) {
                    WiFiTx6632.this.mCkDuplicateMode.setChecked(false);
                }
                WiFiTx6632.this.updateTxPower();
            }
        }
    };
    /* access modifiers changed from: private */
    public CheckBox mCkDuplicateMode = null;
    /* access modifiers changed from: private */
    public CheckBox mCkWf0 = null;
    /* access modifiers changed from: private */
    public CheckBox mCkWf1 = null;
    private long mCntNum = 0;
    /* access modifiers changed from: private */
    public int mDataBandwidthValue = 0;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mDbwAdapter = null;
    private EditText mEtPkt = null;
    private EditText mEtPktCnt = null;
    private EditText mEtTxGain = null;
    private Handler mEventHandler = null;
    /* access modifiers changed from: private */
    public int mGuardIntervalIndex = 0;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (4 == msg.what) {
                Elog.v(WiFiTx6632.TAG, "receive HANDLER_EVENT_FINISH");
                WiFiTx6632.this.setViewEnabled(true);
            }
        }
    };
    private boolean mHighRateSelected = false;
    private ArrayAdapter<String> mModeAdapter = null;
    /* access modifiers changed from: private */
    public int mModeIndex = 0;
    private int mNssValue = 1;
    private long mPktLenNum = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    private ArrayAdapter<String> mPreambleAdapter = null;
    /* access modifiers changed from: private */
    public int mPreambleIndex = 0;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mPrimChAdapter = null;
    /* access modifiers changed from: private */
    public int mPrimaryIndex = 0;
    private RadioGroup mRGNss = null;
    /* access modifiers changed from: private */
    public RateInfo mRate = null;
    private ArrayAdapter<String> mRateAdapter = null;
    private RadioButton mRbAntMain = null;
    private RadioButton mRbNss1 = null;
    private RadioButton mRbNss2 = null;
    private Spinner mSpBandwidth = null;
    private Spinner mSpChannelTx0 = null;
    private Spinner mSpChannelTx1 = null;
    /* access modifiers changed from: private */
    public Spinner mSpDbw = null;
    private Spinner mSpGuardInterval = null;
    private Spinner mSpMode = null;
    private Spinner mSpPreamble = null;
    /* access modifiers changed from: private */
    public Spinner mSpPrimCh = null;
    private Spinner mSpRate = null;
    /* access modifiers changed from: private */
    public int mTargetModeIndex = 0;
    private boolean mTestInProcess = false;
    private HandlerThread mTestThread = null;
    /* access modifiers changed from: private */
    public TextView mTvDbw = null;
    private TextView mTvNss = null;
    /* access modifiers changed from: private */
    public TextView mTvPrimCh = null;
    private long mTxGainVal = 22;
    private ViewGroup mVgAnt = null;
    /* access modifiers changed from: private */
    public View mViewChannelTx1 = null;
    private int mWiFiMode = 1;

    static class RateInfo {
        private static final short EEPROM_RATE_GROUP_CCK = 0;
        private static final short EEPROM_RATE_GROUP_OFDM_12_18M = 2;
        private static final short EEPROM_RATE_GROUP_OFDM_24_36M = 3;
        private static final short EEPROM_RATE_GROUP_OFDM_48_54M = 4;
        private static final short EEPROM_RATE_GROUP_OFDM_6_9M = 1;
        private static final short EEPROM_RATE_GROUP_OFDM_MCS0_32 = 5;
        private static final int MAX_LOWER_RATE_NUMBER = 12;
        static final String[] RATE_ITEM_11M = {"1M", "2M", "5.5M", "11M"};
        static final String[] RATE_ITEM_54M = {"1M", "2M", "5.5M", "11M", "6M", "9M", "12M", "18M", "24M", "36M", "48M", "54M"};
        static final String[] RATE_ITEM_CS15 = {"MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7", "MCS8", "MCS9", "MCS10", "MCS11", "MCS12", "MCS13", "MCS14", "MCS15"};
        static final String[] RATE_ITEM_CS7 = {"MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7"};
        int mOFDMStartIndex = 4;
        int mRateIndex = 0;
        private final short[] mUcRateGroupEep = {EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_OFDM_6_9M, EEPROM_RATE_GROUP_OFDM_6_9M, EEPROM_RATE_GROUP_OFDM_12_18M, EEPROM_RATE_GROUP_OFDM_12_18M, EEPROM_RATE_GROUP_OFDM_24_36M, EEPROM_RATE_GROUP_OFDM_24_36M, EEPROM_RATE_GROUP_OFDM_48_54M, EEPROM_RATE_GROUP_OFDM_48_54M, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32};

        RateInfo() {
        }

        /* access modifiers changed from: package-private */
        public int getRateGroup(int rateIndex) {
            if (rateIndex < 0) {
                return -1;
            }
            short[] sArr = this.mUcRateGroupEep;
            if (rateIndex < sArr.length) {
                return sArr[rateIndex];
            }
            return -1;
        }
    }

    private void initUiLayout() {
        this.mBtnGo = (Button) findViewById(R.id.WiFi_Go);
        this.mBtnStop = (Button) findViewById(R.id.WiFi_Stop);
        this.mBtnGo.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.wifi_tx_nss);
        this.mRGNss = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        this.mSpBandwidth = (Spinner) findViewById(R.id.wifi_bandwidth_spinner);
        this.mSpRate = (Spinner) findViewById(R.id.WiFi_Rate_Spinner);
        this.mTvNss = (TextView) findViewById(R.id.wifi_nss_text);
        this.mRbNss1 = (RadioButton) findViewById(R.id.wifi_tx_nss_1);
        this.mRbNss2 = (RadioButton) findViewById(R.id.wifi_tx_nss_2);
        this.mTvDbw = (TextView) findViewById(R.id.wifi_bandwidth_dbw_tv);
        this.mSpDbw = (Spinner) findViewById(R.id.wifi_bandwidth_dbw_spn);
        this.mTvPrimCh = (TextView) findViewById(R.id.wifi_bandwidth_prim_ch_tv);
        this.mSpPrimCh = (Spinner) findViewById(R.id.wifi_bandwidth_prim_ch_spn);
        this.mEtPkt = (EditText) findViewById(R.id.WiFi_Pkt_Edit);
        this.mEtPktCnt = (EditText) findViewById(R.id.WiFi_Pktcnt_Edit);
        this.mEtTxGain = (EditText) findViewById(R.id.WiFi_Tx_Gain_Edit);
        this.mSpMode = (Spinner) findViewById(R.id.WiFi_Mode_Spinner);
        this.mSpPreamble = (Spinner) findViewById(R.id.WiFi_Preamble_Spinner);
        this.mSpGuardInterval = (Spinner) findViewById(R.id.WiFi_Guard_Interval_Spinner);
        this.mCkWf0 = (CheckBox) findViewById(R.id.wifi_wfx_0);
        this.mCkWf1 = (CheckBox) findViewById(R.id.wifi_wfx_1);
        CheckBox checkBox = (CheckBox) findViewById(R.id.wifi_duplicate_mode_tx);
        this.mCkDuplicateMode = checkBox;
        checkBox.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCkWf0.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCkWf1.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCkWf0.setChecked(true);
        this.mSpChannelTx0 = (Spinner) findViewById(R.id.wifi_tx0_channel_spinner);
        this.mSpChannelTx1 = (Spinner) findViewById(R.id.wifi_tx1_channel_spinner);
        this.mViewChannelTx1 = findViewById(R.id.wifi_tx1_channel_layout);
        this.mChannel = new ChannelInfo();
        this.mEtPkt.setText("1024");
        this.mEtPktCnt.setText("0");
        this.mEtTxGain.setText("22");
        this.mVgAnt = (ViewGroup) findViewById(R.id.wifi_ant_vg);
        this.mRbAntMain = (RadioButton) findViewById(R.id.wifi_tx_ant_main);
    }

    private void initUiComponent() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        ChannelInfo.getSupportChannels();
        this.mChannel.addSupported2dot4gChannels(this.mChannelAdapter, false);
        this.mSpChannelTx0.setAdapter(this.mChannelAdapter);
        this.mSpChannelTx0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int unused = WiFiTx6632.this.mChannelTx0Freq = ChannelInfo.getChannelFrequency(ChannelInfo.parseChannelId((String) WiFiTx6632.this.mChannelAdapter.getItem(position)));
                WiFiTx6632.this.updateTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpChannelTx1.setAdapter(this.mChannelAdapter);
        this.mSpChannelTx1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int unused = WiFiTx6632.this.mChannelTx1Freq = ChannelInfo.getChannelFrequency(ChannelInfo.parseChannelId((String) WiFiTx6632.this.mChannelAdapter.getItem(position)));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> bandwidthAdapter = new ArrayAdapter<>(this, 17367048);
        bandwidthAdapter.setDropDownViewResource(17367049);
        String[] strArr = sBandWidth;
        int size = strArr.length;
        if (this.mWiFiMode == 2) {
            size = strArr.length - 3;
        }
        for (int i = 0; i < size; i++) {
            bandwidthAdapter.add(sBandWidth[i]);
        }
        this.mSpBandwidth.setAdapter(bandwidthAdapter);
        this.mSpBandwidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int unused = WiFiTx6632.this.mBandwidthIndex = arg2;
                WiFiTx6632.this.mTvPrimCh.setEnabled(true);
                WiFiTx6632.this.mSpPrimCh.setEnabled(true);
                WiFiTx6632.this.mTvDbw.setEnabled(true);
                WiFiTx6632.this.mSpDbw.setEnabled(true);
                WiFiTx6632.this.mTvPrimCh.setVisibility(0);
                WiFiTx6632.this.mSpPrimCh.setVisibility(0);
                if (arg2 < 2) {
                    WiFiTx6632.this.mTvPrimCh.setVisibility(8);
                    WiFiTx6632.this.mSpPrimCh.setVisibility(8);
                    WiFiTx6632.this.mDbwAdapter.clear();
                    WiFiTx6632.this.mDbwAdapter.add(WiFiTx6632.sBandWidthDBW[arg2]);
                    WiFiTx6632.this.mViewChannelTx1.setVisibility(8);
                    WiFiTx6632.this.mSpDbw.setAdapter(WiFiTx6632.this.mDbwAdapter);
                } else if (arg2 <= 5) {
                    WiFiTx6632.this.mDbwAdapter.clear();
                    for (int i = 2; i <= arg2; i++) {
                        WiFiTx6632.this.mDbwAdapter.add(WiFiTx6632.sBandWidthDBW[i]);
                    }
                    WiFiTx6632.this.mSpDbw.setAdapter(WiFiTx6632.this.mDbwAdapter);
                    WiFiTx6632.this.mPrimChAdapter.clear();
                    int maxPrimCh = ((int) Math.pow(2.0d, (double) (arg2 - 2))) - 1;
                    for (int i2 = 0; i2 <= maxPrimCh; i2++) {
                        WiFiTx6632.this.mPrimChAdapter.add(String.valueOf(i2));
                    }
                    WiFiTx6632.this.mSpPrimCh.setAdapter(WiFiTx6632.this.mPrimChAdapter);
                    WiFiTx6632.this.mViewChannelTx1.setVisibility(8);
                } else {
                    WiFiTx6632.this.mSpPrimCh.setEnabled(false);
                    WiFiTx6632.this.mSpDbw.setEnabled(false);
                    WiFiTx6632.this.mViewChannelTx1.setVisibility(0);
                    WiFiTx6632.this.mDbwAdapter.clear();
                    for (int i3 = 2; i3 < arg2; i3++) {
                        WiFiTx6632.this.mDbwAdapter.add(WiFiTx6632.sBandWidthDBW[i3]);
                    }
                    WiFiTx6632.this.mSpDbw.setAdapter(WiFiTx6632.this.mDbwAdapter);
                    WiFiTx6632.this.mSpDbw.setSelection(3);
                    WiFiTx6632.this.mPrimChAdapter.clear();
                    WiFiTx6632.this.mPrimChAdapter.add("0");
                    WiFiTx6632.this.mSpPrimCh.setAdapter(WiFiTx6632.this.mPrimChAdapter);
                }
                WiFiTx6632.this.updateRate();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mDbwAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        this.mSpDbw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Integer value = (Integer) WiFiTx6632.sMapDbwValue.get(((TextView) view).getText().toString());
                Elog.i(WiFiTx6632.TAG, "dbw value:" + value);
                if (value != null && value.intValue() != WiFiTx6632.this.mDataBandwidthValue) {
                    int unused = WiFiTx6632.this.mDataBandwidthValue = value.intValue();
                    WiFiTx6632.this.updateTxPower();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, 17367048);
        this.mPrimChAdapter = arrayAdapter3;
        arrayAdapter3.setDropDownViewResource(17367049);
        this.mSpPrimCh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != WiFiTx6632.this.mPrimaryIndex) {
                    int unused = WiFiTx6632.this.mPrimaryIndex = position;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mRate = new RateInfo();
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(this, 17367048);
        this.mRateAdapter = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(17367049);
        this.mSpRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int oldRate = WiFiTx6632.this.mRate.mRateIndex;
                WiFiTx6632.this.mRate.mRateIndex = arg2;
                if (WiFiTx6632.this.mPreambleIndex == 2 || WiFiTx6632.this.mPreambleIndex == 3 || WiFiTx6632.this.mPreambleIndex == 4) {
                    WiFiTx6632.this.mRate.mRateIndex = arg2 + 12;
                }
                WiFiTx6632.this.updateChannels();
                if (oldRate != WiFiTx6632.this.mRate.mRateIndex) {
                    WiFiTx6632.this.updateTxPower();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(this, 17367048);
        this.mPreambleAdapter = arrayAdapter5;
        arrayAdapter5.setDropDownViewResource(17367049);
        for (String add : mPreamble) {
            this.mPreambleAdapter.add(add);
        }
        this.mSpPreamble.setAdapter(this.mPreambleAdapter);
        this.mSpPreamble.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int unused = WiFiTx6632.this.mPreambleIndex = arg2;
                WiFiTx6632.this.updateRate();
                WiFiTx6632.this.updateTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, 17367048);
        this.mModeAdapter = arrayAdapter6;
        arrayAdapter6.setDropDownViewResource(17367049);
        int i2 = 0;
        while (true) {
            String[] strArr2 = mMode;
            if (i2 >= strArr2.length) {
                break;
            }
            this.mModeAdapter.add(strArr2[i2]);
            i2++;
        }
        this.mSpMode.setAdapter(this.mModeAdapter);
        this.mSpMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int unused = WiFiTx6632.this.mModeIndex = arg2;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter arrayAdapter7 = new ArrayAdapter(this, 17367048);
        arrayAdapter7.setDropDownViewResource(17367049);
        int i3 = 0;
        while (true) {
            String[] strArr3 = mGuardInterval;
            if (i3 < strArr3.length) {
                arrayAdapter7.add(strArr3[i3]);
                i3++;
            } else {
                this.mSpGuardInterval.setAdapter(arrayAdapter7);
                this.mSpGuardInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        int unused = WiFiTx6632.this.mGuardIntervalIndex = arg2;
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                HandlerThread handlerThread = new HandlerThread("Wifi Tx Test");
                this.mTestThread = handlerThread;
                handlerThread.start();
                this.mEventHandler = new EventHandler(this.mTestThread.getLooper());
                setViewEnabled(true);
                this.mRbNss1.setChecked(true);
                return;
            }
        }
    }

    public void onClick(View v) {
        if (v.getId() == this.mBtnGo.getId()) {
            onClickBtnTxGo();
        } else if (v.getId() == this.mBtnStop.getId()) {
            onClickBtnTxStop();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_tx_6632);
        Intent intent = getIntent();
        this.mAntSwapSupport = intent.getBooleanExtra("key_ant_swap", false);
        String data = intent.getStringExtra("mode");
        if (data == null) {
            this.mWiFiMode = 1;
        } else if (data.equals("TX Band0")) {
            this.mWiFiMode = 2;
        } else if (data.equals("TX Band1")) {
            this.mWiFiMode = 3;
        }
        initUiLayout();
        initUiComponent();
        int i = this.mWiFiMode;
        if (i == 2) {
            this.mCkWf0.setChecked(true);
            this.mRGNss.setVisibility(8);
            this.mCkWf0.setVisibility(8);
            this.mCkWf1.setVisibility(8);
            this.mCkDuplicateMode.setVisibility(8);
            this.mTvNss.setVisibility(8);
        } else if (i == 3) {
            this.mCkWf1.setChecked(true);
            this.mRGNss.setVisibility(8);
            this.mCkWf0.setVisibility(8);
            this.mCkWf1.setVisibility(8);
            this.mCkDuplicateMode.setVisibility(8);
            this.mTvNss.setVisibility(8);
        }
        if (!this.mAntSwapSupport) {
            this.mVgAnt.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void updateChannels() {
        ArrayAdapter<String> tempChAdapter = new ArrayAdapter<>(this, 17367048);
        int targetBandwidth = this.mBandwidthIndex;
        Elog.d(TAG, "updateChannels");
        if (this.mBandwidthIndex < 2) {
            this.mChannel.insertBw20MChannels(tempChAdapter);
            this.mChannel.insertBw40MChannels(tempChAdapter);
            this.mChannel.insertBw80MChannels(tempChAdapter);
            this.mChannel.insertBw160MChannels(tempChAdapter);
        }
        if (this.mBandwidthIndex == 2) {
            this.mChannel.insertBw20MChannels(tempChAdapter);
        }
        if (this.mBandwidthIndex == 3) {
            this.mChannel.insertBw40MChannels(tempChAdapter);
        }
        if (this.mBandwidthIndex == 4) {
            this.mChannel.insertBw80MChannels(tempChAdapter);
        }
        if (this.mBandwidthIndex == 5) {
            this.mChannel.insertBw160MChannels(tempChAdapter);
        }
        if (this.mBandwidthIndex == 6) {
            this.mChannel.insertBw80MChannels(tempChAdapter);
        }
        int i = this.mWiFiMode;
        if (i == 2) {
            this.mChannel.remove5GChannels(tempChAdapter);
        } else if (i == 3) {
            this.mChannel.remove2dot4GChannels(tempChAdapter);
        }
        updateChannelByRateBandwidth(tempChAdapter, this.mRate.mRateIndex, targetBandwidth);
        boolean bUpdateWifiChannel = false;
        int count = tempChAdapter.getCount();
        if (count == this.mChannelAdapter.getCount()) {
            int k = 0;
            while (true) {
                if (k >= count) {
                    break;
                } else if (!tempChAdapter.getItem(k).equals(this.mChannelAdapter.getItem(k))) {
                    Elog.i(TAG, "index" + k + "new:" + tempChAdapter.getItem(k) + " old:" + this.mChannelAdapter.getItem(k));
                    bUpdateWifiChannel = true;
                    break;
                } else {
                    k++;
                }
            }
        } else {
            bUpdateWifiChannel = true;
        }
        if (!bUpdateWifiChannel) {
            Elog.i(TAG, "no update");
            return;
        }
        Elog.i(TAG, "need update");
        this.mChannelAdapter.clear();
        for (int i2 = 0; i2 < count; i2++) {
            this.mChannelAdapter.add(tempChAdapter.getItem(i2));
        }
        if (this.mChannelAdapter.getCount() == 0) {
            this.mBtnGo.setEnabled(false);
            bUpdateWifiChannel = false;
        } else {
            this.mBtnGo.setEnabled(true);
        }
        if (bUpdateWifiChannel) {
            updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mSpChannelTx0);
            if (this.mViewChannelTx1.getVisibility() == 0) {
                updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mSpChannelTx1);
            }
        }
        this.mSpChannelTx0.setAdapter(this.mChannelAdapter);
        this.mSpChannelTx1.setAdapter(this.mChannelAdapter);
    }

    private void updateChannelByRateBandwidth(ArrayAdapter<String> adapter, int rateIndex, int bandwidthIndex) {
        if (this.mRate.getRateGroup(rateIndex) == 0) {
            this.mChannel.remove5GChannels(adapter);
            Elog.i(TAG, "The mode not support 5G channel : " + rateIndex);
        }
    }

    private void setAntSwpIdx() {
        long antennaIdx;
        if (this.mAntSwapSupport) {
            if (this.mRbAntMain.isChecked()) {
                antennaIdx = (long) WiFiTestActivity.EnumRfAtAntswp.RF_AT_ANTSWP_MAIN.ordinal();
            } else {
                antennaIdx = (long) WiFiTestActivity.EnumRfAtAntswp.RF_AT_ANTSWP_AUX.ordinal();
            }
            EMWifi.setATParam(153, antennaIdx);
        }
    }

    private void setRateIndex() {
        int rateIndex = this.mRate.mRateIndex;
        this.mHighRateSelected = rateIndex >= 12;
        Elog.i(TAG, "rateIndex : " + rateIndex);
        if (this.mHighRateSelected) {
            rateIndex = (rateIndex - 12) | Integer.MIN_VALUE;
        }
        EMWifi.setATParam(3, (long) rateIndex);
    }

    private void startContinueTx() {
        int cbw;
        int i = this.mWiFiMode;
        if (i == 1) {
            EMWifi.setATParam(110, 0);
            EMWifi.setATParam(104, 0);
        } else if (i == 2) {
            EMWifi.setATParam(110, 1);
            EMWifi.setATParam(104, 0);
        } else if (i == 3) {
            EMWifi.setATParam(110, 1);
            EMWifi.setATParam(104, 1);
        }
        EMWifi.setATParam(114, (long) this.mNssValue);
        long wfValue = 1;
        if (this.mWiFiMode == 1 && this.mCkWf1.isChecked()) {
            wfValue = !this.mCkWf0.isChecked() ? 2 : 3;
        }
        EMWifi.setATParam(113, wfValue);
        int i2 = this.mBandwidthIndex;
        if (i2 >= 2) {
            cbw = i2 - 2;
        } else {
            cbw = i2 + 5;
        }
        EMWifi.setATParam(71, (long) cbw);
        EMWifi.setATParam(72, (long) this.mDataBandwidthValue);
        if (this.mSpPrimCh.getVisibility() == 0) {
            EMWifi.setATParam(73, (long) this.mPrimaryIndex);
        }
        EMWifi.setATParam(18, (long) this.mChannelTx0Freq);
        if (this.mViewChannelTx1.getVisibility() == 0) {
            EMWifi.setATParam(65554, (long) this.mChannelTx1Freq);
        }
        setRateIndex();
        EMWifi.setATParam(6, this.mPktLenNum);
        EMWifi.setATParam(7, this.mCntNum);
        EMWifi.setATParam(31, 0);
        EMWifi.setATParam(2, this.mTxGainVal);
        EMWifi.setATParam(4, (long) this.mPreambleIndex);
        EMWifi.setATParam(16, (long) this.mGuardIntervalIndex);
        setAntSwpIdx();
        if (EMWifi.setATParam(1, 1) == 0) {
            this.mTestInProcess = true;
        }
        if (this.mCntNum != 0) {
            this.mEventHandler.sendEmptyMessageDelayed(3, 1000);
        }
    }

    private void startDutyTx() {
        int cbw;
        int i = this.mBandwidthIndex;
        if (i >= 2) {
            cbw = i - 2;
        } else {
            cbw = i + 5;
        }
        EMWifi.setATParam(71, (long) cbw);
        EMWifi.setATParam(72, (long) this.mDataBandwidthValue);
        if (this.mSpPrimCh.getVisibility() == 0) {
            EMWifi.setATParam(73, (long) this.mPrimaryIndex);
        }
        setRateIndex();
        EMWifi.setATParam(31, 0);
        EMWifi.setATParam(2, this.mTxGainVal);
        EMWifi.setATParam(4, (long) this.mPreambleIndex);
        setAntSwpIdx();
        if (EMWifi.setATParam(1, 4) == 0) {
            this.mTestInProcess = true;
        }
    }

    private void startCWToneTx() {
        EMWifi.setATParam(31, 0);
        EMWifi.setATParam(2, this.mTxGainVal);
        EMWifi.setATParam(18, (long) this.mChannelTx0Freq);
        if (this.mViewChannelTx1.getVisibility() == 0) {
            EMWifi.setATParam(65554, (long) this.mChannelTx1Freq);
        }
        setAntSwpIdx();
        long wfValue = 1;
        if (this.mWiFiMode == 1 && this.mCkWf1.isChecked()) {
            wfValue = !this.mCkWf0.isChecked() ? 2 : 3;
        }
        EMWifi.setATParam(113, wfValue);
        if (EMWifi.setATParam(1, 5) == 0) {
            this.mTestInProcess = true;
        }
    }

    /* access modifiers changed from: private */
    public void startTxTest(int mode) {
        switch (this.mTargetModeIndex) {
            case 0:
                startContinueTx();
                return;
            case 1:
                startDutyTx();
                return;
            case 2:
                startCWToneTx();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void stopTxTest() {
        Elog.i(TAG, "stopTxTest");
        if (this.mTestInProcess) {
            EMWifi.setATParam(1, 0);
            this.mTestInProcess = false;
        }
        Handler handler = this.mEventHandler;
        if (handler != null) {
            handler.removeMessages(3);
        }
        this.mHandler.sendEmptyMessage(4);
    }

    /* access modifiers changed from: private */
    public void handleEventTimer() {
        long[] u4Value = new long[2];
        u4Value[0] = 0;
        Elog.i(TAG, "handleEventTimer");
        int indexTransmitCount = 32;
        if (this.mWiFiMode == 3) {
            indexTransmitCount = 288;
        }
        if (this.mModeIndex == 1) {
            boolean completed = false;
            if (EMWifi.getATParam((long) indexTransmitCount, u4Value) == 0) {
                Elog.i(TAG, "query Transmitted packet count succeed, count = " + u4Value[0] + " target count = " + 100);
                if (u4Value[0] == 100) {
                    completed = true;
                }
            } else {
                Elog.w(TAG, "query Transmitted packet count failed");
            }
            if (!completed) {
                EMWifi.setATParam(1, 0);
            }
            this.mTargetModeIndex = 1;
            this.mEventHandler.sendEmptyMessage(1);
            return;
        }
        try {
            long pktCnt = Long.parseLong(this.mEtPktCnt.getText().toString());
            if (EMWifi.getATParam((long) indexTransmitCount, u4Value) == 0) {
                Elog.i(TAG, "query Transmitted packet count succeed, count = " + u4Value[0] + " target count = " + pktCnt);
                if (u4Value[0] == pktCnt) {
                    this.mEventHandler.removeMessages(3);
                    this.mHandler.sendEmptyMessage(4);
                    return;
                }
            } else {
                Elog.w(TAG, "query Transmitted packet count failed");
            }
            this.mEventHandler.sendEmptyMessageDelayed(3, 1000);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "invalid input value", 0).show();
        }
    }

    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    WiFiTx6632 wiFiTx6632 = WiFiTx6632.this;
                    wiFiTx6632.startTxTest(wiFiTx6632.mTargetModeIndex);
                    return;
                case 2:
                    WiFiTx6632.this.stopTxTest();
                    return;
                case 3:
                    WiFiTx6632.this.handleEventTimer();
                    return;
                default:
                    return;
            }
        }
    }

    private boolean checkTxPath() {
        if (this.mCkWf0.isChecked() || this.mCkWf1.isChecked()) {
            return true;
        }
        Toast.makeText(this, R.string.wifi_dialog_no_path_select, 1).show();
        return false;
    }

    private void onClickBtnTxGo() {
        if (checkTxPath()) {
            try {
                this.mTxGainVal = (long) (Float.parseFloat(this.mEtTxGain.getText().toString()) * 2.0f);
                int i = this.mModeIndex;
                this.mTargetModeIndex = i;
                switch (i) {
                    case 0:
                        try {
                            long pktNum = Long.parseLong(this.mEtPkt.getText().toString());
                            long cntNum = Long.parseLong(this.mEtPktCnt.getText().toString());
                            this.mPktLenNum = pktNum;
                            this.mCntNum = cntNum;
                            break;
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "invalid input value", 0).show();
                            return;
                        }
                    case 1:
                        this.mPktLenNum = 100;
                        this.mCntNum = 100;
                        this.mTargetModeIndex = 0;
                        break;
                }
                Handler handler = this.mEventHandler;
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                    setViewEnabled(false);
                }
            } catch (NumberFormatException e2) {
                Toast.makeText(this, "invalid input value", 0).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            handler.removeMessages(3);
        }
        HandlerThread handlerThread = this.mTestThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void setViewEnabled(boolean state) {
        this.mSpChannelTx0.setEnabled(state);
        this.mSpChannelTx1.setEnabled(state);
        this.mSpGuardInterval.setEnabled(state);
        this.mSpBandwidth.setEnabled(state);
        this.mRbNss1.setEnabled(state);
        this.mRbNss2.setEnabled(state);
        this.mCkDuplicateMode.setEnabled(state);
        this.mSpPreamble.setEnabled(state);
        this.mEtPkt.setEnabled(state);
        this.mEtPktCnt.setEnabled(state);
        this.mEtTxGain.setEnabled(state);
        this.mSpRate.setEnabled(state);
        this.mSpMode.setEnabled(state);
        this.mBtnGo.setEnabled(state);
        this.mBtnStop.setEnabled(!state);
        this.mSpDbw.setEnabled(state);
        this.mSpPrimCh.setEnabled(state);
        this.mRGNss.setEnabled(state);
        this.mCkWf0.setEnabled(state);
        this.mCkWf1.setEnabled(state);
        this.mSpDbw.setEnabled(state);
        this.mSpPrimCh.setEnabled(state);
    }

    private void onClickBtnTxStop() {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            handler.sendEmptyMessage(2);
        }
        switch (this.mModeIndex) {
            case 0:
                return;
            default:
                EMWifi.setStandBy();
                return;
        }
    }

    /* access modifiers changed from: private */
    public void updateRate() {
        this.mRateAdapter.clear();
        Elog.i(TAG, "updateRate, mNssValue = " + this.mNssValue);
        int i = this.mPreambleIndex;
        if (i == 0) {
            for (String add : RateInfo.RATE_ITEM_54M) {
                this.mRateAdapter.add(add);
            }
        } else if (i == 1) {
            for (String add2 : RateInfo.RATE_ITEM_11M) {
                this.mRateAdapter.add(add2);
            }
        } else if (i == 2 || i == 3) {
            int i2 = this.mNssValue;
            if (i2 == 1) {
                for (String add3 : RateInfo.RATE_ITEM_CS7) {
                    this.mRateAdapter.add(add3);
                }
            } else if (i2 == 2) {
                for (String add4 : RateInfo.RATE_ITEM_CS15) {
                    this.mRateAdapter.add(add4);
                }
            }
        } else if (i == 4) {
            for (String add5 : RateInfo.RATE_ITEM_CS7) {
                this.mRateAdapter.add(add5);
            }
            if (this.mBandwidthIndex >= 2) {
                this.mRateAdapter.add("MCS8");
            }
            if (this.mBandwidthIndex >= 3) {
                this.mRateAdapter.add("MCS9");
            }
        }
        this.mSpRate.setAdapter(this.mRateAdapter);
        if (this.mBandwidthIndex >= 4 && this.mPreambleIndex == 0) {
            this.mSpRate.setSelection(4);
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.wifi_tx_nss_1) {
            this.mNssValue = 1;
            this.mCkDuplicateMode.setVisibility(0);
        } else if (checkedId == R.id.wifi_tx_nss_2) {
            this.mNssValue = 2;
            this.mCkDuplicateMode.setVisibility(8);
        }
        Elog.i(TAG, "mNssValue changed = " + this.mNssValue);
        updateRate();
    }

    /* access modifiers changed from: private */
    public void updateTxPower() {
        Elog.i(TAG, "updateTxPower");
        if (this.mRate != null) {
            EMWifi.setATParam(18, (long) this.mChannelTx0Freq);
            EMWifi.setATParam(4, (long) this.mPreambleIndex);
            setRateIndex();
            EMWifi.setATParam(72, (long) this.mDataBandwidthValue);
            long wfValue = 1;
            if (this.mWiFiMode == 1 && this.mCkWf1.isChecked()) {
                wfValue = !this.mCkWf0.isChecked() ? 2 : 3;
            }
            EMWifi.setATParam(113, wfValue);
            long[] u4Value = new long[2];
            u4Value[0] = 0;
            if (EMWifi.getATParam(136, u4Value) != 0 || u4Value[0] == 0) {
                this.mEtTxGain.setText(String.valueOf(22));
            } else {
                this.mEtTxGain.setText(String.valueOf(((double) u4Value[0]) * 0.5d));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }
}
