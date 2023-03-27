package com.mediatek.engineermode.wifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class WifiTestSetting extends ListActivity {
    private static final int DIALOG_WIFI_CTIA = 1;
    private static final int DLG_ENABLE_WIFI = 2;
    private static final int DLG_ENABLE_WIFI_ERROR = 3;
    private static final String TAG = "WifiTestSetting";
    private ArrayList<String> mItemList;
    /* access modifiers changed from: private */
    public WifiManager mWiFiMgr;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (EmUtils.ifAirplaneModeEnabled()) {
            Toast.makeText(this, getString(R.string.wifi_ctia_turn_off_airplane_mode_warning), 1).show();
            finish();
            return;
        }
        this.mWiFiMgr = (WifiManager) getSystemService("wifi");
        showDialog(2);
        new Thread(new Runnable() {
            public void run() {
                final boolean result = EMWifi.enableWifi(WifiTestSetting.this.mWiFiMgr);
                WifiTestSetting.this.runOnUiThread(new Runnable() {
                    public void run() {
                        WifiTestSetting.this.removeDialog(2);
                        if (!result) {
                            WifiTestSetting.this.showDialog(3);
                        }
                    }
                });
            }
        }).start();
        setContentView(R.layout.wifi_test_setting);
        ArrayList<String> arrayList = new ArrayList<>();
        this.mItemList = arrayList;
        arrayList.add(getString(R.string.wifi_ctia_test));
        setListAdapter(new ArrayAdapter<>(this, 17367043, this.mItemList));
    }

    /* access modifiers changed from: protected */
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (this.mItemList.get(position).equals(getString(R.string.wifi_ctia_test))) {
            removeDialog(1);
            showDialog(1);
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new MtkCTIATestDialog(this);
            case 2:
                ProgressDialog dlgInitWifi = new ProgressDialog(this);
                dlgInitWifi.setCancelable(false);
                dlgInitWifi.setMessage(getString(R.string.wifi_ctia_turn_on_wifi));
                dlgInitWifi.setIndeterminate(true);
                return dlgInitWifi;
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.em_error).setCancelable(false).setMessage(getString(R.string.wifi_dialog_fail_message)).setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WifiTestSetting.this.finish();
                    }
                }).create();
            default:
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        removeDialog(1);
        super.onPause();
    }
}
