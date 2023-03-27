package com.mediatek.engineermode;

import android.hardware.radio.V1_0.RadioResponseInfo;
import android.os.AsyncResult;
import android.os.Message;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.RIL;
import java.util.ArrayList;
import vendor.mediatek.hardware.mtkradioex.V2_0.IEmRadioResponse;

public class MtkRadioResponseBase2_0 extends IEmRadioResponse.Stub {
    public static final String TAG = "MtkRadioResponseBase";

    public MtkRadioResponseBase2_0(RIL ril) {
    }

    public static byte[] arrayListToPrimitiveArray(ArrayList<Byte> bytes) {
        byte[] ret = new byte[bytes.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bytes.get(i).byteValue();
        }
        return ret;
    }

    private void sendMessageResponse(Message msg, Object ret) {
        if (msg != null) {
            AsyncResult.forMessage(msg, ret, (Throwable) null);
            msg.sendToTarget();
        }
    }

    private void sendMessageResponseError(Message msg, int error, Object ret) {
        CommandException ex = CommandException.fromRilErrno(error);
        if (msg != null) {
            AsyncResult.forMessage(msg, ret, ex);
            msg.sendToTarget();
        }
    }

    private void responseStringArrayList(RIL ril, RadioResponseInfo responseInfo, ArrayList<String> strings) {
        String[] ret = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            ret[i] = strings.get(i);
        }
        Message msg = EmRadioHidl2_0.mRequestList.get(responseInfo.serial);
        if (msg != null) {
            EmRadioHidl2_0.mRequestList.remove(responseInfo.serial);
        }
        if (responseInfo.error == 0) {
            sendMessageResponse(msg, ret);
        } else {
            sendMessageResponseError(msg, responseInfo.error, ret);
        }
    }

    public void sendRequestRawResponse(RadioResponseInfo responseInfo, ArrayList<Byte> data) {
        Elog.d(TAG, "rsp data = ***");
        Message msg = EmRadioHidl2_0.mRequestList.get(responseInfo.serial);
        if (msg != null) {
            EmRadioHidl2_0.mRequestList.remove(responseInfo.serial);
        }
        if (responseInfo.error == 0) {
            sendMessageResponse(msg, arrayListToPrimitiveArray(data));
        } else {
            sendMessageResponseError(msg, responseInfo.error, (Object) null);
        }
    }

    public void sendRequestStringsResponse(RadioResponseInfo responseInfo, ArrayList<String> data) {
        Elog.d(TAG, "rsp data = ***");
        responseStringArrayList((RIL) null, responseInfo, data);
    }
}
