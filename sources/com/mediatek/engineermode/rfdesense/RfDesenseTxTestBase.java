package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.telephony.Phone;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestBase extends Activity {
    protected static final int CHANNEL_DEFAULT = 0;
    protected static final int CHANNEL_MAX = 2;
    protected static final int CHANNEL_MAX2 = 4;
    protected static final int CHANNEL_MIN = 1;
    protected static final int CHANNEL_MIN2 = 3;
    protected static final int HINT = 5;
    protected static final int PAUSE = 2;
    protected static final int POWER_DEFAULT = 5;
    protected static final int POWER_MAX = 7;
    protected static final int POWER_MIN = 6;
    protected static final int START = 1;
    protected static final int STATE_NONE = 0;
    protected static final int STATE_PAUSED = 2;
    protected static final int STATE_STARTED = 1;
    public static final String TAG = "RfDesense/TxTestBase";
    protected static final int UPDATE_BUTTON = 4;
    protected static final int UPDATE_DELAY = 1000;
    protected Editor mAfc = new Editor();
    protected CheckBox mAntMode;
    protected EditText mAntStatus;
    protected Spinner mBand;
    /* access modifiers changed from: private */
    public String[] mBandArray;
    protected Button mButtonSet;
    protected Editor mChannel = new Editor();
    protected int mCurrentBand = -1;
    protected TextView mDbm;
    protected Spinner mPattern;
    /* access modifiers changed from: private */
    public String[] mPatternArray;
    protected Phone mPhone;
    protected Editor mPower = new Editor();
    protected Toast mToast = null;
    protected Editor mTsc = new Editor();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test_gsm);
        this.mBand = (Spinner) findViewById(R.id.band_spinner);
        this.mChannel.editor = (EditText) findViewById(R.id.channel_editor);
        this.mPower.editor = (EditText) findViewById(R.id.power_editor);
        this.mAfc.editor = (EditText) findViewById(R.id.afc_editor);
        this.mTsc.editor = (EditText) findViewById(R.id.tsc_editor);
        this.mPattern = (Spinner) findViewById(R.id.pattern_spinner);
        this.mAntMode = (CheckBox) findViewById(R.id.rf_ant_mode);
        this.mAntStatus = (EditText) findViewById(R.id.rf_ant_status);
        this.mButtonSet = (Button) findViewById(R.id.button_set);
        this.mDbm = (TextView) findViewById(R.id.powerDbm);
        this.mBandArray = getResources().getStringArray(R.array.rf_desense_tx_test_gsm_band_values);
        this.mPatternArray = getResources().getStringArray(R.array.rf_desense_tx_test_gsm_pattern_values);
        this.mButtonSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_set:
                        if (RfDesenseTxTestBase.this.checkValues()) {
                            String band = RfDesenseTxTestBase.this.mBandArray[RfDesenseTxTestBase.this.mBand.getSelectedItemPosition()];
                            String channel = RfDesenseTxTestBase.this.mChannel.getText();
                            String power = RfDesenseTxTestBase.this.mPower.getText();
                            String afc = RfDesenseTxTestBase.this.mAfc.getText();
                            String tsc = RfDesenseTxTestBase.this.mTsc.getText();
                            String pattern = RfDesenseTxTestBase.this.mPatternArray[RfDesenseTxTestBase.this.mPattern.getSelectedItemPosition()];
                            RfDesenseTxTestBase.this.saveState("AT+ERFTX=2,1," + channel + "," + afc + "," + band + "," + tsc + "," + power + "," + pattern);
                            RfDesenseTxTestBase.this.showToast("Set param suecceed!");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
        this.mBand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (arg2 != RfDesenseTxTestBase.this.mCurrentBand) {
                    RfDesenseTxTestBase.this.mCurrentBand = arg2;
                    RfDesenseTxTestBase.this.updateLimits();
                    RfDesenseTxTestBase.this.setDefaultValues();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveState(String command) {
        SharedPreferences.Editor pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        pref.putInt(RfDesenseTxTestGsm.KEY_BAND, this.mBand.getSelectedItemPosition());
        pref.putString(RfDesenseTxTestGsm.KEY_CHANNEL, this.mChannel.getText());
        pref.putString(RfDesenseTxTestGsm.KEY_POWER, this.mPower.getText());
        pref.putString(RfDesenseTxTestGsm.KEY_AFC, this.mAfc.getText());
        pref.putString(RfDesenseTxTestGsm.KEY_TSC, this.mTsc.getText());
        pref.putInt(RfDesenseTxTestGsm.KEY_PATTERN, this.mPattern.getSelectedItemPosition());
        pref.putString(RfDesenseTxTestGsm.KEY_ANT_MODE, this.mAntMode.isChecked() ? "1" : "0");
        pref.putString(RfDesenseTxTestGsm.KEY_ANT_STATUS, this.mAntStatus.getText().toString());
        pref.putString(RfDesenseTxTest.KEY_GSM_ATCMD, command);
        String ant_str = "AT+ETXANT=1,1," + this.mAntStatus.getText() + ",,0";
        if (this.mAntMode.isChecked()) {
            pref.putString(RfDesenseTxTest.KEY_GSM_ATCMD_ANT_SWITCH, ant_str);
        } else {
            ant_str = "AT+ETXANT=0,1,0,,0";
            pref.putString(RfDesenseTxTest.KEY_GSM_ATCMD_ANT_SWITCH, ant_str);
        }
        Elog.i(TAG, "GSM at command = " + command);
        Elog.i(TAG, "GSM at command ant status = " + ant_str);
        pref.apply();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void updateLimits() {
    }

    /* access modifiers changed from: protected */
    public boolean checkValues() {
        Editor[] editors = {this.mChannel, this.mPower, this.mAfc, this.mTsc};
        String[] toast = {"Channel", "TX Power", "AFC", "TSC"};
        int i = 0;
        while (i < 4) {
            Editor editor = editors[i];
            if (editor.editor.getVisibility() != 0 || editor.check()) {
                i++;
            } else {
                Toast.makeText(this, "Invalid " + toast[i] + ". Valid range: " + editor.getValidRange(), 0).show();
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 1);
        this.mToast = makeText;
        makeText.show();
    }

    /* access modifiers changed from: protected */
    public void setDefaultValues() {
        this.mChannel.setToDefault();
        this.mPower.setToDefault();
        this.mAfc.setToDefault();
        this.mTsc.setToDefault();
        this.mAntMode.setChecked("0".equals("1"));
        this.mAntStatus.setText("0");
    }

    protected static class Editor {
        public String defaultValue = null;
        public EditText editor;
        public int max;
        public int max2;
        public int min;
        public int min2;
        public int step = 1;

        protected Editor() {
        }

        public void set(String arg0, String arg1, String arg2, String arg3, String arg4) {
            this.defaultValue = arg0;
            this.min = Integer.parseInt(arg1);
            this.max = Integer.parseInt(arg2);
            this.min2 = Integer.parseInt(arg3);
            this.max2 = Integer.parseInt(arg4);
        }

        public void set(String arg0, String arg1, String arg2) {
            this.defaultValue = arg0;
            this.min = Integer.parseInt(arg1);
            this.max = Integer.parseInt(arg2);
            this.min2 = Integer.parseInt(arg1);
            this.max2 = Integer.parseInt(arg2);
        }

        public String getText() {
            return this.editor.getText().toString();
        }

        public void setText(String text) {
            this.editor.setText(text);
        }

        public void setToDefault() {
            this.editor.setText(this.defaultValue);
        }

        public boolean check() {
            try {
                int value = Integer.parseInt(getText());
                int i = this.min;
                if (value < i || value > this.max) {
                    if (value >= this.min2) {
                        if (value > this.max2) {
                        }
                    }
                    return false;
                }
                int i2 = this.step;
                if (i2 == 1 || (value - i) % i2 == 0) {
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public String getValidRange() {
            String text = "[" + this.min + "," + this.max + "]";
            if (this.min != this.min2) {
                text = text + ", [" + this.min2 + "," + this.max2 + "]";
            }
            if (this.step == 1) {
                return text;
            }
            return text + ", step " + this.step;
        }
    }
}
