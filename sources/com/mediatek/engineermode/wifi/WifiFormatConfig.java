package com.mediatek.engineermode.wifi;

import android.hardware.radio.V1_0.RadioCdmaSmsConst;
import android.hardware.radio.V1_0.SmsAcknowledgeFailCause;
import android.hardware.radio.V1_4.DataCallFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

class WifiFormatConfig {
    private static final String BW_160C = "BW160C";
    private static final String BW_160NC = "BW160NC";
    private static final String BW_20 = "BW20";
    private static final String BW_40 = "BW40";
    private static final String BW_80 = "BW80";
    private static final String FEC_BCC = "BCC";
    private static final int FEC_BCC_VALUE = 0;
    private static final String FEC_LDPC = "LDPC";
    private static final int FEC_LDPC_VALUE = 1;
    private static final String NORMAL_GI = "normal GI";
    private static final String RATE_11M = "11M";
    private static final String RATE_12M = "12M";
    private static final String RATE_18M = "18M";
    private static final String RATE_1M = "1M";
    private static final String RATE_24M = "24M";
    private static final String RATE_2M = "2M";
    private static final String RATE_36M = "36M";
    private static final String RATE_48M = "48M";
    private static final String RATE_54M = "54M";
    private static final String RATE_5dot5M = "5.5M";
    private static final String RATE_6M = "6M";
    private static final String RATE_9M = "9M";
    private static final String RATE_MCS0 = "MCS0";
    private static final String RATE_MCS0_106 = "MCS0-106";
    private static final String RATE_MCS0_106_DCM = "MCS0-106 DCM";
    private static final String RATE_MCS0_242 = "MCS0-242";
    private static final String RATE_MCS0_242_DCM = "MCS0-242 DCM";
    private static final String RATE_MCS0_DCM_16 = "MCS0 DCM (16)";
    private static final String RATE_MCS0_DCM_32 = "MCS0 DCM (32)";
    private static final String RATE_MCS1 = "MCS1";
    private static final String RATE_MCS10 = "MCS10";
    private static final String RATE_MCS11 = "MCS11";
    private static final String RATE_MCS12 = "MCS12";
    private static final String RATE_MCS13 = "MCS13";
    private static final String RATE_MCS14 = "MCS14";
    private static final String RATE_MCS15 = "MCS15";
    private static final String RATE_MCS1_242 = "MCS1-242";
    private static final String RATE_MCS1_242_DCM = "MCS1-242 DCM";
    private static final String RATE_MCS1_DCM_17 = "MCS1 DCM (17)";
    private static final String RATE_MCS1_DCM_33 = "MCS1 DCM (33)";
    private static final String RATE_MCS2 = "MCS2";
    private static final String RATE_MCS2_242 = "MSC2-242";
    private static final String RATE_MCS3 = "MCS3";
    private static final String RATE_MCS32 = "MCS32";
    private static final String RATE_MCS4 = "MCS4";
    private static final String RATE_MCS5 = "MCS5";
    private static final String RATE_MCS6 = "MCS6";
    private static final String RATE_MCS7 = "MCS7";
    private static final String RATE_MCS8 = "MCS8";
    private static final String RATE_MCS9 = "MCS9";
    private static final String SHORT_GI = "short GI";
    /* access modifiers changed from: private */
    public static LinkedHashMap<String, Integer> sMapBw = new LinkedHashMap<String, Integer>() {
        {
            put(WifiFormatConfig.BW_20, 0);
            put(WifiFormatConfig.BW_40, 1);
            put(WifiFormatConfig.BW_80, 2);
            put(WifiFormatConfig.BW_160C, 5);
            put(WifiFormatConfig.BW_160NC, 6);
        }
    };
    /* access modifiers changed from: private */
    public List<ChannelDataFormat> mChFor160M = new ArrayList();
    /* access modifiers changed from: private */
    public List<ChannelDataFormat> mChFor20M = new ArrayList();
    /* access modifiers changed from: private */
    public List<ChannelDataFormat> mChFor40M = new ArrayList();
    /* access modifiers changed from: private */
    public List<ChannelDataFormat> mChFor80M = new ArrayList();
    private HashMap<String, List<ChannelDataFormat>> mMapCbwToCh = new HashMap<String, List<ChannelDataFormat>>() {
        {
            put(WifiFormatConfig.BW_20, WifiFormatConfig.this.mChFor20M);
            put(WifiFormatConfig.BW_40, WifiFormatConfig.this.mChFor40M);
            put(WifiFormatConfig.BW_80, WifiFormatConfig.this.mChFor80M);
            put(WifiFormatConfig.BW_160C, WifiFormatConfig.this.mChFor160M);
            put(WifiFormatConfig.BW_160NC, WifiFormatConfig.this.mChFor160M);
        }
    };
    /* access modifiers changed from: private */
    public final LinkedHashMap<String, Integer> mMapRate = new LinkedHashMap<String, Integer>() {
        {
            put(WifiFormatConfig.RATE_1M, 0);
            put(WifiFormatConfig.RATE_2M, 1);
            put(WifiFormatConfig.RATE_5dot5M, 2);
            put(WifiFormatConfig.RATE_11M, 3);
            put(WifiFormatConfig.RATE_6M, 0);
            put(WifiFormatConfig.RATE_9M, 1);
            put(WifiFormatConfig.RATE_12M, 2);
            put(WifiFormatConfig.RATE_18M, 3);
            put(WifiFormatConfig.RATE_24M, 4);
            put(WifiFormatConfig.RATE_36M, 5);
            put(WifiFormatConfig.RATE_48M, 6);
            put(WifiFormatConfig.RATE_54M, 7);
            put(WifiFormatConfig.RATE_MCS0, 0);
            put(WifiFormatConfig.RATE_MCS1, 1);
            put(WifiFormatConfig.RATE_MCS2, 2);
            put(WifiFormatConfig.RATE_MCS3, 3);
            put(WifiFormatConfig.RATE_MCS4, 4);
            put(WifiFormatConfig.RATE_MCS5, 5);
            put(WifiFormatConfig.RATE_MCS6, 6);
            put(WifiFormatConfig.RATE_MCS7, 7);
            put(WifiFormatConfig.RATE_MCS8, 8);
            put(WifiFormatConfig.RATE_MCS9, 9);
            put(WifiFormatConfig.RATE_MCS10, 10);
            put(WifiFormatConfig.RATE_MCS11, 11);
            put(WifiFormatConfig.RATE_MCS12, 12);
            put(WifiFormatConfig.RATE_MCS13, 13);
            put(WifiFormatConfig.RATE_MCS14, 14);
            put(WifiFormatConfig.RATE_MCS15, 15);
            put(WifiFormatConfig.RATE_MCS32, 32);
            put(WifiFormatConfig.RATE_MCS0_DCM_16, 16);
            put(WifiFormatConfig.RATE_MCS1_DCM_17, 17);
            put(WifiFormatConfig.RATE_MCS0_DCM_32, 32);
            put(WifiFormatConfig.RATE_MCS1_DCM_33, 33);
            put(WifiFormatConfig.RATE_MCS0_242, 0);
            put(WifiFormatConfig.RATE_MCS1_242, 1);
            put(WifiFormatConfig.RATE_MCS2_242, 2);
            put(WifiFormatConfig.RATE_MCS0_106, 32);
            put(WifiFormatConfig.RATE_MCS0_242_DCM, 16);
            put(WifiFormatConfig.RATE_MCS1_242_DCM, 17);
            put(WifiFormatConfig.RATE_MCS0_106_DCM, 48);
        }
    };
    private HashMap<Integer, LinkedHashMap<RuConfig, Integer>> mMapRuConfig = initRuConfigList();

