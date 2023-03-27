package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.AudioVolumeTypeScene;
import java.util.ArrayList;
import java.util.Iterator;

public class AudioVolumePlayback extends Activity implements View.OnClickListener {
    private static final String CATEGORY_PLAYBACK = "Playback";
    private static final String CATEGORY_PLAYBACK_ANA = "PlaybackVolAna";
    private static final String CATEGORY_PLAYBACK_DIGI = "PlaybackVolDigi";
    private static final String CATEGORY_VOLUME = "Volume";
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int DGGAIN_MAX = 255;
    private static final int DGGAIN_MIN = 0;
    private static final int DIALOG_INIT_FAIL = 4;
    private static final int DIALOG_RESULT = 3;
    private static final String DIGI_GAIN = "digital_gain";
    private static final int HEADSET_MAX = 160;
    private static final int HEADSET_MIN = 88;
    private static final String HEADSET_PGA = "headset_pga";
    private static final int INVALID_RESULT = -1;
    private static final String LIST_DIVIDER = ",";
    private static final String PARAM_COMMON = "VolumeParam,Common";
    private static final String PARAM_DG_INDEX_MAX = "play_digi_range_max";
    private static final String PARAM_DG_INDEX_MIN = "play_digi_range_min";
    private static final String PARAM_DG_STEP = "dec_play_digi_step_per_db";
    private static final String PARAM_DG_VALUE_MAX = "dec_play_digi_max";
    private static final String PARAM_HS_LIST = "hs_ana_gain";
    private static final String PARAM_HS_STEP = "dec_play_hs_step_per_db";
    private static final String PARAM_HS_VALUE_MAX = "dec_play_hs_max";
    private static final String PARAM_SPK_LIST = "spk_ana_gain";
    private static final String PARAM_SPK_STEP = "dec_play_spk_step_per_db";
    private static final String PARAM_SPK_VALUE_MAX = "dec_play_spk_max";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    private static final int SPEAKER_MAX = 180;
    private static final int SPEAKER_NIN = 128;
    private static final String SPEAKER_PGA = "speaker_pga";
    private static final int SPECIAL_MAX = 256;
    public static final String TAG = "Audio/VolumePlayback";
    private static final String TYPE_PROFILE = "Profile";
    private static final String TYPE_VOLUME_TYPE = "Volume type";
    private static final String TYPE_VOLUME_TYPE_ALARM = "Alarm";
    private static final String TYPE_VOLUME_TYPE_OTHERS = "Others";
    private static final String TYPE_VOLUME_TYPE_RING = "Ring";
    private static final String TYPE_VOLUME_TYPE_RING_ALARM = "Ring_Alarm";
    private ArrayList<EditText> mArrayDlText;
    private String[] mArraySpinnerProfile;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerProfileValue;
    private String[] mArraySpinnerVolumeType;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerVolumeTypeValue;
    private AudioManager mAudioMgr = null;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public String mCurrentProfile;
    /* access modifiers changed from: private */
    public String mCurrentVolumeType;
    private int mDgMaxIndex;
    private int mDgMaxValue;
    private int mDgMinValue;
    private int mDgValueStep;
    private EditText mEditHeaset;
    private EditText mEditSpeaker;
    private boolean mHsAscending;
    private int mHsIndexStep;
    private int mHsMaxIndex;
    private int mHsMaxValue;
    private int mHsMinValue;
    private int mHsValueStep;
    private View mLayoutText1;
    private View mLayoutText2;
    private ArrayAdapter<String> mProfileAdatper;
    private Spinner mProfileSpinner;
    private boolean mSpkAscending;
    private int mSpkIndexStep;
    private int mSpkMaxIndex;
    private int mSpkMaxValue;
    private int mSpkMinValue;
    private int mSpkValueStep;
    private String mStrErrorInfo;
    private TableLayout mTableLayout;
    private TextView mTableTitleText;
    private AudioVolumeTypeScene mTypeScene;
    private ArrayAdapter<String> mVolumeTypeAdatper;
    private Spinner mVolumeTypeSpinner;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_volume_item);
        this.mAudioMgr = (AudioManager) getSystemService("audio");
        this.mTypeScene = new AudioVolumeTypeScene(new AudioVolumeTypeScene.Listener() {
            public void onSceneChanged() {
                AudioVolumePlayback.this.updateValue();
            }
        });
        initComponents();
    }

    private void initComponents() {
        this.mLayoutText1 = findViewById(R.id.layout_text1);
        this.mLayoutText2 = findViewById(R.id.layout_text2);
        this.mArrayDlText = new ArrayList<>();
        Button button = (Button) findViewById(R.id.btn_set);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        this.mVolumeTypeSpinner = (Spinner) findViewById(R.id.audio_volume_spinner1);
        String strSpinner1 = AudioTuningJni.getCategory(CATEGORY_PLAYBACK, TYPE_VOLUME_TYPE);
        Elog.d(TAG, "strSpinner1:" + strSpinner1);
        if (strSpinner1 != null) {
            String[] value = strSpinner1.split(LIST_DIVIDER);
            int length = value.length / 2;
            if (length > 0) {
                this.mArraySpinnerVolumeType = new String[length];
                this.mArraySpinnerVolumeTypeValue = new String[length];
                for (int k = 0; k < length; k++) {
                    this.mArraySpinnerVolumeTypeValue[k] = value[k * 2];
                    this.mArraySpinnerVolumeType[k] = value[(k * 2) + 1];
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mArraySpinnerVolumeType);
                this.mVolumeTypeAdatper = arrayAdapter;
                arrayAdapter.setDropDownViewResource(17367049);
                this.mVolumeTypeSpinner.setAdapter(this.mVolumeTypeAdatper);
                this.mCurrentVolumeType = this.mArraySpinnerVolumeTypeValue[0];
                this.mVolumeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        if (AudioVolumePlayback.this.mCurrentVolumeType != AudioVolumePlayback.this.mArraySpinnerVolumeTypeValue[arg2]) {
                            AudioVolumePlayback audioVolumePlayback = AudioVolumePlayback.this;
                            String unused = audioVolumePlayback.mCurrentVolumeType = audioVolumePlayback.mArraySpinnerVolumeTypeValue[arg2];
                            AudioVolumePlayback.this.updateValue();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mProfileSpinner = (Spinner) findViewById(R.id.audio_volume_spinner2);
                String strSpinner2 = AudioTuningJni.getCategory(CATEGORY_PLAYBACK, TYPE_PROFILE);
                Elog.d(TAG, "strSpinner2:" + strSpinner2);
                if (strSpinner2 != null) {
                    String[] value2 = strSpinner2.split(LIST_DIVIDER);
                    int length2 = value2.length / 2;
                    if (length2 > 0) {
                        this.mArraySpinnerProfile = new String[length2];
                        this.mArraySpinnerProfileValue = new String[length2];
                        for (int k2 = 0; k2 < length2; k2++) {
                            this.mArraySpinnerProfileValue[k2] = value2[k2 * 2];
                            this.mArraySpinnerProfile[k2] = value2[(k2 * 2) + 1];
                        }
                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, 17367048, this.mArraySpinnerProfile);
                        this.mProfileAdatper = arrayAdapter2;
                        arrayAdapter2.setDropDownViewResource(17367049);
                        this.mProfileSpinner.setAdapter(this.mProfileAdatper);
                        this.mCurrentProfile = this.mArraySpinnerProfileValue[0];
                        this.mProfileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                                if (AudioVolumePlayback.this.mCurrentProfile != AudioVolumePlayback.this.mArraySpinnerProfileValue[arg2]) {
                                    AudioVolumePlayback audioVolumePlayback = AudioVolumePlayback.this;
                                    String unused = audioVolumePlayback.mCurrentProfile = audioVolumePlayback.mArraySpinnerProfileValue[arg2];
                                    AudioVolumePlayback.this.updateValue();
                                }
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        this.mTypeScene.initSceneSpinner(this, CATEGORY_PLAYBACK);
                        ((TextView) findViewById(R.id.audio_volume_text1)).setText(R.string.audio_volume_heaset_pga);
                        this.mEditHeaset = (EditText) findViewById(R.id.audio_volume_edittext1);
                        ((TextView) findViewById(R.id.audio_volume_text2)).setText(R.string.audio_volume_speaker_pga);
                        this.mEditSpeaker = (EditText) findViewById(R.id.audio_volume_edittext2);
                        this.mTableLayout = (TableLayout) findViewById(R.id.dl_table);
                        TextView textView = (TextView) findViewById(R.id.table_title);
                        this.mTableTitleText = textView;
                        textView.setText(R.string.audio_volume_digital_gain);
                        initTableValue();
                        updateValue();
                    }
                }
            }
        }
    }

    private void initTableValue() {
        String[] arrayIndex;
        try {
            String strMaxValue = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_DG_VALUE_MAX);
            String strMaxIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_DG_INDEX_MAX);
            String strMinIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_DG_INDEX_MIN);
            String strValueStep = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_DG_STEP);
            this.mDgMaxValue = Integer.parseInt(strMaxValue);
            this.mDgMaxIndex = Integer.parseInt(strMaxIndex);
            this.mDgValueStep = Integer.parseInt(strValueStep);
            this.mDgMinValue = this.mDgMaxValue - (this.mDgValueStep * (this.mDgMaxIndex - Integer.parseInt(strMinIndex)));
            Elog.d(TAG, "Init UL gain table: mUlMaxValue " + this.mDgMaxValue + " mUlMinValue " + this.mDgMinValue + " mUlMaxIndex " + this.mDgMaxIndex + " mUlValueStep " + this.mDgValueStep);
            String strMaxValue2 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_HS_VALUE_MAX);
            String strValueStep2 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_HS_STEP);
            String strIndexList = AudioTuningJni.getChecklist(CATEGORY_PLAYBACK_ANA, HEADSET_PGA, PARAM_HS_LIST);
            String[] arrayIndex2 = null;
            if (strIndexList != null) {
                arrayIndex2 = strIndexList.trim().split(LIST_DIVIDER);
            }
            if (arrayIndex2 == null) {
                String str = strMaxValue2;
            } else if (arrayIndex2.length < 3) {
                String str2 = strMaxValue2;
            } else {
                this.mHsMaxValue = Integer.parseInt(strMaxValue2);
                this.mHsMaxIndex = Integer.parseInt(arrayIndex2[0]);
                this.mHsValueStep = Integer.parseInt(strValueStep2);
                int i = this.mHsMaxIndex;
                int secondIndex = Integer.parseInt(arrayIndex2[2]);
                this.mHsAscending = i < secondIndex;
                this.mHsIndexStep = Math.abs(secondIndex - i);
                int i2 = secondIndex;
                String str3 = strMaxValue2;
                this.mHsMinValue = this.mHsMaxValue - (this.mHsValueStep * Math.abs(Integer.parseInt(arrayIndex2[arrayIndex2.length - 2]) - this.mHsMaxIndex));
                Elog.d(TAG, "Init Headset table: mHsMaxValue " + this.mHsMaxValue + " mHsMinValue " + this.mHsMinValue + " mHsMaxIndex " + this.mHsMaxIndex + " mHsValueStep " + this.mHsValueStep + " mHsIndexStep " + this.mHsIndexStep);
                String strMaxValue3 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_SPK_VALUE_MAX);
                String strValueStep3 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_SPK_STEP);
                String strIndexList2 = AudioTuningJni.getChecklist(CATEGORY_PLAYBACK_ANA, SPEAKER_PGA, PARAM_SPK_LIST);
                if (strIndexList2 != null) {
                    arrayIndex = strIndexList2.trim().split(LIST_DIVIDER);
                } else {
                    arrayIndex = null;
                }
                if (arrayIndex != null) {
                    if (arrayIndex.length >= 3) {
                        String strMaxIndex2 = arrayIndex[0];
                        this.mSpkMaxValue = Integer.parseInt(strMaxValue3);
                        this.mSpkMaxIndex = Integer.parseInt(strMaxIndex2);
                        this.mSpkValueStep = Integer.parseInt(strValueStep3);
                        int secondIndex2 = Integer.parseInt(arrayIndex[2]);
                        int i3 = this.mSpkMaxIndex;
                        this.mSpkAscending = i3 < secondIndex2;
                        this.mSpkIndexStep = Math.abs(i3 - secondIndex2);
                        this.mSpkMinValue = this.mSpkMaxValue - (this.mSpkValueStep * Math.abs(this.mSpkMaxIndex - Integer.parseInt(arrayIndex[arrayIndex.length - 2])));
                        Elog.d(TAG, "Init Speaker table: mSpkMaxValue " + this.mSpkMaxValue + " mSpkMinValue " + this.mSpkMinValue + " mStfMaxIndex " + this.mSpkMaxIndex + " mSpkValueStep " + this.mSpkValueStep + " mSpkIndexStep " + this.mSpkIndexStep);
                        return;
                    }
                }
                removeDialog(4);
                showDialog(4);
                return;
            }
            removeDialog(4);
            showDialog(4);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "initTableValue wrong format", 1).show();
        }
    }

    /* access modifiers changed from: private */
    public void updateValue() {
        String valueSpeaker;
        String valueHeadset;
        if (TYPE_VOLUME_TYPE_RING.equals(this.mCurrentVolumeType) || TYPE_VOLUME_TYPE_ALARM.equals(this.mCurrentVolumeType)) {
            AudioVolumeTypeScene audioVolumeTypeScene = this.mTypeScene;
            valueHeadset = AudioTuningJni.getParams(CATEGORY_PLAYBACK_ANA, audioVolumeTypeScene.getPara2String("Volume type,Ring_Alarm,Profile," + this.mCurrentProfile), HEADSET_PGA);
            AudioVolumeTypeScene audioVolumeTypeScene2 = this.mTypeScene;
            valueSpeaker = AudioTuningJni.getParams(CATEGORY_PLAYBACK_ANA, audioVolumeTypeScene2.getPara2String("Volume type,Ring_Alarm,Profile," + this.mCurrentProfile), SPEAKER_PGA);
        } else {
            AudioVolumeTypeScene audioVolumeTypeScene3 = this.mTypeScene;
            valueHeadset = AudioTuningJni.getParams(CATEGORY_PLAYBACK_ANA, audioVolumeTypeScene3.getPara2String("Volume type,Others,Profile," + this.mCurrentProfile), HEADSET_PGA);
            AudioVolumeTypeScene audioVolumeTypeScene4 = this.mTypeScene;
            valueSpeaker = AudioTuningJni.getParams(CATEGORY_PLAYBACK_ANA, audioVolumeTypeScene4.getPara2String("Volume type,Others,Profile," + this.mCurrentProfile), SPEAKER_PGA);
        }
        Elog.d(TAG, "valueHeadset:" + valueHeadset + " valueSpeaker: " + valueSpeaker);
        try {
            int value = Integer.parseInt(valueHeadset);
            if (value == -1) {
                this.mLayoutText1.setVisibility(8);
            } else {
                int value2 = this.mHsMaxValue - (this.mHsValueStep * Math.abs(value - this.mHsMaxIndex));
                if (value2 <= this.mHsMaxValue) {
                    if (value2 >= this.mHsMinValue) {
                        this.mEditHeaset.setText(String.valueOf(value2));
                        this.mLayoutText1.setVisibility(0);
                    }
                }
                this.mLayoutText1.setVisibility(8);
            }
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            this.mLayoutText1.setVisibility(8);
        }
        try {
            int value3 = Integer.parseInt(valueSpeaker);
            if (value3 == -1) {
                this.mLayoutText2.setVisibility(8);
            } else {
                int value4 = this.mSpkMaxValue - (this.mSpkValueStep * Math.abs(this.mSpkMaxIndex - value3));
                if (value4 <= this.mSpkMaxValue) {
                    if (value4 >= this.mSpkMinValue) {
                        this.mEditSpeaker.setText(String.valueOf(value4));
                        this.mLayoutText2.setVisibility(0);
                    }
                }
                this.mLayoutText2.setVisibility(8);
            }
        } catch (NumberFormatException exception2) {
            exception2.printStackTrace();
            this.mLayoutText2.setVisibility(8);
        }
        AudioVolumeTypeScene audioVolumeTypeScene5 = this.mTypeScene;
        String strDigiGain = AudioTuningJni.getParams(CATEGORY_PLAYBACK_DIGI, audioVolumeTypeScene5.getPara2String("Volume type," + this.mCurrentVolumeType + LIST_DIVIDER + TYPE_PROFILE + LIST_DIVIDER + this.mCurrentProfile), DIGI_GAIN);
        this.mArrayDlText.clear();
        this.mTableLayout.removeAllViews();
        if (strDigiGain == null) {
            this.mTableTitleText.setVisibility(8);
            return;
        }
        this.mTableTitleText.setVisibility(0);
        String[] arrayDigi = strDigiGain.split(LIST_DIVIDER);
        int size = arrayDigi.length;
        for (int k = 0; k < size; k++) {
            TableRow row = new TableRow(this);
            TextView textView = new TextView(this);
            EditText editText = new EditText(this);
            textView.setText("Index " + k);
            try {
                int index = Integer.parseInt(arrayDigi[k]);
                int i = this.mDgMaxValue;
                int i2 = this.mDgValueStep;
                int i3 = this.mDgMaxIndex;
                int value5 = i - (i2 * (i3 - index));
                if (index == i3 && value5 == 256) {
                    value5 = 255;
                }
                editText.setText(String.valueOf(value5));
                editText.setInputType(2);
                textView.setPadding(0, 3, 3, 3);
                editText.setPadding(20, 3, 3, 3);
                this.mArrayDlText.add(editText);
                row.addView(textView);
                row.addView(editText);
                this.mTableLayout.addView(row);
            } catch (NumberFormatException exception3) {
                exception3.printStackTrace();
            }
        }
    }

    public void onClick(View v) {
        if (v.equals(this.mBtnSet)) {
            if (!FeatureSupport.isEngLoad()) {
                String check = this.mAudioMgr.getParameters(CUST_XML_PARAM);
                if (check == null || !RESULT_SUPPORT.equals(check)) {
                    Elog.d(TAG, "set CUST_XML_PARAM = 1");
                    this.mAudioMgr.setParameters(CUST_XML_SET_SUPPORT_PARAM);
                    AudioTuningJni.CustXmlEnableChanged(1);
                } else {
                    Elog.d(TAG, "get CUST_XML_PARAM = 1");
                }
            }
            this.mStrErrorInfo = new String();
            setDigiGain();
            setSpeakerPga();
            setHeadsetPga();
            removeDialog(3);
            showDialog(3);
        }
    }

    private void setDigiGain() {
        String strValueDigiGain = new String();
        Iterator<EditText> it = this.mArrayDlText.iterator();
        while (it.hasNext()) {
            EditText editText = it.next();
            if (editText == null || editText.getText() == null) {
                this.mStrErrorInfo += getString(R.string.audio_volume_error_digi_empty);
                return;
            }
            try {
                int value = Integer.parseInt(editText.getText().toString().trim());
                int i = this.mDgMaxValue;
                if (value <= i) {
                    if (value >= this.mDgMinValue) {
                        if (i == 256 && value == i) {
                            value++;
                        }
                        int index = this.mDgMaxIndex - ((i - value) / this.mDgValueStep);
                        if (strValueDigiGain.length() == 0) {
                            strValueDigiGain = strValueDigiGain + index;
                        } else {
                            strValueDigiGain = strValueDigiGain + LIST_DIVIDER + index;
                        }
                    }
                }
                this.mStrErrorInfo += getString(R.string.audio_volume_error_digital_gain_invalid, new Object[]{Integer.valueOf(this.mDgMinValue), Integer.valueOf(this.mDgMaxValue)});
                return;
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
                this.mStrErrorInfo += getString(R.string.audio_volume_error_ditigain_format);
                return;
            }
        }
        if (!AudioTuningJni.setParams(CATEGORY_PLAYBACK_DIGI, this.mTypeScene.getPara2String("Volume type," + this.mCurrentVolumeType + LIST_DIVIDER + TYPE_PROFILE + LIST_DIVIDER + this.mCurrentProfile), DIGI_GAIN, strValueDigiGain) || !AudioTuningJni.saveToWork(CATEGORY_PLAYBACK_DIGI)) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_set_ditigain);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d2 A[Catch:{ NumberFormatException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[Catch:{ NumberFormatException -> 0x011b }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setSpeakerPga() {
        /*
            r9 = this;
            android.view.View r0 = r9.mLayoutText2
            int r0 = r0.getVisibility()
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            android.widget.EditText r0 = r9.mEditHeaset
            r1 = 1
            r2 = 0
            r3 = 2
            if (r0 == 0) goto L_0x0016
            android.text.Editable r0 = r0.getText()
            if (r0 != 0) goto L_0x0042
        L_0x0016:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = r9.mStrErrorInfo
            r0.append(r4)
            r4 = 2131362059(0x7f0a010b, float:1.8343888E38)
            java.lang.Object[] r5 = new java.lang.Object[r3]
            int r6 = r9.mHsMinValue
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r5[r2] = r6
            int r6 = r9.mHsMaxValue
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r5[r1] = r6
            java.lang.String r4 = r9.getString(r4, r5)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            r9.mStrErrorInfo = r0
        L_0x0042:
            android.widget.EditText r0 = r9.mEditSpeaker
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            int r4 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x011b }
            int r5 = r9.mSpkMaxValue     // Catch:{ NumberFormatException -> 0x011b }
            if (r4 > r5) goto L_0x00ee
            int r6 = r9.mSpkMinValue     // Catch:{ NumberFormatException -> 0x011b }
            if (r4 >= r6) goto L_0x005a
            goto L_0x00ee
        L_0x005a:
            int r5 = r5 - r4
            int r1 = r9.mSpkValueStep     // Catch:{ NumberFormatException -> 0x011b }
            int r5 = r5 / r1
            r1 = r5
            int r2 = r9.mSpkIndexStep     // Catch:{ NumberFormatException -> 0x011b }
            int r3 = r1 / r2
            int r3 = r3 * r2
            boolean r1 = r9.mSpkAscending     // Catch:{ NumberFormatException -> 0x011b }
            if (r1 == 0) goto L_0x006c
            int r1 = r9.mSpkMaxIndex     // Catch:{ NumberFormatException -> 0x011b }
            int r1 = r1 + r3
            goto L_0x006f
        L_0x006c:
            int r1 = r9.mSpkMaxIndex     // Catch:{ NumberFormatException -> 0x011b }
            int r1 = r1 - r3
        L_0x006f:
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ NumberFormatException -> 0x011b }
            r3 = 0
            java.lang.String r4 = "Ring"
            java.lang.String r5 = r9.mCurrentVolumeType     // Catch:{ NumberFormatException -> 0x011b }
            boolean r4 = r4.equals(r5)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r5 = "speaker_pga"
            java.lang.String r6 = "PlaybackVolAna"
            if (r4 != 0) goto L_0x00ac
            java.lang.String r4 = "Alarm"
            java.lang.String r7 = r9.mCurrentVolumeType     // Catch:{ NumberFormatException -> 0x011b }
            boolean r4 = r4.equals(r7)     // Catch:{ NumberFormatException -> 0x011b }
            if (r4 == 0) goto L_0x008d
            goto L_0x00ac
        L_0x008d:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r4 = r9.mTypeScene     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x011b }
            r7.<init>()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r8 = "Volume type,Others,Profile,"
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r8 = r9.mCurrentProfile     // Catch:{ NumberFormatException -> 0x011b }
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r4 = r4.getPara2String(r7)     // Catch:{ NumberFormatException -> 0x011b }
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.setParams(r6, r4, r5, r2)     // Catch:{ NumberFormatException -> 0x011b }
            r3 = r4
            goto L_0x00ca
        L_0x00ac:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r4 = r9.mTypeScene     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x011b }
            r7.<init>()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r8 = "Volume type,Ring_Alarm,Profile,"
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r8 = r9.mCurrentProfile     // Catch:{ NumberFormatException -> 0x011b }
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r4 = r4.getPara2String(r7)     // Catch:{ NumberFormatException -> 0x011b }
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.setParams(r6, r4, r5, r2)     // Catch:{ NumberFormatException -> 0x011b }
            r3 = r4
        L_0x00ca:
            if (r3 == 0) goto L_0x00d2
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.saveToWork(r6)     // Catch:{ NumberFormatException -> 0x011b }
            if (r4 != 0) goto L_0x00ec
        L_0x00d2:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x011b }
            r4.<init>()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r5 = r9.mStrErrorInfo     // Catch:{ NumberFormatException -> 0x011b }
            r4.append(r5)     // Catch:{ NumberFormatException -> 0x011b }
            r5 = 2131362064(0x7f0a0110, float:1.8343898E38)
            java.lang.String r5 = r9.getString(r5)     // Catch:{ NumberFormatException -> 0x011b }
            r4.append(r5)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r4 = r4.toString()     // Catch:{ NumberFormatException -> 0x011b }
            r9.mStrErrorInfo = r4     // Catch:{ NumberFormatException -> 0x011b }
        L_0x00ec:
            return
        L_0x00ee:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x011b }
            r5.<init>()     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r6 = r9.mStrErrorInfo     // Catch:{ NumberFormatException -> 0x011b }
            r5.append(r6)     // Catch:{ NumberFormatException -> 0x011b }
            r6 = 2131362068(0x7f0a0114, float:1.8343906E38)
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ NumberFormatException -> 0x011b }
            int r7 = r9.mSpkMinValue     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ NumberFormatException -> 0x011b }
            r3[r2] = r7     // Catch:{ NumberFormatException -> 0x011b }
            int r2 = r9.mSpkMaxValue     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x011b }
            r3[r1] = r2     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r1 = r9.getString(r6, r3)     // Catch:{ NumberFormatException -> 0x011b }
            r5.append(r1)     // Catch:{ NumberFormatException -> 0x011b }
            java.lang.String r1 = r5.toString()     // Catch:{ NumberFormatException -> 0x011b }
            r9.mStrErrorInfo = r1     // Catch:{ NumberFormatException -> 0x011b }
            return
        L_0x011b:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r9.mStrErrorInfo
            r2.append(r3)
            r3 = 2131362067(0x7f0a0113, float:1.8343904E38)
            java.lang.String r3 = r9.getString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r9.mStrErrorInfo = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.audio.AudioVolumePlayback.setSpeakerPga():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ab A[Catch:{ NumberFormatException -> 0x00f1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[Catch:{ NumberFormatException -> 0x00f1 }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setHeadsetPga() {
        /*
            r9 = this;
            android.view.View r0 = r9.mLayoutText1
            int r0 = r0.getVisibility()
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            android.widget.EditText r0 = r9.mEditHeaset
            r1 = 1
            r2 = 0
            r3 = 2
            r4 = 2131362059(0x7f0a010b, float:1.8343888E38)
            if (r0 == 0) goto L_0x0110
            android.text.Editable r0 = r0.getText()
            if (r0 != 0) goto L_0x001b
            goto L_0x0110
        L_0x001b:
            android.widget.EditText r0 = r9.mEditHeaset
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            int r5 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x00f1 }
            int r6 = r9.mHsMaxValue     // Catch:{ NumberFormatException -> 0x00f1 }
            if (r5 > r6) goto L_0x00c7
            int r7 = r9.mHsMinValue     // Catch:{ NumberFormatException -> 0x00f1 }
            if (r5 >= r7) goto L_0x0033
            goto L_0x00c7
        L_0x0033:
            int r6 = r6 - r5
            int r1 = r9.mHsValueStep     // Catch:{ NumberFormatException -> 0x00f1 }
            int r6 = r6 / r1
            r1 = r6
            int r2 = r9.mHsIndexStep     // Catch:{ NumberFormatException -> 0x00f1 }
            int r3 = r1 / r2
            int r3 = r3 * r2
            boolean r1 = r9.mHsAscending     // Catch:{ NumberFormatException -> 0x00f1 }
            if (r1 == 0) goto L_0x0045
            int r1 = r9.mHsMaxIndex     // Catch:{ NumberFormatException -> 0x00f1 }
            int r1 = r1 + r3
            goto L_0x0048
        L_0x0045:
            int r1 = r9.mHsMaxIndex     // Catch:{ NumberFormatException -> 0x00f1 }
            int r1 = r1 - r3
        L_0x0048:
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ NumberFormatException -> 0x00f1 }
            r3 = 0
            java.lang.String r4 = "Ring"
            java.lang.String r5 = r9.mCurrentVolumeType     // Catch:{ NumberFormatException -> 0x00f1 }
            boolean r4 = r4.equals(r5)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r5 = "headset_pga"
            java.lang.String r6 = "PlaybackVolAna"
            if (r4 != 0) goto L_0x0085
            java.lang.String r4 = "Alarm"
            java.lang.String r7 = r9.mCurrentVolumeType     // Catch:{ NumberFormatException -> 0x00f1 }
            boolean r4 = r4.equals(r7)     // Catch:{ NumberFormatException -> 0x00f1 }
            if (r4 == 0) goto L_0x0066
            goto L_0x0085
        L_0x0066:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r4 = r9.mTypeScene     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00f1 }
            r7.<init>()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r8 = "Volume type,Others,Profile,"
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r8 = r9.mCurrentProfile     // Catch:{ NumberFormatException -> 0x00f1 }
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r4 = r4.getPara2String(r7)     // Catch:{ NumberFormatException -> 0x00f1 }
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.setParams(r6, r4, r5, r2)     // Catch:{ NumberFormatException -> 0x00f1 }
            r3 = r4
            goto L_0x00a3
        L_0x0085:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r4 = r9.mTypeScene     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00f1 }
            r7.<init>()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r8 = "Volume type,Ring_Alarm,Profile,"
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r8 = r9.mCurrentProfile     // Catch:{ NumberFormatException -> 0x00f1 }
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r4 = r4.getPara2String(r7)     // Catch:{ NumberFormatException -> 0x00f1 }
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.setParams(r6, r4, r5, r2)     // Catch:{ NumberFormatException -> 0x00f1 }
            r3 = r4
        L_0x00a3:
            if (r3 == 0) goto L_0x00ab
            boolean r4 = com.mediatek.engineermode.audio.AudioTuningJni.saveToWork(r6)     // Catch:{ NumberFormatException -> 0x00f1 }
            if (r4 != 0) goto L_0x00c5
        L_0x00ab:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00f1 }
            r4.<init>()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r5 = r9.mStrErrorInfo     // Catch:{ NumberFormatException -> 0x00f1 }
            r4.append(r5)     // Catch:{ NumberFormatException -> 0x00f1 }
            r5 = 2131362063(0x7f0a010f, float:1.8343896E38)
            java.lang.String r5 = r9.getString(r5)     // Catch:{ NumberFormatException -> 0x00f1 }
            r4.append(r5)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r4 = r4.toString()     // Catch:{ NumberFormatException -> 0x00f1 }
            r9.mStrErrorInfo = r4     // Catch:{ NumberFormatException -> 0x00f1 }
        L_0x00c5:
            return
        L_0x00c7:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00f1 }
            r6.<init>()     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r7 = r9.mStrErrorInfo     // Catch:{ NumberFormatException -> 0x00f1 }
            r6.append(r7)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ NumberFormatException -> 0x00f1 }
            int r7 = r9.mHsMinValue     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ NumberFormatException -> 0x00f1 }
            r3[r2] = r7     // Catch:{ NumberFormatException -> 0x00f1 }
            int r2 = r9.mHsMaxValue     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x00f1 }
            r3[r1] = r2     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r1 = r9.getString(r4, r3)     // Catch:{ NumberFormatException -> 0x00f1 }
            r6.append(r1)     // Catch:{ NumberFormatException -> 0x00f1 }
            java.lang.String r1 = r6.toString()     // Catch:{ NumberFormatException -> 0x00f1 }
            r9.mStrErrorInfo = r1     // Catch:{ NumberFormatException -> 0x00f1 }
            return
        L_0x00f1:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r9.mStrErrorInfo
            r2.append(r3)
            r3 = 2131362058(0x7f0a010a, float:1.8343886E38)
            java.lang.String r3 = r9.getString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r9.mStrErrorInfo = r2
            return
        L_0x0110:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = r9.mStrErrorInfo
            r0.append(r5)
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r5 = r9.mHsMinValue
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r2] = r5
            int r2 = r9.mHsMaxValue
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r1] = r2
            java.lang.String r1 = r9.getString(r4, r3)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r9.mStrErrorInfo = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.audio.AudioVolumePlayback.setHeadsetPga():void");
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 3:
                String str = this.mStrErrorInfo;
                if (str == null || str.isEmpty()) {
                    return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.audio_volume_set_success).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                }
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(this.mStrErrorInfo).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
            case 4:
                return new AlertDialog.Builder(this).setTitle(R.string.init_error_title).setMessage(R.string.init_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
            default:
                return null;
        }
    }
}
