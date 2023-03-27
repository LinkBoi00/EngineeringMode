package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponent */
class LteUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};
    HashMap<Integer, String> RRCStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "RRC Idle");
            put(1, "RRC Connected");
            put(2, "-");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
        }
    };
    String[] labelKeys = {"Tas Common", "Cell Info", "UL Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS Enable", "Switch state", "TX Power", "RRC status"});
            add(new String[]{"Band", "TX Index"});
            add(new String[]{"ANT Index", "TX Pwr dBm", "PHR dB", "RSRP dBm"});
        }
    };
    private String[] tabTitle;
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(LteUTasInfo.this.initHashMap((Object[]) LteUTasInfo.this.labelsList.get(0)));
            add(LteUTasInfo.this.initHashMap((Object[]) LteUTasInfo.this.labelsList.get(1)));
            add(LteUTasInfo.this.initArrayHashMap((Object[]) LteUTasInfo.this.labelsList.get(2)));
        }
    };

    public LteUTasInfo(Activity context) {
        super(context);
        String[] strArr = {"Common", "CC0", "CC1", "CC2"};
        this.tabTitle = strArr;
        initTableComponent(strArr);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "LTE UTAS Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "5. LTE EM Component";
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x04c8  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x04c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(java.lang.String r39, java.lang.Object r40) {
        /*
            r38 = this;
            r0 = r38
            r1 = r40
            com.mediatek.mdml.Msg r1 = (com.mediatek.mdml.Msg) r1
            r38.clearData()
            java.lang.String r2 = "ul_info"
            java.lang.String r3 = "cell_info"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            java.lang.String r5 = "[0]."
            r4.append(r5)
            java.lang.String r6 = "utas_info_valid"
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            int r4 = r0.getFieldValue(r1, r4)
            r0.setInfoValid(r4)
            boolean r6 = r38.isInfoValid()
            if (r6 != 0) goto L_0x0031
            return
        L_0x0031:
            java.lang.String r6 = "dl_cc_count"
            int r6 = r0.getFieldValue(r1, r6)
            java.lang.String r7 = "ul_cc_count"
            int r7 = r0.getFieldValue(r1, r7)
            java.lang.String r11 = ")"
            java.lang.String r12 = "-("
            java.lang.String r13 = ""
            r15 = 2
            r10 = 1
            if (r6 <= 0) goto L_0x0179
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r5)
            java.lang.String r8 = "tas_status"
            r14.append(r8)
            java.lang.String r8 = r14.toString()
            int r8 = r0.getFieldValue(r1, r8)
            if (r8 < 0) goto L_0x0070
            if (r8 > r10) goto L_0x0070
            java.util.HashMap<java.lang.Integer, java.lang.String> r14 = r0.TasEnableMapping
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            java.lang.Object r9 = r14.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            goto L_0x0091
        L_0x0070:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.util.HashMap<java.lang.Integer, java.lang.String> r14 = r0.TasEnableMapping
            java.lang.Integer r10 = java.lang.Integer.valueOf(r15)
            java.lang.Object r10 = r14.get(r10)
            java.lang.String r10 = (java.lang.String) r10
            r9.append(r10)
            r9.append(r12)
            r9.append(r8)
            r9.append(r11)
            java.lang.String r9 = r9.toString()
        L_0x0091:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r2)
            r10.append(r5)
            java.lang.String r14 = "utas_switch_state"
            r10.append(r14)
            java.lang.String r10 = r10.toString()
            int r10 = r0.getFieldValue(r1, r10)
            if (r10 < 0) goto L_0x00bc
            r14 = 31
            if (r10 > r14) goto L_0x00bc
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r13)
            r14.append(r10)
            goto L_0x00ca
        L_0x00bc:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r12)
            r14.append(r10)
            r14.append(r11)
        L_0x00ca:
            java.lang.String r14 = r14.toString()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r2)
            r15.append(r5)
            java.lang.String r5 = "utas_tx_power"
            r15.append(r5)
            java.lang.String r5 = r15.toString()
            r15 = 1
            int r5 = r0.getFieldValue(r1, r5, r15)
            r15 = -50
            if (r5 < r15) goto L_0x0103
            r15 = 33
            if (r5 > r15) goto L_0x0103
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r5)
            r20 = r4
            java.lang.String r4 = "dBm"
            r15.append(r4)
            java.lang.String r4 = r15.toString()
            goto L_0x0117
        L_0x0103:
            r20 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r12)
            r4.append(r5)
            r4.append(r11)
            java.lang.String r4 = r4.toString()
        L_0x0117:
            r15 = 1
            if (r6 != r15) goto L_0x012f
            if (r7 != 0) goto L_0x012f
            java.util.HashMap<java.lang.Integer, java.lang.String> r15 = r0.RRCStateMapping
            r21 = r5
            r17 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r17)
            java.lang.Object r5 = r15.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            r22 = r8
            goto L_0x0152
        L_0x012f:
            r21 = r5
            r5 = 1
            if (r6 < r5) goto L_0x0143
            if (r7 < r5) goto L_0x0143
            java.util.HashMap<java.lang.Integer, java.lang.String> r15 = r0.RRCStateMapping
            r22 = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r15.get(r8)
            goto L_0x0150
        L_0x0143:
            r22 = r8
            java.util.HashMap<java.lang.Integer, java.lang.String> r5 = r0.RRCStateMapping
            r8 = 2
            java.lang.Integer r15 = java.lang.Integer.valueOf(r8)
            java.lang.Object r5 = r5.get(r15)
        L_0x0150:
            java.lang.String r5 = (java.lang.String) r5
        L_0x0152:
            java.lang.String[] r8 = r0.labelKeys
            r15 = 0
            r8 = r8[r15]
            r17 = r10
            java.util.List<java.lang.String[]> r10 = r0.labelsList
            java.lang.Object r10 = r10.get(r15)
            java.lang.String[] r10 = (java.lang.String[]) r10
            r23 = r13
            r13 = 4
            java.lang.String[] r13 = new java.lang.String[r13]
            r13[r15] = r9
            r18 = 1
            r13[r18] = r14
            r19 = 2
            r13[r19] = r4
            r16 = 3
            r13[r16] = r5
            r0.setHashMapKeyValues((java.lang.String) r8, (int) r15, (java.lang.String[]) r10, (java.lang.Object[]) r13)
            goto L_0x0191
        L_0x0179:
            r20 = r4
            r23 = r13
            r15 = 0
            java.lang.String[] r4 = r0.labelKeys
            r4 = r4[r15]
            java.util.List<java.lang.String[]> r5 = r0.labelsList
            java.lang.Object r5 = r5.get(r15)
            java.lang.Object[] r5 = (java.lang.Object[]) r5
            java.util.LinkedHashMap r5 = r0.initHashMap(r5)
            r0.setHashMapKeyValues(r4, r15, r5)
        L_0x0191:
            java.lang.String[] r4 = r0.labelKeys
            r4 = r4[r15]
            r0.addData(r4, r15)
            r4 = 3
            int[] r5 = new int[r4]
            int[] r8 = new int[r4]
            java.lang.String[] r9 = new java.lang.String[r4]
            java.lang.String[] r10 = new java.lang.String[r4]
            r4 = 2
            int[] r13 = new int[r4]
            r13 = {3, 5} // fill-array
            java.lang.Class<java.lang.String> r14 = java.lang.String.class
            java.lang.Object r13 = java.lang.reflect.Array.newInstance(r14, r13)
            java.lang.String[][] r13 = (java.lang.String[][]) r13
            int[] r14 = new int[r4]
            r14 = {3, 5} // fill-array
            java.lang.Class<java.lang.String> r15 = java.lang.String.class
            java.lang.Object r14 = java.lang.reflect.Array.newInstance(r15, r14)
            java.lang.String[][] r14 = (java.lang.String[][]) r14
            int[] r15 = new int[r4]
            r15 = {3, 5} // fill-array
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            java.lang.Object r4 = java.lang.reflect.Array.newInstance(r4, r15)
            java.lang.String[][] r4 = (java.lang.String[][]) r4
            r21 = r4
            r15 = 2
            int[] r4 = new int[r15]
            r4 = {3, 5} // fill-array
            java.lang.Class<java.lang.String> r15 = java.lang.String.class
            java.lang.Object r4 = java.lang.reflect.Array.newInstance(r15, r4)
            java.lang.String[][] r4 = (java.lang.String[][]) r4
            r15 = 1
            if (r7 < r15) goto L_0x01df
            r18 = r15
            goto L_0x01e1
        L_0x01df:
            r18 = 0
        L_0x01e1:
            r17 = 0
            r5[r17] = r18
            if (r6 < r15) goto L_0x01e9
            r15 = 1
            goto L_0x01eb
        L_0x01e9:
            r15 = r17
        L_0x01eb:
            r8[r17] = r15
            r15 = 0
        L_0x01ee:
            r22 = r6
            r6 = 3
            if (r15 >= r6) goto L_0x0505
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r3)
            r24 = r7
            java.lang.String r7 = "["
            r6.append(r7)
            r6.append(r15)
            r25 = r4
            java.lang.String r4 = "]."
            r6.append(r4)
            r26 = r14
            java.lang.String r14 = "band"
            r6.append(r14)
            java.lang.String r6 = r6.toString()
            int r6 = r0.getFieldValue(r1, r6)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r3)
            r14.append(r7)
            r14.append(r15)
            r14.append(r4)
            r27 = r3
            java.lang.String r3 = "ul_cc_idx"
            r14.append(r3)
            java.lang.String r3 = r14.toString()
            r14 = 1
            int r3 = r0.getFieldValue(r1, r3, r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r7)
            r14.append(r15)
            r14.append(r4)
            r28 = r13
            java.lang.String r13 = "utas_cur_ant_idx"
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            int r13 = r0.getFieldValue(r1, r13)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r7)
            r14.append(r15)
            r14.append(r4)
            r29 = r10
            java.lang.String r10 = "utas_tx_activated"
            r14.append(r10)
            java.lang.String r10 = r14.toString()
            int r10 = r0.getFieldValue(r1, r10)
            if (r15 <= 0) goto L_0x028d
            r14 = -1
            if (r3 == r14) goto L_0x0286
            r14 = 1
            if (r10 != r14) goto L_0x0286
            r14 = 1
            goto L_0x0287
        L_0x0286:
            r14 = 0
        L_0x0287:
            r5[r15] = r14
            r14 = r5[r15]
            r8[r15] = r14
        L_0x028d:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r7)
            r14.append(r15)
            r14.append(r4)
            r30 = r3
            java.lang.String r3 = "utas_max_ant_idx_cnt"
            r14.append(r3)
            java.lang.String r3 = r14.toString()
            int r3 = r0.getFieldValue(r1, r3)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r31 = r10
            java.lang.String r10 = "tab: "
            r14.append(r10)
            int r10 = r15 + 1
            r14.append(r10)
            java.lang.String r10 = ", "
            r14.append(r10)
            r14.append(r13)
            r14.append(r10)
            r10 = r5[r15]
            r14.append(r10)
            java.lang.String r10 = ":"
            r14.append(r10)
            r10 = r8[r15]
            r14.append(r10)
            java.lang.String r10 = r14.toString()
            java.lang.String r14 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r14, r10)
            if (r6 < 0) goto L_0x02f5
            r10 = 255(0xff, float:3.57E-43)
            if (r6 > r10) goto L_0x02f5
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r14 = "Band "
            r10.append(r14)
            r10.append(r6)
            goto L_0x0303
        L_0x02f5:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r12)
            r10.append(r6)
            r10.append(r11)
        L_0x0303:
            java.lang.String r10 = r10.toString()
            r9[r15] = r10
            r10 = 10
            if (r13 < 0) goto L_0x031d
            if (r13 > r10) goto L_0x031d
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r10 = r23
            r14.append(r10)
            r14.append(r13)
            goto L_0x032d
        L_0x031d:
            r10 = r23
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r12)
            r14.append(r13)
            r14.append(r11)
        L_0x032d:
            java.lang.String r14 = r14.toString()
            r29[r15] = r14
            r14 = 0
        L_0x0334:
            r23 = r6
            r6 = 5
            if (r14 >= r6) goto L_0x04e7
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            r6.append(r7)
            r6.append(r15)
            r6.append(r4)
            r32 = r13
            java.lang.String r13 = "utas_partial_blank"
            r6.append(r13)
            r6.append(r7)
            r6.append(r14)
            java.lang.String r13 = "]"
            r6.append(r13)
            java.lang.String r6 = r6.toString()
            int r6 = r0.getFieldValue(r1, r6)
            r33 = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r2)
            r9.append(r7)
            r9.append(r15)
            r9.append(r4)
            r34 = r8
            java.lang.String r8 = "utas_ant_idx"
            r9.append(r8)
            r9.append(r7)
            r9.append(r14)
            r9.append(r13)
            java.lang.String r8 = r9.toString()
            int r8 = r0.getFieldValue(r1, r8)
            r9 = r28[r15]
            if (r8 < 0) goto L_0x03a5
            r35 = r5
            r5 = 10
            if (r8 > r5) goto L_0x03a7
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r8)
            r5.append(r10)
            goto L_0x03b5
        L_0x03a5:
            r35 = r5
        L_0x03a7:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r12)
            r5.append(r8)
            r5.append(r11)
        L_0x03b5:
            java.lang.String r5 = r5.toString()
            r9[r14] = r5
            r5 = 1
            if (r6 == r5) goto L_0x04bb
            if (r14 >= r3) goto L_0x04bb
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r2)
            r5.append(r7)
            r5.append(r15)
            r5.append(r4)
            java.lang.String r9 = "utas_tx_pwr"
            r5.append(r9)
            r5.append(r7)
            r5.append(r14)
            r5.append(r13)
            java.lang.String r5 = r5.toString()
            r9 = 1
            int r5 = r0.getFieldValue(r1, r5, r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r2)
            r9.append(r7)
            r9.append(r15)
            r9.append(r4)
            r36 = r6
            java.lang.String r6 = "utas_phr"
            r9.append(r6)
            r9.append(r7)
            r9.append(r14)
            r9.append(r13)
            java.lang.String r6 = r9.toString()
            r9 = 1
            int r6 = r0.getFieldValue(r1, r6, r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r2)
            r9.append(r7)
            r9.append(r15)
            r9.append(r4)
            r37 = r2
            java.lang.String r2 = "utas_rsrp"
            r9.append(r2)
            r9.append(r7)
            r9.append(r14)
            r9.append(r13)
            java.lang.String r2 = r9.toString()
            r9 = 1
            int r2 = r0.getFieldValue(r1, r2, r9)
            r9 = r26[r15]
            r13 = -50
            if (r5 < r13) goto L_0x0452
            r13 = 33
            if (r5 > r13) goto L_0x0452
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r5)
            r13.append(r10)
            goto L_0x0460
        L_0x0452:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            r13.append(r5)
            r13.append(r11)
        L_0x0460:
            java.lang.String r13 = r13.toString()
            r9[r14] = r13
            r9 = r21[r15]
            r13 = -50
            if (r6 < r13) goto L_0x047c
            r13 = 33
            if (r6 > r13) goto L_0x047c
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r6)
            r13.append(r10)
            goto L_0x048a
        L_0x047c:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            r13.append(r6)
            r13.append(r11)
        L_0x048a:
            java.lang.String r13 = r13.toString()
            r9[r14] = r13
            r9 = r25[r15]
            r13 = -140(0xffffffffffffff74, float:NaN)
            if (r2 < r13) goto L_0x04a6
            r13 = 18
            if (r2 > r13) goto L_0x04a6
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r2)
            r13.append(r10)
            goto L_0x04b4
        L_0x04a6:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r12)
            r13.append(r2)
            r13.append(r11)
        L_0x04b4:
            java.lang.String r13 = r13.toString()
            r9[r14] = r13
            goto L_0x04d7
        L_0x04bb:
            r37 = r2
            r36 = r6
            r2 = r28[r15]
            if (r14 >= r3) goto L_0x04c8
            r5 = r28[r15]
            r5 = r5[r14]
            goto L_0x04c9
        L_0x04c8:
            r5 = r10
        L_0x04c9:
            r2[r14] = r5
            r2 = r26[r15]
            r2[r14] = r10
            r2 = r21[r15]
            r2[r14] = r10
            r2 = r25[r15]
            r2[r14] = r10
        L_0x04d7:
            int r14 = r14 + 1
            r6 = r23
            r13 = r32
            r9 = r33
            r8 = r34
            r5 = r35
            r2 = r37
            goto L_0x0334
        L_0x04e7:
            r37 = r2
            r35 = r5
            r34 = r8
            r33 = r9
            r32 = r13
            int r15 = r15 + 1
            r23 = r10
            r6 = r22
            r7 = r24
            r4 = r25
            r14 = r26
            r3 = r27
            r13 = r28
            r10 = r29
            goto L_0x01ee
        L_0x0505:
            r37 = r2
            r27 = r3
            r25 = r4
            r35 = r5
            r24 = r7
            r34 = r8
            r33 = r9
            r29 = r10
            r28 = r13
            r26 = r14
            r10 = r23
            r2 = 1
            r3 = r35[r2]
            if (r3 != 0) goto L_0x0546
            r3 = 2
            r4 = r35[r3]
            if (r4 != r2) goto L_0x0546
            r34[r2] = r2
            r35[r2] = r2
            r4 = 0
            r34[r3] = r4
            r35[r3] = r4
            r4 = r33[r3]
            r33[r2] = r4
            r4 = r29[r3]
            r29[r2] = r4
            r4 = r28[r3]
            r28[r2] = r4
            r4 = r26[r3]
            r26[r2] = r4
            r4 = r21[r3]
            r21[r2] = r4
            r4 = r25[r3]
            r25[r2] = r4
        L_0x0546:
            r2 = 0
        L_0x0547:
            r3 = 3
            if (r2 >= r3) goto L_0x060e
            java.lang.String[] r3 = r0.labelKeys
            r4 = 1
            r3 = r3[r4]
            int r5 = r2 + 1
            java.util.List<java.lang.String[]> r6 = r0.labelsList
            java.lang.Object r6 = r6.get(r4)
            java.lang.String[] r6 = (java.lang.String[]) r6
            r7 = 0
            r6 = r6[r7]
            r7 = r34[r2]
            if (r7 != r4) goto L_0x0563
            r4 = r33[r2]
            goto L_0x0564
        L_0x0563:
            r4 = r10
        L_0x0564:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r5, (java.lang.String) r6, (java.lang.Object) r4)
            java.lang.String[] r3 = r0.labelKeys
            r4 = 2
            r3 = r3[r4]
            int r5 = r2 + 1
            java.util.List<java.lang.String[]> r6 = r0.labelsList
            java.lang.Object r6 = r6.get(r4)
            java.lang.String[] r6 = (java.lang.String[]) r6
            r4 = 0
            r6 = r6[r4]
            r7 = r34[r2]
            r8 = 0
            r9 = 1
            if (r7 != r9) goto L_0x0582
            r7 = r28[r2]
            goto L_0x0583
        L_0x0582:
            r7 = r8
        L_0x0583:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r5, (java.lang.String) r6, (java.lang.String[]) r7)
            java.lang.String[] r3 = r0.labelKeys
            r5 = 2
            r3 = r3[r5]
            int r6 = r2 + 1
            java.util.List<java.lang.String[]> r7 = r0.labelsList
            java.lang.Object r7 = r7.get(r5)
            java.lang.String[] r7 = (java.lang.String[]) r7
            r5 = 3
            r7 = r7[r5]
            r9 = r34[r2]
            r11 = 1
            if (r9 != r11) goto L_0x05a0
            r9 = r25[r2]
            goto L_0x05a1
        L_0x05a0:
            r9 = r8
        L_0x05a1:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r6, (java.lang.String) r7, (java.lang.String[]) r9)
            java.lang.String[] r3 = r0.labelKeys
            r3 = r3[r11]
            int r6 = r2 + 1
            java.util.List<java.lang.String[]> r7 = r0.labelsList
            java.lang.Object r7 = r7.get(r11)
            java.lang.String[] r7 = (java.lang.String[]) r7
            r7 = r7[r11]
            r9 = r34[r2]
            if (r9 != r11) goto L_0x05bb
            r9 = r29[r2]
            goto L_0x05bc
        L_0x05bb:
            r9 = r10
        L_0x05bc:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r6, (java.lang.String) r7, (java.lang.Object) r9)
            java.lang.String[] r3 = r0.labelKeys
            r6 = 2
            r3 = r3[r6]
            int r7 = r2 + 1
            java.util.List<java.lang.String[]> r9 = r0.labelsList
            java.lang.Object r9 = r9.get(r6)
            java.lang.String[] r9 = (java.lang.String[]) r9
            r6 = 1
            r9 = r9[r6]
            r11 = r35[r2]
            if (r11 != r6) goto L_0x05d8
            r6 = r26[r2]
            goto L_0x05d9
        L_0x05d8:
            r6 = r8
        L_0x05d9:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r7, (java.lang.String) r9, (java.lang.String[]) r6)
            java.lang.String[] r3 = r0.labelKeys
            r6 = 2
            r3 = r3[r6]
            int r7 = r2 + 1
            java.util.List<java.lang.String[]> r9 = r0.labelsList
            java.lang.Object r9 = r9.get(r6)
            java.lang.String[] r9 = (java.lang.String[]) r9
            r9 = r9[r6]
            r6 = r35[r2]
            r11 = 1
            if (r6 != r11) goto L_0x05f4
            r8 = r21[r2]
        L_0x05f4:
            r0.setHashMapKeyValues((java.lang.String) r3, (int) r7, (java.lang.String) r9, (java.lang.String[]) r8)
            java.lang.String[] r3 = r0.labelKeys
            r3 = r3[r11]
            int r6 = r2 + 1
            r0.addData(r3, r6)
            java.lang.String[] r3 = r0.labelKeys
            r6 = 2
            r3 = r3[r6]
            int r7 = r2 + 1
            r0.addData(r3, r7)
            int r2 = r2 + 1
            goto L_0x0547
        L_0x060e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.LteUTasInfo.update(java.lang.String, java.lang.Object):void");
    }

    public void addDataToList(ArrayList<int[]> list, int[] value) {
        for (int i = 0; i < value.length; i++) {
            if (list.get(i) == null) {
                list.add(i, new int[]{value[i]});
            } else {
                int[] newValue = Arrays.copyOf(list.get(i), list.get(i).length + 1);
                newValue[newValue.length - 1] = value[i];
                list.add(i, newValue);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, LinkedHashMap> getHashMapLabels(int index) {
        LinkedHashMap<String, LinkedHashMap> hashMapkeyValues = new LinkedHashMap<>();
        switch (index) {
            case 0:
                hashMapkeyValues.put(this.labelKeys[0], this.valuesHashMap.get(0));
                break;
            case 1:
            case 2:
            case 3:
                hashMapkeyValues.put(this.labelKeys[1], this.valuesHashMap.get(1));
                hashMapkeyValues.put(this.labelKeys[2], this.valuesHashMap.get(2));
                break;
        }
        return hashMapkeyValues;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> getArrayTypeKey() {
        ArrayList<String> arrayTypeKeys = new ArrayList<>();
        arrayTypeKeys.add("UL Info");
        return arrayTypeKeys;
    }

    /* access modifiers changed from: package-private */
    public boolean isLabelArrayType(String label) {
        if (getArrayTypeKey().contains(label)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }
}
