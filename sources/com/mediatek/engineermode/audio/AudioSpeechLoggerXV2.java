package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.Audio;
import com.mediatek.engineermode.audio.CopyHalDumpService;

public class AudioSpeechLoggerXV2 extends Activity {
    private static final String ANC_DOWN_SAMPLE = "downSample";
    private static final String ANC_NO_DOWN_SAMPLE = "noDownSample";
    public static final String ANC_STATUS = "anc_status";
    private static final String CMD_GET_DEBUG_INFO = "APP_GET_PARAM=SpeechGeneral#CategoryLayer,%s#debug_info";
    private static final String CMD_GET_PARAMETER = "APP_GET_PARAM=SpeechGeneral#CategoryLayer,%s#speech_common_para";
    private static final String CMD_GET_VMLOG_STATE = "GET_VMLOG_CONFIG";
    private static final String CMD_PREFIX = "APP_GET_PARAM=";
    private static final String CMD_SET_COMMON_PARAMETER = "APP_SET_PARAM=SpeechGeneral#CategoryLayer,Common#speech_common_para#";
    private static final String CMD_SET_DEBUG_INFO = "APP_SET_PARAM=SpeechGeneral#CategoryLayer,Common#debug_info#";
    public static final String CMD_SET_VMLOG_STATE = "SET_VMLOG_CONFIG=";
    private static final int CONSTANT_0XFF = 255;
    private static final int CONSTANT_256 = 256;
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int DATA_SIZE = 1444;
    private static final int DIALOG_GET_DATA_ERROR = 0;
    private static final int DIALOG_ID_DUMP_PATH_CHANGE = 10;
    private static final int DIALOG_ID_NO_SDCARD = 1;
    private static final int DIALOG_ID_SDCARD_BUSY = 2;
    private static final int ENABLE_ANC_DOWN_SAMPLE = 1;
    private static final int ENABLE_ANC_NO_DOWN_SAMPLE = 0;
    public static final String ENGINEER_MODE_PREFERENCE = "engineermode_audiolog_preferences";
    public static final String EPL_STATUS = "epl_status";
    private static final int GET_DUMP_AP_SPEECH_EPL = 161;
    private static final int GET_SPEECH_ANC_LOG_STATUS = 181;
    private static final int GET_SPEECH_ANC_SUPPORT = 176;
    private static final String PARAM_DEVIDER = "#";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    private static final int SET_DUMP_AP_SPEECH_EPL = 160;
    private static final int SET_DUMP_SPEECH_DEBUG_INFO = 97;
    private static final int SET_SPEECH_ANC_DISABLE = 180;
    private static final int SET_SPEECH_ANC_LOG_STATUS = 179;
    private static final int SET_SPEECH_VM_ENABLE = 96;
    public static final String TAG = "Audio/SpeechLogger2";
    private static final String TYPE_COMMON = "Common";
    private static final String TYPE_EPL_OFF = "EPL_Off";
    private static final String TYPE_EPL_ON = "EPL_On";
    private static final String VERSION_DEVIDER = "=";
    private static final int VM_LOG_POS = 1440;
    AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public boolean mBound;
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
            CompoundButton compoundButton = buttonView;
            boolean z = checked;
            SharedPreferences preferences = AudioSpeechLoggerXV2.this.getSharedPreferences("engineermode_audiolog_preferences", 0);
            SharedPreferences.Editor edit = preferences.edit();
            if (compoundButton.equals(AudioSpeechLoggerXV2.this.mCbSpeechLogger)) {
                if (z && AudioSpeechLoggerXV2.this.mCbCtm4Way.isChecked()) {
                    AudioSpeechLoggerXV2.this.mCbCtm4Way.setChecked(false);
                }
                AudioSpeechLoggerXV2.this.onClickSpeechLogger(edit, z);
                SharedPreferences sharedPreferences = preferences;
            } else if (compoundButton.equals(AudioSpeechLoggerXV2.this.mCbCtm4Way)) {
                if (z) {
                    if (AudioSpeechLoggerXV2.this.mCbSpeechLogger.isChecked()) {
                        AudioSpeechLoggerXV2.this.mCbSpeechLogger.setChecked(false);
                    }
                    AudioSpeechLoggerXV2.this.mAudioManager.setParameters("SET_VMLOG_CONFIG=2");
                    SharedPreferences sharedPreferences2 = preferences;
                    return;
                }
                AudioSpeechLoggerXV2.this.mAudioManager.setParameters("SET_VMLOG_CONFIG=0");
                SharedPreferences sharedPreferences3 = preferences;
            } else if (compoundButton.equals(AudioSpeechLoggerXV2.this.mCbEplDebug)) {
                if (z) {
                    Elog.d(AudioSpeechLoggerXV2.TAG, "mCKEPLDebug checked");
                    AudioTuningJni.setAudioCommand(160, 1);
                    SharedPreferences sharedPreferences4 = preferences;
                    return;
                }
                Elog.d(AudioSpeechLoggerXV2.TAG, "mCKEPLDebug Unchecked");
                AudioTuningJni.setAudioCommand(160, 0);
                SharedPreferences sharedPreferences5 = preferences;
            } else if (!compoundButton.equals(AudioSpeechLoggerXV2.this.mRbEpl)) {
                if (compoundButton.equals(AudioSpeechLoggerXV2.this.mRbNormalVm)) {
                    if (z) {
                        Elog.d(AudioSpeechLoggerXV2.TAG, "mCKBNormalVm checked");
                        AudioSpeechLoggerXV2.this.custXmlEnableChanged();
                        if (AudioSpeechLoggerXV2.this.mForRefresh) {
                            boolean unused = AudioSpeechLoggerXV2.this.mForRefresh = false;
                            return;
                        }
                        Elog.d(AudioSpeechLoggerXV2.TAG, "mCKBNormalVm checked ok");
                        String ret = AudioSpeechLoggerXV2.this.getVmCmdValue(AudioSpeechLoggerXV2.CMD_GET_DEBUG_INFO, "0x0", false);
                        if (ret != null) {
                            String cmd = AudioSpeechLoggerXV2.CMD_SET_DEBUG_INFO + ret;
                            Elog.i(AudioSpeechLoggerXV2.TAG, "setParameters " + cmd);
                            AudioSpeechLoggerXV2.this.setParameters(cmd);
                        } else {
                            Elog.i(AudioSpeechLoggerXV2.TAG, "set mCKBEPL parameter failed");
                            Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_failed_tip, 1).show();
                        }
                        String ret2 = AudioSpeechLoggerXV2.this.getVmCmdValue(AudioSpeechLoggerXV2.CMD_GET_PARAMETER, "", false);
                        Elog.i(AudioSpeechLoggerXV2.TAG, "getParameters " + ret2);
                        if (ret2 == null) {
                            Elog.i(AudioSpeechLoggerXV2.TAG, "set mCKBEPL parameter failed");
                            Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_failed_tip, 1).show();
                        } else if (!ret2.isEmpty()) {
                            String cmd2 = AudioSpeechLoggerXV2.CMD_SET_COMMON_PARAMETER + ret2;
                            Elog.i(AudioSpeechLoggerXV2.TAG, "setParameters " + cmd2);
                            AudioSpeechLoggerXV2.this.setParameters(cmd2);
                        }
                        edit.putInt("epl_status", 0);
                        edit.commit();
                        return;
                    }
                    Elog.d(AudioSpeechLoggerXV2.TAG, "mCKBNormalVm unchecked");
                } else if (compoundButton.equals(AudioSpeechLoggerXV2.this.mCbAncLogger)) {
                    for (int i = 0; i < AudioSpeechLoggerXV2.this.mRgAnc.getChildCount(); i++) {
                        AudioSpeechLoggerXV2.this.mRgAnc.getChildAt(i).setEnabled(z);
                    }
                    if (z) {
                        Elog.d(AudioSpeechLoggerXV2.TAG, "mCbSpeechLogger checked");
                        int audioCommand = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV2.SET_SPEECH_ANC_LOG_STATUS, 1);
                        AudioSpeechLoggerXV2.this.mRgAnc.check(R.id.Audio_AncLogger_Down);
                    } else {
                        Elog.d(AudioSpeechLoggerXV2.TAG, "mCbSpeechLogger unchecked");
                        AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV2.SET_SPEECH_ANC_DISABLE, 0);
                    }
                    edit.putString("anc_status", AudioSpeechLoggerXV2.ANC_DOWN_SAMPLE);
                    edit.commit();
                } else if (!compoundButton.equals(AudioSpeechLoggerXV2.this.mCbMagiConf)) {
                } else {
                    if (z) {
                        Elog.d(AudioSpeechLoggerXV2.TAG, "mCbMagiConf checked");
                        AudioSystem.setParameters("SET_MAGI_CONFERENCE_ENABLE=1");
                        return;
                    }
                    Elog.d(AudioSpeechLoggerXV2.TAG, "mCbMagiConf Unchecked");
                    AudioSystem.setParameters("SET_MAGI_CONFERENCE_ENABLE=0");
                }
            } else if (z) {
                Elog.d(AudioSpeechLoggerXV2.TAG, "mCKBEPL checked");
                AudioSpeechLoggerXV2.this.custXmlEnableChanged();
                SharedPreferences sharedPreferences6 = preferences;
                String ret3 = AudioSpeechLoggerXV2.this.getVmCmdValue(AudioSpeechLoggerXV2.CMD_GET_DEBUG_INFO, "0x3", true);
                if (ret3 != null) {
                    String cmd3 = AudioSpeechLoggerXV2.CMD_SET_DEBUG_INFO + ret3;
                    Elog.i(AudioSpeechLoggerXV2.TAG, "setParameters " + cmd3);
                    AudioSpeechLoggerXV2.this.setParameters(cmd3);
                } else {
                    Elog.i(AudioSpeechLoggerXV2.TAG, "set mCKBEPL parameter failed");
                    Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_failed_tip, 1).show();
                }
                String ret4 = AudioSpeechLoggerXV2.this.getVmCmdValue(AudioSpeechLoggerXV2.CMD_GET_PARAMETER, "0x6", true);
                Elog.i(AudioSpeechLoggerXV2.TAG, "getParameters " + ret4);
                if (ret4 != null) {
                    String cmd4 = AudioSpeechLoggerXV2.CMD_SET_COMMON_PARAMETER + ret4;
                    Elog.i(AudioSpeechLoggerXV2.TAG, "setParameters " + cmd4);
                    AudioSpeechLoggerXV2.this.setParameters(cmd4);
                } else {
                    Elog.i(AudioSpeechLoggerXV2.TAG, "set mCKBEPL parameter failed");
                    Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_failed_tip, 1).show();
                }
                edit.putInt("epl_status", 1);
                edit.commit();
            } else {
                Elog.d(AudioSpeechLoggerXV2.TAG, "mCKBEPL unchecked");
            }
        }
    };
    /* access modifiers changed from: private */
    public Button mClearDumpButton;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Elog.d(AudioSpeechLoggerXV2.TAG, "onServiceConnected");
            AudioSpeechLoggerXV2 audioSpeechLoggerXV2 = AudioSpeechLoggerXV2.this;
            CopyHalDumpService unused = audioSpeechLoggerXV2.mService = ((CopyHalDumpService.LocalBinder) service).getService(audioSpeechLoggerXV2.mHandler);
            boolean unused2 = AudioSpeechLoggerXV2.this.mBound = true;
            AudioSpeechLoggerXV2.this.checkCopyDumpStatus();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Elog.d(AudioSpeechLoggerXV2.TAG, "onServiceDisconnected");
            AudioSpeechLoggerXV2.this.mService.removeUiHandler();
            boolean unused = AudioSpeechLoggerXV2.this.mBound = false;
        }
    };
    /* access modifiers changed from: private */
    public Button mCopy2SdButton;
    View.OnClickListener mCopyAudioDumpButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (!AudioSpeechLoggerXV2.this.mBound) {
                Elog.w(AudioSpeechLoggerXV2.TAG, "onClick CopyAudioDumpButtons should connect Service first");
            }
            switch (v.getId()) {
                case R.id.Audio_clear_dump:
                    Elog.v(AudioSpeechLoggerXV2.TAG, "clear hal dump");
                    AudioSpeechLoggerXV2.this.mCopy2SdButton.setEnabled(false);
                    AudioSpeechLoggerXV2.this.mClearDumpButton.setEnabled(false);
                    AudioTuningJni.delAudioHalDumpFiles(AudioSpeechLoggerXV2.this.mService);
                    return;
                case R.id.Audio_move_to_sdcard:
                    AudioSpeechLoggerXV2.this.mCopy2SdButton.setEnabled(false);
                    if (AudioSpeechLoggerXV2.this.mClearDumpButton.isEnabled()) {
                        Elog.v(AudioSpeechLoggerXV2.TAG, "copy hal dump");
                        AudioSpeechLoggerXV2.this.mCopy2SdButton.setText(R.string.Audio_stop_moving);
                        AudioTuningJni.copyAudioHalDumpFilesToSdcard(AudioSpeechLoggerXV2.this.mService);
                        AudioSpeechLoggerXV2.this.mClearDumpButton.setEnabled(false);
                    } else {
                        Elog.v(AudioSpeechLoggerXV2.TAG, "stop copying hal dump");
                        AudioSpeechLoggerXV2.this.mCopy2SdButton.setText(R.string.Audio_move_to_sdcard);
                        AudioSpeechLoggerXV2.this.mCopyProgressTv.setText(R.string.Audio_stop_moving_text);
                        AudioTuningJni.cancleCopyAudioHalDumpFile();
                        AudioSpeechLoggerXV2.this.mClearDumpButton.setEnabled(true);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    AudioSpeechLoggerXV2.this.mCopy2SdButton.setEnabled(true);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public TextView mCopyProgressTv;
    /* access modifiers changed from: private */
    public boolean mForRefresh = false;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    AudioSpeechLoggerXV2.this.mCopyProgressTv.setText((String) msg.obj);
                    return;
                case 11:
                    AudioSpeechLoggerXV2.this.mCopyProgressTv.setText((String) msg.obj);
                    return;
                case 12:
                    AudioSpeechLoggerXV2.this.mCopyProgressTv.setText(R.string.Audio_copy_dump_done);
                    AudioSpeechLoggerXV2.this.mCopy2SdButton.setText(R.string.Audio_move_to_sdcard);
                    AudioSpeechLoggerXV2.this.mClearDumpButton.setEnabled(true);
                    return;
                case 13:
                    AudioSpeechLoggerXV2.this.mCopyProgressTv.setText(R.string.Audio_delete_dump_done);
                    AudioSpeechLoggerXV2.this.mCopy2SdButton.setEnabled(true);
                    AudioSpeechLoggerXV2.this.mClearDumpButton.setEnabled(true);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioButton mRbEpl;
    /* access modifiers changed from: private */
    public RadioButton mRbNormalVm;
    /* access modifiers changed from: private */
    public RadioGroup mRgAnc;
    private final RadioGroup.OnCheckedChangeListener mRgCheckedListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.equals(AudioSpeechLoggerXV2.this.mRgAnc)) {
                SharedPreferences.Editor edit = AudioSpeechLoggerXV2.this.getSharedPreferences("engineermode_audiolog_preferences", 0).edit();
                if (R.id.Audio_AncLogger_Down == checkedId) {
                    Elog.d(AudioSpeechLoggerXV2.TAG, "Audio_AncLogger_Down checked");
                    int audioCommand = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV2.SET_SPEECH_ANC_LOG_STATUS, 1);
                    edit.putString("anc_status", AudioSpeechLoggerXV2.ANC_DOWN_SAMPLE);
                    edit.commit();
                } else if (R.id.Audio_AncLogger_NoDown == checkedId) {
                    Elog.d(AudioSpeechLoggerXV2.TAG, "Audio_SpeechLogger_NoDown checked");
                    int audioCommand2 = AudioTuningJni.setAudioCommand(AudioSpeechLoggerXV2.SET_SPEECH_ANC_LOG_STATUS, 0);
                    edit.putString("anc_status", AudioSpeechLoggerXV2.ANC_NO_DOWN_SAMPLE);
                    edit.commit();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public CopyHalDumpService mService;

    /* access modifiers changed from: private */
    public String getVmCmdValue(String getCmd, String def, boolean isVmEpl) {
        Object[] objArr = new Object[1];
        objArr[0] = isVmEpl ? TYPE_EPL_ON : TYPE_EPL_OFF;
        String nodeList = getParameters(String.format(getCmd, objArr));
        String node = def;
        if (nodeList != null && !nodeList.isEmpty()) {
            node = nodeList.split(",")[0];
            Elog.d(TAG, "get VM Cmd value from xml, new node = " + node);
        }
        if (node.isEmpty()) {
            return "";
        }
        String ret = getParameters(String.format(getCmd, new Object[]{TYPE_COMMON}));
        if (ret == null) {
            return null;
        }
        String[] entries = ret.split(",");
        if (entries.length < 1) {
            return null;
        }
        String cmdValue = node;
        for (int i = 1; i < entries.length; i++) {
            cmdValue = cmdValue + "," + entries[i];
        }
        Elog.i(TAG, "getParametersValue " + cmdValue);
        return cmdValue;
    }

    /* access modifiers changed from: private */
    public void custXmlEnableChanged() {
        if (!FeatureSupport.isEngLoad()) {
            String check = this.mAudioManager.getParameters(CUST_XML_PARAM);
            if (check == null || !RESULT_SUPPORT.equals(check)) {
                Elog.d(TAG, "set CUST_XML_PARAM = 1");
                this.mAudioManager.setParameters(CUST_XML_SET_SUPPORT_PARAM);
                AudioTuningJni.CustXmlEnableChanged(1);
                return;
            }
            Elog.d(TAG, "get CUST_XML_PARAM = 1");
        }
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
            this.mAudioManager.setParameters("SET_VMLOG_CONFIG=1");
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
        this.mAudioManager.setParameters("SET_VMLOG_CONFIG=0");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_speechloggerx);
        this.mAudioManager = (AudioManager) getSystemService("audio");
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
        String[] split = magiSupport.split(VERSION_DEVIDER);
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
                Elog.d(AudioSpeechLoggerXV2.TAG, "On Click mDumpSpeechInfo button.");
                if (AudioTuningJni.setAudioCommand(97, 1) == -1) {
                    Elog.i(AudioSpeechLoggerXV2.TAG, "set mDumpSpeechInfo parameter failed");
                    Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_failed_tip, 1).show();
                    return;
                }
                Toast.makeText(AudioSpeechLoggerXV2.this, R.string.set_success_tip, 1).show();
            }
        });
        showDialog(10);
        Intent intent = new Intent();
        intent.setClass(this, CopyHalDumpService.class);
        startForegroundService(intent);
        initCopyDumpUi();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void checkStatus() {
        int eplStatus = getSharedPreferences("engineermode_audiolog_preferences", 0).getInt("epl_status", 1);
        String vmLogState = this.mAudioManager.getParameters(CMD_GET_VMLOG_STATE);
        Elog.i(TAG, "return value is : " + vmLogState);
        if (vmLogState != null && vmLogState.length() > CMD_GET_VMLOG_STATE.length() + 1) {
            vmLogState = vmLogState.substring(CMD_GET_VMLOG_STATE.length() + 1);
        }
        if (!"1".equals(vmLogState)) {
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
            if (!FeatureSupport.isEngLoad()) {
                String check = this.mAudioManager.getParameters(CUST_XML_PARAM);
                if (check == null || !RESULT_SUPPORT.equals(check)) {
                    Elog.d(TAG, "get CUST_XML_PARAM = 0");
                    this.mCbSpeechLogger.setChecked(false);
                    this.mRbEpl.setEnabled(false);
                    this.mRbNormalVm.setEnabled(false);
                    this.mRbEpl.setChecked(false);
                    this.mRbNormalVm.setChecked(false);
                } else {
                    Elog.d(TAG, "get CUST_XML_PARAM = 1");
                }
            }
        }
        this.mCbCtm4Way.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        if (!"2".equals(vmLogState)) {
            this.mCbCtm4Way.setChecked(false);
        } else {
            this.mCbCtm4Way.setChecked(true);
        }
        this.mCbCtm4Way.setOnCheckedChangeListener(this.mCheckedListener);
        int epl = AudioTuningJni.getAudioCommand(GET_DUMP_AP_SPEECH_EPL);
        Elog.i(TAG, "Get EPL setting: " + epl);
        this.mCbEplDebug.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        if (epl == 1) {
            this.mCbEplDebug.setChecked(true);
        } else {
            this.mCbEplDebug.setChecked(false);
        }
        this.mCbEplDebug.setOnCheckedChangeListener(this.mCheckedListener);
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

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechLoggerXV2.this.finish();
                    }
                }).create();
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.no_sdcard_title).setMessage(R.string.no_sdcard_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.sdcard_busy_title).setMessage(R.string.sdcard_busy_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 10:
                return new AlertDialog.Builder(this).setTitle(R.string.audio_warning_title).setMessage(R.string.audio_dump_path_change_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private String getParameters(String command) {
        if (Audio.AudioTuningVer.VER_2_2 != Audio.getAudioTuningVer()) {
            String result = this.mAudioManager.getParameters(command);
            if (result != null && result.length() > CMD_PREFIX.length()) {
                result = result.substring(CMD_PREFIX.length());
            }
            Elog.i(TAG, "getParameters return " + result);
            return result;
        }
        String command2 = command.substring(command.indexOf(VERSION_DEVIDER) + 1);
        String[] params = command2.split(PARAM_DEVIDER);
        String result2 = null;
        if (params.length == 3) {
            result2 = AudioTuningJni.getParams(params[0], params[1], params[2]);
        } else if (params.length == 2) {
            result2 = AudioTuningJni.getCategory(params[0], params[1]);
        } else {
            Elog.i(TAG, "error parameter");
        }
        Elog.i(TAG, "getParameters " + command2 + " return " + result2);
        return result2;
    }

    /* access modifiers changed from: private */
    public void setParameters(String command) {
        if (Audio.AudioTuningVer.VER_2_2 != Audio.getAudioTuningVer()) {
            this.mAudioManager.setParameters(command);
        } else {
            command = command.substring(command.indexOf(VERSION_DEVIDER) + 1);
            String[] params = command.split(PARAM_DEVIDER);
            if (params.length == 4) {
                AudioTuningJni.setParams(params[0], params[1], params[2], params[3]);
                AudioTuningJni.saveToWork(params[0]);
            }
        }
        Elog.i(TAG, "setParameters " + command);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        bindService(new Intent(this, CopyHalDumpService.class), this.mConnection, 1);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mBound) {
            unbindService(this.mConnection);
            this.mBound = false;
        }
    }

    private void initCopyDumpUi() {
        this.mCopy2SdButton = (Button) findViewById(R.id.Audio_move_to_sdcard);
        this.mClearDumpButton = (Button) findViewById(R.id.Audio_clear_dump);
        this.mCopy2SdButton.setOnClickListener(this.mCopyAudioDumpButtonListener);
        this.mClearDumpButton.setOnClickListener(this.mCopyAudioDumpButtonListener);
        this.mCopy2SdButton.setEnabled(false);
        this.mClearDumpButton.setEnabled(false);
        this.mCopyProgressTv = (TextView) findViewById(R.id.Audio_copy_progress);
    }

    /* renamed from: com.mediatek.engineermode.audio.AudioSpeechLoggerXV2$8  reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS;

        static {
            int[] iArr = new int[CopyHalDumpService.DUMP_STATUS.values().length];
            $SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS = iArr;
            try {
                iArr[CopyHalDumpService.DUMP_STATUS.COPY_HAL_DUMP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS[CopyHalDumpService.DUMP_STATUS.DELETE_HAL_DUMP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS[CopyHalDumpService.DUMP_STATUS.COPY_DUMP_DONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS[CopyHalDumpService.DUMP_STATUS.DELETE_DUMP_DONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkCopyDumpStatus() {
        switch (AnonymousClass8.$SwitchMap$com$mediatek$engineermode$audio$CopyHalDumpService$DUMP_STATUS[this.mService.getCopyDumpStatus().ordinal()]) {
            case 1:
                Elog.v(TAG, "COPY_HAL_DUMP");
                this.mCopy2SdButton.setText(R.string.Audio_stop_moving);
                this.mCopy2SdButton.setEnabled(true);
                this.mClearDumpButton.setEnabled(false);
                return;
            case 2:
                Elog.v(TAG, "DELETE_HAL_DUMP");
                this.mCopy2SdButton.setEnabled(false);
                this.mClearDumpButton.setEnabled(false);
                return;
            case 3:
                Elog.v(TAG, "COPY_DUMP_DONE");
                this.mCopy2SdButton.setEnabled(true);
                this.mClearDumpButton.setEnabled(true);
                this.mCopyProgressTv.setText(R.string.Audio_copy_dump_done);
                return;
            case 4:
                Elog.v(TAG, "DELETE_DUMP_DONE");
                this.mCopy2SdButton.setEnabled(true);
                this.mClearDumpButton.setEnabled(true);
                this.mCopyProgressTv.setText(R.string.Audio_delete_dump_done);
                return;
            default:
                Elog.v(TAG, "DEFAULT");
                this.mCopy2SdButton.setEnabled(true);
                this.mClearDumpButton.setEnabled(true);
                return;
        }
    }
}
