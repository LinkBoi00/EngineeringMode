package android.hardware.radio.V1_3;

import android.hardware.radio.V1_0.CdmaCallWaiting;
import android.hardware.radio.V1_0.CdmaInformationRecords;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.PcoDataInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SignalStrength;
import android.hardware.radio.V1_0.SimRefreshResult;
import android.hardware.radio.V1_0.StkCcUnsolSsResult;
import android.hardware.radio.V1_0.SuppSvcNotification;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_1.NetworkScanResult;
import android.hardware.radio.V1_2.LinkCapacityEstimate;
import android.hardware.radio.V1_2.PhysicalChannelConfig;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IRadioIndication extends android.hardware.radio.V1_2.IRadioIndication {
    public static final String kInterfaceName = "android.hardware.radio@1.3::IRadioIndication";

    IHwBinder asBinder();

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IRadioIndication asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IRadioIndication)) {
            return (IRadioIndication) iface;
        }
        IRadioIndication proxy = new Proxy(binder);
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

    static IRadioIndication castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IRadioIndication getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IRadioIndication getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IRadioIndication getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IRadioIndication getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadioIndication {
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
                return "[class or subclass of android.hardware.radio@1.3::IRadioIndication]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void radioStateChanged(int type, int radioState) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(radioState);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void callStateChanged(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void networkStateChanged(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void newSms(int type, ArrayList<Byte> pdu) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(pdu);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void newSmsStatusReport(int type, ArrayList<Byte> pdu) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(pdu);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void newSmsOnSim(int type, int recordNumber) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(recordNumber);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void onUssd(int type, int modeType, String msg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(modeType);
            _hidl_request.writeString(msg);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nitzTimeReceived(int type, String nitzTime, long receivedTime) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(nitzTime);
            _hidl_request.writeInt64(receivedTime);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void currentSignalStrength(int type, SignalStrength signalStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            signalStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dataCallListChanged(int type, ArrayList<SetupDataCallResult> dcList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            SetupDataCallResult.writeVectorToParcel(_hidl_request, dcList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void suppSvcNotify(int type, SuppSvcNotification suppSvc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            suppSvc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stkSessionEnd(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stkProactiveCommand(int type, String cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stkEventNotify(int type, String cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stkCallSetup(int type, long timeout) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt64(timeout);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void simSmsStorageFull(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void simRefresh(int type, SimRefreshResult refreshResult) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            refreshResult.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void callRing(int type, boolean isGsm, CdmaSignalInfoRecord record) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(isGsm);
            record.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void simStatusChanged(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaNewSms(int type, CdmaSmsMessage msg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            msg.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void newBroadcastSms(int type, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaRuimSmsStorageFull(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void restrictedStateChanged(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enterEmergencyCallbackMode(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaCallWaiting(int type, CdmaCallWaiting callWaitingRecord) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            callWaitingRecord.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaOtaProvisionStatus(int type, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaInfoRec(int type, CdmaInformationRecords records) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            records.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void indicateRingbackTone(int type, boolean start) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(start);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resendIncallMute(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaSubscriptionSourceChanged(int type, int cdmaSource) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(cdmaSource);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cdmaPrlChanged(int type, int version) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(version);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void exitEmergencyCallbackMode(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rilConnected(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void voiceRadioTechChanged(int type, int rat) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(rat);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cellInfoList(int type, ArrayList<CellInfo> records) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            CellInfo.writeVectorToParcel(_hidl_request, records);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void imsNetworkStateChanged(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void subscriptionStatusChanged(int type, boolean activate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(activate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void srvccStateNotify(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hardwareConfigChanged(int type, ArrayList<HardwareConfig> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HardwareConfig.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void radioCapabilityIndication(int type, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void onSupplementaryServiceIndication(int type, StkCcUnsolSsResult ss) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            ss.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stkCallControlAlphaNotify(int type, String alpha) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(alpha);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void lceData(int type, LceDataInfo lce) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            lce.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pcoData(int type, PcoDataInfo pco) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            pco.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void modemReset(int type, String reason) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(reason);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void carrierInfoForImsiEncryption(int info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(info);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void networkScanResult(int type, NetworkScanResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void keepaliveStatus(int type, KeepaliveStatus status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            status.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void networkScanResult_1_2(int type, android.hardware.radio.V1_2.NetworkScanResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cellInfoList_1_2(int type, ArrayList<android.hardware.radio.V1_2.CellInfo> records) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            android.hardware.radio.V1_2.CellInfo.writeVectorToParcel(_hidl_request, records);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void currentLinkCapacityEstimate(int type, LinkCapacityEstimate lce) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            lce.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void currentPhysicalChannelConfigs(int type, ArrayList<PhysicalChannelConfig> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            PhysicalChannelConfig.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void currentSignalStrength_1_2(int type, android.hardware.radio.V1_2.SignalStrength signalStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            signalStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
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

    public static abstract class Stub extends HwBinder implements IRadioIndication {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IRadioIndication.kInterfaceName, android.hardware.radio.V1_2.IRadioIndication.kInterfaceName, android.hardware.radio.V1_1.IRadioIndication.kInterfaceName, android.hardware.radio.V1_0.IRadioIndication.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadioIndication.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{-23, -48, -15, 26, 82, 113, 95, 90, 41, -40, -98, 45, -114, 46, 33, -37, 30, 22, -92, 49, 116, -81, 107, -99, 81, -90, 45, 112, 92, -38, 20, 85}, new byte[]{-51, -89, 82, -82, -85, -86, -68, 32, 72, 106, -126, -84, 87, -93, -35, 16, 119, -123, -64, 6, 9, 74, 52, -101, -59, -30, 36, -24, -86, 34, -95, 124}, new byte[]{-4, -59, -56, -56, -117, -123, -87, -10, 63, -70, 103, -39, -26, 116, -38, 70, 108, 114, -87, -116, -94, -121, -13, 67, -5, 87, 33, -48, -104, 113, 63, -122}, new byte[]{-119, -41, -113, -92, -101, 9, -30, -13, 24, 18, -69, 99, -31, -65, -84, 43, -13, 24, -87, 86, 20, 115, -58, -80, -19, 105, 4, -50, 24, 55, 125, 84}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
            if (IRadioIndication.kInterfaceName.equals(descriptor)) {
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
            switch (_hidl_code) {
                case 1:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    radioStateChanged(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 2:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    callStateChanged(_hidl_request.readInt32());
                    return;
                case 3:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    networkStateChanged(_hidl_request.readInt32());
                    return;
                case 4:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    newSms(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 5:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    newSmsStatusReport(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 6:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    newSmsOnSim(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 7:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    onUssd(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 8:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    nitzTimeReceived(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt64());
                    return;
                case 9:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type = _hidl_request.readInt32();
                    SignalStrength signalStrength = new SignalStrength();
                    signalStrength.readFromParcel(_hidl_request);
                    currentSignalStrength(type, signalStrength);
                    return;
                case 10:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    dataCallListChanged(_hidl_request.readInt32(), SetupDataCallResult.readVectorFromParcel(_hidl_request));
                    return;
                case 11:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type2 = _hidl_request.readInt32();
                    SuppSvcNotification suppSvc = new SuppSvcNotification();
                    suppSvc.readFromParcel(_hidl_request);
                    suppSvcNotify(type2, suppSvc);
                    return;
                case 12:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    stkSessionEnd(_hidl_request.readInt32());
                    return;
                case 13:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    stkProactiveCommand(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 14:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    stkEventNotify(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 15:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    stkCallSetup(_hidl_request.readInt32(), _hidl_request.readInt64());
                    return;
                case 16:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    simSmsStorageFull(_hidl_request.readInt32());
                    return;
                case 17:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type3 = _hidl_request.readInt32();
                    SimRefreshResult refreshResult = new SimRefreshResult();
                    refreshResult.readFromParcel(_hidl_request);
                    simRefresh(type3, refreshResult);
                    return;
                case 18:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type4 = _hidl_request.readInt32();
                    boolean isGsm = _hidl_request.readBool();
                    CdmaSignalInfoRecord record = new CdmaSignalInfoRecord();
                    record.readFromParcel(_hidl_request);
                    callRing(type4, isGsm, record);
                    return;
                case 19:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    simStatusChanged(_hidl_request.readInt32());
                    return;
                case 20:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type5 = _hidl_request.readInt32();
                    CdmaSmsMessage msg = new CdmaSmsMessage();
                    msg.readFromParcel(_hidl_request);
                    cdmaNewSms(type5, msg);
                    return;
                case 21:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    newBroadcastSms(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 22:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    cdmaRuimSmsStorageFull(_hidl_request.readInt32());
                    return;
                case 23:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    restrictedStateChanged(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 24:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    enterEmergencyCallbackMode(_hidl_request.readInt32());
                    return;
                case 25:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type6 = _hidl_request.readInt32();
                    CdmaCallWaiting callWaitingRecord = new CdmaCallWaiting();
                    callWaitingRecord.readFromParcel(_hidl_request);
                    cdmaCallWaiting(type6, callWaitingRecord);
                    return;
                case 26:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    cdmaOtaProvisionStatus(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 27:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type7 = _hidl_request.readInt32();
                    CdmaInformationRecords records = new CdmaInformationRecords();
                    records.readFromParcel(_hidl_request);
                    cdmaInfoRec(type7, records);
                    return;
                case 28:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    indicateRingbackTone(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 29:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    resendIncallMute(_hidl_request.readInt32());
                    return;
                case 30:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    cdmaSubscriptionSourceChanged(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 31:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    cdmaPrlChanged(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 32:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    exitEmergencyCallbackMode(_hidl_request.readInt32());
                    return;
                case 33:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    rilConnected(_hidl_request.readInt32());
                    return;
                case 34:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    voiceRadioTechChanged(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 35:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    cellInfoList(_hidl_request.readInt32(), CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 36:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    imsNetworkStateChanged(_hidl_request.readInt32());
                    return;
                case 37:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    subscriptionStatusChanged(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 38:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    srvccStateNotify(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 39:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    hardwareConfigChanged(_hidl_request.readInt32(), HardwareConfig.readVectorFromParcel(_hidl_request));
                    return;
                case 40:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type8 = _hidl_request.readInt32();
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(_hidl_request);
                    radioCapabilityIndication(type8, rc);
                    return;
                case 41:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type9 = _hidl_request.readInt32();
                    StkCcUnsolSsResult ss = new StkCcUnsolSsResult();
                    ss.readFromParcel(_hidl_request);
                    onSupplementaryServiceIndication(type9, ss);
                    return;
                case 42:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    stkCallControlAlphaNotify(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 43:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type10 = _hidl_request.readInt32();
                    LceDataInfo lce = new LceDataInfo();
                    lce.readFromParcel(_hidl_request);
                    lceData(type10, lce);
                    return;
                case 44:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    int type11 = _hidl_request.readInt32();
                    PcoDataInfo pco = new PcoDataInfo();
                    pco.readFromParcel(_hidl_request);
                    pcoData(type11, pco);
                    return;
                case 45:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioIndication.kInterfaceName);
                    modemReset(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 46:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
                    carrierInfoForImsiEncryption(_hidl_request.readInt32());
                    return;
                case 47:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
                    int type12 = _hidl_request.readInt32();
                    NetworkScanResult result = new NetworkScanResult();
                    result.readFromParcel(_hidl_request);
                    networkScanResult(type12, result);
                    return;
                case 48:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_1.IRadioIndication.kInterfaceName);
                    int type13 = _hidl_request.readInt32();
                    KeepaliveStatus status = new KeepaliveStatus();
                    status.readFromParcel(_hidl_request);
                    keepaliveStatus(type13, status);
                    return;
                case 49:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
                    int type14 = _hidl_request.readInt32();
                    android.hardware.radio.V1_2.NetworkScanResult result2 = new android.hardware.radio.V1_2.NetworkScanResult();
                    result2.readFromParcel(_hidl_request);
                    networkScanResult_1_2(type14, result2);
                    return;
                case 50:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
                    cellInfoList_1_2(_hidl_request.readInt32(), android.hardware.radio.V1_2.CellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case 51:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
                    int type15 = _hidl_request.readInt32();
                    LinkCapacityEstimate lce2 = new LinkCapacityEstimate();
                    lce2.readFromParcel(_hidl_request);
                    currentLinkCapacityEstimate(type15, lce2);
                    return;
                case 52:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
                    currentPhysicalChannelConfigs(_hidl_request.readInt32(), PhysicalChannelConfig.readVectorFromParcel(_hidl_request));
                    return;
                case 53:
                    _hidl_request.enforceInterface(android.hardware.radio.V1_2.IRadioIndication.kInterfaceName);
                    int type16 = _hidl_request.readInt32();
                    android.hardware.radio.V1_2.SignalStrength signalStrength2 = new android.hardware.radio.V1_2.SignalStrength();
                    signalStrength2.readFromParcel(_hidl_request);
                    currentSignalStrength_1_2(type16, signalStrength2);
                    return;
                case 256067662:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    return;
                case 256131655:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    debug(_hidl_request.readNativeHandle(), _hidl_request.readStringVector());
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    return;
                case 256398152:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    _hidl_reply.writeStatus(0);
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
                    _hidl_reply.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256921159:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ping();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    _hidl_reply.writeStatus(0);
                    _hidl_out_info.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
                default:
                    return;
            }
        }
    }
}
