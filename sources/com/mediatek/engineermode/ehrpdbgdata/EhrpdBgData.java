package com.mediatek.engineermode.ehrpdbgdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import vendor.mediatek.hardware.netdagent.V1_0.INetdagent;

public class EhrpdBgData extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String BG_DATA_DISABLED_PROPERTY = "persist.vendor.radio.bgdata.disabled";
    private static final String TAG = "EhrpdBgData";
    private static INetdagent agent = null;
    private static boolean mEhrpdBgDataEnable = false;
    private CheckBox mCheckBox;

    public static void setDataDisable(boolean isEnable) {
        try {
            INetdagent service = INetdagent.getService();
            agent = service;
            if (service == null) {
                Elog.e(TAG, "agnet is null");
            } else if (!isEnable) {
                Elog.d(TAG, "clearIotFirewall");
                agent.dispatchNetdagentCmd("netdagent firewall clear_nsiot_firewall");
            } else {
                Elog.d(TAG, "setIotFirewall");
                agent.dispatchNetdagentCmd("netdagent firewall set_nsiot_firewall");
            }
        } catch (RemoteException e) {
            Elog.d(TAG, "RomoteException");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ehrpd_bg_data);
        CheckBox checkBox = (CheckBox) findViewById(R.id.ehrpd_bg_data_botton);
        this.mCheckBox = checkBox;
        checkBox.setOnCheckedChangeListener(this);
        boolean equals = EmUtils.systemPropertyGet(BG_DATA_DISABLED_PROPERTY, "0").equals("1");
        mEhrpdBgDataEnable = equals;
        this.mCheckBox.setChecked(equals);
        setDataDisable(mEhrpdBgDataEnable);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String str;
        if (buttonView.getId() == R.id.ehrpd_bg_data_botton) {
            boolean ehrpdBgDataEnable = this.mCheckBox.isChecked();
            Elog.d(TAG, "ehrpdBgDataEnable is " + ehrpdBgDataEnable);
            if (isChecked) {
                showCheckInfoDlg(getString(R.string.ehrpd_bg_data), getString(R.string.ehrpd_bg_data_disable_hint));
            }
            if (ehrpdBgDataEnable) {
                str = "1";
            } else {
                str = "0";
            }
            EmUtils.systemPropertySet(BG_DATA_DISABLED_PROPERTY, str);
            setDataDisable(ehrpdBgDataEnable);
        }
    }

    private void showCheckInfoDlg(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == -1) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