    WifiFormatConfig(boolean support6G) {
        generateCh(support6G);
    }

    static int getFecValue(String fecName) {
        return FEC_BCC.equals(fecName) ^ true ? 1 : 0;
    }

    private HashMap<Integer, LinkedHashMap<RuConfig, Integer>> initRuConfigList() {
        HashMap<Integer, LinkedHashMap<RuConfig, Integer>> mMap = new HashMap<>();
        mMap.put(0, new LinkedHashMap<RuConfig, Integer>() {
            {
                put(RuConfig.RU_CONFIG_26by9, 8);
                put(RuConfig.RU_CONFIG_52by4, 3);
                put(RuConfig.RU_CONFIG_106by2, 1);
                put(RuConfig.RU_CONFIG_242by1, 0);
            }
        });
        mMap.put(1, new LinkedHashMap<RuConfig, Integer>() {
            {
                put(RuConfig.RU_CONFIG_26by9, 17);
                put(RuConfig.RU_CONFIG_52by4, 7);
                put(RuConfig.RU_CONFIG_106by2, 3);
                put(RuConfig.RU_CONFIG_242by1, 1);
                put(RuConfig.RU_CONFIG_484by1, 0);
            }
        });
        mMap.put(2, new LinkedHashMap<RuConfig, Integer>() {
            {
                put(RuConfig.RU_CONFIG_26by9, 36);
                put(RuConfig.RU_CONFIG_52by4, 15);
                put(RuConfig.RU_CONFIG_106by2, 7);
                put(RuConfig.RU_CONFIG_242by1, 3);
                put(RuConfig.RU_CONFIG_484by1, 1);
                put(RuConfig.RU_CONFIG_996by1, 0);
            }
        });
        LinkedHashMap<RuConfig, Integer> data160 = new LinkedHashMap<RuConfig, Integer>() {
            {
                put(RuConfig.RU_CONFIG_26by9, 73);
                put(RuConfig.RU_CONFIG_52by4, 31);
                put(RuConfig.RU_CONFIG_106by2, 15);
                put(RuConfig.RU_CONFIG_242by1, 7);
                put(RuConfig.RU_CONFIG_484by1, 3);
                put(RuConfig.RU_CONFIG_996by1, 1);
            }
        };
        mMap.put(5, data160);
        mMap.put(6, data160);
        return mMap;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<RuConfig, Integer> getRuConfig(int index) {
        return this.mMapRuConfig.get(Integer.valueOf(index));
    }

    enum RuConfig {
        RU_CONFIG_26by9("1:26*9", 1, 0, 0),
        RU_CONFIG_52by4("6:52*4", 6, 17891328, 37),
        RU_CONFIG_106by2("12:106*2", 12, 17825792, 53),
        RU_CONFIG_242by1("13:242*1", 13, 285212672, 61),
        RU_CONFIG_484by1("14:484*1", 14, 285216768, 65),
        RU_CONFIG_996by1("15:996*1", 15, 285278208, 67);
        
        private int mAllocation;
        private int mIndex;
        private String mName;
        private int mOffset;

        private RuConfig(String name, int index, int allocation, int offset) {
            this.mName = name;
            this.mIndex = index;
            this.mAllocation = allocation;
            this.mOffset = offset;
        }

        /* access modifiers changed from: package-private */
        public int getAllocation() {
            return this.mAllocation;
        }

        /* access modifiers changed from: package-private */
        public int getIndex() {
            return this.mIndex;
        }

        /* access modifiers changed from: package-private */
        public int getOffset() {
            return this.mOffset;
        }

        public String toString() {
            return this.mName;
        }
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, Integer> getMapBw() {
        return sMapBw;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, Integer> getMapRate() {
        return this.mMapRate;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Preamble> getPreambles(boolean hasCapLdpc, boolean isTx, boolean is11AxSupport, boolean cap160c, boolean cap160nc) {
        ArrayList<Preamble> list = new ArrayList<>();
        list.add(new CckPreamble());
        list.add(new OfdmPreamble());
        list.add(new HtMmPreamble(hasCapLdpc));
        list.add(new HtGfPreamble(hasCapLdpc));
        list.add(new VhtPreamble(this, hasCapLdpc, is11AxSupport, cap160c, cap160nc));
        if (is11AxSupport) {
            list.add(new HeSuPreamble(this, cap160c, cap160nc));
            list.add(new HeTbPreamble(cap160c, cap160nc));
            if (!isTx) {
                list.add(new HeMuPreamble(cap160c, cap160nc));
            }
            list.add(new HeErPreamble(this));
        }
        return list;
    }

    protected static List<String> genCbwListForCap(boolean cap160c, boolean cap160nc) {
        List<String> cbwList = new ArrayList<>();
        cbwList.add(BW_20);
        cbwList.add(BW_40);
        cbwList.add(BW_80);
        if (cap160c) {
            cbwList.add(BW_160C);
        }
        if (cap160nc) {
            cbwList.add(BW_160NC);
        }
        return cbwList;
    }

    abstract class Preamble {
        private static final int NSS_1 = 1;
        private List<String> mCbwList;
        private List<String> mFecList = null;
        private int mIndex;
        private String mName;
        private boolean mSupportNss2;

        /* access modifiers changed from: protected */
        public abstract List<String> getGiList(int i);

        /* access modifiers changed from: protected */
        public abstract List<String> getRateList(int i, int i2);

        protected Preamble(boolean capLdpc, String name, int index, boolean supportNss2, List<String> cbwList) {
            this.mName = name;
            this.mIndex = index;
            this.mSupportNss2 = supportNss2;
            this.mCbwList = cbwList;
            ArrayList arrayList = new ArrayList();
            this.mFecList = arrayList;
            if (capLdpc) {
                arrayList.add(WifiFormatConfig.FEC_LDPC);
            }
            this.mFecList.add(WifiFormatConfig.FEC_BCC);
        }

        /* access modifiers changed from: protected */
        public boolean isBw20(int cbwValue) {
            return ((Integer) WifiFormatConfig.sMapBw.get(WifiFormatConfig.BW_20)).intValue() == cbwValue;
        }

        /* access modifiers changed from: protected */
        public boolean isNss1(int nssValue) {
            return nssValue == 1;
        }

        /* access modifiers changed from: protected */
        public boolean isFecBcc(int fecValue) {
            return fecValue == 0;
        }

        /* access modifiers changed from: protected */
        public boolean isSupportNss2() {
            return this.mSupportNss2;
        }

        /* access modifiers changed from: protected */
        public List<String> getBwList() {
            return this.mCbwList;
        }

        /* access modifiers changed from: protected */
        public List<String> getFecList(int cbwValue) {
            return this.mFecList;
        }

        /* access modifiers changed from: protected */
        public int getIndex() {
            return this.mIndex;
        }

        /* access modifiers changed from: protected */
        public boolean supportRu() {
            return false;
        }

        public String toString() {
            return this.mName;
        }
    }

    abstract class SimplePreamble extends Preamble {
        private final List<String> mGiList = Arrays.asList(new String[]{WifiFormatConfig.NORMAL_GI});
        private List<String> mRateList;

        protected SimplePreamble(String name, int index, List<String> rateList) {
            super(false, name, index, false, Arrays.asList(new String[]{WifiFormatConfig.BW_20}));
            this.mRateList = rateList;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return this.mGiList;
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return this.mRateList;
        }
    }

    class CckPreamble extends SimplePreamble {
        protected CckPreamble() {
            super("CCK", 0, Arrays.asList(new String[]{WifiFormatConfig.RATE_1M, WifiFormatConfig.RATE_2M, WifiFormatConfig.RATE_5dot5M, WifiFormatConfig.RATE_11M}));
        }
    }

    class OfdmPreamble extends SimplePreamble {
        protected OfdmPreamble() {
            super("OFDM", 1, Arrays.asList(new String[]{WifiFormatConfig.RATE_6M, WifiFormatConfig.RATE_9M, WifiFormatConfig.RATE_12M, WifiFormatConfig.RATE_18M, WifiFormatConfig.RATE_24M, WifiFormatConfig.RATE_36M, WifiFormatConfig.RATE_48M, WifiFormatConfig.RATE_54M}));
        }
    }

    abstract class HtPreamble extends Preamble {
        private final List<String> mGiList = Arrays.asList(new String[]{WifiFormatConfig.NORMAL_GI, WifiFormatConfig.SHORT_GI});
        private final List<String> mRateList1 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7});
        private final List<String> mRateList2 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11, WifiFormatConfig.RATE_MCS12, WifiFormatConfig.RATE_MCS13, WifiFormatConfig.RATE_MCS14, WifiFormatConfig.RATE_MCS15});

        protected HtPreamble(boolean capLdpc, String name, int index) {
            super(capLdpc, name, index, true, Arrays.asList(new String[]{WifiFormatConfig.BW_20, WifiFormatConfig.BW_40}));
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return nssValue == 1 ? this.mRateList1 : this.mRateList2;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return this.mGiList;
        }
    }

