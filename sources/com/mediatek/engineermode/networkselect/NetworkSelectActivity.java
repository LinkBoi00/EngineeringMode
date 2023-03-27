package com.mediatek.engineermode.networkselect;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;

public class NetworkSelectActivity extends Activity {
    private static final int CDMA_ONLY = 5;
    private static final int EVENT_QUERY_NETWORKMODE_DONE = 101;
    private static final int EVENT_SET_NETWORKMODE_DONE = 102;
    private static final int GSM_ONLY = 1;
    private static final int GSM_TDSCDMA_AUTO = 16;
    private static final int GSM_WCDMA_AUTO = 3;
    private static final int INDEX_CDMA_ONLY = 7;
    private static final int INDEX_EM_UI_NOT_SUPPORT_TYPE = 10;
    private static final int INDEX_GSM_ONLY = 1;
    private static final int INDEX_GSM_TDSCDMA_AUTO = 5;
    private static final int INDEX_GSM_WCDMA_AUTO = 4;
    private static final int INDEX_LTE_ONLY = 6;
    private static final int INDEX_LTE_TDSCDMA_GSM_WCDMA = 8;
    private static final int INDEX_LTE_WCDMA = 9;
    private static final int INDEX_TDSCDMA_ONLY = 3;
    private static final int INDEX_WCDMA_ONLY = 2;
    private static final int INDEX_WCDMA_PREFERRED = 0;
    private static final int LTE_CDMA_EVDO_GSM_WCDMA = 10;
    private static final int LTE_GSM_WCDMA = 9;
    private static final int LTE_ONLY = 11;
    private static final int LTE_TDSCDMA_GSM = 17;
    private static final int LTE_TDSCDMA_GSM_WCDMA = 20;
    private static final int LTE_WCDMA = 12;
    private static final String TAG = "NetworkMode";
    private static final int TDSCDMA_ONLY = 13;
    private static final int WCDMA_ONLY = 2;
    private static final int WCDMA_PREFERRED = 0;
    /* access modifiers changed from: private */
    public int mCurrentSelected = 0;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String info;
            switch (msg.what) {
                case 101:
                    if (msg.arg1 >= 0) {
                        Elog.d(NetworkSelectActivity.TAG, "Get Preferred Type = " + msg.arg1);
                        NetworkSelectActivity.this.updateNetworkType(msg.arg1);
                        return;
                    }
                    Elog.d(NetworkSelectActivity.TAG, "query_preferred_failed");
                    EmUtils.showToast("query_preferred_failed");
                    return;
                case 102:
                    if (msg.arg1 == 0) {
                        info = "set the network succeed";
                    } else {
                        info = "set the network failed";
                    }
                    EmUtils.showToast(info);
                    Elog.d(NetworkSelectActivity.TAG, info);
                    return;
                default:
                    return;
            }
        }
    };
    private int mModemType;
    /* access modifiers changed from: private */
    public int[] mNetworkTypeValues = {0, 1, 2, 13, 3, 16, 11, 5, 20, 12};
    private AdapterView.OnItemSelectedListener mPreferredNetworkListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int pos, long id) {
            Elog.d(NetworkSelectActivity.TAG, "onItemSelected " + pos + " mCurrentSelected: " + NetworkSelectActivity.this.mCurrentSelected);
            if (NetworkSelectActivity.this.mCurrentSelected != pos && pos != 10) {
                int unused = NetworkSelectActivity.this.mCurrentSelected = pos;
                Message obtainMessage = NetworkSelectActivity.this.mHandler.obtainMessage(102);
                final int selectNetworkMode = NetworkSelectActivity.this.mNetworkTypeValues[pos];
                Elog.d(NetworkSelectActivity.TAG, "selectNetworkMode " + selectNetworkMode);
                ContentResolver contentResolver = NetworkSelectActivity.this.getContentResolver();
                Settings.Global.putInt(contentResolver, "preferred_network_mode" + NetworkSelectActivity.this.mSubId, selectNetworkMode);
                new Thread(new Runnable() {
                    public void run() {
                        boolean result = NetworkSelectActivity.this.mTelephonyManager.setPreferredNetworkType(NetworkSelectActivity.this.mSubId, selectNetworkMode);
                        Message message = new Message();
                        message.what = 102;
                        message.arg1 = result ? 0 : -1;
                        NetworkSelectActivity.this.mHandler.sendMessage(message);
                    }
                }).start();
            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };
    private Spinner mPreferredNetworkSpinner = null;
    private SimCardInfo mSimCard;
    /* access modifiers changed from: private */
    public int mSubId = 1;
    /* access modifiers changed from: private */
    public TelephonyManager mTelephonyManager = null;

    public class SimCardInfo {
        private boolean isCapabilitySim = ModemCategory.isCapabilitySim(0);
        private boolean isLteCapabilityViceSim = ModemCategory.checkViceSimCapability(this.mSimType, 4096);
        private boolean isWCapabilityViceSim = ModemCategory.CheckViceSimWCapability(this.mSimType);
        private int mSimType = 0;

        public boolean isLteCapabilityViceSim() {
            return this.isLteCapabilityViceSim;
        }

        private void setLteCapabilityViceSim(boolean isLteCapabilityViceSim2) {
            this.isLteCapabilityViceSim = isLteCapabilityViceSim2;
        }

        public SimCardInfo(int mSimType2) {
            setSimType(mSimType2);
        }

        public boolean isWCapabilityViceSim() {
            return this.isWCapabilityViceSim;
        }

        private void setWCapabilityViceSim(boolean isWCapabilityViceSim2) {
            this.isWCapabilityViceSim = isWCapabilityViceSim2;
        }

        public int getSimType() {
            return this.mSimType;
        }

        public void setSimType(int mSimType2) {
            this.mSimType = mSimType2;
            setCapabilitySim(ModemCategory.isCapabilitySim(mSimType2));
            setWCapabilityViceSim(ModemCategory.CheckViceSimWCapability(this.mSimType));
            setLteCapabilityViceSim(ModemCategory.checkViceSimCapability(mSimType2, 4096));
        }

        public boolean isCapabilitySim() {
            return this.isCapabilitySim;
        }

        private void setCapabilitySim(boolean isCapabilitySim2) {
            this.isCapabilitySim = isCapabilitySim2;
        }
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Elog.d(NetworkSelectActivity.TAG, "isAvailable: " + position + " is " + NetworkSelectActivity.this.isAvailable(position));
            if (NetworkSelectActivity.this.isAvailable(position) && position != 10) {
                return super.getDropDownView(position, (View) null, parent);
            }
            TextView tv = new TextView(getContext());
            tv.setVisibility(8);
            tv.setHeight(0);
            return tv;
        }
    }

    /* access modifiers changed from: private */
    public boolean isAvailable(int index) {
        if (this.mSimCard.isCapabilitySim()) {
            int i = this.mModemType;
            if (i == 2 && (index == 0 || index == 2 || index == 4)) {
                return false;
            }
            if (i == 1 && (index == 3 || index == 5)) {
                return false;
            }
            return (ModemCategory.isLteSupport() || !(index == 6 || index == 8 || index == 9)) && index != 7;
        }
        if (this.mSimCard.isLteCapabilityViceSim()) {
            if (index == 6 || index == 8 || index == 9) {
                return true;
            }
        } else if (index == 6 || index == 8 || index == 9) {
            return false;
        }
        return this.mSimCard.isWCapabilityViceSim() ? (index == 3 || index == 5 || index == 7) ? false : true : index == 1;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networkmode_switching);
        this.mPreferredNetworkSpinner = (Spinner) findViewById(R.id.networkModeSwitching);
        if (!ModemCategory.isCdma()) {
            findViewById(R.id.network_mode_set_hint).setVisibility(8);
        }
        findViewById(R.id.disable_eHRPD).setVisibility(8);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        String[] labels;
        super.onResume();
        int mSimType = getIntent().getIntExtra("mSimType", ModemCategory.getCapabilitySim());
        Elog.i(TAG, "mSimType " + mSimType);
        SimCardInfo simCardInfo = new SimCardInfo(mSimType);
        this.mSimCard = simCardInfo;
        if (!ModemCategory.isSimReady(simCardInfo.getSimType())) {
            EmUtils.showToast("The card is not ready,please check it");
            Elog.w(TAG, "The card is not ready,please check it");
            this.mPreferredNetworkSpinner.setEnabled(false);
            return;
        }
        int subIdBySlot = ModemCategory.getSubIdBySlot(this.mSimCard.getSimType());
        this.mSubId = subIdBySlot;
        if (subIdBySlot < 0) {
            Elog.w(TAG, "Invalid sub id");
            return;
        }
        this.mPreferredNetworkSpinner.setEnabled(true);
        int modemType = ModemCategory.getModemType();
        this.mModemType = modemType;
        if (modemType != 3) {
            labels = getResources().getStringArray(R.array.network_mode_labels);
        } else {
            Elog.w(TAG, "Isn't TD/WCDMA modem: " + this.mModemType);
            labels = new String[]{"GSM only"};
            this.mNetworkTypeValues = new int[]{1};
        }
        CustomAdapter adapter = new CustomAdapter(this, 17367048, labels);
        adapter.setDropDownViewResource(17367049);
        this.mPreferredNetworkSpinner.setAdapter(adapter);
        int i = 0;
        while (true) {
            if (i >= this.mNetworkTypeValues.length) {
                break;
            } else if (isAvailable(i)) {
                this.mCurrentSelected = i;
                this.mPreferredNetworkSpinner.setSelection(i, true);
                break;
            } else {
                i++;
            }
        }
        this.mPreferredNetworkSpinner.setOnItemSelectedListener(this.mPreferredNetworkListener);
        new Thread(new Runnable() {
            public void run() {
                if (NetworkSelectActivity.this.mTelephonyManager != null) {
                    int result = NetworkSelectActivity.this.mTelephonyManager.getPreferredNetworkType(NetworkSelectActivity.this.mSubId);
                    Message message = new Message();
                    message.what = 101;
                    message.arg1 = result;
                    NetworkSelectActivity.this.mHandler.sendMessage(message);
                    return;
                }
                Elog.e(NetworkSelectActivity.TAG, "mTelephonyManager = null");
            }
        }).start();
    }

    private int findSpinnerIndexByType(int type) {
        if ((type == 0 || type == 3) && this.mModemType == 2) {
            type = 16;
        }
        if (type == 2 && this.mModemType == 2) {
            type = 13;
        }
        if (type == 9 || type == 17) {
            type = 20;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.mNetworkTypeValues;
            if (i >= iArr.length) {
                return -1;
            }
            if (iArr[i] == type && isAvailable(i)) {
                return i;
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateNetworkType(int type) {
        int index = findSpinnerIndexByType(type);
        Elog.d(TAG, "Index = " + index);
        if (index < 0 || index >= this.mPreferredNetworkSpinner.getCount()) {
            Elog.d(TAG, "Netwok select not support the type: " + type);
            EmUtils.showToast("Netwok select not support the type: " + type);
            this.mCurrentSelected = 10;
            this.mPreferredNetworkSpinner.setSelection(10, true);
            return;
        }
        this.mCurrentSelected = index;
        this.mPreferredNetworkSpinner.setSelection(index, true);
        Elog.d(TAG, "Netwok select type is: " + type);
        EmUtils.showToast("Netwok select type is: " + type);
    }
}
