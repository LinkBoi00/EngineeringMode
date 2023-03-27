package com.mediatek.wfo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mediatek.wfo.IWifiOffloadService;

public interface IMwiService extends IInterface {
    public static final String DESCRIPTOR = "com.mediatek.wfo.IMwiService";

    IWifiOffloadService getWfcHandlerInterface() throws RemoteException;

    public static class Default implements IMwiService {
        public IWifiOffloadService getWfcHandlerInterface() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMwiService {
        static final int TRANSACTION_getWfcHandlerInterface = 1;

        public Stub() {
            attachInterface(this, IMwiService.DESCRIPTOR);
        }

        public static IMwiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMwiService.DESCRIPTOR);
            if (iin == null || !(iin instanceof IMwiService)) {
                return new Proxy(obj);
            }
            return (IMwiService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1598968902:
                    reply.writeString(IMwiService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            data.enforceInterface(IMwiService.DESCRIPTOR);
                            IWifiOffloadService _result = getWfcHandlerInterface();
                            reply.writeNoException();
                            reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IMwiService {
            public static IMwiService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMwiService.DESCRIPTOR;
            }

            public IWifiOffloadService getWfcHandlerInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMwiService.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWfcHandlerInterface();
                    }
                    _reply.readException();
                    IWifiOffloadService _result = IWifiOffloadService.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMwiService impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IMwiService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
