package com.mediatek.engineermode;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.SparseArray;
import com.android.internal.telephony.RIL;
import com.mediatek.engineermode.aospradio.RadioIndication;
import java.util.ArrayList;
import java.util.Arrays;
import vendor.mediatek.hardware.mtkradioex.V3_0.IMtkRadioEx;

public class EmRadioHidl3_0 {
    private static final String[] HIDL_SERVICE_NAME = {"mtkEm1", "mtkEm2", "mtkEm3", "mtkEm4"};
    public static final String TAG = "EmRadioHidl3_0";
    public static Handler mHandler;
    private static boolean mIsRadioIndicationListen = false;
    private static MtkRadioIndication3_0 mRadioIndicationMtk = null;
    public static int mRadioIndicationType = -1;
    private static IMtkRadioEx[] mRadioProxyMtk = new IMtkRadioEx[4];
    private static MtkRadioResponseBase3_0 mRadioResponseMtk = null;
    public static SparseArray<Message> mRequestList = new SparseArray<>();
    public static int mWhat;

    private static IMtkRadioEx getRadioProxy(int phoneId, Message result) {
        if (mRadioResponseMtk == null) {
            mRadioResponseMtk = new MtkRadioResponseBase3_0((RIL) null);
        }
        if (mRadioIndicationMtk == null) {
            mRadioIndicationMtk = new MtkRadioIndication3_0((RIL) null);
        }
        if (result != null) {
            mRequestList.append(result.what, result);
        }
        try {
            mRadioProxyMtk[phoneId] = IMtkRadioEx.getService(HIDL_SERVICE_NAME[phoneId], false);
            IMtkRadioEx[] iMtkRadioExArr = mRadioProxyMtk;
            if (iMtkRadioExArr[phoneId] != null) {
                iMtkRadioExArr[phoneId].setResponseFunctionsEm(mRadioResponseMtk, mIsRadioIndicationListen ? mRadioIndicationMtk : null);
                Elog.d(TAG, "getMtkRadioProxy succeed");
            } else {
                Elog.e(TAG, "getMtkRadioProxy failed");
            }
        } catch (RemoteException | RuntimeException e) {
            mRadioProxyMtk[phoneId] = null;
            Elog.d(TAG, "RadioProxy getService: " + e);
        }
        return mRadioProxyMtk[phoneId];
    }

    public static boolean isSupport(int phoneId) {
        int slotId = getSlotId(phoneId);
        try {
            mRadioProxyMtk[slotId] = IMtkRadioEx.getService(HIDL_SERVICE_NAME[slotId], false);
        } catch (RemoteException | RuntimeException e) {
            Elog.d(TAG, "RadioProxy getService: " + e);
        }
        if (mRadioProxyMtk[slotId] != null) {
            return true;
        }
        return false;
    }

    public static int getSlotId(int phoneid) {
        switch (phoneid) {
            case -1:
                return ModemCategory.getCapabilitySim();
            case 255:
                return ModemCategory.getCdmaPhoneId();
            default:
                return phoneid;
        }
    }

    public static void invokeOemRilRequestStringsEm(int phoneId, String[] strings, Message result) {
        int i;
        IMtkRadioEx radioProxy = getRadioProxy(getSlotId(phoneId), result);
        if (radioProxy != null) {
            if (result == null) {
                i = 0;
            } else {
                try {
                    i = result.what;
                } catch (RemoteException | RuntimeException e) {
                    Elog.e(TAG, e.toString());
                    return;
                }
            }
            radioProxy.sendRequestStrings(i, new ArrayList(Arrays.asList(strings)));
        }
    }

    private static ArrayList<Byte> primitiveArrayToArrayList(byte[] arr) {
        ArrayList<Byte> arrayList = new ArrayList<>(arr.length);
        for (byte b : arr) {
            arrayList.add(Byte.valueOf(b));
        }
        return arrayList;
    }

    public static void invokeOemRilRequestRawEm(int phoneId, byte[] data, Message result) {
        IMtkRadioEx radioProxy = getRadioProxy(getSlotId(phoneId), result);
        if (radioProxy != null) {
            try {
                radioProxy.sendRequestRaw(result.what, primitiveArrayToArrayList(data));
            } catch (RemoteException | RuntimeException e) {
                Elog.e(TAG, e.toString());
            }
        } else {
            Elog.e(TAG, "MTkRadioProxy is null");
        }
    }

    public static void setRadioIndicationType(int type) {
        mRadioIndicationType = type;
    }

    public static void mSetRadioIndicationMtk(int phoneId, Handler handler, int what, boolean isRadioIndicationListen) {
        mIsRadioIndicationListen = isRadioIndicationListen;
        mHandler = handler;
        mWhat = what;
        if (getRadioProxy(phoneId, (Message) null) != null) {
            Elog.d(TAG, "SetMtkRadioIndication3_0 succeed");
        } else {
            Elog.e(TAG, "MtkRadioIndicatio is null");
        }
    }

    public static void rebootModemHidl() {
        RadioIndication.resetStateLast();
        IMtkRadioEx radioProxy = getRadioProxy(0, (Message) null);
        if (radioProxy != null) {
            try {
                radioProxy.setTrm(0, 2);
            } catch (RemoteException | RuntimeException e) {
                Elog.e(TAG, e.toString());
            }
        } else {
            Elog.e(TAG, "radioProxy is null");
        }
    }

    public static void reloadModemType(int modemType) {
        IMtkRadioEx radioProxy = getRadioProxy(getSlotId(-1), (Message) null);
        if (radioProxy != null) {
            try {
                radioProxy.modifyModemType(0, 1, modemType);
            } catch (RemoteException | RuntimeException e) {
                Elog.e(TAG, e.toString());
            }
        } else {
            Elog.e(TAG, "radioProxy is null");
        }
    }

    public static void storeModemType(int modemType) {
        IMtkRadioEx radioProxy = getRadioProxy(getSlotId(-1), (Message) null);
        if (radioProxy != null) {
            try {
                radioProxy.modifyModemType(0, 2, modemType);
            } catch (RemoteException | RuntimeException e) {
                Elog.e(TAG, e.toString());
            }
        } else {
            Elog.e(TAG, "radioProxy is null");
        }
    }
}
