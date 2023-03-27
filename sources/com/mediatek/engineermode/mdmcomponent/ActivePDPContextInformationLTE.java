package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class ActivePDPContextInformationLTE extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ESM_ESM_INFO_IND, MDMContent.MSG_ID_EM_DDM_IP_INFO_IND};
    int[] QoS = new int[11];
    int[] addr_type = new int[11];
    String[] ipv4_s = new String[11];
    String[] ipv6_s = new String[11];
    int[] linked_cid = new int[11];
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

    public ActivePDPContextInformationLTE(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Active PDP Context Information(LTE)";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "5. LTE EM Component";
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
        return new String[]{"IPV4 address", "IPV6 address", "IP version", "QoS (QCI)", "APN"};
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        int addr_ipv6_base;
        String str = name;
        clearData();
        Msg data = (Msg) msg;
        int i = 11;
        boolean z = false;
        boolean z2 = true;
        if (str.equals(MDMContent.MSG_ID_EM_ESM_ESM_INFO_IND)) {
            int j = 0;
            while (j < i) {
                String coName = "esm_epsbc[" + j + "].";
                int is_active = getFieldValue(data, coName + "is_active");
                Elog.d("EmInfo/MDMComponent", coName + "is_active: " + is_active);
                if (is_active == 0) {
                    this.status[j] = z;
                } else {
                    this.status[j] = z2;
                    this.addr_type[j] = getFieldValue(data, coName + "ip_addr.ip_addr_type");
                    this.QoS[j] = getFieldValue(data, coName + "qci");
                    this.linked_cid[j] = getFieldValue(data, coName + "linked_cid");
                    int apn_length = getFieldValue(data, coName + "apn.length");
                    this.pdpapn_s[j] = "";
                    for (int i2 = 0; i2 < apn_length; i2++) {
                        int value = getFieldValue(data, coName + "apn.data[" + i2 + "]");
                        if (i2 <= 0 || value >= 31) {
                            StringBuilder sb = new StringBuilder();
                            String[] strArr = this.pdpapn_s;
                            sb.append(strArr[j]);
                            sb.append((char) value);
                            strArr[j] = sb.toString();
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            String[] strArr2 = this.pdpapn_s;
                            sb2.append(strArr2[j]);
                            sb2.append(".");
                            strArr2[j] = sb2.toString();
                        }
                    }
                }
                j++;
                i = 11;
                z = false;
                z2 = true;
            }
        }
        int i3 = 4;
        int i4 = 3;
        if (str.equals(MDMContent.MSG_ID_EM_DDM_IP_INFO_IND)) {
            int p_cid = getFieldValue(data, "p_cid");
            int addr_len = getFieldValue(data, "addr.len");
            int index = 0;
            while (true) {
                if (index >= 11) {
                    break;
                } else if (p_cid == this.linked_cid[index]) {
                    this.ipv4_s[index] = "";
                    int i5 = 0;
                    while (i5 < i3) {
                        int ipv4 = getFieldValue(data, "addr.val[" + i5 + "]");
                        StringBuilder sb3 = new StringBuilder();
                        String[] strArr3 = this.ipv4_s;
                        sb3.append(strArr3[index]);
                        sb3.append(ipv4);
                        strArr3[index] = sb3.toString();
                        if (i5 != i4) {
                            StringBuilder sb4 = new StringBuilder();
                            String[] strArr4 = this.ipv4_s;
                            sb4.append(strArr4[index]);
                            sb4.append(".");
                            strArr4[index] = sb4.toString();
                        }
                        i5++;
                        i3 = 4;
                    }
                    if (addr_len == 16) {
                        addr_ipv6_base = 0;
                    } else {
                        addr_ipv6_base = 4;
                    }
                    this.ipv6_s[index] = "";
                    for (int i6 = addr_ipv6_base + 0; i6 < addr_ipv6_base + 16; i6++) {
                        int ipv6 = getFieldValue(data, "addr.val[" + i6 + "]");
                        StringBuilder sb5 = new StringBuilder();
                        String[] strArr5 = this.ipv6_s;
                        sb5.append(strArr5[index]);
                        sb5.append(String.format("%02x", new Object[]{Integer.valueOf(ipv6)}));
                        strArr5[index] = sb5.toString();
                        if ((i6 + 1) % 2 == 0 && i6 != addr_ipv6_base + 15) {
                            StringBuilder sb6 = new StringBuilder();
                            String[] strArr6 = this.ipv6_s;
                            sb6.append(strArr6[index]);
                            sb6.append(":");
                            strArr6[index] = sb6.toString();
                        }
                    }
                } else {
                    index++;
                    i3 = 4;
                    i4 = 3;
                }
            }
        }
        for (int i7 = 0; i7 < 11; i7++) {
            if (this.status[i7]) {
                addData(this.ipv4_s[i7], this.ipv6_s[i7], this.mMapping.get(Integer.valueOf(this.addr_type[i7])), Integer.valueOf(this.QoS[i7]), this.pdpapn_s[i7]);
            }
        }
    }
}
