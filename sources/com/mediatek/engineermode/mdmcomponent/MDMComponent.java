package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.os.Environment;
import android.view.View;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.mdml.Msg;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class MDMComponent implements Comparable<MDMComponent> {
    protected static final String TAG = "EmInfo/MDMComponent";
    protected static String[] emLogInfo = new String[6];
    protected static String[] icdLogInfo = new String[6];
    private static List<MDMComponent> mComponents = null;
    protected Activity mActivity;

    /* access modifiers changed from: package-private */
    public abstract void clearData();

    /* access modifiers changed from: package-private */
    public abstract String[] getEmComponentName();

    /* access modifiers changed from: package-private */
    public abstract String getGroup();

    /* access modifiers changed from: package-private */
    public abstract String getName();

    /* access modifiers changed from: package-private */
    public abstract View getView();

    /* access modifiers changed from: package-private */
    public abstract void removeView();

    /* access modifiers changed from: package-private */
    public abstract boolean supportMultiSIM();

    /* access modifiers changed from: package-private */
    public abstract void update(String str, Object obj);

    public MDMComponent(Activity context) {
        this.mActivity = context;
    }

    static List<MDMComponent> getComponents(Activity context) {
        List<MDMComponent> list = mComponents;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        mComponents = arrayList;
        arrayList.add(new RRCState(context));
        mComponents.add(new RRState(context));
        mComponents.add(new ERRCState(context));
        mComponents.add(new HighPriorityPLMNSearch(context));
        mComponents.add(new CarrierRSSIServing(context));
        mComponents.add(new FTNetworkInfo(context, 2));
        mComponents.add(new EUtranNeighborCellInfo(context));
        mComponents.add(new TddServingCellInfo(context));
        mComponents.add(new LaiRaiUmtsTdd(context));
        mComponents.add(new GeranNeighborCellInfoUmtsTdd(context));
        mComponents.add(new EUtranNeighborCellInfoUmtsTdd(context));
        mComponents.add(new LteServingCellInfo(context));
        mComponents.add(new C0000LteSCellInfo(context));
        mComponents.add(new PrimaryCellDlBlockErrorRate(context));
        mComponents.add(new PrimaryCellDownlinkBandwidth(context));
        mComponents.add(new PrimaryCellUplinkBandwidth(context));
        mComponents.add(new PrimaryCellUplinkDownlinkConfiguration(context));
        mComponents.add(new PrimaryCellSpecialSubframeConfiguration(context));
        mComponents.add(new PrimaryCellTransmissionMode(context));
        mComponents.add(new SecondaryCellDlBlockErrorRate(context));
        mComponents.add(new SecondaryCellDownlinkBandwidth(context));
        mComponents.add(new SecondaryCellUplinkBandwidth(context));
        mComponents.add(new SecondaryCellUplinkDownlinkConfiguration(context));
        mComponents.add(new SecondaryCellSpecialSubframeConfiguration(context));
        mComponents.add(new SecondaryCellTransmissionMode(context));
        mComponents.add(new Tai(context));
        mComponents.add(new EmIrReport(context));
        mComponents.add(new UmtsRrcMeasurementControlForE3a(context));
        mComponents.add(new UmtsRrcMeasurementControlForE3b(context));
        mComponents.add(new UmtsRrcMeasurementControlForE3c(context));
        mComponents.add(new UmtsRrcMeasurementControlForE3d(context));
        mComponents.add(new UmtsRrcMeasurementReportForE3a(context));
        mComponents.add(new UmtsRrcMeasurementReportForE3b(context));
        mComponents.add(new UmtsRrcMeasurementReportForE3c(context));
        mComponents.add(new UmtsRrcMeasurementReportForE3d(context));
        mComponents.add(new UmtsNeighborCellInfoGsmTdd(context));
        mComponents.add(new LTENeighborCellInfoGSM(context));
        mComponents.add(new LteErrcMeasurementConfigForEventB1(context));
        mComponents.add(new LteErrcMeasurementConfigForEventB2(context));
        mComponents.add(new LteErrcMeasurementReportForEventB1(context));
        mComponents.add(new LteErrcMeasurementReportForEventB2(context));
        mComponents.add(new EDchTtiConfigured(context));
        mComponents.add(new HsdpaConfiguredUmtsFdd(context));
        mComponents.add(new DcHsdpaConfiguredUmtsFdd(context));
        mComponents.add(new HsDschModulationPrimaryCell(context));
        mComponents.add(new HsupaConfiguredUmtsFdd(context));
        mComponents.add(new WcdmaHsupaCapability(context));
        mComponents.add(new WcdmaHsdpaCapability(context));
        mComponents.add(new Fdd3gSpeechCodec(context));
        mComponents.add(new PlmnSearchStatusUmtsFdd(context));
        mComponents.add(new CellSupportPsInfo(context));
        mComponents.add(new DtxConfiguredUmtsFdd(context));
        mComponents.add(new DrxConfiguredUmtsFdd(context));
        mComponents.add(new FastDormancyConfiguration(context));
        mComponents.add(new IntraFrequencyMonitoredSetUmtsFdd(context));
        mComponents.add(new IntraFrequencyDetectedSetUmtsFdd(context));
        mComponents.add(new ActiveSetUmtsFdd(context));
        mComponents.add(new CsOverHspaUmtsFdd(context));
        mComponents.add(new ShareNetworkPlmnInfo(context));
        mComponents.add(new ServingCellInformationUmtsFdd(context));
        mComponents.add(new PrimaryCellCqi(context));
        mComponents.add(new PrimaryCellDlImcs(context));
        mComponents.add(new PrimaryCellDlResourceBlock(context));
        mComponents.add(new PrimaryCellAntennaPort(context));
        mComponents.add(new PrimaryCellDlThroughput(context));
        mComponents.add(new PrimaryCellDlBlockRate(context));
        mComponents.add(new SecondaryCellCqi(context));
        mComponents.add(new SecondaryCellDlImcs(context));
        mComponents.add(new SecondaryCellDlResourceBlock(context));
        mComponents.add(new SecondaryCellAntennaPort(context));
        mComponents.add(new SecondaryCellDlThroughput(context));
        mComponents.add(new SecondaryCellDlBlockRate(context));
        mComponents.add(new DownlinkDataThroughput(context));
        mComponents.add(new UplinkDataThroughput(context));
        mComponents.add(new HandoverIntraLte(context));
        mComponents.add(new MDFeatureDetection(context));
        mComponents.add(new RatAndServiceStatus(context));
        mComponents.add(new HsDschServingCellUmtsFdd(context));
        mComponents.add(new SecondHsDschServingCell(context));
        mComponents.add(new BasicInfoServingGsm(context));
        mComponents.add(new RsrpLteCandidateCellUmtsFdd(context));
        mComponents.add(new RsrqLteCandidateCellUmtsFdd(context));
        mComponents.add(new UmtsRscp(context));
        mComponents.add(new UmtsEcn0(context));
        mComponents.add(new IntraFrequencyNeighbouringCellInformationLteTdd(context));
        mComponents.add(new InterFrequencyNeighbouringCellInformationLteTdd(context));
        mComponents.add(new GeranNeighbouringCellInformation(context));
        mComponents.add(new UtraTddNeighbouringCellInformation(context));
        mComponents.add(new WcdmaTasInfo(context));
        mComponents.add(new WcdmaUTasInfo(context));
        mComponents.add(new GSMTasInfo(context));
        mComponents.add(new GSMUTasInfo(context));
        mComponents.add(new TddTasInfo(context));
        mComponents.add(new TddUTasInfo(context));
        mComponents.add(new PrimaryCellRsrpRx(context));
        mComponents.add(new PrimaryCellRsrp(context));
        mComponents.add(new PrimaryCellRsrq(context));
        mComponents.add(new PrimaryCellRssiRx(context));
        mComponents.add(new PrimaryCellSnrRx(context));
        mComponents.add(new PrimaryCellOsSnr(context));
        mComponents.add(new SecondaryCellRsrpRx(context));
        mComponents.add(new SecondaryCellRsrp(context));
        mComponents.add(new SecondaryCellRsrq(context));
        mComponents.add(new SecondaryCellRssiRx(context));
        mComponents.add(new SecondaryCellSnrRx(context));
        mComponents.add(new SecondaryCellOsSnr(context));
        mComponents.add(new EnhancedRRCState(context));
        mComponents.add(new LteUTasInfo(context));
        mComponents.add(new LteTasInfo(context));
        mComponents.add(new CellStrength(context));
        mComponents.add(new PCellSCellBasicInfo(context));
        mComponents.add(new ErrcFeatureDetection(context));
        mComponents.add(new El2FeatureDetection(context));
        mComponents.add(new LteErlcDlDrbConfiguration(context));
        mComponents.add(new LteErlcUlDrbConfiguration(context));
        mComponents.add(new SecondaryCell(context));
        mComponents.add(new EMMCallInformation(context));
        mComponents.add(new EmacInfo(context));
        mComponents.add(new EPSBearerThroughput(context));
        mComponents.add(new GSMRxdInfo(context));
        mComponents.add(new LteEmacRachFailure(context));
        mComponents.add(new LteErrcRlfEvent(context));
        mComponents.add(new LteErrcOosEvent(context));
        if (FeatureSupport.is93ModemAndAbove()) {
            mComponents.add(new Cdma1xrttRadioInfo(context));
            mComponents.add(new Cdma1xrttRadioUTasInfo(context));
            mComponents.add(new EvdoServingInfo(context));
            mComponents.add(new Cdma1xrttInfo(context));
            mComponents.add(new Cdma1xSchInfo(context));
            mComponents.add(new Cdma1xStatisticsInfo(context));
            mComponents.add(new Cdma1xSeringNeihbrInfo(context));
            mComponents.add(new EvdoFlInfo(context));
            mComponents.add(new EvdoRlInfo(context));
            mComponents.add(new EvdoStatueInfo(context));
            mComponents.add(new EvdoSprintXRttInfo(context));
            mComponents.add(new EvdoSprintInfo(context));
            mComponents.add(new EvdoActiveSet(context));
            mComponents.add(new EvdoCandSet(context));
            mComponents.add(new EvdoNghdrSet(context));
        }
        mComponents.add(new SpeechCodecInfo(context));
        mComponents.add(new RFCalibrationStatusCheck(context));
        mComponents.add(new LastRegisteredNetwork(context));
        mComponents.add(new TMSIandPTMSI(context));
        mComponents.add(new PeriodicLocationUpdateValue(context));
        mComponents.add(new RejectCauseCode(context));
        mComponents.add(new ActivePDPContextInformationUmtsFDD(context));
        mComponents.add(new ActivePDPContextInformationLTE(context));
        mComponents.add(new EUTRAMeasurementReport(context));
        mComponents.add(new LTEPCellDLModulationTB1(context));
        mComponents.add(new LTEPCellDLModulationTB2(context));
        mComponents.add(new LTEPCellULModulation(context));
        mComponents.add(new LTEPCellMIMOLayers(context));
        mComponents.add(new LTEPDCPThroughput(context));
        mComponents.add(new LTERLCThroughput(context));
        mComponents.add(new LTEMACThroughput(context));
        mComponents.add(new LTEPCellPHYThroughput(context));
        mComponents.add(new LTESCellPHYThroughput(context));
        mComponents.add(new LTESCellInfo(context));
        mComponents.add(new LTENeighborCellInfo(context));
        mComponents.add(new LTESignalView(context));
        mComponents.add(new NRSessionInfo(context));
        mComponents.add(new NRPServingCellInfo1(context));
        mComponents.add(new NRPServingCellInfo2(context));
        mComponents.add(new NRPServingCellRsrpRx(context));
        mComponents.add(new NRPServingCellRsrqRx(context));
        mComponents.add(new NRPServingCellRssiRx(context));
        mComponents.add(new NRPServingCellSinrRx(context));
        mComponents.add(new NRPNeighborCellInfo(context));
        mComponents.add(new NRPCellResourceBlock(context));
        mComponents.add(new NRPCellTransMode(context));
        mComponents.add(new NRPCellTxPower(context));
        mComponents.add(new NRPCellChannelStatePMI1(context));
        mComponents.add(new NRPCellChannelStatePMI2(context));
        mComponents.add(new NRPCellChannelStateCqi1(context));
        mComponents.add(new NRPCellChannelStateCqi2(context));
        mComponents.add(new NRPCellChannelStateRi(context));
        mComponents.add(new NRPCellDLModulationTB1(context));
        mComponents.add(new NRPCellDLModulationTB2(context));
        mComponents.add(new NRPCellULModulation(context));
        mComponents.add(new NRPCellULMIMOLayers(context));
        mComponents.add(new NRPCellBLER(context));
        mComponents.add(new NRPCellSDAPThroughput(context));
        mComponents.add(new NRPCellPDCPThroughput(context));
        mComponents.add(new NRPCellRLCThroughput(context));
        mComponents.add(new NRPCellMACThroughput(context));
        mComponents.add(new NRPCellPHYThroughput(context));
        mComponents.add(new NRCdrxInfo(context));
        mComponents.add(new NRPCellTxOpMode(context));
        mComponents.add(new NSA(context));
        mComponents.add(new SA(context));
        mComponents.add(new SRSTxStatus(context));
        mComponents.add(new NRUTasInfo(context));
        mComponents.add(new NRNetCon(context));
        mComponents.add(new NrTddPattern(context));
        mComponents.add(new NRCAActive(context));
        mComponents.add(new NRSCellInfo(context));
        mComponents.add(new ONasInfo(context));
        mComponents.add(new oRfcInfo(context));
        mComponents.add(new DRBType(context));
        mComponents.add(new NRPServingCellCSIRSReport(context));
        mComponents.add(new DRDSDS(context));
        mComponents.add(new RRCProcDuration(context));
        mComponents.add(new MmWaveRFChipStatusCheck(context));
        return mComponents;
    }

    public int compareTo(MDMComponent another) {
        return getName().compareTo(another.getName());
    }

    /* access modifiers changed from: package-private */
    public boolean hasEmType(String type) {
        String[] types = getEmComponentName();
        if (types != null) {
            for (String upperCase : types) {
                if (upperCase.toUpperCase().equals(type.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }
        Elog.d(TAG, "type is null " + type);
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getTypeIndex(String type) {
        String[] types = getEmComponentName();
        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                if (types[i].equals(type)) {
                    return i;
                }
            }
            return -1;
        }
        Elog.d(TAG, "type is null " + type);
        return -1;
    }

    public String getValueByMapping(HashMap<Integer, String> mappings, int index) {
        if (mappings.containsKey(Integer.valueOf(index))) {
            return mappings.get(Integer.valueOf(index));
        }
        if (mappings.containsKey(-1)) {
            return mappings.get(-1);
        }
        return "-(" + index + ")";
    }

    public int getFieldValue(Msg data, String msgName) {
        byte[] bData = data.getFieldValue(msgName);
        if (bData == null) {
            Elog.d(TAG, "[getFieldValue] returned null from :" + msgName);
        }
        return (int) Utils.getIntFromByte(bData);
    }

    public int getFieldValue(Msg data, String msgName, boolean sign) {
        if (!sign) {
            return getFieldValue(data, msgName);
        }
        byte[] bData = data.getFieldValue(msgName);
        if (bData == null) {
            Elog.d(TAG, "[getFieldValue] returned null from :" + msgName);
        }
        return (int) Utils.getIntFromByte(bData, true);
    }

    public int getOffsetByAddrs(int[] addrs) {
        int sum = 0;
        for (int i : addrs) {
            sum += i;
        }
        return sum;
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int offset, int length, boolean sign) {
        int value = getFieldValueIcd(icdPacket, offset, length);
        if (sign && (icdPacket.get((((offset + length) - 1) / 8) + 21) & 128) != 0) {
            return ~value;
        }
        return value;
    }

    public int getFieldValueIcdVersion(ByteBuffer icdPacket, int len) {
        return (int) getValueFromIcdSource(icdPacket, new int[]{getFieldHeaderLength(icdPacket), len});
    }

    public int getFieldValueIcdType(ByteBuffer icdPacket) {
        return icdPacket.get(0) & 15;
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int[] offsetAddrs) {
        if (offsetAddrs.length == 0) {
            Elog.e(TAG, "[getFieldValueIcd] offsetAddrs length is 0, version is " + getFieldValueIcdVersion(icdPacket, 8) + ", return");
            return 0;
        }
        int endIndex = offsetAddrs.length - 1;
        return getFieldValueIcd(icdPacket, getOffsetByAddrs(Arrays.copyOfRange(offsetAddrs, 0, endIndex)), offsetAddrs[endIndex]);
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int version, int[][] offsetAddrs) {
        if (version <= 0 || offsetAddrs == null || offsetAddrs.length == 0) {
            Elog.e(TAG, "getFieldValueIcd: version: " + version + ", offsetAddrs, need check!");
            return 0;
        }
        if (version > offsetAddrs.length) {
            version = offsetAddrs.length;
            Elog.d(TAG, "offsetAddrs < version, use latest record" + version + ", " + offsetAddrs.length);
        }
        return getFieldValueIcd(icdPacket, offsetAddrs[version - 1]);
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int version, int[][] offsetAddrs, boolean isSigned) {
        if (version <= 0 || offsetAddrs == null || offsetAddrs.length == 0) {
            Elog.e(TAG, "getFieldValueIcd: version: " + version + ", offsetAddrs, need check!");
            return 0;
        }
        if (version > offsetAddrs.length) {
            version = offsetAddrs.length;
            Elog.d(TAG, "offsetAddrs < version, use latest record" + version + ", " + offsetAddrs.length);
        }
        return getFieldValueIcd(icdPacket, offsetAddrs[version - 1], isSigned);
    }

    public long getLongFieldValueIcd(ByteBuffer icdPacket, int version, int[][] offsetAddrs, boolean isSigned) {
        if (version <= 0 || offsetAddrs == null || offsetAddrs.length == 0) {
            Elog.e(TAG, "getFieldValueIcd: version: " + version + ", offsetAddrs, need check!");
            return 0;
        }
        if (version > offsetAddrs.length) {
            version = offsetAddrs.length;
            Elog.d(TAG, "offsetAddrs < version, use latest record" + version + ", " + offsetAddrs.length);
        }
        int endIndex = offsetAddrs[version - 1].length - 1;
        int offset = getOffsetByAddrs(Arrays.copyOfRange(offsetAddrs[version - 1], 0, endIndex));
        int length = offsetAddrs[version - 1][endIndex];
        int headLen = getFieldHeaderLength(icdPacket);
        if (isSigned) {
            return getSignedValueFromIcdSource(icdPacket, new int[]{offset + headLen, length});
        }
        return getValueFromIcdSource(icdPacket, new int[]{offset + headLen, length});
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int[] offsetAddrs, boolean isSigned) {
        if (offsetAddrs.length == 0) {
            Elog.e(TAG, "[getFieldValueIcd] offsetAddrs length is 0, version is " + getFieldValueIcdVersion(icdPacket, 8) + ", return");
            return 0;
        }
        int endIndex = offsetAddrs.length - 1;
        int offset = getOffsetByAddrs(Arrays.copyOfRange(offsetAddrs, 0, endIndex));
        int length = offsetAddrs[endIndex];
        if (isSigned) {
            return getFieldSignedValueIcd(icdPacket, offset, length);
        }
        return getFieldValueIcd(icdPacket, offset, length);
    }

    public int getFieldHeaderLength(ByteBuffer icdPacket) {
        int version = (icdPacket.get(0) & 240) >> 4;
        int type = icdPacket.get(0) & 15;
        int len = 0;
        int timestamp_type = 0;
        HashMap<Integer, Integer> TimeStampSize = new HashMap<Integer, Integer>() {
            {
                put(0, 0);
                put(1, 8);
                put(2, 4);
                put(8, 8);
                put(9, 4);
            }
        };
        switch (type) {
            case 0:
                switch (version) {
                    case 1:
                        len = MDMContentICD.ICD_EVENT_V1.timeStamp[0];
                        timestamp_type = (int) getValueFromIcdSource(icdPacket, MDMContentICD.ICD_EVENT_V1.timestamp_type);
                        break;
                    case 2:
                        len = MDMContentICD.ICD_EVENT_V2.timeStamp[0];
                        timestamp_type = (int) getValueFromIcdSource(icdPacket, MDMContentICD.ICD_EVENT_V2.timestamp_type);
                        break;
                }
            case 1:
                switch (version) {
                    case 1:
                        len = MDMContentICD.ICD_RECORD_V1.timeStamp[0];
                        timestamp_type = (int) getValueFromIcdSource(icdPacket, MDMContentICD.ICD_RECORD_V1.timestamp_type);
                        break;
                    case 2:
                        len = MDMContentICD.ICD_RECORD_V2.timeStamp[0];
                        timestamp_type = (int) getValueFromIcdSource(icdPacket, MDMContentICD.ICD_RECORD_V2.timestamp_type);
                        break;
                }
            default:
                Elog.e(TAG, "getFieldHeaderLength error, type is : " + type);
                break;
        }
        return (TimeStampSize.get(Integer.valueOf(timestamp_type)).intValue() * 8) + len;
    }

    public long getValueFromIcdSource(ByteBuffer icdPacket, int[] despArr) {
        int length;
        ByteBuffer byteBuffer = icdPacket;
        int offset = despArr[0];
        int length2 = despArr[1];
        int version = (byteBuffer.get(0) & 240) >> 4;
        int type = byteBuffer.get(0) & 15;
        long value = 0;
        int maskBit = offset % 8;
        int beginByte = offset / 8;
        int endBit = (offset + length2) % 8;
        int endByte = (length2 + offset) / 8;
        if (endByte > icdPacket.limit()) {
            length = length2;
        } else if (beginByte > icdPacket.limit()) {
            length = length2;
        } else {
            if (maskBit == 0) {
            } else if (length2 <= 8 - maskBit) {
                int i = length2;
                return (((long) ((1 << length2) - 1)) & (((long) (byteBuffer.get(beginByte) & 255)) >> maskBit)) | 0;
            } else {
                value = 0 | (((long) (byteBuffer.get(beginByte) & 255)) >> maskBit);
                maskBit = 8 - maskBit;
                beginByte++;
            }
            if (endByte > beginByte) {
                for (int i2 = beginByte; i2 < endByte; i2++) {
                    value |= ((long) (byteBuffer.get(i2) & 255)) << (((i2 - beginByte) * 8) + maskBit);
                }
            }
            if (endBit == 0 || endByte >= icdPacket.limit()) {
                return value;
            }
            return value | ((((long) ((1 << endBit) - 1)) & ((long) (byteBuffer.get(endByte) & 255))) << (((endByte - beginByte) * 8) + maskBit));
        }
        Elog.d(TAG, "getFieldValueIcd version: " + version + ", type: " + type + ", offset: " + offset + ", length: " + length + ", limit: " + icdPacket.limit() + ", beginByte: " + beginByte + ", maskBit: " + maskBit + ", endByte: " + endByte + ", endBit: " + endBit);
        Elog.e(TAG, "data transfer error! return.....");
        return 0;
    }

    public long getSignedValueFromIcdSource(ByteBuffer icdPacket, int[] despArr) {
        int length;
        ByteBuffer byteBuffer = icdPacket;
        int offset = despArr[0];
        int length2 = despArr[1];
        int version = (byteBuffer.get(0) & 240) >> 4;
        int type = byteBuffer.get(0) & 15;
        long value = 0;
        int maskBit = offset % 8;
        int beginByte = offset / 8;
        int endBit = (offset + length2) % 8;
        int endByte = (length2 + offset) / 8;
        if (endByte > icdPacket.limit()) {
            length = length2;
        } else if (beginByte > icdPacket.limit()) {
            length = length2;
        } else {
            if (maskBit == 0) {
            } else if (length2 <= 8 - maskBit) {
                int i = length2;
                return (((long) ((1 << length2) - 1)) & (((long) byteBuffer.get(beginByte)) >> maskBit)) | 0;
            } else {
                value = 0 | (((long) byteBuffer.get(beginByte)) >> maskBit);
                maskBit = 8 - maskBit;
                beginByte++;
            }
            if (endByte > beginByte) {
                for (int i2 = beginByte; i2 < endByte; i2++) {
                    value |= ((long) byteBuffer.get(i2)) << (((i2 - beginByte) * 8) + maskBit);
                }
            }
            if (endBit == 0 || endByte >= icdPacket.limit()) {
                return value;
            }
            return value | (((long) (byteBuffer.get(endByte) & ((1 << endBit) - 1))) << (((endByte - beginByte) * 8) + maskBit));
        }
        Elog.d(TAG, "getFieldSignedValueIcd version: " + version + ", type: " + type + ", offset: " + offset + ", length: " + length + ", limit: " + icdPacket.limit() + ", beginByte: " + beginByte + ", maskBit: " + maskBit + ", endByte: " + endByte + ", endBit: " + endBit);
        Elog.e(TAG, "data transfer error! return.....");
        return 0;
    }

    public int getFieldSignedValueIcd(ByteBuffer icdPacket, int offset, int length) {
        return (int) getSignedValueFromIcdSource(icdPacket, new int[]{offset + getFieldHeaderLength(icdPacket), length});
    }

    public int getFieldValueIcd(ByteBuffer icdPacket, int offset, int length) {
        return (int) getValueFromIcdSource(icdPacket, new int[]{offset + getFieldHeaderLength(icdPacket), length});
    }

    /* access modifiers changed from: package-private */
    public String[] addLablesAtPosition(String[] a, String[] b, int position) {
        String[] c = new String[(a.length + b.length)];
        if (position >= a.length) {
            System.arraycopy(a, 0, c, 0, a.length);
            System.arraycopy(b, 0, c, a.length, b.length);
        } else {
            for (int i = 0; i < a.length; i++) {
                if (i < position) {
                    c[i] = a[i];
                } else if (i == position) {
                    for (int j = 0; j < b.length; j++) {
                        c[i + j] = b[j];
                    }
                    c[b.length + i] = a[i];
                } else {
                    c[b.length + i] = a[i];
                }
            }
        }
        return c;
    }

    /* access modifiers changed from: package-private */
    public String[] removeLablesAtPosition(String[] a, int position, int num) {
        if (position >= a.length) {
            return a;
        }
        String[] c = new String[(a.length - num)];
        for (int i = 0; i < a.length - num; i++) {
            if (i < position) {
                c[i] = a[i];
            } else if (i != position) {
                c[i] = a[i + num];
            } else if (a.length - position <= num) {
                break;
            } else {
                c[i] = a[i + num];
            }
        }
        return c;
    }

    public <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array2 : rest) {
            System.arraycopy(array2, 0, result, offset, array2.length);
            offset += array2.length;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public int[] insertElement(int[] original, int element, int index) {
        int length = original.length;
        if (index > length || (-index) > length) {
            return original;
        }
        if (index < 0 && (-index) < length) {
            index += length;
        }
        int[] destination = new int[(length + 1)];
        System.arraycopy(original, 0, destination, 0, index);
        destination[index] = element;
        System.arraycopy(original, index, destination, index + 1, length - index);
        return destination;
    }

    /* access modifiers changed from: protected */
    public int[][] insertElement(int[][] original, int version, int[] elementArrays, int index) {
        if (version <= 0 || original == null || original.length == 0) {
            Elog.e(TAG, "getFieldValueIcd: version: " + version + ", offsetAddrs, need check!");
            return original;
        }
        int element = elementArrays[(version > elementArrays.length ? elementArrays.length : version) - 1];
        int[] curOrigin = original[(version > original.length ? original.length : version) - 1];
        int length = curOrigin.length;
        int[] destination = new int[(length + 1)];
        if (index == 0 || index > length || (-index) > length) {
            return original;
        }
        if (index < 0 && (-index) < length) {
            index += length;
        }
        System.arraycopy(curOrigin, 0, destination, 0, index);
        destination[index] = element;
        System.arraycopy(curOrigin, index, destination, index + 1, length - index);
        original[(version > original.length ? original.length : version) - 1] = destination;
        return original;
    }

    /* access modifiers changed from: protected */
    public int[] updateElement(int[] original, int element, int index) {
        if (index > original.length || (-index) > original.length) {
            return original;
        }
        if (index < 0 && (-index) < original.length) {
            index += original.length;
        }
        original[index] = element;
        return original;
    }

    public void saveToSDCard(String dirName, String fileName, String content, boolean append) throws IOException {
        String path = Environment.getExternalStorageDirectory().getPath() + dirName;
        File filedDir = new File(path);
        if (!filedDir.exists()) {
            filedDir.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(new File(path, fileName), append);
        fos.write(content.getBytes());
        fos.write("\r\n".getBytes());
        fos.close();
    }

    public String getCurrectTime() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS").format(new Date());
    }

    public boolean diff_time(String lastTime, String CurTime, int diff_s) {
        Date dlast = null;
        Date dcurrent = null;
        SimpleDateFormat mCurrectTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS");
        try {
            dlast = mCurrectTime.parse(lastTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dcurrent = mCurrectTime.parse(CurTime);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (dcurrent.getTime() - dlast.getTime() > ((long) diff_s)) {
            return true;
        }
        return false;
    }

    public void testData() {
    }
}
