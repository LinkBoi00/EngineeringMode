package com.mediatek.engineermode.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.wifi.WiFiTestActivity;

public class WiFiRx6632 extends WiFiTestActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int BAND_WIDTH_160NC_INDEX = 6;
    private static final int BAND_WIDTH_160_INDEX = 5;
    private static final int BAND_WIDTH_20_INDEX = 2;
    private static final int BAND_WIDTH_40_INDEX = 3;
    private static final int BAND_WIDTH_80_INDEX = 4;
    private static final int COMMAND_INDEX_STARTRX = 2;
    private static final int HANDLER_EVENT_RX = 2;
    protected static final long HANDLER_RX_DELAY_TIME = 1000;
    private static final long PERCENT = 100;
    private static final String TAG = "WifiRx";
    private static final String TEXT_ZERO = "0";
    private static final int WAIT_COUNT = 10;
    private static final int WIFI_MODE_DBDC_RX0 = 2;
    private static final int WIFI_MODE_DBDC_RX1 = 3;
    private static final int WIFI_MODE_NORMAL = 1;
    private static final String[] sBandWidth = {"5MHz", "10MHz", "20MHz", "40MHz", "80MHz", "160MHz", "160NC"};
    /* access modifiers changed from: private */
    public static final String[] sBandWidthDBW = {"BW5", "BW10", "BW20", "BW40", "BW80", "BW160"};
    private boolean mAntSwapSupport = false;
    /* access modifiers changed from: private */
    public int mBandwidthIndex = 0;
    private Button mBtnGo = null;
    private Button mBtnStop = null;
    private ChannelInfo mChannel = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mChannelAdapter = null;
    /* access modifiers changed from: private */
    public int mChannelRx0Freq;
    /* access modifiers changed from: private */
    public int mChannelRx1Freq;
    private CheckBox mCkDuplicateMode = null;
    /* access modifiers changed from: private */
    public CheckBox mCkWf0 = null;
    /* access modifiers changed from: private */
    public CheckBox mCkWf1 = null;
    /* access modifiers changed from: private */
    public int mDataBandwidthIndex = 0;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mDbwAdapter = null;
    private Handler mEventHandler = null;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (2 == msg.what) {
                long[] i4Rx = new long[3];
                long i4RxPer = -1;
                Elog.i(WiFiRx6632.TAG, "The Handle event is : HANDLER_EVENT_RX");
                try {
                    i4RxPer = Long.parseLong(WiFiRx6632.this.mTvPer.getText().toString());
                } catch (NumberFormatException e) {
                    Elog.w(WiFiRx6632.TAG, "Long.parseLong NumberFormatException: " + e.getMessage());
                }
                if (WiFiRx6632.this.mWiFiMode == 2) {
                    EMWifi.setATParam(104, 0);
                } else if (WiFiRx6632.this.mWiFiMode == 3) {
                    EMWifi.setATParam(104, 1);
                }
                EMWifi.getPacketRxStatus(i4Rx, 3);
                Elog.i(WiFiRx6632.TAG, "rx ok = " + i4Rx[0] + "fcs error = " + i4Rx[1] + "RSSI = " + i4Rx[2]);
                long i4RxCntOk = i4Rx[0];
                long i4RxCntFcsErr = i4Rx[1];
                long i4RxRssiWiFi0 = (i4Rx[2] & 255) - 255;
                long i4RxRssiWiFi1 = ((i4Rx[2] & 65280) >> 8) - 255;
                if (i4RxCntFcsErr + i4RxCntOk != 0) {
                    i4RxPer = (WiFiRx6632.PERCENT * i4RxCntFcsErr) / (i4RxCntFcsErr + i4RxCntOk);
                }
                WiFiRx6632.this.mTvFcs.setText(String.valueOf(i4RxCntFcsErr));
                WiFiRx6632.this.mTvRx.setText(String.valueOf(i4RxCntOk));
                WiFiRx6632.this.mTvPer.setText(String.valueOf(i4RxPer));
                if (!WiFiRx6632.this.mCkWf0.isChecked() || !WiFiRx6632.this.mCkWf1.isChecked()) {
                    WiFiRx6632.this.mTvWiFi1RssiContext.setVisibility(8);
                    WiFiRx6632.this.mTvWiFi1RssiText.setVisibility(8);
                    WiFiRx6632.this.mTvWiFi0RssiText.setText("RSSI: ");
                    if (WiFiRx6632.this.mWiFiMode == 2 || WiFiRx6632.this.mWiFiMode == 3) {
                        WiFiRx6632.this.mTvWiFi0RssiContext.setText(String.valueOf(i4RxRssiWiFi0));
                    } else {
                        WiFiRx6632.this.mTvWiFi0RssiContext.setText(WiFiRx6632.this.mCkWf0.isChecked() ? String.valueOf(i4RxRssiWiFi0) : String.valueOf(i4RxRssiWiFi1));
                    }
                } else {
                    WiFiRx6632.this.mTvWiFi1RssiContext.setVisibility(0);
                    WiFiRx6632.this.mTvWiFi1RssiText.setVisibility(0);
                    WiFiRx6632.this.mTvWiFi0RssiText.setText("RSSI(WiFi0):");
                    WiFiRx6632.this.mTvWiFi0RssiContext.setText(String.valueOf(i4RxRssiWiFi0));
                    WiFiRx6632.this.mTvWiFi1RssiContext.setText(String.valueOf(i4RxRssiWiFi1));
                }
            }
            WiFiRx6632.this.mHandler.sendEmptyMessageDelayed(2, WiFiRx6632.HANDLER_RX_DELAY_TIME);
        }
    };
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mPrimChAdapter = null;
    /* access modifiers changed from: private */
    public int mPrimaryIndex = 0;
    private RadioButton mRbAntMain = null;
    private Spinner mSpBandwidth = null;
    private Spinner mSpChannelRx0 = null;
    private Spinner mSpChannelRx1 = null;
    /* access modifiers changed from: private */
    public Spinner mSpDbw = null;
    /* access modifiers changed from: private */
    public Spinner mSpPrimCh = null;
    private HandlerThread mTestThread = null;
    /* access modifiers changed from: private */
    public TextView mTvDbw = null;
    /* access modifiers changed from: private */
    public TextView mTvFcs = null;
    /* access modifiers changed from: private */
    public TextView mTvPer = null;
    /* access modifiers changed from: private */
    public TextView mTvPrimCh = null;
    /* access modifiers changed from: private */
    public TextView mTvRx = null;
    /* access modifiers changed from: private */
    public TextView mTvWiFi0RssiContext = null;
    /* access modifiers changed from: private */
    public TextView mTvWiFi0RssiText = null;
    /* access modifiers changed from: private */
    public TextView mTvWiFi1RssiContext = null;
    /* access modifiers changed from: private */
    public TextView mTvWiFi1RssiText = null;
    private ViewGroup mVgAnt = null;
    /* access modifiers changed from: private */
    public View mViewChannelRx1 = null;
    /* access modifiers changed from: private */
    public int mWiFiMode = 1;

    private void initUiLayout() {
        this.mCkWf0 = (CheckBox) findViewById(R.id.wifi_wfx_0);
        this.mCkWf1 = (CheckBox) findViewById(R.id.wifi_wfx_1);
        this.mCkDuplicateMode = (CheckBox) findViewById(R.id.wifi_duplicate_mode_rx);
        this.mCkWf0.setOnCheckedChangeListener(this);
        this.mCkWf1.setOnCheckedChangeListener(this);
        this.mCkDuplicateMode.setOnCheckedChangeListener(this);
        this.mCkWf0.setChecked(true);
        this.mBtnGo = (Button) findViewById(R.id.WiFi_Go_Rx);
        this.mBtnStop = (Button) findViewById(R.id.WiFi_Stop_Rx);
        this.mBtnGo.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        this.mSpBandwidth = (Spinner) findViewById(R.id.wifi_bandwidth_spinner);
        this.mSpChannelRx0 = (Spinner) findViewById(R.id.wifi_rx0_channel_spinner);
        this.mSpChannelRx1 = (Spinner) findViewById(R.id.wifi_rx1_channel_spinner);
        this.mViewChannelRx1 = findViewById(R.id.wifi_rx1_channel_layout);
        this.mTvDbw = (TextView) findViewById(R.id.wifi_bandwidth_dbw_tv);
        this.mSpDbw = (Spinner) findViewById(R.id.wifi_bandwidth_dbw_spn);
        this.mTvPrimCh = (TextView) findViewById(R.id.wifi_bandwidth_prim_ch_tv);
        this.mSpPrimCh = (Spinner) findViewById(R.id.wifi_bandwidth_prim_ch_spn);
        this.mTvFcs = (TextView) findViewById(R.id.WiFi_FCS_Content);
        this.mTvRx = (TextView) findViewById(R.id.WiFi_Rx_Content);
        this.mTvPer = (TextView) findViewById(R.id.WiFi_PER_Content);
        this.mTvWiFi0RssiText = (TextView) findViewById(R.id.WiFi0_RX_RSSI_Text);
        this.mTvWiFi1RssiText = (TextView) findViewById(R.id.WiFi1_RX_RSSI_Text);
        this.mTvWiFi0RssiContext = (TextView) findViewById(R.id.WiFi0_RX_RSSI_Content);
        this.mTvWiFi1RssiContext = (TextView) findViewById(R.id.WiFi1_RX_RSSI_Content);
        this.mChannel = new ChannelInfo();
        this.mVgAnt = (ViewGroup) findViewById(R.id.wifi_ant_vg);
        this.mRbAntMain = (RadioButton) findViewById(R.id.wifi_rx_ant_main);
        setViewEnabled(true);
    }

    private void initUiComponent() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mChannelAdapter.clear();
        ChannelInfo.getSupportChannels();
        this.mChannel.resetSupportedChannels(this.mChannelAdapter);
        this.mSpChannelRx0.setAdapter(this.mChannelAdapter);
        this.mSpChannelRx0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int unused = WiFiRx6632.this.mChannelRx0Freq = ChannelInfo.getChannelFrequency(ChannelInfo.parseChannelId((String) WiFiRx6632.this.mChannelAdapter.getItem(position)));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpChannelRx1.setAdapter(this.mChannelAdapter);
        this.mSpChannelRx1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int unused = WiFiRx6632.this.mChannelRx1Freq = ChannelInfo.getChannelFrequency(ChannelInfo.parseChannelId((String) WiFiRx6632.this.mChannelAdapter.getItem(position)));
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
                int unused = WiFiRx6632.this.mBandwidthIndex = arg2;
                WiFiRx6632.this.mTvPrimCh.setEnabled(true);
                WiFiRx6632.this.mSpPrimCh.setEnabled(true);
                WiFiRx6632.this.mTvDbw.setEnabled(true);
                WiFiRx6632.this.mSpDbw.setEnabled(true);
                WiFiRx6632.this.mTvPrimCh.setVisibility(0);
                WiFiRx6632.this.mSpPrimCh.setVisibility(0);
                if (arg2 < 2) {
                    WiFiRx6632.this.mTvPrimCh.setVisibility(8);
                    WiFiRx6632.this.mSpPrimCh.setVisibility(8);
                    WiFiRx6632.this.mDbwAdapter.clear();
                    WiFiRx6632.this.mDbwAdapter.add(WiFiRx6632.sBandWidthDBW[arg2]);
                    WiFiRx6632.this.mViewChannelRx1.setVisibility(8);
                    WiFiRx6632.this.mSpDbw.setAdapter(WiFiRx6632.this.mDbwAdapter);
                } else if (arg2 <= 5) {
                    WiFiRx6632.this.mDbwAdapter.clear();
                    for (int i = 2; i <= arg2; i++) {
                        WiFiRx6632.this.mDbwAdapter.add(WiFiRx6632.sBandWidthDBW[i]);
                    }
                    WiFiRx6632.this.mSpDbw.setAdapter(WiFiRx6632.this.mDbwAdapter);
                    WiFiRx6632.this.mPrimChAdapter.clear();
                    int maxPrimCh = ((int) Math.pow(2.0d, (double) (arg2 - 2))) - 1;
                    for (int i2 = 0; i2 <= maxPrimCh; i2++) {
                        WiFiRx6632.this.mPrimChAdapter.add(String.valueOf(i2));
                    }
                    WiFiRx6632.this.mSpPrimCh.setAdapter(WiFiRx6632.this.mPrimChAdapter);
                    WiFiRx6632.this.mViewChannelRx1.setVisibility(8);
                } else {
                    WiFiRx6632.this.mSpPrimCh.setEnabled(false);
                    WiFiRx6632.this.mSpDbw.setEnabled(false);
                    WiFiRx6632.this.mViewChannelRx1.setVisibility(0);
                    WiFiRx6632.this.mDbwAdapter.clear();
                    for (int i3 = 2; i3 < arg2; i3++) {
                        WiFiRx6632.this.mDbwAdapter.add(WiFiRx6632.sBandWidthDBW[i3]);
                    }
                    WiFiRx6632.this.mSpDbw.setAdapter(WiFiRx6632.this.mDbwAdapter);
                    WiFiRx6632.this.mSpDbw.setSelection(3);
                    WiFiRx6632.this.mPrimChAdapter.clear();
                    WiFiRx6632.this.mPrimChAdapter.add("0");
                    WiFiRx6632.this.mSpPrimCh.setAdapter(WiFiRx6632.this.mPrimChAdapter);
                }
                WiFiRx6632.this.updateChannels();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mDbwAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        this.mSpDbw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != WiFiRx6632.this.mDataBandwidthIndex) {
                    int unused = WiFiRx6632.this.mDataBandwidthIndex = position;
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
                if (position != WiFiRx6632.this.mPrimaryIndex) {
                    int unused = WiFiRx6632.this.mPrimaryIndex = position;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_rx_6632);
        Intent intent = getIntent();
        this.mAntSwapSupport = intent.getBooleanExtra("key_ant_swap", false);
        String data = intent.getStringExtra("mode");
        if (data == null) {
            this.mWiFiMode = 1;
        } else if (data.equals("RX Band0")) {
            this.mWiFiMode = 2;
        } else if (data.equals("RX Band1")) {
            this.mWiFiMode = 3;
        }
        initUiLayout();
        initUiComponent();
        int i = this.mWiFiMode;
        if (i == 2) {
            this.mCkWf0.setChecked(true);
            this.mCkWf1.setChecked(false);
            this.mCkWf0.setVisibility(8);
            this.mCkWf1.setVisibility(8);
            this.mCkDuplicateMode.setVisibility(8);
        } else if (i == 3) {
            this.mCkWf0.setChecked(false);
            this.mCkWf1.setChecked(true);
            this.mCkWf0.setVisibility(8);
            this.mCkWf1.setVisibility(8);
            this.mCkDuplicateMode.setVisibility(8);
        }
        if (!this.mAntSwapSupport) {
            this.mVgAnt.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void updateChannels() {
        ArrayAdapter<String> tempChAdapter = new ArrayAdapter<>(this, 17367048);
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
        boolean bUpdateWifiChannel = false;
        int count = tempChAdapter.getCount();
        if (count == this.mChannelAdapter.getCount()) {
            int k = 0;
            while (true) {
                if (k >= count) {
                    break;
                } else if (!tempChAdapter.getItem(k).equals(this.mChannelAdapter.getItem(k))) {
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
            int i3 = this.mWiFiMode;
            if (i3 == 2) {
                EMWifi.setATParam(104, 0);
            } else if (i3 == 3) {
                EMWifi.setATParam(104, 1);
            }
            updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mSpChannelRx0);
            if (this.mSpChannelRx1.getVisibility() == 0) {
                updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mSpChannelRx1);
            }
        }
    }

    public void onClick(View v) {
        if (v.getId() == this.mBtnGo.getId()) {
            onClickBtnRxGo();
        } else if (v.getId() == this.mBtnStop.getId()) {
            onClickBtnRxStop();
        }
    }

    private boolean checkRxPath() {
        if (this.mCkWf0.isChecked() || this.mCkWf1.isChecked()) {
            return true;
        }
        Toast.makeText(this, R.string.wifi_dialog_no_path_select, 1).show();
        return false;
    }

    private void onClickBtnRxGo() {
        long wfValue;
        int cbw;
        long antennaIdx;
        if (checkRxPath()) {
            setViewEnabled(false);
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
            if (this.mWiFiMode == 3) {
                wfValue = 65537;
                if (this.mCkWf1.isChecked()) {
                    if (!this.mCkWf0.isChecked()) {
                        wfValue = 131073;
                    } else {
                        wfValue = 196609;
                    }
                }
            } else {
                wfValue = PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
                if (this.mCkWf1.isChecked()) {
                    if (!this.mCkWf0.isChecked()) {
                        wfValue = PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
                    } else {
                        wfValue = 196608;
                    }
                }
            }
            EMWifi.setATParam(106, wfValue);
            int i2 = this.mBandwidthIndex;
            if (i2 >= 2) {
                cbw = i2 - 2;
            } else {
                cbw = i2 + 5;
            }
            EMWifi.setATParam(71, (long) cbw);
            EMWifi.setATParam(72, (long) this.mDataBandwidthIndex);
            if (this.mSpPrimCh.getVisibility() == 0) {
                EMWifi.setATParam(73, (long) this.mPrimaryIndex);
            }
            EMWifi.setATParam(18, (long) this.mChannelRx0Freq);
            if (this.mViewChannelRx1.getVisibility() == 0) {
                EMWifi.setATParam(65554, (long) this.mChannelRx1Freq);
            }
            if (this.mAntSwapSupport) {
                if (this.mRbAntMain.isChecked()) {
                    antennaIdx = (long) WiFiTestActivity.EnumRfAtAntswp.RF_AT_ANTSWP_MAIN.ordinal();
                } else {
                    antennaIdx = (long) WiFiTestActivity.EnumRfAtAntswp.RF_AT_ANTSWP_AUX.ordinal();
                }
                EMWifi.setATParam(153, antennaIdx);
            }
            EMWifi.setATParam(1, 2);
            this.mHandler.sendEmptyMessage(2);
            this.mTvFcs.setText("0");
            this.mTvRx.setText("0");
            this.mTvPer.setText("0");
        }
    }

    private void onClickBtnRxStop() {
        long[] u4Value = new long[1];
        this.mHandler.removeMessages(2);
        for (int i = 0; i < 10; i++) {
            u4Value[0] = (long) EMWifi.setATParam(1, 0);
            if (u4Value[0] == 0) {
                break;
            }
            SystemClock.sleep(10);
            Elog.w(TAG, "stop Rx test failed at the " + i + "times try");
        }
        setViewEnabled(true);
    }

    private void setViewEnabled(boolean state) {
        this.mBtnGo.setEnabled(state);
        this.mBtnStop.setEnabled(!state);
        this.mCkWf0.setEnabled(state);
        this.mCkWf1.setEnabled(state);
        this.mSpChannelRx0.setEnabled(state);
        this.mSpChannelRx1.setEnabled(state);
        this.mSpBandwidth.setEnabled(state);
        this.mSpDbw.setEnabled(state);
        this.mSpPrimCh.setEnabled(state);
        this.mCkDuplicateMode.setEnabled(state);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mHandler.removeMessages(2);
        super.onDestroy();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.wifi_duplicate_mode_rx) {
            if (isChecked) {
                this.mCkWf0.setChecked(true);
                this.mCkWf1.setChecked(true);
            }
        } else if (buttonView.getId() == R.id.wifi_wfx_0) {
            if (!isChecked) {
                this.mCkDuplicateMode.setChecked(false);
            }
        } else if (buttonView.getId() == R.id.wifi_wfx_1 && !isChecked) {
            this.mCkDuplicateMode.setChecked(false);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }
}
