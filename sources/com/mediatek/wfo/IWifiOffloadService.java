package com.mediatek.wfo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mediatek.wfo.IWifiOffloadListener;

public interface IWifiOffloadService extends IInterface {
    public static final String DESCRIPTOR = "com.mediatek.wfo.IWifiOffloadService";

    void factoryReset() throws RemoteException;

    DisconnectCause getDisconnectCause(int i) throws RemoteException;

    String[] getMccMncAllowList(int i) throws RemoteException;

    int getRatType(int i) throws RemoteException;

    boolean isWifiConnected() throws RemoteException;

    void registerForHandoverEvent(IWifiOffloadListener iWifiOffloadListener) throws RemoteException;

    void setEpdgFqdn(int i, String str, boolean z) throws RemoteException;

    boolean setMccMncAllowList(String[] strArr) throws RemoteException;

    boolean setWifiOff() throws RemoteException;

    void unregisterForHandoverEvent(IWifiOffloadListener iWifiOffloadListener) throws RemoteException;

    void updateCallState(int i, int i2, int i3, int i4) throws RemoteException;

    void updateRadioState(int i, int i2) throws RemoteException;

    public static class Default implements IWifiOffloadService {
        public void registerForHandoverEvent(IWifiOffloadListener listener) throws RemoteException {
        }

        public void unregisterForHandoverEvent(IWifiOffloadListener listener) throws RemoteException {
        }

        public int getRatType(int simIdx) throws RemoteException {
            return 0;
        }

        public DisconnectCause getDisconnectCause(int simIdx) throws RemoteException {
            return null;
        }

        public void setEpdgFqdn(int simIdx, String fqdn, boolean wfcEnabled) throws RemoteException {
        }

        public void updateCallState(int simIdx, int callId, int callType, int callState) throws RemoteException {
        }

        public boolean isWifiConnected() throws RemoteException {
            return false;
        }

        public void updateRadioState(int simIdx, int radioState) throws RemoteException {
        }

        public boolean setMccMncAllowList(String[] allowList) throws RemoteException {
            return false;
        }

        public String[] getMccMncAllowList(int mode) throws RemoteException {
            return null;
        }

        public void factoryReset() throws RemoteException {
        }

        public boolean setWifiOff() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWifiOffloadService {
        static final int TRANSACTION_factoryReset = 11;
        static final int TRANSACTION_getDisconnectCause = 4;
        static final int TRANSACTION_getMccMncAllowList = 10;
        static final int TRANSACTION_getRatType = 3;
        static final int TRANSACTION_isWifiConnected = 7;
        static final int TRANSACTION_registerForHandoverEvent = 1;
        static final int TRANSACTION_setEpdgFqdn = 5;
        static final int TRANSACTION_setMccMncAllowList = 9;
        static final int TRANSACTION_setWifiOff = 12;
        static final int TRANSACTION_unregisterForHandoverEvent = 2;
        static final int TRANSACTION_updateCallState = 6;
        static final int TRANSACTION_updateRadioState = 8;

        public Stub() {
            attachInterface(this, IWifiOffloadService.DESCRIPTOR);
        }

        public static IWifiOffloadService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWifiOffloadService.DESCRIPTOR);
            if (iin == null || !(iin instanceof IWifiOffloadService)) {
                return new Proxy(obj);
            }
            return (IWifiOffloadService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1598968902:
                    reply.writeString(IWifiOffloadService.DESCRIPTOR);
                    return true;
                default:
                    boolean _arg2 = false;
                    switch (code) {
                        case 1:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            registerForHandoverEvent(IWifiOffloadListener.Stub.asInterface(data.readStrongBinder()));
                            reply.writeNoException();
                            return true;
                        case 2:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            unregisterForHandoverEvent(IWifiOffloadListener.Stub.asInterface(data.readStrongBinder()));
                            reply.writeNoException();
                            return true;
                        case 3:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            int _result = getRatType(data.readInt());
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 4:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            DisconnectCause _result2 = getDisconnectCause(data.readInt());
                            reply.writeNoException();
                            if (_result2 != null) {
                                reply.writeInt(1);
                                _result2.writeToParcel(reply, 1);
                            } else {
                                reply.writeInt(0);
                            }
                            return true;
                        case 5:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            int _arg0 = data.readInt();
                            String _arg1 = data.readString();
                            if (data.readInt() != 0) {
                                _arg2 = true;
                            }
                            setEpdgFqdn(_arg0, _arg1, _arg2);
                            reply.writeNoException();
                            return true;
                        case 6:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            updateCallState(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                            reply.writeNoException();
                            return true;
                        case 7:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            boolean _result3 = isWifiConnected();
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 8:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            updateRadioState(data.readInt(), data.readInt());
                            reply.writeNoException();
                            return true;
                        case 9:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            boolean _result4 = setMccMncAllowList(data.createStringArray());
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 10:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            String[] _result5 = getMccMncAllowList(data.readInt());
                            reply.writeNoException();
                            reply.writeStringArray(_result5);
                            return true;
                        case 11:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            factoryReset();
                            reply.writeNoException();
                            return true;
                        case 12:
                            data.enforceInterface(IWifiOffloadService.DESCRIPTOR);
                            boolean _result6 = setWifiOff();
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IWifiOffloadService {
            public static IWifiOffloadService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWifiOffloadService.DESCRIPTOR;
            }

            public void registerForHandoverEvent(IWifiOffloadListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerForHandoverEvent(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterForHandoverEvent(IWifiOffloadListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterForHandoverEvent(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRatType(int simIdx) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(simIdx);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRatType(simIdx);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DisconnectCause getDisconnectCause(int simIdx) throws RemoteException {
                DisconnectCause _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(simIdx);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisconnectCause(simIdx);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DisconnectCause.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setEpdgFqdn(int simIdx, String fqdn, boolean wfcEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(simIdx);
                    _data.writeString(fqdn);
                    _data.writeInt(wfcEnabled ? 1 : 0);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setEpdgFqdn(simIdx, fqdn, wfcEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateCallState(int simIdx, int callId, int callType, int callState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(simIdx);
                    _data.writeInt(callId);
                    _data.writeInt(callType);
                    _data.writeInt(callState);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateCallState(simIdx, callId, callType, callState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWifiConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWifiConnected();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateRadioState(int simIdx, int radioState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(simIdx);
                    _data.writeInt(radioState);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateRadioState(simIdx, radioState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setMccMncAllowList(String[] allowList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeStringArray(allowList);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMccMncAllowList(allowList);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getMccMncAllowList(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    _data.writeInt(mode);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMccMncAllowList(mode);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().factoryReset();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setWifiOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWifiOffloadService.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWifiOff();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWifiOffloadService impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IWifiOffloadService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
