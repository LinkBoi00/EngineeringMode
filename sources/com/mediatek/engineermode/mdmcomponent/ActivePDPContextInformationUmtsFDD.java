package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class ActivePDPContextInformationUmtsFDD extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_SM_NSAPI5_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI6_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI7_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI8_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI9_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI10_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI11_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI12_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI13_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI14_STATUS_IND, MDMContent.MSG_ID_EM_SM_NSAPI15_STATUS_IND};
    int[] QoS = new int[11];
    int[] addr_type = new int[11];
    String[] ipv4_s = new String[11];
    String[] ipv6_s = new String[11];
    HashMap<Integer, String> mMapping = new HashMap<Integer, String>() {
        {
            put(33, "IPV4");
            put(87, "IPV6");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLY), "IPV4V6");
            put(1, "PPP");
            put(2, "OSP_IMOSS");
            put(3, "NULL_PDP");
        }
    };
    String[] pdpapn_s = new String[11];
    boolean[] status = new boolean[11];

    public ActivePDPContextInformationUmtsFDD(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Active PDP Context Information(UMTS FDD)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "3. UMTS FDD EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"NSAPI index", "IP Type", "IPv4", "IPv6", "APN", "QoS (traffic_class)"};
    }

    /* access modifiers changed from: package-private */
    public int parsIndex(String name) {
        int index = Integer.parseInt(name.substring(18).split("_")[0]);
        Elog.d("EmInfo/MDMComponent", "name = " + index);
        return index - 5;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int addr_ipv6_base;
        Msg data = (Msg) msg;
        clearData();
        int index = parsIndex(name);
        int pdp_context_status = getFieldValue(data, "pdp.pdp_context_status");
        Elog.d("EmInfo/MDMComponent", "pdp_context_status = " + pdp_context_status);
        char c = 0;
        if (pdp_context_status == 0 || pdp_context_status == 2) {
            this.status[index] = false;
        } else {
            this.status[index] = true;
        }
        if (this.status[index]) {
            this.addr_type[index] = getFieldValue(data, "pdp.pdp_addr_type");
            this.ipv4_s[index] = "";
            for (int i = 0; i < 4; i++) {
                int ipv4 = getFieldValue(data, "pdp.ip[" + i + "]");
                StringBuilder sb = new StringBuilder();
                String[] strArr = this.ipv4_s;
                sb.append(strArr[index]);
                sb.append(ipv4);
                strArr[index] = sb.toString();
                if (i != 3) {
                    StringBuilder sb2 = new StringBuilder();
                    String[] strArr2 = this.ipv4_s;
                    sb2.append(strArr2[index]);
                    sb2.append(".");
                    strArr2[index] = sb2.toString();
                }
            }
            int[] iArr = this.addr_type;
            if (iArr[index] == 87) {
                addr_ipv6_base = 0;
            } else {
                int i2 = iArr[index];
                addr_ipv6_base = 4;
            }
            this.ipv6_s[index] = "";
            int i3 = addr_ipv6_base + 0;
            while (i3 < addr_ipv6_base + 16) {
                int ipv6 = getFieldValue(data, "pdp.ip[" + i3 + "]");
                StringBuilder sb3 = new StringBuilder();
                String[] strArr3 = this.ipv6_s;
                sb3.append(strArr3[index]);
                Object[] objArr = new Object[1];
                objArr[c] = Integer.valueOf(ipv6);
                sb3.append(String.format("%02x", objArr));
                strArr3[index] = sb3.toString();
                if ((i3 + 1) % 2 == 0 && i3 != addr_ipv6_base + 15) {
                    StringBuilder sb4 = new StringBuilder();
                    String[] strArr4 = this.ipv6_s;
                    sb4.append(strArr4[index]);
                    sb4.append(":");
                    strArr4[index] = sb4.toString();
                }
                i3++;
                c = 0;
            }
            this.pdpapn_s[index] = "";
            for (int i4 = 0; i4 < 100; i4++) {
                StringBuilder sb5 = new StringBuilder();
                String[] strArr5 = this.pdpapn_s;
                sb5.append(strArr5[index]);
                sb5.append((char) getFieldValue(data, "pdp.apn[" + i4 + "]"));
                strArr5[index] = sb5.toString();
            }
            int[] iArr2 = this.addr_type;
            if (iArr2[index] == 33) {
                this.ipv6_s[index] = "-";
            } else if (iArr2[index] == 87) {
                this.ipv4_s[index] = "-";
            }
            this.QoS[index] = getFieldValue(data, "pdp.em_negotiated_qos.human_readable_traffic_class");
        }
        for (int i5 = 0; i5 < 11; i5++) {
            if (this.status[i5]) {
                addData("SM_NSAP" + (i5 + 5), this.mMapping.get(Integer.valueOf(this.addr_type[i5])), this.ipv4_s[i5], this.ipv6_s[i5], this.pdpapn_s[i5], Integer.valueOf(this.QoS[i5]));
            }
        }
    }
}