    class HtMmPreamble extends HtPreamble {
        protected HtMmPreamble(boolean capLdpc) {
            super(capLdpc, "HT_MM", 2);
        }
    }

    class HtGfPreamble extends HtPreamble {
        protected HtGfPreamble(boolean capLdpc) {
            super(capLdpc, "HT_GF", 3);
        }
    }

    class VhtPreamble extends Preamble {
        private final List<String> mGiList = Arrays.asList(new String[]{WifiFormatConfig.NORMAL_GI, WifiFormatConfig.SHORT_GI});
        private List<String> mRateList;
        final /* synthetic */ WifiFormatConfig this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        protected VhtPreamble(WifiFormatConfig this$02, boolean capLdpc, boolean cap11ax, boolean cap160c, boolean cap160nc) {
            super(capLdpc, "VHT", 4, true, WifiFormatConfig.genCbwListForCap(cap160c, cap160nc));
            this.this$0 = this$02;
            if (cap11ax) {
                this.mRateList = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11});
            } else {
                this.mRateList = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9});
            }
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return this.mRateList;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return this.mGiList;
        }
    }

    class HeSuPreamble extends Preamble {
        private final List<String> mFecListLdpc = Arrays.asList(new String[]{WifiFormatConfig.FEC_LDPC});
        private final List<String> mGiList1 = Arrays.asList(new String[]{"1xLTF+0.8us GI", "2xLTF+0.8us GI", "2xLTF+1.6us GI", "4xLTF+3.2us GI", "4xLTF+0.8us GI"});
        private final List<String> mGiList2 = Arrays.asList(new String[]{"1xLTF+0.8us GI", "2xLTF+0.8us GI", "2xLTF+1.6us GI", "4xLTF+3.2us GI"});
        private final List<String> mRateList1 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9});
        private final List<String> mRateList2 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS0_DCM_16, WifiFormatConfig.RATE_MCS1_DCM_17});
        private final List<String> mRateList3 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11});
        private final List<String> mRateList4 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11, WifiFormatConfig.RATE_MCS0_DCM_16, WifiFormatConfig.RATE_MCS1_DCM_17});
        final /* synthetic */ WifiFormatConfig this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        protected HeSuPreamble(WifiFormatConfig this$02, boolean cap160c, boolean cap160nc) {
            super(true, "HE_SU", 8, true, WifiFormatConfig.genCbwListForCap(cap160c, cap160nc));
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return isNss1(nssValue) ? isFecBcc(fecValue) ? this.mRateList2 : this.mRateList4 : isFecBcc(fecValue) ? this.mRateList1 : this.mRateList3;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return rateIndex >= ((Integer) this.this$0.mMapRate.get(WifiFormatConfig.RATE_MCS0_DCM_16)).intValue() ? this.mGiList2 : this.mGiList1;
        }

        /* access modifiers changed from: protected */
        public List<String> getFecList(int cbwValue) {
            return isBw20(cbwValue) ? super.getFecList(cbwValue) : this.mFecListLdpc;
        }
    }

    abstract class HeBasePreamble extends Preamble {
        private final List<String> mFecListLdpc = Arrays.asList(new String[]{WifiFormatConfig.FEC_LDPC});
        private List<String> mGiList;
        private final List<String> mRateList1 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9});
        private final List<String> mRateList2 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS0_DCM_32, WifiFormatConfig.RATE_MCS1_DCM_33});
        private final List<String> mRateList3 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11});
        private final List<String> mRateList4 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0, WifiFormatConfig.RATE_MCS1, WifiFormatConfig.RATE_MCS2, WifiFormatConfig.RATE_MCS3, WifiFormatConfig.RATE_MCS4, WifiFormatConfig.RATE_MCS5, WifiFormatConfig.RATE_MCS6, WifiFormatConfig.RATE_MCS7, WifiFormatConfig.RATE_MCS8, WifiFormatConfig.RATE_MCS9, WifiFormatConfig.RATE_MCS10, WifiFormatConfig.RATE_MCS11, WifiFormatConfig.RATE_MCS0_DCM_32, WifiFormatConfig.RATE_MCS1_DCM_33});
        final /* synthetic */ WifiFormatConfig this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        protected HeBasePreamble(WifiFormatConfig this$02, String name, int index, boolean cap160c, boolean cap160nc, List<String> giList) {
            super(true, name, index, true, WifiFormatConfig.genCbwListForCap(cap160c, cap160nc));
            this.this$0 = this$02;
            this.mGiList = giList;
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return isNss1(nssValue) ? isFecBcc(fecValue) ? this.mRateList2 : this.mRateList4 : isFecBcc(fecValue) ? this.mRateList1 : this.mRateList3;
        }

        /* access modifiers changed from: protected */
        public List<String> getFecList(int cbwValue) {
            return isBw20(cbwValue) ? super.getFecList(cbwValue) : this.mFecListLdpc;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return this.mGiList;
        }
    }

    class HeTbPreamble extends HeBasePreamble {
        protected HeTbPreamble(boolean cap160c, boolean cap160nc) {
            super(WifiFormatConfig.this, "HE_TB", 10, cap160c, cap160nc, Arrays.asList(new String[]{"1x HE-LTF + 1.6us GI", "2x HE-LTF + 1.6us GI", "4x HE-LTF + 3.2us GI"}));
        }

        /* access modifiers changed from: protected */
        public boolean supportRu() {
            return true;
        }
    }

    class HeMuPreamble extends HeBasePreamble {
        protected HeMuPreamble(boolean cap160c, boolean cap160nc) {
            super(WifiFormatConfig.this, "HE_MU", 11, cap160c, cap160nc, Arrays.asList(new String[]{"1xLTF+0.8us GI", "2xLTF+0.8us GI", "2xLTF+1.6us GI", "4xLTF+3.2us GI"}));
        }
    }

    class HeErPreamble extends Preamble {
        private final List<String> mGiList = Arrays.asList(new String[]{"1xLTF+0.8us GI", "2xLTF+0.8us GI", "2xLTF+1.6us GI", "4xLTF+3.2us GI", "4xLTF+0.8us GI"});
        private final List<String> mRateList1 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0_242, WifiFormatConfig.RATE_MCS1_242, WifiFormatConfig.RATE_MCS2_242, WifiFormatConfig.RATE_MCS0_106, WifiFormatConfig.RATE_MCS0_242_DCM, WifiFormatConfig.RATE_MCS1_242_DCM, WifiFormatConfig.RATE_MCS0_106_DCM});
        private final List<String> mRateList2 = Arrays.asList(new String[]{WifiFormatConfig.RATE_MCS0_242, WifiFormatConfig.RATE_MCS1_242, WifiFormatConfig.RATE_MCS2_242, WifiFormatConfig.RATE_MCS0_106});
        final /* synthetic */ WifiFormatConfig this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        protected HeErPreamble(WifiFormatConfig this$02) {
            super(true, "HE_ER", 9, true, Arrays.asList(new String[]{WifiFormatConfig.BW_20}));
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public List<String> getRateList(int nssValue, int fecValue) {
            return isNss1(nssValue) ? this.mRateList1 : this.mRateList2;
        }

        /* access modifiers changed from: protected */
        public List<String> getGiList(int rateIndex) {
            return this.mGiList;
        }
    }

    private void generateChFor20M(boolean support6G) {
        this.mChFor20M.add(new ChannelDataFormat(1, "1 [2412MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(2, "2 [2417MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(3, "3 [2422MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(4, "4 [2427MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(5, "5 [2432MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(6, "6 [2437MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(7, "7 [2442MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(8, "8 [2447MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(9, "9 [2452MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(10, "10 [2457MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(11, "11 [2462MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(12, "12 [2467MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(13, "13 [2472MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        this.mChFor20M.add(new ChannelDataFormat(14, "14 [2484MHz]", ChBandType.CH_BAND_TYPE_2_DOT_4G));
        if (!support6G) {
            this.mChFor20M.add(new ChannelDataFormat(184, "184 [4920MHz]", ChBandType.CH_BAND_TYPE_5G));
            this.mChFor20M.add(new ChannelDataFormat(188, "188 [4940MHz]", ChBandType.CH_BAND_TYPE_5G));
            this.mChFor20M.add(new ChannelDataFormat(192, "192 [4960MHz]", ChBandType.CH_BAND_TYPE_5G));
            this.mChFor20M.add(new ChannelDataFormat(196, "196 [4980MHz]", ChBandType.CH_BAND_TYPE_5G));
        }
        this.mChFor20M.add(new ChannelDataFormat(8, "8 [5040MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(12, "12 [5060MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(16, "16 [5080MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(36, "36 [5180MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(40, "40 [5200MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(44, "44 [5220MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(48, "48 [5240MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(52, "52 [5260MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(56, "56 [5280MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(60, "60 [5300MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(64, "64 [5320MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(68, "68 [5340MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(72, "72 [5360MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(76, "76 [5380MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(80, "80 [5400MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(84, "84 [5420MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(88, "88 [5440MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(92, "92 [5460MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(96, "96 [5480MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(100, "100 [5500MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(104, "104 [5520MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, "108 [5540MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(112, "112 [5560MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(116, "116 [5580MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(120, "120 [5600MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, "124 [5620MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(128, "128 [5640MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(132, "132 [5660MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(136, "136 [5680MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DLW, "140 [5700MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_SPA, "144 [5720MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(149, "149 [5745MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF1, "153 [5765MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF5, "157 [5785MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(161, "161 [5805MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(165, "165 [5825MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(169, "169 [5845MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(173, "173 [5865MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(177, "177 [5885MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor20M.add(new ChannelDataFormat(181, "181 [5905MHz]", ChBandType.CH_BAND_TYPE_5G));
        if (support6G) {
            this.mChFor20M.add(new ChannelDataFormat(1, "1 [5955MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(5, "5 [5975MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(9, "9 [5995MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(13, "13 [6015MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(17, "17 [6035MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(21, "21 [6055MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(25, "25 [6075MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(29, "29 [6095MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(33, "33 [6115MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(37, "37 [6135MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(41, "41 [6155MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(45, "45 [6175MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(49, "49 [6195MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(53, "53 [6215MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(57, "57 [6235MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(61, "61 [6255MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(65, "65 [6275MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(69, "69 [6295MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(73, "73 [6315MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(77, "77 [6335MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(81, "81 [6355MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(85, "85 [6375MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(89, "89 [6395MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(93, "93 [6415MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(97, "97 [6435MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(101, "101 [6455MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(105, "105 [6475MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, "109 [6495MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(113, "113 [6515MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(117, "117 [6535MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(121, "121 [6555MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(125, "125 [6575MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(129, "129 [6595MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_CW5, "133 [6615MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DSW, "137 [6635MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DLY, "141 [6655MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_SPC, "145 [6675MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(149, "149 [6695MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF1, "153 [6715MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF5, "157 [6735MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(161, "161 [6755MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(165, "165 [6775MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(169, "169 [6795MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(173, "173 [6815MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(177, "177 [6835MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(181, "181 [6855MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(185, "185 [6875MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(189, "189 [6895MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(193, "193 [6915MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(197, "197 [6935MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_REQ, "201 [6955MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(205, "205 [6975MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(209, "209 [6995MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(213, "213 [7015MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(217, "217 [7035MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(221, "221 [7055MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(NfcCommand.RspResult.NFC_STATUS_NO_SIM, "225 [7075MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(RadioCdmaSmsConst.USER_DATA_MAX, "229 [7095MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor20M.add(new ChannelDataFormat(233, "233 [7115MHz]", ChBandType.CH_BAND_TYPE_6G));
        }
    }

    private void generateChFor40M(boolean support6G) {
        if (!support6G) {
            this.mChFor40M.add(new ChannelDataFormat(186, "186 [4930MHz]", ChBandType.CH_BAND_TYPE_5G));
            this.mChFor40M.add(new ChannelDataFormat(194, "194 [4970MHz]", ChBandType.CH_BAND_TYPE_5G));
        }
        this.mChFor40M.add(new ChannelDataFormat(10, "10 [5050MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(38, "38 [5190MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(46, "46 [5230MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(54, "54 [5270MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(62, "62 [5310MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(70, "70 [5350MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(78, "78 [5390MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(86, "86 [5430MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(94, "94 [5470MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(102, "102 [5510MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(110, "110 [5550MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(118, "118 [5590MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(126, "126 [5630MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(134, "134 [5670MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DLC, "142 [5710MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_SWA, "151 [5755MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(159, "159 [5795MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(167, "167 [5835MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor40M.add(new ChannelDataFormat(175, "175 [5875MHz]", ChBandType.CH_BAND_TYPE_5G));
        if (support6G) {
            this.mChFor40M.add(new ChannelDataFormat(3, "3 [5965MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(11, "11 [6005MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(19, "19 [6045MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(27, "27 [6085MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(35, "35 [6125MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(43, "43 [6165MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(51, "51 [6205MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(59, "59 [6245MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(67, "67 [6285MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(75, "75 [6325MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(83, "83 [6365MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(91, "91 [6405MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(99, "99 [6445MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, "107 [6485MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(115, "115 [6525MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(DataCallFailCause.INVALID_DNS_ADDR, "123 [6565MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(131, "131 [6605MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_TGW, "139 [6645MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(147, "147 [6685MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF3, "155 [6725MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(163, "163 [6765MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(171, "171 [6805MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(179, "179 [6845MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(187, "187 [6885MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(195, "195 [6925MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_RSP, "203 [6965MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(SmsAcknowledgeFailCause.MEMORY_CAPACITY_EXCEEDED, "211 [7005MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(219, "219 [7045MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor40M.add(new ChannelDataFormat(NfcCommand.RspResult.NFC_STATUS_REMOVE_SE, "227 [7085MHz]", ChBandType.CH_BAND_TYPE_6G));
        }
    }

    private void generateChFor80M(boolean support6G) {
        this.mChFor80M.add(new ChannelDataFormat(42, "42 [5210MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(58, "58 [5290MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(74, "74 [5370MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(90, "90 [5450MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(106, "106 [5530MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(122, "122 [5610MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_HDW, "138 [5690MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_DF3, "155 [5775MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor80M.add(new ChannelDataFormat(171, "171 [5855MHz]", ChBandType.CH_BAND_TYPE_5G));
        if (support6G) {
            this.mChFor80M.add(new ChannelDataFormat(7, "7 [5985MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(23, "23 [6065MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(39, "39 [6145MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(55, "55 [6225MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(71, "71 [6305MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(87, "87 [6385MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(103, "103 [6465MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(119, "119 [6545MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(135, "135 [6625MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(Cea708CCParser.Const.CODE_C1_SWA, "151 [6705MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(167, "167 [6785MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(183, "183 [6865MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(199, "199 [6945MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor80M.add(new ChannelDataFormat(215, "215 [7025MHz]", ChBandType.CH_BAND_TYPE_6G));
        }
    }

    private void generateChFor160M(boolean support6G) {
        this.mChFor160M.add(new ChannelDataFormat(50, "50 [5250MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor160M.add(new ChannelDataFormat(82, "82 [5410MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor160M.add(new ChannelDataFormat(114, "114 [5570MHz]", ChBandType.CH_BAND_TYPE_5G));
        this.mChFor160M.add(new ChannelDataFormat(163, "163 [5815MHz]", ChBandType.CH_BAND_TYPE_5G));
        if (support6G) {
            this.mChFor160M.add(new ChannelDataFormat(15, "15 [6025MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(47, "47 [6185MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(79, "79 [6345MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(111, "111 [6505MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(143, "143 [6665MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(175, "175 [6825MHz]", ChBandType.CH_BAND_TYPE_6G));
            this.mChFor160M.add(new ChannelDataFormat(207, "207 [6985MHz]", ChBandType.CH_BAND_TYPE_6G));
        }
    }

    private void generateCh(boolean support6G) {
        generateChFor20M(support6G);
        generateChFor40M(support6G);
        generateChFor80M(support6G);
        generateChFor160M(support6G);
    }

    /* access modifiers changed from: package-private */
    public List<ChannelDataFormat> getChannel(String bw) {
        return this.mMapCbwToCh.get(bw);
    }
}
