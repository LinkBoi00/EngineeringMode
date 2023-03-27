package com.mediatek.mdml;

import java.util.HashMap;

/* compiled from: CommonMsgInfo */
class BaseMsg {
    HashMap<String, OffsetAndSize> fieldMap = new HashMap<>();
    boolean hasPeerBufferHeader;
    String msgName;
    int peerBufferHeaderSize;

    BaseMsg() {
    }
}
