package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.ShellExe;
import com.mediatek.engineermode.audio.Audio;
import java.io.File;
import java.io.IOException;

public class AudioDebugSession extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String AUDIO_DISENABLE_HYBRID_NLE = "AudioEnableHybridNLE=0";
    private static final String AUDIO_ENABLE_HYBRID_NLE = "AudioEnableHybridNLE=1";
    private static final String AUDIO_HYBRID_NLE = "AudioEnableHybridNLE";
    private static final String AUDIO_HYBRID_NLE_EOP = "AudioHybridNLEEOP";
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int GET_AECREC_TEST_ENABLE = 165;
    private static final int GET_MAGIASR_TEST_ENABLE = 163;
    private static final String HEAD_DETECT_PATH = "/sys/bus/platform/drivers/Accdet_Driver/state";
    private static final String HEAD_DETECT_PATH2 = "/sys/bus/platform/drivers/pmic-codec-accdet/state";
    private static final String MTK_AUDIO_HYBRID_NLE_SUPPORT = "MTK_AUDIO_HYBRID_NLE_SUPPORT";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    private static final int SET_AECREC_TEST_ENABLE = 164;
    private static final int SET_MAGIASR_TEST_ENABLE = 162;
    private static final String TAG = "Audio/DebugSession";
    private AudioManager mAudioMgr = null;
    private Button mBtnDetect;
    private Button mBtnSetNLE;
    private CheckBox mCbAecRec;
    private CheckBox mCbCustParam;
    private CheckBox mCbMagi;
    private CheckBox mCbNLE;
    private EditText mEtNLE;
    private LinearLayout mLinearLayoutNLE;
    private Toast mToast = null;
    private TextView mTvDetect;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_debugsession);
        this.mAudioMgr = (AudioManager) getSystemService("audio");
        Button button = (Button) findViewById(R.id.Audio_Headset_Detect_Button);
        this.mBtnDetect = button;
        button.setOnClickListener(this);
        this.mTvDetect = (TextView) findViewById(R.id.Audio_Headset_Detect_Text);
        this.mCbMagi = (CheckBox) findViewById(R.id.Audio_MagiAsr);
        this.mCbAecRec = (CheckBox) findViewById(R.id.Audio_AecRec);
        this.mCbCustParam = (CheckBox) findViewById(R.id.Audio_Custom_Param);
        this.mBtnSetNLE = (Button) findViewById(R.id.Audio_Hybrid_Dynamic_set);
        this.mEtNLE = (EditText) findViewById(R.id.Audio_Hybrid_Dynamic_edit);
        this.mCbNLE = (CheckBox) findViewById(R.id.Audio_Hybrid_Dynamic);
        this.mLinearLayoutNLE = (LinearLayout) findViewById(R.id.Audio_Hybrid_Dynamic_LinearLayout);
        String audioHybridNleSupport = AudioSystem.getParameters(MTK_AUDIO_HYBRID_NLE_SUPPORT);
        Elog.d(TAG, audioHybridNleSupport);
        this.mLinearLayoutNLE.setVisibility(8);
        boolean z = false;
        if (audioHybridNleSupport.equals("MTK_AUDIO_HYBRID_NLE_SUPPORT=1")) {
            this.mLinearLayoutNLE.setVisibility(0);
            String ret = AudioSystem.getParameters(AUDIO_HYBRID_NLE);
            Elog.d(TAG, "getParameters(AUDIO_HYBRID_NLE) ret = " + ret);
            this.mCbNLE.setChecked(AUDIO_ENABLE_HYBRID_NLE.equals(ret));
            this.mCbNLE.setOnCheckedChangeListener(this);
            String rets = AudioSystem.getParameters(AUDIO_HYBRID_NLE_EOP);
            Elog.d(TAG, "getParameters(AUDIO_HYBRID_NLE_EOP) rets = " + rets);
            String[] values = rets.split("=");
            if (values.length == 2) {
                this.mEtNLE.setText(values[1]);
            }
            this.mBtnSetNLE.setOnClickListener(this);
        }
        TextView tvCustParam = (TextView) findViewById(R.id.Audio_Custom_Param_Title);
        View divider = findViewById(R.id.Audio_Custom_Param_Divider);
        if (Audio.AudioTuningVer.VER_2_2 != Audio.getAudioTuningVer()) {
            this.mCbCustParam.setVisibility(8);
            tvCustParam.setVisibility(8);
            divider.setVisibility(8);
        } else if (FeatureSupport.isEngLoad()) {
            this.mCbCustParam.setChecked(true);
            this.mCbCustParam.setEnabled(false);
        } else {
            String check = this.mAudioMgr.getParameters(CUST_XML_PARAM);
            if (check == null || !RESULT_SUPPORT.equals(check)) {
                this.mCbCustParam.setChecked(false);
            } else {
                this.mCbCustParam.setChecked(true);
            }
            this.mCbCustParam.setOnCheckedChangeListener(this);
        }
        int ret2 = AudioTuningJni.getAudioCommand(GET_MAGIASR_TEST_ENABLE);
        Elog.d(TAG, "getAudioCommand(0xA3) ret " + ret2);
        if (ret2 == 0) {
            findViewById(R.id.Audio_MagiAsr_Group).setVisibility(8);
        } else {
            this.mCbMagi.setChecked(ret2 == 1);
            this.mCbMagi.setOnCheckedChangeListener(this);
        }
        int ret3 = AudioTuningJni.getAudioCommand(GET_AECREC_TEST_ENABLE);
        Elog.d(TAG, "getAudioCommand(0xA5) ret " + ret3);
        CheckBox checkBox = this.mCbAecRec;
        if (ret3 == 1) {
            z = true;
        }
        checkBox.setChecked(z);
        this.mCbAecRec.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals(this.mCbMagi)) {
            int ret = AudioTuningJni.setAudioCommand(SET_MAGIASR_TEST_ENABLE, isChecked);
            Elog.d(TAG, "setAudioCommand(0xA2, " + isChecked + ") ret " + ret);
            if (ret == -1) {
                showToast("set audio parameter 0xA2 failed.");
            }
        } else if (buttonView.equals(this.mCbAecRec)) {
            int ret2 = AudioTuningJni.setAudioCommand(SET_AECREC_TEST_ENABLE, isChecked);
            Elog.d(TAG, "setAudioCommand(0xA4, " + isChecked + ") ret " + ret2);
            if (ret2 == -1) {
                showToast("set audio parameter 0xA4 failed.");
            }
        } else if (buttonView.equals(this.mCbCustParam)) {
            this.mAudioMgr.setParameters(this.mCbCustParam.isChecked() ? CUST_XML_SET_SUPPORT_PARAM : CUST_XML_SET_UNSUPPORT_PARAM);
            AudioTuningJni.CustXmlEnableChanged(this.mCbCustParam.isChecked() ? 1 : 0);
        } else if (buttonView.equals(this.mCbNLE)) {
            AudioManager audioManager = this.mAudioMgr;
            boolean isChecked2 = this.mCbNLE.isChecked();
            String str = AUDIO_ENABLE_HYBRID_NLE;
            audioManager.setParameters(isChecked2 ? str : AUDIO_DISENABLE_HYBRID_NLE);
            StringBuilder sb = new StringBuilder();
            sb.append("mCbNLE changed ");
            if (!this.mCbNLE.isChecked()) {
                str = AUDIO_DISENABLE_HYBRID_NLE;
            }
            sb.append(str);
            Elog.d(TAG, sb.toString());
        }
    }

    public void onClick(View arg0) {
        if (arg0.getId() == this.mBtnDetect.getId()) {
            String headDetectPath = HEAD_DETECT_PATH;
            if (!new File(headDetectPath).exists()) {
                headDetectPath = HEAD_DETECT_PATH2;
            }
            String cmd = "cat " + headDetectPath;
            try {
                if (ShellExe.execCommand(cmd, true) == 0) {
                    int mode = Integer.parseInt(ShellExe.getOutput());
                    Elog.d(TAG, headDetectPath + ": " + ShellExe.getOutput());
                    if (mode == 1) {
                        this.mTvDetect.setText(getString(R.string.Audio_Headset_Jak_Headset));
                    } else if (mode == 2) {
                        this.mTvDetect.setText(getString(R.string.Audio_Headset_Jak_Headphone));
                    } else {
                        this.mTvDetect.setText(getString(R.string.Audio_Headset_None));
                    }
                    return;
                }
                showToast("Detection failed");
            } catch (IOException e) {
                Elog.d(TAG, cmd.toString() + e.getMessage());
                showToast("Detection failed");
            } catch (NumberFormatException e2) {
                showToast("Detection failed");
            }
        } else if (arg0.getId() == this.mBtnSetNLE.getId()) {
            EditText editText = this.mEtNLE;
            if (editText == null || editText.getText() == null) {
                showToast("Please input an num 0 ~~ -96");
                return;
            }
            String input = this.mEtNLE.getText().toString();
            Elog.d(TAG, "input AUDIO_HYBRID_NLE_EOP = " + input);
            if (input.isEmpty() || Long.valueOf(input).longValue() > 0 || Long.valueOf(input).longValue() < -96) {
                showToast("Please input an num 0 ~~ -96");
                return;
            }
            String sets = "AudioHybridNLEEOP=" + input;
            AudioSystem.setParameters(sets);
            Elog.d(TAG, "set AUDIO_HYBRID_NLE_EOP = " + sets);
            String ret = AudioSystem.getParameters(AUDIO_HYBRID_NLE_EOP);
            Elog.d(TAG, "get AUDIO_HYBRID_NLE_EOP = " + ret);
            if (ret.equals(sets)) {
                showToast("The value set succeeful");
            } else {
                showToast("The value set failed");
            }
        }
    }

    private void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }
}
