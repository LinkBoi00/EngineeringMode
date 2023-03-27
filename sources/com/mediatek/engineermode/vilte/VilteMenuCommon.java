package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class VilteMenuCommon extends Activity implements View.OnClickListener {
    private final String[] PROFILE_LABLE = {"Default", "Baseline 1", "", "", "Main 4", "", "", "", "", "", "", "", "", "", "", "", "High 16"};
    private final String[] PROFILE_VALUES = {"0", "1", "4", "16"};
    private final String PROPERTY_VIDEOCALL_AUDIO_OUTPUT = "persist.radio.call.audio.output";
    private final String PROP_VILTE_CAMERA = "persist.vendor.vt.camera";
    private final String PROP_VILTE_DEBUG_INFO_DISPLAY = "persist.vendor.vt.RTPInfo";
    private final String PROP_VILTE_DOWN_GRADE = "persist.vendor.vt.downgrade";
    private final String PROP_VILTE_SINK_BITSTREAM = "persist.vendor.vt.dump_sink";
    private final String PROP_VILTE_SOURCE_BITSTREAM = "persist.vendor.vt.dump_source";
    private final String PROP_VILTE_VFORMAT = "persist.vendor.vt.vformat";
    private final String PROP_VILTE_VHEIGHT = "persist.vendor.vt.vheight";
    private final String PROP_VILTE_VIDEO_FPS = "persist.vendor.vt.vfps";
    private final String PROP_VILTE_VIDEO_IDR_PERIOD = "persist.vendor.vt.viperiod";
    private final String PROP_VILTE_VIDEO_LEVEL = "persist.vendor.vt.vlevel";
    private final String PROP_VILTE_VIDEO_LEVEL_BIT_RATE = "persist.vendor.vt.vbitrate";
    private final String PROP_VILTE_VIDEO_PROFILE = "persist.vendor.vt.vprofile";
    private final String PROP_VILTE_VIDEO_VENC_BITRATE_RATIO = "persist.vendor.vt.bitrate_ratio";
    private final String PROP_VILTE_VWIDTH = "persist.vendor.vt.vwidth";
    private final String TAG = "Vilte/MenuCommon";
    private Button mButtonDisableDebugInfoDisplay;
    private Button mButtonDisableDownGrade;
    private Button mButtonDisableSinkBitstream;
    private Button mButtonDisableSourceBitstream;
    private Button mButtonDisabletAudioOutput;
    private Button mButtonEnableCamera;
    private Button mButtonEnableDebugInfoDisplay;
    private Button mButtonEnableDownGrade;
    private Button mButtonEnableSinkBitstream;
    private Button mButtonEnableSourceBitstream;
    private Button mButtonEnabletAudioOutput;
    private Button mButtonSetBitRate;
    private Button mButtonSetBitrateRatio;
    private Button mButtonSetFps;
    private Button mButtonSetIperiod;
    private Button mButtonSetLevel;
    private Button mButtonSetProfile;
    private Button mButtonVformat;
    private Button mButtonVheight;
    private Button mButtonVwidth;
    private EditText mEdittextBitRate;
    private EditText mEdittextBitrateRatio;
    private EditText mEdittextIperiod;
    private EditText mEdittextVheight;
    private EditText mEdittextVwidth;
    private Spinner mSpinner;
    private Spinner mSpinnerCamera;
    private Spinner mSpinnerLevel;
    private Spinner mSpinnerProfile;
    private Spinner mSpinnerVformat;
    private TextView mTextviewBitrate;
    private TextView mTextviewBitrateRatio;
    private TextView mTextviewCamera;
    private TextView mTextviewDebugInfoDisplay;
    private TextView mTextviewDownGrade;
    private TextView mTextviewIPeriod;
    private TextView mTextviewLevel;
    private TextView mTextviewProfile;
    private TextView mTextviewVformat;
    private TextView mTextviewVheight;
    private TextView mTextviewVwidth;
    private TextView mVilteAudioOutput;
    private TextView mVilteSinkBitstream;
    private TextView mVilteSourceBitstream;
    private TextView mVilteVideoFps;

    public VilteMenuCommon() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_common);
        Elog.d("Vilte/MenuCommon", "onCreate()");
        this.mVilteVideoFps = (TextView) findViewById(R.id.vilte_video_fps_status);
        this.mVilteSourceBitstream = (TextView) findViewById(R.id.vilte_source_bitstream_status);
        this.mVilteSinkBitstream = (TextView) findViewById(R.id.vilte_sink_bitstream_status);
        this.mTextviewLevel = (TextView) findViewById(R.id.vilte_video_level_status);
        this.mTextviewProfile = (TextView) findViewById(R.id.vilte_video_profile_status);
        this.mTextviewBitrate = (TextView) findViewById(R.id.vilte_video_bitrate_status);
        this.mTextviewBitrateRatio = (TextView) findViewById(R.id.vilte_video_bitrate_ratio_status);
        this.mTextviewIPeriod = (TextView) findViewById(R.id.vilte_video_iperiod_status);
        this.mTextviewDebugInfoDisplay = (TextView) findViewById(R.id.vilte_debug_info_display_status);
        this.mTextviewDownGrade = (TextView) findViewById(R.id.vilte_down_grade_status);
        this.mTextviewCamera = (TextView) findViewById(R.id.vilte_camera_status);
        this.mTextviewVformat = (TextView) findViewById(R.id.vilte_video_format);
        this.mTextviewVwidth = (TextView) findViewById(R.id.vilte_video_width);
        this.mTextviewVheight = (TextView) findViewById(R.id.vilte_video_height);
        this.mSpinner = (Spinner) findViewById(R.id.vilte_video_fps_values);
        this.mSpinnerLevel = (Spinner) findViewById(R.id.vilte_video_level_values);
        this.mSpinnerProfile = (Spinner) findViewById(R.id.vilte_video_profile_values);
        this.mSpinnerCamera = (Spinner) findViewById(R.id.vilte_camera_values);
        this.mSpinnerVformat = (Spinner) findViewById(R.id.vilte_video_format_values);
        this.mEdittextBitRate = (EditText) findViewById(R.id.vilte_video_level_bit_rate_values);
        this.mEdittextBitrateRatio = (EditText) findViewById(R.id.vilte_video_bitrate_ratio_values);
        this.mEdittextIperiod = (EditText) findViewById(R.id.vilte_video_idr_period_values);
        this.mEdittextVwidth = (EditText) findViewById(R.id.vilte_video_width_values);
        this.mEdittextVheight = (EditText) findViewById(R.id.vilte_video_height_values);
        this.mVilteAudioOutput = (TextView) findViewById(R.id.vilte_audio_output_status);
        this.mButtonSetFps = (Button) findViewById(R.id.vilte_video_fps_set);
        this.mButtonSetLevel = (Button) findViewById(R.id.vilte_video_level_set);
        this.mButtonSetProfile = (Button) findViewById(R.id.vilte_video_profile_set);
        this.mButtonSetBitRate = (Button) findViewById(R.id.vilte_video_level_bit_rate_set);
        this.mButtonSetBitrateRatio = (Button) findViewById(R.id.vilte_video_venc_bitrate_ratio_set);
        this.mButtonSetIperiod = (Button) findViewById(R.id.vilte_video_idr_period_set);
        this.mButtonEnableSourceBitstream = (Button) findViewById(R.id.vilte_source_bitstream_enable);
        this.mButtonDisableSourceBitstream = (Button) findViewById(R.id.vilte_source_bitstream_disable);
        this.mButtonEnableSinkBitstream = (Button) findViewById(R.id.vilte_sink_bitstream_enable);
        this.mButtonDisableSinkBitstream = (Button) findViewById(R.id.vilte_sink_bitstream_disable);
        this.mButtonEnableDebugInfoDisplay = (Button) findViewById(R.id.vilte_debug_info_dispaly_enable);
        this.mButtonDisableDebugInfoDisplay = (Button) findViewById(R.id.vilte_debug_info_dispaly_disable);
        this.mButtonEnableDownGrade = (Button) findViewById(R.id.vilte_downGrade_enable);
        this.mButtonEnableCamera = (Button) findViewById(R.id.vilte_camera_enable);
        this.mButtonDisableDownGrade = (Button) findViewById(R.id.vilte_downGrade_disable);
        this.mButtonVformat = (Button) findViewById(R.id.vilte_video_format_set);
        this.mButtonVwidth = (Button) findViewById(R.id.vilte_video_width_set);
        this.mButtonVheight = (Button) findViewById(R.id.vilte_video_height_set);
        this.mButtonEnabletAudioOutput = (Button) findViewById(R.id.vilte_audio_output_enable);
        this.mButtonDisabletAudioOutput = (Button) findViewById(R.id.vilte_audio_output_disable);
        this.mButtonSetFps.setOnClickListener(this);
        this.mButtonEnableSourceBitstream.setOnClickListener(this);
        this.mButtonDisableSourceBitstream.setOnClickListener(this);
        this.mButtonEnableSinkBitstream.setOnClickListener(this);
        this.mButtonDisableSinkBitstream.setOnClickListener(this);
        this.mButtonSetLevel.setOnClickListener(this);
        this.mButtonSetProfile.setOnClickListener(this);
        this.mButtonSetBitRate.setOnClickListener(this);
        this.mButtonSetBitrateRatio.setOnClickListener(this);
        this.mButtonSetIperiod.setOnClickListener(this);
        this.mButtonEnableDebugInfoDisplay.setOnClickListener(this);
        this.mButtonDisableDebugInfoDisplay.setOnClickListener(this);
        this.mButtonEnableDownGrade.setOnClickListener(this);
        this.mButtonDisableDownGrade.setOnClickListener(this);
        this.mButtonEnableCamera.setOnClickListener(this);
        this.mButtonVformat.setOnClickListener(this);
        this.mButtonVwidth.setOnClickListener(this);
        this.mButtonVheight.setOnClickListener(this);
        this.mButtonEnabletAudioOutput.setOnClickListener(this);
        this.mButtonDisabletAudioOutput.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d("Vilte/MenuCommon", "onResume()");
        queryCurrentValue();
    }

    public void onClick(View v) {
        if (v == this.mButtonSetFps) {
            String fps = this.mSpinner.getSelectedItem().toString();
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vfps = " + fps);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vfps", fps);
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnableSourceBitstream) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.dump_source = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.dump_source", "1");
            } catch (Exception e2) {
                e2.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonDisableSourceBitstream) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.dump_source = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.dump_source", "0");
            } catch (Exception e3) {
                e3.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnableSinkBitstream) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.dump_sink = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.dump_sink", "1");
            } catch (Exception e4) {
                e4.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonDisableSinkBitstream) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.dump_sink = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.dump_sink", "0");
            } catch (Exception e5) {
                e5.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonSetLevel) {
            String level = String.valueOf(this.mSpinnerLevel.getSelectedItemPosition());
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vlevel = " + level);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vlevel", level);
            } catch (Exception e6) {
                e6.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonSetBitRate) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vbitrate = " + this.mEdittextBitRate.getText().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vbitrate", this.mEdittextBitRate.getText().toString());
            } catch (Exception e7) {
                e7.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonSetBitrateRatio) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.bitrate_ratio = " + this.mEdittextBitrateRatio.getText().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.bitrate_ratio", this.mEdittextBitrateRatio.getText().toString());
            } catch (Exception e8) {
                e8.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonSetIperiod) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.viperiod = " + this.mEdittextIperiod.getText().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.viperiod", this.mEdittextIperiod.getText().toString());
            } catch (Exception e9) {
                e9.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonSetProfile) {
            String values = this.PROFILE_VALUES[this.mSpinnerProfile.getSelectedItemPosition()];
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vprofile = " + values);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vprofile", values);
            } catch (Exception e10) {
                e10.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnableDebugInfoDisplay) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.RTPInfo = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.RTPInfo", "1");
            } catch (Exception e11) {
                e11.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonDisableDebugInfoDisplay) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.RTPInfo = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.RTPInfo", "0");
            } catch (Exception e12) {
                e12.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnableDownGrade) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.downgrade = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.downgrade", "1");
            } catch (Exception e13) {
                e13.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonDisableDownGrade) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.downgrade = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.downgrade", "0");
            } catch (Exception e14) {
                e14.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnableCamera) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.camera" + this.mSpinnerCamera.getSelectedItem().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.camera", this.mSpinnerCamera.getSelectedItem().toString());
            } catch (Exception e15) {
                e15.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonVformat) {
            String format = String.valueOf(this.mSpinnerVformat.getSelectedItemPosition());
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vformat" + format);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vformat", format);
            } catch (Exception e16) {
                e16.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonVwidth) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vwidth" + this.mEdittextVwidth.getText().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vwidth", this.mEdittextVwidth.getText().toString());
            } catch (Exception e17) {
                e17.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonVheight) {
            Elog.d("Vilte/MenuCommon", "Set persist.vendor.vt.vheight" + this.mEdittextVheight.getText().toString());
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.vheight", this.mEdittextVheight.getText().toString());
            } catch (Exception e18) {
                e18.printStackTrace();
                Elog.e("Vilte/MenuCommon", "set property failed ...");
            }
        } else if (v == this.mButtonEnabletAudioOutput) {
            Elog.d("Vilte/MenuCommon", "Set persist.radio.call.audio.output = 0");
            EmUtils.systemPropertySet("persist.radio.call.audio.output", "0");
        } else if (v == this.mButtonDisabletAudioOutput) {
            Elog.d("Vilte/MenuCommon", "Set persist.radio.call.audio.output = 1");
            EmUtils.systemPropertySet("persist.radio.call.audio.output", "1");
        }
        queryCurrentValue();
    }

    private void queryCurrentValue() {
        String fps = EmUtils.systemPropertyGet("persist.vendor.vt.vfps", "0");
        String level = EmUtils.systemPropertyGet("persist.vendor.vt.vlevel", "0");
        String profile = EmUtils.systemPropertyGet("persist.vendor.vt.vprofile", "0");
        String bitrate = EmUtils.systemPropertyGet("persist.vendor.vt.vbitrate", "");
        String bitrateRatio = EmUtils.systemPropertyGet("persist.vendor.vt.bitrate_ratio", "");
        String iPeriod = EmUtils.systemPropertyGet("persist.vendor.vt.viperiod", "");
        String source = EmUtils.systemPropertyGet("persist.vendor.vt.dump_source", "");
        String sink = EmUtils.systemPropertyGet("persist.vendor.vt.dump_sink", "");
        String debugInfoDisplay = EmUtils.systemPropertyGet("persist.vendor.vt.RTPInfo", "");
        String downGrade = EmUtils.systemPropertyGet("persist.vendor.vt.downgrade", "");
        String camera = EmUtils.systemPropertyGet("persist.vendor.vt.camera", "");
        String format = EmUtils.systemPropertyGet("persist.vendor.vt.vformat", "0");
        String width = EmUtils.systemPropertyGet("persist.vendor.vt.vwidth", "");
        String downGrade2 = downGrade;
        String height = EmUtils.systemPropertyGet("persist.vendor.vt.vheight", "");
        String audioOutput = EmUtils.systemPropertyGet("persist.radio.call.audio.output", "0");
        TextView textView = this.mVilteVideoFps;
        String audioOutput2 = audioOutput;
        StringBuilder sb = new StringBuilder();
        String debugInfoDisplay2 = debugInfoDisplay;
        sb.append("persist.vendor.vt.vfps = ");
        sb.append(fps);
        textView.setText(sb.toString());
        TextView textView2 = this.mVilteSourceBitstream;
        textView2.setText("persist.vendor.vt.dump_source = " + source);
        TextView textView3 = this.mVilteSinkBitstream;
        textView3.setText("persist.vendor.vt.dump_sink = " + sink);
        TextView textView4 = this.mTextviewLevel;
        textView4.setText("persist.vendor.vt.vlevel = " + level);
        TextView textView5 = this.mTextviewProfile;
        textView5.setText("persist.vendor.vt.vprofile = " + profile);
        TextView textView6 = this.mTextviewBitrate;
        textView6.setText("persist.vendor.vt.vbitrate = " + bitrate);
        TextView textView7 = this.mTextviewBitrateRatio;
        textView7.setText("persist.vendor.vt.bitrate_ratio = " + bitrateRatio);
        TextView textView8 = this.mTextviewIPeriod;
        textView8.setText("persist.vendor.vt.viperiod = " + iPeriod);
        int i = 0;
        while (true) {
            if (i >= this.mSpinner.getCount()) {
                break;
            } else if (this.mSpinner.getItemAtPosition(i).toString().equals(fps)) {
                this.mSpinner.setSelection(i);
                break;
            } else {
                i++;
            }
        }
        String profile_label = this.PROFILE_LABLE[Integer.valueOf(profile).intValue()];
        int i2 = 0;
        while (true) {
            if (i2 >= this.mSpinnerProfile.getCount()) {
                break;
            } else if (this.mSpinnerProfile.getItemAtPosition(i2).toString().equals(profile_label)) {
                this.mSpinnerProfile.setSelection(i2);
                break;
            } else {
                i2++;
            }
        }
        this.mSpinnerLevel.setSelection(Integer.parseInt(level));
        this.mSpinnerVformat.setSelection(Integer.parseInt(format));
        int i3 = 0;
        while (true) {
            if (i3 >= this.mSpinnerCamera.getCount()) {
                break;
            } else if (this.mSpinnerCamera.getItemAtPosition(i3).toString().equals(camera)) {
                this.mSpinnerCamera.setSelection(i3);
                break;
            } else {
                i3++;
            }
        }
        this.mEdittextBitRate.setText(bitrate);
        this.mEdittextBitrateRatio.setText(bitrateRatio);
        this.mEdittextIperiod.setText(iPeriod);
        this.mEdittextVwidth.setText(width);
        this.mEdittextVheight.setText(height);
        TextView textView9 = this.mTextviewDebugInfoDisplay;
        StringBuilder sb2 = new StringBuilder();
        String str = fps;
        sb2.append("persist.vendor.vt.RTPInfo = ");
        sb2.append(debugInfoDisplay2);
        textView9.setText(sb2.toString());
        TextView textView10 = this.mTextviewDownGrade;
        textView10.setText("persist.vendor.vt.downgrade = " + downGrade2);
        TextView textView11 = this.mTextviewCamera;
        textView11.setText("persist.vendor.vt.camera = " + camera);
        TextView textView12 = this.mTextviewVformat;
        textView12.setText("persist.vendor.vt.vformat = " + format);
        TextView textView13 = this.mTextviewVwidth;
        textView13.setText("persist.vendor.vt.vwidth = " + width);
        TextView textView14 = this.mTextviewVheight;
        textView14.setText("persist.vendor.vt.vheight = " + height);
        TextView textView15 = this.mVilteAudioOutput;
        textView15.setText("persist.radio.call.audio.output = " + audioOutput2);
    }
}
