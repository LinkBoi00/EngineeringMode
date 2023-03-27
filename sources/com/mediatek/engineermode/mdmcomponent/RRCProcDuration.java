package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* compiled from: MDMComponentICD */
class RRCProcDuration extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NRRC_PROCEDURE_DURATION_EVENT};
    int[][] durationAddr = {new int[]{8, 24, 32}};
    int[][] procedureAddr = {new int[]{8, 0, 8}};
    int[] resumeDuration = new int[10];
    int resumeIndex = 0;
    int[] setupDuration = new int[10];
    int setupIndex = 0;

    public RRCProcDuration(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR RRC Procedure Duration";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "10. Procedure Duration";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"Procedure", "RRC Setup duration", "RRC Resume duration"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Integer num;
        Integer num2;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int[][] iArr = this.procedureAddr;
        int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
        int procedure = getFieldValueIcd(icdPacket, version, this.procedureAddr);
        int duration = getFieldValueIcd(icdPacket, version, this.durationAddr);
        String[] strArr = icdLogInfo;
        Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", condition = " + procedure + ", " + duration + ", value = " + Arrays.toString(this.setupDuration) + Arrays.toString(this.resumeDuration));
        if (procedure == 0) {
            int i = this.setupIndex;
            int[] iArr2 = this.setupDuration;
            if (i >= iArr2.length) {
                this.setupIndex = 0;
            }
            int i2 = this.setupIndex;
            iArr2[i2] = duration;
            this.setupIndex = i2 + 1;
            int sum = 0;
            int i3 = 0;
            while (true) {
                int[] iArr3 = this.setupDuration;
                if (i3 < iArr3.length) {
                    if (iArr3[i3] == 0) {
                        num2 = "";
                    } else {
                        num2 = Integer.valueOf(iArr3[i3]);
                    }
                    setDataAtPosition(i3, 1, num2);
                    setDataAtPosition(i3, 0, Integer.valueOf(i3));
                    sum += this.setupDuration[i3];
                    i3++;
                } else {
                    setDataAtPosition(iArr3.length, 0, "avg");
                    int[] iArr4 = this.setupDuration;
                    setDataAtPosition(iArr4.length, 1, Integer.valueOf(sum / iArr4.length));
                    return;
                }
            }
        } else if (procedure == 1) {
            int i4 = this.resumeIndex;
            int[] iArr5 = this.resumeDuration;
            if (i4 >= iArr5.length) {
                this.resumeIndex = 0;
            }
            int i5 = this.resumeIndex;
            iArr5[i5] = duration;
            this.resumeIndex = i5 + 1;
            int sum2 = 0;
            int i6 = 0;
            while (true) {
                int[] iArr6 = this.resumeDuration;
                if (i6 < iArr6.length) {
                    if (iArr6[i6] == 0) {
                        num = "";
                    } else {
                        num = Integer.valueOf(iArr6[i6]);
                    }
                    setDataAtPosition(i6, 2, num);
                    setDataAtPosition(i6, 0, Integer.valueOf(i6));
                    sum2 += this.resumeDuration[i6];
                    i6++;
                } else {
                    setDataAtPosition(iArr6.length, 0, "avg");
                    int[] iArr7 = this.resumeDuration;
                    setDataAtPosition(iArr7.length, 2, Integer.valueOf(sum2 / iArr7.length));
                    return;
                }
            }
        }
    }
}
