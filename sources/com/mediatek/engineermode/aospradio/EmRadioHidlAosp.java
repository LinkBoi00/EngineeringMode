package com.mediatek.engineermode.aospradio;

import android.hardware.radio.V1_3.IRadio;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.SparseArray;
import com.android.internal.telephony.RIL;
import com.mediatek.engineermode.Elog;

public class EmRadioHidlAosp {
    private static final int EVENT_RADIO_PROXY_DEAD = 1;
    private static final String[] HIDL_SERVICE_NAME = {"em1", "em2", "em3", "em4"};
    private static final int IRADIO_GET_SERVICE_DELAY_MILLIS = 500;
    public static final String TAG = "EmRadioHidlAosp";
    private static CapRadioProxyDeathRecipient mCapRadioProxyDeathRecipient = null;
    public static Handler mHandler;
    /* access modifiers changed from: private */
    public static Handler mHandlerRadio = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Elog.d(EmRadioHidlAosp.TAG, "RadioProxy dead");
                    IRadio unused = EmRadioHidlAosp.getRadioProxy(0, (Message) null);
                    return;
                default:
                    return;
            }
        }
    };
    private static boolean mIsRadioIndicationListen = false;
    private static boolean mIsRadioResponseListen = false;
    private static RadioIndication mRadioIndicationMtk = null;
    public static int mRadioIndicationType = -1;
    private static IRadio[] mRadioProxyMtk = new IRadio[4];
    private static RadioResponse mRadioResponseMtk = null;
    public static SparseArray<Message> mRequestList = new SparseArray<>();
    public static int mWhat;

    /* access modifiers changed from: private */
    public static IRadio getRadioProxy(int phoneId, Message result) {
        if (mCapRadioProxyDeathRecipient == null) {
            mCapRadioProxyDeathRecipient = new CapRadioProxyDeathRecipient();
        }
        if (mRadioIndicationMtk == null) {
            mRadioIndicationMtk = new RadioIndication((RIL) null);
        }
        if (mRadioResponseMtk == null) {
            mRadioResponseMtk = new RadioResponse((RIL) null);
        }
        if (result != null) {
            mRequestList.append(result.what, result);
        }
        try {
            mRadioProxyMtk[phoneId] = IRadio.getService(HIDL_SERVICE_NAME[phoneId], false);
            IRadio[] iRadioArr = mRadioProxyMtk;
            if (iRadioArr[phoneId] != null) {
                iRadioArr[phoneId].setResponseFunctions(mIsRadioResponseListen ? mRadioResponseMtk : null, mIsRadioIndicationListen ? mRadioIndicationMtk : null);
                mRadioProxyMtk[phoneId].linkToDeath(mCapRadioProxyDeathRecipient, 0);
                Elog.d(TAG, "getAospRadioProxy succeed " + phoneId);
            } else {
                Elog.e(TAG, "getAospRadioProxy failed " + phoneId);
            }
        } catch (RemoteException | RuntimeException e) {
            mRadioProxyMtk[phoneId] = null;
            mRadioResponseMtk.resetModemStatus();
            Elog.d(TAG, "AospRadioProxy " + phoneId + ", getService: " + e);
            Handler handler = mHandlerRadio;
            handler.sendMessageDelayed(handler.obtainMessage(1), 500);
        }
        return mRadioProxyMtk[phoneId];
    }

    public static void mSetRadioIndicationAosp(int phoneId, Handler handler, int what, boolean isRadioIndicationListen) {
        mIsRadioIndicationListen = isRadioIndicationListen;
        mHandler = handler;
        mWhat = what;
        if (getRadioProxy(phoneId, (Message) null) != null) {
            Elog.d(TAG, "Set AospRadioIndication succeed");
        } else {
            Elog.e(TAG, "AospRadioProxy is null");
        }
    }

    public static void setRadioResponseAosp(int phoneId, Handler handler, int what, boolean isRadioResponseListen) {
        mIsRadioResponseListen = isRadioResponseListen;
        mHandler = handler;
        mWhat = what;
        if (getRadioProxy(phoneId, (Message) null) != null) {
            Elog.d(TAG, "Set AospRadioIndication succeed: " + phoneId + ", " + isRadioResponseListen);
            return;
        }
        Elog.e(TAG, "AospRadioProxy is null");
    }

    public static void getModemStatus(int phoneId) {
        IRadio radioProxy = getRadioProxy(phoneId, (Message) null);
        if (radioProxy != null) {
            try {
                radioProxy.getModemStackStatus(0);
            } catch (RemoteException | RuntimeException e) {
                Elog.e(TAG, e.toString());
            }
        } else {
            Elog.e(TAG, "radioProxy is null");
        }
    }

    static final class CapRadioProxyDeathRecipient implements IHwBinder.DeathRecipient {
        CapRadioProxyDeathRecipient() {
        }

        public void serviceDied(long cookie) {
            EmRadioHidlAosp.mHandlerRadio.sendMessage(EmRadioHidlAosp.mHandlerRadio.obtainMessage(1, Long.valueOf(cookie)));
        }
    }
}
