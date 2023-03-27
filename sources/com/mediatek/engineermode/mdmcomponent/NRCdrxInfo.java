package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NRCdrxInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NL2_MAC_DRX_STATUS_EVENT};
    int[][] dlRestranTimerAddrs = {new int[0], new int[]{8, 56, 112, 16}, new int[]{8, 56, 112, 16}, new int[]{8, 56, 112, 16}};
    int[][] dlRttTimerAddrs = {new int[0], new int[]{8, 56, 8, 8}, new int[]{8, 56, 8, 8}, new int[]{8, 56, 8, 8}};
    int[][] drxConfValidAddrs = {new int[0], new int[]{8, 56, 0, 8}, new int[]{8, 56, 0, 8}, new int[]{8, 56, 0, 8}};
    int[][] drxDurTimerAddrs = {new int[0], new int[]{8, 56, 32, 16, 16}, new int[]{8, 56, 32, 16, 16}, new int[]{8, 56, 32, 16, 16}};
    int[][] drxDurTimerUnitAddrs = {new int[0], new int[]{8, 56, 32, 0, 8}, new int[]{8, 56, 32, 0, 8}, new int[]{8, 56, 32, 0, 8}};
    int[][] inactTimerAddrs = {new int[0], new int[]{8, 56, 96, 16}, new int[]{8, 56, 96, 16}, new int[]{8, 56, 96, 16}};
    int[][] longCycleAddrs = {new int[0], new int[]{8, 56, Cea708CCParser.Const.CODE_C1_SPA, 16}, new int[]{8, 56, Cea708CCParser.Const.CODE_C1_SPA, 16}, new int[]{8, 56, Cea708CCParser.Const.CODE_C1_SPA, 16}};
    int[][] shortCycleAddrs = {new int[0], new int[]{8, 56, 64, 16, 16}, new int[]{8, 56, 64, 16, 16}, new int[]{8, 56, 64, 16, 16}};
    int[][] shortCycleTimerAddrs = {new int[0], new int[]{8, 56, 64, 8, 8}, new int[]{8, 56, 64, 8, 8}, new int[]{8, 56, 64, 8, 8}};
    int[][] shortCycleValidAddrs = {new int[0], new int[]{8, 56, 64, 0, 8}, new int[]{8, 56, 64, 0, 8}, new int[]{8, 56, 64, 0, 8}};
    int[][] slotOffsetAddrs = {new int[0], new int[]{8, 56, 24, 8}, new int[]{8, 56, 24, 8}, new int[]{8, 56, 24, 8}};
    int[][] startOffsetAddrs = {new int[0], new int[]{8, 56, 160, 16}, new int[]{8, 56, 160, 16}, new int[]{8, 56, 160, 16}};
    int[][] ulRestranTimerAddrs = {new int[0], new int[]{8, 56, 128, 16}, new int[]{8, 56, 128, 16}, new int[]{8, 56, 128, 16}};
    int[][] ulRttTimerAddrs = {new int[0], new int[]{8, 56, 16, 8}, new int[]{8, 56, 16, 8}, new int[]{8, 56, 16, 8}};

    public NRCdrxInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR CDRX Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "8. NR EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"OnDuration Timer", " Inactivity Timer", "Long Cycle", "Short Cycle", "Start Offset", "Slot Offset", "Short Cycle Timer", "DL RTT Timer", "UL RTT Timer", "DL Retransmission Timer", "UL Retransmission Timer"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String str;
        Object obj;
        String str2 = name;
        ByteBuffer icdPacket = (ByteBuffer) msg;
        clearData();
        int version = getFieldValueIcdVersion(icdPacket, 8);
        int drxConfValid = getFieldValueIcd(icdPacket, version, this.drxConfValidAddrs);
        Object obj2 = "Invalid";
        if (drxConfValid == 0) {
            Elog.d("EmInfo/MDMComponent", icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version + ", valid = " + drxConfValid);
            addData(obj2, obj2, obj2, obj2, obj2, obj2, obj2, obj2, obj2, obj2, obj2);
            ByteBuffer byteBuffer = icdPacket;
            int i = version;
            int i2 = drxConfValid;
            return;
        }
        int drxDurTimerUnit = getFieldValueIcd(icdPacket, version, this.drxDurTimerUnitAddrs);
        int drxDurTimer = getFieldValueIcd(icdPacket, version, this.drxDurTimerAddrs);
        int inactTimer = getFieldValueIcd(icdPacket, version, this.inactTimerAddrs);
        int longCycle = getFieldValueIcd(icdPacket, version, this.longCycleAddrs);
        int shortCycleValid = getFieldValueIcd(icdPacket, version, this.shortCycleValidAddrs);
        int shortCycle = getFieldValueIcd(icdPacket, version, this.shortCycleAddrs);
        int shortCycleTimer = getFieldValueIcd(icdPacket, version, this.shortCycleTimerAddrs);
        int startOffset = getFieldValueIcd(icdPacket, version, this.startOffsetAddrs);
        int slotOffset = getFieldValueIcd(icdPacket, version, this.slotOffsetAddrs);
        int shortCycleTimer2 = shortCycleTimer;
        int dlRttTimer = getFieldValueIcd(icdPacket, version, this.dlRttTimerAddrs);
        int ulRttTimer = getFieldValueIcd(icdPacket, version, this.ulRttTimerAddrs);
        int dlRestranTimer = getFieldValueIcd(icdPacket, version, this.dlRestranTimerAddrs);
        int ulRestranTimer = getFieldValueIcd(icdPacket, version, this.ulRestranTimerAddrs);
        ByteBuffer byteBuffer2 = icdPacket;
        String[] strArr = icdLogInfo;
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(" update,name id = ");
        sb.append(str2);
        sb.append(", version = ");
        sb.append(version);
        sb.append(", condition = ");
        sb.append(drxConfValid);
        sb.append(", ");
        sb.append(shortCycleValid);
        sb.append(", ");
        sb.append(drxDurTimerUnit);
        sb.append(", value = ");
        sb.append(drxDurTimerUnit);
        sb.append(", ");
        sb.append(drxDurTimer);
        sb.append(", ");
        sb.append(inactTimer);
        sb.append(", ");
        sb.append(longCycle);
        sb.append(", , ");
        sb.append(shortCycleValid);
        sb.append(", ");
        sb.append(shortCycle);
        sb.append(", ");
        sb.append(startOffset);
        sb.append(", ");
        sb.append(slotOffset);
        sb.append(", ");
        int shortCycleTimer3 = shortCycleTimer2;
        sb.append(shortCycleTimer3);
        sb.append(", ");
        int dlRttTimer2 = dlRttTimer;
        sb.append(dlRttTimer2);
        sb.append(", ");
        int ulRttTimer2 = ulRttTimer;
        sb.append(ulRttTimer2);
        sb.append(", ");
        int i3 = version;
        int dlRestranTimer2 = dlRestranTimer;
        sb.append(dlRestranTimer2);
        sb.append(", ");
        sb.append(ulRestranTimer);
        int i4 = drxConfValid;
        Elog.d("EmInfo/MDMComponent", strArr, sb.toString());
        Object[] objArr = new Object[11];
        StringBuilder sb2 = new StringBuilder();
        sb2.append(drxDurTimer);
        if (drxDurTimerUnit == 0) {
            int i5 = drxDurTimerUnit;
            str = " 1/32ms";
        } else {
            int i6 = drxDurTimerUnit;
            str = " ms";
        }
        sb2.append(str);
        objArr[0] = sb2.toString();
        objArr[1] = inactTimer + " ms";
        objArr[2] = longCycle + " ms";
        if (shortCycleValid == 0) {
            obj = obj2;
        } else {
            obj = shortCycle + " ms";
        }
        objArr[3] = obj;
        objArr[4] = startOffset + " ms";
        objArr[5] = slotOffset + " slot";
        if (shortCycleValid != 0) {
            obj2 = Integer.valueOf(shortCycleTimer3);
        }
        objArr[6] = obj2;
        objArr[7] = dlRttTimer2 + " symbol";
        objArr[8] = ulRttTimer2 + " symbol";
        objArr[9] = dlRestranTimer2 + " slot";
        objArr[10] = ulRestranTimer + " slot";
        addData(objArr);
    }
}
