package android.hardware.radio.V1_5;

import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsWriteArgs;
import android.hardware.radio.V1_0.DataProfileInfo;
import android.hardware.radio.V1_0.Dial;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.IRadioIndication;
import android.hardware.radio.V1_0.IRadioResponse;
import android.hardware.radio.V1_0.IccIo;
import android.hardware.radio.V1_0.ImsSmsMessage;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.hardware.radio.V1_0.NvWriteItem;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SelectUiccSub;
import android.hardware.radio.V1_0.SimApdu;
import android.hardware.radio.V1_0.SmsWriteArgs;
import android.hardware.radio.V1_1.ImsiEncryptionInfo;
import android.hardware.radio.V1_1.KeepaliveRequest;
import android.hardware.radio.V1_1.NetworkScanRequest;
import android.hardware.radio.V1_1.RadioAccessSpecifier;
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

public interface IRadio extends android.hardware.radio.V1_4.IRadio {
    public static final String kInterfaceName = "android.hardware.radio@1.5::IRadio";

    void areUiccApplicationsEnabled(int i) throws RemoteException;

    IHwBinder asBinder();

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void enableUiccApplications(int i, boolean z) throws RemoteException;

    void getBarringInfo(int i) throws RemoteException;

    void getDataRegistrationState_1_5(int i) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getVoiceRegistrationState_1_5(int i) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void setDataProfile_1_5(int i, ArrayList<DataProfileInfo> arrayList) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilter_1_5(int i, int i2) throws RemoteException;

    void setInitialAttachApn_1_5(int i, DataProfileInfo dataProfileInfo) throws RemoteException;

    void setLinkCapacityReportingCriteria_1_5(int i, int i2, int i3, int i4, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, int i5) throws RemoteException;

    void setNetworkSelectionModeManual_1_5(int i, String str, int i2) throws RemoteException;

    void setRadioPower_1_5(int i, boolean z, boolean z2, boolean z3) throws RemoteException;

    void setSignalStrengthReportingCriteria_1_5(int i, SignalThresholdInfo signalThresholdInfo, int i2) throws RemoteException;

    void setSystemSelectionChannels_1_5(int i, boolean z, ArrayList<RadioAccessSpecifier> arrayList) throws RemoteException;

    void setupDataCall_1_5(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, ArrayList<LinkAddress> arrayList, ArrayList<String> arrayList2) throws RemoteException;

    void startNetworkScan_1_5(int i, NetworkScanRequest networkScanRequest) throws RemoteException;

