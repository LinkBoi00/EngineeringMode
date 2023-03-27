package com.mediatek.engineermode;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class AutoAnswer extends Activity implements View.OnClickListener {
    private final String AUTO_ANSWER_PROPERTY = "persist.vendor.em.hidl.auto_answer";
    private final String OEM_HOOK_STRING_AUTO_ANSWER = "AUTO_ANSWER";
    private final String TAG = "AutoAnswer";
    private Button mDisableButton;
    private Button mEnableButton;
    private int mResult = -1;
    private String mStatus = "0";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_answer);
        this.mEnableButton = (Button) findViewById(R.id.autoanswer_enable);
        this.mDisableButton = (Button) findViewById(R.id.autoanswer_disable);
        this.mEnableButton.setOnClickListener(this);
        this.mDisableButton.setOnClickListener(this);
        queryAutoAnswerStatus();
        updateButtonStatus();
        if (this.mStatus.equals("0")) {
            setAutoAnswer(0);
        } else if (this.mStatus.equals("1")) {
            setAutoAnswer(1);
        }
    }

    public void onClick(View arg0) {
        if (arg0.getId() == R.id.autoanswer_enable) {
            setAutoAnswer(1);
        } else if (arg0.getId() == R.id.autoanswer_disable) {
            setAutoAnswer(0);
        }
        queryAutoAnswerStatus();
        updateButtonStatus();
    }

    private void queryAutoAnswerStatus() {
        Elog.v("AutoAnswer", "queryAutoAnswerStatus");
        this.mStatus = EmUtils.systemPropertyGet("persist.vendor.em.hidl.auto_answer", "0");
        Elog.v("AutoAnswer", "The autoanswer flag is : " + this.mStatus);
    }

    private void setAutoAnswer(int mode) {
        Elog.i("AutoAnswer", "setAutoAnswer to " + mode);
        try {
            EmUtils.getEmHidlService().setEmConfigure("persist.vendor.em.hidl.auto_answer", String.valueOf(mode));
            EmUtils.invokeOemRilRequestStringsEm(new String[]{"AUTO_ANSWER", String.valueOf(mode)}, (Message) null);
        } catch (Exception e) {
            e.printStackTrace();
            Elog.e("AutoAnswer", "set property failed ...");
        }
    }

    private void updateButtonStatus() {
        if (this.mStatus.equals("0")) {
            this.mEnableButton.setEnabled(true);
            this.mDisableButton.setEnabled(false);
            return;
        }
        this.mEnableButton.setEnabled(false);
        this.mDisableButton.setEnabled(true);
    }
}
