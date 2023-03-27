package vendor.mediatek.hardware.engineermode.V1_1;

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
import vendor.mediatek.hardware.engineermode.V1_0.IEmCallback;

public interface IEmd extends vendor.mediatek.hardware.engineermode.V1_0.IEmd {
    public static final String kInterfaceName = "vendor.mediatek.hardware.engineermode@1.1::IEmd";

    IHwBinder asBinder();

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    boolean setEmConfigure(String str, String str2) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IEmd asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IEmd)) {
            return (IEmd) iface;
        }
        IEmd proxy = new Proxy(binder);
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

    static IEmd castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IEmd getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IEmd getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IEmd getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IEmd getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IEmd {
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
                return "[class or subclass of vendor.mediatek.hardware.engineermode@1.1::IEmd]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void setCallback(IEmCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean sendToServer(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean sendToServerWithCallBack(String data, IEmCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setSmsFormat(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setCtIREngMode(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setTestSimCardType(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setPreferGprsMode(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setRadioCapabilitySwitchEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setDisableC2kCap(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setImsTestMode(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setDsbpSupport(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setVolteMalPctid(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btStartRelayer(int port, int speed) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeInt32(port);
            _hidl_request.writeInt32(speed);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btStopRelayer() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btIsBLESupport() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean btIsBLEEnhancedSupport() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btInit() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btUninit() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btDoTest(int kind, int pattern, int channel, int pocketType, int pocketTypeLen, int freq, int power) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeInt32(kind);
            _hidl_request.writeInt32(pattern);
            _hidl_request.writeInt32(channel);
            _hidl_request.writeInt32(pocketType);
            _hidl_request.writeInt32(pocketTypeLen);
            _hidl_request.writeInt32(freq);
            _hidl_request.writeInt32(power);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<Byte> btHciCommandRun(ArrayList<Byte> input) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeInt8Vector(input);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt8Vector();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean btStartNoSigRxTest(int pattern, int pockettype, int freq, int address) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeInt32(pattern);
            _hidl_request.writeInt32(pockettype);
            _hidl_request.writeInt32(freq);
            _hidl_request.writeInt32(address);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<Integer> btEndNoSigRxTest() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32Vector();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btIsComboSupport() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btPollingStart() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public int btPollingStop() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readInt32();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setMdResetDelay(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setWcnCoreDump(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setOmxVencLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setOmxVdecLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setVdecDriverLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setSvpLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setOmxCoreLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setVencDriverLogEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setModemWarningEnable(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean genMdLogFilter(String keyword, String filepath) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(keyword);
            _hidl_request.writeString(filepath);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setUsbPort(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setUsbOtgSwitch(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setEmUsbValue(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setEmUsbType(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setBypassEn(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setBypassDis(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setBypassService(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setMoms(String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean clearItemsforRsc() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean getFilePathListWithCallBack(String data, IEmCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
            _hidl_request.writeString(data);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean setEmConfigure(String name, String value) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IEmd.kInterfaceName);
            _hidl_request.writeString(name);
            _hidl_request.writeString(value);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readBool();
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

    public static abstract class Stub extends HwBinder implements IEmd {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IEmd.kInterfaceName, vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IEmd.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{99, -93, 35, -85, -5, -25, 48, 13, -112, -127, 102, -26, 71, 112, 32, -43, 73, 58, 120, -17, -118, -84, 123, -14, -27, -42, -96, 67, -65, -69, -92, -113}, new byte[]{-66, -27, 18, -107, 121, -115, -5, -113, -11, 28, 119, -105, 121, -100, 105, -29, 86, 105, -79, 32, -9, 25, 54, -117, -93, -97, -29, -30, -10, -48, 79, -100}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
            if (IEmd.kInterfaceName.equals(descriptor)) {
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
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    setCallback(IEmCallback.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success = sendToServer(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success);
                    _hidl_reply.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success2 = sendToServerWithCallBack(_hidl_request.readString(), IEmCallback.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success2);
                    _hidl_reply.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success3 = setSmsFormat(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success3);
                    _hidl_reply.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success4 = setCtIREngMode(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success4);
                    _hidl_reply.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success5 = setTestSimCardType(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success5);
                    _hidl_reply.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success6 = setPreferGprsMode(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success6);
                    _hidl_reply.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success7 = setRadioCapabilitySwitchEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success7);
                    _hidl_reply.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success8 = setDisableC2kCap(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success8);
                    _hidl_reply.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success9 = setImsTestMode(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success9);
                    _hidl_reply.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success10 = setDsbpSupport(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success10);
                    _hidl_reply.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success11 = setVolteMalPctid(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success11);
                    _hidl_reply.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result = btStartRelayer(_hidl_request.readInt32(), _hidl_request.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result);
                    _hidl_reply.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result2 = btStopRelayer();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result2);
                    _hidl_reply.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result3 = btIsBLESupport();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result3);
                    _hidl_reply.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success12 = btIsBLEEnhancedSupport();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success12);
                    _hidl_reply.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result4 = btInit();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result4);
                    _hidl_reply.send();
                    return;
                case 18:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result5 = btUninit();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result5);
                    _hidl_reply.send();
                    return;
                case 19:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result6 = btDoTest(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result6);
                    _hidl_reply.send();
                    return;
                case 20:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    ArrayList<Byte> _hidl_out_result7 = btHciCommandRun(_hidl_request.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt8Vector(_hidl_out_result7);
                    _hidl_reply.send();
                    return;
                case 21:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success13 = btStartNoSigRxTest(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success13);
                    _hidl_reply.send();
                    return;
                case 22:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    ArrayList<Integer> _hidl_out_result8 = btEndNoSigRxTest();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32Vector(_hidl_out_result8);
                    _hidl_reply.send();
                    return;
                case 23:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result9 = btIsComboSupport();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result9);
                    _hidl_reply.send();
                    return;
                case 24:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result10 = btPollingStart();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result10);
                    _hidl_reply.send();
                    return;
                case 25:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    int _hidl_out_result11 = btPollingStop();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(_hidl_out_result11);
                    _hidl_reply.send();
                    return;
                case 26:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success14 = setMdResetDelay(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success14);
                    _hidl_reply.send();
                    return;
                case 27:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success15 = setWcnCoreDump(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success15);
                    _hidl_reply.send();
                    return;
                case 28:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success16 = setOmxVencLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success16);
                    _hidl_reply.send();
                    return;
                case 29:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success17 = setOmxVdecLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success17);
                    _hidl_reply.send();
                    return;
                case 30:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success18 = setVdecDriverLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success18);
                    _hidl_reply.send();
                    return;
                case 31:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success19 = setSvpLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success19);
                    _hidl_reply.send();
                    return;
                case 32:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success20 = setOmxCoreLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success20);
                    _hidl_reply.send();
                    return;
                case 33:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success21 = setVencDriverLogEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success21);
                    _hidl_reply.send();
                    return;
                case 34:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success22 = setModemWarningEnable(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success22);
                    _hidl_reply.send();
                    return;
                case 35:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success23 = genMdLogFilter(_hidl_request.readString(), _hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success23);
                    _hidl_reply.send();
                    return;
                case 36:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success24 = setUsbPort(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success24);
                    _hidl_reply.send();
                    return;
                case 37:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success25 = setUsbOtgSwitch(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success25);
                    _hidl_reply.send();
                    return;
                case 38:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success26 = setEmUsbValue(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success26);
                    _hidl_reply.send();
                    return;
                case 39:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success27 = setEmUsbType(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success27);
                    _hidl_reply.send();
                    return;
                case 40:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success28 = setBypassEn(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success28);
                    _hidl_reply.send();
                    return;
                case 41:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success29 = setBypassDis(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success29);
                    _hidl_reply.send();
                    return;
                case 42:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success30 = setBypassService(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success30);
                    _hidl_reply.send();
                    return;
                case 43:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success31 = setMoms(_hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success31);
                    _hidl_reply.send();
                    return;
                case 44:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success32 = clearItemsforRsc();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success32);
                    _hidl_reply.send();
                    return;
                case 45:
                    hwParcel.enforceInterface(vendor.mediatek.hardware.engineermode.V1_0.IEmd.kInterfaceName);
                    boolean _hidl_out_success33 = getFilePathListWithCallBack(_hidl_request.readString(), IEmCallback.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success33);
                    _hidl_reply.send();
                    return;
                case 46:
                    hwParcel.enforceInterface(IEmd.kInterfaceName);
                    boolean _hidl_out_success34 = setEmConfigure(_hidl_request.readString(), _hidl_request.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(_hidl_out_success34);
                    _hidl_reply.send();
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
