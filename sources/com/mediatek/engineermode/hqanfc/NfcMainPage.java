package com.mediatek.engineermode.hqanfc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.ShellExe;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.io.File;
import java.io.IOException;

public class NfcMainPage extends PreferenceActivity {
    private static final int DIALOG_WAIT = 1;
    private static final int DLG_NOT_SUPPORT = 2;
    private static final String KILL_LIB_COMMAND = "kill -9 nfcstackp";
    private static final String QUICK_MODE_FILE = "/sdcard/mtknfcSyncQuickMode";
    private static final String START_LIB_COMMAND = "./system/bin/nfcstackp";
    public static final String TAG = "Nfc";
    private boolean mIsSupport = true;
    private ConnectServerTask mTask;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FeatureSupport.isNfcSupport()) {
            showDialog(2);
            this.mIsSupport = false;
            return;
        }
        addPreferencesFromResource(R.xml.hqa_nfc_main);
        showDialog(1);
        ConnectServerTask connectServerTask = new ConnectServerTask();
        this.mTask = connectServerTask;
        connectServerTask.execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.mIsSupport) {
            Elog.i(TAG, "[NfcMainPage]Nfc main page onDestroy().");
            NfcClient.getInstance().sendCommand(135, (NfcEmReqRsp.NfcEmReq) null);
            NfcClient.getInstance().sendCommand(120, (NfcEmReqRsp.NfcEmReq) null);
            NfcClient.getInstance().closeConnection();
            this.mTask.cancel(true);
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Please Wait...");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                return dialog;
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.nfc_st).setCancelable(false).setMessage(getString(R.string.nfc_st_not_support)).setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NfcMainPage.this.finish();
                    }
                }).create();
            default:
                Elog.d(TAG, "error dialog ID");
                return super.onCreateDialog(id);
        }
    }

    private void closeNFCServiceAtStart() {
        NfcAdapter adp = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (adp == null) {
            Elog.i(TAG, "[NfcMainPage]Nfc adapter is null");
        } else if (!adp.isEnabled()) {
            Elog.i(TAG, "[NfcMainPage]Nfc service is off");
        } else if (adp.disable()) {
            Elog.i(TAG, "[NfcMainPage]Nfc service set off.");
        } else {
            Elog.i(TAG, "[NfcMainPage]Nfc service set off Fail.");
        }
    }

    private void executeXbinFile(final String command, int sleepTime) {
        new Thread() {
            public void run() {
                Elog.d(NfcMainPage.TAG, "[NfcMainPage]nfc command:" + command);
                try {
                    int err = ShellExe.execCommand(command, true);
                    Elog.d(NfcMainPage.TAG, "[NfcMainPage]nfc command:result: " + err);
                } catch (IOException e) {
                    Elog.e(NfcMainPage.TAG, "[NfcMainPage]executeXbinFile IOException: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Elog.e(TAG, "[NfcMainPage]executeXbinFile InterruptedException: " + e.getMessage());
        }
    }

    private void setNfcQuickMode(int mode) {
        File file = new File(QUICK_MODE_FILE);
        boolean result = false;
        try {
            Elog.i(TAG, "[QE]setNfcQuickMode(" + mode);
            if (!file.exists()) {
                if (mode == 1) {
                    result = file.createNewFile();
                }
            } else if (mode == 0) {
                result = file.delete();
            }
            Elog.i(TAG, "[QE]setNfcQuickMode(" + mode + ") result:" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void init() {
        NfcAdapter adp = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (adp == null) {
            Elog.e(TAG, "adp == null");
            return;
        }
        Elog.i(TAG, "[QE]Engineer Mode clear all.");
        setNfcQuickMode(0);
        Elog.i(TAG, "[QE]set file");
        setNfcQuickMode(1);
        Elog.i(TAG, "[QE]NFC Disable.");
        if (adp.isEnabled()) {
            Elog.i(TAG, "[QE] force NFC Disable.");
            adp.disable();
        } else {
            Elog.i(TAG, "[QE]NFC Enable -->Disable.");
            adp.enable();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Elog.i(TAG, "InterruptedException");
            }
            adp.disable();
        }
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e2) {
                Elog.i(TAG, "InterruptedException");
            }
        } while (adp.isEnabled());
    }

    private class ConnectServerTask extends AsyncTask<Void, Void, Boolean> {
        private ConnectServerTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            NfcMainPage.this.init();
            return Boolean.valueOf(NfcClient.getInstance().createConnection(NfcMainPage.this));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            if (result == null || !result.booleanValue()) {
                Toast.makeText(NfcMainPage.this, R.string.hqa_nfc_connect_fail, 0).show();
            } else {
                PreferenceScreen screen = NfcMainPage.this.getPreferenceScreen();
                int count = screen.getPreferenceCount();
                for (int index = 0; index < count; index++) {
                    screen.getPreference(index).setEnabled(true);
                }
                NfcClient.getInstance().sendCommand(100, (NfcEmReqRsp.NfcEmReq) null);
                NfcClient.getInstance().sendCommand(135, (NfcEmReqRsp.NfcEmReq) null);
            }
            NfcMainPage.this.dismissDialog(1);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        Elog.i(TAG, "fragmentName is " + fragmentName);
        return false;
    }
}
