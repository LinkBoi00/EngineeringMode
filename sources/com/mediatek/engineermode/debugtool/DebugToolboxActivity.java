package com.mediatek.engineermode.debugtool;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.debugtool.AEEControlService;

public class DebugToolboxActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String TAG = "DebugToolboxActivity/Debugutils";
    private static final String sAEE_BUILD_INFO = "ro.vendor.aee.build.info";
    private static final String sAEE_MODE = "persist.vendor.mtk.aee.mode";
    private static final String[] sAEE_MODE_STRING = {"", "MediatekEngineer", "MediatekUser", "CustomerEngineer", "CustomerUser"};
    private static final String[] sDAL_OPTION_STRING = {"EnableDAL", "DisableDAL"};
    private static final String sDAL_SETTING = "persist.vendor.mtk.aee.dal";
    /* access modifiers changed from: private */
    public AEEControlService mBoundService;
    PreferenceScreen mCleanData;
    PreferenceScreen mClearDAL;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            AEEControlService unused = DebugToolboxActivity.this.mBoundService = ((AEEControlService.LocalBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            AEEControlService unused = DebugToolboxActivity.this.mBoundService = null;
        }
    };
    private boolean mIsBound;
    ListPreference mPrefAeemode;
    ListPreference mPrefDaloption;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_debugtoolbox);
        this.mPrefAeemode = (ListPreference) getPreferenceScreen().findPreference(getString(R.string.preference_aeemode));
        String aeebuildinfo = getProperty(sAEE_BUILD_INFO);
        if (aeebuildinfo == null || !aeebuildinfo.equals("mtk")) {
            this.mPrefAeemode.setEntries(R.array.entries_list_preference_aeemode_oem);
            this.mPrefAeemode.setEntryValues(R.array.entryvalues_list_preference_aeemode_oem);
        }
        String deviceAEEmode = currentAEEMode();
        this.mPrefAeemode.setValue(deviceAEEmode);
        this.mPrefAeemode.setSummary(deviceAEEmode);
        this.mPrefDaloption = (ListPreference) getPreferenceScreen().findPreference(getString(R.string.preference_dal_setting));
        String deviceDalOption = currentDalOption();
        this.mPrefDaloption.setValue(deviceDalOption);
        this.mPrefDaloption.setSummary(deviceDalOption);
        if (aeebuildinfo == null || !aeebuildinfo.equals("mtk")) {
            this.mPrefDaloption.setSelectable(false);
        }
        PreferenceScreen preferenceScreen = (PreferenceScreen) getPreferenceScreen().findPreference(getString(R.string.preference_cleardal));
        this.mClearDAL = preferenceScreen;
        preferenceScreen.setOnPreferenceClickListener(this);
        PreferenceScreen preferenceScreen2 = (PreferenceScreen) getPreferenceScreen().findPreference(getString(R.string.preference_cleandata));
        this.mCleanData = preferenceScreen2;
        preferenceScreen2.setOnPreferenceClickListener(this);
        if (!Build.TYPE.equals(FeatureSupport.ENG_LOAD)) {
            this.mCleanData.setEnabled(false);
        }
        getPreferenceScreen().removePreference((PreferenceCategory) getPreferenceScreen().findPreference(getString(R.string.preferences_device_maintain)));
        doBindService();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.preference_aeemode))) {
            String modevalue = sharedPreferences.getString(key, (String) null);
            if (modevalue == null) {
                Elog.e(TAG, "AEE working mode is set to NULL.");
                return;
            }
            try {
                this.mBoundService.changeAEEMode(modevalue);
            } catch (NullPointerException e) {
                Elog.e(TAG, "AEE Service is not started");
            }
            String deviceAEEmode = currentAEEMode();
            if (!deviceAEEmode.equals(modevalue)) {
                this.mPrefAeemode.setValue(deviceAEEmode);
                String errorString = "Change debug level [" + deviceAEEmode + "-->" + modevalue + "] too fequent, please retry later.";
                Elog.e(TAG, errorString);
                Toast.makeText(this, errorString, 0).show();
            }
            this.mPrefAeemode.setSummary(deviceAEEmode);
        }
        if (key.equals(getString(R.string.preference_dal_setting))) {
            String dalOption = sharedPreferences.getString(key, (String) null);
            if (dalOption == null) {
                Elog.d(TAG, "DAL setting mode is set to NULL.");
                return;
            }
            try {
                this.mBoundService.dalSetting(dalOption);
            } catch (NullPointerException e2) {
                Elog.e(TAG, "DAL setting error");
            }
            String deviceDalOption = currentDalOption();
            if (!deviceDalOption.equals(dalOption)) {
                this.mPrefDaloption.setValue(deviceDalOption);
            }
            this.mPrefDaloption.setSummary(deviceDalOption);
        }
    }

    public boolean onPreferenceClick(Preference preference) {
        if (preference.equals(this.mClearDAL)) {
            try {
                this.mBoundService.clearDAL();
                return true;
            } catch (NullPointerException e) {
                Elog.e(TAG, "AEE Service is not started");
                return false;
            }
        } else if (!preference.equals(this.mCleanData)) {
            return false;
        } else {
            try {
                this.mBoundService.cleanData();
                return true;
            } catch (NullPointerException e2) {
                Elog.e(TAG, "AEE Service is not started");
                return false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void doBindService() {
        bindService(new Intent(this, AEEControlService.class), this.mConnection, 1);
        this.mIsBound = true;
    }

    /* access modifiers changed from: package-private */
    public void doUnbindService() {
        if (this.mIsBound) {
            unbindService(this.mConnection);
            this.mIsBound = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private static String currentAEEMode() {
        int aeemodeIndex;
        String aeemodeIndexString = getProperty(sAEE_MODE);
        int aeebuildinfoInt = 4;
        if (aeemodeIndexString != null && !aeemodeIndexString.isEmpty() && (aeemodeIndex = Integer.valueOf(aeemodeIndexString).intValue()) >= 1 && aeemodeIndex <= 4) {
            return sAEE_MODE_STRING[aeemodeIndex];
        }
        if (getProperty(sAEE_BUILD_INFO).equals("mtk")) {
            aeebuildinfoInt = 2;
        }
        return sAEE_MODE_STRING[aeebuildinfoInt - ((int) Build.TYPE.equals(FeatureSupport.ENG_LOAD))];
    }

    private static String currentDalOption() {
        String dalOptionIndexString = getProperty(sDAL_SETTING);
        if (dalOptionIndexString == null || dalOptionIndexString.isEmpty()) {
            return "";
        }
        int dalOptionIndex = Integer.valueOf(dalOptionIndexString).intValue();
        if (dalOptionIndex == 0) {
            return sDAL_OPTION_STRING[1];
        }
        if (dalOptionIndex == 1) {
            return sDAL_OPTION_STRING[0];
        }
        return "";
    }

    private static String getProperty(String prop) {
        return SystemProperties.get(prop);
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        return false;
    }
}
