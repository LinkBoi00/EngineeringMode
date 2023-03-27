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

public class VilteMenuMedia extends Activity implements View.OnClickListener {
    private final String PROP_VILTE_OPTEST_MA = "persist.vendor.vt.OPTest_MA";
    private final String PROP_VILTE_OPTEST_MM = "persist.vendor.vt.OPTest_MM";
    private final String PROP_VILTE_OPTEST_RTP = "persist.vendor.vt.OPTest_RTP";
    private final String PROP_VILTE_VT_DE_DIFF = "persist.vendor.vt.de_diff";
    private final String PROP_VILTE_VT_DE_START = "persist.vendor.vt.de_start";
    private final String PROP_VILTE_VT_DOWN_GRADE_TH = "persist.vendor.vt.DownGradeTH";
    private final String PROP_VILTE_VT_RTP_QDURHW = "persist.vendor.vt.rtpQDurHW";
    private final String PROP_VILTE_VT_RTP_RES1 = "persist.vendor.vt.rtp_res1";
    private final String PROP_VILTE_VT_RTP_RES2 = "persist.vendor.vt.rtp_res2";
    private final String TAG = "Vilte/MenuMedia";
    private TextView mVilte_Reserved1_view;
    private TextView mVilte_Reserved2_view;
    private TextView mVilte_TX_fallback_queue_time_view;
    private TextView mVilte_customize_status_view;
    private TextView mVilte_downgrade_packetlost_view;
    private TextView mVilte_ma_view;
    private Button mVilte_operator_media_customize_mode_disable;
    private Button mVilte_operator_media_customize_mode_enable;
    private Button mVilte_operator_media_downgrade_packetlost_set;
    private EditText mVilte_operator_media_downgrade_packetlost_values;
    private Button mVilte_operator_media_ma_set;
    private EditText mVilte_operator_media_ma_values;
    private Button mVilte_operator_media_rtp_set;
    private EditText mVilte_operator_media_rtp_values;
    private Button mVilte_operator_media_tmmbr_decrease_diff_set;
    private EditText mVilte_operator_media_tmmbr_decrease_diff_values;
    private Button mVilte_operator_media_tmmbr_decrease_start_set;
    private EditText mVilte_operator_media_tmmbr_decrease_start_values;
    private Button mVilte_operator_media_tmmbr_reserved1_set;
    private EditText mVilte_operator_media_tmmbr_reserved1_values;
    private Button mVilte_operator_media_tmmbr_reserved2_set;
    private EditText mVilte_operator_media_tmmbr_reserved2_values;
    private Button mVilte_operator_media_tx_fallback_set;
    private EditText mVilte_operator_media_tx_fallback_values;
    private TextView mVilte_rtp_view;
    private TextView mVilte_tmmbr_decrease_diff_view;
    private TextView mVilte_tmmbr_decrease_start_view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_media);
        bindViews();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d("Vilte/MenuMedia", "onResume()");
        queryCurrentValue();
    }

    private void queryCurrentValue() {
        String mm = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_MM", "");
        String rtp = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_RTP", "");
        String ma = EmUtils.systemPropertyGet("persist.vendor.vt.OPTest_MA", "");
        String rtp_qdurhw = EmUtils.systemPropertyGet("persist.vendor.vt.rtpQDurHW", "");
        String down_grade_th = EmUtils.systemPropertyGet("persist.vendor.vt.DownGradeTH", "");
        String de_start = EmUtils.systemPropertyGet("persist.vendor.vt.de_start", "");
        String de_diff = EmUtils.systemPropertyGet("persist.vendor.vt.de_diff", "");
        String rtp_res1 = EmUtils.systemPropertyGet("persist.vendor.vt.rtp_res1", "");
        String rtp_res2 = EmUtils.systemPropertyGet("persist.vendor.vt.rtp_res2", "");
        TextView textView = this.mVilte_customize_status_view;
        textView.setText("persist.vendor.vt.OPTest_MM = " + mm);
        TextView textView2 = this.mVilte_rtp_view;
        textView2.setText("persist.vendor.vt.OPTest_RTP = " + rtp);
        this.mVilte_operator_media_rtp_values.setText(rtp);
        TextView textView3 = this.mVilte_ma_view;
        textView3.setText("persist.vendor.vt.OPTest_MA = " + ma);
        this.mVilte_operator_media_ma_values.setText(ma);
        TextView textView4 = this.mVilte_TX_fallback_queue_time_view;
        textView4.setText("persist.vendor.vt.rtpQDurHW = " + rtp_qdurhw);
        this.mVilte_operator_media_tx_fallback_values.setText(rtp_qdurhw);
        TextView textView5 = this.mVilte_downgrade_packetlost_view;
        textView5.setText("persist.vendor.vt.DownGradeTH = " + down_grade_th);
        this.mVilte_operator_media_downgrade_packetlost_values.setText(down_grade_th);
        TextView textView6 = this.mVilte_tmmbr_decrease_start_view;
        textView6.setText("persist.vendor.vt.de_start = " + de_start);
        this.mVilte_operator_media_tmmbr_decrease_start_values.setText(de_start);
        TextView textView7 = this.mVilte_tmmbr_decrease_diff_view;
        textView7.setText("persist.vendor.vt.de_diff = " + de_diff);
        this.mVilte_operator_media_tmmbr_decrease_diff_values.setText(de_diff);
        TextView textView8 = this.mVilte_Reserved1_view;
        textView8.setText("persist.vendor.vt.rtp_res1 = " + rtp_res1);
        this.mVilte_operator_media_tmmbr_reserved1_values.setText(rtp_res1);
        TextView textView9 = this.mVilte_Reserved2_view;
        textView9.setText("persist.vendor.vt.rtp_res2 = " + rtp_res2);
        this.mVilte_operator_media_tmmbr_reserved2_values.setText(rtp_res2);
    }

    /* access modifiers changed from: package-private */
    public boolean checkValue(View v, String values) {
        if (v == this.mVilte_operator_media_ma_set || v == this.mVilte_operator_media_rtp_set) {
            try {
                Integer.parseInt(values, 16);
                return true;
            } catch (NumberFormatException e) {
                EmUtils.showToast("value should be 16 HEX", 0);
                return false;
            }
        } else if (v != this.mVilte_operator_media_downgrade_packetlost_set && v != this.mVilte_operator_media_tmmbr_decrease_start_set && v != this.mVilte_operator_media_tmmbr_decrease_diff_set) {
            return true;
        } else {
            if (values.equals("")) {
                return false;
            }
            int value = Integer.parseInt(values, 10);
            if (value >= 0 && value <= 100) {
                return true;
            }
            EmUtils.showToast("value should be 16 HEX", 0);
            return false;
        }
    }

    public void onClick(View v) {
        if (v == this.mVilte_operator_media_customize_mode_enable) {
            Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.OPTest_MM = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_MM", "1");
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e("Vilte/MenuMedia", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_media_customize_mode_disable) {
            Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.OPTest_MM = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_MM", "0");
            } catch (Exception e2) {
                e2.printStackTrace();
                Elog.e("Vilte/MenuMedia", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_media_rtp_set) {
            String value = this.mVilte_operator_media_rtp_values.getText().toString();
            if (checkValue(this.mVilte_operator_media_rtp_set, value)) {
                Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.OPTest_RTP = " + value);
                try {
                    EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_RTP", value);
                } catch (Exception e3) {
                    e3.printStackTrace();
                    Elog.e("Vilte/MenuMedia", "set property failed ...");
                }
            } else {
                Elog.d("Vilte/MenuMedia", "Input value format error ");
            }
        } else if (v == this.mVilte_operator_media_ma_set) {
            String value2 = this.mVilte_operator_media_ma_values.getText().toString();
            if (checkValue(this.mVilte_operator_media_ma_set, value2)) {
                Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.OPTest_MA = " + value2);
                try {
                    EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.OPTest_MA", value2);
                } catch (Exception e4) {
                    e4.printStackTrace();
                    Elog.e("Vilte/MenuMedia", "set property failed ...");
                }
            } else {
                Elog.d("Vilte/MenuMedia", "Input value format error ");
            }
        } else if (v == this.mVilte_operator_media_tx_fallback_set) {
            String value3 = this.mVilte_operator_media_tx_fallback_values.getText().toString();
            Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.rtpQDurHW = " + value3);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.rtpQDurHW", value3);
            } catch (Exception e5) {
                e5.printStackTrace();
                Elog.e("Vilte/MenuMedia", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_media_downgrade_packetlost_set) {
            String value4 = this.mVilte_operator_media_downgrade_packetlost_values.getText().toString();
            if (checkValue(this.mVilte_operator_media_downgrade_packetlost_set, value4)) {
                Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.DownGradeTH = " + value4);
                try {
                    EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.DownGradeTH", value4);
                } catch (Exception e6) {
                    e6.printStackTrace();
                    Elog.e("Vilte/MenuMedia", "set property failed ...");
                }
            } else {
                Elog.d("Vilte/MenuMedia", "Input value format error ");
            }
        } else if (v == this.mVilte_operator_media_tmmbr_decrease_start_set) {
            String value5 = this.mVilte_operator_media_tmmbr_decrease_start_values.getText().toString();
            if (checkValue(this.mVilte_operator_media_tmmbr_decrease_start_set, value5)) {
                Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.de_start = " + value5);
                try {
                    EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.de_start", value5);
                } catch (Exception e7) {
                    e7.printStackTrace();
                    Elog.e("Vilte/MenuMedia", "set property failed ...");
                }
            } else {
                Elog.d("Vilte/MenuMedia", "Input value format error ");
            }
        } else if (v == this.mVilte_operator_media_tmmbr_decrease_diff_set) {
            String value6 = this.mVilte_operator_media_tmmbr_decrease_diff_values.getText().toString();
            if (checkValue(this.mVilte_operator_media_tmmbr_decrease_diff_set, value6)) {
                Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.de_diff = " + value6);
                try {
                    EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.de_diff", value6);
                } catch (Exception e8) {
                    e8.printStackTrace();
                    Elog.e("Vilte/MenuMedia", "set property failed ...");
                }
            } else {
                Elog.d("Vilte/MenuMedia", "Input value format error ");
            }
        } else if (v == this.mVilte_operator_media_tmmbr_reserved1_set) {
            String value7 = this.mVilte_operator_media_tmmbr_reserved1_values.getText().toString();
            Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.rtp_res1 = " + value7);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.rtp_res1", value7);
            } catch (Exception e9) {
                e9.printStackTrace();
                Elog.e("Vilte/MenuMedia", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_media_tmmbr_reserved2_set) {
            String value8 = this.mVilte_operator_media_tmmbr_reserved2_values.getText().toString();
            Elog.d("Vilte/MenuMedia", "Set persist.vendor.vt.rtp_res2 = " + value8);
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.rtp_res2", value8);
            } catch (Exception e10) {
                e10.printStackTrace();
                Elog.e("Vilte/MenuMedia", "set property failed ...");
            }
        }
        queryCurrentValue();
    }

    private void bindViews() {
        this.mVilte_operator_media_customize_mode_enable = (Button) findViewById(R.id.vilte_operator_media_customize_mode_enable);
        this.mVilte_operator_media_customize_mode_disable = (Button) findViewById(R.id.vilte_operator_media_customize_mode_disable);
        this.mVilte_operator_media_customize_mode_enable.setOnClickListener(this);
        this.mVilte_operator_media_customize_mode_disable.setOnClickListener(this);
        this.mVilte_operator_media_rtp_values = (EditText) findViewById(R.id.vilte_operator_media_rtp_values);
        Button button = (Button) findViewById(R.id.vilte_operator_media_rtp_set);
        this.mVilte_operator_media_rtp_set = button;
        button.setOnClickListener(this);
        this.mVilte_operator_media_ma_values = (EditText) findViewById(R.id.vilte_operator_media_ma_values);
        Button button2 = (Button) findViewById(R.id.vilte_operator_media_ma_set);
        this.mVilte_operator_media_ma_set = button2;
        button2.setOnClickListener(this);
        this.mVilte_operator_media_tx_fallback_values = (EditText) findViewById(R.id.vilte_operator_media_tx_fallback_values);
        Button button3 = (Button) findViewById(R.id.vilte_operator_media_tx_fallback_set);
        this.mVilte_operator_media_tx_fallback_set = button3;
        button3.setOnClickListener(this);
        this.mVilte_operator_media_downgrade_packetlost_values = (EditText) findViewById(R.id.vilte_operator_media_downgrade_packetlost_values);
        Button button4 = (Button) findViewById(R.id.vilte_operator_media_downgrade_packetlost_set);
        this.mVilte_operator_media_downgrade_packetlost_set = button4;
        button4.setOnClickListener(this);
        this.mVilte_operator_media_tmmbr_decrease_start_values = (EditText) findViewById(R.id.vilte_operator_media_tmmbr_decrease_start_values);
        Button button5 = (Button) findViewById(R.id.vilte_operator_media_tmmbr_decrease_start_set);
        this.mVilte_operator_media_tmmbr_decrease_start_set = button5;
        button5.setOnClickListener(this);
        this.mVilte_operator_media_tmmbr_decrease_diff_values = (EditText) findViewById(R.id.vilte_operator_media_tmmbr_decrease_diff_values);
        Button button6 = (Button) findViewById(R.id.vilte_operator_media_tmmbr_decrease_diff_set);
        this.mVilte_operator_media_tmmbr_decrease_diff_set = button6;
        button6.setOnClickListener(this);
        this.mVilte_operator_media_tmmbr_reserved1_values = (EditText) findViewById(R.id.vilte_operator_media_tmmbr_reserved1_values);
        Button button7 = (Button) findViewById(R.id.vilte_operator_media_tmmbr_reserved1_set);
        this.mVilte_operator_media_tmmbr_reserved1_set = button7;
        button7.setOnClickListener(this);
        this.mVilte_operator_media_tmmbr_reserved2_values = (EditText) findViewById(R.id.vilte_operator_media_tmmbr_reserved2_values);
        Button button8 = (Button) findViewById(R.id.vilte_operator_media_tmmbr_reserved2_set);
        this.mVilte_operator_media_tmmbr_reserved2_set = button8;
        button8.setOnClickListener(this);
        this.mVilte_customize_status_view = (TextView) findViewById(R.id.vilte_customize_status_view);
        this.mVilte_rtp_view = (TextView) findViewById(R.id.vilte_rtp_view);
        this.mVilte_ma_view = (TextView) findViewById(R.id.vilte_ma_view);
        this.mVilte_TX_fallback_queue_time_view = (TextView) findViewById(R.id.vilte_TX_fallback_queue_time_view);
        this.mVilte_downgrade_packetlost_view = (TextView) findViewById(R.id.vilte_downgrade_packetlost_view);
        this.mVilte_tmmbr_decrease_start_view = (TextView) findViewById(R.id.vilte_tmmbr_decrease_start_view);
        this.mVilte_tmmbr_decrease_diff_view = (TextView) findViewById(R.id.vilte_tmmbr_decrease_diff_view);
        this.mVilte_Reserved1_view = (TextView) findViewById(R.id.vilte_Reserved1_view);
        this.mVilte_Reserved2_view = (TextView) findViewById(R.id.vilte_Reserved2_view);
    }
}
