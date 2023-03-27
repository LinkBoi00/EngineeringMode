package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;

/* renamed from: com.mediatek.engineermode.mdmcomponent.LteSCellInfo  reason: case insensitive filesystem */
/* compiled from: MDMComponent */
class C0000LteSCellInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND};

    public C0000LteSCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Secondary Cell Info";
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
    public String[] getLabels() {
        return new String[]{"PCI", "EARFCN (Band)", "SINR", "RSRP", "RSRQ"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: java.lang.String} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(java.lang.String r18, java.lang.Object r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            com.mediatek.mdml.Msg r1 = (com.mediatek.mdml.Msg) r1
            java.lang.String r2 = "scell_info_list.scell_info[0]."
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = "earfcn"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            int r3 = r0.getFieldValue(r1, r3)
            long r3 = (long) r3
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r2)
            java.lang.String r6 = "pci"
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            int r5 = r0.getFieldValue(r1, r5)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            java.lang.String r7 = "rsrp"
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r7 = 1
            int r6 = r0.getFieldValue(r1, r6, r7)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r2)
            java.lang.String r9 = "rsrq"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            int r8 = r0.getFieldValue(r1, r8, r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r2)
            java.lang.String r10 = "rs_snr_in_qdb"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            int r9 = r0.getFieldValue(r1, r9, r7)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r2)
            java.lang.String r11 = "serv_lte_band"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            int r10 = r0.getFieldValue(r1, r10)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "band = "
            r11.append(r12)
            r11.append(r10)
            java.lang.String r11 = r11.toString()
            java.lang.String r12 = "EmInfo/MDMComponent"
            com.mediatek.engineermode.Elog.d(r12, r11)
            r17.clearData()
            java.lang.Object[] r11 = new java.lang.Object[r7]
            r12 = -1
            int r14 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            java.lang.String r15 = ""
            if (r14 != 0) goto L_0x00ad
            r14 = r15
            goto L_0x00b1
        L_0x00ad:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
        L_0x00b1:
            r16 = 0
            r11[r16] = r14
            r0.addData((java.lang.Object[]) r11)
            int r11 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r11 != 0) goto L_0x00be
            r11 = r15
            goto L_0x00dc
        L_0x00be:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "EARFCN: "
            r11.append(r12)
            r11.append(r3)
            java.lang.String r12 = " (Band "
            r11.append(r12)
            r11.append(r10)
            java.lang.String r12 = ")"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
        L_0x00dc:
            r0.addData((java.lang.String) r11)
            java.lang.Object[] r11 = new java.lang.Object[r7]
            r12 = 1082130432(0x40800000, float:4.0)
            r13 = -1
            if (r6 != r13) goto L_0x00e8
            r14 = r15
            goto L_0x00ee
        L_0x00e8:
            float r14 = (float) r6
            float r14 = r14 / r12
            java.lang.Float r14 = java.lang.Float.valueOf(r14)
        L_0x00ee:
            r11[r16] = r14
            r0.addData((java.lang.Object[]) r11)
            java.lang.Object[] r11 = new java.lang.Object[r7]
            if (r8 != r13) goto L_0x00f9
            r14 = r15
            goto L_0x00ff
        L_0x00f9:
            float r14 = (float) r8
            float r14 = r14 / r12
            java.lang.Float r14 = java.lang.Float.valueOf(r14)
        L_0x00ff:
            r11[r16] = r14
            r0.addData((java.lang.Object[]) r11)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            if (r9 != r13) goto L_0x0109
            goto L_0x010f
        L_0x0109:
            float r11 = (float) r9
            float r11 = r11 / r12
            java.lang.Float r15 = java.lang.Float.valueOf(r11)
        L_0x010f:
            r7[r16] = r15
            r0.addData((java.lang.Object[]) r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.C0000LteSCellInfo.update(java.lang.String, java.lang.Object):void");
    }
}
