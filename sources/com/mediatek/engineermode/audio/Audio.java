package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class Audio extends Activity implements AdapterView.OnItemClickListener {
    private static final String AUDIO_LOGGER_NAME = "Audio Logger";
    private static final String AUDIO_TURNING_VER = "MTK_AUDIO_TUNING_TOOL_VERSION";
    private static final String AUDIO_TURNING_VER_V1 = "V1";
    private static final String AUDIO_TURNING_VER_V2_1 = "V2.1";
    private static final String AUDIO_TURNING_VER_V2_2 = "V2.2";
    public static final String AUDIO_VERSION_1 = "GET_AUDIO_VOLUME_VERSION=1";
    public static final String AUDIO_VERSION_COMMAND = "GET_AUDIO_VOLUME_VERSION";
    public static final String CURRENT_MODE = "CurrentMode";
    private static final String DEBUG_INFO_NAME = "Debug Info";
    private static final String DEBUG_SESSION_NAME = "Debug Session";
    public static final String ENHANCE_MODE = "is_enhance";
    private static final String HEADSET_LOUDSPEAKER_MODE_NAME = "Headset_LoudSpeaker Mode";
    private static final String HEADSET_MODE_NAME = "Headset Mode";
    private static final String LOUDSPEAKER_MODE_NAME = "LoudSpeaker Mode";
    private static final String NORMAL_MODE_NAME = "Normal Mode";
    private static final String SPEECH_ENHANCE_NAME = "Speech Enhancement";
    private static final String SPEECH_LOGGER_NAME = "Speech Logger";
    public static final String TAG = "Audio";
    private static final String VOLUME_NAME = "Volume";
    private static AudioTuningVer sAudioTuningVer = AudioTuningVer.UNKNOWN;
    private ListView mAudioListView;
    private boolean mIsVolumeEnhancementMode = true;
    private ArrayList<String> mListData;

    enum AudioTuningVer {
        UNKNOWN,
        VER_1,
        VER_2_1,
        VER_2_2
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);
        initAudioTunVer();
        initVolumeModeVer();
        initListItem();
    }

    private static void initAudioTunVer() {
        String value = AudioTuningJni.getFeatureValue(AUDIO_TURNING_VER);
        Elog.d(TAG, "initAudioTurnVer:" + value);
        sAudioTuningVer = AudioTuningVer.VER_1;
        if (value != null) {
            if (AUDIO_TURNING_VER_V2_1.equalsIgnoreCase(value)) {
                sAudioTuningVer = AudioTuningVer.VER_2_1;
            }
            if (AUDIO_TURNING_VER_V2_2.equalsIgnoreCase(value)) {
                sAudioTuningVer = AudioTuningVer.VER_2_2;
                if (AudioTuningJni.registerXmlChangedCallback()) {
                    Elog.d(TAG, "registerXmlChangedCallback OK!");
                } else {
                    Elog.d(TAG, "registerXmlChangedCallback failed!");
                }
            }
        }
        Elog.d(TAG, "sAudioTuningVer:" + sAudioTuningVer);
    }

    private void initVolumeModeVer() {
        String version = AudioSystem.getParameters(AUDIO_VERSION_COMMAND);
        Elog.d(TAG, "initVolumeModeVer:" + version);
        if (!AUDIO_VERSION_1.equals(version)) {
            this.mIsVolumeEnhancementMode = false;
        }
    }

    private void initListItem() {
        this.mListData = new ArrayList<>();
        if (sAudioTuningVer != AudioTuningVer.VER_2_2) {
            this.mListData.add(NORMAL_MODE_NAME);
            this.mListData.add(HEADSET_MODE_NAME);
            this.mListData.add(LOUDSPEAKER_MODE_NAME);
            if (this.mIsVolumeEnhancementMode) {
                this.mListData.add(HEADSET_LOUDSPEAKER_MODE_NAME);
            }
        } else {
            this.mListData.add(VOLUME_NAME);
        }
        this.mListData.add(SPEECH_ENHANCE_NAME);
        if (sAudioTuningVer == AudioTuningVer.VER_1) {
            this.mListData.add(DEBUG_INFO_NAME);
        }
        this.mListData.add(DEBUG_SESSION_NAME);
        this.mListData.add(SPEECH_LOGGER_NAME);
        this.mListData.add(AUDIO_LOGGER_NAME);
        this.mAudioListView = (ListView) findViewById(R.id.ListView_Audio);
        this.mAudioListView.setAdapter(new ArrayAdapter<>(this, 17367043, this.mListData));
        this.mAudioListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        String itemName = this.mListData.get(arg2);
        Intent intent = new Intent();
        if (NORMAL_MODE_NAME.equalsIgnoreCase(itemName) || HEADSET_MODE_NAME.equalsIgnoreCase(itemName) || LOUDSPEAKER_MODE_NAME.equalsIgnoreCase(itemName) || HEADSET_LOUDSPEAKER_MODE_NAME.equalsIgnoreCase(itemName)) {
            intent.setClass(this, AudioModeSetting.class);
            intent.putExtra(CURRENT_MODE, arg2);
            intent.putExtra(ENHANCE_MODE, this.mIsVolumeEnhancementMode);
        } else if (VOLUME_NAME.equalsIgnoreCase(itemName)) {
            intent.setClass(this, AudioVolume.class);
        } else if (SPEECH_ENHANCE_NAME.equalsIgnoreCase(itemName)) {
            if (sAudioTuningVer == AudioTuningVer.VER_1) {
                intent.setClass(this, AudioSpeechEnhancementV1.class);
            } else {
                intent.setClass(this, AudioSpeechEnhancementV2.class);
            }
        } else if (DEBUG_INFO_NAME.equalsIgnoreCase(itemName)) {
            intent.setClass(this, AudioDebugInfo.class);
        } else if (DEBUG_SESSION_NAME.equalsIgnoreCase(itemName)) {
            intent.setClass(this, AudioDebugSession.class);
        } else if (SPEECH_LOGGER_NAME.equalsIgnoreCase(itemName)) {
            if (sAudioTuningVer == AudioTuningVer.VER_1) {
                intent.setClass(this, AudioSpeechLoggerXV1.class);
            } else {
                intent.setClass(this, AudioSpeechLoggerXV2.class);
            }
        } else if (AUDIO_LOGGER_NAME.equalsIgnoreCase(itemName)) {
            intent.setClass(this, AudioAudioLogger.class);
        } else {
            return;
        }
        startActivity(intent);
    }

    static AudioTuningVer getAudioTuningVer() {
        if (AudioTuningVer.UNKNOWN == sAudioTuningVer) {
            initAudioTunVer();
        }
        return sAudioTuningVer;
    }
}
