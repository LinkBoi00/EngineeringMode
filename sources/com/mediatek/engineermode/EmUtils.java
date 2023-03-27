package com.mediatek.engineermode;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.widget.Toast;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.mediatek.engineermode.aospradio.EmRadioHidlAosp;
import com.mediatek.engineermode.aospradio.RadioIndication;
import com.mediatek.engineermode.mnldinterface.Debug2MnldInterface;
import com.mediatek.socket.base.UdpClient;
import java.io.File;
import java.util.NoSuchElementException;
import vendor.mediatek.hardware.engineermode.V1_3.IEmd;

public class EmUtils {
    private static final String BATTERY_DEFAULT_PATH = "battery/";
    private static final String BATTERY_FILE = "Power_On_Voltage";
    private static final String BATTERY_MT6357_PATH = "mt6357-gauge/";
    private static final String BATTERY_MT6359_PATH = "mt6359-gauge/";
    private static final String BATTERY_MT6359_P_PATH = "mt6359p-gauge/";
    public static final String BATTERY_ROOT_PATH = "/sys/bus/platform/devices/";
    private static final String CHANNEL_OUT = "mtk_debugService2mnld";
    private static final String DEFAULT_MNL_PROP = "000000010000";
    public static final String DEV_GAUGE_PATH = "/dev/gauge/";
    private static final char MNL_DISABLE = '0';
    private static final char MNL_ENABLE = '1';
    private static final int MNL_INDEX_DBG2FILE = 2;
    private static final int MNL_INDEX_GNSS_PORT = 3;
    private static final String MNL_PROP_NAME = "persist.vendor.radio.mnl.prop";
    public static final int OEM_RIL_REQUEST_HIDL = 1;
    public static final int OEM_RIL_REQUEST_MODE = 1;
    public static final int OEM_RIL_REQUEST_PHOHE = 0;
    public static final int RADIO_INDICATION_TYPE_NETWORKINFO = 1;
    public static final int RADIO_INDICATION_TYPE_NONE = -1;
    public static final int RADIO_INDICATION_TYPE_PHONE_STAUS_CHANGE = 3;
    public static final int RADIO_INDICATION_TYPE_TXPOWER_INFO = 2;
    public static final int RADIO_INDICATION_TYPE_URCINFO = 0;
    public static final int RADIO_RESPONSE_MODEM_STAUS_CHANGE = 4;
    public static final String TAG = "EmUtils";
    public static IEmd mEmHIDLService = null;
    public static Phone mPhone = null;
    public static Phone mPhoneCdma = null;
    public static Phone mPhoneMain = null;
    public static Phone mPhoneSlot1 = null;
    public static Phone mPhoneSlot2 = null;
    public static Toast mToast = null;

    public static Phone getmPhone(int phoneid) {
        PhoneFactory.makeDefaultPhones(EmApplication.getContext());
        switch (phoneid) {
            case -1:
                Phone phone = PhoneFactory.getPhone(ModemCategory.getCapabilitySim());
                mPhoneMain = phone;
                mPhone = phone;
                break;
            case 0:
                if (mPhoneSlot1 == null) {
                    mPhoneSlot1 = PhoneFactory.getPhone(0);
                }
                mPhone = mPhoneSlot1;
                break;
            case 1:
                if (mPhoneSlot2 == null) {
                    mPhoneSlot2 = PhoneFactory.getPhone(1);
                }
                mPhone = mPhoneSlot2;
                break;
            case 255:
                Phone cdmaPhone = ModemCategory.getCdmaPhone();
                mPhoneCdma = cdmaPhone;
                mPhone = cdmaPhone;
                break;
        }
        Elog.v(TAG, "getmPhone,phoneid = " + phoneid);
        return mPhone;
    }

