package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;

/* compiled from: MDMComponentICD */
class NrTddPattern extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION};
    int[] dlSlotNum1Addrs = {8, 56, 64, 5, 9};
    int[] dlSlotNumAddrs = {8, 56, 5, 9};
    int[] dlSymbolNum1Addrs = {8, 56, 64, 23, 4};
    int[] dlSymbolNumAddrs = {8, 56, 23, 4};
    int[] transPeriod1Addrs = {8, 56, 64, 1, 4};
    int[] transPeriodAddrs = {8, 56, 1, 4};
    int[] ulSlotNum1Addrs = {8, 56, 64, 14, 9};
    int[] ulSlotNumAddrs = {8, 56, 14, 9};
    int[] ulSymbolNum1Addrs = {8, 56, 64, 27, 4};
    int[] ulSymbolNumAddrs = {8, 56, 27, 4};
    int[] valid1Addrs = {8, 56, 64, 0, 1};
    int[] validAddrs = {8, 56, 0, 1};

    public NrTddPattern(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR TDD UL/DL Pattern ";
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
        return new String[]{"Transmission Period 1", "DL slots 1", "UL slots 1", "DL symbols 1", "UL symbols 1", "Transmission Period 2", "DL slots 2", "UL slots 2", "DL symbols 2", "UL symbols 2"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int version = getFieldValueIcdVersion(icdPacket, this.validAddrs[0]);
        int valid = getFieldValueIcd(icdPacket, this.validAddrs);
        int transPeriod = getFieldValueIcd(icdPacket, this.transPeriodAddrs);
        int dlSlotNum = getFieldValueIcd(icdPacket, this.dlSlotNumAddrs);
        int ulSlotNum = getFieldValueIcd(icdPacket, this.ulSlotNumAddrs);
        int dlSymbolNum = getFieldValueIcd(icdPacket, this.dlSymbolNumAddrs);
        int ulSymbolNum = getFieldValueIcd(icdPacket, this.ulSymbolNumAddrs);
        int valid1 = getFieldValueIcd(icdPacket, this.valid1Addrs);
        int transPeriod1 = getFieldValueIcd(icdPacket, this.transPeriod1Addrs);
        int dlSlotNum1 = getFieldValueIcd(icdPacket, this.dlSlotNum1Addrs);
        int ulSlotNum1 = getFieldValueIcd(icdPacket, this.ulSlotNum1Addrs);
        int dlSymbolNum1 = getFieldValueIcd(icdPacket, this.dlSymbolNum1Addrs);
        int ulSymbolNum1 = getFieldValueIcd(icdPacket, this.ulSymbolNum1Addrs);
        String[] strArr = icdLogInfo;
        ByteBuffer byteBuffer = icdPacket;
        StringBuilder sb = new StringBuilder();
        int valid12 = valid1;
        sb.append(getName());
        sb.append(" update,name id = ");
        sb.append(name);
        sb.append(", version = ");
        sb.append(version);
        sb.append(", values = ");
        sb.append(transPeriod);
        sb.append(", ");
        sb.append(dlSlotNum);
        sb.append(", ");
        sb.append(ulSlotNum);
        sb.append(", ");
        sb.append(dlSymbolNum);
        sb.append(", ");
        sb.append(ulSymbolNum);
        sb.append(", ");
        sb.append(transPeriod1);
        sb.append(", ");
        sb.append(dlSlotNum1);
        sb.append(", ");
        sb.append(ulSlotNum1);
        sb.append(", ");
        sb.append(dlSymbolNum1);
        sb.append(", ");
        sb.append(ulSymbolNum1);
        Elog.d("EmInfo/MDMComponent", strArr, sb.toString());
        Object obj = "Invlid";
        setData(0, valid == 0 ? obj : Integer.valueOf(transPeriod));
        setData(1, valid == 0 ? obj : Integer.valueOf(dlSlotNum));
        setData(2, valid == 0 ? obj : Integer.valueOf(ulSlotNum));
        setData(3, valid == 0 ? obj : Integer.valueOf(dlSymbolNum));
        setData(4, valid == 0 ? obj : Integer.valueOf(ulSymbolNum));
        setData(5, valid12 == 0 ? obj : Integer.valueOf(transPeriod1));
        setData(6, valid12 == 0 ? obj : Integer.valueOf(dlSlotNum1));
        setData(7, valid12 == 0 ? obj : Integer.valueOf(ulSlotNum1));
        setData(8, valid12 == 0 ? obj : Integer.valueOf(dlSymbolNum1));
        if (valid12 != 0) {
            obj = Integer.valueOf(ulSymbolNum1);
        }
        setData(9, obj);
    }
}
