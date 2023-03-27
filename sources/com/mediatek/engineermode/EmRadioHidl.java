package com.mediatek.engineermode;

import android.os.Handler;
import android.os.Message;

public class EmRadioHidl {
    public static void invokeOemRilRequestStringsEm(int phoneId, String[] strings, Message result) {
        if (EmRadioHidl3_0.isSupport(phoneId)) {
            EmRadioHidl3_0.invokeOemRilRequestStringsEm(phoneId, strings, result);
        } else {
            EmRadioHidl2_0.invokeOemRilRequestStringsEm(phoneId, strings, result);
        }
    }

    public static void invokeOemRilRequestRawEm(int phoneId, byte[] data, Message result) {
        if (EmRadioHidl3_0.isSupport(phoneId)) {
            EmRadioHidl3_0.invokeOemRilRequestRawEm(phoneId, data, result);
        } else {
            EmRadioHidl2_0.invokeOemRilRequestRawEm(phoneId, data, result);
        }
    }

    public static void setRadioIndicationType(int type) {
        if (EmRadioHidl3_0.isSupport(0)) {
            EmRadioHidl3_0.setRadioIndicationType(type);
        } else {
            EmRadioHidl2_0.setRadioIndicationType(type);
        }
    }

    public static void mSetRadioIndicationMtk(int phoneId, Handler handler, int what, boolean isRadioIndicationListen) {
        if (EmRadioHidl3_0.isSupport(phoneId)) {
            EmRadioHidl3_0.mSetRadioIndicationMtk(phoneId, handler, what, isRadioIndicationListen);
        } else {
            EmRadioHidl2_0.mSetRadioIndicationMtk(phoneId, handler, what, isRadioIndicationListen);
        }
    }

    public static void rebootModemHidl() {
        if (EmRadioHidl3_0.isSupport(0)) {
            EmRadioHidl3_0.rebootModemHidl();
        } else {
            EmRadioHidl2_0.rebootModemHidl();
        }
    }

    public static void reloadModemType(int modemType) {
        if (EmRadioHidl3_0.isSupport(0)) {
            EmRadioHidl3_0.reloadModemType(modemType);
        } else {
            EmRadioHidl2_0.reloadModemType(modemType);
        }
    }

    public static void storeModemType(int modemType) {
        if (EmRadioHidl3_0.isSupport(0)) {
            EmRadioHidl3_0.storeModemType(modemType);
        } else {
            EmRadioHidl2_0.storeModemType(modemType);
        }
    }
}
