package com.mediatek.engineermode.siminfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.internal.telephony.uicc.IccServiceInfo;
import com.mediatek.internal.telephony.uicc.MtkSIMRecords;
import com.mediatek.telephony.MtkTelephonyManagerEx;
import java.util.ArrayList;
import java.util.List;

public class SimInfo extends Activity {
    private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private static final String INTENT_KEY_ICC_STATE = "ss";
    private static final int INTENT_SIM_INFO_DPDATE = 2;
    private static final int INTENT_VALUE_ICC_ABSENT = 3;
    private static final int INTENT_VALUE_ICC_READY = 1;
    private static final String TAG = "SimInfo";
    private static final String VALUE_ICC_ABSENT = "ABSENT";
    private static final String VALUE_ICC_LOADED = "LOADED";
    private final BroadcastReceiver SimCardChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Elog.d(SimInfo.TAG, "SIMChangedBroadcastReceiver ");
            if (SimInfo.this.mFirstBroadcast) {
                Elog.d(SimInfo.TAG, "it is the  mFirstBroadcast");
                boolean unused = SimInfo.this.mFirstBroadcast = false;
            } else if (action != null && action.equals(SimInfo.ACTION_SIM_STATE_CHANGED)) {
                String newState = intent.getStringExtra(SimInfo.INTENT_KEY_ICC_STATE);
                int changeSlot = intent.getIntExtra("slot", 0);
                Elog.d(SimInfo.TAG, "SIM state change changeSlot=" + changeSlot + " mSimType=" + SimInfo.this.mSimType + " new state =" + newState);
                if (changeSlot != SimInfo.this.mSimType) {
                    return;
                }
                if (newState.equals(SimInfo.VALUE_ICC_LOADED)) {
                    Elog.d(SimInfo.TAG, "SIM card status ready ");
                    SimInfo.this.loadInCardInfoBackground();
                } else if (newState.equals(SimInfo.VALUE_ICC_ABSENT)) {
                    Elog.d(SimInfo.TAG, "SIM card status absent ");
                    SimInfo.this.mHandler.sendEmptyMessage(3);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mFirstBroadcast = true;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Elog.d(SimInfo.TAG, "SIM card status ready ");
                    TextView textView = SimInfo.this.siminfo_status;
                    textView.setText("sim info status: Ready , card type: " + SimInfo.this.mIccCardTypes[SimInfo.this.mIccCardType]);
                    return;
                case 2:
                    String info = msg.obj.toString();
                    if (info.equals("clean")) {
                        SimInfo.this.siminfo.update(0, "");
                        return;
                    } else if (info.equals("update")) {
                        SimInfo.this.siminfo.update(2, "");
                        return;
                    } else {
                        SimInfo.this.siminfo.update(1, info);
                        return;
                    }
                case 3:
                    Elog.d(SimInfo.TAG, "SIM card status absent ");
                    SimInfo.this.siminfo_status.setText("sim info status: UNReady");
                    SimInfo.this.siminfo.update(0, "");
                    SimInfo.this.siminfo.update(2, "");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mIccCardType = 0;
    /* access modifiers changed from: private */
    public String[] mIccCardTypes = {"SIM", "USIM", "UNSUPPORT"};
    IccRecords mIccRecords = null;
    private FrameLayout mInfoFrameLayout;
    List<SimInfoData> mSimInfoData;
    /* access modifiers changed from: private */
    public int mSimType = 0;
    private int mSubId = 1;
    private Toast mToast = null;
    NormalTableComponent siminfo = null;
    TextView siminfo_status = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siminfo);
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        Elog.d(TAG, " onCreate mSimType " + this.mSimType);
        this.mInfoFrameLayout = (FrameLayout) findViewById(R.id.siminfo_frame);
        this.mFirstBroadcast = true;
        SimInfoUpdate simInfoUpdate = new SimInfoUpdate(this);
        this.siminfo = simInfoUpdate;
        View view = simInfoUpdate.getView();
        if (view == null) {
            Elog.e(TAG, "updateUI view is null");
            return;
        }
        this.mInfoFrameLayout.removeAllViews();
        this.mInfoFrameLayout.addView(view);
        TextView textView = (TextView) findViewById(R.id.siminfo_status);
        this.siminfo_status = textView;
        textView.setText("sim" + this.mSimType + " info status: UNReady");
        this.mSimInfoData = new ArrayList();
        updateSimInfoData();
        if (ModemCategory.isSimReady(this.mSimType)) {
            Elog.d(TAG, "loadInCardInfoBackground before registSimReceiver");
            loadInCardInfoBackground();
        }
        registSimReceiver(this);
    }

