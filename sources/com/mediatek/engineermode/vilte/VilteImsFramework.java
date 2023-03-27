package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class VilteImsFramework extends Activity implements View.OnClickListener {
    private final String PROP_VILTE_CONFERENCE_SUPPORT = "persist.vendor.vt.video_conference_support";
    private final String TAG = "Vilte/ImsFramework";
    private Button mVilte_operator_imsframework_video_conference_disable;
    private Button mVilte_operator_imsframework_video_conference_enable;
    private TextView mVilte_video_conference_view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_ims_framework);
        bindViews();
    }

    private void bindViews() {
        this.mVilte_operator_imsframework_video_conference_enable = (Button) findViewById(R.id.vilte_operator_imsframework_video_conference_enable);
        this.mVilte_operator_imsframework_video_conference_disable = (Button) findViewById(R.id.vilte_operator_imsframework_video_conference_disable);
        this.mVilte_operator_imsframework_video_conference_enable.setOnClickListener(this);
        this.mVilte_operator_imsframework_video_conference_disable.setOnClickListener(this);
        this.mVilte_video_conference_view = (TextView) findViewById(R.id.vilte_video_conference_view);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d("Vilte/ImsFramework", "onResume()");
        queryCurrentValue();
    }

    private void queryCurrentValue() {
        String videoConferenceSupport = EmUtils.systemPropertyGet("persist.vendor.vt.video_conference_support", "");
        TextView textView = this.mVilte_video_conference_view;
        textView.setText("persist.vendor.vt.video_conference_support = " + videoConferenceSupport);
    }

    public void onClick(View v) {
        if (v == this.mVilte_operator_imsframework_video_conference_enable) {
            Elog.d("Vilte/ImsFramework", "Set persist.vendor.vt.video_conference_support = 1");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.video_conference_support", "1");
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e("Vilte/ImsFramework", "set property failed ...");
            }
        } else if (v == this.mVilte_operator_imsframework_video_conference_disable) {
            Elog.d("Vilte/ImsFramework", "Set persist.vendor.vt.video_conference_support = 0");
            try {
                EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.video_conference_support", "0");
            } catch (Exception e2) {
                e2.printStackTrace();
                Elog.e("Vilte/ImsFramework", "set property failed ...");
            }
        }
        queryCurrentValue();
    }
}
