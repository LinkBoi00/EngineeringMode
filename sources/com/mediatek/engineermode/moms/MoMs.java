package com.mediatek.engineermode.moms;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class MoMs extends Activity {
    private static final String DISABLE_MOMS = "1";
    private static final String REENABLE_MOMS = "0";
    private static final String TAG = "MOMS";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moms);
        ((Button) findViewById(R.id.disable_moms_for_cts)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(MoMs.TAG, "mDisableMoMs is clicked, call setprop persist.sys.mtk.disable.moms 1");
                try {
                    EmUtils.getEmHidlService().setMoms("1");
                    if (Settings.Global.getInt(MoMs.this.getContentResolver(), "show_first_crash_dialog", 0) == 1) {
                        Elog.d(MoMs.TAG, "mDisableMoMs is clicked, set SHOW_FIRST_CRASH_DIALOG 0");
                        Settings.Global.putInt(MoMs.this.getContentResolver(), "show_first_crash_dialog", 0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) findViewById(R.id.reenable_moms)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(MoMs.TAG, "mReenableMoMs is clicked, call setprop persist.sys.mtk.disable.moms 0");
                try {
                    EmUtils.getEmHidlService().setMoms("0");
                    if (Settings.Global.getInt(MoMs.this.getContentResolver(), "show_first_crash_dialog", 0) == 0) {
                        Elog.d(MoMs.TAG, "mReenableMoMs is clicked, set SHOW_FIRST_CRASH_DIALOG 1");
                        Settings.Global.putInt(MoMs.this.getContentResolver(), "show_first_crash_dialog", 1);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
