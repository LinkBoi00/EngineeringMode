package com.mediatek.engineermode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;

public class MobileDataPreferred extends PreferenceActivity {
    private static final String DATA_PREFER_KEY = "data_prefer_key";
    private static final int MOBILE_DATA_PREF_DIALOG = 10;
    private static final int PCH_CALL_PREFER = 1;
    private static final int PCH_DATA_PREFER = 0;
    private static final String TAG = "CallDataPreferred";
    /* access modifiers changed from: private */
    public CheckBoxPreference mMobileDataPref;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.gsm_umts_options);
        this.mMobileDataPref = (CheckBoxPreference) getPreferenceScreen().findPreference(DATA_PREFER_KEY);
        boolean z = false;
        int pchFlag = SystemProperties.getInt("persist.vendor.radio.gprs.prefer", 0);
        Elog.v(TAG, "Orgin value persist.vendor.radio.gprs.prefer = " + pchFlag);
        CheckBoxPreference checkBoxPreference = this.mMobileDataPref;
        if (pchFlag != 0) {
            z = true;
        }
        checkBoxPreference.setChecked(z);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (id == 10) {
            return new AlertDialog.Builder(this).setTitle(17039380).setCancelable(false).setIcon(17301543).setMessage(R.string.pch_data_prefer_message).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MobileDataPreferred.this.setGprsTransferType(0);
                }
            }).setNegativeButton(17039369, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MobileDataPreferred.this.mMobileDataPref.setChecked(false);
                }
            }).create();
        }
        return super.onCreateDialog(id);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (!this.mMobileDataPref.equals(preference)) {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        if (this.mMobileDataPref.isChecked()) {
            showDialog(10);
        } else {
            setGprsTransferType(1);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void setGprsTransferType(int type) {
        String property = type == 0 ? "1" : "0";
        Elog.v(TAG, "Change persist.radio.gprs.prefer to " + property);
        try {
            EmUtils.getEmHidlService().setPreferGprsMode(property);
        } catch (Exception e) {
            e.printStackTrace();
            Elog.e(TAG, "set property failed ...");
        }
        for (int i = 0; i < TelephonyManager.getDefault().getPhoneCount(); i++) {
            EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+EGTP=" + type, ""}, (Message) null);
            EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+EMPPCH=" + type, ""}, (Message) null);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        Elog.i(TAG, "fragmentName is " + fragmentName);
        return false;
    }
}
