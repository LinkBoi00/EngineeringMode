package com.mediatek.engineermode.networkselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;

public class NetworkSelectActivity93 extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final int CARD_TYPE_CDMA_ONLY = 3;
    private static final int CARD_TYPE_GSM_CDMA = 2;
    private static final int CARD_TYPE_GSM_ONLY = 1;
    public static final int CDMA_EVDO = 4;
    public static final int CDMA_EVDO_GSM_UMTS = 21;
    public static final int CDMA_EVDO_GSM_WCDMA = 7;
    public static final int CDMA_ONLY = 5;
    public static final int EVDO_ONLY = 6;
    private static final int EVENT_QUERY_EHRPD_ENABLE_DONE = 103;
    private static final int EVENT_QUERY_NETWORKMODE_DONE = 101;
    private static final int EVENT_SET_EHRPD_ENABLE_DONE = 104;
    private static final int EVENT_SET_NETWORKMODE_DONE = 102;
    public static final String FK_MTK_C2K_CAPABILITY = "persist.vendor.radio.disable_c2k_cap";
    public static final int GSM_ONLY = 1;
    public static final int GSM_TDSCDMA_AUTO = 16;
    public static final int GSM_WCDMA_AUTO = 3;
    private static final int INDEX_CDMA_EVDO = 9;
    private static final int INDEX_CDMA_EVDO_UMTS_GSM = 14;
    private static final int INDEX_CDMA_ONLY = 10;
    private static final int INDEX_EM_UI_NOT_SUPPORT_TYPE = 21;
    private static final int INDEX_EVDO_ONLY = 11;
    private static final int INDEX_GSM_ONLY = 1;
    private static final int INDEX_GSM_TDSCDMA_AUTO = 5;
    private static final int INDEX_GSM_WCDMA_AUTO = 4;
    private static final int INDEX_LTE_CDMA_EVDO = 13;
    private static final int INDEX_LTE_CDMA_EVDO_UMTS_GSM = 12;
    private static final int INDEX_LTE_GSM_UMTS = 7;
    private static final int INDEX_LTE_ONLY = 6;
    private static final int INDEX_LTE_UMTS = 8;
    private static final int INDEX_NR_LTE = 16;
    private static final int INDEX_NR_LTE_CDMA_EVDO = 19;
    private static final int INDEX_NR_LTE_UMTS = 17;
    private static final int INDEX_NR_LTE_UMTS_GSM = 18;
    private static final int INDEX_NR_LTE_UMTS_GSM_CDMA = 20;
    private static final int INDEX_NR_ONLY = 15;
    private static final int INDEX_TDSCDMA_ONLY = 3;
    private static final int INDEX_WCDMA_ONLY = 2;
    private static final int INDEX_WCDMA_PREFERRED = 0;
    public static final int LTE_CDMA_EVDO = 8;
    public static final int LTE_CDMA_EVDO_GSM_UMTS = 22;
    public static final int LTE_CDMA_EVDO_GSM_WCDMA = 10;
    public static final int LTE_GSM_WCDMA = 9;
    public static final int LTE_ONLY = 11;
    public static final int LTE_TDSCDMA = 15;
    public static final int LTE_TDSCDMA_GSM = 17;
    public static final int LTE_TDSCDMA_GSM_WCDMA = 20;
    public static final int LTE_TDSCDMA_WCDMA = 19;
    public static final int LTE_WCDMA = 12;
    public static final int NR_LTE = 24;
    public static final int NR_LTE_CDMA_EVDO = 25;
    public static final int NR_LTE_CDMA_EVDO_GSM_UMTS = 33;
    public static final int NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
    public static final int NR_LTE_GSM_WCDMA = 26;
    public static final int NR_LTE_TDSCDMA = 29;
    public static final int NR_LTE_TDSCDMA_GSM = 30;
    public static final int NR_LTE_UMTS = 31;
    public static final int NR_LTE_UMTS_GSM = 32;
    public static final int NR_LTE_WCDMA = 28;
    public static final int NR_ONLY = 23;
    private static final int REBOOT_DIALOG = 2000;
    public static final String TAG = "NetworkMode93";
    public static final int TDSCDMA_GSM_WCDMA = 18;
    public static final int TDSCDMA_ONLY = 13;
    public static final int TDSCDMA_WCDMA_ONLY = 14;
    public static final int WCDMA_ONLY = 2;
    public static final int WCDMA_PREFERRED = 0;
    /* access modifiers changed from: private */
    public String[] mCardTypeValues = {"gsm_only_card", "gsm_cdma_card", "cdma_only_card"};
    private CheckBox mCbDisableC2kCapabilit = null;
    /* access modifiers changed from: private */
    public int mCurrentSelected = 0;
    /* access modifiers changed from: private */
    public CheckBox mDisableeHRPDCheckBox = null;
    /* access modifiers changed from: private */
    public boolean mEHRPDFirstEnter = true;
    private boolean mFirstEntry = true;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String info;
            switch (msg.what) {
                case 101:
                    if (msg.arg1 >= 0) {
                        Elog.d(NetworkSelectActivity93.TAG, "Get Preferred Type = " + msg.arg1);
                        NetworkSelectActivity93.this.updateNetworkType(msg.arg1);
                        return;
                    }
                    Elog.d(NetworkSelectActivity93.TAG, "query_preferred_failed");
                    EmUtils.showToast("query_preferred_failed");
                    return;
                case 102:
                    if (msg.arg1 == 0) {
                        info = "set the network to : " + NetworkSelectActivity93.this.selectNetworkMode + " succeed";
                    } else {
                        info = "set the network to : " + NetworkSelectActivity93.this.selectNetworkMode + " failed";
                    }
                    EmUtils.showToast(info);
                    Elog.d(NetworkSelectActivity93.TAG, info);
                    return;
                case 103:
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception != null) {
                        EmUtils.showToast((int) R.string.query_eHRPD_state_fail);
                        boolean unused = NetworkSelectActivity93.this.mIsEvdoSupport = false;
                        Elog.d(NetworkSelectActivity93.TAG, NetworkSelectActivity93.this.getResources().getString(R.string.query_eHRPD_state_fail));
                        Elog.d(NetworkSelectActivity93.TAG, "mIsEvdoSupport = " + NetworkSelectActivity93.this.mIsEvdoSupport);
                        NetworkSelectActivity93.this.mDisableeHRPDCheckBox.setVisibility(8);
                        ((CustomAdapter) NetworkSelectActivity93.this.mPreferredNetworkSpinner.getAdapter()).notifyDataSetChanged();
                        return;
                    } else if (ar.result != null && (ar.result instanceof String[])) {
                        String[] data = (String[]) ar.result;
                        if (data.length > 0 && data[0] != null) {
                            Elog.d(NetworkSelectActivity93.TAG, "data[0]:" + data[0]);
                            if (data[0].equals("+EHRPD:0")) {
                                boolean unused2 = NetworkSelectActivity93.this.mEHRPDFirstEnter = true;
                                NetworkSelectActivity93.this.mDisableeHRPDCheckBox.setChecked(true);
                                return;
                            }
                            boolean unused3 = NetworkSelectActivity93.this.mEHRPDFirstEnter = false;
                            NetworkSelectActivity93.this.mDisableeHRPDCheckBox.setChecked(false);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                case 104:
                    if (((AsyncResult) msg.obj).exception != null) {
                        EmUtils.showToast((int) R.string.set_eHRPD_state_fail);
                        Elog.d(NetworkSelectActivity93.TAG, NetworkSelectActivity93.this.getResources().getString(R.string.set_eHRPD_state_fail));
                        return;
                    }
                    EmUtils.showToast((int) R.string.set_eHRPD_state_succeed);
                    Elog.d(NetworkSelectActivity93.TAG, NetworkSelectActivity93.this.getResources().getString(R.string.set_eHRPD_state_succeed));
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsEvdoSupport = false;
    private int mModemType;
    /* access modifiers changed from: private */
    public String[] mNetworkTypeLabel = {"GSM/WCDMA (WCDMA preferred)", "GSM only", "WCDMA only", "TD-SCDMA only", "GSM/WCDMA (auto)", "GSM/TD-SCDMA (auto)", "LTE only", "LTE/UMTS/GSM", "LTE/UMTS", "CDMA/EVDO", "CDMA only", "EVDO only", "LTE/CDMA/EVDO/UMTS/GSM", "LTE/CDMA/EVDO", "CDMA/EVDO/UMTS/GSM", "NR only", "NR/LTE", "NR/LTE/UMTS", "NR/LTE/UMTS/GSM", "NR/LTE/CDMA/EVDO", "NR/LTE/UMTS/GSM/C2K", "EM UI not support this type"};
    /* access modifiers changed from: private */
    public int[] mNetworkTypeValues = {0, 1, 2, 13, 3, 16, 11, 20, 19, 4, 5, 6, 22, 8, 21, 23, 24, 31, 32, 25, 33};
    private AdapterView.OnItemSelectedListener mPreferredNetworkListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int pos, long id) {
            Elog.d(NetworkSelectActivity93.TAG, "onItemSelected = " + pos + ",mCurrentSelected = " + NetworkSelectActivity93.this.mCurrentSelected + " " + NetworkSelectActivity93.this.mNetworkTypeLabel[pos] + ",mNetworkTypeValues length " + NetworkSelectActivity93.this.mNetworkTypeValues.length);
            if (NetworkSelectActivity93.this.mCurrentSelected == pos) {
                Elog.d(NetworkSelectActivity93.TAG, "listener being invoked by setSelection, return ");
            } else if (pos >= NetworkSelectActivity93.this.mNetworkTypeValues.length) {
                Elog.d(NetworkSelectActivity93.TAG, "mNetworkTypeValues array out of bound return!");
            } else {
                int unused = NetworkSelectActivity93.this.mCurrentSelected = pos;
                NetworkSelectActivity93 networkSelectActivity93 = NetworkSelectActivity93.this;
                int unused2 = networkSelectActivity93.selectNetworkMode = networkSelectActivity93.mNetworkTypeValues[pos];
                NetworkSelectActivity93 networkSelectActivity932 = NetworkSelectActivity93.this;
                int unused3 = networkSelectActivity932.selectNetworkMode = networkSelectActivity932.filterTypeByCapability(networkSelectActivity932.selectNetworkMode);
                Elog.d(NetworkSelectActivity93.TAG, "selectNetworkMode to: " + NetworkSelectActivity93.this.selectNetworkMode);
                ContentResolver contentResolver = NetworkSelectActivity93.this.getContentResolver();
                Settings.Global.putInt(contentResolver, "preferred_network_mode" + NetworkSelectActivity93.this.mSubId, NetworkSelectActivity93.this.selectNetworkMode);
                new Thread(new Runnable() {
                    public void run() {
                        boolean result = NetworkSelectActivity93.this.mTelephonyManager.setPreferredNetworkType(NetworkSelectActivity93.this.mSubId, NetworkSelectActivity93.this.selectNetworkMode);
                        Message message = new Message();
                        message.what = 102;
                        message.arg1 = result ? 0 : -1;
                        NetworkSelectActivity93.this.mHandler.sendMessage(message);
                    }
                }).start();
            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };
    /* access modifiers changed from: private */
    public Spinner mPreferredNetworkSpinner = null;
    private SimCardInfo mSimCard;
    private int mSimType;
    /* access modifiers changed from: private */
    public int mSubId = 1;
    /* access modifiers changed from: private */
    public TelephonyManager mTelephonyManager = null;
    private String[] network_mode_labels;
    /* access modifiers changed from: private */
    public int selectNetworkMode;

    public NetworkSelectActivity93() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networkmode_switching);
        this.mPreferredNetworkSpinner = (Spinner) findViewById(R.id.networkModeSwitching);
        findViewById(R.id.network_mode_set_hint).setVisibility(8);
        findViewById(R.id.disable_eHRPD).setVisibility(8);
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        Elog.i(TAG, "mSimType " + this.mSimType);
        boolean isCdma = ModemCategory.isCdma();
        if (isCdma) {
            this.mIsEvdoSupport = true;
            CheckBox checkBox = (CheckBox) findViewById(R.id.disable_c2k_capability);
            this.mCbDisableC2kCapabilit = checkBox;
            checkBox.setVisibility(0);
            this.mCbDisableC2kCapabilit.setOnCheckedChangeListener(this);
            handleQueryCdmaCapability();
            CheckBox checkBox2 = (CheckBox) findViewById(R.id.disable_eHRPD);
            this.mDisableeHRPDCheckBox = checkBox2;
            checkBox2.setOnCheckedChangeListener(this);
        } else {
            this.mIsEvdoSupport = false;
            findViewById(R.id.disable_eHRPD).setVisibility(8);
        }
        CustomAdapter adapter = new CustomAdapter(this, 17367048, this.mNetworkTypeLabel);
        adapter.setDropDownViewResource(17367049);
        this.mPreferredNetworkSpinner.setAdapter(adapter);
        this.mPreferredNetworkSpinner.setOnItemSelectedListener(this.mPreferredNetworkListener);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        if (isCdma) {
            queryeHRPDStatus();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        int mSimType2 = getIntent().getIntExtra("mSimType", 0);
        Elog.i(TAG, "mSimType " + mSimType2);
        SimCardInfo simCardInfo = new SimCardInfo(mSimType2);
        this.mSimCard = simCardInfo;
        if (!ModemCategory.isSimReady(simCardInfo.getSimType())) {
            EmUtils.showToast("The card is not ready,please check it");
            Elog.w(TAG, "The card is not ready,please check it");
            this.mPreferredNetworkSpinner.setEnabled(false);
            return;
        }
        this.mSubId = ModemCategory.getSubIdBySlot(this.mSimCard.getSimType());
        Elog.d(TAG, "mSubId = " + this.mSubId);
        if (this.mSubId < 0) {
            Elog.w(TAG, "Invalid sub id");
            return;
        }
        this.mPreferredNetworkSpinner.setEnabled(true);
        this.mModemType = ModemCategory.getModemType();
        Elog.d(TAG, "mModemType = " + this.mModemType);
        new Thread(new Runnable() {
            public void run() {
                if (NetworkSelectActivity93.this.mTelephonyManager != null) {
                    int result = NetworkSelectActivity93.this.mTelephonyManager.createForSubscriptionId(NetworkSelectActivity93.this.mSubId).getPreferredNetworkType(NetworkSelectActivity93.this.mSubId);
                    Message message = new Message();
                    message.what = 101;
                    message.arg1 = result;
                    NetworkSelectActivity93.this.mHandler.sendMessage(message);
                    return;
                }
                Elog.e(NetworkSelectActivity93.TAG, "mTelephonyManager = null");
            }
        }).start();
    }

    /* access modifiers changed from: package-private */
    public void updateNetworkType(int type) {
        int index = findSpinnerIndexByType(type);
        Elog.d(TAG, "Index = " + index);
        if (index < 0 || index >= this.mPreferredNetworkSpinner.getCount()) {
            Elog.d(TAG, "Netwok select not support the type: " + type);
            EmUtils.showToast("Netwok select not support the type: " + type);
            this.mCurrentSelected = 21;
            this.mPreferredNetworkSpinner.setSelection(21, true);
            return;
        }
        this.mCurrentSelected = index;
        this.mPreferredNetworkSpinner.setSelection(index, true);
        Elog.d(TAG, "The NetworkSpinner show: " + index + " " + this.mNetworkTypeLabel[index]);
        StringBuilder sb = new StringBuilder();
        sb.append("Netwok select type is: ");
        sb.append(type);
        EmUtils.showToast(sb.toString());
    }

    /* access modifiers changed from: private */
    public int filterTypeByCapability(int type) {
        if (!((PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH & this.mTelephonyManager.getSupportedRadioAccessFamily()) > 0)) {
            if (type == 20) {
                return 9;
            }
            if (type == 19) {
                return 12;
            }
            if (type == 22) {
                return 10;
            }
            if (type == 21) {
                return 7;
            }
            if (type == 31) {
                return 28;
            }
            if (type == 32) {
                return 26;
            }
            if (type == 33) {
                return 27;
            }
        }
        return type;
    }

    private int findSpinnerIndexByType(int type) {
        if (type == 7) {
            type = 21;
        }
        if (type == 9 || type == 17) {
            type = 20;
        }
        if (type == 12 || type == 15) {
            type = 19;
        }
        if (type == 10) {
            type = 22;
        }
        if (type == 28 || type == 29) {
            type = 31;
        }
        if (type == 26 || type == 30) {
            type = 32;
        }
        if (type == 27) {
            type = 33;
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

    /* access modifiers changed from: private */
    public boolean isAvailable(int index) {
        int i = index;
        if (21 == i) {
            return false;
        }
        if (!this.mIsEvdoSupport && (i == 11 || i == 9)) {
            Elog.d(TAG, "Don't support EVDO, remove EVDO ONLY");
            return false;
        } else if (!this.mSimCard.isNRCapabilityViceSim() && (i == 15 || i == 16 || i == 17 || i == 18 || i == 19 || i == 20)) {
            return false;
        } else {
            if (this.mSimCard.isCapabilitySim()) {
                if (this.mSimCard.getCardType() == 3 && (i == 10 || i == 11 || i == 9)) {
                    return true;
                }
                if (this.mSimCard.getCardType() == 3) {
                    return false;
                }
                if (this.mSimCard.getCardType() == 1 && (i == 10 || i == 11 || i == 9 || i == 13 || i == 12 || i == 14 || i == 19 || i == 20)) {
                    return false;
                }
                int i2 = this.mModemType;
                if (i2 == 2 && (i == 0 || i == 2 || i == 4)) {
                    return false;
                }
                if (i2 == 1 && (i == 3 || i == 5)) {
                    return false;
                }
                if ((!ModemCategory.isLteSupport() && (i == 6 || i == 7 || i == 8 || i == 12 || i == 13)) || i == 4 || i == 5) {
                    return false;
                }
                return true;
            } else if (this.mSimCard.getCardType() == 3 && i == 10) {
                return true;
            } else {
                if (this.mSimCard.getCardType() == 3) {
                    return false;
                }
                if (this.mSimCard.getCardType() == 1 && (i == 10 || i == 11 || i == 9 || i == 13 || i == 12 || i == 14 || i == 19 || i == 20)) {
                    return false;
                }
                if (this.mSimCard.isLteCapabilityViceSim()) {
                    if (i == 6 || i == 7 || i == 8 || i == 12 || i == 13) {
                        return true;
                    }
                } else if (i == 6 || i == 7 || i == 8 || i == 12 || i == 13) {
                    return false;
                }
                if (this.mSimCard.isWCapabilityViceSim()) {
                    if (i == 3 || i == 5 || i == 4 || i == 5) {
                        return false;
                    }
                    return true;
                } else if (i == 1 || i == 10 || i == 9 || i == 14) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private void handleQueryCdmaCapability() {
        if ("1".equals(SystemProperties.get(FK_MTK_C2K_CAPABILITY, "0"))) {
            Elog.d(TAG, "QueryCdmaCapability set true");
            this.mFirstEntry = true;
            this.mCbDisableC2kCapabilit.setChecked(true);
            return;
        }
        Elog.d(TAG, "QueryCdmaCapability set false");
        this.mFirstEntry = false;
        this.mCbDisableC2kCapabilit.setChecked(false);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 2000:
                return new AlertDialog.Builder(this).setTitle("Disable c2k capability").setMessage("Please reboot the phone!").setPositiveButton("OK", (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.disable_c2k_capability) {
            if (this.mFirstEntry) {
                this.mFirstEntry = false;
                return;
            }
            try {
                if (this.mCbDisableC2kCapabilit.isChecked()) {
                    EmUtils.getEmHidlService().setDisableC2kCap("1");
                } else {
                    EmUtils.getEmHidlService().setDisableC2kCap("0");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e(TAG, "set property failed ...");
            }
            showDialog(2000);
        } else if (buttonView.getId() != R.id.disable_eHRPD) {
        } else {
            if (this.mEHRPDFirstEnter) {
                this.mEHRPDFirstEnter = false;
                return;
            }
            this.mDisableeHRPDCheckBox.setChecked(isChecked);
            seteHRPDStatus(isChecked ^ true ? 1 : 0);
        }
    }

    private void queryeHRPDStatus() {
        sendAtCommand(ModemCategory.getCdmaCmdArr(new String[]{"AT+eHRPD?", "+EHRPD:", "DESTRILD:C2K"}), 103);
    }

    private void seteHRPDStatus(int state) {
        String atCommand = null;
        switch (state) {
            case 0:
                atCommand = "AT+eHRPD=0";
                break;
            case 1:
                atCommand = "AT+eHRPD=1";
                break;
        }
        sendAtCommand(ModemCategory.getCdmaCmdArr(new String[]{atCommand, "", "DESTRILD:C2K"}), 104);
    }

    private void sendAtCommand(String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand: " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(this.mSimType, command, this.mHandler.obtainMessage(msg));
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Elog.d(NetworkSelectActivity93.TAG, "isAvailable: " + position + " is " + NetworkSelectActivity93.this.isAvailable(position));
            if (NetworkSelectActivity93.this.isAvailable(position)) {
                return super.getDropDownView(position, (View) null, parent);
            }
            TextView tv = new TextView(getContext());
            tv.setVisibility(8);
            tv.setHeight(0);
            return tv;
        }
    }

    class SimCardInfo {
        private boolean isCapabilitySim;
        private boolean isLteCapabilityViceSim;
        private boolean isNRCapabilityViceSim;
        private boolean isWCapabilityViceSim;
        private int mCardType;
        private int mSimType;

        public SimCardInfo(int mSimType2) {
            setSimType(mSimType2);
            setCapabilitySim(ModemCategory.isCapabilitySim(mSimType2));
            setWCapabilityViceSim(ModemCategory.CheckViceSimWCapability(mSimType2));
            setLteCapabilityViceSim(ModemCategory.checkViceSimCapability(mSimType2, 4096));
            setNRCapabilityViceSim(ModemCategory.CheckViceSimNRCapability(mSimType2));
            setCardType(getCardType(mSimType2));
        }

        public int getCardType() {
            return this.mCardType;
        }

        private void setCardType(int mCardType2) {
            this.mCardType = mCardType2;
        }

        public boolean isNRCapabilityViceSim() {
            return this.isNRCapabilityViceSim;
        }

        public void setNRCapabilityViceSim(boolean NRCapabilityViceSim) {
            this.isNRCapabilityViceSim = NRCapabilityViceSim;
        }

        public boolean isLteCapabilityViceSim() {
            return this.isLteCapabilityViceSim;
        }

        private void setLteCapabilityViceSim(boolean isLteCapabilityViceSim2) {
            this.isLteCapabilityViceSim = isLteCapabilityViceSim2;
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
        }

        public boolean isCapabilitySim() {
            return this.isCapabilitySim;
        }

        private void setCapabilitySim(boolean isCapabilitySim2) {
            this.isCapabilitySim = isCapabilitySim2;
        }

        private int getCardType(int mSimType2) {
            int type = 1;
            String[] Cardtype = ModemCategory.getSupportCardType(mSimType2);
            if (Cardtype != null) {
                boolean iscCt3gDualMode = ModemCategory.isCt3gDualMode(mSimType2);
                String Cardtypes = "";
                for (int i = 0; i < Cardtype.length; i++) {
                    Cardtypes = Cardtypes + Cardtype[i] + " ";
                }
                if ((Cardtypes.contains("USIM") && Cardtypes.contains("CSIM")) || iscCt3gDualMode) {
                    type = 2;
                } else if (Cardtypes.contains("USIM") && Cardtypes.contains("RUIM")) {
                    type = 2;
                } else if (Cardtypes.contains("CSIM") && Cardtypes.contains("SIM")) {
                    type = 2;
                } else if (!Cardtypes.contains("RUIM") && !Cardtypes.contains("CSIM")) {
                    type = 1;
                } else if ((!Cardtypes.contains("SIM") || Cardtypes.contains("USIM")) && !iscCt3gDualMode) {
                    type = 3;
                } else {
                    Elog.w(NetworkSelectActivity93.TAG, "the card type is unknow!");
                }
            } else {
                Elog.w(NetworkSelectActivity93.TAG, "there has no card insert,default type is GSM_only");
            }
            Elog.d(NetworkSelectActivity93.TAG, "card type = " + NetworkSelectActivity93.this.mCardTypeValues[type - 1]);
            return type;
        }
    }
}
