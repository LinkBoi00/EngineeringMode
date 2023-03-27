package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class AudioVolumeTypeScene {
    private static final String CATEGORY_SPEECHVOL = "SpeechVol";
    private static final String PARAM2 = "Scene,%1$s,%2$s";
    public static final String TAG = "Audio/VolumnTypeScene";
    private static final String TYPE_SCENE = "Scene";
    /* access modifiers changed from: private */
    public String mCurrentScene;
    private boolean mIsSupportScene;
    /* access modifiers changed from: private */
    public Listener mListener;
    private Spinner mSceneSpinner;

    public interface Listener {
        void onSceneChanged();
    }

    AudioVolumeTypeScene(Listener listener) {
        this.mListener = listener;
    }

    public boolean initSceneSpinner(Activity activity, String category) {
        this.mSceneSpinner = (Spinner) activity.findViewById(R.id.audio_volume_scene_spinner);
        String strSpinnerScene = AudioTuningJni.getCategory(category, TYPE_SCENE);
        Elog.d(TAG, "strSpinnerScene:" + strSpinnerScene);
        if (strSpinnerScene == null) {
            this.mIsSupportScene = false;
            return false;
        }
        String[] value = strSpinnerScene.split(",");
        int length = value.length / 2;
        if (length <= 0) {
            this.mIsSupportScene = false;
            return false;
        }
        String[] arraySpinner = new String[length];
        final String[] mArrayValue = new String[length];
        for (int k = 0; k < length; k++) {
            mArrayValue[k] = value[k * 2];
            arraySpinner[k] = value[(k * 2) + 1];
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<>(activity, 17367048, arraySpinner);
        adatper.setDropDownViewResource(17367049);
        this.mSceneSpinner.setAdapter(adatper);
        this.mCurrentScene = mArrayValue[0];
        this.mSceneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                String access$000 = AudioVolumeTypeScene.this.mCurrentScene;
                String[] strArr = mArrayValue;
                if (access$000 != strArr[arg2]) {
                    String unused = AudioVolumeTypeScene.this.mCurrentScene = strArr[arg2];
                    Elog.d(AudioVolumeTypeScene.TAG, "onSceneChanged");
                    AudioVolumeTypeScene.this.mListener.onSceneChanged();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSceneSpinner.setVisibility(0);
        this.mIsSupportScene = true;
        return true;
    }

    public String getPara2String(String para2) {
        if (!this.mIsSupportScene) {
            return para2;
        }
        return String.format(PARAM2, new Object[]{this.mCurrentScene, para2});
    }
}
