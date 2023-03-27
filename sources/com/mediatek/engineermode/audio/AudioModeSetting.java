package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import java.util.Arrays;
import java.util.List;

public class AudioModeSetting extends Activity implements View.OnClickListener {
    private static final int AUDIO_COMMAND_PARAM0 = 32;
    private static final int AUDIO_COMMAND_PARAM1 = 33;
    private static final int AUDIO_COMMAND_PARAM2 = 34;
    private static final int CONSTANT_0XFF = 255;
    private static final int CONSTANT_256 = 256;
    private static final int DIALOG_ID_GET_DATA_ERROR = 1;
    private static final int DIALOG_ID_SET_ERROR = 3;
    private static final int DIALOG_ID_SET_SUCCESS = 2;
    private static final int GET_CUSTOMD_DATASIZE = 5;
    private static final int MAX_VOL_SIZE = 6;
    private static final String TAG = "Audio/ModeSetting";
    private static final int TYPE_MAX_EXTAMP = 6;
    private static final int TYPE_MAX_HEADSET = 6;
    private static final int TYPE_MAX_HEADSPEAKER = 8;
    private static final int TYPE_MAX_NORMAL = 6;
    private static final int TYPE_MAX_SPEAKER = 6;
    private static final int VALUE_RANGE_160 = 160;
    private static final int VALUE_RANGE_255 = 255;
    private static int sGetCustomerData = 7;
    private static int sMaxVolLevel = 7;
    private static int sMaxVolMode = 3;
    private static int sMaxVolType = 8;
    /* access modifiers changed from: private */
    public static int sModeMicIndex = 2;
    /* access modifiers changed from: private */
    public static int sModeSidIndex = 5;
    /* access modifiers changed from: private */
    public static int sModeSph2Index = 4;
    /* access modifiers changed from: private */
    public static int sModeSphIndex = 4;
    private static int[] sOffSet = {(3 * 7) * 0, (3 * 7) * 1, (3 * 7) * 2, (3 * 7) * 3, (3 * 7) * 4, (3 * 7) * 5, (3 * 7) * 6, (3 * 7) * 7};
    private static int sSetCustomerData = 6;
    private static int sStructSize = ((3 * 7) * 8);
    private static int sTypeMedia = 6;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public Button mBtnSetMaxVol;
    /* access modifiers changed from: private */
    public Button mBtnSetMaxVolSpeaker;
    /* access modifiers changed from: private */
    public int mCurrentMode;
    /* access modifiers changed from: private */
    public byte[] mData = null;
    /* access modifiers changed from: private */
    public EditText mEditMaxVol;
    /* access modifiers changed from: private */
    public EditText mEditMaxVolSpeaker;
    private Spinner mFirSpinner;
    /* access modifiers changed from: private */
    public TextView mFirsummary;
    /* access modifiers changed from: private */
    public boolean mIsFirstFirSet = true;
    /* access modifiers changed from: private */
    public int mLevelIndex;
    private int[] mRealUsageVols;
    /* access modifiers changed from: private */
    public boolean mSupportEnhance = false;
    /* access modifiers changed from: private */
    public int mTypeIndex;
    /* access modifiers changed from: private */
    public TextView mValText;
    /* access modifiers changed from: private */
    public EditText mValueEdit;
    /* access modifiers changed from: private */
    public int mValueRange = 255;
    /* access modifiers changed from: private */
    public Spinner mVolLevelSpinner;
    private Spinner mVolTypeSpinner;

