package com.mediatek.engineermode.wificalling;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class EntitlementConfigActivity extends Activity {
    private static final String KEY_ENTITLEMENT_ENABLES = "persist.vendor.entitlement_enabled";
    private static final String KEY_ENTITLEMENT_SERVER = "persist.vendor.entitlement.sesurl";
    private static final String KEY_EPDG_ADDRESS = "persist.vendor.net.wo.epdg_fqdn";
    private static final String TAG = "EM/WifiCallingConfigActivity";
    /* access modifiers changed from: private */
    public boolean entitlementEnable;
    /* access modifiers changed from: private */
    public String entitlementServer;
    /* access modifiers changed from: private */
    public EditText entitlementServerEt;
    /* access modifiers changed from: private */
    public RadioGroup entitlementStatus;
    private Button getBtn;
    private Button setBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wificalling_config);
        initView();
        new DoSystemPropTask().execute(new String[]{"get"});
    }

    private void initView() {
        this.entitlementStatus = (RadioGroup) findViewById(R.id.entitlement_status);
        this.entitlementStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.entitlement_disable:
                        boolean unused = EntitlementConfigActivity.this.entitlementEnable = false;
                        return;
                    case R.id.entitlement_enable:
                        boolean unused2 = EntitlementConfigActivity.this.entitlementEnable = true;
                        return;
                    default:
                        return;
                }
            }
        });
        this.entitlementServerEt = (EditText) findViewById(R.id.entitlement_server);
        Button button = (Button) findViewById(R.id.wificalling_set);
        this.setBtn = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EntitlementConfigActivity entitlementConfigActivity = EntitlementConfigActivity.this;
                String unused = entitlementConfigActivity.entitlementServer = entitlementConfigActivity.entitlementServerEt.getText().toString();
                new DoSystemPropTask().execute(new String[]{"set"});
            }
        });
        Button button2 = (Button) findViewById(R.id.wificalling_get);
        this.getBtn = button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DoSystemPropTask().execute(new String[]{"get"});
            }
        });
    }

    protected class DoSystemPropTask extends AsyncTask<String, String, String> {
        protected DoSystemPropTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            String str = "1";
            if (params[0].equals("set")) {
                Elog.d(EntitlementConfigActivity.TAG, "[Set]entitlementServer: " + EntitlementConfigActivity.this.entitlementServer + ",entitlementEnable:" + EntitlementConfigActivity.this.entitlementEnable);
                boolean set1 = EmUtils.systemPropertySet(EntitlementConfigActivity.KEY_ENTITLEMENT_SERVER, EntitlementConfigActivity.this.entitlementServer);
                if (!EntitlementConfigActivity.this.entitlementEnable) {
                    str = "0";
                }
                boolean set2 = EmUtils.systemPropertySet(EntitlementConfigActivity.KEY_ENTITLEMENT_ENABLES, str);
                if (!set1 || !set2) {
                    return "Set failed!";
                }
                return "Set succeed!";
            } else if (!params[0].equals("get")) {
                return "UnKnown";
            } else {
                String unused = EntitlementConfigActivity.this.entitlementServer = EmUtils.systemPropertyGet(EntitlementConfigActivity.KEY_ENTITLEMENT_SERVER, "");
                boolean unused2 = EntitlementConfigActivity.this.entitlementEnable = EmUtils.systemPropertyGet(EntitlementConfigActivity.KEY_ENTITLEMENT_ENABLES, "0").equals(str);
                return "get";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            if (result.equals("get")) {
                EntitlementConfigActivity.this.entitlementStatus.check(EntitlementConfigActivity.this.entitlementEnable ? R.id.entitlement_enable : R.id.entitlement_disable);
                EntitlementConfigActivity.this.entitlementServerEt.setText(EntitlementConfigActivity.this.entitlementServer);
                Elog.d(EntitlementConfigActivity.TAG, "[Get]EntitlementServer: " + EntitlementConfigActivity.this.entitlementServer + ",entitlementEnable:" + EntitlementConfigActivity.this.entitlementEnable);
                return;
            }
            Toast.makeText(EntitlementConfigActivity.this.getApplicationContext(), result, 1).show();
        }
    }
}