    private static void invokeOemRilRequestStringsEmPhone(int phoneid, String[] command, Message response) {
        try {
            getmPhone(phoneid).invokeOemRilRequestStrings(command, response);
        } catch (Exception e) {
            Elog.v(TAG, e.getMessage());
            Elog.v(TAG, "get phone invokeOemRilRequestStrings failed");
        }
    }

    private static void invokeOemRilRequestStringsEmHidl(int phoneid, String[] command, Message response) {
        EmRadioHidl.invokeOemRilRequestStringsEm(phoneid, command, response);
    }

    public static void invokeOemRilRequestStringsEm(int phoneid, String[] command, Message response) {
        invokeOemRilRequestStringsEmHidl(phoneid, command, response);
    }

    public static void invokeOemRilRequestStringsEm(String[] command, Message response) {
        invokeOemRilRequestStringsEm(-1, command, response);
    }

    public static void invokeOemRilRequestStringsEm(boolean isCdma, String[] command, Message response) {
        if (isCdma) {
            invokeOemRilRequestStringsEm(255, command, response);
        } else {
            invokeOemRilRequestStringsEm(-1, command, response);
        }
    }

    public static void invokeOemRilRequestRawEmPhone(int phoneid, byte[] command, Message response) {
        try {
            getmPhone(phoneid).invokeOemRilRequestRaw(command, response);
        } catch (Exception e) {
            Elog.v(TAG, e.getMessage());
            Elog.v(TAG, "get phone invokeOemRilRequestRaw failed");
        }
    }

    public static void invokeOemRilRequestRawEmHidl(int phoneid, byte[] command, Message response) {
        EmRadioHidl.invokeOemRilRequestRawEm(phoneid, command, response);
    }

    public static void invokeOemRilRequestRawEm(int phoneid, byte[] command, Message response) {
        invokeOemRilRequestRawEmHidl(phoneid, command, response);
    }

    public static void invokeOemRilRequestRawEm(byte[] command, Message response) {
        invokeOemRilRequestRawEm(-1, command, response);
    }

    public static IEmd getEmHidlService() {
        Elog.v(TAG, "getEmHidlService ...");
        if (mEmHIDLService == null) {
            Elog.v(TAG, "getEmHidlService init...");
            try {
                mEmHIDLService = IEmd.getService("EmHidlServer", true);
            } catch (RemoteException e) {
                e.printStackTrace();
                Elog.e(TAG, "EmHIDLConnection exception1 ...");
                Elog.e(TAG, e.getMessage());
            } catch (NoSuchElementException e2) {
                e2.printStackTrace();
                Elog.e(TAG, "EmHIDLConnection exception2 ...");
                Elog.e(TAG, e2.getMessage());
            }
        }
        return mEmHIDLService;
    }

    public static void rebootModemPhone() {
        try {
            getmPhone(-1).invokeOemRilRequestStrings(new String[]{"SET_TRM", "2"}, (Message) null);
        } catch (Exception e) {
            Elog.v(TAG, e.getMessage());
            Elog.v(TAG, "rebootModem SET_TRM 2 failed");
        }
        Elog.d(TAG, "rebootModem SET_TRM 2");
    }

    public static void rebootModemHidl() {
        EmRadioHidl.rebootModemHidl();
        Elog.d(TAG, "rebootModemHidl");
    }

