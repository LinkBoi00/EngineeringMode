package com.mediatek.engineermode.voicesettings;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.AudioTuningJni;
import com.mediatek.provider.MtkSettingsExt;

public class VoiceWakeupRecognition extends Activity implements View.OnClickListener {
    private static final String CATEGORY = "VoWHwVad";
    private static final String KEY_PRE_KR = "vow_KR";
    private static final String KEY_PRE_KRSR = "vow_KRSR";
    private static final int RECOGNITION_CLEAN = 1;
    private static final int RECOGNITION_NOISY = 2;
    private static final int RECOGNITION_TESTING = 4;
    private static final int RECOGNITION_TRAINING = 3;
    private static final String TAG = "VoiceWakeupRecognition";
    private static final String TYPE = "VAD,common";
    private static final int WAKEUP_MODE_KEYWORD = 1;
    private static final int WAKEUP_MODE_KEYWORD_SPEAKER = 2;
    private static final int WAKEUP_MODE_TRIGGER_KEYWORD = 3;
    private Button mBtnSet;
    private Spinner mSpnClean;
    private Spinner mSpnNoisy;
    private Spinner mSpnTesting;
    private Spinner mSpnTraining;
    private int mWakeupMode = 0;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_common_setting);
        int wakeupMode = Settings.System.getInt(getContentResolver(), MtkSettingsExt.System.VOICE_WAKEUP_MODE, 0);
        int i = wakeupMode == 3 ? 1 : wakeupMode;
        this.mWakeupMode = i;
        if (i == 1 || i == 2) {
            initUiComponent();
            return;
        }
        Toast.makeText(this, "Invalid voice wake-up mode:" + this.mWakeupMode, 0).show();
        finish();
    }

    private void initUiComponent() {
        int i = this.mWakeupMode;
        if (i == 1) {
            setTitle(R.string.voice_wakeup_switch_1);
        } else if (i == 2) {
            setTitle(R.string.voice_wakeup_switch_2);
        }
        this.mSpnClean = (Spinner) findViewById(R.id.voice_settings_clean_spn);
        this.mSpnNoisy = (Spinner) findViewById(R.id.voice_settings_noisy_spn);
        this.mSpnTraining = (Spinner) findViewById(R.id.voice_settings_training_spn);
        this.mSpnTesting = (Spinner) findViewById(R.id.voice_settings_testing_spn);
        ArrayAdapter<String> adapter = createEmptySpinnerAdapter();
        for (int i2 = 0; i2 <= 15; i2++) {
            adapter.add(String.valueOf(i2));
        }
        this.mSpnClean.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = createEmptySpinnerAdapter();
        for (int i3 = 0; i3 <= 15; i3++) {
            adapter2.add(String.valueOf(i3));
        }
        this.mSpnNoisy.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = createEmptySpinnerAdapter();
        for (int i4 = 0; i4 <= 7; i4++) {
            adapter3.add(String.valueOf(i4));
        }
        this.mSpnTraining.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = createEmptySpinnerAdapter();
        for (int i5 = 0; i5 <= 7; i5++) {
            adapter4.add(String.valueOf(i5));
        }
        this.mSpnTesting.setAdapter(adapter4);
        Button button = (Button) findViewById(R.id.voice_settings_set_btn);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        initUiByData();
    }

    private void initUiByData() {
        this.mSpnClean.setSelection(getValue(1));
        this.mSpnNoisy.setSelection(getValue(2));
        this.mSpnTraining.setSelection(getValue(3));
        this.mSpnTesting.setSelection(getValue(4));
    }

    private int getValue(int index) {
        String key;
        Elog.i(TAG, "index:" + index);
        if (this.mWakeupMode == 1) {
            key = KEY_PRE_KR + index;
        } else {
            key = KEY_PRE_KRSR + index;
        }
        Elog.i(TAG, "key:" + key);
        String result = AudioTuningJni.getParams(CATEGORY, TYPE, key);
        Elog.i(TAG, "result" + result);
        return Integer.parseInt(result);
    }

    private ArrayAdapter<String> createEmptySpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 17367048);
        adapter.setDropDownViewResource(17367049);
        return adapter;
    }

    private void setRecognitionSetting(int clean, int noisy, int training, int testing) {
        String strPreKey = this.mWakeupMode == 1 ? KEY_PRE_KR : KEY_PRE_KRSR;
        Elog.i(TAG, "setRecognitionSetting:" + strPreKey + " strPreKey," + clean + "," + noisy + "," + training + "," + testing);
        StringBuilder sb = new StringBuilder();
        sb.append(strPreKey);
        sb.append(1);
        AudioTuningJni.setParams(CATEGORY, TYPE, sb.toString(), String.valueOf(clean));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strPreKey);
        sb2.append(2);
        AudioTuningJni.setParams(CATEGORY, TYPE, sb2.toString(), String.valueOf(noisy));
        AudioTuningJni.setParams(CATEGORY, TYPE, strPreKey + 3, String.valueOf(training));
        AudioTuningJni.setParams(CATEGORY, TYPE, strPreKey + 4, String.valueOf(testing));
        AudioTuningJni.saveToWork(CATEGORY);
    }

    private boolean checkSetResult() {
        int clean = this.mSpnClean.getSelectedItemPosition();
        int noisy = this.mSpnNoisy.getSelectedItemPosition();
        int training = this.mSpnTraining.getSelectedItemPosition();
        int testing = this.mSpnTesting.getSelectedItemPosition();
        int clean2 = getValue(1);
        int noisy2 = getValue(2);
        int training2 = getValue(3);
        int testing2 = getValue(4);
        if (clean == clean2 && noisy == noisy2 && training == training2 && testing == testing2) {
            return true;
        }
        return false;
    }

    public void onClick(View view) {
        int msgid;
        if (view == this.mBtnSet) {
            setRecognitionSetting(this.mSpnClean.getSelectedItemPosition(), this.mSpnNoisy.getSelectedItemPosition(), this.mSpnTraining.getSelectedItemPosition(), this.mSpnTesting.getSelectedItemPosition());
            if (checkSetResult()) {
                msgid = R.string.voice_set_success_msg;
            } else {
                msgid = R.string.voice_set_fail_msg;
            }
            Toast.makeText(this, msgid, 0).show();
        }
    }
}
