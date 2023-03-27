package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.RadioStateManager;

public class RfDesenseTxTestLteCA extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final int DEFAULT_BAND = 2;
    private static final int DEFAULT_BAND_WIDTH = 0;
    private static final int DEFAULT_DUPLEX = 1;
    private static final String DEFAULT_FREQ = "17475";
    private static final int DEFAULT_MCS = 0;
    private static final int DEFAULT_MODE_SCELL1 = 0;
    private static final int DEFAULT_MODE_SCELL2 = 0;
    private static final String DEFAULT_POWER = "5";
    private static final int DEFAULT_TDD_CONFIG = 0;
    private static final int DEFAULT_TDD_SPECIAL = 0;
    private static final String DEFAULT_VRB_LENGTH = "1";
    private static final String DEFAULT_VRB_START = "0";
    private static final int DUPLEX_FDD = 1;
    private static final int DUPLEX_TDD = 0;
    private static final int FDD_BAND_MAX = 31;
    private static final int FDD_BAND_MIN = 1;
    private static final int HINT = 1;
    private static final String KEY_BAND_PCELL = "band_pcell";
    private static final String KEY_BAND_SCELL1 = "band_scell1";
    private static final String KEY_BAND_SCELL2 = "band_scell2";
    private static final String KEY_BAND_WIDTH_PCELL = "bandwidth_pcell";
    private static final String KEY_BAND_WIDTH_SCELL1 = "bandwidth_scell1";
    private static final String KEY_BAND_WIDTH_SCELL2 = "bandwidth_scell2";
    private static final String KEY_DUPLEX_PCELL = "duplex_pcell";
    private static final String KEY_DUPLEX_SCELL1 = "duplex_scell1";
    private static final String KEY_DUPLEX_SCELL2 = "duplex_scell2";
    private static final String KEY_FREQ_PCELL = "freq_pcell";
    private static final String KEY_FREQ_SCELL1 = "freq_scell1";
    private static final String KEY_FREQ_SCELL2 = "freq_scell2";
    private static final String KEY_MCS_PCELL = "mcs_pcell";
    private static final String KEY_MCS_SCELL1 = "mcs_scell1";
    private static final String KEY_MCS_SCELL2 = "mcs_scell2";
    private static final String KEY_MODE_SCELL1 = "mode_scell1";
    private static final String KEY_MODE_SCELL2 = "mode_scell2";
    private static final String KEY_POWER_PCELL = "power_pcell";
    private static final String KEY_POWER_SCELL1 = "power_scell1";
    private static final String KEY_POWER_SCELL2 = "power_scell2";
    public static final String KEY_STATE = "rf_state";
    private static final String KEY_TDD_CONFIG_PCELL = "tdd_config_pcell";
    private static final String KEY_TDD_CONFIG_SCELL1 = "tdd_config_scell1";
    private static final String KEY_TDD_CONFIG_SCELL2 = "tdd_config_scell2";
    private static final String KEY_TDD_SPECIAL_PCELL = "tdd_special_pcell";
    private static final String KEY_TDD_SPECIAL_SCELL1 = "tdd_special_scell1";
    private static final String KEY_TDD_SPECIAL_SCELL2 = "tdd_special_scell2";
    private static final String KEY_VRB_LENGTH_PCELL = "vrb_length_pcell";
    private static final String KEY_VRB_LENGTH_SCELL1 = "vrb_length_scell1";
    private static final String KEY_VRB_LENGTH_SCELL2 = "vrb_length_scell2";
    private static final String KEY_VRB_START_PCELL = "vrb_start_pcell";
    private static final String KEY_VRB_START_SCELL1 = "vrb_start_scell1";
    private static final String KEY_VRB_START_SCELL2 = "vrb_start_scell2";
    private static final int PAUSE = 2;
    private static final int POWER_MAX = 23;
    private static final int POWER_MIN = -50;
    private static final int SIM_CARD_INSERT = 2;
    private static final int START = 1;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_STARTED = 1;
    public static final String TAG = "RfDesenseTxTestLteCA";
    private static final int TDD_BAND_MAX = 44;
    private static final int TDD_BAND_MIN = 33;
    private static final int TDD_CONFIG_MAX = 6;
    private static final int TDD_SPECIAL_MAX = 9;
    private static final int UPDATE_BUTTON = 4;
    private static final int VRB_LENGTH_MAX = 100;
    private static final int VRB_LENGTH_MIN = 1;
    private static final int VRB_START_MAX = 99;
    private static final int VRB_START_MIN = 0;
    private Spinner[] mBand = new Spinner[3];
    private Spinner[] mBandWidth = new Spinner[3];
    private Button mButtonPause;
    private Button mButtonStart;
    private int mCellCount = 1;
    private RadioButton[] mFdd = new RadioButton[3];
    private EditText[] mFreq = new EditText[3];
    private TextView[] mFreqRange = new TextView[3];
    private String[] mFreqRangeArray;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (((AsyncResult) msg.obj).exception == null) {
                        RfDesenseTxTestLteCA.this.showToast("START Command succeeded.");
                    } else {
                        RfDesenseTxTestLteCA.this.showToast("START Command failed.");
                    }
                    RfDesenseTxTestLteCA.this.mHandler.sendEmptyMessageDelayed(4, 1000);
                    return;
                case 2:
                    if (((AsyncResult) msg.obj).exception == null) {
                        RfDesenseTxTestLteCA.this.showToast("PAUSE Command succeeded.");
                    } else {
                        RfDesenseTxTestLteCA.this.showToast("PAUSE Command failed.");
                    }
                    RfDesenseTxTestLteCA.this.mHandler.sendEmptyMessageDelayed(4, 1000);
                    return;
                case 4:
                    RfDesenseTxTestLteCA.this.updateButtons();
                    return;
                default:
                    return;
            }
        }
    };
    private Spinner[] mMcs = new Spinner[3];
    private EditText[] mPower = new EditText[3];
    private RadioStateManager.RadioListener mRadioListener = new RadioStateManager.RadioListener() {
        public void onRadioPowerOff() {
            Elog.d(RfDesenseTxTestLteCA.TAG, "The rf is off");
            int unused = RfDesenseTxTestLteCA.this.mState = 0;
            RfDesenseTxTestLteCA.this.updateButtons();
        }

        public void onRadioPowerOn() {
        }
    };
    private RadioStateManager mRadioStateManager;
    private CheckBox[] mRbCellSelect = new CheckBox[3];
    /* access modifiers changed from: private */
    public int mState = 0;
    private TableLayout[] mTbCellConfig = new TableLayout[3];
    private RadioButton[] mTdd = new RadioButton[3];
    private Spinner[] mTddConfig = new Spinner[3];
    private Spinner[] mTddSpecial = new Spinner[3];
    private Toast mToast = null;
    private EditText[] mVrbLength = new EditText[3];
    private EditText[] mVrbStart = new EditText[3];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test_lte_ca);
        this.mFdd[0] = (RadioButton) findViewById(R.id.fdd_radio_button_pcell);
        this.mTdd[0] = (RadioButton) findViewById(R.id.tdd_radio_button_pcell);
        this.mFdd[0].setChecked(false);
        this.mTdd[0].setChecked(false);
        this.mFdd[1] = (RadioButton) findViewById(R.id.fdd_radio_button_scell1);
        this.mTdd[1] = (RadioButton) findViewById(R.id.tdd_radio_button_scell1);
        this.mFdd[1].setChecked(false);
        this.mTdd[1].setChecked(false);
        this.mFdd[2] = (RadioButton) findViewById(R.id.fdd_radio_button_scell2);
        this.mTdd[2] = (RadioButton) findViewById(R.id.tdd_radio_button_scell2);
        this.mFdd[2].setChecked(false);
        this.mTdd[2].setChecked(false);
        this.mRbCellSelect[0] = (CheckBox) findViewById(R.id.rb_rf_desense_pcell);
        this.mRbCellSelect[1] = (CheckBox) findViewById(R.id.rb_rf_desense_scell1);
        this.mRbCellSelect[2] = (CheckBox) findViewById(R.id.rb_rf_desense_scell2);
        this.mRbCellSelect[0].setChecked(true);
        this.mRbCellSelect[0].setEnabled(false);
        this.mRbCellSelect[1].setChecked(false);
        this.mRbCellSelect[1].setOnCheckedChangeListener(this);
        this.mRbCellSelect[2].setChecked(false);
        this.mRbCellSelect[2].setOnCheckedChangeListener(this);
        this.mTbCellConfig[0] = (TableLayout) findViewById(R.id.TableLayout_pcell);
        this.mTbCellConfig[1] = (TableLayout) findViewById(R.id.TableLayout_Scell1);
        this.mTbCellConfig[2] = (TableLayout) findViewById(R.id.TableLayout_Scell2);
        this.mTbCellConfig[1].setVisibility(8);
        this.mTbCellConfig[2].setVisibility(8);
        this.mBand[0] = (Spinner) findViewById(R.id.band_spinner_pcell);
        this.mBandWidth[0] = (Spinner) findViewById(R.id.bandwidth_spinner_pcell);
        this.mFreq[0] = (EditText) findViewById(R.id.freq_editor_pcell);
        this.mTddConfig[0] = (Spinner) findViewById(R.id.tdd_config_spinner_pcell);
        this.mTddSpecial[0] = (Spinner) findViewById(R.id.tdd_special_spinner_pcell);
        this.mVrbStart[0] = (EditText) findViewById(R.id.vrb_start_editor_pcell);
        this.mVrbLength[0] = (EditText) findViewById(R.id.vrb_length_editor_pcell);
        this.mMcs[0] = (Spinner) findViewById(R.id.mcs_spinner_pcell);
        this.mPower[0] = (EditText) findViewById(R.id.power_editor_pcell);
        this.mFreqRange[0] = (TextView) findViewById(R.id.freq_pcell);
        this.mBand[1] = (Spinner) findViewById(R.id.band_spinner_scell1);
        this.mBandWidth[1] = (Spinner) findViewById(R.id.bandwidth_spinner_scell1);
        this.mFreq[1] = (EditText) findViewById(R.id.freq_editor_scell1);
        this.mTddConfig[1] = (Spinner) findViewById(R.id.tdd_config_spinner_scell1);
        this.mTddSpecial[1] = (Spinner) findViewById(R.id.tdd_special_spinner_scell1);
        this.mVrbStart[1] = (EditText) findViewById(R.id.vrb_start_editor_scell1);
        this.mVrbLength[1] = (EditText) findViewById(R.id.vrb_length_editor_scell1);
        this.mMcs[1] = (Spinner) findViewById(R.id.mcs_spinner_scell1);
        this.mPower[1] = (EditText) findViewById(R.id.power_editor_scell1);
        this.mFreqRange[1] = (TextView) findViewById(R.id.freq_scell1);
        this.mBand[2] = (Spinner) findViewById(R.id.band_spinner_scell2);
        this.mBandWidth[2] = (Spinner) findViewById(R.id.bandwidth_spinner_scell2);
        this.mFreq[2] = (EditText) findViewById(R.id.freq_editor_scell2);
        this.mTddConfig[2] = (Spinner) findViewById(R.id.tdd_config_spinner_scell2);
        this.mTddSpecial[2] = (Spinner) findViewById(R.id.tdd_special_spinner_scell2);
        this.mVrbStart[2] = (EditText) findViewById(R.id.vrb_start_editor_scell2);
        this.mVrbLength[2] = (EditText) findViewById(R.id.vrb_length_editor_scell2);
        this.mMcs[2] = (Spinner) findViewById(R.id.mcs_spinner_scell2);
        this.mPower[2] = (EditText) findViewById(R.id.power_editor_scell2);
        this.mFreqRange[2] = (TextView) findViewById(R.id.freq_scell2);
        this.mButtonStart = (Button) findViewById(R.id.button_start);
        this.mButtonPause = (Button) findViewById(R.id.button_pause);
        ArrayAdapter[] adapter = new ArrayAdapter[3];
        for (int i = 0; i < 3; i++) {
            adapter[i] = new ArrayAdapter(this, 17367048);
            adapter[i].setDropDownViewResource(17367049);
            this.mBand[i].setAdapter(adapter[i]);
            adapter[i] = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_lte_bandwidth, 17367048);
            adapter[i].setDropDownViewResource(17367049);
            this.mBandWidth[i].setAdapter(adapter[i]);
            adapter[i] = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_lte_mcs, 17367048);
            adapter[i].setDropDownViewResource(17367049);
            this.mMcs[i].setAdapter(adapter[i]);
            adapter[i] = new ArrayAdapter(this, 17367048);
            adapter[i].setDropDownViewResource(17367049);
            this.mTddConfig[i].setAdapter(adapter[i]);
            adapter[i] = new ArrayAdapter(this, 17367048);
            adapter[i].setDropDownViewResource(17367049);
            this.mTddSpecial[i].setAdapter(adapter[i]);
            this.mFdd[i].setOnCheckedChangeListener(this);
            this.mTdd[i].setOnCheckedChangeListener(this);
            this.mBand[i].setOnItemSelectedListener(this);
        }
        this.mButtonStart.setOnClickListener(this);
        this.mButtonPause.setOnClickListener(this);
        this.mFreqRangeArray = getResources().getStringArray(R.array.rf_desense_tx_test_lte_freq_range);
        restoreState();
        disableAllButtons();
        RadioStateManager radioStateManager = new RadioStateManager(this);
        this.mRadioStateManager = radioStateManager;
        radioStateManager.registerRadioStateChanged(this.mRadioListener);
        if (!EmUtils.ifAirplaneModeEnabled()) {
            Elog.d(TAG, "it is in AirplaneMode");
            this.mRadioStateManager.setAirplaneModeEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        saveState();
        this.mRadioStateManager.unregisterRadioStateChanged();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d(TAG, "onResume");
        if (ModemCategory.isSimReady(-1)) {
            Elog.d(TAG, "some card insert");
            showDialog(2);
        }
    }

    public void onBackPressed() {
        if (this.mState == 1) {
            showDialog(1);
            return;
        }
        EmUtils.setAirplaneModeEnabled(false);
        super.onBackPressed();
    }

    public void onCheckedChanged(CompoundButton v, boolean checked) {
        switch (v.getId()) {
            case R.id.fdd_radio_button_pcell:
            case R.id.tdd_radio_button_pcell:
                onDuplexChange(0);
                return;
            case R.id.fdd_radio_button_scell1:
            case R.id.tdd_radio_button_scell1:
                onDuplexChange(1);
                return;
            case R.id.fdd_radio_button_scell2:
            case R.id.tdd_radio_button_scell2:
                onDuplexChange(2);
                return;
            case R.id.rb_rf_desense_scell1:
                if (checked) {
                    this.mRbCellSelect[2].setEnabled(true);
                    this.mTbCellConfig[1].setVisibility(0);
                    this.mCellCount = 2;
                    return;
                }
                this.mRbCellSelect[2].setEnabled(false);
                this.mRbCellSelect[2].setChecked(false);
                this.mTbCellConfig[1].setVisibility(8);
                this.mTbCellConfig[2].setVisibility(8);
                this.mCellCount = 1;
                return;
            case R.id.rb_rf_desense_scell2:
                if (checked) {
                    this.mTbCellConfig[2].setVisibility(0);
                    this.mCellCount = 3;
                    return;
                }
                this.mTbCellConfig[2].setVisibility(8);
                this.mCellCount = 2;
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pause:
                sendAtCommand("AT+EGMC=1,\"lte_tx\",1,0", 2);
                disableAllButtons();
                this.mState = 2;
                return;
            case R.id.button_start:
                if (checkValues()) {
                    for (int i = 0; i < this.mCellCount; i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("AT+EGMC=1,\"lte_tx\",1,1,");
                        sb.append(this.mCellCount);
                        sb.append(",");
                        sb.append(i + 1);
                        sb.append(",");
                        sb.append(this.mTdd[i].isChecked() ^ true ? 1 : 0);
                        sb.append(",");
                        sb.append(this.mBand[i].getSelectedItemPosition() + (this.mTdd[i].isChecked() ? 33 : 1));
                        sb.append(",");
                        sb.append(this.mBandWidth[i].getSelectedItemPosition());
                        sb.append(",");
                        sb.append(this.mFreq[i].getText().toString());
                        sb.append(",");
                        sb.append(this.mTddConfig[i].getSelectedItemPosition());
                        sb.append(",");
                        sb.append(this.mTddSpecial[i].getSelectedItemPosition());
                        sb.append(",");
                        sb.append(this.mVrbStart[i].getText().toString());
                        sb.append(",");
                        sb.append(this.mVrbLength[i].getText().toString());
                        sb.append(",");
                        sb.append(this.mMcs[i].getSelectedItemPosition());
                        sb.append(",");
                        sb.append(this.mPower[i].getText().toString());
                        sendAtCommand(sb.toString(), 1);
                    }
                    disableAllButtons();
                    this.mState = 1;
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("Hint").setMessage("Please pause the test first!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle("Notice").setMessage("Please pull out the sim card before test").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private void onDuplexChange(int cell_index) {
        boolean tdd = this.mTdd[cell_index].isChecked();
        int bandMin = tdd ? 33 : 1;
        int bandMax = tdd ? 44 : 31;
        int i = 0;
        int configMax = tdd ? 6 : 0;
        int specialMax = tdd ? 9 : 0;
        this.mTddConfig[cell_index].setEnabled(tdd);
        this.mTddSpecial[cell_index].setEnabled(tdd);
        Elog.i(TAG, "tdd = " + tdd);
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter) this.mBand[cell_index].getAdapter();
        adapter.clear();
        for (int i2 = bandMin; i2 <= bandMax; i2++) {
            adapter.add(String.valueOf(i2));
        }
        adapter.notifyDataSetChanged();
        Spinner spinner = this.mBand[cell_index];
        if (!tdd) {
            i = 2;
        }
        spinner.setSelection(i);
        ArrayAdapter<CharSequence> adapter2 = (ArrayAdapter) this.mTddConfig[cell_index].getAdapter();
        adapter2.clear();
        for (int i3 = 0; i3 <= configMax; i3++) {
            adapter2.add(String.valueOf(i3));
        }
        adapter2.notifyDataSetChanged();
        ArrayAdapter<CharSequence> adapter3 = (ArrayAdapter) this.mTddSpecial[cell_index].getAdapter();
        adapter3.clear();
        for (int i4 = 0; i4 <= specialMax; i4++) {
            adapter3.add(String.valueOf(i4));
        }
        adapter3.notifyDataSetChanged();
        setDefaultValue(cell_index);
    }

    private boolean checkValues() {
        try {
            int value = Integer.parseInt(this.mVrbStart[0].getText().toString());
            if (value >= 0) {
                if (value <= 99) {
                    int value2 = Integer.parseInt(this.mVrbLength[0].getText().toString());
                    if (value2 >= 1) {
                        if (value2 <= 100) {
                            int value3 = Integer.parseInt(this.mPower[0].getText().toString());
                            if (value3 >= POWER_MIN) {
                                if (value3 <= 23) {
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

    private void saveState() {
        SharedPreferences.Editor editor = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        editor.putInt(KEY_DUPLEX_PCELL, this.mTdd[0].isChecked() ^ true ? 1 : 0);
        editor.putInt(KEY_BAND_PCELL, this.mBand[0].getSelectedItemPosition());
        editor.putInt(KEY_BAND_WIDTH_PCELL, this.mBandWidth[0].getSelectedItemPosition());
        editor.putString(KEY_FREQ_PCELL, this.mFreq[0].getText().toString());
        editor.putInt(KEY_TDD_CONFIG_PCELL, this.mTddConfig[0].getSelectedItemPosition());
        editor.putInt(KEY_TDD_SPECIAL_PCELL, this.mTddSpecial[0].getSelectedItemPosition());
        editor.putString(KEY_VRB_START_PCELL, this.mVrbStart[0].getText().toString());
        editor.putString(KEY_VRB_LENGTH_PCELL, this.mVrbLength[0].getText().toString());
        editor.putInt(KEY_MCS_PCELL, this.mMcs[0].getSelectedItemPosition());
        editor.putString(KEY_POWER_PCELL, this.mPower[0].getText().toString());
        editor.putInt(KEY_DUPLEX_SCELL1, this.mTdd[1].isChecked() ^ true ? 1 : 0);
        editor.putInt(KEY_BAND_SCELL1, this.mBand[1].getSelectedItemPosition());
        editor.putInt(KEY_BAND_WIDTH_SCELL1, this.mBandWidth[1].getSelectedItemPosition());
        editor.putString(KEY_FREQ_SCELL1, this.mFreq[1].getText().toString());
        editor.putInt(KEY_TDD_CONFIG_SCELL1, this.mTddConfig[1].getSelectedItemPosition());
        editor.putInt(KEY_TDD_SPECIAL_SCELL1, this.mTddSpecial[1].getSelectedItemPosition());
        editor.putString(KEY_VRB_START_SCELL1, this.mVrbStart[1].getText().toString());
        editor.putString(KEY_VRB_LENGTH_SCELL1, this.mVrbLength[1].getText().toString());
        editor.putInt(KEY_MCS_SCELL1, this.mMcs[1].getSelectedItemPosition());
        editor.putString(KEY_POWER_SCELL1, this.mPower[1].getText().toString());
        editor.putInt(KEY_DUPLEX_SCELL2, this.mTdd[2].isChecked() ^ true ? 1 : 0);
        editor.putInt(KEY_BAND_SCELL2, this.mBand[2].getSelectedItemPosition());
        editor.putInt(KEY_BAND_WIDTH_SCELL2, this.mBandWidth[2].getSelectedItemPosition());
        editor.putString(KEY_FREQ_SCELL2, this.mFreq[2].getText().toString());
        editor.putInt(KEY_TDD_CONFIG_SCELL2, this.mTddConfig[2].getSelectedItemPosition());
        editor.putInt(KEY_TDD_SPECIAL_SCELL2, this.mTddSpecial[2].getSelectedItemPosition());
        editor.putString(KEY_VRB_START_SCELL2, this.mVrbStart[2].getText().toString());
        editor.putString(KEY_VRB_LENGTH_SCELL2, this.mVrbLength[2].getText().toString());
        editor.putInt(KEY_MCS_SCELL2, this.mMcs[2].getSelectedItemPosition());
        editor.putString(KEY_POWER_SCELL2, this.mPower[2].getText().toString());
        editor.putInt(KEY_MODE_SCELL1, this.mRbCellSelect[1].isChecked() ? 1 : 0);
        editor.putInt(KEY_MODE_SCELL2, this.mRbCellSelect[2].isChecked() ? 1 : 0);
        editor.putInt(KEY_STATE, this.mState);
        editor.commit();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        boolean z = true;
        int duplex = pref.getInt(KEY_DUPLEX_PCELL, 1);
        this.mFdd[0].setChecked(duplex == 1);
        this.mTdd[0].setChecked(duplex == 0);
        this.mBand[0].setSelection(pref.getInt(KEY_BAND_PCELL, 2));
        this.mBandWidth[0].setSelection(pref.getInt(KEY_BAND_WIDTH_PCELL, 0));
        this.mFreq[0].setText(pref.getString(KEY_FREQ_PCELL, "17475"));
        this.mTddConfig[0].setSelection(pref.getInt(KEY_TDD_CONFIG_PCELL, 0));
        this.mTddSpecial[0].setSelection(pref.getInt(KEY_TDD_SPECIAL_PCELL, 0));
        this.mVrbStart[0].setText(pref.getString(KEY_VRB_START_PCELL, "0"));
        this.mVrbLength[0].setText(pref.getString(KEY_VRB_LENGTH_PCELL, "1"));
        this.mMcs[0].setSelection(pref.getInt(KEY_MCS_PCELL, 0));
        this.mPower[0].setText(pref.getString(KEY_POWER_PCELL, DEFAULT_POWER));
        int duplex2 = pref.getInt(KEY_DUPLEX_SCELL1, 1);
        this.mFdd[1].setChecked(duplex2 == 1);
        this.mTdd[1].setChecked(duplex2 == 0);
        this.mBand[1].setSelection(pref.getInt(KEY_BAND_SCELL1, 2));
        this.mBandWidth[1].setSelection(pref.getInt(KEY_BAND_WIDTH_SCELL1, 0));
        this.mFreq[1].setText(pref.getString(KEY_FREQ_SCELL1, "17475"));
        this.mTddConfig[1].setSelection(pref.getInt(KEY_TDD_CONFIG_SCELL1, 0));
        this.mTddSpecial[1].setSelection(pref.getInt(KEY_TDD_SPECIAL_SCELL1, 0));
        this.mVrbStart[1].setText(pref.getString(KEY_VRB_START_SCELL1, "0"));
        this.mVrbLength[1].setText(pref.getString(KEY_VRB_LENGTH_SCELL1, "1"));
        this.mMcs[1].setSelection(pref.getInt(KEY_MCS_SCELL1, 0));
        this.mPower[1].setText(pref.getString(KEY_POWER_SCELL1, DEFAULT_POWER));
        int duplex3 = pref.getInt(KEY_DUPLEX_SCELL2, 1);
        this.mFdd[2].setChecked(duplex3 == 1);
        this.mTdd[2].setChecked(duplex3 == 0);
        this.mBand[2].setSelection(pref.getInt(KEY_BAND_SCELL2, 2));
        this.mBandWidth[2].setSelection(pref.getInt(KEY_BAND_WIDTH_SCELL2, 0));
        this.mFreq[2].setText(pref.getString(KEY_FREQ_SCELL2, "17475"));
        this.mTddConfig[2].setSelection(pref.getInt(KEY_TDD_CONFIG_SCELL2, 0));
        this.mTddSpecial[2].setSelection(pref.getInt(KEY_TDD_SPECIAL_SCELL2, 0));
        this.mVrbStart[2].setText(pref.getString(KEY_VRB_START_SCELL2, "0"));
        this.mVrbLength[2].setText(pref.getString(KEY_VRB_LENGTH_SCELL2, "1"));
        this.mMcs[2].setSelection(pref.getInt(KEY_MCS_SCELL2, 0));
        this.mPower[2].setText(pref.getString(KEY_POWER_SCELL2, DEFAULT_POWER));
        this.mRbCellSelect[1].setChecked(pref.getInt(KEY_MODE_SCELL1, 0) == 1);
        CheckBox checkBox = this.mRbCellSelect[2];
        if (pref.getInt(KEY_MODE_SCELL2, 0) != 1) {
            z = false;
        }
        checkBox.setChecked(z);
        this.mState = pref.getInt(KEY_STATE, 0);
        updateButtons();
    }

    private void setDefaultValue(int cell_index) {
        this.mBandWidth[cell_index].setSelection(0);
        this.mFreq[cell_index].setText(String.valueOf(getDefaultFreq(cell_index)));
        this.mTddConfig[cell_index].setSelection(0);
        this.mTddSpecial[cell_index].setSelection(0);
        this.mVrbStart[cell_index].setText("0");
        this.mVrbLength[cell_index].setText("1");
        this.mMcs[cell_index].setSelection(0);
        this.mPower[cell_index].setText(DEFAULT_POWER);
    }

    private int getDefaultFreq(int cell_index) {
        String[] range = this.mFreqRangeArray[(this.mBand[cell_index].getSelectedItemPosition() + (this.mTdd[cell_index].isChecked() ? 33 : 1)) - 1].split(",");
        try {
            return (Integer.parseInt(range[0]) + Integer.parseInt(range[1])) / 2;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Check the array resource");
        }
    }

    private void disableAllButtons() {
        this.mButtonStart.setEnabled(false);
        this.mButtonPause.setEnabled(false);
    }

    /* access modifiers changed from: private */
    public void updateButtons() {
        Button button = this.mButtonStart;
        int i = this.mState;
        boolean z = false;
        button.setEnabled(i == 0 || i == 2);
        Button button2 = this.mButtonPause;
        if (this.mState == 1) {
            z = true;
        }
        button2.setEnabled(z);
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Elog.d(TAG, msg);
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 1);
        this.mToast = makeText;
        makeText.show();
    }

    private void sendAtCommand(String str, int what) {
        String[] cmd = {str, ""};
        Elog.i(TAG, "send: " + cmd[0]);
        EmUtils.invokeOemRilRequestStringsEm(cmd, this.mHandler.obtainMessage(what));
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view.getId() == R.id.band_spinner_pcell) {
            setDefaultValue(0);
        } else if (view.getId() == R.id.band_spinner_scell1) {
            setDefaultValue(1);
        } else if (view.getId() == R.id.band_spinner_scell2) {
            setDefaultValue(2);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
