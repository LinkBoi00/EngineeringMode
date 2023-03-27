package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponent */
class GSMUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_GSM_TAS_INFO_IND, MDMContent.MSG_ID_EM_GSM_UTAS_INFO_IND};
    HashMap<Integer, String> ServingBandMapping = new HashMap<Integer, String>() {
        {
            put(1, "Band 850");
            put(2, "Band 900");
            put(3, "Band 1800");
            put(4, "Band 1900");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
        }
    };
    String[] labelsKey = {"Tas Common", "TX Info", "ANT Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS Enable Info", "Serving Band", "Cur ant state", "Current Primary RxLev", "Current Diversity RxLev", "Current SNR", "Tx Power Detect"});
            add(new String[]{"TX Index"});
            add(new String[]{"ANT Index", "TX Pwr dBm", "SNR", "RSSI dBm"});
        }
    };
    private String[] tabTitle;
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(GSMUTasInfo.this.initHashMap((Object[]) GSMUTasInfo.this.labelsList.get(0)));
            add(GSMUTasInfo.this.initHashMap((Object[]) GSMUTasInfo.this.labelsList.get(1)));
            add(GSMUTasInfo.this.initArrayHashMap((Object[]) GSMUTasInfo.this.labelsList.get(2)));
        }
    };

    public GSMUTasInfo(Activity context) {
        super(context);
        String[] strArr = {"Common", "Detail"};
        this.tabTitle = strArr;
        initTableComponent(strArr);
    }

    /* access modifiers changed from: package-private */
    public String tasEableMapping(int tasidx) {
        if (tasidx >= 0 && tasidx <= 1) {
            return this.TasEnableMapping.get(Integer.valueOf(tasidx));
        }
        return this.TasEnableMapping.get(2) + "(" + tasidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String servingBandMapping(int bandidx) {
        if (bandidx >= 1 && bandidx <= 4) {
            return this.ServingBandMapping.get(Integer.valueOf(bandidx));
        }
        return "-(" + bandidx + ")";
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "GSM UTAS Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "2. GSM EM Component";
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
    public ArrayList<String> getArrayTypeKey() {
        ArrayList<String> arrayTypeKeys = new ArrayList<>();
        arrayTypeKeys.add(this.labelsKey[2]);
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
    public LinkedHashMap<String, LinkedHashMap> getHashMapLabels(int index) {
        LinkedHashMap<String, LinkedHashMap> hashMapkeyValues = new LinkedHashMap<>();
        switch (index) {
            case 0:
                hashMapkeyValues.put(this.labelsKey[0], this.valuesHashMap.get(0));
                break;
            case 1:
                hashMapkeyValues.put(this.labelsKey[1], this.valuesHashMap.get(1));
                hashMapkeyValues.put(this.labelsKey[2], this.valuesHashMap.get(2));
                break;
        }
        return hashMapkeyValues;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x01a4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(java.lang.String r29, java.lang.Object r30) {
        /*
            r28 = this;
            r0 = r28
            r1 = r30
            com.mediatek.mdml.Msg r1 = (com.mediatek.mdml.Msg) r1
            r28.clearData()
            java.lang.String r2 = "MSG_ID_EM_GSM_TAS_INFO_IND"
            r3 = r29
            boolean r2 = r3.equals(r2)
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L_0x0019
            r0.setInfoValid(r4)
            goto L_0x001c
        L_0x0019:
            r0.setInfoValid(r5)
        L_0x001c:
            boolean r2 = r28.isInfoValid()
            r6 = -1
            if (r2 != 0) goto L_0x0066
            r28.resetView()
            android.widget.TextView r2 = new android.widget.TextView
            android.app.Activity r5 = r0.mContext
            r2.<init>(r5)
            android.widget.AbsListView$LayoutParams r5 = new android.widget.AbsListView$LayoutParams
            r7 = -2
            r5.<init>(r6, r7)
            r2.setLayoutParams(r5)
            r6 = 20
            r2.setPadding(r6, r4, r6, r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Use "
            r4.append(r6)
            java.lang.String r6 = r28.getName()
            java.lang.String r7 = "UTAS"
            java.lang.String r8 = "TAS"
            java.lang.String r6 = r6.replace(r7, r8)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r2.setText(r4)
            r4 = 1098907648(0x41800000, float:16.0)
            r2.setTextSize(r4)
            android.widget.ScrollView r4 = r0.scrollView
            r4.addView(r2)
            return
        L_0x0066:
            java.lang.String r2 = "tas_enable"
            int r2 = r0.getFieldValue(r1, r2)
            java.lang.String r7 = "serving_band"
            int r7 = r0.getFieldValue(r1, r7)
            java.lang.String r8 = "gsm_tx_antenna_rxLevel"
            int r8 = r0.getFieldValue(r1, r8, r5)
            java.lang.String r9 = "gsm_drx_antenna_rxLevel"
            int r9 = r0.getFieldValue(r1, r9, r5)
            java.lang.String r10 = "gsm_current_average_snr"
            int r10 = r0.getFieldValue(r1, r10, r5)
            java.lang.String r11 = "gsm_antenna_state"
            int r11 = r0.getFieldValue(r1, r11)
            java.lang.String r12 = "gsm_txPower_det"
            int r12 = r0.getFieldValue(r1, r12, r5)
            java.lang.String[] r13 = r0.labelsKey
            r13 = r13[r4]
            java.util.List<java.lang.String[]> r14 = r0.labelsList
            java.lang.Object r14 = r14.get(r4)
            java.lang.String[] r14 = (java.lang.String[]) r14
            r15 = 7
            java.lang.String[] r15 = new java.lang.String[r15]
            java.lang.String r16 = r0.tasEableMapping(r2)
            r15[r4] = r16
            java.lang.String r16 = r0.servingBandMapping(r7)
            r15[r5] = r16
            java.lang.String r5 = ")"
            java.lang.String r4 = "-("
            java.lang.String r6 = ""
            if (r11 < 0) goto L_0x00c5
            r19 = r2
            r2 = 23
            if (r11 > r2) goto L_0x00c7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r11)
            r2.append(r6)
            goto L_0x00d5
        L_0x00c5:
            r19 = r2
        L_0x00c7:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            r2.append(r11)
            r2.append(r5)
        L_0x00d5:
            java.lang.String r2 = r2.toString()
            r3 = 2
            r15[r3] = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r8)
            java.lang.String r3 = "dBm"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r21 = 3
            r15[r21] = r2
            r2 = -1
            if (r9 != r2) goto L_0x0103
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            r2.append(r9)
            r2.append(r5)
            goto L_0x010e
        L_0x0103:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r9)
            r2.append(r3)
        L_0x010e:
            java.lang.String r2 = r2.toString()
            r18 = r7
            r7 = 4
            r15[r7] = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r10)
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            r7 = 5
            r15[r7] = r2
            r2 = 6
            if (r12 < 0) goto L_0x013c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r12)
            r7.append(r3)
            java.lang.String r7 = r7.toString()
            goto L_0x013d
        L_0x013c:
            r7 = r6
        L_0x013d:
            r15[r2] = r7
            r2 = 0
            r0.setHashMapKeyValues((java.lang.String) r13, (int) r2, (java.lang.String[]) r14, (java.lang.Object[]) r15)
            java.lang.String[] r7 = r0.labelsKey
            r7 = r7[r2]
            r0.addData(r7, r2)
            java.lang.String r2 = "gsm_tx_ant_index"
            int r2 = r0.getFieldValue(r1, r2)
            java.lang.String[] r7 = r0.labelsKey
            r13 = 1
            r7 = r7[r13]
            java.util.List<java.lang.String[]> r14 = r0.labelsList
            java.lang.Object r14 = r14.get(r13)
            java.lang.String[] r14 = (java.lang.String[]) r14
            java.lang.String[] r15 = new java.lang.String[r13]
            r23 = r8
            r8 = 14
            if (r2 < r13) goto L_0x0177
            if (r2 > r8) goto L_0x0177
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r2)
            r13.append(r6)
            java.lang.String r13 = r13.toString()
            goto L_0x0189
        L_0x0177:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r4)
            r13.append(r2)
            r13.append(r5)
            java.lang.String r13 = r13.toString()
        L_0x0189:
            r17 = 0
            r15[r17] = r13
            r13 = 1
            r0.setHashMapKeyValues((java.lang.String) r7, (int) r13, (java.lang.String[]) r14, (java.lang.Object[]) r15)
            java.lang.String[] r7 = r0.labelsKey
            r7 = r7[r13]
            r0.addData(r7, r13)
            java.lang.String r7 = "available_ant_num"
            int r7 = r0.getFieldValue(r1, r7)
            r13 = 0
        L_0x019f:
            if (r13 >= r7) goto L_0x029f
            r14 = 5
            if (r13 > r14) goto L_0x029f
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r14 = "gsm_available_ant["
            r15.append(r14)
            r15.append(r13)
            java.lang.String r14 = "]"
            r15.append(r14)
            java.lang.String r15 = r15.toString()
            int r15 = r0.getFieldValue(r1, r15)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r24 = r2
            java.lang.String r2 = "gsm_antenna_rxLevel["
            r8.append(r2)
            r8.append(r13)
            r8.append(r14)
            java.lang.String r2 = r8.toString()
            r8 = 1
            int r2 = r0.getFieldValue(r1, r2, r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r25 = r7
            java.lang.String r7 = "gsm_snr["
            r8.append(r7)
            r8.append(r13)
            r8.append(r14)
            java.lang.String r7 = r8.toString()
            r8 = 1
            int r7 = r0.getFieldValue(r1, r7, r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r26 = r9
            java.lang.String r9 = "gsm_txpower["
            r8.append(r9)
            r8.append(r13)
            r8.append(r14)
            java.lang.String r8 = r8.toString()
            r9 = 1
            int r8 = r0.getFieldValue(r1, r8, r9)
            java.lang.String[] r14 = r0.labelsKey
            r9 = 2
            r14 = r14[r9]
            r27 = r1
            java.util.List<java.lang.String[]> r1 = r0.labelsList
            java.lang.Object r1 = r1.get(r9)
            java.lang.String[] r1 = (java.lang.String[]) r1
            r22 = r10
            r9 = 4
            java.lang.String[] r10 = new java.lang.String[r9]
            r9 = 1
            if (r15 < r9) goto L_0x0236
            r9 = 14
            if (r15 > r9) goto L_0x0236
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r15)
            r9.append(r6)
            goto L_0x0244
        L_0x0236:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r4)
            r9.append(r15)
            r9.append(r5)
        L_0x0244:
            java.lang.String r9 = r9.toString()
            r17 = 0
            r10[r17] = r9
            if (r8 < 0) goto L_0x025e
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r8)
            r9.append(r3)
            java.lang.String r9 = r9.toString()
            goto L_0x025f
        L_0x025e:
            r9 = r6
        L_0x025f:
            r16 = 1
            r10[r16] = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            r20 = 2
            r10[r20] = r9
            if (r2 > 0) goto L_0x0288
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r2)
            r9.append(r3)
            java.lang.String r9 = r9.toString()
            goto L_0x0289
        L_0x0288:
            r9 = r6
        L_0x0289:
            r10[r21] = r9
            r9 = 1
            r0.setHashMapKeyValues((java.lang.String) r14, (int) r9, (java.lang.String[]) r1, (java.lang.Object[]) r10)
            int r13 = r13 + 1
            r10 = r22
            r2 = r24
            r7 = r25
            r9 = r26
            r1 = r27
            r8 = 14
            goto L_0x019f
        L_0x029f:
            r27 = r1
            r24 = r2
            r25 = r7
            r26 = r9
            r22 = r10
            java.lang.String[] r1 = r0.labelsKey
            r2 = 2
            r1 = r1[r2]
            r2 = 1
            r0.addData(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.GSMUTasInfo.update(java.lang.String, java.lang.Object):void");
    }
}
