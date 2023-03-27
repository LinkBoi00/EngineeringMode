package com.mediatek.engineermode;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class DeviceRegister extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_SMS_REGISTER_SWITCH = "ct_sms_register_switch";
    private static final String PROPERTY_KEY_SMSREG = "persist.vendor.radio.selfreg";
    private static final String TAG = "DeviceRegister";
    private static final String TURN_OFF = "Off";
    private static final String TURN_ON = "On";
    private ListPreference mListPreference;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.device_register);
        ListPreference listPreference = (ListPreference) findPreference(KEY_SMS_REGISTER_SWITCH);
        this.mListPreference = listPreference;
        listPreference.setOnPreferenceChangeListener(this);
        int savedCTAValue = getSavedCTAValue();
        this.mListPreference.setSummary(savedCTAValue == 1 ? TURN_ON : TURN_OFF);
        this.mListPreference.setValue(String.valueOf(savedCTAValue));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals(KEY_SMS_REGISTER_SWITCH)) {
            try {
                setCTAValue((String) newValue);
            } catch (NumberFormatException e) {
                Elog.e(TAG, "setCTAValue NumberFormatException");
            }
            boolean z = true;
            if (getSavedCTAValue() != 1) {
                z = false;
            }
            boolean isEnabled = z;
            this.mListPreference.setValue(isEnabled ? "1" : "0");
            this.mListPreference.setSummary(isEnabled ? TURN_ON : TURN_OFF);
        }
        return false;
    }

    private int getSavedCTAValue() {
        if (getSelfRegConfig().charAt(0) == '1') {
            return 1;
        }
        return 0;
    }

    private void setCTAValue(String cta) {
        String newString = cta + getSelfRegConfig().charAt(1);
        SystemProperties.set("persist.vendor.radio.selfreg", newString);
        Elog.i(TAG, "save CTA [" + newString + "]");
    }

    private String getSelfRegConfig() {
        String config = SystemProperties.get("persist.vendor.radio.selfreg", "11");
        if (!config.equals("11") && !config.equals("10") && !config.equals("01") && !config.equals("00")) {
            config = "11";
        }
        Elog.i(TAG, "persist.vendor.radio.selfreg: " + config);
        return config;
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        Elog.i(TAG, "fragmentName is " + fragmentName);
        return false;
    }
}
