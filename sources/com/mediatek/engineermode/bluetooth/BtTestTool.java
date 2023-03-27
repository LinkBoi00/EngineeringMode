package com.mediatek.engineermode.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class BtTestTool extends Activity implements View.OnClickListener {
    public static final ParcelUuid BASE_UUID = ParcelUuid.fromString("12345678-0000-1000-8000-00805F9B34FB");
    static final String PREF_BLE_ALWAYS_SCANNING = "pref_ble_always_scanning";
    static final String PREF_BT_ALWAYS_VISIBLE = "pref_bt_always_visible";
    private static final String TAG = "BtTestTool";
    private static BluetoothLeScanner mBluetoothLeScanner = null;
    private static final ScanCallback mScanCallback = new ScanCallback() {
        public void onScanResult(int callbackType, ScanResult result) {
            Elog.i(BtTestTool.TAG, "onScanResult callbackType:" + callbackType);
        }

        public void onScanFailed(int errorCode) {
            Elog.i(BtTestTool.TAG, "onScanFailed errorCode:" + errorCode);
        }
    };
    private Button mBtnBluetoothInfo;
    private CheckBox mCbAlwaysVisible;
    private CheckBox mCbBleAlwaysScan;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(BtTestTool.this.getApplicationContext()).edit();
                    editor.putInt(BtTestTool.PREF_BLE_ALWAYS_SCANNING, -1);
                    editor.commit();
                    return;
                case 1:
                    BtTestTool.this.startBleScan(2);
                    return;
                case 2:
                    BtTestTool.this.stopBleScan();
                    return;
                default:
                    return;
            }
        }
    };
    private int mInterval = 0;
    private RadioGroup mRgBleScanMode;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Elog.v(TAG, "onCreate: " + mBluetoothLeScanner);
        setContentView(R.layout.bt_test_tool);
        CheckBox checkBox = (CheckBox) findViewById(R.id.always_visible_cb);
        this.mCbAlwaysVisible = checkBox;
        checkBox.setChecked(getAlwaysVisible(this));
        this.mCbAlwaysVisible.setOnClickListener(this);
        this.mCbBleAlwaysScan = (CheckBox) findViewById(R.id.ble_always_scan_cb);
        int type = getAlwaysScanning(this);
        boolean checked = false;
        if (type >= 0) {
            checked = true;
        }
        this.mCbBleAlwaysScan.setChecked(checked);
        this.mCbBleAlwaysScan.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.ble_scan_mode_rg);
        this.mRgBleScanMode = radioGroup;
        int radio_button_Id = R.id.ble_scan_mode_high;
        switch (type) {
            case 0:
                radio_button_Id = R.id.ble_scan_mode_low;
                break;
            case 1:
                radio_button_Id = R.id.ble_scan_mode_medium;
                break;
        }
        radioGroup.check(radio_button_Id);
        Button button = (Button) findViewById(R.id.bluetooth_info_bn);
        this.mBtnBluetoothInfo = button;
        button.setOnClickListener(this);
    }

    public void onClick(View v) {
        Elog.v(TAG, "onClick: " + v);
        if (v.equals(this.mCbAlwaysVisible)) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            boolean on = this.mCbAlwaysVisible.isChecked();
            editor.putBoolean(PREF_BT_ALWAYS_VISIBLE, on);
            editor.commit();
            setAlwaysVisible(on);
            BtWatchService.enableService(this, on);
        } else if (v.equals(this.mCbBleAlwaysScan)) {
            SharedPreferences.Editor editor2 = PreferenceManager.getDefaultSharedPreferences(this).edit();
            boolean on2 = this.mCbBleAlwaysScan.isChecked();
            int scan_mode = 2;
            switch (this.mRgBleScanMode.getCheckedRadioButtonId()) {
                case R.id.ble_scan_mode_low:
                    scan_mode = 0;
                    break;
                case R.id.ble_scan_mode_medium:
                    scan_mode = 1;
                    break;
            }
            if (!on2) {
                scan_mode = -1;
            }
            editor2.putInt(PREF_BLE_ALWAYS_SCANNING, scan_mode);
            editor2.commit();
            BleScanService.enableService(this, on2);
            if (on2) {
                startBleScan(scan_mode);
            } else {
                stopBleScan();
            }
        } else if (v.equals(this.mBtnBluetoothInfo)) {
            Intent intent = new Intent();
            intent.setClassName("com.android.bluetooth", "com.android.bluetooth.util.BtInfoActivity");
            startActivity(intent);
        }
    }

    /* access modifiers changed from: private */
    public void stopBleScan() {
        Elog.v(TAG, "stopBleScan: " + mBluetoothLeScanner);
        StringBuilder sb = new StringBuilder();
        sb.append("stopBleScan - mScanCallback: ");
        ScanCallback scanCallback = mScanCallback;
        sb.append(scanCallback);
        Elog.v(TAG, sb.toString());
        if (mBluetoothLeScanner == null) {
            mBluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        }
        mBluetoothLeScanner.stopScan(scanCallback);
        Elog.v(TAG, "stop ble scan - " + mBluetoothLeScanner);
    }

    /* access modifiers changed from: private */
    public void startBleScan(int scan_mode) {
        Elog.v(TAG, "startBleScan[" + scan_mode + "]: " + mBluetoothLeScanner);
        StringBuilder sb = new StringBuilder();
        sb.append("startBleScan - mScanCallback: ");
        ScanCallback scanCallback = mScanCallback;
        sb.append(scanCallback);
        Elog.v(TAG, sb.toString());
        if (mBluetoothLeScanner == null) {
            mBluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        }
        List<ScanFilter> mFilters = new ArrayList<>();
        ScanFilter.Builder filterBuilder = new ScanFilter.Builder();
        filterBuilder.setServiceUuid(BASE_UUID);
        mFilters.add(filterBuilder.build());
        ScanSettings.Builder settingsBuilder = new ScanSettings.Builder();
        settingsBuilder.setScanMode(scan_mode);
        mBluetoothLeScanner.startScan(mFilters, settingsBuilder.build(), scanCallback);
        Elog.v(TAG, "start ble scan - " + mBluetoothLeScanner);
    }

    public static void setAlwaysVisible(boolean on) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            Elog.i(TAG, "setAlwaysVisable on:" + on);
            if (on) {
                if (adapter.getScanMode() != 23) {
                    adapter.setScanMode(23);
                }
            } else if (adapter.getScanMode() == 23) {
                adapter.setScanMode(21);
            }
        }
    }

    public static boolean getAlwaysVisible(Context context) {
        boolean on = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_BT_ALWAYS_VISIBLE, false);
        Elog.i(TAG, "getAlwaysVisible:" + on);
        return on;
    }

    public static void setAlwaysScanning(int scan_mode) {
        Elog.i(TAG, "setAlwaysScanning scan_mode:" + scan_mode);
    }

    public static int getAlwaysScanning(Context context) {
        int scan_mode = PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_BLE_ALWAYS_SCANNING, -1);
        Elog.i(TAG, "getAlwaysScanning:" + scan_mode);
        return scan_mode;
    }
}
