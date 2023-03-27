package com.mediatek.engineermode.misc;

import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiscConfig extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CMD_QUERY = "AT+ECFGGET=\"sms_over_sgs\"";
    private static final String CMD_SET = "AT+ECFGSET=\"sms_over_sgs\"";
    public static final String FK_MTK_MISC_SELF_REG_CONFIG = "persist.vendor.radio.selfreg";
    public static final String FK_MTK_MISC_UCE_SUPPORT = "persist.vendor.mtk_uce_support";
    public static final String FK_MTK_MISC_VIBRATE_CONFIG = "persist.vendor.radio.telecom.vibrate";
    private static final int MSG_QUERY = 0;
    private static final int MSG_QUERY_HVOLTE = 2;
    private static final int MSG_QUERY_SLIENT = 3;
    private static final int MSG_QUERY_STATUS_1X_TIME = 7;
    private static final int MSG_QUERY_VOLTE_HYS = 4;
    private static final int MSG_SET = 1;
    private static final int MSG_SET_DISABLE_1X_TIME = 5;
    private static final int MSG_SET_ENABLE_1X_TIME = 6;
    private static final String TAG = "MiscConfig";
    private static final String VALUE_DISABLE = "0";
    private static final String VALUE_ENABLE = "1";
    private static final String[] mATCmdValue9192md = {"AT+EIMSVOICE=0", "AT+EIMSVOICE=1"};
    private static final String[] mATCmdValue93md = {"AT+EIMSCFG=0,0,0,0,1,1", "AT+EIMSCFG=1,0,0,0,1,1"};
    private static final String[] mEGCMDEntries = {"Disable", "Enable"};
    private static final String[] mEGCMDEntriesValue = {"\"00\"", "\"01\""};
    private static final String[] mEntries = {"bSRLTE", "hVolte"};
    private static final String[] mEntriesValue = {"1", "3"};
    private AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public CheckBoxPreference mDisable1XTime;
    /* access modifiers changed from: private */
    public ListPreference mHVolteDeviceModePreference;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (msg.what == 0) {
                if (ar.exception != null || ar.result == null) {
                    EmUtils.showToast("Query failed");
                    return;
                }
                String[] data = (String[]) ar.result;
                if (data.length > 0 && data[0] != null) {
                    MiscConfig.this.parseSmsSgsValue(data[0]);
                }
            } else if (msg.what == 1) {
                if (ar.exception == null) {
                    EmUtils.showToast("Set successful");
                    Elog.v(MiscConfig.TAG, "Set successful");
                    return;
                }
                EmUtils.showToast("Set failed");
                Elog.v(MiscConfig.TAG, "Set failed");
            } else if (msg.what == 2) {
                if (ar.exception == null && ar.result != null) {
                    String[] data2 = (String[]) ar.result;
                    if (data2.length > 0 && data2[0] != null) {
                        String hVolte = "0";
                        try {
                            hVolte = data2[0].replace(" ", "").substring("+CEVDP:".length());
                        } catch (Exception e) {
                            Elog.v(MiscConfig.TAG, "CEVDP failed ");
                        }
                        Elog.v(MiscConfig.TAG, "hVolte = " + hVolte);
                        MiscConfig.this.mHVolteDeviceModePreference.setValue(hVolte);
                        if (MiscConfig.this.mHVolteDeviceModePreference.getEntry() != null) {
                            Elog.d(MiscConfig.TAG, "mHVolteDeviceModePreference.getEntry() = " + MiscConfig.this.mHVolteDeviceModePreference.getEntry());
                            MiscConfig.this.mHVolteDeviceModePreference.setSummary(MiscConfig.this.mHVolteDeviceModePreference.getEntry());
                            return;
                        }
                        Elog.d(MiscConfig.TAG, "mHVolteDeviceModePreference.getEntry() = null");
                    }
                }
            } else if (msg.what == 3) {
                if (ar.exception == null && ar.result != null) {
                    String[] data3 = (String[]) ar.result;
                    if (data3.length > 0 && data3[0] != null) {
                        Elog.v(MiscConfig.TAG, "EHVOLTE data[0] = " + data3[0]);
                        String slient = "0";
                        try {
                            slient = data3[0].replace(" ", "").substring("+EHVOLTE:4,".length());
                        } catch (Exception e2) {
                            Elog.v(MiscConfig.TAG, "+EHVOLTE: failed ");
                        }
                        Elog.v(MiscConfig.TAG, "EHVOLTE data = " + slient);
                        MiscConfig.this.mSilentRedialPreference.setChecked(slient.equals("1"));
                    }
                }
            } else if (msg.what == 4) {
                if (ar.exception == null && ar.result != null) {
                    String[] data4 = (String[]) ar.result;
                    if (data4.length > 0 && data4[0] != null) {
                        String volteHys = "0";
                        Elog.v(MiscConfig.TAG, "volteHys data[0] = " + data4[0]);
                        try {
                            volteHys = data4[0].replace(" ", "").substring("+EVZWT:11,".length());
                        } catch (Exception e3) {
                            Elog.v(MiscConfig.TAG, "+EVZWT:11 failed ");
                        }
                        Elog.v(MiscConfig.TAG, "volteHys data = " + volteHys);
                        MiscConfig.this.mTVolteHysPreference.setText(volteHys);
                        EditTextPreference access$300 = MiscConfig.this.mTVolteHysPreference;
                        access$300.setSummary(MiscConfig.this.getString(R.string.misc_config_tvolte) + " : " + volteHys);
                    }
                }
            } else if (msg.what == 5) {
                if (ar.exception == null) {
                    EmUtils.showToast("Disable_Time_REG successful.", 0);
                } else {
                    EmUtils.showToast("Disable_Time_REG failed.", 0);
                }
            } else if (msg.what == 6) {
                if (ar.exception == null) {
                    EmUtils.showToast("Enable_Time_REG successful.", 0);
                } else {
                    EmUtils.showToast("Enable_Time_REG failed.", 0);
                }
            } else if (msg.what != 7) {
            } else {
                if (ar.exception == null) {
                    String[] mReturnData = (String[]) ar.result;
                    if (mReturnData.length > 0) {
                        Elog.d(MiscConfig.TAG, "close 1x mReturnData = " + mReturnData[0]);
                        String result = "";
                        try {
                            result = mReturnData[0].split(",")[1];
                        } catch (Exception e4) {
                            Elog.e(MiscConfig.TAG, e4.getMessage());
                            Elog.e(MiscConfig.TAG, "mReturnData error");
                        }
                        Elog.d(MiscConfig.TAG, "result = " + result);
                        if (result.equals("0")) {
                            MiscConfig.this.mDisable1XTime.setChecked(true);
                        } else {
                            MiscConfig.this.mDisable1XTime.setChecked(false);
                        }
                    }
                } else {
                    Elog.d(MiscConfig.TAG, "quary MSG_QUERY_STATUS_TIME_REG failed.");
                }
            }
        }
    };
    private Preference mPresence;
    private CheckBoxPreference mSelfRegPreference;
    /* access modifiers changed from: private */
    public CheckBoxPreference mSilentRedialPreference;
    private CheckBoxPreference mSmsSgsPreference;
    private CheckBoxPreference mSpeakerPreference;
    /* access modifiers changed from: private */
    public EditTextPreference mTVolteHysPreference;
    private ListPreference mVdmImsReconfigPreference;
    private CheckBoxPreference mVibratePreference;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.misc);
        this.mAudioManager = (AudioManager) getSystemService(AudioManager.class);
        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
        this.mSpeakerPreference = checkBoxPreference;
        checkBoxPreference.setPersistent(false);
        this.mSpeakerPreference.setTitle(getString(R.string.misc_config_speaker));
        getPreferenceScreen().addPreference(this.mSpeakerPreference);
        CheckBoxPreference checkBoxPreference2 = new CheckBoxPreference(this);
        this.mVibratePreference = checkBoxPreference2;
        checkBoxPreference2.setTitle(getString(R.string.misc_config_vibrate));
        this.mVibratePreference.setPersistent(false);
        getPreferenceScreen().addPreference(this.mVibratePreference);
        if (FeatureSupport.isSupported(FeatureSupport.FK_CT4GREG_APP)) {
            CheckBoxPreference checkBoxPreference3 = new CheckBoxPreference(this);
            this.mSelfRegPreference = checkBoxPreference3;
            checkBoxPreference3.setTitle(getString(R.string.misc_config_selfreg));
            this.mSelfRegPreference.setPersistent(false);
            getPreferenceScreen().addPreference(this.mSelfRegPreference);
        } else {
            Elog.d(TAG, "Not show entry for CT4GREG.");
        }
        CheckBoxPreference checkBoxPreference4 = new CheckBoxPreference(this);
        this.mSmsSgsPreference = checkBoxPreference4;
        checkBoxPreference4.setTitle(getString(R.string.misc_config_sgs));
        this.mSmsSgsPreference.setPersistent(false);
        getPreferenceScreen().addPreference(this.mSmsSgsPreference);
        if (ModemCategory.isCdma()) {
            CheckBoxPreference checkBoxPreference5 = new CheckBoxPreference(this);
            this.mDisable1XTime = checkBoxPreference5;
            checkBoxPreference5.setTitle(getString(R.string.misc_config_disable_1X_time));
            this.mDisable1XTime.setPersistent(false);
            getPreferenceScreen().addPreference(this.mDisable1XTime);
        }
        if (FeatureSupport.getProperty(FK_MTK_MISC_UCE_SUPPORT).equals("1")) {
            Elog.d(TAG, "init presence");
            Preference preference = new Preference(this);
            this.mPresence = preference;
            preference.setTitle(getString(R.string.presence));
            this.mPresence.setPersistent(false);
            getPreferenceScreen().addPreference(this.mPresence);
        }
        if (!FeatureSupport.is90Modem() && !FeatureSupport.is3GOnlyModem()) {
            ListPreference listPreference = new ListPreference(this);
            this.mHVolteDeviceModePreference = listPreference;
            listPreference.setTitle(getString(R.string.misc_config_hvolte));
            this.mHVolteDeviceModePreference.setPersistent(false);
            this.mHVolteDeviceModePreference.setEntries(mEntries);
            this.mHVolteDeviceModePreference.setEntryValues(mEntriesValue);
            this.mHVolteDeviceModePreference.setOnPreferenceChangeListener(this);
            getPreferenceScreen().addPreference(this.mHVolteDeviceModePreference);
            CheckBoxPreference checkBoxPreference6 = new CheckBoxPreference(this);
            this.mSilentRedialPreference = checkBoxPreference6;
            checkBoxPreference6.setTitle(getString(R.string.misc_config_silent));
            this.mSilentRedialPreference.setPersistent(false);
            getPreferenceScreen().addPreference(this.mSilentRedialPreference);
            EditTextPreference editTextPreference = new EditTextPreference(this);
            this.mTVolteHysPreference = editTextPreference;
            editTextPreference.setTitle(getString(R.string.misc_config_tvolte));
            this.mTVolteHysPreference.setPersistent(false);
            this.mTVolteHysPreference.setOnPreferenceChangeListener(this);
            getPreferenceScreen().addPreference(this.mTVolteHysPreference);
            ListPreference listPreference2 = new ListPreference(this);
            this.mVdmImsReconfigPreference = listPreference2;
            listPreference2.setTitle(getString(R.string.misc_config_vdm));
            this.mVdmImsReconfigPreference.setPersistent(true);
            this.mVdmImsReconfigPreference.setEntries(mEGCMDEntries);
            this.mVdmImsReconfigPreference.setEntryValues(mEGCMDEntriesValue);
            this.mVdmImsReconfigPreference.setOnPreferenceChangeListener(this);
            getPreferenceScreen().addPreference(this.mVdmImsReconfigPreference);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (FeatureSupport.isSupported(FeatureSupport.FK_CT4GREG_APP)) {
            querySelfRegValue();
        }
        querySpeakerValue();
        queryVibrateValue();
        querySmsSgsValue();
        if (!FeatureSupport.is90Modem() && !FeatureSupport.is3GOnlyModem()) {
            queryVolteValue();
        }
        if (!FeatureSupport.is90Modem() && ModemCategory.isCdma()) {
            query1XTimeStatus(7);
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String str;
        String str2;
        String config;
        CheckBoxPreference checkBoxPreference = this.mSelfRegPreference;
        String enable = "1";
        if (preference == checkBoxPreference) {
            boolean isChecked = checkBoxPreference.isChecked();
            String config2 = getSelfRegConfig();
            if (isChecked) {
                config = config2.charAt(0) + enable;
            } else {
                config = config2.charAt(0) + "0";
            }
            Elog.i(TAG, "set self reg config value :" + config);
            EmUtils.systemPropertySet(FK_MTK_MISC_SELF_REG_CONFIG, config);
        }
        CheckBoxPreference checkBoxPreference2 = this.mSpeakerPreference;
        if (preference == checkBoxPreference2) {
            this.mAudioManager.setSpeakerphoneOn(checkBoxPreference2.isChecked());
        }
        CheckBoxPreference checkBoxPreference3 = this.mVibratePreference;
        if (preference == checkBoxPreference3) {
            boolean sFlag = checkBoxPreference3.isChecked();
            Elog.i(TAG, "set VibrateValue flag is :" + sFlag);
            if (sFlag) {
                str2 = enable;
            } else {
                str2 = "0";
            }
            EmUtils.systemPropertySet(FK_MTK_MISC_VIBRATE_CONFIG, str2);
        }
        CheckBoxPreference checkBoxPreference4 = this.mSmsSgsPreference;
        if (preference == checkBoxPreference4) {
            if (checkBoxPreference4.isChecked()) {
                str = enable;
            } else {
                str = "0";
            }
            setSgsValue(str);
        }
        CheckBoxPreference checkBoxPreference5 = this.mDisable1XTime;
        if (preference == checkBoxPreference5) {
            if (checkBoxPreference5.isChecked()) {
                set1XTime(0, 5);
            } else {
                set1XTime(1, 6);
            }
        }
        if (preference == this.mPresence) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.mediatek.engineermode.misc.PresenceActivity");
            startActivity(intent);
        }
        CheckBoxPreference checkBoxPreference6 = this.mSilentRedialPreference;
        if (preference == checkBoxPreference6) {
            if (!checkBoxPreference6.isChecked()) {
                enable = "0";
            }
            sendATCommand(new String[]{"AT+EHVOLTE=4," + enable, ""}, 1);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        ListPreference listPreference = this.mHVolteDeviceModePreference;
        if (preference == listPreference) {
            int index = listPreference.findIndexOfValue((String) objValue);
            Elog.v(TAG, "mHVolteDeviceModePreference index is :" + index);
            String value = "";
            if (FeatureSupport.is93ModemAndAbove()) {
                value = mATCmdValue93md[index];
            } else if (FeatureSupport.is92Modem() || FeatureSupport.is91Modem()) {
                value = mATCmdValue9192md[index];
            }
            sendATCommand(new String[]{value, ""}, 1);
            sendATCommand(new String[]{"AT+CEVDP=" + mEntriesValue[index], ""}, 1);
            this.mHVolteDeviceModePreference.setSummary(mEntries[index]);
        }
        ListPreference listPreference2 = this.mVdmImsReconfigPreference;
        if (preference == listPreference2) {
            int index2 = listPreference2.findIndexOfValue((String) objValue);
            Elog.v(TAG, "mVdmImsReconfigPreference index is :" + index2);
            sendATCommand(new String[]{"AT+EGCMD=499,0," + mEGCMDEntriesValue[index2], ""}, 1);
            this.mVdmImsReconfigPreference.setSummary(mEGCMDEntries[index2]);
        }
        if (preference == this.mTVolteHysPreference) {
            String values = (String) objValue;
            Elog.d(TAG, "Volte Hys value = " + values);
            sendATCommand(new String[]{"AT+EVZWT=1,11," + values, ""}, 1);
            if (this.mTVolteHysPreference.getText() != null) {
                EditTextPreference editTextPreference = this.mTVolteHysPreference;
                editTextPreference.setSummary(getString(R.string.misc_config_tvolte) + " : " + values);
            } else {
                Elog.d(TAG, "mTVolteHysPreference.getText() = null");
            }
        }
        return true;
    }

    private void querySpeakerValue() {
        if (this.mAudioManager.isSpeakerphoneOn()) {
            this.mSpeakerPreference.setChecked(true);
        } else {
            this.mSpeakerPreference.setChecked(false);
        }
    }

    private void queryVibrateValue() {
        String sFlag = EmUtils.systemPropertyGet(FK_MTK_MISC_VIBRATE_CONFIG, "1");
        Elog.v(TAG, "queryVibrateValue flag is :" + sFlag);
        if (sFlag.equals("0")) {
            this.mVibratePreference.setChecked(false);
        } else {
            this.mVibratePreference.setChecked(true);
        }
    }

    private void querySelfRegValue() {
        if (getSelfRegConfig().charAt(1) == '1') {
            this.mSelfRegPreference.setChecked(true);
        } else {
            this.mSelfRegPreference.setChecked(false);
        }
    }

    private String getSelfRegConfig() {
        String config = EmUtils.systemPropertyGet(FK_MTK_MISC_SELF_REG_CONFIG, "11");
        if (!config.equals("11") && !config.equals("10") && !config.equals("01") && !config.equals("00")) {
            config = "11";
        }
        Elog.i(TAG, "persist.vendor.radio.selfreg: " + config);
        return config;
    }

    private void queryVolteValue() {
        queryHVolteDeviceMode();
        querySilentRedialMode();
        queryTVolteHys();
    }

    private void queryHVolteDeviceMode() {
        sendATCommand(new String[]{"AT+CEVDP?", "+CEVDP:"}, 2);
    }

    private void querySilentRedialMode() {
        sendATCommand(new String[]{"AT+EHVOLTE=4,2", "+EHVOLTE:"}, 3);
    }

    private void queryTVolteHys() {
        sendATCommand(new String[]{"AT+EVZWT=0,11", "+EVZWT:"}, 4);
    }

    private void set1XTime(int command, int msg) {
        String[] cmd = new String[3];
        StringBuilder sb = new StringBuilder();
        sb.append("AT+ECREGTYPE=0,");
        sb.append(command == 1 ? 1 : 0);
        cmd[0] = sb.toString();
        cmd[1] = "";
        cmd[2] = "DESTRILD:C2K";
        String[] cmd_s = ModemCategory.getCdmaCmdArr(cmd);
        Elog.d(TAG, "set1XTime AT command: " + cmd_s[0] + ",cmd_s.length = " + cmd_s.length);
        EmUtils.invokeOemRilRequestStringsEm(true, cmd_s, this.mHandler.obtainMessage(msg));
    }

    private void query1XTimeStatus(int msg) {
        String[] cmd_s = ModemCategory.getCdmaCmdArr(new String[]{"AT+ECREGTYPE=0", "+ECREGTYPE:", "DESTRILD:C2K"});
        Elog.d(TAG, "query1XTimeStatus: " + cmd_s[0] + ",cmd_s.length = " + cmd_s.length);
        EmUtils.invokeOemRilRequestStringsEm(true, cmd_s, this.mHandler.obtainMessage(msg));
    }

    private void querySmsSgsValue() {
        EmUtils.invokeOemRilRequestStringsEm(new String[]{CMD_QUERY, "+ECFGGET:"}, this.mHandler.obtainMessage(0));
        Elog.i(TAG, "send AT+ECFGGET=\"sms_over_sgs\"");
    }

    private void setSgsValue(String value) {
        Message msg = this.mHandler.obtainMessage(1);
        EmUtils.invokeOemRilRequestStringsEm(new String[]{"AT+ECFGSET=\"sms_over_sgs\",\"" + value + "\"", ""}, msg);
        Elog.i(TAG, "send AT+ECFGSET=\"sms_over_sgs\",\"" + value + "\"");
    }

    /* access modifiers changed from: private */
    public void parseSmsSgsValue(String data) {
        this.mSmsSgsPreference.setChecked("1".equals(parseCommandResponse(data)));
    }

    private String parseCommandResponse(String data) {
        Elog.d(TAG, "reply data: " + data);
        Matcher m = Pattern.compile("\\+ECFGGET:\\s*\".*\"\\s*,\\s*\"(.*)\"").matcher(data);
        if (m.find()) {
            String value = m.group(1);
            Elog.d(TAG, "sms over sgs support value: " + value);
            return value;
        }
        Elog.e(TAG, "wrong format: " + data);
        EmUtils.showToast("wrong format: " + data);
        return "";
    }

    private void sendATCommand(String[] atCommand, int msg) {
        Elog.d(TAG, "send at cmd : " + atCommand[0]);
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mHandler.obtainMessage(msg));
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        Elog.i(TAG, "fragmentName is " + fragmentName);
        return false;
    }
}
