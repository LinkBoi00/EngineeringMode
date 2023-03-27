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

public class AudioVolumeVoIP extends Activity implements View.OnClickListener {
    private static final String CATEGORY_GAINMAP = "VolumeGainMap";
    private static final String CATEGORY_VOIPV2 = "VoIPv2";
    private static final String CATEGORY_VOIPVOL = "VoIPVol";
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
    public static final String TAG = "Audio/VolumeVoIP";
    private static final String TYPE_PROFILE = "Profile";
    private static final String UL_GAIN = "ul_gain";
    private String[] mArrayDlTable;
    private ArrayList<EditText> mArrayDlText;
    private String[] mArraySpinnerProfile;
    /* access modifiers changed from: private */
    public String[] mArraySpinnerProfileValue;
    private AudioManager mAudioMgr = null;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public String mCurrentProfile;
    private EditText mEditUlGain;
    private View mLayoutText1;
    private ArrayAdapter<String> mProfileAdatper;
    private Spinner mProfileSpinner;
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
                AudioVolumeVoIP.this.updateValue();
            }
        });
        initComponents();
    }

    private void initComponents() {
        this.mLayoutText1 = findViewById(R.id.layout_text1);
        this.mArrayDlText = new ArrayList<>();
        Button button = (Button) findViewById(R.id.btn_set);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        findViewById(R.id.audio_volume_spinner1).setVisibility(8);
        this.mProfileSpinner = (Spinner) findViewById(R.id.audio_volume_spinner2);
        String strSpinner2 = AudioTuningJni.getCategory(CATEGORY_VOIPVOL, TYPE_PROFILE);
        Elog.d(TAG, "strSpinner2:" + strSpinner2);
        if (strSpinner2 != null) {
            String[] value = strSpinner2.split(",");
            int length = value.length / 2;
            if (length > 0) {
                this.mArraySpinnerProfile = new String[length];
                this.mArraySpinnerProfileValue = new String[length];
                for (int k = 0; k < length; k++) {
                    this.mArraySpinnerProfileValue[k] = value[k * 2];
                    this.mArraySpinnerProfile[k] = value[(k * 2) + 1];
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mArraySpinnerProfile);
                this.mProfileAdatper = arrayAdapter;
                arrayAdapter.setDropDownViewResource(17367049);
                this.mProfileSpinner.setAdapter(this.mProfileAdatper);
                this.mCurrentProfile = this.mArraySpinnerProfileValue[0];
                this.mProfileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                        if (AudioVolumeVoIP.this.mCurrentProfile != AudioVolumeVoIP.this.mArraySpinnerProfileValue[arg2]) {
                            AudioVolumeVoIP audioVolumeVoIP = AudioVolumeVoIP.this;
                            String unused = audioVolumeVoIP.mCurrentProfile = audioVolumeVoIP.mArraySpinnerProfileValue[arg2];
                            AudioVolumeVoIP.this.updateValue();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mTypeScene.initSceneSpinner(this, CATEGORY_VOIPV2);
                this.mEditUlGain = (EditText) findViewById(R.id.audio_volume_edittext1);
                findViewById(R.id.layout_text2).setVisibility(8);
                this.mTableTitleText = (TextView) findViewById(R.id.table_title);
                this.mTableLayout = (TableLayout) findViewById(R.id.dl_table);
                initTableValue();
                updateValue();
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
            Toast.makeText(this, "initTableValue Wrong format", 1).show();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateValue() {
        /*
            r12 = this;
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r0 = r12.mTypeScene
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Profile,"
            r1.append(r2)
            java.lang.String r3 = r12.mCurrentProfile
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r0 = r0.getPara2String(r1)
            java.lang.String r1 = "VoIPVol"
            java.lang.String r3 = "ul_gain"
            java.lang.String r0 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r1, r0, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "UL gain Value:"
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "Audio/VolumeVoIP"
            com.mediatek.engineermode.Elog.d(r4, r3)
            r3 = 0
            r4 = 8
            int r5 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0064 }
            int r6 = r12.mUlMaxValue     // Catch:{ NumberFormatException -> 0x0064 }
            int r7 = r12.mUlValueStep     // Catch:{ NumberFormatException -> 0x0064 }
            int r8 = r12.mUlMaxIndex     // Catch:{ NumberFormatException -> 0x0064 }
            int r8 = r8 - r5
            int r7 = r7 * r8
            int r5 = r6 - r7
            if (r5 > r6) goto L_0x005e
            int r6 = r12.mUlMinValue     // Catch:{ NumberFormatException -> 0x0064 }
            if (r5 >= r6) goto L_0x004f
            goto L_0x005e
        L_0x004f:
            android.widget.EditText r6 = r12.mEditUlGain     // Catch:{ NumberFormatException -> 0x0064 }
            java.lang.String r7 = java.lang.String.valueOf(r5)     // Catch:{ NumberFormatException -> 0x0064 }
            r6.setText(r7)     // Catch:{ NumberFormatException -> 0x0064 }
            android.view.View r6 = r12.mLayoutText1     // Catch:{ NumberFormatException -> 0x0064 }
            r6.setVisibility(r3)     // Catch:{ NumberFormatException -> 0x0064 }
            goto L_0x0063
        L_0x005e:
            android.view.View r6 = r12.mLayoutText1     // Catch:{ NumberFormatException -> 0x0064 }
            r6.setVisibility(r4)     // Catch:{ NumberFormatException -> 0x0064 }
        L_0x0063:
            goto L_0x006d
        L_0x0064:
            r5 = move-exception
            r5.printStackTrace()
            android.view.View r6 = r12.mLayoutText1
            r6.setVisibility(r4)
        L_0x006d:
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r5 = r12.mTypeScene
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            java.lang.String r7 = r12.mCurrentProfile
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r5 = r5.getPara2String(r6)
            java.lang.String r6 = "VolumeGainMap"
            java.lang.String r7 = "dl_total_gain_decimal"
            java.lang.String r5 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r6, r5, r7)
            java.lang.String r6 = ","
            java.lang.String[] r7 = r5.split(r6)
            r12.mArrayDlTable = r7
            com.mediatek.engineermode.audio.AudioVolumeTypeScene r7 = r12.mTypeScene
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r2)
            java.lang.String r2 = r12.mCurrentProfile
            r8.append(r2)
            java.lang.String r2 = r8.toString()
            java.lang.String r2 = r7.getPara2String(r2)
            java.lang.String r7 = "dl_gain"
            java.lang.String r1 = com.mediatek.engineermode.audio.AudioTuningJni.getParams(r1, r2, r7)
            java.util.ArrayList<android.widget.EditText> r2 = r12.mArrayDlText
            r2.clear()
            android.widget.TableLayout r2 = r12.mTableLayout
            r2.removeAllViews()
            if (r1 != 0) goto L_0x00c3
            android.widget.TextView r2 = r12.mTableTitleText
            r2.setVisibility(r4)
            return
        L_0x00c3:
            android.widget.TextView r2 = r12.mTableTitleText
            r2.setVisibility(r3)
            java.lang.String[] r2 = r1.split(r6)
            int r4 = r2.length
            r6 = 0
        L_0x00ce:
            if (r6 >= r4) goto L_0x0127
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
            r10 = r2[r6]     // Catch:{ NumberFormatException -> 0x011f }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x011f }
            java.lang.String[] r11 = r12.mArrayDlTable     // Catch:{ NumberFormatException -> 0x011f }
            r11 = r11[r10]     // Catch:{ NumberFormatException -> 0x011f }
            r9.setText(r11)     // Catch:{ NumberFormatException -> 0x011f }
            r10 = 2
            r9.setInputType(r10)
            r10 = 3
            r8.setPadding(r3, r10, r10, r10)
            r11 = 20
            r9.setPadding(r11, r10, r10, r10)
            java.util.ArrayList<android.widget.EditText> r10 = r12.mArrayDlText
            r10.add(r9)
            r7.addView(r8)
            r7.addView(r9)
            android.widget.TableLayout r10 = r12.mTableLayout
            r10.addView(r7)
            goto L_0x0124
        L_0x011f:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0124:
            int r6 = r6 + 1
            goto L_0x00ce
        L_0x0127:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.audio.AudioVolumeVoIP.updateValue():void");
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
            if (!AudioTuningJni.saveToWork(CATEGORY_VOIPVOL)) {
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
                    strValueDlGain = strValueDlGain + "," + k;
                    continue;
                }
            }
            if (k == size) {
                this.mStrErrorInfo += strValue + getString(R.string.audio_volume_error_dl_invalid);
                return;
            }
        }
        if (!AudioTuningJni.setParams(CATEGORY_VOIPVOL, this.mTypeScene.getPara2String("Profile," + this.mCurrentProfile), DL_GAIN, strValueDlGain)) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_set_dl_gain);
        }
    }

    private void setUlGain() {
        EditText editText = this.mEditUlGain;
        if (editText == null || editText.getText() == null) {
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_invalid, new Object[]{Integer.valueOf(this.mUlMinValue), Integer.valueOf(this.mUlMaxValue)});
            return;
        }
        try {
            int valueUl = Integer.parseInt(this.mEditUlGain.getText().toString());
            int i = this.mUlMaxValue;
            if (valueUl <= i) {
                if (valueUl >= this.mUlMinValue) {
                    if (!AudioTuningJni.setParams(CATEGORY_VOIPVOL, this.mTypeScene.getPara2String("Profile," + this.mCurrentProfile), UL_GAIN, String.valueOf(this.mUlMaxIndex - ((i - valueUl) / this.mUlValueStep)))) {
                        this.mStrErrorInfo += getString(R.string.audio_volume_error_set_ul_gain);
                    }
                    return;
                }
            }
            this.mStrErrorInfo += getString(R.string.audio_volume_error_ul_invalid, new Object[]{Integer.valueOf(this.mUlMinValue), Integer.valueOf(this.mUlMaxValue)});
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
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
