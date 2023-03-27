package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class VilteMenuCodec extends Activity implements View.OnClickListener {
    private final String PROP_VILTE_OPTEST_VCODEC = "persist.vendor.vt.OPTest_vcodec";
    private final String PROP_VILTE_OPTEST_VDEC = "persist.vendor.vt.OPTest_vdec";
    private final String PROP_VILTE_OPTEST_VENC = "persist.vendor.vt.OPTest_venc";
    private final String TAG = "Vilte/MenuCodec";
    private TextView mVilte_customize_status_view;
    private Button mVilte_operator_codec_vdec_set;
    private EditText mVilte_operator_codec_vdec_values;
    private Button mVilte_operator_codec_venc_set;
    private EditText mVilte_operator_codec_venc_values;
    private Button mVilte_operator_media_customize_mode_disable;
    private Button mVilte_operator_media_customize_mode_enable;
    private TextView mVilte_vdec_view;
    private TextView mVilte_venc_view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_codec);
        bindViews();
    }

    private void bindViews() {
        this.mVilte_operator_media_customize_mode_enable = (Button) findViewById(R.id.vilte_operator_media_customize_mode_enable);
        this.mVilte_operator_media_customize_mode_disable = (Button) findViewById(R.id.vilte_operator_media_customize_mode_disable);
        this.mVilte_operator_media_customize_mode_enable.setOnClickListener(this);
        this.mVilte_operator_media_customize_mode_disable.setOnClickListener(this);
        this.mVilte_operator_codec_venc_values = (EditText) findViewById(R.id.vilte_operator_codec_venc_values);
        Button button = (Button) findViewById(R.id.vilte_operator_codec_venc_set);
        this.mVilte_operator_codec_venc_set = button;
        button.setOnClickListener(this);
        this.mVilte_operator_codec_vdec_values = (EditText) findViewById(R.id.vilte_operator_codec_vdec_values);
        Button button2 = (Button) findViewById(R.id.vilte_operator_codec_vdec_set);
        this.mVilte_operator_codec_vdec_set = button2;
        button2.setOnClickListener(this);
        this.mVilte_customize_status_view = (TextView) findViewById(R.id.vilte_customize_status_view);
        this.mVilte_venc_view = (TextView) findViewById(R.id.vilte_venc_view);
        this.mVilte_vdec_view = (TextView) findViewById(R.id.vilte_vdec_view);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d("Vilte/MenuCodec", "onResume()");
        queryCurrentValue();
    }

    private void queryCurrentValue() {
        String vcodec = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_vcodec", "");
        String venc = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_venc", "");
        String vdec = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_vdec", "");
        TextView textView = this.mVilte_customize_status_view;
        textView.setText("persist.vendor.vt.OPTest_vcodec = " + vcodec);
        TextView textView2 = this.mVilte_venc_view;
        textView2.setText("persist.vendor.vt.OPTest_venc = " + venc);
        this.mVilte_operator_codec_venc_values.setText(venc);
        TextView textView3 = this.mVilte_vdec_view;
        textView3.setText("persist.vendor.vt.OPTest_vdec = " + vdec);
        this.mVilte_operator_codec_vdec_values.setText(vdec);
    }

    /* access modifiers changed from: package-private */
    public boolean checkValue(View v, String values) {
        if (v != this.mVilte_operator_codec_venc_set && v != this.mVilte_operator_codec_vdec_set) {
            return true;
        }
        try {
            Integer.parseInt(values, 16);
            return true;
        } catch (NumberFormatException e) {
            EmUtils.showToast("value should be 16 HEX", 0);
            return false;
        }
    }

    public void onClick(View v) {
        if (v == this.mVilte_operator_media_customize_mode_enable) {
            Elog.d("Vilte/MenuCodec", "Set persist.vendor.vt.OPTest_vcodec = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_vcodec", "1");
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e("Vilte/MenuCodec", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_media_customize_mode_disable) {
            Elog.d("Vilte/MenuCodec", "Set persist.vendor.vt.OPTest_vcodec = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_vcodec", "0");
            } catch (Exception e2) {
                e2.printStackTrace();
                Elog.e("Vilte/MenuCodec", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_codec_venc_set) {
            String value = this.mVilte_operator_codec_venc_values.getText().toString();
            Elog.d("Vilte/MenuCodec", "Set persist.vendor.vt.OPTest_venc = " + value);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_venc", value);
            } catch (Exception e3) {
                e3.printStackTrace();
                Elog.e("Vilte/MenuCodec", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_codec_vdec_set) {
            String value2 = this.mVilte_operator_codec_vdec_values.getText().toString();
            Elog.d("Vilte/MenuCodec", "Set persist.vendor.vt.OPTest_vdec = " + value2);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_vdec", value2);
            } catch (Exception e4) {
                e4.printStackTrace();
                Elog.e("Vilte/MenuCodec", "set property failed ...");
            }
        }
        queryCurrentValue();
    }
}
