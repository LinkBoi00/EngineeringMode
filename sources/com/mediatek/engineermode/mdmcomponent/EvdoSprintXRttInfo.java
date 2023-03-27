package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponentC2k */
class EvdoSprintXRttInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_C2K_L4_SPRINT_XRTT_INFO_IND};
    HashMap<Integer, String> AtMapping = new HashMap<Integer, String>() {
        {
            put(0, "NOSVC");
            put(1, "INIT");
            put(2, "IDLE");
            put(3, "TRAFFICINI");
            put(4, "TRAFFIC");
            put(5, "STATE NUM");
        }
    };

    public EvdoSprintXRttInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Sprint XRTT info";
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
        return new String[]{"State", "serviceOption", "rateReduction", MDMContent.C2K_L4_CHANNEL, "bandClass", "sid", "nid", "baseId", "pilotPNOffset", "mob_p_revp", "baseLat", "baseLong", "rxPower", MDMContent.ECIO, "FER", "txPower"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        int State = getFieldValue(data, "State");
        int serviceOption = getFieldValue(data, "serviceOption");
        int rateReduction = getFieldValue(data, "rateReduction");
        int channel = getFieldValue(data, MDMContent.C2K_L4_CHANNEL);
        int bandClass = getFieldValue(data, "bandClass");
        int sid = getFieldValue(data, "sid");
        int nid = getFieldValue(data, "nid");
        int baseId = getFieldValue(data, "baseId");
        int pilotPNOffset = getFieldValue(data, "pilotPNOffset");
        int mob_p_revp = getFieldValue(data, "mob_p_revp");
        int baseLat = getFieldValue(data, "baseLat");
        int baseLong = getFieldValue(data, "baseLong");
        int rxPower = getFieldValue(data, "rxPower", true);
        int EcIo = getFieldValue(data, MDMContent.ECIO, true);
        int i = EcIo;
        float ecio_f = ((float) EcIo) / 2.0f;
        int FER = getFieldValue(data, "FER", true);
        int txPower = getFieldValue(data, "txPower", true);
        clearData();
        Msg msg2 = data;
        addData(this.AtMapping.get(Integer.valueOf(State)));
        addData(Integer.valueOf(serviceOption));
        addData(Integer.valueOf(rateReduction));
        addData(Integer.valueOf(channel));
        addData(Integer.valueOf(bandClass));
        addData(Integer.valueOf(sid));
        addData(Integer.valueOf(nid));
        addData(Integer.valueOf(baseId));
        addData(Integer.valueOf(pilotPNOffset));
        addData(Integer.valueOf(mob_p_revp));
        addData(Integer.valueOf(baseLat));
        addData(Integer.valueOf(baseLong));
        addData(Integer.valueOf(rxPower));
        addData(Float.valueOf(ecio_f));
        addData(Integer.valueOf(FER));
        addData(Integer.valueOf(txPower));
        notifyDataSetChanged();
    }
}
