package com.mediatek.engineermode.ecc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class EmergencyNumbersActivity extends Activity {
    private static final String ECC_PROP_KEY = "persist.vendor.em.hidl.testecc";
    private static final String TAG = "EmergencyNumbersActivity";
    private Context mContext;
    /* access modifiers changed from: private */
    public EditText mEccNumTextView;
    private Button mOkCmd;
    View.OnClickListener mSaveEccNums = new View.OnClickListener() {
        public void onClick(View v) {
            String eccNum = EmergencyNumbersActivity.this.mEccNumTextView.getText().toString();
            if (eccNum.length() > 91) {
                Toast.makeText(EmergencyNumbersActivity.this.getApplicationContext(), "Error : Max input allowed91", 0).show();
                Elog.e(EmergencyNumbersActivity.TAG, "set persist.vendor.em.hidl.testecc failed, exceeded the maximum : " + eccNum);
                return;
            }
            Context applicationContext = EmergencyNumbersActivity.this.getApplicationContext();
            Toast.makeText(applicationContext, "setting ecc: " + eccNum, 0).show();
            try {
                EmUtils.getEmHidlService().setEmConfigure(EmergencyNumbersActivity.ECC_PROP_KEY, eccNum);
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e(EmergencyNumbersActivity.TAG, "set property failed ...");
            }
            Elog.d(EmergencyNumbersActivity.TAG, "set persist.vendor.em.hidl.testecc : " + eccNum);
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mContext = getBaseContext();
        setContentView(R.layout.ecc_num);
        Button button = (Button) findViewById(R.id.ok_btn);
        this.mOkCmd = button;
        button.setOnClickListener(this.mSaveEccNums);
        this.mEccNumTextView = (EditText) findViewById(R.id.ecc_number);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        String eccNum = SystemProperties.get(ECC_PROP_KEY);
        Elog.d(TAG, "get persist.vendor.em.hidl.testecc : " + eccNum);
        Toast.makeText(getApplicationContext(), "Enter Numbers separated by ;", 0).show();
        this.mEccNumTextView.setText(eccNum);
    }
}
