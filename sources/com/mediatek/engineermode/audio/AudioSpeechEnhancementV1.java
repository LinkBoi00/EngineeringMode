package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class AudioSpeechEnhancementV1 extends Activity implements View.OnClickListener {
    private static final int ACTURAL_PARAM_NUM = 32;
    static final String AFK_WB_SPEED = "MTK_WB_SPEECH_SUPPORT";
    private static final int AUDIO_PARA_DIV_INDEX = 15;
    private static final int AUDIO_PARA_DIV_INDEX1 = 16;
    private static final int COMMON_PARA_SIZE = 24;
    private static final int CONSTANT_0XFF = 255;
    private static final int CONSTANT_256 = 256;
    private static final int CONSTANT_32 = 32;
    private static final int DATA_SIZE = 1444;
    private static final int DIALOG_GET_DATA_ERROR = 1;
    private static final int DIALOG_GET_WBDATA_ERROR = 2;
    private static final int DIALOG_SET_SE_ERROR = 4;
    private static final int DIALOG_SET_SE_SUCCESS = 3;
    private static final int DIALOG_SET_WB_ERROR = 6;
    private static final int DIALOG_SET_WB_SUCCESS = 5;
    private static final int GET_SPEECH_HAC_PARAMETER = 208;
    private static final int GET_SPEECH_MAGICON_PARAMETER = 192;
    private static final int GET_WB_SPEECH_PARAMETER = 64;
    private static final int HAC_DATA_SIZE = 650;
    private static final String HAC_MODE = "HAC Mode";
    private static final int INDEX_HAC_MODE = 10;
    private static final int INDEX_VOICE_TRACKING_MODE = 9;
    private static final int LARGEST_NUM = 65535;
    private static final int LONGEST_NUM_LENGHT = 5;
    private static final int MODE0_PARAM_NUM = 12;
    private static final int SET_SPEECH_HAC_PARAMETER = 209;
    private static final int SET_SPEECH_MAGICON_PARAMETER = 193;
    private static final int SET_WB_SPEECH_PARAMETER = 65;
    private static final int SPEECH_DATA_SIZE = 64;
    private static final String TAG = "Audio/SpeechEnhancement1";
    private static final String VOICE_TRACKING_MODE = "Voice Tracking Mode";
    private static final int VOLUME_SIZE = 22;
    private static final int WB_DATA_SIZE = 2416;
    private Button mBtnSet;
    private byte[] mData;
    private byte[] mHACdata;
    /* access modifiers changed from: private */
    public int mModeIndex;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mParaAdatper;
    /* access modifiers changed from: private */
    public int mParaIndex;
    /* access modifiers changed from: private */
    public Spinner mParaSpinner;
    /* access modifiers changed from: private */
    public String mParamterStr = "";
    private byte[] mSpeechdata;
    /* access modifiers changed from: private */
    public EditText mValueEdit;
    private byte[] mWBdata;

    static boolean isAudioFeatureSupport(Context context, String featureKey) {
        String pairs = ((AudioManager) context.getSystemService("audio")).getParameters(featureKey);
        if (pairs == null) {
            Elog.d(TAG, "PARSE FAIL; parameters is null");
            return false;
        }
        String[] keyvals = pairs.split(";");
        String[] keyval = keyvals[0].split("=");
        if (keyval.length < 2) {
            Elog.d(TAG, "parse fail; invalid keyval:" + keyvals[0]);
            return false;
        }
        String val = keyval[1].trim();
        if ("true".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val)) {
            return true;
        }
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_speechenhancement);
        this.mParamterStr = getResources().getString(R.string.paramter);
        this.mBtnSet = (Button) findViewById(R.id.Audio_SpEnhancement_Button);
        this.mValueEdit = (EditText) findViewById(R.id.Audio_SpEnhancement_EditText);
        this.mParaSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_ParaType);
        TextView valueText = (TextView) findViewById(R.id.Audio_SpEnhancement_TextView);
        Spinner modeSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_ModeType);
        String[] modeArray = getResources().getStringArray(R.array.speech_enhance_mode);
        List<String> modeArrayList = new ArrayList<>();
        for (String add : modeArray) {
            modeArrayList.add(add);
        }
        ArrayAdapter<String> mModeAdatper = new ArrayAdapter<>(this, 17367048, modeArrayList);
        if (checkMagiStatus()) {
            mModeAdatper.add(VOICE_TRACKING_MODE);
        }
        if (checkHacStatus()) {
            mModeAdatper.add(HAC_MODE);
        }
        mModeAdatper.setDropDownViewResource(17367049);
        modeSpinner.setAdapter(mModeAdatper);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int initValue;
                int unused = AudioSpeechEnhancementV1.this.mModeIndex = arg2;
                if (arg2 == 0) {
                    AudioSpeechEnhancementV1.this.mParaAdatper.clear();
                    for (int i = 0; i < 12; i++) {
                        ArrayAdapter access$100 = AudioSpeechEnhancementV1.this.mParaAdatper;
                        access$100.add(AudioSpeechEnhancementV1.this.mParamterStr + i);
                    }
                } else {
                    AudioSpeechEnhancementV1.this.mParaAdatper.clear();
                    for (int i2 = 0; i2 < 32; i2++) {
                        ArrayAdapter access$1002 = AudioSpeechEnhancementV1.this.mParaAdatper;
                        access$1002.add(AudioSpeechEnhancementV1.this.mParamterStr + i2);
                    }
                }
                AudioSpeechEnhancementV1.this.mParaSpinner.setSelection(0);
                int unused2 = AudioSpeechEnhancementV1.this.mParaIndex = 0;
                String tag = arg0.getSelectedItem().toString();
                if (tag.equals(AudioSpeechEnhancementV1.VOICE_TRACKING_MODE)) {
                    int unused3 = AudioSpeechEnhancementV1.this.mModeIndex = 9;
                    initValue = AudioSpeechEnhancementV1.this.getSpeechData();
                } else if (tag.equals(AudioSpeechEnhancementV1.HAC_MODE)) {
                    int unused4 = AudioSpeechEnhancementV1.this.mModeIndex = 10;
                    initValue = AudioSpeechEnhancementV1.this.getHacData();
                } else {
                    initValue = AudioSpeechEnhancementV1.this.getAudioData();
                }
                AudioSpeechEnhancementV1.this.mValueEdit.setText(String.valueOf(initValue));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Elog.i(AudioSpeechEnhancementV1.TAG, "do noting...");
            }
        });
        this.mBtnSet.setOnClickListener(this);
        valueText.setText(R.string.speech_enhance_text);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mParaAdatper = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        for (int i = 0; i < 12; i++) {
            this.mParaAdatper.add(this.mParamterStr + i);
        }
        this.mParaSpinner.setAdapter(this.mParaAdatper);
        this.mParaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int initValue;
                int unused = AudioSpeechEnhancementV1.this.mParaIndex = arg2;
                if (AudioSpeechEnhancementV1.this.mModeIndex == 9) {
                    initValue = AudioSpeechEnhancementV1.this.getSpeechData();
                } else if (AudioSpeechEnhancementV1.this.mModeIndex == 10) {
                    initValue = AudioSpeechEnhancementV1.this.getHacData();
                } else {
                    initValue = AudioSpeechEnhancementV1.this.getAudioData();
                }
                AudioSpeechEnhancementV1.this.mValueEdit.setText(String.valueOf(initValue));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Elog.i(AudioSpeechEnhancementV1.TAG, "do noting...");
            }
        });
        this.mData = new byte[DATA_SIZE];
        for (int n = 0; n < DATA_SIZE; n++) {
            this.mData[n] = 0;
        }
        this.mWBdata = new byte[WB_DATA_SIZE];
        for (int n2 = 0; n2 < WB_DATA_SIZE; n2++) {
            this.mWBdata[n2] = 0;
        }
        int ret = AudioTuningJni.getEmParameter(this.mData, DATA_SIZE);
        if (ret != 0) {
            showDialog(1);
            Elog.i(TAG, "Audio_SpeechEnhancement GetEMParameter return value is : " + ret);
        }
        int ret2 = AudioTuningJni.getAudioData(64, WB_DATA_SIZE, this.mWBdata);
        if (ret2 != 0) {
            showDialog(2);
            Elog.i(TAG, "Audio_SpeechEnhancement Get wb data return value is : " + ret2);
        }
        modeSpinner.setSelection(0);
        this.mParaSpinner.setSelection(0);
        this.mModeIndex = 0;
        this.mParaIndex = 0;
        this.mValueEdit.setText(String.valueOf(getAudioData()));
    }

    public void onClick(View arg0) {
        int inputValue;
        if (arg0.equals(this.mBtnSet) && (inputValue = checkEditNumber(this.mValueEdit, 65535)) != -1) {
            int i = this.mModeIndex;
            if (i == 9) {
                setSpeechData(inputValue);
            } else if (i == 10) {
                setHacData(inputValue);
            } else {
                setAudioData(inputValue);
            }
        }
    }

    private int checkEditNumber(EditText edit, int maxValue) {
        if (edit == null || edit.getText() == null) {
            Toast.makeText(this, getString(R.string.input_null_tip), 1).show();
            return -1;
        }
        String editStr = edit.getText().toString();
        if (editStr == null || editStr.length() == 0) {
            Toast.makeText(this, getString(R.string.input_null_tip), 1).show();
            return -1;
        }
        try {
            int value = Integer.valueOf(editStr).intValue();
            if (value <= maxValue) {
                return value;
            }
            Toast.makeText(this, getString(R.string.number_arrage_tip) + maxValue, 1).show();
            return -1;
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.number_arrage_tip) + maxValue, 1).show();
            return -1;
        }
    }

    /* access modifiers changed from: private */
    public int getAudioData() {
        if (this.mParaIndex > 15) {
            return getWBAudioData();
        }
        return getSpeechEnhanceAudioData();
    }

    /* access modifiers changed from: private */
    public int getSpeechData() {
        byte[] bArr = this.mSpeechdata;
        int i = this.mParaIndex;
        byte high = bArr[(i * 2) + 1];
        byte low = bArr[i * 2];
        return ((high < 0 ? high + 256 : high) * 256) + (low < 0 ? low + 256 : low);
    }

    /* access modifiers changed from: private */
    public int getHacData() {
        byte[] bArr = this.mHACdata;
        int i = this.mParaIndex;
        byte high = bArr[(i * 2) + 1];
        byte low = bArr[i * 2];
        return ((high < 0 ? high + 256 : high) * 256) + (low < 0 ? low + 256 : low);
    }

    private void setHacData(int inputValue) {
        byte[] bArr = this.mHACdata;
        int i = this.mParaIndex;
        bArr[i * 2] = (byte) (inputValue % 256);
        bArr[(i * 2) + 1] = (byte) (inputValue / 256);
        int result = AudioTuningJni.setAudioData(SET_SPEECH_HAC_PARAMETER, HAC_DATA_SIZE, bArr);
        if (result == 0 || -38 == result) {
            showDialog(3);
            return;
        }
        showDialog(4);
        Elog.i(TAG, "Audio_SpeechEnhancement setHacData return value is : " + result);
    }

    private void setAudioData(int inputValue) {
        if (this.mParaIndex > 15) {
            setWBAudioData(inputValue);
        } else {
            setSpeechEnhanceAudioData(inputValue);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v3, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v4, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v3, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v4, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getSpeechEnhanceAudioData() {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            int r2 = r7.mModeIndex
            if (r2 != 0) goto L_0x0019
            byte[] r2 = r7.mData
            int r3 = r7.mParaIndex
            int r4 = r3 * 2
            int r4 = r4 + 22
            int r4 = r4 + 1
            byte r0 = r2[r4]
            int r3 = r3 * 2
            int r3 = r3 + 22
            byte r1 = r2[r3]
            goto L_0x0035
        L_0x0019:
            byte[] r3 = r7.mData
            int r4 = r2 + -1
            int r4 = r4 * 32
            int r4 = r4 + 46
            int r5 = r7.mParaIndex
            int r6 = r5 * 2
            int r4 = r4 + r6
            int r4 = r4 + 1
            byte r0 = r3[r4]
            int r2 = r2 + -1
            int r2 = r2 * 32
            int r2 = r2 + 46
            int r5 = r5 * 2
            int r2 = r2 + r5
            byte r1 = r3[r2]
        L_0x0035:
            if (r0 >= 0) goto L_0x003a
            int r2 = r0 + 256
            goto L_0x003b
        L_0x003a:
            r2 = r0
        L_0x003b:
            r0 = r2
            if (r1 >= 0) goto L_0x0041
            int r2 = r1 + 256
            goto L_0x0042
        L_0x0041:
            r2 = r1
        L_0x0042:
            r1 = r2
            int r2 = r0 * 256
            int r2 = r2 + r1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.audio.AudioSpeechEnhancementV1.getSpeechEnhanceAudioData():int");
    }

    private void setSpeechData(int inputValue) {
        byte[] bArr = this.mSpeechdata;
        int i = this.mParaIndex;
        bArr[i * 2] = (byte) (inputValue % 256);
        bArr[(i * 2) + 1] = (byte) (inputValue / 256);
        int result = AudioTuningJni.setAudioData(SET_SPEECH_MAGICON_PARAMETER, 64, bArr);
        if (result == 0 || -38 == result) {
            showDialog(3);
            return;
        }
        showDialog(4);
        Elog.i(TAG, "Audio_SpeechEnhancement setAudioData return value is : " + result);
    }

    private void setSpeechEnhanceAudioData(int inputValue) {
        int high = inputValue / 256;
        int low = inputValue % 256;
        int i = this.mModeIndex;
        if (i == 0) {
            byte[] bArr = this.mData;
            int i2 = this.mParaIndex;
            bArr[(i2 * 2) + 22] = (byte) low;
            bArr[(i2 * 2) + 22 + 1] = (byte) high;
        } else {
            byte[] bArr2 = this.mData;
            int i3 = this.mParaIndex;
            bArr2[((i - 1) * 32) + 46 + (i3 * 2)] = (byte) low;
            bArr2[((i - 1) * 32) + 46 + (i3 * 2) + 1] = (byte) high;
        }
        int result = AudioTuningJni.setEmParameter(this.mData, DATA_SIZE);
        if (result == 0 || -38 == result) {
            showDialog(3);
            return;
        }
        showDialog(4);
        Elog.i(TAG, "Audio_SpeechEnhancement SetEMParameter return value is : " + result);
    }

    private int getWBdata(int catalogIdx, int paraIdx) {
        byte[] bArr = this.mWBdata;
        int highByte = ((bArr[(catalogIdx * 32) + (paraIdx * 2) + 1] + 256) & 255) * 256;
        int lowByte = (bArr[(catalogIdx * 32) + (paraIdx * 2)] + 256) & 255;
        Elog.v(TAG, "getWBdata mode " + catalogIdx + ", paraIdx " + paraIdx + "val " + (highByte + lowByte));
        return highByte + lowByte;
    }

    private void setWBdata(int catalogIdx, int paraIdx, int val) {
        byte[] bArr = this.mWBdata;
        bArr[(catalogIdx * 32) + (paraIdx * 2)] = (byte) (val % 256);
        bArr[(catalogIdx * 32) + (paraIdx * 2) + 1] = (byte) (val / 256);
    }

    private int getWBAudioData() {
        return getWBdata(this.mModeIndex - 1, this.mParaIndex - 16);
    }

    private void setWBAudioData(int inputval) {
        int i = this.mParaIndex;
        if (i < 16) {
            Elog.i(TAG, "Internal error. check the code.");
            return;
        }
        setWBdata(this.mModeIndex - 1, i - 16, inputval);
        int result = AudioTuningJni.setAudioData(65, WB_DATA_SIZE, this.mWBdata);
        if (result == 0 || -38 == result) {
            showDialog(5);
            return;
        }
        showDialog(6);
        Elog.i(TAG, "WB data SetAudioData return value is : " + result);
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechEnhancementV1.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.get_wbdata_error_title).setMessage(R.string.get_wbdata_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechEnhancementV1.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_speech_enhance_success).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 4:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_speech_enhance_failed).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 5:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_wb_success).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 6:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_wb_failed).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }

    private boolean checkMagiStatus() {
        String magiSupport = AudioSystem.getParameters("GET_MAGI_CONFERENCE_SUPPORT");
        Elog.i(TAG, "Get Magi support " + magiSupport);
        String[] magiStr = magiSupport.split("=");
        if (magiStr.length < 2 || !magiStr[1].equals("1")) {
            return false;
        }
        this.mSpeechdata = new byte[64];
        for (int n = 0; n < 64; n++) {
            this.mSpeechdata[n] = 0;
        }
        int ret = AudioTuningJni.getAudioData(GET_SPEECH_MAGICON_PARAMETER, 64, this.mSpeechdata);
        if (ret != 0) {
            showDialog(2);
            Elog.i(TAG, "Audio_SpeechEnhancement Get speech data return value is : " + ret);
        }
        return true;
    }

    private boolean checkHacStatus() {
        String hacSupport = AudioSystem.getParameters("GET_HAC_SUPPORT");
        Elog.i(TAG, "Get Hac support " + hacSupport);
        String[] hacStr = hacSupport.split("=");
        if (hacStr.length < 2 || !hacStr[1].equals("1")) {
            return false;
        }
        this.mHACdata = new byte[HAC_DATA_SIZE];
        for (int n = 0; n < HAC_DATA_SIZE; n++) {
            this.mHACdata[n] = 0;
        }
        int ret = AudioTuningJni.getAudioData(GET_SPEECH_HAC_PARAMETER, HAC_DATA_SIZE, this.mHACdata);
        if (ret != 0) {
            showDialog(2);
            Elog.i(TAG, "Audio_SpeechEnhancement Get hac data return value is : " + ret);
        }
        return true;
    }
}
