package com.mediatek.engineermode.bluetooth;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class BtList extends ListActivity {
    private static final int DLG_CHECK_BT_CONFIG = 2;
    static final int DUAL_ANT = 2;
    static final String KEY_ANT_NUM = "ant_num";
    static final int SINGLE_ANT = 1;
    private static final String TAG = "BTList";
    /* access modifiers changed from: private */
    public int mAntNum = 1;
    /* access modifiers changed from: private */
    public BleSupportState mBleSupport = BleSupportState.BLE_NONE;
    private BluetoothAdapter mBtAdapter;
    /* access modifiers changed from: private */
    public BtTest mBtTest = null;
    /* access modifiers changed from: private */
    public boolean mComboSupport = false;
    /* access modifiers changed from: private */
    public ArrayList<String> mModuleList = null;

    enum BleSupportState {
        BLE_NONE,
        BLE_NORMAL,
        BLE_ENHANCED
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FeatureSupport.isEmBTSupport()) {
            Toast.makeText(this, R.string.bt_not_support, 1).show();
            finish();
            return;
        }
        setContentView(R.layout.bt_list);
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        BtTest btTest = new BtTest();
        this.mBtTest = btTest;
        if (!btTest.checkInitState(this.mBtAdapter, this)) {
            finish();
            return;
        }
        showDialog(2);
        new FunctionTask().execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (id != 2) {
            return null;
        }
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.bt_init_dev));
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    /* access modifiers changed from: protected */
    public void onListItemClick(ListView l, View v, int position, long id) {
        String moduleTitle = this.mModuleList.get(position);
        if (moduleTitle != null) {
            Intent it = null;
            if (moduleTitle.equals(getString(R.string.bt_tx_only))) {
                it = new Intent(this, TxOnlyTestActivity.class);
            } else if (moduleTitle.equals(getString(R.string.bt_tx_tone))) {
                it = new Intent(this, TxToneTestActivity.class);
            } else if (moduleTitle.equals(getString(R.string.bt_rx))) {
                it = new Intent(this, NoSigRxTestActivity.class);
            } else if (moduleTitle.equals(getString(R.string.bt_tm))) {
                it = new Intent(this, TestModeActivity.class);
            } else if (moduleTitle.equals(getString(R.string.bt_relayer))) {
                it = new Intent(this, BtRelayerModeActivity.class);
            } else if (moduleTitle.equals(getString(R.string.bt_le_test))) {
                it = new Intent(this, BleTestMode.class);
            } else if (moduleTitle.equals(getString(R.string.bt_le_eh_test))) {
                it = new Intent(this, BleEnhancedTestMode.class);
            }
            if (it != null) {
                it.putExtra(KEY_ANT_NUM, this.mAntNum);
                startActivity(it);
            }
        }
    }

    private class FunctionTask extends AsyncTask<Void, Void, Void> {
        private FunctionTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            BtList.this.mBtTest.init();
            if (BtList.this.mBtTest.isBLEEnhancedSupport()) {
                BleSupportState unused = BtList.this.mBleSupport = BleSupportState.BLE_ENHANCED;
            } else if (BtList.this.mBtTest.isBLESupport()) {
                BleSupportState unused2 = BtList.this.mBleSupport = BleSupportState.BLE_NORMAL;
            }
            BtList btList = BtList.this;
            boolean unused3 = btList.mComboSupport = btList.mBtTest.isComboSupport();
            BtList btList2 = BtList.this;
            int unused4 = btList2.mAntNum = btList2.mBtTest.getAntNum();
            BtList.this.mBtTest.unInit();
            Elog.i(BtList.TAG, "BLE supported: " + BtList.this.mBleSupport + " ant number:" + BtList.this.mAntNum);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            ArrayList unused = BtList.this.mModuleList = new ArrayList();
            BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_tx_only));
            BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_tx_tone));
            BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_rx));
            BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_tm));
            if (BtList.this.mBleSupport == BleSupportState.BLE_ENHANCED) {
                BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_le_eh_test));
            } else if (BtList.this.mBleSupport == BleSupportState.BLE_NORMAL) {
                BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_le_test));
            }
            if (BtList.this.mComboSupport) {
                BtList.this.mModuleList.add(BtList.this.getString(R.string.bt_relayer));
            }
            BtList btList = BtList.this;
            BtList.this.setListAdapter(new ArrayAdapter<>(btList, 17367043, btList.mModuleList));
            BtList.this.removeDialog(2);
            super.onPostExecute(result);
        }
    }
}
