package com.mediatek.engineermode.rfdesense;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.radio.V1_4.DataCallFailCause;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaPlayer2;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.Manifest;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RfDesenseService extends Service {
    private final String DEFAULT_VALUES = "[GSM,850,192,5,5][TDSCDMA,BAND34,10087,24,10][WCDMA,BAND1,9612,23,10][LTE(FDD),BAND1,19500,24,10][LTE(TDD),BAND34,20175,24,10][CDMA(1X),BC0,384,83,10][10]";
    private final int STATE_NONE = 0;
    private final int STATE_STARTED = 1;
    private final String TAG = "RfDesense/TxTestService";
    /* access modifiers changed from: private */
    public RfDesenseRatInfo mCurrectRatInfo = null;
    private int mErrorCodeKey = 1000;
    private HashMap<Integer, String> mErrorCodeMapping = new HashMap<>();
    private boolean mIsSending = false;
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onRadioPowerStateChanged(int state) {
            if (state == 1) {
                Elog.v("RfDesense/TxTestService", "RADIO_POWER_ON");
                RfDesenseService.this.mServiceHandler.sendEmptyMessage(6);
                int unused = RfDesenseService.this.mRadioStatesLast = state;
            } else if (state == 0) {
                Elog.v("RfDesense/TxTestService", "RADIO_POWER_OFF");
                if (RfDesenseService.this.mRadioStatesLast == 1) {
                    RfDesenseService.this.mServiceHandler.sendEmptyMessage(9);
                }
                int unused2 = RfDesenseService.this.mRadioStatesLast = state;
            } else if (state == 2) {
                Elog.v("RfDesense/TxTestService", "RADIO_POWER_UNAVAILABLE");
            }
        }
    };
    /* access modifiers changed from: private */
    public int mRadioStatesLast = -1;
    /* access modifiers changed from: private */
    public ArrayList<RfDesenseRatInfo> mRatList = new ArrayList<>();
    private List<RfDesenseServiceData> mRfDesenseServiceData = new ArrayList();
    private HashMap<String, RfDesenseServiceData> mRfdesenseDefaultData = new HashMap<>();
    /* access modifiers changed from: private */
    public final Handler mServiceHandler = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 1:
                    if (ar.exception == null) {
                        Elog.d("RfDesense/TxTestService", "RfService ->start cmd ok");
                        Elog.d("RfDesense/TxTestService", "RfService ->mTestDuration = " + RfDesenseService.this.mTestDuration);
                        RfDesenseService.this.sendResultToClient(2001, "start test");
                        RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 2), 1000);
                        return;
                    }
                    Elog.d("RfDesense/TxTestService", "RfService ->start cmd failed");
                    RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 2), 1000);
                    return;
                case 2:
                    if (RfDesenseService.this.mState == 2) {
                        RfDesenseService.this.stopTx();
                        return;
                    }
                    RfDesenseService.access$514(RfDesenseService.this, 1);
                    Elog.d("RfDesense/TxTestService", "RfService ->mTestDurationSended:" + RfDesenseService.this.mTestDurationSended);
                    if (RfDesenseService.this.mTestDurationSended > RfDesenseService.this.mTestDuration) {
                        long unused = RfDesenseService.this.mTestDurationSended = 0;
                        RfDesenseService.this.txTestStop(4);
                        return;
                    }
                    RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 2), 1000);
                    return;
                case 4:
                    if (ar.exception == null) {
                        Elog.d("RfDesense/TxTestService", "RfService ->stop cmd ok");
                        RfDesenseService rfDesenseService = RfDesenseService.this;
                        RfDesenseRatInfo unused2 = rfDesenseService.mCurrectRatInfo = rfDesenseService.getCurrectRatInfo();
                        if (RfDesenseService.this.mCurrectRatInfo != null) {
                            RfDesenseService.this.rebootModem();
                            return;
                        }
                        Elog.d("RfDesense/TxTestService", "RfService ->send done,mTestCountSended = " + RfDesenseService.this.mTestCountSended);
                        RfDesenseService.access$908(RfDesenseService.this);
                        if (RfDesenseService.this.mTestCountSended < RfDesenseService.this.mTestCount) {
                            for (int i = 0; i < RfDesenseTxTest.mRatName.length; i++) {
                                ((RfDesenseRatInfo) RfDesenseService.this.mRatList.get(i)).setRatSendState(false);
                            }
                            RfDesenseService.this.rebootModem();
                            return;
                        }
                        Elog.d("RfDesense/TxTestService", "RfService ->send all rat done");
                        RfDesenseService.this.sendResultToClient(DataCallFailCause.MIP_FA_REASON_UNSPECIFIED, "");
                        RfDesenseService.this.updateUIView();
                        RfDesenseService.this.stopSelf();
                        return;
                    }
                    Elog.d("RfDesense/TxTestService", "RfService ->stop cmd failed");
                    RfDesenseService.this.updateUIView();
                    return;
                case 6:
                    RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 8), 1000);
                    return;
                case 8:
                    if (RfDesenseService.this.mState == 1) {
                        Elog.d("RfDesense/TxTestService", "RfService ->reboot modem succeed");
                        RfDesenseService rfDesenseService2 = RfDesenseService.this;
                        RfDesenseRatInfo unused3 = rfDesenseService2.mCurrectRatInfo = rfDesenseService2.getCurrectRatInfo();
                        if (RfDesenseService.this.mCurrectRatInfo == null || RfDesenseService.this.mCurrectRatInfo.getRatCmdSwitch().equals("")) {
                            Elog.d("RfDesense/TxTestService", "RfService ->no need switch rat ");
                            Elog.d("RfDesense/TxTestService", "RfService ->entry airplane...");
                            RfDesenseService.this.startAirplane();
                            return;
                        }
                        Elog.d("RfDesense/TxTestService", "RfService ->switch rat(" + RfDesenseService.this.mCurrectRatInfo.getRatCmdSwitch() + ")");
                        RfDesenseService rfDesenseService3 = RfDesenseService.this;
                        rfDesenseService3.sendAtCommand(rfDesenseService3.mCurrectRatInfo.getRatCmdSwitch(), 15);
                        return;
                    }
                    return;
                case 9:
                    RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 16), 2000);
                    return;
                case 12:
                    Elog.d("RfDesense/TxTestService", "RfService ->AT+EWMPOLICY=0 send succeed");
                    Elog.d("RfDesense/TxTestService", "RfService ->send AT+ECSRA=2,0,1,0,1,0 ...");
                    RfDesenseService.this.sendAtCommand("AT+ECSRA=2,0,1,0,1,0", 14);
                    return;
                case 13:
                    Elog.d("RfDesense/TxTestService", "RfService ->AT+EWMPOLICY=0 send succeed");
                    Elog.d("RfDesense/TxTestService", "RfService ->send AT+ECSRA=2,1,0,1,1,0 ...");
                    RfDesenseService.this.sendAtCommand("AT+ECSRA=2,1,0,1,1,0", 14);
                    return;
                case 14:
                    Elog.d("RfDesense/TxTestService", "RfService ->AT+ECSRA send succeed");
                    RfDesenseService.this.startAirplane();
                    return;
                case 15:
                    if (ar.exception == null) {
                        Elog.d("RfDesense/TxTestService", "RfService ->switch rat succeed");
                        if (RfDesenseService.this.mCurrectRatInfo.getRatName().equals(RfDesenseTxTest.mRatName[1])) {
                            Elog.d("RfDesense/TxTestService", "RfService ->send AT+EWMPOLICY=0");
                            RfDesenseService.this.sendAtCommand("AT+EWMPOLICY=0", 12);
                            return;
                        } else if (RfDesenseService.this.mCurrectRatInfo.getRatName().equals(RfDesenseTxTest.mRatName[2])) {
                            Elog.d("RfDesense/TxTestService", "RfService ->send AT+EWMPOLICY=0");
                            RfDesenseService.this.sendAtCommand("AT+EWMPOLICY=0", 13);
                            return;
                        } else {
                            Elog.d("RfDesense/TxTestService", "RfService ->entry airplane...");
                            RfDesenseService.this.startAirplane();
                            return;
                        }
                    } else {
                        Elog.d("RfDesense/TxTestService", "RfService ->switch rat failed");
                        RfDesenseService.this.mServiceHandler.sendMessageDelayed(Message.obtain(RfDesenseService.this.mServiceHandler, 8), 1000);
                        return;
                    }
                case 16:
                    Elog.d("RfDesense/TxTestService", "RfService -> entry airplane succeed...");
                    if (RfDesenseService.this.mCurrectRatInfo != null) {
                        RfDesenseService.this.mCurrectRatInfo.setRatSendState(true);
                        RfDesenseService rfDesenseService4 = RfDesenseService.this;
                        rfDesenseService4.sendAtCommand(rfDesenseService4.mCurrectRatInfo.getRatCmdStart(), 1);
                        Elog.d("RfDesense/TxTestService", "RfService ->send: " + RfDesenseService.this.mCurrectRatInfo.getRatName() + " " + RfDesenseService.this.mCurrectRatInfo.getRatCmdStart());
                        return;
                    }
                    Elog.d("RfDesense/TxTestService", "mCurrectRatInfo == null");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mState = 0;
    private TelephonyManager mTelephonyManager;
    /* access modifiers changed from: private */
    public int mTestCount = 1;
    /* access modifiers changed from: private */
    public int mTestCountSended = 0;
    /* access modifiers changed from: private */
    public long mTestDuration = 0;
    /* access modifiers changed from: private */
    public long mTestDurationSended = 0;
    private Toast mToast = null;

    static /* synthetic */ long access$514(RfDesenseService x0, long x1) {
        long j = x0.mTestDurationSended + x1;
        x0.mTestDurationSended = j;
        return j;
    }

    static /* synthetic */ int access$908(RfDesenseService x0) {
        int i = x0.mTestCountSended;
        x0.mTestCountSended = i + 1;
        return i;
    }

    /* access modifiers changed from: package-private */
    public void initDefaultSuppotData() {
        this.mRfdesenseDefaultData.put("GSM 850", new RfDesenseServiceData(190, 5, 128, 10));
        this.mRfdesenseDefaultData.put("GSM 900", new RfDesenseServiceData(62, 5, 2, 10));
        this.mRfdesenseDefaultData.put("GSM 1800", new RfDesenseServiceData(MediaPlayer2.MEDIA_INFO_VIDEO_TRACK_LAGGING, 0, 8, 10));
        this.mRfdesenseDefaultData.put("GSM 1900", new RfDesenseServiceData(661, 0, 16, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND1", new RfDesenseServiceData(9750, 23, 1, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND2", new RfDesenseServiceData(9400, 23, 2, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND3", new RfDesenseServiceData(1112, 23, 3, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND4", new RfDesenseServiceData(1412, 23, 4, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND5", new RfDesenseServiceData(4182, 23, 5, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND6", new RfDesenseServiceData(4175, 23, 6, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND7", new RfDesenseServiceData(DataCallFailCause.RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE, 23, 7, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND8", new RfDesenseServiceData(2787, 23, 8, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND9", new RfDesenseServiceData(8837, 23, 9, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND10", new RfDesenseServiceData(3025, 23, 10, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND11", new RfDesenseServiceData(3524, 23, 11, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND12", new RfDesenseServiceData(3647, 23, 12, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND13", new RfDesenseServiceData(3805, 23, 13, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND14", new RfDesenseServiceData(3905, 23, 14, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND19", new RfDesenseServiceData(337, 23, 19, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND20", new RfDesenseServiceData(4350, 23, 20, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND21", new RfDesenseServiceData(487, 23, 21, 10));
        this.mRfdesenseDefaultData.put("WCDMA BAND22", new RfDesenseServiceData(4625, 23, 22, 10));
        this.mRfdesenseDefaultData.put("TDSCDMA BAND34", new RfDesenseServiceData(10087, 24, 1, 10));
        this.mRfdesenseDefaultData.put("TDSCDMA BAND39", new RfDesenseServiceData(9500, 24, 6, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND1", new RfDesenseServiceData(19500, 23, 1, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND2", new RfDesenseServiceData(18800, 23, 2, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND3", new RfDesenseServiceData(17475, 23, 3, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND4", new RfDesenseServiceData(17325, 23, 4, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND5", new RfDesenseServiceData(8365, 23, 5, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND6", new RfDesenseServiceData(8300, 23, 46, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND7", new RfDesenseServiceData(25350, 23, 7, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND8", new RfDesenseServiceData(8975, 23, 8, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND9", new RfDesenseServiceData(17674, 23, 9, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND10", new RfDesenseServiceData(17400, 23, 10, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND11", new RfDesenseServiceData(14379, 23, 11, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND12", new RfDesenseServiceData(7075, 23, 12, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND13", new RfDesenseServiceData(7820, 23, 13, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND14", new RfDesenseServiceData(7930, 23, 14, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND17", new RfDesenseServiceData(7100, 23, 17, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND18", new RfDesenseServiceData(8225, 23, 18, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND19", new RfDesenseServiceData(8375, 23, 19, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND20", new RfDesenseServiceData(8470, 23, 20, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND21", new RfDesenseServiceData(14554, 23, 21, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND22", new RfDesenseServiceData(34500, 23, 22, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND23", new RfDesenseServiceData(20100, 23, 23, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND24", new RfDesenseServiceData(16435, 23, 24, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND25", new RfDesenseServiceData(18825, 23, 25, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND26", new RfDesenseServiceData(8315, 23, 26, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND27", new RfDesenseServiceData(8155, 23, 27, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND28", new RfDesenseServiceData(7255, 23, 28, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND30", new RfDesenseServiceData(23100, 23, 30, 10));
        this.mRfdesenseDefaultData.put("LTE(FDD) BAND31", new RfDesenseServiceData(4550, 23, 31, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND33", new RfDesenseServiceData(19100, 23, 33, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND34", new RfDesenseServiceData(20175, 23, 34, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND35", new RfDesenseServiceData(18800, 23, 35, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND36", new RfDesenseServiceData(19600, 23, 36, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND37", new RfDesenseServiceData(19200, 23, 37, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND38", new RfDesenseServiceData(25950, 23, 38, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND39", new RfDesenseServiceData(19000, 23, 39, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND40", new RfDesenseServiceData(23500, 23, 40, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND41", new RfDesenseServiceData(25930, 23, 41, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND42", new RfDesenseServiceData(35000, 23, 42, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND43", new RfDesenseServiceData(37000, 23, 43, 10));
        this.mRfdesenseDefaultData.put("LTE(TDD) BAND44", new RfDesenseServiceData(7530, 23, 44, 10));
        this.mRfdesenseDefaultData.put("CDMA(1X) BC0", new RfDesenseServiceData(RfDesenseTxTestCdma.DEFAULT_CHANNEL_VALUE, 83, 0, 10));
        this.mErrorCodeMapping.put(1000, "argument parse pass and start tx");
        this.mErrorCodeMapping.put(1001, "[rat,");
        this.mErrorCodeMapping.put(1002, "service is tx ing,please try again later");
        this.mErrorCodeMapping.put(1003, "service works well");
        this.mErrorCodeMapping.put(Integer.valueOf(DataCallFailCause.MIP_FA_REASON_UNSPECIFIED), "test finished");
        this.mErrorCodeMapping.put(2001, "test start ret");
        this.mErrorCodeMapping.put(Integer.valueOf(DataCallFailCause.MIP_FA_INSUFFICIENT_RESOURCES), "test failed");
        this.mErrorCodeMapping.put(Integer.valueOf(DataCallFailCause.MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE), "test stoped");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void parseIntentData(String arguments) {
        int i;
        String[] rat = arguments.split("\\[|\\]");
        int i2 = 1001;
        if (rat.length == 0) {
            sendResultToClient(1001, "null]");
            return;
        }
        int i3 = 0;
        while (i3 < rat.length) {
            if (!rat[i3].isEmpty()) {
                Elog.d("RfDesense/TxTestService", "RfService ->rat = " + rat[i3]);
                RfDesenseServiceData rfreceiveData = new RfDesenseServiceData();
                String[] info = rat[i3].split(",");
                if (info.length == 1) {
                    try {
                        int count = Integer.valueOf(info[0]).intValue();
                        if (count == -1) {
                            this.mTestCount = 1;
                        } else {
                            this.mTestCount = count;
                        }
                        i = i2;
                    } catch (Exception e) {
                        if (info[0].equals("TEST")) {
                            sendResultToClient(1003, "");
                            return;
                        }
                        sendResultToClient(i2, info[0] + "]");
                        return;
                    }
                } else if (info.length < 5) {
                    sendResultToClient(i2, info[0] + "]");
                    return;
                } else {
                    rfreceiveData.setRat(info[0]);
                    RfDesenseServiceData mDefaultRfData = this.mRfdesenseDefaultData.get(info[0] + " " + info[1]);
                    if (mDefaultRfData != null) {
                        rfreceiveData.setBand(mDefaultRfData.getBand());
                        try {
                            int channel = Integer.valueOf(info[2]).intValue();
                            if (channel == -1) {
                                rfreceiveData.setChannel(mDefaultRfData.getChannel());
                            } else {
                                rfreceiveData.setChannel(channel);
                            }
                            int power = Integer.valueOf(info[3]).intValue();
                            if (power == -1) {
                                rfreceiveData.setPower(mDefaultRfData.getPower());
                            } else {
                                rfreceiveData.setPower(power);
                            }
                            int time = Integer.valueOf(info[4]).intValue();
                            if (time == -1) {
                                rfreceiveData.setTime(mDefaultRfData.getTime());
                            } else {
                                rfreceiveData.setTime(time);
                            }
                            if (info.length > 5) {
                                rfreceiveData.setBw(Integer.valueOf(info[5]).intValue());
                            }
                            if (info.length > 6) {
                                rfreceiveData.setRb(Integer.valueOf(info[6]).intValue());
                            }
                            if (info.length > 7 || (info.length > 5 && !info[0].equals(RfDesenseTxTest.mRatName[3]) && !info[0].equals(RfDesenseTxTest.mRatName[4]))) {
                                sendResultToClient(1001, info[0] + "]");
                                return;
                            }
                            this.mRfDesenseServiceData.add(rfreceiveData);
                            i = 1001;
                        } catch (Exception e2) {
                            sendResultToClient(1001, info[0] + "]");
                            return;
                        }
                    } else {
                        sendResultToClient(i2, info[0] + "]");
                        return;
                    }
                }
            } else {
                i = i2;
            }
            i3++;
            i2 = i;
        }
    }

    private void initRatList() {
        for (int i = 0; i < RfDesenseTxTest.mRatName.length; i++) {
            RfDesenseRatInfo mRatInfo = new RfDesenseRatInfo();
            mRatInfo.setRatName(RfDesenseTxTest.mRatName[i]);
            mRatInfo.setRatCmdStart(RfDesenseTxTest.mRatCmdStart[i]);
            mRatInfo.setRatCmdStop(RfDesenseTxTest.mRatCmdStop[i]);
            mRatInfo.setRatPowerRead(RfDesenseTxTest.mRatCmdPowerRead[i]);
            mRatInfo.setRatCheckState(false);
            mRatInfo.setRatSendState(false);
            this.mRatList.add(mRatInfo);
        }
    }

    private void updateRatList() {
        for (int i = 0; i < this.mRfDesenseServiceData.size(); i++) {
            int k = 0;
            while (true) {
                if (k >= RfDesenseTxTest.mRatName.length) {
                    break;
                } else if (RfDesenseTxTest.mRatName[k].equals(this.mRfDesenseServiceData.get(i).getRat())) {
                    this.mRatList.get(k).setRatCmdLteBwRb(this.mRfDesenseServiceData.get(i).getBw(), this.mRfDesenseServiceData.get(i).getRb());
                    this.mRatList.get(k).setRatCmdStart(RfDesenseTxTest.mRatName[k], this.mRfDesenseServiceData.get(i).getChannel(), this.mRfDesenseServiceData.get(i).getPower(), this.mRfDesenseServiceData.get(i).getBand());
                    this.mRatList.get(k).setRatTxtimes(this.mRfDesenseServiceData.get(i).getTime());
                    Elog.d("RfDesense/TxTestService", "RfService ->mRatInfo send = " + RfDesenseTxTest.mRatName[k] + "," + this.mRatList.get(k).getRatCmdStart() + ",time = " + this.mRatList.get(k).getRatTxtimes());
                    this.mRatList.get(k).setRatCheckState(true);
                    break;
                } else {
                    k++;
                }
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        Elog.d("RfDesense/TxTestService", "RfService -> onCreate()");
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        telephonyManager.listen(this.mPhoneStateListener, 8388608);
        startForeground(1, getNotification("Rfdesense service started...", 0));
    }

    public void onDestroy() {
        Elog.d("RfDesense/TxTestService", "RfService -> onDestroy");
        stopForeground(true);
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        this.mState = 2;
        if (this.mIsSending) {
            sendResultToClient(DataCallFailCause.MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE, "");
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Elog.d("RfDesense/TxTestService", "RfService -> onStartCommand()");
        if (this.mIsSending) {
            sendResultToClient(1002, "");
            return 2;
        }
        String arguments = null;
        if (intent != null) {
            arguments = intent.getStringExtra(RfDesenseBroadcastReceiver.ARGUMENTS);
        }
        if (arguments == null) {
            arguments = "[GSM,850,192,5,5][TDSCDMA,BAND34,10087,24,10][WCDMA,BAND1,9612,23,10][LTE(FDD),BAND1,19500,24,10][LTE(TDD),BAND34,20175,24,10][CDMA(1X),BC0,384,83,10][10]";
            Elog.d("RfDesense/TxTestService", "RfService -> use default arguments");
        }
        String arguments2 = arguments.replaceAll(" ", "");
        Elog.d("RfDesense/TxTestService", "RfService -> arguments: " + arguments2);
        this.mRatList.clear();
        this.mRfDesenseServiceData.clear();
        this.mErrorCodeKey = 1000;
        initDefaultSuppotData();
        initRatList();
        parseIntentData(arguments2);
        if (this.mErrorCodeKey == 1000) {
            updateRatList();
            sendResultToClient(1000, "");
            startTx();
        }
        return 2;
    }

    /* access modifiers changed from: package-private */
    public void sendResultToClient(int errorCodeKey, String msg) {
        String info;
        String rat = "";
        this.mErrorCodeKey = errorCodeKey;
        RfDesenseRatInfo rfDesenseRatInfo = this.mCurrectRatInfo;
        if (rfDesenseRatInfo != null) {
            rat = rfDesenseRatInfo.getRatName();
        }
        if (errorCodeKey == 2002 || errorCodeKey == 2001) {
            info = "[count," + this.mTestCountSended + "][rat," + rat + "]";
        } else {
            info = this.mErrorCodeMapping.get(Integer.valueOf(errorCodeKey)) + msg;
        }
        Intent intent = new Intent();
        intent.setAction("com.mediatek.engineermode.rfdesenseServiceResult");
        intent.putExtra("result_id", errorCodeKey);
        intent.putExtra("result_info", info);
        sendBroadcast(intent, Manifest.permission.permission);
        Elog.d("RfDesense/TxTestService", "RfService ->send " + errorCodeKey + " : " + this.mErrorCodeMapping.get(Integer.valueOf(errorCodeKey)) + info);
    }

    /* access modifiers changed from: package-private */
    public void stopTx() {
        this.mIsSending = false;
        this.mServiceHandler.removeCallbacksAndMessages((Object) null);
        rebootModem();
        super.onDestroy();
    }

    /* access modifiers changed from: package-private */
    public void startTx() {
        RfDesenseRatInfo currectRatInfo = getCurrectRatInfo();
        this.mCurrectRatInfo = currectRatInfo;
        if (currectRatInfo != null) {
            Elog.d("RfDesense/TxTestService", "RfService ->mCurrectRatInfo = " + this.mCurrectRatInfo.getRatCmdStart());
            this.mIsSending = true;
            this.mTestCountSended = 0;
            this.mTestDurationSended = 0;
            this.mState = 1;
            Elog.d("RfDesense/TxTestService", "RfService ->mTestCount = " + this.mTestCount);
            rebootModem();
            return;
        }
        Elog.d("RfDesense/TxTestService", "RfService ->you must set at least one rat to tx ");
    }

    /* access modifiers changed from: private */
    public RfDesenseRatInfo getCurrectRatInfo() {
        this.mCurrectRatInfo = null;
        int index = 0;
        while (true) {
            if (index < this.mRatList.size()) {
                if (this.mRatList.get(index).getRatCheckState().booleanValue() && !this.mRatList.get(index).getRatSendState().booleanValue()) {
                    RfDesenseRatInfo rfDesenseRatInfo = this.mRatList.get(index);
                    this.mCurrectRatInfo = rfDesenseRatInfo;
                    this.mTestDuration = (long) (rfDesenseRatInfo.getRatTxtimes() * 60);
                    break;
                }
                index++;
            } else {
                break;
            }
        }
        return this.mCurrectRatInfo;
    }

    /* access modifiers changed from: private */
    public void updateUIView() {
        this.mIsSending = false;
        for (int i = 0; i < RfDesenseTxTest.mRatName.length; i++) {
            this.mRatList.get(i).setRatSendState(false);
        }
    }

    /* access modifiers changed from: package-private */
    public void startAirplane() {
        Elog.d("RfDesense/TxTestService", "start entry Airplane...");
        EmUtils.setAirplaneModeEnabled(true);
    }

    /* access modifiers changed from: private */
    public void rebootModem() {
        EmUtils.rebootModem();
        EmUtils.setAirplaneModeEnabled(false);
    }

    /* access modifiers changed from: private */
    public void txTestStop(int what) {
        RfDesenseRatInfo rfDesenseRatInfo = this.mCurrectRatInfo;
        if (rfDesenseRatInfo != null) {
            sendAtCommand(rfDesenseRatInfo.getRatCmdStop(), what);
            Elog.d("RfDesense/TxTestService", "RfService ->stop: " + this.mCurrectRatInfo.getRatName() + " " + this.mCurrectRatInfo.getRatCmdStop());
            return;
        }
        Elog.d("RfDesense/TxTestService", "RfService ->mCurrectRatInfo is null");
        updateUIView();
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String str, int what) {
        EmUtils.invokeOemRilRequestStringsEm(new String[]{str, ""}, this.mServiceHandler.obtainMessage(what));
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService("notification");
    }

    private Notification getNotification(String title, int progress) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, RfDesenseTxTest.class), 67108864);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, EmApplication.getSilentNotificationChannelID());
        builder.setSmallIcon(R.drawable.cross);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cross));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        return builder.build();
    }
}
