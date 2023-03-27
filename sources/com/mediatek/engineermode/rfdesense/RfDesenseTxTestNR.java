package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestNR extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String DEFAULT_ANT_MODE = "0";
    public static final String DEFAULT_ANT_STATUS = "0";
    public static final int DEFAULT_BAND_NR = 0;
    public static final int DEFAULT_BAND_WIDTH = 7;
    public static final int DEFAULT_MCS = 0;
    public static final String DEFAULT_NR_FREQ = "1950000";
    public static final String DEFAULT_POWER = "23";
    public static final int DEFAULT_SCS_CONFIG = 0;
    public static final String DEFAULT_TDD_SLOT_CONFIG = "1";
    public static final int DEFAULT_TX_MODE = 0;
    public static final String DEFAULT_VRB_LENGTH = "216";
    public static final String DEFAULT_VRB_START = "0";
    public static final String KEY_NR_ANT_MODE = "ant_mode_nr";
    public static final String KEY_NR_ANT_STATUS_RX = "ant_status_nr_rx";
    public static final String KEY_NR_ANT_STATUS_TX = "ant_status_nr_tx";
    public static final String KEY_NR_BAND = "band_nr";
    public static final String KEY_NR_BAND_WIDTH = "bandwidth_nr";
    public static final String KEY_NR_FREQ = "freq_nr";
    public static final String KEY_NR_MCS = "mcs_nr";
    public static final String KEY_NR_POWER = "power_nr";
    public static final String KEY_NR_SCS = "scs_nr";
    public static final String KEY_NR_VRB_LENGTH = "vrb_length_nr";
    public static final String KEY_NR_VRB_START = "vrb_start_nr";
    public static final String KEY_TDD_SLOT_CONFIG = "tdd_slot_config";
    public static final String KEY_TX_MODE = "tx_mode";
    private static final int POWER_MAX_PUSCH = 23;
    private static final int POWER_MAX_TONE = 26;
    private static final int POWER_MIN = -50;
    public static final String TAG = "RfDesense/TxTestNr";
    private static final int TDD_SLOT_CONFIG_MAX = 44;
    private static final int TDD_SLOT_CONFIG_MIN = 1;
    private static final int VRB_LENGTH_MAX = 273;
    private static final int VRB_LENGTH_MIN = 0;
    private static final int VRB_START_MAX = 272;
    private static final int VRB_START_MIN = 0;
    public static final int[] mBandMapping = {1, 3, 7, 8, 20, 28, 38, 41, 77, 78, 79};
    public static final int[] mBandWidthMapping = {5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 55000, 60000, 65000, 70000, 75000, 80000, 85000, 90000, 95000, 100000};
    private static int mPowerMax = 23;
    private String atcmd = "";
    private CheckBox mAntMode;
    private EditText mAntStatusRx;
    private EditText mAntStatusTx;
    private Spinner mBand;
    private Spinner mBandWidth;
    private Button mButtonSet;
    /* access modifiers changed from: private */
    public int mCurrentBandIndex = 1;
    /* access modifiers changed from: private */
    public EditText mFreq;
    private String[] mFreqRangeArray;
    private Spinner mMcs;
    private EditText mPower;
    private TextView mPowerView;
    private RadioButton mPuschTX;
    private Spinner mScsConfig;
    private EditText mTddSlotConfig;
    private RadioButton mToneTX;
    private EditText mVrbLength;
    private EditText mVrbStart;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test_nr);
        this.mToneTX = (RadioButton) findViewById(R.id.tone_radio_button);
        this.mPuschTX = (RadioButton) findViewById(R.id.pusch_radio_button);
        this.mBand = (Spinner) findViewById(R.id.band_spinner);
        this.mBandWidth = (Spinner) findViewById(R.id.bandwidth_spinner);
        this.mFreq = (EditText) findViewById(R.id.freq_editor);
        this.mScsConfig = (Spinner) findViewById(R.id.scs_config_spinner);
        this.mTddSlotConfig = (EditText) findViewById(R.id.tddSlotConfig_editor);
        this.mVrbStart = (EditText) findViewById(R.id.vrb_start_editor);
        this.mVrbLength = (EditText) findViewById(R.id.vrb_length_editor);
        this.mMcs = (Spinner) findViewById(R.id.mcs_spinner);
        this.mPower = (EditText) findViewById(R.id.power_editor);
        this.mPowerView = (TextView) findViewById(R.id.power_view);
        this.mAntMode = (CheckBox) findViewById(R.id.rf_ant_mode);
        this.mAntStatusTx = (EditText) findViewById(R.id.rf_ant_status_tx);
        this.mAntStatusRx = (EditText) findViewById(R.id.rf_ant_status_rx);
        this.mButtonSet = (Button) findViewById(R.id.button_set);
        this.mToneTX.setOnCheckedChangeListener(this);
        this.mPuschTX.setOnCheckedChangeListener(this);
        this.mButtonSet.setOnClickListener(this);
        new ArrayAdapter(this, 17367048);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_nr_freq_range, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mBand.setAdapter(adapter);
        this.mBandWidth.setAdapter(ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_nr_bandwidth, 17367048));
        this.mMcs.setAdapter(ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_nr_mcs, 17367048));
        this.mScsConfig.setAdapter(ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_nr_scs, 17367048));
        this.mFreqRangeArray = getResources().getStringArray(R.array.rf_desense_tx_test_nr_freq_range);
        this.mBand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (arg2 != RfDesenseTxTestNR.this.mCurrentBandIndex) {
                    int unused = RfDesenseTxTestNR.this.mCurrentBandIndex = arg2;
                    RfDesenseTxTestNR.this.mFreq.setText(String.valueOf(RfDesenseTxTestNR.this.getDefaultFreq()));
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        restoreState();
        onTxModeChange();
        this.mFreq.setText(String.valueOf(getDefaultFreq()));
    }

    /* access modifiers changed from: private */
    public Long getDefaultFreq() {
        String[] range = this.mFreqRangeArray[this.mBand.getSelectedItemPosition()].split(",");
        try {
            return Long.valueOf((Long.valueOf(Long.parseLong(range[1])).longValue() + Long.valueOf(Long.parseLong(range[2])).longValue()) / 2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Check the array resource");
        }
    }

    private void onTxModeChange() {
        if (this.mToneTX.isChecked()) {
            this.mBandWidth.setEnabled(false);
            this.mScsConfig.setEnabled(false);
            this.mMcs.setEnabled(false);
            this.mVrbStart.setEnabled(false);
            this.mVrbLength.setEnabled(false);
            this.mTddSlotConfig.setEnabled(false);
            mPowerMax = 26;
            this.mPowerView.setText(R.string.rf_desense_nr_power_tone);
            return;
        }
        this.mBandWidth.setEnabled(true);
        this.mScsConfig.setEnabled(true);
        this.mMcs.setEnabled(true);
        this.mVrbStart.setEnabled(true);
        this.mVrbLength.setEnabled(true);
        this.mTddSlotConfig.setEnabled(true);
        mPowerMax = 23;
        this.mPowerView.setText(R.string.rf_desense_nr_power_pusch);
    }

    public void onCheckedChanged(CompoundButton v, boolean checked) {
        switch (v.getId()) {
            case R.id.pusch_radio_button:
            case R.id.tone_radio_button:
                onTxModeChange();
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_set:
                if (checkValues()) {
                    int txMode = this.mToneTX.isChecked() ^ 1;
                    int band = mBandMapping[this.mBand.getSelectedItemPosition()];
                    int bandWidth = mBandWidthMapping[this.mBandWidth.getSelectedItemPosition()];
                    String freq = this.mFreq.getText().toString();
                    int ScsConfig = this.mScsConfig.getSelectedItemPosition();
                    String vrbStart = this.mVrbStart.getText().toString();
                    String vrbLength = this.mVrbLength.getText().toString();
                    int mcs = this.mMcs.getSelectedItemPosition();
                    String power = this.mPower.getText().toString();
                    String tddSlotConfig = this.mTddSlotConfig.getText().toString();
                    if (txMode == 0) {
                        this.atcmd = "AT+EGMC=1,\"NrForcedTx\",2,";
                        this.atcmd += band + "," + freq + "," + power;
                    } else {
                        this.atcmd = "AT+EGMC=1,\"NrForcedTx\",1,";
                        this.atcmd += band + "," + bandWidth + "," + freq + "," + vrbStart + "," + vrbLength + "," + mcs + "," + ScsConfig + "," + power + "," + tddSlotConfig;
                    }
                    saveState(this.atcmd);
                    EmUtils.showToast("Set param suecceed");
                    Elog.d(TAG, "command = " + this.atcmd);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean checkValues() {
        try {
            int value = Integer.parseInt(this.mVrbStart.getText().toString());
            if (value >= 0) {
                if (value <= VRB_START_MAX) {
                    int value2 = Integer.parseInt(this.mVrbLength.getText().toString());
                    if (value2 >= 0) {
                        if (value2 <= VRB_LENGTH_MAX) {
                            int value3 = Integer.parseInt(this.mPower.getText().toString());
                            if (value3 >= POWER_MIN) {
                                if (value3 <= mPowerMax) {
                                    int value4 = Integer.parseInt(this.mTddSlotConfig.getText().toString());
                                    if (value4 >= 1) {
                                        if (value4 <= 44) {
                                            return true;
                                        }
                                    }
                                    EmUtils.showToast("Invalid tdd Slot Config.");
                                    return false;
                                }
                            }
                            EmUtils.showToast("Invalid Power Level.");
                            return false;
                        }
                    }
                    EmUtils.showToast("Invalid VRB Length.");
                    return false;
                }
            }
            EmUtils.showToast("Invalid VRB Start.");
            return false;
        } catch (NumberFormatException e) {
            EmUtils.showToast("Invalid Value.");
            return false;
        }
    }

    private void saveState(String command) {
        SharedPreferences.Editor editor = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        editor.putInt(KEY_TX_MODE, this.mToneTX.isChecked() ^ true ? 1 : 0);
        editor.putString(RfDesenseTxTest.KEY_NR_ATCMD, command);
        editor.putInt(KEY_NR_BAND, this.mBand.getSelectedItemPosition());
        editor.putInt(KEY_NR_BAND_WIDTH, this.mBandWidth.getSelectedItemPosition());
        editor.putString(KEY_NR_FREQ, this.mFreq.getText().toString());
        editor.putString(KEY_NR_VRB_START, this.mVrbStart.getText().toString());
        editor.putString(KEY_NR_VRB_LENGTH, this.mVrbLength.getText().toString());
        editor.putInt(KEY_NR_MCS, this.mMcs.getSelectedItemPosition());
        editor.putInt(KEY_NR_SCS, this.mScsConfig.getSelectedItemPosition());
        editor.putString(KEY_NR_POWER, this.mPower.getText().toString());
        editor.putString(KEY_TDD_SLOT_CONFIG, this.mTddSlotConfig.getText().toString());
        editor.putString(KEY_NR_ANT_MODE, this.mAntMode.isChecked() ? "1" : "0");
        editor.putString(KEY_NR_ANT_STATUS_TX, this.mAntStatusTx.getText().toString());
        editor.putString(KEY_NR_ANT_STATUS_RX, this.mAntStatusRx.getText().toString());
        String ant_str = "AT+EGMC=1,\"NrForceTxRx\",1," + this.mAntStatusTx.getText().toString() + "," + this.mAntStatusRx.getText().toString() + ",0";
        if (this.mAntMode.isChecked()) {
            editor.putString(RfDesenseTxTest.KEY_NR_ATCMD_ANT_SWITCH, ant_str);
        } else {
            editor.putString(RfDesenseTxTest.KEY_NR_ATCMD_ANT_SWITCH, "AT+EGMC=1,\"NrForceTxRx\",0,,,0");
        }
        editor.apply();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        int checked = pref.getInt(KEY_TX_MODE, 0);
        boolean z = true;
        this.mToneTX.setChecked(checked == 0);
        RadioButton radioButton = this.mPuschTX;
        if (checked != 1) {
            z = false;
        }
        radioButton.setChecked(z);
        this.mCurrentBandIndex = pref.getInt(KEY_NR_BAND, 0);
        this.mBand.setSelection(pref.getInt(KEY_NR_BAND, 0));
        this.mBandWidth.setSelection(pref.getInt(KEY_NR_BAND_WIDTH, 7));
        this.mFreq.setText(pref.getString(KEY_NR_FREQ, DEFAULT_NR_FREQ));
        this.mVrbStart.setText(pref.getString(KEY_NR_VRB_START, "0"));
        this.mVrbLength.setText(pref.getString(KEY_NR_VRB_LENGTH, DEFAULT_VRB_LENGTH));
        this.mMcs.setSelection(pref.getInt(KEY_NR_MCS, 0));
        this.mScsConfig.setSelection(pref.getInt(KEY_NR_SCS, 0));
        this.mPower.setText(pref.getString(KEY_NR_POWER, "23"));
        this.mTddSlotConfig.setText(pref.getString(KEY_TDD_SLOT_CONFIG, "1"));
        this.mAntMode.setChecked(pref.getString(KEY_NR_ANT_MODE, "0").equals("1"));
        this.mAntStatusTx.setText(pref.getString(KEY_NR_ANT_STATUS_TX, "0"));
        this.mAntStatusRx.setText(pref.getString(KEY_NR_ANT_STATUS_RX, "0"));
    }
}