    private void updateSimInfoData() {
        this.mSimInfoData.add(new SimInfoData("CDR-PER-512", "EF-ICCID", 12258, new String[]{"3F00", "3F00"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-514", "EF-MSISDN", 28480, new String[]{"7F10", "7FFF"}, 1, "linear fixed", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-516", "EF-CPHS MAIL BOX NUMBER", 28439, new String[]{"7F20", "x"}, 1, "linear fixed", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-518", "EF-3GPP MAIL BOX DIALING NUMBER", 28615, new String[]{"x", "7FFF"}, 1, "linear fixed", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-520", "EF-HPPLMN SEARCH PERIOD", 28465, new String[]{"x", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-522", "EF-LOCI", 28529, new String[]{"7F20", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-524", "EF-GPRS/PS-LOCI", 28531, new String[]{"x", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-526", "EF-ACCESS CONTROL CLASS", 28536, new String[]{"7F20", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-528", "EF-ADMINISTRATIVE DATA", 28589, new String[]{"7F20", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-530", "EF-FPLMN", 28539, new String[]{"7F20", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-536", "EF-PLMN NETWORK NAME", 28613, new String[]{"x", "7FFF"}, 1, "linear fixed", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-538", "EF-OPERATOR PLMN LIST", 28614, new String[]{"x", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-540", "EF-PLMN SELECTOR", 28464, new String[]{"7F20", "x"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-542", "EF-OPLMNAcT", 28513, new String[]{"x", "7FFF"}, 1, "transparent", false));
        this.mSimInfoData.add(new SimInfoData("CDR-PER-544", "EF-IMS PUBLIC USER IDENTITY", 28420, new String[]{"x", "7FFF"}, 3, "linear fixed", false));
    }

    /* access modifiers changed from: private */
    public void loadInCardInfoBackground() {
        new Thread() {
            public void run() {
                SimInfo.this.loadSubId();
                SimInfo.this.loadIccCardType();
                SimInfo.this.mHandler.sendEmptyMessage(1);
                if (SimInfo.this.mIccCardType < 2) {
                    SimInfo.this.loadSimInfoData();
                }
            }
        }.start();
    }

    /* access modifiers changed from: package-private */
    public void registSimReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SIM_STATE_CHANGED);
        context.registerReceiver(this.SimCardChangedReceiver, intentFilter);
    }

    /* access modifiers changed from: package-private */
    public void sendMsg(int MsgID, String MsgInfo) {
        Message msg = new Message();
        msg.what = MsgID;
        msg.obj = MsgInfo;
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: package-private */
    public void loadSimInfoData() {
        String SimInfoData;
        sendMsg(2, "clean");
        int i = 0;
        while (true) {
            List<SimInfoData> list = this.mSimInfoData;
            if (list == null || i >= list.size()) {
                sendMsg(2, "update");
            } else {
                if (this.mSimInfoData.get(i).EF_Path[this.mIccCardType].equals("x")) {
                    sendMsg(2, this.mSimInfoData.get(i).name + "is not support");
                } else {
                    Elog.d(TAG, this.mSimInfoData.get(i).num + "," + this.mSimInfoData.get(i).name);
                    if (this.mSimInfoData.get(i).need_to_check) {
                        int i2 = this.mIccCardType;
                        if (i2 == 1) {
                            Elog.d(TAG, "USIM need to check");
                            MtkSIMRecords mtkSIMRecords = this.mIccRecords;
                            if (!(mtkSIMRecords == null || mtkSIMRecords.getSIMServiceStatus(IccServiceInfo.IccService.OPLMNwACT) == IccServiceInfo.IccServiceStatus.ACTIVATED)) {
                                Elog.d(TAG, "USIM not support EF-OPLMNAcT");
                                sendMsg(2, this.mSimInfoData.get(i).name + "is not support");
                            }
                        } else if (i2 == 0) {
                            Elog.d(TAG, "SIM need to check");
                            MtkSIMRecords mtkSIMRecords2 = this.mIccRecords;
                            if (!(mtkSIMRecords2 == null || mtkSIMRecords2.getSIMServiceStatus(IccServiceInfo.IccService.PLMNsel) == IccServiceInfo.IccServiceStatus.ACTIVATED)) {
                                Elog.d(TAG, "SIM not support EF-PLMN SELECTOR");
                                sendMsg(2, this.mSimInfoData.get(i).name + "is not support");
                            }
                        }
                    }
                    if (this.mSimInfoData.get(i).type.equals("transparent")) {
                        byte[] data = MtkTelephonyManagerEx.getDefault().loadEFTransparent(this.mSimType, this.mSimInfoData.get(i).Family, this.mSimInfoData.get(i).EF_id, this.mSimInfoData.get(i).EF_Path[this.mIccCardType]);
                        if (data != null) {
                            String SimInfoData2 = IccUtils.bytesToHexString(data);
                            SimInfoData = this.mSimInfoData.get(i).name + ":" + SimInfoData2.toUpperCase();
                        } else {
                            SimInfoData = this.mSimInfoData.get(i).name + " is empty";
                        }
                        sendMsg(2, SimInfoData);
                    } else if (this.mSimInfoData.get(i).type.equals("linear fixed")) {
                        List<String> data2 = MtkTelephonyManagerEx.getDefault().loadEFLinearFixedAll(this.mSimType, this.mSimInfoData.get(i).Family, this.mSimInfoData.get(i).EF_id, this.mSimInfoData.get(i).EF_Path[this.mIccCardType]);
                        String SimInfoData3 = "";
                        int j = 0;
                        while (data2 != null && j < data2.size()) {
                            SimInfoData3 = SimInfoData3 + this.mSimInfoData.get(i).name + "[" + j + "]:" + data2.get(j).toUpperCase() + "\n";
                            j++;
                        }
                        if (data2 == null) {
                            SimInfoData3 = this.mSimInfoData.get(i).name + " is empty";
                        }
                        sendMsg(2, SimInfoData3);
                    }
                }
                i++;
            }
        }
        sendMsg(2, "update");
    }

    /* access modifiers changed from: package-private */
    public void loadSubId() {
        int[] subId = SubscriptionManager.getSubId(this.mSimType);
        if (subId != null) {
            for (int i = 0; i < subId.length; i++) {
                Elog.d(TAG, "subId[" + i + "]: " + subId[i]);
            }
        }
        if (subId == null || subId.length == 0 || !SubscriptionManager.isValidSubscriptionId(subId[0])) {
            Elog.e(TAG, "Invalid sub id");
        } else {
            this.mSubId = subId[0];
        }
    }

    /* access modifiers changed from: package-private */
    public void loadIccCardType() {
        String IccCardType = MtkTelephonyManagerEx.getDefault().getIccCardType(this.mSubId);
        if (IccCardType.equals("SIM")) {
            this.mIccCardType = 0;
        } else if (IccCardType.equals("USIM")) {
            this.mIccCardType = 1;
        } else {
            this.mIccCardType = 2;
        }
        Elog.d(TAG, "IccCardType: " + IccCardType + " = " + this.mIccCardType);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        unregisterReceiver(this.SimCardChangedReceiver);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        Elog.d(TAG, "unregisterReceiver SimCardChangedReceiver");
        super.onDestroy();
    }

    private void showToast(String msg) {
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }
}
