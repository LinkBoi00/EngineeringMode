package android.hardware.radio.V1_0;

import android.hardware.radio.V1_4.DataCallFailCause;
import android.hardware.radio.V1_5.EutranBands;
import android.hardware.radio.V1_5.NgranBands;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IRadio extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadio";

    void acceptCall(int i) throws RemoteException;

    void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException;

    void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException;

    void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException;

    IHwBinder asBinder();

    void cancelPendingUssd(int i) throws RemoteException;

    void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException;

    void conference(int i) throws RemoteException;

    void deactivateDataCall(int i, int i2, boolean z) throws RemoteException;

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteSmsOnRuim(int i, int i2) throws RemoteException;

    void deleteSmsOnSim(int i, int i2) throws RemoteException;

    void dial(int i, Dial dial) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    void explicitCallTransfer(int i) throws RemoteException;

    void getAllowedCarriers(int i) throws RemoteException;

    void getAvailableBandModes(int i) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getBasebandVersion(int i) throws RemoteException;

    void getCDMASubscription(int i) throws RemoteException;

    void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void getCallWaiting(int i, int i2) throws RemoteException;

    void getCdmaBroadcastConfig(int i) throws RemoteException;

    void getCdmaRoamingPreference(int i) throws RemoteException;

    void getCdmaSubscriptionSource(int i) throws RemoteException;

    void getCellInfoList(int i) throws RemoteException;

    void getClip(int i) throws RemoteException;

    void getClir(int i) throws RemoteException;

    void getCurrentCalls(int i) throws RemoteException;

    void getDataCallList(int i) throws RemoteException;

    void getDataRegistrationState(int i) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    void getDeviceIdentity(int i) throws RemoteException;

    void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException;

    void getGsmBroadcastConfig(int i) throws RemoteException;

    void getHardwareConfig(int i) throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    void getImsRegistrationState(int i) throws RemoteException;

    void getImsiForApp(int i, String str) throws RemoteException;

    void getLastCallFailCause(int i) throws RemoteException;

    void getModemActivityInfo(int i) throws RemoteException;

    void getMute(int i) throws RemoteException;

    void getNeighboringCids(int i) throws RemoteException;

    void getNetworkSelectionMode(int i) throws RemoteException;

    void getOperator(int i) throws RemoteException;

    void getPreferredNetworkType(int i) throws RemoteException;

    void getPreferredVoicePrivacy(int i) throws RemoteException;

    void getRadioCapability(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getSmscAddress(int i) throws RemoteException;

    void getTTYMode(int i) throws RemoteException;

    void getVoiceRadioTechnology(int i) throws RemoteException;

    void getVoiceRegistrationState(int i) throws RemoteException;

    void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException;

    void hangup(int i, int i2) throws RemoteException;

    void hangupForegroundResumeBackground(int i) throws RemoteException;

    void hangupWaitingOrBackground(int i) throws RemoteException;

    void iccCloseLogicalChannel(int i, int i2) throws RemoteException;

    void iccIOForApp(int i, IccIo iccIo) throws RemoteException;

    void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException;

    void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException;

    void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void nvReadItem(int i, int i2) throws RemoteException;

    void nvResetConfig(int i, int i2) throws RemoteException;

    void nvWriteCdmaPrl(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException;

    void ping() throws RemoteException;

    void pullLceData(int i) throws RemoteException;

    void rejectCall(int i) throws RemoteException;

    void reportSmsMemoryStatus(int i, boolean z) throws RemoteException;

    void reportStkServiceIsRunning(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException;

    void requestIsimAuthentication(int i, String str) throws RemoteException;

    void requestShutdown(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException;

    void sendCDMAFeatureCode(int i, String str) throws RemoteException;

    void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendDeviceState(int i, int i2, boolean z) throws RemoteException;

    void sendDtmf(int i, String str) throws RemoteException;

    void sendEnvelope(int i, String str) throws RemoteException;

    void sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException;

    void sendSMSExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendTerminalResponseToSim(int i, String str) throws RemoteException;

    void sendUssd(int i, String str) throws RemoteException;

    void separateConnection(int i, int i2) throws RemoteException;

    void setAllowedCarriers(int i, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException;

    void setBandMode(int i, int i2) throws RemoteException;

    void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException;

    void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void setCallWaiting(int i, boolean z, int i2) throws RemoteException;

    void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException;

    void setCdmaBroadcastConfig(int i, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void setCdmaRoamingPreference(int i, int i2) throws RemoteException;

    void setCdmaSubscriptionSource(int i, int i2) throws RemoteException;

    void setCellInfoListRate(int i, int i2) throws RemoteException;

    void setClir(int i, int i2) throws RemoteException;

    void setDataAllowed(int i, boolean z) throws RemoteException;

    void setDataProfile(int i, ArrayList<DataProfileInfo> arrayList, boolean z) throws RemoteException;

    void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException;

    void setGsmBroadcastActivation(int i, boolean z) throws RemoteException;

    void setGsmBroadcastConfig(int i, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilter(int i, int i2) throws RemoteException;

    void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo, boolean z, boolean z2) throws RemoteException;

    void setLocationUpdates(int i, boolean z) throws RemoteException;

    void setMute(int i, boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    void setNetworkSelectionModeManual(int i, String str) throws RemoteException;

    void setPreferredNetworkType(int i, int i2) throws RemoteException;

    void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException;

    void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException;

    void setRadioPower(int i, boolean z) throws RemoteException;

    void setResponseFunctions(IRadioResponse iRadioResponse, IRadioIndication iRadioIndication) throws RemoteException;

    void setSimCardPower(int i, boolean z) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    void setSuppServiceNotifications(int i, boolean z) throws RemoteException;

    void setTTYMode(int i, int i2) throws RemoteException;

    void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void startLceService(int i, int i2, boolean z) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    void stopLceService(int i) throws RemoteException;

    void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPinForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str) throws RemoteException;

    void switchWaitingOrHoldingAndActive(int i) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException;

    void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException;

    static IRadio asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IRadio)) {
            return (IRadio) iface;
        }
        IRadio proxy = new Proxy(binder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static IRadio castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IRadio getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IRadio getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IRadio getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IRadio getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadio {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            Objects.requireNonNull(remote);
            this.mRemote = remote;
        }

        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of android.hardware.radio@1.0::IRadio]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void setResponseFunctions(IRadioResponse radioResponse, IRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccCardStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPinForApp(int serial, String pin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPukForApp(int serial, String puk, String pin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPin2ForApp(int serial, String pin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPuk2ForApp(int serial, String puk2, String pin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk2);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPinForApp(int serial, String oldPin, String newPin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin);
            _hidl_request.writeString(newPin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPin2ForApp(int serial, String oldPin2, String newPin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin2);
            _hidl_request.writeString(newPin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyNetworkDepersonalization(int serial, String netPin) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(netPin);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentCalls(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dial(int serial, Dial dialInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsiForApp(int serial, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangup(int serial, int gsmIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWaitingOrBackground(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupForegroundResumeBackground(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void switchWaitingOrHoldingAndActive(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void conference(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rejectCall(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLastCallFailCause(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrength(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getOperator(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPower(int serial, boolean on) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(on);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDtmf(int serial, String s) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSms(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSMSExpectMore(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCall(int serial, int radioTechnology, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean roamingAllowed, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(radioTechnology);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccIOForApp(int serial, IccIo iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendUssd(int serial, String ussd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ussd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelPendingUssd(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClir(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClir(int serial, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallForwardStatus(int serial, CallForwardInfo callInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForward(int serial, CallForwardInfo callInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallWaiting(int serial, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallWaiting(int serial, boolean enable, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingGsmSms(int serial, boolean success, int cause) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeInt32(cause);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acceptCall(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateDataCall(int serial, int cid, boolean reasonRadioShutDown) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cid);
            _hidl_request.writeBool(reasonRadioShutDown);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFacilityLockForApp(int serial, String facility, String password, int serviceClass, String appId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFacilityLockForApp(int serial, String facility, boolean lockState, String password, int serviceClass, String appId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeBool(lockState);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBarringPassword(int serial, String facility, String oldPassword, String newPassword) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(oldPassword);
            _hidl_request.writeString(newPassword);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNetworkSelectionMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeAutomatic(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManual(int serial, String operatorNumeric) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworks(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startDtmf(int serial, String s) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopDtmf(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBasebandVersion(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void separateConnection(int serial, int gsmIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMute(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getMute(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClip(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServiceNotifications(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToSim(int serial, SmsWriteArgs smsWriteArgs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsWriteArgs.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnSim(int serial, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBandMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableBandModes(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelope(int serial, String command) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(command);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendTerminalResponseToSim(int serial, String commandResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(commandResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSim(int serial, boolean accept) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(accept);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void explicitCallTransfer(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkType(int serial, int nwType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(nwType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkType(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNeighboringCids(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLocationUpdates(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaSubscriptionSource(int serial, int cdmaSub) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cdmaSub);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaRoamingPreference(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaRoamingPreference(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTTYMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTTYMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredVoicePrivacy(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredVoicePrivacy(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCDMAFeatureCode(int serial, String featureCode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(featureCode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendBurstDtmf(int serial, String dtmf, int on, int off) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(dtmf);
            _hidl_request.writeInt32(on);
            _hidl_request.writeInt32(off);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSms(int serial, CdmaSmsMessage sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingCdmaSms(int serial, CdmaSmsAck smsAck) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsAck.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastConfig(int serial, ArrayList<GsmBroadcastSmsConfigInfo> configInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastActivation(int serial, boolean activate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaBroadcastConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastConfig(int serial, ArrayList<CdmaBroadcastSmsConfigInfo> configInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastActivation(int serial, boolean activate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCDMASubscription(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToRuim(int serial, CdmaSmsWriteArgs cdmaSms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            cdmaSms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnRuim(int serial, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeviceIdentity(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void exitEmergencyCallbackMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmscAddress(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmscAddress(int serial, String smsc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(smsc);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportSmsMemoryStatus(int serial, boolean available) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(available);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportStkServiceIsRunning(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaSubscriptionSource(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIsimAuthentication(int serial, String challenge) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(challenge);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeIncomingGsmSmsWithPdu(int serial, boolean success, String ackPdu) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeString(ackPdu);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeWithStatus(int serial, String contents) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(contents);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRadioTechnology(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCellInfoListRate(int serial, int rate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApn(int serial, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(103, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendImsSms(int serial, ImsSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduBasicChannel(int serial, SimApdu message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccOpenLogicalChannel(int serial, String aid, int p2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            _hidl_request.writeInt32(p2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccCloseLogicalChannel(int serial, int channelId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(channelId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduLogicalChannel(int serial, SimApdu message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvReadItem(int serial, int itemId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(itemId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteItem(int serial, NvWriteItem item) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            item.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteCdmaPrl(int serial, ArrayList<Byte> prl) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(prl);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvResetConfig(int serial, int resetType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(resetType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(112, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setUiccSubscription(int serial, SelectUiccSub uiccSub) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            uiccSub.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(113, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataAllowed(int serial, boolean allow) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allow);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(114, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getHardwareConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(115, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIccSimAuthentication(int serial, int authContext, String authData, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(authContext);
            _hidl_request.writeString(authData);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(116, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfile(int serial, ArrayList<DataProfileInfo> profiles, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            DataProfileInfo.writeVectorToParcel(_hidl_request, profiles);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(117, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestShutdown(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(118, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRadioCapability(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(119, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioCapability(int serial, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(120, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startLceService(int serial, int reportInterval, boolean pullMode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(reportInterval);
            _hidl_request.writeBool(pullMode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(121, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopLceService(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(122, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pullLceData(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_DNS_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemActivityInfo(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriers(int serial, boolean allAllowed, CarrierRestrictions carriers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allAllowed);
            carriers.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(125, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllowedCarriers(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDeviceState(int serial, int deviceStateType, boolean state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(deviceStateType);
            _hidl_request.writeBool(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(127, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilter(int serial, int indicationFilter) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(indicationFilter);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPower(int serial, boolean powerUp) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(powerUp);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void responseAcknowledgement() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readStringVector();
            } finally {
                _hidl_reply.release();
            }
        }

        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readString();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16);
                int _hidl_vec_size = _hidl_blob.getInt32(8);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer((long) (_hidl_vec_size * 32), _hidl_blob.handle(), 0, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    childBlob.copyToInt8Array((long) (_hidl_index_0 * 32), _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadio {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IRadio.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{-49, -86, -80, -28, 92, 93, 123, 53, -107, 3, 45, 100, -99, -94, -98, -41, 18, -23, 32, -7, 86, -63, 54, 113, -17, -45, 86, 2, -6, -127, -55, 35}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
        }

        public final void setHALInstrumentation() {
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        public final void ping() {
        }

        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0;
            info.arch = 0;
            return info;
        }

        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public IHwInterface queryLocalInterface(String descriptor) {
            if (IRadio.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public void onTransact(int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags) throws RemoteException {
            HwParcel hwParcel = _hidl_request;
            HwParcel hwParcel2 = _hidl_reply;
            switch (_hidl_code) {
                case 1:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setResponseFunctions(IRadioResponse.asInterface(_hidl_request.readStrongBinder()), IRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getIccCardStatus(_hidl_request.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPinForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 4:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPukForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 5:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPin2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 6:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPuk2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 7:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    changeIccPinForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 8:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    changeIccPin2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 9:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyNetworkDepersonalization(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 10:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCurrentCalls(_hidl_request.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial = _hidl_request.readInt32();
                    Dial dialInfo = new Dial();
                    dialInfo.readFromParcel(hwParcel);
                    dial(serial, dialInfo);
                    return;
                case 12:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getImsiForApp(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 13:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangup(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangupWaitingOrBackground(_hidl_request.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangupForegroundResumeBackground(_hidl_request.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    switchWaitingOrHoldingAndActive(_hidl_request.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    conference(_hidl_request.readInt32());
                    return;
                case 18:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    rejectCall(_hidl_request.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getLastCallFailCause(_hidl_request.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSignalStrength(_hidl_request.readInt32());
                    return;
                case 21:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRegistrationState(_hidl_request.readInt32());
                    return;
                case 22:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataRegistrationState(_hidl_request.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getOperator(_hidl_request.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setRadioPower(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 25:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendDtmf(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 26:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial2 = _hidl_request.readInt32();
                    GsmSmsMessage message = new GsmSmsMessage();
                    message.readFromParcel(hwParcel);
                    sendSms(serial2, message);
                    return;
                case 27:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial3 = _hidl_request.readInt32();
                    GsmSmsMessage message2 = new GsmSmsMessage();
                    message2.readFromParcel(hwParcel);
                    sendSMSExpectMore(serial3, message2);
                    return;
                case 28:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial4 = _hidl_request.readInt32();
                    int radioTechnology = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo = new DataProfileInfo();
                    dataProfileInfo.readFromParcel(hwParcel);
                    setupDataCall(serial4, radioTechnology, dataProfileInfo, _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 29:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial5 = _hidl_request.readInt32();
                    IccIo iccIo = new IccIo();
                    iccIo.readFromParcel(hwParcel);
                    iccIOForApp(serial5, iccIo);
                    return;
                case 30:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendUssd(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 31:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    cancelPendingUssd(_hidl_request.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getClir(_hidl_request.readInt32());
                    return;
                case 33:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setClir(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial6 = _hidl_request.readInt32();
                    CallForwardInfo callInfo = new CallForwardInfo();
                    callInfo.readFromParcel(hwParcel);
                    getCallForwardStatus(serial6, callInfo);
                    return;
                case 35:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial7 = _hidl_request.readInt32();
                    CallForwardInfo callInfo2 = new CallForwardInfo();
                    callInfo2.readFromParcel(hwParcel);
                    setCallForward(serial7, callInfo2);
                    return;
                case 36:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCallWaiting(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCallWaiting(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 38:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acknowledgeLastIncomingGsmSms(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 39:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acceptCall(_hidl_request.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deactivateDataCall(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 41:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getFacilityLockForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 42:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setFacilityLockForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readBool(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setBarringPassword(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 44:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getNetworkSelectionMode(_hidl_request.readInt32());
                    return;
                case 45:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNetworkSelectionModeAutomatic(_hidl_request.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNetworkSelectionModeManual(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 47:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAvailableNetworks(_hidl_request.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    startDtmf(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 49:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    stopDtmf(_hidl_request.readInt32());
                    return;
                case 50:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getBasebandVersion(_hidl_request.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    separateConnection(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 52:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setMute(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getMute(_hidl_request.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getClip(_hidl_request.readInt32());
                    return;
                case 55:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataCallList(_hidl_request.readInt32());
                    return;
                case 56:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSuppServiceNotifications(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 57:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial8 = _hidl_request.readInt32();
                    SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
                    smsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToSim(serial8, smsWriteArgs);
                    return;
                case 58:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deleteSmsOnSim(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 59:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setBandMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 60:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAvailableBandModes(_hidl_request.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendEnvelope(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 62:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendTerminalResponseToSim(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 63:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    handleStkCallSetupRequestFromSim(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 64:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    explicitCallTransfer(_hidl_request.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setPreferredNetworkType(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getPreferredNetworkType(_hidl_request.readInt32());
                    return;
                case 67:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getNeighboringCids(_hidl_request.readInt32());
                    return;
                case 68:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setLocationUpdates(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case LastCallFailCause.REQUESTED_FACILITY_NOT_IMPLEMENTED:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaSubscriptionSource(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 70:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaRoamingPreference(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaRoamingPreference(_hidl_request.readInt32());
                    return;
                case 72:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setTTYMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getTTYMode(_hidl_request.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setPreferredVoicePrivacy(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getPreferredVoicePrivacy(_hidl_request.readInt32());
                    return;
                case 76:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendCDMAFeatureCode(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 77:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendBurstDtmf(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 78:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial9 = _hidl_request.readInt32();
                    CdmaSmsMessage sms = new CdmaSmsMessage();
                    sms.readFromParcel(hwParcel);
                    sendCdmaSms(serial9, sms);
                    return;
                case 79:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial10 = _hidl_request.readInt32();
                    CdmaSmsAck smsAck = new CdmaSmsAck();
                    smsAck.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSms(serial10, smsAck);
                    return;
                case NgranBands.BAND_80 /*80*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getGsmBroadcastConfig(_hidl_request.readInt32());
                    return;
                case 81:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setGsmBroadcastConfig(_hidl_request.readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case NgranBands.BAND_82 /*82*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setGsmBroadcastActivation(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_83 /*83*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaBroadcastConfig(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_84 /*84*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaBroadcastConfig(_hidl_request.readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case EutranBands.BAND_85 /*85*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaBroadcastActivation(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_86 /*86*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCDMASubscription(_hidl_request.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial11 = _hidl_request.readInt32();
                    CdmaSmsWriteArgs cdmaSms = new CdmaSmsWriteArgs();
                    cdmaSms.readFromParcel(hwParcel);
                    writeSmsToRuim(serial11, cdmaSms);
                    return;
                case 88:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deleteSmsOnRuim(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NgranBands.BAND_89 /*89*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDeviceIdentity(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_90 /*90*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    exitEmergencyCallbackMode(_hidl_request.readInt32());
                    return;
                case 91:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSmscAddress(_hidl_request.readInt32());
                    return;
                case 92:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSmscAddress(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case NgranBands.BAND_93 /*93*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    reportSmsMemoryStatus(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_94 /*94*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    reportStkServiceIsRunning(_hidl_request.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaSubscriptionSource(_hidl_request.readInt32());
                    return;
                case 96:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestIsimAuthentication(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 97:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acknowledgeIncomingGsmSmsWithPdu(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readString());
                    return;
                case 98:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendEnvelopeWithStatus(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 99:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRadioTechnology(_hidl_request.readInt32());
                    return;
                case 100:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCellInfoList(_hidl_request.readInt32());
                    return;
                case 101:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCellInfoListRate(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial12 = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo2 = new DataProfileInfo();
                    dataProfileInfo2.readFromParcel(hwParcel);
                    setInitialAttachApn(serial12, dataProfileInfo2, _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 103:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getImsRegistrationState(_hidl_request.readInt32());
                    return;
                case 104:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial13 = _hidl_request.readInt32();
                    ImsSmsMessage message3 = new ImsSmsMessage();
                    message3.readFromParcel(hwParcel);
                    sendImsSms(serial13, message3);
                    return;
                case 105:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial14 = _hidl_request.readInt32();
                    SimApdu message4 = new SimApdu();
                    message4.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannel(serial14, message4);
                    return;
                case 106:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    iccOpenLogicalChannel(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ /*107*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    iccCloseLogicalChannel(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP /*108*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial15 = _hidl_request.readInt32();
                    SimApdu message5 = new SimApdu();
                    message5.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannel(serial15, message5);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ /*109*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvReadItem(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 110:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial16 = _hidl_request.readInt32();
                    NvWriteItem item = new NvWriteItem();
                    item.readFromParcel(hwParcel);
                    nvWriteItem(serial16, item);
                    return;
                case 111:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvWriteCdmaPrl(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 112:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvResetConfig(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 113:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial17 = _hidl_request.readInt32();
                    SelectUiccSub uiccSub = new SelectUiccSub();
                    uiccSub.readFromParcel(hwParcel);
                    setUiccSubscription(serial17, uiccSub);
                    return;
                case 114:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataAllowed(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 115:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getHardwareConfig(_hidl_request.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestIccSimAuthentication(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 117:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataProfile(_hidl_request.readInt32(), DataProfileInfo.readVectorFromParcel(_hidl_request), _hidl_request.readBool());
                    return;
                case 118:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestShutdown(_hidl_request.readInt32());
                    return;
                case 119:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getRadioCapability(_hidl_request.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial18 = _hidl_request.readInt32();
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(hwParcel);
                    setRadioCapability(serial18, rc);
                    return;
                case 121:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    startLceService(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 122:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    stopLceService(_hidl_request.readInt32());
                    return;
                case DataCallFailCause.INVALID_DNS_ADDR /*123*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    pullLceData(_hidl_request.readInt32());
                    return;
                case DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS /*124*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getModemActivityInfo(_hidl_request.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial19 = _hidl_request.readInt32();
                    boolean allAllowed = _hidl_request.readBool();
                    CarrierRestrictions carriers = new CarrierRestrictions();
                    carriers.readFromParcel(hwParcel);
                    setAllowedCarriers(serial19, allAllowed, carriers);
                    return;
                case 126:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAllowedCarriers(_hidl_request.readInt32());
                    return;
                case 127:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendDeviceState(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 128:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setIndicationFilter(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 129:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSimCardPower(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 130:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    responseAcknowledgement();
                    return;
                case 256067662:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    debug(_hidl_request.readNativeHandle(), _hidl_request.readStringVector());
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    hwParcel2.writeStatus(0);
                    HwBlob _hidl_blob = new HwBlob(16);
                    int _hidl_vec_size = _hidl_out_hashchain.size();
                    _hidl_blob.putInt32(8, _hidl_vec_size);
                    _hidl_blob.putBool(12, false);
                    HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
                    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                        long _hidl_array_offset_1 = (long) (_hidl_index_0 * 32);
                        byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
                        if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
                            throw new IllegalArgumentException("Array element is not of the expected length");
                        }
                        childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
                    }
                    _hidl_blob.putBlob(0, childBlob);
                    hwParcel2.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256921159:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    _hidl_out_info.writeToParcel(hwParcel2);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
                default:
                    return;
            }
        }
    }
}