    public static void rebootModem() {
        rebootModemHidl();
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        while (strLen < strLength) {
            StringBuffer sb = new StringBuffer();
            sb.append(str);
            sb.append("0");
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static String systemPropertyGet(String property_name, String default_value) {
        try {
            return SystemProperties.get(property_name, default_value);
        } catch (Exception e) {
            Elog.e(TAG, "EmUtils systemPropertyGet failed");
            return "";
        }
    }

    public static boolean systemPropertySet(String property_name, String set_value) {
        try {
            SystemProperties.set(property_name, set_value);
            return true;
        } catch (Exception e) {
            Elog.e(TAG, "EmUtils systemPropertySet failed :" + property_name);
            return false;
        }
    }

    public static void showToast(String msg, int time) {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(EmApplication.getContext(), msg, time);
        mToast = makeText;
        makeText.show();
    }

    public static void showToast(int msg_id, int time) {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(EmApplication.getContext(), msg_id, time);
        mToast = makeText;
        makeText.show();
    }

    public static void showToast(int msg_id) {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(EmApplication.getContext(), msg_id, 0);
        mToast = makeText;
        makeText.show();
    }

    public static void showToast(String msg) {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(EmApplication.getContext(), msg, 0);
        mToast = makeText;
        makeText.show();
    }

    public static void showToast(String msg, boolean last) {
        Toast toast;
        if (!last && (toast = mToast) != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(EmApplication.getContext(), msg, 0);
        mToast = makeText;
        makeText.show();
    }

    public static void setAirplaneModeEnabled(boolean enabled) {
        Elog.d(TAG, "setAirplaneModEnabled = " + enabled);
        RadioIndication.resetStateLast();
        Context context = EmApplication.getContext();
        EmApplication.getContext();
        ((ConnectivityManager) context.getSystemService("connectivity")).setAirplaneMode(enabled);
    }

    public static void initPoweroffmdMode(boolean enabled, boolean RFonly) {
        Elog.d(TAG, "initPoweroffmdMode: " + enabled + ",RFonly: " + RFonly);
        String str = "0";
        systemPropertySet("vendor.ril.test.poweroffmd", RFonly ? str : "1");
        if (enabled) {
            str = "1";
        }
        systemPropertySet("vendor.ril.testmode", str);
    }

    public static boolean ifAirplaneModeEnabled() {
        boolean z = false;
        if (Settings.System.getInt(EmApplication.getContext().getContentResolver(), "airplane_mode_on", 0) != 0) {
            z = true;
        }
        boolean isAirplaneModeOn = z;
        Elog.d(TAG, "isAirplaneModeOn: " + isAirplaneModeOn);
        return isAirplaneModeOn;
    }

    public static void writeSharedPreferences(String preferencesName, String key, String value) {
        Context context = EmApplication.getContext();
        EmApplication.getContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(preferencesName, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readSharedPreferences(String preferencesName, String key, String default_value) {
        Context context = EmApplication.getContext();
        EmApplication.getContext();
        return context.getSharedPreferences(preferencesName, 0).getString(key, default_value);
    }

    public static void registerForTxpowerInfo(Handler handler, int what) {
        EmRadioHidl.setRadioIndicationType(2);
        EmRadioHidl.mSetRadioIndicationMtk(0, handler, what, true);
    }

    public static void unregisterForTxpowerInfo() {
        EmRadioHidl.setRadioIndicationType(-1);
        EmRadioHidl.mSetRadioIndicationMtk(0, (Handler) null, 0, false);
    }

    public static void registerForUrcInfo(int phoneid, Handler handler, int what) {
        EmRadioHidl.setRadioIndicationType(0);
        EmRadioHidl.mSetRadioIndicationMtk(phoneid, handler, what, true);
    }

    public static void unregisterForUrcInfo(int phoneid) {
        EmRadioHidl.setRadioIndicationType(-1);
        EmRadioHidl.mSetRadioIndicationMtk(phoneid, (Handler) null, 0, false);
    }

    public static void registerForradioStateChanged(int phoneid, Handler handler, int what) {
        EmRadioHidlAosp.mRadioIndicationType = 3;
        EmRadioHidlAosp.mSetRadioIndicationAosp(phoneid, handler, what, true);
    }

    public static void unregisterradioStateChanged(int phoneid) {
        EmRadioHidlAosp.mRadioIndicationType = -1;
        EmRadioHidlAosp.mSetRadioIndicationAosp(phoneid, (Handler) null, 0, false);
    }

    public static void registerForModemStatusChanged(int phoneid, Handler handler, int what) {
        EmRadioHidlAosp.mRadioIndicationType = 4;
        EmRadioHidlAosp.setRadioResponseAosp(phoneid, handler, what, true);
    }

    public static void unregisterForModemStatusChanged(int phoneid) {
        EmRadioHidlAosp.mRadioIndicationType = -1;
        EmRadioHidlAosp.setRadioResponseAosp(phoneid, (Handler) null, 0, false);
    }

    public static void reloadModemType(int modemType) {
        EmRadioHidl.reloadModemType(modemType);
    }

    public static void storeModemType(int modemType) {
        EmRadioHidl.storeModemType(modemType);
    }

    public static String getBatteryPath() {
        if (new File("/dev/gauge/Power_On_Voltage").exists()) {
            Elog.v(TAG, "battry path is /dev/gauge/");
            return DEV_GAUGE_PATH;
        } else if (new File("/sys/bus/platform/devices/mt6357-gauge/Power_On_Voltage").exists()) {
            Elog.v(TAG, "it is MT6357 ");
            return "/sys/bus/platform/devices/mt6357-gauge/";
        } else if (new File("/sys/bus/platform/devices/mt6359-gauge/Power_On_Voltage").exists()) {
            Elog.v(TAG, "it is MT6359 ");
            return "/sys/bus/platform/devices/mt6359-gauge/";
        } else if (!new File("/sys/bus/platform/devices/mt6359p-gauge/Power_On_Voltage").exists()) {
            return BATTERY_DEFAULT_PATH;
        } else {
            Elog.v(TAG, "it is MT6359 P");
            return "/sys/bus/platform/devices/mt6359p-gauge/";
        }
    }

    private static void setMnldProp(int index, boolean enable) {
        String prop = SystemProperties.get(MNL_PROP_NAME);
        Elog.i(TAG, "getMnlProp: " + prop);
        if (prop == null || prop.isEmpty()) {
            prop = DEFAULT_MNL_PROP;
        }
        if (prop.length() > index) {
            char[] charArray = prop.toCharArray();
            charArray[index] = enable ? MNL_ENABLE : MNL_DISABLE;
            String newProp = String.valueOf(charArray);
            new Debug2MnldInterface.Debug2MnldInterfaceSender().debugMnldRadioMsg(new UdpClient(CHANNEL_OUT, LocalSocketAddress.Namespace.ABSTRACT, 45), newProp);
            Elog.i(TAG, "setMnlProp newProp: " + newProp);
        }
    }

    public static void enableGnssPort(boolean enable) {
        setMnldProp(3, enable);
    }

    public static void enableDbg2File(boolean enable) {
        setMnldProp(2, enable);
    }

    public static boolean getDbg2FileValue(boolean defaultValue) {
        String prop = SystemProperties.get(MNL_PROP_NAME);
        Elog.i(TAG, "getMnlProp: " + prop);
        if (prop == null || prop.isEmpty()) {
            return defaultValue;
        }
        char charAt = prop.charAt(2);
        return '1' == prop.charAt(2);
    }

    public static void enableDbg2FileAndGnssPort(boolean onDbg2File, boolean onGnssPort) {
        String prop = SystemProperties.get(MNL_PROP_NAME);
        Elog.i(TAG, "getMnlProp: " + prop);
        if (prop == null || prop.isEmpty()) {
            prop = DEFAULT_MNL_PROP;
        }
        char[] charArray = prop.toCharArray();
        char c = MNL_ENABLE;
        charArray[2] = onDbg2File ? '1' : '0';
        if (!onGnssPort) {
            c = '0';
        }
        charArray[3] = c;
        String newProp = String.valueOf(charArray);
        new Debug2MnldInterface.Debug2MnldInterfaceSender().debugMnldRadioMsg(new UdpClient(CHANNEL_OUT, LocalSocketAddress.Namespace.ABSTRACT, 45), newProp);
        Elog.i(TAG, "setMnlProp newProp: " + newProp);
    }
}
