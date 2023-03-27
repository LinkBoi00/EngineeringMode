package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestCdma extends Activity {
    private static final int CHANNEL_DEFAULT = 0;
    private static final int CHANNEL_MAX = 2;
    private static final int CHANNEL_MAX2 = 4;
    private static final int CHANNEL_MIN = 1;
    private static final int CHANNEL_MIN2 = 3;
    public static final int DEFAULT_BAND_VALUE = 0;
    public static final int DEFAULT_CHANNEL_VALUE = 384;
    public static final int DEFAULT_POWER_VALUE = 23;
    public static final String KEY_1X_BAND = "Band_1x_CDMA";
    public static final String KEY_1X_CHANNEL = "Channel_1x_CDMA";
    public static final String KEY_1X_POWER = "Power_1x_CDMA";
    public static final String KEY_CDMA1X_ANT_MODE = "cdma1x_ant_mode";
    public static final String KEY_CDMA1x_ANT_STATUS = "cdma1x_ant_status";
    public static final String KEY_EVDO_ANT_MODE = "evdo_ant_mode";
    public static final String KEY_EVDO_ANT_STATUS = "evdo_ant_status";
    public static final String KEY_EVDO_BAND = "Band_evdo_CDMA";
    public static final String KEY_EVDO_CHANNEL = "Channel_evdo_CDMA";
    public static final String KEY_EVDO_POWER = "Power_evdo_CDMA";
    public static final String KEY_MODULATION = "Modulation_CDMA";
    private static final int POWER_DEFAULT = 5;
    private static final int POWER_MAX = 7;
    private static final int POWER_MIN = 6;
    public static final String TAG = "RfDesense/TxTestCdma";
    /* access modifiers changed from: private */
    public static int mModemType;
    protected CheckBox mAntMode;
    protected EditText mAntStatus;
    /* access modifiers changed from: private */
    public Spinner mBand;
    private Button mButtonSet;
    /* access modifiers changed from: private */
    public EditText mChannel;
    /* access modifiers changed from: private */
    public RadioGroup mModulation;
    /* access modifiers changed from: private */
    public EditText mPower;
    protected Toast mToast = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_tx_test_cdma);
        mModemType = getIntent().getIntExtra("mModemType", 2);
        this.mBand = (Spinner) findViewById(R.id.band_spinner);
        this.mModulation = (RadioGroup) findViewById(R.id.modulation_radio_group);
        this.mChannel = (EditText) findViewById(R.id.channel_editor);
        this.mPower = (EditText) findViewById(R.id.power_editor);
        this.mAntMode = (CheckBox) findViewById(R.id.rf_ant_mode);
        this.mAntStatus = (EditText) findViewById(R.id.rf_ant_status);
        this.mButtonSet = (Button) findViewById(R.id.button_set);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_cdma_band, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mBand.setAdapter(adapter);
        final String[] bandValues = getResources().getStringArray(R.array.rf_desense_tx_test_cdma_band_values);
        this.mButtonSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String command;
                switch (v.getId()) {
                    case R.id.button_set:
                        if (RfDesenseTxTestCdma.this.checkValues()) {
                            String band = bandValues[RfDesenseTxTestCdma.this.mBand.getSelectedItemPosition()];
                            long modulation = (long) RfDesenseTxTestCdma.this.mModulation.getCheckedRadioButtonId();
                            String channel = RfDesenseTxTestCdma.this.mChannel.getText().toString();
                            String power = RfDesenseTxTestCdma.this.mPower.getText().toString();
                            int tx_power = 0;
                            if (power != null && !"".equals(power)) {
                                tx_power = Integer.valueOf(power).intValue() + 60;
                            }
                            int i = 1;
                            if (RfDesenseTxTestCdma.mModemType == 1) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("AT+ECRFTX=1,");
                                sb.append(channel);
                                sb.append(",");
                                sb.append(band);
                                sb.append(",");
                                sb.append(tx_power);
                                sb.append(",");
                                if (modulation == 2131101010) {
                                    i = 0;
                                }
                                sb.append(i);
                                command = sb.toString();
                            } else {
                                command = "AT+ERFTX=13,4," + channel + "," + band + "," + tx_power;
                            }
                            RfDesenseTxTestCdma.this.saveState(command);
                            RfDesenseTxTestCdma.this.showToast("Set param suecceed!");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
        restoreState();
    }

    /* access modifiers changed from: private */
    public void saveState(String command) {
        SharedPreferences.Editor pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        String str = "1";
        if (mModemType == 1) {
            pref.putInt(KEY_1X_BAND, this.mBand.getSelectedItemPosition());
            pref.putString(KEY_1X_CHANNEL, this.mChannel.getText().toString());
            pref.putString(KEY_1X_POWER, this.mPower.getText().toString());
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            pref.putString(KEY_CDMA1X_ANT_MODE, str);
            pref.putString(KEY_CDMA1x_ANT_STATUS, this.mAntStatus.getText().toString());
            pref.putString(RfDesenseTxTest.KEY_CDMA_1X_ATCMD, command);
            String ant_str = "AT+ETXANT=1,4," + this.mAntStatus.getText();
            if (this.mAntMode.isChecked()) {
                pref.putString(RfDesenseTxTest.KEY_CDMA1X_ATCMD_ANT_SWITCH, ant_str);
            } else {
                pref.putString(RfDesenseTxTest.KEY_CDMA1X_ATCMD_ANT_SWITCH, "AT+ETXANT=0,4,0");
            }
        } else {
            pref.putInt(KEY_EVDO_BAND, this.mBand.getSelectedItemPosition());
            pref.putString(KEY_EVDO_CHANNEL, this.mChannel.getText().toString());
            pref.putString(KEY_EVDO_POWER, this.mPower.getText().toString());
            pref.putString(RfDesenseTxTest.KEY_CDMA_EVDO_ATCMD, command);
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            pref.putString(KEY_EVDO_ANT_MODE, str);
            pref.putString(KEY_EVDO_ANT_STATUS, this.mAntStatus.getText().toString());
            String ant_str2 = "AT+ETXANT=1,4," + this.mAntStatus.getText() + ",,0";
            if (this.mAntMode.isChecked()) {
                pref.putString(RfDesenseTxTest.KEY_EVDO_ATCMD_ANT_SWITCH, ant_str2);
            } else {
                pref.putString(RfDesenseTxTest.KEY_EVDO_ATCMD_ANT_SWITCH, "AT+ETXANT=0,4,0,,0");
            }
        }
        pref.apply();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        if (mModemType == 1) {
            this.mModulation.check(R.id.modulation_1x);
            this.mBand.setSelection(pref.getInt(KEY_1X_BAND, 0));
            this.mChannel.setText(pref.getString(KEY_1X_CHANNEL, "384"));
            this.mPower.setText(pref.getString(KEY_1X_POWER, "23"));
            this.mAntMode.setChecked(pref.getString(KEY_CDMA1X_ANT_MODE, "0").equals("1"));
            this.mAntStatus.setText(pref.getString(KEY_CDMA1x_ANT_STATUS, "0"));
            return;
        }
        this.mModulation.check(R.id.modulation_evdo);
        this.mBand.setSelection(pref.getInt(KEY_EVDO_BAND, 0));
        this.mChannel.setText(pref.getString(KEY_EVDO_CHANNEL, "384"));
        this.mPower.setText(pref.getString(KEY_EVDO_POWER, "23"));
        this.mAntMode.setChecked(pref.getString(KEY_EVDO_ANT_MODE, "0").equals("1"));
        this.mAntStatus.setText(pref.getString(KEY_EVDO_ANT_STATUS, "0"));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public boolean checkValues() {
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
}
