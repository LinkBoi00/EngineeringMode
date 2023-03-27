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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.wifi.WiFiTestActivity;
import java.util.Arrays;
import java.util.Locale;

public class WiFiTx6620 extends WiFiTestActivity implements View.OnClickListener {
    private static final int ANTENNA = 0;
    private static final int BANDWIDTH_40MHZ_MASK = 32768;
    private static final int BANDWIDTH_INDEX_20M = 0;
    private static final int BANDWIDTH_INDEX_40M = 1;
    private static final int BANDWIDTH_INDEX_80M = 2;
    private static final int BIT_8_MASK = 255;
    /* access modifiers changed from: private */
    public static final String[] BW_ADVANCED_ITEMS = {"BW20", "BW40", "BW80"};
    private static final int BW_INDX_ADVANCED = 4;
    private static final int CCK_RATE_NUMBER = 4;
    private static final int COMMAND_INDEX_CARRIER_NEW = 10;
    private static final int COMMAND_INDEX_LOCALFREQ = 5;
    private static final int COMMAND_INDEX_OUTPUTPOWER = 4;
    private static final int COMMAND_INDEX_STARTTX = 1;
    private static final int COMMAND_INDEX_STOPTEST = 0;
    private static final int CWMODE_CCKPI = 5;
    private static final int CWMODE_OFDMLTF = 2;
    private static final int DEFAULT_PKT_CNT = 3000;
    private static final int DEFAULT_PKT_LEN = 1024;
    private static final int DEFAULT_TX_GAIN = 0;
    private static final int HANDLER_EVENT_FINISH = 4;
    private static final int HANDLER_EVENT_GO = 1;
    private static final int HANDLER_EVENT_STOP = 2;
    private static final int HANDLER_EVENT_TIMER = 3;
    private static final int HIGH_RATE_PREAMBLE_BASE = 2;
    private static final int LENGTH_3 = 3;
    private static final int MAX_LOWER_RATE_NUMBER = 12;
    private static final int ONE_SENCOND = 1000;
    /* access modifiers changed from: private */
    public static final long[] PACKCONTENT_BUFFER = {-14548988, 860094470, 1432748040, 1431633945, -1431699429, -1145372643};
    private static final int RATE_MCS_INDEX = 32;
    private static final int RATE_MODE_MASK = 31;
    private static final int RATE_NOT_MCS_INDEX = 9;
    private static final String TAG = "WifiTx";
    private static final int TEST_MODE_CARRIER = 2;
    private static final int TEST_MODE_DUTY = 1;
    private static final int TEST_MODE_LEAKAGE = 3;
    private static final int TEST_MODE_POWEROFF = 4;
    private static final int TEST_MODE_TX = 0;
    private static final int TXOP_LIMIT_VALUE = 131072;
    private boolean mAntSwapSupport = false;
    String[] mBandwidth = {"20MHz", "40MHz", "U20MHz", "L20MHz", "Advanced"};
    /* access modifiers changed from: private */
    public int mBandwidthIndex = 0;
    /* access modifiers changed from: private */
    public Spinner mBandwidthSpinner = null;
    private Button mBtnGo = null;
    private Button mBtnStop = null;
    /* access modifiers changed from: private */
    public boolean mCCKRateSelected = true;
    /* access modifiers changed from: private */
    public ChannelInfo mChannel = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mChannelAdapter = null;
    int mChannelBandwidth = 0;
    private Spinner mChannelSpinner = null;
    /* access modifiers changed from: private */
    public long mCntNum = 3000;
    int mDataBandwidth = 0;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mDbwAdapter = null;
    private EditText mEtPkt = null;
    /* access modifiers changed from: private */
    public EditText mEtPktCnt = null;
    private EditText mEtTxGain = null;
    /* access modifiers changed from: private */
    public Handler mEventHandler = null;
    String[] mGuardInterval = {"800ns", "400ns"};
    /* access modifiers changed from: private */
    public int mGuardIntervalIndex = 0;
    private Spinner mGuardIntervalSpinner = null;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (4 == msg.what) {
                Elog.v(WiFiTx6620.TAG, "receive HANDLER_EVENT_FINISH");
                WiFiTx6620.this.setViewEnabled(true);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mHighRateSelected = false;
    /* access modifiers changed from: private */
    public boolean mIsAdvancedMode = false;
    /* access modifiers changed from: private */
    public int mLastBandwidth = 0;
    String[] mMode = {"continuous packet tx", "100% duty cycle", "carrier suppression", "local leakage", "enter power off"};
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mModeAdapter = null;
    /* access modifiers changed from: private */
    public int mModeIndex = 0;
    /* access modifiers changed from: private */
    public Spinner mModeSpinner = null;
    /* access modifiers changed from: private */
    public long mPktLenNum = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    String[] mPreamble = {"Normal", "CCK short", "802.11n mixed mode", "802.11n green field", "802.11ac"};
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mPreambleAdapter = null;
    /* access modifiers changed from: private */
    public int mPreambleIndex = 0;
    /* access modifiers changed from: private */
    public Spinner mPreambleSpinner = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mPrimChAdapter = null;
    int mPrimarySetting = 0;
    /* access modifiers changed from: private */
    public RateInfo mRate = null;
    private ArrayAdapter<String> mRateAdapter = null;
    private Spinner mRateSpinner = null;
    private int mRateUpdateCounter = 0;
    private RadioButton mRbAntMain = null;
    private Spinner mSpBwAdvCbw = null;
    /* access modifiers changed from: private */
    public Spinner mSpBwAdvDbw = null;
    /* access modifiers changed from: private */
    public Spinner mSpBwAdvPrimCh = null;
    /* access modifiers changed from: private */
    public int mTargetModeIndex = 0;
    private HandlerThread mTestThread = null;
    /* access modifiers changed from: private */
    public long mTxGainVal = 0;
    private ViewGroup mVgAnt = null;

    static /* synthetic */ int access$1608(WiFiTx6620 x0) {
        int i = x0.mRateUpdateCounter;
        x0.mRateUpdateCounter = i + 1;
        return i;
    }

    static /* synthetic */ int access$1808(WiFiTx6620 x0) {
        int i = x0.mModeIndex;
        x0.mModeIndex = i + 1;
        return i;
    }

    static class RateInfo {
        /* access modifiers changed from: private */
        public static final String[] ADVANCED_RATE_20M = {"1M", "2M", "5.5M", "11M", "6M", "9M", "12M", "18M", "24M", "36M", "48M", "54M", "MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7", "MCS8"};
        /* access modifiers changed from: private */
        public static final String[] ADVANCED_RATE_40M = {"MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7", "MCS8", "MCS9", "MCS32"};
        /* access modifiers changed from: private */
        public static final String[] ADVANCED_RATE_80M = {"MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7", "MCS8", "MCS9"};
        private static final short EEPROM_RATE_GROUP_CCK = 0;
        private static final short EEPROM_RATE_GROUP_OFDM_12_18M = 2;
        private static final short EEPROM_RATE_GROUP_OFDM_24_36M = 3;
        private static final short EEPROM_RATE_GROUP_OFDM_48_54M = 4;
        private static final short EEPROM_RATE_GROUP_OFDM_6_9M = 1;
        private static final short EEPROM_RATE_GROUP_OFDM_MCS0_32 = 5;
        private static final short EEPROM_RATE_GROUP_OFDM_MEDIUM = 6;
        int mOFDMStartIndex = 4;
        /* access modifiers changed from: private */
        public final String[] mPszRate = {"1M", "2M", "5.5M", "11M", "6M", "9M", "12M", "18M", "24M", "36M", "48M", "54M", "MCS0", "MCS1", "MCS2", "MCS3", "MCS4", "MCS5", "MCS6", "MCS7", "MCS8", "MCS9", "MCS32"};
        private final int[] mRateCfg = {2, 4, 11, 22, 12, 18, 24, 36, 48, 72, 96, NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, 22, 12, 18, 24, 36, 48, 72, 96, NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP};
        int mRateIndex = 0;
        private final short[] mUcRateGroupEep = {EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_CCK, EEPROM_RATE_GROUP_OFDM_6_9M, EEPROM_RATE_GROUP_OFDM_6_9M, EEPROM_RATE_GROUP_OFDM_12_18M, EEPROM_RATE_GROUP_OFDM_12_18M, EEPROM_RATE_GROUP_OFDM_24_36M, EEPROM_RATE_GROUP_OFDM_24_36M, EEPROM_RATE_GROUP_OFDM_48_54M, EEPROM_RATE_GROUP_OFDM_48_54M, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32, EEPROM_RATE_GROUP_OFDM_MCS0_32};

        RateInfo() {
        }

        private void setRateAdapterTo(ArrayAdapter<String> adapter, String[] rateArr) {
            adapter.clear();
            for (String add : rateArr) {
                adapter.add(add);
            }
        }

        /* access modifiers changed from: package-private */
        public int getRateNumber() {
            return this.mPszRate.length;
        }

        /* access modifiers changed from: package-private */
        public String getRate() {
            return this.mPszRate[this.mRateIndex];
        }

        /* access modifiers changed from: package-private */
        public int getRateCfg() {
            return this.mRateCfg[this.mRateIndex];
        }

        /* access modifiers changed from: package-private */
        public int getUcRateGroupEep() {
            return this.mUcRateGroupEep[this.mRateIndex];
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

        /* access modifiers changed from: package-private */
        public int getRateGroupExt(int rateIndex) {
            int group = getRateGroup(rateIndex);
            if (group <= 0 || group >= 5) {
                return group;
            }
            return 6;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_tx_6620);
        Intent it = getIntent();
        this.mAntSwapSupport = it.getBooleanExtra("key_ant_swap", false);
        this.mIsAdvancedMode = it.getBooleanExtra("key_11ac", false);
        this.mChannelSpinner = (Spinner) findViewById(R.id.WiFi_Channel_Spinner);
        this.mPreambleSpinner = (Spinner) findViewById(R.id.WiFi_Preamble_Spinner);
        this.mEtPkt = (EditText) findViewById(R.id.WiFi_Pkt_Edit);
        this.mEtPktCnt = (EditText) findViewById(R.id.WiFi_Pktcnt_Edit);
        this.mEtTxGain = (EditText) findViewById(R.id.WiFi_Tx_Gain_Edit);
        this.mRateSpinner = (Spinner) findViewById(R.id.WiFi_Rate_Spinner);
        this.mModeSpinner = (Spinner) findViewById(R.id.WiFi_Mode_Spinner);
        this.mBtnGo = (Button) findViewById(R.id.WiFi_Go);
        this.mBtnStop = (Button) findViewById(R.id.WiFi_Stop);
        this.mBandwidthSpinner = (Spinner) findViewById(R.id.WiFi_Bandwidth_Spinner);
        this.mGuardIntervalSpinner = (Spinner) findViewById(R.id.WiFi_Guard_Interval_Spinner);
        HandlerThread handlerThread = new HandlerThread("Wifi Tx Test");
        this.mTestThread = handlerThread;
        handlerThread.start();
        this.mEventHandler = new EventHandler(this.mTestThread.getLooper());
        this.mBtnGo.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        this.mChannel = new ChannelInfo();
        this.mRate = new RateInfo();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        ChannelInfo.getSupportChannels();
        this.mChannel.addSupported2dot4gChannels(this.mChannelAdapter, false);
        this.mChannelSpinner.setAdapter(this.mChannelAdapter);
        this.mChannelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String name = (String) WiFiTx6620.this.mChannelAdapter.getItem(position);
                WiFiTx6620.this.mChannel.setSelectedChannel(name);
                EMWifi.setChannel((long) ChannelInfo.getChannelFrequency(ChannelInfo.parseChannelId(name)));
                WiFiTx6620.this.uiUpdateTxPower();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mRateAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        if (!this.mIsAdvancedMode) {
            for (int i = 0; i < this.mRate.getRateNumber(); i++) {
                this.mRateAdapter.add(this.mRate.mPszRate[i]);
            }
        }
        this.mRateSpinner.setAdapter(this.mRateAdapter);
        this.mRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                boolean z;
                int lastGroup = WiFiTx6620.this.mRate.getRateGroupExt(WiFiTx6620.this.mRate.mRateIndex);
                WiFiTx6620.this.mRate.mRateIndex = arg2;
                int targetIndex = arg2;
                if (WiFiTx6620.this.mIsAdvancedMode) {
                    WiFiTx6620 wiFiTx6620 = WiFiTx6620.this;
                    targetIndex = wiFiTx6620.convertAdvancedRateIndex2Normal(wiFiTx6620.mChannelBandwidth, targetIndex);
                    WiFiTx6620.this.mRate.mRateIndex = targetIndex;
                }
                int currentGroup = WiFiTx6620.this.mRate.getRateGroupExt(WiFiTx6620.this.mRate.mRateIndex);
                Elog.i(WiFiTx6620.TAG, "The mRateIndex is : " + arg2 + " targetIndex:" + targetIndex);
                if (lastGroup != currentGroup) {
                    WiFiTx6620 wiFiTx66202 = WiFiTx6620.this;
                    if (targetIndex >= 12) {
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean unused = wiFiTx66202.mHighRateSelected = z;
                    int delta = WiFiTx6620.this.mHighRateSelected ? 2 : 0;
                    WiFiTx6620.this.mPreambleAdapter.clear();
                    WiFiTx6620.this.addPreambleItems(currentGroup);
                    int unused2 = WiFiTx6620.this.mPreambleIndex = delta;
                    WiFiTx6620.this.mPreambleSpinner.setAdapter(WiFiTx6620.this.mPreambleAdapter);
                }
                WiFiTx6620.this.uiUpdateTxPower();
                if (targetIndex >= 4) {
                    if (WiFiTx6620.this.mCCKRateSelected) {
                        boolean unused3 = WiFiTx6620.this.mCCKRateSelected = false;
                        WiFiTx6620.this.mModeAdapter.remove(WiFiTx6620.this.mMode[2]);
                        WiFiTx6620.this.mModeSpinner.setSelection(0);
                    }
                } else if (!WiFiTx6620.this.mCCKRateSelected) {
                    boolean unused4 = WiFiTx6620.this.mCCKRateSelected = true;
                    WiFiTx6620.this.mModeAdapter.insert(WiFiTx6620.this.mMode[2], 2);
                    WiFiTx6620.this.mModeSpinner.setSelection(0);
                }
                WiFiTx6620.access$1608(WiFiTx6620.this);
                WiFiTx6620.this.updateChannels();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (!this.mIsAdvancedMode) {
            this.mRateSpinner.setSelection(this.mRate.mOFDMStartIndex);
        }
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, 17367048);
        this.mModeAdapter = arrayAdapter3;
        arrayAdapter3.setDropDownViewResource(17367049);
        int i2 = 0;
        while (true) {
            String[] strArr = this.mMode;
            if (i2 >= strArr.length) {
                break;
            }
            this.mModeAdapter.add(strArr[i2]);
            i2++;
        }
        this.mModeSpinner.setAdapter(this.mModeAdapter);
        this.mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int unused = WiFiTx6620.this.mModeIndex = arg2;
                Elog.i(WiFiTx6620.TAG, "The mModeIndex is : " + arg2);
                if (!WiFiTx6620.this.mCCKRateSelected && arg2 >= 2) {
                    WiFiTx6620.access$1808(WiFiTx6620.this);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(this, 17367048);
        this.mPreambleAdapter = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(17367049);
        addPreambleItems(0);
        this.mPreambleSpinner.setAdapter(this.mPreambleAdapter);
        this.mPreambleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                WiFiTx6620 wiFiTx6620 = WiFiTx6620.this;
                int unused = wiFiTx6620.mPreambleIndex = (wiFiTx6620.mHighRateSelected ? 2 : 0) + arg2;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> bandwidthAdapter = new ArrayAdapter<>(this, 17367048);
        bandwidthAdapter.setDropDownViewResource(17367049);
        if (this.mIsAdvancedMode) {
            bandwidthAdapter.add(this.mBandwidth[4]);
        } else {
            for (int i3 = 0; i3 < 4; i3++) {
                bandwidthAdapter.add(this.mBandwidth[i3]);
            }
        }
        this.mBandwidthSpinner.setAdapter(bandwidthAdapter);
        this.mBandwidthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int i;
                Elog.i(WiFiTx6620.TAG, "mBandwidthSpinner.onItemSelected:" + arg2 + " mBandwidthIndex:" + WiFiTx6620.this.mBandwidthIndex + " mLastBandwidth:" + WiFiTx6620.this.mLastBandwidth);
                WiFiTx6620 wiFiTx6620 = WiFiTx6620.this;
                if (arg2 < wiFiTx6620.mBandwidth.length) {
                    i = arg2;
                } else {
                    i = WiFiTx6620.this.mBandwidthIndex;
                }
                int unused = wiFiTx6620.mBandwidthIndex = i;
                if (WiFiTx6620.this.mBandwidth[4].equals(WiFiTx6620.this.mBandwidthSpinner.getSelectedItem().toString())) {
                    int unused2 = WiFiTx6620.this.mBandwidthIndex = 4;
                }
                if (WiFiTx6620.this.mBandwidthIndex == 4) {
                    WiFiTx6620.this.onAdvancedBandwidthSelected();
                } else {
                    WiFiTx6620.this.updateChannels();
                    WiFiTx6620.this.findViewById(R.id.wifi_bandwidth_advanced_ll).setVisibility(8);
                }
                WiFiTx6620 wiFiTx66202 = WiFiTx6620.this;
                int unused3 = wiFiTx66202.mLastBandwidth = wiFiTx66202.mBandwidthIndex;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> guardIntervalAdapter = new ArrayAdapter<>(this, 17367048);
        guardIntervalAdapter.setDropDownViewResource(17367049);
        int i4 = 0;
        while (true) {
            String[] strArr2 = this.mGuardInterval;
            if (i4 < strArr2.length) {
                guardIntervalAdapter.add(strArr2[i4]);
                i4++;
            } else {
                this.mGuardIntervalSpinner.setAdapter(guardIntervalAdapter);
                this.mGuardIntervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        int i;
                        WiFiTx6620 wiFiTx6620 = WiFiTx6620.this;
                        if (arg2 < 2) {
                            i = arg2;
                        } else {
                            i = wiFiTx6620.mGuardIntervalIndex;
                        }
                        int unused = wiFiTx6620.mGuardIntervalIndex = i;
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mSpBwAdvCbw = (Spinner) findViewById(R.id.wifi_bandwidth_cbw_spn);
                this.mSpBwAdvDbw = (Spinner) findViewById(R.id.wifi_bandwidth_dbw_spn);
                this.mSpBwAdvPrimCh = (Spinner) findViewById(R.id.wifi_bandwidth_prim_ch_spn);
                setViewEnabled(true);
                initUiComponent();
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void addPreambleItems(int rateGroup) {
        if (rateGroup == 5) {
            int i = 2;
            while (true) {
                String[] strArr = this.mPreamble;
                if (i < strArr.length) {
                    this.mPreambleAdapter.add(strArr[i]);
                    i++;
                } else {
                    return;
                }
            }
        } else if (rateGroup == 0) {
            for (int i2 = 0; i2 < 2; i2++) {
                this.mPreambleAdapter.add(this.mPreamble[i2]);
            }
        } else if (rateGroup == 6) {
            this.mPreambleAdapter.add(this.mPreamble[0]);
        } else {
            Elog.d(TAG, "addPreambleItems; INVALID rateGroup:" + rateGroup);
        }
    }

    private void initUiComponent() {
        this.mVgAnt = (ViewGroup) findViewById(R.id.wifi_ant_vg);
        this.mRbAntMain = (RadioButton) findViewById(R.id.wifi_tx_ant_main);
        if (!this.mAntSwapSupport) {
            this.mVgAnt.setVisibility(8);
        }
        String[] strArr = BW_ADVANCED_ITEMS;
        ArrayAdapter<String> cbwAdapter = new ArrayAdapter<>(this, 17367048, strArr);
        cbwAdapter.setDropDownViewResource(17367049);
        this.mSpBwAdvCbw.setAdapter(cbwAdapter);
        this.mSpBwAdvCbw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Elog.i(WiFiTx6620.TAG, "mSpBwAdvCbw onItemSelected position:" + position + " mChannelBandwidth:" + WiFiTx6620.this.mChannelBandwidth);
                if (WiFiTx6620.this.mChannelBandwidth != position) {
                    WiFiTx6620.this.mChannelBandwidth = position;
                    WiFiTx6620.this.mDbwAdapter.clear();
                    for (int i = 0; i <= position; i++) {
                        WiFiTx6620.this.mDbwAdapter.add(WiFiTx6620.BW_ADVANCED_ITEMS[i]);
                    }
                    WiFiTx6620.this.mSpBwAdvDbw.setAdapter(WiFiTx6620.this.mDbwAdapter);
                    WiFiTx6620.this.mPrimChAdapter.clear();
                    int maxPrimCh = ((int) Math.pow(2.0d, (double) position)) - 1;
                    for (int i2 = 0; i2 <= maxPrimCh; i2++) {
                        WiFiTx6620.this.mPrimChAdapter.add(String.valueOf(i2));
                    }
                    WiFiTx6620.this.mSpBwAdvPrimCh.setAdapter(WiFiTx6620.this.mPrimChAdapter);
                    WiFiTx6620 wiFiTx6620 = WiFiTx6620.this;
                    wiFiTx6620.onAdvancedSelectChanged(wiFiTx6620.mChannelBandwidth, WiFiTx6620.this.mDataBandwidth, WiFiTx6620.this.mPrimarySetting);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mDbwAdapter = arrayAdapter;
        arrayAdapter.add(strArr[0]);
        this.mDbwAdapter.setDropDownViewResource(17367049);
        this.mSpBwAdvDbw.setAdapter(this.mDbwAdapter);
        this.mSpBwAdvDbw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != WiFiTx6620.this.mDataBandwidth) {
                    WiFiTx6620.this.mDataBandwidth = position;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mPrimChAdapter = arrayAdapter2;
        arrayAdapter2.add("0");
        this.mPrimChAdapter.setDropDownViewResource(17367049);
        this.mSpBwAdvPrimCh.setAdapter(this.mPrimChAdapter);
        this.mSpBwAdvPrimCh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != WiFiTx6620.this.mPrimarySetting) {
                    WiFiTx6620.this.mPrimarySetting = position;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateChannelByRateBandwidth(int rateIndex, int bandwidthIndex) {
        Elog.i(TAG, "updateChannelByRateBandwidth: rateIndex:" + rateIndex + " bandwidthIndex:" + bandwidthIndex);
        if (this.mRate.getRateGroup(rateIndex) == 0) {
            this.mChannel.remove5GChannels(this.mChannelAdapter);
        } else {
            this.mChannel.removeChannels(new int[]{14}, this.mChannelAdapter);
        }
        if (bandwidthIndex == 1) {
            this.mChannel.removeBw40mUnsupported2dot4GChannels(this.mChannelAdapter);
            this.mChannel.remove5GChannels(this.mChannelAdapter);
            if (this.mRate.getRateGroup(rateIndex) != 0) {
                this.mChannel.insertBw40MChannels(this.mChannelAdapter);
            }
        }
    }

    /* access modifiers changed from: private */
    public int convertAdvancedRateIndex2Normal(int cbw, int selectedIndex) {
        int targetIndex = selectedIndex;
        if (cbw == 0) {
            targetIndex = selectedIndex;
        } else if (cbw == 1) {
            targetIndex = selectedIndex + 12;
        } else if (cbw == 2) {
            targetIndex = selectedIndex + 12;
        }
        Elog.i(TAG, "convertAdvancedRateIndex2Normal: cbw: " + cbw + " targetIndex:" + targetIndex);
        return targetIndex;
    }

    private void updateRateByBandwidth(ArrayAdapter<String> adapter, int bandwidth) {
        String[] rateItems = null;
        if (bandwidth == 0) {
            rateItems = RateInfo.ADVANCED_RATE_20M;
        } else if (bandwidth == 1) {
            rateItems = RateInfo.ADVANCED_RATE_40M;
        } else if (bandwidth == 2) {
            rateItems = RateInfo.ADVANCED_RATE_80M;
        } else {
            Elog.d(TAG, "updateRateByBandwidth:Invalid bandwith " + bandwidth);
        }
        if (rateItems != null) {
            adapter.clear();
            for (String add : rateItems) {
                adapter.add(add);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onAdvancedBandwidthSelected() {
        findViewById(R.id.wifi_bandwidth_advanced_ll).setVisibility(0);
        updateChannels();
    }

    private void updateChannelByAdvancedSetting(int cbw, int dbw, int primCh) {
        Elog.i(TAG, "updateChannelByAdvancedSetting: cbw:" + cbw);
        if (cbw == 0) {
            this.mChannelAdapter.clear();
            this.mChannel.addSupported2dot4gChannels(this.mChannelAdapter, true);
            this.mChannel.addSupported5gChannelsByBandwidth(this.mChannelAdapter, 1, true);
        } else if (cbw == 1) {
            this.mChannelAdapter.clear();
            this.mChannel.addSupported2dot4gChannels(this.mChannelAdapter, true);
            this.mChannel.removeBw40mUnsupported2dot4GChannels(this.mChannelAdapter);
            this.mChannel.addSupported5gChannelsByBandwidth(this.mChannelAdapter, 2, true);
        } else if (cbw == 2) {
            this.mChannelAdapter.clear();
            this.mChannel.addSupported5gChannelsByBandwidth(this.mChannelAdapter, 4, true);
        }
        updateRateByBandwidth(this.mRateAdapter, cbw);
        if (this.mRateUpdateCounter == 0) {
            this.mRateSpinner.setAdapter(this.mRateAdapter);
        } else {
            this.mRateUpdateCounter = 0;
        }
    }

    /* access modifiers changed from: private */
    public void onAdvancedSelectChanged(int cbw, int dbw, int primCh) {
        updateChannels();
    }

    public void onClick(View view) {
        if (view.getId() == this.mBtnGo.getId()) {
            onClickBtnTxGo();
        } else if (view.getId() == this.mBtnStop.getId()) {
            onClickBtnTxStop();
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
    public void uiUpdateTxPower() {
        int i;
        short ucGain = 0;
        long[] gain = new long[3];
        int comboChannelIndex = this.mChannel.getSelectedChannelId();
        if (this.mBandwidthIndex == 1) {
            i = 32768;
        } else {
            i = 0;
        }
        int comboChannelIndex2 = comboChannelIndex | i;
        Elog.i(TAG, "channelIdx " + comboChannelIndex2 + " rateIdx " + this.mRate.mRateIndex + " gain " + Arrays.toString(gain) + " Len " + 3);
        if (EMWifi.readTxPowerFromEEPromEx((long) comboChannelIndex2, (long) this.mRate.mRateIndex, gain, 3) == 0) {
            long i4TxPwrGain = gain[0];
            Elog.i(TAG, "i4TxPwrGain from uiUpdateTxPower is " + i4TxPwrGain);
            ucGain = (short) ((int) (255 & i4TxPwrGain));
        }
        this.mEtTxGain.setText(String.format(Locale.ENGLISH, getString(R.string.wifi_tx_gain_format), new Object[]{Double.valueOf(((double) ucGain) / 2.0d)}));
    }

    /* access modifiers changed from: private */
    public void updateChannels() {
        int targetBandwidth = this.mBandwidthIndex;
        this.mChannel.resetSupportedChannels(this.mChannelAdapter);
        if (this.mBandwidthIndex == 4) {
            updateChannelByAdvancedSetting(this.mChannelBandwidth, this.mDataBandwidth, this.mPrimarySetting);
        }
        updateChannelByRateBandwidth(this.mRate.mRateIndex, targetBandwidth);
        boolean bUpdateWifiChannel = true;
        if (this.mChannelAdapter.getCount() == 0) {
            this.mBtnGo.setEnabled(false);
            bUpdateWifiChannel = false;
        } else {
            this.mBtnGo.setEnabled(true);
        }
        if (bUpdateWifiChannel) {
            updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mChannelSpinner);
            uiUpdateTxPower();
        }
    }

    private void onClickBtnTxGo() {
        try {
            long u4TxGainVal = (long) (2.0f * Float.parseFloat(this.mEtTxGain.getText().toString()));
            this.mEtTxGain.setText(String.format(Locale.ENGLISH, getString(R.string.wifi_tx_gain_format), new Object[]{Double.valueOf(((double) u4TxGainVal) / 2.0d)}));
            this.mTxGainVal = u4TxGainVal;
            long j = 255;
            if (u4TxGainVal <= 255) {
                j = u4TxGainVal;
            }
            this.mTxGainVal = j;
            if (j < 0) {
                j = 0;
            }
            this.mTxGainVal = j;
            Elog.i(TAG, "Wifi Tx Test : " + this.mMode[this.mModeIndex]);
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

    /* access modifiers changed from: private */
    public void setViewEnabled(boolean state) {
        this.mChannelSpinner.setEnabled(state);
        this.mGuardIntervalSpinner.setEnabled(state);
        this.mBandwidthSpinner.setEnabled(state);
        this.mPreambleSpinner.setEnabled(state);
        this.mEtPkt.setEnabled(state);
        this.mEtPktCnt.setEnabled(state);
        this.mEtTxGain.setEnabled(state);
        this.mRateSpinner.setEnabled(state);
        this.mModeSpinner.setEnabled(state);
        this.mBtnGo.setEnabled(state);
        this.mBtnStop.setEnabled(!state);
        this.mSpBwAdvCbw.setEnabled(state);
        this.mSpBwAdvDbw.setEnabled(state);
        this.mSpBwAdvPrimCh.setEnabled(state);
    }

    private void onClickBtnTxStop() {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            handler.sendEmptyMessage(2);
        }
        switch (this.mModeIndex) {
            case 0:
                return;
            case 4:
                EMWifi.setPnpPower(1);
                EMWifi.setTestMode();
                EMWifi.setChannel((long) this.mChannel.getSelectedFrequency());
                uiUpdateTxPower();
                return;
            default:
                EMWifi.setStandBy();
                return;
        }
    }

    /* access modifiers changed from: private */
    public void setAntSwpIdx() {
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

    /* access modifiers changed from: private */
    public void setRateIndex() {
        int rateIndex = this.mRate.mRateIndex;
        if (this.mHighRateSelected) {
            int rateIndex2 = rateIndex - 12;
            if (rateIndex2 > 9) {
                rateIndex2 = 32;
            }
            rateIndex = rateIndex2 | Integer.MIN_VALUE;
        }
        Elog.i(TAG, String.format("TX rate index = 0x%08x", new Object[]{Integer.valueOf(rateIndex)}));
        EMWifi.setATParam(3, (long) rateIndex);
    }

    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Message message = msg;
            Elog.i(WiFiTx6620.TAG, "receive msg " + message.what);
            long[] u4Value = new long[2];
            switch (message.what) {
                case 1:
                    switch (WiFiTx6620.this.mTargetModeIndex) {
                        case 0:
                            EMWifi.setATParam(2, WiFiTx6620.this.mTxGainVal);
                            EMWifi.setATParam(4, (long) WiFiTx6620.this.mPreambleIndex);
                            EMWifi.setATParam(5, 0);
                            EMWifi.setATParam(6, WiFiTx6620.this.mPktLenNum);
                            EMWifi.setATParam(7, WiFiTx6620.this.mCntNum);
                            EMWifi.setATParam(8, 20);
                            EMWifi.setATParam(9, 0);
                            EMWifi.setATParam(10, PlaybackStateCompat.ACTION_PREPARE_FROM_URI);
                            for (long aTParam : WiFiTx6620.PACKCONTENT_BUFFER) {
                                EMWifi.setATParam(12, aTParam);
                            }
                            EMWifi.setATParam(13, 1);
                            EMWifi.setATParam(14, 2);
                            EMWifi.setATParam(16, (long) WiFiTx6620.this.mGuardIntervalIndex);
                            Elog.i(WiFiTx6620.TAG, "The mBandwidthIndex is : " + WiFiTx6620.this.mBandwidthIndex);
                            if (4 == WiFiTx6620.this.mBandwidthIndex) {
                                EMWifi.setATParam(71, (long) WiFiTx6620.this.mChannelBandwidth);
                                EMWifi.setATParam(72, (long) WiFiTx6620.this.mDataBandwidth);
                                EMWifi.setATParam(73, (long) WiFiTx6620.this.mPrimarySetting);
                            } else {
                                EMWifi.setATParam(15, (long) WiFiTx6620.this.mBandwidthIndex);
                            }
                            WiFiTx6620.this.setRateIndex();
                            EMWifi.setChannel((long) WiFiTx6620.this.mChannel.getSelectedFrequency());
                            WiFiTx6620.this.setAntSwpIdx();
                            EMWifi.setATParam(1, 1);
                            if (WiFiTx6620.this.mCntNum != 0) {
                                sendEmptyMessageDelayed(3, 1000);
                                return;
                            }
                            return;
                        case 1:
                            if (4 == WiFiTx6620.this.mBandwidthIndex) {
                                EMWifi.setATParam(71, (long) WiFiTx6620.this.mChannelBandwidth);
                                EMWifi.setATParam(72, (long) WiFiTx6620.this.mDataBandwidth);
                                EMWifi.setATParam(73, (long) WiFiTx6620.this.mPrimarySetting);
                            } else {
                                EMWifi.setATParam(15, (long) WiFiTx6620.this.mBandwidthIndex);
                            }
                            WiFiTx6620.this.setRateIndex();
                            EMWifi.setATParam(2, WiFiTx6620.this.mTxGainVal);
                            EMWifi.setATParam(4, (long) WiFiTx6620.this.mPreambleIndex);
                            EMWifi.setATParam(5, 0);
                            WiFiTx6620.this.setAntSwpIdx();
                            EMWifi.setATParam(1, 4);
                            return;
                        case 2:
                            EMWifi.setATParam(2, WiFiTx6620.this.mTxGainVal);
                            EMWifi.setATParam(5, 0);
                            if (WiFiTx6620.this.mCCKRateSelected) {
                                EMWifi.setATParam(65, 5);
                            } else {
                                EMWifi.setATParam(65, 2);
                            }
                            WiFiTx6620.this.setAntSwpIdx();
                            EMWifi.setATParam(1, 10);
                            return;
                        case 3:
                            EMWifi.setATParam(2, WiFiTx6620.this.mTxGainVal);
                            EMWifi.setATParam(5, 0);
                            WiFiTx6620.this.setAntSwpIdx();
                            EMWifi.setATParam(1, 5);
                            return;
                        default:
                            return;
                    }
                case 2:
                    u4Value[0] = (long) EMWifi.setATParam(1, 0);
                    if (WiFiTx6620.this.mEventHandler != null) {
                        WiFiTx6620.this.mEventHandler.removeMessages(3);
                    }
                    WiFiTx6620.this.mHandler.sendEmptyMessage(4);
                    return;
                case 3:
                    u4Value[0] = 0;
                    if (WiFiTx6620.this.mModeIndex == 1) {
                        boolean completed = false;
                        if (EMWifi.getATParam(32, u4Value) == 0) {
                            Elog.i(WiFiTx6620.TAG, "query Transmitted packet count succeed, count = " + u4Value[0] + " target count = " + 100);
                            if (u4Value[0] == 100) {
                                completed = true;
                            }
                        } else {
                            Elog.w(WiFiTx6620.TAG, "query Transmitted packet count failed");
                        }
                        if (!completed) {
                            u4Value[0] = (long) EMWifi.setATParam(1, 0);
                        }
                        int unused = WiFiTx6620.this.mTargetModeIndex = 1;
                        sendEmptyMessage(1);
                        return;
                    }
                    try {
                        long pktCnt = Long.parseLong(WiFiTx6620.this.mEtPktCnt.getText().toString());
                        if (EMWifi.getATParam(32, u4Value) == 0) {
                            Elog.i(WiFiTx6620.TAG, "query Transmitted packet count succeed, count = " + u4Value[0] + " target count = " + pktCnt);
                            if (u4Value[0] == pktCnt) {
                                removeMessages(3);
                                WiFiTx6620.this.mHandler.sendEmptyMessage(4);
                                return;
                            }
                        } else {
                            Elog.w(WiFiTx6620.TAG, "query Transmitted packet count failed");
                        }
                        sendEmptyMessageDelayed(3, 1000);
                        return;
                    } catch (NumberFormatException e) {
                        Toast.makeText(WiFiTx6620.this, "invalid input value", 0).show();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }
}
