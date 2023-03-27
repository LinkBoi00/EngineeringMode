package com.mediatek.engineermode;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;

public class ModemCategory {
    public static final String FK_CDMA_SLOT = "persist.vendor.radio.cdma_slot";
    public static final String FK_NR_SLOT = "persist.vendor.radio.nrslot";
    public static final String FK_SIM_SWITCH = "persist.vendor.radio.simswitch";
    public static final int MODEM_FDD = 1;
    public static final int MODEM_NO3G = 3;
    public static final int MODEM_TD = 2;
    private static final String[] ModemType = {"none", "FDD", "TDD", "No3G"};
    private static final String[] PROPERTY_RIL_CT3G = {"vendor.gsm.ril.ct3g", "vendor.gsm.ril.ct3g.2", "vendor.gsm.ril.ct3g.3", "vendor.gsm.ril.ct3g.4"};
    private static final String[] PROPERTY_RIL_FULL_UICC_TYPE = {"vendor.gsm.ril.fulluicctype", "vendor.gsm.ril.fulluicctype.2", "vendor.gsm.ril.fulluicctype.3", "vendor.gsm.ril.fulluicctype.4"};
    private static final String TAG = "ModemCategory";

    public static int getModemType() {
        int mode = 3;
        int mask = WorldModeUtil.get3GDivisionDuplexMode();
        if (1 == mask || 2 == mask) {
            mode = mask;
        }
        Elog.v(TAG, "mode = " + mode + "(" + ModemType[mode] + ")");
        return mode;
    }

    public static boolean isCdma() {
        return RatConfiguration.isC2kSupported();
    }

    public static boolean isLteSupport() {
        return RatConfiguration.isLteFddSupported() || RatConfiguration.isLteTddSupported();
    }

    public static boolean isNrSupport() {
        return RatConfiguration.isNrSupported();
    }

    public static boolean isGsmSupport() {
        return RatConfiguration.isGsmSupported();
    }

    public static boolean isWcdmaSupport() {
        return RatConfiguration.isWcdmaSupported();
    }

    public static boolean isTdscdmaSupport() {
        return RatConfiguration.isTdscdmaSupported();
    }

    public static int getCapabilitySim() {
        int phoneid;
        String mPhoneId = SystemProperties.get(FK_SIM_SWITCH, "1");
        if ("1".equals(mPhoneId)) {
            phoneid = 0;
        } else if ("2".equals(mPhoneId)) {
            phoneid = 1;
        } else if ("3".equals(mPhoneId)) {
            phoneid = 2;
        } else {
            Elog.w(TAG, "read phone id error");
            return -1;
        }
        Elog.v(TAG, "main card phoneid = " + phoneid);
        return phoneid;
    }

    public static Phone getCdmaPhone() {
        try {
            if (FeatureSupport.is93ModemAndAbove()) {
                return PhoneFactory.getPhone(getCapabilitySim());
            }
            String sCdmaSlotId = SystemProperties.get(FK_CDMA_SLOT, "1");
            Elog.d(TAG, "sCdmaSlotId = " + sCdmaSlotId);
            return PhoneFactory.getPhone(Integer.parseInt(sCdmaSlotId) - 1);
        } catch (Exception e) {
            Elog.e(TAG, e.getMessage());
            return null;
        }
    }

    public static int getCdmaPhoneId() {
        try {
            if (FeatureSupport.is93ModemAndAbove()) {
                return getCapabilitySim();
            }
            String sCdmaSlotId = SystemProperties.get(FK_CDMA_SLOT, "1");
            Elog.d(TAG, "sCdmaSlotId = " + sCdmaSlotId);
            return Integer.parseInt(sCdmaSlotId) - 1;
        } catch (Exception e) {
            Elog.e(TAG, e.getMessage());
            return 0;
        }
    }

    public static boolean CheckViceSimCdmaCapability(int simtype) {
        int cdmaid = getCdmaPhoneId();
        Elog.d(TAG, "sCdmaSlotId = " + cdmaid);
        return simtype == cdmaid;
    }

    public static String[] getCdmaCmdArr(String[] cmdArray) {
        if (!FeatureSupport.is93ModemAndAbove()) {
            return cmdArray;
        }
        return new String[]{cmdArray[0], cmdArray[1]};
    }

