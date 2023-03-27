package com.mediatek.mdml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class CommonMsgInfo {
    static final String peerBufHeaderSizeName = "MSG_PEER_HEADER_SIZE";
    private int currentMsgPeerBufferHeaderSize;
    private boolean doesCurrentMsgHavePeerBufferHeader;
    private HashMap<Integer, BaseMsg> idMsgMap = new HashMap<>();
    OffsetAndSize msgIdOffsetAndSize = new OffsetAndSize();
    private String msgIdOffsetName;
    private HashMap<String, Integer> nameIdMap = new HashMap<>();
    private int parserLastID_internal;
    private String parserLastNameID_internal;
    private String parserLastName_internal;
    private final List<String> parserSplitList_1separator_internal = new ArrayList();
    private final List<String> parserSplitList_2separator_internal = new ArrayList();
    private String peerBufSizeName;
    OffsetAndSize peerBufSizeOffsetAndSize = new OffsetAndSize();
    OffsetAndSize simIdxOffsetAndSize = new OffsetAndSize();
    private String simIdxOffsetName;

    /* renamed from: com.mediatek.mdml.CommonMsgInfo$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$mdml$InfoType;

        static {
            int[] iArr = new int[InfoType.values().length];
            $SwitchMap$com$mediatek$mdml$InfoType = iArr;
            try {
                iArr[InfoType.OTA_INFO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$InfoType[InfoType.MSG_INFO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    CommonMsgInfo(InfoType t) {
        switch (AnonymousClass1.$SwitchMap$com$mediatek$mdml$InfoType[t.ordinal()]) {
            case 1:
                this.msgIdOffsetName = "OTA_TRACE_ID_OFFSET";
                this.simIdxOffsetName = "OTA_SIM_IDX_OFFSET";
                this.peerBufSizeName = null;
                break;
            case 2:
                this.msgIdOffsetName = "MSG_ID_OFFSET";
                this.simIdxOffsetName = "SIM_IDX_OFFSET";
                this.peerBufSizeName = "MSG_PEERSIZE_OFFSET";
                break;
            default:
                this.msgIdOffsetName = null;
                this.simIdxOffsetName = null;
                this.peerBufSizeName = null;
                break;
        }
        this.doesCurrentMsgHavePeerBufferHeader = false;
        this.parserLastID_internal = -1;
        this.parserLastNameID_internal = null;
        this.parserLastName_internal = null;
    }

    public String[] getMsgList() {
        Set<String> mapKeySet = this.nameIdMap.keySet();
        String[] keyArray = (String[]) mapKeySet.toArray(new String[mapKeySet.size()]);
        Arrays.sort(keyArray);
        return keyArray;
    }

    public Integer getMsgID(String msgName) {
        return this.nameIdMap.get(msgName);
    }

    public String getMsgName(Integer id) {
        BaseMsg m = this.idMsgMap.get(id);
        if (m != null) {
            return m.msgName;
        }
        return null;
    }

    public void setMsgHasPeerBufferHeader(boolean hasHeader) {
        this.doesCurrentMsgHavePeerBufferHeader = hasHeader;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.msgIdOffsetAndSize.clear();
        this.simIdxOffsetAndSize.clear();
        this.peerBufSizeOffsetAndSize.clear();
        this.idMsgMap.clear();
        this.nameIdMap.clear();
    }

    private List<String> fastSplit_1separator(String text, char separator) {
        this.parserSplitList_1separator_internal.clear();
        if (text != null && text.length() > 0) {
            int index1 = 0;
            int index2 = text.indexOf(separator);
            while (index2 >= 0) {
                int indexEnd = index2;
                if (text.charAt(index1) == ' ') {
                    index1++;
                }
                if (text.charAt(index2 - 1) == ' ') {
                    indexEnd--;
                }
                this.parserSplitList_1separator_internal.add(text.substring(index1, indexEnd));
                index1 = index2 + 1;
                index2 = text.indexOf(separator, index1);
            }
            if (index1 <= text.length() - 1) {
                if (text.charAt(index1) == ' ') {
                    index1++;
                }
                if (index1 <= text.length() - 1) {
                    this.parserSplitList_1separator_internal.add(text.substring(index1));
                }
            }
        }
        return this.parserSplitList_1separator_internal;
    }

    private List<String> fastSplit_2separator(String text, char separator1, char separator2) {
        int index2;
        this.parserSplitList_2separator_internal.clear();
        if (text != null && text.length() > 0) {
            int index1 = 0;
            int index22 = text.indexOf(separator1);
            int index3 = text.indexOf(separator2);
            if (index22 > index3) {
                index22 = index3;
            }
            while (index2 >= 0) {
                this.parserSplitList_2separator_internal.add(text.substring(index1, index2).trim());
                index1 = index2 + 1;
                index2 = text.indexOf(separator1, index1);
                int index32 = text.indexOf(separator2, index1);
                if (index2 == -1 || (index2 > index32 && index32 != -1)) {
                    index2 = index32;
                }
            }
            if (index1 < text.length() - 1) {
                this.parserSplitList_2separator_internal.add(text.substring(index1).trim());
            }
        }
        return this.parserSplitList_2separator_internal;
    }

    /* access modifiers changed from: package-private */
    public boolean readLine(String line) {
        int id;
        List<String> s = fastSplit_1separator(line, ',');
        if (s.size() <= 0) {
            return false;
        }
        String str = this.msgIdOffsetName;
        if (str == null || str.length() <= 0 || !s.get(0).equals(this.msgIdOffsetName) || s.size() != 3) {
            String str2 = this.simIdxOffsetName;
            if (str2 == null || str2.length() <= 0 || !s.get(0).equals(this.simIdxOffsetName) || s.size() != 3) {
                String str3 = this.peerBufSizeName;
                if (str3 != null && str3.length() > 0 && s.get(0).equals(this.peerBufSizeName) && s.size() == 3) {
                    this.peerBufSizeOffsetAndSize.offset = Integer.parseInt(s.get(1));
                    this.peerBufSizeOffsetAndSize.size = Integer.parseInt(s.get(2));
                } else if (peerBufHeaderSizeName.length() > 0 && s.get(0).equals(peerBufHeaderSizeName) && s.size() == 2) {
                    this.currentMsgPeerBufferHeaderSize = Integer.parseInt(s.get(1));
                } else if (s.size() < 3 || s.size() > 4) {
                    return false;
                } else {
                    if (s.get(0).equals(this.parserLastNameID_internal)) {
                        id = this.parserLastID_internal;
                    } else {
                        List<String> m = fastSplit_2separator(s.get(0), '(', ')');
                        this.parserLastNameID_internal = s.get(0);
                        this.parserLastID_internal = Integer.parseInt(m.get(1));
                        this.parserLastName_internal = m.get(0);
                        id = this.parserLastID_internal;
                    }
                    BaseMsg msg = this.idMsgMap.get(Integer.valueOf(id));
                    if (msg == null) {
                        msg = new BaseMsg();
                        msg.msgName = this.parserLastName_internal;
                        this.nameIdMap.put(msg.msgName, Integer.valueOf(id));
                    }
                    String fieldName = s.get(1);
                    OffsetAndSize tmpOffsetAndSize = new OffsetAndSize();
                    tmpOffsetAndSize.offset = Integer.parseInt(s.get(2));
                    if (s.size() == 3) {
                        tmpOffsetAndSize.size = -1;
                    } else {
                        tmpOffsetAndSize.size = Integer.parseInt(s.get(3));
                    }
                    msg.fieldMap.put(fieldName, tmpOffsetAndSize);
                    msg.hasPeerBufferHeader = this.doesCurrentMsgHavePeerBufferHeader;
                    msg.peerBufferHeaderSize = this.currentMsgPeerBufferHeaderSize;
                    this.idMsgMap.put(Integer.valueOf(id), msg);
                }
            } else {
                this.simIdxOffsetAndSize.offset = Integer.parseInt(s.get(1));
                this.simIdxOffsetAndSize.size = Integer.parseInt(s.get(2));
            }
        } else {
            this.msgIdOffsetAndSize.offset = Integer.parseInt(s.get(1));
            this.msgIdOffsetAndSize.size = Integer.parseInt(s.get(2));
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public BaseMsg getBaseMsg(Integer id) {
        BaseMsg msg = this.idMsgMap.get(id);
        if (msg == null) {
            return null;
        }
        return msg;
    }

    /* access modifiers changed from: package-private */
    public BaseMsg getBaseMsg(String name) {
        Integer id = getMsgID(name);
        if (id == null) {
            return null;
        }
        return getBaseMsg(id);
    }
}
