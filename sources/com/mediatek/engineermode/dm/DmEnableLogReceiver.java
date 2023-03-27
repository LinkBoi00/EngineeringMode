package com.mediatek.engineermode.dm;

import android.content.BroadcastReceiver;

public class DmEnableLogReceiver extends BroadcastReceiver {
    private static final String ACTION_ENABLE_DM_LOG = "com.mediatek.engineermode.dm.ACTION_ENABLE_DM_LOG";
    private static final String EXTRA_ENABLED = "enabled";
    private static final String EXTRA_TARGET = "target";
    private static final String TAG = "DmEnableLogReceiver";
    private static final String TARGET_ALL = "all";
    private static final String TARGET_DM = "dm";
    private static final String TARGET_PKM = "pkm";

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005f, code lost:
        if (r0.equals(TARGET_ALL) != false) goto L_0x006d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r8, android.content.Intent r9) {
        /*
            r7 = this;
            java.lang.String r0 = r9.getAction()
            java.lang.String r1 = "com.mediatek.engineermode.dm.ACTION_ENABLE_DM_LOG"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x007f
            java.lang.String r0 = "target"
            java.lang.String r0 = r9.getStringExtra(r0)
            if (r0 != 0) goto L_0x0016
            java.lang.String r0 = "none"
        L_0x0016:
            java.lang.String r1 = "enabled"
            r2 = 0
            int r1 = r9.getIntExtra(r1, r2)
            r3 = 1
            if (r1 <= 0) goto L_0x0022
            r1 = r3
            goto L_0x0023
        L_0x0022:
            r1 = r2
        L_0x0023:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "receive ACTION_ENABLE_DM_LOG, target = "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = ", enabled = "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "DmEnableLogReceiver"
            com.mediatek.engineermode.Elog.i(r5, r4)
            com.mediatek.engineermode.dm.DmSettingController r4 = new com.mediatek.engineermode.dm.DmSettingController
            r4.<init>(r8)
            r5 = -1
            int r6 = r0.hashCode()
            switch(r6) {
                case 3209: goto L_0x0062;
                case 96673: goto L_0x0059;
                case 111058: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x006c
        L_0x004f:
            java.lang.String r2 = "pkm"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x004e
            r2 = 2
            goto L_0x006d
        L_0x0059:
            java.lang.String r3 = "all"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x004e
            goto L_0x006d
        L_0x0062:
            java.lang.String r2 = "dm"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x004e
            r2 = r3
            goto L_0x006d
        L_0x006c:
            r2 = r5
        L_0x006d:
            switch(r2) {
                case 0: goto L_0x0079;
                case 1: goto L_0x0075;
                case 2: goto L_0x0071;
                default: goto L_0x0070;
            }
        L_0x0070:
            goto L_0x007f
        L_0x0071:
            r4.enablePkmLog(r1)
            goto L_0x007f
        L_0x0075:
            r4.enableDmLog(r1)
            goto L_0x007f
        L_0x0079:
            r4.enableDmLog(r1)
            r4.enablePkmLog(r1)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.dm.DmEnableLogReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
