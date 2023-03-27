package com.mediatek.engineermode.dyndebugctrl;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.view.View;
import android.widget.CheckBox;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class DynDebugCtrl extends Activity {
    private static final String PROPERY_NAME = "persist.vendor.em.dy.debug";
    private static final String VALUE_DISABLE = "0";
    private static final String VALUE_ENABLE = "1";
    /* access modifiers changed from: private */
    public CheckBox mCbEnable;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dyn_debug_ctrl);
        CheckBox checkBox = (CheckBox) findViewById(R.id.dyn_debug_ctrl_cb);
        this.mCbEnable = checkBox;
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    EmUtils.getEmHidlService().setEmConfigure(DynDebugCtrl.PROPERY_NAME, DynDebugCtrl.this.mCbEnable.isChecked() ? "1" : "0");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mCbEnable.setChecked("1".equals(SystemProperties.get(PROPERY_NAME, "0")));
    }
}
