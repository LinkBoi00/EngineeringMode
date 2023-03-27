package com.mediatek.engineermode.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.InputDeviceCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.wifi.WifiCapability;
import com.mediatek.engineermode.wifi.WifiFormatConfig;
import java.util.List;

public class WiFiRxFormat extends WiFiTestActivity implements View.OnClickListener {
    private static final int DEFAULT_PRIM_CH = 0;
    private static final int DEFAULT_STA_ID = 0;
    private static final int HANDLER_EVENT_GO = 1;
    private static final int HANDLER_EVENT_STOP = 2;
    private static final int HANDLER_EVENT_UPDATE = 3;
    private static final int ONE_SECOND = 1000;
    private static final String TAG = "WifiRX";
    /* access modifiers changed from: private */
    public int mBandValue = 0;
    private Button mBtnGo;
    private Button mBtnStop;
    private boolean mCap11ax = false;
    private boolean mCap2by2 = false;
    private boolean mCapAntSwap = false;
    private boolean mCapBw160c = false;
    private boolean mCapBw160nc = false;
    private boolean mCapCh6G = false;
    private CheckBox mCbWf1;
    private CheckBox mCbWf2;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mCbwAdapter = null;
    private List<ChannelDataFormat> mChList;
    private ArrayAdapter<String> mChannelAdapter = null;
    private boolean mDbdcMode = false;
    private ArrayAdapter<String> mDbwAdapter = null;
    private EditText mEtStaId;
    private WifiFormatConfig mParamConfig;
    /* access modifiers changed from: private */
    public ArrayAdapter<WifiFormatConfig.Preamble> mPreambleAdapter = null;
    private RadioGroup mRgAnt;
    /* access modifiers changed from: private */
    public RxHandler mRxHandler;
    private RxParam mRxParam;
    private HandlerThread mRxThread;
    private Spinner mSpCbw;
    private Spinner mSpCh;
    private Spinner mSpDbw;
    private Spinner mSpPreamble;
    private Spinner mSpPrimCh;
    /* access modifiers changed from: private */
    public TextView mTvFcsErr;
    /* access modifiers changed from: private */
    public TextView mTvPer;
    /* access modifiers changed from: private */
    public TextView mTvRssi0;
    /* access modifiers changed from: private */
    public TextView mTvRssi1;
    /* access modifiers changed from: private */
    public TextView mTvRxOk;

    private class RxParam {
        int mAntIndex;
        int mCbwIndex;
        int mChID;
        int mChannelType;
        int mDbwIndex;
        int mPreambleIndex;
        int mPrimCh;
        int mStaId;
        int mWfSelect;

        private RxParam() {
        }
    }

