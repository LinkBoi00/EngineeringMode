package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_4.DataCallFailCause;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* compiled from: MDMComponentICD */
class NRPNeighborCellInfo extends ArrayTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_NEIGHBOR_CELL_MEASUREMENT};
    int[] carTypeAddrs = {8, 24, 3};
    int[] cellNumAddrs = {8, 56, 0, 5};
    int[] narfcnAddrs = {8, 0, 22};
    int[] pciAddrs = {8, 56, 32, 0, 10};
    int[] rsrpAddrs = {8, 56, 32, 32, 16};
    int[] rsrqAddrs = {8, 56, 32, 96, 8};
    int[] rssiAddrs = {8, 56, 32, 128, 16};
    int[] sinrAddrs = {8, 56, 32, 192, 8};

    public NRPNeighborCellInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR Primary Neighbor Cell Info";
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
        return new String[]{"NARFCN", "PCI", "SSB - RSRP", "SSB - RSRQ", "SSB - RSSI", "SSB - SINR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        int i = 0;
        int version = getFieldValueIcdVersion(icdPacket, this.carTypeAddrs[0]);
        int carType = getFieldValueIcd(icdPacket, this.carTypeAddrs);
        int narfcn = getFieldValueIcd(icdPacket, this.narfcnAddrs);
        int cellNum = getFieldValueIcd(icdPacket, this.cellNumAddrs);
        if (carType == 0) {
            clearData();
            int i2 = 0;
            while (i2 < cellNum && i2 <= 20) {
                int[] updateElement = updateElement(this.pciAddrs, (i2 * 224) + i, -2);
                this.pciAddrs = updateElement;
                int pci = getFieldValueIcd(icdPacket, updateElement);
                int[] updateElement2 = updateElement(this.rsrpAddrs, (i2 * 224) + 32, -2);
                this.rsrpAddrs = updateElement2;
                int rsrp = getFieldValueIcd(icdPacket, updateElement2, true);
                int[] updateElement3 = updateElement(this.rsrqAddrs, (i2 * 224) + 96, -2);
                this.rsrqAddrs = updateElement3;
                int rsrq = getFieldValueIcd(icdPacket, updateElement3, true);
                int[] updateElement4 = updateElement(this.rssiAddrs, (i2 * 224) + 128, -2);
                this.rssiAddrs = updateElement4;
                int rssi = getFieldValueIcd(icdPacket, updateElement4, true);
                int[] updateElement5 = updateElement(this.sinrAddrs, (i2 * 224) + 192, -2);
                this.sinrAddrs = updateElement5;
                int sinr = getFieldValueIcd(icdPacket, updateElement5, true);
                String[] strArr = icdLogInfo;
                StringBuilder sb = new StringBuilder();
                sb.append(getName());
                sb.append(" update,name id = ");
                sb.append(name);
                sb.append(", version = ");
                sb.append(version);
                sb.append(", condition = ");
                sb.append(carType);
                sb.append(", ");
                sb.append(cellNum);
                ByteBuffer icdPacket2 = icdPacket;
                sb.append(", values = ");
                sb.append(narfcn);
                sb.append(", ");
                sb.append(pci);
                sb.append(", ");
                sb.append(rsrp);
                sb.append(", ");
                sb.append(rsrq);
                sb.append(", ");
                sb.append(rssi);
                sb.append(", ");
                sb.append(sinr);
                Elog.d("EmInfo/MDMComponent", strArr, sb.toString());
                addData(narfcn + "", pci + "", rsrp + "", rsrq + "", rssi + "", sinr + "");
                i2++;
                icdPacket = icdPacket2;
                i = 0;
            }
            String str = name;
            ByteBuffer byteBuffer = icdPacket;
            return;
        }
        String str2 = name;
        ByteBuffer byteBuffer2 = icdPacket;
    }

    public void testData() {
        if (0 == 0) {
            clearData();
            int i = 0;
            while (i < 5 && i <= 20) {
                this.pciAddrs = updateElement(this.pciAddrs, (i * 224) + 0, -2);
                int pci = (i * 2) + DataCallFailCause.INVALID_DNS_ADDR + i;
                this.rsrpAddrs = updateElement(this.rsrpAddrs, (i * 224) + 32, -2);
                int rsrp = (i * 2) + 3 + i;
                this.rsrqAddrs = updateElement(this.rsrqAddrs, (i * 224) + 96, -2);
                int rsrq = (i * 2) + 53 + i;
                this.rssiAddrs = updateElement(this.rssiAddrs, (i * 224) + 128, -2);
                int rssi = (i * 2) + 63 + i;
                this.sinrAddrs = updateElement(this.sinrAddrs, (i * 224) + 192, -2);
                int sinr = ((i * 2) + DataCallFailCause.INVALID_DNS_ADDR) - i;
                Elog.d("EmInfo/MDMComponent", Arrays.toString(this.pciAddrs) + ", " + Arrays.toString(this.rsrpAddrs) + ", " + Arrays.toString(this.rsrqAddrs) + ", " + Arrays.toString(this.rssiAddrs) + ", " + Arrays.toString(this.sinrAddrs));
                String[] strArr = icdLogInfo;
                Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + getName() + ", condition = " + 0 + ", " + 5 + ", values = " + 12345 + ", " + pci + ", " + rsrp + ", " + rsrq + ", " + rssi + ", " + sinr);
                StringBuilder sb = new StringBuilder();
                sb.append(12345);
                sb.append("");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(pci);
                sb2.append("");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(rsrp);
                sb3.append("");
                StringBuilder sb4 = new StringBuilder();
                sb4.append(rsrq);
                sb4.append("");
                StringBuilder sb5 = new StringBuilder();
                sb5.append(rssi);
                sb5.append("");
                StringBuilder sb6 = new StringBuilder();
                sb6.append(sinr);
                sb6.append("");
                addData(sb.toString(), sb2.toString(), sb3.toString(), sb4.toString(), sb5.toString(), sb6.toString());
                i++;
            }
        }
    }
}
