package com.mediatek.engineermode.aospradio;

import android.hardware.radio.V1_0.CdmaCallWaiting;
import android.hardware.radio.V1_0.CdmaInformationRecords;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IRadioIndication;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.PcoDataInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SignalStrength;
import android.hardware.radio.V1_0.SimRefreshResult;
import android.hardware.radio.V1_0.StkCcUnsolSsResult;
import android.hardware.radio.V1_0.SuppSvcNotification;
import android.os.AsyncResult;
import android.os.Message;
import com.android.internal.telephony.RIL;
import com.mediatek.engineermode.Elog;
import java.util.ArrayList;
import java.util.List;

public class RadioIndication extends IRadioIndication.Stub {
    public static final String TAG = "AospRadioIndication";
    public static int stateLast = 2;
    private String[] radioStatus = {"off", "on", "power downm"};

    public RadioIndication(RIL ril) {
    }

    public static void resetStateLast() {
        stateLast = 2;
    }

    public static String[] radioStateToPrimitiveArrayString(List<String> info) {
        String[] ret = new String[info.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = info.get(i);
        }
        return ret;
    }

    /* access modifiers changed from: package-private */
    public int getRadioStateFromInt(int stateInt) {
        switch (stateInt) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 10:
                return 1;
            default:
                throw new RuntimeException("Unrecognized RadioState: " + stateInt);
        }
    }

    private void sendMessageResponse(Message msg, Object ret) {
        try {
            AsyncResult.forMessage(msg, ret, (Throwable) null);
            msg.sendToTarget();
        } catch (Exception e) {
            Elog.e(TAG, "sendMessageResponse: " + e.getMessage());
        }
    }

    public void radioStateChanged(int indicationType, int radioState) {
        int state = getRadioStateFromInt(radioState);
        Elog.v(TAG, "radioStateChanged,state = " + state + ":" + this.radioStatus[state]);
        int[] response = new int[2];
        response[0] = state;
        if (EmRadioHidlAosp.mRadioIndicationType == 3) {
            Message msg = EmRadioHidlAosp.mHandler.obtainMessage(EmRadioHidlAosp.mWhat);
            if (state != stateLast) {
                sendMessageResponse(msg, response);
            }
            stateLast = state;
            return;
        }
        Elog.v(TAG, "radioStateChanged not send to app");
    }

    public void callStateChanged(int indicationType) {
    }

    public void networkStateChanged(int indicationType) {
    }

    public void newSms(int indicationType, ArrayList<Byte> arrayList) {
    }

    public void newSmsStatusReport(int indicationType, ArrayList<Byte> arrayList) {
    }

    public void newSmsOnSim(int indicationType, int recordNumber) {
    }

    public void onUssd(int indicationType, int ussdModeType, String msg) {
    }

    public void nitzTimeReceived(int indicationType, String nitzTime, long receivedTime) {
    }

    public void currentSignalStrength(int indicationType, SignalStrength signalStrength) {
    }

    public void dataCallListChanged(int indicationType, ArrayList<SetupDataCallResult> arrayList) {
    }

    public void suppSvcNotify(int indicationType, SuppSvcNotification suppSvcNotification) {
    }

    public void stkSessionEnd(int indicationType) {
    }

    public void stkProactiveCommand(int indicationType, String cmd) {
    }

    public void stkEventNotify(int indicationType, String cmd) {
    }

    public void stkCallSetup(int indicationType, long timeout) {
    }

    public void simSmsStorageFull(int indicationType) {
    }

    public void simRefresh(int indicationType, SimRefreshResult refreshResult) {
    }

    public void callRing(int indicationType, boolean isGsm, CdmaSignalInfoRecord record) {
    }

    public void simStatusChanged(int indicationType) {
    }

    public void cdmaNewSms(int indicationType, CdmaSmsMessage msg) {
    }

    public void newBroadcastSms(int indicationType, ArrayList<Byte> arrayList) {
    }

    public void cdmaRuimSmsStorageFull(int indicationType) {
    }

    public void restrictedStateChanged(int indicationType, int state) {
    }

    public void enterEmergencyCallbackMode(int indicationType) {
    }

    public void cdmaCallWaiting(int indicationType, CdmaCallWaiting callWaitingRecord) {
    }

    public void cdmaOtaProvisionStatus(int indicationType, int status) {
    }

    public void cdmaInfoRec(int indicationType, CdmaInformationRecords records) {
    }

    public void indicateRingbackTone(int indicationType, boolean start) {
    }

    public void resendIncallMute(int indicationType) {
    }

    public void cdmaSubscriptionSourceChanged(int indicationType, int cdmaSource) {
    }

    public void cdmaPrlChanged(int indicationType, int version) {
    }

    public void exitEmergencyCallbackMode(int indicationType) {
    }

    public void rilConnected(int indicationType) {
    }

    public void voiceRadioTechChanged(int indicationType, int rat) {
    }

    public void cellInfoList(int indicationType, ArrayList<CellInfo> arrayList) {
    }

    public void imsNetworkStateChanged(int indicationType) {
    }

    public void subscriptionStatusChanged(int indicationType, boolean activate) {
    }

    public void srvccStateNotify(int indicationType, int state) {
    }

    public void hardwareConfigChanged(int indicationType, ArrayList<HardwareConfig> arrayList) {
    }

    public void radioCapabilityIndication(int indicationType, RadioCapability rc) {
    }

    public void onSupplementaryServiceIndication(int indicationType, StkCcUnsolSsResult ss) {
    }

    public void stkCallControlAlphaNotify(int indicationType, String alpha) {
    }

    public void lceData(int indicationType, LceDataInfo lce) {
    }

    public void pcoData(int indicationType, PcoDataInfo pco) {
    }

    public void modemReset(int indicationType, String reason) {
    }
}