    private RxParam getRxParam() {
        RxParam param = new RxParam();
        ChannelDataFormat channel = this.mChList.get(this.mSpCh.getSelectedItemPosition());
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
        param.mStaId = 0;
        try {
            param.mStaId = Integer.parseInt(this.mEtStaId.getText().toString());
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        WifiFormatConfig.Preamble preamble = (WifiFormatConfig.Preamble) this.mSpPreamble.getSelectedItem();
        Elog.i(TAG, "preamble:" + this.mSpPreamble.getSelectedItem() + preamble + " " + preamble.getIndex());
        param.mPreambleIndex = preamble.getIndex();
        if (this.mRgAnt.getCheckedRadioButtonId() == R.id.wifi_format_rx_ant_main_rb) {
            i = 0;
        }
        param.mAntIndex = i;
        return param;
    }

    private class RxHandler extends Handler {
        public RxHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    WiFiRxFormat.this.startRx();
                    WiFiRxFormat.this.mRxHandler.sendEmptyMessageDelayed(3, 1000);
                    return;
                case 2:
                    WiFiRxFormat.this.stopRx();
                    WiFiRxFormat.this.mRxHandler.removeMessages(3);
                    return;
                case 3:
                    WiFiRxFormat.this.updateRxResult();
                    WiFiRxFormat.this.mRxHandler.sendEmptyMessageDelayed(3, 1000);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateRxResult() {
        int[] fcsErr = {0};
        int[] rxOk = {0};
        int[] rssi0 = {0};
        int[] rssi1 = {0};
        Elog.i(TAG, "start hqaGetRXStatisticsAllExt");
        if (EMWifi.hqaGetRXStatisticsAllExt(this.mBandValue, fcsErr, rxOk, rssi0, rssi1) == 0) {
            final int[] iArr = fcsErr;
            final int[] iArr2 = rxOk;
            final int[] iArr3 = rssi0;
            final int[] iArr4 = rssi1;
            runOnUiThread(new Runnable() {
                public void run() {
                    Elog.i(WiFiRxFormat.TAG, "hqaGetRXStatisticsAllExt:" + WiFiRxFormat.this.mBandValue + " " + iArr[0] + " " + iArr2[0] + " " + iArr3[0] + " " + iArr4[0]);
                    WiFiRxFormat.this.mTvFcsErr.setText(String.valueOf(iArr[0]));
                    WiFiRxFormat.this.mTvRxOk.setText(String.valueOf(iArr2[0]));
                    TextView access$800 = WiFiRxFormat.this.mTvRssi0;
                    int[] iArr = iArr3;
                    access$800.setText(String.valueOf(iArr[0] >= 128 ? iArr[0] + InputDeviceCompat.SOURCE_ANY : iArr[0]));
                    TextView access$900 = WiFiRxFormat.this.mTvRssi1;
                    int[] iArr2 = iArr4;
                    access$900.setText(String.valueOf(iArr2[0] >= 128 ? iArr2[0] + InputDeviceCompat.SOURCE_ANY : iArr2[0]));
                    int value = iArr[0] + iArr2[0];
                    if (value != 0) {
                        WiFiRxFormat.this.mTvPer.setText(String.valueOf((iArr[0] * 100) / value));
                    } else {
                        WiFiRxFormat.this.mTvPer.setText("0");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void startRx() {
        EMWifi.hqaSetTxPath(this.mRxParam.mWfSelect, this.mBandValue);
        EMWifi.hqaSetRxPath(this.mRxParam.mWfSelect, this.mBandValue);
        Elog.i(TAG, "hqaSetTxPath:" + this.mRxParam.mWfSelect + " " + this.mBandValue);
        Elog.i(TAG, "hqaSetRxPath:" + this.mRxParam.mWfSelect + " " + this.mBandValue);
        if (this.mCapAntSwap) {
            EMWifi.hqaSetAntSwap(this.mRxParam.mAntIndex);
            Elog.i(TAG, "hqaSetAntSwap:" + this.mRxParam.mAntIndex);
        }
        EMWifi.hqaDbdcSetChannel(this.mBandValue, this.mRxParam.mChID, 0, this.mRxParam.mCbwIndex, this.mRxParam.mDbwIndex, this.mRxParam.mPrimCh, this.mRxParam.mChannelType);
        Elog.i(TAG, "hqaDbdcSetChannel:" + this.mBandValue + " " + this.mRxParam.mChID + " " + 0 + " " + this.mRxParam.mCbwIndex + " " + this.mRxParam.mDbwIndex + " " + this.mRxParam.mPrimCh + " " + this.mRxParam.mChannelType);
        EMWifi.hqaDbdcStartRXExt(this.mBandValue, this.mRxParam.mWfSelect, this.mRxParam.mStaId, this.mRxParam.mPreambleIndex);
        StringBuilder sb = new StringBuilder();
        sb.append("hqaDbdcStartRXExt:");
        sb.append(this.mBandValue);
        sb.append(" ");
        sb.append(this.mRxParam.mWfSelect);
        sb.append(" ");
        sb.append(this.mRxParam.mStaId);
        sb.append(" ");
        sb.append(this.mRxParam.mPreambleIndex);
        Elog.i(TAG, sb.toString());
    }

    /* access modifiers changed from: private */
    public void stopRx() {
        EMWifi.hqaDbdcStopRX(this.mBandValue);
        Elog.i(TAG, "hqaDbdcStopRX:" + this.mBandValue);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        String mode = it.getStringExtra("mode");
        boolean z = true;
        if (mode != null) {
            this.mDbdcMode = true;
            if (mode.equals("RX Band1")) {
                this.mBandValue = 1;
            }
            Elog.i(TAG, "wifi rx in dbdc mode");
        }
        EMWifi.hqaSetBandMode(this.mDbdcMode ? 2 : 1);
        this.mCapAntSwap = it.getBooleanExtra("key_ant_swap", false);
        this.mCap11ax = it.getBooleanExtra("key_11ax", false);
        this.mCap2by2 = it.getBooleanExtra("key_2by2", false);
        this.mCapBw160c = it.getBooleanExtra("key_bw_160c", false);
        this.mCapBw160nc = it.getBooleanExtra("key_bw_160nc", false);
        if (WifiCapability.CapChBand.CAP_CH_BAND_6G.ordinal() != it.getIntExtra("key_ch_band", WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT.ordinal())) {
            z = false;
        }
        this.mCapCh6G = z;
        this.mParamConfig = new WifiFormatConfig(this.mCapCh6G);
        setContentView(R.layout.wifi_rx_format);
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mRxThread = handlerThread;
        handlerThread.start();
        this.mRxHandler = new RxHandler(this.mRxThread.getLooper());
        initComponent();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mRxHandler.removeMessages(3);
        this.mRxThread.quit();
        super.onDestroy();
    }

    private void initComponent() {
        this.mCbWf1 = (CheckBox) findViewById(R.id.wifi_format_rx_wf0);
        this.mCbWf2 = (CheckBox) findViewById(R.id.wifi_format_rx_wf1);
        if (!this.mCap2by2) {
            findViewById(R.id.wifi_format_rx_wf_ll).setVisibility(8);
        }
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.wifi_format_rx_ant_swap_rg);
        this.mRgAnt = radioGroup;
        radioGroup.check(R.id.wifi_format_rx_ant_main_rb);
        if (!this.mCapAntSwap) {
            findViewById(R.id.wifi_format_rx_ant_swap_ll).setVisibility(8);
        }
        this.mSpCh = (Spinner) findViewById(R.id.wifi_format_rx0_channel_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mChannelAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mSpCh.setAdapter(this.mChannelAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048);
        this.mCbwAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) findViewById(R.id.wifi_format_rx_cbw_spinner);
        this.mSpCbw = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Elog.i(WiFiRxFormat.TAG, "mSpCbw onItemSelected:" + position);
                WiFiRxFormat.this.updateParamWithCbw(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpCbw.setAdapter(this.mCbwAdapter);
        this.mSpDbw = (Spinner) findViewById(R.id.wifi_format_rx_dbw_spinner);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, 17367048);
        this.mDbwAdapter = arrayAdapter3;
        arrayAdapter3.setDropDownViewResource(17367049);
        this.mSpDbw.setAdapter(this.mDbwAdapter);
        this.mSpPrimCh = (Spinner) findViewById(R.id.wifi_format_rx_prim_ch_spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.wifi_format_rx_preamble_spinner);
        this.mSpPreamble = spinner2;
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WiFiRxFormat.this.mCbwAdapter.clear();
                WiFiRxFormat.this.mCbwAdapter.addAll(((WifiFormatConfig.Preamble) WiFiRxFormat.this.mPreambleAdapter.getItem(position)).getBwList());
                WiFiRxFormat.this.mCbwAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<WifiFormatConfig.Preamble> arrayAdapter4 = new ArrayAdapter<>(this, 17367048);
        this.mPreambleAdapter = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(17367049);
        this.mPreambleAdapter.addAll(this.mParamConfig.getPreambles(false, false, this.mCap11ax, this.mCapBw160c, this.mCapBw160nc));
        this.mSpPreamble.setAdapter(this.mPreambleAdapter);
        EditText editText = (EditText) findViewById(R.id.wifi_rx_ru_sta_id_et);
        this.mEtStaId = editText;
        editText.setText(String.valueOf(0));
        if (!this.mCap11ax) {
            findViewById(R.id.wifi_format_rx_11ax_ll).setVisibility(8);
        }
        this.mTvFcsErr = (TextView) findViewById(R.id.wifi_format_rx_fcs_err_tv);
        this.mTvRxOk = (TextView) findViewById(R.id.wifi_format_rx_ok_tv);
        this.mTvPer = (TextView) findViewById(R.id.wifi_format_rx_per_tv);
        this.mTvRssi0 = (TextView) findViewById(R.id.wifi_format_rx0_rssi_tv);
        this.mTvRssi1 = (TextView) findViewById(R.id.wifi_format_rx1_rssi_tv);
        Button button = (Button) findViewById(R.id.wifi_format_rx_go_btn);
        this.mBtnGo = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.wifi_format_rx_stop_btn);
        this.mBtnStop = button2;
        button2.setOnClickListener(this);
        if (this.mDbdcMode) {
            findViewById(R.id.wifi_format_rx_wf_ll).setVisibility(8);
        }
        setUiEnable(true);
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnGo)) {
            if (this.mCbWf1.isChecked() || this.mCbWf2.isChecked()) {
                this.mRxParam = getRxParam();
                RxHandler rxHandler = this.mRxHandler;
                if (rxHandler != null) {
                    rxHandler.sendEmptyMessage(1);
                    setUiEnable(false);
                    return;
                }
                return;
            }
            Toast.makeText(this, R.string.wifi_dialog_no_path_select, 1).show();
        } else if (v.equals(this.mBtnStop)) {
            this.mRxHandler.sendEmptyMessage(2);
            setUiEnable(true);
        }
    }

    private void setUiEnable(boolean enable) {
        this.mBtnGo.setEnabled(enable);
        this.mBtnStop.setEnabled(!enable);
        this.mSpCh.setEnabled(enable);
        this.mSpCbw.setEnabled(enable);
        this.mSpDbw.setEnabled(enable);
        this.mSpPrimCh.setEnabled(enable);
        this.mSpPreamble.setEnabled(enable);
        this.mCbWf1.setEnabled(enable);
        this.mCbWf2.setEnabled(enable);
        this.mEtStaId.setEnabled(enable);
        int count = this.mRgAnt.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgAnt.getChildAt(k).setEnabled(enable);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInTestProcess() {
        return !this.mBtnGo.isEnabled();
    }

    /* access modifiers changed from: private */
    public void updateParamWithCbw(int position) {
        this.mChList = this.mParamConfig.getChannel(this.mCbwAdapter.getItem(position));
        this.mChannelAdapter.clear();
        for (ChannelDataFormat chData : this.mChList) {
            this.mChannelAdapter.add(chData.getName());
        }
        this.mChannelAdapter.notifyDataSetChanged();
        this.mDbwAdapter.clear();
        for (int k = 0; k <= position; k++) {
            this.mDbwAdapter.add(this.mCbwAdapter.getItem(k));
        }
        this.mDbwAdapter.notifyDataSetChanged();
    }
}
