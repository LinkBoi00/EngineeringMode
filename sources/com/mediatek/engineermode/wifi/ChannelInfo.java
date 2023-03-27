package com.mediatek.engineermode.wifi;

import android.hardware.radio.V1_4.DataCallFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import android.widget.ArrayAdapter;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelInfo {
    static final int BW_160M = 8;
    static final int BW_20M = 1;
    static final int BW_40M = 2;
    static final int BW_80M = 4;
    static final int CHANNEL_2DOT4G = 10;
    static final int CHANNEL_5G = 11;
    public static final int CHANNEL_NUMBER_14 = 14;
    private static final int DEFAULT_CHANNEL_COUNT = 11;
    private static final int MAX_CHANNEL_COUNT = 75;
    private static final String TAG = "WifiChannelInfo";
    private static final int[] sBw40mUnsupported2dot4GChannels = {1, 2, 12, 13, 14};
    private static long[] sCachedSupportChannels = null;
    private static HashMap<Integer, int[]> sCachedSupportedChs = new HashMap<>();
    private static HashMap<Integer, ChannelData> sChannelDataDb = null;
    private static HashMap<Integer, ArrayList<ChannelData>> sChannelGroupMap = null;
    protected static long[] sChannels = null;
    protected static boolean sHas14Ch = false;
    protected static boolean sHasUpper14Ch = false;
    private static final ChannelData[] sWifiChannelDatas = {new ChannelData(1, "Channel 1 [2412MHz]", 2412000, 1), new ChannelData(2, "Channel 2 [2417MHz]", 2417000, 1), new ChannelData(3, "Channel 3 [2422MHz]", 2422000, 3), new ChannelData(4, "Channel 4 [2427MHz]", 2427000, 3), new ChannelData(5, "Channel 5 [2432MHz]", 2432000, 3), new ChannelData(6, "Channel 6 [2437MHz]", 2437000, 3), new ChannelData(7, "Channel 7 [2442MHz]", 2442000, 3), new ChannelData(8, "Channel 8 [2447MHz]", 2447000, 3), new ChannelData(9, "Channel 9 [2452MHz]", 2452000, 3), new ChannelData(10, "Channel 10 [2457MHz]", 2457000, 3), new ChannelData(11, "Channel 11 [2462MHz]", 2462000, 3), new ChannelData(12, "Channel 12 [2467MHz]", 2467000, 1), new ChannelData(13, "Channel 13 [2472MHz]", 2472000, 1), new ChannelData(14, "Channel 14 [2484MHz]", 2484000, 1), new ChannelData(18, "Channel 8 5G [5040MHz]", 5040000, 1), new ChannelData(20, "Channel 10 5G [5050MHz]", 5050000, 2), new ChannelData(22, "Channel 12 5G [5060MHz]", 5060000, 1), new ChannelData(26, "Channel 16 5G [5080MHz]", 5080000, 1), new ChannelData(36, "Channel 36 [5180MHz]", 5180000, 1), new ChannelData(38, "Channel 38 [5190MHz]", 5190000, 2), new ChannelData(40, "Channel 40 [5200MHz]", 5200000, 1), new ChannelData(42, "Channel 42 [5210MHz]", 5210000, 4), new ChannelData(44, "Channel 44 [5220MHz]", 5220000, 1), new ChannelData(46, "Channel 46 [5230MHz]", 5230000, 2), new ChannelData(48, "Channel 48 [5240MHz]", 5240000, 1), new ChannelData(50, "Channel 50 [5250MHz]", 5250000, 8), new ChannelData(52, "Channel 52 [5260MHz]", 5260000, 1), new ChannelData(54, "Channel 54 [5270MHz]", 5270000, 2), new ChannelData(56, "Channel 56 [5280MHz]", 5280000, 1), new ChannelData(58, "Channel 58 [5290MHz]", 5290000, 4), new ChannelData(60, "Channel 60 [5300MHz]", 5300000, 1), new ChannelData(62, "Channel 62 [5310MHz]", 5310000, 2), new ChannelData(64, "Channel 64 [5320MHz]", 5320000, 1), new ChannelData(68, "Channel 68 [5340MHz]", 5340000, 1), new ChannelData(70, "Channel 70 [5350MHz]", 5350000, 2), new ChannelData(72, "Channel 72 [5360MHz]", 5360000, 1), new ChannelData(74, "Channel 74 [5370MHz]", 5370000, 4), new ChannelData(76, "Channel 76 [5380MHz]", 5380000, 1), new ChannelData(78, "Channel 78 [5390MHz]", 5390000, 2), new ChannelData(80, "Channel 80 [5400MHz]", 5400000, 1), new ChannelData(82, "Channel 82 [5410MHz]", 5410000, 8), new ChannelData(84, "Channel 84 [5420MHz]", 5420000, 1), new ChannelData(86, "Channel 86 [5430MHz]", 5430000, 2), new ChannelData(88, "Channel 88 [5440MHz]", 5440000, 1), new ChannelData(90, "Channel 90 [5450MHz]", 5450000, 4), new ChannelData(92, "Channel 92 [5460MHz]", 5460000, 1), new ChannelData(94, "Channel 94 [5470MHz]", 5470000, 2), new ChannelData(96, "Channel 96 [5480MHz]", 5480000, 1), new ChannelData(100, "Channel 100 [5500MHz]", 5500000, 1), new ChannelData(102, "Channel 102 [5510MHz]", 5510000, 2), new ChannelData(104, "Channel 104 [5520MHz]", 5520000, 1), new ChannelData(106, "Channel 106 [5530MHz]", 5530000, 4), new ChannelData(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, "Channel 108 [5540MHz]", 5540000, 1), new ChannelData(110, "Channel 110 [5550MHz]", 5550000, 2), new ChannelData(112, "Channel 112 [5560MHz]", 5560000, 1), new ChannelData(114, "Channel 114 [5570MHz]", 5570000, 8), new ChannelData(116, "Channel 116 [5580MHz]", 5580000, 1), new ChannelData(118, "Channel 118 [5590MHz]", 5590000, 2), new ChannelData(120, "Channel 120 [5600MHz]", 5600000, 1), new ChannelData(122, "Channel 122 [5610MHz]", 5610000, 4), new ChannelData(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, "Channel 124 [5620MHz]", 5620000, 1), new ChannelData(126, "Channel 126 [5630MHz]", 5630000, 2), new ChannelData(128, "Channel 128 [5640MHz]", 5640000, 1), new ChannelData(132, "Channel 132 [5660MHz]", 5660000, 1), new ChannelData(134, "Channel 134 [5670MHz]", 5670000, 2), new ChannelData(136, "Channel 136 [5680MHz]", 5680000, 1), new ChannelData(Cea708CCParser.Const.CODE_C1_HDW, "Channel 138 [5690MHz]", 5690000, 4), new ChannelData(Cea708CCParser.Const.CODE_C1_DLW, "Channel 140 [5700MHz]", 5700000, 1), new ChannelData(Cea708CCParser.Const.CODE_C1_DLC, "Channel 142 [5710MHz]", 5710000, 2), new ChannelData(Cea708CCParser.Const.CODE_C1_SPA, "Channel 144 [5720MHz]", 5720000, 1), new ChannelData(149, "Channel 149 [5745MHz]", 5745000, 1), new ChannelData(Cea708CCParser.Const.CODE_C1_SWA, "Channel 151 [5755MHz]", 5755000, 2), new ChannelData(Cea708CCParser.Const.CODE_C1_DF1, "Channel 153 [5765MHz]", 5765000, 1), new ChannelData(Cea708CCParser.Const.CODE_C1_DF3, "Channel 155 [5775MHz]", 5775000, 4), new ChannelData(Cea708CCParser.Const.CODE_C1_DF5, "Channel 157 [5785MHz]", 5785000, 1), new ChannelData(159, "Channel 159 [5795MHz]", 5795000, 2), new ChannelData(161, "Channel 161 [5805MHz]", 5805000, 1), new ChannelData(163, "Channel 163 [5815MHz]", 5815000, 8), new ChannelData(165, "Channel 165 [5825MHz]", 5825000, 1), new ChannelData(167, "Channel 167 [5835MHz]", 5835000, 2), new ChannelData(169, "Channel 169 [5845MHz]", 5845000, 1), new ChannelData(171, "Channel 171 [5855MHz]", 5855000, 4), new ChannelData(173, "Channel 173 [5865MHz]", 5865000, 1), new ChannelData(175, "Channel 175 [5875MHz]", 5875000, 2), new ChannelData(177, "Channel 177 [5885MHz]", 5885000, 1), new ChannelData(181, "Channel 181 [5905MHz]", 5905000, 1), new ChannelData(184, "Channel 184 [4920MHz]", 4920000, 1), new ChannelData(186, "Channel 186 [4930MHz]", 4930000, 2), new ChannelData(188, "Channel 188 [4940MHz]", 4940000, 1), new ChannelData(192, "Channel 192 [4960MHz]", 4960000, 1), new ChannelData(194, "Channel 194 [4970MHz]", 4970000, 2), new ChannelData(196, "Channel 196 [4980MHz]", 4980000, 1)};
    private String mChannelSelect;

    private static class ChannelData {
        public int bandwidth;
        public int frequency;
        public int id;
        public String name;
        public int sequence;

        public ChannelData(int id2, String name2, int frequency2, int bandwidth2) {
            this.id = id2;
            this.name = name2;
            this.frequency = frequency2;
            this.bandwidth = bandwidth2;
        }
    }

    static {
        initChannelDataDatabase();
    }

    private static boolean initChannelDataDatabase() {
        if (sChannelDataDb == null) {
            sChannelDataDb = new HashMap<>();
            if (sChannelGroupMap == null) {
                sChannelGroupMap = new HashMap<>();
            }
            int i = 0;
            while (true) {
                ChannelData[] channelDataArr = sWifiChannelDatas;
                if (i >= channelDataArr.length) {
                    break;
                }
                ChannelData channel = channelDataArr[i];
                channel.sequence = i + 1;
                String name = channel.name;
                int frequency = channel.frequency;
                if (!name.contains(String.valueOf(frequency / 1000))) {
                    Elog.e(TAG, "UnMatch name & frequency at index:" + i + " name:" + name + " frequency:" + frequency);
                } else {
                    int id = channel.id;
                    if (extractChannelIdFromName(name) != id) {
                        Elog.e(TAG, "UnMatch id :" + id + " and name:" + name);
                    }
                    sChannelDataDb.put(Integer.valueOf(id), channel);
                    if (isInBandwidth(1, channel)) {
                        addToChannelGroup(1, channel);
                    }
                    if (isInBandwidth(2, channel)) {
                        addToChannelGroup(2, channel);
                    }
                    if (isInBandwidth(4, channel)) {
                        addToChannelGroup(4, channel);
                    }
                    if (isInBandwidth(8, channel)) {
                        addToChannelGroup(8, channel);
                    }
                }
                i++;
            }
        }
        return true;
    }

    static int[] getChannelGroupArray(int groupId) {
        ArrayList<ChannelData> groupList = getChannelGroup(groupId);
        if (groupList == null) {
            return null;
        }
        int length = groupList.size();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = groupList.get(i).id;
        }
        return array;
    }

    private static void addToChannelGroup(int groupId, ChannelData cd) {
        ArrayList<ChannelData> list = sChannelGroupMap.get(Integer.valueOf(groupId));
        if (list == null) {
            list = new ArrayList<>();
            sChannelGroupMap.put(Integer.valueOf(groupId), list);
        }
        list.add(cd);
    }

    private static ArrayList<ChannelData> getChannelGroup(int groupId) {
        return sChannelGroupMap.get(Integer.valueOf(groupId));
    }

    /* access modifiers changed from: package-private */
    public void removeBw40mUnsupported2dot4GChannels(ArrayAdapter<String> adapter) {
        int i = 0;
        while (true) {
            int[] iArr = sBw40mUnsupported2dot4GChannels;
            if (i < iArr.length) {
                String name = getChannelName(iArr[i]);
                if (name != null) {
                    adapter.remove(name);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getSelectedChannelId() {
        return extractChannelIdFromName(this.mChannelSelect);
    }

    /* access modifiers changed from: package-private */
    public int getSelectedFrequency() {
        return getChannelFrequency(getSelectedChannelId());
    }

    /* access modifiers changed from: package-private */
    public void setSelectedChannel(String channelName) {
        this.mChannelSelect = channelName;
    }

    /* access modifiers changed from: package-private */
    public void insertBw40mUnsupported2dot4GChannels(ArrayAdapter<String> adapter) {
        int i = 0;
        while (true) {
            int[] iArr = sBw40mUnsupported2dot4GChannels;
            if (i < iArr.length) {
                int channel = iArr[i];
                String name = getChannelName(channel);
                if (name != null && isSupported(channel)) {
                    insertChannelIntoAdapterByOrder(adapter, name);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetSupportedChannels(ArrayAdapter<String> adapter) {
        adapter.clear();
        addChannelsIntoAdapter(getCachedSupportChannels(), adapter, false);
    }

    private static long[] getCachedSupportChannels() {
        if (sCachedSupportChannels == null) {
            int len = (int) sChannels[0];
            sCachedSupportChannels = new long[len];
            for (int i = 0; i < len; i++) {
                sCachedSupportChannels[i] = sChannels[i + 1];
            }
        }
        return sCachedSupportChannels;
    }

    private boolean isSupported(int channelId) {
        ChannelData cd = sChannelDataDb.get(Integer.valueOf(channelId));
        if (cd == null) {
            return false;
        }
        if (isInBandwidth(1, cd) && isChannelSupported(channelId, 1)) {
            return true;
        }
        if (isInBandwidth(2, cd) && isChannelSupported(channelId, 2)) {
            return true;
        }
        if (!isInBandwidth(4, cd) || !isChannelSupported(channelId, 4)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void addChannelsIntoAdapter(long[] channels, ArrayAdapter<String> adapter, boolean byOrder, boolean checkSupported) {
        String name;
        if (channels != null) {
            for (long j : channels) {
                int id = (int) j;
                if ((!checkSupported || isSupported(id)) && (name = getChannelName(id)) != null) {
                    if (byOrder) {
                        insertChannelIntoAdapterByOrder(adapter, name);
                    } else {
                        adapter.add(name);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addChannelsIntoAdapter(long[] channels, ArrayAdapter<String> adapter, boolean byOrder) {
        addChannelsIntoAdapter(channels, adapter, byOrder, true);
    }

    /* access modifiers changed from: package-private */
    public void removeChannels(int[] channels, ArrayAdapter<String> adapter) {
        for (int ch : channels) {
            String name = getChannelName(ch);
            if (name != null) {
                adapter.remove(name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void remove2dot4GChannels(ArrayAdapter<String> adapter) {
        for (int i = 1; i <= 14; i++) {
            adapter.remove(getChannelName(i));
        }
    }

    /* access modifiers changed from: package-private */
    public void insertBw80MChannels(ArrayAdapter<String> adapter) {
        ArrayList<ChannelData> bw80mChannelList = getChannelGroup(4);
        if (bw80mChannelList == null) {
            Elog.e(TAG, "BW_80M channel group is null");
            return;
        }
        for (int i = 0; i < bw80mChannelList.size(); i++) {
            ChannelData cd = bw80mChannelList.get(i);
            if (isChannelSupported(cd.id, 4)) {
                insertChannelIntoAdapterByOrder(adapter, cd.name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void insertBw160MChannels(ArrayAdapter<String> adapter) {
        ArrayList<ChannelData> bw160mChannelList = getChannelGroup(8);
        if (bw160mChannelList == null) {
            Elog.e(TAG, "BW_160M channel group is null");
            return;
        }
        for (int i = 0; i < bw160mChannelList.size(); i++) {
            ChannelData cd = bw160mChannelList.get(i);
            if (isChannelSupported(cd.id, 8)) {
                insertChannelIntoAdapterByOrder(adapter, cd.name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeBw40MChannels(ArrayAdapter<String> adapter) {
        ArrayList<ChannelData> list = getChannelGroup(2);
        if (list == null) {
            Elog.e(TAG, "BW_40M channel group is null");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ChannelData cd = list.get(i);
            if (cd.id > 14) {
                adapter.remove(cd.name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void remove5GChannels(ArrayAdapter<String> adapter) {
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            String name = adapter.getItem(i);
            if (extractChannelIdFromName(name) > 14) {
                adapter.remove(name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void insert5GChannels(ArrayAdapter<String> adapter) {
        long[] channels = getCachedSupportChannels();
        for (int i = 0; i <= channels.length; i++) {
            int id = (int) channels[i];
            if (id > 14) {
                String tag = getChannelName(id);
                if (tag != null) {
                    insertChannelIntoAdapterByOrder(adapter, tag);
                } else {
                    Elog.d(TAG, "UNKnown channel:" + id);
                }
            }
        }
    }

    private int[] getSupported2dot4gChannels() {
        int[] cachedChs = sCachedSupportedChs.get(10);
        if (cachedChs != null) {
            return cachedChs;
        }
        long[] chsupported = getCachedSupportChannels();
        List<Long> list = new ArrayList<>();
        for (long id : chsupported) {
            if (id >= 1 && id <= 14) {
                list.add(Long.valueOf(id));
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        int[] target = new int[list.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = list.get(i).intValue();
        }
        sCachedSupportedChs.put(10, target);
        return target;
    }

    /* access modifiers changed from: package-private */
    public void addSupported2dot4gChannels(ArrayAdapter<String> adapter, boolean byOrder) {
        addChannelsIntoAdapter(ints2longs(getSupported2dot4gChannels()), adapter, byOrder);
    }

    /* access modifiers changed from: package-private */
    public void addSupported5gChannelsByBandwidth(ArrayAdapter<String> adapter, int bandwidth, boolean byOrder) {
        int[] channels = getSupported5gChannelsByBandwidth(bandwidth);
        if (channels != null) {
            addChannelsIntoAdapter(ints2longs(channels), adapter, byOrder, false);
        }
    }

    private int[] getSupported5gChannelsByBandwidth(int bandwidth) {
        List<Integer> list = new ArrayList<>();
        boolean isCached = true;
        if (bandwidth == 1) {
            int[] cachedChs = sCachedSupportedChs.get(1);
            if (cachedChs != null) {
                return cachedChs;
            }
            int[] chsupported = longs2ints(getCachedSupportChannels());
            if (chsupported != null) {
                for (int id : chsupported) {
                    if (isIn5gChannelBandwidth(id, bandwidth)) {
                        list.add(Integer.valueOf(id));
                    }
                }
            }
        } else if (bandwidth == 2) {
            int[] cachedChs2 = sCachedSupportedChs.get(2);
            if (cachedChs2 != null) {
                return cachedChs2;
            }
            ArrayList<ChannelData> groupList = getChannelGroup(2);
            if (groupList == null) {
                Elog.e(TAG, "getSupported5gChannelsByBandwidth BW_40M channel group is null");
                return null;
            }
            for (int i = 0; i < groupList.size(); i++) {
                int id2 = groupList.get(i).id;
                if (id2 > 14 && isChannelSupported(id2, 2)) {
                    list.add(Integer.valueOf(id2));
                }
            }
        } else if (bandwidth == 4) {
            int[] cachedChs3 = sCachedSupportedChs.get(4);
            if (cachedChs3 != null) {
                return cachedChs3;
            }
            ArrayList<ChannelData> groupList2 = getChannelGroup(4);
            if (groupList2 == null) {
                Elog.e(TAG, "getSupported5gChannelsByBandwidth BW_80M channel group is null");
                return null;
            }
            for (int i2 = 0; i2 < groupList2.size(); i2++) {
                int id3 = groupList2.get(i2).id;
                if (isChannelSupported(id3, 4)) {
                    list.add(Integer.valueOf(id3));
                }
            }
        } else {
            Elog.i(TAG, "getSupported5gChannelsByBandwidth invalid bandwidth:" + bandwidth);
            isCached = false;
        }
        if (list.size() <= 0) {
            return null;
        }
        int[] target = new int[list.size()];
        for (int i3 = 0; i3 < target.length; i3++) {
            target[i3] = list.get(i3).intValue();
        }
        if (isCached) {
            sCachedSupportedChs.put(Integer.valueOf(bandwidth), target);
        }
        return target;
    }

    private static int[] longs2ints(long[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int[] ints = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ints[i] = (int) array[i];
        }
        return ints;
    }

    private static long[] ints2longs(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        long[] longs = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            longs[i] = (long) array[i];
        }
        return longs;
    }

    /* access modifiers changed from: package-private */
    public void insertBw20MChannels(ArrayAdapter<String> adapter) {
        ArrayList<ChannelData> groupList = getChannelGroup(1);
        if (groupList == null) {
            Elog.e(TAG, "BW_20M channel group is null");
            return;
        }
        for (int i = 0; i < groupList.size(); i++) {
            ChannelData cd = groupList.get(i);
            if (isChannelSupported(cd.id, 1)) {
                insertChannelIntoAdapterByOrder(adapter, cd.name);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void insertBw40MChannels(ArrayAdapter<String> adapter) {
        ArrayList<ChannelData> groupList = getChannelGroup(2);
        if (groupList == null) {
            Elog.e(TAG, "BW_40M channel group is null");
            return;
        }
        for (int i = 0; i < groupList.size(); i++) {
            ChannelData cd = groupList.get(i);
            if (isChannelSupported(cd.id, 2)) {
                insertChannelIntoAdapterByOrder(adapter, cd.name);
            }
        }
    }

    private int computeInsertIndex(ArrayAdapter<String> adapter, int channel) {
        int targetIndex = -1;
        int i = 0;
        while (true) {
            if (i >= adapter.getCount()) {
                break;
            } else if (extractChannelIdFromName(adapter.getItem(i)) > channel) {
                targetIndex = i;
                break;
            } else {
                i++;
            }
        }
        if (targetIndex == -1) {
            return adapter.getCount();
        }
        return targetIndex;
    }

    static int parseChannelId(String fullName) {
        return extractChannelIdFromName(fullName);
    }

    private static int extractChannelIdFromName(String fullName) {
        String[] strs = fullName.split(" +");
        if (strs.length == 3) {
            try {
                return Integer.valueOf(strs[1]).intValue();
            } catch (NumberFormatException e) {
                Elog.e(TAG, "NumberFormatException:" + e.getMessage());
                return -1;
            }
        } else if (strs.length == 4) {
            try {
                return Integer.valueOf(strs[1]).intValue() + 10;
            } catch (NumberFormatException e2) {
                Elog.e(TAG, "NumberFormatException:" + e2.getMessage());
                return -1;
            }
        } else {
            Elog.w(TAG, "extractChannelIdFromName(): " + fullName + " invalid name format!");
            return -1;
        }
    }

    /* access modifiers changed from: package-private */
    public void insertChannelIntoAdapterByOrder(ArrayAdapter<String> adapter, String channelName) {
        if (adapter.getPosition(channelName) == -1) {
            adapter.insert(channelName, computeInsertIndex(adapter, extractChannelIdFromName(channelName)));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0009, code lost:
        r3 = sChannelDataDb.get(java.lang.Integer.valueOf(r6));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isIn5gChannelBandwidth(int r6, int r7) {
        /*
            r5 = this;
            r0 = 14
            r1 = 1
            r2 = 0
            if (r6 < r1) goto L_0x0009
            if (r6 > r0) goto L_0x0009
            return r2
        L_0x0009:
            java.util.HashMap<java.lang.Integer, com.mediatek.engineermode.wifi.ChannelInfo$ChannelData> r3 = sChannelDataDb
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)
            java.lang.Object r3 = r3.get(r4)
            com.mediatek.engineermode.wifi.ChannelInfo$ChannelData r3 = (com.mediatek.engineermode.wifi.ChannelInfo.ChannelData) r3
            if (r3 != 0) goto L_0x0018
            return r2
        L_0x0018:
            if (r6 <= r0) goto L_0x0021
            boolean r0 = isInBandwidth(r7, r3)
            if (r0 == 0) goto L_0x0021
            return r1
        L_0x0021:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.wifi.ChannelInfo.isIn5gChannelBandwidth(int, int):boolean");
    }

    private static boolean isInBandwidth(int bandwidth, ChannelData cd) {
        return (cd.bandwidth & bandwidth) > 0;
    }

    private boolean isChannelSupported(int channel, int bandwidth) {
        int[] testChannels;
        if (bandwidth == 1) {
            testChannels = new int[]{channel};
        } else if (bandwidth == 2) {
            testChannels = new int[]{channel - 2, channel + 2};
        } else if (bandwidth == 4) {
            testChannels = new int[]{channel - 6, channel - 2, channel + 2, channel + 6};
        } else if (bandwidth == 8) {
            testChannels = new int[]{channel - 14, channel - 10, channel - 6, channel - 2, channel + 2, channel + 6, channel + 10, channel + 14};
        } else {
            Elog.w(TAG, "Invalid bandwidth:" + bandwidth);
            return false;
        }
        for (int ch : testChannels) {
            if (!isContains(ch)) {
                return false;
            }
        }
        return true;
    }

    static String getChannelName(int channelId) {
        ChannelData cd = sChannelDataDb.get(Integer.valueOf(channelId));
        if (cd == null) {
            return null;
        }
        return cd.name;
    }

    static int getChannelFrequency(int channelId) {
        ChannelData cd = sChannelDataDb.get(Integer.valueOf(channelId));
        if (cd == null) {
            return 0;
        }
        return cd.frequency;
    }

    public boolean isContains(int channel) {
        int i = 1;
        while (true) {
            long[] jArr = sChannels;
            if (((long) i) > jArr[0]) {
                return false;
            }
            if (((long) channel) == jArr[i]) {
                return true;
            }
            i++;
        }
    }

    static void getSupportChannels() {
        if (sChannels != null) {
            return;
        }
        if (EMWifi.isInTestMode()) {
            long[] jArr = new long[75];
            sChannels = jArr;
            if (EMWifi.getSupportChannelList(jArr) == 0) {
                Elog.i(TAG, "LENGTH channels[0] = " + sChannels[0]);
                int i = 1;
                while (true) {
                    long[] jArr2 = sChannels;
                    if (((long) i) <= jArr2[0]) {
                        if (14 == jArr2[i]) {
                            sHas14Ch = true;
                        }
                        if (jArr2[i] > 14) {
                            sHasUpper14Ch = true;
                        }
                        Elog.i(TAG, "channels[" + i + "] = " + sChannels[i]);
                        i++;
                    } else {
                        return;
                    }
                }
            } else {
                sChannels[0] = 11;
                for (int i2 = 0; i2 < 11; i2++) {
                    sChannels[i2 + 1] = (long) (i2 + 1);
                }
            }
        } else {
            Elog.w(TAG, "Wifi is not initialed");
        }
    }

    public ChannelInfo() {
        this.mChannelSelect = null;
        this.mChannelSelect = sWifiChannelDatas[0].name;
    }
}
