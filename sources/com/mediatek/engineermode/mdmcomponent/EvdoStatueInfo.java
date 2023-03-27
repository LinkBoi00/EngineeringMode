package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponentC2k */
class EvdoStatueInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_EVDO_STATE_INFO_IND};
    HashMap<Integer, String> ALMPMapping = new HashMap<Integer, String>() {
        {
            put(0, "ALMP_INIT_STATE");
            put(1, "ALMP_IDLE_STATE");
            put(2, "ALMP_CONN_SETUP_STATE");
            put(3, "ALMP_CONNECTED_STATE");
        }
    };
    HashMap<Integer, String> AtMapping = new HashMap<Integer, String>() {
        {
            put(0, "AT_PWROFF");
            put(1, "AT_INACTIVE");
            put(2, "AT_PILOTACQ");
            put(3, "AT_SYNC");
            put(4, "AT_IDLE");
            put(5, "AT_ACCESS");
            put(6, "AT_CONNECTED");
        }
    };
    HashMap<Integer, String> CspMapping = new HashMap<Integer, String>() {
        {
            put(0, "CSP_INACTIVE_STATE");
            put(1, "CSP_CLOSING_STATE");
            put(2, "CSP_OPEN_STATE");
        }
    };
    HashMap<Integer, String> IdpMapping = new HashMap<Integer, String>() {
        {
            put(0, "IDP_INACTIVE_ST");
            put(1, "IDP_MONITOR_ST");
            put(2, "IDP_SLEEP_ST");
            put(3, "IDP_CONN_SETUP_ST");
            put(4, "IDP_FREEZE_PENDING_ST");
            put(5, "IDP_FREEZE_ST");
            put(6, "IDP_CONN_FROZEN_ST");
            put(7, "IDP_STATE_MAX");
        }
    };
    HashMap<Integer, String> InspMapping = new HashMap<Integer, String>() {
        {
            put(0, "INSP_INACTIVE_STATE");
            put(1, "INSP_NETWORK_DET_STATE");
            put(2, "INSP_PILOT_ACQ_STATE");
            put(3, "INSP_SYNC_STATE");
            put(4, "INSP_TIMING_CHANGE_STATE");
            put(5, "INSP_WFR_1XASSTST_STATE");
        }
    };
    HashMap<Integer, String> OmpMapping = new HashMap<Integer, String>() {
        {
            put(0, "OMP_INACTIVE_ST");
            put(1, "OMP_ACTIVE_ST");
            put(2, "OMP_STATE_MAX");
        }
    };
    HashMap<Integer, String> RupMapping = new HashMap<Integer, String>() {
        {
            put(0, "RUP_INACTIVE");
            put(1, "RUP_IDLE");
            put(2, "RUP_CONNECTED");
            put(3, "RUP_IRAT_MEASUREMENT");
            put(4, "RUP_INVALID");
        }
    };
    HashMap<Integer, String> SessionMapping = new HashMap<Integer, String>() {
        {
            put(0, "NEW_SESSION");
            put(1, "ALIVE_SESSION");
            put(2, "PRIOR_SESSION");
            put(3, "OPENED_SESSION");
        }
    };

    public EvdoStatueInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EVDO Status info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "7. CDMA EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"sessionState", "atState", "almpState", "inspState", "idpState", "ompState", "cspState", "rupState"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int sessionState = getFieldValue(data, "sessionState");
        int atState = getFieldValue(data, "atState");
        int almpState = getFieldValue(data, "almpState");
        int inspState = getFieldValue(data, "inspState");
        int idpState = getFieldValue(data, "idpState");
        int ompState = getFieldValue(data, "ompState");
        int cspState = getFieldValue(data, "cspState");
        int rupState = getFieldValue(data, "rupState");
        Elog.d("EmInfo/MDMComponent", "rupState = " + rupState);
        clearData();
        addData(this.SessionMapping.get(Integer.valueOf(sessionState)));
        addData(this.AtMapping.get(Integer.valueOf(atState)));
        addData(this.ALMPMapping.get(Integer.valueOf(almpState)));
        addData(this.InspMapping.get(Integer.valueOf(inspState)));
        addData(this.IdpMapping.get(Integer.valueOf(idpState)));
        addData(this.OmpMapping.get(Integer.valueOf(ompState)));
        addData(this.CspMapping.get(Integer.valueOf(cspState)));
        addData(this.RupMapping.get(Integer.valueOf(rupState)));
        notifyDataSetChanged();
    }
}
