package com.mediatek.engineermode.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.wifi.WiFiTestActivity;

public class WiFiRx6620 extends WiFiTestActivity implements View.OnClickListener {
    private static final int BANDWIDTH_INDEX_40M = 1;
    private static final int BANDWIDTH_INDEX_80M = 2;
    /* access modifiers changed from: private */
    public static final String[] BW_ADVANCED_ITEMS = {"BW20", "BW40", "BW80"};
    private static final int BW_INDX_ADVANCED = 4;
    private static final int HANDLER_EVENT_RX = 2;
    protected static final long HANDLER_RX_DELAY_TIME = 1000;
    private static final long PERCENT = 100;
    private static final String TAG = "WifiRx";
    private static final String TEXT_ZERO = "0";
    private static final int WAIT_COUNT = 10;
    private boolean mAntSwapSupport = false;
    /* access modifiers changed from: private */
    public final String[] mBandwidth = {"20MHz", "40MHz", "U20MHz", "L20MHz", "Advanced"};
    /* access modifiers changed from: private */
    public int mBandwidthIndex = 0;
    /* access modifiers changed from: private */
    public Spinner mBandwidthSpinner = null;
    private final AdapterView.OnItemSelectedListener mBandwidthSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            int i;
            WiFiRx6620 wiFiRx6620 = WiFiRx6620.this;
            if (position < wiFiRx6620.mBandwidth.length) {
                i = position;
            } else {
                i = WiFiRx6620.this.mBandwidthIndex;
            }
            int unused = wiFiRx6620.mBandwidthIndex = i;
            if (WiFiRx6620.this.mBandwidth[4].equals(WiFiRx6620.this.mBandwidthSpinner.getSelectedItem().toString())) {
                int unused2 = WiFiRx6620.this.mBandwidthIndex = 4;
            }
            if (WiFiRx6620.this.mBandwidthIndex == 4) {
                WiFiRx6620.this.onAdvancedBandwidthSelected();
                return;
            }
            WiFiRx6620.this.updateChannels();
            WiFiRx6620.this.findViewById(R.id.wifi_bandwidth_advanced_ll).setVisibility(8);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private Button mBtnGo = null;
    private Button mBtnStop = null;
    /* access modifiers changed from: private */
    public ChannelInfo mChannel = null;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mChannelAdapter = null;
    int mChannelBandwidth = 0;
    private Spinner mChannelSpinner = null;
    private final AdapterView.OnItemSelectedListener mChannelSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            WiFiRx6620.this.mChannel.setSelectedChannel((String) WiFiRx6620.this.mChannelAdapter.getItem(position));
            EMWifi.setChannel((long) WiFiRx6620.this.mChannel.getSelectedFrequency());
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    int mDataBandwidth = 0;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mDbwAdapter = null;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (2 == msg.what) {
                long[] i4Rx = new long[3];
                long i4RxPer = -1;
                Elog.i(WiFiRx6620.TAG, "The Handle event is : HANDLER_EVENT_RX");
                try {
                    i4RxPer = Long.parseLong(WiFiRx6620.this.mTvPer.getText().toString());
                } catch (NumberFormatException e) {
                    Elog.e(WiFiRx6620.TAG, "Long.parseLong NumberFormatException: " + e.getMessage());
                }
                EMWifi.getPacketRxStatus(i4Rx, 3);
                Elog.i(WiFiRx6620.TAG, "rx ok = " + String.valueOf(i4Rx[0]) + "fcs error = " + String.valueOf(i4Rx[1]));
                long i4RxCntOk = i4Rx[0];
                long i4RxCntFcsErr = i4Rx[1];
                if (i4RxCntFcsErr + i4RxCntOk != 0) {
                    i4RxPer = (WiFiRx6620.PERCENT * i4RxCntFcsErr) / (i4RxCntFcsErr + i4RxCntOk);
                }
                WiFiRx6620.this.mTvFcs.setText(String.valueOf(i4RxCntFcsErr));
                WiFiRx6620.this.mTvRx.setText(String.valueOf(i4RxCntOk));
                WiFiRx6620.this.mTvPer.setText(String.valueOf(i4RxPer));
            }
            WiFiRx6620.this.mHandler.sendEmptyMessageDelayed(2, WiFiRx6620.HANDLER_RX_DELAY_TIME);
        }
    };
    private long[] mInitData = null;
    private boolean mIsAdvancedMode = false;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mPrimChAdapter = null;
    int mPrimarySetting = 0;
    private RadioButton mRbAntMain = null;
    private Spinner mSpBwAdvCbw = null;
    /* access modifiers changed from: private */
    public Spinner mSpBwAdvDbw = null;
    /* access modifiers changed from: private */
    public Spinner mSpBwAdvPrimCh = null;
    /* access modifiers changed from: private */
    public TextView mTvFcs = null;
    /* access modifiers changed from: private */
    public TextView mTvPer = null;
    /* access modifiers changed from: private */
    public TextView mTvRx = null;
    private ViewGroup mVgAnt = null;

    /* access modifiers changed from: private */
    public void updateChannels() {
        this.mChannel.resetSupportedChannels(this.mChannelAdapter);
        updateChannelByBandwidth(this.mBandwidthIndex);
        if (this.mBandwidthIndex == 4) {
            updateChannelByAdvancedSetting(this.mChannelBandwidth, this.mDataBandwidth, this.mPrimarySetting);
        }
        boolean bUpdateWifiChannel = true;
        if (this.mChannelAdapter.getCount() == 0) {
            this.mBtnGo.setEnabled(false);
            bUpdateWifiChannel = false;
        } else {
            this.mBtnGo.setEnabled(true);
        }
        if (bUpdateWifiChannel) {
            updateWifiChannel(this.mChannel, this.mChannelAdapter, this.mChannelSpinner);
        }
    }

    private void updateChannelByBandwidth(int bandwidthIndex) {
        if (bandwidthIndex == 1) {
            this.mChannel.removeBw40mUnsupported2dot4GChannels(this.mChannelAdapter);
            this.mChannel.remove5GChannels(this.mChannelAdapter);
            this.mChannel.insertBw40MChannels(this.mChannelAdapter);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_rx_6620);
        Intent it = getIntent();
        this.mAntSwapSupport = it.getBooleanExtra("key_ant_swap", false);
        this.mIsAdvancedMode = it.getBooleanExtra("key_11ac", false);
        this.mTvFcs = (TextView) findViewById(R.id.WiFi_FCS_Content);
        this.mTvRx = (TextView) findViewById(R.id.WiFi_Rx_Content);
        this.mTvPer = (TextView) findViewById(R.id.WiFi_PER_Content);
        this.mBtnGo = (Button) findViewById(R.id.WiFi_Go_Rx);
        this.mBtnStop = (Button) findViewById(R.id.WiFi_Stop_Rx);
        this.mBtnGo.setOnClickListener(this);
        this.mBtnStop.setOnClickListener(this);
        this.mInitData = new long[3];
        this.mChannel = new ChannelInfo();
        this.mChannelSpinner = (Spinner) findViewById(R.id.WiFi_RX_Channel_Spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mChannelAdapter.clear();
        ChannelInfo.getSupportChannels();
        this.mChannel.resetSupportedChannels(this.mChannelAdapter);
        this.mChannelSpinner.setAdapter(this.mChannelAdapter);
        this.mChannelSpinner.setOnItemSelectedListener(this.mChannelSpinnerListener);
        this.mBandwidthSpinner = (Spinner) findViewById(R.id.WiFi_Bandwidth_Spinner);
        ArrayAdapter<String> bwAdapter = new ArrayAdapter<>(this, 17367048);
        bwAdapter.setDropDownViewResource(17367049);
        if (this.mIsAdvancedMode) {
            bwAdapter.add(this.mBandwidth[4]);
        } else {
            for (int i = 0; i < 4; i++) {
                bwAdapter.add(this.mBandwidth[i]);
            }
        }
        this.mBandwidthSpinner.setAdapter(bwAdapter);
        this.mBandwidthSpinner.setOnItemSelectedListener(this.mBandwidthSpinnerListener);
        this.mSpBwAdvCbw = (Spinner) findViewById(R.id.wifi_bandwidth_cbw_spn);
        this.mSpBwAdvDbw = (Spinner) findViewById(R.id.wifi_bandwidth_dbw_spn);
        this.mSpBwAdvPrimCh = (Spinner) findViewById(R.id.wifi_bandwidth_prim_ch_spn);
        setViewEnabled(true);
        initUiComponent();
    }

    private void initUiComponent() {
        this.mVgAnt = (ViewGroup) findViewById(R.id.wifi_ant_vg);
        this.mRbAntMain = (RadioButton) findViewById(R.id.wifi_rx_ant_main);
        if (!this.mAntSwapSupport) {
            this.mVgAnt.setVisibility(8);
        }
        String[] strArr = BW_ADVANCED_ITEMS;
        ArrayAdapter<String> cbwAdapter = new ArrayAdapter<>(this, 17367048, strArr);
        cbwAdapter.setDropDownViewResource(17367049);
        this.mSpBwAdvCbw.setAdapter(cbwAdapter);
        this.mSpBwAdvCbw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (WiFiRx6620.this.mChannelBandwidth != position) {
                    WiFiRx6620.this.mChannelBandwidth = position;
                    WiFiRx6620.this.mDbwAdapter.clear();
                    for (int i = 0; i <= position; i++) {
                        WiFiRx6620.this.mDbwAdapter.add(WiFiRx6620.BW_ADVANCED_ITEMS[i]);
                    }
                    WiFiRx6620.this.mSpBwAdvDbw.setAdapter(WiFiRx6620.this.mDbwAdapter);
                    WiFiRx6620.this.mPrimChAdapter.clear();
                    int maxPrimCh = ((int) Math.pow(2.0d, (double) position)) - 1;
                    for (int i2 = 0; i2 <= maxPrimCh; i2++) {
                        WiFiRx6620.this.mPrimChAdapter.add(String.valueOf(i2));
                    }
                    WiFiRx6620.this.mSpBwAdvPrimCh.setAdapter(WiFiRx6620.this.mPrimChAdapter);
                    WiFiRx6620 wiFiRx6620 = WiFiRx6620.this;
                    wiFiRx6620.onAdvancedSelectChanged(wiFiRx6620.mChannelBandwidth, WiFiRx6620.this.mDataBandwidth, WiFiRx6620.this.mPrimarySetting);
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
                if (position != WiFiRx6620.this.mDataBandwidth) {
                    WiFiRx6620.this.mDataBandwidth = position;
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
                if (position != WiFiRx6620.this.mPrimarySetting) {
                    WiFiRx6620.this.mPrimarySetting = position;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void onAdvancedSelectChanged(int cbw, int dbw, int primCh) {
        updateChannels();
    }

    /* access modifiers changed from: private */
    public void onAdvancedBandwidthSelected() {
        findViewById(R.id.wifi_bandwidth_advanced_ll).setVisibility(0);
        updateChannels();
    }

    private void updateChannelByAdvancedSetting(int cbw, int dbw, int primCh) {
        Elog.i(TAG, "updateChannelByAdvancedSetting cbw:" + cbw);
        if (cbw == 1) {
            this.mChannelAdapter.clear();
            this.mChannel.addSupported2dot4gChannels(this.mChannelAdapter, false);
            this.mChannel.removeBw40mUnsupported2dot4GChannels(this.mChannelAdapter);
            this.mChannel.insertBw40MChannels(this.mChannelAdapter);
        } else if (cbw == 2) {
            this.mChannelAdapter.clear();
            this.mChannel.insertBw80MChannels(this.mChannelAdapter);
        }
    }

    public void onClick(View arg0) {
        if (arg0.getId() == this.mBtnGo.getId()) {
            onClickBtnRxGo();
        } else if (arg0.getId() == this.mBtnStop.getId()) {
            onClickBtnRxStop();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mHandler.removeMessages(2);
        super.onDestroy();
    }

    private void onClickBtnRxGo() {
        long antennaIdx;
        setViewEnabled(false);
        EMWifi.getPacketRxStatus(this.mInitData, 3);
        Elog.i(TAG, "before rx test: rx ok = " + String.valueOf(this.mInitData[0]) + " fcs error = " + String.valueOf(this.mInitData[1]));
        EMWifi.setATParam(9, 0);
        int i = this.mBandwidthIndex;
        if (4 == i) {
            Elog.i(TAG, "mChannelBandwidth:" + this.mChannelBandwidth + " mDataBandwidth:" + this.mDataBandwidth + " mPrimarySetting:" + this.mPrimarySetting);
            EMWifi.setATParam(71, (long) this.mChannelBandwidth);
            EMWifi.setATParam(72, (long) this.mDataBandwidth);
            EMWifi.setATParam(73, (long) this.mPrimarySetting);
        } else {
            EMWifi.setATParam(15, (long) i);
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
        this.mChannelSpinner.setEnabled(state);
        this.mBandwidthSpinner.setEnabled(state);
        this.mSpBwAdvCbw.setEnabled(state);
        this.mSpBwAdvDbw.setEnabled(state);
        this.mSpBwAdvPrimCh.setEnabled(state);
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }
}
