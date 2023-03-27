package com.mediatek.engineermode.rfdesense;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class RfDesenseTxTestGsm extends RfDesenseTxTestBase {
    public static final String KEY_AFC = "AFC_2G";
    public static final String KEY_ANT_MODE = "gsm_ant_mode";
    public static final String KEY_ANT_STATUS = "gsm_ant_status";
    public static final String KEY_BAND = "Band_2G";
    public static final String KEY_CHANNEL = "Channel_2G";
    public static final String KEY_MODULATION = "Bodulation_2G";
    public static final String KEY_PATTERN = "Pattern_2G";
    public static final String KEY_POWER = "Power_2G";
    public static final String KEY_TSC = "TSC_2G";
    public static final String TAG = "RfDesense/TxTestGsm";

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        this.mCurrentBand = pref.getInt(KEY_BAND, 0);
        this.mBand.setSelection(this.mCurrentBand);
        updateLimits();
        this.mChannel.setText(pref.getString(KEY_CHANNEL, this.mChannel.defaultValue));
        this.mPower.setText(pref.getString(KEY_POWER, this.mPower.defaultValue));
        this.mAfc.setText(pref.getString(KEY_AFC, this.mAfc.defaultValue));
        this.mTsc.setText(pref.getString(KEY_TSC, this.mTsc.defaultValue));
        this.mPattern.setSelection(pref.getInt(KEY_PATTERN, 0));
        this.mAntMode.setChecked(pref.getString(KEY_ANT_MODE, "0").equals("1"));
        this.mAntStatus.setText(pref.getString(KEY_ANT_STATUS, "0"));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<CharSequence> adapter_band = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_gsm_band, 17367048);
        adapter_band.setDropDownViewResource(17367049);
        this.mBand.setAdapter(adapter_band);
        ArrayAdapter<CharSequence> adapter_pattern = ArrayAdapter.createFromResource(this, R.array.rf_desense_tx_test_gsm_pattern, 17367048);
        adapter_pattern.setDropDownViewResource(17367049);
        this.mPattern.setAdapter(adapter_pattern);
        restoreState();
        Elog.i(TAG, "restoreState GSM");
        this.mDbm.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void updateLimits() {
        String[] limits = getResources().getStringArray(R.array.rf_desense_tx_test_gsm_gmsk_limits)[this.mBand.getSelectedItemPosition()].split(",");
        this.mChannel.set(limits[0], limits[1], limits[2], limits[3], limits[4]);
        this.mPower.set(limits[5], limits[6], limits[7]);
        this.mPower.step = 1;
        this.mAfc.set("4100", "0", "8191");
        this.mTsc.set("0", "0", "7");
    }
}