    public static boolean CheckViceSimWCapability(int simtype) {
        TelephonyManager telephonyManager = TelephonyManager.getDefault();
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (iTelephony == null || telephonyManager == null || telephonyManager.getSimCount() <= 1) {
            return false;
        }
        try {
            if ((iTelephony.getRadioAccessFamily(simtype, "engineermode") & 4) > 0) {
                Elog.d(TAG, "SIM has W capability ");
                return true;
            }
        } catch (RemoteException e) {
            Elog.e(TAG, e.getMessage());
        }
        Elog.d(TAG, "SIM has no W capability ");
        return false;
    }

    public static boolean checkViceSimCapability(int simType, int capability) {
        TelephonyManager telephonyManager = TelephonyManager.getDefault();
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (iTelephony == null || telephonyManager == null || telephonyManager.getSimCount() <= 1) {
            return false;
        }
        try {
            if ((iTelephony.getRadioAccessFamily(simType, "engineermode") & capability) > 0) {
                Elog.d(TAG, "SIM has checked capability ");
                return true;
            }
        } catch (RemoteException e) {
            Elog.e(TAG, e.getMessage());
        }
        Elog.v(TAG, "SIM has no checked capability ");
        return false;
    }

    public static boolean isCapabilitySim(int mSimType) {
        boolean isCapability = mSimType == getCapabilitySim();
        Elog.v(TAG, "The card: " + mSimType + " is main card = " + isCapability);
        return isCapability;
    }

    public static boolean isCt3gDualMode(int slotId) {
        if (slotId >= 0) {
            String[] strArr = PROPERTY_RIL_CT3G;
            if (slotId < strArr.length) {
                String result = SystemProperties.get(strArr[slotId], "");
                Elog.v(TAG, "isCt3gDualMode:  " + result);
                return "1".equals(result);
            }
        }
        Elog.e(TAG, "isCt3gDualMode: invalid slotId " + slotId);
        return false;
    }

    public static String[] getSupportCardType(int slotId) {
        String[] values = null;
        if (slotId >= 0) {
            String[] strArr = PROPERTY_RIL_FULL_UICC_TYPE;
            if (slotId < strArr.length) {
                String prop = SystemProperties.get(strArr[slotId], "");
                if (!prop.equals("") && prop.length() > 0) {
                    values = prop.split(",");
                }
                StringBuilder sb = new StringBuilder();
                sb.append("getSupportCardType slotId ");
                sb.append(slotId);
                sb.append(", prop value= ");
                sb.append(prop);
                sb.append(", size= ");
                sb.append(values != null ? values.length : 0);
                Elog.v(TAG, sb.toString());
                return values;
            }
        }
        Elog.e(TAG, "getSupportCardType: invalid slotId " + slotId);
        return null;
    }

    public static int getSubIdBySlot(int slot) {
        int[] subId = SubscriptionManager.getSubId(slot);
        if (subId != null) {
            for (int i = 0; i < subId.length; i++) {
                Elog.v(TAG, "subId[" + i + "]: " + subId[i]);
            }
        }
        if (subId == null || subId.length == 0) {
            Elog.w(TAG, "the subid is empty");
            return -1;
        }
        Elog.v(TAG, "subId = " + subId[0]);
        return subId[0];
    }

    public static boolean isSimReady(int slotId) {
        int status;
        Context context = EmApplication.getContext();
        EmApplication.getContext();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (slotId < 0) {
            status = telephonyManager.getSimState();
        } else {
            status = telephonyManager.getSimState(slotId);
        }
        Elog.v(TAG, "slotId = " + slotId + ",simStatus = " + status);
        if (status == 1 || status == 0) {
            return false;
        }
        return true;
    }

    public static boolean CheckViceSimNRCapability(int simtype) {
        String sNRSlotId = SystemProperties.get(FK_NR_SLOT, "0");
        if (!RatConfiguration.isNrSupported()) {
            Elog.d(TAG, "project not support NR");
            return false;
        }
        Elog.d(TAG, "sNRSlotId = " + sNRSlotId);
        int NRSlotId = Integer.parseInt(sNRSlotId) - 1;
        if (NRSlotId == -1) {
            Elog.d(TAG, "both sim support NR");
            return true;
        }
        Elog.d(TAG, "sim " + NRSlotId + " support NR");
        if (simtype == NRSlotId) {
            return true;
        }
        return false;
    }
}
