package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponentICD */
class NRUTasInfo extends CombinationTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_RECORD, MDMContentICD.MSG_ID_NL1_TAS_INFORMATION, MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION, MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT};
    HashMap<Integer, String> RRCStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "null");
            put(1, "idleCampedOnAnyCell");
            put(2, "idleCampedNormally");
            put(3, "idlConnecting");
            put(4, "connectedNormally");
            put(5, "releasing");
            put(6, "atmptOutbndMobility");
            put(7, "atmptInbndMobility");
            put(8, "inactive");
            put(9, "endcScgAdded");
            put(10, "endcScgSuspended");
        }
    };
    HashMap<Integer, String> TasEnableMapping = new HashMap<Integer, String>() {
        {
            put(0, "DISABLE");
            put(1, "ENABLE");
        }
    };
    int antReportdLen;
    int[][] antReportsAntIndexAddrs;
    int[][] antReportsPhrAddrs;
    int[][] antReportsRsrpAddrs;
    int[][] antReportsTxPowerAddrs;
    int[][] antReportsValidAddrs;
    int[][] antStatusAddrs;
    int[][] bandAddrs;
    String bandStr;
    int[][] carrIndexAddrs;
    boolean[] ccIndexValid;
    String[] labelKeys = {"Tas Common", "Cell Info", "UL Info"};
    List<String[]> labelsList = new ArrayList<String[]>() {
        {
            add(new String[]{"TAS Enable", "Band", "RRC status"});
            add(new String[]{"Band", "RX State", "TX State", "TX0 Index", "TX1 Index"});
            add(new String[]{"ANT Index", "TX Pwr dBm", "PHR dB", "RSRP dBm"});
        }
    };
    int[] rrcStateAddrs;
    int[][] rxStateAddrs;
    int[][] statusAddrs;
    private String[] tabTitle;
    int[][] txIndex0Addrs;
    int[][] txIndex1Addrs;
    int[] txIndex1ValidAddrsV1;
    int[][] txStateAddrs;
    List<LinkedHashMap> valuesHashMap = new ArrayList<LinkedHashMap>() {
        {
            add(NRUTasInfo.this.initHashMap((Object[]) NRUTasInfo.this.labelsList.get(0)));
            add(NRUTasInfo.this.initHashMap((Object[]) NRUTasInfo.this.labelsList.get(1)));
            add(NRUTasInfo.this.initArrayHashMap((Object[]) NRUTasInfo.this.labelsList.get(2)));
        }
    };

    public NRUTasInfo(Activity context) {
        super(context);
        String[] strArr = {"CM", "CC0", "CC1", "CC2", "CC3", "CC4", "CC5", "CC6", "CC7"};
        this.tabTitle = strArr;
        this.statusAddrs = new int[][]{new int[]{8, 0, 2}, new int[]{8, 0, 1}, new int[]{8, 0, 1}};
        this.antStatusAddrs = new int[][]{new int[]{8, 0, 2}, new int[]{8, 24, 0, 2}, new int[]{8, 24, 0, 2}};
        this.carrIndexAddrs = new int[][]{new int[]{8, 56, 0, 3}, new int[]{8, 24, 2, 3}, new int[]{8, 24, 2, 3}};
        this.txIndex0Addrs = new int[][]{new int[]{8, 56, 3, 4}, new int[]{8, 24, 5, 5}, new int[]{8, 24, 8, 8}};
        this.txIndex1ValidAddrsV1 = new int[]{8, 56, 7, 1};
        this.txIndex1Addrs = new int[][]{new int[]{8, 56, 8, 4}, new int[]{8, 24, 10, 5}, new int[]{8, 24, 16, 8}};
        this.rxStateAddrs = new int[][]{new int[0], new int[]{8, 24, 15, 5}, new int[]{8, 24, 24, 8}};
        this.txStateAddrs = new int[][]{new int[0], new int[]{8, 24, 20, 5}, new int[]{8, 24, 32, 8}};
        this.antReportdLen = 64;
        this.antReportsValidAddrs = new int[][]{new int[]{8, 56, 32, 16, 0, 1}, new int[]{8, 24, 32, 16, 0, 1}, new int[]{8, 24, 64, 16, 0, 1}};
        this.antReportsAntIndexAddrs = new int[][]{new int[]{8, 56, 32, 25, 0, 7}, new int[]{8, 24, 32, 25, 0, 7}, new int[]{8, 24, 64, 25, 0, 7}};
        this.antReportsTxPowerAddrs = new int[][]{new int[]{8, 56, 32, 0, 0, 8}, new int[]{8, 24, 32, 0, 0, 8}, new int[]{8, 24, 64, 0, 0, 8}};
        this.antReportsPhrAddrs = new int[][]{new int[]{8, 56, 32, 8, 0, 8}, new int[]{8, 24, 32, 8, 0, 8}, new int[]{8, 24, 64, 8, 0, 8}};
        this.antReportsRsrpAddrs = new int[][]{new int[]{8, 56, 32, 32, 0, 16}, new int[]{8, 24, 32, 32, 0, 16}, new int[]{8, 24, 64, 32, 0, 16}};
        this.bandAddrs = new int[][]{new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{8, 49, 7}, new int[]{8, 49, 7}, new int[]{8, 49, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 7}, new int[]{8, 46, 8}, new int[]{8, 46, 8}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}, new int[]{8, 46, 9}};
        this.rrcStateAddrs = new int[]{8, 0, 8};
        this.bandStr = "";
        this.ccIndexValid = new boolean[]{false, false, false, false, false, false, false, false};
        initTableComponent(strArr);
        clearData();
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "NR UTAS Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "8. NR EM Component";
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        String[] antReportsRsrpS;
        int[] antReportsTxPower;
        int[] antReportsValid;
        ByteBuffer icdPacket;
        int txIndex1;
        int txIndex0;
        String str;
        int txIndex12;
        String str2 = name;
        ByteBuffer icdPacket2 = (ByteBuffer) msg;
        String str3 = "EmInfo/MDMComponent";
        if (str2.equals(MDMContentICD.MSG_ID_NRRC_STATE_CHANGE_EVENT)) {
            int version = getFieldValueIcdVersion(icdPacket2, this.rrcStateAddrs[0]);
            int rrcState = getFieldValueIcd(icdPacket2, this.rrcStateAddrs);
            Elog.d(str3, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version + ", values = " + rrcState);
            setHashMapKeyValues(this.labelKeys[0], 0, this.labelsList.get(0)[2], (Object) getValueByMapping(this.RRCStateMapping, rrcState));
            addData(this.labelKeys[0], 0);
            ByteBuffer byteBuffer = icdPacket2;
        } else if (str2.equals(MDMContentICD.MSG_ID_NL1_PHYSICAL_CONFIGURATION)) {
            int[][] iArr = this.bandAddrs;
            int version2 = getFieldValueIcdVersion(icdPacket2, iArr[iArr.length - 1][0]);
            if (version2 >= 6) {
                int band = getFieldValueIcd(icdPacket2, version2, this.bandAddrs);
                this.bandStr = band + "";
                Elog.d(str3, icdLogInfo, getName() + " update,name id = " + str2 + ", version = " + version2 + ", values = " + band);
                setHashMapKeyValues(this.labelKeys[0], 0, this.labelsList.get(0)[1], (Object) Integer.valueOf(band));
                addData(this.labelKeys[0], 0);
            } else {
                Elog.e(str3, getName() + " update,name id = " + str2 + ", version = " + version2 + ", this version is error, return");
            }
            ByteBuffer byteBuffer2 = icdPacket2;
        } else {
            int[][] iArr2 = this.statusAddrs;
            int version3 = getFieldValueIcdVersion(icdPacket2, iArr2[iArr2.length - 1][0]);
            int tasEnable = getFieldValueIcd(icdPacket2, version3, this.statusAddrs);
            int antStatus = getFieldValueIcd(icdPacket2, version3, this.antStatusAddrs);
            int ccIndex = getFieldValueIcd(icdPacket2, version3, this.carrIndexAddrs);
            this.ccIndexValid[ccIndex] = true;
            int txIndex02 = getFieldValueIcd(icdPacket2, version3, this.txIndex0Addrs);
            int txIndex13 = getFieldValueIcd(icdPacket2, version3, this.txIndex1Addrs);
            int rxState = getFieldValueIcd(icdPacket2, version3, this.rxStateAddrs);
            int txState = getFieldValueIcd(icdPacket2, version3, this.txStateAddrs);
            int rxState2 = rxState;
            int[] antReportsValid2 = new int[5];
            int tasEnable2 = tasEnable;
            int[] antReportsAntIndex = new int[5];
            String str4 = ", values = ";
            int[] antReportsTxPower2 = new int[5];
            String str5 = "";
            int[] antReportsPhr = new int[5];
            int txIndex14 = txIndex13;
            int[] antReportsRsrp = new int[5];
            int txIndex03 = txIndex02;
            String[] antReportsPhrS = new String[5];
            String[] antReportsRsrpS2 = new String[5];
            String[] antReportsAntIndexS = new String[5];
            String[] antReportsAntIndexS2 = new String[5];
            if (antStatus == 0) {
                String[] strArr = antReportsAntIndexS2;
                String[] antReportsTxPowerS = icdLogInfo;
                int[] iArr3 = antReportsRsrp;
                StringBuilder sb = new StringBuilder();
                int[] iArr4 = antReportsPhr;
                sb.append(getName());
                sb.append(" update,name id = ");
                sb.append(str2);
                sb.append(", version = ");
                sb.append(version3);
                sb.append(", condition = ");
                sb.append(antStatus);
                sb.append(", ");
                sb.append(ccIndex);
                Elog.d(str3, antReportsTxPowerS, sb.toString());
                return;
            }
            int[] antReportsRsrp2 = antReportsRsrp;
            int[] antReportsPhr2 = antReportsPhr;
            String[] antReportsTxPowerS2 = antReportsAntIndexS2;
            int j = 0;
            int i = 0;
            while (i < 5) {
                int[][] iArr5 = this.antReportsValidAddrs;
                String str6 = str3;
                int index = (version3 <= iArr5.length ? version3 : iArr5.length) - 1;
                iArr5[index][4] = i * 64;
                antReportsValid2[i] = getFieldValueIcd(icdPacket2, version3, iArr5);
                if (antReportsValid2[i] == 0) {
                    antReportsValid = antReportsValid2;
                    str = str5;
                    txIndex1 = txIndex14;
                    txIndex0 = txIndex03;
                    icdPacket = icdPacket2;
                } else {
                    int[][] iArr6 = this.antReportsAntIndexAddrs;
                    iArr6[index][4] = i * 64;
                    antReportsAntIndex[i] = getFieldValueIcd(icdPacket2, version3, iArr6);
                    int[][] iArr7 = this.antReportsTxPowerAddrs;
                    iArr7[index][4] = i * 64;
                    antReportsValid = antReportsValid2;
                    antReportsTxPower2[i] = getFieldValueIcd(icdPacket2, version3, iArr7, true);
                    int[][] iArr8 = this.antReportsPhrAddrs;
                    iArr8[index][4] = i * 64;
                    antReportsPhr2[i] = getFieldValueIcd(icdPacket2, version3, iArr8, true);
                    int[][] iArr9 = this.antReportsRsrpAddrs;
                    iArr9[index][4] = i * 64;
                    antReportsRsrp2[i] = getFieldValueIcd(icdPacket2, version3, iArr9, true);
                    txIndex0 = txIndex03;
                    if (antReportsAntIndex[i] != txIndex0) {
                        icdPacket = icdPacket2;
                        txIndex12 = txIndex14;
                        if (antReportsAntIndex[i] != txIndex12 || antStatus < 2) {
                            antReportsPhrS[j] = str5;
                            antReportsTxPowerS2[j] = str5;
                            int i2 = index;
                            str = str5;
                            txIndex1 = txIndex12;
                            antReportsAntIndexS[j] = antReportsAntIndex[i] + str;
                            antReportsRsrpS2[j] = str + antReportsRsrp2[i];
                            j++;
                        }
                    } else {
                        icdPacket = icdPacket2;
                        txIndex12 = txIndex14;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    int i3 = index;
                    str = str5;
                    sb2.append(str);
                    txIndex1 = txIndex12;
                    sb2.append(antReportsPhr2[i]);
                    antReportsPhrS[j] = sb2.toString();
                    antReportsTxPowerS2[j] = antReportsTxPower2[i] + str;
                    antReportsAntIndexS[j] = antReportsAntIndex[i] + str;
                    antReportsRsrpS2[j] = str + antReportsRsrp2[i];
                    j++;
                }
                i++;
                txIndex14 = txIndex1;
                icdPacket2 = icdPacket;
                antReportsValid2 = antReportsValid;
                str5 = str;
                txIndex03 = txIndex0;
                str3 = str6;
            }
            String str7 = str3;
            int[] iArr10 = antReportsValid2;
            Object obj = str5;
            int txIndex04 = txIndex03;
            ByteBuffer byteBuffer3 = icdPacket2;
            String[] strArr2 = icdLogInfo;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getName());
            sb3.append(" update,name id = ");
            sb3.append(str2);
            sb3.append(", version = ");
            sb3.append(version3);
            sb3.append(", conditions = ");
            sb3.append(antStatus);
            sb3.append(", ");
            sb3.append(ccIndex);
            sb3.append(",");
            sb3.append(Arrays.toString(this.antReportsValidAddrs));
            sb3.append(str4);
            int tasEnable3 = tasEnable2;
            sb3.append(tasEnable3);
            sb3.append(", ");
            int rxState3 = rxState2;
            sb3.append(rxState3);
            sb3.append(", ");
            int txState2 = txState;
            sb3.append(txState2);
            sb3.append(", ");
            sb3.append(txIndex04);
            sb3.append(", ");
            int txIndex15 = txIndex14;
            sb3.append(txIndex15);
            sb3.append(", ");
            int i4 = version3;
            sb3.append(Arrays.toString(antReportsAntIndex));
            sb3.append(", ");
            sb3.append(Arrays.toString(antReportsTxPower2));
            sb3.append(", ");
            sb3.append(Arrays.toString(antReportsPhrS));
            sb3.append(", ");
            sb3.append(Arrays.toString(antReportsRsrpS2));
            Elog.d(str7, strArr2, sb3.toString());
            setHashMapKeyValues(this.labelKeys[0], 0, this.labelsList.get(0)[0], (Object) getValueByMapping(this.TasEnableMapping, tasEnable3));
            addData(this.labelKeys[0], 0);
            setHashMapKeyValues(this.labelKeys[1], ccIndex + 1, this.labelsList.get(1)[0], (Object) this.bandStr);
            setHashMapKeyValues(this.labelKeys[1], ccIndex + 1, this.labelsList.get(1)[1], (Object) Integer.valueOf(rxState3));
            setHashMapKeyValues(this.labelKeys[1], ccIndex + 1, this.labelsList.get(1)[2], (Object) Integer.valueOf(txState2));
            setHashMapKeyValues(this.labelKeys[1], ccIndex + 1, this.labelsList.get(1)[3], (Object) Integer.valueOf(txIndex04));
            String str8 = this.labelKeys[1];
            int i5 = ccIndex + 1;
            String str9 = this.labelsList.get(1)[4];
            if (antStatus >= 2) {
                obj = Integer.valueOf(txIndex15);
            }
            setHashMapKeyValues(str8, i5, str9, obj);
            addData(this.labelKeys[1], ccIndex + 1);
            setHashMapKeyValues(this.labelKeys[2], ccIndex + 1, this.labelsList.get(2)[0], antReportsAntIndexS);
            setHashMapKeyValues(this.labelKeys[2], ccIndex + 1, this.labelsList.get(2)[1], antReportsTxPowerS2);
            int i6 = txIndex15;
            setHashMapKeyValues(this.labelKeys[2], ccIndex + 1, this.labelsList.get(2)[2], antReportsPhrS);
            String[] antReportsRsrpS3 = antReportsRsrpS2;
            setHashMapKeyValues(this.labelKeys[2], ccIndex + 1, this.labelsList.get(2)[3], antReportsRsrpS3);
            addData(this.labelKeys[2], ccIndex + 1);
            int i7 = 0;
            while (true) {
                boolean[] zArr = this.ccIndexValid;
                if (i7 < zArr.length) {
                    if (!zArr[i7]) {
                        antReportsRsrpS = antReportsRsrpS3;
                        antReportsTxPower = antReportsTxPower2;
                        setHashMapKeyArrays(this.labelKeys[1], i7 + 1, initArrayHashMap((Object[]) this.labelsList.get(1)));
                        setHashMapKeyArrays(this.labelKeys[2], i7 + 1, initArrayHashMap((Object[]) this.labelsList.get(2)));
                        addData(this.labelKeys[1], i7 + 1);
                        addData(this.labelKeys[2], i7 + 1);
                    } else {
                        antReportsRsrpS = antReportsRsrpS3;
                        antReportsTxPower = antReportsTxPower2;
                    }
                    i7++;
                    antReportsTxPower2 = antReportsTxPower;
                    antReportsRsrpS3 = antReportsRsrpS;
                } else {
                    int[] iArr11 = antReportsTxPower2;
                    return;
                }
            }
        }
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
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                hashMapkeyValues.put(this.labelKeys[1], this.valuesHashMap.get(1));
                hashMapkeyValues.put(this.labelKeys[2], this.valuesHashMap.get(2));
                break;
        }
        return hashMapkeyValues;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> getArrayTypeKey() {
        ArrayList<String> arrayTypeKeys = new ArrayList<>();
        arrayTypeKeys.add(this.labelKeys[2]);
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
