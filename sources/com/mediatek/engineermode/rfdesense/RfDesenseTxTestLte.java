package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestLte extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String DEFAULT_ANT_MODE = "0";
    public static final String DEFAULT_ANT_STATUS = "0";
    public static final int DEFAULT_BAND_FDD = 2;
    public static final int DEFAULT_BAND_TDD = 5;
    public static final int DEFAULT_BAND_WIDTH = 3;
    public static final String DEFAULT_FDD_FREQ = "17475";
    public static final int DEFAULT_MCS = 0;
    public static final int DEFAULT_MODULATION_MODE = 1;
    public static final String DEFAULT_POWER = "23";
    public static final int DEFAULT_TDD_CONFIG = 0;
    public static final String DEFAULT_TDD_FREQ = "25950";
    public static final int DEFAULT_TDD_SPECIAL = 0;
    public static final String DEFAULT_VRB_LENGTH = "1";
    public static final String DEFAULT_VRB_START = "0";
    private static final int DUPLEX_FDD = 1;
    private static final int DUPLEX_TDD = 0;
    private static final int FDD_BAND_MAX = 31;
    private static final int FDD_BAND_MIN = 1;
    public static final String KEY_FDD_ANT_MODE = "ant_mode_fdd";
    public static final String KEY_FDD_ANT_STATUS = "ant_status_fdd";
    public static final String KEY_FDD_ANT_STATUS_RX = "ant_status_fdd_rx";
    public static final String KEY_FDD_BAND = "band_fdd";
    public static final String KEY_FDD_BAND_WIDTH = "bandwidth_fdd";
    public static final String KEY_FDD_FREQ = "freq_fdd";
    public static final String KEY_FDD_MCS = "mcs_fdd";
    public static final String KEY_FDD_POWER = "power_fdd";
    public static final String KEY_FDD_VRB_LENGTH = "vrb_length_fdd";
    public static final String KEY_FDD_VRB_START = "vrb_start_fdd";
    public static final String KEY_MODULATION_MODE = "modulation_mode";
    public static final String KEY_TDD_ANT_MODE = "ant_mode_tdd";
    public static final String KEY_TDD_ANT_STATUS = "ant_status_tdd";
    public static final String KEY_TDD_ANT_STATUS_RX = "ant_status_tdd_rx";
    public static final String KEY_TDD_BAND = "band_tdd";
    public static final String KEY_TDD_BAND_WIDTH = "bandwidth_tdd";
    public static final String KEY_TDD_CONFIG = "tdd_config";
    public static final String KEY_TDD_FREQ = "freq_tdd";
    public static final String KEY_TDD_MCS = "mcs_tdd";
    public static final String KEY_TDD_POWER = "power_tdd";
    public static final String KEY_TDD_SPECIAL = "tdd_special";
    public static final String KEY_TDD_VRB_LENGTH = "vrb_length_tdd";
    public static final String KEY_TDD_VRB_START = "vrb_start_tdd";
    private static final int POWER_MAX = 27;
    private static final int POWER_MIN = -50;
    private static final String TAG = "RfDesense/TxTestLte";
    private static final int TDD_BAND_MAX = 44;
    private static final int TDD_BAND_MIN = 33;
    private static final int TDD_CONFIG_MAX = 6;
    private static final int TDD_SPECIAL_MAX = 9;
    private static final int VRB_LENGTH_MAX = 100;
    private static final int VRB_LENGTH_MIN = 1;
    private static final int VRB_START_MAX = 99;
    private static final int VRB_START_MIN = 0;
    private static int mModemType;
    protected CheckBox mAntMode;
    protected EditText mAntStatus;
    protected EditText mAntStatusRx;
    private Spinner mBand;
    private Spinner mBandWidth;
    private Button mButtonSet;
    /* access modifiers changed from: private */
    public int mCurrentBand = 0;
    private RadioButton mFdd;
    private EditText mFreq;
    private TextView mFreqRange;
    String[] mFreqRangeArray;
    private Spinner mMcs;
    private TextView mModulationMode;
    private RadioButton mModulationSignal;
    private EditText mPower;
    private RadioButton mSingleTone;
    private RadioButton mTdd;
    private Spinner mTddConfig;
    private Spinner mTddSpecial;
    private Toast mToast = null;
    private EditText mVrbLength;
    private EditText mVrbStart;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test_lte);
        mModemType = getIntent().getIntExtra("mModemType", 1);
        this.mModulationMode = (TextView) findViewById(R.id.rf_desense_modulation_mode);
        this.mSingleTone = (RadioButton) findViewById(R.id.single_radio_button);
        this.mModulationSignal = (RadioButton) findViewById(R.id.modulatio_radio_button);
        if (!FeatureSupport.is93ModemAndAbove()) {
            this.mModulationMode.setVisibility(8);
            this.mSingleTone.setVisibility(8);
            this.mModulationSignal.setVisibility(8);
        }
        this.mFdd = (RadioButton) findViewById(R.id.fdd_radio_button);
        this.mTdd = (RadioButton) findViewById(R.id.tdd_radio_button);
        this.mFdd.setChecked(false);
        this.mTdd.setChecked(false);
        this.mBand = (Spinner) findViewById(R.id.band_spinner);
        this.mBandWidth = (Spinner) findViewById(R.id.bandwidth_spinner);
        this.mFreq = (EditText) findViewById(R.id.freq_editor);
        this.mTddConfig = (Spinner) findViewById(R.id.tdd_config_spinner);
        this.mTddSpecial = (Spinner) findViewById(R.id.tdd_special_spinner);
        this.mVrbStart = (EditText) findViewById(R.id.vrb_start_editor);
        this.mVrbLength = (EditText) findViewById(R.id.vrb_length_editor);
        this.mMcs = (Spinner) findViewById(R.id.mcs_spinner);
        this.mPower = (EditText) findViewById(R.id.power_editor);
        this.mFreqRange = (TextView) findViewById(R.id.freq);
        this.mAntMode = (CheckBox) findViewById(R.id.rf_ant_mode);
        this.mAntStatus = (EditText) findViewById(R.id.rf_ant_status);
        this.mAntStatusRx = (EditText) findViewById(R.id.rf_ant_status_rx);
        if (!FeatureSupport.is97ModemAndAbove()) {
            findViewById(R.id.rf_ant_status_tx_tv).setVisibility(8);
            findViewById(R.id.rf_ant_status_rx_ll).setVisibility(8);
        }
        this.mButtonSet = (Button) findViewById(R.id.button_set);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mBand.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_lte_bandwidth, 17367048);
        adapter2.setDropDownViewResource(17367049);
        this.mBandWidth.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_lte_mcs, 17367048);
        adapter3.setDropDownViewResource(17367049);
        this.mMcs.setAdapter(adapter3);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        this.mTddConfig.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, 17367048);
        arrayAdapter2.setDropDownViewResource(17367049);
        this.mTddSpecial.setAdapter(arrayAdapter2);
        this.mFdd.setOnCheckedChangeListener(this);
        this.mTdd.setOnCheckedChangeListener(this);
        this.mButtonSet.setOnClickListener(this);
        this.mBand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (arg2 != RfDesenseTxTestLte.this.mCurrentBand) {
                    int unused = RfDesenseTxTestLte.this.mCurrentBand = arg2;
                    RfDesenseTxTestLte.this.setDefaultValue();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mFreqRangeArray = getResources().getStringArray(R.array.rf_desense_tx_test_lte_freq_range);
        if (mModemType == 2) {
            this.mTdd.setChecked(true);
        } else {
            this.mFdd.setChecked(true);
        }
        restoreState();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCheckedChanged(CompoundButton v, boolean checked) {
        switch (v.getId()) {
            case R.id.fdd_radio_button:
            case R.id.tdd_radio_button:
                onDuplexChange();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public int calcExtentedIndex(int SelectedItemPosition) {
        if (SelectedItemPosition == 31) {
            return 34;
        }
        if (SelectedItemPosition == 32) {
            return 38;
        }
        return 0;
    }

    public void onClick(View v) {
        String atcmd;
        switch (v.getId()) {
            case R.id.button_set:
                if (checkValues()) {
                    if (!FeatureSupport.is93ModemAndAbove() || !this.mSingleTone.isChecked()) {
                        atcmd = "AT+ERFTX=6,0,1,";
                    } else {
                        atcmd = "AT+ERFTX=6,0,2,";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(atcmd);
                    sb.append(this.mBand.getSelectedItemPosition() + (this.mTdd.isChecked() ? 33 : 1) + calcExtentedIndex(this.mBand.getSelectedItemPosition()));
                    sb.append(",");
                    sb.append(this.mBandWidth.getSelectedItemPosition());
                    sb.append(",");
                    sb.append(this.mFreq.getText().toString());
                    sb.append(",");
                    sb.append(this.mTdd.isChecked() ^ true ? 1 : 0);
                    sb.append(",");
                    sb.append(this.mTddConfig.getSelectedItemPosition());
                    sb.append(",");
                    sb.append(this.mTddSpecial.getSelectedItemPosition());
                    sb.append(",");
                    sb.append(this.mVrbStart.getText().toString());
                    sb.append(",");
                    sb.append(this.mVrbLength.getText().toString());
                    sb.append(",");
                    sb.append(this.mMcs.getSelectedItemPosition());
                    sb.append(",");
                    sb.append(this.mPower.getText().toString());
                    String command = sb.toString();
                    if (this.mAntMode.isChecked() && FeatureSupport.is93ModemAndAbove()) {
                        command = command + ",1," + this.mAntStatus.getText().toString();
                        if (FeatureSupport.is97ModemAndAbove()) {
                            command = command + "," + this.mAntStatusRx.getText().toString();
                        }
                    }
                    saveState(command);
                    showToast("Set param suecceed, " + command);
                    Elog.d(TAG, "command = " + command);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void onDuplexChange() {
        int i;
        boolean tdd = this.mTdd.isChecked();
        if (tdd) {
        }
        if (tdd) {
        }
        int specialMax = 0;
        int configMax = tdd ? 6 : 0;
        if (tdd) {
            specialMax = 9;
        }
        this.mTddConfig.setEnabled(tdd);
        this.mTddSpecial.setEnabled(tdd);
        Elog.i(TAG, "tdd = " + tdd);
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter) this.mBand.getAdapter();
        adapter.clear();
        Resources resources = getResources();
        if (tdd) {
            i = R.array.rf_desense_tx_test_lte_tdd_band_values;
        } else {
            i = R.array.rf_desense_tx_test_lte_fdd_band_values;
        }
        adapter.addAll(resources.getStringArray(i));
        adapter.notifyDataSetChanged();
        this.mBand.setSelection(tdd ? 5 : 2);
        ArrayAdapter<CharSequence> adapter2 = (ArrayAdapter) this.mTddConfig.getAdapter();
        adapter2.clear();
        for (int i2 = 0; i2 <= configMax; i2++) {
            adapter2.add(String.valueOf(i2));
        }
        adapter2.notifyDataSetChanged();
        ArrayAdapter<CharSequence> adapter3 = (ArrayAdapter) this.mTddSpecial.getAdapter();
        adapter3.clear();
        for (int i3 = 0; i3 <= specialMax; i3++) {
            adapter3.add(String.valueOf(i3));
        }
        adapter3.notifyDataSetChanged();
        setDefaultValue();
    }

    private boolean checkValues() {
        try {
            int value = Integer.parseInt(this.mVrbStart.getText().toString());
            if (value >= 0) {
                if (value <= 99) {
                    int value2 = Integer.parseInt(this.mVrbLength.getText().toString());
                    if (value2 >= 1) {
                        if (value2 <= 100) {
                            int value3 = Integer.parseInt(this.mPower.getText().toString());
                            if (value3 >= POWER_MIN) {
                                if (value3 <= 27) {
                                    return true;
                                }
                            }
                            showToast("Invalid Power Level.");
                            return false;
                        }
                    }
                    showToast("Invalid VRB Length.");
                    return false;
                }
            }
            showToast("Invalid VRB Start.");
            return false;
        } catch (NumberFormatException e) {
            showToast("Invalid Value.");
            return false;
        }
    }

    private void saveState(String command) {
        SharedPreferences.Editor editor = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        editor.putInt(KEY_MODULATION_MODE, this.mSingleTone.isChecked() ^ true ? 1 : 0);
        String str = "1";
        if (mModemType == 2) {
            editor.putString(RfDesenseTxTest.KEY_LTE_TDD_ATCMD, command);
            editor.putInt(KEY_TDD_BAND, this.mBand.getSelectedItemPosition());
            editor.putInt(KEY_TDD_BAND_WIDTH, this.mBandWidth.getSelectedItemPosition());
            editor.putString(KEY_TDD_FREQ, this.mFreq.getText().toString());
            editor.putInt(KEY_TDD_CONFIG, this.mTddConfig.getSelectedItemPosition());
            editor.putInt(KEY_TDD_SPECIAL, this.mTddSpecial.getSelectedItemPosition());
            editor.putString(KEY_TDD_VRB_START, this.mVrbStart.getText().toString());
            editor.putString(KEY_TDD_VRB_LENGTH, this.mVrbLength.getText().toString());
            editor.putInt(KEY_TDD_MCS, this.mMcs.getSelectedItemPosition());
            editor.putString(KEY_TDD_POWER, this.mPower.getText().toString());
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            editor.putString(KEY_TDD_ANT_MODE, str);
            editor.putString(KEY_TDD_ANT_STATUS, this.mAntStatus.getText().toString());
            editor.putString(KEY_TDD_ANT_STATUS_RX, this.mAntStatusRx.getText().toString());
        } else {
            editor.putString(RfDesenseTxTest.KEY_LTE_FDD_ATCMD, command);
            editor.putInt(KEY_FDD_BAND, this.mBand.getSelectedItemPosition());
            editor.putInt(KEY_FDD_BAND_WIDTH, this.mBandWidth.getSelectedItemPosition());
            editor.putString(KEY_FDD_FREQ, this.mFreq.getText().toString());
            editor.putString(KEY_FDD_VRB_START, this.mVrbStart.getText().toString());
            editor.putString(KEY_FDD_VRB_LENGTH, this.mVrbLength.getText().toString());
            editor.putInt(KEY_FDD_MCS, this.mMcs.getSelectedItemPosition());
            editor.putString(KEY_FDD_POWER, this.mPower.getText().toString());
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            editor.putString(KEY_FDD_ANT_MODE, str);
            editor.putString(KEY_FDD_ANT_STATUS, this.mAntStatus.getText().toString());
            editor.putString(KEY_FDD_ANT_STATUS_RX, this.mAntStatusRx.getText().toString());
        }
        editor.apply();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        boolean z = true;
        int checked = pref.getInt(KEY_MODULATION_MODE, 1);
        this.mSingleTone.setChecked(checked == 0);
        RadioButton radioButton = this.mModulationSignal;
        if (checked != 1) {
            z = false;
        }
        radioButton.setChecked(z);
        if (mModemType == 2) {
            this.mCurrentBand = pref.getInt(KEY_TDD_BAND, 5);
            this.mBand.setSelection(pref.getInt(KEY_TDD_BAND, 5));
            this.mBandWidth.setSelection(pref.getInt(KEY_TDD_BAND_WIDTH, 3));
            this.mFreq.setText(pref.getString(KEY_TDD_FREQ, DEFAULT_TDD_FREQ));
            this.mTddConfig.setSelection(pref.getInt(KEY_TDD_CONFIG, 0));
            this.mTddSpecial.setSelection(pref.getInt(KEY_TDD_SPECIAL, 0));
            this.mVrbStart.setText(pref.getString(KEY_TDD_VRB_START, "0"));
            this.mVrbLength.setText(pref.getString(KEY_TDD_VRB_LENGTH, "1"));
            this.mMcs.setSelection(pref.getInt(KEY_TDD_MCS, 0));
            this.mPower.setText(pref.getString(KEY_TDD_POWER, "23"));
            this.mAntMode.setChecked(pref.getString(KEY_TDD_ANT_MODE, "0").equals("1"));
            this.mAntStatus.setText(pref.getString(KEY_TDD_ANT_STATUS, "0"));
            this.mAntStatusRx.setText(pref.getString(KEY_TDD_ANT_STATUS_RX, "0"));
            return;
        }
        this.mCurrentBand = pref.getInt(KEY_FDD_BAND, 2);
        this.mBand.setSelection(pref.getInt(KEY_FDD_BAND, 2));
        this.mBandWidth.setSelection(pref.getInt(KEY_FDD_BAND_WIDTH, 3));
        this.mFreq.setText(pref.getString(KEY_FDD_FREQ, DEFAULT_FDD_FREQ));
        this.mVrbStart.setText(pref.getString(KEY_FDD_VRB_START, "0"));
        this.mVrbLength.setText(pref.getString(KEY_FDD_VRB_LENGTH, "1"));
        this.mMcs.setSelection(pref.getInt(KEY_FDD_MCS, 0));
        this.mPower.setText(pref.getString(KEY_FDD_POWER, "23"));
        this.mAntMode.setChecked(pref.getString(KEY_FDD_ANT_MODE, "0").equals("1"));
        this.mAntStatus.setText(pref.getString(KEY_FDD_ANT_STATUS, "0"));
        this.mAntStatusRx.setText(pref.getString(KEY_FDD_ANT_STATUS_RX, "0"));
    }

    /* access modifiers changed from: private */
    public void setDefaultValue() {
        this.mBandWidth.setSelection(3);
        this.mFreq.setText(String.valueOf(getDefaultFreq()));
        this.mTddConfig.setSelection(0);
        this.mTddSpecial.setSelection(0);
        this.mVrbStart.setText("0");
        this.mVrbLength.setText("1");
        this.mMcs.setSelection(0);
        this.mPower.setText("23");
        this.mAntMode.setChecked("0".equals("1"));
        this.mAntStatus.setText("0");
        this.mAntStatusRx.setText("0");
    }

    private int getDefaultFreq() {
        int index = this.mBand.getSelectedItemPosition() + (this.mTdd.isChecked() ? 33 : 1);
        if (this.mBand.getSelectedItemPosition() == 32) {
            index = 29;
        }
        String[] range = this.mFreqRangeArray[index - 1].split(",");
        try {
            return (Integer.parseInt(range[0]) + Integer.parseInt(range[1])) / 2;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Check the array resource");
        }
    }

    private void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }
}
