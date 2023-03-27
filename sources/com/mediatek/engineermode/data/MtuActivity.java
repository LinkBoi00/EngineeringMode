package com.mediatek.engineermode.data;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class MtuActivity extends Activity {
    private static final String PROP_KEY_MTU = "persist.vendor.radio.mobile.mtu";
    private static final String PROP_KEY_MTU_SYSENV = "persist.vendor.radio.mobile.mtu.sysenv";
    private static final String TAG = "MtuActivity";
    private Context mContext;
    /* access modifiers changed from: private */
    public CheckBox mKeepMtuAfterFactoryResetCb;
    /* access modifiers changed from: private */
    public EditText mMtuTextView;
    private Button mOkCmd;
    View.OnClickListener mSaveMtu = new View.OnClickListener() {
        public void onClick(View v) {
            boolean bKeepMtu = MtuActivity.this.mKeepMtuAfterFactoryResetCb.isChecked();
            String strMtu = MtuActivity.this.mMtuTextView.getText().toString();
            if (strMtu.length() > 91) {
                Toast.makeText(MtuActivity.this.getApplicationContext(), "Error : Max input allowed91", 0).show();
                Elog.e(MtuActivity.TAG, "set persist.vendor.radio.mobile.mtu failed, exceeded the maximum : " + strMtu);
                return;
            }
            Context applicationContext = MtuActivity.this.getApplicationContext();
            Toast.makeText(applicationContext, "Setting MTU: " + strMtu, 0).show();
            try {
                EmUtils.systemPropertySet(MtuActivity.PROP_KEY_MTU, strMtu);
                EmUtils.getEmHidlService().setEmConfigure(MtuActivity.PROP_KEY_MTU_SYSENV, bKeepMtu ? strMtu : "0");
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e(MtuActivity.TAG, "set property mtu failed ...");
            }
            Elog.d(MtuActivity.TAG, "set persist.vendor.radio.mobile.mtu : " + strMtu);
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mContext = getBaseContext();
        setContentView(R.layout.data_mtu);
        Button button = (Button) findViewById(R.id.ok_btn);
        this.mOkCmd = button;
        button.setOnClickListener(this.mSaveMtu);
        this.mMtuTextView = (EditText) findViewById(R.id.et_mds_mtu);
        this.mKeepMtuAfterFactoryResetCb = (CheckBox) findViewById(R.id.cb_mds_keep_mtu_after_factory_reset);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        String strMtu = SystemProperties.get(PROP_KEY_MTU);
        Elog.d(TAG, "get persist.vendor.radio.mobile.mtu : " + strMtu);
        Toast.makeText(getApplicationContext(), "Enter Number 1280~1500.", 0).show();
        this.mKeepMtuAfterFactoryResetCb.setChecked(false);
        this.mMtuTextView.setText(strMtu.length() > 0 ? strMtu : "1460");
    }
}
