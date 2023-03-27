package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.bandselect.BandModeContent;

public class AudioDebugInfo extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String AUDIO_RECORD_PREFER = "audio_record";
    private static final int DATA_SIZE = 1444;
    private static final int DIALOG_ID_GET_DATA_ERROR = 1;
    private static final int DIALOG_ID_SET_ERROR = 3;
    private static final int DIALOG_ID_SET_SUCCESS = 2;
    private static final int LONGEST_NUM_LEN = 5;
    private static final int MAGIC_NUMBER_256 = 256;
    private static final int MAGIC_NUMBER_65535 = 65535;
    private static final int SPINNER_COUNT = 16;
    private static final String TAG = "Audio/DebugInfo";
    private static final int VOLUME_SPEECH_SIZE = 310;
    private Button mBtnSet;
    private byte[] mData;
    private EditText mDebugValue;
    private int mSpinnerIndex;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_debuginfo);
        Spinner paramSpinner = (Spinner) findViewById(R.id.Audio_DebugInfo_Spinner);
        this.mDebugValue = (EditText) findViewById(R.id.Audio_DebugInfo_EditText);
        Button button = (Button) findViewById(R.id.Audio_DebugInfo_Button);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        ArrayAdapter<String> mSpinnerAdatper = new ArrayAdapter<>(this, 17367048);
        mSpinnerAdatper.setDropDownViewResource(17367049);
        Resources resources = getResources();
        for (int i = 0; i < 16; i++) {
            mSpinnerAdatper.add(resources.getString(R.string.paramter) + i);
        }
        paramSpinner.setAdapter(mSpinnerAdatper);
        paramSpinner.setOnItemSelectedListener(this);
        this.mData = new byte[DATA_SIZE];
        for (int n = 0; n < DATA_SIZE; n++) {
            this.mData[n] = 0;
        }
        int ret = AudioTuningJni.getEmParameter(this.mData, DATA_SIZE);
        if (ret != 0) {
            showDialog(1);
            Elog.i(TAG, "Audio_DebugInfo GetEMParameter return value is : " + ret);
        }
        int i2 = getSharedPreferences(AUDIO_RECORD_PREFER, 0).getInt("NUM", 0);
        this.mSpinnerIndex = i2;
        paramSpinner.setSelection(i2);
        byte[] bArr = this.mData;
        int i3 = this.mSpinnerIndex;
        this.mDebugValue.setText(String.valueOf((bArr[(i3 * 2) + VOLUME_SPEECH_SIZE + 1] * 256) + bArr[(i3 * 2) + VOLUME_SPEECH_SIZE]));
    }

    public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        this.mSpinnerIndex = arg2;
        byte[] bArr = this.mData;
        this.mDebugValue.setText(String.valueOf((bArr[(arg2 * 2) + VOLUME_SPEECH_SIZE + 1] * 256) + bArr[(arg2 * 2) + VOLUME_SPEECH_SIZE]));
        SharedPreferences.Editor editor = getSharedPreferences(AUDIO_RECORD_PREFER, 0).edit();
        editor.putInt("NUM", this.mSpinnerIndex);
        editor.commit();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        Elog.i(TAG, "do noting...");
    }

    public void onClick(View arg0) {
        if (arg0.getId() == this.mBtnSet.getId()) {
            EditText editText = this.mDebugValue;
            if (editText == null || editText.getText() == null || this.mDebugValue.getText().toString() == null) {
                Toast.makeText(this, R.string.input_null_tip, 1).show();
            } else if (5 < this.mDebugValue.getText().toString().length() || this.mDebugValue.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.input_length_error, 1).show();
            } else {
                long inputValue = Long.valueOf(this.mDebugValue.getText().toString()).longValue();
                if (inputValue > BandModeContent.UMTS_MAX_VALUE) {
                    Toast.makeText(this, R.string.input_length_error, 1).show();
                    return;
                }
                byte[] bArr = this.mData;
                int i = this.mSpinnerIndex;
                bArr[(i * 2) + VOLUME_SPEECH_SIZE] = (byte) ((int) (inputValue % 256));
                bArr[(i * 2) + VOLUME_SPEECH_SIZE + 1] = (byte) ((int) (inputValue / 256));
                int result = AudioTuningJni.setEmParameter(bArr, DATA_SIZE);
                if (result == 0 || -38 == result) {
                    showDialog(2);
                    return;
                }
                showDialog(3);
                Elog.i(TAG, "SetEMParameter return value is : " + result);
            }
        }
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioDebugInfo.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_debuginfo_success).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_debuginfo_failed).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }
}
