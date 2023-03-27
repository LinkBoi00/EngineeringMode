package com.mediatek.mdml;

import java.util.Arrays;
import java.util.Set;

public class Msg {
    BaseMsg baseMsg;
    byte[] buf = null;
    GlobalID globalID;
    OffsetAndSize peerBufSizeOffsetAndSize;
    OffsetAndSize simIdxOffsetAndSize;
    SimInfo simInfo;
    int startPos;
    InfoType type;

    public byte[] getFieldValue(String fieldName) {
        BaseMsg baseMsg2;
        OffsetAndSize fieldOffsetAndSize;
        if (this.buf == null || (baseMsg2 = this.baseMsg) == null || baseMsg2.fieldMap == null || (fieldOffsetAndSize = this.baseMsg.fieldMap.get(fieldName)) == null) {
            return null;
        }
        return Util.getValue(this.buf, this.startPos, fieldOffsetAndSize);
    }

    public byte[] getPeerBufferValue() {
        return getFieldValue("PEER_OFFSET");
    }

    public int getPeerBufferLength() {
        OffsetAndSize offsetAndSize;
        if (this.buf == null || (offsetAndSize = this.peerBufSizeOffsetAndSize) == null || offsetAndSize.size <= 0) {
            return -1;
        }
        int peerBufferSize = Util.getIntValue(this.buf, this.startPos, this.peerBufSizeOffsetAndSize);
        if (peerBufferSize <= 0 || !this.baseMsg.hasPeerBufferHeader) {
            return peerBufferSize;
        }
        return peerBufferSize - this.baseMsg.peerBufferHeaderSize;
    }

    public String[] getFieldList() {
        BaseMsg baseMsg2 = this.baseMsg;
        if (baseMsg2 == null || baseMsg2.fieldMap == null) {
            return null;
        }
        Set<String> mapKeySet = this.baseMsg.fieldMap.keySet();
        if (mapKeySet.size() == 0) {
            return null;
        }
        String[] keyArray = (String[]) mapKeySet.toArray(new String[mapKeySet.size()]);
        Arrays.sort(keyArray);
        return keyArray;
    }

    public final String getMsgName() {
        BaseMsg baseMsg2 = this.baseMsg;
        if (baseMsg2 == null) {
            return null;
        }
        return baseMsg2.msgName;
    }

    public int getSimIdx() {
        OffsetAndSize offsetAndSize;
        if (this.buf == null || (offsetAndSize = this.simIdxOffsetAndSize) == null || offsetAndSize.size <= 0) {
            return -1;
        }
        return getSimIdx(Integer.valueOf(Util.getIntValue(this.buf, this.startPos, this.simIdxOffsetAndSize)));
    }

    public byte[] getOtaMsgFieldValue() {
        return getFieldValue("OTA_MSG_OFFSET");
    }

    public int getOtaMsgAirMsgRawDataBufOffset(byte[] otaMsgFieldValue) {
        int extHdrLen;
        if (this.type != InfoType.OTA_INFO || otaMsgFieldValue == null || (extHdrLen = getOtaMsgExtHdrLen(2, otaMsgFieldValue)) <= 0) {
            return -1;
        }
        return extHdrLen + 2 + 2;
    }

    public int getOtaMsgAirMsgRawDataBufLength(byte[] otaMsgFieldValue) {
        int extHdrLen;
        if (this.type != InfoType.OTA_INFO || otaMsgFieldValue == null || (extHdrLen = getOtaMsgExtHdrLen(2, otaMsgFieldValue)) <= 0) {
            return -1;
        }
        OffsetAndSize tmpOffsetAndSize = new OffsetAndSize();
        tmpOffsetAndSize.offset = extHdrLen + 2;
        tmpOffsetAndSize.size = 2;
        return Util.getIntValue(otaMsgFieldValue, 0, tmpOffsetAndSize);
    }

    public String getOtaMsgGlobalID(byte[] otaMsgFieldValue) {
        int extHdrLen;
        if (this.type != InfoType.OTA_INFO || this.globalID == null || otaMsgFieldValue == null || (extHdrLen = getOtaMsgExtHdrLen(2, otaMsgFieldValue)) <= 0) {
            return null;
        }
        OffsetAndSize tmpOffsetAndSize = new OffsetAndSize();
        tmpOffsetAndSize.offset = 2;
        tmpOffsetAndSize.size = extHdrLen;
        int tmp = Util.getIntValue(otaMsgFieldValue, 0, tmpOffsetAndSize);
        if (tmp == -1) {
            return null;
        }
        return this.globalID.getName(Integer.valueOf(tmp));
    }

    private int getOtaMsgExtHdrLen(int extHdrSize, byte[] otaMsgFieldValue) {
        if (this.type != InfoType.OTA_INFO || otaMsgFieldValue == null) {
            return -1;
        }
        OffsetAndSize tmpOffsetAndSize = new OffsetAndSize();
        tmpOffsetAndSize.offset = 0;
        tmpOffsetAndSize.size = extHdrSize;
        return Util.getIntValue(otaMsgFieldValue, 0, tmpOffsetAndSize);
    }

    private int getSimIdx(Integer id) {
        SimInfo simInfo2 = this.simInfo;
        if (simInfo2 == null) {
            return -1;
        }
        Integer ret = simInfo2.getSimIdx(id);
        if (ret == null) {
            return 1;
        }
        return ret.intValue();
    }
}
