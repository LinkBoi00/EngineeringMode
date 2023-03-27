package com.mediatek.engineermode.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaPlayer2;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.options.OptionsCapInfo;
import com.android.ims.internal.uce.options.OptionsCmdStatus;
import com.android.ims.internal.uce.options.OptionsSipResponse;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;
import com.android.ims.internal.uce.presence.PresCapInfo;
import com.android.ims.internal.uce.presence.PresCmdStatus;
import com.android.ims.internal.uce.presence.PresPublishTriggerType;
import com.android.ims.internal.uce.presence.PresResInfo;
import com.android.ims.internal.uce.presence.PresRlmiInfo;
import com.android.ims.internal.uce.presence.PresSipResponse;
import com.android.ims.internal.uce.presence.PresTupleInfo;
import com.android.ims.internal.uce.uceservice.IUceListener;
import com.android.ims.internal.uce.uceservice.IUceService;
import com.android.ims.internal.uce.uceservice.ImsUceManager;
import com.mediatek.engineermode.R;
import java.util.List;

public class AospPresenceListAdapter extends BaseAdapter implements View.OnClickListener {
    private final int EVENT_SHOW_TOAST = 0;
    private final String TOASTINFO = "toastInfo";
    /* access modifiers changed from: private */
    public String[] mContactUriList = new String[2];
    private Context mContext;
    private List<String> mData;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                AospPresenceListAdapter.this.showToast(msg.getData().getString("toastInfo"));
            }
        }
    };
    private final int mId = 1;
    private ImsUceManager mImsUceManager;
    private IOptionsListener mOptionsListener;
    private IOptionsService mOptionsService;
    private int mOptionsServiceHandle = -1;
    private UceLong mOptionsServiceListenerHdl;
    private IPresenceListener mPresenceListener;
    private IPresenceService mPresenceService;
    private int mPresenceServiceHandle = -1;
    private UceLong mPresenceServiceListenerHdl;
    private final String mReasonPhrase = "no reason phrase";
    private final int mSipResponseCode = MediaPlayer2.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK;
    private IUceListener mUceListener;
    private IUceService mUceService;
    private final int mUserData = 10;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            String value = prefs.getString(key, "-1");
            AospPresenceListAdapter aospPresenceListAdapter = AospPresenceListAdapter.this;
            aospPresenceListAdapter.showToast("Key changed: " + key);
            if (key.equals(AospPresenceTestActivity.PREF_KEY_CONTACT1)) {
                String[] access$100 = AospPresenceListAdapter.this.mContactUriList;
                access$100[0] = "tel:" + value;
            } else if (key.equals(AospPresenceTestActivity.PREF_KEY_CONTACT2)) {
                String[] access$1002 = AospPresenceListAdapter.this.mContactUriList;
                access$1002[1] = "tel:" + value;
            }
        }
    };

    public AospPresenceListAdapter(List<String> data) {
        this.mData = data;
    }

    public int getCount() {
        List<String> list = this.mData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        if (this.mContext == null) {
            this.mContext = viewGroup.getContext();
            initContactUriList();
            initUce();
        }
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.aosp_presence_listview_item, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mTv = (TextView) view.findViewById(R.id.mTv);
            viewHolder.mBtn = (Button) view.findViewById(R.id.mBtn);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        viewHolder2.mBtn.setText("Test");
        viewHolder2.mBtn.setTag(R.id.btn, this.mData.get(position));
        viewHolder2.mBtn.setOnClickListener(this);
        viewHolder2.mTv.setText(this.mData.get(position));
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtn:
                startTestApi((String) view.getTag(R.id.btn));
                return;
            default:
                return;
        }
    }

    static class ViewHolder {
        Button mBtn;
        TextView mTv;

        ViewHolder() {
        }
    }

    private void initContactUriList() {
        String msisdn = TelephonyManager.from(this.mContext).getMsisdn();
        if (msisdn == null) {
            msisdn = "";
        } else if (msisdn.startsWith("+")) {
            msisdn = msisdn.substring(1);
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(AospPresenceTestActivity.PREF_NAME, 0);
        String contact1PhoneNum = sharedPreferences.getString(AospPresenceTestActivity.PREF_KEY_CONTACT1, msisdn);
        String contact2PhoneNum = sharedPreferences.getString(AospPresenceTestActivity.PREF_KEY_CONTACT2, msisdn);
        String[] strArr = this.mContactUriList;
        strArr[0] = "tel:" + contact1PhoneNum;
        String[] strArr2 = this.mContactUriList;
        strArr2[1] = "tel:" + contact2PhoneNum;
        sharedPreferences.registerOnSharedPreferenceChangeListener(this.prefListener);
    }

    private void initUce() {
        ImsUceManager instance = ImsUceManager.getInstance(this.mContext);
        this.mImsUceManager = instance;
        if (instance != null) {
            instance.createUceService(true);
            this.mUceService = this.mImsUceManager.getUceServiceInstance();
        }
        this.mUceListener = new IUceListener.Stub() {
            public void setStatus(int serviceStatusValue) throws RemoteException {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [setStatus] Service status value = " + serviceStatusValue);
            }
        };
        this.mPresenceListener = new IPresenceListener.Stub() {
            public void getVersionCb(String version) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [getVersionCb] Presence service version = " + version);
            }

            public void serviceAvailable(StatusCode statusCode) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [serviceAvailable] StatusCode = " + statusCode);
            }

            public void serviceUnAvailable(StatusCode statusCode) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [serviceUnAvailable] StatusCode = " + statusCode);
            }

            public void publishTriggering(PresPublishTriggerType type) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [publishTriggering] Type = " + type.getPublishTrigeerType());
            }

            public void cmdStatus(PresCmdStatus cmdStatus) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [CmdStatus] CmdId = " + cmdStatus.getCmdId().getCmdId() + ", StatusCode = " + cmdStatus.getStatus().getStatusCode() + ", UserData = " + cmdStatus.getUserData() + ", RequestId = " + cmdStatus.getRequestId());
            }

            public void sipResponseReceived(PresSipResponse response) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [sipResponseReceived] CmdId = " + response.getCmdId().getCmdId() + ", RequestId = " + response.getRequestId() + ", SipResponseCode = " + response.getSipResponseCode() + ", RetryAfter time = " + response.getRetryAfter() + ", ReasonPharse = " + response.getReasonPhrase());
            }

            public void capInfoReceived(String contactUri, PresTupleInfo[] tupleInfo) {
                StringBuilder tupleInfoDetail = new StringBuilder();
                for (int i = 0; i < tupleInfo.length; i++) {
                    tupleInfoDetail.append("[i] Feature tag = " + tupleInfo[i].getFeatureTag());
                    tupleInfoDetail.append("[i] Contact uri in tupleInfo = " + tupleInfo[i].getContactUri());
                    tupleInfoDetail.append("[i] Timestamp = " + tupleInfo[i].getTimestamp());
                }
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [capInfoReceived] Contact uri = " + contactUri + tupleInfoDetail.toString());
            }

            public void listCapInfoReceived(PresRlmiInfo rlmiInfo, PresResInfo[] resInfo) {
                StringBuilder resInfoDetail = new StringBuilder();
                int i = 0;
                while (i < resInfo.length) {
                    resInfoDetail.append("[" + i + "] ResUri = " + resInfo[i].getResUri());
                    resInfoDetail.append("[" + i + "] DisPlayName = " + resInfo[i].getDisplayName());
                    resInfoDetail.append("[" + i + "] ResInstanceState = " + resInfo[i].getInstanceInfo().getResInstanceState());
                    resInfoDetail.append("[" + i + "] ResId = " + resInfo[i].getInstanceInfo().getResId());
                    resInfoDetail.append("[" + i + "] Reason = " + resInfo[i].getInstanceInfo().getReason());
                    resInfoDetail.append("[" + i + "] Uri = " + resInfo[i].getInstanceInfo().getPresentityUri());
                    PresTupleInfo[] tupleInfo = resInfo[i].getInstanceInfo().getTupleInfo();
                    while (0 < tupleInfo.length) {
                        resInfoDetail.append("[" + i + "][" + 0 + "] Feature tag = " + tupleInfo[0].getFeatureTag());
                        resInfoDetail.append("[" + i + "][" + 0 + "] Contact uri in tupleInfo = " + tupleInfo[0].getContactUri());
                        resInfoDetail.append("[" + i + "][" + 0 + "] Timestamp = " + tupleInfo[0].getTimestamp());
                        i++;
                    }
                    i++;
                }
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [listCapInfoReceived]  Rlmi uri = " + rlmiInfo.getUri() + ", Version = " + rlmiInfo.getVersion() + ", FullState = " + rlmiInfo.isFullState() + ", ListName = " + rlmiInfo.getListName() + ", RequestId = " + rlmiInfo.getRequestId() + ", SubscrptionState = " + rlmiInfo.getPresSubscriptionState().getPresSubscriptionStateValue() + ", SubscriptionExpireTime = " + rlmiInfo.getSubscriptionExpireTime() + ", SubscriptionTerminatedReason = " + rlmiInfo.getSubscriptionTerminatedReason() + resInfoDetail.toString());
            }

            public void unpublishMessageSent() {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [unpublishMessageSent] ");
            }
        };
        this.mOptionsListener = new IOptionsListener.Stub() {
            public void getVersionCb(String version) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [getVersionCb] Options service version = " + version);
            }

            public void serviceAvailable(StatusCode statusCode) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [serviceAvailable] StatusCode = " + statusCode);
            }

            public void serviceUnavailable(StatusCode statusCode) {
                AospPresenceListAdapter.this.sendMessageToHandler("SUCCESS!! [serviceUnAvailable] StatusCode = " + statusCode);
            }

            public void sipResponseReceived(String uri, OptionsSipResponse response, OptionsCapInfo optionsCapInfo) {
                AospPresenceListAdapter.this.sendMessageToHandler(("SUCCESS!! [sipResponseReceived] CmdId = " + response.getCmdId().getCmdId() + ", RequestId = " + response.getRequestId() + ", SipResponseCode = " + response.getSipResponseCode() + ", RetryAfter time = " + response.getRetryAfter() + ", ReasonPharse = " + response.getReasonPhrase() + ", sdp = " + optionsCapInfo.getSdp()) + AospPresenceListAdapter.this.transferCapInfoToString(optionsCapInfo.getCapInfo()));
            }

            public void cmdStatus(OptionsCmdStatus cmdStatus) {
                AospPresenceListAdapter.this.sendMessageToHandler(("SUCCESS!! [CmdStatus] CmdId = " + cmdStatus.getCmdId().getCmdId() + ", StatusCode = " + cmdStatus.getStatus().getStatusCode() + ", UserData = " + cmdStatus.getUserData()) + AospPresenceListAdapter.this.transferCapInfoToString(cmdStatus.getCapInfo()));
            }

            public void incomingOptions(String uri, OptionsCapInfo optionsCapInfo, int tId) {
                AospPresenceListAdapter.this.sendMessageToHandler(("SUCCESS!! [incomingOptions] uri = " + uri + ", tId = " + tId) + AospPresenceListAdapter.this.transferCapInfoToString(optionsCapInfo.getCapInfo()));
            }
        };
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void startTestApi(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -1689962892: goto L_0x0119;
                case -1471587171: goto L_0x010f;
                case -1272511197: goto L_0x0104;
                case -1261760667: goto L_0x00f9;
                case -1041480718: goto L_0x00ef;
                case -931201733: goto L_0x00e4;
                case -454681828: goto L_0x00d9;
                case -320955208: goto L_0x00ce;
                case 182346004: goto L_0x00c4;
                case 359683280: goto L_0x00ba;
                case 732184031: goto L_0x00ae;
                case 811377918: goto L_0x00a2;
                case 915770818: goto L_0x0096;
                case 1003500663: goto L_0x008b;
                case 1130387894: goto L_0x007f;
                case 1414784848: goto L_0x0073;
                case 1428534606: goto L_0x0067;
                case 1615425298: goto L_0x005c;
                case 1624300765: goto L_0x0050;
                case 1641941676: goto L_0x0044;
                case 1674844705: goto L_0x0038;
                case 1699648681: goto L_0x002c;
                case 1710955214: goto L_0x0020;
                case 1851609810: goto L_0x0014;
                case 1937902017: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0123
        L_0x0009:
            java.lang.String r0 = "[UCE]destroyPresenceService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 6
            goto L_0x0124
        L_0x0014:
            java.lang.String r0 = "[OPTIONS]removeListener"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 19
            goto L_0x0124
        L_0x0020:
            java.lang.String r0 = "[OPTIONS]getContactCap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 22
            goto L_0x0124
        L_0x002c:
            java.lang.String r0 = "[PRESENCE]getVersion"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 10
            goto L_0x0124
        L_0x0038:
            java.lang.String r0 = "[PRESENCE]getContactCap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 15
            goto L_0x0124
        L_0x0044:
            java.lang.String r0 = "[UCE]getOptionsService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 9
            goto L_0x0124
        L_0x0050:
            java.lang.String r0 = "[OPTIONS]responseIncomingOptions"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 24
            goto L_0x0124
        L_0x005c:
            java.lang.String r0 = "[UCE]stopService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0124
        L_0x0067:
            java.lang.String r0 = "[PRESENCE]addListener"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 11
            goto L_0x0124
        L_0x0073:
            java.lang.String r0 = "[OPTIONS]getContactListCap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 23
            goto L_0x0124
        L_0x007f:
            java.lang.String r0 = "[OPTIONS]getMyInfo"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 21
            goto L_0x0124
        L_0x008b:
            java.lang.String r0 = "[UCE]isServiceStarted"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0124
        L_0x0096:
            java.lang.String r0 = "[OPTIONS]setMyInfo"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 20
            goto L_0x0124
        L_0x00a2:
            java.lang.String r0 = "[PRESENCE]publishMyCap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 14
            goto L_0x0124
        L_0x00ae:
            java.lang.String r0 = "[PRESENCE]removeListener"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 12
            goto L_0x0124
        L_0x00ba:
            java.lang.String r0 = "[UCE]destroyOptionsService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x0124
        L_0x00c4:
            java.lang.String r0 = "[UCE]startService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0124
        L_0x00ce:
            java.lang.String r0 = "[PRESENCE]reenableService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 13
            goto L_0x0124
        L_0x00d9:
            java.lang.String r0 = "[OPTIONS]getVersion"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 17
            goto L_0x0124
        L_0x00e4:
            java.lang.String r0 = "[OPTIONS]addListener"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 18
            goto L_0x0124
        L_0x00ef:
            java.lang.String r0 = "[UCE]getServiceStatus"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 7
            goto L_0x0124
        L_0x00f9:
            java.lang.String r0 = "[UCE]getPresenceService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 8
            goto L_0x0124
        L_0x0104:
            java.lang.String r0 = "[PRESENCE]getContactListCap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 16
            goto L_0x0124
        L_0x010f:
            java.lang.String r0 = "[UCE]createPresenceService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 5
            goto L_0x0124
        L_0x0119:
            java.lang.String r0 = "[UCE]createOptionsService"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x0124
        L_0x0123:
            r0 = -1
        L_0x0124:
            switch(r0) {
                case 0: goto L_0x018c;
                case 1: goto L_0x0188;
                case 2: goto L_0x0184;
                case 3: goto L_0x0180;
                case 4: goto L_0x017c;
                case 5: goto L_0x0178;
                case 6: goto L_0x0174;
                case 7: goto L_0x0170;
                case 8: goto L_0x016c;
                case 9: goto L_0x0168;
                case 10: goto L_0x0164;
                case 11: goto L_0x0160;
                case 12: goto L_0x015c;
                case 13: goto L_0x0158;
                case 14: goto L_0x0154;
                case 15: goto L_0x0150;
                case 16: goto L_0x014c;
                case 17: goto L_0x0148;
                case 18: goto L_0x0144;
                case 19: goto L_0x0140;
                case 20: goto L_0x013c;
                case 21: goto L_0x0138;
                case 22: goto L_0x0133;
                case 23: goto L_0x012e;
                case 24: goto L_0x0129;
                default: goto L_0x0127;
            }
        L_0x0127:
            goto L_0x0190
        L_0x0129:
            r1.testOptionsResponseIncomingOptions()
            goto L_0x0190
        L_0x012e:
            r1.testOptionsGetContactListCap()
            goto L_0x0190
        L_0x0133:
            r1.testOptionsGetContactCap()
            goto L_0x0190
        L_0x0138:
            r1.testOptionsGetMyInfo()
            goto L_0x0190
        L_0x013c:
            r1.testOptionsSetMyInfo()
            goto L_0x0190
        L_0x0140:
            r1.testOptionsRemoveListener()
            goto L_0x0190
        L_0x0144:
            r1.testOptionsAddListener()
            goto L_0x0190
        L_0x0148:
            r1.testOptionsGetVersion()
            goto L_0x0190
        L_0x014c:
            r1.testPresenceGetContactListCap()
            goto L_0x0190
        L_0x0150:
            r1.testPresenceGetContactCap()
            goto L_0x0190
        L_0x0154:
            r1.testPresencePublshMyCap()
            goto L_0x0190
        L_0x0158:
            r1.testPresenceReenableService()
            goto L_0x0190
        L_0x015c:
            r1.testPresenceRemoveLitener()
            goto L_0x0190
        L_0x0160:
            r1.testPresenceAddLitener()
            goto L_0x0190
        L_0x0164:
            r1.testPresenceGetVersion()
            goto L_0x0190
        L_0x0168:
            r1.testUceGetOptionsService()
            goto L_0x0190
        L_0x016c:
            r1.testUceGetPresenceService()
            goto L_0x0190
        L_0x0170:
            r1.testUceGetServiceStatus()
            goto L_0x0190
        L_0x0174:
            r1.testUceDestroyPresenceService()
            goto L_0x0190
        L_0x0178:
            r1.testUceCreatePresenceService()
            goto L_0x0190
        L_0x017c:
            r1.testUceDestroyOptionsService()
            goto L_0x0190
        L_0x0180:
            r1.testUceCreateOptionsService()
            goto L_0x0190
        L_0x0184:
            r1.testUceIsServiceStarted()
            goto L_0x0190
        L_0x0188:
            r1.testUceStopService()
            goto L_0x0190
        L_0x018c:
            r1.testUceStartService()
        L_0x0190:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.misc.AospPresenceListAdapter.startTestApi(java.lang.String):void");
    }

    private void testUceStartService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            if (iUceService.startService(this.mUceListener)) {
                showToast("SUCCESS!! [startService]");
            } else {
                showToast("FAIL!! [startService]");
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceStopService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            if (iUceService.stopService()) {
                showToast("SUCCESS!! [stopService]");
            } else {
                showToast("FAIL!! [stopService]");
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceIsServiceStarted() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            boolean ret = iUceService.isServiceStarted();
            showToast("SUCCESS!! [isServiceStarted] ret = " + ret);
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceCreateOptionsService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            this.mOptionsServiceHandle = iUceService.createOptionsService((IOptionsListener) null, (UceLong) null);
            showToast("SUCCESS!! [createOptionsService]  ret = " + this.mOptionsServiceHandle);
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceDestroyOptionsService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            iUceService.destroyOptionsService(this.mOptionsServiceHandle);
            showToast("SUCCESS!! [destroyOptionsService]");
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceCreatePresenceService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            this.mPresenceServiceHandle = iUceService.createPresenceService((IPresenceListener) null, (UceLong) null);
            showToast("SUCCESS!! [createPresenceService] presenceServiceHandle = " + this.mPresenceServiceHandle);
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceDestroyPresenceService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            iUceService.destroyPresenceService(this.mOptionsServiceHandle);
            showToast("SUCCESS!! [destroyPresenceService] ");
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceGetServiceStatus() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            boolean ret = iUceService.getServiceStatus();
            showToast("SUCCESS!! [getServiceStatus] ret = " + ret);
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceGetPresenceService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            IPresenceService presenceService = iUceService.getPresenceService();
            this.mPresenceService = presenceService;
            if (presenceService != null) {
                showToast("SUCCESS!! [GetPresenceService] mPresenceService is not null");
            } else {
                showToast("SUCCESS!! [GetPresenceService] mPresenceService is null");
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testUceGetOptionsService() {
        IUceService iUceService = this.mUceService;
        if (iUceService == null) {
            showToast("FAIL!! UceService is null");
            return;
        }
        try {
            IOptionsService optionsService = iUceService.getOptionsService();
            this.mOptionsService = optionsService;
            if (optionsService != null) {
                showToast("SUCCESS!! [GetOptionsService] mOptionsService is not null");
            } else {
                showToast("SUCCESS!! [GetOptionsService] mOptionsService is null");
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceAddLitener() {
        try {
            if (this.mPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            UceLong uceLong = new UceLong();
            this.mPresenceServiceListenerHdl = uceLong;
            uceLong.setUceLong(100);
            StatusCode ret = this.mPresenceService.addListener(this.mPresenceServiceHandle, this.mPresenceListener, this.mPresenceServiceListenerHdl);
            if (ret != null) {
                showToastWithStatusCode("AddListener", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceRemoveLitener() {
        try {
            IPresenceService iPresenceService = this.mPresenceService;
            if (iPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = iPresenceService.removeListener(this.mPresenceServiceHandle, this.mPresenceServiceListenerHdl);
            if (ret != null) {
                showToastWithStatusCode("RemoveListener", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceGetVersion() {
        try {
            IPresenceService iPresenceService = this.mPresenceService;
            if (iPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = iPresenceService.getVersion(this.mPresenceServiceHandle);
            if (ret != null) {
                showToastWithStatusCode("GetVersion", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceReenableService() {
        try {
            IPresenceService iPresenceService = this.mPresenceService;
            if (iPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = iPresenceService.reenableService(this.mPresenceServiceHandle, 10);
            if (ret != null) {
                showToastWithStatusCode("ReenableService", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresencePublshMyCap() {
        try {
            if (this.mPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = this.mPresenceService.publishMyCap(this.mPresenceServiceHandle, createPresCapInfo(), 10);
            if (ret != null) {
                showToastWithStatusCode("PublishMyCap", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceGetContactCap() {
        try {
            IPresenceService iPresenceService = this.mPresenceService;
            if (iPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = iPresenceService.getContactCap(this.mPresenceServiceHandle, this.mContactUriList[0], 10);
            if (ret != null) {
                showToastWithStatusCode("GetContactCap", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testPresenceGetContactListCap() {
        try {
            IPresenceService iPresenceService = this.mPresenceService;
            if (iPresenceService == null) {
                showToast("FAIL!! Please Step1. Click createPresenceService\nStep2. Click getPresenceService");
                return;
            }
            StatusCode ret = iPresenceService.getContactListCap(this.mPresenceServiceHandle, this.mContactUriList, 10);
            if (ret != null) {
                showToastWithStatusCode("GetContactListCap", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsAddListener() {
        try {
            if (this.mOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            UceLong uceLong = new UceLong();
            this.mOptionsServiceListenerHdl = uceLong;
            uceLong.setUceLong(100);
            StatusCode ret = this.mOptionsService.addListener(this.mOptionsServiceHandle, this.mOptionsListener, this.mOptionsServiceListenerHdl);
            if (ret != null) {
                showToastWithStatusCode("AddListener", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsRemoveListener() {
        try {
            IOptionsService iOptionsService = this.mOptionsService;
            if (iOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = iOptionsService.removeListener(this.mOptionsServiceHandle, this.mOptionsServiceListenerHdl);
            if (ret != null) {
                showToastWithStatusCode("RemoveListener", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsGetVersion() {
        try {
            IOptionsService iOptionsService = this.mOptionsService;
            if (iOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = iOptionsService.getVersion(this.mOptionsServiceHandle);
            if (ret != null) {
                showToastWithStatusCode("GetVersion", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsSetMyInfo() {
        try {
            if (this.mOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = this.mOptionsService.setMyInfo(this.mOptionsServiceHandle, new CapInfo(), 10);
            if (ret != null) {
                showToastWithStatusCode("SetMyInfo", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsGetMyInfo() {
        try {
            if (this.mOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            new CapInfo();
            StatusCode ret = this.mOptionsService.getMyInfo(this.mOptionsServiceHandle, 10);
            if (ret != null) {
                showToastWithStatusCode("GetMyInfo", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsGetContactCap() {
        try {
            IOptionsService iOptionsService = this.mOptionsService;
            if (iOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = iOptionsService.getContactCap(this.mOptionsServiceHandle, this.mContactUriList[0], 10);
            if (ret != null) {
                showToastWithStatusCode("GetContactCap", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsGetContactListCap() {
        try {
            IOptionsService iOptionsService = this.mOptionsService;
            if (iOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = iOptionsService.getContactListCap(this.mOptionsServiceHandle, this.mContactUriList, 10);
            if (ret != null) {
                showToastWithStatusCode("GetContactListCap", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private void testOptionsResponseIncomingOptions() {
        try {
            if (this.mOptionsService == null) {
                showToast("FAIL!! Please Step1. Click createOptionsService\nStep2. Click getOptionsService");
                return;
            }
            StatusCode ret = this.mOptionsService.responseIncomingOptions(this.mOptionsServiceHandle, 1, MediaPlayer2.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK, "no reason phrase", createOptionsCapInfo(), false);
            if (ret != null) {
                showToastWithStatusCode("ResponseIncomingOptions", ret.getStatusCode());
            }
        } catch (RemoteException e) {
            showToast("FAIL!! The connection between client and server is lost");
        }
    }

    private PresCapInfo createPresCapInfo() {
        PresCapInfo presCapInfo = new PresCapInfo();
        presCapInfo.setCapInfo(new CapInfo());
        presCapInfo.setContactUri("test");
        return presCapInfo;
    }

    private OptionsCapInfo createOptionsCapInfo() {
        OptionsCapInfo optionsCapInfo = new OptionsCapInfo();
        CapInfo capInfo = new CapInfo();
        optionsCapInfo.setSdp("test");
        optionsCapInfo.setCapInfo(capInfo);
        return optionsCapInfo;
    }

    /* access modifiers changed from: private */
    public void showToast(String info) {
        Toast.makeText(this.mContext, info, 0).show();
    }

    private void showToastWithStatusCode(String functionName, int statusCode) {
        if (statusCode == 0) {
            Context context = this.mContext;
            Toast.makeText(context, "SUCCESS!! [ " + functionName + "] statusCode = " + statusCode, 0).show();
            return;
        }
        Context context2 = this.mContext;
        Toast.makeText(context2, "FAIL!! [ " + functionName + "] statusCode = " + statusCode, 0).show();
    }

    /* access modifiers changed from: private */
    public void sendMessageToHandler(String info) {
        Message msg = new Message();
        msg.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("toastInfo", info);
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    public String transferCapInfoToString(CapInfo capInfo) {
        String info = ", isImsSupported = " + capInfo.isImSupported() + ", isFtThumbSupported = " + capInfo.isFtThumbSupported() + ", isFtSnFSupported = " + capInfo.isFtSnFSupported() + ", isFtHttpSupported = " + capInfo.isFtHttpSupported() + ", isFtSupported = " + capInfo.isFtSupported() + ", isIsSupported = " + capInfo.isIsSupported() + ", isVsDuringCSSupported = " + capInfo.isVsDuringCSSupported() + ", isVsSupported = " + capInfo.isVsSupported() + ", isSpSupported = " + capInfo.isSpSupported() + ", isCdViaPresenceSupported = " + capInfo.isCdViaPresenceSupported() + ", isIpVideoSupported = " + capInfo.isIpVideoSupported() + ", isGeoPullFtSupported = " + capInfo.isGeoPullFtSupported() + ", isGeoPullSupported = " + capInfo.isGeoPullSupported() + ", isGeoPushSupported = " + capInfo.isGeoPushSupported() + ", isSmSupported = " + capInfo.isSmSupported() + ", isFullSnFGroupChatSupported = " + capInfo.isFullSnFGroupChatSupported() + ", isRcsIpVoiceCallSupported = " + capInfo.isRcsIpVoiceCallSupported() + ", isRcsIpVideoCallSupported = " + capInfo.isRcsIpVideoCallSupported() + ", isRcsIpVideoOnlyCallSupported = " + capInfo.isRcsIpVideoOnlyCallSupported() + ", timestamp = " + capInfo.getCapTimestamp();
        String[] extensions = capInfo.getExts();
        for (int i = 0; i < extensions.length; i++) {
            info = (info + ",") + extensions[i];
        }
        return info;
    }
}
