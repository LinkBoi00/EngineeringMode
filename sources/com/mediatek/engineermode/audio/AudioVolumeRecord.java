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
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.AudioVolumeTypeScene;

public class AudioVolumeRecord extends Activity implements View.OnClickListener {
    private static final String CATEGORY_GAINMAP = "VolumeGainMap";
    private static final String CATEGORY_RECORD = "Record";
    private static final String CATEGORY_RECORDVOL = "RecordVol";
    private static final String CATEGORY_VOLUME = "Volume";
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int DIALOG_RESULT = 3;
    private static final String DL_GAIN = "dl_gain";
    private static final String DL_MAP = "dl_total_gain_decimal";
    private static final String PARAM_COMMON = "VolumeParam,Common";
    private static final String PARAM_UL_INDEX_MAX = "mic_idx_range_max";
    private static final String PARAM_UL_INDEX_MIN = "mic_idx_range_min";
    private static final String PARAM_UL_STEP = "dec_rec_step_per_db";
    private static final String PARAM_UL_VALUE_MAX = "dec_rec_max";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    private static final String STF_GAIN = "stf_gain";
    public static final String TAG = "Audio/VolumeRecord";
    private static final String TYPE_APP = "Application";
    private static final String TYPE_PROFILE = "Profile";
    private static final String UL_GAIN = "ul_gain";
    private ArrayAdapter<String> mAppAdatper;
    private Spinner mAppSpinner;
    private String[] mArrayDlTable;
    private String[] mArraySpinnerApp;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerAppValue;
    private String[] mArraySpinnerProfile;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerProfileValue;
    private AudioManager mAudioMgr = null;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public String mCurrentApp;
    /* access modifiers changed from: private */
    public String mCurrentProfile;
    private EditText mEditUlGain;
    private View mLayoutText1;
    private ArrayAdapter<String> mProfileAdatper;
    private Spinner mProfileSpinner;
    private String mStrErrorInfo;
    private TableLayout mTableLayout;
    private AudioVolumeTypeScene mTypeScene;
    private int mUlMaxIndex;
    private int mUlMaxValue;
    private int mUlMinValue;
    private int mUlValueStep;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_volume_item);
        this.mAudioMgr = (AudioManager) getSystemService("audio");
        this.mTypeScene = new AudioVolumeTypeScene(new AudioVolumeTypeScene.Listener() {
            public void onSceneChanged() {
                AudioVolumeRecord.this.updateValue();
            }
        });
        initComponents();
    }

    private void initComponents() {
        this.mLayoutText1 = findViewById(R.id.layout_text1);
        Button button = (Button) findViewById(R.id.btn_set);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        this.mAppSpinner = (Spinner) findViewById(R.id.audio_volume_spinner1);
        String strSpinner1 = AudioTuningJni.getCategory(CATEGORY_RECORDVOL, TYPE_APP);
        Elog.d(TAG, "strSpinner1:" + strSpinner1);
        if (strSpinner1 != null) {
            String[] value = strSpinner1.split(",");
            int length = value.length / 2;
            if (length > 0) {
                this.mArraySpinnerApp = new String[length];
                this.mArraySpinnerAppValue = new String[length];
                for (int k = 0; k < length; k++) {
                    this.mArraySpinnerAppValue[k] = value[k * 2];
                    this.mArraySpinnerApp[k] = value[(k * 2) + 1];
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mArraySpinnerApp);
                this.mAppAdatper = arrayAdapter;
                arrayAdapter.setDropDownViewResource(17367049);
                this.mAppSpinner.setAdapter(this.mAppAdatper);
                this.mCurrentApp = this.mArraySpinnerAppValue[0];
                this.mAppSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        if (AudioVolumeRecord.this.mCurrentApp != AudioVolumeRecord.this.mArraySpinnerAppValue[arg2]) {
                            AudioVolumeRecord audioVolumeRecord = AudioVolumeRecord.this;
                            String unused = audioVolumeRecord.mCurrentApp = audioVolumeRecord.mArraySpinnerAppValue[arg2];
                            AudioVolumeRecord.this.updateValue();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mProfileSpinner = (Spinner) findViewById(R.id.audio_volume_spinner2);
                String strSpinner2 = AudioTuningJni.getCategory(CATEGORY_RECORDVOL, TYPE_PROFILE);
                Elog.d(TAG, "strSpinner2:" + strSpinner2);
                if (strSpinner2 != null) {
                    String[] value2 = strSpinner2.split(",");
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
                                if (AudioVolumeRecord.this.mCurrentProfile != AudioVolumeRecord.this.mArraySpinnerProfileValue[arg2]) {
                                    AudioVolumeRecord audioVolumeRecord = AudioVolumeRecord.this;
                                    String unused = audioVolumeRecord.mCurrentProfile = audioVolumeRecord.mArraySpinnerProfileValue[arg2];
                                    AudioVolumeRecord.this.updateValue();
                                }
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        this.mTypeScene.initSceneSpinner(this, CATEGORY_RECORD);
                        this.mEditUlGain = (EditText) findViewById(R.id.audio_volume_edittext1);
                        findViewById(R.id.layout_text2).setVisibility(8);
                        findViewById(R.id.table_title).setVisibility(8);
                        findViewById(R.id.dl_table).setVisibility(8);
                        initTableValue();
                        updateValue();
                    }
                }
            }
        }
    }

    private void initTableValue() {
        String strMaxValue = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_VALUE_MAX);
        String strMaxIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_INDEX_MAX);
        String strMinIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_INDEX_MIN);
        String strValueStep = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_STEP);
        try {
            this.mUlMaxValue = Integer.parseInt(strMaxValue);
            this.mUlMaxIndex = Integer.parseInt(strMaxIndex);
            this.mUlValueStep = Integer.parseInt(strValueStep);
            this.mUlMinValue = this.mUlMaxValue - (this.mUlValueStep * (this.mUlMaxIndex - Integer.parseInt(strMinIndex)));
            Elog.d(TAG, "Init UL gain table: mUlMaxValue " + this.mUlMaxValue + " mUlMinValue " + this.mUlMinValue + " mUlMaxIndex " + this.mUlMaxIndex + " mUlValueStep " + this.mUlValueStep);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "initTableValue wrong format", 1).show();
        }
    }

    /* access modifiers changed from: private */
    public void updateValue() {
        AudioVolumeTypeScene audioVolumeTypeScene = this.mTypeScene;
        String strValue = AudioTuningJni.getParams(CATEGORY_RECORDVOL, audioVolumeTypeScene.getPara2String("Application," + this.mCurrentApp + "," + TYPE_PROFILE + "," + this.mCurrentProfile), UL_GAIN);
        StringBuilder sb = new StringBuilder();
        sb.append("UL gain Value:");
        sb.append(strValue);
        Elog.d(TAG, sb.toString());
        try {
            int value = Integer.parseInt(strValue);
            int i = this.mUlMaxValue;
            int value2 = i - (this.mUlValueStep * (this.mUlMaxIndex - value));
            if (value2 <= i) {
                if (value2 >= this.mUlMinValue) {
                    this.mEditUlGain.setText(String.valueOf(value2));
                    this.mLayoutText1.setVisibility(0);
                    return;
                }
            }
            this.mLayoutText1.setVisibility(8);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            this.mLayoutText1.setVisibility(8);
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
            setUlGain();
            removeDialog(3);
            showDialog(3);
        }
    }

    private void setUlGain() {
        EditText editText = this.mEditUlGain;
        if (editText == null || editText.getText() == null) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_gain_format);
            return;
        }
        try {
            int valueUl = Integer.parseInt(this.mEditUlGain.getText().toString());
            if (valueUl <= this.mUlMaxValue) {
                if (valueUl >= this.mUlMinValue) {
                    if (!AudioTuningJni.setParams(CATEGORY_RECORDVOL, this.mTypeScene.getPara2String("Application," + this.mCurrentApp + "," + TYPE_PROFILE + "," + this.mCurrentProfile), UL_GAIN, String.valueOf((valueUl - 72) / 4)) || !AudioTuningJni.saveToWork(CATEGORY_RECORDVOL)) {
                        this.mStrErrorInfo += getString(R.string.audio_volume_error_set_ul_gain);
                    }
                    return;
                }
            }
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_invalid, new Object[]{Integer.valueOf(this.mUlMinValue), Integer.valueOf(this.mUlMaxValue)});
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_gain_format);
        }
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
            default:
                return null;
        }
    }
}
