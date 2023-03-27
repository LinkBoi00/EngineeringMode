package android.hardware.radio.V1_5;

import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CardStatus;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.DataRegStateResult;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IccIoResult;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.OperatorInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SignalStrength;
import android.hardware.radio.V1_0.VoiceRegStateResult;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.V1_4.DataCallFailCause;
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
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IRadioResponse extends android.hardware.radio.V1_4.IRadioResponse {
    public static final String kInterfaceName = "android.hardware.radio@1.5::IRadioResponse";

    void areUiccApplicationsEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    IHwBinder asBinder();

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void enableUiccApplicationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getBarringInfoResponse(RadioResponseInfo radioResponseInfo, CellIdentity cellIdentity, ArrayList<BarringInfo> arrayList) throws RemoteException;

    void getCellInfoListResponse_1_5(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException;

    void getDataCallListResponse_1_5(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) throws RemoteException;

    void getDataRegistrationStateResponse_1_5(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatusResponse_1_5(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException;

    void getVoiceRegistrationStateResponse_1_5(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void sendCdmaSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void setDataProfileResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilterResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setInitialAttachApnResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLinkCapacityReportingCriteriaResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeManualResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRadioPowerResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSignalStrengthReportingCriteriaResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSystemSelectionChannelsResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setupDataCallResponse_1_5(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException;

    void startNetworkScanResponse_1_5(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void supplySimDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IRadioResponse asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IRadioResponse)) {
            return (IRadioResponse) iface;
        }
        IRadioResponse proxy = new Proxy(binder);
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

    static IRadioResponse castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IRadioResponse getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IRadioResponse getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IRadioResponse getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IRadioResponse getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadioResponse {
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
                return "[class or subclass of android.hardware.radio@1.5::IRadioResponse]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void getIccCardStatusResponse(RadioResponseInfo info, CardStatus cardStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPinForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPukForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPin2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPuk2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPinForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPin2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentCallsResponse(RadioResponseInfo info, ArrayList<Call> calls) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            Call.writeVectorToParcel(_hidl_request, calls);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dialResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIMSIForAppResponse(RadioResponseInfo info, String imsi) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imsi);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupConnectionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWaitingOrBackgroundResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void conferenceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rejectCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLastCallFailCauseResponse(RadioResponseInfo info, LastCallFailCauseInfo failCauseinfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            failCauseinfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthResponse(RadioResponseInfo info, SignalStrength sigStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sigStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationStateResponse(RadioResponseInfo info, VoiceRegStateResult voiceRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            voiceRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationStateResponse(RadioResponseInfo info, DataRegStateResult dataRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getOperatorResponse(RadioResponseInfo info, String longName, String shortName, String numeric) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(longName);
            _hidl_request.writeString(shortName);
            _hidl_request.writeString(numeric);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPowerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSMSExpectMoreResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCallResponse(RadioResponseInfo info, SetupDataCallResult dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dcResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccIOForAppResponse(RadioResponseInfo info, IccIoResult iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendUssdResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelPendingUssdResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClirResponse(RadioResponseInfo info, int n, int m) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            _hidl_request.writeInt32(m);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClirResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallForwardStatusResponse(RadioResponseInfo info, ArrayList<CallForwardInfo> callForwardInfos) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CallForwardInfo.writeVectorToParcel(_hidl_request, callForwardInfos);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForwardResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallWaitingResponse(RadioResponseInfo info, boolean enable, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallWaitingResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acceptCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateDataCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFacilityLockForAppResponse(RadioResponseInfo info, int response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFacilityLockForAppResponse(RadioResponseInfo info, int retry) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(retry);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBarringPasswordResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNetworkSelectionModeResponse(RadioResponseInfo info, boolean manual) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(manual);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManualResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworksResponse(RadioResponseInfo info, ArrayList<OperatorInfo> networkInfos) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            OperatorInfo.writeVectorToParcel(_hidl_request, networkInfos);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBasebandVersionResponse(RadioResponseInfo info, String version) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(version);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void separateConnectionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMuteResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getMuteResponse(RadioResponseInfo info, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClipResponse(RadioResponseInfo info, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallListResponse(RadioResponseInfo info, ArrayList<SetupDataCallResult> dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            SetupDataCallResult.writeVectorToParcel(_hidl_request, dcResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServiceNotificationsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToSimResponse(RadioResponseInfo info, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBandModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableBandModesResponse(RadioResponseInfo info, ArrayList<Integer> bandModes) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(bandModes);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeResponse(RadioResponseInfo info, String commandResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(commandResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendTerminalResponseToSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void explicitCallTransferResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkTypeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkTypeResponse(RadioResponseInfo info, int nwType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(nwType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNeighboringCidsResponse(RadioResponseInfo info, ArrayList<NeighboringCell> cells) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            NeighboringCell.writeVectorToParcel(_hidl_request, cells);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLocationUpdatesResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaSubscriptionSourceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaRoamingPreferenceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaRoamingPreferenceResponse(RadioResponseInfo info, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTTYModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTTYModeResponse(RadioResponseInfo info, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredVoicePrivacyResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredVoicePrivacyResponse(RadioResponseInfo info, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCDMAFeatureCodeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendBurstDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastConfigResponse(RadioResponseInfo info, ArrayList<GsmBroadcastSmsConfigInfo> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastActivationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaBroadcastConfigResponse(RadioResponseInfo info, ArrayList<CdmaBroadcastSmsConfigInfo> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastActivationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCDMASubscriptionResponse(RadioResponseInfo info, String mdn, String hSid, String hNid, String min, String prl) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(mdn);
            _hidl_request.writeString(hSid);
            _hidl_request.writeString(hNid);
            _hidl_request.writeString(min);
            _hidl_request.writeString(prl);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToRuimResponse(RadioResponseInfo info, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnRuimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeviceIdentityResponse(RadioResponseInfo info, String imei, String imeisv, String esn, String meid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imei);
            _hidl_request.writeString(imeisv);
            _hidl_request.writeString(esn);
            _hidl_request.writeString(meid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void exitEmergencyCallbackModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmscAddressResponse(RadioResponseInfo info, String smsc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(smsc);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmscAddressResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportSmsMemoryStatusResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportStkServiceIsRunningResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaSubscriptionSourceResponse(RadioResponseInfo info, int source) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(source);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIsimAuthenticationResponse(RadioResponseInfo info, String response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeWithStatusResponse(RadioResponseInfo info, IccIoResult iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRadioTechnologyResponse(RadioResponseInfo info, int rat) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(rat);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoListResponse(RadioResponseInfo info, ArrayList<CellInfo> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CellInfo.writeVectorToParcel(_hidl_request, cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCellInfoListRateResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApnResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsRegistrationStateResponse(RadioResponseInfo info, boolean isRegistered, int ratFamily) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(isRegistered);
            _hidl_request.writeInt32(ratFamily);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendImsSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(103, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduBasicChannelResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccOpenLogicalChannelResponse(RadioResponseInfo info, int channelId, ArrayList<Byte> selectResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(channelId);
            _hidl_request.writeInt8Vector(selectResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccCloseLogicalChannelResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvReadItemResponse(RadioResponseInfo info, String result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(result);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteItemResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteCdmaPrlResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvResetConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setUiccSubscriptionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(112, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataAllowedResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(113, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getHardwareConfigResponse(RadioResponseInfo info, ArrayList<HardwareConfig> config) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HardwareConfig.writeVectorToParcel(_hidl_request, config);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(114, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIccSimAuthenticationResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(115, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfileResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(116, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestShutdownResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(117, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRadioCapabilityResponse(RadioResponseInfo info, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(118, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioCapabilityResponse(RadioResponseInfo info, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(119, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startLceServiceResponse(RadioResponseInfo info, LceStatusInfo statusInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(120, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopLceServiceResponse(RadioResponseInfo info, LceStatusInfo statusInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(121, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pullLceDataResponse(RadioResponseInfo info, LceDataInfo lceInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            lceInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(122, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemActivityInfoResponse(RadioResponseInfo info, ActivityStatsInfo activityInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            activityInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_DNS_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriersResponse(RadioResponseInfo info, int numAllowed) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(numAllowed);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllowedCarriersResponse(RadioResponseInfo info, boolean allAllowed, CarrierRestrictions carriers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
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

        public void sendDeviceStateResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilterResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(127, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPowerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeRequest(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPowerResponse_1_1(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(131, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScanResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(132, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopNetworkScanResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_CW5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startKeepaliveResponse(RadioResponseInfo info, KeepaliveStatus status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            status.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(134, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopKeepaliveResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(135, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoListResponse_1_2(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_2.CellInfo> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_2.CellInfo.writeVectorToParcel(_hidl_request, cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(136, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccCardStatusResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.CardStatus cardStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DSW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_HDW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_TGW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentCallsResponse_1_2(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_2.Call> calls) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_2.Call.writeVectorToParcel(_hidl_request, calls);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.SignalStrength signalStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            signalStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.VoiceRegStateResult voiceRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            voiceRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationStateResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.DataRegStateResult dataRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(143, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSystemSelectionChannelsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableModemResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemStackStatusResponse(RadioResponseInfo info, boolean isEnabled) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(isEnabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void emergencyDialResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(147, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScanResponse_1_4(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(148, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoListResponse_1_4(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_4.CellInfo> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_4.CellInfo.writeVectorToParcel(_hidl_request, cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(149, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationStateResponse_1_4(RadioResponseInfo info, android.hardware.radio.V1_4.DataRegStateResult dataRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(150, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccCardStatusResponse_1_4(RadioResponseInfo info, android.hardware.radio.V1_4.CardStatus cardStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SWA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkTypeBitmapResponse(RadioResponseInfo info, int networkTypeBitmap) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(networkTypeBitmap);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF0, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkTypeBitmapResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallListResponse_1_4(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_4.SetupDataCallResult> dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_4.SetupDataCallResult.writeVectorToParcel(_hidl_request, dcResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCallResponse_1_4(RadioResponseInfo info, android.hardware.radio.V1_4.SetupDataCallResult dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dcResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriersResponse_1_4(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllowedCarriersResponse_1_4(RadioResponseInfo info, CarrierRestrictionsWithPriority carriers, int multiSimPolicy) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            carriers.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(multiSimPolicy);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthResponse_1_4(RadioResponseInfo info, android.hardware.radio.V1_4.SignalStrength signalStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            signalStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSignalStrengthReportingCriteriaResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(159, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLinkCapacityReportingCriteriaResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(160, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableUiccApplicationsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(161, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void areUiccApplicationsEnabledResponse(RadioResponseInfo info, boolean enabled) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(162, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSystemSelectionChannelsResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(163, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScanResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(164, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCallResponse_1_5(RadioResponseInfo info, SetupDataCallResult dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dcResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(165, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallListResponse_1_5(RadioResponseInfo info, ArrayList<SetupDataCallResult> dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            SetupDataCallResult.writeVectorToParcel(_hidl_request, dcResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(166, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApnResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(167, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfileResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(168, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPowerResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(169, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilterResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(170, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBarringInfoResponse(RadioResponseInfo info, CellIdentity cellIdentity, ArrayList<BarringInfo> barringInfos) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cellIdentity.writeToParcel(_hidl_request);
            BarringInfo.writeVectorToParcel(_hidl_request, barringInfos);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(171, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationStateResponse_1_5(RadioResponseInfo info, RegStateResult voiceRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            voiceRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(172, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationStateResponse_1_5(RadioResponseInfo info, RegStateResult dataRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(173, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoListResponse_1_5(RadioResponseInfo info, ArrayList<CellInfo> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CellInfo.writeVectorToParcel(_hidl_request, cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(174, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManualResponse_1_5(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(175, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSmsExpectMoreResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(176, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplySimDepersonalizationResponse(RadioResponseInfo info, int persoType, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(persoType);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(177, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccCardStatusResponse_1_5(RadioResponseInfo info, CardStatus cardStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(178, _hidl_request, _hidl_reply, 1);
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

    public static abstract class Stub extends HwBinder implements IRadioResponse {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IRadioResponse.kInterfaceName, android.hardware.radio.V1_4.IRadioResponse.kInterfaceName, android.hardware.radio.V1_3.IRadioResponse.kInterfaceName, android.hardware.radio.V1_2.IRadioResponse.kInterfaceName, android.hardware.radio.V1_1.IRadioResponse.kInterfaceName, android.hardware.radio.V1_0.IRadioResponse.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{-72, 9, 25, 57, 112, -87, 28, -90, 55, -92, -80, 24, 71, 103, 49, 86, 1, -45, 46, 62, -13, -43, -103, 47, -5, -57, -88, -47, 74, 20, -16, 21}, new byte[]{12, -64, -35, -121, -58, 52, -86, -45, 109, 125, -14, 43, 40, 50, -125, -98, -9, -34, -41, 25, 9, -37, -51, -31, 28, -3, -42, -99, -64, -36, 82, -72}, new byte[]{-11, -5, -28, -14, -118, -98, 52, 107, -29, 96, 99, -20, -92, -26, -56, 100, 17, 74, 26, 111, -74, 72, -124, -37, 3, -3, -40, 37, 121, 26, -39, -72}, new byte[]{-38, -116, 106, -23, -111, -58, -92, -78, -124, -52, 110, 68, 83, 50, -32, 100, -30, -114, -24, -96, -108, -126, -19, 90, -1, -7, -47, 89, -20, 102, -108, -73}, new byte[]{0, 54, 107, 47, -120, -7, -20, 36, 88, 1, 73, 114, -109, -126, 112, -56, 65, 61, 74, -77, 3, 33, -114, 55, -65, 58, -35, 43, -114, 107, -126, -102}, new byte[]{-68, 60, -116, 35, 48, -123, -4, -93, -121, -99, -57, 75, 73, 11, -98, 91, -63, 6, 50, 88, 71, 13, 59, 76, 18, -9, -89, 75, -14, 21, -53, -67}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
            if (IRadioResponse.kInterfaceName.equals(descriptor)) {
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
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info = new RadioResponseInfo();
                    info.readFromParcel(hwParcel);
                    CardStatus cardStatus = new CardStatus();
                    cardStatus.readFromParcel(hwParcel);
                    getIccCardStatusResponse(info, cardStatus);
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info2 = new RadioResponseInfo();
                    info2.readFromParcel(hwParcel);
                    supplyIccPinForAppResponse(info2, _hidl_request.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info3 = new RadioResponseInfo();
                    info3.readFromParcel(hwParcel);
                    supplyIccPukForAppResponse(info3, _hidl_request.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info4 = new RadioResponseInfo();
                    info4.readFromParcel(hwParcel);
                    supplyIccPin2ForAppResponse(info4, _hidl_request.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info5 = new RadioResponseInfo();
                    info5.readFromParcel(hwParcel);
                    supplyIccPuk2ForAppResponse(info5, _hidl_request.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info6 = new RadioResponseInfo();
                    info6.readFromParcel(hwParcel);
                    changeIccPinForAppResponse(info6, _hidl_request.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info7 = new RadioResponseInfo();
                    info7.readFromParcel(hwParcel);
                    changeIccPin2ForAppResponse(info7, _hidl_request.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info8 = new RadioResponseInfo();
                    info8.readFromParcel(hwParcel);
                    supplyNetworkDepersonalizationResponse(info8, _hidl_request.readInt32());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info9 = new RadioResponseInfo();
                    info9.readFromParcel(hwParcel);
                    getCurrentCallsResponse(info9, Call.readVectorFromParcel(_hidl_request));
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info10 = new RadioResponseInfo();
                    info10.readFromParcel(hwParcel);
                    dialResponse(info10);
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info11 = new RadioResponseInfo();
                    info11.readFromParcel(hwParcel);
                    getIMSIForAppResponse(info11, _hidl_request.readString());
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info12 = new RadioResponseInfo();
                    info12.readFromParcel(hwParcel);
                    hangupConnectionResponse(info12);
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info13 = new RadioResponseInfo();
                    info13.readFromParcel(hwParcel);
                    hangupWaitingOrBackgroundResponse(info13);
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info14 = new RadioResponseInfo();
                    info14.readFromParcel(hwParcel);
                    hangupForegroundResumeBackgroundResponse(info14);
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info15 = new RadioResponseInfo();
                    info15.readFromParcel(hwParcel);
                    switchWaitingOrHoldingAndActiveResponse(info15);
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info16 = new RadioResponseInfo();
                    info16.readFromParcel(hwParcel);
                    conferenceResponse(info16);
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info17 = new RadioResponseInfo();
                    info17.readFromParcel(hwParcel);
                    rejectCallResponse(info17);
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info18 = new RadioResponseInfo();
                    info18.readFromParcel(hwParcel);
                    LastCallFailCauseInfo failCauseinfo = new LastCallFailCauseInfo();
                    failCauseinfo.readFromParcel(hwParcel);
                    getLastCallFailCauseResponse(info18, failCauseinfo);
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info19 = new RadioResponseInfo();
                    info19.readFromParcel(hwParcel);
                    SignalStrength sigStrength = new SignalStrength();
                    sigStrength.readFromParcel(hwParcel);
                    getSignalStrengthResponse(info19, sigStrength);
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info20 = new RadioResponseInfo();
                    info20.readFromParcel(hwParcel);
                    VoiceRegStateResult voiceRegResponse = new VoiceRegStateResult();
                    voiceRegResponse.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse(info20, voiceRegResponse);
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info21 = new RadioResponseInfo();
                    info21.readFromParcel(hwParcel);
                    DataRegStateResult dataRegResponse = new DataRegStateResult();
                    dataRegResponse.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse(info21, dataRegResponse);
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info22 = new RadioResponseInfo();
                    info22.readFromParcel(hwParcel);
                    getOperatorResponse(info22, _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info23 = new RadioResponseInfo();
                    info23.readFromParcel(hwParcel);
                    setRadioPowerResponse(info23);
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info24 = new RadioResponseInfo();
                    info24.readFromParcel(hwParcel);
                    sendDtmfResponse(info24);
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info25 = new RadioResponseInfo();
                    info25.readFromParcel(hwParcel);
                    SendSmsResult sms = new SendSmsResult();
                    sms.readFromParcel(hwParcel);
                    sendSmsResponse(info25, sms);
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info26 = new RadioResponseInfo();
                    info26.readFromParcel(hwParcel);
                    SendSmsResult sms2 = new SendSmsResult();
                    sms2.readFromParcel(hwParcel);
                    sendSMSExpectMoreResponse(info26, sms2);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info27 = new RadioResponseInfo();
                    info27.readFromParcel(hwParcel);
                    SetupDataCallResult dcResponse = new SetupDataCallResult();
                    dcResponse.readFromParcel(hwParcel);
                    setupDataCallResponse(info27, dcResponse);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info28 = new RadioResponseInfo();
                    info28.readFromParcel(hwParcel);
                    IccIoResult iccIo = new IccIoResult();
                    iccIo.readFromParcel(hwParcel);
                    iccIOForAppResponse(info28, iccIo);
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info29 = new RadioResponseInfo();
                    info29.readFromParcel(hwParcel);
                    sendUssdResponse(info29);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info30 = new RadioResponseInfo();
                    info30.readFromParcel(hwParcel);
                    cancelPendingUssdResponse(info30);
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info31 = new RadioResponseInfo();
                    info31.readFromParcel(hwParcel);
                    getClirResponse(info31, _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info32 = new RadioResponseInfo();
                    info32.readFromParcel(hwParcel);
                    setClirResponse(info32);
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info33 = new RadioResponseInfo();
                    info33.readFromParcel(hwParcel);
                    getCallForwardStatusResponse(info33, CallForwardInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info34 = new RadioResponseInfo();
                    info34.readFromParcel(hwParcel);
                    setCallForwardResponse(info34);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info35 = new RadioResponseInfo();
                    info35.readFromParcel(hwParcel);
                    getCallWaitingResponse(info35, _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info36 = new RadioResponseInfo();
                    info36.readFromParcel(hwParcel);
                    setCallWaitingResponse(info36);
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info37 = new RadioResponseInfo();
                    info37.readFromParcel(hwParcel);
                    acknowledgeLastIncomingGsmSmsResponse(info37);
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info38 = new RadioResponseInfo();
                    info38.readFromParcel(hwParcel);
                    acceptCallResponse(info38);
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info39 = new RadioResponseInfo();
                    info39.readFromParcel(hwParcel);
                    deactivateDataCallResponse(info39);
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info40 = new RadioResponseInfo();
                    info40.readFromParcel(hwParcel);
                    getFacilityLockForAppResponse(info40, _hidl_request.readInt32());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info41 = new RadioResponseInfo();
                    info41.readFromParcel(hwParcel);
                    setFacilityLockForAppResponse(info41, _hidl_request.readInt32());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info42 = new RadioResponseInfo();
                    info42.readFromParcel(hwParcel);
                    setBarringPasswordResponse(info42);
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info43 = new RadioResponseInfo();
                    info43.readFromParcel(hwParcel);
                    getNetworkSelectionModeResponse(info43, _hidl_request.readBool());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info44 = new RadioResponseInfo();
                    info44.readFromParcel(hwParcel);
                    setNetworkSelectionModeAutomaticResponse(info44);
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info45 = new RadioResponseInfo();
                    info45.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse(info45);
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info46 = new RadioResponseInfo();
                    info46.readFromParcel(hwParcel);
                    getAvailableNetworksResponse(info46, OperatorInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info47 = new RadioResponseInfo();
                    info47.readFromParcel(hwParcel);
                    startDtmfResponse(info47);
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info48 = new RadioResponseInfo();
                    info48.readFromParcel(hwParcel);
                    stopDtmfResponse(info48);
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info49 = new RadioResponseInfo();
                    info49.readFromParcel(hwParcel);
                    getBasebandVersionResponse(info49, _hidl_request.readString());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info50 = new RadioResponseInfo();
                    info50.readFromParcel(hwParcel);
                    separateConnectionResponse(info50);
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info51 = new RadioResponseInfo();
                    info51.readFromParcel(hwParcel);
                    setMuteResponse(info51);
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info52 = new RadioResponseInfo();
                    info52.readFromParcel(hwParcel);
                    getMuteResponse(info52, _hidl_request.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info53 = new RadioResponseInfo();
                    info53.readFromParcel(hwParcel);
                    getClipResponse(info53, _hidl_request.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info54 = new RadioResponseInfo();
                    info54.readFromParcel(hwParcel);
                    getDataCallListResponse(info54, SetupDataCallResult.readVectorFromParcel(_hidl_request));
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info55 = new RadioResponseInfo();
                    info55.readFromParcel(hwParcel);
                    setSuppServiceNotificationsResponse(info55);
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info56 = new RadioResponseInfo();
                    info56.readFromParcel(hwParcel);
                    writeSmsToSimResponse(info56, _hidl_request.readInt32());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info57 = new RadioResponseInfo();
                    info57.readFromParcel(hwParcel);
                    deleteSmsOnSimResponse(info57);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info58 = new RadioResponseInfo();
                    info58.readFromParcel(hwParcel);
                    setBandModeResponse(info58);
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info59 = new RadioResponseInfo();
                    info59.readFromParcel(hwParcel);
                    getAvailableBandModesResponse(info59, _hidl_request.readInt32Vector());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info60 = new RadioResponseInfo();
                    info60.readFromParcel(hwParcel);
                    sendEnvelopeResponse(info60, _hidl_request.readString());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info61 = new RadioResponseInfo();
                    info61.readFromParcel(hwParcel);
                    sendTerminalResponseToSimResponse(info61);
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info62 = new RadioResponseInfo();
                    info62.readFromParcel(hwParcel);
                    handleStkCallSetupRequestFromSimResponse(info62);
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info63 = new RadioResponseInfo();
                    info63.readFromParcel(hwParcel);
                    explicitCallTransferResponse(info63);
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info64 = new RadioResponseInfo();
                    info64.readFromParcel(hwParcel);
                    setPreferredNetworkTypeResponse(info64);
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info65 = new RadioResponseInfo();
                    info65.readFromParcel(hwParcel);
                    getPreferredNetworkTypeResponse(info65, _hidl_request.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info66 = new RadioResponseInfo();
                    info66.readFromParcel(hwParcel);
                    getNeighboringCidsResponse(info66, NeighboringCell.readVectorFromParcel(_hidl_request));
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info67 = new RadioResponseInfo();
                    info67.readFromParcel(hwParcel);
                    setLocationUpdatesResponse(info67);
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info68 = new RadioResponseInfo();
                    info68.readFromParcel(hwParcel);
                    setCdmaSubscriptionSourceResponse(info68);
                    return;
                case LastCallFailCause.REQUESTED_FACILITY_NOT_IMPLEMENTED:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info69 = new RadioResponseInfo();
                    info69.readFromParcel(hwParcel);
                    setCdmaRoamingPreferenceResponse(info69);
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info70 = new RadioResponseInfo();
                    info70.readFromParcel(hwParcel);
                    getCdmaRoamingPreferenceResponse(info70, _hidl_request.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info71 = new RadioResponseInfo();
                    info71.readFromParcel(hwParcel);
                    setTTYModeResponse(info71);
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info72 = new RadioResponseInfo();
                    info72.readFromParcel(hwParcel);
                    getTTYModeResponse(info72, _hidl_request.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info73 = new RadioResponseInfo();
                    info73.readFromParcel(hwParcel);
                    setPreferredVoicePrivacyResponse(info73);
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info74 = new RadioResponseInfo();
                    info74.readFromParcel(hwParcel);
                    getPreferredVoicePrivacyResponse(info74, _hidl_request.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info75 = new RadioResponseInfo();
                    info75.readFromParcel(hwParcel);
                    sendCDMAFeatureCodeResponse(info75);
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info76 = new RadioResponseInfo();
                    info76.readFromParcel(hwParcel);
                    sendBurstDtmfResponse(info76);
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info77 = new RadioResponseInfo();
                    info77.readFromParcel(hwParcel);
                    SendSmsResult sms3 = new SendSmsResult();
                    sms3.readFromParcel(hwParcel);
                    sendCdmaSmsResponse(info77, sms3);
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info78 = new RadioResponseInfo();
                    info78.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSmsResponse(info78);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info79 = new RadioResponseInfo();
                    info79.readFromParcel(hwParcel);
                    getGsmBroadcastConfigResponse(info79, GsmBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case NgranBands.BAND_80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info80 = new RadioResponseInfo();
                    info80.readFromParcel(hwParcel);
                    setGsmBroadcastConfigResponse(info80);
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info81 = new RadioResponseInfo();
                    info81.readFromParcel(hwParcel);
                    setGsmBroadcastActivationResponse(info81);
                    return;
                case NgranBands.BAND_82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info82 = new RadioResponseInfo();
                    info82.readFromParcel(hwParcel);
                    getCdmaBroadcastConfigResponse(info82, CdmaBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case NgranBands.BAND_83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info83 = new RadioResponseInfo();
                    info83.readFromParcel(hwParcel);
                    setCdmaBroadcastConfigResponse(info83);
                    return;
                case NgranBands.BAND_84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info84 = new RadioResponseInfo();
                    info84.readFromParcel(hwParcel);
                    setCdmaBroadcastActivationResponse(info84);
                    return;
                case EutranBands.BAND_85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info85 = new RadioResponseInfo();
                    info85.readFromParcel(hwParcel);
                    getCDMASubscriptionResponse(info85, _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case NgranBands.BAND_86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info86 = new RadioResponseInfo();
                    info86.readFromParcel(hwParcel);
                    writeSmsToRuimResponse(info86, _hidl_request.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info87 = new RadioResponseInfo();
                    info87.readFromParcel(hwParcel);
                    deleteSmsOnRuimResponse(info87);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info88 = new RadioResponseInfo();
                    info88.readFromParcel(hwParcel);
                    getDeviceIdentityResponse(info88, _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case NgranBands.BAND_89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info89 = new RadioResponseInfo();
                    info89.readFromParcel(hwParcel);
                    exitEmergencyCallbackModeResponse(info89);
                    return;
                case NgranBands.BAND_90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info90 = new RadioResponseInfo();
                    info90.readFromParcel(hwParcel);
                    getSmscAddressResponse(info90, _hidl_request.readString());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info91 = new RadioResponseInfo();
                    info91.readFromParcel(hwParcel);
                    setSmscAddressResponse(info91);
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info92 = new RadioResponseInfo();
                    info92.readFromParcel(hwParcel);
                    reportSmsMemoryStatusResponse(info92);
                    return;
                case NgranBands.BAND_93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info93 = new RadioResponseInfo();
                    info93.readFromParcel(hwParcel);
                    reportStkServiceIsRunningResponse(info93);
                    return;
                case NgranBands.BAND_94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info94 = new RadioResponseInfo();
                    info94.readFromParcel(hwParcel);
                    getCdmaSubscriptionSourceResponse(info94, _hidl_request.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info95 = new RadioResponseInfo();
                    info95.readFromParcel(hwParcel);
                    requestIsimAuthenticationResponse(info95, _hidl_request.readString());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info96 = new RadioResponseInfo();
                    info96.readFromParcel(hwParcel);
                    acknowledgeIncomingGsmSmsWithPduResponse(info96);
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info97 = new RadioResponseInfo();
                    info97.readFromParcel(hwParcel);
                    IccIoResult iccIo2 = new IccIoResult();
                    iccIo2.readFromParcel(hwParcel);
                    sendEnvelopeWithStatusResponse(info97, iccIo2);
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info98 = new RadioResponseInfo();
                    info98.readFromParcel(hwParcel);
                    getVoiceRadioTechnologyResponse(info98, _hidl_request.readInt32());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info99 = new RadioResponseInfo();
                    info99.readFromParcel(hwParcel);
                    getCellInfoListResponse(info99, CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info100 = new RadioResponseInfo();
                    info100.readFromParcel(hwParcel);
                    setCellInfoListRateResponse(info100);
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info101 = new RadioResponseInfo();
                    info101.readFromParcel(hwParcel);
                    setInitialAttachApnResponse(info101);
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info102 = new RadioResponseInfo();
                    info102.readFromParcel(hwParcel);
                    getImsRegistrationStateResponse(info102, _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info103 = new RadioResponseInfo();
                    info103.readFromParcel(hwParcel);
                    SendSmsResult sms4 = new SendSmsResult();
                    sms4.readFromParcel(hwParcel);
                    sendImsSmsResponse(info103, sms4);
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info104 = new RadioResponseInfo();
                    info104.readFromParcel(hwParcel);
                    IccIoResult result = new IccIoResult();
                    result.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannelResponse(info104, result);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info105 = new RadioResponseInfo();
                    info105.readFromParcel(hwParcel);
                    iccOpenLogicalChannelResponse(info105, _hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info106 = new RadioResponseInfo();
                    info106.readFromParcel(hwParcel);
                    iccCloseLogicalChannelResponse(info106);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ /*107*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info107 = new RadioResponseInfo();
                    info107.readFromParcel(hwParcel);
                    IccIoResult result2 = new IccIoResult();
                    result2.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannelResponse(info107, result2);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP /*108*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info108 = new RadioResponseInfo();
                    info108.readFromParcel(hwParcel);
                    nvReadItemResponse(info108, _hidl_request.readString());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ /*109*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info109 = new RadioResponseInfo();
                    info109.readFromParcel(hwParcel);
                    nvWriteItemResponse(info109);
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info110 = new RadioResponseInfo();
                    info110.readFromParcel(hwParcel);
                    nvWriteCdmaPrlResponse(info110);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info111 = new RadioResponseInfo();
                    info111.readFromParcel(hwParcel);
                    nvResetConfigResponse(info111);
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info112 = new RadioResponseInfo();
                    info112.readFromParcel(hwParcel);
                    setUiccSubscriptionResponse(info112);
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info113 = new RadioResponseInfo();
                    info113.readFromParcel(hwParcel);
                    setDataAllowedResponse(info113);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info114 = new RadioResponseInfo();
                    info114.readFromParcel(hwParcel);
                    getHardwareConfigResponse(info114, HardwareConfig.readVectorFromParcel(_hidl_request));
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info115 = new RadioResponseInfo();
                    info115.readFromParcel(hwParcel);
                    IccIoResult result3 = new IccIoResult();
                    result3.readFromParcel(hwParcel);
                    requestIccSimAuthenticationResponse(info115, result3);
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info116 = new RadioResponseInfo();
                    info116.readFromParcel(hwParcel);
                    setDataProfileResponse(info116);
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info117 = new RadioResponseInfo();
                    info117.readFromParcel(hwParcel);
                    requestShutdownResponse(info117);
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info118 = new RadioResponseInfo();
                    info118.readFromParcel(hwParcel);
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(hwParcel);
                    getRadioCapabilityResponse(info118, rc);
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info119 = new RadioResponseInfo();
                    info119.readFromParcel(hwParcel);
                    RadioCapability rc2 = new RadioCapability();
                    rc2.readFromParcel(hwParcel);
                    setRadioCapabilityResponse(info119, rc2);
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info120 = new RadioResponseInfo();
                    info120.readFromParcel(hwParcel);
                    LceStatusInfo statusInfo = new LceStatusInfo();
                    statusInfo.readFromParcel(hwParcel);
                    startLceServiceResponse(info120, statusInfo);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info121 = new RadioResponseInfo();
                    info121.readFromParcel(hwParcel);
                    LceStatusInfo statusInfo2 = new LceStatusInfo();
                    statusInfo2.readFromParcel(hwParcel);
                    stopLceServiceResponse(info121, statusInfo2);
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info122 = new RadioResponseInfo();
                    info122.readFromParcel(hwParcel);
                    LceDataInfo lceInfo = new LceDataInfo();
                    lceInfo.readFromParcel(hwParcel);
                    pullLceDataResponse(info122, lceInfo);
                    return;
                case DataCallFailCause.INVALID_DNS_ADDR:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info123 = new RadioResponseInfo();
                    info123.readFromParcel(hwParcel);
                    ActivityStatsInfo activityInfo = new ActivityStatsInfo();
                    activityInfo.readFromParcel(hwParcel);
                    getModemActivityInfoResponse(info123, activityInfo);
                    return;
                case DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info124 = new RadioResponseInfo();
                    info124.readFromParcel(hwParcel);
                    setAllowedCarriersResponse(info124, _hidl_request.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info125 = new RadioResponseInfo();
                    info125.readFromParcel(hwParcel);
                    boolean allAllowed = _hidl_request.readBool();
                    CarrierRestrictions carriers = new CarrierRestrictions();
                    carriers.readFromParcel(hwParcel);
                    getAllowedCarriersResponse(info125, allAllowed, carriers);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info126 = new RadioResponseInfo();
                    info126.readFromParcel(hwParcel);
                    sendDeviceStateResponse(info126);
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info127 = new RadioResponseInfo();
                    info127.readFromParcel(hwParcel);
                    setIndicationFilterResponse(info127);
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info128 = new RadioResponseInfo();
                    info128.readFromParcel(hwParcel);
                    setSimCardPowerResponse(info128);
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    acknowledgeRequest(_hidl_request.readInt32());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info129 = new RadioResponseInfo();
                    info129.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryptionResponse(info129);
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info130 = new RadioResponseInfo();
                    info130.readFromParcel(hwParcel);
                    setSimCardPowerResponse_1_1(info130);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info131 = new RadioResponseInfo();
                    info131.readFromParcel(hwParcel);
                    startNetworkScanResponse(info131);
                    return;
                case Cea708CCParser.Const.CODE_C1_CW5 /*133*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info132 = new RadioResponseInfo();
                    info132.readFromParcel(hwParcel);
                    stopNetworkScanResponse(info132);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info133 = new RadioResponseInfo();
                    info133.readFromParcel(hwParcel);
                    KeepaliveStatus status = new KeepaliveStatus();
                    status.readFromParcel(hwParcel);
                    startKeepaliveResponse(info133, status);
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info134 = new RadioResponseInfo();
                    info134.readFromParcel(hwParcel);
                    stopKeepaliveResponse(info134);
                    return;
                case 136:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info135 = new RadioResponseInfo();
                    info135.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_2(info135, android.hardware.radio.V1_2.CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case Cea708CCParser.Const.CODE_C1_DSW /*137*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info136 = new RadioResponseInfo();
                    info136.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.CardStatus cardStatus2 = new android.hardware.radio.V1_2.CardStatus();
                    cardStatus2.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_2(info136, cardStatus2);
                    return;
                case Cea708CCParser.Const.CODE_C1_HDW /*138*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info137 = new RadioResponseInfo();
                    info137.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse(info137);
                    return;
                case Cea708CCParser.Const.CODE_C1_TGW /*139*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info138 = new RadioResponseInfo();
                    info138.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse(info138);
                    return;
                case Cea708CCParser.Const.CODE_C1_DLW /*140*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info139 = new RadioResponseInfo();
                    info139.readFromParcel(hwParcel);
                    getCurrentCallsResponse_1_2(info139, android.hardware.radio.V1_2.Call.readVectorFromParcel(_hidl_request));
                    return;
                case Cea708CCParser.Const.CODE_C1_DLY /*141*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info140 = new RadioResponseInfo();
                    info140.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.SignalStrength signalStrength = new android.hardware.radio.V1_2.SignalStrength();
                    signalStrength.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_2(info140, signalStrength);
                    return;
                case Cea708CCParser.Const.CODE_C1_DLC /*142*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info141 = new RadioResponseInfo();
                    info141.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.VoiceRegStateResult voiceRegResponse2 = new android.hardware.radio.V1_2.VoiceRegStateResult();
                    voiceRegResponse2.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_2(info141, voiceRegResponse2);
                    return;
                case 143:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info142 = new RadioResponseInfo();
                    info142.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.DataRegStateResult dataRegResponse2 = new android.hardware.radio.V1_2.DataRegStateResult();
                    dataRegResponse2.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_2(info142, dataRegResponse2);
                    return;
                case Cea708CCParser.Const.CODE_C1_SPA /*144*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info143 = new RadioResponseInfo();
                    info143.readFromParcel(hwParcel);
                    setSystemSelectionChannelsResponse(info143);
                    return;
                case Cea708CCParser.Const.CODE_C1_SPC /*145*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info144 = new RadioResponseInfo();
                    info144.readFromParcel(hwParcel);
                    enableModemResponse(info144);
                    return;
                case Cea708CCParser.Const.CODE_C1_SPL /*146*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info145 = new RadioResponseInfo();
                    info145.readFromParcel(hwParcel);
                    getModemStackStatusResponse(info145, _hidl_request.readBool());
                    return;
                case 147:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info146 = new RadioResponseInfo();
                    info146.readFromParcel(hwParcel);
                    emergencyDialResponse(info146);
                    return;
                case 148:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info147 = new RadioResponseInfo();
                    info147.readFromParcel(hwParcel);
                    startNetworkScanResponse_1_4(info147);
                    return;
                case 149:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info148 = new RadioResponseInfo();
                    info148.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_4(info148, android.hardware.radio.V1_4.CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 150:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info149 = new RadioResponseInfo();
                    info149.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.DataRegStateResult dataRegResponse3 = new android.hardware.radio.V1_4.DataRegStateResult();
                    dataRegResponse3.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_4(info149, dataRegResponse3);
                    return;
                case Cea708CCParser.Const.CODE_C1_SWA /*151*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info150 = new RadioResponseInfo();
                    info150.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.CardStatus cardStatus3 = new android.hardware.radio.V1_4.CardStatus();
                    cardStatus3.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_4(info150, cardStatus3);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF0 /*152*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info151 = new RadioResponseInfo();
                    info151.readFromParcel(hwParcel);
                    getPreferredNetworkTypeBitmapResponse(info151, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF1 /*153*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info152 = new RadioResponseInfo();
                    info152.readFromParcel(hwParcel);
                    setPreferredNetworkTypeBitmapResponse(info152);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF2 /*154*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info153 = new RadioResponseInfo();
                    info153.readFromParcel(hwParcel);
                    getDataCallListResponse_1_4(info153, android.hardware.radio.V1_4.SetupDataCallResult.readVectorFromParcel(_hidl_request));
                    return;
                case Cea708CCParser.Const.CODE_C1_DF3 /*155*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info154 = new RadioResponseInfo();
                    info154.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.SetupDataCallResult dcResponse2 = new android.hardware.radio.V1_4.SetupDataCallResult();
                    dcResponse2.readFromParcel(hwParcel);
                    setupDataCallResponse_1_4(info154, dcResponse2);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF4 /*156*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info155 = new RadioResponseInfo();
                    info155.readFromParcel(hwParcel);
                    setAllowedCarriersResponse_1_4(info155);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF5 /*157*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info156 = new RadioResponseInfo();
                    info156.readFromParcel(hwParcel);
                    CarrierRestrictionsWithPriority carriers2 = new CarrierRestrictionsWithPriority();
                    carriers2.readFromParcel(hwParcel);
                    getAllowedCarriersResponse_1_4(info156, carriers2, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF6 /*158*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    RadioResponseInfo info157 = new RadioResponseInfo();
                    info157.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.SignalStrength signalStrength2 = new android.hardware.radio.V1_4.SignalStrength();
                    signalStrength2.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_4(info157, signalStrength2);
                    return;
                case 159:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info158 = new RadioResponseInfo();
                    info158.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse_1_5(info158);
                    return;
                case 160:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info159 = new RadioResponseInfo();
                    info159.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse_1_5(info159);
                    return;
                case 161:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info160 = new RadioResponseInfo();
                    info160.readFromParcel(hwParcel);
                    enableUiccApplicationsResponse(info160);
                    return;
                case 162:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info161 = new RadioResponseInfo();
                    info161.readFromParcel(hwParcel);
                    areUiccApplicationsEnabledResponse(info161, _hidl_request.readBool());
                    return;
                case 163:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info162 = new RadioResponseInfo();
                    info162.readFromParcel(hwParcel);
                    setSystemSelectionChannelsResponse_1_5(info162);
                    return;
                case 164:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info163 = new RadioResponseInfo();
                    info163.readFromParcel(hwParcel);
                    startNetworkScanResponse_1_5(info163);
                    return;
                case 165:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info164 = new RadioResponseInfo();
                    info164.readFromParcel(hwParcel);
                    SetupDataCallResult dcResponse3 = new SetupDataCallResult();
                    dcResponse3.readFromParcel(hwParcel);
                    setupDataCallResponse_1_5(info164, dcResponse3);
                    return;
                case 166:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info165 = new RadioResponseInfo();
                    info165.readFromParcel(hwParcel);
                    getDataCallListResponse_1_5(info165, SetupDataCallResult.readVectorFromParcel(_hidl_request));
                    return;
                case 167:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info166 = new RadioResponseInfo();
                    info166.readFromParcel(hwParcel);
                    setInitialAttachApnResponse_1_5(info166);
                    return;
                case 168:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info167 = new RadioResponseInfo();
                    info167.readFromParcel(hwParcel);
                    setDataProfileResponse_1_5(info167);
                    return;
                case 169:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info168 = new RadioResponseInfo();
                    info168.readFromParcel(hwParcel);
                    setRadioPowerResponse_1_5(info168);
                    return;
                case 170:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info169 = new RadioResponseInfo();
                    info169.readFromParcel(hwParcel);
                    setIndicationFilterResponse_1_5(info169);
                    return;
                case 171:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info170 = new RadioResponseInfo();
                    info170.readFromParcel(hwParcel);
                    CellIdentity cellIdentity = new CellIdentity();
                    cellIdentity.readFromParcel(hwParcel);
                    getBarringInfoResponse(info170, cellIdentity, BarringInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 172:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info171 = new RadioResponseInfo();
                    info171.readFromParcel(hwParcel);
                    RegStateResult voiceRegResponse3 = new RegStateResult();
                    voiceRegResponse3.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_5(info171, voiceRegResponse3);
                    return;
                case 173:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info172 = new RadioResponseInfo();
                    info172.readFromParcel(hwParcel);
                    RegStateResult dataRegResponse4 = new RegStateResult();
                    dataRegResponse4.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_5(info172, dataRegResponse4);
                    return;
                case 174:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info173 = new RadioResponseInfo();
                    info173.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_5(info173, CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 175:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info174 = new RadioResponseInfo();
                    info174.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse_1_5(info174);
                    return;
                case 176:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info175 = new RadioResponseInfo();
                    info175.readFromParcel(hwParcel);
                    SendSmsResult sms5 = new SendSmsResult();
                    sms5.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMoreResponse(info175, sms5);
                    return;
                case 177:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info176 = new RadioResponseInfo();
                    info176.readFromParcel(hwParcel);
                    supplySimDepersonalizationResponse(info176, _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 178:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo info177 = new RadioResponseInfo();
                    info177.readFromParcel(hwParcel);
                    CardStatus cardStatus4 = new CardStatus();
                    cardStatus4.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_5(info177, cardStatus4);
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
