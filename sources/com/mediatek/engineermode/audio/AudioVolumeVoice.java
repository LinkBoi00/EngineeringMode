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
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.AudioVolumeTypeScene;
import java.util.ArrayList;
import java.util.Iterator;

public class AudioVolumeVoice extends Activity implements View.OnClickListener {
    private static final String CATEGORY_GAINMAP = "VolumeGainMap";
    private static final String CATEGORY_SPEECH = "Speech";
    private static final String CATEGORY_SPEECHVOL = "SpeechVol";
    private static final String CATEGORY_VOLUME = "Volume";
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int DIALOG_INIT_FAIL = 4;
    private static final int DIALOG_RESULT = 3;
    private static final String DL_GAIN = "dl_gain";
    private static final String DL_MAP = "dl_total_gain_decimal";
    private static final String LIST_DIVIDER = ",";
    private static final String PARAM2 = "Band,%1$s,Profile,%2$s";
    private static final String PARAM2_NEW = "Band,%1$s,Profile,%2$s,Network,%3$s";
    private static final String PARAM_COMMON = "VolumeParam,Common";
    private static final String PARAM_STF_LIST = "stf_gain_field";
    private static final String PARAM_STF_STEP = "dec_stf_step_per_db";
    private static final String PARAM_STF_VALUE_MAX = "dec_stf_max";
    private static final String PARAM_UL_INDEX_MAX = "mic_idx_range_max";
    private static final String PARAM_UL_INDEX_MIN = "mic_idx_range_min";
    private static final String PARAM_UL_STEP = "dec_rec_step_per_db";
    private static final String PARAM_UL_VALUE_MAX = "dec_rec_max";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    private static final String STF_GAIN = "stf_gain";
    private static final int STF_MAX = 240;
    private static final int STF_MIN = 0;
    public static final String TAG = "Audio/VolumeVoice";
    private static final String TYPE_BAND = "Band";
    private static final String TYPE_NETWORK = "Network";
    private static final String TYPE_PROFILE = "Profile";
    private static final String UL_GAIN = "ul_gain";
    private String[] mArrayDlTable;
    private ArrayList<EditText> mArrayDlText;
    private String[] mArraySpinnerBand;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerBandValue;
    private String[] mArraySpinnerProfile;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerProfileValue;
    private AudioManager mAudioMgr = null;
    private ArrayAdapter<String> mBandAdatper;
    private Spinner mBandSpinner;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public String mCurrentBand;
    /* access modifiers changed from: private */
    public String mCurrentNetwork;
    /* access modifiers changed from: private */
    public String mCurrentProfile;
    private EditText mEditStfGain;
    private EditText mEditUlGain;
    private View mLayoutText1;
    private View mLayoutText2;
    private Spinner mNetworkSpinner;
    private ArrayAdapter<String> mProfileAdatper;
    private Spinner mProfileSpinner;
    private boolean mStfAscending;
    private int mStfIndexStep;
    private int mStfMaxIndex;
    private int mStfMaxValue;
    private int mStfMinValue;
    private int mStfValueStep;
    private String mStrErrorInfo;
    private TableLayout mTableLayout;
    private TextView mTableTitleText;
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
                AudioVolumeVoice.this.updateValue();
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
        this.mBandSpinner = (Spinner) findViewById(R.id.audio_volume_spinner1);
        String strSpinner1 = AudioTuningJni.getCategory(CATEGORY_SPEECHVOL, TYPE_BAND);
        Elog.d(TAG, "strSpinner1:" + strSpinner1);
        if (strSpinner1 != null) {
            String[] value = strSpinner1.split(LIST_DIVIDER);
            int length = value.length / 2;
            if (length > 0) {
                this.mArraySpinnerBand = new String[length];
                this.mArraySpinnerBandValue = new String[length];
                for (int k = 0; k < length; k++) {
                    this.mArraySpinnerBandValue[k] = value[k * 2];
                    this.mArraySpinnerBand[k] = value[(k * 2) + 1];
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mArraySpinnerBand);
                this.mBandAdatper = arrayAdapter;
                arrayAdapter.setDropDownViewResource(17367049);
                this.mBandSpinner.setAdapter(this.mBandAdatper);
                this.mCurrentBand = this.mArraySpinnerBandValue[0];
                this.mBandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        if (AudioVolumeVoice.this.mCurrentBand != AudioVolumeVoice.this.mArraySpinnerBandValue[arg2]) {
                            AudioVolumeVoice audioVolumeVoice = AudioVolumeVoice.this;
                            String unused = audioVolumeVoice.mCurrentBand = audioVolumeVoice.mArraySpinnerBandValue[arg2];
                            AudioVolumeVoice.this.updateValue();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mProfileSpinner = (Spinner) findViewById(R.id.audio_volume_spinner2);
                String strSpinner2 = AudioTuningJni.getCategory(CATEGORY_SPEECH, TYPE_PROFILE);
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
                                if (AudioVolumeVoice.this.mCurrentProfile != AudioVolumeVoice.this.mArraySpinnerProfileValue[arg2]) {
                                    AudioVolumeVoice audioVolumeVoice = AudioVolumeVoice.this;
                                    String unused = audioVolumeVoice.mCurrentProfile = audioVolumeVoice.mArraySpinnerProfileValue[arg2];
                                    AudioVolumeVoice.this.updateValue();
                                }
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        initNetworkSpinner();
                        this.mTypeScene.initSceneSpinner(this, CATEGORY_SPEECHVOL);
                        this.mEditUlGain = (EditText) findViewById(R.id.audio_volume_edittext1);
                        this.mEditStfGain = (EditText) findViewById(R.id.audio_volume_edittext2);
                        this.mTableTitleText = (TextView) findViewById(R.id.table_title);
                        this.mTableLayout = (TableLayout) findViewById(R.id.dl_table);
                        initTableValue();
                        updateValue();
                    }
                }
            }
        }
    }

    private boolean initNetworkSpinner() {
        this.mNetworkSpinner = (Spinner) findViewById(R.id.audio_volume_spinner3);
        String strSpinner3 = AudioTuningJni.getCategory(CATEGORY_SPEECHVOL, TYPE_NETWORK);
        Elog.d(TAG, "strSpinner3:" + strSpinner3);
        if (strSpinner3 == null) {
            return false;
        }
        String[] value = strSpinner3.split(LIST_DIVIDER);
        int length = value.length / 2;
        if (length <= 0) {
            return false;
        }
        String[] arraySpinner = new String[length];
        final String[] mArrayValue = new String[length];
        for (int k = 0; k < length; k++) {
            mArrayValue[k] = value[k * 2];
            arraySpinner[k] = value[(k * 2) + 1];
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<>(this, 17367048, arraySpinner);
        adatper.setDropDownViewResource(17367049);
        this.mNetworkSpinner.setAdapter(adatper);
        this.mCurrentNetwork = mArrayValue[0];
        this.mNetworkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                String access$500 = AudioVolumeVoice.this.mCurrentNetwork;
                String[] strArr = mArrayValue;
                if (access$500 != strArr[arg2]) {
                    String unused = AudioVolumeVoice.this.mCurrentNetwork = strArr[arg2];
                    AudioVolumeVoice.this.updateValue();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mNetworkSpinner.setVisibility(0);
        return true;
    }

    private void initTableValue() {
        try {
            String strMaxValue = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_VALUE_MAX);
            String strMaxIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_INDEX_MAX);
            String strMinIndex = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_INDEX_MIN);
            String strValueStep = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_UL_STEP);
            this.mUlMaxValue = Integer.parseInt(strMaxValue);
            this.mUlMaxIndex = Integer.parseInt(strMaxIndex);
            this.mUlValueStep = Integer.parseInt(strValueStep);
            this.mUlMinValue = this.mUlMaxValue - (this.mUlValueStep * (this.mUlMaxIndex - Integer.parseInt(strMinIndex)));
            Elog.d(TAG, "Init UL gain table: mUlMaxValue " + this.mUlMaxValue + " mUlMinValue " + this.mUlMinValue + " mUlMaxIndex " + this.mUlMaxIndex + " mUlValueStep " + this.mUlValueStep);
            String strMaxValue2 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_STF_VALUE_MAX);
            String strValueStep2 = AudioTuningJni.getParams(CATEGORY_VOLUME, PARAM_COMMON, PARAM_STF_STEP);
            String strIndexList = AudioTuningJni.getChecklist(CATEGORY_SPEECHVOL, STF_GAIN, PARAM_STF_LIST);
            String[] arrayIndex = null;
            if (strIndexList != null) {
                arrayIndex = strIndexList.trim().split(LIST_DIVIDER);
            }
            if (arrayIndex != null) {
                if (arrayIndex.length >= 3) {
                    boolean z = false;
                    String strMaxIndex2 = arrayIndex[0];
                    this.mStfMaxValue = Integer.parseInt(strMaxValue2);
                    this.mStfMaxIndex = Integer.parseInt(strMaxIndex2);
                    this.mStfValueStep = Integer.parseInt(strValueStep2);
                    int secodnIndex = Integer.parseInt(arrayIndex[2]);
                    int i = this.mStfMaxIndex;
                    if (i < secodnIndex) {
                        z = true;
                    }
                    this.mStfAscending = z;
                    this.mStfIndexStep = Math.abs(i - secodnIndex);
                    this.mStfMinValue = this.mStfMaxValue - (this.mStfValueStep * Math.abs(this.mStfMaxIndex - Integer.parseInt(arrayIndex[arrayIndex.length - 2])));
                    Elog.d(TAG, "Init Stf gain table: mStfMaxValue " + this.mStfMaxValue + " mStfMinValue " + this.mStfMinValue + " mStfMaxIndex " + this.mStfMaxIndex + " mStfValueStep " + this.mStfValueStep + " mStfIndexStep " + this.mStfIndexStep);
                    return;
                }
            }
            removeDialog(4);
            showDialog(4);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "initTableValue Wrong format", 1).show();
        }
    }

    private String getParam2() {
        String str = this.mCurrentNetwork;
        if (str == null) {
            return String.format(PARAM2, new Object[]{this.mCurrentBand, this.mCurrentProfile});
        }
        return String.format(PARAM2_NEW, new Object[]{this.mCurrentBand, this.mCurrentProfile, str});
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0097 A[Catch:{ NumberFormatException -> 0x00b1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00f1 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateValue() {
        /*
            r12 = this;
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r0 = r12.mTypeScene
            java.lang.String r1 = r12.getParam2()
            java.lang.String r0 = r0.getPara2String(r1)
            java.lang.String r1 = "SpeechVol"
            java.lang.String r2 = "ul_gain"
            java.lang.String r0 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r1, r0, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "UL gain Value:"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "Audio/VolumeVoice"
            com.mediatek.engineermode.Elog.d(r3, r2)
            r2 = 0
            r4 = 8
            int r5 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0055 }
            int r6 = r12.mUlMaxValue     // Catch:{ NumberFormatException -> 0x0055 }
            int r7 = r12.mUlValueStep     // Catch:{ NumberFormatException -> 0x0055 }
            int r8 = r12.mUlMaxIndex     // Catch:{ NumberFormatException -> 0x0055 }
            int r8 = r8 - r5
            int r7 = r7 * r8
            int r5 = r6 - r7
            if (r5 > r6) goto L_0x004f
            int r6 = r12.mUlMinValue     // Catch:{ NumberFormatException -> 0x0055 }
            if (r5 >= r6) goto L_0x0040
            goto L_0x004f
        L_0x0040:
            android.widget.EditText r6 = r12.mEditUlGain     // Catch:{ NumberFormatException -> 0x0055 }
            java.lang.String r7 = java.lang.String.valueOf(r5)     // Catch:{ NumberFormatException -> 0x0055 }
            r6.setText(r7)     // Catch:{ NumberFormatException -> 0x0055 }
            android.view.View r6 = r12.mLayoutText1     // Catch:{ NumberFormatException -> 0x0055 }
            r6.setVisibility(r2)     // Catch:{ NumberFormatException -> 0x0055 }
            goto L_0x0054
        L_0x004f:
            android.view.View r6 = r12.mLayoutText1     // Catch:{ NumberFormatException -> 0x0055 }
            r6.setVisibility(r4)     // Catch:{ NumberFormatException -> 0x0055 }
        L_0x0054:
            goto L_0x005e
        L_0x0055:
            r5 = move-exception
            r5.printStackTrace()
            android.view.View r6 = r12.mLayoutText1
            r6.setVisibility(r4)
        L_0x005e:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r5 = r12.mTypeScene
            java.lang.String r6 = r12.getParam2()
            java.lang.String r5 = r5.getPara2String(r6)
            java.lang.String r6 = "stf_gain"
            java.lang.String r0 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r1, r5, r6)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "STF gain Value:"
            r5.append(r6)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.mediatek.engineermode.Elog.d(r3, r5)
            int r3 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x00b1 }
            int r5 = r12.mStfMaxValue     // Catch:{ NumberFormatException -> 0x00b1 }
            int r6 = r12.mStfValueStep     // Catch:{ NumberFormatException -> 0x00b1 }
            int r7 = r12.mStfMaxIndex     // Catch:{ NumberFormatException -> 0x00b1 }
            int r7 = r7 - r3
            int r7 = java.lang.Math.abs(r7)     // Catch:{ NumberFormatException -> 0x00b1 }
            int r6 = r6 * r7
            int r5 = r5 - r6
            int r3 = r12.mStfMaxValue     // Catch:{ NumberFormatException -> 0x00b1 }
            if (r5 > r3) goto L_0x00ab
            int r3 = r12.mStfMinValue     // Catch:{ NumberFormatException -> 0x00b1 }
            if (r5 >= r3) goto L_0x009c
            goto L_0x00ab
        L_0x009c:
            android.widget.EditText r3 = r12.mEditStfGain     // Catch:{ NumberFormatException -> 0x00b1 }
            java.lang.String r6 = java.lang.String.valueOf(r5)     // Catch:{ NumberFormatException -> 0x00b1 }
            r3.setText(r6)     // Catch:{ NumberFormatException -> 0x00b1 }
            android.view.View r3 = r12.mLayoutText2     // Catch:{ NumberFormatException -> 0x00b1 }
            r3.setVisibility(r2)     // Catch:{ NumberFormatException -> 0x00b1 }
            goto L_0x00b0
        L_0x00ab:
            android.view.View r3 = r12.mLayoutText2     // Catch:{ NumberFormatException -> 0x00b1 }
            r3.setVisibility(r4)     // Catch:{ NumberFormatException -> 0x00b1 }
        L_0x00b0:
            goto L_0x00ba
        L_0x00b1:
            r3 = move-exception
            r3.printStackTrace()
            android.view.View r5 = r12.mLayoutText2
            r5.setVisibility(r4)
        L_0x00ba:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Profile,"
            r3.append(r5)
            java.lang.String r5 = r12.mCurrentProfile
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            java.lang.String r5 = "VolumeGainMap"
            java.lang.String r6 = "dl_total_gain_decimal"
            java.lang.String r3 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r5, r3, r6)
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r5 = r12.mTypeScene
            java.lang.String r6 = r12.getParam2()
            java.lang.String r5 = r5.getPara2String(r6)
            java.lang.String r6 = "dl_gain"
            java.lang.String r1 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r1, r5, r6)
            java.util.ArrayList<android.widget.EditText> r5 = r12.mArrayDlText
            r5.clear()
            android.widget.TableLayout r5 = r12.mTableLayout
            r5.removeAllViews()
            if (r3 == 0) goto L_0x016d
            if (r1 == 0) goto L_0x016d
            boolean r5 = r3.isEmpty()
            if (r5 != 0) goto L_0x016d
            boolean r5 = r1.isEmpty()
            if (r5 == 0) goto L_0x0100
            goto L_0x016d
        L_0x0100:
            android.widget.TextView r4 = r12.mTableTitleText
            r4.setVisibility(r2)
            java.lang.String r4 = ","
            java.lang.String[] r5 = r1.split(r4)
            java.lang.String[] r4 = r3.split(r4)
            r12.mArrayDlTable = r4
            int r4 = r5.length
            r6 = 0
        L_0x0113:
            if (r6 >= r4) goto L_0x016c
            android.widget.TableRow r7 = new android.widget.TableRow
            r7.<init>(r12)
            android.widget.TextView r8 = new android.widget.TextView
            r8.<init>(r12)
            android.widget.EditText r9 = new android.widget.EditText
            r9.<init>(r12)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Index "
            r10.append(r11)
            r10.append(r6)
            java.lang.String r10 = r10.toString()
            r8.setText(r10)
            r10 = r5[r6]     // Catch:{ NumberFormatException -> 0x0164 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x0164 }
            java.lang.String[] r11 = r12.mArrayDlTable     // Catch:{ NumberFormatException -> 0x0164 }
            r11 = r11[r10]     // Catch:{ NumberFormatException -> 0x0164 }
            r9.setText(r11)     // Catch:{ NumberFormatException -> 0x0164 }
            r10 = 2
            r9.setInputType(r10)
            r10 = 3
            r8.setPadding(r2, r10, r10, r10)
            r11 = 20
            r9.setPadding(r11, r10, r10, r10)
            java.util.ArrayList<android.widget.EditText> r10 = r12.mArrayDlText
            r10.add(r9)
            r7.addView(r8)
            r7.addView(r9)
            android.widget.TableLayout r10 = r12.mTableLayout
            r10.addView(r7)
            goto L_0x0169
        L_0x0164:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0169:
            int r6 = r6 + 1
            goto L_0x0113
        L_0x016c:
            return
        L_0x016d:
            android.widget.TextView r2 = r12.mTableTitleText
            r2.setVisibility(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.audio.AudioVolumeVoice.updateValue():void");
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
            setDlGain();
            setUlGain();
            setStfGain();
            if (!AudioTuningJni.saveToWork(CATEGORY_SPEECHVOL)) {
                this.mStrErrorInfo += getString(R.string.audio_volume_error_save_xml);
            }
            removeDialog(3);
            showDialog(3);
        }
    }

    private void setDlGain() {
        String strValueDlGain = new String();
        Iterator<EditText> it = this.mArrayDlText.iterator();
        while (it.hasNext()) {
            EditText editText = it.next();
            if (editText == null || editText.getText() == null) {
                this.mStrErrorInfo += getString(R.string.audio_volume_error_dl_empty);
                return;
            }
            String strValue = editText.getText().toString().trim();
            int size = this.mArrayDlTable.length;
            int k = 0;
            while (true) {
                if (k >= size) {
                    break;
                } else if (!this.mArrayDlTable[k].equals(strValue)) {
                    k++;
                } else if (strValueDlGain.length() == 0) {
                    strValueDlGain = strValueDlGain + k;
                    continue;
                } else {
                    strValueDlGain = strValueDlGain + LIST_DIVIDER + k;
                    continue;
                }
            }
            if (k == size) {
                this.mStrErrorInfo += strValue + getString(R.string.audio_volume_error_dl_invalid);
                return;
            }
        }
        if (!AudioTuningJni.setParams(CATEGORY_SPEECHVOL, this.mTypeScene.getPara2String(getParam2()), DL_GAIN, strValueDlGain)) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_set_dl_gain);
        }
    }

    private void setUlGain() {
        if (this.mEditUlGain.getText() == null) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_gain_format);
            return;
        }
        try {
            int valueUl = Integer.parseInt(this.mEditUlGain.getText().toString());
            int i = this.mUlMaxValue;
            if (valueUl <= i) {
                if (valueUl >= this.mUlMinValue) {
                    if (!AudioTuningJni.setParams(CATEGORY_SPEECHVOL, this.mTypeScene.getPara2String(getParam2()), UL_GAIN, String.valueOf(this.mUlMaxIndex - ((i - valueUl) / this.mUlValueStep)))) {
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

    private void setStfGain() {
        if (this.mEditStfGain.getText() == null) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_stf_gain_format);
            return;
        }
        try {
            int value = Integer.parseInt(this.mEditStfGain.getText().toString());
            int i = this.mStfMaxValue;
            if (value <= i) {
                if (value >= this.mStfMinValue) {
                    int value2 = (i - value) / this.mStfValueStep;
                    int i2 = this.mStfIndexStep;
                    int value3 = (value2 / i2) * i2;
                    if (!AudioTuningJni.setParams(CATEGORY_SPEECHVOL, this.mTypeScene.getPara2String(getParam2()), STF_GAIN, String.valueOf(this.mStfAscending != 0 ? this.mStfMaxIndex + value3 : this.mStfMaxIndex - value3))) {
                        this.mStrErrorInfo += getString(R.string.audio_volume_error_set_stf_gain);
                    }
                    return;
                }
            }
            this.mStrErrorInfo += getString(R.string.audio_volume_error_stf_invalid, new Object[]{Integer.valueOf(this.mStfMinValue), Integer.valueOf(this.mStfMaxValue)});
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.mStrErrorInfo += getString(R.string.audio_volume_error_stf_gain_format);
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
