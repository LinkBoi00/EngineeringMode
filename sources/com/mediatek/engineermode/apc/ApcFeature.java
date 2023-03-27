package com.mediatek.engineermode.apc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.telephony.MtkTelephonyManagerEx;

public class ApcFeature extends Activity implements View.OnClickListener {
    private static final int CHECK_INFOMATION_ID = 1;
    private static final String TAG = "ApcFeature";
    private static boolean mIsApcEnabled = false;
    private CheckBox mApcEnable;
    private CheckBox mReportEnable;
    private EditText mReportTimer;
    private Button mSetButton;
    private int mSimType;
    private MtkTelephonyManagerEx mTelephonyManagerEx;
    private int mTimer = 600;
    private Toast mToast = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apc_feature);
        Button button = (Button) findViewById(R.id.apc_set);
        this.mSetButton = button;
        button.setOnClickListener(this);
        this.mApcEnable = (CheckBox) findViewById(R.id.cb_apc_enable);
        this.mReportEnable = (CheckBox) findViewById(R.id.cb_report_enable);
        this.mReportTimer = (EditText) findViewById(R.id.apc_report_timer);
    }

    public void onResume() {
        super.onResume();
        this.mSimType = getIntent().getIntExtra("mSimType", ModemCategory.getCapabilitySim());
        this.mTelephonyManagerEx = MtkTelephonyManagerEx.getDefault();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, getString(R.string.apc_menu));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem aMenuItem) {
        switch (aMenuItem.getItemId()) {
            case 1:
                if (mIsApcEnabled) {
                    Intent intent = new Intent(this, ApcFakeInfo.class);
                    intent.putExtra("mSimType", this.mSimType);
                    startActivity(intent);
                    break;
                } else {
                    EmUtils.showToast("APC is not enabled!", 0);
                    break;
                }
        }
        return super.onOptionsItemSelected(aMenuItem);
    }

    public void onClick(View arg0) {
        String reportTimer = this.mReportTimer.getText().toString();
        if (reportTimer == null || reportTimer.length() <= 0) {
            this.mTimer = 600;
        } else {
            try {
                this.mTimer = Integer.parseInt(reportTimer);
            } catch (NumberFormatException e) {
                Elog.w(TAG, "Invalid format: " + reportTimer);
                EmUtils.showToast("Invalid value", 0);
            }
        }
        if (arg0 != this.mSetButton) {
            return;
        }
        if (this.mApcEnable.isChecked() && !mIsApcEnabled) {
            Elog.d(TAG, "mSimType: " + this.mSimType);
            Elog.d(TAG, "mReportEnable.isChecked(): " + this.mReportEnable.isChecked());
            Elog.d(TAG, "mTimer: " + this.mTimer);
            mIsApcEnabled = true;
            this.mTelephonyManagerEx.setApcMode(this.mSimType, 1, this.mReportEnable.isChecked(), this.mTimer);
            EmUtils.showToast("enable APC done", 0);
        } else if (mIsApcEnabled && !this.mApcEnable.isChecked()) {
            this.mTelephonyManagerEx.setApcMode(this.mSimType, 0, false, 0);
            mIsApcEnabled = false;
            EmUtils.showToast("disable APC done", 0);
        }
    }
}
