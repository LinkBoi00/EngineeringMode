package com.mediatek.engineermode;

import android.os.AsyncResult;
import android.os.Message;
import com.android.internal.telephony.RIL;
import java.util.ArrayList;
import java.util.List;
import vendor.mediatek.hardware.mtkradioex.V2_0.IEmRadioIndication;

public class MtkRadioIndication2_0 extends IEmRadioIndication.Stub {
    public static final String TAG = "MtkRadioIndication";

    public MtkRadioIndication2_0(RIL ril) {
    }

    public static byte[] arrayListToPrimitiveArray(List<Byte> bytes) {
        byte[] ret = new byte[bytes.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bytes.get(i).byteValue();
        }
        return ret;
    }

    public static int[] arrayListToPrimitiveArrayInt(List<Integer> value) {
        int[] ret = new int[value.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = value.get(i).intValue();
        }
        return ret;
    }

    public static String[] arrayListToPrimitiveArrayString(List<String> info) {
        String[] ret = new String[info.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = info.get(i);
        }
        return ret;
    }

    private void sendMessageResponse(Message msg, Object ret) {
        try {
            AsyncResult.forMessage(msg, ret, (Throwable) null);
            msg.sendToTarget();
            Elog.d(TAG, "msg send");
        } catch (Exception e) {
            Elog.e(TAG, "sendMessageResponse: " + e.getMessage());
        }
    }

    public void networkInfoInd(int indicationType, ArrayList<String> info) {
        String[] response = arrayListToPrimitiveArrayString(info);
        if (EmRadioHidl2_0.mRadioIndicationType == 1) {
            sendMessageResponse(EmRadioHidl2_0.mHandler.obtainMessage(EmRadioHidl2_0.mWhat), response);
        } else {
            Elog.e(TAG, "networkInfoInd not send to app");
        }
    }

    public void onTxPowerIndication(int indicationType, ArrayList<Integer> indPower) {
        int[] response = arrayListToPrimitiveArrayInt(indPower);
        Elog.d(TAG, "onTxPowerIndication: " + indPower.toString());
        if (EmRadioHidl2_0.mRadioIndicationType == 2) {
            sendMessageResponse(EmRadioHidl2_0.mHandler.obtainMessage(EmRadioHidl2_0.mWhat), response);
        } else {
            Elog.e(TAG, "onTxPowerIndication not send to app");
        }
    }

    public void oemHookRaw(int indicationType, ArrayList<Byte> data) {
        byte[] response = arrayListToPrimitiveArray(data);
        String str = new String(response);
        Elog.d(TAG, "oemHookRaw:" + str);
        if (EmRadioHidl2_0.mRadioIndicationType == 0) {
            sendMessageResponse(EmRadioHidl2_0.mHandler.obtainMessage(EmRadioHidl2_0.mWhat), response);
        } else {
            Elog.e(TAG, "oemHookRaw not send to app");
        }
    }
}
