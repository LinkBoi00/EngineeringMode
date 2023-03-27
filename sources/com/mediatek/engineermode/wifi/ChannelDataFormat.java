package com.mediatek.engineermode.wifi;

/* compiled from: WifiFormatConfig */
class ChannelDataFormat {
    private ChBandType mBandType;
    private int mId;
    private String mName;

    public ChannelDataFormat(int id, String name, ChBandType bandType) {
        this.mId = id;
        this.mName = name;
        this.mBandType = bandType;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: package-private */
    public int getId() {
        return this.mId;
    }

    /* access modifiers changed from: package-private */
    public ChBandType getChBandType() {
        return this.mBandType;
    }
}