    void supplySimDepersonalization(int i, int i2, String str) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

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
                return "[class or subclass of android.hardware.radio@1.5::IRadio]@Proxy";
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCarrierInfoForImsiEncryption(int serial, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            imsiEncryptionInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(131, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPower_1_1(int serial, int powerUp) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(powerUp);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(132, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScan(int serial, NetworkScanRequest request) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            request.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_CW5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopNetworkScan(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(134, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startKeepalive(int serial, KeepaliveRequest keepalive) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            keepalive.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(135, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopKeepalive(int serial, int sessionHandle) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(sessionHandle);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(136, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScan_1_2(int serial, android.hardware.radio.V1_2.NetworkScanRequest request) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            request.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DSW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilter_1_2(int serial, int indicationFilter) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(indicationFilter);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_HDW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSignalStrengthReportingCriteria(int serial, int hysteresisMs, int hysteresisDb, ArrayList<Integer> thresholdsDbm, int accessNetwork) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(hysteresisMs);
            _hidl_request.writeInt32(hysteresisDb);
            _hidl_request.writeInt32Vector(thresholdsDbm);
            _hidl_request.writeInt32(accessNetwork);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_TGW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLinkCapacityReportingCriteria(int serial, int hysteresisMs, int hysteresisDlKbps, int hysteresisUlKbps, ArrayList<Integer> thresholdsDownlinkKbps, ArrayList<Integer> thresholdsUplinkKbps, int accessNetwork) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(hysteresisMs);
            _hidl_request.writeInt32(hysteresisDlKbps);
            _hidl_request.writeInt32(hysteresisUlKbps);
            _hidl_request.writeInt32Vector(thresholdsDownlinkKbps);
            _hidl_request.writeInt32Vector(thresholdsUplinkKbps);
            _hidl_request.writeInt32(accessNetwork);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCall_1_2(int serial, int accessNetwork, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean roamingAllowed, boolean isRoaming, int reason, ArrayList<String> addresses, ArrayList<String> dnses) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(accessNetwork);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeBool(isRoaming);
            _hidl_request.writeInt32(reason);
            _hidl_request.writeStringVector(addresses);
            _hidl_request.writeStringVector(dnses);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateDataCall_1_2(int serial, int cid, int reason) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cid);
            _hidl_request.writeInt32(reason);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSystemSelectionChannels(int serial, boolean specifyChannels, ArrayList<RadioAccessSpecifier> specifiers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(specifyChannels);
            RadioAccessSpecifier.writeVectorToParcel(_hidl_request, specifiers);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(143, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableModem(int serial, boolean on) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(on);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemStackStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCall_1_4(int serial, int accessNetwork, android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo, boolean roamingAllowed, int reason, ArrayList<String> addresses, ArrayList<String> dnses) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(accessNetwork);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeInt32(reason);
            _hidl_request.writeStringVector(addresses);
            _hidl_request.writeStringVector(dnses);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApn_1_4(int serial, android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dataProfileInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(147, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfile_1_4(int serial, ArrayList<android.hardware.radio.V1_4.DataProfileInfo> profiles) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            android.hardware.radio.V1_4.DataProfileInfo.writeVectorToParcel(_hidl_request, profiles);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(148, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void emergencyDial(int serial, Dial dialInfo, int categories, ArrayList<String> urns, int routing, boolean hasKnownUserIntentEmergency, boolean isTesting) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(categories);
            _hidl_request.writeStringVector(urns);
            _hidl_request.writeInt32(routing);
            _hidl_request.writeBool(hasKnownUserIntentEmergency);
            _hidl_request.writeBool(isTesting);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(149, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScan_1_4(int serial, android.hardware.radio.V1_2.NetworkScanRequest request) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            request.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(150, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkTypeBitmap(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SWA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkTypeBitmap(int serial, int networkTypeBitmap) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(networkTypeBitmap);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF0, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriers_1_4(int serial, CarrierRestrictionsWithPriority carriers, int multiSimPolicy) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            carriers.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(multiSimPolicy);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllowedCarriers_1_4(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrength_1_4(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSignalStrengthReportingCriteria_1_5(int serial, SignalThresholdInfo signalThresholdInfo, int accessNetwork) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            signalThresholdInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(accessNetwork);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLinkCapacityReportingCriteria_1_5(int serial, int hysteresisMs, int hysteresisDlKbps, int hysteresisUlKbps, ArrayList<Integer> thresholdsDownlinkKbps, ArrayList<Integer> thresholdsUplinkKbps, int accessNetwork) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(hysteresisMs);
            _hidl_request.writeInt32(hysteresisDlKbps);
            _hidl_request.writeInt32(hysteresisUlKbps);
            _hidl_request.writeInt32Vector(thresholdsDownlinkKbps);
            _hidl_request.writeInt32Vector(thresholdsUplinkKbps);
            _hidl_request.writeInt32(accessNetwork);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableUiccApplications(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void areUiccApplicationsEnabled(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(159, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSystemSelectionChannels_1_5(int serial, boolean specifyChannels, ArrayList<RadioAccessSpecifier> specifiers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(specifyChannels);
            RadioAccessSpecifier.writeVectorToParcel(_hidl_request, specifiers);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(160, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startNetworkScan_1_5(int serial, NetworkScanRequest request) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            request.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(161, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCall_1_5(int serial, int accessNetwork, DataProfileInfo dataProfileInfo, boolean roamingAllowed, int reason, ArrayList<LinkAddress> addresses, ArrayList<String> dnses) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(accessNetwork);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeInt32(reason);
            LinkAddress.writeVectorToParcel(_hidl_request, addresses);
            _hidl_request.writeStringVector(dnses);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(162, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApn_1_5(int serial, DataProfileInfo dataProfileInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dataProfileInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(163, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfile_1_5(int serial, ArrayList<DataProfileInfo> profiles) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            DataProfileInfo.writeVectorToParcel(_hidl_request, profiles);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(164, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPower_1_5(int serial, boolean powerOn, boolean forEmergencyCall, boolean preferredForEmergencyCall) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(powerOn);
            _hidl_request.writeBool(forEmergencyCall);
            _hidl_request.writeBool(preferredForEmergencyCall);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(165, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilter_1_5(int serial, int indicationFilter) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(indicationFilter);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(166, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBarringInfo(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(167, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationState_1_5(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(168, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationState_1_5(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(169, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManual_1_5(int serial, String operatorNumeric, int ran) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);
            _hidl_request.writeInt32(ran);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(170, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSmsExpectMore(int serial, CdmaSmsMessage sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(171, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplySimDepersonalization(int serial, int persoType, String controlKey) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(persoType);
            _hidl_request.writeString(controlKey);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(172, _hidl_request, _hidl_reply, 1);
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
            return new ArrayList<>(Arrays.asList(new String[]{IRadio.kInterfaceName, android.hardware.radio.V1_4.IRadio.kInterfaceName, android.hardware.radio.V1_3.IRadio.kInterfaceName, android.hardware.radio.V1_2.IRadio.kInterfaceName, android.hardware.radio.V1_1.IRadio.kInterfaceName, android.hardware.radio.V1_0.IRadio.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{-76, 84, -33, -123, 52, 65, -63, 47, 110, 66, 94, -118, 96, -35, 41, -3, -94, 15, 94, 110, 57, -71, 61, 17, 3, -28, -77, 116, -107, -37, 56, -86}, new byte[]{-17, 74, -73, 65, -9, -25, 118, 47, -76, 94, 46, 36, -54, -125, -121, 31, 114, 0, 108, -32, 95, 87, -86, -102, -35, -59, 116, -119, 61, -46, -104, 114}, new byte[]{-95, -58, -80, 118, 27, -53, -119, -42, -65, 21, -95, 86, -7, 48, 107, Byte.MIN_VALUE, -112, -77, -87, 22, -95, 95, -22, 22, -119, -76, -80, -63, 115, -114, 56, 47}, new byte[]{43, 90, -2, -10, -114, 62, 47, -15, -38, -74, 62, 79, 46, -27, 115, 55, -17, 38, 53, -20, -127, 47, 73, 8, 12, -83, -4, -23, 102, -45, 59, 82}, new byte[]{-122, -5, 7, -102, 96, 11, 35, 1, -89, 82, 36, -99, -5, -4, 83, -104, 58, 121, 93, 117, 47, 17, -86, -68, -74, -125, 21, -95, -119, -10, -55, -94}, new byte[]{-49, -86, -80, -28, 92, 93, 123, 53, -107, 3, 45, 100, -99, -94, -98, -41, 18, -23, 32, -7, 86, -63, 54, 113, -17, -45, 86, 2, -6, -127, -55, 35}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setResponseFunctions(IRadioResponse.asInterface(_hidl_request.readStrongBinder()), IRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getIccCardStatus(_hidl_request.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPinForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPukForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPin2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPuk2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    changeIccPinForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    changeIccPin2ForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyNetworkDepersonalization(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCurrentCalls(_hidl_request.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial = _hidl_request.readInt32();
                    Dial dialInfo = new Dial();
                    dialInfo.readFromParcel(hwParcel);
                    dial(serial, dialInfo);
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getImsiForApp(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangup(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangupWaitingOrBackground(_hidl_request.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangupForegroundResumeBackground(_hidl_request.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    switchWaitingOrHoldingAndActive(_hidl_request.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    conference(_hidl_request.readInt32());
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    rejectCall(_hidl_request.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getLastCallFailCause(_hidl_request.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getSignalStrength(_hidl_request.readInt32());
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getVoiceRegistrationState(_hidl_request.readInt32());
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDataRegistrationState(_hidl_request.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getOperator(_hidl_request.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setRadioPower(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendDtmf(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial2 = _hidl_request.readInt32();
                    GsmSmsMessage message = new GsmSmsMessage();
                    message.readFromParcel(hwParcel);
                    sendSms(serial2, message);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial3 = _hidl_request.readInt32();
                    GsmSmsMessage message2 = new GsmSmsMessage();
                    message2.readFromParcel(hwParcel);
                    sendSMSExpectMore(serial3, message2);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial4 = _hidl_request.readInt32();
                    int radioTechnology = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo = new DataProfileInfo();
                    dataProfileInfo.readFromParcel(hwParcel);
                    setupDataCall(serial4, radioTechnology, dataProfileInfo, _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial5 = _hidl_request.readInt32();
                    IccIo iccIo = new IccIo();
                    iccIo.readFromParcel(hwParcel);
                    iccIOForApp(serial5, iccIo);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendUssd(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    cancelPendingUssd(_hidl_request.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getClir(_hidl_request.readInt32());
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setClir(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial6 = _hidl_request.readInt32();
                    CallForwardInfo callInfo = new CallForwardInfo();
                    callInfo.readFromParcel(hwParcel);
                    getCallForwardStatus(serial6, callInfo);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial7 = _hidl_request.readInt32();
                    CallForwardInfo callInfo2 = new CallForwardInfo();
                    callInfo2.readFromParcel(hwParcel);
                    setCallForward(serial7, callInfo2);
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCallWaiting(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCallWaiting(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acknowledgeLastIncomingGsmSms(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acceptCall(_hidl_request.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deactivateDataCall(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getFacilityLockForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setFacilityLockForApp(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readBool(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setBarringPassword(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getNetworkSelectionMode(_hidl_request.readInt32());
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setNetworkSelectionModeAutomatic(_hidl_request.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setNetworkSelectionModeManual(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAvailableNetworks(_hidl_request.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    startDtmf(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    stopDtmf(_hidl_request.readInt32());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getBasebandVersion(_hidl_request.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    separateConnection(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setMute(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getMute(_hidl_request.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getClip(_hidl_request.readInt32());
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDataCallList(_hidl_request.readInt32());
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSuppServiceNotifications(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial8 = _hidl_request.readInt32();
                    SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
                    smsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToSim(serial8, smsWriteArgs);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deleteSmsOnSim(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setBandMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAvailableBandModes(_hidl_request.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendEnvelope(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendTerminalResponseToSim(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    handleStkCallSetupRequestFromSim(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    explicitCallTransfer(_hidl_request.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setPreferredNetworkType(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getPreferredNetworkType(_hidl_request.readInt32());
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getNeighboringCids(_hidl_request.readInt32());
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setLocationUpdates(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case LastCallFailCause.REQUESTED_FACILITY_NOT_IMPLEMENTED:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaSubscriptionSource(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaRoamingPreference(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaRoamingPreference(_hidl_request.readInt32());
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setTTYMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getTTYMode(_hidl_request.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setPreferredVoicePrivacy(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getPreferredVoicePrivacy(_hidl_request.readInt32());
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendCDMAFeatureCode(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendBurstDtmf(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial9 = _hidl_request.readInt32();
                    CdmaSmsMessage sms = new CdmaSmsMessage();
                    sms.readFromParcel(hwParcel);
                    sendCdmaSms(serial9, sms);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial10 = _hidl_request.readInt32();
                    CdmaSmsAck smsAck = new CdmaSmsAck();
                    smsAck.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSms(serial10, smsAck);
                    return;
                case NgranBands.BAND_80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getGsmBroadcastConfig(_hidl_request.readInt32());
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setGsmBroadcastConfig(_hidl_request.readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case NgranBands.BAND_82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setGsmBroadcastActivation(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaBroadcastConfig(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaBroadcastConfig(_hidl_request.readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request));
                    return;
                case EutranBands.BAND_85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaBroadcastActivation(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCDMASubscription(_hidl_request.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial11 = _hidl_request.readInt32();
                    CdmaSmsWriteArgs cdmaSms = new CdmaSmsWriteArgs();
                    cdmaSms.readFromParcel(hwParcel);
                    writeSmsToRuim(serial11, cdmaSms);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deleteSmsOnRuim(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NgranBands.BAND_89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDeviceIdentity(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    exitEmergencyCallbackMode(_hidl_request.readInt32());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getSmscAddress(_hidl_request.readInt32());
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSmscAddress(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case NgranBands.BAND_93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    reportSmsMemoryStatus(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case NgranBands.BAND_94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    reportStkServiceIsRunning(_hidl_request.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaSubscriptionSource(_hidl_request.readInt32());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestIsimAuthentication(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acknowledgeIncomingGsmSmsWithPdu(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readString());
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendEnvelopeWithStatus(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getVoiceRadioTechnology(_hidl_request.readInt32());
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCellInfoList(_hidl_request.readInt32());
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCellInfoListRate(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial12 = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo2 = new DataProfileInfo();
                    dataProfileInfo2.readFromParcel(hwParcel);
                    setInitialAttachApn(serial12, dataProfileInfo2, _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getImsRegistrationState(_hidl_request.readInt32());
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial13 = _hidl_request.readInt32();
                    ImsSmsMessage message3 = new ImsSmsMessage();
                    message3.readFromParcel(hwParcel);
                    sendImsSms(serial13, message3);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial14 = _hidl_request.readInt32();
                    SimApdu message4 = new SimApdu();
                    message4.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannel(serial14, message4);
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    iccOpenLogicalChannel(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ /*107*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    iccCloseLogicalChannel(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP /*108*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial15 = _hidl_request.readInt32();
                    SimApdu message5 = new SimApdu();
                    message5.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannel(serial15, message5);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ /*109*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvReadItem(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial16 = _hidl_request.readInt32();
                    NvWriteItem item = new NvWriteItem();
                    item.readFromParcel(hwParcel);
                    nvWriteItem(serial16, item);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvWriteCdmaPrl(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvResetConfig(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial17 = _hidl_request.readInt32();
                    SelectUiccSub uiccSub = new SelectUiccSub();
                    uiccSub.readFromParcel(hwParcel);
                    setUiccSubscription(serial17, uiccSub);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setDataAllowed(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getHardwareConfig(_hidl_request.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestIccSimAuthentication(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setDataProfile(_hidl_request.readInt32(), DataProfileInfo.readVectorFromParcel(_hidl_request), _hidl_request.readBool());
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestShutdown(_hidl_request.readInt32());
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getRadioCapability(_hidl_request.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial18 = _hidl_request.readInt32();
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(hwParcel);
                    setRadioCapability(serial18, rc);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    startLceService(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    stopLceService(_hidl_request.readInt32());
                    return;
                case DataCallFailCause.INVALID_DNS_ADDR:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    pullLceData(_hidl_request.readInt32());
                    return;
                case DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getModemActivityInfo(_hidl_request.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int serial19 = _hidl_request.readInt32();
                    boolean allAllowed = _hidl_request.readBool();
                    CarrierRestrictions carriers = new CarrierRestrictions();
                    carriers.readFromParcel(hwParcel);
                    setAllowedCarriers(serial19, allAllowed, carriers);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAllowedCarriers(_hidl_request.readInt32());
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendDeviceState(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setIndicationFilter(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSimCardPower(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    responseAcknowledgement();
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int serial20 = _hidl_request.readInt32();
                    ImsiEncryptionInfo imsiEncryptionInfo = new ImsiEncryptionInfo();
                    imsiEncryptionInfo.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryption(serial20, imsiEncryptionInfo);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    setSimCardPower_1_1(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_CW5 /*133*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int serial21 = _hidl_request.readInt32();
                    NetworkScanRequest request = new NetworkScanRequest();
                    request.readFromParcel(hwParcel);
                    startNetworkScan(serial21, request);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    stopNetworkScan(_hidl_request.readInt32());
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int serial22 = _hidl_request.readInt32();
                    KeepaliveRequest keepalive = new KeepaliveRequest();
                    keepalive.readFromParcel(hwParcel);
                    startKeepalive(serial22, keepalive);
                    return;
                case 136:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    stopKeepalive(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DSW /*137*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    int serial23 = _hidl_request.readInt32();
                    android.hardware.radio.V1_2.NetworkScanRequest request2 = new android.hardware.radio.V1_2.NetworkScanRequest();
                    request2.readFromParcel(hwParcel);
                    startNetworkScan_1_2(serial23, request2);
                    return;
                case Cea708CCParser.Const.CODE_C1_HDW /*138*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setIndicationFilter_1_2(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_TGW /*139*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setSignalStrengthReportingCriteria(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32Vector(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLW /*140*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setLinkCapacityReportingCriteria(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32Vector(), _hidl_request.readInt32Vector(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLY /*141*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    int serial24 = _hidl_request.readInt32();
                    int accessNetwork = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo3 = new DataProfileInfo();
                    dataProfileInfo3.readFromParcel(hwParcel);
                    setupDataCall_1_2(serial24, accessNetwork, dataProfileInfo3, _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readInt32(), _hidl_request.readStringVector(), _hidl_request.readStringVector());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLC /*142*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    deactivateDataCall_1_2(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 143:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    setSystemSelectionChannels(_hidl_request.readInt32(), _hidl_request.readBool(), RadioAccessSpecifier.readVectorFromParcel(_hidl_request));
                    return;
                case Cea708CCParser.Const.CODE_C1_SPA /*144*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    enableModem(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPC /*145*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    getModemStackStatus(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPL /*146*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int serial25 = _hidl_request.readInt32();
                    int accessNetwork2 = _hidl_request.readInt32();
                    android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo4 = new android.hardware.radio.V1_4.DataProfileInfo();
                    dataProfileInfo4.readFromParcel(hwParcel);
                    setupDataCall_1_4(serial25, accessNetwork2, dataProfileInfo4, _hidl_request.readBool(), _hidl_request.readInt32(), _hidl_request.readStringVector(), _hidl_request.readStringVector());
                    return;
                case 147:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int serial26 = _hidl_request.readInt32();
                    android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo5 = new android.hardware.radio.V1_4.DataProfileInfo();
                    dataProfileInfo5.readFromParcel(hwParcel);
                    setInitialAttachApn_1_4(serial26, dataProfileInfo5);
                    return;
                case 148:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    setDataProfile_1_4(_hidl_request.readInt32(), android.hardware.radio.V1_4.DataProfileInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 149:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int serial27 = _hidl_request.readInt32();
                    Dial dialInfo2 = new Dial();
                    dialInfo2.readFromParcel(hwParcel);
                    emergencyDial(serial27, dialInfo2, _hidl_request.readInt32(), _hidl_request.readStringVector(), _hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 150:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int serial28 = _hidl_request.readInt32();
                    android.hardware.radio.V1_2.NetworkScanRequest request3 = new android.hardware.radio.V1_2.NetworkScanRequest();
                    request3.readFromParcel(hwParcel);
                    startNetworkScan_1_4(serial28, request3);
                    return;
                case Cea708CCParser.Const.CODE_C1_SWA /*151*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getPreferredNetworkTypeBitmap(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF0 /*152*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    setPreferredNetworkTypeBitmap(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF1 /*153*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int serial29 = _hidl_request.readInt32();
                    CarrierRestrictionsWithPriority carriers2 = new CarrierRestrictionsWithPriority();
                    carriers2.readFromParcel(hwParcel);
                    setAllowedCarriers_1_4(serial29, carriers2, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF2 /*154*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getAllowedCarriers_1_4(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF3 /*155*/:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getSignalStrength_1_4(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF4 /*156*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial30 = _hidl_request.readInt32();
                    SignalThresholdInfo signalThresholdInfo = new SignalThresholdInfo();
                    signalThresholdInfo.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteria_1_5(serial30, signalThresholdInfo, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF5 /*157*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setLinkCapacityReportingCriteria_1_5(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32Vector(), _hidl_request.readInt32Vector(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF6 /*158*/:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    enableUiccApplications(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 159:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    areUiccApplicationsEnabled(_hidl_request.readInt32());
                    return;
                case 160:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSystemSelectionChannels_1_5(_hidl_request.readInt32(), _hidl_request.readBool(), RadioAccessSpecifier.readVectorFromParcel(_hidl_request));
                    return;
                case 161:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial31 = _hidl_request.readInt32();
                    NetworkScanRequest request4 = new NetworkScanRequest();
                    request4.readFromParcel(hwParcel);
                    startNetworkScan_1_5(serial31, request4);
                    return;
                case 162:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial32 = _hidl_request.readInt32();
                    int accessNetwork3 = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo6 = new DataProfileInfo();
                    dataProfileInfo6.readFromParcel(hwParcel);
                    setupDataCall_1_5(serial32, accessNetwork3, dataProfileInfo6, _hidl_request.readBool(), _hidl_request.readInt32(), LinkAddress.readVectorFromParcel(_hidl_request), _hidl_request.readStringVector());
                    return;
                case 163:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial33 = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo7 = new DataProfileInfo();
                    dataProfileInfo7.readFromParcel(hwParcel);
                    setInitialAttachApn_1_5(serial33, dataProfileInfo7);
                    return;
                case 164:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataProfile_1_5(_hidl_request.readInt32(), DataProfileInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 165:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setRadioPower_1_5(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 166:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setIndicationFilter_1_5(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 167:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getBarringInfo(_hidl_request.readInt32());
                    return;
                case 168:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRegistrationState_1_5(_hidl_request.readInt32());
                    return;
                case 169:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataRegistrationState_1_5(_hidl_request.readInt32());
                    return;
                case 170:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNetworkSelectionModeManual_1_5(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 171:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int serial34 = _hidl_request.readInt32();
                    CdmaSmsMessage sms2 = new CdmaSmsMessage();
                    sms2.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMore(serial34, sms2);
                    return;
                case 172:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplySimDepersonalization(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
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