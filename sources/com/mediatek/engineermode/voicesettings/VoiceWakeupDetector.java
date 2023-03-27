package com.mediatek.engineermode.voicesettings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.AudioTuningJni;

public class VoiceWakeupDetector extends Activity implements View.OnClickListener {
    private static final String CATEGORY = "VoWHwVad";
    private static final int DETECTOR_PAR_COUNT = 10;
    private static final String KEY_PRE = "Par_";
    private static final String TAG = "VoiceWakeupDetector";
    private static final String TYPE = "VAD,common";
    private Button mBtnSet;
    private Spinner[] mSpnParArr = new Spinner[10];

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_wakeup_detector);
        initUiComponent();
    }

    private void initUiComponent() {
        int maxIdx;
        int[] spnIds = {R.id.voice_detector_par01, R.id.voice_detector_par02, R.id.voice_detector_par03, R.id.voice_detector_par04, R.id.voice_detector_par05, R.id.voice_detector_par06, R.id.voice_detector_par07, R.id.voice_detector_par08, R.id.voice_detector_par09, R.id.voice_detector_par10};
        for (int i = 0; i < 10; i++) {
            Spinner spn = (Spinner) findViewById(spnIds[i]);
            this.mSpnParArr[i] = spn;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 17367048);
            adapter.setDropDownViewResource(17367049);
            if (i <= 4) {
                maxIdx = 15;
            } else {
                maxIdx = 7;
            }
            for (int j = 0; j <= maxIdx; j++) {
                adapter.add(String.valueOf(j));
            }
            spn.setAdapter(adapter);
        }
        Button button = (Button) findViewById(R.id.voice_detector_set_btn);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        initUiByData();
    }

    private void initUiByData() {
        for (int i = 0; i < this.mSpnParArr.length; i++) {
            int val = getValue(i);
            this.mSpnParArr[i].setSelection(val);
            Elog.i(TAG, "val:" + val);
        }
    }

    private void setDetectorSetting() {
        int i = 0;
        while (true) {
            Spinner[] spinnerArr = this.mSpnParArr;
            if (i < spinnerArr.length) {
                int selection = spinnerArr[i].getSelectedItemPosition();
                String strKey = KEY_PRE + String.format("%02d", new Object[]{Integer.valueOf(i + 1)});
                Elog.i(TAG, "strKey:" + strKey);
                AudioTuningJni.setParams(CATEGORY, TYPE, strKey, String.valueOf(selection));
                i++;
            } else {
                AudioTuningJni.saveToWork(CATEGORY);
                return;
            }
        }
    }

    private int getValue(int index) {
        String strKey = KEY_PRE + String.format("%02d", new Object[]{Integer.valueOf(index + 1)});
        Elog.i(TAG, "strKey:" + strKey);
        String result = AudioTuningJni.getParams(CATEGORY, TYPE, strKey);
        Elog.i(TAG, "result:" + result);
        return Integer.parseInt(result);
    }

    private boolean checkSetResult() {
        int i = 0;
        while (true) {
            Spinner[] spinnerArr = this.mSpnParArr;
            if (i >= spinnerArr.length) {
                return true;
            }
            if (spinnerArr[i].getSelectedItemPosition() != getValue(i)) {
                return false;
            }
            i++;
        }
    }

    public void onClick(View view) {
        int msgid;
        if (view == this.mBtnSet) {
            setDetectorSetting();
            if (checkSetResult()) {
                msgid = R.string.voice_set_success_msg;
            } else {
                msgid = R.string.voice_set_fail_msg;
            }
            Toast.makeText(this, msgid, 0).show();
        }
    }
}
