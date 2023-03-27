package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioSystem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class AudioSpeechLoggerXV1 extends Activity {
    private static final String ANC_DOWN_SAMPLE = "downSample";
    private static final String ANC_NO_DOWN_SAMPLE = "noDownSample";
    public static final String ANC_STATUS = "anc_status";
    private static final int CONSTANT_0XFF = 255;
    private static final int CONSTANT_256 = 256;
    private static final int DATA_SIZE = 1444;
    private static final int DIALOG_GET_DATA_ERROR = 0;
    private static final int DIALOG_ID_NO_SDCARD = 1;
    private static final int DIALOG_ID_SDCARD_BUSY = 2;
    private static final int ENABLE_ANC_DOWN_SAMPLE = 1;
    private static final int ENABLE_ANC_NO_DOWN_SAMPLE = 0;
    public static final String ENGINEER_MODE_PREFERENCE = "engineermode_audiolog_preferences";
    public static final String EPL_STATUS = "epl_status";
    private static final int GET_DUMP_AP_SPEECH_EPL = 161;
    private static final int GET_SPEECH_ANC_LOG_STATUS = 181;
    private static final int GET_SPEECH_ANC_SUPPORT = 176;
    private static final int SET_DUMP_AP_SPEECH_EPL = 160;
    private static final int SET_DUMP_SPEECH_DEBUG_INFO = 97;
    private static final int SET_SPEECH_ANC_DISABLE = 180;
    private static final int SET_SPEECH_ANC_LOG_STATUS = 179;
    private static final int SET_SPEECH_VM_ENABLE = 96;
    public static final String TAG = "Audio/SpeechLogger1";
    private static final int VM_LOG_POS = 1440;
    /* access modifiers changed from: private */
    public CheckBox mCbAncLogger;
    /* access modifiers changed from: private */
    public CheckBox mCbCtm4Way;
    /* access modifiers changed from: private */
    public CheckBox mCbEplDebug;
    /* access modifiers changed from: private */
    public CheckBox mCbMagiConf;
    /* access modifiers changed from: private */
    public CheckBox mCbSpeechLogger;
    private final CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
            SharedPreferences.Editor edit = AudioSpeechLoggerXV1.this.getSharedPreferences("engineermode_audiolog_preferences", 0).edit();
            if (buttonView.equals(AudioSpeechLoggerXV1.this.mCbSpeechLogger)) {
                AudioSpeechLoggerXV1.this.onClickSpeechLogger(edit, checked);
            } else if (buttonView.equals(AudioSpeechLoggerXV1.this.mCbCtm4Way)) {
                if (checked) {
                    byte[] access$300 = AudioSpeechLoggerXV1.this.mData;
                    access$300[AudioSpeechLoggerXV1.VM_LOG_POS] = (byte) (access$300[AudioSpeechLoggerXV1.VM_LOG_POS] | 2);
                    AudioSpeechLoggerXV1.access$476(AudioSpeechLoggerXV1.this, 2);
                    Elog.d(AudioSpeechLoggerXV1.TAG, "E mVmLogState " + AudioSpeechLoggerXV1.this.mVmLogState);
                } else {
                    byte[] access$3002 = AudioSpeechLoggerXV1.this.mData;
                    access$3002[AudioSpeechLoggerXV1.VM_LOG_POS] = (byte) (access$3002[AudioSpeechLoggerXV1.VM_LOG_POS] & -3);
                    AudioSpeechLoggerXV1.access$472(AudioSpeechLoggerXV1.this, -3);
                    Elog.d(AudioSpeechLoggerXV1.TAG, "D mVmLogState " + AudioSpeechLoggerXV1.this.mVmLogState);
                }
                int index = AudioTuningJni.setEmParameter(AudioSpeechLoggerXV1.this.mData, AudioSpeechLoggerXV1.DATA_SIZE);
                if (index != 0 && index != -38) {
                    Elog.i(AudioSpeechLoggerXV1.TAG, "set CTM4WAY parameter failed");
                    Toast.makeText(AudioSpeechLoggerXV1.this, R.string.set_failed_tip, 1).show();
                }
            } else if (buttonView.equals(AudioSpeechLoggerXV1.this.mCbEplDebug)) {
                if (checked) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCKEPLDebug checked");
                    AudioTuningJni.setAudioCommand(160, 1);
                    return;
                }
                Elog.d(AudioSpeechLoggerXV1.TAG, "mCKEPLDebug Unchecked");
                AudioTuningJni.setAudioCommand(160, 0);
            } else if (buttonView.equals(AudioSpeechLoggerXV1.this.mRbEpl)) {
                if (checked) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCKBEPL checked");
                    int ret = AudioTuningJni.setAudioCommand(96, 1);
                    AudioTuningJni.getEmParameter(AudioSpeechLoggerXV1.this.mData, AudioSpeechLoggerXV1.DATA_SIZE);
                    if (ret == -1) {
                        Elog.i(AudioSpeechLoggerXV1.TAG, "set mCKBEPL parameter failed");
                        Toast.makeText(AudioSpeechLoggerXV1.this, R.string.set_failed_tip, 1).show();
                    }
                    edit.putInt("epl_status", 1);
                    edit.commit();
                    return;
                }
                Elog.d(AudioSpeechLoggerXV1.TAG, "mCKBEPL unchecked");
            } else if (buttonView.equals(AudioSpeechLoggerXV1.this.mRbNormalVm)) {
                if (checked) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCKBNormalVm checked");
                    if (AudioSpeechLoggerXV1.this.mForRefresh) {
                        boolean unused = AudioSpeechLoggerXV1.this.mForRefresh = false;
                        return;
                    }
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCKBNormalVm checked ok");
                    int ret2 = AudioTuningJni.setAudioCommand(96, 0);
                    AudioTuningJni.getEmParameter(AudioSpeechLoggerXV1.this.mData, AudioSpeechLoggerXV1.DATA_SIZE);
                    if (ret2 == -1) {
                        Elog.i(AudioSpeechLoggerXV1.TAG, "set mCKBNormalVm parameter failed");
                        Toast.makeText(AudioSpeechLoggerXV1.this, R.string.set_failed_tip, 1).show();
                    }
                    edit.putInt("epl_status", 0);
                    edit.commit();
                    return;
                }
                Elog.d(AudioSpeechLoggerXV1.TAG, "mCKBNormalVm unchecked");
            } else if (buttonView.equals(AudioSpeechLoggerXV1.this.mCbAncLogger)) {
                for (int i = 0; i < AudioSpeechLoggerXV1.this.mRgAnc.getChildCount(); i++) {
                    AudioSpeechLoggerXV1.this.mRgAnc.getChildAt(i).setEnabled(checked);
                }
                if (checked) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCbSpeechLogger checked");
                    int audioCommand = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV1.SET_SPEECH_ANC_LOG_STATUS, 1);
                    AudioSpeechLoggerXV1.this.mRgAnc.check(R.id.Audio_AncLogger_Down);
                } else {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCbSpeechLogger unchecked");
                    AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV1.SET_SPEECH_ANC_DISABLE, 0);
                }
                edit.putString("anc_status", AudioSpeechLoggerXV1.ANC_DOWN_SAMPLE);
                edit.commit();
            } else if (!buttonView.equals(AudioSpeechLoggerXV1.this.mCbMagiConf)) {
            } else {
                if (checked) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "mCbMagiConf checked");
                    AudioSystem.setParameters("SET_MAGI_CONFERENCE_ENABLE=1");
                    return;
                }
                Elog.d(AudioSpeechLoggerXV1.TAG, "mCbMagiConf Unchecked");
                AudioSystem.setParameters("SET_MAGI_CONFERENCE_ENABLE=0");
            }
        }
    };
    /* access modifiers changed from: private */
    public byte[] mData;
    /* access modifiers changed from: private */
    public boolean mForRefresh = false;
    /* access modifiers changed from: private */
    public RadioButton mRbEpl;
    /* access modifiers changed from: private */
    public RadioButton mRbNormalVm;
    /* access modifiers changed from: private */
    public RadioGroup mRgAnc;
    private final RadioGroup.OnCheckedChangeListener mRgCheckedListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.equals(AudioSpeechLoggerXV1.this.mRgAnc)) {
                SharedPreferences.Editor edit = AudioSpeechLoggerXV1.this.getSharedPreferences("engineermode_audiolog_preferences", 0).edit();
                if (R.id.Audio_AncLogger_Down == checkedId) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "Audio_AncLogger_Down checked");
                    int audioCommand = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV1.SET_SPEECH_ANC_LOG_STATUS, 1);
                    edit.putString("anc_status", AudioSpeechLoggerXV1.ANC_DOWN_SAMPLE);
                    edit.commit();
                } else if (R.id.Audio_AncLogger_NoDown == checkedId) {
                    Elog.d(AudioSpeechLoggerXV1.TAG, "Audio_SpeechLogger_NoDown checked");
                    int audioCommand2 = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV1.SET_SPEECH_ANC_LOG_STATUS, 0);
                    edit.putString("anc_status", AudioSpeechLoggerXV1.ANC_NO_DOWN_SAMPLE);
                    edit.commit();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mVmLogState = 0;

    static /* synthetic */ int access$472(AudioSpeechLoggerXV1 x0, int x1) {
        int i = x0.mVmLogState & x1;
        x0.mVmLogState = i;
        return i;
    }

    static /* synthetic */ int access$476(AudioSpeechLoggerXV1 x0, int x1) {
        int i = x0.mVmLogState | x1;
        x0.mVmLogState = i;
        return i;
    }

    /* access modifiers changed from: private */
    public void onClickSpeechLogger(SharedPreferences.Editor edit, boolean checked) {
        if (checked) {
            Elog.d(TAG, "mCbSpeechLogger checked");
            this.mRbEpl.setEnabled(true);
            this.mRbNormalVm.setEnabled(true);
            this.mForRefresh = true;
            this.mRbNormalVm.setChecked(true);
            this.mRbEpl.setChecked(true);
            byte[] bArr = this.mData;
            bArr[VM_LOG_POS] = (byte) (bArr[VM_LOG_POS] | 1);
            int index = AudioTuningJni.setEmParameter(bArr, DATA_SIZE);
            if (index != 0 && index != -38) {
                Elog.i(TAG, "set mAutoVM parameter failed");
                Toast.makeText(this, R.string.set_failed_tip, 1).show();
                return;
            }
            return;
        }
        Elog.d(TAG, "mCbSpeechLogger unchecked");
        if (this.mRbEpl.isChecked()) {
            this.mRbEpl.setChecked(false);
        }
        if (this.mRbNormalVm.isChecked()) {
            this.mRbNormalVm.setChecked(false);
        }
        this.mRbEpl.setEnabled(false);
        this.mRbNormalVm.setEnabled(false);
        AudioTuningJni.getEmParameter(this.mData, DATA_SIZE);
        edit.putInt("epl_status", 0);
        edit.commit();
        byte[] bArr2 = this.mData;
        bArr2[VM_LOG_POS] = (byte) (bArr2[VM_LOG_POS] & -2);
        int index2 = AudioTuningJni.setEmParameter(bArr2, DATA_SIZE);
        if (index2 != 0 && index2 != -38) {
            Elog.i(TAG, "set mAutoVM parameter failed");
            Toast.makeText(this, R.string.set_failed_tip, 1).show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_speechloggerx);
        this.mCbSpeechLogger = (CheckBox) findViewById(R.id.Audio_SpeechLogger_Enable);
        this.mCbEplDebug = (CheckBox) findViewById(R.id.Audio_EPLDebug_Enable);
        this.mCbCtm4Way = (CheckBox) findViewById(R.id.Audio_CTM4WAYLogger_Enable);
        this.mCbAncLogger = (CheckBox) findViewById(R.id.Audio_AncLogger_Enable);
        this.mCbMagiConf = (CheckBox) findViewById(R.id.Audio_MagiConference_Enable);
        this.mRbEpl = (RadioButton) findViewById(R.id.Audio_SpeechLogger_EPL);
        this.mRbNormalVm = (RadioButton) findViewById(R.id.Audio_SpeechLogger_Normalvm);
        this.mRgAnc = (RadioGroup) findViewById(R.id.RadioGroup2);
        Button dumpSpeechInfo = (Button) findViewById(R.id.Dump_Speech_DbgInfo);
        View spliteView = findViewById(R.id.Audio_View1);
        TextView ctm4WayText = (TextView) findViewById(R.id.Audio_CTM4WAYLogger_EnableText);
        View spliteView3 = findViewById(R.id.Audio_View3);
        TextView ancText = (TextView) findViewById(R.id.Audio_AncLogger_FileText);
        if (!AudioTuningJni.isFeatureSupported("MTK_TTY_SUPPORT")) {
            this.mCbCtm4Way.setVisibility(8);
            ctm4WayText.setVisibility(8);
            spliteView.setVisibility(8);
        }
        if (AudioTuningJni.getAudioCommand(GET_SPEECH_ANC_SUPPORT) == 0) {
            ancText.setVisibility(8);
            this.mCbAncLogger.setVisibility(8);
            this.mRgAnc.setVisibility(8);
            spliteView3.setVisibility(8);
        } else {
            initAncStatus();
        }
        String magiSupport = AudioSystem.getParameters("GET_MAGI_CONFERENCE_SUPPORT");
        Elog.i(TAG, "Get Magi support " + magiSupport);
        String[] split = magiSupport.split("=");
        findViewById(R.id.Audio_View2).setVisibility(8);
        findViewById(R.id.Audio_MagiConference_EnableText).setVisibility(8);
        this.mCbMagiConf.setVisibility(8);
        checkStatus();
        this.mCbSpeechLogger.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCbEplDebug.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCbCtm4Way.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCbAncLogger.setOnCheckedChangeListener(this.mCheckedListener);
        this.mCbMagiConf.setOnCheckedChangeListener(this.mCheckedListener);
        this.mRbEpl.setOnCheckedChangeListener(this.mCheckedListener);
        this.mRbNormalVm.setOnCheckedChangeListener(this.mCheckedListener);
        this.mRgAnc.setOnCheckedChangeListener(this.mRgCheckedListener);
        dumpSpeechInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(AudioSpeechLoggerXV1.TAG, "On Click mDumpSpeechInfo button.");
                if (AudioTuningJni.setAudioCommand(97, 1) == -1) {
                    Elog.i(AudioSpeechLoggerXV1.TAG, "set mDumpSpeechInfo parameter failed");
                    Toast.makeText(AudioSpeechLoggerXV1.this, R.string.set_failed_tip, 1).show();
                    return;
                }
                Toast.makeText(AudioSpeechLoggerXV1.this, R.string.set_success_tip, 1).show();
            }
        });
    }

    private void checkStatus() {
        int eplStatus = getSharedPreferences("engineermode_audiolog_preferences", 0).getInt("epl_status", 1);
        byte[] bArr = new byte[DATA_SIZE];
        this.mData = bArr;
        int ret = AudioTuningJni.getEmParameter(bArr, DATA_SIZE);
        if (ret != 0) {
            showDialog(0);
            Elog.i(TAG, "Audio_SpeechLogger GetEMParameter return value is : " + ret);
        }
        byte[] bArr2 = this.mData;
        this.mVmLogState = shortToInt(bArr2[VM_LOG_POS], bArr2[1441]);
        Elog.i(TAG, "Audio_SpeechLogger GetEMParameter return value is : " + this.mVmLogState);
        if ((this.mVmLogState & 1) == 0) {
            this.mCbSpeechLogger.setChecked(false);
            this.mRbEpl.setEnabled(false);
            this.mRbNormalVm.setEnabled(false);
            this.mRbEpl.setChecked(false);
            this.mRbNormalVm.setChecked(false);
        } else {
            this.mCbSpeechLogger.setChecked(true);
            this.mRbEpl.setEnabled(true);
            this.mRbNormalVm.setEnabled(true);
            if (eplStatus == 1) {
                this.mRbEpl.setChecked(true);
            } else {
                this.mRbNormalVm.setChecked(true);
            }
        }
        if ((this.mVmLogState & 2) == 0) {
            this.mCbCtm4Way.setChecked(false);
        } else {
            this.mCbCtm4Way.setChecked(true);
        }
        int epl = AudioTuningJni.getAudioCommand(GET_DUMP_AP_SPEECH_EPL);
        Elog.i(TAG, "Get EPL setting: " + epl);
        if (epl == 1) {
            this.mCbEplDebug.setChecked(true);
        } else {
            this.mCbEplDebug.setChecked(false);
        }
    }

    private void initAncStatus() {
        SharedPreferences preferences = getSharedPreferences("engineermode_audiolog_preferences", 0);
        int anc = AudioTuningJni.getAudioCommand(GET_SPEECH_ANC_LOG_STATUS);
        Elog.i(TAG, "Get ANC setting: " + anc);
        if (anc == 1) {
            this.mCbAncLogger.setChecked(true);
            String ancStatus = preferences.getString("anc_status", ANC_DOWN_SAMPLE);
            Elog.i(TAG, "Get ANC status: " + ancStatus);
            if (ancStatus.equals(ANC_DOWN_SAMPLE)) {
                this.mRgAnc.check(R.id.Audio_AncLogger_Down);
            } else {
                this.mRgAnc.check(R.id.Audio_AncLogger_NoDown);
            }
        } else {
            this.mCbAncLogger.setChecked(false);
            for (int i = 0; i < this.mRgAnc.getChildCount(); i++) {
                this.mRgAnc.getChildAt(i).setEnabled(false);
            }
        }
    }

    private int shortToInt(byte low, byte high) {
        return (((high + 256) & 255) * 256) + ((low + 256) & 255);
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechLoggerXV1.this.finish();
                    }
                }).create();
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.no_sdcard_title).setMessage(R.string.no_sdcard_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.sdcard_busy_title).setMessage(R.string.sdcard_busy_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }
}
