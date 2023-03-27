package com.mediatek.engineermode.testsimswitch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class TestSimSwitchActivity extends Activity {
    private static final String PROP_TESTSIM_CARDTYPE = "persist.vendor.radio.testsim.cardtype";
    private static final int REBOOT = 0;
    private static final String TAG = "testsimswitch";
    /* access modifiers changed from: private */
    public RadioButton mRadioBtnCdma;
    /* access modifiers changed from: private */
    public RadioButton mRadioBtnDefault;
    /* access modifiers changed from: private */
    public RadioButton mRadioBtnGsm;
    /* access modifiers changed from: private */
    public String mTestSimType = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testsimswitch);
        this.mRadioBtnDefault = (RadioButton) findViewById(R.id.simtype_default_radio);
        this.mRadioBtnCdma = (RadioButton) findViewById(R.id.simtype_cdma_radio);
        this.mRadioBtnGsm = (RadioButton) findViewById(R.id.simtype_gsm_radio);
        ((Button) findViewById(R.id.simtype_set_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (TestSimSwitchActivity.this.mRadioBtnDefault.isChecked()) {
                    String unused = TestSimSwitchActivity.this.mTestSimType = "0";
                } else if (TestSimSwitchActivity.this.mRadioBtnCdma.isChecked()) {
                    String unused2 = TestSimSwitchActivity.this.mTestSimType = "1";
                } else if (TestSimSwitchActivity.this.mRadioBtnGsm.isChecked()) {
                    String unused3 = TestSimSwitchActivity.this.mTestSimType = "2";
                } else {
                    String unused4 = TestSimSwitchActivity.this.mTestSimType = "";
                }
                try {
                    EmUtils.getEmHidlService().setTestSimCardType(TestSimSwitchActivity.this.mTestSimType);
                } catch (Exception e) {
                    e.printStackTrace();
                    Elog.e(TestSimSwitchActivity.TAG, "set property failed ...");
                }
                Elog.d(TestSimSwitchActivity.TAG, "set persist.radio.testsim.cardtype to " + TestSimSwitchActivity.this.mTestSimType);
                TestSimSwitchActivity.this.showDialog(0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        queryTestSimType();
    }

    private void queryTestSimType() {
        this.mTestSimType = SystemProperties.get(PROP_TESTSIM_CARDTYPE, "0");
        Elog.d(TAG, "get persist.radio.testsim.cardtype is " + this.mTestSimType);
        if (this.mTestSimType.equals("0")) {
            this.mRadioBtnDefault.setChecked(true);
        } else if (this.mTestSimType.equals("1")) {
            this.mRadioBtnCdma.setChecked(true);
        } else if (this.mTestSimType.equals("2")) {
            this.mRadioBtnGsm.setChecked(true);
        } else {
            this.mRadioBtnDefault.setChecked(true);
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle("Hint").setMessage("Please reboot phone to apply new setting!").setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
            default:
                return super.onCreateDialog(id);
        }
    }
}
