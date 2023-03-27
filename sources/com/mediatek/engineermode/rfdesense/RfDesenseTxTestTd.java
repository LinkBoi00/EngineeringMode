package com.mediatek.engineermode.rfdesense;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestTd extends RfDesenseTxTestBase {
    public static final String KEY_FDD_ANT_MODE = "Power_FDD_ant_mode";
    public static final String KEY_FDD_ANT_STATUS = "Power_FDD_ant_status";
    public static final String KEY_FDD_BAND = "Band_FDD_3G";
    public static final String KEY_FDD_CHANNEL = "Channel_FDD_3G";
    public static final String KEY_FDD_POWER = "Power_FDD_3G";
    public static final String KEY_TDD_ANT_MODE = "Power_TDD_ant_mode";
    public static final String KEY_TDD_ANT_STATUS = "Power_TDD_ant_status";
    public static final String KEY_TDD_BAND = "Band_TDD_3G";
    public static final String KEY_TDD_CHANNEL = "Channel_TDD_3G";
    public static final String KEY_TDD_POWER = "Power_TDD_3G";
    public static final String TAG = "RfDesense/TxTestTd";
    private static int mModemType;
    /* access modifiers changed from: private */
    public String[] mBandValues;

    /* access modifiers changed from: private */
    public void saveState(String command) {
        SharedPreferences.Editor pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        String str = "1";
        if (mModemType == 2) {
            pref.putString(RfDesenseTxTest.KEY_TDSCDMA_ATCMD, command);
            pref.putInt(KEY_TDD_BAND, this.mBand.getSelectedItemPosition());
            pref.putString(KEY_TDD_CHANNEL, this.mChannel.getText());
            pref.putString(KEY_TDD_POWER, this.mPower.getText());
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            pref.putString(KEY_TDD_ANT_MODE, str);
            pref.putString(KEY_TDD_ANT_STATUS, this.mAntStatus.getText().toString());
            String ant_str = "AT+ETXANT=1,2," + this.mAntStatus.getText() + ",,0";
            if (this.mAntMode.isChecked()) {
                pref.putString(RfDesenseTxTest.KEY_TDSCDMA_ATCMD_ANT_SWITCH, ant_str);
            } else {
                pref.putString(RfDesenseTxTest.KEY_TDSCDMA_ATCMD_ANT_SWITCH, "AT+ETXANT=0,2,0,,0");
            }
        } else {
            pref.putString(RfDesenseTxTest.KEY_WCDMA_ATCMD, command);
            pref.putInt(KEY_FDD_BAND, this.mBand.getSelectedItemPosition());
            pref.putString(KEY_FDD_CHANNEL, this.mChannel.getText());
            pref.putString(KEY_FDD_POWER, this.mPower.getText());
            if (!this.mAntMode.isChecked()) {
                str = "0";
            }
            pref.putString(KEY_FDD_ANT_MODE, str);
            pref.putString(KEY_FDD_ANT_STATUS, this.mAntStatus.getText().toString());
            String ant_str2 = "AT+ETXANT=1,2," + this.mAntStatus.getText();
            if (this.mAntMode.isChecked()) {
                pref.putString(RfDesenseTxTest.KEY_WCDMA_ATCMD_ANT_SWITCH, ant_str2);
            } else {
                pref.putString(RfDesenseTxTest.KEY_WCDMA_ATCMD_ANT_SWITCH, "AT+ETXANT=0,2,0");
            }
        }
        pref.apply();
    }

    public void onCreate(Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> adapter;
        super.onCreate(savedInstanceState);
        mModemType = getIntent().getIntExtra("mModemType", 1);
        findViewById(R.id.afc).setVisibility(8);
        findViewById(R.id.tsc).setVisibility(8);
        findViewById(R.id.pattern).setVisibility(8);
        this.mAfc.editor.setVisibility(8);
        this.mTsc.editor.setVisibility(8);
        this.mPattern.setVisibility(8);
        ((TextView) findViewById(R.id.channel)).setText(R.string.rf_desense_channel_3g);
        if (mModemType == 2) {
            this.mBandValues = getResources().getStringArray(R.array.rf_desense_tx_test_td_band_values);
        } else {
            this.mBandValues = getResources().getStringArray(R.array.rf_desense_tx_test_fd_band_values);
        }
        this.mButtonSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_set:
                        if (RfDesenseTxTestTd.this.checkValues()) {
                            String band = RfDesenseTxTestTd.this.mBandValues[RfDesenseTxTestTd.this.mBand.getSelectedItemPosition()];
                            String channel = RfDesenseTxTestTd.this.mChannel.getText();
                            String power = RfDesenseTxTestTd.this.mPower.getText();
                            RfDesenseTxTestTd.this.saveState("AT+ERFTX=0,0," + band + "," + channel + "," + power);
                            RfDesenseTxTestTd.this.showToast("Set param suecceed!");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
        if (mModemType == 2) {
            adapter = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_td_band, 17367048);
        } else {
            adapter = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_fd_band, 17367048);
        }
        adapter.setDropDownViewResource(17367049);
        this.mBand.setAdapter(adapter);
        restoreState();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        if (mModemType == 2) {
            this.mCurrentBand = pref.getInt(KEY_TDD_BAND, 0);
            this.mBand.setSelection(this.mCurrentBand);
            updateLimits();
            this.mChannel.setText(pref.getString(KEY_TDD_CHANNEL, this.mChannel.defaultValue));
            this.mPower.setText(pref.getString(KEY_TDD_POWER, this.mPower.defaultValue));
            this.mAntMode.setChecked(pref.getString(KEY_TDD_ANT_MODE, "0").equals("1"));
            this.mAntStatus.setText(pref.getString(KEY_TDD_ANT_STATUS, "0"));
            return;
        }
        this.mCurrentBand = pref.getInt(KEY_FDD_BAND, 0);
        this.mBand.setSelection(this.mCurrentBand);
        updateLimits();
        this.mChannel.setText(pref.getString(KEY_FDD_CHANNEL, this.mChannel.defaultValue));
        this.mPower.setText(pref.getString(KEY_FDD_POWER, this.mPower.defaultValue));
        this.mAntMode.setChecked(pref.getString(KEY_FDD_ANT_MODE, "0").equals("1"));
        this.mAntStatus.setText(pref.getString(KEY_FDD_ANT_STATUS, "0"));
    }

    /* access modifiers changed from: protected */
    public void updateLimits() {
        String[] limits;
        int band = this.mBand.getSelectedItemPosition();
        if (mModemType == 2) {
            limits = getResources().getStringArray(R.array.rf_desense_tx_test_td_limits)[band].split(",");
        } else {
            limits = getResources().getStringArray(R.array.rf_desense_tx_test_fd_limits)[band].split(",");
        }
        String defaults = "";
        try {
            int min = Integer.valueOf(limits[1]).intValue();
            int max = Integer.valueOf(limits[2]).intValue();
            defaults = ((min + max) / 2) + "";
        } catch (Exception e) {
            e.getMessage();
        }
        this.mChannel.set(defaults, limits[1], limits[2], limits[3], limits[4]);
        this.mPower.set(limits[5], limits[6], limits[7]);
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
        this.mToast = Toast.makeText(this, msg, 1);
        this.mToast.show();
    }
}
