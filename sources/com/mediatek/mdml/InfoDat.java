package com.mediatek.mdml;

/* compiled from: PlainDataDecoder */
class InfoDat {
    GlobalID globalID = new GlobalID();
    CommonMsgInfo msgInfo = new CommonMsgInfo(InfoType.MSG_INFO);
    CommonMsgInfo otaInfo = new CommonMsgInfo(InfoType.OTA_INFO);
    SimInfo simInfo = new SimInfo();

    InfoDat() {
    }
}
