package com.mediatek.engineermode.nrconfigure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class NrConfigActivity extends Activity {
    private static final String AT_CMD_5G = "+E5GOPT";
    private static final String AT_CMD_PSCONFIG = "+EPSCONFIG";
    private static final int MSG_QUERY_5G_CMD = 101;
    private static final int MSG_QUERY_AT_CMD_PSCONFIG_CMD = 102;
    private static final int MSG_SET_5G_CMD = 102;
    private static final String TAG = "NrConfig";
    /* access modifiers changed from: private */
    public static int current_selected_phone = 0;
    /* access modifiers changed from: private */
    public static Switch mNsaSw;
    private static int mPhone_count = 0;
    /* access modifiers changed from: private */
    public static Switch mSaSw;
    /* access modifiers changed from: private */
    public static boolean nsa_sw = false;
    /* access modifiers changed from: private */
    public static Spinner phoneSelector;
    /* access modifiers changed from: private */
    public static boolean sa_sw = false;
    private final Handler mCommandHander = new Handler() {
        public void handleMessage(Message msg) {
            Elog.i(NrConfigActivity.TAG, "Receive msg from modem");
            AsyncResult asyncResult = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 101:
                    if (asyncResult == null || asyncResult.exception != null || asyncResult.result == null) {
                        NrConfigActivity.this.showToast("Query NR status failed.");
                        Elog.d(NrConfigActivity.TAG, "Query NR status failed.");
                        return;
                    }
                    String[] result = (String[]) asyncResult.result;
                    Elog.d(NrConfigActivity.TAG, "MSG_QUERY_5G_CMD succeed,result = " + result[0]);
                    int mode = NrConfigActivity.this.parseCurrentNrMode(result[0]);
                    Elog.d(NrConfigActivity.TAG, "mode = " + mode);
                    switch (mode) {
                        case 1:
                            boolean unused = NrConfigActivity.sa_sw = false;
                            boolean unused2 = NrConfigActivity.nsa_sw = false;
                            NrConfigActivity.mSaSw.setChecked(false);
                            NrConfigActivity.mNsaSw.setChecked(false);
                            return;
                        case 3:
                            boolean unused3 = NrConfigActivity.sa_sw = true;
                            boolean unused4 = NrConfigActivity.nsa_sw = false;
                            NrConfigActivity.mSaSw.setChecked(true);
                            NrConfigActivity.mNsaSw.setChecked(false);
                            return;
                        case 5:
                            boolean unused5 = NrConfigActivity.sa_sw = false;
                            boolean unused6 = NrConfigActivity.nsa_sw = true;
                            NrConfigActivity.mSaSw.setChecked(false);
                            NrConfigActivity.mNsaSw.setChecked(true);
                            return;
                        case 7:
                            boolean unused7 = NrConfigActivity.sa_sw = true;
                            boolean unused8 = NrConfigActivity.nsa_sw = true;
                            NrConfigActivity.mSaSw.setChecked(true);
                            NrConfigActivity.mNsaSw.setChecked(true);
                            return;
                        default:
                            NrConfigActivity nrConfigActivity = NrConfigActivity.this;
                            nrConfigActivity.showToast("unknown mode " + mode);
                            return;
                    }
                case 102:
                    NrConfigActivity.this.showToast("configuration is done");
                    NrConfigActivity.this.updateStatus();
                    return;
                default:
                    return;
            }
        }
    };
    private Toast mToast = null;

    /* access modifiers changed from: private */
    public int parseCurrentNrMode(String data) {
        try {
            return Integer.valueOf(data.substring(new String("+E5GOPT:").length()).trim()).intValue();
        } catch (NumberFormatException e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nr_config_activity);
        mSaSw = (Switch) findViewById(R.id.sa_sw);
        mNsaSw = (Switch) findViewById(R.id.nsa_sw);
        phoneSelector = (Spinner) findViewById(R.id.nr_config_phones);
        mPhone_count = TelephonyManager.getDefault().getPhoneCount();
        mSaSw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NrConfigActivity.this.showRebootHint();
                NrConfigActivity.this.setSA(NrConfigActivity.mSaSw.isChecked());
            }
        });
        mNsaSw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NrConfigActivity.this.setNSA(NrConfigActivity.mNsaSw.isChecked());
            }
        });
        if (mPhone_count == 0) {
            Elog.e(TAG, "no phone");
            mSaSw.setClickable(false);
            mNsaSw.setClickable(false);
            return;
        }
        ArrayList<String> phones = new ArrayList<>();
        for (int i = 0; i < mPhone_count; i++) {
            phones.add(new String("Slot " + i));
        }
        phoneSelector.setAdapter(new ArrayAdapter<>(this, 17367049, phones));
        phoneSelector.setSelection(0);
        current_selected_phone = 0;
        phoneSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long id) {
                String obj = NrConfigActivity.phoneSelector.getSelectedItem().toString();
                int unused = NrConfigActivity.current_selected_phone = position;
                NrConfigActivity.this.updateStatus();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (mPhone_count != 0) {
            updateStatus();
        }
    }

    /* access modifiers changed from: private */
    public void updateStatus() {
        sendAtCommand("AT+E5GOPT?", "+E5GOPT:", 101);
    }

    /* access modifiers changed from: private */
    public void setSA(boolean sw) {
        Elog.i(TAG, "Set SA : " + sw);
        sa_sw = sw;
        set5G();
    }

    /* access modifiers changed from: private */
    public void setNSA(boolean sw) {
        Elog.i(TAG, "Set NSA : " + sw);
        nsa_sw = sw;
        set5G();
    }

    private void set5G() {
        boolean z = sa_sw;
        if (z && nsa_sw) {
            sendAtCommand("AT+E5GOPT=7", "", 102);
            showToast("enabling SA and NSA");
        } else if (z) {
            sendAtCommand("AT+E5GOPT=3", "", 102);
            showToast("enabling SA and disabling NSA");
        } else if (nsa_sw) {
            sendAtCommand("AT+E5GOPT=5", "", 102);
            showToast("disabling SA and enabling NSA");
        } else {
            sendAtCommand("AT+E5GOPT=1", "", 102);
            showToast("disabling SA and disabling NSA");
        }
    }

    private void sendAtCommand(String cmd, String resp, int msg) {
        sendCommand(new String[]{cmd, resp}, msg);
    }

    private void sendCommand(String[] command, int msg) {
        Elog.i(TAG, "sendCommand " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(current_selected_phone, command, this.mCommandHander.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    /* access modifiers changed from: private */
    public void showRebootHint() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Please reboot the phone");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