    private class ValLevelItemSelectListener implements AdapterView.OnItemSelectedListener {
        private ValLevelItemSelectListener() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            int unused = AudioModeSetting.this.mLevelIndex = arg2;
            EditText access$500 = AudioModeSetting.this.mValueEdit;
            AudioModeSetting audioModeSetting = AudioModeSetting.this;
            access$500.setText(String.valueOf(audioModeSetting.getValue(audioModeSetting.mData, AudioModeSetting.this.mCurrentMode, AudioModeSetting.this.mTypeIndex, AudioModeSetting.this.mLevelIndex)));
            AudioModeSetting.this.setMaxVolEdit();
            Elog.v(AudioModeSetting.TAG, "SLevel: " + AudioModeSetting.this.mCurrentMode + " " + AudioModeSetting.this.mTypeIndex + " " + AudioModeSetting.this.mLevelIndex);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            Elog.v(AudioModeSetting.TAG, "noting selected.");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        List<String> volLevelList;
        int i;
        int i2;
        int i3;
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        Intent intent = getIntent();
        this.mCurrentMode = intent.getIntExtra(Audio.CURRENT_MODE, 0);
        this.mSupportEnhance = intent.getBooleanExtra(Audio.ENHANCE_MODE, false);
        Elog.v(TAG, "mCurrentMode: " + this.mCurrentMode + "mSupportEnhance: " + this.mSupportEnhance);
        if (this.mSupportEnhance) {
            setContentView(R.layout.audio_modesetting_enhance);
            sMaxVolMode = 4;
            sMaxVolLevel = 15;
            sMaxVolType = 9;
            sTypeMedia = 7;
            sStructSize = (4 * 15 * 9) + 6 + 6 + 6 + 8 + 6 + 9;
            sSetCustomerData = 257;
            sGetCustomerData = 256;
            sModeSph2Index = 5;
            sModeSidIndex = 6;
            sOffSet = new int[]{4 * 15 * 0, 4 * 15 * 1, 4 * 15 * 2, 4 * 15 * 3, 4 * 15 * 4, 4 * 15 * 5, 4 * 15 * 6, 4 * 15 * 7, 4 * 15 * 8, 4 * 15 * 9};
        } else {
            setContentView(R.layout.audio_modesetting);
            if (AudioTuningJni.getAudioCommand(5) != sStructSize) {
                Elog.d(TAG, "assert! Check the structure size!");
                Toast.makeText(this, "Error: the structure size is error", 0).show();
                finish();
                return;
            }
        }
        int dataSize = sStructSize;
        byte[] bArr = new byte[dataSize];
        this.mData = bArr;
        Arrays.fill(bArr, 0, dataSize, (byte) 0);
        int ret = AudioTuningJni.getAudioData(sGetCustomerData, sStructSize, this.mData);
        if (ret != 0) {
            showDialog(1);
            Elog.i(TAG, "AudioModeSetting GetAudioData return value is : " + ret);
        }
        if (this.mSupportEnhance) {
            this.mRealUsageVols = new int[sMaxVolType];
            String debugStr = "[";
            int i4 = 0;
            while (true) {
                int i5 = sMaxVolType;
                if (i4 >= i5) {
                    break;
                }
                this.mRealUsageVols[(i5 - i4) - 1] = this.mData[(sStructSize - i4) - 1];
                debugStr = debugStr + this.mData[(sStructSize - i4) - 1] + ",";
                i4++;
            }
            Elog.d(TAG, "mRealUsageVols: " + debugStr + "]");
        }
        this.mBtnSet = (Button) findViewById(R.id.Audio_ModeSetting_Button);
        this.mValueEdit = (EditText) findViewById(R.id.Audio_ModeSetting_EditText);
        if (this.mSupportEnhance) {
            this.mBtnSetMaxVol = (Button) findViewById(R.id.Audio_MaxVol_Set_headset);
            this.mEditMaxVol = (EditText) findViewById(R.id.Audio_MaxVol_Edit_headset);
            this.mBtnSetMaxVolSpeaker = (Button) findViewById(R.id.Audio_MaxVol_Set_speaker);
            this.mEditMaxVolSpeaker = (EditText) findViewById(R.id.Audio_MaxVol_Edit_speaker);
        } else {
            this.mBtnSetMaxVol = (Button) findViewById(R.id.Audio_MaxVol_Set);
            this.mEditMaxVol = (EditText) findViewById(R.id.Audio_MaxVol_Edit);
        }
        this.mVolTypeSpinner = (Spinner) findViewById(R.id.Audio_ModeSetting);
        this.mVolLevelSpinner = (Spinner) findViewById(R.id.Audio_Level);
        this.mValText = (TextView) findViewById(R.id.Audio_ModeSetting_TextView);
        if (!this.mSupportEnhance) {
            this.mFirSpinner = (Spinner) findViewById(R.id.Audio_Fir_Spinner);
            this.mFirsummary = (TextView) findViewById(R.id.Audio_Fir_Title);
        } else if (this.mCurrentMode != 3) {
            ((TextView) findViewById(R.id.Audio_MaxVol_Tv_Max)).setText(R.string.Audio_MaxVol_Text);
            findViewById(R.id.Audio_MaxVol_Dual_Set).setVisibility(8);
        }
        ArrayList<String> adapterList = new ArrayList<>();
        int i6 = this.mCurrentMode;
        if (i6 == 0) {
            if (this.mSupportEnhance) {
                i3 = R.array.mode_type0_enh;
            } else {
                i3 = R.array.mode_type0;
            }
            adapterList.addAll(Arrays.asList(resources.getStringArray(i3)));
        } else if (i6 == 1) {
            if (this.mSupportEnhance) {
                i2 = R.array.mode_type1_enh;
            } else {
                i2 = R.array.mode_type1;
            }
            adapterList.addAll(Arrays.asList(resources.getStringArray(i2)));
        } else if (i6 == 2) {
            if (this.mSupportEnhance) {
                i = R.array.mode_type2_enh;
            } else {
                i = R.array.mode_type2;
            }
            adapterList.addAll(Arrays.asList(resources.getStringArray(i)));
        } else {
            adapterList.addAll(Arrays.asList(resources.getStringArray(R.array.mode_type3_enh)));
        }
        if (this.mSupportEnhance) {
            for (int i7 = adapterList.size() - 1; i7 >= 0; i7--) {
                String item = adapterList.get(i7);
                if (getString(R.string.audio_val_type_fmr).equals(item)) {
                    adapterList.remove(item);
                }
                if (getString(R.string.audio_val_type_matv).equals(item)) {
                    adapterList.remove(item);
                }
            }
        }
        ArrayAdapter<String> mTypeAdatper = new ArrayAdapter<>(this, 17367048, adapterList);
        mTypeAdatper.setDropDownViewResource(17367049);
        this.mVolTypeSpinner.setAdapter(mTypeAdatper);
        this.mVolTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AudioModeSetting.this.mValText.setText(R.string.Audio_ModeSetting_TextView);
                int unused = AudioModeSetting.this.mValueRange = 255;
                String itemName = arg0.getSelectedItem().toString();
                if (AudioModeSetting.this.mSupportEnhance) {
                    AudioModeSetting audioModeSetting = AudioModeSetting.this;
                    int unused2 = audioModeSetting.mTypeIndex = audioModeSetting.getVolumeTypeIndex(itemName);
                } else if (AudioModeSetting.this.mCurrentMode == 0) {
                    if (arg2 == 0 || arg2 == 1) {
                        int unused3 = AudioModeSetting.this.mTypeIndex = arg2 + 1;
                    } else {
                        int unused4 = AudioModeSetting.this.mTypeIndex = arg2 + 2;
                    }
                } else if (AudioModeSetting.this.mCurrentMode == 1) {
                    int unused5 = AudioModeSetting.this.mTypeIndex = arg2 + 1;
                } else {
                    int unused6 = AudioModeSetting.this.mTypeIndex = arg2;
                }
                Elog.d(AudioModeSetting.TAG, "mTypeIndex is:" + AudioModeSetting.this.mTypeIndex);
                if (AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeSphIndex || AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeSph2Index || AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeSidIndex || AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeMicIndex) {
                    AudioModeSetting.this.mEditMaxVol.setEnabled(false);
                    AudioModeSetting.this.mBtnSetMaxVol.setEnabled(false);
                    if (AudioModeSetting.this.mSupportEnhance) {
                        AudioModeSetting.this.mEditMaxVolSpeaker.setEnabled(false);
                        AudioModeSetting.this.mBtnSetMaxVolSpeaker.setEnabled(false);
                    }
                    if (AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeSphIndex || AudioModeSetting.this.mTypeIndex == AudioModeSetting.sModeSph2Index) {
                        AudioModeSetting.this.mValText.setText(R.string.text_tip);
                        int unused7 = AudioModeSetting.this.mValueRange = 160;
                    }
                } else {
                    AudioModeSetting.this.mEditMaxVol.setEnabled(true);
                    AudioModeSetting.this.mBtnSetMaxVol.setEnabled(true);
                    if (AudioModeSetting.this.mSupportEnhance) {
                        AudioModeSetting.this.mEditMaxVolSpeaker.setEnabled(true);
                        AudioModeSetting.this.mBtnSetMaxVolSpeaker.setEnabled(true);
                    }
                }
                EditText access$500 = AudioModeSetting.this.mValueEdit;
                AudioModeSetting audioModeSetting2 = AudioModeSetting.this;
                access$500.setText(String.valueOf(audioModeSetting2.getValue(audioModeSetting2.mData, AudioModeSetting.this.mCurrentMode, AudioModeSetting.this.mTypeIndex, AudioModeSetting.this.mLevelIndex)));
                AudioModeSetting.this.setMaxVolEdit();
                Elog.v(AudioModeSetting.TAG, "SMode: " + AudioModeSetting.this.mCurrentMode + " " + AudioModeSetting.this.mTypeIndex + " " + AudioModeSetting.this.mLevelIndex);
                if (AudioModeSetting.this.mSupportEnhance) {
                    Elog.d(AudioModeSetting.TAG, "itemName: " + itemName);
                    AudioModeSetting audioModeSetting3 = AudioModeSetting.this;
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(audioModeSetting3, 17367048, audioModeSetting3.getVolLevelList(itemName));
                    adapter.setDropDownViewResource(17367049);
                    AudioModeSetting.this.mVolLevelSpinner.setAdapter(adapter);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Elog.v(AudioModeSetting.TAG, "noting selected.");
            }
        });
        if (this.mSupportEnhance) {
            volLevelList = getVolLevelList(adapterList.get(0));
        } else {
            volLevelList = Arrays.asList(resources.getStringArray(R.array.mode_level));
        }
        ArrayAdapter<String> mLevelAdatper = new ArrayAdapter<>(this, 17367048, volLevelList);
        mLevelAdatper.setDropDownViewResource(17367049);
        this.mVolLevelSpinner.setAdapter(mLevelAdatper);
        this.mVolLevelSpinner.setOnItemSelectedListener(new ValLevelItemSelectListener());
        this.mBtnSet.setOnClickListener(this);
        this.mBtnSetMaxVol.setOnClickListener(this);
        if (this.mSupportEnhance) {
            this.mBtnSetMaxVolSpeaker.setOnClickListener(this);
        } else {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, resources.getStringArray(R.array.mode_fir));
            arrayAdapter.setDropDownViewResource(17367049);
            this.mFirSpinner.setAdapter(arrayAdapter);
            this.mFirSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                    if (AudioModeSetting.this.mIsFirstFirSet) {
                        AudioModeSetting.this.mFirsummary.setText(R.string.fir_text);
                        boolean unused = AudioModeSetting.this.mIsFirstFirSet = false;
                        return;
                    }
                    int ret = -1;
                    if (AudioModeSetting.this.mCurrentMode == 0) {
                        ret = AudioTuningJni.setAudioCommand(32, arg2);
                        Elog.v(AudioModeSetting.TAG, "set normal fir Z" + arg2);
                    } else if (AudioModeSetting.this.mCurrentMode == 1) {
                        ret = AudioTuningJni.setAudioCommand(33, arg2);
                        Elog.v(AudioModeSetting.TAG, "set headset fir Z" + arg2);
                    } else if (AudioModeSetting.this.mCurrentMode == 2) {
                        ret = AudioTuningJni.setAudioCommand(34, arg2);
                        Elog.v(AudioModeSetting.TAG, "set loudspeaker fir Z" + arg2);
                    }
                    if (-1 == ret) {
                        AudioModeSetting.this.mFirsummary.setText("FIR set error!");
                        Toast.makeText(AudioModeSetting.this, "Set error, check permission.", 1).show();
                        return;
                    }
                    TextView access$2300 = AudioModeSetting.this.mFirsummary;
                    access$2300.setText("Current selected: " + arg2);
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    Elog.v(AudioModeSetting.TAG, "noting selected.");
                }
            });
        }
        this.mValueEdit.setText(String.valueOf(getValue(this.mData, this.mCurrentMode, this.mTypeIndex, this.mLevelIndex)));
        setMaxVolEdit();
    }

    /* access modifiers changed from: private */
    public int getVolumeTypeIndex(String volTypeStr) {
        if (getString(R.string.audio_val_type_ring).equals(volTypeStr)) {
            return 0;
        }
        if (getString(R.string.audio_val_type_sip).equals(volTypeStr)) {
            return 1;
        }
        if (getString(R.string.audio_val_type_mic).equals(volTypeStr)) {
            return 2;
        }
        if (getString(R.string.audio_val_type_fmr).equals(volTypeStr)) {
            return 3;
        }
        if (getString(R.string.audio_val_type_sph).equals(volTypeStr)) {
            return 4;
        }
        if (getString(R.string.audio_val_type_sph2).equals(volTypeStr)) {
            return 5;
        }
        if (getString(R.string.audio_val_type_sid).equals(volTypeStr)) {
            return 6;
        }
        if (getString(R.string.audio_val_type_media).equals(volTypeStr)) {
            return 7;
        }
        if (getString(R.string.audio_val_type_matv).equals(volTypeStr)) {
            return 8;
        }
        return -1;
    }

    private int getUsageVol(String volTypeStr) {
        int[] iArr = this.mRealUsageVols;
        if (iArr == null || iArr.length != sMaxVolType) {
            throw new RuntimeException("Invalid mRealUsageVols");
        }
        int index = getVolumeTypeIndex(volTypeStr);
        if (index != -1) {
            return this.mRealUsageVols[index];
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public List<String> getVolLevelList(String volTypeStr) {
        List<String> list = new ArrayList<>();
        int usageVol = getUsageVol(volTypeStr);
        for (int i = 0; i < usageVol; i++) {
            list.add("Level " + i);
        }
        return list;
    }

    private void setValue(byte[] dataPara, int mode, int type, int level, byte val) {
        if (dataPara == null || mode >= sMaxVolMode || type >= sMaxVolType || level >= sMaxVolLevel) {
            Elog.d(TAG, "assert! Check the setting value.");
            return;
        }
        Elog.d(TAG, "setValue() mode:" + mode + ", type:" + type + "level:" + level);
        dataPara[(sMaxVolLevel * mode) + level + sOffSet[type]] = val;
    }

    /* access modifiers changed from: private */
    public int getValue(byte[] dataPara, int mode, int type, int level) {
        if (dataPara == null || mode >= sMaxVolMode || type >= sMaxVolType || level >= sMaxVolLevel) {
            Elog.d(TAG, "assert! Check the setting value.");
        }
        return dataPara[(sMaxVolLevel * mode) + level + sOffSet[type]] & 255;
    }

    /* access modifiers changed from: private */
    public void setMaxVolEdit() {
        Elog.i(TAG, "Set max vol Edit.");
        if (this.mSupportEnhance) {
            this.mEditMaxVol.setText(String.valueOf(getMaxValue(this.mData, this.mCurrentMode, false)));
            int i = this.mCurrentMode;
            if (i == 3) {
                this.mEditMaxVolSpeaker.setText(String.valueOf(getMaxValue(this.mData, i, true)));
                return;
            }
            return;
        }
        int i2 = this.mCurrentMode;
        if (i2 == 0) {
            this.mEditMaxVol.setText(String.valueOf(getValue(this.mData, 0, sTypeMedia, sModeSphIndex)));
            Elog.i(TAG, "0 is " + getValue(this.mData, 0, sTypeMedia, sModeSphIndex));
        } else if (i2 == 1) {
            this.mEditMaxVol.setText(String.valueOf(getValue(this.mData, 0, sTypeMedia, sModeSidIndex)));
            Elog.i(TAG, "1 is " + getValue(this.mData, 0, sTypeMedia, sModeSidIndex));
        } else if (i2 == 2) {
            EditText editText = this.mEditMaxVol;
            byte[] bArr = this.mData;
            int i3 = sTypeMedia;
            editText.setText(String.valueOf(getValue(bArr, 0, i3, i3)));
            StringBuilder sb = new StringBuilder();
            sb.append("2 is ");
            byte[] bArr2 = this.mData;
            int i4 = sTypeMedia;
            sb.append(getValue(bArr2, 0, i4, i4));
            Elog.i(TAG, sb.toString());
        } else {
            this.mEditMaxVol.setText("0");
            Elog.i(TAG, "error is 0");
        }
    }

    private void setMaxValue(byte[] dataPara, int mode, byte val, boolean dual) {
        if (dataPara == null || mode >= sMaxVolMode) {
            Elog.d(TAG, "assert! Check the setting value.");
        } else if (!dual || mode != 3) {
            dataPara[sOffSet[sMaxVolType] + (mode * 6)] = val;
        } else {
            dataPara[sOffSet[sMaxVolType] + (mode * 6) + 1] = val;
        }
    }

    private int getMaxValue(byte[] dataPara, int mode, boolean dual) {
        if (dataPara == null || mode >= sMaxVolMode) {
            Elog.d(TAG, "assert! Check the setting value.");
            return -1;
        } else if (!dual || mode != 3) {
            return dataPara[sOffSet[sMaxVolType] + (mode * 6)] & 255;
        } else {
            return dataPara[sOffSet[sMaxVolType] + (mode * 6) + 1] & 255;
        }
    }

    private void setMaxVolData(byte val) {
        int i = this.mCurrentMode;
        if (i == 0) {
            setValue(this.mData, 0, sTypeMedia, sModeSphIndex, val);
        } else if (i == 1) {
            setValue(this.mData, 0, sTypeMedia, sModeSidIndex, val);
        } else if (i == 2) {
            byte[] bArr = this.mData;
            int i2 = sTypeMedia;
            setValue(bArr, 0, i2, i2, val);
        }
    }

    private void setMaxVolData(byte val, boolean dual) {
        setMaxValue(this.mData, this.mCurrentMode, val, dual);
    }

    private void setAudioData() {
        int result = AudioTuningJni.setAudioData(sSetCustomerData, sStructSize, this.mData);
        int parameters = AudioSystem.setParameters("ReloadAudioVolume");
        if (result == 0 || -38 == result) {
            showDialog(2);
            return;
        }
        showDialog(3);
        Elog.i(TAG, "AudioModeSetting SetAudioData return value is : " + result);
    }

    private boolean checkEditNumber(EditText edit, int maxValue) {
        if (edit == null || edit.getText() == null) {
            Toast.makeText(this, getString(R.string.input_null_tip), 1).show();
            return false;
        }
        String editStr = edit.getText().toString();
        if (editStr == null || editStr.length() == 0) {
            Toast.makeText(this, getString(R.string.input_null_tip), 1).show();
            return false;
        }
        try {
            if (Integer.valueOf(editStr).intValue() <= maxValue) {
                return true;
            }
            Toast.makeText(this, getString(R.string.number_arrage_tip) + maxValue, 1).show();
            return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.number_arrage_tip) + maxValue, 1).show();
            return false;
        }
    }

    public void onClick(View arg0) {
        if (arg0.getId() == this.mBtnSet.getId()) {
            if (checkEditNumber(this.mValueEdit, this.mValueRange)) {
                setValue(this.mData, this.mCurrentMode, this.mTypeIndex, this.mLevelIndex, (byte) Integer.valueOf(this.mValueEdit.getText().toString()).intValue());
                setAudioData();
            }
        } else if (arg0.getId() == this.mBtnSetMaxVol.getId()) {
            if (checkEditNumber(this.mEditMaxVol, 160)) {
                byte editByte = (byte) Integer.valueOf(this.mEditMaxVol.getText().toString()).intValue();
                if (this.mSupportEnhance) {
                    setMaxVolData(editByte, false);
                } else {
                    setMaxVolData(editByte);
                }
                setAudioData();
            }
        } else if (arg0.getId() == this.mBtnSetMaxVolSpeaker.getId() && checkEditNumber(this.mEditMaxVolSpeaker, 160)) {
            setMaxVolData((byte) Integer.valueOf(this.mEditMaxVolSpeaker.getText().toString()).intValue(), true);
            setAudioData();
        }
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioModeSetting.this.removeDialog(1);
                        AudioModeSetting.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_success_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_error_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }
}
