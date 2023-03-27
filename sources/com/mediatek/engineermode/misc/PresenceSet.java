package com.mediatek.engineermode.misc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.ims.ImsConfig;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.HashMap;

public class PresenceSet extends Activity implements View.OnClickListener {
    public static final String ACTION_PRESENCE_489_STATE = "android.intent.presence.RESET_489_STATE";
    public static final String ACTION_PRESENCE_RESET_ETAG = "android.intent.presence.RESET_ETAG";
    public static final String BROADCAST_PERMISSION = "com.mediatek.uce.permission.RECEIVE_UCE_EVENT";
    public static final Uri PRESENCE_SLOT0_SETTINGS_URI = Uri.parse("content://com.mediatek.presence.settings/slot0settings");
    public static final Uri PRESENCE_SLOT1_SETTINGS_URI = Uri.parse("content://com.mediatek.presence.settings/slot1settings");
    private static String TAG = "[SLOT0] PresenceSet";
    private static HashMap<String, String> mPresenceSetDataMap = null;
    private final String PRESENCE_PACKAGE_NAME = "com.mediatek.presence";
    private String mAddress = null;
    private Button mBt489TimerValue;
    private Button mBtCapabilityExpiryTime;
    private Button mBtCapabilityPollingPeriod;
    private Button mBtEabAvalCacheExpiry;
    private Button mBtEabCapCacheExpiry;
    private Button mBtEabCapDiscovery;
    private Button mBtEabCapPollExpiry;
    private Button mBtEabCapPollIntv;
    private Button mBtEabGzipEnabled;
    private Button mBtEabMaxNoEntries;
    private Button mBtEabPubErrRetryTimer;
    private Button mBtEabPublishTimer;
    private Button mBtEabPublishTimerExtended;
    private Button mBtEabSrcThrotPub;
    private Button mBtEnablePresence;
    private Button mBtMaxSubscriptionList;
    private Button mBtPublishExpiryTime;
    private Button mBtReset489State;
    private Button mBtResetETAG;
    private Button mBtSlotId;
    private String mData = null;
    private EditText mEd489TimerValue;
    private EditText mEdCapabilityExpiryTime;
    private EditText mEdCapabilityPollingPeriod;
    private EditText mEdEabAvalCacheExpiry;
    private EditText mEdEabCapCacheExpiry;
    private EditText mEdEabCapDiscovery;
    private EditText mEdEabCapPollExpiry;
    private EditText mEdEabCapPollIntv;
    private EditText mEdEabGzipEnabled;
    private EditText mEdEabMaxNoEntries;
    private EditText mEdEabPubErrRetryTimer;
    private EditText mEdEabPublishTimer;
    private EditText mEdEabPublishTimerExtended;
    private EditText mEdEabSrcThrotPub;
    private EditText mEdMaxSubscriptionList;
    private EditText mEdPublishExpiryTime;
    private EditText mEdSlotId;
    private EditText mEnablePresence;
    private ImsConfig mImsConfig;
    private String mMipiMode = null;
    private String mPort = null;
    private String mRWType = null;
    private int mSlotId = 0;
    private Toast mToast = null;
    private String mUsid = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presence_set);
        ImsManager imsManager = ImsManager.getInstance(this, 0);
        if (imsManager != null) {
            try {
                this.mImsConfig = imsManager.getConfigInterface();
            } catch (ImsException e) {
            }
        }
        this.mEd489TimerValue = (EditText) findViewById(R.id.presence_set_slot_id);
        this.mEd489TimerValue = (EditText) findViewById(R.id.presence_set_489_timer_value);
        this.mEdCapabilityPollingPeriod = (EditText) findViewById(R.id.presence_set_capability_polling_period);
        this.mEdCapabilityExpiryTime = (EditText) findViewById(R.id.presence_set_capability_expiry_time);
        this.mEdPublishExpiryTime = (EditText) findViewById(R.id.presence_set_publish_expiry_time);
        this.mEdMaxSubscriptionList = (EditText) findViewById(R.id.presence_set_max_subscription_list);
        this.mEnablePresence = (EditText) findViewById(R.id.presence_enable);
        this.mEdEabPublishTimer = (EditText) findViewById(R.id.presence_set_eab_publish_timer_value);
        this.mEdEabPublishTimerExtended = (EditText) findViewById(R.id.presence_set_eab_publish_timer_extended_value);
        this.mEdEabCapCacheExpiry = (EditText) findViewById(R.id.presence_set_eab_capability_cache_exp_value);
        this.mEdEabAvalCacheExpiry = (EditText) findViewById(R.id.presence_set_eab_availability_cache_exp_value);
        this.mEdEabSrcThrotPub = (EditText) findViewById(R.id.presence_set_eab_source_throttle_publish_value);
        this.mEdEabCapPollIntv = (EditText) findViewById(R.id.presence_set_eab_capability_poll_interval_value);
        this.mEdEabMaxNoEntries = (EditText) findViewById(R.id.presence_set_eab_max_no_entries_value);
        this.mEdEabCapPollExpiry = (EditText) findViewById(R.id.presence_set_eab_cap_poll_exp_value);
        this.mEdEabCapDiscovery = (EditText) findViewById(R.id.presence_set_eab_capability_discovery_value);
        this.mEdEabGzipEnabled = (EditText) findViewById(R.id.presence_set_eab_gzip_enabled_value);
        this.mEdEabPubErrRetryTimer = (EditText) findViewById(R.id.presence_set_eab_publish_error_retry_timer_value);
        this.mEdSlotId = (EditText) findViewById(R.id.presence_set_slot_id);
        this.mBt489TimerValue = (Button) findViewById(R.id.presence_set_489_timer_value_button);
        this.mBtCapabilityPollingPeriod = (Button) findViewById(R.id.presence_set_capability_polling_period_button);
        this.mBtCapabilityExpiryTime = (Button) findViewById(R.id.presence_set_capability_expiry_time_button);
        this.mBtPublishExpiryTime = (Button) findViewById(R.id.presence_set_publish_expiry_time_button);
        this.mBtMaxSubscriptionList = (Button) findViewById(R.id.presence_set_max_subscription_list_button);
        this.mBtEnablePresence = (Button) findViewById(R.id.presence_enable_button);
        this.mBtResetETAG = (Button) findViewById(R.id.presence_set_reset_ETAG);
        this.mBtReset489State = (Button) findViewById(R.id.presence_set_reset_489_state);
        this.mBtSlotId = (Button) findViewById(R.id.presence_set_slot_id_button);
        this.mBtEabPublishTimer = (Button) findViewById(R.id.presence_set_eab_publish_timer_button);
        this.mBtEabPublishTimerExtended = (Button) findViewById(R.id.presence_set_eab_publish_timer_extended_button);
        this.mBtEabCapCacheExpiry = (Button) findViewById(R.id.presence_set_eab_capability_cache_exp_button);
        this.mBtEabAvalCacheExpiry = (Button) findViewById(R.id.presence_set_eab_availability_cache_exp_button);
        this.mBtEabSrcThrotPub = (Button) findViewById(R.id.presence_set_eab_source_throttle_publish_button);
        this.mBtEabCapPollIntv = (Button) findViewById(R.id.presence_set_eab_capability_poll_interval_button);
        this.mBtEabMaxNoEntries = (Button) findViewById(R.id.presence_set_eab_max_no_entries_button);
        this.mBtEabCapPollExpiry = (Button) findViewById(R.id.presence_set_eab_cap_poll_exp_button);
        this.mBtEabCapDiscovery = (Button) findViewById(R.id.presence_set_eab_capability_discovery_button);
        this.mBtEabGzipEnabled = (Button) findViewById(R.id.presence_set_eab_gzip_enabled_button);
        this.mBtEabPubErrRetryTimer = (Button) findViewById(R.id.presence_set_eab_publish_error_retry_timer_button);
        this.mBt489TimerValue.setOnClickListener(this);
        this.mBtCapabilityPollingPeriod.setOnClickListener(this);
        this.mBtCapabilityExpiryTime.setOnClickListener(this);
        this.mBtPublishExpiryTime.setOnClickListener(this);
        this.mBtMaxSubscriptionList.setOnClickListener(this);
        this.mBtEnablePresence.setOnClickListener(this);
        this.mBtResetETAG.setOnClickListener(this);
        this.mBtReset489State.setOnClickListener(this);
        this.mBtEabPublishTimer.setOnClickListener(this);
        this.mBtEabPublishTimerExtended.setOnClickListener(this);
        this.mBtEabCapCacheExpiry.setOnClickListener(this);
        this.mBtEabAvalCacheExpiry.setOnClickListener(this);
        this.mBtEabSrcThrotPub.setOnClickListener(this);
        this.mBtEabCapPollIntv.setOnClickListener(this);
        this.mBtEabMaxNoEntries.setOnClickListener(this);
        this.mBtEabCapPollExpiry.setOnClickListener(this);
        this.mBtEabCapDiscovery.setOnClickListener(this);
        this.mBtEabGzipEnabled.setOnClickListener(this);
        this.mBtEabPubErrRetryTimer.setOnClickListener(this);
        this.mBtSlotId.setOnClickListener(this);
        if (mPresenceSetDataMap == null) {
            HashMap<String, String> hashMap = new HashMap<>();
            mPresenceSetDataMap = hashMap;
            hashMap.put("SipBadEventExpiredTime", "0");
            mPresenceSetDataMap.put("CapabilityPollingPeriod", "0");
            mPresenceSetDataMap.put("CapabilityExpiryTimeout", "0");
            mPresenceSetDataMap.put("PublishExpirePeriod", "0");
            mPresenceSetDataMap.put("MaxSubscriptionPresenceList", "0");
        }
        queryPresenceDB();
        this.mEdEabPublishTimer.setText(String.valueOf(queryEabConfiguration(15)));
        this.mEdEabPublishTimerExtended.setText(String.valueOf(queryEabConfiguration(16)));
        this.mEdEabCapCacheExpiry.setText(String.valueOf(queryEabConfiguration(18)));
        this.mEdEabAvalCacheExpiry.setText(String.valueOf(queryEabConfiguration(19)));
        this.mEdEabSrcThrotPub.setText(String.valueOf(queryEabConfiguration(21)));
        this.mEdEabCapPollIntv.setText(String.valueOf(queryEabConfiguration(20)));
        this.mEdEabMaxNoEntries.setText(String.valueOf(queryEabConfiguration(22)));
        this.mEdEabCapPollExpiry.setText(String.valueOf(queryEabConfiguration(23)));
        this.mEdEabCapDiscovery.setText(String.valueOf(queryEabConfiguration(17)));
        this.mEdEabGzipEnabled.setText(String.valueOf(queryEabConfiguration(24)));
        this.mEdEabPubErrRetryTimer.setText(String.valueOf(queryEabConfiguration(1001)));
        this.mEdSlotId.setText("0");
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void queryPresenceDB() {
        Cursor cursor = null;
        int i = this.mSlotId;
        if (i == 0) {
            cursor = getContentResolver().query(PRESENCE_SLOT0_SETTINGS_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        } else if (i == 1) {
            cursor = getContentResolver().query(PRESENCE_SLOT1_SETTINGS_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    String key = cursor.getString(cursor.getColumnIndex("key"));
                    String value = cursor.getString(cursor.getColumnIndex("value"));
                    if (mPresenceSetDataMap.containsKey(key)) {
                        String str = TAG;
                        Elog.d(str, key + " = " + value);
                        mPresenceSetDataMap.put(key, value);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Elog.d(TAG, "cursor is null!");
        }
        this.mEdCapabilityPollingPeriod.setText(mPresenceSetDataMap.get("CapabilityPollingPeriod"));
        this.mEdCapabilityExpiryTime.setText(mPresenceSetDataMap.get("CapabilityExpiryTimeout"));
        this.mEdPublishExpiryTime.setText(mPresenceSetDataMap.get("PublishExpirePeriod"));
        this.mEdMaxSubscriptionList.setText(mPresenceSetDataMap.get("MaxSubscriptionPresenceList"));
        this.mEnablePresence.setText(SystemProperties.get(MiscConfig.FK_MTK_MISC_UCE_SUPPORT));
        this.mEd489TimerValue.setText(mPresenceSetDataMap.get("SipBadEventExpiredTime"));
    }

    private void setPresenceDB(int slotId, String settins, String value) {
        String where = "key = \"" + settins + "\"";
        if (!value.equals("")) {
            ContentValues values = new ContentValues();
            values.put("value", value);
            Elog.d(TAG, "where = " + where);
            if (slotId == 0) {
                getContentResolver().update(PRESENCE_SLOT0_SETTINGS_URI, values, where, (String[]) null);
                showToast("set " + settins + " to " + value + " succeed");
                return;
            } else if (slotId == 1) {
                getContentResolver().update(PRESENCE_SLOT1_SETTINGS_URI, values, where, (String[]) null);
                showToast("set " + settins + " to " + value + " succeed");
                return;
            }
        }
        showToast("The value input is error!");
    }

    private int checkValue(String value) {
        try {
            return Integer.valueOf(value).intValue();
        } catch (Exception e) {
            Elog.d(TAG, "The value input is error!");
            showToast("The value input is error!");
            return 0;
        }
    }

    private void updateSlotId() {
        this.mSlotId = checkValue(this.mEdSlotId.getText().toString());
        TAG = "[SLOT" + this.mSlotId + "] PresenceSet";
        queryPresenceDB();
        ImsManager imsManager = ImsManager.getInstance(this, this.mSlotId);
        if (imsManager != null) {
            try {
                this.mImsConfig = imsManager.getConfigInterface();
            } catch (ImsException e) {
            }
        }
        showToast("Slot Id is updated to : " + this.mSlotId);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.presence_enable_button:
                if ("1".equals(this.mEnablePresence.getText().toString())) {
                    SystemProperties.set(MiscConfig.FK_MTK_MISC_UCE_SUPPORT, "1");
                    showToast("persist.vendor.mtk_uce_support = 1");
                    return;
                }
                SystemProperties.set(MiscConfig.FK_MTK_MISC_UCE_SUPPORT, "0");
                showToast("persist.vendor.mtk_uce_support = 0");
                return;
            case R.id.presence_set_489_timer_value_button:
                int msg = checkValue(this.mEd489TimerValue.getText().toString());
                SendBroadcast(this, ACTION_PRESENCE_489_STATE, msg);
                int i = this.mSlotId;
                setPresenceDB(i, "SipBadEventExpiredTime", "" + msg);
                String str = TAG;
                Elog.d(str, "sendBroadcast = android.intent.presence.RESET_489_STATE,489ExpiredTime = " + msg);
                showToast("sendBroadcast = android.intent.presence.RESET_489_STATE , " + msg + " succeed");
                return;
            case R.id.presence_set_capability_expiry_time_button:
                setPresenceDB(this.mSlotId, "CapabilityExpiryTimeout", this.mEdCapabilityExpiryTime.getText().toString());
                return;
            case R.id.presence_set_capability_polling_period_button:
                setPresenceDB(this.mSlotId, "CapabilityPollingPeriod", this.mEdCapabilityPollingPeriod.getText().toString());
                return;
            case R.id.presence_set_eab_availability_cache_exp_button:
                setEabConfiguration(19, Integer.parseInt(this.mEdEabAvalCacheExpiry.getText().toString()));
                return;
            case R.id.presence_set_eab_cap_poll_exp_button:
                setEabConfiguration(23, Integer.parseInt(this.mEdEabCapPollExpiry.getText().toString()));
                return;
            case R.id.presence_set_eab_capability_cache_exp_button:
                setEabConfiguration(18, Integer.parseInt(this.mEdEabCapCacheExpiry.getText().toString()));
                return;
            case R.id.presence_set_eab_capability_discovery_button:
                setEabConfiguration(17, Integer.parseInt(this.mEdEabCapDiscovery.getText().toString()));
                return;
            case R.id.presence_set_eab_capability_poll_interval_button:
                setEabConfiguration(20, Integer.parseInt(this.mEdEabCapPollIntv.getText().toString()));
                return;
            case R.id.presence_set_eab_gzip_enabled_button:
                setEabConfiguration(24, Integer.parseInt(this.mEdEabGzipEnabled.getText().toString()));
                return;
            case R.id.presence_set_eab_max_no_entries_button:
                setEabConfiguration(22, Integer.parseInt(this.mEdEabMaxNoEntries.getText().toString()));
                return;
            case R.id.presence_set_eab_publish_error_retry_timer_button:
                setEabConfiguration(1001, Integer.parseInt(this.mEdEabPubErrRetryTimer.getText().toString()));
                return;
            case R.id.presence_set_eab_publish_timer_button:
                setEabConfiguration(15, Integer.parseInt(this.mEdEabPublishTimer.getText().toString()));
                return;
            case R.id.presence_set_eab_publish_timer_extended_button:
                setEabConfiguration(16, Integer.parseInt(this.mEdEabPublishTimerExtended.getText().toString()));
                return;
            case R.id.presence_set_eab_source_throttle_publish_button:
                setEabConfiguration(21, Integer.parseInt(this.mEdEabSrcThrotPub.getText().toString()));
                return;
            case R.id.presence_set_max_subscription_list_button:
                setPresenceDB(this.mSlotId, "MaxSubscriptionPresenceList", this.mEdMaxSubscriptionList.getText().toString());
                return;
            case R.id.presence_set_publish_expiry_time_button:
                setPresenceDB(this.mSlotId, "PublishExpirePeriod", this.mEdPublishExpiryTime.getText().toString());
                return;
            case R.id.presence_set_reset_489_state:
                SendBroadcast(this, ACTION_PRESENCE_489_STATE, -1);
                Elog.d(TAG, "sendBroadcast = android.intent.presence.RESET_489_STATE");
                showToast("sendBroadcast = android.intent.presence.RESET_489_STATE succeed");
                return;
            case R.id.presence_set_reset_ETAG:
                SendBroadcast(this, ACTION_PRESENCE_RESET_ETAG, -1);
                Elog.d(TAG, "sendBroadcast = android.intent.presence.RESET_ETAG");
                showToast("sendBroadcast = android.intent.presence.RESET_ETAG succeed");
                return;
            case R.id.presence_set_slot_id_button:
                updateSlotId();
                return;
            default:
                return;
        }
    }

    private int queryEabConfiguration(int key) {
        int result = -1;
        try {
            result = this.mImsConfig.getProvisionedValue(key);
            String str = TAG;
            Elog.d(str, "getProvisionedValue, result=" + result);
            return result;
        } catch (ImsException ex) {
            String str2 = TAG;
            Elog.d(str2, "setEabConfiguration exception = " + ex);
            return result;
        }
    }

    private void setEabConfiguration(int key, int value) {
        try {
            this.mImsConfig.setProvisionedValue(key, value);
            showToast("config:" + key + ", value = " + value);
        } catch (ImsException ex) {
            String str = TAG;
            Elog.d(str, "setEabConfiguration exception = " + ex);
        }
    }

    private void SendBroadcast(Context mcontext, String intentID, int msg) {
        Intent intent = new Intent();
        intent.setAction(intentID);
        intent.setPackage("com.mediatek.presence");
        intent.putExtra("slotId", this.mSlotId);
        if (msg != -1) {
            intent.putExtra("489ExpiredTime", msg);
            String str = TAG;
            Elog.d(str, "sendBroadcast result = " + msg);
        }
        if (mcontext != null) {
            mcontext.sendBroadcast(intent, "com.mediatek.uce.permission.RECEIVE_UCE_EVENT");
        }
    }

    private void showToast(String msg) {
        Toast makeText = Toast.makeText(this, "[SLOT" + this.mSlotId + "] " + msg, 0);
        this.mToast = makeText;
        makeText.show();
    }
}
